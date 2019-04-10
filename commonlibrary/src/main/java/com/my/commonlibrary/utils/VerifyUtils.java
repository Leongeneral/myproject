package com.my.commonlibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Date: 2019/1/3
 * desc:
 *
 * @author:DingZhixiang
 */
public class VerifyUtils {
    // 手机号正则表达式
    private final static Pattern phonePattern = Pattern
            .compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
    /**
     * 验证身份证,Dialog
     *
     * @return
     */
    public static boolean formatCardNo(String cardNoStr) {
        String resultStr;
        try {
            resultStr = IDCard.IDCardValidate(cardNoStr);
            if (isEmpty(resultStr)) {
                return true;
            } else {
                // ToastUtils.ToastMessageListen.ToastMessage(context,
                // resultStr,null);
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证手机号
     *
     * @return
     */
    public static boolean formatPhone(String phoneStr) {
        if (phoneStr != null && !"".equals(phoneStr) && phonePattern.matcher(phoneStr).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getAge(String  birthDay) {
        Date birthdayData = parseServerTime(birthDay);
        //获取当前系统时间
        java.util.Calendar cal = java.util.Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthdayData)) {
            return 0;
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(java.util.Calendar.YEAR);
        int monthNow = cal.get(java.util.Calendar.MONTH);
        int dayOfMonthNow = cal.get(java.util.Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthdayData);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(java.util.Calendar.YEAR);
        int monthBirth = cal.get(java.util.Calendar.MONTH);
        int dayOfMonthBirth = cal.get(java.util.Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    public static Date parseServerTime(String serverTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
