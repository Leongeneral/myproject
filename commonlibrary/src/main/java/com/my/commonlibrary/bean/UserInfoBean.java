package com.my.commonlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBean implements Parcelable {

	private String id;
	private String registerDate;
	private String phoneNumber;
	private String customerId;
	private String loginName;
	private String certificatesNumber;
	private String password;
	private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCertificatesNumber() {
		return certificatesNumber;
	}

	public void setCertificatesNumber(String certificatesNumber) {
		this.certificatesNumber = certificatesNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.registerDate);
		dest.writeString(this.phoneNumber);
		dest.writeString(this.customerId);
		dest.writeString(this.loginName);
		dest.writeString(this.certificatesNumber);
		dest.writeString(this.password);
		dest.writeString(this.token);
	}

	public UserInfoBean() {
	}

	protected UserInfoBean(Parcel in) {
		this.id = in.readString();
		this.registerDate = in.readString();
		this.phoneNumber = in.readString();
		this.customerId = in.readString();
		this.loginName = in.readString();
		this.certificatesNumber = in.readString();
		this.password = in.readString();
		this.token = in.readString();
	}

	public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
		@Override
		public UserInfoBean createFromParcel(Parcel source) {
			return new UserInfoBean(source);
		}

		@Override
		public UserInfoBean[] newArray(int size) {
			return new UserInfoBean[size];
		}
	};
}
