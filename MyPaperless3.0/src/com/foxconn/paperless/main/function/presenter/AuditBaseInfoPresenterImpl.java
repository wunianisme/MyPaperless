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
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.helper.AuditSearchPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.AuditSearchModel;
import com.foxconn.paperless.main.function.model.AuditSearchModelImpl;
import com.foxconn.paperless.main.function.view.AuditBaseInfoView;
import com.foxconn.paperless.main.function.view.AuditCheckResultActivity;
/**
 * 簽核基本信息邏輯處理
 *@ClassName AuditBaseInfoPresenterImpl
 *@Author wunian
 *@Date 2018/1/4 上午10:44:16
 */
public class AuditBaseInfoPresenterImpl implements AuditBaseInfoPresenter,
	OnModelListener {

	private Context context;
	private AuditBaseInfoView auditBaseInfoView;
	private AuditSearchModel auditSearchModel;
	private Params params;
	private Euser user;
	private List<CheckInfo> checkInfoList;
	private String RNO;
	private String reportNum;
	private String reportName;
	private String checkType;
	private final static int REQUESTCODE_CHECKRESULT=1;
	
	public AuditBaseInfoPresenterImpl(AuditBaseInfoView auditBaseInfoView,
			Context context){
		this.auditBaseInfoView=auditBaseInfoView;
		this.context=context;
		this.auditSearchModel=new AuditSearchModelImpl(this);
		this.params=new Params(auditSearchModel);
		this.user=(Euser) context.getApplicationContext();
		this.checkInfoList=new ArrayList<CheckInfo>();
	}

	@Override
	public void success(JsonResult result) {
		auditBaseInfoView.dismissLoading();
		if(result.getAction().equals(Action.GET_AUDIT_BASEINFO)){
			checkInfoList=AuditSearchPresenterHelper.getBaseInfo(result);
			checkInfoList.get(0).setReportNum(reportNum);
			checkInfoList.get(0).setReportName(reportName);
			auditBaseInfoView.setAuditBaseInfoAdapter(checkInfoList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_AUDIT_BASEINFO)){
			auditBaseInfoView.showToastFailedMsg(R.string.getbaseinfo_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		auditBaseInfoView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲得簽核基本信息
	 */
	@Override
	public void getAuditBaseInfo(String RNO, String reportNum, String reportName,
			String checkType) {
		this.RNO=RNO;
		this.reportNum=reportNum;
		this.reportName=reportName;
		this.checkType=checkType;
		params=ParamsUtil.getParam(params,Action.GET_AUDIT_BASEINFO,new String[]{RNO});
		auditSearchModel.getAuditBaseInfo(params);
		auditBaseInfoView.showLoading();
	}
	/**
	 * 跳轉至詳細點檢信息頁面
	 */
	@Override
	public void goAuditCheckResultPage(int pos,String checkId,String checkStatus) {
		//獲得簽核人姓名集合
		ArrayList<String> checkByNameList=(ArrayList<String>) 
				AuditSearchPresenterHelper.getCheckByName(checkInfoList);
		//add in 2018/03/15 獲得前一個簽核人的簽核狀態，如果該簽核人還未簽核，則當前簽核人將不能簽核
		String preCheckStatus=Report.DEFAULT_VALUE;//當簽核人為第一個時，前一個簽核人簽核狀態為“N/A”
		String preCheckId=checkId;//前一個簽核人的簽核節次
		if(pos>0){
			preCheckStatus=checkInfoList.get(pos-1).getCheckStatus();
			preCheckId=checkInfoList.get(pos-1).getCheckId();
		}
		
		Bundle bundle=new Bundle();
		bundle.putString("checkType", checkType);
		bundle.putString("reportNum", reportNum);
		bundle.putString("reportName", reportName);
		bundle.putString("RNO", RNO);
		bundle.putString("checkId",checkId);
		bundle.putString("checkSeqno",checkInfoList.get(pos).getCheckSeqno());
		bundle.putString("checkRemark",checkInfoList.get(pos).getCheckRemark());
		bundle.putString("checkRemarkReason", checkInfoList.get(pos).getCheckRemarkReason());
		bundle.putString("checkBy", checkInfoList.get(pos).getCheckBy());
		bundle.putString("checkByName", checkInfoList.get(pos).getCheckByName());
		bundle.putString("checkStatus",checkStatus);//當前條目簽核人的簽核狀態
		bundle.putString("preCheckStatus",preCheckStatus);//前一個簽核人的簽核狀態
		bundle.putString("preCheckId",preCheckId);//前一個簽核人簽核的節次
		//auditBaseInfoView.gotoActivity(AuditCheckResultActivity.class,bundle );
		auditBaseInfoView.gotoActivityForResult(AuditCheckResultActivity.class,bundle,REQUESTCODE_CHECKRESULT );
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_CHECKRESULT:
			if(resultCode==Activity.RESULT_OK){//簽核完成后,重新加載數據
				getAuditBaseInfo(RNO, reportNum, reportName, checkType);
			}
			break;

		default:
			break;
		}
	}
	
	
}
