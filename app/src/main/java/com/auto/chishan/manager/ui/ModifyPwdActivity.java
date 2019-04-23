package com.auto.chishan.manager.ui;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auto.chishan.manager.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseActivity;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyPwdActivity extends BaseActivity {

    @BindView(R.id.old_pwd)
    EditText oldPwd;
    @BindView(R.id.new_pwd)
    EditText newPwd;
    @BindView(R.id.new_pwd_sure)
    EditText newPwdSure;
    @BindView(R.id.saveBtn)
    Button saveBtn;

    @Override
    protected int setLayout() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("修改密码");
        saveBtn.setText("确认");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.saveBtn)
    public void onViewClick() {
        modify();

    }

    private void modify() {
        String old = oldPwd.getText().toString().trim();
        String ne = newPwd.getText().toString().trim();
        String newSure = newPwdSure.getText().toString().trim();
        if(TextUtils.isEmpty(old) ||  TextUtils.isEmpty(ne) || TextUtils.isEmpty(newSure)){
            Toast.makeText(this,"输入框内不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!ne.equals(newSure)){
            Toast.makeText(this,"新密码输入不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>post(Urls.FirstHost + Urls.MODIFYPWD).tag(this)
                .params("certificatesNumber", SPUtils.getInstance(this).getLoginName())
                .params("oldPassword",old)
                .params("password",newSure)
//                .params("password",key)
                .execute(new DialogCallback<String>(mActivity, true) {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 try {
                                     JSONObject json = new JSONObject(response.body());
                                     if(json.has("code")&& json.optInt("code") == 1){

                                     }else{

                                     }

                                     if(json.has("message")){
                                         Toast.makeText(ModifyPwdActivity.this,json.optString("message"),Toast.LENGTH_SHORT).show();

                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }

                             }
                         }
                );
    }

}

