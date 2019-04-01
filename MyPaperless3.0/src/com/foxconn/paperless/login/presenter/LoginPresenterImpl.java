package com.foxconn.paperless.login.presenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.ServerConfig;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.APK;
import com.foxconn.paperless.constant.MyEnum.Config;
import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.constant.MyEnum.NetWorkType;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.constant.MyEnum.ServerType;
import com.foxconn.paperless.helper.LoginPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.login.model.LoginModel;
import com.foxconn.paperless.login.model.LoginModelImpl;
import com.foxconn.paperless.login.view.ForgetPwdActivity;
import com.foxconn.paperless.login.view.LoginView;
import com.foxconn.paperless.main.view.MainActivity;
import com.foxconn.paperless.service.NetWorkService;
import com.foxconn.paperless.service.NetWorkService.LocalBinder;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.LanguageUtil;
import com.foxconn.paperless.util.TextUtil;
import com.foxconn.paperless.util.DownloadManager.OnDownloadListener;

/**
 * 登陆业务逻辑处理
 * 
 * @ClassName LoginPresenterImpl
 * @Author wunian
 * @Date 2017/9/18 下午4:07:40
 */
public class LoginPresenterImpl implements LoginPresenter, OnModelListener,OnDownloadListener {
	private LoginView loginView;
	private LoginModel loginModel;
	private Euser user;
	private Params p;
	private List<ServerConfig> serverList;// 所有服务器的信息
	private List<String> serverName;// 服务器名称
	private Context context;
	private Map<String, String> map;
	private boolean dropdown;// 下拉状态 初始:未下拉 false
	private int languageId;//語言ID 中文：0  英文：1
	private String apkUrl;//apk下載地址

	public LoginPresenterImpl(LoginView loginView, Context context) {
		this.loginView = loginView;
		this.context = context;
		this.user = (Euser) context.getApplicationContext();
		this.loginModel = new LoginModelImpl(this);
		this.serverList = new ArrayList<ServerConfig>();
		this.serverName = new ArrayList<String>();
		ParamsUtil.initMyConstant();//初始化服務器相關常量
		this.p=new Params(loginModel);
		this.dropdown = false;
	}
	/**
	 * 获得厂区服务器
	 */
	@Override
	public void getServer() {
		p = ParamsUtil.getParam(p, Action.GET_SERVER, new String[] { "" });
		loginModel.getServer(p);
	}
	/**
	 * 獲得最後一次登錄的賬號和密碼，並且自動填充到輸入框
	 */
	@Override
	public void getLastLoginAccount() {	
		String[] loginArray=loginModel.getLastLoginAccount(context);
		//ToastHelper.showInfo(context, Language.LOCALE[2].getLanguage()+"", 0);
		if(!TextUtil.isEmpty(loginArray[0])&&
				MyConstant.TEST_ACCOUNT.contains(loginArray[0]))//資安管控，普通賬號不允許記住最後一次密碼
			loginView.inputAccount(loginArray[0], loginArray[1]);
		//獲取最後一次輸入登錄的賬號和密碼后再獲取服務器信息，減輕服務器端壓力
		getServer();
	}
	/**
	 * APK版本更新
	 */
	@Override
	public void checkApkVersion() {
		p=ParamsUtil.getParam(p, Action.GET_APKINFO, new String[]{APK.NAME});
		loginModel.getApkInfo(p);
	}
	/**
	 * 下載APK
	 */
	@Override
	public void downloadApk() {
		loginModel.downloadApk(context,apkUrl,this);
	}
	
