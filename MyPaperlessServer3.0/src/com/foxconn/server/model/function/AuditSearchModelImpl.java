package com.foxconn.server.model.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyAction.Action;
import com.foxconn.server.constant.MyEnum.Report;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
/**
 * 處理簽核查詢業務邏輯
 * @author foxconn
 *
 */
public class AuditSearchModelImpl implements AuditSearchModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public AuditSearchModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}
	
	@Override
	public JsonResult getMessageNum(MyParam p) {
		String MFG=p.getParams().get(0).toString();
		String checkBy=p.getParams().get(1).toString();
		//查詢待簽核點檢記錄條數
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_1),
				MFG,checkBy);
		if(jsonResult.getResultCode()!=ResultCode.TRUE){
			return jsonResult;
		}
		//查詢待簽核參數記錄條數
		JsonResult jsonResult2=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_2), 
				checkBy,checkBy,checkBy);
		if(jsonResult2.getResultCode()!=ResultCode.TRUE){
			return jsonResult;
		}
		//計算三個參數表未簽核記錄的總數
		int totalCount=0;
		for (int i = 0; i < jsonResult2.getData().size(); i++) {
			totalCount+=Integer.parseInt(jsonResult2.getData().get(i));
		}
		jsonResult.getData().add(totalCount+"");//將最後的計算總數添加到結果集中
		//查詢待處理異常記錄條數
		JsonResult jsonResult3=serviceDao.query(p.getDatabase(), 
				MySql.getSQL(p.getAction(), MySql.STEP_3),checkBy);
		if(jsonResult3.getResultCode()!=ResultCode.TRUE){
			return jsonResult;
		}
		jsonResult.getData().add(jsonResult3.getData().get(0));//添加待處理異常記錄條數到結果集中
		return jsonResult;
	}

	@Override
	public JsonResult getMFGReport(MyParam p) {
		String BU=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU,MFG,BU);
		return jsonResult;
	}
	
	@Override
	public JsonResult getAuditReport(MyParam p) {
		String checkBy=p.getParams().get(0).toString();
		String checkRelease=p.getParams().get(1).toString();
		String checkReject=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),checkBy,checkRelease,checkReject);
		return jsonResult;
	}
	
	@Override
	public JsonResult getAuditInfoByQRCode(MyParam p) {
		String qrInfo=p.getParams().get(0).toString();
		//根據二維碼查詢點檢基本信息
		JsonResult result=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_1),qrInfo);
		if(result.getResultCode()!=ResultCode.TRUE){
			return result;
		}
		String reportNum=result.getData().get(0);
		String MFG=result.getData().get(1);
		String floorName=result.getData().get(2);
		String equipment=result.getData().get(3);
		//帶入基本信息帶出具體點檢記錄
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_2),
				reportNum,MFG,floorName,equipment);
		return jsonResult;
	}

	@Override
	public JsonResult getAuditInfo(MyParam p) {
		//@checktype（record） @queryby  @reportNum  @mfg '' @check_Report_No
		//@create_date @workorderno @linename @skuno @rownum(2000)
		String checkType=p.getParams().get(0).toString();//查詢類型（歷史/待簽核/已簽核/拒簽）
		String queryBy=p.getParams().get(1).toString();//按什麼查詢
		String reportNum=p.getParams().get(2).toString();
		String MFG=p.getParams().get(3).toString();
		String checkBy=p.getParams().get(4).toString();//簽核人工號
		String RNO=p.getParams().get(5).toString();
		String createDate=p.getParams().get(6).toString();//點檢時間
		String workorderNo=p.getParams().get(7).toString();
		String lineName=p.getParams().get(8).toString();//設備編號
		String skuNo=p.getParams().get(9).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),checkType,queryBy, reportNum,
				MFG,checkBy,RNO,createDate,workorderNo,lineName,skuNo);
		return jsonResult;
	}

	@Override
	public JsonResult getAuditBaseInfo(MyParam p) {
		String RNO=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),RNO);
		return jsonResult;
	}

	@Override
	public JsonResult getAuditCheckResult(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String RNO=p.getParams().get(1).toString();
		String checkId=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),reportNum,RNO,checkId);
		
		return jsonResult;
	}

	@Override
	public JsonResult getRejectRemark(MyParam p) {
		String RNO=p.getParams().get(0).toString();
		String checkId=p.getParams().get(1).toString();
		String checkSeqno=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),RNO,checkId,checkSeqno);
		return jsonResult;
	}

	@Override
	public JsonResult updateAuditResult(MyParam p) {
		String checkRelease=p.getParams().get(0).toString();
		String checkReject=p.getParams().get(1).toString();
		String remark=p.getParams().get(2).toString();
		String RNO=p.getParams().get(3).toString();
		String checkId=p.getParams().get(4).toString();
		String checkBy=p.getParams().get(5).toString();
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(),checkRelease,checkReject,
				remark,RNO,checkId,checkBy);
		return jsonResult;
	}

	@Override
	public JsonResult updateAuditPass(MyParam p) {
		String RNOStr=p.getParams().get(0).toString();
		String checkBy=p.getParams().get(1).toString();
		List<String> RNOList=Arrays.asList(RNOStr.split(Report.SPLIT));
		for (int i = 0; i < RNOList.size(); i++) {
			jsonResult=serviceDao.update(p.getDatabase(), p.getSql(),
					RNOList.get(i),checkBy);
		}
		return jsonResult;
	}

	
	
}
