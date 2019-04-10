package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Date: 2019/3/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class ContractLookDetailBean implements Parcelable {

    /**
     * contractDetailList : [{"contractTxt":"合同正文","contractNo":"合同编号","contractName":"合同名称","contractNum":"合同数量"}]
     * contractNo : 20180900698
     * contractStartDate : 2017-06-30
     * contractSignAmount : null
     * contractEndDate : 2017-12-29
     * contractAmount : 1020000.0000
     * contractTempType : 草拟合同
     * flowType : 非印刷合同
     */

    private String contractNo;
    private String contractStartDate;
    private String contractSignAmount;
    private String contractEndDate;
    private String contractAmount;
    private String contractTempType;
    private String flowType;
    private List<ContractBean> contractDetailList;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractSignAmount() {
        return contractSignAmount;
    }

    public void setContractSignAmount(String contractSignAmount) {
        this.contractSignAmount = contractSignAmount;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractTempType() {
        return contractTempType;
    }

    public void setContractTempType(String contractTempType) {
        this.contractTempType = contractTempType;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public List<ContractBean> getContractDetailList() {
        return contractDetailList;
    }

    public void setContractDetailList(List<ContractBean> contractDetailList) {
        this.contractDetailList = contractDetailList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contractNo);
        dest.writeString(this.contractStartDate);
        dest.writeString(this.contractSignAmount);
        dest.writeString(this.contractEndDate);
        dest.writeString(this.contractAmount);
        dest.writeString(this.contractTempType);
        dest.writeString(this.flowType);
        dest.writeTypedList(this.contractDetailList);
    }

    public ContractLookDetailBean() {
    }

    protected ContractLookDetailBean(Parcel in) {
        this.contractNo = in.readString();
        this.contractStartDate = in.readString();
        this.contractSignAmount = in.readString();
        this.contractEndDate = in.readString();
        this.contractAmount = in.readString();
        this.contractTempType = in.readString();
        this.flowType = in.readString();
        this.contractDetailList = in.createTypedArrayList(ContractBean.CREATOR);
    }

    public static final Parcelable.Creator<ContractLookDetailBean> CREATOR = new Parcelable.Creator<ContractLookDetailBean>() {
        @Override
        public ContractLookDetailBean createFromParcel(Parcel source) {
            return new ContractLookDetailBean(source);
        }

        @Override
        public ContractLookDetailBean[] newArray(int size) {
            return new ContractLookDetailBean[size];
        }
    };
}
