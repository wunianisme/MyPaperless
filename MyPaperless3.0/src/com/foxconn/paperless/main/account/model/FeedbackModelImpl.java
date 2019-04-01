package com.foxconn.paperless.main.account.model;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
/**
 * 意見反饋相關業務數據處理
 *@ClassName FeedbackModelImpl
 *@Author wunian
 *@Date 2017/10/27 下午3:08:01
 */
public class FeedbackModelImpl implements FeedbackModel {
	
	private OnModelListener onModelListener;
	private WebServiceConnect webServiceConnect;
	
	public FeedbackModelImpl(OnModelListener onModelListener){
		this.onModelListener=onModelListener;
		this.webServiceConnect=new WebServiceConnect();
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
	public void getHistoryFeedback(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public void publishFeedback(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

}
