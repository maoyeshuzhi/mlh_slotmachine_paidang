/*
 * Copyright (c) 2016. Bond(China)
 */

package com.maoye.mlh_slotmachine.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期的操作工具集锦
 *
 * @version 1.0.0
 */
public final class DateUtils {
    private DateUtils() {

    }

    /**
     * 日期格式
     *
     * @author Bond(China)
     */
    public enum Pattern {

        /**
         * 格式:20161102
         */
        YYYYMMDD("yyyyMMdd"),
        /**
         * 格式：2016/11/02
         */
        SPLIT_YYYY_MM_DD("yyyy/MM/dd"),
        /**
         * 格式：2016-11-02
         */
        YYYY_MM_DD("yyyy-MM-dd"),
        /**
         * 格式：2016-11-02 13:22:59
         */
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        /**
         * 格式：2016-11-02 星期一
         */
        YYYY_MM_DD_WEEK("yyyy-MM-dd EEE"),

        /**
         * 格式：HH:mm:ss
         */
        HH_MM_SS("HH:mm:ss");

        /**
         * pattern value
         */
        private String value;

        Pattern(String value) {
            this.value = value;
        }
    }

    /**
     * String to Date by pattern
     *
     * @param date    Date对象
     * @param pattern 格式化的模式
     * @return Date
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            // LOGGER.warn("[DateUtils parse]", e);
            return null;
        }
    }

    /**
     * Date to String by pattern
     *
     * @param date    Date对象
     * @param pattern 格式化的模式
     * @return Date
     */
    public static String format(Date date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(date);
        } catch (Exception e) {
            //LOGGER.warn("[DateUtils format]", e);
            return null;
        }
    }

    /**
     * String to Date by pattern
     *
     * @param date    Date对象
     * @param pattern 格式化的模式
     * @return Date
     */
    public static Date parse(String date, Pattern pattern) {

        return parse(date, pattern.value);
    }


    /**
     * Integer to date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Integer date, Pattern pattern) {

        return format(new Date(date), pattern.value);
    }

    public static String format(String date, Pattern pattern) {

        return format(new Date(date), pattern.value);
    }

    /**
     * Long to date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Long date, Pattern pattern) {
        if(date.toString().length()<=10){
            return format(new Date(date*1000), pattern.value);
        }else {
            return format(new Date(date), pattern.value);
        }
    }

    /**
     * Long to date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Double date, Pattern pattern) {
        Long val = date.longValue();
        return format(new Date(val), pattern.value);
    }


    /**
     * Date to String by pattern
     *
     * @param date    Date对象
     * @param pattern 格式化的模式
     * @return Date
     */
    public static String format(Date date, Pattern pattern) {

        return format(date, pattern.value);
    }

    /**
     * 对现在的时间进行格式化返回
     *
     * @param pattern
     * @return
     */
    public static String formatNow(Pattern pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 对现在的时间进行格式化返回
     *
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        return format(new Date(), pattern);
    }


    /**
     * 计算时间溅差
     * @param endTime
     * @param startTime
     * @return
     */
    public static long differentDay(long endTime, long startTime) {
        if((endTime+"").length()<13){
            endTime = endTime*1000;
        }
        if((startTime+"").length()<13){
            startTime = startTime*1000;
        }
        Date d1 = null;
        Date d2 = null;
            d1 = new Date(endTime);
            d2 =new Date(startTime);
       // long diff = (d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24);
        long diff = d1.getTime() - d2.getTime();
        return diff +1;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time
     *            需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowTime(long time) {
        boolean isDayu = false;
            Date parse = new Date(time);
            Date parse1 = new Date(System.currentTimeMillis());
            long diff = parse1.getTime() - parse.getTime();
            if (diff <= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }

        return isDayu;
    }



    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
      /*  SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");*/
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param opTime 时间戳
     * @return boolean
     */
    public static boolean isToday(long opTime) {
       String opDate = format(opTime, Pattern.YYYYMMDD);
        boolean isToday = false;
        String today = String.format(Locale.getDefault(), "%1$tY%1$tm%1$td",
                System.currentTimeMillis());
        if (today.equals(opDate)) {
            isToday = true;
        }
        return isToday;
    }

    public static boolean isEmpty(String sid) {
        return TextUtils.isEmpty(sid);
    }

}
