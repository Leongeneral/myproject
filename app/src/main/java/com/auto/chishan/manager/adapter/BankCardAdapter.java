package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.BankCardBean;
import com.auto.chishan.manager.bean.InstallmentBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * author : lyzhang3
 * date   : 2019/3/1510:39 AM
 * desc   :
 */
public class BankCardAdapter extends SuperAdapter<BankCardBean> {
    private Activity mContext;
    public BankCardAdapter(Activity context, List<BankCardBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final BankCardBean item) {

        holder.setText(R.id.money,"¥"+item.getAmount());
        holder.setText(R.id.house,item.getCommunity() +"    "+item.getNumber());




        holder.setOnClickListener(R.id.plain, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.setOnClickListener(R.id.refund, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
