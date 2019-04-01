package com.foxconn.server.model.account;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.constant.MyEnum.ResultMsg;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
/**
 * 處理賬戶業務邏輯
 * @author NSD
 *
 */
public class AccountModelImpl implements AccountModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public AccountModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}

	@Override
	public JsonResult getMFGItem(MyParam p) {
		String BU=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU);
		return jsonResult;
	}

	@Override
	public JsonResult getSBUItem(MyParam p) {
		String BU=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU);
		return jsonResult;
	}

	@Override
	public JsonResult getTeamItem(MyParam p) {
		String BU=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU);
		return jsonResult;
	}

	@Override
	public JsonResult getSectionItem(MyParam p) {
		String BU=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU);
		return jsonResult;
	}

	@Override
	public JsonResult saveAccountInfo(MyParam p) {
		String ChineseName=p.getParams().get(0).toString();
		String EnglishName=p.getParams().get(1).toString();
		String ext=p.getParams().get(2).toString();
		String email=p.getParams().get(3).toString();
		String team=p.getParams().get(4).toString();
		String section=p.getParams().get(5).toString();
		String SBU=p.getParams().get(6).toString();
		String MFG=p.getParams().get(7).toString();
		String logonName=p.getParams().get(8).toString();
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(), ChineseName,EnglishName,ext,
				email,team,section,SBU,MFG,logonName);
		return jsonResult;
	}

	@Override
	public JsonResult getEmployeeInfo(MyParam p) {
		String MFG=p.getParams().get(0).toString();
		String SBU=p.getParams().get(1).toString();
		String team=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), MFG,SBU,team);
		return jsonResult;
	}

	@Override
	public JsonResult saveEmployeeInfo(MyParam p) {
		String logonName=p.getParams().get(0).toString();
		String password=p.getParams().get(1).toString();
		String ChineseName=p.getParams().get(2).toString();
		String EnglishName=p.getParams().get(3).toString();
		String job=p.getParams().get(4).toString();
		String ext=p.getParams().get(5).toString();
		String email=p.getParams().get(6).toString();
		String userlevel=p.getParams().get(7).toString();
		String master=p.getParams().get(8).toString();
		String team=p.getParams().get(9).toString();
		String section=p.getParams().get(10).toString();
		String SBU=p.getParams().get(11).toString();
		String MFG=p.getParams().get(12).toString();
		String BU=p.getParams().get(13).toString();
		String site=p.getParams().get(14).toString();
		String lasteditby=p.getParams().get(15).toString();
		String BG=p.getParams().get(16).toString();
		
		//首先判斷賬號是否存在
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_1), logonName);
		if(jsonResult.getResultCode()==ResultCode.TRUE){//賬號已存在
			jsonResult.setResultMsg(ResultMsg.ADDEMPLOYEE_EXISIT);
			return jsonResult;
		}
		//不存在才進行插入操作
		jsonResult=serviceDao.update(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_2),
				logonName,password,ChineseName,EnglishName,job,ext,email,userlevel,master,team,section,
				SBU,MFG,BU,site,lasteditby,BG);
		jsonResult.setResultMsg(ResultMsg.ADDEMPLOYEE_SUCCESS);
		return jsonResult;
	}
	
}
