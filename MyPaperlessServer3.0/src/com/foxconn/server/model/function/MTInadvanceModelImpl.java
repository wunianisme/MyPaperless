package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.dao.ServiceDao;

public class MTInadvanceModelImpl implements MTInadvanceModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public MTInadvanceModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}

	@Override
	public JsonResult getMTFloor(MyParam p) {
		String BU=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),BU,MFG);
		return jsonResult;
	}

	@Override
	public JsonResult getMTLine(MyParam p) {
		String floorName=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),floorName,MFG);
		return jsonResult;
	}
	
	@Override
	public JsonResult getMTCheckRecord(MyParam p) {
		String yeildFlag=p.getParams().get(0).toString();
		String reportNum=p.getParams().get(1).toString();
		String MFG=p.getParams().get(2).toString();
		String floorName=p.getParams().get(3).toString();
		String equipment=p.getParams().get(4).toString();
		String createDate=p.getParams().get(5).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),
				yeildFlag,reportNum,MFG,floorName,equipment,createDate);
		return jsonResult;
	}

	@Override
	public JsonResult saveMT(MyParam p) {
		////'Night','LH','NSDI','MFGI','D9 3F','D93FS1B','','2016/12/07','admin'
		String shift=p.getParams().get(0).toString();
		String site=p.getParams().get(1).toString();
		String BU=p.getParams().get(2).toString();
		String MFG=p.getParams().get(3).toString();
		String floorName=p.getParams().get(4).toString();
		String lineName=p.getParams().get(5).toString();
		String mtDate=p.getParams().get(6).toString();
		String createBy=p.getParams().get(7).toString();
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(),
				shift,site,BU,MFG,floorName,lineName,mtDate,createBy);
		/*if(jsonResult.getResultCode()!=ResultCode.TRUE){
			return jsonResult;
		}
		//sp返回一個值：1：成功，0：失敗
		int resultCode=Integer.parseInt(jsonResult.getData().get(0).toString().trim());
		jsonResult.setResultCode(resultCode);//重新設置結果碼
		*/
		return jsonResult;
	}

	
}
