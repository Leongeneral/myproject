package com.auto.chishan.manager.bean;

/**
 * author : lyzhang3
 * date   : 2019/3/232:51 PM
 * desc   :
 */
public class ReviewDetailBean {
    private String mgrId;//回访记录ID
    String hideId;//回访影像资料保存Id
    String returnTime;//本次回访时间
    String reviewContent;//
    String windControlAuditor;//初审人员ID
    String windControlAuditorName;//初审人员名称
    String curAddress;//所在位置

    public String getHideId() {
        return hideId;
    }

    public void setHideId(String hideId) {
        this.hideId = hideId;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getWindControlAuditor() {
        return windControlAuditor;
    }

    public void setWindControlAuditor(String windControlAuditor) {
        this.windControlAuditor = windControlAuditor;
    }

    public String getWindControlAuditorName() {
        return windControlAuditorName;
    }

    public void setWindControlAuditorName(String windControlAuditorName) {
        this.windControlAuditorName = windControlAuditorName;
    }

    public String getCurAddress() {
        return curAddress;
    }

    public void setCurAddress(String curAddress) {
        this.curAddress = curAddress;
    }

    public String getMgrId() {
        return mgrId;

    }

    public void setMgrId(String mgrId) {
        this.mgrId = mgrId;
    }
}
