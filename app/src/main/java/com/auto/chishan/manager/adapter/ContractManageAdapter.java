package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ProjectManageBean;
import com.auto.chishan.manager.ui.project.ContractLookActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class ContractManageAdapter extends SuperAdapter<ProjectManageBean> {
    private Activity mContext;
    public ContractManageAdapter(Activity context, List<ProjectManageBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final ProjectManageBean item) {
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
        holder.setText(R.id.auditStateTv,"合同份数："+item.getContractCount()+"份" );
        holder.setVisibility(R.id.sendTv, View.GONE);
        holder.setVisibility(R.id.traceTv, View.VISIBLE);
        holder.setText(R.id.traceTv,"添加合同");
        holder.setOnClickListener(R.id.traceTv,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContractLookActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("projectId",item.getProjectId());
                mContext.startActivityForResult(intent,1001);
            }
        });
    }
}
