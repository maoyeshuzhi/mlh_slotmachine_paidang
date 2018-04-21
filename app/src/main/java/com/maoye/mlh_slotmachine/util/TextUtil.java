package com.maoye.mlh_slotmachine.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Rs on 2018/4/16.
 */

public class TextUtil {

    public static String setPriceText(String priceText){
       return String.format("￥%s",priceText);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNo(String mobiles) {

        String regex =  "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return Pattern.matches(regex,mobiles);
    }
}
