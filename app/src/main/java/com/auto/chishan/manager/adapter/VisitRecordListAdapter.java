package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ReviewMgrMaintainBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.http.Utils.LzyResponse;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.DialogUtils2;
import com.my.commonlibrary.utils.RSAandDES;
import com.my.commonlibrary.utils.SPUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * author : lyzhang3
 * date   : 2019/3/1510:39 AM
 * desc   :
 */
public class VisitRecordListAdapter extends SuperAdapter<ReviewMgrMaintainBean> {
    private Activity mContext;
    public VisitRecordListAdapter(Activity context, List<ReviewMgrMaintainBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final ReviewMgrMaintainBean item) {
        holder.setVisibility(R.id.tv4,View.GONE);
        holder.setVisibility(R.id.tv5,View.GONE);
        holder.setVisibility(R.id.tv4_des,View.GONE);
        holder.setVisibility(R.id.tv5_des,View.GONE);

        holder.setText(R.id.sendTv,"跟踪");
        holder.setText(R.id.traceTv,"发送");

        holder.setText(R.id.busiCodeTv,"项目编号："+item.getBusiCode());
        holder.setText(R.id.tv1,"客户名称");
        holder.setText(R.id.tv1_des,item.getCustomerName());

        holder.setText(R.id.tv2,"回访时间");
        holder.setText(R.id.tv2_des,item.getReturnTime());

        holder.setText(R.id.tv3,"回访状态");
        holder.setText(R.id.tv3_des,item.getReviewState());

        holder.setText(R.id.productNameTv,"产品类型："+ item.getProductName());
        holder.setText(R.id.auditStateTv,"项目状态："+ item.getProjectState());

        holder.setOnClickListener(R.id.traceTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               send(item);
            }
        });
    }

    private void send(ReviewMgrMaintainBean item){
        SPUtils spUtils = SPUtils.getInstance(getContext());
        JSONObject paramJo = new JSONObject();
        try {
            paramJo.put("mgrId",item.getMgrId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String hexStr = RSAandDES.getRSA("Android", RSAandDES.privateKey, spUtils.getLoginName(), spUtils
                .getPwd(), Urls.checkReviewSendFlag, paramJo.toString());
        OkGo.<LzyResponse>post(Urls.FirstHost + Urls.SecondHost).tag(this)
                .params("params", hexStr)
                .execute(new DialogCallback<LzyResponse>(mContext, true) {
                    @Override
                    public void onSuccess(final Response<LzyResponse> response) {
                        DialogUtils2.showAlertDialog("提示", "发送成功", getContext(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                    }

                    @Override
                    public void onError(Response<LzyResponse> response) {
                        super.onError(response);
                    }
                });
    }


}
