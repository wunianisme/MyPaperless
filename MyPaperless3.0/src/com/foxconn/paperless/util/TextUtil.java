package com.foxconn.paperless.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.integer;

import com.foxconn.paperless.constant.MyConstant;

import taobe.tec.jcc.JChineseConvertor;

/**
 * 操作文本的工具類
 * 
 * @ClassName TextUtil
 * @Author wunian
 * @Date 2017/9/5 下午3:55:37
 */
public class TextUtil {
	//匹配密码的正则表达式(大小寫字母+數字+特殊字符 6-16位)
	public static final String PASSWORD_REGEXP="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*" +
			"[\\!\\@\\#\\$\\%\\^\\&\\*\\.])[0-9a-zA-Z\\!\\@\\#\\$\\%\\^\\&\\*\\.]{8,16}$";
	//匹配郵箱的正則表達式(字母開頭+多個字符+零個或多個包含（一個或多個.或-或字符)+一個或多個字符+一個或多個包含（一個或多個.或字符）結尾
	public static final String EMAIL_REGEXP="^(\\w)+([\\.\\-]\\w+)*@(\\w)+((\\.\\w+)+)$";
	/**
	 * 判断文本是否为空
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		if (text == null || text.trim().equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 判斷輸入的賬號是否是測試賬號
	 * @param logonName
	 * @return
	 */
	public static boolean isTestAccount(String logonName){
		String[] testAccount=MyConstant.TEST_ACCOUNT.split(MyConstant.TEST_ACCOUNT_SPLIT);
		for (int i = 0; i < testAccount.length; i++) {
			if(logonName.equalsIgnoreCase(testAccount[i])){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断密码的合法性，true：合法
	 * @param password
	 * @return
	 */
	public static boolean passwordLegal(String password){
		return password.matches(PASSWORD_REGEXP);
	}
	/**
	 * 判斷郵箱的合法性，true:合法
	 * @param email
	 * @return
	 */
	public static boolean EmailLegal(String email){
		return email.matches(EMAIL_REGEXP);
	}
	/**
	 * 简体转成繁体
	 * 
	 * @param changeText
	 * @return
	 */
	public static String simpleToTradition(String changeText) {
		try {
			JChineseConvertor jChineseConvertor = JChineseConvertor
					.getInstance();
			changeText = jChineseConvertor.s2t(changeText);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return changeText;
	}
	/**
	 * 繁体转成简体
	 * 
	 * @param changeText
	 * @return
	 */
	public static String traditionToSimple(String changeText) {
		try {
			JChineseConvertor jChineseConvertor = JChineseConvertor
					.getInstance();
			changeText = jChineseConvertor.t2s(changeText);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return changeText;
	}
	/**
	 * 判斷輸入的文本是否為中文(只要出現中文便可算中文)
	 * @param word
	 * @return true 是中文 
	 */
	public static boolean isChinese(String word) {
		if (word != null) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
				if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
						|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
						|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
						|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
						|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
						|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 日期格式化
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
	/**
	 * 日期格式化，自定義格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormat(Date date,String format){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
	/**
	 * 將字符串轉換為日期、時間
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date strToDate(String str,String format) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	/**
	 * 獲取日期（年、月、日）
	 * @return
	 */
	public static int[] getdate(){
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int date=calendar.get(Calendar.DAY_OF_MONTH);
		return new int[]{year,month,date};
	}
	/**
	 * 增加日期天數
	 * @param day
	 * @return
	 */
	public static int addDay(int day){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 獲取時間（時、分、秒、毫秒）
	 * @return
	 */
	public static int[] getTime(){
		Calendar calendar=Calendar.getInstance();
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		int milliSecond=calendar.get(Calendar.MILLISECOND);
		return new int[]{hour,minute,second,milliSecond};
	}
	/**
	 * 格式化日期字符串 yyyy/MM/dd
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static String getDateStr(int year,int month,int date){
		String monthStr=(month+1)+"";
		String dateStr=date+"";
		if(month<9){
			monthStr="0"+(month+1);
		}
		if(date<10){
			dateStr="0"+dateStr;
		}
		return year + "/" +monthStr+ "/" + dateStr;
	}
	/**
	 * 格式化時間字符串 HH:mm:ss.fff
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	public static String getTimeStr(int hour,int minute,int second,int milliSecond){
		String hourStr=hour+"";
		String minuteStr=minute+"";
		String secondStr=second+"";
		String milliSecondStr=milliSecond+"";
		if(hour<10){
			hourStr="0"+hour;
		}
		if(minute<10){
			minuteStr="0"+minute;
		}
		if(second<10){
			secondStr="0"+second;
		}
		if(milliSecond<10){
			milliSecondStr="00"+milliSecond;
		}else if(milliSecond<100){
			milliSecondStr="0"+milliSecond;
		}
		return hourStr+":"+minuteStr+":"+secondStr+"."+milliSecondStr;	
	}

}
