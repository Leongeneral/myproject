package com.my.commonlibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

/**
 * Date: 2018/8/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class SelectPicUtil {
    /**
     * 选择一张图片，不裁剪
     * @param context
     */
    public static void selectOnePic(Activity context){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = 140;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setMultiMode(false); //单选
        imagePicker.setSaveRectangle(false); //按矩形区域保存裁剪图片
        Intent intent = new Intent(context, ImageGridActivity.class);
        context.startActivityForResult(intent, 100);
    }

    /**
     * 多选
     * @param context
     */
    public static void initSelectMorePic(Activity context){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setMultiMode(true);
    }

    /**
     * 选择头像  只可以选一张，圆形裁剪
     * @param context
     */
    public static void changeHeadIcon(Activity context){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = 140;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setMultiMode(false); //单选
        imagePicker.setSaveRectangle(false); //按矩形区域保存裁剪图片
        Intent intent = new Intent(context, ImageGridActivity.class);
        context.startActivityForResult(intent, 100);
    }
}
