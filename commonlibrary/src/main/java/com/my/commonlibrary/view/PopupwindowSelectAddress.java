package com.my.commonlibrary.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.my.commonlibrary.R;
import com.my.commonlibrary.view.wheelview.adapter.AbstractWheelTextAdapter;
import com.my.commonlibrary.view.wheelview.view.OnWheelChangedListener;
import com.my.commonlibrary.view.wheelview.view.OnWheelScrollListener;
import com.my.commonlibrary.view.wheelview.view.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
*@Date: 2016年10月20日 
*@author dingzhixiang
 描述：选择地区的Popupwindow
 */
public class PopupwindowSelectAddress extends PopupWindow implements View.OnClickListener{

	private View mainView;
	private WheelView wvProvince;
	private WheelView wvCitys;
	private WheelView wvCountrys;
	private View lyChangeAddress;
	private View lyChangeAddressChild;
	private TextView btnSure;
	private TextView btnCancel;

	private Context context;
	private JSONObject mJsonObj;
	private String[] mProvinceDatas;
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

	private ArrayList<String> arrProvinces = new ArrayList<String>();
	private ArrayList<String> arrCitys = new ArrayList<String>();
	private ArrayList<String> arrCountrys = new ArrayList<String>();
	private AddressTextAdapter provinceAdapter;
	private AddressTextAdapter cityAdapter;
	private AddressTextAdapter countryAdapter;

	private String strProvince = "安徽";
	private String strCity = "合肥";
	private String strCountry = "高新区";
	private OnAddressCListener onAddressCListener;

	private int maxsize = 24;
	private int minsize = 14;

	public PopupwindowSelectAddress(Context context, int paramWidth,
									int paramHeight) {
		this.context=context;
		mainView = LayoutInflater.from(context).inflate(R.layout.pop_selectaddress, null);
		setContentView(mainView);
		// 设置宽度
		setWidth(paramWidth);
		// 设置高度
		setHeight(paramHeight);
		setFocusable(true);
		// 设置显示隐藏动画
		setAnimationStyle(R.style.popAnimation);
		setBackgroundDrawable(new BitmapDrawable());
		initView();
		setListener();
//		initAllData();
	}

