package com.auto.chishan.manager.ui.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ProjectDetailBean;
import com.auto.chishan.manager.ui.customer.CustomerListActivity;
import com.auto.chishan.manager.ui.customer.ImageDataActivity;
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
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/8
 * desc:
 *
 * @author:DingZhixiang
 */
public class ProjectDetailActivity extends BaseResultActivity {

    @BindView(R.id.customerNameTv)
    TextView customerNameTv;
    @BindView(R.id.customerNameRl)
    RelativeLayout customerNameRl;
    @BindView(R.id.busiTypeTv)
    TextView busiTypeTv;
    @BindView(R.id.busiTypeRl)
    RelativeLayout busiTypeRl;
    @BindView(R.id.productTypeTv)
    TextView productTypeTv;
    @BindView(R.id.entityCompanyNameTv)
    TextView entityCompanyNameTv;
    @BindView(R.id.projectNameEt)
    EditText projectNameEt;
    @BindView(R.id.applyTimeTv)
    TextView applyTimeTv;
    @BindView(R.id.financingAmountEt)
    EditText financingAmountEt;
    @BindView(R.id.timeLimitTypeTv)
    TextView timeLimitTypeTv;
    @BindView(R.id.timeLimitEt)
    EditText timeLimitEt;
    @BindView(R.id.baseInterestTypeTv)
    TextView baseInterestTypeTv;
    @BindView(R.id.interestRateEt)
    EditText interestRateEt;
    @BindView(R.id.repaymentWayTv)
    TextView repaymentWayTv;
    @BindView(R.id.repaymentMethodTv)
    TextView repaymentMethodTv;
    @BindView(R.id.lesseCustomerNameTv)
    TextView lesseCustomerNameTv;
    @BindView(R.id.imageDataLayout)
    RelativeLayout imageDataLayout;
    @BindView(R.id.repaymentWayRl)
    RelativeLayout repaymentWayRl;
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.financingAmountTitleTv)
    TextView financingAmountTitleTv;
    @BindView(R.id.timeLimitTitleTv)
    TextView timeLimitTitleTv;
    @BindView(R.id.marginRateEt)
    EditText marginRateEt;
    @BindView(R.id.depositAmountEt)
    EditText depositAmountEt;
    @BindView(R.id.isAffiliatedCompanyTv)
    TextView isAffiliatedCompanyTv;
    @BindView(R.id.qualificationGradeEt)
    EditText qualificationGradeEt;
    @BindView(R.id.guaranteeBeneficiaryEt)
    EditText guaranteeBeneficiaryEt;
    @BindView(R.id.pawnLayout)
    LinearLayout pawnLayout;
    @BindView(R.id.guaranteeLayout)
    LinearLayout guaranteeLayout;
    @BindView(R.id.busiCodeTv)
    TextView busiCodeTv;
    @BindView(R.id.busiCodeLayout)
    LinearLayout busiCodeLayout;

    private int selectType = 1;
    private String busiType, productType, entityCompanyId, timeLimitType, customerId, lesseCustomerId, baseInterestType, repaymentWay, repaymentMethod, isAffiliatedCompany;
    private ProjectDetailBean detailBean;

    //弹出选择
    private List<SelectBean> selectBeans;
    private SelectAdapter selectAdapter;
    private RecyclerView recyclerView;
    private PopupLayout popupLayout;

    //项目列表是否需要刷新，修改或者创建了需要刷新
    private boolean needRefresh = false;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_project;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("项目");
        busiType = getIntent().getStringExtra("busiType");
        titlebackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("needRefsh",needRefresh);
                setResult(1001,intent);
                finish();
            }
        });
        if (busiType.equals("pawn")) {
            busiTypeTv.setText("典当");
            pawnLayout.setVisibility(View.VISIBLE);
            guaranteeLayout.setVisibility(View.GONE);
            financingAmountTitleTv.setText("融资金额");
            timeLimitTitleTv.setText("融资期限");
        } else {
            busiTypeTv.setText("担保");
            pawnLayout.setVisibility(View.GONE);
            guaranteeLayout.setVisibility(View.VISIBLE);
            financingAmountTitleTv.setText("担保金额");
            timeLimitTitleTv.setText("担保期限");
        }
        //业务类型前面已经选好了
        busiTypeRl.setEnabled(false);

        if (getIntent().getBooleanExtra("isChange", false)) {
            //修改/查看 展示影像资料
            saveBtn.setText("保存");
            imageDataLayout.setVisibility(View.VISIBLE);
            busiCodeLayout.setVisibility(View.VISIBLE);
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
                        productTypeTv.setText(selectAdapter.getData().get(position).getName());
                        productType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 3:
                        entityCompanyNameTv.setText(selectAdapter.getData()
                                .get(position)
                                .getName());
                        entityCompanyId = selectAdapter.getData().get(position).getCode();
                        break;
                    case 4:
                        timeLimitTypeTv.setText(selectAdapter.getData().get(position).getName());
                        timeLimitType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 5:
                        baseInterestTypeTv.setText(selectAdapter.getData().get(position).getName());
                        baseInterestType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 6:
                        repaymentWayTv.setText(selectAdapter.getData().get(position).getName());
                        repaymentWay = selectAdapter.getData().get(position).getCode();
                        break;
                    case 7:
                        repaymentMethodTv.setText(selectAdapter.getData().get(position).getName());
                        repaymentMethod = selectAdapter.getData().get(position).getCode();
                        //当选择的还款方式是自定义和随借随还的时候 ，收取方式可以自己选，还款方式为其他的时候，收取方式默认为预收。
                        repaymentWayTv.setText("预收");
                        repaymentWay = "0";
                        if (repaymentMethod.equals("8") || repaymentMethod.equals("10")) {
                            repaymentWayRl.setEnabled(true);
                        } else {
                            repaymentWayRl.setEnabled(false);
                        }
                        break;
                    case 8:
                        isAffiliatedCompany = selectAdapter.getData().get(position).getCode();
                        isAffiliatedCompanyTv.setText(selectAdapter.getData()
                                .get(position)
                                .getName());
                        break;
                }
                popupLayout.dismiss();
            }
        });
    }

    @OnClick({R.id.customerNameRl, R.id.busiTypeRl, R.id.imageDataLayout, R.id.productTypeRl, R.id.entityCompanyNameRl, R.id.isAffiliatedCompanyRl, R.id.applyTimeRl, R.id.timeLimitTypeRl, R.id.baseInterestTypeRl, R.id.repaymentWayRl, R.id.lesseCustomerNameRl, R.id.repaymentMethodRl, R.id.saveBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customerNameRl:
                //申请人
                Intent intent = new Intent(mActivity, CustomerListActivity.class);
                intent.putExtra("tag", 2);
                startActivityForResult(intent, 1001);
                break;
            case R.id.busiTypeRl:
                selectType = 1;
                getBusiType();
                break;
            case R.id.productTypeRl:
                //产品类型
                selectType = 2;
                getProductTypeList();
                break;
            case R.id.entityCompanyNameRl:
                //操作公司
                selectType = 3;
                getEntityCompanyList();
                break;
            case R.id.applyTimeRl:
                //申请日期
                final Calendar time = Calendar.getInstance();
                Dialog dateDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        applyTimeTv.setText(arg1 + "-" + (arg2 + 1) + "-" + arg3);
                    }
                }, time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("选择申请日期");
                dateDialog.show();
                break;
            case R.id.timeLimitTypeRl:
                //申请日期
                selectType = 4;
                getTimeLimitType();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.baseInterestTypeRl:
                //基准利率
                selectType = 5;
                getBaseInterestType();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.repaymentWayRl:
                //收取方式
                selectType = 6;
                getRepaymentWay();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.lesseCustomerNameRl:
                //共同申请人
                Intent intentLesseCustomer = new Intent(mActivity, CustomerListActivity.class);
                intentLesseCustomer.putExtra("tag", 2);
                startActivityForResult(intentLesseCustomer, 1002);
                break;
            case R.id.repaymentMethodRl:
                //还款方式
                selectType = 7;
                getRepaymentMethod();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.isAffiliatedCompanyRl:
                //是否挂靠
                selectType = 8;
                getIsAffiliatedCompany();
                selectAdapter.notifyDataSetChanged();
                popupLayout.show();
                break;
            case R.id.imageDataLayout:
                Intent intent1 = new Intent(mActivity, ImageDataActivity.class);
                intent1.putExtra("id", detailBean.getId());
                intent1.putExtra("imageType", detailBean.getImageType());
                startActivity(intent1);
                break;
            case R.id.saveBtn:
                save();
                break;
        }
    }

    private void getData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("id", getIntent().getStringExtra("id"));
            paramJo.put("busiType", getIntent().getStringExtra("busiType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.editProjectInfo, paramJo.toString());
        OkGo.<LzyResponse<ProjectDetailBean>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<ProjectDetailBean>>(mActivity, true) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ProjectDetailBean>> response) {
                        detailBean = response.body().getAuto_data();
                        busiType = detailBean.getBusiType();
                        if (!TextUtils.isEmpty(busiType)) {
                            if (busiType.equals("pawn")) {
                                busiTypeTv.setText("典当");
                            } else {
                                busiTypeTv.setText("担保");
                            }
                        }
                        customerId = detailBean.getCustomerId();
                        customerNameTv.setText(detailBean.getCustomerName());
                        productType = detailBean.getProductType();
                        productTypeTv.setText(detailBean.getProductTypeName());
                        entityCompanyId = detailBean.getEntityCompanyId();
                        entityCompanyNameTv.setText(detailBean.getEntityCompanyName());
                        projectNameEt.setText(detailBean.getProjectName());
                        busiCodeTv.setText(detailBean.getBusiCode());
                        applyTimeTv.setText(detailBean.getApplyTime());
                        financingAmountEt.setText(detailBean.getFinancingAmount());
                        timeLimitEt.setText(detailBean.getTimeLimit());
                        timeLimitType = detailBean.getTimeLimitType();
                        if (!TextUtils.isEmpty(timeLimitType)) {
                            getTimeLimitType();
                            for (SelectBean selectBean : selectBeans) {
                                if (timeLimitType.equals(selectBean.getCode())) {
                                    timeLimitTypeTv.setText(selectBean.getName());
                                }
                            }
                        }

                        baseInterestType = detailBean.getBaseInterestType();
                        if (!TextUtils.isEmpty(baseInterestType)) {
                            getBaseInterestType();
                            for (SelectBean selectBean : selectBeans) {
                                if (baseInterestType.equals(selectBean.getCode())) {
                                    baseInterestTypeTv.setText(selectBean.getName());
                                }
                            }
                        }

                        interestRateEt.setText(detailBean.getInterestRate());

                        repaymentMethod = detailBean.getRepaymentMethod();
                        if (!TextUtils.isEmpty(repaymentMethod)) {
                            //当选择的还款方式是自定义和随借随还的时候，能够选择收取方式为预收或者后收
                            if (repaymentMethod.equals("8") || repaymentMethod.equals("10")) {
                                repaymentWayRl.setVisibility(View.VISIBLE);
                            } else {
                                repaymentWayRl.setVisibility(View.GONE);
                            }
                            getRepaymentMethod();
                            for (SelectBean selectBean : selectBeans) {
                                if (repaymentMethod.equals(selectBean.getCode())) {
                                    repaymentMethodTv.setText(selectBean.getName());
                                }
                            }
                        }

                        repaymentWay = detailBean.getRepaymentWay();
                        if (!TextUtils.isEmpty(repaymentWay)) {
                            getRepaymentWay();
                            for (SelectBean selectBean : selectBeans) {
                                if (repaymentWay.equals(selectBean.getCode())) {
                                    repaymentWayTv.setText(selectBean.getName());
                                }
                            }
                        }

                        lesseCustomerId = detailBean.getLesseCustomerId();
                        lesseCustomerNameTv.setText(detailBean.getLesseCustomerName());

                        marginRateEt.setText(detailBean.getMarginRate());
                        depositAmountEt.setText(detailBean.getDepositAmount());
                        qualificationGradeEt.setText(detailBean.getQualificationGrade());
                        guaranteeBeneficiaryEt.setText(detailBean.getGuaranteeBeneficiary());
                        isAffiliatedCompany = detailBean.getIsAffiliatedCompany();
                        if (!TextUtils.isEmpty(isAffiliatedCompany)) {
                            if (isAffiliatedCompany.equals("0")) {
                                isAffiliatedCompanyTv.setText("是");
                            } else {
                                isAffiliatedCompanyTv.setText("否");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<LzyResponse<ProjectDetailBean>> response) {
                        super.onError(response);
                    }
                });
    }

    private void save() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("id", getIntent().getStringExtra("id"));
            paramJo.put("customerId", customerId);
            paramJo.put("busiType", busiType);
            paramJo.put("productType", productType);
            paramJo.put("entityCompanyId", entityCompanyId);
            paramJo.put("projectName", projectNameEt.getText().toString().trim());
            paramJo.put("applyTime", applyTimeTv.getText().toString().trim());
            paramJo.put("financingAmount", financingAmountEt.getText().toString().trim());
            paramJo.put("timeLimit", timeLimitEt.getText().toString().trim());
            paramJo.put("timeLimitType", timeLimitType);
            paramJo.put("baseInterestType", baseInterestType);
            paramJo.put("interestRate", interestRateEt.getText().toString().trim());
            paramJo.put("repaymentMethod", repaymentMethod);
            paramJo.put("repaymentWay", repaymentWay);
            paramJo.put("lesseCustomerId", lesseCustomerId);
            paramJo.put("marginRate", marginRateEt.getText().toString().trim());
            paramJo.put("depositAmount", depositAmountEt.getText().toString().trim());
            paramJo.put("qualificationGrade", qualificationGradeEt.getText().toString().trim());
            paramJo.put("guaranteeBeneficiary", guaranteeBeneficiaryEt.getText().toString().trim());
            paramJo.put("isAffiliatedCompany", isAffiliatedCompany);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.saveProjectInfo, paramJo.toString());
        OkGo.<LzyResponse>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse>(mActivity, true) {
                    @Override
                    public void onSuccess(final Response<LzyResponse> response) {
                        needRefresh = true;
                        if (detailBean == null) {
                            //创建新客户
                            DialogUtils2.showAlertDialog("提示", "创建成功，去上传影像资料", mActivity, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mActivity, ImageDataActivity.class);
                                    try {
                                        JSONObject jo = new JSONObject(response.body()
                                                .getAuto_data()
                                                .toString());
                                        intent.putExtra("id", jo.optString("projectId"));
                                        intent.putExtra("imageType", jo.optString("imageType"));
                                        startActivity(intent);
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
     * 获取产品列表
     */
    private void getProductTypeList() {
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("busiType", busiType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.getProductTypeList, paramJo.toString());
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
     * 获取实体公司列表
     */
    private void getEntityCompanyList() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.getEntityCompanyList, null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1001) {
                //选择申请人
                if (!TextUtils.isEmpty(data.getStringExtra("id")) && !TextUtils.isEmpty(lesseCustomerId) && data
                        .getStringExtra("id")
                        .equals(lesseCustomerId)) {
                    DialogUtils2.showAlertDialog("提醒", "申请人和共同申请人不能是同一个人", mActivity);
                } else {
                    customerId = data.getStringExtra("id");
                    customerNameTv.setText(data.getStringExtra("name"));
                }
            } else if (requestCode == 1002) {
                //共同申请人
                if (!TextUtils.isEmpty(customerId) && !TextUtils.isEmpty(data.getStringExtra("id")) && customerId
                        .equals(data.getStringExtra("id"))) {
                    DialogUtils2.showAlertDialog("提醒", "申请人和共同申请人不能是同一个人", mActivity);
                } else {
                    lesseCustomerId = data.getStringExtra("id");
                    lesseCustomerNameTv.setText(data.getStringExtra("name"));
                }
            }
        }
    }

    /**
     * 获取期限类型
     */
    private void getTimeLimitType() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setName("按月");
        beanAccount1.setCode("1");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setName("按日");
        beanAccount2.setCode("2");
        selectBeans.add(beanAccount2);
    }

    /**
     * 获取利率类型
     */
    private void getBaseInterestType() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setName("按月");
        beanAccount1.setCode("1");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setName("按日");
        beanAccount2.setCode("2");
        selectBeans.add(beanAccount2);
    }

    /**
     * 获取收取方式
     */
    private void getRepaymentWay() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setName("预收");
        beanAccount1.setCode("0");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setName("后收");
        beanAccount2.setCode("1");
        selectBeans.add(beanAccount2);
    }


    /**
     * 是否挂靠
     */
    private void getIsAffiliatedCompany() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setName("是");
        beanAccount1.setCode("0");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setName("否");
        beanAccount2.setCode("1");
        selectBeans.add(beanAccount2);
    }

    /**
     * 获取还款方式
     */
    private void getRepaymentMethod() {
        selectBeans.clear();
        SelectBean beanAccount1 = new SelectBean();
        beanAccount1.setName("按期收费到期还本");
        beanAccount1.setCode("4");
        selectBeans.add(beanAccount1);

        SelectBean beanAccount2 = new SelectBean();
        beanAccount2.setName("预收全部费用到期还本");
        beanAccount2.setCode("7");
        selectBeans.add(beanAccount2);

        SelectBean beanAccount3 = new SelectBean();
        beanAccount3.setName("自定义计划表");
        beanAccount3.setCode("8");
        selectBeans.add(beanAccount3);

        SelectBean beanAccount4 = new SelectBean();
        beanAccount4.setName("随借随还");
        beanAccount4.setCode("10");
        selectBeans.add(beanAccount4);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent();
            intent.putExtra("needRefsh",needRefresh);
            setResult(1001,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
