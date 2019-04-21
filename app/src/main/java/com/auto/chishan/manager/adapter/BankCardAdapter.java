package com.auto.chishan.manager.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.bean.BankCardBean;
import com.auto.chishan.manager.bean.InstallmentBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.my.commonlibrary.Constant.Urls;
import com.my.commonlibrary.http.callback.DialogCallback;
import com.my.commonlibrary.utils.SPUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public interface NotifyData{
        void notifyData();
    }

    public  NotifyData notifyData;

    public void setNotifyData(NotifyData notifyData){
        this.notifyData = notifyData;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final BankCardBean item) {

        holder.setText(R.id.bank_card_number,item.getBankNumber());




        holder.setOnClickListener(R.id.unbind, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unbind(item.getAgreeId());

            }
        });


    }


    private void unbind(String agreeId) {
        SPUtils spUtils = SPUtils.getInstance(getContext());
        OkGo.<String>post(Urls.FirstHost+ Urls.UNBINDBANKCARD).tag(this)
                .params("customerId", spUtils.getUserId())
                .params("agreeid", agreeId)
                .execute(new DialogCallback<String>(mContext, false) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (response.body() != null) {
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response.body());
                                Toast.makeText(mContext,json.optString("message"),Toast.LENGTH_SHORT).show();
                                Log.e("****",response.body());

                                notifyData.notifyData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }


}
