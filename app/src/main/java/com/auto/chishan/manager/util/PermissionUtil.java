package com.auto.chishan.manager.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;


import java.util.Arrays;

/**
 * 作者：jxxu2 2018年7月17日
 * 权限申请的操作类，实现权限申请的相关方法
 */
public class PermissionUtil {
    private final static String TAG = "PermissionUtil";
    public final static String KEY_REQUESTER_ID = "requester_id";

    public final static String[] PERMISSION_LIST_FULL = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.USE_FINGERPRINT,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_CONTACTS};
    /**
     * 启动app所需的基本权限
     */
    public final static String[] PERMISSION_LIST_APP = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public static class Requester {
        static int mRequestId = 0;
        Context mContext;
        Activity mActivity;
        String[] mPermissionList;

        /**
         * 某些基本的权限必须授权的情况下才能进行后续操作，如果基本权限为null则默认认为所有权限都必须授权
         */
        String[] mBasicList;
        /**
         * 回调不为null，需要利用广播监听回调
         */
        GrantCallback mCallback;
        /**
         * 是否需要发起权限申请，默认为true
         * 非主进程不需要重复申请权限,需要手动设置为false
         */
        boolean mNeedRequest = true;
        boolean mImmeUnRegister = true;
        PermissionReceiver mReceiver;

        public Requester(Context context) {
            mContext = context;
        }

        public Requester(Activity activity) {
            mContext = activity;
            mActivity = activity;
        }

        public Requester permissions(String... list) {
            this.mPermissionList = list;
            return this;
        }


        public Requester basicPermission(String... list) {
            mBasicList = list;
            return this;
        }

        public Requester immeUnRegister(boolean autoUnRegister) {

            mImmeUnRegister = autoUnRegister;
            return this;
        }

        public Requester callback(GrantCallback callback) {
            mCallback = callback;
            return this;
        }

        public Requester needRequest(boolean need) {
            mNeedRequest = need;
            return this;
        }

        /**
         * 只添加监听，不拉起申请页面
         * @return
         */
        public boolean preCheck() {
            if (checkPermissions(mContext, mPermissionList)) {
                if (mCallback != null) {
                    mCallback.onGranted(true, true);
                }
                return false;
            } else {
                if (mCallback != null) {
                    if (mReceiver != null) {
                        mReceiver.unRegister(mContext);
                    }
                    mReceiver = PermissionReceiver.register(mContext, mCallback,
                            mBasicList != null ? mBasicList : mPermissionList, mImmeUnRegister);
                }

                return mNeedRequest;
            }

        }

        /**
         * 检查当前权限
         * @return 返回是否需要申请权限
         */
        public boolean check() {

            boolean notGranted = preCheck();
            if (notGranted && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                Intent intent = PermissionActivity.equipIntent(mContext, mPermissionList)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
                int requesterId= mRequestId++;
//                int  ret = PermissionActivity.startActivity(mContext,requesterId, mPermissionList);
//                if(mReceiver != null && ret == 0 && mImmeUnRegister){
//                    //只有在id相同的情况下才在授权失败后不再监听
//                    mReceiver.setReceiverId(requesterId);
//                }

            }
            return notGranted;
        }
        /**
         * 检查当前权限,支持onActivityResult
         * @param requestCode
         * @return 返回是否需要申请权限
         */
        public boolean checkForResult(int requestCode) {
            boolean notGranted = preCheck();
            if (notGranted && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                mActivity.startActivityForResult(PermissionActivity.equipIntent(mActivity, mPermissionList), requestCode);
//                PermissionActivity.startActivityForResult(mActivity, requestCode, mPermissionList);

            }
            return notGranted;
        }
    }

    public static boolean checkPermissions(Context context, String... list) {
        boolean ret = true;
        for (String p : list) {
            if (ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_DENIED) {
                ret = false;
                break;
            }
        }
        return ret;
    }
    public static String[] getRequestList(Context context, String[] list){
        String[] tmp =  new String[list.length];
        int i = 0;
        for (String p : list) {
            if (ContextCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_DENIED) {
                tmp[i++] = p;
            }
        }
        if(i == 0){
            return null;
        }else{
           return Arrays.copyOf(tmp, i);
        }
    }

    public static String[] contactPermissions(String[] srcList, String... addLList) {
        int lenA = srcList.length, lenB = addLList.length;
        String[] list = new String[lenA + lenB];
        System.arraycopy(srcList, 0, list, 0, lenA);
        System.arraycopy(srcList, 0, list, lenA, lenB);
        return list;

    }
    public static boolean hasCommonPermission(String[] pListA, String[] pListB){
        for(String a:pListA){
            for(String b:pListB){
                if(a.equals(b)){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 申请权限包含回调
     *
     * @param context
     * @param isMainProcess  是否为主进程，子进程不做权限的重复申请
     * @param callback
     * @param permissionList
     */
    public static void requestPermissions(Context context, boolean isMainProcess, GrantCallback callback, String... permissionList) {
        new Requester(context).needRequest(isMainProcess).callback(callback).permissions(permissionList).check();
    }

    public static void requestPermissions(Context context, GrantCallback callback, String... permissionList) {
        requestPermissions(context, true, callback, permissionList);
    }


    public static void prePermissionRecord(final Context context, GrantCallback callback) {
        new Requester(context).permissions(Manifest.permission.RECORD_AUDIO)
                .callback(callback)
                .check();
    }

    public static void prePermissionCamera(final Context context, GrantCallback callback) {
        new Requester(context).permissions(Manifest.permission.CAMERA)
                .callback(callback)
                .check();
    }

    public static Requester createAppRequester(Context context){
       return new Requester(context).permissions(PermissionUtil.PERMISSION_LIST_APP);
    }
    public static boolean checkAppStartPermissions(Context context){
        return checkPermissions(context,PERMISSION_LIST_APP);
    }

}
