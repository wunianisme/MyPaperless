package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.audit.CheckInfo;

public interface AuditBaseInfoView extends BaseView {
	/**
	 * 設置簽核基本信息列表適配器
	 * @param checkInfoList
	 */
	void setAuditBaseInfoAdapter(List<CheckInfo> checkInfoList);
	
	/**
	 * Activity跳轉
	 * @param class1
	 * @param bundle
	 */
	void gotoActivity(Class<?> class1, Bundle bundle) ;
	/**
	 * Activity跳轉，回傳數據
	 * @param class1
	 * @param bundle
	 * @param requestCode
	 */
	void gotoActivityForResult(Class<?> class1,
			Bundle bundle, int requestCode);

}
