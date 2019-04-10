package com.auto.chishan.manager.adapter;

import android.content.Context;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.ImageDataBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Date: 2018/12/11
 * desc:
 *
 * @author:DingZhixiang
 */
public class ImageDataAdapter extends SuperAdapter<ImageDataBean> {
    public ImageDataAdapter(Context context, List<ImageDataBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ImageDataBean item) {
        holder.setText(R.id.nameTv,item.getName());
    }
}
