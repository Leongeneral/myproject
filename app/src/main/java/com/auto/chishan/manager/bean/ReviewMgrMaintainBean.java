package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : lyzhang3
 * date   : 2019/3/218:19 PM
 * desc   :
 */
public class ReviewMgrMaintainBean  implements Parcelable {
//    /* "mgrId": "5d895dab89154e7bb64b2f10acd6d382",
//             "busiCode": "fcfbdd20180830000037",
//             "busiType": "pawn,guarantee,",
//             "projectName": "顾建平80万房产抵押业务",
//             "reviewState": "审核通过",
//             "projectId": "01e51140042a456f8430fd115a7ddf9d",
//             "customerName": "顾建平",
//             "returnTime": "2018-12-14 00:00:00",
//             "productName": "房产非标典当",
//             "projectState": "正常"*/


    private String mgrId;//回访记录ID
    private String busiType;//项目业务类型
    private String projectName;//项目名称
    private String busiCode;//项目编号
    private String customerName;//客户名称
    private String returnTime;//回访时间

    private String reviewState;//审核状态
    private String projectState;//项目状态
    private String productName;//产品名称
    private String imageType;//回访资料类型

    protected ReviewMgrMaintainBean(Parcel in) {
        mgrId = in.readString();
        busiType = in.readString();
        projectName = in.readString();
        busiCode = in.readString();
        customerName = in.readString();
        returnTime = in.readString();
        reviewState = in.readString();
        projectState = in.readString();
        productName = in.readString();
        imageType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mgrId);
        dest.writeString(busiType);
        dest.writeString(projectName);
        dest.writeString(busiCode);
        dest.writeString(customerName);
        dest.writeString(returnTime);
        dest.writeString(reviewState);
        dest.writeString(projectState);
        dest.writeString(productName);
        dest.writeString(imageType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewMgrMaintainBean> CREATOR = new Creator<ReviewMgrMaintainBean>() {
        @Override
        public ReviewMgrMaintainBean createFromParcel(Parcel in) {
            return new ReviewMgrMaintainBean(in);
        }

        @Override
        public ReviewMgrMaintainBean[] newArray(int size) {
            return new ReviewMgrMaintainBean[size];
        }
    };

    public String getMgrId() {
        return mgrId;
    }

    public void setMgrId(String mgrId) {
        this.mgrId = mgrId;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

}
