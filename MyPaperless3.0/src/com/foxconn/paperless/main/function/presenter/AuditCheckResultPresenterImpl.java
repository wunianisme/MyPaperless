package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.bean.audit.CheckResult;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.CheckType;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.editimage.ZoomImageActivity;
import com.foxconn.paperless.helper.AuditSearchPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.AuditSearchModel;
import com.foxconn.paperless.main.function.model.AuditSearchModelImpl;
import com.foxconn.paperless.main.function.view.AuditBaseInfoView;
import com.foxconn.paperless.main.function.view.AuditCheckResultView;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 詳細點檢信息邏輯處理
 *@ClassName AuditCheckResultPresenterImpl
 *@Author wunian
 *@Date 2018/1/4 上午11:20:08
 */
public class AuditCheckResultPresenterImpl implements AuditCheckResultPresenter,OnModelListener {

	private Context context;
	private AuditCheckResultView auditCheckResultView;
	private AuditSearchModel auditSearchModel;
	private Params params;
	private Euser user;
	private List<CheckResult> checkResultList;//點檢信息集合
	private String checkType;//查詢類型
	private String reportNum;//報表編號
	private String reportName;//報表名
	private String RNO;//點檢編號
	private String checkId;//節次
	private String checkSeqno;//簽核序列
	private String checkRemark;//備註
	private String checkRemarkReason;//備註原因
	private String checkBy;//當前簽核人工號
	private String checkStatus;//簽核狀態：待簽核/已簽核
	private String preCheckStatus;//前一個簽核人的簽核狀態
	private String preCheckId;//前一個簽核人的節次
	
	public AuditCheckResultPresenterImpl(AuditCheckResultView auditCheckResultView,
			Context context){
		this.auditCheckResultView=auditCheckResultView;
		this.context=context;
		this.auditSearchModel=new AuditSearchModelImpl(this);
		this.params=new Params(auditSearchModel);
		this.user=(Euser) context.getApplicationContext();
		this.checkResultList=new ArrayList<CheckResult>();
	}

	@Override
	public void success(JsonResult result) {
		auditCheckResultView.dismissLoading();
		if(result.getAction().equals(Action.GET_AUDIT_CHECKRESULT)){
			checkResultList=AuditSearchPresenterHelper.getCheckResult(result);
			auditCheckResultView.setCheckResultAdapter(checkResultList);
			showSubmitBtn();
			
		}
		if(result.getAction().equals(Action.GET_REJECT_REMARK)){//獲得拒簽原因
			auditCheckResultView.showRejectRemark(result.getData().get(0));
		}
		if(result.getAction().equals(Action.UPDATE_AUDIT_RESULT)){//簽核成功
			auditCheckResultView.showToastSuccessMsg(R.string.submitaudit_success);
			auditCheckResultView.back();//返回上一個頁面
		}
	}

