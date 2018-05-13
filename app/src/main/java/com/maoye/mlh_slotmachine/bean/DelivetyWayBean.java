package com.maoye.mlh_slotmachine.bean;

/**
 * Created by Rs on 2018/5/11.
 */

public class DelivetyWayBean {


    private int shiptype;//  -1：门店自提 0：快递 1：EMS 2:平邮
    private String name;

    public int getShiptype() {
        return shiptype;
    }

    public void setShiptype(int shiptype) {
        this.shiptype = shiptype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
