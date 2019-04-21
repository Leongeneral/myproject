package com.auto.chishan.manager.ui;

import android.util.Log;
import android.view.View;

import com.auto.chishan.manager.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.base.BaseActivity;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.SPUtils;

/**
 * author : lyzhang3
 * date   : 2019/4/811:55 PM
 * desc   :{
 * 	"code": 1,
 * 	"message": "",
 * 	"data": {
 * 		"amount": 17998.23,
 * 		"subject": "第1期还款",
 * 		"appname": "",
 * 		"paytype": "",
 * 		"reqsn": "738c7745bc574b7ea4c1354d705eeec0",
 * 		"initialState": "",
 * 		"apppackage": "",
 * 		"orderid": "738c7745bc574b7ea4c1354d705eeec0",
 * 		"cusip": "",
 * 		"apptype": "",
 * 		"agreeid": ""
 *        }
 * }
 */
public class RefoundActivity extends BaseActivity {
    @Override
    protected int setLayout() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("还款");

    }

    @Override
    protected void initData() {
        SPUtils spUtils = SPUtils.getInstance(mActivity);
        OkGo.<String>post(Urls.FirstHost+Urls.SecondHost1+ Urls.PAY).tag(this)
                .params("projectId", getIntent().getStringExtra("projectId"))
                .params("customerId",spUtils.getUserId())
                .execute(new DialogCallback<String>(mActivity, false) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (response.body() != null) {
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
}
