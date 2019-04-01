package com.foxconn.paperless.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.foxconn.paperless.constant.MyEnum.NetWorkType;
/**
 * 网络管理
 *@ClassName NetWorkManager
 *@Author wunian
 *@Date 2017/9/11 下午3:15:45
 */
public class NetWorkManager {

	/**
	 * 获取网络类型名称
	 * @param context
	 * @return
	 */
	public static String getNetWorkType(Context context){
		String networkType=context.getResources().getString(NetWorkType.DISCONNECT);//初始化網絡狀態為無網絡連接
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null&&networkInfo.isConnected()){
			networkType=networkInfo.getTypeName();//获得网络类型名称
		}
		return networkType;
	}
	/**
	 * 获得Wifi名称
	 * @param context
	 * @return
	 */
	public static String getWifiName(Context context){
		WifiManager wifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo=wifiManager.getConnectionInfo();
		//Log.d("wifiInfo", wifiInfo.toString());    
        //Log.d("SSID",wifiInfo.getSSID()); 
		return wifiInfo.getSSID();
	}
}
