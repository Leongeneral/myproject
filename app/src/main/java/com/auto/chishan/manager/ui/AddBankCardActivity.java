package com.auto.chishan.manager.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.util.AppUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.adapter.SelectAdapter;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.bean.SelectBean;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.SPUtils;
import com.my.commonlibrary.view.poplayout.PopupLayout;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/13
 * desc:
 *
 * @author:DingZhixiang
 */
public class AddBankCardActivity extends BaseResultActivity {
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_card_number)
    EditText idCardNumber;
    @BindView(R.id.bank_card_number)
    EditText bankCardNumber;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verify_code)
    EditText verify_code;

    @BindView(R.id.bank_cvv2)
    EditText bank_cvv2;
    @BindView(R.id.bank_validdate)
    EditText bank_validdate;

    @BindView(R.id.get_verify_code)
    TextView get_verify_code;



    @BindView(R.id.contractTypeTv_kind)
    TextView bank_kind;
    @BindView(R.id.ll_cvv2)
    LinearLayout ll_cvv2;
    @BindView(R.id.ll_validdate)
    LinearLayout ll_validdate;

    private String[] bank;
    private int mBankIndex = 1;
    private boolean isChange;

    private Handler mHandler;

    private Toast mToast;
    @Override
    protected int setLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("添加银行卡");
        saveBtn.setText("提交");

        bank = getResources().getStringArray(R.array.bank_card_kind_select);


    }

    @Override
    protected void initData() {


        View typeView = View.inflate(mActivity, R.layout.include_onlyrv, null);
        selectBeans = new ArrayList<>();
        selectAdapter = new SelectAdapter(mActivity, selectBeans, R.layout.item_tv_center);

        popupLayout = PopupLayout.init(mActivity, typeView);
        popupLayout.setHeight(300, true);
        popupLayout.setUseRadius(false);
        selectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                switch (selectType) {
                    case 1:
//                        contractTypeTv.setText(selectAdapter.getData().get(position).getName());
//                        contractType = selectAdapter.getData().get(position).getCode();
                        break;
                    case 2:
//                        flowTv.setText(selectAdapter.getData().get(position).getLabel());
//                        flow = selectAdapter.getData().get(position).getValue();
                        break;
                }
                popupLayout.dismiss();
            }
        });
    }

    private int selectType = 1;
    private SelectAdapter selectAdapter;

    private List<SelectBean> selectBeans;

    private PopupLayout popupLayout;

    @OnClick({R.id.saveBtn,R.id.title_right,R.id.select_bank_kind,R.id.get_verify_code})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.saveBtn:
                submmit();
