package com.auto.chishan.manager.adapter;

/**
 * author : lyzhang3
 * date   : 2019/4/2210:42 PM
 * desc   :
 *          "id": "b92fac137a374cb2bdd2fdd8898f2175",
 * 			"createDate": "2019-04-22 14:37:36",
 * 			"updateDate": "2019-04-22 14:37:39",
 * 			"remindType": "1",
 * 			"isAutoRemind": "2",
 * 			"projectId": "1c2b90d37b2946fbbdb183b2a42a1455",
 * 			"customerId": "68cdf2bf3c7d48baba3ac3dfd016e266",
 * 			"customerName": "张大钟",
 * 			"peoplePhone": "15556562323",
 * 			"remindWay": "1",
 * 			"tempId": "b23c83a279154b7282f5b01b9c6cfe32",
 * 			"content": "尊敬的张大钟客户，你好，你的房贷将于2019-04-12到期，到期本息合计金额5000元，请提前做好准备！"
 */
public class NoticeBean {
    private  String id;
    private  String createDate;
    private  String updateDate;
    private  String remindType;
    private  String isAutoRemind;
    private  String projectId;
    private  String customerId;
    private  String customerName;
    private  String peoplePhone;
    private  String remindWay;
    private  String tempId;
    private  String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getIsAutoRemind() {
        return isAutoRemind;
    }

    public void setIsAutoRemind(String isAutoRemind) {
        this.isAutoRemind = isAutoRemind;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPeoplePhone() {
        return peoplePhone;
    }

    public void setPeoplePhone(String peoplePhone) {
        this.peoplePhone = peoplePhone;
    }

    public String getRemindWay() {
        return remindWay;
    }

    public void setRemindWay(String remindWay) {
        this.remindWay = remindWay;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
