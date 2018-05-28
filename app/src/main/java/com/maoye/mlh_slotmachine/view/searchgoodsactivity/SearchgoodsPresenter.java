package com.maoye.mlh_slotmachine.view.searchgoodsactivity;

import android.support.annotation.NonNull;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;
import com.maoye.mlh_slotmachine.view.goodsactivity.GoodsModel;

import java.util.ArrayList;
import java.util.List;


public class SearchgoodsPresenter extends BasePresenterImpl<SearchgoodsContract.View> implements SearchgoodsContract.Presenter{
private GoodsModel model;
    public SearchgoodsPresenter() {
        model = new GoodsModel();
    }

    @Override
    public void defaultGoods() {
        model.defautlGoods(new BaseObserver<BaseResult<List<GoodsBean>>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<List<GoodsBean>> data) {
                if(data!=null) mView.onSuccess(data);
            }

            @Override
            protected void onBaseError(Throwable t) {
                mView.onFail(t);

            }
        });
    }

    @Override
    public void searchGoods(String name) {
        model.searchGoods(name, new BaseObserver<BaseResult<BrandGoodsBean>>(mView.getContext(),true) {
            @Override
            protected void onBaseNext(BaseResult<BrandGoodsBean> data) {
                if(data!=null) mView.searchGoodsListData(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public List<List<GoodsBean>> handerGoodsData(List<GoodsBean> listBeanXES) {
        List<List<GoodsBean>> resultList = new ArrayList<>();
        List<GoodsBean> itemList = new ArrayList<>();
        for (int i = 0; i < listBeanXES.size(); i++) {
            if (itemList.size() < 5) {
                itemList.add(listBeanXES.get(i));
                if (i == listBeanXES.size() - 1) {
                    resultList.add(itemList);
                }
            } else {
                itemList.add(listBeanXES.get(i));
                resultList.add(itemList);
                itemList = new ArrayList<>();
            }

        }
        return resultList;
    }

    public List<List<GoodsBean>> handerBrandGoodsData(List<BrandGoodsBean.ListBean> listBeanXES) {
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
    public GoodsBean getGoodsBean(List<BrandGoodsBean.ListBean> listBeanXES, int i) {
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
