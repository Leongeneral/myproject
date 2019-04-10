package com.my.commonlibrary.mvp;

/**
 * Created by jess on 16/4/28.
 */
public interface IPresenter {
    void onStart();
    void onDestroy();
    void showError(String error);
}
