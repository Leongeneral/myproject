package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.os.Handler;

import com.auto.chishan.manager.R;
import com.my.commonlibrary.base.BaseResultActivity;


/**
 * author : lyzhang3
 * date   : 2019/4/2110:23 PM
 * desc   :
 */
public class ChishanWelcom extends BaseResultActivity {

    @Override
    protected int setLayout() {
        return R.layout.skipads_layout;
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChishanWelcom.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }

}
