package com.auto.chishan.manager.bean;

import java.util.List;

/**
 * author : lyzhang3
 * date   : 2019/3/214:12 PM
 * desc   :
 */
public class ReviewMgrMaintainListBean {

    private String imageType;

    private List<ReviewMgrMaintainBean> mgrList;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        imageType = imageType;
    }

    public List<ReviewMgrMaintainBean> getMgrList() {
        return mgrList;
    }

    public void setMgrList(List<ReviewMgrMaintainBean> mgrList) {
        this.mgrList = mgrList;
    }





}
