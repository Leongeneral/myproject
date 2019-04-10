package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.ui.project.ContractManageActivity;
import com.auto.chishan.manager.ui.project.ProjectManageActivity;
import com.auto.chishan.manager.ui.project.UnderwriteManageActivity;
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
public class BeforeLoanFragment extends BaseFragment {
    @BindView(R.id.userNameTv)
    TextView userNameTv;

    public static BeforeLoanFragment getInstance() {
        return new BeforeLoanFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.frag_before_loan;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        userNameTv.setText(SPUtils.getInstance(mActivity).getName());
    }

    @OnClick({R.id.projectManageLayout, R.id.contractManageLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.projectManageLayout:
                Intent intent = new Intent(mActivity, ProjectManageActivity.class);
                startActivity(intent);
                break;
            case R.id.contractManageLayout:
                Intent intent2 = new Intent(mActivity, ContractManageActivity.class);
                startActivity(intent2);
                break;
            case R.id.underwriteLayout:
                //核保
                Intent intent3 = new Intent(mActivity, UnderwriteManageActivity.class);
                startActivity(intent3);
                break;
            case R.id.paymentLayout:
                //付款

                break;
        }
    }
}
