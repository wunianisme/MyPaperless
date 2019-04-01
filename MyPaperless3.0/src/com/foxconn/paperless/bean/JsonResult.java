package com.foxconn.paperless.bean;

import java.util.List;
/**
 * 服务器返回的JSON数据bean
 *@ClassName JsonResult
 *@Author wunian
 *@Date 2017/9/1 下午3:09:29
 */
public class JsonResult {

	private String action;
	
	private int resultCode;
	
	private String resultMsg;
	
	private List<String>  data;
	
	private int columnNum;
	
	public JsonResult(){}
	
	public JsonResult(String action,int resultCode, String resultMsg, List<String> data,int columnNum) {
		this.action=action;
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.data = data;
		this.columnNum=columnNum;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	@Override
	public String toString() {
		return "JsonResult [action=" + action + ", resultCode=" + resultCode
				+ ", resultMsg=" + resultMsg + ", data=" + data
				+ ", columnNum=" + columnNum + "]";
	}
}
