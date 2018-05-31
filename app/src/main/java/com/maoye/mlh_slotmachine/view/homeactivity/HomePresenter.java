package com.maoye.mlh_slotmachine.view.homeactivity;


import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.bean.VersionInfoBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.httputil.BaseRetrofit;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {
    private final HomeModel homeModel;

    public HomePresenter() {
        this.homeModel = new HomeModel();
    }

    @Override
    public void homedata(boolean isShowDialog) {
        homeModel.getHomeData( new BaseObserver<BaseResult<HomeBean>>(mView.getContext(),isShowDialog) {
            @Override
            protected void onBaseNext(BaseResult<HomeBean> data) {
                if(mView!=null)
                mView.onSuccess(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                if(mView!=null)
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
            if (itemList.size() < 3) {
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

    @Override
    public void versionInfo() {
      homeModel.versionInfo(new BaseObserver<BaseResult<VersionInfoBean>>(mView.getContext(),false) {
          @Override
          protected void onBaseNext(BaseResult<VersionInfoBean> data) {
              if(mView!=null&&data!=null) mView.getVersionInfo(data.getData());
          }

          @Override
          protected void onBaseError(Throwable t) {

          }
      });
    }
}
