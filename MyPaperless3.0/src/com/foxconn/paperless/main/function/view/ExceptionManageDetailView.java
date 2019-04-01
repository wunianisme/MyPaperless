package com.foxconn.paperless.main.function.view;

import java.io.File;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ExceptionFeedback;

public interface ExceptionManageDetailView extends BaseView {
	/**
	 * 顯示底部按鈕佈局
	 * @param btnLayoutVisible
	 * @param picLayoutVisible
	 * @param userNameId
	 */
	void showBtnLayout(int btnLayoutVisible,int picLayoutVisible, int userNameId);
	/**
	 * 顯示異常詳細信息
	 * @param exception
	 * @param dealStateId
	 * @param userName
	 */
	void showExceptionDetail(ExceptionFeedback exception, int dealStateId,
			String userName);
	/**
	 * 退出當前頁面
	 */
	void back();
	/**
	 * 加載圖片
	 * @param absoluteFile
	 * @param index
	 */
	void loadingImage(File absoluteFile,int index);

}
