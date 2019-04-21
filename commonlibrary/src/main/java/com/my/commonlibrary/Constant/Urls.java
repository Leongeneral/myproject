package com.my.commonlibrary.Constant;

/**
 * Date: 2018/12/17
 * desc:
 * @author:DingZhixiang
 */
public class Urls {
    public static String FirstHost = "http://36.33.24.109:8199";//演示

//    public static String FirstHost = "http://192.168.80.197:8080";//演示

//    http://36.33.24.109:8199/weijinrong-app/app/customer/myIndex?

    public static String SecondHost = "/weijinrong-web/app/check/customer/";
    public static String SecondHost1 = "/weijinrong-web/app/customer/";

    /** 登录 */
    public static String LOGIN = "login?";

    public static String CUSTOMER = "myIndex?";

    public static String MYINSTALLMENT = "myProjects?";

    public static String MYBANKCARD = "myBankCards?";

    public static String PROJECTPLAN="projectPlan";


    public static String PAY = "pay";

    public static String APPLY = "/weijinrong-web/app/quickpay/apply?";

    public static String SIGNCONFIRM = "/weijinrong-web/app/quickpay/signConfirm?";

    public static String UNBINDBANKCARD = "/weijinrong-web/app/quickpay/deleteCard?";

    /** 客户经理关联的客户列表 */
    public static String managerCustomerList = "managerCustomerList";
    /** 客户详情页面 */
    public static String editCustomerInfo = "editCustomerInfo";
    /** 保存客户信息 */
    public static String saveCustomerInfo  = "saveCustomerInfo";
    /** 客户经理关联的项目列表 */
    public static String managerProjectList  = "managerProjectList";
    /** 项目详情页面 */
    public static String editProjectInfo  = "editProjectInfo";
    /** 保存项目信息 */
    public static String saveProjectInfo  = "saveProjectInfo";
    /** 获取当前客户经理可操作业务类型*/
    public static String getCurUserBusi  = "getCurUserBusi";
    /** 获取影像资料类型列表*/
    public static String getImageTypeList  = "getImageTypeList";
    /** 获取影像资料文件列表*/
    public static String getMediaInfos  = "getMediaInfos";
    /** 上传图片（多张）*/
    public static String imageUpdateMulti  = "/weijinrong/app/upload/imageUpdateMulti";
    /** 删除影响资料*/
    public static String delImgDataById  = "/weijinrong/app/upload/delImgDataById";
    /** 获取产品列表*/
    public static String getProductTypeList  = "getProductTypeList";
    /** 获取实体公司列表*/
    public static String getEntityCompanyList  = "getEntityCompanyList";
    /** 合同项目列表*/
    public static String managerContractList  = "managerContractList";
    /** 合同维护*/
    public static String managerContractMaintainList  = "managerContractMaintainList";
    /** 合同详情页面*/
    public static String showContractInfo  = "showContractInfo";
    /** 添加合同*/
    public static String saveContractInfo  = "saveContractInfo";
    /**回访项目列表*/
    public static String managerReviewMgrList = "managerReviewMgrList";
    /**回访记录列表**/
    public static String reviewMgrMaintainList = "reviewMgrMaintainList";

    /** 待办-人员分配审核 */
    public static String findUser = "findUser";

    public static String getHideId = "getHideId";

    /***回访保存**/
    public static final String saveAppReviewMgrInfo = "saveAppReviewMgrInfo";

    /***回访详情**/
    public static final String showReviewMgrInfo = "showReviewMgrInfo";

    public static final String checkReviewSendFlag = "checkReviewSendFlag";


}
