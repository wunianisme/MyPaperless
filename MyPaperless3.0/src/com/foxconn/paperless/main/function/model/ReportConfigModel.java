package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;

public interface ReportConfigModel extends Response{
	/**
	 * 獲得MFG下所有已導入的報表信息
	 * @param params
	 */
	void getMFGReport(Params params);
	/**
	 * 獲得SBU下的報表點檢項
	 * @param params
	 */
	void getCheckItem(Params params);
	/**
	 * 獲得該SBU報表已配置的PROID
	 * @param params
	 */
	void getConfigProId(Params params);
	/**
	 * 獲得製造處下的所有已配置的SBU
	 * @param params
	 */
	void getSBU(Params params);
	/**
	 * 保存報表配置信息
	 * @param params
	 */
	void saveReportConfig(Params params);

}
