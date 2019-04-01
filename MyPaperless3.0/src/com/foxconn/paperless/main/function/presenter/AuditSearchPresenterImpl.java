package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.bean.audit.AuditQueryBy;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.CheckType;
import com.foxconn.paperless.helper.AuditSearchPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.AuditSearchModel;
import com.foxconn.paperless.main.function.model.AuditSearchModelImpl;
import com.foxconn.paperless.main.function.view.AuditBaseInfoActivity;
import com.foxconn.paperless.main.function.view.AuditBatchActivity;
import com.foxconn.paperless.main.function.view.AuditSearchView;
import com.foxconn.paperless.qr.CaptureActivity;
import com.foxconn.paperless.util.TextUtil;
/**
 * 審核查詢邏輯處理
 * @author nwe
 *
 */
public class AuditSearchPresenterImpl implements AuditSearchPresenter,OnModelListener {

	private AuditSearchView auditSearchView;
	private AuditSearchModel auditSearchModel;
	private Context context;
	private Euser user;
	private Params params;
	private List<ReportInfo> reportInfoList;//報表信息列表
	private List<String> reportNameList;//報表名稱列表
	private List<CheckInfo> checkInfoList;//查詢的點檢信息
	private AuditQueryBy auditQueryBy;//條件查詢參數
	private String reportName;//選擇的報表名
	private int reportIndex;//選擇的報表索引
	private String checkType;
	//查詢條件（對應sp:auditQuery）
	public final static String QUERYBY_ALL="all";
	public final static String QUERYBY_RNO="RNO";
	public final static String QUERYBY_DATE="date";
	public final static String QUERYBY_WO="wo";
	public final static String QUERYBY_LINENAME="linename";
	public final static String QUERYBY_SKUNO="skuno";
	
	public final static int REQUESTCODE_SCAN=10;
	public final static int REQUESTCODE_AUDITBUNDLE=20;
	
	public AuditSearchPresenterImpl(AuditSearchView auditSearchView,Context context){
		this.auditSearchView=auditSearchView;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.auditSearchModel=new AuditSearchModelImpl(this);
		this.params=new Params(auditSearchModel);
		this.checkType=CheckType.REVIEW;
		this.auditQueryBy=new AuditQueryBy();
		this.reportNameList=new ArrayList<String>();
		this.checkInfoList=new ArrayList<CheckInfo>();
		this.reportInfoList=new ArrayList<ReportInfo>();
	}

