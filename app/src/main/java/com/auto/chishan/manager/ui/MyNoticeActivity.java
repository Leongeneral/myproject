package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.adapter.NoticeBean;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : lyzhang3
 * date   : 2019/4/232:29 PM
 * desc   :
 */
public class MyNoticeActivity extends BaseActivity {
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.notice_refund)
    TextView noticeRefund;
    @BindView(R.id.notice_refund_time)
    TextView noticeRefundTime;
    @BindView(R.id.tv_overtime)
    TextView tvOvertime;
    @BindView(R.id.tv_overtime_content)
    TextView tvOvertimeContent;
    @BindView(R.id.tv_overtime_time)
    TextView tvOvertimeTime;
    @BindView(R.id.tv_expire)
    TextView tvExpire;
    @BindView(R.id.tv_expire_content)
    TextView tvExpireContent;
    @BindView(R.id.tv_expire_time)
    TextView tvExpireTime;

    @Override
    protected int setLayout() {
        return R.layout.activity_my_notice;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("我的通知");

    }

    @Override
    protected void initData() {

        OkGo.<String>get(Urls.FirstHost + Urls.MYNOTICE).tag(this)
                .params("customerId", SPUtils.getInstance(MyNoticeActivity.this).getUserId())
                .params("remindType",1)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body();
                        Log.e("****",response.body());
                        ArrayList<NoticeBean> beans = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(result);


                            if(json.getInt("code") == 1){
                                JSONArray array = json.getJSONObject("data").getJSONArray("arrData");
                                if(array.length() !=0) {
                                    JSONObject js = array.getJSONObject(0);
                                    tvExpireContent.setText(js.optString("content"));
                                    tvExpireTime.setText(js.optString("updateDate"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


        OkGo.<String>get(Urls.FirstHost + Urls.MYNOTICE).tag(this)
                .params("customerId", SPUtils.getInstance(MyNoticeActivity.this).getUserId())
                .params("remindType",2)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body();
                        Log.e("****",response.body());
                        ArrayList<NoticeBean> beans = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(result);


                            if(json.getInt("code") == 1){
                                JSONArray array = json.getJSONObject("data").getJSONArray("arrData");
                                if(array.length() !=0) {
                                    JSONObject js = array.getJSONObject(0);
                                    tvOvertimeContent.setText(js.optString("content"));
                                    tvOvertime.setText(js.optString("updateDate"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


        OkGo.<String>get(Urls.FirstHost + Urls.MYNOTICE).tag(this)
                .params("customerId", SPUtils.getInstance(MyNoticeActivity.this).getUserId())
                .params("remindType",3)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body();
                        Log.e("****",response.body());
                        ArrayList<NoticeBean> beans = new ArrayList<>();
                        try {
                            JSONObject json = new JSONObject(result);


                            if(json.getInt("code") == 1){
                                JSONArray array = json.getJSONObject("data").getJSONArray("arrData");
                                if(array.length() !=0) {
                                    JSONObject js = array.getJSONObject(0);
                                    noticeRefund.setText(js.optString("content"));
                                    noticeRefundTime.setText(js.optString("updateDate"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }


    @OnClick({R.id.tv_refund, R.id.tv_overtime, R.id.tv_expire})
    public void onViewClicked(View view) {
        Intent intent = new Intent(MyNoticeActivity.this, NoticeDetailActivity.class);
        switch (view.getId()) {
            case R.id.tv_refund:
                intent.putExtra("name","还款通知");
                intent.putExtra("type",3);
                break;
            case R.id.tv_overtime:
                intent.putExtra("name","逾期通知");
                intent.putExtra("type",2);
                break;
            case R.id.tv_expire:
                intent.putExtra("name","到期通知");
                intent.putExtra("type",1);
                break;
        }
        startActivity(intent);
    }
}
