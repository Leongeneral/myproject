package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : lyzhang3
 * date   : 2019/3/218:19 PM
 * desc   :
 "id": "2e43673b7d584252bfb0aa2b11a1d7cb",
 "remarks": "",
 "createDate": "2019-04-20 23:11:28",
 "updateDate": "2019-04-20 23:11:28",
 "customerId": "bff50e83125f4ca399e0c8675d007669",
 "bankCode": "03010000",
 "type": "00",
 "bankNumber": "6222620250009986154",
 "prePhone": "15655578681",
 "customerName": "张礼耀",
 "idCard": "342422199103095294",
 "agreeId": "201904202311281823",
 "defaultFlag": "0"
 *
 *
 */
public class BankCardBean implements Parcelable {


    private String id;
    private String remarks;
    private String createDate;
    private String updateDate;
    private String customerId;
    private String bankCode;
    private String prePhone;
    private String customerName;
    private String idCard;
    private String agreeId;
    private String defaultFlag;

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String bankNumber;
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPrePhone() {
        return prePhone;
    }

    public void setPrePhone(String prePhone) {
        this.prePhone = prePhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAgreeId() {
        return agreeId;
    }

    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }
    public BankCardBean(){}

    public BankCardBean(Parcel in) {
        id = in.readString();
        remarks = in.readString();
        createDate=in.readString();
        updateDate=in.readString();
        customerId=in.readString();
        bankCode=in.readString();
        prePhone=in.readString();
        customerName=in.readString();
        idCard=in.readString();
        agreeId=in.readString();
        defaultFlag=in.readString();
        bankNumber=in.readString();
        type=in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(remarks);
        dest.writeString(createDate);

        dest.writeString(updateDate);
        dest.writeString(customerId);
        dest.writeString(bankCode);
        dest.writeString(prePhone);
        dest.writeString(customerName);
        dest.writeString(idCard);
        dest.writeString(agreeId);
        dest.writeString(defaultFlag);
        dest.writeString(bankNumber);
        dest.writeString(type);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BankCardBean> CREATOR = new Creator<BankCardBean>() {
        @Override
        public BankCardBean createFromParcel(Parcel in) {
            return new BankCardBean(in);
        }

        @Override
        public BankCardBean[] newArray(int size) {
            return new BankCardBean[size];
        }
    };


}
