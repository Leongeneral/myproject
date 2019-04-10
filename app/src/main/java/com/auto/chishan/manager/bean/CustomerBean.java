package com.auto.chishan.manager.bean;

/**
 * Date: 2019/3/4
 * desc:
 *
 * @author:DingZhixiang
 */
public class CustomerBean {

    /**
     * custType : 1
     * phoneNum : 13858119999
     * id : abc92e4c5be14a5db1507ea97949a009
     * certificateNum : 330103194503061016
     * customerName : 吴建中
     */

    //个人  1  企业  2
    private String custType;
    private String phoneNum;
    private String id;
    private String certificateNum;
    private String customerName;

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
