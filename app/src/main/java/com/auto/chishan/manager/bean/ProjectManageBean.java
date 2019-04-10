package com.auto.chishan.manager.bean;

/**
 * Date: 2019/3/8
 * desc:
 *
 * @author:DingZhixiang
 */
public class ProjectManageBean {

    /**
     * financingAmount : 35000000.0000
     * interestRate : null（年利率）
     * timeLimit : 6月
     * busiCode : gqzydd20190307000001
     * busiType : pawn
     * id : a5ced698e0b944389ea6939e8f093081
     * projectName : 贤丰控股集团有限公司3500万元股权质押业务
     * applyTime : 2018-09-10
     * customerName : 贤丰控股集团有限公司
     * productName : 股权质押典当
     * auditState : 0
     */

    private String financingAmount;
    private String interestRate;
    private String timeLimit;
    private String busiCode;
    private String busiType;
    private String id;
    private String projectId;
    private String projectName;
    private String applyTime;
    private String customerName;
    private String productName;
    private String auditState;
    private String contractCount;
    private String contractNo;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractCount() {
        return contractCount;
    }

    public void setContractCount(String contractCount) {
        this.contractCount = contractCount;
    }

    public String getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(String financingAmount) {
        this.financingAmount = financingAmount;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
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

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }
}
