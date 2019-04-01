package com.foxconn.paperless.main.function.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ParamInfo;

public interface ParameterManageDetailView extends BaseView {
	/**
	 * 顯示參數詳情佈局
	 * @param visible
	 * @param gone
	 * @param gone2
	 */
	void showParamDetailLayout(int SMTPrinterVisible, int SMTReflowVisible, 
			int PTHWSVisible);
	/**
	 * 初始化參數
	 * @param paramInfo
	 */
	void initParam(ParamInfo paramInfo,String side);
	/**
	 * 顯示印刷機參數表詳情
	 * @param paramList
	 */
	void showSMTPrinterParamDetail(List<String> paramList,String side);
	/**
	 * 顯示印刷機參數修改佈局
	 */
	void showSMTPrinterUpdateView();
	/**
	 * 顯示回焊爐參數表詳情
	 * @param paramList
	 */
	void showSMTReflowParamDetail(List<String> paramList,String side);
	/**
	 * 顯示回焊爐參數修改佈局
	 */
	void showSMTReflowUpdateView();
	/**
	 * 顯示波峰焊參數表詳情
	 * @param paramList
	 */
	void showPTHWSParamDetail(List<String> paramList);
	/**
	 * 顯示波峰焊參數修改佈局
	 */
	void showPTHWSUpdateView();
	/**
	 * 顯示參數修改基本佈局
	 * @param floorNameList
	 * @param lineNameList
	 */
	void showBaseUpdateView(List<String> floorNameList,
			List<String> lineNameList,String side);
	/**
	 * 設置side的值
	 * @param side
	 */
	void setSide(String side);
	/**
	 * 顯示底部按鈕佈局
	 * @param visible
	 */
	void showBtnLayout(int visible);
	/**
	 * 顯示提交參數的彈出框
	 * @param titleId
	 * @param btnOK
	 * @param btnNo
	 */
	void showSubmitParamDialog(int titleId, int btnOK,
			int btnNo,List<String> masterNameList);
	/**
	 * 關閉提交參數的彈出框
	 */
	void dismissSubmitParamDialog();
	/**
	 * 提交參數
	 */
	void submitParam();
	
	
	
	
	

}
