package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface ReportConfigModel {
	/**
	 * 	獲得報表點檢項
	 * @param p
	 * @return
	 */
	JsonResult getReportCheckItem(MyParam p);
	/**
	 * 獲得該SBU報表已配置的PROID
	 * @param p
	 * @return
	 */
	JsonResult getConfigProId(MyParam p);
	
	/**
	 * 獲得製造處下的所有已配置的SBU
	 * @param p
	 * @return
	 */
	JsonResult getSBU(MyParam p);
	/**
	 * 保存報表配置信息
	 * @param p
	 * @return
	 */
	JsonResult saveReportConfig(MyParam p);
	
}
