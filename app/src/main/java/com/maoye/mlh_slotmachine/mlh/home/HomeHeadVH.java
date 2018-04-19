package com.maoye.mlh_slotmachine.mlh.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.maoye.mlh_slotmachine.bean.HomeBean;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.widget.banner.holder.Holder;

/**
 * Created by Rs on 2018/4/16.
 */

public class HomeHeadVH implements Holder<HomeBean.AdvertBean> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position,HomeBean.AdvertBean data) {
        ImgGlideUtil.displayImage(data.getImage_url(),imageView,true);
    }
}
