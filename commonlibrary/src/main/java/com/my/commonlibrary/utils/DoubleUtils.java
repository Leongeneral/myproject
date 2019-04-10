package com.my.commonlibrary.utils;

import java.text.DecimalFormat;

public class DoubleUtils {
	public static String format(double number) {
		if (number == 0) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(number);
	}

	public static String format2(double number) {
		if (number == 0) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#0");
		return df.format(number);
	}


}
