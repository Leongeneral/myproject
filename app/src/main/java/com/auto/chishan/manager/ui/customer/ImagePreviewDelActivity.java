package com.auto.chishan.manager.ui.customer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewBaseActivity;


import java.util.ArrayList;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧），ikkong （ikkong@163.com）
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：预览已经选择的图片，并可以删除, 感谢 ikkong 的提交
 * ================================================
 */
public class ImagePreviewDelActivity extends ImagePreviewBaseActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private ArrayList<ImageItem> uploadedImageList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(ImagePreviewDelActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        ImageView mBtnDel = (ImageView) findViewById(com.lzy.imagepicker.R.id.btn_del);
        mBtnDel.setOnClickListener(this);
        mBtnDel.setVisibility(View.VISIBLE);
        if(!getIntent().getBooleanExtra("showDel",true)){
            mBtnDel.setVisibility(View.GONE);
        }
        topBar.findViewById(com.lzy.imagepicker.R.id.btn_back).setOnClickListener(this);

        mTitleCount.setText(getString(com.lzy.imagepicker.R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
        //滑动ViewPager的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mTitleCount.setText(getString(com.lzy.imagepicker.R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
            }
        });

        if(getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_UPLOADED) != null){
            uploadedImageList = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_UPLOADED);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.lzy.imagepicker.R.id.btn_del) {
            showDeleteDialog();
        } else if (id == com.lzy.imagepicker.R.id.btn_back) {
            onBackPressed();
        }
    }

    /** 是否删除此张图片 */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("要删除这张照片吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //移除当前图片刷新界面
                if(TextUtils.isEmpty(mImageItems.get(mCurrentPosition).id)){
                    mImageItems.remove(mCurrentPosition);
                    if (mImageItems.size() > 0) {
                        mAdapter.setData(mImageItems);
                        mAdapter.notifyDataSetChanged();
                        mTitleCount.setText(getString(com.lzy.imagepicker.R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
                    } else {
                        onBackPressed();
                    }
                }else{
//                    deletePic(mImageItems.get(mCurrentPosition).id);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //带回最新数据
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, mImageItems);
        intent.putExtra(ImagePicker.EXTRA_IMAGE_UPLOADED, uploadedImageList);
        setResult(ImagePicker.RESULT_CODE_BACK, intent);
        finish();
        super.onBackPressed();
    }

  /*  *//**
     * 删除影像资料
     * @param id
     *//*
    private void deletePic(final String id) {
        RequestParams params = new RequestParams();
        params.put("fileId", id);
        HttpHelper.post(ImagePreviewDelActivity.this, Urls.delHouseUpload, params, new MyTextHttpResponseHandler() {
            @Override
            public void onStart() {
                progressDialog.setMessage(getResources().getString(com.app.jdxmanager.R.string.progress_loading_text));
                progressDialog.show();
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ToastUtils.ToastMessageListen(ImagePreviewDelActivity.this, getResources().getString(com.app.jdxmanager.R.string.loading_error), null);
            }

            @SuppressLint("StringFormatMatches")
            @Override
            public void onSuccess(int arg0, org.apache.http.Header[] arg1, String data) {
                super.onSuccess(arg0,arg1,data);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                HttpResultBean resultBean = JSON.parseObject(data, HttpResultBean.class);
                if ("1".equals(resultBean.getCode())) {
                    for (int i = 0; i <uploadedImageList.size() ; i++) {
                        if(uploadedImageList.get(i).id.equals(id)){
                            uploadedImageList.remove(i);
                            break;
                        }
                    }

                    mImageItems.remove(mCurrentPosition);
                    if (mImageItems.size() > 0) {
                        mAdapter.setData(mImageItems);
                        mAdapter.notifyDataSetChanged();
                        mTitleCount.setText(getString(com.lzy.imagepicker.R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
                    } else {
                        onBackPressed();
                    }
                } else {
                    ToastUtils.ToastMessageListen(ImagePreviewDelActivity.this, resultBean.getMessage(), null);
                }
            }
        });
    }*/

    /** 单击时，隐藏头和尾 */
    @Override
    public void onImageSingleTap() {
        if (topBar.getVisibility() == View.VISIBLE) {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_out));
            topBar.setVisibility(View.GONE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.transparent);//通知栏所需颜色
            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_in));
            topBar.setVisibility(View.VISIBLE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.status_bar);//通知栏所需颜色
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}