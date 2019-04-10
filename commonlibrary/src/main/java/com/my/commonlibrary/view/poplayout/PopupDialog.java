package com.my.commonlibrary.view.poplayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.my.commonlibrary.R;

/**
 * Date: 2018/12/21
 * desc:
 *
 * @author:DingZhixiang
 */
public class PopupDialog extends Dialog {
    private static final int DEFAULT_CONTENT_LAYOUT;
    private int mContentLayoutId = -1;
    private View mContentLayout;
    private int mGravity = 80;
    private boolean mUseRadius = true;
    private int mWindowWidth = -1;
    private int mWindowHeight = -1;

    public PopupDialog(@NonNull Context context) {
        super(context);
    }

    public PopupDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PopupDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private int getContentLayoutId() {
        return this.mContentLayoutId <= 0 ? DEFAULT_CONTENT_LAYOUT : this.mContentLayoutId;
    }

    protected void setContentLayout(@LayoutRes int contentLayoutId) {
        this.mContentLayoutId = contentLayoutId;
    }

    protected void setContentLayout(View contentLayout) {
        this.mContentLayout = contentLayout;
    }

    protected void setWindowGravity(int gravity) {
        this.mGravity = gravity;
    }

    protected void setUseRadius(boolean useRadius) {
        this.mUseRadius = useRadius;
    }

    protected void setWindowWidth(int width) {
        this.mWindowWidth = width;
    }

    protected void setWindowHeight(int height) {
        this.mWindowHeight = height;
    }

    private void configWindow() {
        Window window = this.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = this.mGravity;
            this.configWindowBackground(window);
            this.configWindowLayoutParams(window, params);
            this.configWindowAnimations(window);
        }

    }

    private void configWindowLayoutParams(Window window, WindowManager.LayoutParams params) {
        params.gravity = this.mGravity;
        if (this.mGravity != 3 && this.mGravity != 5) {
            if (this.mGravity != 48 && this.mGravity != 80) {
                params.width = this.getWidthParams(-2);
                params.height = this.getHeightParams(-2);
            } else {
                params.width = this.getWidthParams(-1);
                params.height = this.getHeightParams(-2);
            }
        } else {
            params.width = this.getWidthParams(-2);
            params.height = this.getHeightParams(-1);
        }

        window.setAttributes(params);
    }

    private int getWidthParams(int defaultParams) {
        return this.mWindowWidth >= 0 ? this.mWindowWidth : defaultParams;
    }

    private int getHeightParams(int defaultParams) {
        return this.mWindowHeight >= 0 ? this.mWindowHeight : defaultParams;
    }

    private void configWindowAnimations(Window window) {
        switch(this.mGravity) {
            case 3:
                window.setWindowAnimations(R.style.LeftDialogAnimation);
                break;
            case 5:
                window.setWindowAnimations(R.style.RightDialogAnimation);
            case 17:
                break;
            case 48:
                window.setWindowAnimations(R.style.TopDialogAnimation);
                break;
            case 80:
            default:
                window.setWindowAnimations(R.style.BottomDialogAnimation);
        }

    }

    private void configWindowBackground(Window window) {
        if (!this.mUseRadius) {
            window.setBackgroundDrawableResource(R.drawable.background_normal);
        } else {
            switch(this.mGravity) {
                case 3:
                    window.setBackgroundDrawableResource(R.drawable.background_left);
                    break;
                case 5:
                    window.setBackgroundDrawableResource(R.drawable.background_right);
                    break;
                case 17:
                    window.setBackgroundDrawableResource(R.drawable.background_center);
                    break;
                case 48:
                    window.setBackgroundDrawableResource(R.drawable.background_top);
                    break;
                case 80:
                default:
                    window.setBackgroundDrawableResource(R.drawable.background_bottom);
            }

        }
    }

    private void configContentView() {
        if (this.mContentLayout != null) {
            this.setContentView(this.mContentLayout);
        } else {
            this.setContentView(this.getContentLayoutId());
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configContentView();
        this.configWindow();
    }

    static {
        DEFAULT_CONTENT_LAYOUT = R.layout.layout_dialog_default;
    }
}
