package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.BankCardBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.SPUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * author : lyzhang3
 * date   : 2019/3/1510:39 AM
 * desc   :
 */
public class NoticeDetailAdapter extends SuperAdapter<NoticeBean> {
    private Activity mContext;
    public NoticeDetailAdapter(Activity context, List<NoticeBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    public interface NotifyData{
        void notifyData();
    }

    public  NotifyData notifyData;

    public void setNotifyData(NotifyData notifyData){
        this.notifyData = notifyData;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final NoticeBean item) {

        holder.setText(R.id.time,item.getUpdateDate());
        holder.setText(R.id.content,item.getContent());







    }


}
