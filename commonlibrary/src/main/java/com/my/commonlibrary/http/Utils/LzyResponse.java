/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.my.commonlibrary.http.Utils;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class LzyResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int code;
    public String msg;
    public String message;
    /**处理成功*/
    public static String SUCCESS = "success";
    /** 处理失败*/
    public static String ERROR = "error";
    /** 无权访问*/
    public static String NORIGHT = "noRight";
    /**超时*/
    public static String TIMEOUT = "timeOut";

    private String auto_stata;
    private String auto_errorMsg;
    private T data;

    public String getAuto_stata() {
        return auto_stata;
    }

    public void setAuto_stata(String auto_stata) {
        this.auto_stata = auto_stata;
    }

    public String getAuto_errorMsg() {
        return auto_errorMsg;
    }

    public void setAuto_errorMsg(String auto_errorMsg) {
        this.auto_errorMsg = auto_errorMsg;
    }

    public T getAuto_data() {
        return data;
    }

    public void setAuto_data(T auto_data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
               "\tcode=" + code + "\n" +//
               "\tmsg='" + msg + "\'\n" +//
               "\tdata=" + data + "\n" +//
               '}';
    }
}
