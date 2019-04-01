package com.foxconn.paperless.util;

import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.ui.widget.ToastHelper;
/**
 * 語言工具類
 *@ClassName LanguageUtil
 *@Author wunian
 *@Date 2017/9/22 上午11:37:27
 */
public class LanguageUtil {

	/**
	 * 設置系統語言環境
	 * @param context
	 * @param languageId
	 */
	public static void setLanguage(Context context,int languageId){
		Resources res=context.getResources();
		DisplayMetrics dm=res.getDisplayMetrics();
		Configuration config=res.getConfiguration();
		config.locale=Language.LOCALE[languageId];
		res.updateConfiguration(config, dm);
	}
	/**
	 * 獲得系統設置語言
	 * @param context
	 * @return
	 */
	public static Locale getLanguage(Context context){
		Resources res=context.getResources();
		Configuration config=res.getConfiguration();
		return config.locale;
	}
}
