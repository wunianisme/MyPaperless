package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
import com.foxconn.paperless.util.FinalHttpManager;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;
/**
 * 異常管理業務處理
 *@ClassName ExceptionManageModelImpl
 *@Author wunian
 *@Date 2018/1/18 下午4:07:22
 */
public class ExceptionManageModelImpl implements ExceptionManageModel {

	private OnModelListener onModelListener;
	private WebServiceConnect connect;
	
	public ExceptionManageModelImpl(OnModelListener onModelListener){
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
	public void getMyExceptionInfo(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void getExceptionDetail(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void submitDealState(Params params) {
		connect.getCommonServerData(params);
	}

	@Override
	public void downloadImg(FinalHttpCallback callback, String downloadUrl,
			String savePath) {
		FinalHttpManager manage=new FinalHttpManager(callback);
		manage.download(downloadUrl, savePath);
		
	}

}
