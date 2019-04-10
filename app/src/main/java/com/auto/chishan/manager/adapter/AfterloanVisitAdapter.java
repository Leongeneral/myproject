package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ProjectManagerReviewBean;
import com.auto.chishan.manager.ui.afterloan.VisitAddRecordActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * author : lyzhang3
 * date   : 2019/3/1510:39 AM
 * desc   :
 */
public class AfterloanVisitAdapter extends SuperAdapter<ProjectManagerReviewBean> {

    private Activity mContext;
    public AfterloanVisitAdapter(Activity context, List<ProjectManagerReviewBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,final ProjectManagerReviewBean item) {
        holder.setVisibility(R.id.traceTv,View.GONE);
        holder.setVisibility(R.id.tv4,View.GONE);
        holder.setVisibility(R.id.tv5,View.GONE);
        holder.setVisibility(R.id.tv4_des,View.GONE);
        holder.setVisibility(R.id.tv5_des,View.GONE);


        holder.setText(R.id.sendTv,"回访");

        holder.setText(R.id.busiCodeTv,"项目编号："+item.getBusiCode());
        holder.setText(R.id.tv1,"客户名称");
        holder.setText(R.id.tv2,"项目金额（元)");
        holder.setText(R.id.tv3,"期限（月)");
        holder.setText(R.id.tv1_des,item.getCustomerName());
        holder.setText(R.id.tv2_des,item.getFinancingAmount());
        holder.setText(R.id.tv3_des,item.getTimeLimit());
        holder.setText(R.id.productNameTv,"产品类型："+item.getProductName());
        holder.setText(R.id.auditStateTv,"项目状态："+item.getProjectState());


        holder.setOnClickListener(R.id.sendTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VisitAddRecordActivity.class);
                intent.putExtra("ProjectManagerReviewBean",item);
                mContext.startActivity(intent);
            }
        });
    }
}
