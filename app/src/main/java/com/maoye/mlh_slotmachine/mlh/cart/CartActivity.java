package com.maoye.mlh_slotmachine.mlh.cart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.CartGoodsAdapter;
import com.maoye.mlh_slotmachine.bean.GoodsItemBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 购物车
 */
public class CartActivity extends MVPBaseActivity<CartContract.View, CartPresenter> implements CartContract.View, OnItemChildClickListener {

    @BindView(R.id.goodsnum_tv)
    TextView goodsnumTv;
    @BindView(R.id.selctall_cb)
    CheckBox selctallCb;
    @BindView(R.id.selectnum_tv)
    TextView selectnumTv;
    @BindView(R.id.clearcart_tv)
    TextView clearcartTv;
    @BindView(R.id.delete_tv)
    TextView deleteTv;
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
    private CartGoodsAdapter cartGoodsAdapter;
    private List<GoodsItemBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        cartGoodsAdapter = new CartGoodsAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(cartGoodsAdapter);
        cartGoodsAdapter.setOnItemChildClickListener(this);

        for (int i = 0; i < 10; i++) {
            GoodsItemBean bean = new GoodsItemBean();
            list.add(bean);
        }
        cartGoodsAdapter.addDatas(list);

    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }


    @OnClick({R.id.clearcart_tv, R.id.delete_tv, R.id.back_imgbt, R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clearcart_tv:
                break;
            case R.id.delete_tv:
                break;
            case R.id.back_imgbt:
                break;
            case R.id.submit_bt:
                break;
        }
    }

    @Override
    public void onChildItemClick(View view, int type, int position, Object data) {
        switch (type) {
            case CartGoodsAdapter.SELECT_GOODS:
                break;
        }
    }
}
