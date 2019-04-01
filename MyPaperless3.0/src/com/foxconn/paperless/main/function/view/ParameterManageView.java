package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.main.view.MainActivity;
import com.foxconn.paperless.qr.CaptureActivity;

public interface ParameterManageView extends BaseView {
	/**
	 * 設置樓層適配器
	 * @param data
	 */
	void setFloorNameAdapter(List<String> floorNameList);
	/**
	 * 設置線體適配器
	 * @param data
	 */
	void setLineNameAdapter(List<String> lineNameList);
	/**
	 * 設置參數基本信息的適配器
	 * @param paramInfoList
	 */
	void setParamInfoAdapter(List<ParamInfo> paramInfoList);
	/**
	 * Activity跳轉
	 * @param cls
	 * @param bundle
	 */
	void gotoActivity(Class<?> cls, Bundle bundle);
	/**
	 * Activity跳轉回傳數據
	 * @param cls
	 * @param Bundle
	 * @param requestCode
	 */
	void gotoActivityForResult(Class<?> cls, Bundle Bundle,
			int requestCode);
	/**
	 * 輸入機種
	 * @param skuNo
	 */
	void inputSkuNo(String skuNo);
	/**
	 * 顯示簽核消息按鈕
	 * @param gone
	 */
	void showAuditMessageView(int gone);
	

}
