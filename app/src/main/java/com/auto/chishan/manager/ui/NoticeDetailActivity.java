package com.auto.chishan.manager.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.NoticeBean;
import com.auto.chishan.manager.adapter.NoticeDetailAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseActivity;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.view.DivItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyzhang3
 * date   : 2019/4/811:55 PM
 * desc   :{
 * }
 */
public class NoticeDetailActivity extends BaseResultActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    private String name;
    private int type;

    NoticeDetailAdapter mAdapter;
    private List<NoticeBean> beans;
    private int pageNo = 1;

    @Override
    protected int setLayout() {
        return R.layout.activity_my_installment;
    }

    @Override
    protected void initView() {
        initTitleBar();
        name = getIntent().getStringExtra("name");
        type = getIntent().getIntExtra("type", 0);
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText(name);

        smartRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                getData();
            }
        });
        smartRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                getData();
            }
        });

        showNormal();
        smartRefreshView.autoRefresh();

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(30, false));

    }

    private void getData(){
        OkGo.<String>get(Urls.FirstHost + Urls.MYNOTICE).tag(this)
                .params("customerId", SPUtils.getInstance(NoticeDetailActivity.this).getUserId())
                .params("remindType", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body();
                        try {
                            JSONObject json = new JSONObject(result);


                            if (json.getInt("code") == 1) {
                                beans.clear();
                                JSONArray array = json.getJSONObject("data").getJSONArray("arrData");
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject js = array.getJSONObject(i);
                                    Gson gson = new GsonBuilder().create();
                                    NoticeBean bean = gson.fromJson(js.toString(), NoticeBean.class);
                                    beans.add(bean);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        String result = response.body();
//                        Log.e("****",response.body());
//                        ArrayList<NoticeBean> beans = new ArrayList<>();
//                        try {
//                            JSONObject json = new JSONObject(result);
//
//
//                            if(json.getInt("code") == 1){
//                                JSONArray array = json.getJSONObject("data").getJSONArray("arrData");
//                                if(array.length() !=0) {
//                                    JSONObject js = array.getJSONObject(0);
//                                    noticeRefund.setText(js.optString("content"));
//                                    noticeRefundTime.setText(js.optString("updateDate"));
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        mAdapter.notifyDataSetChanged();
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }
                });
    }

    @Override
    protected void initData() {

        beans = new ArrayList<>();
        mAdapter = new NoticeDetailAdapter(mActivity, beans, R.layout.item_notice_detail);
        mAdapter.setNotifyData(new NoticeDetailAdapter.NotifyData() {
            @Override
            public void notifyData() {
                getData();
            }
        });
        recyclerView.setAdapter(mAdapter);


    }


}
