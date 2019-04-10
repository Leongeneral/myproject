package com.auto.chishan.manager.ui;

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
import com.auto.chishan.manager.ui.customer.AddCustomerActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseFragment;
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
 * Date: 2019/3/4
 * desc:
 *
 * @author:DingZhixiang
 */
public class CustomerFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    @BindView(R.id.uerNameEt)
    EditText uerNameEt;
    private CustomerAdapter adapter;
    private List<CustomerBean> beans;
    private int pageNo = 1;

    public static CustomerFragment getInstance() {
        return new CustomerFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.frag_customer;
    }

    @Override
    protected void initView() {
        super.initView();
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
                Intent intent = new Intent(mActivity, AddCustomerActivity.class);
                intent.putExtra("custType",beans.get(position).getCustType());
                intent.putExtra("customerId",beans.get(position).getId());
                intent.putExtra("isChange",true);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.title_right, R.id.searchBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                XPopup.get(getActivity()).asCenterList("选择客户类型",new String[]{"个人", "企业"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Intent intent = new Intent(mActivity, AddCustomerActivity.class);
                                if(position == 0){
                                    intent.putExtra("custType","1");
                                }else{
                                    intent.putExtra("custType","2");
                                }
                                startActivity(intent);
                            }
                        })
                        .show();
                break;
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
