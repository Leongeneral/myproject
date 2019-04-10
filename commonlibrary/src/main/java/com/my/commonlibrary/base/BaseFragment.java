package com.my.commonlibrary.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.commonlibrary.R;
import com.my.commonlibrary.utils.DialogUtil;
import com.my.commonlibrary.utils.PassWord;
import com.my.commonlibrary.utils.RSAandDES;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment 基础类
 * @fileName: BaseFragment
 */

public abstract class BaseFragment extends Fragment{
    protected Activity mActivity;
    protected MyApplication mContext;
    private Unbinder mBind;
    /**
     * 处理页面加载中、页面加载失败、页面没数据
     */
    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;

    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private TextView tvMsg;
    protected TextView titleContentTv, titlebackTv, titlerightTv;
    protected ImageView title_rightImage;
    private View parrentView;
    /**
     * 当前状态
     */
    private int currentState = NORMAL_STATE;
    public Dialog mLoadingDialog;
    protected String privateKey;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        privateKey = PassWord.getRandom(8, PassWord.TYPE.LETTER_NUMBER);
        RSAandDES.privateKey = privateKey;
        parrentView = inflater.inflate(getLayoutResID(), container,false);
        return parrentView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        mContext = MyApplication.getInstance();
        mBind = ButterKnife.bind(this, view);
        mLoadingDialog = DialogUtil.getCustomAlertDialog(mActivity, "请稍后...");
        initView();
        initData();
    }

    public void initTitleBar() {
        titleContentTv = parrentView.findViewById(R.id.title_content);
        titlerightTv = parrentView.findViewById(R.id.title_right);
        titlebackTv = parrentView.findViewById(R.id.title_back);
        titlebackTv.setVisibility(View.GONE);
        title_rightImage = parrentView.findViewById(R.id.title_rightImage);
    }

    @Override
    public void onDestroy() {
        if (mBind != Unbinder.EMPTY){
            mBind.unbind();
        }
        mBind = null;
        super.onDestroy();
    }

    /**
     * 获取 布局信息
     * @return
     */
    public abstract int getLayoutResID();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 初始化 ui 布局
     */
    protected void initView(){
        if (getView() == null) {
            return;
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView != null && (mNormalView.getParent() instanceof ViewGroup)) {
            ViewGroup parent = (ViewGroup) mNormalView.getParent();
            View.inflate(mActivity, R.layout.view_loading, parent);
            View.inflate(mActivity, R.layout.view_error, parent);
            View.inflate(mActivity, R.layout.view_empty, parent);
            mLoadingView = parent.findViewById(R.id.loading_group);
            mErrorView = parent.findViewById(R.id.error_group);
            mEmptyView = parent.findViewById(R.id.empty_group);
            tvMsg = parent.findViewById(R.id.tv_err_msg);
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
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    public void showError(String err) {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
        tvMsg.setText(err);
    }

    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void showEmpty() {
        if (currentState == EMPTY_STATE) {
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void reload() {

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
                break;
            default:
                break;
        }
    }
}
