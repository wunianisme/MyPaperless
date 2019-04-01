package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;

public class CheckSearchModelImpl implements CheckSearchModel {

	private OnModelListener onModelListener;
	private WebServiceConnect connect;
	
	public CheckSearchModelImpl(OnModelListener onModelListener){
		this.onModelListener=onModelListener;
		this.connect=new WebServiceConnect();
	}

	@Override
	public void onSuccess(JsonResult result) {
		if(result.getResultCode()==ResultCode.TRUE){
			onModelListener.success(result);
		}
		if(result.getResultCode()==ResultCode.NULL){
			onModelListener.failed(result);
		}
	}

	@Override
	public void onError(JsonResult result) {
		onModelListener.exception(result);
	}

	@Override
	public void getFloorName(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getLineName(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getCheckStatus(Params params) {
		connect.getCommonServerData(params);
	}
}
