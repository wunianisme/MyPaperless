package com.foxconn.server.constant;

import com.foxconn.server.constant.MyAction.Action;
/**
 * 存放sql语句的类
 * @author NSD
 *
 */
public class MySql {
	//定義步驟（第幾步）
	public static final int STEP_1=1;
	public static final int STEP_2=2;
	public static final int STEP_3=3;
	public static final int STEP_4=4;
	public static final int STEP_5=5;
	//三個參數表的步驟
	public static final int STEP_SMTPRINTER=10;
	public static final int STEP_SMTREFLOW=20;
	public static final int STEP_PTHWS=30;
	/**
	 * 获得sql语句（单个参数）
	 * @param action
	 * @return
	 */
	public static String getSQL(String action){
		//登陸模塊
		if(action.equals(Action.GET_SERVER)){
			return "select * from IPQC_Server_Config(nolock) " +
					"where Enable='1' order by Editdt asc";
		}
		if(action.equals(Action.UPDATE_PASSWORD)){
			return "update ipqceuser set password=? " +
					"where logonname=?";
		}
		if(action.equals(Action.GET_EMAIL)){
			return "select Email from ipqceuser(nolock) " +
					"where logonname=?";
		}
		if(action.equals(Action.FIND_PASSWORD)){
			return "select ChineseName,password from ipqceuser(nolock) " +
					"where logonname=?";
		}
		if(action.equals(Action.GET_APKINFO)){
			return "select * from AppStoreInfo(nolock) " +
					"where appname =?";
		}
		if(action.equals(Action.SAVE_DOWNLOADINFO)){
			return "insert into PAPERLESS3_DOWNLOAD_NUM(DEVICEID,PHONEBRAND," +
					"PHONEMODEL,VERCODE,METRICS,SIMOPERATORNAME,DOWNLOAD_DATE) " +
					"values(?,?,?,?,?,?,GETDATE())";
		}
		//賬戶模塊
		if(action.equals(Action.GET_MFG_ITEM)){
			return "select distinct MFG from ipqceuser(nolock) " +
					"where BU=? order by MFG";
		}
		if(action.equals(Action.GET_SBU_ITEM)){
			return "select distinct SBU,MFG from ipqceuser(nolock)  " +
					"where BU=? order by MFG";
		}
		if(action.equals(Action.GET_TEAM_ITEM)){
			return "select distinct Team,SBU,MFG from ipqceuser(nolock) " +
					"where BU=? order by MFG";
		}
		if(action.equals(Action.GET_SECTION_ITEM)){
			return "select distinct section,SBU from ipqceuser(nolock) " +
					"where BU=? order by SBU";
		}
		
		if(action.equals(Action.UPDATE_ACCOUNTINFO)){
			return "update ipqceuser set ChineseName=?,EnglishName=?,Ext=?,Email=?," +
					"Team=?,Section=?,SBU=?,MFG=? " +
					"where logonName=?";
		}
		if(action.equals(Action.GET_EMPLOYEEINFO)){
			return "select * from ipqceuser(nolock) where MFG=? and SBU=? and Team=? and logonName" +
					" not like '#%' and len(logonName)>5 order by logonName ";
		}
		if(action.equals(Action.GET_FEEDBACK)){
			return "select logonName,ChineseName,Feedback," +
					"left(Lasteditdt,len(Lasteditdt)-4) as Lasteditdt,site from " +
					"ipqceuser_opinion(nolock) order by Lasteditdt desc";
		}
		if(action.equals(Action.SAVE_FEEDBACK)){
			return "insert into ipqceuser_opinion " +
					"values(?,?,?,?,?,CONVERT(varchar(25),getdate(),121 ),?,?)";
		}
		//點檢模塊
		if(action.equals(Action.GET_BU)){
			return "select BU from ipqceuser(nolock) where site=? and BG=?  group by  BU";
		}
		
		if(action.equals(Action.GET_BU_REPORTNAME)){
			return "select distinct a.report_num,a.report_name,a.check_section from " +
					"IPQC_RI_Report_name(nolock) a,IPQC_Check_BU(nolock) b " +
					"where a.Report_Num=b.report_Num and b.BU=? order by a.check_section";
		}
		if(action.equals(Action.GET_QR_REPORTINPUTTYPE)){//獲得二維碼對應的報表信息
			return "select distinct a.Report_Num,b.Report_Name,b.is_input_order,a.MFG_NAME," +
					"a.SBU_NAME,b.Yeild_Flag,a.FLOOR_NAME,a.EQUIPMENT,a.section from " +
					"mypaperless_qr_equipment a(nolock),IPQC_RI_Report_Name b(nolock) " +
					"where a.Report_Num=b.Report_Num and a.QR_IMAGE_INFO=?";
		}
		if(action.equals(Action.GET_CHECKRECORD)){
			return "exec Paperless_GetCheckRecord_sp ?,?,?,?,?";//查詢該時段點檢記錄
		}
		if(action.equals(Action.GET_CHECKITEM)){//獲得報表點檢項目
			return " select check_name,check_pro_name,pro_id,check_flag," +
					"case when TakePhoto is Null then '0' else TakePhoto end as TakePhoto from " +
					"IPQC_RI_Report_Name(nolock) where pro_id in(select pro_id from "+
					"IPQC_Check_BU_Configuration(nolock) where  report_num=? and " +
					"site_name=? and mfg_name=? and (sbu_name=? or sbu_name='all'))"+
					" and  report_num=? order by pro_id";
		}
		if(action.equals(Action.GET_WO)){//通過SN帶出工單
			return "select top 1 workorderNo from mfworkstatus(nolock) where sysserialno=?";
		}
		//選擇簽核人
		if(action.equals(Action.GET_CHECKBY)){//獲取具有簽核權限的人員信息（工號和中文名）
			return "exec Paperless_SelectCenter_sp5 'GET_PD_CHECK_NAME',?,?,'','',''";
		}
		if(action.equals(Action.GET_MASTER)){//獲得上級主管
			return "select * from ipqceuser(nolock) where logonName in" +
					"(select master from ipqceuser(nolock) where logonName=?)";
		}
		if(action.equals(Action.SAVE_EXCEPTION)){//保存提交的異常點檢信息
			return "insert into IPQC_Exception_Feedback(Site,BU,MFG_Name,Equipment,Floor,Team," +
					"Report_Num,Check_Report_NO,Pro_Id,Check_Id,From_User,Content,To_User,PicTure_Url) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		if(action.equals(Action.GET_PARAMSCONFIG_REPORT)){//獲取已配置了要帶參數的報表（除三個帶參數表的報表外）
			return "select ReportNum,ProId,type,ServerAddress,Param,ParamNum,describe from IPQC_Param_Config(nolock) " +
					"where ReportNum=?";
		}
		//功能管理
		//簽核查詢
		if(action.equals(Action.GET_MFG_REPORT)){//獲取製造處下所有已導入的報表信息(歷史/報表配置)
			return "select distinct a.report_num,a.report_Name,a.check_section from IPQC_RI_Report_Name a(nolock),"+
					"IPQC_Check_BU b(nolock) where a.report_num=b.report_num and b.BU=? " +
					"and b.MFG_Name in(?,?)  order by a.check_section ";
		}
		if(action.equals(Action.GET_AUDIT_REPORT)){//獲取待簽核/已簽核/拒簽報表信息
			return "select distinct a.report_num,a.report_name from IPQC_RI_Report_Name a(nolock)" +
					",IPQC_RI_Check_Report b(nolock),IPQC_Report_Check c(nolock)" +
					" where b.check_report_no=c.check_report_no and a.report_num=b.report_num " +
					"and c.check_by=? and c.check_release=? and c.check_reject=?";
		}
		
		if(action.equals(Action.GET_AUDIT_INFO)){//獲得簽核歷史/待簽核/已簽核/拒簽記錄
			//@checktype @queryby  @reportNum  @mfg @check_by @check_Report_No
			//@create_date @workorderno @linename @skuno @rownum
			return "exec auditQuery ?,?,?,?,?,?,?,?,?,?,'2000' ";
		}
		if(action.equals(Action.GET_AUDIT_BASEINFO)){//獲得簽核記錄基本信息
			return "select distinct C.check_Id,C.check_Report_No,D.logonName,D.ChineseName," +
					"C.check_seqno,case when C.check_release ='0' and C.check_reject='0' then '0'"+
	                " when C.check_release ='1' and C.check_reject='0' then '1'"+
	                " when C.check_release='1' and C.check_reject='1' then '-1' end as check_status,"+
	                "A.mfg_Name,A.floor_Name,B.check_WorkOrder,A.line_Name,B.check_SkuNo,B.check_Woqty," +
	                "B.deviation_No,B.check_Side,B.check_Remark,B.check_Remark_Reason,C.create_Date " +
	                "from IPQC_RI_Check_Report A(nolock),IPQC_RI_Check_BaseInfo B(nolock)," +
					"IPQC_Report_Check C(nolock),IPQCEuser D(nolock) where " +
					"A.check_report_no=C.check_report_no and B.check_report_no=C.check_report_no " +
					"and B.check_Id =C.check_Id and C.check_by=D.logonname and " +
					"C.check_report_no=?  order by C.check_Id asc,C.check_seqno asc";
		}
		if(action.equals(Action.GET_AUDIT_CHECKRESULT)){//獲得點檢詳細點檢信息
			return "select b.pro_Id,a.check_name,a.check_yeild,a.check_pro_name,b.check_result," +
					"b.check_content,b.Icarno,case when b.imageData is Null then 'N/A' else b.imageData " +
					"end as imageData from IPQC_RI_Report_Name a(nolock),IPQC_RI_Check_Result b(nolock) " +
					"where a.report_num=? and a.pro_id=b.pro_id and b.check_report_no=?  and  " +
					"b.check_Id=? order by b.pro_Id";
		}
		if(action.equals(Action.GET_REJECT_REMARK)){//獲得拒簽原因
			return "select remark from IPQC_Report_Check(nolock) where check_report_no=? " +
					"and check_Id=? and Check_Seqno=?";
		}
		if(action.equals(Action.UPDATE_AUDIT_RESULT)){//提交簽核結果
			return "update IPQC_Report_Check set check_Release=?,check_Reject=?,Remark=?"+
					",check_date=getdate() where check_report_no=? and check_Id=? " +
					"and check_by=?";
		}
		if(action.equals(Action.UPDATE_AUDIT_PASS)){//提交簽核通過（批量）
			return "update IPQC_Report_Check set check_Release='1',check_Reject='0',Remark=''"+
					",check_date=getdate() where check_report_no=? and check_by=?";
		}
		//點檢狀態查詢
		if(action.equals(Action.GET_FLOORNAME)){//獲得樓層
			return "select distinct FLOOR_NAME from Mypaperless_QR_equipment(nolock) " +
					"WHERE BU=? AND MFG_NAME=?";
		}
		if(action.equals(Action.GET_LINENAME)){//獲得線別
			return "select distinct LINE_NAME from Mypaperless_QR_equipment(nolock) " +
					"WHERE FLOOR_NAME=? AND MFG_NAME=?";
		}
		if(action.equals(Action.GET_CHECKSTATUS)){//獲得同一個製造處下某天的報表點檢狀態
			return "exec Paperless_Get_LeakCheck_web ?,?,?,?,?,?,'','',''";
		}
		//報表配置
		if(action.equals(Action.GET_REPORT_CHECKITEM)){//獲得報表點檢項
			return "select distinct a.pro_Id,a.check_name,a.check_pro_name"+
					" from IPQC_RI_Report_Name a(nolock) ,IPQC_Check_BU b(nolock) " +
					"where a.pro_Id=b.pro_Id and a.report_num=b.report_num and " +
					"b.site_name=? and b.BU=? and b.report_num=? order by a.pro_Id";
		}
		if(action.equals(Action.GET_CONFIG_PROID)){//獲得該SBU報表已配置的PROID
			return "select Pro_ID from IPQC_Check_BU_Configuration(nolock) where " +
					"Report_num =? and SITE_Name=? and BU=? and MFG_Name=? and SBU_Name=?";
		}
		if(action.equals(Action.GET_SBU)){//獲得製造處下的所有已配置的SBU
			return "select distinct SBU_Name from Mypaperless_QR_equipment(nolock) where SITE_Name=?" +
					" and BU=? and MFG_Name=?";
		}
		//參數管理
		if(action.equals(Action.GET_PARAM_FLOORNAME)){//獲得參數表對應樓層
			return "select distinct FLOOR_NAME from Mypaperless_QR_equipment(nolock) " +
					"WHERE BU=? AND MFG_NAME=? and report_num=?";
		}
		if(action.equals(Action.GET_PARAM_LINENAME)){//獲得參數表對應線別
			return "select distinct equipment from Mypaperless_QR_equipment(nolock) " +
					"WHERE FLOOR_NAME=? AND MFG_NAME=? and report_num=?";
		}
		//獲得參數信息、詳細信息、我提交的參數內容、我要簽核的參數內容、簽核詳細參數信息
		if(action.equals(Action.GET_PARAM_INFO)
				||action.equals(Action.GET_PARAM_DETAILINFO)
				||action.equals(Action.GET_PARAMMYSUBMIT_INFO)
				||action.equals(Action.GET_PARAMMYCHECK_INFO)
				||action.equals(Action.GET_PARAMAUDIT_DETAILINFO)){
			//@type,@Building,@Line,@productName,@side,@parameterNum,@createby,@checkby,@rownum
			return "exec paramManage ?,?,?,?,?,?,?,?,?,?,'2000'";
		}
		if(action.equals(Action.UPDATE_SMTPRINTER_INFO)){//修改SMT印刷機參數信息
			return "exec Paperless_PrinterParameterSet_SP 'UPDATE_PRINTER_PARAMETER',?,?,?,?,?,?,?,?,?,?,?,?,'',?,'',?";
		}
		if(action.equals(Action.UPDATE_SMTREFLOW_INFO)){//修改SMT回焊爐參數信息
			return "exec Paperless_ReflowParameterSet_SP 'UPDATE_REFLOW_PARAMETER',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'',?,'',?";
		}
		if(action.equals(Action.UPDATE_PTHWS_INFO)){//修改PTH波峰焊參數信息
			return "exec Paperless_WSParameterSet_SP 'UPDATE_WS_PARAMETER',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'',?,?,'',?";
		}
		if(action.equals(Action.DELETE_SMTPRINTER_INFO)){//刪除SMT印刷機參數信息
			return "exec Paperless_PrinterParameterSet_SP 'DELETE_PRINTER_PARAMETER',?,?,?,?,?,?,?,?,?,?,?,?,'',?,'',?";
		}
		if(action.equals(Action.DELETE_SMTREFLOW_INFO)){//刪除SMT回焊爐參數信息
			return "exec Paperless_ReflowParameterSet_SP 'DELETE_REFLOW_PARAMETER',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'',?,'',?";
		}
		if(action.equals(Action.DELETE_PTHWS_INFO)){//刪除PTH波峰焊參數信息
			return "exec Paperless_WSParameterSet_SP 'DELETE_WS_PARAMETER',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'',?,?,'',?";
		}
		if(action.equals(Action.GET_PARAMCHECK_MASTER)){//獲得參數提交審核主管信息
			return "select logonName,ChineseName from IPQCEuser(nolock) where MFG=? and team=? and " +
					"userlevel in('1','2')";
		}
		//異常管理
		if(action.equals(Action.UPDATE_EXCEPTION_DEALSTATE)){//更改異常處理結果
			return "update IPQC_exception_feedback set Deal_State=?,Back_Content=?,CompleteDate=getdate()" +
					" where id=?";
		}
		//提前維護線體
		if(action.equals(Action.GET_MT_FLOOR)){//獲得樓層
			return "select distinct FLOOR_NAME from Mypaperless_QR_equipment " +
					"WHERE BU=? AND MFG_NAME=?";
		}
		if(action.equals(Action.GET_MT_LINE)){//獲得線別
			return "select distinct LINE_NAME,SITE_NAME,BU,MFG_NAME from Mypaperless_QR_equipment " +
					"WHERE FLOOR_NAME=? AND MFG_NAME=? AND LINE_NAME<>'NA'";
		}
		if(action.equals(Action.GET_MT_CHECKRECORD)){//獲得提前點檢/補點檢記錄
			return "exec paperless_GetCheckRecord_Advance_SP ?,?,?,?,?,?";//查詢該時段點檢記錄
		}
		if(action.equals(Action.SAVE_MT)){//提交提前維護信息
			return "exec Check_Maintain_SP ?,?,?,?,?,?,?,?";
		}
		return "";
	}
	/**
	 * 获得sql语句，分多個步驟
	 * @param action
	 * @param params
	 * @return
	 */
	public static String getSQL(String action,int step){
		//登錄校驗，增加輸錯五次鎖定賬號，三十分鐘后解鎖功能
		if(action.equals(Action.CHECK_LOGIN)){
			if(step==STEP_1){//判斷是否輸入正確，如果輸入錯誤則累計輸錯次數，五次后鎖定
				return "exec MyPaperless_CheckLogin_sp 'CheckLogin',?,?";
			}
			if(step==STEP_2){//獲得登錄成功后的用戶個人信息
				return "select * from ipqceuser(nolock) " +
						"where logonname=? and password=?";
			}
		}
		//賬戶模塊
		if(action.equals(Action.SAVE_EMPLOYEEINFO)){
			if(step==STEP_1){//查詢員工工號是否存在系統中
				return "select logonName from ipqceuser(nolock) where logonName=?";
			}
			
			if(step==STEP_2){//插入員工數據
				return "insert into ipqceuser " +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,getdate(),'0')";
			}
		}
		//點檢模塊
		//報表基本信息
		if(action.equals(Action.GET_SHIFTCHECKNODE)){////獲得班別和節次
			if(step==STEP_1){//正常點檢
				return "select check_Id,shift,check_Node from IPQC_Check_Time(nolock) " +
						"where CONVERT(varchar(100),GETDATE(),108)  between  start_Time and  end_Time";
			}
			if(step==STEP_2){//提前維護
				return "select check_Id,shift,check_Node from IPQC_Check_Time(nolock) " +
						"where CONVERT(varchar(100),?) between  start_Time and  end_Time";
			}
		}
		if(action.equals(Action.GET_REPORTBASEINFO)){
			
			if(step==STEP_1){//從SFC帶出基本信息（首選）
				return "select a.skuno,a.skuversion,a.workorderqty as qty,b.deviation  from" +
				" mfworkorder a left join mfworkdeviation b on a.workorderno=b.workorderno " +
				"where a.workorderno=?";
			}
			if(step==STEP_2){//從AllPart帶出基本信息（備選）
				return "select P_NO as skuno,P_VERSION as skuversion,WO_QTY as qty," +
						"nvl(DEVIATION_NO,'N/A') as deviation from mes4.R_Wo_Base where wo = ?";
			}
			if(step==STEP_3){//HWT無mfworkdeviation表，不需要帶出deviation
				return "select skuno,skuversion,workorderqty as qty,'N/A' as deviation " +
						"from mfworkorder where workorderno=?";
			}
			/*if(step==STEP_4){//SFC、ALLPart均無deviation數據，但SFC中存在此工單
				return "select a.skuno,a.skuversion,a.workorderqty as qty,'N/A' as deviation  from" +
						" mfworkorder a left join mfworkdeviation b on a.workorderno=b.workorderno " +
						"where a.workorderno=?";
			}*/
			if(step==STEP_5){//CSD帶基本信息
				return "select model_name,version_code,target_qty,'N/A' as deviation from " +
						"SFISM4.R_MO_BASE_T where mo_number=?";
			}
		}
		if(action.equals(Action.GET_SIDE)){//獲得面別
			if(step==STEP_1){//普通
				return "select distinct process_flag from mes4.r_travel_sn where smt_code "
						+ "in(select smt_code from mes4.r_station_wip where wo=? and station like ?||'%')";
			}
			if(step==STEP_2){//CSD帶面別
				return "SELECT distinct process_flag FROM MES1.C_PRODUCT_CONFIG where p_no=?";
			}
		}
		//獲得標準參數（印刷機、回焊爐、波峰焊）
		if(action.equals(Action.GET_PARAMS)){
			if(step==STEP_SMTPRINTER){//帶出印刷機參數
				return "select top 1 ScraperPressure,PrintSpeed,SeparationSpeed,SeparationDistance," +
						"ScreenCleanRate,SqueegeeLength from ipqc_printer_parameter(nolock) "+ 
						"where Building=? and Line=? and ProductName=? and Side=?";
			}
			if(step==STEP_SMTREFLOW){//帶出回焊爐參數
				return "select top 1 Zone1,Zone2,Zone3,Zone4,Zone5,Zone6,Zone7,Zone8,Zone9,Zone10," +
						"Zone11,Zone12,Zone13,Speed,FanSpeed,ShuntValue " +
						"from ipqc_reflow_parameter(nolock) "+ 
						"where Building=? and Line=? and ProductName=? and Side=?";
			}
			if(step==STEP_PTHWS){//帶出波峰焊參數
				return "select top 1 ChainSpeed,FluxType,TankAirPressure,NozzleAirPressure,FluxFlow," +
						"ChainSpeed,FluxProportion,TotalAirPressure,TopPreheatI,BOTPreheatI," +
						"TopPreheatII,BOTPreheatII,TopPreheatIII,BOTPreheatIII,TopPreheatIV,BOTPreheatIV," +
						"'N/A',TinTemperature,TurbulentWave,AdvectionWave,'N/A',TinBarType " +
						"from ipqc_ws_parameter(nolock) where MFG_Name=? and Line=? and ProductName=?";
			}
		}
		//點檢最重要的一個sp:saveCheckInfo
		if(action.equals(Action.SAVE_CHECKINFO)){
			if(step==STEP_1){//查詢數據庫中是否還有該筆數據
				return "select top 1 1 from IPQC_RI_Check_baseInfo(nolock) " +
						"where check_report_no=? and check_ID=?";
			}
			if(step==STEP_2){//保存點檢提交信息
				return "exec saveCheckInfo ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
			}
			if(step==STEP_3){//獲得點檢簽核人員信息
				return "select distinct b.report_num,c.report_name,a.check_by,d.chineseName,d.email from " +
						"IPQC_Report_Check a(nolock),IPQC_RI_Check_Report b(nolock),IPQC_RI_Report_Name " +
						"c(nolock),IPQCEuser d(nolock) where a.check_report_no=b.check_report_no and " +
						"b.report_num=c.report_num and a.check_by=d.logonName and a.check_report_no" +
						"=? and a.check_Id=?";
			}
			if(step==STEP_4){//獲得簽核人的簽核狀態
				return "select check_release from IPQC_Report_Check(nolock) where check_report_no=? " +
						"and check_Id=? and check_by=?";
			}
		}
		if(action.equals(Action.GET_CHECKITEM_PARAM)){//獲取部分要帶參數的點檢項的參數值
			if(step==STEP_1){//製造處匹配或為ALL時
				return "select * from IPQC_Param_Config(nolock) where ReportNum=? and ProId=? and " +
						"(MFG='ALL' or MFG=?)";
			}
			if(step==STEP_2){//當通過STEP_1未獲取到數據，執行STEP_2
				return "select * from IPQC_Param_Config(nolock) where ReportNum=? and ProId=? and " +
						"MFG='OTHER'";
			}
			if(step==STEP_3){//SMT開線&換線點檢表 開線換線
				return "select * from IPQC_Param_Config(nolock) where ReportNum=? and ProId=? and "+
						"describe=?";
			}
			if(step==STEP_4){//SMT開線&換線點檢表 開線換線 還需要獲得標準範圍
				return "SELECT distinct D.P_NO,A.STENCIL_SN,decode(substr(B.THICKNESS,1,1),'.'," +
						"'0'||B.THICKNESS,B.THICKNESS) FROM MES4.R_STENCIL_WIP A," +
						"MES1.C_STENCIL_BASE B,MES4.R_WO_BASE C,MES1.C_STENCIL_PRODUCT D "+
					    "WHERE A.STENCIL_SN=B.STENCIL_SN AND A.WO=C.WO AND C.P_NO=D.P_NO AND " +
					    "B.STENCIL_SN=D.STENCIL_SN AND A.WO=? AND A.STATION_NAME like ?||'%' AND " +
					    "B.PROCESS=?";
			}
		}
		//功能管理
		//小紅點提示消息條數
		if(action.equals(Action.GET_MESSAGE_NUM)){
			if(step==STEP_1){//待簽核點檢記錄條數
				return "select count(a.check_report_no) as num from Ipqc_Report_Check a(nolock)," +
						"IPQC_RI_Check_BaseInfo b(nolock),Ipqc_Ri_Check_Report c(nolock), " +
						"IPQCEuser d(nolock) where a.create_by=d.logonName and " +
						"a.check_report_no=b.check_report_no and a.check_report_no=c.check_report_no and " +
						"a.check_Id=b.check_Id  and c.mfg_Name=? and a.check_by=? and a.check_release='0'";
			}
			if(step==STEP_2){//待簽核參數條數（印刷機+波峰焊+回焊爐）
				return "select count(parameterNum) as num from IPQC_Printer_Parameter_Check b(nolock) " +
						"where b.check_by=? and check_state='0' union "+	
						"select count(parameterNum) as num from IPQC_Reflow_Parameter_Check b(nolock)" +
						" where b.check_by=? and check_state='0' union "+	
						"select count(parameterNum) as num from IPQC_WS_Parameter_Check b(nolock) " +
						"where b.check_by=? and check_state='0' ";
			}
			if(step==STEP_3){//待處理異常記錄條數
				return "select count(a.ID) from IPQC_exception_feedback a(nolock)," +
						"IPQC_RI_Report_Name b(nolock),IPQCEuser c(nolock) where " +
						"a.report_num=b.report_num and a.Pro_Id=b.Pro_Id and a.from_user =c.logonName " +
						"and a.To_user=? and a.deal_state='0'";
			}
		}
		//add in 2018/2/7  掃碼獲得簽核記錄
		if(action.equals(Action.GET_AUDIT_INFO_BYQRCODE)){
			if(step==STEP_1){//先獲得報表的一些基本信息
				return "select distinct a.report_num,a.mfg_name,a.floor_name,a.equipment " +
						"from mypaperless_qr_equipment a inner join " +
						"IPQC_RI_Report_Name b on a.report_num=b.report_num and a.qr_image_info=?";
			}
			if(step==STEP_2){//帶入基本信息，查詢二維碼對應的簽核記錄
				return "select distinct top 2000 a.check_Report_No,convert(varchar(10)," +
						"B.create_Date,120) create_date,d.ChineseName,case when min(a.check_release)=0 " +
						"then 0 else 1 end as checkstatus,c.report_num,b.create_date from Ipqc_Report_Check a(nolock)," +
						"IPQC_RI_Check_BaseInfo b(nolock),Ipqc_Ri_Check_Report c(nolock), IPQCEuser d(nolock)" +
						" where a.create_by=d.logonName and a.check_report_no=b.check_report_no and " +
						"a.check_report_no=c.check_report_no and a.check_Id=b.check_Id and " +
						"c.report_num =? and c.mfg_Name=? and c.Floor_Name=? and c.Line_Name=? " +
						"group by a.check_report_no,b.create_date,d.ChineseName,a.check_release," +
						"c.report_num ORDER BY b.create_date DESC";
			}
		}
		if(action.equals(Action.SAVE_REPORT_CONFIG)){//保存報表配置信息
			if(step==STEP_1){//插入報表配置信息之前先把之前那筆數據刪除
				return "delete from IPQC_Check_BU_Configuration where " +
					"Report_num =? and SITE_Name=? and BU=? and MFG_Name=? and SBU_Name=?";
			}
			if(step==STEP_2){//插入新的報表配置信息
				return "insert into IPQC_Check_BU_Configuration values(?,?,?,?,?,?)";
			}
		}
		//提交參數簽核通過
		if(action.equals(Action.PARAM_CHECKPASS)){
			if(step==STEP_SMTPRINTER){//印刷機參數簽核通過
				return "exec Paperless_PrinterParameterSet_SP 'CHECK_PASS',?,'','','','','',''," +
						"'','','','','','','',?,?" ;
			}
			if(step==STEP_SMTREFLOW){//回焊爐參數簽核通過
				return "exec Paperless_ReflowParameterSet_SP 'CHECK_PASS',?,'','','','','',''," +
						"'','','','','','','','','','','','','','','','','',?,?";
			}
			if(step==STEP_PTHWS){//波峰焊參數簽核通過
				return "exec Paperless_WSParameterSet_SP 'CHECK_PASS',?,'','','','','','','',''," +
						"'','','','','','','','','','','','','','','','','','','','','','','',?,?";
			}
		}
		//提交參數簽核駁回
		if(action.equals(Action.PARAM_CHECKFAILED)){
			if(step==STEP_SMTPRINTER){//印刷機參數簽核駁回
				return "exec Paperless_PrinterParameterSet_SP 'CHECK_FAIL',?,'','','','','',''," +
						"'','','','','','','',?,?" ;
			}
			if(step==STEP_SMTREFLOW){//回焊爐參數簽核駁回
				return "exec Paperless_ReflowParameterSet_SP 'CHECK_FAIL',?,'','','','','',''," +
						"'','','','','','','','','','','','','','','','','',?,?";
			}
			if(step==STEP_PTHWS){//波峰焊參數簽核駁回
				return "exec Paperless_WSParameterSet_SP 'CHECK_FAIL',?,'','','','','','','',''," +
				"'','','','','','','','','','','','','','','','','','','','','','','',?,?";
			}
		}
		//異常管理
		if(action.equals(Action.GET_EXCEPTIONINFO)){//獲得異常基本信息
			if(step==STEP_1){//處理
				return "select a.ID,b.report_name,c.chineseName as from_user,a.content," +
						"convert(varchar(10),a.Commit_time,120) as Committime,deal_state from " +
						"IPQC_exception_feedback a(nolock),IPQC_RI_Report_Name b(nolock)," +
						"IPQCEuser c(nolock) where a.report_num=b.report_num and a.Pro_Id=b.Pro_Id and " +
						"a.from_user =c.logonName and  a.To_user=? order by a.deal_state asc,a.Commit_time desc";
			}
			if(step==STEP_2){//創建
				return "select a.ID,b.report_name,c.chineseName as to_user,a.content," +
						"convert(varchar(10),a.Commit_time,120) as Committime,deal_state from " +
						"IPQC_exception_feedback a(nolock),IPQC_RI_Report_Name b(nolock)," +
						"IPQCEuser c(nolock) where a.report_num=b.report_num and a.Pro_Id=b.Pro_Id and " +
						"a.to_user =c.logonName and  a.from_user=? order by a.Commit_time desc";
			}
		}
		if(action.equals(Action.GET_EXCEPTION_DETAILINFO)){//異常詳細信息
			if(step==STEP_1){////處理
				return "select distinct a.MFG_Name,a.floor,a.Equipment,b.Report_Name,a.Check_Report_No," +
						"a.Check_Id,a.Pro_Id,b.check_name,b.check_pro_name,c.ChineseName as from_user," +
						"a.content,a.picture_Url,a.commit_Time,a.deal_state,a.back_content,a.completeDate " +
						"from IPQC_exception_feedback a(nolock),IPQC_RI_Report_Name b(nolock)," +
						"IPQCEuser c(nolock) where a.report_num=b.report_num and a.pro_Id=b.pro_Id and " +
						"a.from_user=c.logonName and a.ID=?";
			}
			if(step==STEP_2){//創建
				return "select distinct a.MFG_Name,a.floor,a.Equipment,b.Report_Name,a.Check_Report_No," +
						"a.Check_Id,a.Pro_Id,b.check_name,b.check_pro_name,c.ChineseName as to_user," +
						"a.content,a.picture_Url,a.commit_Time,a.deal_state,a.back_content,a.completeDate " +
						"from IPQC_exception_feedback a(nolock),IPQC_RI_Report_Name b(nolock)," +
						"IPQCEuser c(nolock) where a.report_num=b.report_num and a.pro_Id=b.pro_Id and " +
						"a.to_user=c.logonName and a.ID=?";
			}
		}
		return "";
	}
}
