package com.foxconn.paperless.main.view;

import android.content.Intent;
import android.graphics.Bitmap;

import com.foxconn.paperless.base.BaseView;

public interface AccountView extends BaseView{
	/**
	 * 顯示賬號名稱、工號
	 * @param logonName
	 * @param name
	 */
	void showAccountName(String logonName,String name);
	/**
	 * 啟動Activity並且退出時返回結果
	 * @param intent
	 */
	void goActivityForResult(Intent intent,int requestCode);
	/**
	 * 設置頭像圖標
	 * @param bitmap
	 */
	void setImageBitmap(Bitmap bitmap);
	/**
	 * 顯示退出賬號對話框
	 * @param titleId
	 * @param msgId
	 * @param btnOk
	 * @param btnNo
	 */
	void showLogoffDialog(int titleId, int msgId, int btnOk, int btnNo);
	/**
	 * 顯示聯繫我們的對話框
	 * @param titleId
	 * @param msgId
	 */
	void showContactUsDialog(int titleId, int msgId);
	/**
	 * 顯示或隱藏更新版本提示
	 * @param visible
	 * @param versionMsg
	 */
	void showNewVersion(int visible, String versionMsg);
	/**
	 * 顯示APK更新對話框
	 * @param apkUpdate
	 * @param message
	 * @param btnUpdateapk
	 * @param btnNo
	 */
	void showApkUpdateDialog(int titleId, String message, int btnOk,
			int btnNo);
	/**
	 * 顯示版本信息對話框
	 * @param titleId
	 * @param versionMsg
	 */
	void showVersionInfoDialog(int titleId, String versionMsg);
	
}
