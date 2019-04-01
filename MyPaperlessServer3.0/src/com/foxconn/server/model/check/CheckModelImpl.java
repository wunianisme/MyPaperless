package com.foxconn.server.model.check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyEnum.DBFlag;
import com.foxconn.server.constant.MyEnum.EmailAlert;
import com.foxconn.server.constant.MyEnum.Exception;
import com.foxconn.server.constant.MyEnum.ParamConfig;
import com.foxconn.server.constant.MyEnum.Report;
import com.foxconn.server.constant.MyEnum.ReportNum;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.constant.MyEnum.ResultMsg;
import com.foxconn.server.constant.MyEnum.Side;
import com.foxconn.server.constant.MyEnum.Site;
import com.foxconn.server.constant.MyEnum.WS;
import com.foxconn.server.constant.MyParamConfig;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
import com.foxconn.server.email.SendEmailManager;
import com.foxconn.server.resource.Database;
import com.foxconn.server.util.TextUtil;
import com.foxconn.server.util.WebServiceUtil;
/**
 * 處理點檢業務邏輯
 * @author NSD
 *
 */
public class CheckModelImpl implements CheckModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public CheckModelImpl(ServiceDao serviceDao){
		this.serviceDao=serviceDao;
	}

	@Override
	public JsonResult getBU(MyParam p) {
		String site=p.getParams().get(0).toString();
		String BG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), site,BG);
		return jsonResult;
	}

	@Override
	public JsonResult getBUReportName(MyParam p) {
		String BU=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), BU);
		return jsonResult;
	}

	@Override
	public JsonResult getQRReportInputType(MyParam p) {
		String qrInfo=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), qrInfo);
		return jsonResult;
	}

	@Override
	public JsonResult getCheckRecord(MyParam p) {
		String yieldFlag=p.getParams().get(0).toString();
		String reportNum=p.getParams().get(1).toString();
		String MFG=p.getParams().get(2).toString();
		//對應mypaperless_qr_equipment中的FLOOR_NAME欄位
		String floorName=p.getParams().get(3).toString();
		//對應mypaperless_qr_equipment 中的equipment欄位
		String lineName=p.getParams().get(4).toString();
		jsonResult=serviceDao.query(
				p.getDatabase(), p.getSql(), yieldFlag,reportNum,MFG,floorName,lineName);
		return jsonResult;
	}
	
	@Override
	public JsonResult getWO(MyParam p) {
		String MFG=p.getParams().get(0).toString();//製造處（用於連接SFC各製造處的數據庫）
		String SN=p.getParams().get(1).toString();//SN 
		jsonResult=serviceDao.query(Database.getConnByMFG(MFG),p.getSql(),SN);
		return jsonResult;
	}

	@Override
	public JsonResult getReportBaseInfo(MyParam p) {
		String MFG=p.getParams().get(0).toString();//製造處（用於連接SFC各製造處的數據庫）
		String workorderNo=p.getParams().get(1).toString();//工單
		if(p.getSite().equals(Site.TW)){//台灣廠區通過接口獲得相關數據
			MyParamConfig config=new MyParamConfig();
			Map<String,String> params=config.getWSParams(WS.param_TW,MFG,workorderNo);
			WebServiceUtil wsUtil=new WebServiceUtil(WS.WSDL_TW, WS.NAMESPACE_TW, WS.METHODNAME_TW, params);
			List<String> dataList=wsUtil.connectWebService(true, 0);
			jsonResult=new JsonResult(p.getAction(), ResultCode.TRUE, "query rownum:"+4, null,5);
			//重新整理數據的位置
			if(dataList.size()>=4){
				List<String> newData=new ArrayList<String>();
				newData.add(dataList.get(0));//sku
				newData.add(dataList.get(1));//skuversion
				newData.add(dataList.get(3));//qty
				newData.add(Report.DEFAULT_VALUE);//deviation
				newData.add(dataList.get(2));//工單
				jsonResult.setData(newData);
			}else{
				jsonResult.setResultCode(ResultCode.NULL);
			}
		}else{
			//HWT不需要deviation（無mfworkdeviation表）,不需要再去ALLPART帶出
			if(MFG.equalsIgnoreCase(DBFlag.CND)){
				jsonResult=serviceDao.query(
						Database.getConnByMFG(MFG), MySql.getSQL(p.getAction(), MySql.STEP_3), workorderNo);
				return jsonResult;
			}
			//CSD從ALLPART帶基本信息  add in 2018/05/14
			if(MFG.equalsIgnoreCase(DBFlag.CSD)){
				jsonResult=serviceDao.query(
						Database.getConnByMFG(MFG), MySql.getSQL(p.getAction(), MySql.STEP_5), workorderNo);
				return jsonResult;
			}
			//其他BU先從SFC帶數據
			jsonResult=serviceDao.query(
					Database.getConnByMFG(MFG), MySql.getSQL(p.getAction(), MySql.STEP_1), workorderNo);
			if(jsonResult.getResultCode()==ResultCode.TRUE){//SFC有數據直接返回
				return jsonResult;
			}
			//連接AllPart數據庫
			jsonResult=serviceDao.query(Database.getMFGVConn(DBFlag.ALLPART), 
					MySql.getSQL(p.getAction(), MySql.STEP_2), workorderNo);
			if(jsonResult.getResultCode()==ResultCode.TRUE){
				return jsonResult;
			}
			//若SFC/AllPart均無deviation數據，但SFC中含有此工單，則返回deviation為“N/A”
			/*jsonResult=serviceDao.query(
					Database.getConnByMFG(MFG), MySql.getSQL(p.getAction(), MySql.STEP_4), workorderNo);*/
		}
		return jsonResult;
	}

	@Override
	public JsonResult getCheckItem(MyParam p) {
		String reportNum=p.getParams( ).get(0).toString();
		String site=p.getParams().get(1).toString();
		String MFG=p.getParams().get(2).toString();
		String SBU=p.getParams().get(3).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), reportNum,site,MFG,SBU,reportNum);
		return jsonResult;
	}

	@Override
	public JsonResult getShiftCheckNode(MyParam p) {
		String time=p.getParams().get(0).toString();
		System.out.println("time:"+time);
		if(TextUtil.isEmpty(time)){
			jsonResult=serviceDao.query(p.getDatabase(), 
					MySql.getSQL(p.getAction(), MySql.STEP_1));
		}else{
			jsonResult=serviceDao.query(p.getDatabase(),
					MySql.getSQL(p.getAction(), MySql.STEP_2),time);
		}
		
		return jsonResult;
	}

	@Override
	public JsonResult getSide(MyParam p) {
		String workorderNo=p.getParams().get(0).toString();
		//對應mypaperless_qr_equipment 中的equipment欄位
		String lineName=p.getParams().get(1).toString();
		String skuno=p.getParams().get(2).toString();
		String MFG=p.getParams().get(3).toString();
		if(MFG.equalsIgnoreCase(DBFlag.CSD)){//CSD帶面別 add in 2018/05/14
			jsonResult=serviceDao.query(Database.getMFGVConn(DBFlag.CSD), 
					MySql.getSQL(p.getAction(), MySql.STEP_2),skuno);
		}else{
			//連接AllPart數據庫
			jsonResult=serviceDao.query(Database.getMFGVConn(DBFlag.ALLPART), 
					MySql.getSQL(p.getAction(), MySql.STEP_1), workorderNo,lineName);
			if(jsonResult.getResultCode()==ResultCode.TRUE){
				return jsonResult;
			}
			//連接AllPart數據庫(五處ALLPart,部分工單屬於代工工單)
			jsonResult=serviceDao.query(Database.getMFGVConn(DBFlag.BPD), 
					MySql.getSQL(p.getAction(), MySql.STEP_1), workorderNo,lineName);
		}
		return jsonResult;
	}

	@Override
	public JsonResult getParams(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		if(reportNum.equals(ReportNum.SMT_PRINTER)
				||reportNum.equals(ReportNum.SMT_REFLOW)){//印刷機、回焊爐的傳進的參數相同
			String building=p.getParams().get(1).toString();
			String line=p.getParams().get(2).toString();//對應mypaperless_qr_equipment中的equipment欄位
			String productName=p.getParams().get(3).toString();//機種
			String side=p.getParams().get(4).toString();//面別
			if(side.equalsIgnoreCase(Side.BOT[0])){//B面
				side=Side.BOT[1];
			}
			if(side.equalsIgnoreCase(Side.TOP[0])){//T面
				side=Side.TOP[1];
			}
			int step=MySql.STEP_SMTPRINTER;//默認查詢印刷機參數
			if(reportNum.equals(ReportNum.SMT_REFLOW)){//查詢回焊爐參數
				step=MySql.STEP_SMTREFLOW;
			}
			jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(),
					step), building,line,productName,side);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){//查詢波峰焊參數
			String MFG=p.getParams().get(1).toString();
			String line=p.getParams().get(2).toString();//對應mypaperless_qr_equipment中的equipment欄位
			String productName=p.getParams().get(3).toString();
			jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_PTHWS),
					MFG,line,productName);
		}
		return jsonResult;
	}

	@Override
	public JsonResult getCheckBy(MyParam p) {
		String team=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),team,MFG);
		return jsonResult;
	}

	@Override
	public JsonResult getMaster(MyParam p) {
		String logonName=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), logonName);
		return jsonResult;
	}

	@Override
	public JsonResult saveException(MyParam p) {
		String site=p.getParams().get(0).toString();
		String BU=p.getParams().get(1).toString();
		String MFG=p.getParams().get(2).toString();
		String equipment=p.getParams().get(3).toString();
		String floorName=p.getParams().get(4).toString();
		String team=p.getParams().get(5).toString();
		String reportNum=p.getParams().get(6).toString();
		String RNO=p.getParams().get(7).toString();
		String proId=p.getParams().get(8).toString();
		String checkId=p.getParams().get(9).toString();
		String fromUser=p.getParams().get(10).toString();
		String content=p.getParams().get(11).toString();
		//簽核人列表字符串，分隔符：“———”
		List<String> toUsers=Arrays.asList(p.getParams().get(12).toString().split(Exception.SPLIT));
		//上傳圖片名稱字符串，分隔符：“---”
		String pictureUrl=p.getParams().get(13).toString();
		for (int i = 0; i < toUsers.size(); i++) {//當有多個簽核人的時候，循環插入多筆數據
			jsonResult=serviceDao.update(p.getDatabase(), p.getSql(), 
					site,BU,MFG,equipment,floorName,team,reportNum,RNO,proId,checkId,fromUser,
					content,toUsers.get(i),pictureUrl);
		}
		return jsonResult;
	}

	@Override
	public JsonResult saveCheckInfo(MyParam p) {
		String RNO=p.getParams().get(0).toString();//點檢編號
		String checkId=p.getParams().get(1).toString();//節次
		String workorderNo=p.getParams().get(2).toString();//工單
		String skuNo=p.getParams().get(3).toString();//機種
		String qty=p.getParams().get(4).toString();//批量
		String skuVersion=p.getParams().get(5).toString();//機種版本
		String side=p.getParams().get(6).toString();//面別
		String deviation=p.getParams().get(7).toString();//deviation
		String check=p.getParams().get(8).toString();//是否點檢
		String checkRemark=p.getParams().get(9).toString();//備註
		String checkRemarkReason=p.getParams().get(10).toString();//備註原因
		String checkType=p.getParams().get(11).toString();//類型
		String createBy=p.getParams().get(12).toString();//點檢人工號
		String reportNum=p.getParams().get(13).toString();//報表編號
		String MFG=p.getParams().get(14).toString();//製造處
		String floorName=p.getParams().get(15).toString();//樓層
		String equipment=p.getParams().get(16).toString();//設備編號
		String shiftName=p.getParams().get(17).toString();//班別
		String checkByStr=p.getParams().get(18).toString();//簽核人(分隔符拼接)
		String proIdStr=p.getParams().get(19).toString();//proId(分隔符拼接)
		String checkContentStr=p.getParams().get(20).toString();//點檢內容(分隔符拼接)
		String checkResultStr=p.getParams().get(21).toString();//是否是點檢內容(分隔符拼接)
		//String imageDataStr=p.getParams().get(22).toString();//照片數據(分隔符拼接)
		String imageDataStr=p.getImageDataStr();//照片數據(分隔符拼接)
		String split=p.getParams().get(22).toString();//分隔符
		//創建時間（正常提交默認為''[getdate()],補點檢或提前維護由用戶傳入時間）
		String createDate=p.getParams().get(23).toString();
		//先判斷數據庫中是否存在一筆相同的數據，存在則直接返回
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_1),
				RNO,checkId);
		if(jsonResult.getResultCode()==ResultCode.TRUE){
			jsonResult.setResultMsg(ResultMsg.CHECKINFO_EXIST);//已點檢
			return jsonResult;
		}
		//提交點檢數據
		jsonResult=serviceDao.update(p.getDatabase(),MySql.getSQL(p.getAction(),MySql.STEP_2 ),
				RNO,checkId, workorderNo,skuNo,qty,skuVersion,side,deviation,check,checkRemark,
				checkRemarkReason,checkType,createBy,reportNum,MFG,floorName,equipment,shiftName,
				checkByStr,proIdStr,checkContentStr,checkResultStr,imageDataStr,split,createDate);
		
		AuditEmailAlert(p,RNO,checkId);//兩小時后未簽核郵箱報警
		return jsonResult;
	}
	
	//未簽核郵箱報警
	private void AuditEmailAlert(MyParam p, String RNO,String checkId){
		//獲得簽核人員信息（點檢報名名，姓名，郵箱）
		JsonResult result=serviceDao.query(p.getDatabase(),MySql.getSQL(p.getAction(),
				MySql.STEP_3) , RNO,checkId);
		Timer timer=new Timer();
		timer.schedule(new AuditEmailTimer(p, result,RNO,checkId), EmailAlert.AUDITDELAY);//啟動定時任務	
	}
	//定時器，兩小時后執行
	public class AuditEmailTimer extends TimerTask{
		private MyParam p;
		private JsonResult result;
		private String RNO;
		private String checkId;
		
		public AuditEmailTimer(MyParam p,JsonResult result,String RNO,String checkId){
			this.p=p;
			this.result=result;
			this.RNO=RNO;
			this.checkId=checkId;
		}

		@Override
		public void run() {
			//b.report_num,c.report_name,a.check_by,d.chineseName,d.email 
			for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
				String reportNum=result.getData().get(i);
				String reportName=result.getData().get(i+1);
				String checkBy=result.getData().get(i+2);
				String chineseName=result.getData().get(i+3);
				String email=result.getData().get(i+4);
				//查詢簽核人的簽核狀態
				JsonResult auditResult=serviceDao.query(p.getDatabase(),MySql.getSQL(p.getAction(),
						MySql.STEP_4), RNO,checkId,checkBy);
				if(auditResult.getResultCode()==ResultCode.TRUE){
					String checkRelease=auditResult.getData().get(0);
					if(checkRelease.equals(Report.NOAUDIT)){//未簽核狀態，發送郵件報警
						SendEmailManager manager=new SendEmailManager();
						manager.SubmitMailTo2H(reportName+"("+reportNum+")",chineseName+"("+checkBy+")",email);
					}
				}
			}
		}
	}
	
	@Override
	public JsonResult getParamsConfigReport(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), reportNum);
		return jsonResult;
	}

	@Override
	public JsonResult getCheckItemParam(MyParam p) {
		String reportNum=p.getParams().get(0).toString();
		String MFG=p.getParams().get(1).toString();
		String workorderNo=p.getParams().get(2).toString();
		String floorName=p.getParams().get(3).toString();
		String equipment=p.getParams().get(4).toString();
		String skuNo=p.getParams().get(5).toString();
		String skuVersion=p.getParams().get(6).toString();
		String qty=p.getParams().get(7).toString();
		String deviation=p.getParams().get(8).toString();
		String side=p.getParams().get(9).toString();
		String checkRemark=p.getParams().get(10).toString();
		String proIdStr=p.getParams().get(11).toString();
		//切割得到ProID集合
		List<String> proIdList=Arrays.asList(proIdStr.split(Report.SPLIT));
		jsonResult=new JsonResult();
		List<String> dataList=new ArrayList<String>();
		MyParamConfig config=new MyParamConfig(
				reportNum, MFG, workorderNo, floorName, equipment, skuNo, skuVersion, qty,
				deviation, side, checkRemark);
		for (int i = 0; i < proIdList.size(); i++) {
			System.out.println(proIdList.get(i)+"<<<");
			JsonResult result=serviceDao.query(p.getDatabase(), 
					MySql.getSQL(p.getAction(), MySql.STEP_1), reportNum,proIdList.get(i),MFG);
			
			//有數據,製造處為ALL或用戶傳入的製造處
			if(result.getResultCode()==ResultCode.TRUE){
				//SMT開線換線時，同一個PROID含多條數據，根據describe欄位再次進行查詢
				if(reportNum.equals(ReportNum.SMT_OPENCHANGE)){
					//如果是ProId 為9(開線)和10（換線），但沒有選擇備註為開線、換線、直接跳過
					if(proIdList.get(i).equals(ParamConfig.OC_BEFORE_PROID)
							||proIdList.get(i).equals(ParamConfig.OC_AFTER_PROID)){
						
						if(checkRemark.equals(ParamConfig.OPENLINE)
								||checkRemark.equals(ParamConfig.CHANGELINE)){
							result=serviceDao.query(p.getDatabase(), 
									 MySql.getSQL(p.getAction(), MySql.STEP_3), 
									 reportNum,proIdList.get(i),checkRemark);
						}else{
							continue;
						}
					}
				}
				String resultStr=getParams(config, result,p.getAction());
				//將PROID和獲取數據添加到List中
				dataList.add(proIdList.get(i));
				dataList.add(resultStr);
			}
			//無數據,用第二個sql進行查詢（製造處為OTHER）
			if(result.getResultCode()==ResultCode.NULL){
				result=serviceDao.query(p.getDatabase(), 
						MySql.getSQL(p.getAction(), MySql.STEP_2), reportNum,proIdList.get(i));
				if(result.getResultCode()==ResultCode.TRUE){
					String resultStr=getParams(config, result,p.getAction());
					//將PROID和獲取數據添加到List中
					dataList.add(proIdList.get(i));
					dataList.add(resultStr);
				}
			}
		}
		jsonResult.setData(dataList);
		jsonResult.setResultCode(ResultCode.TRUE);
		jsonResult.setResultMsg("query row:"+dataList.size()/2);
		jsonResult.setColumnNum(2);
		return jsonResult;
	}
	
	private String getParams(MyParamConfig config,JsonResult result,String action){
		String resultStr="";
		String reportNum=result.getData().get(0);
		String proId=result.getData().get(4);
		String type=result.getData().get(5);
		String serviceAddress=result.getData().get(6);
		String param=result.getData().get(12);
		String describe=result.getData().get(15);
		//通過webservice帶出數據
		if(type.equals(ParamConfig.TYPE_WS)){
			Map<String,String> paramMap=config.getWSParams(param);//獲得請求參數
			String nameSpace=result.getData().get(10);
			String methodName=result.getData().get(11);
			//這裡的DBNAME欄位對應的是webservice中對應的SoapObject索引位置
			String index=result.getData().get(7);
			String dotNet=result.getData().get(18);
			WebServiceUtil service=new WebServiceUtil(serviceAddress,nameSpace,methodName, paramMap);
			boolean isDotNet=config.getIsDotNet(dotNet);//連接webservice
			List<String> dataList=service.connectWebService(isDotNet,Integer.parseInt(index));
			for (int i = 0; i < dataList.size(); i++) {
				resultStr+=dataList.get(i)+ParamConfig.RESULT_SPLIT;//拼接查詢數據，在末尾添加“;”
			}
		}
		//通過數據庫帶出數據
		if(type.equals(ParamConfig.TYPE_DB)){
			String dbName=result.getData().get(7);
			String userName=result.getData().get(8);
			String password=result.getData().get(9);
			String sql=result.getData().get(14);
			String dbType=result.getData().get(17);
			String[] database = null;
			String[] params=config.getDBParams(param);//獲得請求參數
			if(dbType.equalsIgnoreCase(ParamConfig.DBTYPE_SQLSERVER)){//連接sqlserver數據庫
				database=Database.getSqlServerConn(serviceAddress, dbName, 
						userName, password);
			}
			if(dbType.equalsIgnoreCase(ParamConfig.DBTYPE_ORACLE)){//連接oracle數據庫
				database=Database.getOracleConn(serviceAddress, dbName, userName, password);
			}
			System.out.println("database:"+database[1]+","+database[2]+","+database[3]);
			System.out.println("DB sql:"+sql);
			for (int i = 0; i < params.length; i++) {
				System.out.println("params["+i+"]:"+params[i]);
			}
			JsonResult jsonResult=serviceDao.query(database, sql, params);
			System.out.println(jsonResult.toString());
			if(jsonResult.getResultCode()==ResultCode.TRUE){
				
				for (int i = 0; i < jsonResult.getData().size(); i++) {
					System.out.println("result:"+jsonResult.getData().get(i));
					//拼接查詢數據，在末尾添加“;”
					resultStr+= jsonResult.getData().get(i)+ParamConfig.RESULT_SPLIT;
				}
				if(reportNum.equals(ReportNum.SMT_OPENCHANGE)){//SMT開環線還需要獲取厚度範圍
					if(describe.equals(ParamConfig.OPENLINE)||describe.equals(ParamConfig.CHANGELINE)){
						JsonResult jsonResult2=serviceDao.query(
								Database.getMFGVConn(DBFlag.ALLPART), 
								MySql.getSQL(action, MySql.STEP_4), 
								config.getDBParams("wo,station_Name,process"));
						if(jsonResult2.getResultCode()==ResultCode.TRUE){
							//計算標準範圍
							String range = (int)(Float.parseFloat(jsonResult2.getData().get(2).trim()) * 1000)
									+ 20 + "+/-30";
							resultStr+="標準範圍："+range+ParamConfig.RESULT_SPLIT;
						}
					}
				}
			}
		}
		if(TextUtil.isEmpty(resultStr)){
			resultStr="暫無數據";
			if(reportNum.equals(ReportNum.SMT_OPENCHANGE)){//SMT開換線點檢表
				//開線狀態下本班別內前刮刀無測試錫膏厚度
				if(proId.equals(ParamConfig.OC_BEFORE_PROID)&&describe.equals(ParamConfig.OPENLINE)){
					resultStr="開線狀態下本班別內前刮刀無測試錫膏厚度";
				}
				//換線狀態下兩小時內前刮刀無測試錫膏厚度
				if(proId.equals(ParamConfig.OC_BEFORE_PROID)&&describe.equals(ParamConfig.CHANGELINE)){
					resultStr="換線狀態下兩小時內前刮刀無測試錫膏厚度";
				}
				//開線狀態下本班別內后刮刀無測試錫膏厚度
				if(proId.equals(ParamConfig.OC_AFTER_PROID)&&describe.equals(ParamConfig.OPENLINE)){
					resultStr="開線狀態下本班別內后刮刀無測試錫膏厚度";
				}
				//換線狀態下兩小時內后刮刀無測試錫膏厚度
				if(proId.equals(ParamConfig.OC_AFTER_PROID)&&describe.equals(ParamConfig.CHANGELINE)){
					resultStr="換線狀態下兩小時內后刮刀無測試錫膏厚度";
				}
			}
		}else{
			resultStr=resultStr.substring(0, resultStr.length()-1);//去除末尾的分號
		}
		return resultStr;
	}

	
}
