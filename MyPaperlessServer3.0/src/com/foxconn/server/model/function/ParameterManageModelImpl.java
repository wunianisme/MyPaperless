package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyEnum.ParamManage;
import com.foxconn.server.constant.MyEnum.ReportNum;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
/**
 * 參數管理業務處理
 * @author foxconn
 *
 */
public class ParameterManageModelImpl implements ParameterManageModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public ParameterManageModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}
	
	@Override
	public JsonResult getParamFloorName(MyParam p) {
		String BU=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		String reportNum=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU,MFG,reportNum);
		return jsonResult;
	}

	@Override
	public JsonResult getParamLineName(MyParam p) {
		String floorName=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		String reportNum=p.getParams().get(2).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), floorName,MFG,reportNum);
		return jsonResult;
	}

	@Override
	public JsonResult getParamInfo(MyParam p) {
		////@type,@Building,@Line,@productName,@side,@parameterNum,@createby,@checkby,@rownum
		String reportNum=p.getParams().get(0).toString();
		String floorName=p.getParams().get(1).toString();
		String lineName=p.getParams().get(2).toString();
		String skuno=p.getParams().get(3).toString();
		String side=p.getParams().get(4).toString();
		String type=ParamManage.SMT_PRINTER_SELECT;
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_PRINTER)){//SMT印刷機參數表
			type=ParamManage.SMT_PRINTER_SELECT;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_REFLOW)){//SMT回焊爐參數表
			type=ParamManage.SMT_REFLOW_SELECT;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.PTH_WS)){//PTH波峰焊參數表
			type=ParamManage.PTH_WS_SELECT;
		}
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), 
				type,floorName,lineName,skuno,side,"","","","","");
		return jsonResult;
	}

	@Override
	public JsonResult getParamDetailInfo(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String paramNum=p.getParams().get(1).toString();
		String type=ParamManage.SMT_PRINTER_SELECT_DETAIL;
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_PRINTER)){
			type=ParamManage.SMT_PRINTER_SELECT_DETAIL;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_REFLOW)){
			type=ParamManage.SMT_REFLOW_SELECT_DETAIL;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.PTH_WS)){
			type=ParamManage.PTH_WS_SELECT_DETAIL;
		}
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), 
				type,"","","","",paramNum,"","","","");
		return jsonResult;
	}

	@Override
	public JsonResult updateSMTPrinterInfo(MyParam p) {

		//@Building @Line @ProductName @Side @ScraperPressure @PrintSpeed
		//@SeparationSpeed @SeparationDistance @ScreenCleanRate @SqueegeeLength  
		//@ProblemDescription @ResultVerification @Create_by @Create_Date @Check_By 
		String paramNum=p.getParams().get(0).toString();
		String building=p.getParams().get(1).toString();
		String line=p.getParams().get(2).toString();
		String productName=p.getParams().get(3).toString();
		String side=p.getParams().get(4).toString();
		String scraperPressure=p.getParams().get(5).toString();
		String printSpeed=p.getParams().get(6).toString();
		String separationSpeed=p.getParams().get(7).toString();
		String separationDistance=p.getParams().get(8).toString();
		String screenCleanRate=p.getParams().get(9).toString();
		String squeegeeLength=p.getParams().get(10).toString();
		String problemDesc=p.getParams().get(11).toString();
		String createBy=p.getParams().get(12).toString();
		String checkBy=p.getParams().get(13).toString();
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(), paramNum,building,line,
				productName,side,scraperPressure,printSpeed,separationSpeed,separationDistance,
				screenCleanRate,squeegeeLength,problemDesc,createBy,checkBy);
		return jsonResult;
	}

	@Override
	public JsonResult updateSMTReflowInfo(MyParam p) {
		/*@ParameterNum varchar(50),
		@Building varchar(50),
		@Line varchar(30),
		@ProductName varchar(50),
		@Side varchar(10),
		@Zone1 varchar(10),@Zone2 varchar(10),@Zone3 varchar(10),@Zone4 varchar(10),@Zone5 varchar(10),@Zone6 varchar(10),@Zone7 varchar(10),
		@Zone8 varchar(10),@Zone9 varchar(10),@Zone10 varchar(10),@Zone11 varchar(10),@Zone12 varchar(10),@Zone13 varchar(10),
		@Speed varchar(15),@FanSpeed varchar(15),
		@ShuntValue varchar(15),
		@ProblemDescription varchar(500)=null,
		@Create_by varchar(20),
		@Check_By varchar(20)*/
		String paramNum=p.getParams().get(0).toString();
		String building=p.getParams().get(1).toString();
		String line=p.getParams().get(2).toString();
		String productName=p.getParams().get(3).toString();
		String side=p.getParams().get(4).toString();
		String zone1=p.getParams().get(5).toString();
		String zone2=p.getParams().get(6).toString();
		String zone3=p.getParams().get(7).toString();
		String zone4=p.getParams().get(8).toString();
		String zone5=p.getParams().get(9).toString();
		String zone6=p.getParams().get(10).toString();
		String zone7=p.getParams().get(11).toString();
		String zone8=p.getParams().get(12).toString();
		String zone9=p.getParams().get(13).toString();
		String zone10=p.getParams().get(14).toString();
		String zone11=p.getParams().get(15).toString();
		String zone12=p.getParams().get(16).toString();
		String zone13=p.getParams().get(17).toString();
		String speed=p.getParams().get(18).toString();
		String fanSpeed=p.getParams().get(19).toString();
		String shuntValue=p.getParams().get(20).toString();
		String problemDesc=p.getParams().get(21).toString();
		String createBy=p.getParams().get(22).toString();
		String checkBy=p.getParams().get(23).toString();
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(), paramNum,building,line,
				productName,side,zone1,zone2,zone3,zone4,zone5,zone6,zone7,zone8,zone9,zone10,
				zone11,zone12,zone13,speed,fanSpeed,shuntValue,problemDesc,createBy,checkBy);
		
		return jsonResult;
	}

	@Override
	public JsonResult updatePTHWSInfo(MyParam p) {
		/*@ParameterNum varchar(60),
		@MFG_Name varchar(20),@SBU varchar(20),@Building varchar(20),@Line varchar(20),
		@ModelName varchar(30),@ProductName varchar(50),@BOTPreheatI varchar(10), @BOTPreheatII varchar(10), 
		@BOTPreheatIII varchar(10),@BOTPreheatIV varchar(10),@TopPreheatI varchar(10), @TopPreheatII varchar(10), 
		@TopPreheatIII varchar(10), @TopPreheatIV varchar(10), @ChainSpeedAfter varchar(10), 
		@ChainSpeed varchar(10),@TinTemperature varchar(10), @TinBathHeight varchar(10),
		@TurbulentWave varchar(10),@AdvectionWave varchar(10), @TinBarType varchar(30), @FluxType varchar(40), 
		@FluxFlow varchar(40),@NozzleAirPressure varchar(10), @TotalAirPressure varchar(10),
		@TankAirPressure varchar(10),@FluxProportion varchar(10),@ProblemDescription varchar(500)=null,
		@Remark varchar(100)，@Create_by varchar(20),@Check_By varchar(20)*/
		String paramNum=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		String SBU=p.getParams().get(2).toString();
		String building=p.getParams().get(3).toString();
		String line=p.getParams().get(4).toString();
		String modelName=p.getParams().get(5).toString();
		String productName=p.getParams().get(6).toString();
		String BI=p.getParams().get(7).toString();
		String BII=p.getParams().get(8).toString();
		String BIII=p.getParams().get(9).toString();
		String BIV=p.getParams().get(10).toString();
		String TI=p.getParams().get(11).toString();
		String TII=p.getParams().get(12).toString();
		String TIII=p.getParams().get(13).toString();
		String TIV=p.getParams().get(14).toString();
		String chainSpeedAfter=p.getParams().get(15).toString();
		String chainSpeed=p.getParams().get(16).toString();
		String TinTemp=p.getParams().get(17).toString();
		String TinBath=p.getParams().get(18).toString();
		String turbulent=p.getParams().get(19).toString();
		String advectionWave=p.getParams().get(20).toString();
		String TinBarType=p.getParams().get(21).toString();
		String fluxType=p.getParams().get(22).toString();
		String fluxFlow=p.getParams().get(23).toString();
		String NozzleAir=p.getParams().get(24).toString();
		String TotalAir=p.getParams().get(25).toString();
		String TankAir=p.getParams().get(26).toString();
		String fluxProp=p.getParams().get(27).toString();
		String problemDesc=p.getParams().get(28).toString();
		String remark=p.getParams().get(29).toString();
		String createBy=p.getParams().get(30).toString();
		String checkBy=p.getParams().get(31).toString();
		
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(), paramNum,MFG,SBU,building,line,
				modelName,productName,BI,BII,BIII,BIV,TI,TII,TIII,TIV,chainSpeedAfter,chainSpeed,
				TinTemp,TinBath,turbulent,advectionWave,TinBarType,fluxType,fluxFlow,NozzleAir,
				TotalAir,TankAir,fluxProp,problemDesc,remark,createBy,checkBy);
		
		return jsonResult;
	}

	@Override
	public JsonResult getParamCheckMaster(MyParam p) {
		String MFG=p.getParams().get(0).toString();
		String team=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), MFG,team);
		return jsonResult;
	}

	@Override
	public JsonResult getParamMySubmitInfo(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String createBy=p.getParams().get(1).toString();
		String type=ParamManage.SMT_PRINTER_SUBMIT;
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_PRINTER)){
			type=ParamManage.SMT_PRINTER_SUBMIT;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_REFLOW)){
			type=ParamManage.SMT_REFLOW_SUBMIT;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.PTH_WS)){
			type=ParamManage.PTH_WS_SUBMIT;
		}
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),type,"","","","","",createBy,"","","");
		return jsonResult;
	}

	@Override
	public JsonResult getParamMyCheckInfo(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String checkBy=p.getParams().get(1).toString();
		String type=ParamManage.SMT_PRINTER_CHECK;
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_PRINTER)){
			type=ParamManage.SMT_PRINTER_CHECK;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.SMT_REFLOW)){
			type=ParamManage.SMT_REFLOW_CHECK;
		}
		if(reportNum.equalsIgnoreCase(ReportNum.PTH_WS)){
			type=ParamManage.PTH_WS_CHECK;
		}
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),type,"","","","","","",checkBy,"","");
		return jsonResult;
	}

	@Override
	public JsonResult getParamAuditDetailInfo(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String paramNum=p.getParams().get(1).toString();
		String updateType=p.getParams().get(2).toString();
		String checkState=p.getParams().get(3).toString();
		String type=ParamManage.SMT_PRINTER_AUDIT_DETAIL;
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			type=ParamManage.SMT_PRINTER_AUDIT_DETAIL;
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			type=ParamManage.SMT_REFLOW_AUDIT_DETAIL;
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			type=ParamManage.PTH_WS_AUDIT_DETAIL;
		}
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),type,"","","","",paramNum,
				"","",updateType,checkState);
		return jsonResult;
	}

	@Override
	public JsonResult paramCheckPass(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String paramNum=p.getParams().get(1).toString();
		String createDate=p.getParams().get(2).toString();
		String checkBy=p.getParams().get(3).toString();
		int step=MySql.STEP_SMTPRINTER;
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			step=MySql.STEP_SMTPRINTER;
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			step=MySql.STEP_SMTREFLOW;
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			step=MySql.STEP_PTHWS;
		}
		jsonResult=serviceDao.update(p.getDatabase(), MySql.getSQL(p.getAction(), step),
				paramNum,createDate,checkBy);
		return jsonResult;
	}

	@Override
	public JsonResult paramCheckFailed(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String paramNum=p.getParams().get(1).toString();
		String createDate=p.getParams().get(2).toString();
		String checkBy=p.getParams().get(3).toString();
		int step=MySql.STEP_SMTPRINTER;
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			step=MySql.STEP_SMTPRINTER;
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			step=MySql.STEP_SMTREFLOW;
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			step=MySql.STEP_PTHWS;
		}
		jsonResult=serviceDao.update(p.getDatabase(), MySql.getSQL(p.getAction(), step),
				paramNum,createDate,checkBy);
		return jsonResult;
	}
}
