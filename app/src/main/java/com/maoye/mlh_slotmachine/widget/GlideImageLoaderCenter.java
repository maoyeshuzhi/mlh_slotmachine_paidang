package com.maoye.mlh_slotmachine.widget;

import android.content.Context;
import android.widget.ImageView;

import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Rs on 2018/5/9.
 */

public class GlideImageLoaderCenter extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImgGlideUtil.displayImage((String) path, imageView, true);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
