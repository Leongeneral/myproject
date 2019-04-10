package com.auto.chishan.manager.ui.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.ContractAdapter;
import com.auto.chishan.manager.bean.ContractBean;
import com.auto.chishan.manager.bean.UnderwriteLookDetailBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.adapter.SelectAdapter;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.bean.SelectBean;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.DialogUtils2;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.view.DivItemDecoration;
import com.my.commonlibrary.view.poplayout.PopupLayout;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.OnItemLongClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class UnderwriteLookActivity extends BaseResultActivity {
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.contractNoTv)
    TextView contractNoTv;
    @BindView(R.id.addContractIv)
    ImageView addContractIv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.moneyMaxEt)
    EditText moneyMaxEt;
    @BindView(R.id.contractMoneyEt)
    EditText contractMoneyEt;
    @BindView(R.id.contractTypeTv)
    TextView contractTypeTv;
    @BindView(R.id.contractTypeRl)
    RelativeLayout contractTypeRl;
    @BindView(R.id.flowTv)
    TextView flowTv;
    @BindView(R.id.flowRl)
    RelativeLayout flowRl;
    @BindView(R.id.contractStartDateTv)
    TextView contractStartDateTv;
    @BindView(R.id.contractStartDateRl)
    RelativeLayout contractStartDateRl;
    @BindView(R.id.contractEndDateTv)
    TextView contractEndDateTv;
    @BindView(R.id.contractEndDateRl)
    RelativeLayout contractEndDateRl;
    @BindView(R.id.contractNoLl)
    LinearLayout contractNoLl;

    //弹出的是选择什么
    private int selectType = 1;
    private String contractType,flow;
    //弹出选择
    private List<SelectBean> selectBeans;
    private SelectAdapter selectAdapter;
    private RecyclerView recyclerViewPop;
    private PopupLayout popupLayout;

    private UnderwriteLookDetailBean detailBean;
    private List<ContractBean> contractBeans;
    private ContractAdapter adapter;
    private int clickPosition;
    @Override
    protected int setLayout() {
        return R.layout.activity_underwrite_look;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        if (getIntent().getIntExtra("type", 1) == 2) {
            titleContentTv.setText("核保查看");
            saveBtn.setVisibility(View.GONE);
            addContractIv.setVisibility(View.GONE);
            contractNoLl.setVisibility(View.GONE);
        } else {
            titleContentTv.setText("核保申请");
            saveBtn.setText("保存并发送");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(30, false));
    }

    @Override
    protected void initData() {
        /** 底部弹出选择 */
        View typeView = View.inflate(mActivity, R.layout.include_onlyrv, null);
        recyclerViewPop = typeView.findViewById(R.id.recyclerView);
        selectBeans = new ArrayList<>();
        selectAdapter = new SelectAdapter(mActivity, selectBeans, R.layout.item_tv_center);
        recyclerViewPop.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerViewPop.addItemDecoration(new DivItemDecoration(1, false));
        recyclerViewPop.setAdapter(selectAdapter);
        popupLayout = PopupLayout.init(mActivity, typeView);
        popupLayout.setHeight(300, true);
        popupLayout.setUseRadius(false);
        selectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                switch (selectType) {
                    case 1:
                        contractTypeTv.setText(selectAdapter.getData().get(position).getName());
                        contractType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 2:
                        flowTv.setText(selectAdapter.getData().get(position).getLabel());
                        flow = selectAdapter.getData().get(position).getValue();
                        break;
                }
                popupLayout.dismiss();
            }
        });

        contractBeans = new ArrayList<>();
        adapter = new ContractAdapter(mActivity, contractBeans, R.layout.item_contract);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                clickPosition = position;
                Intent intent = new Intent(mActivity, AddContractActivity.class);
                intent.putExtra("bean",contractBeans.get(position));
                if(detailBean != null){
                    //查看的不能修改
                    intent.putExtra("isChange",false);
                }else{
                    intent.putExtra("isChange",true);
                }
                startActivityForResult(intent,1002);
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int viewType, final int position) {
                if(detailBean == null){
                    //查看的不能删除，新增的可以删除
                    DialogUtils2.showAlertDialog("提醒", "确定删除该条数据", mActivity, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contractBeans.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                    });
                }
            }
        });
        showContractInfo();
    }

    @OnClick({R.id.contractTypeRl, R.id.flowRl, R.id.contractStartDateRl, R.id.contractEndDateRl,R.id.saveBtn,R.id.addContractIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contractTypeRl:
                //合同类型
                selectType = 1;
                getContractType();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.flowRl:
                //流程
                selectType = 2;
                getFlow();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.contractStartDateRl:
                //合同开始日期
                final Calendar time = Calendar.getInstance();
                Dialog dateDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        contractStartDateTv.setText(arg1 + "-" + (arg2 + 1) + "-" + arg3);
                    }
                }, time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("选择合同起始日");
                dateDialog.show();
                break;
            case R.id.contractEndDateRl:
                //合同结束日期
                final Calendar time2 = Calendar.getInstance();
                Dialog dateDialog2 = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        contractEndDateTv.setText(arg1 + "-" + (arg2 + 1) + "-" + arg3);
                    }
                }, time2.get(Calendar.YEAR), time2.get(Calendar.MONTH), time2.get(Calendar.DAY_OF_MONTH));
                dateDialog2.setTitle("选择合同结束日");
                dateDialog2.show();
                break;
            case R.id.addContractIv:
                //添加合同
                Intent intent = new Intent(mActivity,AddContractActivity.class);
                intent.putExtra("isChange",true);
                startActivityForResult(intent,1001);
                break;
            case R.id.saveBtn:
                if (TextUtils.isEmpty(moneyMaxEt.getText().toString().trim())){
                    DialogUtils2.showAlertDialog("请输入合同金额上限",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(contractType)){
                    DialogUtils2.showAlertDialog("请选择合同类型",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(flow)){
                    DialogUtils2.showAlertDialog("请选择流程类型",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(contractStartDateTv.getText().toString().trim())){
                    DialogUtils2.showAlertDialog("请选择合同起始日",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(contractEndDateTv.getText().toString().trim())){
                    DialogUtils2.showAlertDialog("请选择合同结束日",mActivity);
                    return;
                }
                save();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == 1001){
                //添加合同清单
                contractBeans.add((ContractBean) data.getParcelableExtra("bean"));
                adapter.notifyItemRangeInserted(contractBeans.size() - 1, 1);
            }else if(requestCode == 1002){
                //修改合同清单
                ContractBean contractBean = data.getParcelableExtra("bean");
                adapter.getData().remove(clickPosition);
                adapter.getData().add(clickPosition, contractBean);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 获取合同详情
     */
    private void showContractInfo() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("contractId", getIntent().getStringExtra("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.showContractInfo, paramJo.toString());
        OkGo.<LzyResponse<UnderwriteLookDetailBean>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<UnderwriteLookDetailBean>>(mActivity, true) {
                    @Override
                    public void onSuccess(Response<LzyResponse<UnderwriteLookDetailBean>> response) {
                        detailBean = response.body().getAuto_data();

                    }

                    @Override
                    public void onError(Response<LzyResponse<UnderwriteLookDetailBean>> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 添加
     */
    private void save() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("projectId", getIntent().getStringExtra("projectId"));
            paramJo.put("contractNo", contractNoTv.getText().toString());
            paramJo.put("contractStartDate", contractStartDateTv.getText().toString().trim());
            paramJo.put("contractSignAmount", contractMoneyEt.getText().toString().trim());
            paramJo.put("contractEndDate", contractEndDateTv.getText().toString().trim());
            paramJo.put("contractAmount", moneyMaxEt.getText().toString().trim());
            paramJo.put("contractTempType", contractType);
            paramJo.put("flowType", flow);
            JSONArray ja = new JSONArray();
            for (ContractBean contractBean:contractBeans) {
               JSONObject jo = new JSONObject();
               jo.put("contractTxt",contractBean.getContractTxt());
               jo.put("contractNo",contractBean.getContractNo());
               jo.put("contractName",contractBean.getContractName());
               jo.put("contractNum",contractBean.getContractNum());
               ja.put(jo);
            }
            paramJo.put("contractDetailList", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.saveCustomerInfo, paramJo.toString());
        OkGo.<LzyResponse>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse>(mActivity, true) {
                    @Override
                    public void onSuccess(final Response<LzyResponse> response) {
                       DialogUtils2.showAlertDialog("提示", "添加成功", mActivity, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               finish();
                           }
                       });
                    }

                    @Override
                    public void onError(Response<LzyResponse> response) {
                        super.onError(response);
                    }
                });
    }


    /**
     * 合同类型
     */
    private void getContractType() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setLabel("草拟合同");
        beanAccount1.setValue("true");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setLabel("格式合同");
        beanAccount2.setValue("false");
        selectBeans.add(beanAccount2);
    }

    /**
     * 流程
     */
    private void getFlow() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setLabel("非印刷合同");
        beanAccount1.setValue("2");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setLabel("印刷合同");
        beanAccount2.setValue("1");
        selectBeans.add(beanAccount2);
    }
}
