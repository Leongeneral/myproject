package com.my.commonlibrary.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.my.commonlibrary.R;
import com.my.commonlibrary.bean.SelectBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class SelectAdapter extends SuperAdapter<SelectBean> {
    public SelectAdapter(Context context, List<SelectBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SelectBean item) {
        if(TextUtils.isEmpty(item.getLabel())){
            holder.setText(R.id.productNameTv, item.getName());
        }else{
            holder.setText(R.id.productNameTv, item.getLabel());
        }
    }
}