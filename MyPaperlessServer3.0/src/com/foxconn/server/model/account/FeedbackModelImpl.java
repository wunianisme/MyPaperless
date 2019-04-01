package com.foxconn.server.model.account;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.dao.ServiceDao;
/**
 * 處理意見反饋業務邏輯
 * @author NSD
 *
 */
public class FeedbackModelImpl implements FeedbackModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public FeedbackModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}

	@Override
	public JsonResult getFeedback(MyParam p) {
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql());
		return jsonResult;
	}

	@Override
	public JsonResult saveFeedback(MyParam p) {
		String logonName=p.getParams().get(0).toString();
		String name=p.getParams().get(1).toString();
		String ext=p.getParams().get(2).toString();
		String email=p.getParams().get(3).toString();
		String site=p.getParams().get(4).toString();
		String BG=p.getParams().get(5).toString();
		String feedback=p.getParams().get(6).toString();
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(), logonName,name,ext,email,site,BG,feedback);
		return jsonResult;
	}
	
}
