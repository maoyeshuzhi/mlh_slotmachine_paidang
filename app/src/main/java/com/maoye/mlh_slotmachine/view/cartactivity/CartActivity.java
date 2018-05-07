package com.maoye.mlh_slotmachine.view.cartactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.CartGoodsAdapter;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.view.loginactivity.LoginActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 购物车
 */
public class CartActivity extends MVPBaseActivity<CartContract.View, CartPresenter> implements CartContract.View, OnItemChildClickListener, CompoundButton.OnCheckedChangeListener {

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
    @BindView(R.id.nodata_view_rl)
    RelativeLayout nodataViewRl;
    private CartGoodsAdapter cartGoodsAdapter;
    private List<GoodsBean> list = new ArrayList<>();
    public static final String ALL_GOODS_NUM_FORMAT = "购物车商品（共%d件）";
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
        mPresenter.getCartGoods();
        selctallCb.setOnCheckedChangeListener(this);


    }


    @Override
    public void onSuccess(Object o) {
        list = (List<GoodsBean>) o;
        goodsnumTv.setText(String.format(ALL_GOODS_NUM_FORMAT, list.size()));
        cartGoodsAdapter.addDatas(list);

    }

    @Override
    public void onFail(Throwable throwable) {

    }


    @OnClick({R.id.clearcart_tv, R.id.delete_tv, R.id.back_imgbt, R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clearcart_tv:
                //清空购物车
                mPresenter.deleteAll();
                break;
            case R.id.delete_tv:
               String str = mPresenter.getSelectPotion(list);
                if (TextUtils.isEmpty(str)) {
                    Toast.getInstance().toast(this, "请选择需要删除的商品", 2);
                } else {
                    mPresenter.deleteCart(str);
                }
                break;
            case R.id.back_imgbt:
                finish();
                break;
            case R.id.submit_bt:
                ArrayList<GoodsBean> selectList = new ArrayList<>();
                for (GoodsBean bean : list) {
                     if(bean.isSelect()){
                         selectList.add(bean);
                     }
                }
                if(selectList.size()==0){
                    Toast.getInstance().toast(this,"请选择需要购买的商品",2);
                }else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.FROM,Constant.FROM_CART);
                    intent.putExtra(Constant.KEY,selectList);
                    startActivity(intent);
                }

                break;
        }
    }

    /**
     * @param view
     * @param type     click类型
     * @param position
     * @param data
     */
    @Override
    public void onChildItemClick(View view, int type, int position, Object data) {
        switch (type) {
            case CartGoodsAdapter.SELECT_GOODS:
                //是否被选中
                if (list.get(position).isSelect()) {
                    selectnumTv.setText(Integer.valueOf(selectnumTv.getText().toString()) -1 + "");
                } else {
                    selectnumTv.setText(Integer.valueOf(selectnumTv.getText().toString()) + 1 + "");
                }
                 priceTv.setText( mPresenter.getPrice(list));
                break;
            case CartGoodsAdapter.ADD_GOODS:
                mPresenter.changeGoodsNum(position, ((GoodsBean) data).getNum() + 1);
                break;
            case CartGoodsAdapter.SUBTRICT_GOODS:
                mPresenter.changeGoodsNum(position, ((GoodsBean) data).getNum() - 1);
                break;
        }
    }

    @Override
    public void getChangeGoodsNumResult(BaseResult baseResult, int postion, int goodsNum) {
        GoodsBean bean = list.get(postion);
        bean.setNum(goodsNum);
        cartGoodsAdapter.addDatas(list);
    }

    @Override
    public void deleteAllResult(BaseResult baseResult) {
        nodataViewRl.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        list = new ArrayList<>();
        goodsnumTv.setText(String.format(ALL_GOODS_NUM_FORMAT, list.size()));
        selectnumTv.setText("0");
        priceTv.setText(String.format("合计：￥%s",0+""));
        cartGoodsAdapter.addDatas(list);
    }

    @Override
    public void deleteCartResult(BaseResult baseResult) {
        mPresenter.getCartGoods();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            for (GoodsBean bean : list) {
                bean.setSelect(true);
            }
            selectnumTv.setText(list.size()+"");
        } else {
            for (GoodsBean bean : list) {
                bean.setSelect(false);
            }
            selectnumTv.setText(0+"");
        }
        priceTv.setText( mPresenter.getPrice(list));
        cartGoodsAdapter.addDatas(list);
    }
}
