package com.foxconn.paperless.login.model;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.constant.MyEnum.ServerType;
import com.foxconn.paperless.constant.MyEnum.SharePreferenceInfo;
import com.foxconn.paperless.helper.LoginModelHelper;
import com.foxconn.paperless.http.WebServiceConnect;
import com.foxconn.paperless.util.DownloadManager;
import com.foxconn.paperless.util.DownloadManager.OnDownloadListener;
import com.foxconn.paperless.util.SharePreferenceManager;
/**
 * 登陆业务数据处理
 *@ClassName LoginModelImpl
 *@Author wunian
 *@Date 2017/9/8 上午8:29:06
 */
public class LoginModelImpl implements LoginModel {
	//private OnLoginModelListener onLoginModelListener;
	private OnModelListener onModelListener;
	private WebServiceConnect webServiceConnect;

	public LoginModelImpl(OnModelListener onModelListener){
		this.onModelListener=onModelListener;
		webServiceConnect=new WebServiceConnect();
	}
	
	@Override
	public void validateLogin(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	
	@Override
	public void getServer(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	
	@Override
	public void saveAccount(Context context,String logonName, String password) {
		if(MyConstant.TEST_ACCOUNT.contains(logonName)){//資安管控，普通員工不允許記住密碼
			SharePreferenceManager.saveLogin(context, logonName, password);
		}
		
	}
	
	@Override
	public void deleteAccount(Context context, String logonName) {
		SharePreferenceManager.remove(context, 
				SharePreferenceInfo.FILE_ACCOUNT, SharePreferenceInfo.FILE_ACCOUNT_MODE, logonName);
	}
	
	@Override
	public Map<String,String> getAccount(Context context) {
		return SharePreferenceManager.getAll(context, 
				SharePreferenceInfo.FILE_ACCOUNT, SharePreferenceInfo.FILE_ACCOUNT_MODE);
	}
	
	@Override
	public String[] getLastLoginAccount(Context context) {
		Map<String,String> accountMap= SharePreferenceManager.getAll(context, 
				SharePreferenceInfo.FILE_LASTACCOUNT,SharePreferenceInfo.FILE_LASTACCOUNT_MODE );
		//ToastHelper.showInfo(context, ""+accountMap.size(), 0);
		for (String key:accountMap.keySet()) {
			return new String[]{key,accountMap.get(key)};
		}
		return new String[]{"",""};
	}

	@Override
	public void saveLastLoginAccount(Context context, String logonName,
			String password) {
		String fileName=SharePreferenceInfo.FILE_LASTACCOUNT;
		int mode=SharePreferenceInfo.FILE_LASTACCOUNT_MODE;
		SharePreferenceManager.clear(context, fileName,mode);//先清空數據，每次只保存一條數據
		Map<String,String> accountMap=new HashMap<String, String>();
		accountMap.put(logonName, password);//保存賬號信息
		SharePreferenceManager.saveString(context,fileName,mode,accountMap);
		
	}
	
	@Override
	public void saveLanguageId(Context context, int languageId) {
		SharePreferenceManager.saveInt(context,
				SharePreferenceInfo.FILE_BASEINFO, 
				SharePreferenceInfo.FILE_BASEINFO_MODE, 
				SharePreferenceInfo.KEY_LANGUAGEID, languageId);
	}
	
	@Override
	public int getLanguageId(Context context) {
		return SharePreferenceManager.getInt(context, 
				SharePreferenceInfo.FILE_BASEINFO, 
				SharePreferenceInfo.FILE_BASEINFO_MODE, 
				SharePreferenceInfo.KEY_LANGUAGEID);
	}
	
	@Override
	public void getApkInfo(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	
	@Override
	public boolean checkApkVersion(Context context,int serverVersion) throws NameNotFoundException {
		return LoginModelHelper.checkVersion(context,serverVersion);
		
	}
	
	@Override
	public void downloadApk(Context context, String apkUrl,
			OnDownloadListener listener) {
		DownloadManager downloadManager=new DownloadManager(context, apkUrl, listener);
		downloadManager.download();
	}
	
	@Override
	public void saveDownloadInfo(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	//update
	@Override
	public void saveUpdatePwd(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	
	@Override
	public void getUserEmail(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	
	@Override
	public void sendPasswordToEmail(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public void onSuccess(JsonResult result) {
		//Log.i("LoginModel", "ResultCode:"+result.getResultCode());
		if(result.getResultCode()>ResultCode.NULL){
			if(result.getAction().equals(Action.GET_SERVER)){//這裡獲得的是所有服務器信息
				if(MyConstant.SERVERTYPE.equals(ServerType.OFFICAl)){//獲得正式服務器信息
					result.setData(LoginModelHelper.getOfficalServerList(
							result.getData(),result.getColumnNum()));
				}
			}
			
			onModelListener.success(result);
		}
			
		if(result.getResultCode()==ResultCode.NULL){
			onModelListener.failed(result);
		}
	}

	@Override
	public void onError(JsonResult result) {
		onModelListener.exception(result);
	}
}
