package com.maoye.mlh_slotmachine.bean;

/**
 * Created by Rs on 2018/4/28.
 */

public class AdvertBean {
    private int id;
    private String image_url;
    private String link_url;
    private int product_id;
    private String local_img_url;//缓存再本地的图片

    public String getLocal_img_url() {
        return local_img_url;
    }

    public void setLocal_img_url(String local_img_url) {
        this.local_img_url = local_img_url;
    }

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
