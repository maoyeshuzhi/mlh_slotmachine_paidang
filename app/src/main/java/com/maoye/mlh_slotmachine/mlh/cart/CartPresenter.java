package com.maoye.mlh_slotmachine.mlh.cart;

import android.content.Context;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.ArrayList;
import java.util.List;

public class CartPresenter extends BasePresenterImpl<CartContract.View> implements CartContract.Presenter{
    private CartModel mModel;
    public CartPresenter() {
        this.mModel = new CartModel();
    }


    @Override
    public void getCartGoods() {
            mModel.getCartGoodsList(new BaseObserver<BaseResult<List<GoodsBean>>>(mView.getContext(),true) {
                @Override
                protected void onBaseNext(BaseResult<List<GoodsBean>> data) {
                      mView.onSuccess(data.getData());
                }

                @Override
                protected void onBaseError(Throwable t) {

                }
            });
    }

    @Override
    public void changeGoodsNum(final int index, final int num) {
        mModel.changeGoodsNum(index, num, new BaseObserver<BaseResult>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult data) {
                mView.getChangeGoodsNumResult(data,index,num);
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void deleteAll() {
        mModel.deleteAll(new BaseObserver<BaseResult>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult data) {
                mView.deleteAllResult(data);
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public void deleteCart(String indexs) {
        mModel.deleteCart(indexs, new BaseObserver<BaseResult>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult data) {
                mView.deleteCartResult(data);
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public String getSelectPotion(List<GoodsBean> list) {
       StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isSelect()){
                buffer.append(i+",");
            }
        }

        return buffer.length()==0?"":buffer.toString();
    }

    @Override
    public String getPrice(List<GoodsBean> list) {
        double price = 0.00;
        for (GoodsBean bean : list) {
            if(bean.isSelect()){
                price =price + Double.valueOf(bean.getPrice())*bean.getNum();
            }
        }
        return String.format("合计：￥%s",price+"");
    }
}
