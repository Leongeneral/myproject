package com.auto.chishan.manager.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ProjectManageBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class ProjectManageAdapter extends SuperAdapter<ProjectManageBean> {
    public ProjectManageAdapter(Context context, List<ProjectManageBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ProjectManageBean item) {
        holder.setText(R.id.busiCodeTv,"项目编号："+item.getBusiCode());
        holder.setText(R.id.customerNameTv,item.getCustomerName());
        holder.setText(R.id.projectNameTv,item.getProjectName());
        holder.setText(R.id.financingAmountTv,item.getFinancingAmount());
        holder.setText(R.id.interestRateTv,item.getInterestRate());
        holder.setText(R.id.timeLimitTv,item.getTimeLimit());
        holder.setText(R.id.productNameTv,"产品类型："+item.getProductName());
        holder.setText(R.id.auditStateTv,"状态："+item.getAuditState());
        if(item.getBusiType().equals("guarantee")){
            //担保没有利率
            holder.setVisibility(R.id.rateTitleTv, View.GONE);
            holder.setVisibility(R.id.interestRateTv, View.GONE);
        }
        if(!TextUtils.isEmpty(item.getContractCount())){
            //合同
            holder.setText(R.id.auditStateTv,"合同份数："+item.getContractCount()+"份" );
        }else{
            //项目
            holder.setText(R.id.auditStateTv,"状态："+item.getAuditState());
        }
    }
}
