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
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.ParamManage;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.helper.ParameterManagePresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ParameterManageModel;
import com.foxconn.paperless.main.function.model.ParameterManageModelImpl;
import com.foxconn.paperless.main.function.view.ParameterManageAuditDetailActivity;
import com.foxconn.paperless.main.function.view.ParameterManageAuditView;
/**
 * 提交參數簽核邏輯處理
 *@ClassName ParameterManageAuditPresenterImpl
 *@Author wunian
 *@Date 2018/1/15 上午8:22:50
 */
public class ParameterManageAuditPresenterImpl implements
		ParameterManageAuditPresenter, OnModelListener {
	
	private ParameterManageAuditView paramView;
	private Context context;
	private ParameterManageModel paramModel;
	private Euser user;
	private Params params;
	private String reportNum;
	private int type;
	private List<ParamInfo> paramInfoList;//參數基本信息列表
	
	private final static int QUESTCODE_PARAMDETAIL=10;
	
	public ParameterManageAuditPresenterImpl(ParameterManageAuditView paramView,
			Context context){
		this.paramView=paramView;
		this.context=context;
		this.paramModel=new ParameterManageModelImpl(this);
		this.user=(Euser) context.getApplicationContext();
		this.params=new Params(paramModel);
		this.reportNum=ReportNum.SMT_PRINTER;
		this.paramInfoList=new ArrayList<ParamInfo>();
		this.type=ParamManage.AUDITTYPE_MYCHECK;
	}

	@Override
	public void success(JsonResult result) {
		paramView.dismissLoading();
		if(result.getAction().equals(Action.GET_PARAMMYSUBMIT_INFO)){
			paramInfoList=ParameterManagePresenterHelper.getAuditParamInfo(result);
			paramView.setSubmitInfoAdapter(paramInfoList);
		}
		if(result.getAction().equals(Action.GET_PARAMMYCHECK_INFO)){
			paramInfoList=ParameterManagePresenterHelper.getAuditParamInfo(result);
			paramView.setCheckInfoAdapter(paramInfoList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_PARAMMYSUBMIT_INFO)){
			paramView.showToastFailedMsg(R.string.getsubmitinfo_failed);
			paramInfoList.clear();
			paramView.refreshSubmitInfoAdapter();
		}
		if(result.getAction().equals(Action.GET_PARAMMYCHECK_INFO)){
			paramView.showToastFailedMsg(R.string.getcheckinfo_failed);
			paramInfoList.clear();
			paramView.refreshCheckInfoAdapter();
		}
	}

	@Override
	public void exception(JsonResult result) {
		paramView.showToastExceptionMsg(result.getResultMsg());
	}

	@Override
	public void init(String reportNum) {
		this.reportNum=reportNum;
	}
	/**
	 * 設置簽核類型
	 */
	@Override
	public void setAuditType(int type) {
		this.type=type;
		
	}
	/**
	 * 獲得我提交的參數信息
	 */
	@Override
	public void getMySubmitInfo() {
		params =ParamsUtil.getParam(params, Action.GET_PARAMMYSUBMIT_INFO, new String[]{
				reportNum,
				user.getLogonName()
		});
		paramModel.getMySubmitInfo(params);
		paramView.showLoading();
	}
	/**
	 * 獲得我簽核的參數信息
	 */
	@Override
	public void getMyCheckInfo() {
		params =ParamsUtil.getParam(params, Action.GET_PARAMMYCHECK_INFO, new String[]{
				reportNum,
				user.getLogonName()
		});
		paramModel.getMyCheckInfo(params);
		paramView.showLoading();
	}
	/**
	 * 跳轉到詳細簽核信息頁面
	 */
	@Override
	public void goAuditDetailPage(int pos) {
		Bundle bundle=new Bundle();
		bundle.putString("reportNum", reportNum);
		bundle.putInt("type", type);
		bundle.putSerializable("paramInfo", paramInfoList.get(pos));
		paramView.gotoActivityForResult(ParameterManageAuditDetailActivity.class,bundle,
				QUESTCODE_PARAMDETAIL);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case QUESTCODE_PARAMDETAIL:
			if(resultCode==Activity.RESULT_OK){//簽核完成后重新獲取簽核狀態信息
				if(type==ParamManage.AUDITTYPE_MYCHECK){
					getMyCheckInfo();
				}
				if(type==ParamManage.AUDITTYPE_MYSUBMIT){
					getMySubmitInfo();
				}
			}
			break;

		default:
			break;
		}
		
	}

}
