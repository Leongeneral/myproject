package com.auto.chishan.manager.ui.afterloan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.AfterloanVisitAdapter;
import com.auto.chishan.manager.bean.ProjectManagerReviewBean;
import com.auto.chishan.manager.bean.ProjectManagerReviewListBean;
import com.auto.chishan.manager.ui.project.ProjectDetailActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
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

public class AfterloanVisitActivity extends BaseResultActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefresh_view)
    SmartRefreshLayout smartRefreshView;
    @BindView(R.id.uerNameEt)
    EditText uerNameEt;

    @BindView(R.id.typeSpinner)
    Spinner typeSpinner;
    private AfterloanVisitAdapter adapter;
    private List<ProjectManagerReviewBean> beans;
    private int pageNo = 1;

    private String busiType;
    @Override
    protected int setLayout() {
        return R.layout.activity_search_manage;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("贷后回访");

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


        ArrayAdapter adapter=ArrayAdapter.createFromResource(mActivity,R.array.busyType,R.layout.spinner_layout);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setBackgroundColor(0x0);
        adapter.setDropDownViewResource(R.layout.item_typespinner);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    busiType = "pawn";
                }else{
                    busiType = "guarantee";
                }
                smartRefreshView.autoRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void initData() {
        beans = new ArrayList<>();
        adapter = new AfterloanVisitAdapter(mActivity, beans, R.layout.item_base);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Intent intent = new Intent(mActivity, VisitRecordListActivity.class);
                intent.putExtra("ProjectReviewBean",beans.get(position));
                startActivityForResult(intent,1001);
            }
        });
    }

    @OnClick({R.id.title_right, R.id.searchBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                XPopup.get(mActivity).asCenterList("选择项目类型",new String[]{"典当", "担保"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Intent intent = new Intent(mActivity, ProjectDetailActivity.class);
                                if(position == 0){
                                    intent.putExtra("busiType","pawn");
                                }else{
                                    intent.putExtra("busiType","guarantee");
                                }
                                startActivityForResult(intent,1001);
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
//        pawn--典当，guarantee--担保
        try {
            paramJo.put("busiType",busiType );
            paramJo.put("pageNo",pageNo);
            paramJo.put("pageSize",10);
            paramJo.put("customerName",uerNameEt.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils.getPwd(), Urls.managerReviewMgrList, paramJo.toString());
        OkGo.<LzyResponse<ProjectManagerReviewListBean>>post(Urls.FirstHost+Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<ProjectManagerReviewListBean>>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ProjectManagerReviewListBean>> response) {
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
                    public void onError(Response<LzyResponse<ProjectManagerReviewListBean>> response) {
                        super.onError(response);
                        smartRefreshView.finishRefresh();
                        smartRefreshView.finishLoadMore();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(data.getBooleanExtra("needRefresh",false)){
                smartRefreshView.autoRefresh();
            }
        }
    }
}
