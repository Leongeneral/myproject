package com.auto.chishan.manager.ui.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.UnderwriteManageAdapter;
import com.auto.chishan.manager.bean.ProjectManageBean;
import com.auto.chishan.manager.bean.UnderwriteManageDataBean;
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
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/8
 * desc:
 *
 * @author:DingZhixiang
 */
public class UnderwriteManageActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    @BindView(R.id.uerNameEt)
    EditText uerNameEt;

    private UnderwriteManageAdapter adapter;
    private List<ProjectManageBean> beans;
    private int pageNo = 1;
    @Override
    protected int setLayout() {
        return R.layout.activity_project_manage;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("核保管理");
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
        adapter = new UnderwriteManageAdapter(mActivity, beans, R.layout.item_underwrite);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Intent intent = new Intent(mActivity, UnderWriteMaintainActivity.class);
                intent.putExtra("customerName",beans.get(position).getCustomerName());
                intent.putExtra("id",beans.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.searchBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                pageNo = 1;
                getData();
                break;
        }
    }

    private void getData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("pageNo",pageNo);
            paramJo.put("pageSize",10);
            paramJo.put("customerName",uerNameEt.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils.getPwd(), Urls.managerContractList, paramJo.toString());
        OkGo.<LzyResponse<UnderwriteManageDataBean>>post(Urls.FirstHost+Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<UnderwriteManageDataBean>>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<LzyResponse<UnderwriteManageDataBean>> response) {
                        if (pageNo == 1) {
                            beans.clear();
                        }
                        if (response.body().getAuto_data() != null) {
                            beans.addAll(response.body().getAuto_data().getProjectList());
                        }
                        adapter.notifyDataSetChanged();
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }

                    @Override
                    public void onError(Response<LzyResponse<UnderwriteManageDataBean>> response) {
                        super.onError(response);
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }
                });
    }
}
