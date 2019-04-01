package com.foxconn.paperless.main.model;

import java.io.File;

import android.content.Context;
import android.net.Uri;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.http.WebServiceConnect;
import com.foxconn.paperless.util.ContentProviderUtil;
import com.foxconn.paperless.util.FinalHttpManager;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;

public class MainModelImpl implements MainModel {

	private OnModelListener onModelListener;
	private WebServiceConnect webServiceConnect;
	
	public MainModelImpl(OnModelListener onModelListener){
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
	public void getAllBU(Params params) {
		webServiceConnect.getCommonServerData(params);
	}
	@Override
	public void getHeadPortrait(FinalHttpCallback callback, String downloadUrl,
			String imageFilePath) {
		new FinalHttpManager(callback).download(downloadUrl, imageFilePath);
	}

	@Override
	public void uploadHeadPortrait(FinalHttpCallback callback,
			String uploadUrl, File file) {
		new FinalHttpManager(callback).uploadHeadPortrait(uploadUrl, file);
		
	}

	@Override
	public String ImageMediaData(Context context, Uri uri) {
		return ContentProviderUtil.getImageMediaData(context, uri);
	}

	@Override
	public void getQRReportInputType(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getCheckRecord(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getMsgNum(Params params) {
		webServiceConnect.getCommonServerData(params);
	}
	
}
