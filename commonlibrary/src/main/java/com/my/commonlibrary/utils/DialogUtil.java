package com.my.commonlibrary.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.my.commonlibrary.R;


public class DialogUtil {

    /**
     * 加载中对话框
     * @param context
     * @param message
     * @param cancelable
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog	dialogLoading = new ProgressDialog(context);
        dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogLoading.setMessage(message);
        dialogLoading.setIndeterminate(false);
        dialogLoading.setCancelable(cancelable);
        return dialogLoading;
    }

    public static Dialog getCustomAlertDialog(Context context,String msg) {
        Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog);
        Window window = dialog.getWindow();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView msgTv= (TextView) dialogView.findViewById(R.id.message);
        msgTv.setText(msg);
        window.setContentView(dialogView);
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 确认对话框
     * @param context
     * @param listener
     * @param left
     * @param right
     * @param content
     * @param cancelable
     * @return
     */
    public static Dialog getConfirmDialog(Context context, View.OnClickListener listener, String left, String right, String content,boolean cancelable) {
        Dialog dialog = new Dialog(context, R.style.Theme_CustomDialog);
        Window window = dialog.getWindow();
        View dialogView = LayoutInflater.from(context).inflate(R.layout.confirmdialog, null);
        Button dialogCalcel = (Button) dialogView.findViewById(R.id.dialog_left);
        Button dialogOk = (Button) dialogView.findViewById(R.id.diaolog_right);
        TextView dialogContent = (TextView) dialogView.findViewById(R.id.dialog_content);
        dialogCalcel.setText(left);
        dialogOk.setText(right);
        dialogContent.setText(content);
        dialogCalcel.setOnClickListener(listener);
        dialogOk.setOnClickListener(listener);
        window.setContentView(dialogView);
        dialog.setCancelable(cancelable);
        return dialog;
    }


    public static Window initWindowParams(Dialog dialog,Context mContext) {
        Window dialogWindow = dialog.getWindow();
        // 获取屏幕宽、高用
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (display.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        return dialogWindow;
    }


}
