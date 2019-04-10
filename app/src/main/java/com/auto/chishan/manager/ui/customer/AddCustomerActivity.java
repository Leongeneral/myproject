package com.auto.chishan.manager.ui.customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.CustomerDataDetailBean;
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
public class AddCustomerActivity extends BaseResultActivity {
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.personLayout)
    LinearLayout personLayout;
    @BindView(R.id.companyLayout)
    LinearLayout companyLayout;
    @BindView(R.id.imageDataLayout)
    RelativeLayout imageDataLayout;
    @BindView(R.id.busiTypeTv)
    TextView busiTypeTv;
    @BindView(R.id.customerNameEt)
    EditText customerNameEt;
    @BindView(R.id.certificateTypeTv)
    TextView certificateTypeTv;
    @BindView(R.id.certificateNumEt)
    EditText certificateNumEt;
    @BindView(R.id.phoneNumEt)
    EditText phoneNumEt;
    @BindView(R.id.maritalStatusTv)
    TextView maritalStatusTv;
    @BindView(R.id.workUnitsEt)
    EditText workUnitsEt;
    @BindView(R.id.customerNameCompanyEt)
    EditText customerNameCompanyEt;
    @BindView(R.id.registeredCapitaEt)
    EditText registeredCapitaEt;
    @BindView(R.id.certificateNumCompanyEt)
    EditText certificateNumCompanyEt;
    @BindView(R.id.legalNameEt)
    EditText legalNameEt;
    @BindView(R.id.legalCertificateNumEt)
    EditText legalCertificateNumEt;
    @BindView(R.id.actControllerEt)
    EditText actControllerEt;
    @BindView(R.id.actControllerCertificateNumEt)
    EditText actControllerCertificateNumEt;
    @BindView(R.id.busiTypeRl)
    RelativeLayout busiTypeRl;

    //弹出选择
    private List<SelectBean> selectBeans;
    private SelectAdapter selectAdapter;
    private RecyclerView recyclerView;
    private PopupLayout popupLayout;
    //弹出的是选择什么，1.业务类型 2.证件类型 3.婚姻状况
    private int selectType = 1;
    private String busiType, certificateType, maritalStatus;

    private CustomerDataDetailBean detailBean;

    @Override
    protected int setLayout() {
        return R.layout.activity_add_customer;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("客户");

        if (getIntent().getStringExtra("custType").equals("1")) {
            personLayout.setVisibility(View.VISIBLE);
            companyLayout.setVisibility(View.GONE);
        } else {
            personLayout.setVisibility(View.GONE);
            companyLayout.setVisibility(View.VISIBLE);
        }

        if (getIntent().getBooleanExtra("isChange", false)) {
            //修改/查看 展示影像资料
            imageDataLayout.setVisibility(View.VISIBLE);
            saveBtn.setText("保存");
            busiTypeRl.setEnabled(false);
            getData();
        } else {
            imageDataLayout.setVisibility(View.GONE);
            saveBtn.setText("保存并继续");
        }
    }

    @Override
    protected void initData() {
        /** 底部弹出选择 */
        View typeView = View.inflate(mActivity, R.layout.include_onlyrv, null);
        recyclerView = typeView.findViewById(R.id.recyclerView);
        selectBeans = new ArrayList<>();
        selectAdapter = new SelectAdapter(mActivity, selectBeans, R.layout.item_tv_center);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DivItemDecoration(1, false));
        recyclerView.setAdapter(selectAdapter);
        popupLayout = PopupLayout.init(mActivity, typeView);
        popupLayout.setHeight(300, true);
        popupLayout.setUseRadius(false);
        selectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                switch (selectType) {
                    case 1:
                        busiTypeTv.setText(selectAdapter.getData().get(position).getName());
                        busiType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 2:
                        certificateTypeTv.setText(selectAdapter.getData().get(position).getLabel());
                        certificateType = selectAdapter.getData().get(position).getValue();
                        break;
                    case 3:
                        maritalStatusTv.setText(selectAdapter.getData().get(position).getLabel());
                        maritalStatus = selectAdapter.getData().get(position).getValue();
                        break;
                }
                popupLayout.dismiss();
            }
        });
    }

    private void getData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("customerId", getIntent().getStringExtra("customerId"));
            paramJo.put("custType", getIntent().getStringExtra("custType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.editCustomerInfo, paramJo.toString());
        OkGo.<LzyResponse<CustomerDataDetailBean>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<CustomerDataDetailBean>>(mActivity, true) {
                    @Override
                    public void onSuccess(Response<LzyResponse<CustomerDataDetailBean>> response) {
                        detailBean = response.body().getAuto_data();
                        busiType = detailBean.getBusiType();
                        if (!TextUtils.isEmpty(busiType)) {
                            if (busiType.equals("pawn")) {
                                busiTypeTv.setText("典当");
                            } else {
                                busiTypeTv.setText("担保");
                            }
                        }
                        customerNameEt.setText(detailBean.getCustomerName());
                        certificateType = detailBean.getCertificateType();
                        if (!TextUtils.isEmpty(certificateType)) {
                            getCertificateType();
                            for (SelectBean selectBean : selectBeans) {
                                if (certificateType.equals(selectBean.getValue())) {
                                    certificateTypeTv.setText(selectBean.getLabel());
                                }
                            }
                        }
                        certificateNumEt.setText(detailBean.getCertificateNum());
                        phoneNumEt.setText(detailBean.getPhoneNum());
                        maritalStatus = detailBean.getMaritalStatus();
                        if (!TextUtils.isEmpty(maritalStatus)) {
                            getMaritalStatus();
                            for (SelectBean selectBean : selectBeans) {
                                if (maritalStatus.equals(selectBean.getValue())) {
                                    maritalStatusTv.setText(selectBean.getLabel());
                                }
                            }
                        }
                        workUnitsEt.setText(detailBean.getWorkUnits());
                        customerNameCompanyEt.setText(detailBean.getCustomerName());
                        registeredCapitaEt.setText(detailBean.getRegisteredCapita());
                        certificateNumCompanyEt.setText(detailBean.getCertificateNum());
                        legalNameEt.setText(detailBean.getLegalName());
                        legalCertificateNumEt.setText(detailBean.getLegalCertificateNum());
                        actControllerEt.setText(detailBean.getActController());
                        actControllerCertificateNumEt.setText(detailBean.getActControllerCertificateNum());
                    }

                    @Override
                    public void onError(Response<LzyResponse<CustomerDataDetailBean>> response) {
                        super.onError(response);
                    }
                });
    }

    private void savePerson() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("customerId", getIntent().getStringExtra("customerId"));
            paramJo.put("busiType", busiType);
            paramJo.put("custType", getIntent().getStringExtra("custType"));
            if (getIntent().getStringExtra("custType").equals("1")) {
                paramJo.put("customerName", customerNameEt.getText().toString().trim());
                paramJo.put("certificateType", certificateType);
                paramJo.put("certificateNum", certificateNumEt.getText().toString().trim());
                paramJo.put("phoneNum", phoneNumEt.getText().toString().trim());
                paramJo.put("maritalStatus", maritalStatus);
                paramJo.put("workUnits", workUnitsEt.getText().toString().trim());
            } else {
                paramJo.put("customerName", customerNameCompanyEt.getText().toString().trim());
                paramJo.put("certificateType", certificateType);
                paramJo.put("certificateNum", certificateNumCompanyEt.getText().toString().trim());
                paramJo.put("registeredCapita", registeredCapitaEt.getText().toString().trim());
                paramJo.put("legalName", legalNameEt.getText().toString().trim());
                paramJo.put("legalCertificateNum", legalCertificateNumEt.getText()
                        .toString()
                        .trim());
                paramJo.put("actController", actControllerEt.getText().toString().trim());
                paramJo.put("actControllerCertificateNum", actControllerCertificateNumEt.getText()
                        .toString()
                        .trim());
            }
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
                        if (TextUtils.isEmpty(getIntent().getStringExtra("customerId"))) {
                            //创建新客户
                            DialogUtils2.showAlertDialog("提示", "创建成功，去上传影像资料", mActivity, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mActivity, ImageDataActivity.class);
                                    try {
                                        JSONObject jo = new JSONObject(response.body()
                                                .getAuto_data()
                                                .toString());
                                        intent.putExtra("id", jo.optString("customerId"));
                                        intent.putExtra("imageType", jo.optString("imageType"));
                                        startActivity(intent);
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        } else {
                            //修改客户资料
                            DialogUtils2.showAlertDialog("提示", "保存成功", mActivity, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Response<LzyResponse> response) {
                        super.onError(response);
                    }
                });
    }

    @OnClick({R.id.busiTypeRl, R.id.certificateTypeRl, R.id.maritalStatusRl, R.id.imageDataLayout, R.id.saveBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.busiTypeRl:
                selectType = 1;
                getBusiType();
                break;
            case R.id.certificateTypeRl:
                selectType = 2;
                getCertificateType();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.maritalStatusRl:
                selectType = 3;
                getMaritalStatus();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.imageDataLayout:
                Intent intent1 = new Intent(mActivity, ImageDataActivity.class);
                intent1.putExtra("id", getIntent().getStringExtra("customerId"));
                intent1.putExtra("imageType", detailBean.getImageType());
                startActivity(intent1);
                break;
            case R.id.saveBtn:
                savePerson();
                break;
        }
    }

    /**
     * 获取业务类型
     */
    private void getBusiType() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.getCurUserBusi, null);
        OkGo.<LzyResponse<List<SelectBean>>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<List<SelectBean>>>(mActivity, true) {
                    @Override
                    public void onSuccess(Response<LzyResponse<List<SelectBean>>> response) {
                        selectBeans.clear();
                        selectBeans.addAll(response.body().getAuto_data());
                        selectAdapter.notifyDataSetChanged();
                        popupLayout.show();
                    }

                    @Override
                    public void onError(Response<LzyResponse<List<SelectBean>>> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 获取证件类型
     */
    private void getCertificateType() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setLabel("身份证");
        beanAccount1.setValue("1");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setLabel("户口本");
        beanAccount2.setValue("2");
        selectBeans.add(beanAccount2);

        SelectBean beanAccount3 = new SelectBean();
        beanAccount3.setLabel("军官证");
        beanAccount3.setValue("3");
        selectBeans.add(beanAccount3);

        SelectBean beanAccount4 = new SelectBean();
        beanAccount4.setLabel("护照");
        beanAccount4.setValue("4");
        selectBeans.add(beanAccount4);

        SelectBean beanAccount5 = new SelectBean();
        beanAccount5.setLabel("外国人居留证");
        beanAccount5.setValue("5");
        selectBeans.add(beanAccount5);

        SelectBean beanAccount6 = new SelectBean();
        beanAccount6.setLabel("外国人出入境证");
        beanAccount6.setValue("6");
        selectBeans.add(beanAccount6);

        SelectBean beanAccount7 = new SelectBean();
        beanAccount7.setLabel("国外身份证");
        beanAccount7.setValue("7");
        selectBeans.add(beanAccount7);
    }

    /**
     * 获取婚姻状况
     */
    private void getMaritalStatus() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setLabel("已婚");
        beanAccount1.setValue("1");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setLabel("未婚单身");
        beanAccount2.setValue("2");
        selectBeans.add(beanAccount2);

        SelectBean beanAccount3 = new SelectBean();
        beanAccount3.setLabel("丧偶");
        beanAccount3.setValue("3");
        selectBeans.add(beanAccount3);

        SelectBean beanAccount4 = new SelectBean();
        beanAccount4.setLabel("离异单身");
        beanAccount4.setValue("4");
        selectBeans.add(beanAccount4);


    }
}
