package com.maoye.mlh_slotmachine.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rs on 2018/4/21.
 */

public class AddressBean {
    private String add_address_link;
    private int total;
    private List<ListBean> list;

    public String getAdd_address_link() {
        return add_address_link;
    }

    public void setAdd_address_link(String add_address_link) {
        this.add_address_link = add_address_link;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 293
         * province_id : 20
         * province_name : 广东省
         * city_id : 233
         * city_name : 深圳市
         * area_id : 2416
         * area_name : 龙岗区
         * phone : 13128837503
         * name : 罗超
         * default : 1
         * street : 中心围
         */

        private int id;
        private int province_id;
        private String province_name;
        private int city_id;
        private String city_name;
        private int area_id;
        private String area_name;
        private String phone;
        private String name;
        @SerializedName("default")
        private int defaultX;//1为默认选中
        private String street;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }
    }
}
