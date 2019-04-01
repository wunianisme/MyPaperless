package com.foxconn.paperless.main.account.model;

import java.util.List;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.helper.AccountModelHelper;
import com.foxconn.paperless.http.WebServiceConnect;

/**
 * 賬戶业务数据处理
 *@ClassName AccountModelImpl
 *@Author wunian
 *@Date 2017/10/12 下午4:27:48
 */
public class AccountModelImpl implements AccountModel {

	private OnModelListener onModelListener;
	private WebServiceConnect webServiceConnect;
	private JsonResult SBUResult;
	private JsonResult teamResult;
	private JsonResult sectionResult;
	
	private JsonResult employeeInfoResult;

	public AccountModelImpl(OnModelListener onModelListener){
		this.onModelListener=onModelListener;
		webServiceConnect=new WebServiceConnect();
	}

	@Override
	public void onSuccess(JsonResult result) {
		if(result.getResultCode()==ResultCode.TRUE){
			//賬號信息頁面Action
			if(result.getAction().equals(Action.GET_SBU_ITEM)){
				SBUResult=result;//將SBU返回結果暫時存在SBUResult
			}
			if(result.getAction().equals(Action.GET_TEAM_ITEM)){
				teamResult=result;//將Team返回結果暫時存在teamResult
			}
			if(result.getAction().equals(Action.GET_SECTION_ITEM)){
				sectionResult=result;//將section返回結果暫時存在sectionResult
			}
			//員工信息頁面Action
			if(result.getAction().equals(Action.GET_EMPLOYEEINFO)){
				employeeInfoResult=result;
				result=AccountModelHelper.getSimpleEmployeeInfo(result);//重新篩選出五項基本數據
			}
			
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
	public void getMFGItem(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public void getSBUItem(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public void getTeamItem(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public void getSectionItem(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public List<String> SBUDataChanged(String MFG) {
		return AccountModelHelper.getSBUList(MFG, SBUResult);
	}

	@Override
	public List<String> teamDataChanged(String SBU, String MFG) {
		return AccountModelHelper.getTeamList(SBU, MFG, teamResult);
	}

	@Override
	public List<String> sectionDataChanged(String SBU) {
		return AccountModelHelper.getSectionList(SBU, sectionResult);
	}

	@Override
	public void saveAccountInfo(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public void getEmployeeInfo(Params p) {
		webServiceConnect.getCommonServerData(p);
	}

	@Override
	public Euser getSingleEmployeeInfo(String logonName) {
		return AccountModelHelper.getSingleEmployeeInfo(employeeInfoResult,logonName);
	}

	@Override
	public void saveEmployeeInfo(Params p) {
		webServiceConnect.getCommonServerData(p);
	}
	
}
