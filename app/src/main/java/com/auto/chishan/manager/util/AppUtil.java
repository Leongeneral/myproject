package com.auto.chishan.manager.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.auto.chishan.manager.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 通用工具类,与业务无关，共通组件
 * 
 */
public class AppUtil {
	private static final String TAG = AppUtil.class.getSimpleName();
	private static final String SEPARATOR_PREFER_URL=";;";

	/**
	 * Check is connect net
	 * @return
	 */
	public static boolean isOnline(Context context) 
	{
		if(context == null)
			return true;
		try {
			ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cManager.getActiveNetworkInfo();
			if (info != null && info.isAvailable() && info.isConnected()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	static Toast mToast;
	/**
	 * 检查有无网络，没有网络弹出提示
	 * @param context 设备上下文，必须是Activity
	 * @return
	 */
	public static boolean checkOnline(Context context) {
		boolean ret = isOnline(context);
		if(!ret){
			if(mToast == null){
				mToast= Toast.makeText(context.getApplicationContext(), R.string.net_unavailable, Toast.LENGTH_SHORT);
			}else{
				mToast.setText(R.string.net_unavailable);
			}
			mToast.show();
		}
		return ret;
	}
	/**
	 * 检查是否是wifi
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查是否是流量（蜂窝网络）
	 * @param context
	 * @return
	 */
	public static boolean isCellularNet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.isAvailable() && activeNetInfo.isConnected() && activeNetInfo.getType() != ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 是否已安装应用
	 * @param context 上下文环境
	 * @param packagename 包名，示例:com.iflytek.vflynote
	 * @return
	 */
	public static boolean isPackageInstalled(Context context,String packagename)
	{
		boolean installed = false;
		PackageInfo packageInfo = null;
	    try {
	       packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);  
	     } catch (NameNotFoundException e) {
	       packageInfo = null;  
//	       e.printStackTrace();
	   }
	   if(packageInfo != null){
		   installed = true;  
	   } 
	   return installed;
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return Math.round(pxValue / scale);
	}
	/** 
     * 将dip或dp值转换为px值，保证尺寸大小不变 
     *  
     * @param context
     * @param dipValue
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int dp2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return Math.round(dipValue * scale);  
    }  
  
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 

	/**
	 * 当一个view的高度获取不到的时候，先调用这个方法测量一下，那么就可以获取到view的高度了
	 * 
	 * @param view
	 */
	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 取得屏幕宽度
	 * 
	 * @return
	 */
	public static int catchDisplayWidth(Context context) {
		
		DisplayMetrics dms = context.getResources().getDisplayMetrics();
		return dms.widthPixels > dms.heightPixels ? dms.heightPixels :  dms.widthPixels;
	}
	/**
	 * 取得屏幕高度
	 * 
	 * @return
	 */
	public static int catchDisplayHeight(Context context) {
		
		DisplayMetrics dms = context.getResources().getDisplayMetrics();
		return dms.widthPixels > dms.heightPixels ?dms.widthPixels  :  dms.heightPixels;
	}

	/**
	 * 和当前时间差值
	 * @param time
	 * @return
	 */
	public static int timeAbs(long time){
		return (int) Math.abs(time -  System.currentTimeMillis());
	}
	
	/**
	 * 获取屏幕分辨率，并保持宽度在前
	 * @return String 宽*高 
	 */
	public static String catchDisplaySize(Context context) {
		String displaySize = "";
		DisplayMetrics dms = context.getResources().getDisplayMetrics();
		if(dms.widthPixels > dms.heightPixels) {
			displaySize = dms.heightPixels + "*" + dms.widthPixels;
		} else {
			displaySize = dms.widthPixels + "*" + dms.heightPixels;
		}
		return displaySize;
	}
	
	
	/**
	 * Set<String>集合转换为字符串，每个值以“;;"分割
	 * @return
	 */
	public static String setToString(Set<String> sets){
		String val = "";
		if(sets == null){
			return val;
		}
		Iterator<String> iteratorTemp=sets.iterator();
	    while(iteratorTemp.hasNext()){
	    	String next = iteratorTemp.next();
	    	if(!TextUtils.isEmpty(next)){
	    		val +=next+SEPARATOR_PREFER_URL;
	    	}
	    }
	    return val;
	}
	
	/**
	 * 以”;;“分割的字符串转换为Set<String>
	 * @param val
	 * @return
	 */
	public static Set<String> stringToSet(String val){
		Set<String>sets = new HashSet<String>();
		if(TextUtils.isEmpty(val)){
			return sets;
		}else if(val.contains(SEPARATOR_PREFER_URL)){
			String[] strings = val.split(SEPARATOR_PREFER_URL);
			if(TextUtils.isEmpty(val)){
				return sets;
			}
			for(int i = 0; i <strings.length ; i++){
				if(!TextUtils.isEmpty(strings[i])){
					sets.add(strings[i]);
				}
			}
		}else {
			sets.add(val);
		}
		return sets;
	}
	/**
	 * 获取所有安装应用包名。
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getAllAppPackageNames(Context context) {
		return getAppPackageNames(context, true);
	}
	
	/**
	 * 获取非内置的安装应用包名。
	 * @param context
	 * @return
	 */
	public static ArrayList<String> getOutlayAppPackageNames(Context context) {
		return getAppPackageNames(context, false);
	}
	
	/**
	 * 获取安装应用包名。
	 * @param context
	 * @param addInlay true:全部，false：非内置
	 * @return
	 */
	private static ArrayList<String> getAppPackageNames(Context context, boolean addInlay) {

		List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
		if (packages != null && !packages.isEmpty()) {
			ArrayList<String> appNames = new ArrayList<String>();
			int size = packages.size();

			for(int i = 0; i < size; i++) {
				PackageInfo info = packages.get(i);
				if (info != null) {
					if (addInlay) {
						// 包含内置应用
						appNames.add(info.packageName);
					} else {
						// 不包含内置应用
						if (info.applicationInfo != null && ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)) {
							appNames.add(info.packageName);
						}
					}
				}
			}
			
			return appNames;
		}
		
		return null;
	}
	/**
	 * 在view绘制之前获取view的宽度高度
	 * @param v
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Rect getTargetSize(View v) {
		
		Rect viewRect = new Rect();
		try {
			Method method = v.getClass().getDeclaredMethod("onMeasure", int.class,
					int.class);
			method.setAccessible(true);
			method.invoke(v, MeasureSpec.makeMeasureSpec(
					((View) v.getParent()).getMeasuredWidth(),
					MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED));
			viewRect.left = 0;
			viewRect.right = v.getMeasuredWidth();
			viewRect.bottom = 0;
			viewRect.top = v.getMeasuredHeight();
		} catch (Exception e) {
		}finally{
			return viewRect;
		}
	}


	/**
	 * 是否是平板
	 * @param context
	 * @return
	 */
	public static boolean isTablet(Context context) {
		boolean tablet = false;
		try {
			tablet = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
		} catch (Exception e) {
		}
		return tablet;
    }




	/**
	 * 获取剪切板第一个内容对象
	 * @param context
	 * @return
	 */
	public static ClipData.Item getClipDataItem(Context context){
		ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if(clipboardManager != null) {
			ClipData cd = clipboardManager.getPrimaryClip();
			if(cd != null && cd.getItemCount() > 0) {
				return 	cd.getItemAt(0);
			}
		}
		return null;
	}

	private static final int PORTRAIT = 0;
	private static final int LANDSCAPE = 1;
	@NonNull
	private volatile static Point[] mRealSizes = new Point[2];


	public static int getScreenRealHeight(@Nullable Context context) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			return getScreenHeight(context);
		}

