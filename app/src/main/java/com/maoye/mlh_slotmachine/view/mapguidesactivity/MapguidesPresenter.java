package com.maoye.mlh_slotmachine.view.mapguidesactivity;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.BrandGoodsListBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.MyContext;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;


public class MapguidesPresenter extends BasePresenterImpl<MapguidesContract.View> implements MapguidesContract.Presenter{
   private MapGuideModel  model;

    public MapguidesPresenter() {
        model = new MapGuideModel();
    }

    @Override
    public void brandDetial(String id, int page, int limit, int type) {
        model.brandDetials(id, page, limit, new BaseObserver<BaseResult<BrandGoodsListBean>>(MyContext.appContext,type== Constant.NOMARL?true:false) {
            @Override
            protected void onBaseNext(BaseResult<BrandGoodsListBean> data) {
                   if(mView!=null) mView.onSuccess(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                  if(mView!=null) mView.onFail(t);
            }
        });
    }
}
