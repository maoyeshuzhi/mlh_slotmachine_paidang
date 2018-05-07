package com.maoye.mlh_slotmachine.view.homeactivity;

import android.content.Context;
import android.widget.ImageView;

import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Rs on 2018/4/27.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImgGlideUtil.displayImage((String) path, imageView, true);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
   /*     int w = DensityUtil.getScreenWidth(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,w);
        imageView.setLayoutParams(layoutParams);*/
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
}
