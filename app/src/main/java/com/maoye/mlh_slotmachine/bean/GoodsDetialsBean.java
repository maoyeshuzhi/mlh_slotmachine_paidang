package com.maoye.mlh_slotmachine.bean;

import java.util.List;

/**
 * Created by Rs on 2018/4/16.
 */

public class GoodsDetialsBean {

    /**
     * id : 203
     * shop_id : 227
     * comp_id : 212
     * name : 巧克力糖
     * introduction :
     * old_price : 20.00
     * price : 1000.00
     * category_id : 55
     * category_name :
     * notice : <p>真诚服务每一天</p><p><img src="http://image1.maoye.cn/Uploads/ueditor/php/upload/image/20171225/1514170612244282.jpg" title="1514170612244282.jpg" alt="1 (2).jpg"/></p>
     * description : <p>辗转须臾无定数</p><p>半生荣华一朝散</p><p><img src="http://image1.maoye.cn/Uploads/ueditor/php/upload/image/20171225/1514170855548572.jpg" title="1514170855548572.jpg"/></p><p><img src="http://image1.maoye.cn/Uploads/ueditor/php/upload/image/20171225/1514170855676148.jpg" title="1514170855676148.jpg"/></p><p><br/></p>
     * brand_id : 3880
     * brand_name : 卡帕
     * brand_no : 100581
     * brand_source : 1
     * is_show : 1
     * enabled : 1
     * is_refund : 1
     * is_activity : 1
     * activity_type : 1
     * activity_title : 巧克力
     * activity_id : 78
     * goods_type : 1
     * is_limit : 0
     * limit_num : 0
     * limit_start_time : 0
     * limit_end_time : 0
     * start_time : 2018-03-27 00:00:00
     * end_time : 2018-04-30 00:00:00
     * is_pinkage : 0
     * default_image : http://image1.maoye.cn/Uploads/product/281/2017-12-25/201712-2515141705303709.jpg
     * spu_code :
     * spec_name_list : ["尺码","颜色"]
     * buy_number : 0
     * service_address : 世界金融中心
     * service_phone : 13512345678
     * product_type : 0
     * delivery_type : 0
     * sap_BrandCname : 卡帕
     * sap_BrandNo : 100581
     * sap_NameL : 深圳市克瑞斯特体育用品有限公司
     * sap_Lifnr : 0010003332
     * weight : 0
     * freight_id : 17
     * points_ratio : 0
     * points_pay_ratio : 10
     * min_points : 0
     * max_points : 0
     * image_list : [{"image_url":"http://image1.maoye.cn/Uploads/product/281/2017-12-25/201712-2515141705303709.jpg","is_default":1},{"image_url":"http://image1.maoye.cn/Uploads/product/281/2018-03-28/201803-2815222240923826.jpg","is_default":0},{"image_url":"http://image1.maoye.cn/Uploads/product/281/2018-03-28/201803-2815222240964426.jpg","is_default":0},{"image_url":"http://image1.maoye.cn/Uploads/product/281/2018-03-28/201803-2815222241012450.jpg","is_default":0},{"image_url":"http://image1.maoye.cn/Uploads/product/281/2018-03-28/201803-2815222241061512.jpg","is_default":0}]
     * spec_list : [{"activity_spec_id":181,"id":976,"old_price":"100.00","price":"1000.00","stock":72,"sku_code":"","spec_val_list":["XXS","黑色"],"spec_vals":"XXS,黑色","is_default":1,"sap_Ean11":"0100866401","sap_Akpos":"000000","sap_Aktnr":"","sap_Akart":"","sap_Ztacname":"","sap_Vkdab":"0000-00-00","sap_Vktim":"00:00:00","sap_Vedab":"0000-00-00","sap_Vetim":"00:00:00","sap_Ztacid":"00000000","sap_ZpDiscount":"0.0","sap_ZcashRate":"0.0","sap_BrandCname":"卡帕","sap_BrandNo":"100581","sap_NameL":"深圳市克瑞斯特体育用品有限公司","sap_Lifnr":"0010003332"},{"activity_spec_id":182,"id":977,"old_price":"100.00","price":"1000.00","stock":95,"sku_code":"","spec_val_list":["165","黑色"],"spec_vals":"165,黑色","is_default":0,"sap_Ean11":"0100866401","sap_Akpos":"000000","sap_Aktnr":"","sap_Akart":"","sap_Ztacname":"","sap_Vkdab":"0000-00-00","sap_Vktim":"00:00:00","sap_Vedab":"0000-00-00","sap_Vetim":"00:00:00","sap_Ztacid":"00000000","sap_ZpDiscount":"0.0","sap_ZcashRate":"0.0","sap_BrandCname":"卡帕","sap_BrandNo":"100581","sap_NameL":"深圳市克瑞斯特体育用品有限公司","sap_Lifnr":"0010003332"}]
     * stock : 167
     * activity_price_int : 1000
     * activity_price_dec : 00
     * activity_start_time : 03月27日00点
     * activity_end_time : 04月30日00点
     * is_index : 1
     */
private int cartNum;
    private int id;
    private int shop_id;
    private int comp_id;
    private String name;
    private String introduction;
    private String old_price;
    private String price;
    private int category_id;
    private String category_name;
    private String notice;
    private String description;
    private int brand_id;
    private String brand_name;
    private String brand_no;
    private int brand_source;
    private int is_show;
    private int enabled;
    private int is_refund;
    private int is_activity;
    private int activity_type;
    private String activity_title;
    private int activity_id;
    private int goods_type;
    private int is_limit;
    private int limit_num;
    private String limit_start_time;
    private String limit_end_time;
    private long start_time;
    private long end_time;
    private int is_pinkage;
    private String default_image;
    private String spu_code;
    private int buy_number;
    private String service_address;
    private String service_phone;
    private int product_type;
    private int delivery_type;
    private String sap_BrandCname;
    private String sap_BrandNo;
    private String sap_NameL;
    private String sap_Lifnr;
    private int weight;
    private int freight_id;
    private int points_ratio;
    private int points_pay_ratio;
    private int min_points;
    private int max_points;
    private int stock;
    private String activity_price_int;
    private String activity_price_dec;
    private String activity_start_time;
    private String activity_end_time;
    private int is_index;
    private List<String> spec_name_list;
    private List<ImageListBean> image_list;
    private List<SpecListBean> spec_list;

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getComp_id() {
        return comp_id;
    }

