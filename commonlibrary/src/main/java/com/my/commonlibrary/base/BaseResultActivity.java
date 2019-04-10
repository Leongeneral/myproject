package com.my.commonlibrary.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.my.commonlibrary.R;
import com.my.commonlibrary.utils.PassWord;
import com.my.commonlibrary.utils.RSAandDES;

/**
 * 基础activity 包含请求成功失败 界面
 */

public abstract class BaseResultActivity extends BaseActivity{

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;

    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private int currentState = NORMAL_STATE;
    private TextView tvErrMsg;
    protected String privateKey;
    @Override
    protected void initView() {
        privateKey = PassWord.getRandom(8, PassWord.TYPE.LETTER_NUMBER);
        RSAandDES.privateKey = privateKey;
        if(mActivity == null){
            throw new IllegalStateException("Activity cannot be empty");
        }
        mNormalView = findViewById(R.id.normal_view);
        if(mNormalView != null && (mNormalView.getParent() instanceof ViewGroup)){
            ViewGroup parent = (ViewGroup) mNormalView.getParent();
            View.inflate(mActivity, R.layout.view_loading, parent);
            View.inflate(mActivity, R.layout.view_error, parent);
            View.inflate(mActivity, R.layout.view_empty, parent);
            mLoadingView = parent.findViewById(R.id.loading_group);
            mErrorView = parent.findViewById(R.id.error_group);
            mEmptyView = parent.findViewById(R.id.empty_group);
            tvErrMsg = parent.findViewById(R.id.tv_err_msg);
            mErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                }
            });
            mErrorView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
            mNormalView.setVisibility(View.VISIBLE);
        }
    }

    public void showNormal() {
        if(currentState == NORMAL_STATE){
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    public void showError(String err) {
        if(currentState == ERROR_STATE){
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        if(currentState == LOADING_STATE){
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void showEmpty() {
        if(currentState == EMPTY_STATE){
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
            default:
                break;
        }
    }

    public void reload() {
        showLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelAll();
    }
}
