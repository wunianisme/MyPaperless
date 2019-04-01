package com.foxconn.server.model.function;

import java.util.Arrays;
import java.util.List;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyEnum.Exception;
import com.foxconn.server.constant.MyEnum.ReportConfig;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
/**
 * 報表配置業務處理
 * @author foxconn
 *
 */
public class ReportConfigModelImpl implements ReportConfigModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public ReportConfigModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}

	@Override
	public JsonResult getReportCheckItem(MyParam p) {
		String site=p.getParams().get(0).toString();
		String BU=p.getParams().get(1).toString();
		String reportNum=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), site,BU,reportNum);
		return jsonResult;
	}

	@Override
	public JsonResult getConfigProId(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String site=p.getParams().get(1).toString();
		String BU=p.getParams().get(2).toString();
		String MFG=p.getParams().get(3).toString();
		String SBU=p.getParams().get(4).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),reportNum, site,BU,MFG,SBU);
		return jsonResult;
	}

	@Override
	public JsonResult getSBU(MyParam p) {
		//String reportNum=p.getParams().get(0).toString();
		String site=p.getParams().get(0).toString();
		String BU=p.getParams().get(1).toString();
		String MFG=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), site,BU,MFG);
		return jsonResult;
	}

	@Override
	public JsonResult saveReportConfig(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String site=p.getParams().get(1).toString();
		String BU=p.getParams().get(2).toString();
		String MFG=p.getParams().get(3).toString();
		String SBU=p.getParams().get(4).toString();
		List<String> proIdList=Arrays.asList(p.getParams().get(5).toString().split(ReportConfig.SPLIT));
		//先刪除之前的數據
		jsonResult=serviceDao.update(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_1),
				reportNum,site,BU,MFG,SBU);
		for (int i = 0; i < proIdList.size(); i++) {//插入數據
			jsonResult=serviceDao.update(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_2),
					reportNum,proIdList.get(i),MFG,SBU,site,BU);
		}
		return jsonResult;
	}
	
}