    public void setComp_id(int comp_id) {
        this.comp_id = comp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_no() {
        return brand_no;
    }

    public void setBrand_no(String brand_no) {
        this.brand_no = brand_no;
    }

    public int getBrand_source() {
        return brand_source;
    }

    public void setBrand_source(int brand_source) {
        this.brand_source = brand_source;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(int is_refund) {
        this.is_refund = is_refund;
    }

    public int getIs_activity() {
        return is_activity;
    }

    public void setIs_activity(int is_activity) {
        this.is_activity = is_activity;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public int getIs_limit() {
        return is_limit;
    }

    public void setIs_limit(int is_limit) {
        this.is_limit = is_limit;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public String getLimit_start_time() {
        return limit_start_time;
    }

    public void setLimit_start_time(String limit_start_time) {
        this.limit_start_time = limit_start_time;
    }

    public String getLimit_end_time() {
        return limit_end_time;
    }

    public void setLimit_end_time(String limit_end_time) {
        this.limit_end_time = limit_end_time;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getIs_pinkage() {
        return is_pinkage;
    }

    public void setIs_pinkage(int is_pinkage) {
        this.is_pinkage = is_pinkage;
    }

    public String getDefault_image() {
        return default_image;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    public String getSpu_code() {
        return spu_code;
    }

    public void setSpu_code(String spu_code) {
        this.spu_code = spu_code;
    }

    public int getBuy_number() {
        return buy_number;
    }

    public void setBuy_number(int buy_number) {
        this.buy_number = buy_number;
    }

    public String getService_address() {
        return service_address;
    }

    public void setService_address(String service_address) {
        this.service_address = service_address;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getSap_BrandCname() {
        return sap_BrandCname;
    }

    public void setSap_BrandCname(String sap_BrandCname) {
        this.sap_BrandCname = sap_BrandCname;
    }

    public String getSap_BrandNo() {
        return sap_BrandNo;
    }

    public void setSap_BrandNo(String sap_BrandNo) {
        this.sap_BrandNo = sap_BrandNo;
    }

    public String getSap_NameL() {
        return sap_NameL;
    }

    public void setSap_NameL(String sap_NameL) {
        this.sap_NameL = sap_NameL;
    }

    public String getSap_Lifnr() {
        return sap_Lifnr;
    }

    public void setSap_Lifnr(String sap_Lifnr) {
        this.sap_Lifnr = sap_Lifnr;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getFreight_id() {
        return freight_id;
    }

    public void setFreight_id(int freight_id) {
        this.freight_id = freight_id;
    }

    public int getPoints_ratio() {
        return points_ratio;
    }

    public void setPoints_ratio(int points_ratio) {
        this.points_ratio = points_ratio;
    }

    public int getPoints_pay_ratio() {
        return points_pay_ratio;
    }

    public void setPoints_pay_ratio(int points_pay_ratio) {
        this.points_pay_ratio = points_pay_ratio;
    }

    public int getMin_points() {
        return min_points;
    }

    public void setMin_points(int min_points) {
        this.min_points = min_points;
    }

    public int getMax_points() {
        return max_points;
    }

    public void setMax_points(int max_points) {
        this.max_points = max_points;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getActivity_price_int() {
        return activity_price_int;
    }

    public void setActivity_price_int(String activity_price_int) {
        this.activity_price_int = activity_price_int;
    }

    public String getActivity_price_dec() {
        return activity_price_dec;
    }

    public void setActivity_price_dec(String activity_price_dec) {
        this.activity_price_dec = activity_price_dec;
    }

    public String getActivity_start_time() {
        return activity_start_time;
    }

    public void setActivity_start_time(String activity_start_time) {
        this.activity_start_time = activity_start_time;
    }

    public String getActivity_end_time() {
        return activity_end_time;
    }

    public void setActivity_end_time(String activity_end_time) {
        this.activity_end_time = activity_end_time;
    }

    public int getIs_index() {
        return is_index;
    }

    public void setIs_index(int is_index) {
        this.is_index = is_index;
    }

    public List<String> getSpec_name_list() {
        return spec_name_list;
    }

    public void setSpec_name_list(List<String> spec_name_list) {
        this.spec_name_list = spec_name_list;
    }

    public List<ImageListBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImageListBean> image_list) {
        this.image_list = image_list;
    }

    public List<SpecListBean> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<SpecListBean> spec_list) {
        this.spec_list = spec_list;
    }

    public static class ImageListBean {
        /**
         * image_url : http://image1.maoye.cn/Uploads/product/281/2017-12-25/201712-2515141705303709.jpg
         * is_default : 1
         */

        private String image_url;
        private int is_default;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }

    public static class SpecListBean {
        /**
         * activity_spec_id : 181
         * id : 976
         * old_price : 100.00
         * price : 1000.00
         * stock : 72
         * sku_code :
         * spec_val_list : ["XXS","黑色"]
         * spec_vals : XXS,黑色
         * is_default : 1
         * sap_Ean11 : 0100866401
         * sap_Akpos : 000000
         * sap_Aktnr :
         * sap_Akart :
         * sap_Ztacname :
         * sap_Vkdab : 0000-00-00
         * sap_Vktim : 00:00:00
         * sap_Vedab : 0000-00-00
         * sap_Vetim : 00:00:00
         * sap_Ztacid : 00000000
         * sap_ZpDiscount : 0.0
         * sap_ZcashRate : 0.0
         * sap_BrandCname : 卡帕
         * sap_BrandNo : 100581
         * sap_NameL : 深圳市克瑞斯特体育用品有限公司
         * sap_Lifnr : 0010003332
         */

        private int activity_spec_id;
        private int id;
        private String old_price;
        private String price;
        private int stock;
        private String sku_code;
        private String spec_vals;
        private int is_default;
        private String sap_Ean11;
        private String sap_Akpos;
        private String sap_Aktnr;
        private String sap_Akart;
        private String sap_Ztacname;
        private String sap_Vkdab;
        private String sap_Vktim;
        private String sap_Vedab;
        private String sap_Vetim;
        private String sap_Ztacid;
        private String sap_ZpDiscount;
        private String sap_ZcashRate;
        private String sap_BrandCname;
        private String sap_BrandNo;
        private String sap_NameL;
        private String sap_Lifnr;
        private List<String> spec_val_list;
        private String sapName;//规格名称

        public String getSapName() {
            return sapName;
        }

        public void setSapName(String sapName) {
            this.sapName = sapName;
        }

        public int getActivity_spec_id() {
            return activity_spec_id;
        }

        public void setActivity_spec_id(int activity_spec_id) {
            this.activity_spec_id = activity_spec_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getSku_code() {
            return sku_code;
        }

        public void setSku_code(String sku_code) {
            this.sku_code = sku_code;
        }

        public String getSpec_vals() {
            return spec_vals;
        }

        public void setSpec_vals(String spec_vals) {
            this.spec_vals = spec_vals;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getSap_Ean11() {
            return sap_Ean11;
        }

        public void setSap_Ean11(String sap_Ean11) {
            this.sap_Ean11 = sap_Ean11;
        }

        public String getSap_Akpos() {
            return sap_Akpos;
        }

        public void setSap_Akpos(String sap_Akpos) {
            this.sap_Akpos = sap_Akpos;
        }

        public String getSap_Aktnr() {
            return sap_Aktnr;
        }

        public void setSap_Aktnr(String sap_Aktnr) {
            this.sap_Aktnr = sap_Aktnr;
        }

        public String getSap_Akart() {
            return sap_Akart;
        }

        public void setSap_Akart(String sap_Akart) {
            this.sap_Akart = sap_Akart;
        }

        public String getSap_Ztacname() {
            return sap_Ztacname;
        }

        public void setSap_Ztacname(String sap_Ztacname) {
            this.sap_Ztacname = sap_Ztacname;
        }

        public String getSap_Vkdab() {
            return sap_Vkdab;
        }

        public void setSap_Vkdab(String sap_Vkdab) {
            this.sap_Vkdab = sap_Vkdab;
        }

        public String getSap_Vktim() {
            return sap_Vktim;
        }

        public void setSap_Vktim(String sap_Vktim) {
            this.sap_Vktim = sap_Vktim;
        }

        public String getSap_Vedab() {
            return sap_Vedab;
        }

        public void setSap_Vedab(String sap_Vedab) {
            this.sap_Vedab = sap_Vedab;
        }

        public String getSap_Vetim() {
            return sap_Vetim;
        }

        public void setSap_Vetim(String sap_Vetim) {
            this.sap_Vetim = sap_Vetim;
        }

        public String getSap_Ztacid() {
            return sap_Ztacid;
        }

        public void setSap_Ztacid(String sap_Ztacid) {
            this.sap_Ztacid = sap_Ztacid;
        }

        public String getSap_ZpDiscount() {
            return sap_ZpDiscount;
        }

        public void setSap_ZpDiscount(String sap_ZpDiscount) {
            this.sap_ZpDiscount = sap_ZpDiscount;
        }

        public String getSap_ZcashRate() {
            return sap_ZcashRate;
        }

        public void setSap_ZcashRate(String sap_ZcashRate) {
            this.sap_ZcashRate = sap_ZcashRate;
        }

        public String getSap_BrandCname() {
            return sap_BrandCname;
        }

        public void setSap_BrandCname(String sap_BrandCname) {
            this.sap_BrandCname = sap_BrandCname;
        }

        public String getSap_BrandNo() {
            return sap_BrandNo;
        }

        public void setSap_BrandNo(String sap_BrandNo) {
            this.sap_BrandNo = sap_BrandNo;
        }

        public String getSap_NameL() {
            return sap_NameL;
        }

        public void setSap_NameL(String sap_NameL) {
            this.sap_NameL = sap_NameL;
        }

        public String getSap_Lifnr() {
            return sap_Lifnr;
        }

        public void setSap_Lifnr(String sap_Lifnr) {
            this.sap_Lifnr = sap_Lifnr;
        }

        public List<String> getSpec_val_list() {
            return spec_val_list;
        }

        public void setSpec_val_list(List<String> spec_val_list) {
            this.spec_val_list = spec_val_list;
        }
    }
}
