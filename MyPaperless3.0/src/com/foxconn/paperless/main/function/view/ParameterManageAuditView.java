package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ParamInfo;

public interface ParameterManageAuditView extends BaseView {
	/**
	 * 設置我提交的參數內容的適配器
	 * @param paramInfoList
	 */
	void setSubmitInfoAdapter(List<ParamInfo> paramInfoList);
	/**
	 * 設置我簽核的參數內容的適配器
	 * @param paramInfoList
	 */
	void setCheckInfoAdapter(List<ParamInfo> paramInfoList);
	/**
	 * 刷新我提交的參數內容的適配器
	 */
	void refreshSubmitInfoAdapter();
	/**
	 * 刷新我簽核的參數內容的適配器
	 */
	void refreshCheckInfoAdapter();
	/**
	 * Activity跳轉
	 * @param cls
	 * @param bundle
	 */
	void gotoActivity(Class<?> cls,Bundle bundle);
	/**
	 * Activity跳轉，回傳數據
	 * @param cls
	 * @param bundle
	 */
	void gotoActivityForResult(
			Class<?> cls, Bundle bundle,int requestCode);

}
