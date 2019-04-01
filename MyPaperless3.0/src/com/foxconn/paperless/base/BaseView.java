package com.foxconn.paperless.base;

public interface BaseView {
	

	void showToastSuccessMsg(int strId);
	
	void showToastFailedMsg(int strId);
	
	void showToastExceptionMsg(String msg);
	
	void showLoading();
	
	void dismissLoading();
}
