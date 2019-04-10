package com.auto.chishan.manager.ui.customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.ImageDataAdapter;
import com.auto.chishan.manager.bean.ImageDataBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.view.DivItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date: 2019/3/4
 * desc:
 *
 * @author:DingZhixiang
 */
public class ImageDataActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    private ImageDataAdapter adapter;
    private List<ImageDataBean> beans;
    @Override
    protected int setLayout() {
        return R.layout.activity_imagedata;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("影像资料");
        smartRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        smartRefreshView.autoRefresh();
        smartRefreshView.setEnableLoadMore(false);
        showNormal();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(1, false));
    }

    @Override
    protected void initData() {
        beans = new ArrayList<>();
        adapter = new ImageDataAdapter(mActivity, beans, R.layout.item_imagedata);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                ImageDataBean bean = adapter.getData().get(position);
                Intent intent = new Intent();
                intent.setClass(mActivity,UploadImageActivity.class);
                intent.putExtra("code", bean.getCode());
                intent.putExtra("name", bean.getName());
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("imageType", getIntent().getStringExtra("imageType"));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("imageType", getIntent().getStringExtra("imageType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.getImageTypeList, paramJo.toString());
        OkGo.<LzyResponse<List<ImageDataBean>>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<List<ImageDataBean>>>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<ImageDataBean>>> response) {
                        if(response.body().getAuto_data() != null){
                            beans.clear();
                            beans.addAll(response.body().getAuto_data());
                            adapter.notifyDataSetChanged();
                        }
                        smartRefreshView.finishRefresh();
                    }

                    @Override
                    public void onError(Response<LzyResponse<List<ImageDataBean>>> response) {
                        super.onError(response);
                        smartRefreshView.finishRefresh();
                    }
                });
    }
}
