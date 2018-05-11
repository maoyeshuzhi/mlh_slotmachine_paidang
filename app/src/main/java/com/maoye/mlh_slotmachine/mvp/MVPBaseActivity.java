package com.maoye.mlh_slotmachine.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.maoye.mlh_slotmachine.MlhApplication;
import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.CacheBean;
import com.maoye.mlh_slotmachine.bean.CacheBean_;
import com.maoye.mlh_slotmachine.util.httputil.cache.ImgCacheUtil;
import com.maoye.mlh_slotmachine.view.adactivity.AdActivity;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.util.DateUtils;
import com.maoye.mlh_slotmachine.util.NetworkUtil;
import com.maoye.mlh_slotmachine.util.StatusBarUtils;
import com.maoye.mlh_slotmachine.util.httputil.BaseRetrofit;
import com.maoye.mlh_slotmachine.util.httputil.cache.CacheUtil;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;


public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView {
    public T mPresenter;
    private FragmentManager fragmentManager;
    private Fragment showFragment;
    private CountDownTimer timer;
    public static final int TIME = 10 * 60 * 1000;//十分钟

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        hideBottomUIMenu();
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
        fragmentManager = getSupportFragmentManager();
        countDownTimer();
        isSaveAdData();


    }




    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flag);
        }

    }

    /**
     * 是否请求并缓存屏保数据
     */
    private void isSaveAdData() {
        Box<CacheBean> cacheBeanBox = MlhApplication.mBoxStore.boxFor(CacheBean.class);
        List<CacheBean> cacheBeans = cacheBeanBox.query().equal(CacheBean_.name, CacheUtil.ADS).build().find();
        if (cacheBeans != null && cacheBeans.size() > 0) {
            for (int i = 0; i < cacheBeans.size(); i++) {
                if (i != cacheBeans.size() - 1) {
                    cacheBeanBox.remove(cacheBeans.get(i));
                }
            }
            if (!DateUtils.isToday(cacheBeans.get(cacheBeans.size() - 1).getUpdateTime())) {
                adbannerData();
            }
        } else {
            adbannerData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNerwork();
    }

    private void isNerwork() {
        if (!(NetworkUtil.checkNetworkAvailable(this)) && !(MVPBaseActivity.this instanceof AdActivity)) {
            startActivity(new Intent(this, AdActivity.class));
        }
    }

    private void adbannerData() {
        Observable observable = BaseRetrofit.getInstance().apiService.advert(3);
        BaseRetrofit.getInstance().toSubscribe(observable, new BaseObserver<BaseResult<List<AdvertBean>>>(this, false) {
            @Override
            protected void onBaseNext(final BaseResult<List<AdvertBean>> data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CacheBean bean = new CacheBean();
                        for (int i = 0; i < data.getData().size(); i++) {
                            String absolutePath = ImgCacheUtil.saveImageToSdCard(getContext(), data.getData().get(i).getImage_url(), i).getAbsolutePath();
                            data.getData().get(i).setLocal_img_url(absolutePath);
                        }
                        bean.setName(CacheUtil.ADS);
                        bean.setUpdateTime(System.currentTimeMillis());
                        bean.setJsonUrl(new Gson().toJson(data));
                        CacheUtil.put(bean);
                    }
                }).start();


            }

            @Override
            protected void onBaseError(Throwable t) {

            }
        });
    }

    private void countDownTimer() {
        timer = new CountDownTimer(TIME, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (MVPBaseActivity.this instanceof HomeActivity) {

                } else {
                    Intent intent = new Intent(MVPBaseActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        timer.start();
        return super.onTouchEvent(event);
    }

    protected void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 管理fragment的显示与隐藏
     */
    protected void fragmentManager(int resid, Fragment fragment, String tag) {
        //开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏正在显示的Fragment对象
        if (showFragment != null) {
            fragmentTransaction.hide(showFragment);
        }

        Fragment mFragment = fragmentManager.findFragmentByTag(tag);
        if (mFragment != null) {
            fragmentTransaction.show(mFragment);
        } else {
            mFragment = fragment;
            fragmentTransaction.add(resid, mFragment, tag);
        }

        showFragment = mFragment;
        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        timer.cancel();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
