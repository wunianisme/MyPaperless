package com.foxconn.paperless.main.function.presenter;

public interface ExceptionManageDetailPresenter {

	void init(int type, String id);

	void getExceptionDetail();

	void pass(String backContent);

	void reject(String backContent);

}
