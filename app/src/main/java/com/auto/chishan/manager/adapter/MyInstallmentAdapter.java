package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.InstallmentBean;
import com.auto.chishan.manager.bean.ReviewMgrMaintainBean;
import com.auto.chishan.manager.ui.InstallmentPlainActivity;
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
public class MyInstallmentAdapter extends SuperAdapter<InstallmentBean> {
    private Activity mContext;
    public MyInstallmentAdapter(Activity context, List<InstallmentBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final InstallmentBean item) {

        holder.setText(R.id.money,"¥"+item.getAmount());
        holder.setText(R.id.house,item.getCommunity() +"    "+item.getNumber());




        holder.setOnClickListener(R.id.plain, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,InstallmentPlainActivity.class);
                intent.putExtra("projectId",item.getId());
                mContext.startActivity(intent);

            }
        });

        holder.setOnClickListener(R.id.refund, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
