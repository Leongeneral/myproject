package com.auto.chishan.manager.ui.afterloan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;

import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.ContractAdapter;
import com.auto.chishan.manager.bean.ContractBean;
import com.auto.chishan.manager.bean.ContractLookDetailBean;
import com.auto.chishan.manager.bean.ProjectManagerReviewBean;
import com.auto.chishan.manager.ui.customer.ImageDataActivity;
import com.auto.chishan.manager.ui.project.FindUserActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.bean.SelectBean;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.DesUtils;
import com.my.commonlibrary.utils.DialogUtils2;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.view.poplayout.PopupLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : lyzhang3
 * date   : 2019/3/162:16 PM
 * desc   :
 */
public class VisitAddRecordActivity extends BaseResultActivity {

    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.project_number)//项目编号
    TextView project_number;
    @BindView(R.id.customer_name)//客户名称
    TextView customer_name;

    @BindView(R.id.select)
    TextView select;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.moneyMaxEt)//本次回访内容
    EditText moneyMaxEt;
    @BindView(R.id.contractMoneyEt)//项目状态
    TextView contractMoneyEt;

    @BindView(R.id.iv_address)
    TextView mAddress;


    @BindView(R.id.contractStartDateTv)//回访时间选择
    TextView contractStartDateTv;

    @BindView(R.id.upload)
    TextView upload;

    //弹出的是选择什么
    private int selectType = 1;
    private String contractType,flow;
    //弹出选择
    private List<SelectBean> selectBeans;
    private PopupLayout popupLayout;

    private ContractLookDetailBean detailBean;
    private List<ContractBean> contractBeans;
    private ContractAdapter adapter;
    private int clickPosition;

    private ProjectManagerReviewBean reviewBean;
    @Override
    protected int setLayout() {
        return R.layout.activity_visit_add_record;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
//        if (getIntent().getIntExtra("type", 1) == 2) {
//            titleContentTv.setText("合同查看");
//            saveBtn.setVisibility(View.GONE);
//            addContractIv.setVisibility(View.GONE);
//            showContractInfo();
//        } else {
            titleContentTv.setText("回访记录添加");
            saveBtn.setText("保存并发送");
    }


    @Override
    protected void initData() {
        reviewBean = getIntent().getParcelableExtra("ProjectManagerReviewBean");

        project_number.setText(reviewBean.getBusiCode());
        customer_name.setText(reviewBean.getCustomerName());
        contractMoneyEt.setText(reviewBean.getProjectState());

        /** 底部弹出选择 */
        View typeView = View.inflate(mActivity, R.layout.include_onlyrv, null);
        selectBeans = new ArrayList<>();

        popupLayout = PopupLayout.init(mActivity, typeView);
        popupLayout.setHeight(300, true);
        popupLayout.setUseRadius(false);



        contractBeans = new ArrayList<>();
        adapter = new ContractAdapter(mActivity, contractBeans, R.layout.item_contract);

    }

    @OnClick({ R.id.contractStartDateRl, R.id.saveBtn , R.id.rl_image_add,R.id.rl_man})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {

            case R.id.rl_man:
                intent = new Intent(this,FindUserActivity.class);
                startActivityForResult(intent,1001);
                break;
            case R.id.rl_image_add:
                if(TextUtils.isEmpty(hideId)) {
                    getHideId();
                }else{
                    if(!TextUtils.isEmpty(hideId)) {
                        intent = new Intent();
                        intent.setClass(mActivity, ImageDataActivity.class);
                        intent.putExtra("id", hideId);
                        intent.putExtra("imageType", "review_info");
                        startActivity(intent);
                    }
                }

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

            case R.id.saveBtn:
                if (TextUtils.isEmpty(id)){
                    DialogUtils2.showAlertDialog("请选择初审人员",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(hideId)){
                    DialogUtils2.showAlertDialog("请上传回访资料",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(moneyMaxEt.getText().toString().trim())){
                    DialogUtils2.showAlertDialog("请填写回访内容",mActivity);
                    return;
                }
                if (TextUtils.isEmpty(contractStartDateTv.getText().toString().trim())){
                    DialogUtils2.showAlertDialog("请选择回访时间",mActivity);
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
                id = data.getStringExtra("id");
                select.setText(data.getStringExtra("name"));
            }
        }
    }
    String hideId =null;

    /**
     * 获取图片id
     */
    private void getHideId() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();


        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.getHideId, paramJo.toString());
        OkGo.<String>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<String>(mActivity, true) {
                    @Override
                    public void onSuccess(final Response<String> response) {


                        try {
                            String result = DesUtils.decrypt(response.body(), RSAandDES.privateKey);
                            JSONObject json = new JSONObject(result);
                            hideId = json.getJSONObject("auto_data").get("hideId").toString();

                        }catch (Exception e){}
                        if(!TextUtils.isEmpty(hideId)) {
                            Intent intent = new Intent();
                            intent.setClass(mActivity, ImageDataActivity.class);
                            intent.putExtra("id", hideId);
                            intent.putExtra("imageType", "review_info");
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }



    /**
     * 添加
     */
    private void save() {

//        mgrId--回访记录ID
//        projectId--项目ID
//        hideId--回访影像资料保存Id
//        returnTime--本次回访时间
//        reviewContent--本次回访内容
//        windControlAuditor--初审人员ID
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("mgrId",getIntent().getStringExtra("mgrId"));
            paramJo.put("projectId", reviewBean.getProjectId());
            paramJo.put("hideId", hideId);
            paramJo.put("returnTime", contractStartDateTv.getText().toString().trim());
            paramJo.put("reviewContent", moneyMaxEt.getText().toString().trim());
            paramJo.put("windControlAuditor", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.saveAppReviewMgrInfo, paramJo.toString());
        OkGo.<LzyResponse>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse>(mActivity, true) {
                    @Override
                    public void onSuccess(final Response<LzyResponse> response) {
                        DialogUtils2.showAlertDialog("提示", "保存成功", mActivity, new DialogInterface.OnClickListener() {
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
    private String id;


}