//            case R.id.select_bank:
//                selectType = 1;
//                getContractType();
//                selectAdapter.notifyDataSetChanged();
//                popupLayout.show();
                break;
            case R.id.get_verify_code:
                getVerifyCode();
                break;

            case R.id.select_bank_kind:
                showChoise();
                break;
        }

    }

    @Override
    public void finish() {
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        super.finish();
    }

    private void showChoise()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddBankCardActivity.this,R.style.MyDialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择类型");
        //    指定下拉列表的显示数据
        final String[] bank = getResources().getStringArray(R.array.bank_card_kind_select);
        //    设置一个下拉的列表选择项
        builder.setItems(bank, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(1 == which){
                    ll_cvv2.setVisibility(View.VISIBLE);
                    ll_validdate.setVisibility(View.VISIBLE);
                }else{
                    ll_cvv2.setVisibility(View.GONE);
                    ll_validdate.setVisibility(View.GONE);
                }
              bank_kind.setText(bank[which]);
            }
        });
        builder.show();
    }

    public  int countTime = -1;// 发送验证码的计时,默认为-1，表示并未开始计时


    /**
     * 获取验证码
     */
    private void getVerifyCode(){
        if(!AppUtil.checkOnline(this)) {
            showTips("当前没有网络");
            return;
        }
        String mName = name.getText().toString().trim();
        String idNumber = idCardNumber.getText().toString().trim();
        String card_number = bankCardNumber.getText().toString().trim();
        String phone_number = phoneNumber.getText().toString().trim();
        String bankkind = bank_kind.getText().toString().trim();
        String cvv2 = bank_cvv2.getText().toString().trim();
        String validdate = bank_validdate.getText().toString().trim();

        if(TextUtils.isEmpty(mName))
        {
            showTips("请输入持卡人姓名");
            return ;
        }
        if(TextUtils.isEmpty(idNumber))
        {
            showTips("请输入身份证号");
            return ;
        }
        if(TextUtils.isEmpty(card_number))
        {
            showTips("请输入银行卡号");
            return ;
        }
        if(TextUtils.isEmpty(phone_number))
        {
            showTips("预留手机号");
            return ;
        }
        if(TextUtils.isEmpty(bankkind))
        {
            showTips("选择银行卡类型");
            return ;
        }
         String type = bankkind.equals("借记卡") ? "00":"02";
        if("02".equals(type)){
            if(TextUtils.isEmpty(cvv2))
            {
                showTips("信用卡卡片安全码必填");
                return ;
            }
            if(TextUtils.isEmpty(validdate))
            {
                showTips("信用卡卡片有效期必填");
                return ;
            }
        }




                get_verify_code.setEnabled(false);
        if(mHandler == null)
            mHandler =  new Handler();

        Runnable rable = new Runnable() {
            public void run() {
                if(countTime >= 0)
                {
                    get_verify_code.setText(String.format("%s秒", countTime));
                    get_verify_code.setTextColor(getApplication().getResources().getColor(R.color.login_showtxt));
                    countTime = countTime -1;
                    mHandler.postDelayed(this, 1000);
                }
                //没有注册
                else if (countTime == -2) {

                    get_verify_code.setEnabled(true);
                    get_verify_code.setText(R.string.register_vercode_des);
                    get_verify_code.setTextColor(getApplication().getResources().getColor(R.color.color_primary_white));

                }
                else
                {
                    get_verify_code.setEnabled(true);
                    get_verify_code.setText(getString(R.string.get_verifycode_again));
                    get_verify_code.setTextColor(getApplication().getResources().getColor(R.color.color_primary_white));
                }
            }
        };
        countTime = 60;
        mHandler.postDelayed(rable, 0);


        OkGo.<String>post(Urls.FirstHost+ Urls.APPLY).tag(this)
                .params("customerId",SPUtils.getInstance(AddBankCardActivity.this).getUserId())
                .params("type",type)
                .params("bankNumber",card_number)
                .params("idCard",idNumber)
                .params("customerName",mName)
                .params("prePhone",phone_number)
                .params("validdate",validdate)
//
                .params("cvv2",cvv2)
                .execute(new DialogCallback<String>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (response.body() != null) {
                            try {
                                JSONObject json = new JSONObject(response.body().toString());
                                if(json.getInt("code") == 0){
                                    if(json.has("thpinfo")){
                                        thpinfo = json.optString("thpinfo");
                                    }
                                    showTips(json.optString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("*****",response.body());
//                            beans.addAll(response.body().getAuto_data().getMgrList());
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("*****",response.body());
                    }
                });
    }

    private void submmit(){
        if(!AppUtil.checkOnline(this)) {
            showTips("当前没有网络");
            return;
        }
        String mName = name.getText().toString().trim();
        String idNumber = idCardNumber.getText().toString().trim();
        String card_number = bankCardNumber.getText().toString().trim();
        String phone_number = phoneNumber.getText().toString().trim();
        String bankkind = bank_kind.getText().toString().trim();
        String verifyCode = verify_code.getText().toString().trim();

        String cvv2 = bank_cvv2.getText().toString().trim();
        String validdate = bank_validdate.getText().toString().trim();

        if(TextUtils.isEmpty(mName))
        {
            showTips("请输入持卡人姓名");
            return ;
        }
        if(TextUtils.isEmpty(idNumber))
        {
            showTips("请输入身份证号");
            return ;
        }
        if(TextUtils.isEmpty(card_number))
        {
            showTips("请输入银行卡号");
            return ;
        }
        if(TextUtils.isEmpty(phone_number))
        {
            showTips("预留手机号");
            return ;
        }
        if(TextUtils.isEmpty(bankkind))
        {
            showTips("选择银行卡类型");
            return ;
        }

        if(TextUtils.isEmpty(verifyCode))
        {
            showTips("输入验证码");
            return ;
        }
        String type = bankkind.equals("借记卡") ? "00":"02";
        if("02".equals(type)){
            if(TextUtils.isEmpty(cvv2))
            {
                showTips("信用卡卡片安全码必填");
                return ;
            }
            if(TextUtils.isEmpty(validdate))
            {
                showTips("信用卡卡片有效期必填");
                return ;
            }
        }


        OkGo.<String>post(Urls.FirstHost+ Urls.SIGNCONFIRM).tag(this)
                .params("customerId",SPUtils.getInstance(AddBankCardActivity.this).getUserId())
                .params("type",type)
                .params("bankNumber",card_number)
                .params("idCard",idNumber)
                .params("customerName",mName)
                .params("prePhone",phone_number)
                .params("identifyCode",verifyCode)
                .params("thpinfo",thpinfo)
                .params("validdate",validdate)
//
                .params("cvv2",cvv2)
                .execute(new DialogCallback<String>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (response.body() != null) {
                            Log.e("*****",response.body());
                            try {
                                JSONObject json = new JSONObject(response.body());
                                if(json.getInt("code") == 0){
                                    if(json.has("message")){
                                        showTips(json.optString("message"));

                                    }
                                }

                                if(json.getInt("code") == 1){
                                    showTips(json.optString("message"));
                                    if("签约成功,流程完成".equals(json.optString("message"))){
                                        finish();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("*****",response.body());
                    }
                });
    }

    private String thpinfo = "";

    private void showTips(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mToast == null ){
                    if(isFinishing()){
                        return;
                    }
                    mToast = Toast.makeText(AddBankCardActivity.this, str, Toast.LENGTH_SHORT);
                }else{
                    mToast.setText(str);
                }
                mToast.show();
            }
        });


    }

}
