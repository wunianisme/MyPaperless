package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.ParamManage;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ParameterManageModel;
import com.foxconn.paperless.main.function.model.ParameterManageModelImpl;
import com.foxconn.paperless.main.function.view.ParameterManageAuditDetailView;
/**
 * 參數簽核詳細信息邏輯處理
 *@ClassName ParameterManageAuditDetailPresenterImpl
 *@Author wunian
 *@Date 2018/1/15 下午2:01:12
 */
public class ParameterManageAuditDetailPresenterImpl implements
		ParameterManageAuditDetailPresenter, OnModelListener {
	
	private ParameterManageAuditDetailView paramView;
	private Context context;
	private ParameterManageModel paramModel;
	private Euser user;
	private Params params;
	private String reportNum;
	private int type;//簽核類型
	private ParamInfo paramInfo;
	private List<String> paramList;//詳細信息列表
	private String updateType;//修改類型
	private String checkState;//簽核狀態
	
	public ParameterManageAuditDetailPresenterImpl(ParameterManageAuditDetailView paramView,
			Context context){
		this.paramView=paramView;
		this.context=context;
		this.paramModel=new ParameterManageModelImpl(this);
		this.user=(Euser) context.getApplicationContext();
		this.params=new Params(paramModel);
		this.reportNum=ReportNum.SMT_PRINTER;
		this.paramList=new ArrayList<String>();
	}

	@Override
	public void success(JsonResult result) {
		paramView.dismissLoading();
		if(result.getAction().equals(Action.GET_PARAMAUDIT_DETAILINFO)){
			paramList=result.getData();
			showAuditParamDetail();
		}
		if(result.getAction().equals(Action.PARAM_CHECKPASS)){
			paramView.showToastSuccessMsg(R.string.audit_success);
			paramView.back();
		}
		if(result.getAction().equals(Action.PARAM_CHECKFAILED)){
			paramView.showToastSuccessMsg(R.string.audit_success);
			paramView.back();
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_PARAMAUDIT_DETAILINFO)){
			paramView.showToastFailedMsg(R.string.getauditinfo_failed);
		}
		if(result.getAction().equals(Action.PARAM_CHECKPASS)){
			paramView.showToastFailedMsg(R.string.audit_failed);
		}
		if(result.getAction().equals(Action.PARAM_CHECKFAILED)){
			paramView.showToastFailedMsg(R.string.audit_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		paramView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 顯示簽核參數詳細內容
	 */
	private void showAuditParamDetail() {
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			paramView.showSMTPrinterParam(paramList);
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			paramView.showSMTReflowParam(paramList);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			paramView.showPTHWSParam(paramList);
		}
		paramView.showCheckStatus(updateType,checkState);
	}

	@Override
	public void init(String reportNum, int type, ParamInfo paramInfo) {
		this.reportNum=reportNum;
		this.type=type;
		this.paramInfo=paramInfo;
		updateType=context.getResources().getString(R.string.update);//默認為修改類型
		if(paramInfo.getUpdateType().equals(ParamManage.UPDATETYPE_ADD)){//添加
			updateType=context.getResources().getString(R.string.add);
		}
		if(paramInfo.getUpdateType().equals(ParamManage.UPDATETYPE_DELETE)){//刪除
			updateType=context.getResources().getString(R.string.delete);
		}
		checkState=context.getResources().getString(R.string.check_pass);//默認簽核通過
		if(paramInfo.getCheckState().equals(ParamManage.CHECKSTATE_REVIEW)){//待簽核
			checkState=context.getResources().getString(R.string.check_review);
		}
		if(paramInfo.getCheckState().equals(ParamManage.CHECKSTATE_FAILED)){//駁回
			checkState=context.getResources().getString(R.string.check_failed);
		}
		int btnVisible=View.GONE;
		if(type==ParamManage.AUDITTYPE_MYCHECK//當處於待簽核狀態且切換成我的簽核菜單時，顯示通過和駁回按鈕
				&&paramInfo.getCheckState().equals(ParamManage.CHECKSTATE_REVIEW)){
			btnVisible=View.VISIBLE;
		}
		//顯示不同參數表對應的佈局
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			paramView.showBaseView(View.VISIBLE,View.GONE,View.GONE,btnVisible);
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			paramView.showBaseView(View.GONE,View.VISIBLE,View.GONE,btnVisible);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			paramView.showBaseView(View.GONE,View.GONE,View.VISIBLE,btnVisible);
		}
	}
	/**
	 * 獲得簽核詳細信息
	 */
	@Override
	public void getAuditDetailInfo() {
		params=ParamsUtil.getParam(params, Action.GET_PARAMAUDIT_DETAILINFO, new String[]{
			reportNum,
			paramInfo.getParameterNum(),
			paramInfo.getUpdateType(),
			paramInfo.getCheckState()
		});
		paramModel.getAuditDetailInfo(params);
		paramView.showLoading();
	}
	/**
	 * 提交簽核通過
	 */
	@Override
	public void pass() {
		if(paramList.size()<1){
			paramView.showToastFailedMsg(R.string.auditfailed_noparaminfo);
			return;
		}
		String createDate = getCreateDate();
		params=ParamsUtil.getParam(params, Action.PARAM_CHECKPASS, new String[]{
			reportNum,
			paramInfo.getParameterNum(),
			createDate,
			user.getLogonName()
		});
		paramModel.checkPass(params);
		paramView.showLoading();
	}
	/**
	 * 提交簽核駁回
	 */
	@Override
	public void failed() {
		if(paramList.size()<1){
			paramView.showToastFailedMsg(R.string.auditfailed_noparaminfo);
			return;
		}
		String createDate = getCreateDate();
		params=ParamsUtil.getParam(params, Action.PARAM_CHECKFAILED, new String[]{
			reportNum,
			paramInfo.getParameterNum(),
			createDate,
			user.getLogonName()
		});
		paramModel.checkFailed(params);
		paramView.showLoading();
	}
	/**
	 * 獲得簽核信息創建時間
	 * @return
	 */
	private String getCreateDate() {
		String createDate="";
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			createDate=paramList.get(11);
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			createDate=paramList.get(21);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			createDate=paramList.get(28);
		}
		return createDate;
	}
}
