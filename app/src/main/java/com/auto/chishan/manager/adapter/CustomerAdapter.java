package com.auto.chishan.manager.adapter;

import android.content.Context;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.CustomerBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class CustomerAdapter extends SuperAdapter<CustomerBean> {
    public CustomerAdapter(Context context, List<CustomerBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CustomerBean item) {
        holder.setText(R.id.customerNameTv,"客户名称："+item.getCustomerName());
        holder.setText(R.id.certificateNumTv,item.getCertificateNum());
        holder.setText(R.id.phoneNumTv,item.getPhoneNum());
    }
}
