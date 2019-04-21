package com.auto.chishan.manager.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;

//import com.afollestad.materialdialogs.MaterialDialog;
import com.auto.chishan.manager.R;

/**
 * author : lyzhang3
 * date   : 2019/4/2011:51 AM
 * desc   :
 */
public class MaterialUtil {

//    public static MaterialDialog.Builder createMaterialDialogBuilder(Context context) {
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
//
//
//            builder.positiveColorRes(R.color.font_semi)
//                    .negativeColorRes(R.color.font_semi)
//                    .titleColorRes(R.color.font_semi)
//                    .contentColorRes(R.color.font_semi)
//                    .backgroundColorRes(R.color.color_primary_white)
//                    .dividerColorRes(R.color.bg_norm_divider)
////					.btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE)
//                    .positiveColor(ContextCompat.getColor(context, R.color.font_blue))
//                    .negativeColor(ContextCompat.getColor(context, R.color.font_blue));
//
//            builder.choiceWidgetColor(createColorStateList(ContextCompat.getColor(context, R.color.font_semi), ContextCompat.getColor(context, R.color.font_blue)));
//
//
//        return builder;
//    }
//
//    public static MaterialDialog createWaitingDialog(Context context, String content){
//        return createMaterialDialogBuilder(context)
//                .progress(true,-1)
//                .cancelable(false)
//                .content(content)
//                .build();
//    }
//
//    private  static MaterialDialog mWaitingDialog ;
//    public static void showWaiting(Context context, int contentId){
//        showWaiting(context, context.getString(contentId));
//    }
//    public static void showWaiting(Context context, String title){
//        mWaitingDialog = MaterialUtil.createMaterialDialogBuilder(context)
//                .progress(true,0).cancelable(false).canceledOnTouchOutside(false)
//                .content(title).show();
//
//    }
//    public static void closeWaiting(){
//        if(mWaitingDialog != null && mWaitingDialog.isShowing()){
//            mWaitingDialog.dismiss();
//        }
//        mWaitingDialog = null;
//    }
//
//    /** 对RadioButton设置不同状态时的颜色。 */
//    private static ColorStateList createColorStateList(int normal, int checked) {
//        int[] colors = new int[] { checked, checked, normal };
//        int[][] states = new int[3][];
//        states[0] = new int[] { android.R.attr.state_pressed};
//        states[1] = new int[] { android.R.attr.state_checked};
//        states[2] = new int[] {};
//
//        ColorStateList colorList = new ColorStateList(states, colors);
//        return colorList;
//    }
}
