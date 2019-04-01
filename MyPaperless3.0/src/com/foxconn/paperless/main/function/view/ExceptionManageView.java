package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ExceptionFeedback;

public interface ExceptionManageView extends BaseView {
	/**
	 * 設置我的處理適配器
	 * @param exceptionList
	 */
	void setMyDealAdapter(List<ExceptionFeedback> exceptionList);
	/**
	 * 設置我的創建適配器
	 * @param exceptionList
	 */
	void setMyCreateAdapter(List<ExceptionFeedback> exceptionList);
	/**
	 * Activity跳轉
	 * @param cls
	 * @param bundle
	 * @param requestCode
	 */
	void gotoActivityForResult(Class<?> cls, Bundle bundle,int requestCode);

}
