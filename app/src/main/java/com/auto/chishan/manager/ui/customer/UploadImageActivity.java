package com.auto.chishan.manager.ui.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.auto.chishan.manager.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.adapter.UploadImgAdapter;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.DialogUtils2;
import com.my.commonlibrary.utils.ImageUtils;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.utils.SelectPicUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Date: 2019/3/5
 * desc:
 *
 * @author:DingZhixiang
 */
public class UploadImageActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private UploadImgAdapter adapter;
    private ArrayList<ImageItem> allImageList = new ArrayList<>(); //所有图片（服务器返回的之前上传的+当前选择的）
    private ArrayList<ImageItem> uploadedImageList = new ArrayList<>(); //服务器返回的之前上传的
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private List<File> files;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String d = df.format(new Date());
    @Override
    protected int setLayout() {
        return R.layout.activity_imagedata;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titlerightTv.setVisibility(View.VISIBLE);
        titlerightTv.setText("上传");
        titleContentTv.setText(getIntent().getStringExtra("name"));

        titlerightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selImageList.clear();
                for (ImageItem item:allImageList) {
                    //id 未空表示是新选择的,需要上传
                    if(TextUtils.isEmpty(item.id)){
                        selImageList.add(item);
                    }
                }
                if(selImageList == null || selImageList.size() == 0){
                    showToast( "请选择图片");
                    return;
                }
                mLoadingDialog.setMessage("请稍后...");
                mLoadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        files = new ArrayList<>();
                        //压缩图片
                        for (int i = 0; i < selImageList.size(); i++) {
                            files.add(ImageUtils.compressByQuality(selImageList.get(i).path, 30, true));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initHttpUploadImg();
                            }
                        });
                    }
                }).start();
            }
        });

        initHttpData();
    }

    @Override
    protected void initData() {
        adapter = new UploadImgAdapter(this, allImageList, maxImgCount,true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        adapter.setDeleteListener(new UploadImgAdapter.DeleteListener() {
            @Override
            public void delete(int layoutPosition) {
                deletePic(layoutPosition);
            }
        });
        SelectPicUtil.initSelectMorePic(UploadImageActivity.this);
        adapter.setOnItemClickListener(new UploadImgAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case -1:
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent = new Intent(mActivity, ImageGridActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        break;
                    default:
                        //打开预览
                        Intent intentPreview = new Intent(mActivity, ImagePreviewDelActivity.class);
                        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        intentPreview.putExtra("showDel", false);
                        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                        break;
                }
            }
        });
    }

    /**
     * 上传图片
     */
    private void initHttpUploadImg() {
        mLoadingDialog.dismiss();
        OkGo.<LzyResponse<List<ImageItem>>>post(Urls.FirstHost + Urls.imageUpdateMulti).tag(this)
                .addFileParams("imageFile", files)
                .params("imageType", getIntent().getStringExtra("imageType"))
                .params("recordId", getIntent().getStringExtra("id"))
                .params("imageCode", getIntent().getStringExtra("code"))
                .execute(new DialogCallback<LzyResponse<List<ImageItem>>>(this, true) {
            @Override
            public void onSuccess(Response<LzyResponse<List<ImageItem>>> response) {
                DialogUtils2.showAlertDialog("提示", "上传成功", mActivity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                notifyPic();
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                //删除已上传的照片操作后的剩余照片（删除了上传过的照片实时刷新uploadedImageList）
                ArrayList<ImageItem> deletedUploadPic = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_UPLOADED);
                uploadedImageList = deletedUploadPic;

                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                allImageList.clear();
                allImageList.addAll(images);
                adapter.setImages(allImageList);
            }
        }
    }

    /**
     * 获取已上传的
     */
    private void initHttpData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("imageType", getIntent().getStringExtra("imageType"));
            paramJo.put("recordId", getIntent().getStringExtra("id"));
            paramJo.put("imageCode", getIntent().getStringExtra("code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.getMediaInfos, paramJo.toString());
        OkGo.<LzyResponse<List<ImageItem>>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<List<ImageItem>>>(mActivity, true) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<ImageItem>>> response) {
                        if(response.body().getAuto_data() != null){
                            uploadedImageList = (ArrayList<ImageItem>) response.body().getAuto_data();
                            allImageList.addAll(uploadedImageList);
                            adapter.setImages(allImageList);
                        }
                    }
                });
    }

    private void deletePic(final int position) {
        DialogUtils2.showConfirmDialog("提示", "确定删除此图片", mActivity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(allImageList.get(position).id)) {
                    //删除未保存的
                    selImageList.remove(position - uploadedImageList.size());
                    notifyPic();
                } else {
                    //删除已保存的
                    deleteSavedPic(allImageList.get(position).id);
                }
            }
        });
    }

    private void deleteSavedPic(final String id) {
        OkGo.<LzyResponse>post(Urls.FirstHost + Urls.delImgDataById).tag(this)
                .params("imageId", id)
                .execute(new DialogCallback<LzyResponse>(this, true) {
                    @Override
                    public void onSuccess(Response<LzyResponse> response) {
                            for (int i = 0; i < uploadedImageList.size(); i++) {
                                if (uploadedImageList.get(i).id.equals(id)) {
                                    uploadedImageList.remove(i);
                                    break;
                                }
                            }
                            notifyPic();
                    }
                });
    }

    private void notifyPic() {
        allImageList.clear();
        allImageList.addAll(selImageList);
        allImageList.addAll(uploadedImageList);
        adapter.setImages(allImageList);
    }
}
