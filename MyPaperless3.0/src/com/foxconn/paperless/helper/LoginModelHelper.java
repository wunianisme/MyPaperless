package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.foxconn.paperless.constant.MyEnum.ServerType;
import com.foxconn.paperless.util.PhoneInfo;
/**
 * 登陆业务数据处理輔助工具類
 *@ClassName LoginModelUtil
 *@Author wunian
 *@Date 2017/10/6 下午1:43:26
 */
public class LoginModelHelper {
	/**
	 * 获得所有厂区的正式服务器信息
	 * @param result
	 * @return
	 */
	public static List<String> getOfficalServerList(List<String> data,int column){
		List<String> serverList=new ArrayList<String>();
		for (int i = 0; i < data.size(); i+=column) {
			if(data.get(i+6).equals(ServerType.OFFICAl)){
				serverList.add(data.get(i));
				serverList.add(data.get(i+1));
				serverList.add(data.get(i+2));
				serverList.add(data.get(i+3));
				serverList.add(data.get(i+4));
				serverList.add(data.get(i+5));
				serverList.add(data.get(i+6));
				serverList.add(data.get(i+7));
				serverList.add(data.get(i+8));
			}
		}
		return serverList;
	}
	/**
	 * 檢查本地APK版本和服務器版本是否一致
	 * @param context
	 * @return true 要更新
	 * @throws NameNotFoundException 
	 */
	public static boolean checkVersion(Context context,int serverVersion) 
			throws NameNotFoundException {
		PhoneInfo phoneInfo=new PhoneInfo(context);
		int localVersion=phoneInfo.getVerCode();//獲得當前APK的版本號
		if(localVersion<serverVersion){//當前版本小與服務器更新的版本，提示更新
			return true;
		}
		return false;
	}
}
