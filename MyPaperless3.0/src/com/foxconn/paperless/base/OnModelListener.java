package com.foxconn.paperless.base;

import com.foxconn.paperless.bean.JsonResult;
/**
 * Model業務邏輯回調接口
 *@ClassName OnModelListener
 *@Author wunian
 *@Date 2017/10/9 上午8:28:42
 */
public interface OnModelListener {

	void success(JsonResult result);
	
	void failed(JsonResult result);
	
	void exception(JsonResult result);
}
