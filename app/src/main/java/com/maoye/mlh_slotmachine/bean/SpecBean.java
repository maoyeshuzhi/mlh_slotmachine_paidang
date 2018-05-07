package com.maoye.mlh_slotmachine.bean;

import java.util.List;

/**
 * Created by Rs on 2018/4/18.
 */

public class SpecBean {
   private String specName;
    private List<SpecItemListBean> pecItemList;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public List<SpecItemListBean> getPecItemList() {
        return pecItemList;
    }

    public void setPecItemList(List<SpecItemListBean> pecItemList) {
        this.pecItemList = pecItemList;
    }

    public static class SpecItemListBean{
        private boolean isSelect;
        private String itemName;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }


        @Override
        public String toString() {
            return "SpecItemListBean{" +
                    "itemName='" + itemName + '\'' +
                    '}';
        }
    }
}