		int orientation = context != null
				? context.getResources().getConfiguration().orientation
				: context.getResources().getConfiguration().orientation;
		orientation = orientation == Configuration.ORIENTATION_PORTRAIT ? PORTRAIT : LANDSCAPE;

		if (mRealSizes[orientation] == null) {
			WindowManager windowManager = context != null
					? (WindowManager) context.getSystemService(Context.WINDOW_SERVICE)
					: (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			if (windowManager == null) {
				return getScreenHeight(context);
			}
			Display display = windowManager.getDefaultDisplay();
			Point point = new Point();
			display.getRealSize(point);
			mRealSizes[orientation] = point;
		}
		return mRealSizes[orientation].y;
	}

	public static int getScreenHeight(@Nullable Context context) {
		if (context != null) {
			return context.getResources().getDisplayMetrics().heightPixels;
		}
		return 0;
	}

	private volatile static boolean mHasCheckAllScreen;
	private volatile static boolean mIsAllScreenDevice;

	public static boolean isAllScreenDevice(Context context) {
		if (mHasCheckAllScreen) {
			return mIsAllScreenDevice;
		}
		mHasCheckAllScreen = true;
		mIsAllScreenDevice = false;
		// 低于 API 21的，都不会是全面屏。。。
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return false;
		}
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (windowManager != null) {
			Display display = windowManager.getDefaultDisplay();
			Point point = new Point();
			display.getRealSize(point);
			float width, height;
			if (point.x < point.y) {
				width = point.x;
				height = point.y;
			} else {
				width = point.y;
				height = point.x;
			}
			if (height / width >= 1.97f) {
				mIsAllScreenDevice = true;
			}
		}
		return mIsAllScreenDevice;
	}

	public static int getFullActivityHeight(@Nullable Context context) {
		if (!isAllScreenDevice(context)) {
			return getScreenHeight(context);
		}
		return getScreenRealHeight(context);
	}


    /**
     * 判断是否安装应用市场
     * @param context
     * @return
     */
    public static boolean hasAnyMarketInstalled(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return 0 != list.size();
    }
}
