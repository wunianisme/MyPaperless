package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface CheckSearchModel {
	/**
	 * 獲得製造處下面的樓層
	 * @param p
	 * @return
	 */
	JsonResult getFloorName(MyParam p);
	/**
	 * 獲得製造處樓層下面的線別
	 * @param p
	 * @return
	 */
	JsonResult getLineName(MyParam p);
	/**
	 * 獲得同一個製造處下某天的報表點檢狀態
	 * @param p
	 * @return
	 */
	JsonResult getCheckStatus(MyParam p);

	
}
