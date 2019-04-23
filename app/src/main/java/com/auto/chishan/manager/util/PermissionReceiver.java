package com.auto.chishan.manager.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import java.util.ArrayList;

/**
 * 作者：jxxu2 2018年7月17日
 * 利用广播实现授权结果的回调
 */
public class PermissionReceiver extends BroadcastReceiver {
    private final static String TAG = PermissionReceiver.class.getSimpleName();
    public static final String ACTION_PERMISSION_CHANGE = "permission_change";
    public static final String KEY_GRANT_PERMISSIONS = "grant_permissions";
    private String[] mPermissionList;
    /**
     * 权限改变后自动解注册
     */
    private boolean mImmeUnregister ;
    /**
     * 标记是否已经解注册
     */
    private boolean isUnRegistered = false;
    /**
     * 授权结果的回调对象
     */
    private GrantCallback mGrantCallback;

    private int mReceiverId = -1;

    public PermissionReceiver(String[] permissions, GrantCallback callback, boolean autoUnregister) {
        mPermissionList = permissions;
        mGrantCallback = callback;
        mImmeUnregister = autoUnregister;
    }

    /**
     * 定义接收者id，避免重复接收处理
     * @return
     */
    public PermissionReceiver setReceiverId(int receiverId){
        this.mReceiverId = receiverId;
        return this;
    }

    public void unRegister(Context context) {
        if (!isUnRegistered) {
            context.unregisterReceiver(this);
            isUnRegistered = true;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_PERMISSION_CHANGE.equals(intent.getAction())) {
            ArrayList<String> list = intent.getStringArrayListExtra(KEY_GRANT_PERMISSIONS);
            boolean granted = false;
            if (list != null && !list.isEmpty()) {
                granted = true;
                for (String p : mPermissionList) {
                    if (!list.contains(p)) {
                        granted = false;
                        break;
                    }
                }
            } else {
                if (PermissionUtil.checkPermissions(context, mPermissionList)) {
                    granted = true;
                }
            }
            int requestId = intent.getIntExtra(PermissionUtil.KEY_REQUESTER_ID, -1);
            //授权通过
//            Logging.i(TAG, "permission granted:" + granted);
            if ((mImmeUnregister && requestId >=0 && mReceiverId==requestId) || granted) {
//                Logging.i(TAG, "permission unregister:" + mReceiverId);
                unRegister(context);
            }
            if (mGrantCallback != null) {
                mGrantCallback.onGranted(granted, false);
            }
        }
    }

    /**
     * 注册权限申请的广播
     *
     * @param context
     * @param permissionList
     * @param callback
     */
    public static PermissionReceiver register(Context context, GrantCallback callback, String[] permissionList, boolean autoUnregister) {
//        Logging.i(TAG,"register:" + context.getClass().getSimpleName());
        PermissionReceiver receiver = new PermissionReceiver(permissionList, callback, autoUnregister);
        context.registerReceiver(receiver, new IntentFilter(ACTION_PERMISSION_CHANGE));
        return receiver;
    }

    /**
     * 发送权限授权的回调广播
     *
     * @param context
     */
    public static void send(Context context) {
        context.sendBroadcast(new Intent(ACTION_PERMISSION_CHANGE));
    }
    public static void send(Context context, int requesterId) {
        Intent intent = new Intent(ACTION_PERMISSION_CHANGE);
        intent.putExtra(PermissionUtil.KEY_REQUESTER_ID, requesterId);
        context.sendBroadcast(intent);
    }

    /**
     * 发送授权改变的广播
     *
     * @param context
     * @param permissionList 已经申请的权限列表
     */
    public static void send(Context context, ArrayList<String> permissionList) {
        Intent intent = new Intent(ACTION_PERMISSION_CHANGE);
        intent.putExtra(KEY_GRANT_PERMISSIONS, permissionList);
        context.sendBroadcast(intent);
    }
}
