package com.maoye.mlh_slotmachine.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Rs on 2018/4/16.
 */

public class TextUtil {

    public static String setPriceText(String priceText){
       return String.format("￥%s",priceText);
    }
    public static boolean isMobile(String str) throws PatternSyntaxException {
        if(str==null|| TextUtils.isEmpty(str))return false;
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
