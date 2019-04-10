package com.auto.chishan.manager.adapter;

import android.content.Context;
import android.view.View;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ExpirationReminderBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class ExpirationReminderAdapter extends SuperAdapter<ExpirationReminderBean> {
    //expire 到期 overdue 逾期
    private String detailType;
    public ExpirationReminderAdapter(Context context,String detailType, List<ExpirationReminderBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.detailType = detailType;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ExpirationReminderBean item) {
        if(detailType.equals("expire")){
            //到期
            holder.setVisibility(R.id.tv4, View.VISIBLE);
            holder.setVisibility(R.id.contractExpiryDateTv, View.VISIBLE);
            holder.setVisibility(R.id.tv5, View.GONE);
            holder.setVisibility(R.id.tv6, View.GONE);
            holder.setVisibility(R.id.overdueTypeTv, View.GONE);
            holder.setVisibility(R.id.overdueDayNumTv, View.GONE);
        }else{
            //逾期
            holder.setVisibility(R.id.tv5, View.VISIBLE);
            holder.setVisibility(R.id.tv6, View.VISIBLE);
            holder.setVisibility(R.id.overdueTypeTv, View.VISIBLE);
            holder.setVisibility(R.id.overdueDayNumTv, View.VISIBLE);
            holder.setVisibility(R.id.tv4, View.GONE);
            holder.setVisibility(R.id.contractExpiryDateTv, View.GONE);
        }
    }
}
