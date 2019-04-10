package com.auto.chishan.manager.ui.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.UserDataAdapter;
import com.auto.chishan.manager.bean.UserDataBean;
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
 * Date: 2019/3/10
 * desc:
 *
 * @author:DingZhixiang
 */
public class FindUserActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    @BindView(R.id.uerNameEt)
    EditText uerNameEt;

    private UserDataAdapter adapter;
    private List<UserDataBean> beans;
    private int pageNo = 1;

    //1 正常从客户管理进入 ，2 创建贷款进入（选择客户）
    private int tag = 1;
    @Override
    protected int setLayout() {
        return R.layout.activity_finduser;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("选择见证人");

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
        smartRefreshView.setEnableLoadMore(false);
        showNormal();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(1, false));
    }

    @Override
    protected void initData() {
        beans = new ArrayList<>();
        adapter = new UserDataAdapter(mActivity, beans, R.layout.item_tv_center);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("name",beans.get(position).getName());
                    intent.putExtra("id",beans.get(position).getUserId());
                    setResult(1001,intent);
                    finish();
            }
        });
    }

    @OnClick({ R.id.searchBtn})
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
            paramJo.put("name",uerNameEt.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils.getPwd(), Urls.findUser, paramJo.toString());
        OkGo.<LzyResponse<List<UserDataBean>>>post(Urls.FirstHost+Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<List<UserDataBean>>>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<UserDataBean>>> response) {
                        if (pageNo == 1) {
                            beans.clear();
                        }
                        if (response.body().getAuto_data() != null) {
                            beans.addAll(response.body().getAuto_data());
                        }
                        adapter.notifyDataSetChanged();
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }

                    @Override
                    public void onError(Response<LzyResponse<List<UserDataBean>>> response) {
                        super.onError(response);
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }
                });
    }
}
