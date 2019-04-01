package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.bean.audit.CheckResult;
import com.foxconn.paperless.constant.MyEnum.Report;

public class AuditSearchPresenterHelper {

	/**
	 * 將製造處報表信息封裝到ReportInfo類中
	 * @param result
	 * @return
	 */
	public static List<ReportInfo> getMFGReport(JsonResult result){
		List<ReportInfo> reportInfoList=new ArrayList<ReportInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			ReportInfo reportInfo=new ReportInfo();
			reportInfo.setReportNum(result.getData().get(i));
			reportInfo.setReportName(result.getData().get(i+1));
			reportInfoList.add(reportInfo);
		}
		return reportInfoList;
	}
	/**
	 * 從製造處報表信息列表中獲取報表名稱
	 * @param reportInfoList
	 * @return
	 */
	public static List<String> getReportName(List<ReportInfo> reportInfoList) {
		List<String> reportNameList=new ArrayList<String>();
		for (int i = 0; i < reportInfoList.size(); i++) {
			ReportInfo reportInfo=reportInfoList.get(i);
			//設置顯示為報表名
			reportNameList.add(reportInfo.getReportName());
		}
		return reportNameList;
	}
	/**
	 * 將查詢出的簽核狀態信息封裝到CheckInfo類中
	 * @param result
	 * @return
	 */
	public static List<CheckInfo> getCheckInfoList(JsonResult result) {
		List<CheckInfo> checkInfoList=new ArrayList<CheckInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			CheckInfo checkInfo=new CheckInfo();
			checkInfo.setRNO(result.getData().get(i));
			checkInfo.setCreateDate(result.getData().get(i+1));
			checkInfo.setCreateByName(result.getData().get(i+2));
			checkInfo.setCheckStatus(result.getData().get(i+3));
			checkInfo.setReportNum(result.getData().get(i+4));
			checkInfoList.add(checkInfo);
		}
		return checkInfoList;
	}
	/**
	 * 從報表信息列表中篩選出用戶輸入的報表名所對應的報表編號的索引
	 * @param reportName
	 * @param reportInfoList
	 * @return
	 */
	public static String getReportNum(String reportName,
			List<ReportInfo> reportInfoList) {
		for (int i = 0; i < reportInfoList.size(); i++) {
			ReportInfo reportInfo=reportInfoList.get(i);
			if(reportInfo.getReportName().equalsIgnoreCase(reportName)){
				return reportInfo.getReportNum();
			}
		}
		return Report.DEFAULT_VALUE;
	}
	/**
	 * 根據報表編號篩選出報表名稱
	 * @param reportNum
	 * @param reportInfoList
	 * @return
	 */
	public static String getReportNameByReportNum(String reportNum,
			List<ReportInfo> reportInfoList) {
		//String reportName="";
		for (int i = 0; i < reportInfoList.size(); i++) {
			ReportInfo reportInfo=reportInfoList.get(i);
			if(reportInfo.getReportNum().equalsIgnoreCase(reportNum)){
				return reportInfo.getReportName();
			}
		}
		return "";
	}
	/**
	 *  將查詢出的簽核基本信息封裝到CheckInfo類中
	 * @param result
	 * @return
	 */
	public static List<CheckInfo> getBaseInfo(JsonResult result) {
		List<CheckInfo> checkInfoList=new ArrayList<CheckInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			CheckInfo checkInfo=new CheckInfo();
			checkInfo.setCheckId(result.getData().get(i));
			checkInfo.setRNO(result.getData().get(i+1));
			checkInfo.setCheckBy(result.getData().get(i+2));
			checkInfo.setCheckByName(result.getData().get(i+3));
			checkInfo.setCheckSeqno(result.getData().get(i+4));
			checkInfo.setCheckStatus(result.getData().get(i+5));
			checkInfo.setMFG(result.getData().get(i+6));
			checkInfo.setFloorName(result.getData().get(i+7));
			checkInfo.setWorkorderNo(result.getData().get(i+8));
			checkInfo.setLineName(result.getData().get(i+9));
			checkInfo.setSkuNo(result.getData().get(i+10));
			checkInfo.setQty(result.getData().get(i+11));
			checkInfo.setDeviation(result.getData().get(i+12));
			checkInfo.setSide(result.getData().get(i+13));
			checkInfo.setCheckRemark(result.getData().get(i+14));
			checkInfo.setCheckRemarkReason(result.getData().get(i+15));
			checkInfo.setCreateDate(result.getData().get(i+16));
			checkInfoList.add(checkInfo);
		}
		return checkInfoList;
	}
	/**
	 * 獲得簽核人的姓名;
	 * @param checkInfoList
	 * @return
	 */
	public static List<String> getCheckByName(List<CheckInfo> checkInfoList) {
		List<String> checkByNameList=new ArrayList<String>();
		for (int i = 0; i < checkInfoList.size(); i++) {
			CheckInfo checkInfo=checkInfoList.get(i);
			checkByNameList.add(checkInfo.getCheckByName());
		}
		
		return checkByNameList;
	}
	/**
	 * 將點檢詳細信息封裝到CheckReusult類中
	 * @param result
	 * @return
	 */
	public static List<CheckResult> getCheckResult(JsonResult result) {
		List<CheckResult> checkResultList=new ArrayList<CheckResult>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			CheckResult checkResult=new CheckResult();
			checkResult.setProId(result.getData().get(i));
			checkResult.setCheckName(result.getData().get(i+1));
			checkResult.setCheckYeild(result.getData().get(i+2));
			checkResult.setCheckProName(result.getData().get(i+3));
			checkResult.setCheckResult(result.getData().get(i+4));
			checkResult.setCheckContent(result.getData().get(i+5));
			checkResult.setIcarNo(result.getData().get(i+6));
			checkResult.setImageData(result.getData().get(i+7));
			checkResultList.add(checkResult);
		}
		return checkResultList;
	}
	/**
	 * 拼接簽核人為一行字符串
	 * @param checkByNameList
	 * @return
	 */
	/*public static String getCheckByStr(List<String> checkByNameList) {
		String checkByStr="";
		for (int i = 0; i < checkByNameList.size(); i++) {
			checkByStr+=checkByNameList.get(i)+" ";
		}
		return checkByStr;
	}*/
	/**
	 * 批量簽核全選或全不選
	 * @param checkInfoList
	 * @param checked
	 * @return
	 */
	public static List<CheckInfo> getSelectAll(List<CheckInfo> checkInfoList,
			boolean checked) {
		for (int i = 0; i < checkInfoList.size(); i++) {
			CheckInfo checkInfo=checkInfoList.get(i);
			if(checked){
				checkInfo.setChecked(true);
			}else{
				checkInfo.setChecked(false);
			}
		}
		return checkInfoList;
	}
	/**
	 * 獲得簽核選中的點檢信息列表
	 * @param checkInfoList
	 * @return
	 */
	public static List<CheckInfo> getSelectCheckInfo(
			List<CheckInfo> checkInfoList) {
		List<CheckInfo> selectCheckInfoList=new ArrayList<CheckInfo>();
		for (int i = 0; i < checkInfoList.size(); i++) {
			CheckInfo checkInfo=checkInfoList.get(i);
			if(checkInfo.isChecked()){
				selectCheckInfoList.add(checkInfo);
			}
		}
		return selectCheckInfoList;
	}
	/**
	 * 拼接點檢編號字符串
	 * @param selectCheckInfoList
	 * @return
	 */
	public static String getRNOStr(List<CheckInfo> selectCheckInfoList) {
		String RNOStr="";
		for (int i = 0; i < selectCheckInfoList.size(); i++) {
			RNOStr+=selectCheckInfoList.get(i).getRNO()+Report.SPLIT;
		}
		return RNOStr;
	}
	
}
