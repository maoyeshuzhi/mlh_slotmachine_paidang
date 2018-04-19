package com.maoye.mlh_slotmachine.mlh.goodsdetials;

import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.bean.SpecBean;
import com.maoye.mlh_slotmachine.mvp.BasePresenterImpl;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.ArrayList;
import java.util.List;

public class GoodsdetialsPresenter extends BasePresenterImpl<GoodsdetialsContract.View> implements GoodsdetialsContract.Presenter {
    private GoodsDetialsModel goodsDetialsModel;

    public GoodsdetialsPresenter() {
        this.goodsDetialsModel = new GoodsDetialsModel();
    }

    @Override
    public void getGoodsDetialsData(int id) {
        goodsDetialsModel.questGoodsDetialsData(id, new BaseObserver<BaseResult<GoodsDetialsBean>>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult<GoodsDetialsBean> data) {
                mView.onSuccess(data.getData());
            }

            @Override
            protected void onBaseError(Throwable t) {
                LogUtils.e(t.getMessage() + "");
            }
        });
    }


    /**
     * 处理规格数据
     *
     * @param spec_name_list
     * @param spec_list
     * @return
     */
    public List<SpecBean> handleSpecifi(List<String> spec_name_list, List<GoodsDetialsBean.SpecListBean> spec_list) {
        List<SpecBean> list = new ArrayList<>();
        for (String s : spec_name_list) {
            SpecBean bean = new SpecBean();
            List<SpecBean.SpecItemListBean> itemList = new ArrayList<>();
            bean.setSpecName(s);
            bean.setPecItemList(itemList);
            list.add(bean);
        }

        for (GoodsDetialsBean.SpecListBean specListBean : spec_list) {
            for (int i = 0; i < specListBean.getSpec_val_list().size(); i++) {
                List<SpecBean.SpecItemListBean> pecItemList = list.get(i).getPecItemList();
                SpecBean.SpecItemListBean itemListBean = new SpecBean.SpecItemListBean();
                itemListBean.setItemName(specListBean.getSpec_val_list().get(i));
                pecItemList.add(itemListBean);
            }
        }
        return list;
    }

    public int getStockNum(List<SpecBean> specList,int type,int position,GoodsDetialsBean bean,int oldStockNum) {
        int stockNum = 0;
        List<SpecBean.SpecItemListBean> pecItemList = specList.get(type).getPecItemList();
        for (int i = 0; i < pecItemList.size(); i++) {
            if(i ==position){
                pecItemList.get(i).setSelect(true);
            }else {
                pecItemList.get(i).setSelect(false);
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (SpecBean specBean : specList) {
            for (SpecBean.SpecItemListBean itemListBean : specBean.getPecItemList()) {
                if(itemListBean.isSelect()){
                    buffer.append(itemListBean.getItemName()+",");
                }
            }
        }


        String substring = buffer.toString().substring(0, buffer.length() - 1);
        boolean isallSelect = true;
        for (GoodsDetialsBean.SpecListBean specListBean : bean.getSpec_list()) {
            if(specListBean.getSpec_vals().equals(substring)) {
                stockNum = specListBean.getStock();
                isallSelect = false;
            }
        }
        if(isallSelect){
            stockNum = oldStockNum;
        }
        return stockNum;

    }

}
