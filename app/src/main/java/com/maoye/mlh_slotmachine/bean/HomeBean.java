package com.maoye.mlh_slotmachine.bean;

import java.util.List;

/**
 * Created by Rs on 2018/4/16.
 */

public class HomeBean  {
        private int cartNum;
        private int limit;
        private int total;
        private List<AdvertBean> advert;
        private List<ListBeanX> list;

        public int getCartNum() {
            return cartNum;
        }

        public void setCartNum(int cartNum) {
            this.cartNum = cartNum;
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

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class AdvertBean {
            private int id;
            private String image_url;
            private String link_url;
            private int product_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;

            }
        }

        public static class ListBeanX {
            /**
             * id : 14
             * product_id : 206
             * name : 卡帕 小黑鞋
             * image : http://image1.maoye.cn/Uploads/product/281/2017-12-27/201712-2715143426546249.jpg
             * price : 80.00
             */

            private int id;
            private int product_id;
            private String name;
            private String image;
            private String price;
            private String product_link;

            public String getProduct_link() {
                return product_link;
            }

            public void setProduct_link(String product_link) {
                this.product_link = product_link;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
}
