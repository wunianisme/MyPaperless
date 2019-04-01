package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ServerConfig;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.LanguageUtil;
import com.foxconn.paperless.util.PhoneInfo;
/**
 * LoginPresenter的工具类
 *@ClassName LoginPresenterUtil
 *@Author wunian
 *@Date 2017/9/9 上午9:32:17
 */
public class LoginPresenterHelper {
	/**
	 * 获得所有厂区的服务器信息
	 * @param result
	 * @return
	 */
	public static List<ServerConfig> getServerList(JsonResult result){
		List<ServerConfig> serverList=new ArrayList<ServerConfig>();
		List<String> data=result.getData();
		for (int i = 0; i < data.size(); i+=result.getColumnNum()) {
			ServerConfig sc=new ServerConfig();
			sc.setSite(data.get(i));
			sc.setServerName(data.get(i+1));
			sc.setEnglishServerName(data.get(i+2));
			sc.setUrl(data.get(i+3));
			sc.setNameSpace(data.get(i+4));
			sc.setMethodName(data.get(i+5));
			sc.setType(data.get(i+6));
			sc.setEnable(data.get(i+7));
			sc.setEditdt(data.get(i+8));
			serverList.add(sc);
			Log.i("serverList", sc.toString());
		}
		return serverList;
	}
	
	/**
	 * 获得服务器名称
	 * @param serverList
	 * @return
	 */
	public static List<String> getServerName(List<String> serverName,List<ServerConfig> serverList,int language){
		if(serverName.size()>0) serverName.clear();
		if(serverList.size()>0){
			for (int i = 0; i < serverList.size(); i++) {
				serverName.add(addServerName(serverList, i, language));
			}
		}
		return serverName;
	}
	/**
	 * 根據不同語言獲取服務器名稱
	 * @param serverList
	 * @param i
	 * @param language
	 * @return
	 */
	private static  String addServerName(List<ServerConfig> serverList,int i,int language){
		if(language==Language.CHINESE)
			return serverList.get(i).getServerName();
		else
			return serverList.get(i).getEnglishServerName();
	}
	
	/**
	 * 获得用户选择的服务器信息
	 * @param serverList
	 * @param pos
	 * @return
	 */
	public static ServerConfig getUserServerInfo(List<ServerConfig> serverList,int pos){
		ServerConfig sc=new ServerConfig();
		if(serverList.size()>0){
			sc=serverList.get(pos);
		}
		return sc;
	}
	/**
	 * 獲得登陸用戶的信息
	 * @param result
	 * @param user
	 * @return
	 */
	public static Euser getEuserInfo(JsonResult result,Euser user){
		List<String> data=result.getData();
		if(data.size()>=result.getColumnNum()){
			user.setEuser(
				data.get(0).toString(),
				data.get(1).toString(), 
				data.get(2).toString(), 
				data.get(3).toString(), 
				data.get(4).toString(), 
				data.get(5).toString(), 
				data.get(6).toString(), 
				data.get(7).toString(), 
				data.get(8).toString(), 
				data.get(9).toString(), 
				data.get(10).toString(), 
				data.get(11).toString(), 
				data.get(12).toString(), 
				data.get(13).toString(), 
				data.get(14).toString(), 
				data.get(15).toString(), 
				data.get(16).toString(), 
				data.get(17).toString());
		}
		Log.i("LoginUser<<<<<<<<<", user.toString());
		return user;
	}
	/**
	 * 設置LanguageId
	 * @param context
	 * @param languageId
	 * @return
	 */
	public static int setLanguageId(Context context,int languageId){
		//獲得系統實際語言
		String systemLanguage=LanguageUtil.getLanguage(context).getLanguage();
		if(!Language.LOCALE[languageId].getLanguage()//判斷當前語言環境與存儲的語言ID對應的語言是否一致
				.equals(systemLanguage)){//不一致將語言ID設置為系統語言環境
			if(systemLanguage.equals(Language.LOCALE[Language.CHINESE]
					.getLanguage())){
				languageId=Language.CHINESE;
			}else if(systemLanguage.equals(Language.LOCALE[Language.ENGLISH]
					.getLanguage())){
				languageId=Language.ENGLISH;
			}else if(systemLanguage.equals(Language.LOCALE[Language.VIETNAMESE].
					getLanguage())){
				languageId=Language.VIETNAMESE;
			}
		}
		return languageId;
	}
	/**
	 * 獲取手機信息
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String[] getPhoneInfo(Context context) throws NameNotFoundException{
		PhoneInfo phoneInfo=new PhoneInfo(context);
		String deviceId=phoneInfo.getDeviceId();
		String phoneBrand=phoneInfo.getPhoneBrand();
		String phoneModel=phoneInfo.getPhoneModel();
		String verCode=phoneInfo.getVerCode()+"";
		int[] metrics=phoneInfo.getMetrics();
		String metric=metrics[0]+"*"+metrics[1];
		String simoperatorName=phoneInfo.getSimoperatorName();
		return new String[]{deviceId,phoneBrand,phoneModel,verCode,metric,simoperatorName};
	}
}
