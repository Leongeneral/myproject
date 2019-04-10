package com.auto.chishan.manager.adapter;

import android.content.Context;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.UnderwriteMaintainBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 * @author:DingZhixiang
 */
public class UnderwriteMaintainAdapter extends SuperAdapter<UnderwriteMaintainBean> {
    public UnderwriteMaintainAdapter(Context context, List<UnderwriteMaintainBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, UnderwriteMaintainBean item) {
        holder.setText(R.id.projectNameTv,"项目名称："+item.getProjectName());
        holder.setText(R.id.customerNameTv,item.getCustomerName());
        holder.setText(R.id.contractNoTv,item.getContractNo());
        holder.setText(R.id.underwriteStateTv,item.getContractNo());
        holder.setText(R.id.contractStartDateTv,"所属机构："+item.getContractStartDate());
        holder.setText(R.id.auditStateTv,"完成状态："+item.getContractState());
    }
}
