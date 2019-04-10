package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.ui.home.ExpirationReminderActivity;
import com.my.commonlibrary.base.BaseFragment;
import com.my.commonlibrary.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2019/3/4
 * desc:
 *
 * @author:DingZhixiang
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.userNameTv)
    TextView userNameTv;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        userNameTv.setText(SPUtils.getInstance(mActivity).getName());
    }

    @OnClick({R.id.expirationReminderLayout, R.id.overdueReminderLayout})
    public void onViewClicked(View view) {
        Intent intent = new Intent(mActivity, ExpirationReminderActivity.class);
        switch (view.getId()) {
            case R.id.expirationReminderLayout:
                //到期提醒
                intent.putExtra("detailType", "expire");
                break;
            case R.id.overdueReminderLayout:
                //逾期提醒
                intent.putExtra("detailType", "overdue");
                break;
        }
        startActivity(intent);
    }

}