	@Override
	public void failed(JsonResult result) {
		auditCheckResultView.dismissLoading();
		if(result.getAction().equals(Action.GET_AUDIT_CHECKRESULT)){//無點檢數據
			auditCheckResultView.showNoDataLayout(reportName,checkId,
					checkRemark+"（"+checkRemarkReason+"）");//顯示暫無數據佈局
			showSubmitBtn();
		}
		if(result.getAction().equals(Action.GET_REJECT_REMARK)){//獲得拒簽原因失敗
			auditCheckResultView.showToastFailedMsg(R.string.getrejectremark_failed);
		}
		if(result.getAction().equals(Action.UPDATE_AUDIT_RESULT)){//簽核失敗
			auditCheckResultView.showToastFailedMsg(R.string.submitaudit_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		auditCheckResultView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 顯示提交按鈕
	 */
	private void showSubmitBtn(){
		//ToastHelper.showInfo(context, preCheckStatus+"-"+pre, 0);
		if(checkType.equals(CheckType.REVIEW)
				&&user.getLogonName().equalsIgnoreCase(checkBy)){//待簽核列表並且簽核人為當前用戶
			String auditWait=context.getResources().getString(R.string.audit_wait);//待簽核
			if(auditWait.equals(checkStatus)){//如果是待簽核列表
				//add in 2018/03/15 只有當簽核人為第一個或前一個簽核人簽核狀態為已簽核/拒簽情況下才顯示通過/駁回按鈕
				if(preCheckId.equals(checkId)){//同一節次
					if(!preCheckStatus.equals("0")){//0：待簽核，1：已簽核，-1：拒簽
						auditCheckResultView.showSubmitBtn();
					}
				}else{//不同節次不用考慮前一個簽核人的簽核狀態（IPQC點檢）
					auditCheckResultView.showSubmitBtn();
				}
			}	
		}else{
			String auditReject=context.getResources().getString(R.string.audit_reject);//拒簽
			if(auditReject.equals(checkStatus)){//獲得拒簽原因
				params=ParamsUtil.getParam(params, Action.GET_REJECT_REMARK, new String[]{
					RNO,checkId,checkSeqno	
				});
				auditSearchModel.getRejectRemark(params);
			}
		}
	}
	/**
	 * 獲得點檢詳細信息
	 */
	@Override
	public void getCheckResult(String checkType, String reportNum,String reportName, String RNO,
			String checkId,String checkSeqno, String checkRemark, String checkRemarkReason,
			String checkBy,String checkByName,String checkStatus,String preCheckStatus,String preCheckId) {
		this.checkType=checkType;
		this.reportNum=reportNum;
		this.reportName=reportName;
		this.RNO=RNO;
		this.checkId=checkId;
		this.checkSeqno=checkSeqno;
		this.checkRemark=checkRemark;
		this.checkRemarkReason=checkRemarkReason;
		this.checkBy=checkBy;
		this.checkStatus=checkStatus;
		this.preCheckStatus=preCheckStatus;
		this.preCheckId=preCheckId;
		String auditOK=context.getResources().getString(R.string.audit_ok);//已簽核
		int textColor=context.getResources().getColor(R.color.red);
		if(checkStatus.equals(auditOK)){//已簽核顯示綠色字體
			textColor=context.getResources().getColor(R.color.green);
		}
		auditCheckResultView.showCheckByName(checkByName,checkStatus,textColor);//顯示簽核狀態信息
		//獲得點檢詳細信息
		params=ParamsUtil.getParam(params, Action.GET_AUDIT_CHECKRESULT, new String[]{
				reportNum,RNO,checkId
		});
		auditSearchModel.getAuditCheckResult(params);
		auditCheckResultView.showLoading();
	}
	/**
	 * 提交通過點檢人員提交的點檢信息
	 */
	@Override
	public void submitPass() {
		//點檢通過的欄位標記
		String checkRelease="1";
		String checkReject="0";
		String remark="";//拒簽原因，此處不需要填
		submitAuditResult(checkRelease, checkReject, remark);
	}
	/**
	 * 提交駁回點檢人員提交的點檢信息
	 */
	@Override
	public void submitReject(String remark) {
		//點檢拒簽的欄位標記
		String checkRelease="1";
		String checkReject="1";
		if(TextUtil.isEmpty(remark)){
			auditCheckResultView.showToastFailedMsg(R.string.remark_empty);
			return;
		}
		remark=TextUtil.simpleToTradition(remark);
		submitAuditResult(checkRelease, checkReject,remark);
	}
	/**
	 * 提交用戶簽核結果
	 * @param checkRelease
	 * @param checkReject
	 * @param remark
	 */
	private void submitAuditResult(String checkRelease,String checkReject,String remark){
		params=ParamsUtil.getParam(params,Action.UPDATE_AUDIT_RESULT, new String[]{
				checkRelease,checkReject,remark,RNO,checkId,checkBy
		});
		auditSearchModel.submitAuditResult(params);//提交簽核結果
		auditCheckResultView.showLoading();
	}
	/**
	 * 縮放圖片
	 */
	@Override
	public void goZoomImagePage(String imageData) {
		Bundle bundle=new Bundle();
		bundle.putString("imageData", imageData);
		auditCheckResultView.gotoActivity(ZoomImageActivity.class,bundle);
	}
}
