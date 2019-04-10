package com.my.commonlibrary.utils;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RSAandDES {

	public static String privateKey;
	/**
	 * RSA加密
	 * @param Auto_device
	 * @param Auto_privateKey
	 * @param Auto_loginName
	 * @param Auto_userPsw
	 * @param Auto_method
	 * @param Auto_parameter
	 * @return
	 */
	public static String getRSA(String Auto_device, String Auto_privateKey, String Auto_loginName, String Auto_userPsw,
                                String Auto_method, String Auto_parameter) {
		String rsaStr = null;
		Map<String, String> map = new HashMap<String, String>(); // 存放到MAP里面
		map.put("Auto_device", Auto_device);
		map.put("Auto_privateKey", Auto_privateKey);
		map.put("Auto_loginName", Auto_loginName);
		map.put("Auto_userPsw", Auto_userPsw);
		map.put("Auto_method", Auto_method);
		if (Auto_parameter != null) {
			map.put("Auto_parameter", Auto_parameter);
		}

		String jsonStr = new Gson().toJson(map);
		byte[] arr = null;
		try {
			arr = RSAUtils.encryptByPublicKey(jsonStr.getBytes(), MyKey.cliPublicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rsaStr = StringUtils.bytesToHexString(arr);
		return rsaStr;
	}

	/**
	 * DES加密
	 * @param bigParams
	 * @param Auto_privateKey
	 * @return
	 */
	public static String getDES(String bigParams, String Auto_privateKey) {
		String desStr = null;
		try {
			desStr = DesUtils.encrypt(bigParams, Auto_privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return desStr;
	}

}
