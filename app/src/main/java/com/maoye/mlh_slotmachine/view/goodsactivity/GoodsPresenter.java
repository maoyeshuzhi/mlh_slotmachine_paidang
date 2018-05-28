package com.maoye.mlh_slotmachine.view.goodsactivity;

import android.support.annotation.NonNull;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;


public class GoodsPresenter extends BasePresenterImpl<GoodsContract.View> implements GoodsContract.Presenter{
private GoodsModel model;

    public GoodsPresenter() {
        model = new GoodsModel();
    }


    @Override
    public void goodsListData(int brandId) {
        model.brandGoods(brandId, new BaseObserver<BaseResult<BrandGoodsBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<BrandGoodsBean> data) {
               if(data!=null) mView.onSuccess(data.getData());

            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public List<List<GoodsBean>> handerGoodsData(List<BrandGoodsBean.ListBean> listBeanXES) {
        List<List<GoodsBean>> resultList = new ArrayList<>();
        List<GoodsBean> itemList = new ArrayList<>();
        for (int i = 0; i < listBeanXES.size(); i++) {
            if (itemList.size() < 5) {
                GoodsBean bean = getGoodsBean(listBeanXES, i);
                itemList.add(bean);
                if (i == listBeanXES.size() - 1) {
                    resultList.add(itemList);
                }
            } else {
                GoodsBean bean = getGoodsBean(listBeanXES, i);
                itemList.add(bean);
                resultList.add(itemList);
                itemList = new ArrayList<>();
            }

        }
        return resultList;
    }

    @NonNull
    private GoodsBean getGoodsBean(List<BrandGoodsBean.ListBean> listBeanXES, int i) {
        GoodsBean bean = new GoodsBean();
        bean.setProduct_id(listBeanXES.get(i).getProduct_id());
        bean.setName(listBeanXES.get(i).getName());
        bean.setImage(listBeanXES.get(i).getImage());
        bean.setOld_price(listBeanXES.get(i).getOld_price());
        bean.setPrice(listBeanXES.get(i).getPrice());
        bean.setProduct_link(listBeanXES.get(i).getProduct_link());
        return bean;
    }
}
