package com.auto.chishan.manager.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.ExpirationReminderAdapter;
import com.auto.chishan.manager.bean.ExpirationReminderBean;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.view.DivItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date: 2019/3/5
 * desc:
 *
 * @author:DingZhixiang
 */
public class ExpirationReminderActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;

    private ExpirationReminderAdapter adapter;
    private List<ExpirationReminderBean> beans;
    private int pageNo = 1;

    //expire 到期 overdue 逾期
    private String detailType;
    @Override
    protected int setLayout() {
        return R.layout.activity_expiration_reminder;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        detailType = getIntent().getStringExtra("detailType");
        if(detailType.equals("expire")){
            titleContentTv.setText("到期提醒");
        }else{
            titleContentTv.setText("逾期提醒");
        }
        smartRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
//                search();
            }
        });
        smartRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
//                search();
            }
        });
        showNormal();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(30, false));
    }

    @Override
    protected void initData() {
        beans = new ArrayList<>();
        beans.add(new ExpirationReminderBean());
        beans.add(new ExpirationReminderBean());
        beans.add(new ExpirationReminderBean());
        beans.add(new ExpirationReminderBean());
        beans.add(new ExpirationReminderBean());
        adapter = new ExpirationReminderAdapter(mActivity,detailType, beans, R.layout.item_expire_remind);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if(position == 0){
                    Intent intent = new Intent(mActivity, ProjectDetailExpireActivity.class);
                    intent.putExtra("projectType","dd");
                    intent.putExtra("detailType",detailType);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(mActivity, ProjectDetailExpireActivity.class);
                    intent.putExtra("projectType","db");
                    intent.putExtra("detailType",detailType);
                    startActivity(intent);
                }

            }
        });
    }
}