	@Override
	public void downloadOver() {
		try {
			p=ParamsUtil.getParam(p, Action.SAVE_DOWNLOADINFO, LoginPresenterHelper.getPhoneInfo(context));
			loginModel.saveDownloadInfo(p);
		} catch (NameNotFoundException e) {
			loginView.showToastExceptionMsg(e.getMessage());
		}
	}
	/**
	 * 登陆校验
	 */
	@Override
	public void validateLogin(String logonName, String password, int pos) {
		if (TextUtil.isEmpty(logonName)) {
			loginView.showToastFailedMsg(R.string.logonname_empty);
		} else if (TextUtil.isEmpty(password)) {
			loginView.showToastFailedMsg(R.string.password_empty);
		} else {
			ServerConfig sc = LoginPresenterHelper.getUserServerInfo(serverList,
					pos);
			if (sc.getSite() != null) {
				p = ParamsUtil.getParam(p, sc, Action.CHECK_LOGIN,
						new String[] { logonName, password });
				ParamsUtil.setServerConstant(sc);// 設置常量
				loginModel.validateLogin(p);// 發送登陸請求
				loginView.showLoading();// 顯示進度條
				MyConstant.SERVERNAME=languageId==Language.CHINESE?
						sc.getServerName():sc.getEnglishServerName();
			} else {// 連接服務器失敗
				loginView.showToastFailedMsg(R.string.connect_network_failed);
			}
		}
	}
	/**
	 * 刪除保存賬號
	 */
	@Override
	public void deleteAccount(String logonName) {
		loginModel.deleteAccount(context, logonName);
	}
	/**
	 * 獲得保存的賬號信息
	 */
	@Override
	public void getAccount() {
		map = loginModel.getAccount(context);
	}
	/**
	 * 設置賬號下拉框的ListView
	 */
	@Override
	public void setAccountListView() {
		getAccount();
		loginView.setAccountListView(map);
	}
	/**
	 * 輸入選擇的賬號
	 */
	@Override
	public void inputSelectAccount(String logonName, String password) {
		loginView.inputAccount(logonName, password);
		if(serverName.size()<=0){//獲取服務器信息異常時每切換一個賬號請求一次
			getServer();
		}
	}
	/**
	 * 設置popupwindow
	 */
	@Override
	public void setAccountPopupWindow() {
		getAccount();
		if(map.size()>0){
			loginView.setAccountPopupWindow(map.size());
			changeDropDownIcon();
		}
	}
	/**
	 * 更新popupwindow
	 */
	@Override
	public void updateAccountPopupWindow() {
		getAccount();
		loginView.updateAccountPopupWindow(map.size());
	}
	
	/**
	 * 賬號輸入文本變化時，判斷是否需要重新獲取服務器信息
	 */
	@Override
	public void onTextChanged(String text) {
		if (TextUtil.isTestAccount(text)||Config.TestMode) {// 判断是测试账号或切換成測試模式
			MyConstant.SERVERTYPE = ServerType.ALL;
			getServer();
		} else if (MyConstant.SERVERTYPE.equals(ServerType.ALL)
				&&!Config.TestMode) {//不是测试账号，但是上一次输入过测试账号並且不是測試模式
			MyConstant.SERVERTYPE = ServerType.OFFICAl;
			getServer();
		}
	}
	/**
	 * 網絡發生改變時，判斷是否需要重新獲取服務器信息
	 */
	@Override
	public void onNetWorkChanged(String preNetWork,String nowNetWork) {
		// 前一次無網絡，當前有網絡或未獲取到服務器信息時重新獲取服務器信息
		if ((preNetWork.equals(NetWorkType.DISCONNECT) &&!nowNetWork.equals(NetWorkType.DISCONNECT)) 
				|| serverName.size()<=0) {
			getServer();
		}
	}
	/**
	 * 网络服务，監聽網絡狀態
	 */
	@Override
	public void getNetWorkService(IBinder service) {
		LocalBinder binder = (LocalBinder) service;
		NetWorkService myService = binder.getService();
		myService.setLoginPresenter(this);
	}
	/**
	 * 显示/隐藏密码
	 */
	@Override
	public void togglePassword(int action) {
		if (action == MotionEvent.ACTION_DOWN) {// 顯示密碼
			loginView.showPassword();
		}
		if (action == MotionEvent.ACTION_UP) {// 隱藏密碼
			loginView.hidePassword();
		}
	}
	/**
	 * 切换账号下拉图标
	 */
	@Override
	public void changeDropDownIcon() {
		if (dropdown) {// 已经下拉
			loginView.setAccountDropDownIcon(R.drawable.arrow_down);// 箭头向下
			dropdown = false;
		} else {// 已经合上
			if(map.size()>0){//有存儲賬號時才改變箭頭
				loginView.setAccountDropDownIcon(R.drawable.arrow_up);// 箭头向上
				dropdown = true;
			}
		}
	}
	/**
	 * 触摸屏幕合上账号下拉框
	 */
	@Override
	public void touchToDropUp(int action) {
		if (action == MotionEvent.ACTION_DOWN && dropdown) {// 按下屏幕并且处于下拉状态
			loginView.setAccountDropDownIcon(R.drawable.arrow_down);
			dropdown = false;
		}
	}
	/**
	 * 初始化語言
	 */
	@Override
	public void initLanguage() {
		this.languageId=loginModel.getLanguageId(context);
		//重新設置languageId,防止語言環境與存儲ID不一致的情況出現
		this.languageId=LoginPresenterHelper.setLanguageId(context, this.languageId);
		loginModel.saveLanguageId(context, this.languageId);//重新保存LanguageId(語言環境與存儲ID不一致的情況需要重設)
	}
	/**
	 * 選擇語言
	 */
	@Override
	public void selectLanguage() {
		loginView.showSelectLanguageDialog(languageId);
	}
	/**
	 * 切换语言
	 */
	@Override
	public void setLanguage(int languageId,String logonName,String password) {
		this.languageId=languageId;
		LanguageUtil.setLanguage(context, languageId);
		loginModel.saveLanguageId(context, languageId);//保存語言ID
		loginModel.saveLastLoginAccount(context, logonName, password);//先保存一下賬號，防止刷新后丟失
		loginView.refreshView();//刷新頁面
	}
	/**
	 * 跳轉到忘記密碼
	 */
	@Override
	public void goForgetPwdActivity(String logonName) {
		Bundle bundle=new Bundle();
		bundle.putString("logonName", logonName);//賬號
		bundle.putSerializable("serverList", (Serializable) serverList);//服務器信息
		bundle.putInt("languageId", languageId);//語言ID
		loginView.gotoActivity(ForgetPwdActivity.class, bundle);
	}

