package com.auto.chishan.manager.util;

public interface GrantCallback {
    /**
     * 授权结果回调
     *
     * @param isBasicGranted 基本权限是否已经授权
     * @param hasGranted     权限之前已经申请，不需要再次请求
     */
     void onGranted(boolean isBasicGranted, boolean hasGranted);
}
