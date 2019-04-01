package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.audit.CheckResult;
import com.foxconn.paperless.editimage.ZoomImageActivity;

public interface AuditCheckResultView extends BaseView {
	/**
	 * 設置點檢詳細內容的適配器
	 * @param checkResultList
	 */
	void setCheckResultAdapter(List<CheckResult> checkResultList);
	/**
	 * 顯示簽核人姓名
	 * @param checkByName
	 * @param checkStatus
	 */
	void showCheckByName(String checkByName,String checkStatus,int textColor);
	/**
	 * 顯示暫無點檢數據佈局
	 * @param reportName
	 * @param checkId
	 * @param reason
	 */
	void showNoDataLayout(String reportName, String checkId, String reason);
	/**
	 * 顯示通過/駁回按鈕
	 */
	void showSubmitBtn();
	/**
	 * 顯示拒簽原因
	 * @param string
	 */
	void showRejectRemark(String remark);
	/**
	 * 結束當前界面
	 */
	void back();
	/**
	 * Activity跳轉
	 * @param class1
	 * @param bundle
	 */
	void gotoActivity(Class<?> class1, Bundle bundle);

}
