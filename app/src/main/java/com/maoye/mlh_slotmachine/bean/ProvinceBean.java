package com.maoye.mlh_slotmachine.bean;

/**
 * Created by Rs on 2018/4/23.
 */

public class ProvinceBean {

    /**
     * region_id : 2
     * region_name : 北京
     */

    private int region_id;
    private String region_name;
    private boolean  isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
