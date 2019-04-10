package com.auto.chishan.manager.ui.afterloan;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ReviewDetailBean;
import com.auto.chishan.manager.bean.ReviewMgrMaintainBean;
import com.auto.chishan.manager.ui.customer.ImageDataActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * author : lyzhang3
 * date   : 2019/3/232:14 PM
 * desc   :
 */
public class VisitRecordDetailActivity extends VisitAddRecordActivity {

    private ReviewMgrMaintainBean maintainBean;

    @Override
    protected void initData() {
        maintainBean = getIntent().getParcelableExtra("ReviewMgrMaintainBean");
        project_number.setText(maintainBean.getBusiCode());
        customer_name.setText(maintainBean.getCustomerName());
        contractMoneyEt.setText(maintainBean.getProjectState());
        setViewGone(R.id.review_arrow,R.id.iv3,R.id.iv_location);

        saveBtn.setVisibility(View.GONE);




        getReviewData();
    }

    /**
     * 获取回访详情
     */
    private void getReviewData() {

        SPUtils spUtils = SPUtils.getInstance(mActivity);
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("mgrId",maintainBean.getMgrId());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.showReviewMgrInfo, paramJo.toString());
        OkGo.<LzyResponse<ReviewDetailBean>>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse<ReviewDetailBean>>(mActivity, true) {
                    @Override
                    public void onSuccess(final Response<LzyResponse<ReviewDetailBean>> response) {
                        ReviewDetailBean detailBean = response.body().getAuto_data();
                        select.setText(detailBean.getWindControlAuditorName());
                        contractStartDateTv.setText(detailBean.getReturnTime());
                        moneyMaxEt.setText(detailBean.getReviewContent());
                        moneyMaxEt.setEnabled(false);

                        mAddress.setVisibility(View.VISIBLE);
                        mAddress.setText(detailBean.getCurAddress());
                        hideId = detailBean.getHideId();
                        upload.setText("查看");


                    }

                    @Override
                    public void onError(Response<LzyResponse<ReviewDetailBean>> response) {
                        super.onError(response);
                    }
                });
    }

    private void setViewGone(int... id){
        for(int i:id){
            findViewById(i).setBackground(null);
        }

    }



    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_image_add:
                if (!TextUtils.isEmpty(hideId)) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, ImageDataActivity.class);
                    intent.putExtra("id", hideId);
                    intent.putExtra("imageType", "review_info");
                    startActivity(intent);
                    }

                break;
        }
    }



}
