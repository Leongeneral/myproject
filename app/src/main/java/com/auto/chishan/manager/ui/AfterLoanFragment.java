package com.auto.chishan.manager.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.auto.chishan.manager.R;
import com.auto.chishan.manager.ui.afterloan.AfterloanVisitActivity;
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
public class AfterLoanFragment extends BaseFragment {
    @BindView(R.id.userNameTv)
    TextView userNameTv;

    public static AfterLoanFragment getInstance() {
        return new AfterLoanFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.frag_after_loan;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        userNameTv.setText(SPUtils.getInstance(mActivity).getName());
    }


    @OnClick({R.id.callbackLl, R.id.pawnLl, R.id.guaranteeLl, R.id.searchLl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.callbackLl:
                //贷后回访
                Intent intent = new Intent(mActivity, AfterloanVisitActivity.class);
                startActivity(intent);
                break;
            case R.id.pawnLl:
                //典当变更

                break;
            case R.id.guaranteeLl:
                //担保变更

                break;
            case R.id.searchLl:
                //综合查询

                break;
        }
    }
}
