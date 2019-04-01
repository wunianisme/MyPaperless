package com.foxconn.paperless.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.foxconn.paperless.constant.MyEnum.SharePreferenceInfo;
/**
 * 管理SharePreference
 *@ClassName SharePreferenceManager
 *@Author wunian
 *@Date 2017/10/6 下午1:51:24
 */
public class SharePreferenceManager {
	/**
	 * 保存登陸信息
	 * @param context
	 * @param logonName
	 * @param password
	 */
	public static void saveLogin(Context context,String logonName,String password){
		String file=SharePreferenceInfo.FILE_ACCOUNT;
		int mode=SharePreferenceInfo.FILE_ACCOUNT_MODE;
		if(checkExistAccount(context, logonName,password,file,mode)) return;
		int count=getAll(context,file,mode).size();
		if(count>=SharePreferenceInfo.SAVE_ACCOUNT_NUM){//如果已达到最大存储数量，删除其中一个再保存
			Map<String,String> accoutMap=getAll(context, file,mode);
			 for (String key:accoutMap.keySet()) {
				remove(context, file,mode, key);
				break;
			}
		}
		HashMap<String,String> pm=new HashMap<String,String>();
		pm.put(logonName, password);
		saveString(context, file,mode,pm);
	}
	/**
	 * 檢驗賬號是否已存儲  true:存在
	 * @param context
	 * @param logonName
	 * @return
	 */
	public static boolean checkExistAccount(Context context,String logonName,String password,String file,int mode){
		SharedPreferences sp=context.getSharedPreferences(file,mode);
		if(sp.getString(logonName,"").equals("")||!sp.getString(logonName, "").equals(password)){
			return false;
		}
		return true;
	}
	/**
	 * 刪除數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @param key
	 * @return
	 */
	public static boolean remove(Context context,String fileName,int mode,String key){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		Editor editor=sp.edit();
		editor.remove(key);
		return editor.commit();
	}
	/**
	 * 清空數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @return
	 */
	public static boolean clear(Context context,String fileName,int mode){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		Editor editor=sp.edit();
		editor.clear();
		return editor.commit();
	}
	
	/**
	 * 保存String數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @param map
	 * @return
	 */
	public static boolean saveString(Context context,String fileName,int mode,Map<String,String> map){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		Editor editor=sp.edit();
		for (String key:map.keySet()) {
			editor.putString(key, map.get(key));
		}
		return editor.commit();
	}
	/**
	 * 保存String數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean saveString(Context context,String fileName,int mode,String key,String value){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		Editor editor=sp.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	/**
	 * 保存Int數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean saveInt(Context context,String fileName,int mode,String key,int value){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		Editor editor=sp.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	/**
	 * 獲得String數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @param map
	 * @return
	 */
	public static Map<String,String> getString(Context context,String fileName,int mode,Map<String,String> map){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		for (String key:map.keySet()) {
			map.put(key, sp.getString(key, ""));
		}
		return map;
	}
	
	/**
	 * 獲得所有的數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getAll(Context context,String fileName,int mode){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		Map<String,String> map=(Map<String, String>) sp.getAll();
		return map;
	}
	/**
	 * 獲得Int數據
	 * @param context
	 * @param fileName
	 * @param mode
	 * @param key
	 * @return
	 */
	public static int getInt(Context context,String fileName,int mode,String key){
		SharedPreferences sp=context.getSharedPreferences(fileName, mode);
		return sp.getInt(key, 0);
	}
}
