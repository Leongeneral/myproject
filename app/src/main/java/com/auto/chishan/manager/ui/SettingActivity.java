package com.auto.chishan.manager.ui;


import android.view.View;

import com.auto.chishan.manager.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseActivity;
import com.my.commonlibrary.http.callback.DialogCallback;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("设置");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_version,R.id.login_out})
    public void onViewClick(View v){
        switch (v.getId()){
            case R.id.login_out:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.rl_version:
                getVersion();
                break;
        }
    }

    private void getVersion() {
        OkGo.<String>post(Urls.FirstHost+Urls.SecondHost).tag(this)
                .params("type", 1)
//                .params("certificatesNumber",name)
//                .params("password",key)
                .execute(new DialogCallback<String>(mActivity,true) {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                }
                );
    }

}