	private void initView() {
		wvProvince = (WheelView) mainView.findViewById(R.id.wv_address_province);
		wvCitys = (WheelView) mainView.findViewById(R.id.wv_address_city);
		wvCountrys = (WheelView) mainView.findViewById(R.id.wv_address_country);
		lyChangeAddress = mainView.findViewById(R.id.ly_myinfo_changeaddress);
		lyChangeAddressChild = mainView.findViewById(R.id.ly_myinfo_changeaddress_child);
		btnSure = (TextView) mainView.findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) mainView.findViewById(R.id.btn_myinfo_cancel);
	}
	
	private void setListener(){
		lyChangeAddress.setOnClickListener(this);
		lyChangeAddressChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		wvProvince.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
				strProvince = currentText;
				setTextviewSize(currentText, provinceAdapter);

				String[] citys = mCitisDatasMap.get(currentText);
				initCitys(citys);
				cityAdapter = new AddressTextAdapter(context, arrCitys, 0, maxsize, minsize);
				wvCitys.setVisibleItems(5);
				wvCitys.setViewAdapter(cityAdapter);
				wvCitys.setCurrentItem(0);

				initCountrys(mCitisDatasMap.get(strCity));
				countryAdapter = new AddressTextAdapter(context, arrCountrys, getCountryItem(strCountry), maxsize, minsize);
				wvCountrys.setVisibleItems(5);
				wvCountrys.setViewAdapter(countryAdapter);
				wvCountrys.setCurrentItem(0);
			}
		});

		wvProvince.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, provinceAdapter);
			}
		});

		wvCitys.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
				strCity = currentText;
				setTextviewSize(currentText, cityAdapter);

				initCountrys(mCitisDatasMap.get(strCity));
				countryAdapter = new AddressTextAdapter(context, arrCountrys, getCountryItem(strCountry), maxsize, minsize);
				wvCountrys.setVisibleItems(5);
				wvCountrys.setViewAdapter(countryAdapter);
				wvCountrys.setCurrentItem(getCityItem(strCountry));
			}
		});

		wvCitys.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, cityAdapter);
			}
		});

		wvCountrys.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				String currentText = (String) countryAdapter.getItemText(wheel.getCurrentItem());
				strCountry = currentText;
				setTextviewSize(currentText, countryAdapter);
			}
		});
		wvCountrys.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) countryAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, countryAdapter);
			}
		});
	}
	
	private void initAllData() {
		initJsonData();
		initDatas();
		initProvinces();
		provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
		wvProvince.setVisibleItems(5);
		wvProvince.setViewAdapter(provinceAdapter);
		wvProvince.setCurrentItem(getProvinceItem(strProvince));

		initCitys(mCitisDatasMap.get(strProvince));
		cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
		wvCitys.setVisibleItems(5);
		wvCitys.setViewAdapter(cityAdapter);
		wvCitys.setCurrentItem(getCityItem(strCity));

		initCountrys(mCitisDatasMap.get(strCity));
		countryAdapter = new AddressTextAdapter(context, arrCountrys, getCountryItem(strCountry), maxsize, minsize);
		wvCountrys.setVisibleItems(5);
		wvCountrys.setViewAdapter(countryAdapter);
		wvCountrys.setCurrentItem(getCityItem(strCountry));
	}
	private class AddressTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(24);
			} else {
				textvew.setTextSize(14);
			}
		}
	}

	public void setAddresskListener(OnAddressCListener onAddressCListener) {
		this.onAddressCListener = onAddressCListener;
	}

	@Override
	public void onClick(View v) {
		if (v == btnSure) {
			if (onAddressCListener != null) {
				onAddressCListener.onClick(strProvince, strCity,strCountry);
			}
		} else if (v == btnCancel) {

		} else if (v == lyChangeAddressChild) {
			return;
		} else {
			dismiss();
		}
		dismiss();
	}

	/**
	 * 回调接口
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnAddressCListener {
		public void onClick(String province, String city, String country);
	}

	/**
	 * 从文件中读取地址数据
	 */
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = context.getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "utf-8"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析数据
	 */
	private void initDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);
				String province = jsonP.getString("p");

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try {
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1) {
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try {
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e) {
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];
					for (int k = 0; k < jsonAreas.length(); k++) {
						String area = jsonAreas.getJSONObject(k).getString("s");
						mAreasDatas[k] = area;
					}
					mCitisDatasMap.put(city, mAreasDatas);
				}
				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	/**
	 * 初始化省会
	 */
	public void initProvinces() {
		int length = mProvinceDatas.length;
		for (int i = 0; i < length; i++) {
			arrProvinces.add(mProvinceDatas[i]);
		}
	}

	/**
	 * 根据省会，生成该省会的所有城市
	 * 
	 * @param citys
	 */
	public void initCitys(String[] citys) {
		if (citys != null) {
			arrCitys.clear();
			int length = citys.length;
			for (int i = 0; i < length; i++) {
				arrCitys.add(citys[i]);
			}
		} else {
			String[] city = mCitisDatasMap.get("安徽");
			arrCitys.clear();
			int length = city.length;
			for (int i = 0; i < length; i++) {
				arrCitys.add(city[i]);
			}
		}
		if (arrCitys != null && arrCitys.size() > 0
				&& !arrCitys.contains(strCity)) {
			strCity = arrCitys.get(0);
		}
	}

	/**
	 * 根据城市，生成该城市的所有区县
	 *
	 * @param countrys
	 */
	public void initCountrys(String[] countrys) {
		if (countrys != null) {
			arrCountrys.clear();
			int length = countrys.length;
			for (int i = 0; i < length; i++) {
				arrCountrys.add(countrys[i]);
			}
		} else {
			String[] city = mCitisDatasMap.get("合肥");
			arrCountrys.clear();
			int length = city.length;
			for (int i = 0; i < length; i++) {
				arrCountrys.add(city[i]);
			}
		}
		if (arrCountrys != null && arrCountrys.size() > 0
				&& !arrCountrys.contains(strCountry)) {
			strCountry = arrCountrys.get(0);
		}
	}

	/**
	 * 初始化地点
	 * 
	 * @param province
	 * @param city
	 */
	public void setAddress(String province, String city, String country) {
		if (province != null && province.length() > 0) {
			this.strProvince = province;
		}
		if (city != null && city.length() > 0) {
			this.strCity = city;
		}
		if (country != null && country.length() > 0) {
			this.strCountry = city;
		}
		initAllData();
	}

	/**
	 * 返回省会索引，没有就返回默认“四川”
	 * 
	 * @param province
	 * @return
	 */
	public int getProvinceItem(String province) {
		int size = arrProvinces.size();
		int provinceIndex = 0;
		boolean noprovince = true;
		for (int i = 0; i < size; i++) {
			if (province.equals(arrProvinces.get(i))) {
				noprovince = false;
				return provinceIndex;
			} else {
				provinceIndex++;
			}
		}
		if (noprovince) {
			strProvince = "安徽";
			return 22;
		}
		return provinceIndex;
	}

	/**
	 * 得到城市索引，没有返回默认“成都”
	 * 
	 * @param city
	 * @return
	 */
	public int getCityItem(String city) {
		int size = arrCitys.size();
		int cityIndex = 0;
		boolean nocity = true;
		for (int i = 0; i < size; i++) {
			System.out.println(arrCitys.get(i));
			if (city.equals(arrCitys.get(i))) {
				nocity = false;
				return cityIndex;
			} else {
				cityIndex++;
			}
		}
		if (nocity) {
			strCity = "合肥";
			return 0;
		}
		return cityIndex;
	}

	/**
	 * 得到区县索引，没有返回默认“高新区”
	 *
	 * @param country
	 * @return
	 */
	public int getCountryItem(String country) {
		int size = arrCountrys.size();
		int cityIndex = 0;
		boolean nocity = true;
		for (int i = 0; i < size; i++) {
			if (country.equals(arrCountrys.get(i))) {
				nocity = false;
				return cityIndex;
			} else {
				cityIndex++;
			}
		}
		if (nocity) {
			strCountry = "高新区";
			return 0;
		}
		return cityIndex;
	}

}
