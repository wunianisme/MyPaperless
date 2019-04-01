package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
/**
 * 報表配置業務處理
 *@ClassName ReportConfigModelImpl
 *@Author wunian
 *@Date 2018/1/5 下午5:08:58
 */
public class ReportConfigModelImpl implements ReportConfigModel {

	private OnModelListener onModelListener;
	private WebServiceConnect connect;
	
	public ReportConfigModelImpl(OnModelListener onModelListener){
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
	public void getMFGReport(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getCheckItem(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getConfigProId(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getSBU(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void saveReportConfig(Params params) {
		connect.getCommonServerData(params);
	}


}
