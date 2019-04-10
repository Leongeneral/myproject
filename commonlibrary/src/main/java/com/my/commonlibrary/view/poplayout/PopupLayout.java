package com.my.commonlibrary.view.poplayout;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;

/**
 * Date: 2018/12/21
 * desc:
 *
 * @author:DingZhixiang
 */
public class PopupLayout {
    private static final String TAG = "PopupLayout";
    public PopupDialog mPopupDialog;
    private PopupLayout.DismissListener mDismissListener;
    public static int POSITION_LEFT = 3;
    public static int POSITION_RIGHT = 5;
    public static int POSITION_CENTER = 17;
    public static int POSITION_TOP = 48;
    public static int POSITION_BOTTOM = 80;

    private PopupLayout() {
    }

    public static PopupLayout init(Context context, @LayoutRes int contentLayoutId) {
        PopupLayout popupLayout = new PopupLayout();
        popupLayout.mPopupDialog = new PopupDialog(context);
        popupLayout.mPopupDialog.setContentLayout(contentLayoutId);
        popupLayout.initListener();
        return popupLayout;
    }

    public static PopupLayout init(Context context, View contentView) {
        PopupLayout popupLayout = new PopupLayout();
        popupLayout.mPopupDialog = new PopupDialog(context);
        popupLayout.mPopupDialog.setContentLayout(contentView);
        popupLayout.initListener();
        return popupLayout;
    }

    private void initListener() {
        this.mPopupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                if (PopupLayout.this.mDismissListener != null) {
                    PopupLayout.this.mDismissListener.onDismiss();
                }

            }
        });
    }

    public void setUseRadius(boolean useRadius) {
        if (this.mPopupDialog != null) {
            this.mPopupDialog.setUseRadius(useRadius);
        }

    }

    public void show() {
        this.show(POSITION_BOTTOM);
    }

    public void show(int position) {
        if (this.mPopupDialog == null) {
            Log.e("PopupLayout", "Dialog init error,it's null");
        } else {
            this.mPopupDialog.setWindowGravity(position);
            this.mPopupDialog.show();
        }
    }

    public void setHeight(int height, boolean dpMode) {
        if (dpMode) {
            this.mPopupDialog.setWindowHeight(dp2Px(this.mPopupDialog.getContext(), height));
        } else {
            this.mPopupDialog.setWindowHeight(height);
        }
    }

    public void setWidth(int width, boolean dpMode) {
        if (dpMode) {
            this.mPopupDialog.setWindowWidth(dp2Px(this.mPopupDialog.getContext(), width));
        } else {
            this.mPopupDialog.setWindowWidth(width);
        }

    }

    public void hide() {
        if (this.mPopupDialog != null) {
            this.mPopupDialog.hide();
        }

    }

    public void dismiss() {
        if (this.mPopupDialog != null) {
            this.mPopupDialog.dismiss();
        }

    }

    public void setDismissListener(PopupLayout.DismissListener dismissListener) {
        this.mDismissListener = dismissListener;
    }

    private static int dp2Px(Context context, int dp) {
        return context == null ? 0 : (int)((float)dp * context.getResources().getDisplayMetrics().density);
    }

    public interface DismissListener {
        void onDismiss();
    }
}
