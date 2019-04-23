package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.NoticeBean;
import com.auto.chishan.manager.bean.InstallmentBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseActivity;
import com.my.commonlibrary.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.user_name)
    TextView mUserName;

    @BindView(R.id.notice)
    TextView mNotice;
    @BindView(R.id.time)
    TextView mNoticeTime;
    @BindView(R.id.rl_notice)
    RelativeLayout rl_notice;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mUserName.setText(SPUtils.getInstance(MainActivity.this).getName());
    }

    @Override
    protected void initData() {
        OkGo.<String>post(Urls.FirstHost+Urls.SecondHost1 + Urls.CUSTOMER).tag(this)
                .params("customerId", SPUtils.getInstance(MainActivity.this).getUserId())
//                .execute(new DialogCallback<LzyResponse<UserInfoBean>>(mActivity,true) {
//                             @Override
//                             public void onSuccess(Response<LzyResponse<UserInfoBean>> response) {
//
//
//                             }
//                         }
//                );
        .execute(new StringCallback(){
            @Override
            public void onSuccess(Response<String> response) {
//                Log.e("****",response.body());
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });


        OkGo.<String>get(Urls.FirstHost + Urls.QUERYNOTICE).tag(this)
                .params("customerId", SPUtils.getInstance(MainActivity.this).getUserId())
//                .execute(new DialogCallback<LzyResponse<UserInfoBean>>(mActivity,true) {
//                             @Override
//                             public void onSuccess(Response<LzyResponse<UserInfoBean>> response) {
//
//
//                             }
//                         }
//                );
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body();
                        ArrayList<NoticeBean> beans = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(result);


                            if(json.getInt("code") == 1){
                                JSONArray array = json.getJSONObject("data").getJSONArray("expireList");
                                for(int i =0; i<array.length();i++){

                                    JSONObject js = array.getJSONObject(i);
                                    Gson gson  = new GsonBuilder().create();
                                    NoticeBean bean = gson.fromJson(js.toString(),NoticeBean.class);
                                    beans.add(bean);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                Log.e("****",response.body());
                        if(beans.size() != 0){
//                            mNotice.setText("共有"+beans.size()+"条通知，点击查看");
                            mNotice.setText(beans.get(0).getContent());
                            mNoticeTime.setText(beans.get(0).getUpdateDate());
                        }else{
                            rl_notice.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });




    }

    @OnClick({R.id.setting,R.id.refund,R.id.installment,R.id.rl_bank_card,R.id.rl_notice})
    public void onViewClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.setting://设置
                intent = new Intent(this,SettingActivity.class);
                startActivityForResult(intent,1001);
                break;
            case R.id.refund://还款
                intent = new Intent(this,RefoundActivity.class);
                startActivity(intent);
                break;
            case R.id.installment://我的分期
                intent = new Intent(this,MyInstallmentActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_bank_card://银行卡
                intent = new Intent(this,MyBankcardActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_notice:
                intent = new Intent(this,MyNoticeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 && resultCode == RESULT_OK){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
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

