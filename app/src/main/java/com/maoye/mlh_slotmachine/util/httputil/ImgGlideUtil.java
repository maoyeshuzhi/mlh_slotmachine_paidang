package com.maoye.mlh_slotmachine.util.httputil;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.maoye.mlh_slotmachine.R;

/**
 * Created by Rs on 2018/4/16.
 */

public class ImgGlideUtil {
    public static final boolean IS_CACHE = true;


    private static ImgGlideUtil instance;

    private ImgGlideUtil() {
    }

    public static ImgGlideUtil getInstance() {
        if (instance == null) {
            instance = new ImgGlideUtil();
        }
        return instance;
    }


    public static void displayImage(String url, ImageView imageView, boolean iscache) {
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(iscache ?DiskCacheStrategy.RESULT:DiskCacheStrategy.NONE)
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.default_image)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
