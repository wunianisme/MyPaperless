package com.foxconn.paperless.ui.widget;

import com.foxconn.paperless.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Toast助手
 *@ClassName ToastHelper
 *@Author wunian
 *@Date 2017/9/5 下午3:21:42
 */
public class ToastHelper {

	/**
	 * 普通的shortToast
	 * @param context
	 * @param strId
	 */
	public static void show(Context context,int strId){
		Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 普通的自定義duration的Toast
	 * @param context
	 * @param strId
	 * @param duration
	 */
	public static void show(Context context,int strId,int duration){
		Toast.makeText(context, strId, duration).show();
	}
	/**
	 * 顯示成功(SUCCESS)或一般信息的Toast
	 * @param context
	 * @param strId
	 * @param duration
	 */
	public static void showInfo(Context context,int strId,int duration){
		createCustomerToast(context,context.getResources().getColor(R.color.green) , 
				Color.WHITE, strId,"",duration).show();
	}
	/**
	 * 顯示成功(SUCCESS)或一般信息的Toast
	 * @param context
	 * @param str
	 * @param duration
	 */
	public static void showInfo(Context context,String str,int duration){
		createCustomerToast(context,context.getResources().getColor(R.color.green) , 
				Color.WHITE, 0,str,duration).show();
	}
	/**
	 * 顯示失敗（NULL）或者錯誤的Toast
	 * @param context
	 * @param strId
	 * @param duration
	 */
	public static void showError(Context context,int strId,int duration){
		createCustomerToast(context,Color.RED , 
				Color.WHITE, strId,"", duration).show();
	}
	/**
	 * 顯示失敗（NULL）或者錯誤的Toast
	 * @param context
	 * @param str
	 * @param duration
	 */
	public static void showError(Context context,String str,int duration){
		createCustomerToast(context,Color.RED , 
				Color.WHITE,0,str, duration).show();
	}
	/**
	 * 顯示異常(Exception)的Toast
	 * @param context
	 * @param strId
	 * @param duration
	 */
	public static void showException(Context context,String str,int duration){
		createCustomerToast(context, Color.BLUE,
				Color.WHITE, 0,str,duration).show();
	}
	
	/**
	 * 創建自定義的Toast
	 * @param context
	 * @param bgColor
	 * @param str
	 * @param duration
	 * @return
	 */
	private static Toast createCustomerToast(Context context,int bgColor,int textColor,int strId,String str,int duration){
		TextView tvContent=new TextView(context);
		if(strId!=0) tvContent.setText(strId);
		else tvContent.setText(str);
		tvContent.setBackgroundColor(bgColor);
		tvContent.setTextColor(textColor);
		tvContent.setPadding(20, 20, 20, 20);
		Toast t=new Toast(context);
		t.setDuration(duration);
		//WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);  
	   // int height = wm.getDefaultDisplay().getHeight();  
		//t.setGravity(Gravity.TOP, 0, height/4);
		t.setView(tvContent);
		return t;
	} 
}
