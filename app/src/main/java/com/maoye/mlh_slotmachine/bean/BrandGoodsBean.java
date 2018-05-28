package com.maoye.mlh_slotmachine.bean;

import java.util.List;

/**
 * Created by Rs on 2018/5/21.
 */

public class BrandGoodsBean {



    private int total;
    private String background_image;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * product_id : 142
         * name : 测试结算码时间
         * image : http://image1.maoye.cn/Uploads/product/281/2017-11-29/201711-2915119216717304.jpg
         * old_price : 156.00
         * price : 156.00
         * product_link : http://172.29.36.157/goods?id=142
         */

        private int product_id;
        private String name;
        private String image;
        private String old_price;
        private String price;
        private String product_link;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
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

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProduct_link() {
            return product_link;
        }

        public void setProduct_link(String product_link) {
            this.product_link = product_link;
        }
    }
}
