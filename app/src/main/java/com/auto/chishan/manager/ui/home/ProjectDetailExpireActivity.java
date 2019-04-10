package com.auto.chishan.manager.ui.home;

import android.view.View;
import android.widget.LinearLayout;

import com.auto.chishan.manager.R;
import com.my.commonlibrary.base.BaseResultActivity;

import butterknife.BindView;

/**
 * Date: 2019/3/5
 * desc:
 *
 * @author:DingZhixiang
 */
public class ProjectDetailExpireActivity extends BaseResultActivity {
    @BindView(R.id.ddLayout)
    LinearLayout ddLayout;
    @BindView(R.id.dbLayout)
    LinearLayout dbLayout;
    @BindView(R.id.advocatePeopleLayout)
    LinearLayout advocatePeopleLayout;
    @BindView(R.id.expirationReminderDayNumLayout)
    LinearLayout expirationReminderDayNumLayout;
    @BindView(R.id.expiresLayout)
    LinearLayout expiresLayout;
    @BindView(R.id.overdueTypeTvLayout)
    LinearLayout overdueTypeTvLayout;
    @BindView(R.id.overdueDayNumLayout)
    LinearLayout overdueDayNumLayout;
    @BindView(R.id.balanceLayout)
    LinearLayout balanceLayout;

    //expire 到期 overdue 逾期
    private String detailType;
    ////dd 典当  db 担保
    private String projectType;

    @Override
    protected int setLayout() {
        return R.layout.activity_expireproject_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        titleContentTv.setText("项目到期查看");
        projectType = getIntent().getStringExtra("projectType");
        detailType = getIntent().getStringExtra("detailType");
        if (projectType.equals("dd")) {
            //典当
            ddLayout.setVisibility(View.VISIBLE);
            dbLayout.setVisibility(View.GONE);
            if (detailType.equals("expire")) {
                //到期
            } else {
                //逾期
                expirationReminderDayNumLayout.setVisibility(View.GONE);
                balanceLayout.setVisibility(View.VISIBLE);
                overdueTypeTvLayout.setVisibility(View.VISIBLE);
                overdueDayNumLayout.setVisibility(View.VISIBLE);
            }
        } else {
            //担保
            ddLayout.setVisibility(View.GONE);
            dbLayout.setVisibility(View.VISIBLE);
            if (detailType.equals("expire")) {
                //到期
            } else {
                //逾期
                expiresLayout.setVisibility(View.VISIBLE);
                overdueTypeTvLayout.setVisibility(View.VISIBLE);
                overdueDayNumLayout.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    protected void initData() {

    }
}
