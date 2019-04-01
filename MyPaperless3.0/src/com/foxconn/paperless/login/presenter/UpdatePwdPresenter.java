package com.foxconn.paperless.login.presenter;

public interface UpdatePwdPresenter {

	void checkUpdate(String oldPassword,String newPassword,String checkNewPassword);
}
