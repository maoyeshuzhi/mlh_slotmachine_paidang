package com.maoye.mlh_slotmachine.view.confirmorderactivity.succfragment;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.AdvertBean;
import com.maoye.mlh_slotmachine.bean.OrderDetialBean;
import com.maoye.mlh_slotmachine.view.goodsdetialsactivity.GoodsdetialsActivity;
import com.maoye.mlh_slotmachine.view.h5activity.H5Activity;
import com.maoye.mlh_slotmachine.view.homeactivity.GlideImageLoader;
import com.maoye.mlh_slotmachine.view.homeactivity.HomeActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;
import com.maoye.mlh_slotmachine.webservice.URL;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SuccFragment extends MVPBaseFragment<SuccContract.View, SuccPresenter> implements SuccContract.View {

    @BindView(R.id.order_no_tv)
    TextView orderNoTv;
    @BindView(R.id.mobile_tv)
    TextView mobileTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.code_img)
    ImageView codeImg;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.count_down_tv)
    TextView countDownTv;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    private List<AdvertBean> list = new ArrayList<>();
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_succ, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        mPresenter.getBannerData(2);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list.size() == 0) return;
                AdvertBean advertBean = list.get(position);
                mPresenter.statisticClickNum(advertBean.getId());
                if (!TextUtils.isEmpty(advertBean.getLink_url())) {
                    //调外部链接
                    Intent intent = new Intent(getActivity(), H5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.KEY,advertBean.getLink_url());
                    startActivity(intent);
                } else if (advertBean.getProduct_id() != 0) {
                    Intent intent = new Intent(getActivity(), GoodsdetialsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.GOODS_ID, advertBean.getProduct_id());
                    startActivity(intent);
                }
            }
        });

        OrderDetialBean bean = (OrderDetialBean) getArguments().getSerializable(Constant.KEY);
        if (bean != null) {
            orderNoTv.setText(bean.getOrder_no() + "");
            mobileTv.setText(bean.getMobile() + "");
            priceTv.setText(bean.getPaid_amount() + "元");
            timeTv.setText(bean.getPaid_time() + "");
            codeImg.setImageBitmap(CodeUtils.createQRCode(EnvConfig.instance().getWebServiceBaseUrl() + URL.ORDER_DETIAL_H5 + bean.getOrder_id(), 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.code_logo))
            );
        }

        countDownTimer = new CountDownTimer(10 * 1000, 1000) {
              @Override
              public void onTick(long l) {
                  countDownTv.setText(l / 1000 + "s");
              }

              @Override
              public void onFinish() {
                  Intent intent = new Intent(getActivity(), HomeActivity.class);
                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
                  getActivity().finish();
              }
          }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        countDownTimer.onTick(10 * 1000);
        countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public void onSuccess(Object o) {
        List<AdvertBean> list = (List<AdvertBean>) o;
        this.list = list;
        List<String> stringList = new ArrayList<>();
        for (AdvertBean advertBean : list) {
            stringList.add(advertBean.getImage_url());
        }
        banner.update(stringList);
    }

    @Override
    public void onFail(Throwable throwable) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
