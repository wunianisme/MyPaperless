package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.helper.AuditSearchPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.AuditSearchModel;
import com.foxconn.paperless.main.function.model.AuditSearchModelImpl;
import com.foxconn.paperless.main.function.view.AuditBatchView;
/**
 * 批量簽核邏輯處理
 *@ClassName AuditBatchPresenterImpl
 *@Author wunian
 *@Date 2018/2/5 上午10:06:21
 */
public class AuditBatchPresenterImpl implements AuditBatchPresenter,OnModelListener {
	
	private AuditBatchView view;
	private AuditSearchModel model;
	private Context context;
	private Euser user;
	private Params params;
	private List<CheckInfo> checkInfoList;//查詢的點檢信息
	private List<CheckInfo> selectCheckInfoList;//選中的點檢信息
	
	
	public AuditBatchPresenterImpl(AuditBatchView view,Context context){
		this.view=view;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.model=new AuditSearchModelImpl(this);
		this.params=new Params(model);
		this.checkInfoList=new ArrayList<CheckInfo>();
		this.selectCheckInfoList=new ArrayList<CheckInfo>();
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.UPDATE_AUDIT_PASS)){
			view.showToastSuccessMsg(R.string.submitaudit_success);
			view.back();
		}
		
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.UPDATE_AUDIT_PASS)){
			view.showToastSuccessMsg(R.string.submitaudit_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		view.showToastExceptionMsg(result.getResultMsg());
	}
	
	@Override
	public void init(List<CheckInfo> checkInfoList) {
		this.checkInfoList=checkInfoList;
		view.setAuditBatchAdapter(checkInfoList);
	}

	@Override
	public void selectAll(boolean checked) {
		checkInfoList=AuditSearchPresenterHelper.getSelectAll(checkInfoList,checked);
		view.setAuditBatchAdapter(checkInfoList);
		
	}
	/**
	 * 顯示簽核通過彈出框
	 */
	@Override
	public void showPassDialog() {
		selectCheckInfoList=AuditSearchPresenterHelper.getSelectCheckInfo(checkInfoList);
		if(selectCheckInfoList.size()<1){
			view.showToastFailedMsg(R.string.select_audititem);
			return;
		}
		int titleId=R.string.audit_tip;
		String message=context.getResources().getString(R.string.auditbatchpass_msg)+
				"（"+selectCheckInfoList.size()+"）";	
		
		view.showPassDialog(titleId,message,R.string.btn_ok,R.string.btn_no);
	}

	@Override
	public void auditPass() {
		String RNOStr=AuditSearchPresenterHelper.getRNOStr(selectCheckInfoList);
		params=ParamsUtil.getParam(params, Action.UPDATE_AUDIT_PASS, new String[]{
			RNOStr,user.getLogonName()	
		});
		model.submitAuditPass(params);
		view.showLoading();
	}

	

}
