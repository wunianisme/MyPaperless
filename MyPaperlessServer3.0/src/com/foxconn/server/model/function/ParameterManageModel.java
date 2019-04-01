package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface ParameterManageModel {
	/**
	 * 獲得參數基本信息
	 * @param p
	 * @return
	 */
	JsonResult getParamInfo(MyParam p);
	/**
	 * 獲得參數表所對應的樓層
	 * @param p
	 * @return
	 */
	JsonResult getParamFloorName(MyParam p);
	/**
	 * 獲得參數表對應線別
	 * @param p
	 * @return
	 */
	JsonResult getParamLineName(MyParam p);
	/**
	 * 獲得參數詳細信息
	 * @param p
	 * @return
	 */
	JsonResult getParamDetailInfo(MyParam p);
	/**
	 * 修改/刪除SMT印刷機參數信息
	 * @param p
	 * @return
	 */
	JsonResult updateSMTPrinterInfo(MyParam p);
	/**
	 * 修改/刪除SMT回焊爐參數信息
	 * @param p
	 * @return
	 */
	JsonResult updateSMTReflowInfo(MyParam p);
	/**
	 * 修改/刪除PTH波峰焊參數信息
	 * @param p
	 * @return
	 */
	JsonResult updatePTHWSInfo(MyParam p);
	/**
	 * 獲得參數提交審核主管信息
	 * @param p
	 * @return
	 */
	JsonResult getParamCheckMaster(MyParam p);
	/**
	 * 獲得我提交的參數內容
	 * @param p
	 * @return
	 */
	JsonResult getParamMySubmitInfo(MyParam p);
	/**
	 * 獲得我要簽核的參數內容
	 * @param p
	 * @return
	 */
	JsonResult getParamMyCheckInfo(MyParam p);
	/**
	 * 獲得參數簽核詳細信息
	 * @param p
	 * @return
	 */
	JsonResult getParamAuditDetailInfo(MyParam p);
	/**
	 * 提交參數簽核通過
	 * @param p
	 * @return
	 */
	JsonResult paramCheckPass(MyParam p);
	/**
	 * 提交參數簽核駁回
	 * @param p
	 * @return
	 */
	JsonResult paramCheckFailed(MyParam p);

}
