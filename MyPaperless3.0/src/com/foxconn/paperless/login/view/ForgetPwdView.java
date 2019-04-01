package com.foxconn.paperless.login.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;

public interface ForgetPwdView extends BaseView {

	/**
	 * 獲得服務器信息
	 */
	void getServer();
	/**
	 * 設置服務器適配器
	 * @param serverName
	 */
	void setServerAdapter(List<String> serverName);
	/**
	 * 輸入郵箱
	 * @param email
	 */
	void inputEmail(String email);
	
}
