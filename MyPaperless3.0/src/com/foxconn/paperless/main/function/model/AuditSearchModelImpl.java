package com.foxconn.paperless.main.function.model;


import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
/**
 * 審核查詢業務數據處理
 * @author hjx
 *
 */
public class AuditSearchModelImpl implements AuditSearchModel {

	private OnModelListener onModelListener;
	private WebServiceConnect connect;
	
	public AuditSearchModelImpl(OnModelListener onModelListener){
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
	public void getAuditReport(Params params) {
		connect.getCommonServerData(params);
	}
	
	@Override
	public void getAuditInfoByQRCode(Params params) {
		connect.getCommonServerData(params);
	}
	@Override
	public void getAuditInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getAuditBaseInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getAuditCheckResult(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getRejectRemark(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void submitAuditResult(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void submitAuditPass(Params params) {
		connect.getCommonServerData(params);
	}

	

}
