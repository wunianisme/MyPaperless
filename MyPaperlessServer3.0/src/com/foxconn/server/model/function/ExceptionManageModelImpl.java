package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.constant.MyEnum.Exception;
import com.foxconn.server.dao.ServiceDao;
/**
 * 異常管理邏輯處理
 * @author F1331847
 *
 */
public class ExceptionManageModelImpl implements ExceptionManageModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public ExceptionManageModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}

	@Override
	public JsonResult getExceptionInfo(MyParam p) {
		int type=Integer.parseInt(p.getParams().get(0).toString());
		String logonName=p.getParams().get(1).toString();
		int step=MySql.STEP_1;
		if(type==Exception.MYDEAL){
			type=MySql.STEP_1;
		}
		if(type==Exception.MYCREATE){
			type=MySql.STEP_2;
		}
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), step), logonName);
		return jsonResult;
	}

	@Override
	public JsonResult getExceptionDetailInfo(MyParam p) {
		int type=Integer.parseInt(p.getParams().get(0).toString());
		String id=p.getParams().get(1).toString();
		int step=MySql.STEP_1;
		if(type==Exception.MYDEAL){
			type=MySql.STEP_1;
		}
		if(type==Exception.MYCREATE){
			type=MySql.STEP_2;
		}
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), step), id);
		return jsonResult;
	}

	@Override
	public JsonResult updateExceptionDealState(MyParam p) {
		String dealState=p.getParams().get(0).toString();
		String backContent=p.getParams().get(1).toString();
		String id=p.getParams().get(2).toString();
		jsonResult=serviceDao.update(p.getDatabase(),p.getSql(),dealState,backContent,id);
		return jsonResult;
	}
}
