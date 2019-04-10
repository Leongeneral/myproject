package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ProjectManageBean;
import com.auto.chishan.manager.ui.project.UnderwriteLookActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class UnderwriteManageAdapter extends SuperAdapter<ProjectManageBean> {
    private Activity mContext;
    public UnderwriteManageAdapter(Activity context, List<ProjectManageBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final ProjectManageBean item) {
        holder.setText(R.id.busiCodeTv,"项目编号："+item.getBusiCode());
        holder.setText(R.id.customerNameTv,item.getCustomerName());
        holder.setText(R.id.projectNameTv,item.getProjectName());
        holder.setText(R.id.contractNoTv,item.getContractNo());
        holder.setText(R.id.moneyTv,item.getInterestRate());

        holder.setText(R.id.projectTypeTv,"项目类型:"+item.getInterestRate());
        holder.setText(R.id.productTypeTv,"产品类型:"+item.getInterestRate());
        holder.setText(R.id.stateTv,"合同状态:"+item.getInterestRate());


        holder.setOnClickListener(R.id.traceTv,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UnderwriteLookActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("projectId",item.getProjectId());
                mContext.startActivityForResult(intent,1001);
            }
        });
    }
}
