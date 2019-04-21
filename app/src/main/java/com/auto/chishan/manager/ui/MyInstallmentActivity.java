package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.MyInstallmentAdapter;
import com.auto.chishan.manager.bean.InstallmentBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.view.DivItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : lyzhang3
 * date   : 2019/4/810:32 PM
 * desc   :
 */
public class MyInstallmentActivity extends BaseResultActivity {

    //：http://36.33.24.109:8199/weijinrong-app/app/customer/myProjects?

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;


    private MyInstallmentAdapter adapter;
    private List<InstallmentBean> beans;
    private int pageNo = 1;


    @Override
    protected int setLayout() {
        return R.layout.activity_my_installment;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("我的分期");

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
        smartRefreshView.autoRefresh();
        showNormal();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(30, false));

    }

    @Override
    protected void initData() {

        beans = new ArrayList<>();
        adapter = new MyInstallmentAdapter(mActivity, beans, R.layout.item_my_installment);
        recyclerView.setAdapter(adapter);


    }

    private void getData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        OkGo.<String>post(Urls.FirstHost+Urls.SecondHost1+ Urls.MYINSTALLMENT).tag(this)
                .params("customerId", spUtils.getUserId())
                .params("pageNo",pageNo)
                .execute(new DialogCallback<String>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (pageNo == 1) {
                            beans.clear();
                        }
                        String result = response.body();
                        try {
                            JSONObject json = new JSONObject(result);

                            if(json.getInt("code") == 1){
                                JSONArray array = json.getJSONObject("data").getJSONArray("arrData");
                                for(int i =0; i<array.length();i++){
                                    InstallmentBean bean = new InstallmentBean();
                                    JSONObject js = array.getJSONObject(i);
                                    bean.setJson(js.toString());
                                    bean.setId(js.getString("id"));
                                    JSONObject js1 = js.getJSONObject("estates");

                                    bean.setCommunity(js1.getString("community"));
                                    bean.setNumber(js1.getString("number"));
                                    bean.setAmount(js1.getString("amount"));
                                    beans.add(bean);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
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
}
