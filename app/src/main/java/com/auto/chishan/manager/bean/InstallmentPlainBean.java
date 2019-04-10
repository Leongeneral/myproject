package com.auto.chishan.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *      "id": "738c7745bc574b7ea4c1354d705eeec0",
 * 		"createDate": "2019-04-10 22:08:22",
 * 		"updateDate": "2019-04-10 22:08:22",
 * 		"projectId": "fab62045d76a4fc5973cf0cd74acdf1f",
 * 		"batchPaymentPeriods": "1",
 * 		"payDate": "2019-04-30",
 * 		"payPrincipal": 14031.56,
 * 		"payInterest": 3966.67,
 * 		"penaltyDue": 0.00,
 * 		"principalPaid": 0.00,
 * 		"interestPaid": 0.00,
 * 		"penaltyPaid": 0.00,
 * 		"penaltyReduce": 0.00,
 * 		"lastPenalty": 0.00,
 * 		"repaymentState": "0"
 *
 */
public class InstallmentPlainBean  {

    private String id;
    private String createDate;

    private String updateDate;

    private String projectId;

    private String batchPaymentPeriods;

    private String payDate;

    private String payPrincipal;

    private String payInterest;

    private String penaltyDue;

    private String principalPaid;

    private String interestPaid;

    private String penaltyPaid;

    private String penaltyReduce;

    private String lastPenalty;

    private String repaymentState;


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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBatchPaymentPeriods() {
        return batchPaymentPeriods;
    }

    public void setBatchPaymentPeriods(String batchPaymentPeriods) {
        this.batchPaymentPeriods = batchPaymentPeriods;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayPrincipal() {
        return payPrincipal;
    }

    public void setPayPrincipal(String payPrincipal) {
        this.payPrincipal = payPrincipal;
    }

    public String getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(String payInterest) {
        this.payInterest = payInterest;
    }

    public String getPenaltyDue() {
        return penaltyDue;
    }

    public void setPenaltyDue(String penaltyDue) {
        this.penaltyDue = penaltyDue;
    }

    public String getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(String principalPaid) {
        this.principalPaid = principalPaid;
    }

    public String getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(String penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    public String getPenaltyReduce() {
        return penaltyReduce;
    }

    public void setPenaltyReduce(String penaltyReduce) {
        this.penaltyReduce = penaltyReduce;
    }

    public String getLastPenalty() {
        return lastPenalty;
    }

    public void setLastPenalty(String lastPenalty) {
        this.lastPenalty = lastPenalty;
    }

    public String getRepaymentState() {
        return repaymentState;
    }

    public void setRepaymentState(String repaymentState) {
        this.repaymentState = repaymentState;
    }
}
