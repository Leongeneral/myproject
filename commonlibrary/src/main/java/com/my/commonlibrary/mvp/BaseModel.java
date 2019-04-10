package com.my.commonlibrary.mvp;

/**
 * Date: 2017/4/12
 * desc:
 * @author:DingZhixiang
 */

public abstract class BaseModel<SubP> {

    protected SubP mPresenter;

    public BaseModel(SubP presenter) {
        this.mPresenter = presenter;
    }

}