	@Override
	public void success(JsonResult result) {
		loginView.dismissLoading();// 隱藏進度框
		if (result.getAction().equals(Action.GET_SERVER)) {
			serverList = LoginPresenterHelper.getServerList(result);
			serverName=LoginPresenterHelper.getServerName(
					serverName, serverList, languageId);
			loginView.setServerAdapter(serverName);
			
		}
		if (result.getAction().equals(Action.CHECK_LOGIN)) {
			if (result.getResultCode() == ResultCode.TRUE) {
				user = LoginPresenterHelper.getEuserInfo(result, user);// 獲得用戶信息
				loginModel.saveAccount(context, user.getLogonName(),user.getPassword());// 保存账号
				//保存最後一次賬號（HashMap無序bug解決方案）
				loginModel.saveLastLoginAccount(context,  user.getLogonName(),user.getPassword());
				//loginView.showToastSuccessMsg(R.string.login_success);
				loginView.gotoActivity(MainActivity.class, null);//進入主頁
			}
			if (result.getResultCode() == ResultCode.PASSWORDNOTLEGAL) {//密碼不合法
				user = LoginPresenterHelper.getEuserInfo(result, user);// 獲得用戶信息
				loginView.showUpdatePwdDialog(R.string.system_tip, R.string.pwd_regular,
						R.string.updatepwd_now);
			}
			if (result.getResultCode() == ResultCode.LOGONNAME_NOTEXIST){//賬號不存在
				loginView.showToastFailedMsg(R.string.loginname_notexist);
			}
			if(result.getResultCode()==ResultCode.PWDERROR_NOLOCK){//密碼錯誤，未被鎖定
				String errorMsg=context.getResources().getString(R.string.pwd_error_nolock)+
						result.getResultMsg();
				loginView.showToastFailedMsg(errorMsg);
			}
			if(result.getResultCode()==ResultCode.PWDERROR_ISLOCK){//密碼輸錯剛好輸入五次，被鎖定
				loginView.showToastFailedMsg(R.string.pwd_error_islock);
			}
			if(result.getResultCode()==ResultCode.ERROR_LOCEKD){//已被鎖定，無法登陸
				loginView.showToastFailedMsg(R.string.error_locked);
			}
		}
		if(result.getAction().equals(Action.GET_APKINFO)){
			try {
				apkUrl=result.getData().get(7)+APK.NAME;//apk的下載路徑
				MyConstant.SERVERVERSION=Integer.parseInt(result.getData().get(1));//服務器APK版本
				boolean download=loginModel.checkApkVersion(context, MyConstant.SERVERVERSION);
				if(download){//可更新下載
					//更新內容
					String message=context.getResources().getString(R.string.APK_update_content)+"\n"+
							result.getData().get(2);	
					loginView.showApkUpdateDialog(R.string.APK_update, message, 
							R.string.btn_updateAPK, R.string.btn_no);
				}
			} catch (Exception e) {
				loginView.showToastExceptionMsg(e.getMessage());
			}
		}
		if(result.getAction().equals(Action.SAVE_DOWNLOADINFO)){
			//loginView.showToastSuccessMsg(R.string.savedownload_success);
		}
	}

	@Override
	public void failed(JsonResult result) {
		loginView.dismissLoading();
		if (result.getAction().equals(Action.CHECK_LOGIN)) {
			loginView.showToastFailedMsg(R.string.logonname_password_error);
		}
	}

	@Override
	public void exception(JsonResult result) {
		loginView.showToastExceptionMsg(result.getResultMsg());
	}
	
}
