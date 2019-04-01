package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface MTInadvanceModel {
	/**
	 * 獲得樓層
	 * @param p
	 * @return
	 */
	JsonResult getMTFloor(MyParam p);
	/**
	 * 獲得線體
	 * @param p
	 * @return
	 */
	JsonResult getMTLine(MyParam p);
	/**
	 * 獲得提前維護/補點檢點檢記錄
	 */
	JsonResult getMTCheckRecord(MyParam p);
	/**
	 * 提交提前維護信息
	 * @param p
	 * @return
	 */
	JsonResult saveMT(MyParam p);
	
	

}
