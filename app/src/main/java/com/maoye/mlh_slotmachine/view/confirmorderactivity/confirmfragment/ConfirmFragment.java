package com.maoye.mlh_slotmachine.view.confirmorderactivity.confirmfragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapter;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.AddressAdapter;
import com.maoye.mlh_slotmachine.adapter.OrderGoodsAdapter;
import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.DelivetyWayBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.SubmitOrderBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.listener.OnMultiClickListener;
import com.maoye.mlh_slotmachine.view.confirmorderactivity.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.Poputils;
import com.maoye.mlh_slotmachine.util.SoftUtil;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.widget.DialogAddLocalAddress;
import com.maoye.mlh_slotmachine.widget.DialogAddressCode;
import com.maoye.mlh_slotmachine.widget.DialogIsAddAdress;
import com.maoye.mlh_slotmachine.widget.GiveUpPayDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


public class ConfirmFragment extends MVPBaseFragment<ConfirmContract.View, ConfirmPresenter> implements ConfirmContract.View, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    @BindView(R.id.pickup_rb)
    RadioButton pickupRb;
    @BindView(R.id.express_rb)
    RadioButton expressRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    @BindView(R.id.address_ll)
    LinearLayout addressLl;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.expresstype_rb)
    RadioButton expresstypeRb;
    @BindView(R.id.EMS_rb)
    RadioButton EMSRb;
    @BindView(R.id.mail_rb)
    RadioButton mailRb;
    @BindView(R.id.deliverway_rg)
    RadioGroup deliverwayRg;
    @BindView(R.id.address_bottom_ll)
    LinearLayout addressBottomLl;
    @BindView(R.id.back_imgbt)
    ImageButton backImgbt;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.isdelivery_tv)
    TextView isdeliveryTv;
    @BindView(R.id.submit_bt)
    Button submitBt;
    Unbinder unbinder;
    private OrderGoodsAdapter goodsAdapter;
    private List<GoodsBean> goodsList = new ArrayList<>();
    private List<AddressBean.ListBean> addressList = new ArrayList<>();
    private AddressAdapter addressAdapter;
    private DialogIsAddAdress addressDialog;
    private DialogAddLocalAddress addLocalAddressDialog;
    private DialogAddressCode addressCodeDialog;
    private EditText editText;
    private double FEE;//邮费
    private double GOODS_PRICE;//商品金额
    private double ALL_PRICE;
    public static final String FEE_FORMAT = "(邮费￥%s)";
    public static final String FEE_HINT = "（不含运费）";
    public static final String PRICE_FORMAT = "合计：￥%s";
    private PopupWindow payPopWindow;
    private CallBackConfirmFragment callBackConfirmFragment;
    private View payView;
    private TextView priceTvPop;
    private int ORDER_ID;
    private String ORDER_NO;
    private RelativeLayout paypop_1;

    private TextView time1Tv;
    private CountDownTimer countDownTimer1;
    public static final int TIME = 120 * 1000;
    public static final String TIME_FORMAT = "剩%s秒自动关闭";
    private int payType;
    private String add_address_link;

    private OrderDetialBean orderDetialBean;
    private int deliveryType;


    //定义一个回调接口
    public interface CallBackConfirmFragment {
        void onCallBack(int payType, OrderDetialBean orderDetialBean);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackConfirmFragment = (CallBackConfirmFragment) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.layout_submitorder, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        submitBt.setOnClickListener(new OnMultiClickListener(1) {
            @Override
            public void onMultiClick(View view) {
                submitOrder();
            }
        });
        RxView.clicks(submitBt)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        submitOrder();
                    }
                });

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        payView = layoutInflater.inflate(R.layout.pop_pay, null);
        time1Tv = payView.findViewById(R.id.time1_tv);
        priceTvPop = payView.findViewById(R.id.price_tv);
        paypop_1 = payView.findViewById(R.id.paypop_1);
        payView.findViewById(R.id.dismiss_img).setOnClickListener(this);
        payView.findViewById(R.id.weichatpay_tv).setOnClickListener(this);
        payView.findViewById(R.id.alipay_tv).setOnClickListener(this);
    }


    private void initData() {
        initcountDownTimer();
        goodsList = (List<GoodsBean>) getActivity().getIntent().getSerializableExtra(Constant.KEY);
        deliveryType = mPresenter.getDeliveryType(goodsList);
        if (deliveryType == 1) {
            switchSelivryWay(0);
            expressRb.setClickable(false);
            expressRb.setEnabled(false);
            expressRb.setTextColor(Color.parseColor("#ffffff"));
            expressRb.setBackgroundColor(getResources().getColor(R.color.color_c8c8c8));
        } else if (deliveryType == 2) {
            expressRb.setChecked(true);
            switchSelivryWay(1);
            pickupRb.setClickable(false);
            pickupRb.setEnabled(false);
            pickupRb.setTextColor(Color.parseColor("#ffffff"));
            pickupRb.setBackgroundColor(getResources().getColor(R.color.color_c8c8c8));
            getAddress();
        } else {
            getAddress();
            switchSelivryWay(0);
        }
        mPresenter.deliveryWay(goodsList.get(0).getProduct_id() + "");

        GOODS_PRICE = mPresenter.getGoodsPrice(goodsList);
        ALL_PRICE = GOODS_PRICE;
        priceTv.setText(String.format(PRICE_FORMAT, String.format("%.2f", ALL_PRICE) + ""));
        rg.setOnCheckedChangeListener(this);
        addressAdapter = new AddressAdapter();
        addressRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        addressRecycler.setAdapter(addressAdapter);
        goodsAdapter = new OrderGoodsAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(goodsAdapter);
        goodsAdapter.addDatas(goodsList);
        editText = new EditText(getContext());
        addLocalAddressDialog = new DialogAddLocalAddress(getContext());
        addLocalAddressDialog.setView(editText);
        addressDialog = new DialogIsAddAdress(getContext());
        addressDialog.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                if (type == 0) {
                    addLocalAddressDialog.show();
                    SoftUtil.showSoft(1, editText);
                } else if (type == 1) {
                    addressCodeDialog = new DialogAddressCode(getContext(), add_address_link + "");
                    addressCodeDialog.setOnItemChildClickListener(new OnItemChildClickListener() {
                        @Override
                        public void onChildItemClick(View view, int type, int position, Object data) {
                            if (deliveryType != 1) {
                                getAddress();
                            }
                        }
                    });
                    addressCodeDialog.show();
                }
            }
        });

        addLocalAddressDialog.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                getAddress();
            }
        });


        deliverwayRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (addressList == null || addressList.size() == 0)
                    return;
                switch (i) {
                    case R.id.expresstype_rb://快递
                        if (expressRb.isChecked()) {
                            String strings = mPresenter.productInfo(goodsList);
                            mPresenter.getFeight(mPresenter.getArea_id(addressList), 0, strings);
                        }
                        break;
                    case R.id.EMS_rb://ems
                        if (expressRb.isChecked()) {
                            mPresenter.getFeight(mPresenter.getArea_id(addressList), 1, mPresenter.productInfo(goodsList));
                        }
                        break;
                    case R.id.mail_rb://邮寄
                        if (expressRb.isChecked())
                            mPresenter.getFeight(mPresenter.getArea_id(addressList), 2, mPresenter.productInfo(goodsList));
                        break;
                }
            }
        });

        addressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                if (position == addressList.size() - 1) {
                    addressDialog.show();
                } else {
                    for (int i = 0; i < addressList.size(); i++) {
                        if (position == i) {
                            addressList.get(i).setDefaultX(1);
                        } else {
                            addressList.get(i).setDefaultX(0);
                        }
                    }
                    addressAdapter.addDatas(addressList);
                    if (expresstypeRb.isChecked()) {
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 0, mPresenter.productInfo(goodsList));
                    } else if (EMSRb.isChecked()) {
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 1, mPresenter.productInfo(goodsList));
                    } else if (mailRb.isChecked()) {
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 2, mPresenter.productInfo(goodsList));

                    }
                }
            }


        });


    }

    BaseObserver addressObserver = new BaseObserver<BaseResult<AddressBean>>(getContext()) {
        @Override
        protected void onBaseNext(BaseResult<AddressBean> data) {
            addressList.clear();
            addressList = data.getData().getList();
            add_address_link = data.getData().getAdd_address_link();
            AddressBean.ListBean listBean = new AddressBean.ListBean();
            listBean.setAddAddress(true);
            addressList.add(listBean);
            addressAdapter.addDatas(addressList);

        }

        @Override
        protected void onBaseError(Throwable t) {
            mPresenter.getAddressList(addressObserver);
            addressLl.setVisibility(View.GONE);
        }
    };

    private void getAddress() {
        mPresenter.getAddressList(addressObserver);
    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        countDownTimer1.cancel();
    }

    @OnClick({R.id.back_imgbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_imgbt:
                getActivity().finish();
                break;
            case R.id.dismiss_img:
                GiveUpPayDialog dialog = new GiveUpPayDialog(getContext());
                dialog.show();
                dialog.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, Object data) {
                        payPopWindow.dismiss();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                });

                break;
            case R.id.weichatpay_tv:
                payType = ConfirmOrderActivity.WECHAT_PAY;
                callBackPayWay(ConfirmOrderActivity.WECHAT_PAY);
                countDownTimer1.cancel();
                payPopWindow.dismiss();
                break;
            case R.id.alipay_tv:
                payType = ConfirmOrderActivity.ALI_PAY;
                callBackPayWay(ConfirmOrderActivity.ALI_PAY);
                countDownTimer1.cancel();
                payPopWindow.dismiss();
                break;

        }
    }


    private void initcountDownTimer() {
        countDownTimer1 = new CountDownTimer(TIME, 1000) {
            @Override
            public void onTick(long l) {
                time1Tv.setText(String.format(TIME_FORMAT, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }
        };


    }

    /**
     * 回调支付方式
     */
    private void callBackPayWay(int type) {
        orderDetialBean.setPayType(type);
        callBackConfirmFragment.onCallBack(type, orderDetialBean);
    }

    /**
     *
     */
    private void submitOrder() {
        submitBt.setClickable(false);
        submitBt.setEnabled(false);
        SubmitOrderBean bean = new SubmitOrderBean();
        bean.setAddress_id(mPresenter.getAddress_id(addressList));
        bean.setShip_type(getDeliveryWay());
        bean.setIs_cart(getActivity().getIntent().getIntExtra(Constant.FROM, 0));
        bean.setProduct_list(new Gson().toJson(goodsList));
        mPresenter.submitOrder(bean, new BaseObserver<BaseResult<OrderIdBean>>(getContext(), true) {
            @Override
            protected void onBaseNext(BaseResult<OrderIdBean> data) {
                mPresenter.orderDetials(data.getData().getOrder_id());
            }

            @Override
            protected void onBaseError(Throwable t) {
                submitBt.setClickable(true);
                submitBt.setEnabled(true);
            }
        });
    }

    /**
     * 得到收货方式
     */
    private int getDeliveryWay() {
        if (pickupRb.isChecked()) {
            return -1;
        } else if (expresstypeRb.isChecked()) {
            return 0;
        } else if (EMSRb.isChecked()) {
            return 1;
        } else if (mailRb.isChecked()) {
            return 2;
        }
        return 0;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.pickup_rb:
                switchSelivryWay(0);
                break;
            case R.id.express_rb:
                switchSelivryWay(1);
                if (addressList.size() > 0) {
                    if (expresstypeRb.isChecked()) {
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 0, mPresenter.productInfo(goodsList));
                    } else if (EMSRb.isChecked()) {
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 1, mPresenter.productInfo(goodsList));
                    } else if (mailRb.isChecked()) {
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 2, mPresenter.productInfo(goodsList));

                    }
                }
                break;
        }
    }


    /**
     * 快递方式 0为门店自提
     *
     * @param type
     * @param type
     */
    private void switchSelivryWay(int type) {
        if (type == 0) {
            addressLl.setVisibility(View.GONE);
            addressBottomLl.setVisibility(View.GONE);
            pickupRb.setTextColor(getResources().getColor(R.color.white));
            expressRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            isdeliveryTv.setText(FEE_HINT);
            ALL_PRICE = Double.valueOf(String.format("%.2f", GOODS_PRICE));
            priceTv.setText(String.format(PRICE_FORMAT, ALL_PRICE + ""));
        } else if (type == 1) {
            isdeliveryTv.setText(String.format(FEE_FORMAT, FEE + ""));
            ALL_PRICE = Double.valueOf(String.format("%.2f", GOODS_PRICE + FEE));
            priceTv.setText(String.format(PRICE_FORMAT, String.format("%.2f", ALL_PRICE) + ""));
            pickupRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            expressRb.setTextColor(getResources().getColor(R.color.white));
            addressLl.setVisibility(View.VISIBLE);
            addressBottomLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getFreight(double fee) {
        FEE = Double.valueOf(String.format("%.2f", fee));
        isdeliveryTv.setText(String.format(FEE_FORMAT, fee + ""));
        ALL_PRICE = Double.valueOf(String.format("%.2f", GOODS_PRICE + FEE) + "");
        priceTv.setText(String.format(PRICE_FORMAT, String.format("%.2f", ALL_PRICE) + ""));
    }


    @Override
    public void getDeliveryType(List<DelivetyWayBean> list) {
        boolean isExpress = false;
        boolean isEms = false;
        boolean isMailRb = false;
        for (DelivetyWayBean delivetyWayBean : list) {
            if (delivetyWayBean.getShiptype() == 0) {
                expresstypeRb.setVisibility(View.VISIBLE);
                expresstypeRb.setClickable(true);
                expresstypeRb.setEnabled(true);
                isExpress = true;
            }
            if (delivetyWayBean.getShiptype() == 1) {
                EMSRb.setVisibility(View.VISIBLE);
                EMSRb.setClickable(true);
                EMSRb.setEnabled(true);
                isEms = true;
            }
            if (delivetyWayBean.getShiptype() == 2) {
                mailRb.setVisibility(View.VISIBLE);
                mailRb.setClickable(true);
                mailRb.setEnabled(true);
                isMailRb = true;
            }
        }
        if (isExpress) {
            expresstypeRb.setChecked(true);
        } else if (isEms) {
            EMSRb.setChecked(true);
        } else if (isMailRb) {
            mailRb.setChecked(true);
        }
    }

    @Override
    public void getOrderDetials(OrderDetialBean orderDetialBean) {
        this.orderDetialBean = orderDetialBean;
        callBackPayWay(ConfirmOrderActivity.CONFIRMF_RAGGMENT);
        priceTvPop.setText(String.format(Constant.PRICE_FORMAT,String.format("%.2f",ALL_PRICE)+""));
        payPopWindow = Poputils.getPop(payView, R.layout.layout_submitorder, getActivity());
        countDownTimer1.start();
    }
}
