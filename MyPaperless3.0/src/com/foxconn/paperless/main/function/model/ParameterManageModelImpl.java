package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
/**
 * 參數管理業務處理
 *@ClassName ParameterManageModelImpl
 *@Author wunian
 *@Date 2018/1/8 下午3:19:07
 */
public class ParameterManageModelImpl implements ParameterManageModel {

	private OnModelListener onModelListener;
	private WebServiceConnect connect;
	
	public ParameterManageModelImpl(OnModelListener onModelListener){
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
	public void getParamInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getParamDetail(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getCheckMaster(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void updateParam(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getMySubmitInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getMyCheckInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getAuditDetailInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void checkPass(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void checkFailed(Params params) {
		connect.getCommonServerData(params);
	}

}
