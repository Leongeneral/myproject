package com.my.commonlibrary.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Date: 2018/12/18
 * desc:
 * @author:DingZhixiang
 */
public class DialogUtils2 {
    public static void showAlertDialog(String msg,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("提醒").setMessage(msg).setNegativeButton("知道了",null).show();
    }
    public static void showAlertDialog(String title,String msg,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title).setMessage(msg).setNegativeButton("知道了",null).show();
    }

    public static void showAlertDialog(String title, String msg, Context context, DialogInterface.OnClickListener clickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title).setMessage(msg).setNegativeButton("知道了", clickListener).show();
    }

    public static void showConfirmDialog(String title, String msg, Context context, DialogInterface.OnClickListener clickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setPositiveButton("取消",null).setNegativeButton("确定", clickListener).show();
    }

    public static void showConfirmDialog(String title, String msg, Context context,String negativeButtonStr,  DialogInterface.OnClickListener clickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setPositiveButton("取消",null).setNegativeButton(negativeButtonStr, clickListener).show();
    }
}
