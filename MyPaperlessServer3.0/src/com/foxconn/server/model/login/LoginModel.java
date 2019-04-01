package com.foxconn.server.model.login;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
/**
 * 處理登陸業務邏輯
 * @author NSD
 *
 */
public interface LoginModel {
	
	/**
	 * 获得厂区服务器信息
	 * @param p
	 * @return
	 */
	JsonResult getServer(MyParam p);
	/**
	 * 登陆校验
	 * @param p
	 * @return
	 */
	JsonResult checkLogin(MyParam p);
	/**
	 * 修改密碼
	 * @param p
	 * @return
	 */
	JsonResult updatePwd(MyParam p);
	/**
	 * 獲得用戶郵箱地址
	 * @param p
	 * @return
	 */
	JsonResult getEmail(MyParam p);
	/**
	 * 找回密碼
	 * @param p
	 * @return
	 */
	JsonResult findPassword(MyParam p);
	/**
	 * 獲得服務器更新的APP信息
	 * @param p
	 * @return
	 */
	JsonResult getAppInfo(MyParam p);
	/**
	 * 保存手機下載信息記錄
	 * @param p
	 * @return
	 */
	JsonResult saveDownloadInfo(MyParam p);
}
