package com.auto.chishan.manager.ui;

import android.view.View;

import com.auto.chishan.manager.R;
import com.my.commonlibrary.base.BaseActivity;

/**
 * author : lyzhang3
 * date   : 2019/4/811:55 PM
 * desc   :
 */
public class RefoundActivity extends BaseActivity {
    @Override
    protected int setLayout() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView() {
        initTitleBar();
        titlerightTv.setVisibility(View.GONE);
        titleContentTv.setText("还款");

    }

    @Override
    protected void initData() {

    }
}
