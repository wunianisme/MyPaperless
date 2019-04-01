package com.foxconn.paperless.util;

import android.content.Context;

/**
 * 分辨率工具
 *@ClassName DensityUtil
 *@Author wunian
 *@Date 2017/10/18 上午10:32:04
 */
public class DensityUtil {
	/**
	 * dp轉換為px
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dpTopx(Context context,float dpValue){
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dpValue*scale+0.5f);
	}
	/**
	 * px轉換為dp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int pxTodp(Context context,float pxValue){
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(pxValue/scale+0.5f);
	}
}
