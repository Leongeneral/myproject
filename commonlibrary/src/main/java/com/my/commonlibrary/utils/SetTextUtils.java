package com.my.commonlibrary.utils;

import android.widget.EditText;
import android.widget.TextView;

public class SetTextUtils {

	public static void setText(TextView textview, String str){
		if(str != null && !str.equals("")){
			textview.setText(str);
		}else{
			textview.setText(null);
		}
	}
	
	public static void setText(EditText textview, String str){
		if(str != null && !str.equals("")){
			textview.setText(str);
		}else{
			textview.setText(null);	
		}
	}
	
}
