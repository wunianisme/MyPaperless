package com.foxconn.paperless.login.model;

import java.util.Map;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;
import com.foxconn.paperless.util.DownloadManager.OnDownloadListener;

public interface LoginModel extends Response {

	/**
	 * 登陆校验
	 * @param p
	 */
	public void validateLogin(Params p) ;
	/**
	 * 获得服务器信息
	 * @param p
	 */
	public void getServer(Params p);
	/**
	 * 保存账号
	 * @param context
	 * @param logonName
	 * @param password
	 */
	public void saveAccount(Context context,String logonName,String password);
	/**
	 * 删除保存账号
	 * @param context
	 * @param logonName
	 */
	public void deleteAccount(Context context,String logonName);
	/**
	 * 获得所有的保存账号信息
	 * @param context
	 * @return
	 */
	public Map<String,String> getAccount(Context context);
	/**
	 * 獲得最後一次的登錄賬號
	 * @param context
	 * @return
	 */
	public String[] getLastLoginAccount(Context context);
	/**
	 * 保存最後一次登錄賬號
	 * @param context
	 * @param logonName
	 * @param password
	 */
	public void saveLastLoginAccount(Context context,String logonName,String password);
	/**
	 * 保存語言ID
	 * @param context
	 * @param languageId
	 */
	public void saveLanguageId(Context context,int languageId);
	/**
	 * 獲得語言ID
	 * @param context
	 * @return
	 */
	public int getLanguageId(Context context);
	/**
	 * 獲得服務器更新的APK信息
	 * @param p
	 */
	public void getApkInfo(Params p);
	/**
	 * APK版本更新
	 * @param context
	 */
	public boolean checkApkVersion(Context context,int serverVersion) throws NameNotFoundException;
	/**
	 * 下載APK
	 * @param context
	 * @param apkUrl
	 * @param listener
	 */
	public void downloadApk(Context context, String apkUrl,OnDownloadListener listener);
	/**
	 * 保存下載信息到數據庫
	 * @param p
	 */
	public void saveDownloadInfo(Params p);
	/**
	 * 保存修改密碼
	 * @param p
	 */
	public void saveUpdatePwd(Params p);
	/**
	 * 獲得郵箱地址
	 * @param p
	 * @return
	 */
	public void getUserEmail(Params p);
	/**
	 * 發送密碼至郵箱
	 * @param p
	 * @return
	 */
	public void sendPasswordToEmail(Params p);
}
