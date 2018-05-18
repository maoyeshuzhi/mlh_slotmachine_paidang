package com.maoye.mlh_slotmachine.view.imgactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ImgActivity extends MVPBaseActivity<ImgContract.View, ImgPresenter> implements ImgContract.View {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.imageView_current_tv)
    TextView imageViewCurrentTv;
    @BindView(R.id.imageView_total_tv)
    TextView imageViewTotalTv;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {
        Intent intent = getIntent();
        int POSITION = intent.getIntExtra(Constant.POSITION, 0);
        list = (List<String>) intent.getSerializableExtra(Constant.KEY);
        imageViewTotalTv.setText("/"+list.size() );
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageViewCurrentTv.setText(position+1 + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(ImgActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(view.getContext()).load(list.get(position))
                        .placeholder(R.mipmap.default_image)
                        .crossFade()
                         .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(view);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        pager.setCurrentItem(POSITION);
    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @OnClick({R.id.back,R.id.back_img})
    public void onClick() {
        finish();
    }
}
