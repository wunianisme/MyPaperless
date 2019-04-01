package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.dao.ServiceDao;
/**
 * 處理點檢狀態查詢業務邏輯
 * @author foxconn
 *
 */
public class CheckSearchModelImpl implements CheckSearchModel {
	
	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public CheckSearchModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}
	
	@Override
	public JsonResult getFloorName(MyParam p) {
		String BU=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU,MFG);
		return jsonResult;
	}

	@Override
	public JsonResult getLineName(MyParam p) {
		String floorName=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), floorName,MFG);
		return jsonResult;
	}

	@Override
	public JsonResult getCheckStatus(MyParam p) {
		String checkFlag=p.getParams().get(0).toString();//點檢頻率（day/week/month/quarter）
		String BU=p.getParams().get(1).toString();
		String MFG=p.getParams().get(2).toString();//製造處
		String createDate=p.getParams().get(3).toString();//點檢日期
		String shift=p.getParams().get(4).toString();//班別
		String lineName=p.getParams().get(5).toString();//線別
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), checkFlag,BU,MFG,createDate,shift,lineName);
		return jsonResult;
	}
}
