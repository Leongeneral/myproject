package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.InstallmentBean;
import com.auto.chishan.manager.bean.InstallmentPlainBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * author : lyzhang3
 * date   : 2019/3/1510:39 AM
 * desc   :
 */
public class InstallmentPlainAdapter extends SuperAdapter<InstallmentPlainBean> {
    private Activity mContext;
    public InstallmentPlainAdapter(Activity context, List<InstallmentPlainBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final InstallmentPlainBean item) {

        holder.setText(R.id.phase,item.getBatchPaymentPeriods());
        holder.setText(R.id.date,item.getPayDate());

        holder.setText(R.id.principal,item.getPayPrincipal());

        holder.setText(R.id.interest,item.getPayInterest());

        holder.setText(R.id.total,Float.parseFloat(item.getPayPrincipal()) + Float.parseFloat(item.getPayInterest())+"" );


        String status = item.getRepaymentState();
        if("0".equals(status)){
            holder.setText(R.id.status,"待还");
        }else if("1".equals(status)){
            holder.setText(R.id.status,"已还");
        }else{
            holder.setText(R.id.status,"逾期");

        }
    }











}
