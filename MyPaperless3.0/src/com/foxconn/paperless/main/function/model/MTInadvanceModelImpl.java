package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
/**
 * 提前維護業務處理
 *@ClassName MTInadvanceModelImpl
 *@Author wunian
 *@Date 2018/1/16 下午5:25:45
 */
public class MTInadvanceModelImpl implements MTInadvanceModel {

	private OnModelListener onModelListener;
	private WebServiceConnect connect;
	
	public MTInadvanceModelImpl(OnModelListener onModelListener){
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
	public void getQRReportInputType(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getCheckRecord(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getMTFloor(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getMTLine(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void saveMT(Params params) {
		connect.getCommonServerData(params);
	}

}
