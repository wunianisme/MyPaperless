package com.foxconn.paperless.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
/**
 * 手機信息
 *@ClassName PhoneInfo
 *@Author wunian
 *@Date 2017/9/26 上午11:04:02
 */
public class PhoneInfo {
	
	private Context context;
	private TelephonyManager tm;
	
	public PhoneInfo(Context context){
		this.context=context;
		tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	/**
	 * 獲取設備ID
	 * @return
	 */
	public String getDeviceId(){
		return tm.getDeviceId();
	}
	/**
	 * 獲得手機運營商
	 * @return
	 */
	public String getSimoperatorName(){
		String simoperatorName="";
		if(tm.getSimOperatorName().length()>0){
			simoperatorName=TextUtil.simpleToTradition(tm.getSimOperatorName());
		}
		return simoperatorName;
	}
	/**
	 * 獲取屏幕分辨率
	 * @return
	 */
	public int[] getMetrics(){
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display=wm.getDefaultDisplay();
		Point point=new Point();
		display.getSize(point);
		int width=point.x;
		int height=point.y;
		return new int[]{width,height};
	}
	/**
	 * 獲取設備廠商
	 * @return
	 */
	public String getPhoneBrand(){
		return Build.BRAND+" "+Build.MANUFACTURER;
	}
	/**
	 * 獲得設備名稱
	 * @return
	 */
	public String getPhoneModel(){
		return Build.MODEL;
	}
	/**
	 * 獲得軟件版本號
	 * @return
	 * @throws NameNotFoundException
	 */
	public int getVerCode() throws NameNotFoundException{
		PackageInfo packageInfo=context.getApplicationContext().getPackageManager()
				.getPackageInfo(context.getPackageName(),PackageManager.PERMISSION_GRANTED);
		int versionCode=packageInfo.versionCode;//獲得當前APK的版本號
		return versionCode;
	}
	/**
	 * 獲得軟件版本名稱
	 * @return
	 * @throws NameNotFoundException
	 */
	public String getVersionName() throws NameNotFoundException{
		PackageInfo packageInfo=context.getApplicationContext().getPackageManager()
				.getPackageInfo(context.getPackageName(),PackageManager.PERMISSION_GRANTED);
		String versionName=packageInfo.versionName;
		return versionName;
	}
	
	/**
	 * 獲得軟件名稱
	 * @return
	 * @throws NameNotFoundException
	 */
	public String getAppName() throws NameNotFoundException{
		PackageManager packageManager=context.getPackageManager();
		ApplicationInfo applicationInfo=packageManager.getApplicationInfo(context.getPackageName(), PackageManager.PERMISSION_GRANTED);
		String appName=(String) packageManager.getApplicationLabel(applicationInfo);
		return appName;
	}
}
