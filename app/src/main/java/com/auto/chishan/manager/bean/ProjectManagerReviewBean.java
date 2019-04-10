package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : lyzhang3
 * date   : 2019/3/214:01 PM
 * desc   :
 */
public class ProjectManagerReviewBean implements Parcelable {

    private String projectId;//项目ID
    private String busiType;//项目业务类型
    private String projectName;//项目名称
    private String busiCode;//项目编号
    private String customerName;//客户名称
    private String financingAmount;//项目金额
    private String timeLimit;//期限
    private String productName;//产品名称
    private String projectState;//项目状态

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(String financingAmount) {
        this.financingAmount = financingAmount;
    }



    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
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



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.projectId);
        dest.writeString(this.busiType);
        dest.writeString(this.projectName);
        dest.writeString(this.busiCode);

        dest.writeString(this.customerName);
        dest.writeString(this.financingAmount);
        dest.writeString(this.timeLimit);
        dest.writeString(this.productName);

        dest.writeString(this.projectState);

    }

    protected ProjectManagerReviewBean(Parcel in) {
        this.projectId = in.readString();
        this.busiType = in.readString();
        this.projectName = in.readString();
        this.busiCode = in.readString();

        this.customerName = in.readString();
        this.financingAmount = in.readString();
        this.timeLimit = in.readString();
        this.productName = in.readString();
        this.projectState = in.readString();

    }

    public static final Creator<ProjectManagerReviewBean> CREATOR = new Creator<ProjectManagerReviewBean>() {
        @Override
        public ProjectManagerReviewBean createFromParcel(Parcel source) {
            return new ProjectManagerReviewBean(source);
        }

        @Override
        public ProjectManagerReviewBean[] newArray(int size) {
            return new ProjectManagerReviewBean[size];
        }
    };
}
