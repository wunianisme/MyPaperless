package com.foxconn.paperless.main.check.view;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Euser;

public interface ReportCheckView extends BaseView {
	/**
	 * 顯示或隱藏頂部報表信息
	 * @param visibility
	 * @param icon
	 */
	void toggleTopInfo(int visibility,int icon);
	/**
	 * 顯示或隱藏頂部報表信息(不輸工單)
	 * @param visibility
	 * @param icon
	 */
	void toggleNoInputTopInfo(int visibility, int icon);

	/**
	 * 設置頂部標題
	 * @param reportName
	 */
	void setPageTitle(String reportName);
	/**
	 * 設置PD或IPQC輸工單點檢點檢編號，線別（含樓層）
	 * @param RNO
	 * @param lineName
	 */
	void setCheckPdInputRNO(String RNO, String lineName);
	/**
	 * 設置不輸工單報表
	 * @param rNO
	 * @param floorName
	 * @param equipment
	 */
	void setCheckNoInputRNO(String RNO, String floorName, String equipment);
	/**
	 * 設置輸工單點檢報表基本信息
	 * @param skuNo
	 * @param skuVersion
	 * @param qty
	 * @param deviation
	 */
	void setBaseInfo(String skuNo, String skuVersion, String qty,
			String deviation);
	/**
	 * 顯示節次
	 */
	void showCheckId();
	/**
	 * 顯示報表頂部界面
	 * @param checkInputVisibility
	 * @param checkNoInputVisibility
	 */
	void showTopLayout(int checkInputVisibility,int checkNoInputVisibility);
	/**
	 * 設置點檢項目的適配器
	 * @param checkItemList
	 */
	void setCheckItemAdapter(List<CheckItem> checkItemList);
	/**
	 * 設置IPQC節次信息
	 * @param checkNode
	 */
	void setCheckNode(String checkNode);
	/**
	 * 跳轉到另一個Activity，并返回數據
	 * @param cls
	 * @param bundle
	 * @param requestCode
	 */
	void gotoActivityForResult(Class<?> cls,Bundle bundle, int requestCode);
	/**
	 * 掃碼工單輸入
	 * @param wO
	 */
	void setWO(String WO);
	/**
	 * 設置面別信息
	 * @param side
	 */
	void setSide(String side);
	/**
	 * 顯示帶出標準參數成功的提示文字
	 * @param visibility
	 */
	void showGetParamsSuccessTip(int visibility);
	/**
	 * 顯示提示框
	 * @param titleId
	 * @param msgId
	 */
	void showTipDialog(int titleId, int msgId);
	/**
	 * 顯示提示框（檢驗）
	 * @param titleId
	 * @param message
	 */
	void showTipDialog(int titleId, String message);
	/**
	 * 獲取點檢數據
	 * @return
	 */
	List<CheckItem> getCheckItemList();
	/**
	 * 清除EditText焦點
	 */
	void clearFocus();
	/**
	 * 設置具有簽核權限人員信息列表的適配器(提交異常)
	 * @param checkByList
	 */
	void setCheckByAdapter(List<Euser> checkByList);
	/**
	 * 設置具有簽核權限人員信息列表的適配器（提交點檢）
	 * @param checkByList
	 */
	void setSubmitCheckAdapter(List<Euser> checkByList);
	/**
	 * 設置已選簽核人員信息列表的適配器(提交異常)
	 * @param selectedCheckByList
	 */
	void setSelectedCheckByAdapter(List<Euser> selectedCheckByList);
	/**
	 * 設置已選簽核人員信息列表的適配器（提交點檢）
	 * @param selectedCheckByList
	 */
	void setSubmitSelectedCheckAdapter(List<Euser> selectedCheckByList);
	/**
	 * 關閉選擇簽核人彈出框（提交異常）
	 */
	void dismissSelectCheckByDialog();
	/**
	 * 顯示提交異常的彈出框
	 * @param title
	 * @param selectedCheckByList
	 */
	void showSubmitExceptionDialog(String title,List<Euser> selectedCheckByList);
	/**
	 * Activity跳轉并回傳數據
	 * @param intent
	 * @param requestCode
	 */
	void goActivityForResult(Intent intent, int requestCode);
	/**
	 * 設置圖片縮略圖的適配器
	 * @param bitmapList
	 */
	void setBitmapAdapter(List<Bitmap> bitmapList);
	/**
	 * 關閉提交異常彈出框
	 */
	void dismissSubmitExceptionDialog();
	/**
	 * 退出
	 */
	void cancel();
	/**
	 * 掃碼輸入點檢內容
	 * @param tag
	 * @param checkContent
	 */
	void setCheckContent(int tag, String checkContent);
	/**
	 * 設置點檢拍照的圖標
	 * @param tag
	 * @param path
	 */
	void setTakePhotoBitmap(int tag, String path);
	/**
	 * 顯示選擇簽核人的彈出框（提交點檢）
	 * @param title
	 * @param btnOK
	 * @param btnNo
	 */
	void showCheckByDialog(String title, int btnOK,
			int btnNo);
	/**
	 * 關閉點檢提交彈出框
	 */
	void dismissSubmitCheckDialog();
	/**
	 *  顯示保存的輸入內容
	 * @param workorderNo
	 */
	void showSaveInputInfo(String workorderNo);
	/**
	 * 刷新點檢項數據
	 */
	void notifyCheckItemDataSetChanged();
	/**
	 * 顯示備註下拉框
	 * @param checkRemarkArray
	 */
	void showCheckRemarkSpinner(String[] checkRemarkArray);
	
	
	

	
	
	
	
}
