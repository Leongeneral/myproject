package com.auto.chishan.manager.adapter;

import android.app.Activity;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ContractBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class ContractAdapter extends SuperAdapter<ContractBean> {
    public ContractAdapter(Activity context, List<ContractBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final ContractBean item) {
        holder.setText(R.id.contractNoTv,item.getContractNo());
        holder.setText(R.id.contractNameTv,item.getContractName());
        holder.setText(R.id.contractTxtTv,item.getContractTxt());
        holder.setText(R.id.contractNumTv,item.getContractNum());
    }
}
