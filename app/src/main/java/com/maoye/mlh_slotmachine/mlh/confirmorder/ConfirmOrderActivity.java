package com.maoye.mlh_slotmachine.mlh.confirmorder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.AddressAdapter;
import com.maoye.mlh_slotmachine.adapter.OrderGoodsAdapter;
import com.maoye.mlh_slotmachine.bean.AddressBean;
import com.maoye.mlh_slotmachine.bean.GoodsItemBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.SoftUtil;
import com.maoye.mlh_slotmachine.widget.DialogAddLocalAddress;
import com.maoye.mlh_slotmachine.widget.DialogAddressCode;
import com.maoye.mlh_slotmachine.widget.DialogIsAddAdress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 确认订单
 */
public class ConfirmOrderActivity extends MVPBaseActivity<ConfirmorderContract.View, ConfirmorderPresenter> implements ConfirmorderContract.View, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.pickup_rb)
    RadioButton pickupRb;
    @BindView(R.id.express_rb)
    RadioButton expressRb;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.stub_import)
    ViewStub stubImport;
    @BindView(R.id.back_imgbt)
    ImageButton backImgbt;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.submit_bt)
    Button submitBt;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    @BindView(R.id.address_ll)
    LinearLayout addressLl;
    @BindView(R.id.deliverway_rg)
    RadioGroup deliverwayRg;
    @BindView(R.id.isdelivery_tv)
    TextView isdeliveryTv;
    @BindView(R.id.address_bottom_ll)
    LinearLayout addressBottomLl;
    @BindView(R.id.flow1_tv)
    TextView flow1Tv;
    @BindView(R.id.flow2_tv)
    TextView flow2Tv;
    @BindView(R.id.flow3_tv)
    TextView flow3Tv;
    @BindView(R.id.flow4_tv)
    TextView flow4Tv;
    @BindView(R.id.flow5_tv)
    TextView flow5Tv;
    @BindView(R.id.expresstype_rb)
    RadioButton expresstypeRb;
    @BindView(R.id.EMS_rb)
    RadioButton EMSRb;
    @BindView(R.id.mail_rb)
    RadioButton mailRb;
    @BindView(R.id.addAddress_tv)
    TextView addAddressTv;
    @BindView(R.id.space_view)
    View spaceView;
    private OrderGoodsAdapter goodsAdapter;
    private List<GoodsItemBean> goodsList = new ArrayList<>();
    private List<AddressBean> addressList = new ArrayList<>();
    private AddressAdapter addressAdapter;
    private DialogIsAddAdress addressDialog;
    private DialogAddLocalAddress addLocalAddressDialog;
    private DialogAddressCode addressCodeDialog;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        rg.setOnCheckedChangeListener(this);
        addressAdapter = new AddressAdapter();
        addressRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        addressRecycler.setAdapter(addressAdapter);
        goodsAdapter = new OrderGoodsAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(goodsAdapter);
        for (int i = 0; i < 10; i++) {
            GoodsItemBean bean = new GoodsItemBean();
            goodsList.add(bean);
            AddressBean addressBean = new AddressBean();
            addressList.add(addressBean);
        }
        addressAdapter.addDatas(addressList);
        goodsAdapter.addDatas(goodsList);
        editText = new EditText(getContext());
        addLocalAddressDialog = new DialogAddLocalAddress(getContext());
        addLocalAddressDialog.setView(editText);
        addressCodeDialog = new DialogAddressCode(getContext(),"url");
        addressDialog = new DialogIsAddAdress(this);
        addressDialog.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                  if(type ==0){
                      addLocalAddressDialog.show();
                      SoftUtil.showSoft(1, editText);
                  }else if(type ==1){

                    addressCodeDialog.show();
                }
            }
        });
    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.back_imgbt, R.id.submit_bt,R.id.addAddress_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_imgbt:
                finish();
                break;
            case R.id.submit_bt:
                break;
            case R.id.addAddress_tv:
                //添加地址
                addressDialog.show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.pickup_rb:
                switchSelivryWay(0);
                break;
            case R.id.express_rb:
                switchSelivryWay(1);
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
            spaceView.setVisibility(View.GONE);
            addressLl.setVisibility(View.GONE);
            addressBottomLl.setVisibility(View.GONE);
            pickupRb.setTextColor(getResources().getColor(R.color.white));

        } else {
            spaceView.setVisibility(View.VISIBLE);
            pickupRb.setTextColor(getResources().getColor(R.color.color_1e1e1e));
            addressLl.setVisibility(View.VISIBLE);
            addressBottomLl.setVisibility(View.VISIBLE);
        }
    }

}
