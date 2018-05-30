package com.maoye.mlh_slotmachine.bean;

import java.util.List;

/**
 * Created by Rs on 2018/5/28.
 */

public class BrandGoodsListBean {

    /**
     * brand_image : http://image1.maoye.cn/Uploads/template/281/2017-12-21/201712-2115138504285523.jpg
     * limit : 10
     * total : 6
     * list : [{"id":62,"name":"普通商品","image":"http://image1.maoye.cn/Uploads/product/281/2017-09-11/201709-1115051190302611.jpg","price":"0.01"},{"id":105,"name":"低功耗8","image":"http://image1.maoye.cn/Uploads/product/281/2017-10-20/201710-2015084777523661.jpg","price":"0.01"},{"id":108,"name":"低功耗5","image":"http://image1.maoye.cn/Uploads/product/281/2017-10-20/201710-2015084939745171.jpg","price":"0.01"},{"id":117,"name":"低功耗1","image":"http://image1.maoye.cn/Uploads/product/281/2017-10-28/201710-2815091566306298.jpg","price":"19.90"},{"id":270,"name":"fas","image":"http://image1.maoye.cn/Uploads/product/281/2018-03-28/201803-2815222011039836.jpg","price":"1.00"},{"id":288,"name":"11111111","image":"http://image1.maoye.cn/Uploads/product/281/2018-04-09/201804-0915232578172873.jpg","price":"22.00"}]
     */

    private String brand_image;
    private int limit;
    private int total;
    private List<ListBean> list;

    public String getBrand_image() {
        return brand_image;
    }

    public void setBrand_image(String brand_image) {
        this.brand_image = brand_image;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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
         * id : 62
         * name : 普通商品
         * image : http://image1.maoye.cn/Uploads/product/281/2017-09-11/201709-1115051190302611.jpg
         * price : 0.01
         */

        private int id;
        private String name;
        private String image;
        private String price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
