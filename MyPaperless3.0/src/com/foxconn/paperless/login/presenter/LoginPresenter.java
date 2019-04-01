package com.foxconn.paperless.login.presenter;

import android.os.IBinder;

public interface LoginPresenter {

	public void validateLogin(String logonName,String password,int pos);
	
	public void getServer();
	
	public void checkApkVersion();
	
	public void downloadApk();
	
	public void getLastLoginAccount();
	
	public void deleteAccount(String logonName);
	
	public void inputSelectAccount(String logonName,String password);
	
	public void getAccount();
	
	public void setAccountListView();
	
	public void setAccountPopupWindow();
	
	public void updateAccountPopupWindow();
	
	public void onTextChanged(String text);
	
	public void onNetWorkChanged(String preNetWork,String nowNetWork);
	
	public void getNetWorkService(IBinder service);
	
	public void togglePassword(int action);
	
	public void changeDropDownIcon();
	
	public void touchToDropUp(int action);
	
	public void setLanguage(int languageId,String logonName,String password);
	
	public void initLanguage();
	
	public void selectLanguage();
	
	public void goForgetPwdActivity(String logonName);

	
	
}
