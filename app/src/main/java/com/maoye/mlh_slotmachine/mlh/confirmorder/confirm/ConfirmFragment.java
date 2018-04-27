package com.maoye.mlh_slotmachine.mlh.confirmorder.confirm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.AddressAdapter;
import com.maoye.mlh_slotmachine.adapter.OrderGoodsAdapter;
import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.OrderIdBean;
import com.maoye.mlh_slotmachine.bean.SubmitOrderBean;
import com.maoye.mlh_slotmachine.event.GoodsEventBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mlh.confirmorder.ConfirmOrderActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.Poputils;
import com.maoye.mlh_slotmachine.util.SoftUtil;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.widget.DialogAddLocalAddress;
import com.maoye.mlh_slotmachine.widget.DialogAddressCode;
import com.maoye.mlh_slotmachine.widget.DialogIsAddAdress;
import com.maoye.mlh_slotmachine.widget.PayPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ConfirmFragment extends MVPBaseFragment<ConfirmContract.View, ConfirmPresenter> implements ConfirmContract.View, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    @BindView(R.id.pickup_rb)
    RadioButton pickupRb;
    @BindView(R.id.express_rb)
    RadioButton expressRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    @BindView(R.id.addAddress_tv)
    TextView addAddressTv;
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

    //定义一个回调接口
    public interface CallBackConfirmFragment {
         void onCallBack(int payType, GoodsEventBean goodsEventBean);
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
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        payView = layoutInflater.inflate(R.layout.pop_pay, null);
        priceTvPop = payView.findViewById(R.id.price_tv);

        payView.findViewById(R.id.dismiss_img).setOnClickListener(this);
        payView.findViewById(R.id.weichatpay_tv).setOnClickListener(this);
        payView.findViewById(R.id.alipay_tv).setOnClickListener(this);
        payView.findViewById(R.id.wechet_ali_code_pay_tv).setOnClickListener(this);

    }


    private void initData() {
        switchSelivryWay(0);
        goodsList = (List<GoodsBean>) getActivity().getIntent().getSerializableExtra(Constant.KEY);
        GOODS_PRICE = mPresenter.getGoodsPrice(goodsList);
        priceTv.setText(String.format(PRICE_FORMAT, GOODS_PRICE + ""));

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
        addressCodeDialog = new DialogAddressCode(getContext(), "url");
        addressDialog = new DialogIsAddAdress(getContext());
        addressDialog.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                if (type == 0) {
                    addLocalAddressDialog.show();
                    SoftUtil.showSoft(1, editText);
                } else if (type == 1) {

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
                switch (i) {
                    case R.id.expresstype_rb://快递
                        String strings = mPresenter.productInfo(goodsList);
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 0, strings);
                        break;
                    case R.id.EMS_rb://ems
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 1, mPresenter.productInfo(goodsList));
                        break;
                    case R.id.mail_rb://邮寄
                        mPresenter.getFeight(mPresenter.getArea_id(addressList), 2, mPresenter.productInfo(goodsList));
                        break;
                }
            }
        });

        addressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                for (int i = 0; i < addressList.size(); i++) {
                    if (position == i) {
                        addressList.get(i).setDefaultX(1);
                    } else {
                        addressList.get(i).setDefaultX(0);
                    }
                }
                addressAdapter.addDatas(addressList);
            }
        });

        getAddress();
    }


    private void getAddress() {
        mPresenter.getAddressList(new BaseObserver<BaseResult<AddressBean>>(getContext()) {
            @Override
            protected void onBaseNext(BaseResult<AddressBean> data) {
                addressList = data.getData().getList();
                addressAdapter.addDatas(addressList);
            }

            @Override
            protected void onBaseError(Throwable t) {
                addressLl.setVisibility(View.GONE);
            }
        });
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
    }

    @OnClick({R.id.addAddress_tv, R.id.back_imgbt, R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_imgbt:
                getActivity().finish();
                break;
            case R.id.submit_bt:
                submitOrder();
                break;
            case R.id.addAddress_tv:
                //添加地址
                addressDialog.show();
                break;
            case R.id.dismiss_img:
                payPopWindow.dismiss();
                break;
            case R.id.weichatpay_tv:
                callBackPayWay(ConfirmOrderActivity.WECHAT_PAY);
                break;
            case R.id.alipay_tv:
                callBackPayWay(ConfirmOrderActivity.ALI_PAY);
                break;
            case R.id.wechet_ali_code_pay_tv:
                break;
        }
    }

    /**
     * 回调支付方式
     */
    private void callBackPayWay(int type) {
        GoodsEventBean bean = new GoodsEventBean();
        bean.setPayType(type);
        bean.setOrderNo(ORDER_NO);
        bean.setOrderId(ORDER_ID);
        bean.setGoodsImg(goodsList.get(0).getProduct_image());
        bean.setGoodsName(goodsList.get(0).getProduct_name());
        double price = 0.00;
        int goodsNum = 0;
        for (GoodsBean goodsBean : goodsList) {
            price = Double.valueOf(goodsBean.getPrice())*goodsBean.getNum();
            goodsNum = goodsNum+goodsBean.getNum();
        }
        bean.setGoodsNum(goodsNum);
        bean.setPrice(price+"");
        callBackConfirmFragment.onCallBack(type,bean);
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
        LogUtils.e(new Gson().toJson(bean));
        mPresenter.submitOrder(bean, new BaseObserver<BaseResult<OrderIdBean>>(getContext(), true) {
            @Override
            protected void onBaseNext(BaseResult<OrderIdBean> data) {
                payPopWindow = Poputils.getPop(payView, R.layout.layout_submitorder, getActivity());
                priceTvPop.setText(ALL_PRICE + "");
                ORDER_ID = data.getData().getOrder_id();
                ORDER_NO = data.getData().getOrder_no();
                callBackConfirmFragment.onCallBack(ConfirmOrderActivity.CONFIRMF_RAGGMENT,null);
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
                if (expresstypeRb.isChecked()) {
                    mPresenter.getFeight(mPresenter.getArea_id(addressList), 0, mPresenter.productInfo(goodsList));
                } else if (EMSRb.isChecked()) {
                    mPresenter.getFeight(mPresenter.getArea_id(addressList), 1, mPresenter.productInfo(goodsList));
                } else if (mailRb.isChecked()) {
                    mPresenter.getFeight(mPresenter.getArea_id(addressList), 2, mPresenter.productInfo(goodsList));

                }

                break;
        }
    }


    /**
     * 快递方式 0为门店自提
     *
     * @param type
     */
    private void switchSelivryWay(int type) {
        if (type == 0) {
            addressLl.setVisibility(View.GONE);
            addressBottomLl.setVisibility(View.GONE);
            pickupRb.setTextColor(getResources().getColor(R.color.white));
            isdeliveryTv.setText(FEE_HINT);
            ALL_PRICE = GOODS_PRICE;
            priceTv.setText(String.format(PRICE_FORMAT, GOODS_PRICE + ""));
        } else {
            isdeliveryTv.setText(String.format(FEE_FORMAT, FEE + ""));
            ALL_PRICE = GOODS_PRICE + FEE;
            priceTv.setText(String.format(PRICE_FORMAT, ALL_PRICE + ""));
            pickupRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            addressLl.setVisibility(View.VISIBLE);
            addressBottomLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getFreight(double fee) {
        FEE = fee;
        isdeliveryTv.setText(String.format(FEE_FORMAT, fee + ""));
        ALL_PRICE = GOODS_PRICE + FEE;
        priceTv.setText(String.format(PRICE_FORMAT, GOODS_PRICE + fee + ""));
    }
}
