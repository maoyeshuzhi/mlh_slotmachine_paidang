package com.maoye.mlh_slotmachine.bean;

/**
 * Created by Rs on 2018/5/23.
 */

public class BaseInfo {
    private static String sapId;
    private static String quickPhone;
    private static String storeName;
    private static String BillTille;


    public static void setSapId(String sapId) {
        BaseInfo.sapId = sapId;
    }

    public static String getSapId() {
      return sapId;
    }

    public static void setQuickPhone(String quickPhone) {
        BaseInfo.quickPhone = quickPhone;
    }

    public static String getQuickPhone() {
      return quickPhone;
    }

    public static void setStoreName(String storeName) {
        BaseInfo.storeName = storeName;
    }

    public static String getStoreName() {
      return storeName;
    }



}
