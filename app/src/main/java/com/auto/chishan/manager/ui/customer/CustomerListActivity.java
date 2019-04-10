package com.auto.chishan.manager.ui.customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.CustomerAdapter;
import com.auto.chishan.manager.bean.CustomerBean;
import com.auto.chishan.manager.bean.CustomerDataBean;
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
public class CustomerListActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    @BindView(R.id.uerNameEt)
    EditText uerNameEt;

    private CustomerAdapter adapter;
    private List<CustomerBean> beans;
    private int pageNo = 1;

    //1 正常从客户管理进入 ，2 创建贷款进入（选择客户）
    private int tag = 1;
    @Override
    protected int setLayout() {
        return R.layout.activity_project_manage;
    }

    @Override
    protected void initView() {
        super.initView();
        tag = getIntent().getIntExtra("tag",1);
        initTitleBar();
        titleContentTv.setText("客户管理");

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
        adapter = new CustomerAdapter(mActivity, beans, R.layout.item_customer);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if(tag == 1){
                    Intent intent = new Intent(mActivity, AddCustomerActivity.class);
                    intent.putExtra("custType",beans.get(position).getCustType());
                    intent.putExtra("customerId",beans.get(position).getId());
                    intent.putExtra("isChange",true);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("name",beans.get(position).getCustomerName());
                    intent.putExtra("id",beans.get(position).getId());
                    setResult(1001,intent);
                    finish();
                }
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
            paramJo.put("pageNo",pageNo);
            paramJo.put("pageSize",10);
            //查询共借人  申请人
            paramJo.put("isDraft ","0");
            paramJo.put("customerName",uerNameEt.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils.getPwd(), Urls.managerCustomerList, paramJo.toString());
        OkGo.<LzyResponse<CustomerDataBean>>post(Urls.FirstHost+Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<CustomerDataBean>>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<LzyResponse<CustomerDataBean>> response) {
                        if (pageNo == 1) {
                            beans.clear();
                        }
                        if (response.body().getAuto_data() != null) {
                            beans.addAll(response.body().getAuto_data().getCustList());
                        }
                        adapter.notifyDataSetChanged();
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }

                    @Override
                    public void onError(Response<LzyResponse<CustomerDataBean>> response) {
                        super.onError(response);
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }
                });
    }
}
