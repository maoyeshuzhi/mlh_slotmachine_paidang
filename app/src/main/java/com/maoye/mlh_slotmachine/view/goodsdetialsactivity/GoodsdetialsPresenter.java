package com.maoye.mlh_slotmachine.view.goodsdetialsactivity;

import android.text.TextUtils;

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
    public int specId;
    public String spec_vals;
    public String price = "0.00";

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

    @Override
    public void addCart(int id, int specId, int num) {
        goodsDetialsModel.addCart(id, specId, num, new BaseObserver<BaseResult>(mView.getContext()) {
            @Override
            protected void onBaseNext(BaseResult data) {
                mView.addCardSucc(data);
            }

            @Override
            protected void onBaseError(Throwable t) {
                LogUtils.e(t + "");
            }
        });
    }

    @Override
    public int getSpecId(List<GoodsDetialsBean.SpecListBean> spec_list) {
        if (specId != 0) {
            return specId;
        } else {
            return spec_list.get(0).getId();
        }
    }


    /**
     * 毫秒转化
     *
     * @param ms
     * @return
     */
    @Override
    public String formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strDay + "天" + strHour + "小时" + strMinute + " 分钟 " + strSecond + " 秒";
    }

    @Override
    public String getSpec_vals(List<GoodsDetialsBean.SpecListBean> spec_list) {
        return TextUtils.isEmpty(spec_vals) ? spec_list.get(0).getSpec_vals() + "" : spec_vals;
    }

    @Override
    public String getPrice(List<GoodsDetialsBean.SpecListBean> spec_list) {
        return price.equals("0.00") ? spec_list.get(0).getPrice() + "" : price;
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
                boolean flag = true;
                for (SpecBean.SpecItemListBean specItemListBean : pecItemList) {
                    if (itemListBean.getItemName().equals(specItemListBean.getItemName())) {
                        flag = false;
                    }
                }
                if (flag) {
                    pecItemList.add(itemListBean);
                }

            }
        }


        boolean isMoreSpecifi = false;
        for (SpecBean bean : list) {
            if (bean.getPecItemList().size() > 1) {
                isMoreSpecifi = true;
            }
        }

        if (!isMoreSpecifi) {
            for (SpecBean bean : list) {
                for (SpecBean.SpecItemListBean itemListBean : bean.getPecItemList()) {
                    itemListBean.setSelect(true);
                }
            }
        }
        return list;
    }

    /**
     * 改变点击状态并获取库存
     *
     * @param specList
     * @param type        点击规格类别
     * @param position    点击位置
     * @param bean
     * @param oldStockNum 原本库存数
     * @return
     */
    public int getStockNum(List<SpecBean> specList, int type, int position, GoodsDetialsBean bean, int oldStockNum) {

        List<SpecBean.SpecItemListBean> pecItemList = specList.get(type).getPecItemList();
        for (int i = 0; i < pecItemList.size(); i++) {
            if (i == position) {
                pecItemList.get(i).setSelect(true);
            } else {
                pecItemList.get(i).setSelect(false);
            }
        }

        return getStockNum(specList, bean, oldStockNum);

    }

    public int getStockNum(List<SpecBean> specList, GoodsDetialsBean bean, int oldStockNum) {
        int stockNum = 0;
        StringBuffer buffer = new StringBuffer();
        for (SpecBean specBean : specList) {
            for (SpecBean.SpecItemListBean itemListBean : specBean.getPecItemList()) {
                if (itemListBean.isSelect()) {
                    buffer.append(itemListBean.getItemName() + ",");
                }
            }
        }

        if (TextUtils.isEmpty(buffer)) {
            return oldStockNum;
        }
        String substring = buffer.toString().substring(0, buffer.length() - 1);
        boolean isallSelect = true;
        for (GoodsDetialsBean.SpecListBean specListBean : bean.getSpec_list()) {
            if (specListBean.getSpec_vals().equals(substring)) {
                stockNum = specListBean.getStock();
                specId = specListBean.getId();
                spec_vals = specListBean.getSpec_vals();
                price = specListBean.getPrice();
                isallSelect = false;
            }
        }

        if (isallSelect) {
            spec_vals = bean.getSpec_list().get(0).getSpec_vals();
            // specId = bean.getSpec_list().get(0).getId();
            price = bean.getSpec_list().get(0).getPrice();
            stockNum = oldStockNum;
        }
        return stockNum;
    }

}
