package com.foxconn.paperless.main.model;

import java.io.File;

import android.content.Context;
import android.net.Uri;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;

public interface MainModel extends Response{
	//首頁Fragment
	/**
	 * 獲得廠區和事業群下的所有BU
	 * @param checkParam
	 */
	void getAllBU(Params params);
	//賬戶Fragment
	/**
	 * 從服務器下載頭像圖片
	 * @param accountPresenterImpl
	 * @param downloadUrl
	 * @param imageFilePath
	 */
	void getHeadPortrait(FinalHttpCallback callback,
			String downloadUrl, String imageFilePath);
	/**
	 * 上傳頭像到服務器
	 * @param accountPresenterImpl
	 * @param uploadUrl
	 * @param file
	 */
	void uploadHeadPortrait(FinalHttpCallback callback,
			String uploadUrl, File file);
	/**
	 * 獲取系統圖片資源路徑
	 * @param context
	 * @param uri
	 * @return
	 */
	String ImageMediaData(Context context, Uri uri);
	/**
	 * 获得二维码对应的报表输入类型（输工单/不输工单）
	 * @param params
	 */
	void getQRReportInputType(Params params);
	/**
	 * 獲得該時段的點檢記錄（有則表示已被點檢）
	 * @param params
	 */
	void getCheckRecord(Params params);
	/**
	 * 獲得各菜單的消息條數
	 * @param mainParam
	 */
	void getMsgNum(Params mainParam);

}
