package com.maoye.mlh_slotmachine.util.httputil.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rs on 2018/5/5.
 */

public class ImgCacheUtil {

    public static final String IMAGE_NAME = "mlhj_";
    //根据网络图片url路径保存到本地
    public static final File saveImageToSdCard(Context context, String image, int i) {
        boolean success = false;
        File file = null;
        try {
            file = createStableImageFile(context, i + "");

            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            FileOutputStream outStream;

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
            bitmap.recycle();
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        if (success) {
            return file;
        } else {
            return null;
        }
    }

    //创建本地保存路径
    public static File createStableImageFile(Context context, String i) throws IOException {
        String imageFileName = IMAGE_NAME + i + ".jpg";
        File storageDir = context.getExternalCacheDir();
        File image = new File(storageDir, imageFileName);
        return image;
    }
}
