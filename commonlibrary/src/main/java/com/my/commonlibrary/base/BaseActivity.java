package com.my.commonlibrary.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.my.commonlibrary.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Date: 2017/4/12
 * desc:
 * @author:DingZhixiang
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;
    protected MyApplication mContext;
    protected BaseActivity mActivity;
    protected ProgressDialog mLoadingDialog;
    protected TextView titleContentTv, titlebackTv, titlerightTv;
    protected ImageView title_rightImage;
    private boolean useEventBus=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        //设置状态栏颜色
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.color_app));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//进入页面不自动弹出软键盘
        mActivity = this;
        mContext = MyApplication.getInstance();
        mUnbinder = ButterKnife.bind(this);
        if (useEventBus){
            EventBus.getDefault().register(this);//注册eventbus
        }
        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setMessage("请稍后...");
        mLoadingDialog.setCanceledOnTouchOutside(false);
        initView();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        if (useEventBus){
            EventBus.getDefault().unregister(this);//解除注册eventbus
        }
        OkGo.getInstance().cancelAll();
        this.mUnbinder = null;
    }

    /**
     * 是否使用eventBus,默认为使用(false)，
     */
    protected void useEventBus(boolean useEventBus) {
        this.useEventBus=useEventBus;
    }

    protected abstract int setLayout();
    protected abstract void initView();
    protected abstract void initData();

    /**
     * activity布局如果引用了titlebar,则可以调用该方法初始化title
     */
    public void initTitleBar() {
        titleContentTv = (TextView) findViewById(R.id.title_content);
        titlerightTv = (TextView) findViewById(R.id.title_right);
        titlebackTv = (TextView) findViewById(R.id.title_back);
        title_rightImage = (ImageView) findViewById(R.id.title_rightImage);
        titlebackTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    public void showMsgDiaog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", null).show();
    }

    public void showToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转下一个activity
     */
    public void jumpToNextActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    // 获取InputMethodManager，隐藏软键盘
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
