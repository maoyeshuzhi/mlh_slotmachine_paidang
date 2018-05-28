package com.maoye.mlh_slotmachine.util.httputil.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maoye.mlh_slotmachine.MlhApplication;
import com.maoye.mlh_slotmachine.bean.CacheBean;
import com.maoye.mlh_slotmachine.bean.CacheBean_;


import java.lang.reflect.Type;
import java.util.List;

import io.objectbox.Box;

/**
 * Created by Rs on 2018/5/3.
 */
public class CacheUtil {
    public static final String HOME_ACTIVITY = "homeactivity";
    public static final int HOME_ACTIVITY_ID = 0;
    public static final int GOODS_DETIAL_ACTIVITY_ID = 1;
    public static final String GOODS_DETIALS = "goodsdetials";
    public static final String GOODS_ACTIVITY = "goodsactivity";
    public static final String DEFAULT_GOODS = "default_goods";
    public static final String ADS = "ads";//广告页面

    /**
     * @param dbName
     * @param cls    需要转化的实体类
     * @return
     */
    public static Object query(String dbName, Class cls) {
        Box<CacheBean> cacheBeanBox = MlhApplication.mBoxStore.boxFor(CacheBean.class);
        List<CacheBean> cacheBeans = cacheBeanBox.query().equal(CacheBean_.name, dbName).build().find();
        if (cacheBeans == null || cacheBeans.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < cacheBeans.size(); i++) {
                if (i != cacheBeans.size() - 1) {
                    cacheBeanBox.remove(cacheBeans.get(i));
                }
            }
            return new Gson().fromJson(cacheBeans.get(cacheBeans.size() - 1).getJsonUrl(), cls);
        }
    }


    public static String query(String dbName) {
        Box<CacheBean> cacheBeanBox = MlhApplication.mBoxStore.boxFor(CacheBean.class);
        List<CacheBean> cacheBeans = cacheBeanBox.query().equal(CacheBean_.name, dbName).build().find();
        if (cacheBeans == null || cacheBeans.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < cacheBeans.size(); i++) {
                if (i != cacheBeans.size() - 1) {
                    cacheBeanBox.remove(cacheBeans.get(i));
                }
            }
            return cacheBeans.get(cacheBeans.size() - 1).getJsonUrl();
        }
    }




    public void undate(String name, String url) {
        Box<CacheBean> cacheBeanBox = MlhApplication.mBoxStore.boxFor(CacheBean.class);
        List<CacheBean> cacheBeans = cacheBeanBox.query().equal(CacheBean_.name, name).build().find();
        cacheBeans.get(0).setJsonUrl(url);
        cacheBeanBox.put(cacheBeans.get(0));
    }

    public static void put(CacheBean bean) {
        Box<CacheBean> cacheBeanBox = MlhApplication.mBoxStore.boxFor(CacheBean.class);
        cacheBeanBox.put(bean);
    }

}
