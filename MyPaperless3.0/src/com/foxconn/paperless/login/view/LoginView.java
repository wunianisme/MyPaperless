package com.foxconn.paperless.login.view;

import java.util.List;
import java.util.Map;

import com.foxconn.paperless.base.BaseView;

import android.os.Bundle;


public interface LoginView extends BaseView{
	/**
	 * 初始化語言環境
	 */
	void initLanguage();
	/**
	 * 获得厂区正式服务器
	 */
	void getServer();
	/**
	 * 獲得最後一次登錄賬號
	 */
	void getLastLoginAccout();
	/**
	 * 檢查Apk版本更新
	 */
	void checkApkVersion();
	/**
	 * 顯示APK更新的提示框
	 * @param titleId
	 * @param msgId
	 * @param positiveId
	 * @param negativeId
	 */
	void showApkUpdateDialog(int titleId,String message,int positiveId,int negativeId);
	
	/**
	 * 设置服务器选择下拉框的适配器，填充服务器名称
	 * @param serverName
	 */
	void setServerAdapter(List<String> serverName);
	/**
	 * 輸入下拉框中的賬號密碼
	 */
	void inputAccount(String logonName,String password);
	/**
	 * 設置賬號選擇ListView適配器
	 */
	void setAccountListView(Map<String,String> map);
	/**
	 * 設置賬號選擇下拉框
	 */
	void setAccountPopupWindow(int size);
	/**
	 * 更新賬號選擇下拉框
	 */
	void updateAccountPopupWindow(int size);
	
	/**
	 * 顯示修改密碼的提示框
	 */
	void showUpdatePwdDialog(int titleId,int msgId,int textId);
	/**
	 * 顯示密碼
	 */
	void showPassword();
	/**
	 * 隱藏密碼
	 */
	void hidePassword();
	/**
	 * 设置账号下拉框的图标
	 * @param drawable
	 */
	void setAccountDropDownIcon(int drawable);
	/**
	 * 選擇中文
	 */
	void selectChineseLanguage();
	/**
	 * 選擇英文
	 */
	void selectEnglishLanguage();
	/**
	 * 刷新頁面（切換語言）
	 */
	void refreshView();
	/**
	 * 頁面跳轉
	 */
	void gotoActivity(Class<?> cls,Bundle bundle);
	/**
	 * 顯示錯誤信息
	 * @param errorMsg
	 */
	void showToastFailedMsg(String errorMsg);
	/**
	 * 顯示選擇語言的彈出框
	 * @param languageId
	 */
	void showSelectLanguageDialog(int languageId);
}
