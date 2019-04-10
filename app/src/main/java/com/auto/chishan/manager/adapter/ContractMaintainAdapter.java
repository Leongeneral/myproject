package com.auto.chishan.manager.adapter;

import android.content.Context;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ContractMaintainBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 * @author:DingZhixiang
 */
public class ContractMaintainAdapter extends SuperAdapter<ContractMaintainBean> {
    public ContractMaintainAdapter(Context context, List<ContractMaintainBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ContractMaintainBean item) {
        holder.setText(R.id.projectNameTv,"项目名称："+item.getProjectName());
        holder.setText(R.id.customerNameTv,item.getCustomerName());
        holder.setText(R.id.contractNoTv,item.getContractNo());
        holder.setText(R.id.contractAmountTv,item.getContractAmount());
        holder.setText(R.id.contractStartDateTv,"合同日期："+item.getContractStartDate()+"至"+item.getContractEndDate());
        holder.setText(R.id.auditStateTv,"状态："+item.getContractState());
    }
}