	@Override
	public void success(JsonResult result) {
		auditSearchView.dismissLoading();
		if(result.getAction().equals(Action.GET_MFG_REPORT)){//獲取歷史記錄查詢報表
			setReportAdapter(result);
			//默認查詢出第一個報表的數據
			auditSearchView.setDefaultReportName(result.getData().get(1));
			auditQueryBy.setReportNum(result.getData().get(0));
			search("");
		}
		//掃碼二維碼獲得去簽核信息記錄
		if(result.getAction().equals(Action.GET_AUDIT_INFO_BYQRCODE)){
			checkInfoList=AuditSearchPresenterHelper.getCheckInfoList(result);
			auditSearchView.setAuditSearchAdapter(checkInfoList);
		}
		if(result.getAction().equals(Action.GET_AUDIT_REPORT)){//獲取未簽核/已簽核/拒簽報表
			setReportAdapter(result);
			//查詢所有滿足條件的報表數據
			auditSearchView.setDefaultReportName("");
			auditQueryBy.setReportNum("");
			search("");
		}
		if(result.getAction().equals(Action.GET_AUDIT_INFO)){//獲取歷史/未簽核/已簽核/拒簽簽核信息記錄
			checkInfoList=AuditSearchPresenterHelper.getCheckInfoList(result);
			auditSearchView.setAuditSearchAdapter(checkInfoList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		auditSearchView.dismissLoading();
		if(result.getAction().equals(Action.GET_MFG_REPORT)){
			auditSearchView.showToastFailedMsg(R.string.getmfgreport_failed);//未獲取到報表(歷史)
			clearReportAdapter();
		}
		if(result.getAction().equals(Action.GET_AUDIT_INFO_BYQRCODE)){
			auditSearchView.showToastFailedMsg(R.string.qrcode_notexist);//二維碼信息錯誤
			clearAuditSearchAdapter();
		}
		if(result.getAction().equals(Action.GET_AUDIT_REPORT)){
			auditSearchView.showToastFailedMsg(R.string.getmfgreport_failed);//未獲取到報表（未簽核/已簽核/拒簽）
			clearReportAdapter();
		}
		if(result.getAction().equals(Action.GET_AUDIT_INFO)){
			auditSearchView.showToastFailedMsg(R.string.getaudit_failed);//暫無簽核信息
			clearAuditSearchAdapter();
		}
	}

	@Override
	public void exception(JsonResult result) {
		auditSearchView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 設置報表適配器數據
	 * @param result
	 */
	private void setReportAdapter(JsonResult result){
		reportInfoList=AuditSearchPresenterHelper.getMFGReport(result);
		reportNameList=AuditSearchPresenterHelper
				.getReportName(reportInfoList);
		auditSearchView.setReportAdapter(reportNameList);
	}
	/**
	 * 清除報表適配器數據，更新適配器
	 */
	private void clearReportAdapter(){
		if(reportNameList.size()>0){
			reportNameList.clear();
			auditSearchView.refreshReportAdapter();
		}
	}
	/**
	 * 清除簽核查詢信息適配器數據，更新適配器
	 */
	private void clearAuditSearchAdapter(){
		if(checkInfoList.size()>0){
			checkInfoList.clear();
			auditSearchView.refreshAuditSearchAdapter();
		}
	}
	/**
	 * 設置查詢類型
	 */
	@Override
	public void setQueryType(String checkType) {
		this.checkType=checkType;
		//清除適配器數據
		clearReportAdapter();
		clearAuditSearchAdapter();
		//重新獲取報表信息
		getMFGReport();
	}
	/**
	 * 設置查詢條件
	 */
	@Override
	public void setQueryBy(String queryBy) {
		auditQueryBy.setQueryBy(queryBy);
	}
	/**
	 * 選擇日期（必須是選擇按時間查詢）
	 */
	@Override
	public void selectDate() {
		if(auditQueryBy.getQueryBy().equals(QUERYBY_DATE)){
			int[] date=TextUtil.getdate();
			auditSearchView.setDatePickDialog(date[0],date[1], date[2]);
		}
	}
	/**
	 * 獲取報表
	 */
	@Override
	public void getMFGReport() {
		if(checkType.equals(CheckType.RECORD)){//歷史記錄查詢/獲取製造處下面的所有已導入的報表
			params=ParamsUtil.getParam(params, Action.GET_MFG_REPORT, new String[]{
				user.getBu(),
				user.getMfg()
			});
			auditSearchModel.getMFGReport(params);
		}else{
			String checkRelease = null;
			String checkReject = null;
			if(checkType.equals(CheckType.REVIEW)){
				checkRelease="0";
				checkReject="0";
			}
			if(checkType.equals(CheckType.AUDITED)){
				checkRelease="1";
				checkReject="0";
			}
			if(checkType.equals(CheckType.REJECT)){
				checkRelease="1";
				checkReject="1";
			}
			params=ParamsUtil.getParam(params, Action.GET_AUDIT_REPORT, new String[]{
					user.getLogonName(),
					checkRelease,
					checkReject
			});
			auditSearchModel.getAuditReport(params);
			auditSearchView.showLoading();
		}
	}
	/**
	 * 選擇報表
	 */
	@Override
	public void selectReport(int pos) {
		reportIndex=pos;
		auditQueryBy.setReportNum(reportInfoList.get(pos).getReportNum());
	}
	/**
	 * 設置報表編號
	 */
	@Override
	public void setReportNum(String reportName) {
		reportName=TextUtil.simpleToTradition(reportName);
		String reportNum=AuditSearchPresenterHelper.getReportNum(reportName,reportInfoList);
		auditQueryBy.setReportNum(reportNum);
		this.reportName=reportName;
	}
	/**
	 * 
	 * 獲取報表名稱集合數據，彈出選擇報表的選擇框
	 */
	@Override
	public void getReportList() {
		if(reportNameList.size()>0){
			auditSearchView.showSelectReportDialog(reportNameList);
		}else{
			auditSearchView.showToastFailedMsg(R.string.getmfgreport_failed);
		}
	}

	/**
	 * 查詢
	 */
	@Override
	public void search(String where) {
		//String reportNum=reportInfoList.get(reportIndex).getReportNum();
		if(TextUtil.isEmpty(auditQueryBy.getReportNum())){//沒輸入報表名或暫無該報表信息
			if(checkType.equals(CheckType.RECORD)){//歷史記錄必須輸報表名
				auditSearchView.showToastFailedMsg(R.string.reportname_empty);
				return;
			}
			auditQueryBy.setReportNum("");
		}
		if(TextUtil.isEmpty(where)){//如果查詢內容為空，默認查詢所有記錄
			auditQueryBy.setQueryBy(QUERYBY_ALL);
		}else{
			if(auditQueryBy.getQueryBy().equals(QUERYBY_ALL)){//有數據，按點檢編號查詢
				auditQueryBy.setQueryBy(QUERYBY_RNO);
				auditQueryBy.setRNO(where);
			}
			else if(auditQueryBy.getQueryBy().equals(QUERYBY_RNO)){//按點檢編號查詢
				auditQueryBy.setRNO(where);
			}
			else if(auditQueryBy.getQueryBy().equals(QUERYBY_DATE)){//按時間查詢
				auditQueryBy.setCreateDate(where);
			}
			else if(auditQueryBy.getQueryBy().equals(QUERYBY_WO)){//按工單查詢
				auditQueryBy.setWorkorderNo(where);
			}
			else if(auditQueryBy.getQueryBy().equals(QUERYBY_LINENAME)){//按線別查詢
				auditQueryBy.setLineName(where);
			}
			else if(auditQueryBy.getQueryBy().equals(QUERYBY_SKUNO)){//按機種查詢
				auditQueryBy.setSkuNo(where);
			}
			
		}
		String[] param=auditQueryBy.getAuditParam(checkType, user.getMfg(),
				user.getLogonName());
		params=ParamsUtil.getParam(params, Action.GET_AUDIT_INFO,param);
		auditSearchModel.getAuditInfo(params);
		auditSearchView.showLoading();
	}
	/**
	 * 掃碼輸入查詢條件
	 */
	@Override
	public void scanQR() {
		auditSearchView.gotoActivityForResult(CaptureActivity.class, null,
				REQUESTCODE_SCAN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK){
			switch (requestCode) {
			case REQUESTCODE_SCAN://掃碼輸入查詢條件
				String where=data.getStringExtra("result");
				auditSearchView.inputWhere(where);//將二維碼信息輸入到輸入框中
				searchByQRCode(where);
				break;
			case REQUESTCODE_AUDITBUNDLE://批量簽核完成
				setQueryType(CheckType.REVIEW);
				break;
			default:
				break;
			}
		}
	}
	/**
	 * 根據二維碼查詢點檢記錄 add in 2018/2/7
	 * @param where
	 */
	private void searchByQRCode(String where){
		params=ParamsUtil.getParam(params, Action.GET_AUDIT_INFO_BYQRCODE,
				new String[]{where});
		auditSearchModel.getAuditInfoByQRCode(params);
		auditSearchView.showLoading();
	}
	
	/**
	 * 進入簽核基本信息頁面
	 */
	@Override
	public void goAuditBaseInfoPage(int position) {
		String reportNum=checkInfoList.get(position).getReportNum();
		reportName=AuditSearchPresenterHelper.getReportNameByReportNum(reportNum, reportInfoList);
		Bundle bundle =new Bundle();
		bundle.putString("RNO", checkInfoList.get(position).getRNO());
		bundle.putString("reportNum",reportNum);
		bundle.putString("reportName",reportName);
		bundle.putString("checkType", checkType);
		auditSearchView.gotoActivity(AuditBaseInfoActivity.class,bundle);
	}
	/**
	 * 進入批量簽核頁面
	 */
	@Override
	public void goAuditBatchPage() {
		//Bundle bundle=new Bundle();
		//（長度有限，當待簽核數據量大的時候會出現異常）
		//bundle.putSerializable("checkInfoList", (Serializable) checkInfoList);
		//將待簽核信息儲存到靜態變量中代替bundle傳值	update in 2018/4/11
		MyConstant.CHECKINFOLIST=checkInfoList;
		auditSearchView.gotoActivityForResult(AuditBatchActivity.class, null, 
				REQUESTCODE_AUDITBUNDLE);
	}
	
}
