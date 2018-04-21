package com.maoye.mlh_slotmachine.mlh.home;


import android.util.Log;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.LoginBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.ArrayList;
import java.util.List;


public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {
    private final HomeModel homeModel;

    public HomePresenter() {
        this.homeModel = new HomeModel();
    }

    @Override
    public void homedata(String deviceNo) {
        homeModel.getHomeData(deviceNo, new BaseObserver<BaseResult<HomeBean>>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult<HomeBean> data) {
                mView.onSuccess(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                mView.onFail(t);
            }
        });
    }

    @Override
    public void statisticClickNum(int adId) {
        homeModel.onClickAd(adId, new BaseObserver<BaseResult>(mView.getContext(), false) {
            @Override
            protected void onBaseNext(BaseResult data) {
            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    @Override
    public List<List<HomeBean.ListBeanX>> handerGoodsData(List<HomeBean.ListBeanX> listBeanXES) {

        List<List<HomeBean.ListBeanX>> resultList = new ArrayList<>();
        List<HomeBean.ListBeanX> itemList = new ArrayList<>();
        for (int i = 0; i < listBeanXES.size(); i++) {
            if (itemList.size() < 6) {
                itemList.add(listBeanXES.get(i));
                if (i == listBeanXES.size() - 1) {
                    resultList.add(itemList);
                }
            } else {
                resultList.add(itemList);
                itemList = new ArrayList<>();
            }

        }

        return resultList;
    }


}
