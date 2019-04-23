package com.auto.chishan.manager.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auto.chishan.manager.R;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseResultActivity;
import com.my.commonlibrary.bean.UserInfoBean;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseResultActivity {
    @BindView(R.id.UI_login_nameEdit)
    EditText UILoginNameEdit;
    @BindView(R.id.UI_login_kayEdit)
    EditText UILoginKayEdit;
    @BindView(R.id.saveBtn)
    Button saveBtn;
    @BindView(R.id.versionTv)
    TextView versionTv;

    private SPUtils spUtils;

    @Override
    protected int setLayout() {
        return R.layout.ui_login;
    }

    @Override
    protected void initData() {
        saveBtn.setText("登录");
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            versionTv.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        spUtils = SPUtils.getInstance(mActivity);
        UILoginNameEdit.setText(spUtils.getLoginName());
        UILoginKayEdit.setText(spUtils.getPwd());
        requestStorage();
    }

    private void requestStorage() {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("帮助", "当前应用需要存储权限\n" + " \n" + " 请点击 \"设置\"-\"权限\"-打开所需权限。", "取消", "设置");
        PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                Toast.makeText(mActivity, "请允许访问存储权限", Toast.LENGTH_LONG).show();
            }
        }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, true, tip);
    }

    @OnClick({R.id.saveBtn,R.id.forget})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.saveBtn:
                initHttpLogin(UILoginNameEdit.getText().toString().trim(), UILoginKayEdit.getText()
                        .toString()
                        .trim());
                break;
            case R.id.forget:
                break;
        }

    }

    private void initHttpLogin(final String name, final String key) {
//        Intent intent = new Intent(mActivity,MainActivity.class);
//                                startActivity(intent);
//                                finish();
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, name, key, Urls.LOGIN, null);
        OkGo.<LzyResponse<UserInfoBean>>post(Urls.FirstHost+Urls.SecondHost + Urls.LOGIN).tag(this)
                .params("type", 1)
                .params("certificatesNumber",name)
                .params("password",key)
                .execute(new DialogCallback<LzyResponse<UserInfoBean>>(mActivity,true) {
                             @Override
                             public void onSuccess(Response<LzyResponse<UserInfoBean>> response) {
                                 UserInfoBean userInfoBean = response.body().getAuto_data();
                                 spUtils.saveLoginName(name);
                                 spUtils.saveName(userInfoBean.getLoginName());
                                 spUtils.savePwd(key);
                                 spUtils.saveToken(userInfoBean.getToken());
                                 spUtils.saveUserId(userInfoBean.getCustomerId());
                                 Intent intent = new Intent(mActivity,MainActivity.class);
                                 startActivity(intent);
                                 finish();

                             }
                         }
                );
    }

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
