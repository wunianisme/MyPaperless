package com.foxconn.paperless.login.presenter;

import java.util.List;

import com.foxconn.paperless.bean.ServerConfig;

public interface ForgetPwdPresenter {
	

	void setFactoryAdapter(List<ServerConfig> serverList,int languageId);
	
	void getUserEmail(String logonName,int pos);
	
	void sendPasswordToEmail(String logonName,String Email,int pos);
	
}
