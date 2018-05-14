package com.maoye.mlh_slotmachine.view.homeactivity;

import android.content.Context;
import android.widget.ImageView;

import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.widget.banner.loader.ImageLoader;

/**
 * Created by Rs on 2018/4/27.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public ImageView createView(Context context) {
        ImageView imageView = new ImageView(context);
   /*     int w = DensityUtil.getScreenWidth(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,w);
        imageView.setLayoutParams(layoutParams);*/
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void displayData(Context context, Object data, ImageView view) {
        ImgGlideUtil.displayImage((String) data, view, true);
    }
}
