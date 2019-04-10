package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Date: 2019/3/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class ContractBean implements Parcelable {


    /**
     * contractTxt : 合同正文
     * contractNo : 合同编号
     * contractName : 合同名称
     * contractNum : 合同数量
     */

    private String contractTxt;
    private String contractNo;
    private String contractName;
    private String contractNum;

    public ContractBean() {
    }

    public String getContractTxt() {
        return contractTxt;
    }

    public void setContractTxt(String contractTxt) {
        this.contractTxt = contractTxt;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contractTxt);
        dest.writeString(this.contractNo);
        dest.writeString(this.contractName);
        dest.writeString(this.contractNum);
    }

    protected ContractBean(Parcel in) {
        this.contractTxt = in.readString();
        this.contractNo = in.readString();
        this.contractName = in.readString();
        this.contractNum = in.readString();
    }

    public static final Creator<ContractBean> CREATOR = new Creator<ContractBean>() {
        @Override
        public ContractBean createFromParcel(Parcel source) {
            return new ContractBean(source);
        }

        @Override
        public ContractBean[] newArray(int size) {
            return new ContractBean[size];
        }
    };
}
