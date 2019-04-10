package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.BankCardAdapter;
import com.auto.chishan.manager.bean.BankCardBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lyzhang3
 * date   : 2019/4/810:59 PM
 * desc   :
 */
public class MyBankcardActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;


    private BankCardAdapter adapter;
    private List<BankCardBean> beans;
    private int pageNo = 1;


    @Override
    protected int setLayout() {
        return R.layout.activity_my_installment;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("我的银行卡");
        title_rightImage.setVisibility(View.VISIBLE);



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

    @OnClick(R.id.title_rightImage)
    public void onViewClick(){
        //添加银行卡
        Intent intent = new Intent(this,AddBankCardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void initData() {

        beans = new ArrayList<>();
        adapter = new BankCardAdapter(mActivity, beans, R.layout.item_my_installment);
        recyclerView.setAdapter(adapter);


    }

    private void getData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        OkGo.<String>post(Urls.FirstHost+Urls.SecondHost1+ Urls.MYBANKCARD).tag(this)
                .params("customerId", spUtils.getUserId())
                .execute(new DialogCallback<String>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (pageNo == 1) {
                            beans.clear();
                        }
                        if (response.body() != null) {
//                            beans.addAll(response.body().getAuto_data().getMgrList());
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
