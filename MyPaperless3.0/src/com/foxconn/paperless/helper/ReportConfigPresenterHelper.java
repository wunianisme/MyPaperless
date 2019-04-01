package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportConfig;

public class ReportConfigPresenterHelper {
	/**
	 * 將報表信息封裝到ReportInfo類中
	 * 
	 * @param result
	 * @return
	 */
	public static List<ReportInfo> getReportInfo(JsonResult result) {

		List<ReportInfo> ReportInfoList = new ArrayList<ReportInfo>();
		for (int i = 0; i < result.getData().size(); i += result.getColumnNum()) {
			ReportInfo reportInfo = new ReportInfo();
			reportInfo.setReportNum(result.getData().get(i));
			reportInfo.setReportName(result.getData().get(i + 1));
			ReportInfoList.add(reportInfo);
		}
		return ReportInfoList;
	}
	/**
	 * 獲得點檢項
	 * @param result
	 * @return
	 */
	public static List<String> getGroupItem(JsonResult result) {
		List<String> groupItem=new ArrayList<String>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			boolean same=false;
			if(groupItem.size()>0){
				for (int j = 0; j < groupItem.size(); j++) {
					//判斷點檢項是否已經存在groupItem中
					if(groupItem.get(j).equals(result.getData().get(i+1))){
						same=true;
						break;
					}
				}
			}
			if(!same) groupItem.add(result.getData().get(i+1));
		}
		return groupItem;
	}
	/**
	 * 獲得點檢子項
	 * @param result
	 * @param groupItem
	 * @return
	 */
	public static List<List<CheckItem>> getChildItem(JsonResult result,
			List<String> groupItem) {
		 List<List<CheckItem>> childItem=new ArrayList<List<CheckItem>>();
		 for (int i = 0; i < groupItem.size(); i++) {
			 List<CheckItem> checkItem=new ArrayList<CheckItem>();
			 for (int j = 0; j < result.getData().size(); j+=result.getColumnNum()) {
				 //添加子項
				if( result.getData().get(j+1).equals(groupItem.get(i))){
					CheckItem item=new CheckItem();
					item.setProId(result.getData().get(j));
					item.setCheckName(result.getData().get(j+1));
					item.setCheckProName(result.getData().get(j+2));
					checkItem.add(item);
				}
			}
			childItem.add(checkItem);
		}
		return childItem;
	}
	/**
	 * 設置被選中的點檢子項
	 * @param result
	 * @param childItem
	 * @return
	 */
	public static List<List<CheckItem>> getCheckdProId(JsonResult result,
			List<List<CheckItem>> childItem) {
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			for (int j = 0; j < childItem.size(); j++) {
				List<CheckItem> checkItem=childItem.get(j);
				for (int z = 0; z < checkItem.size(); z++) {
					if(checkItem.get(z).getProId().equals(result.getData().get(i))){
						checkItem.get(z).setChecked(ReportConfig.CHECKED);
					}
				}
			}
		}
		return childItem;
	}
	/**
	 * 設置選中全部點檢子項或全不選
	 * @param childItem
	 * @param b
	 * @return
	 */
	public static List<List<CheckItem>> getSelectAll(
			List<List<CheckItem>> childItem, boolean checked) {
		for (int j = 0; j < childItem.size(); j++) {
			List<CheckItem> checkItem=childItem.get(j);
			for (int z = 0; z < checkItem.size(); z++) {
				if(checked){
					checkItem.get(z).setChecked(ReportConfig.CHECKED);
				}else{
					checkItem.get(z).setChecked(ReportConfig.NOCHECKED);
				}
			}
		}
		return childItem;
	}
	/**
	 * 獲得選擇的點檢子項數目
	 * @param childItem
	 * @return
	 */
	public static int getSelectItemCount(List<List<CheckItem>> childItem) {
		int count = 0;
		for (int j = 0; j < childItem.size(); j++) {
			List<CheckItem> checkItem=childItem.get(j);
			for (int z = 0; z < checkItem.size(); z++) {
				if(checkItem.get(z).getChecked().equals(ReportConfig.CHECKED)){
					count++;
				}
			}
		}
		return count;
	}
	/**
	 * 獲得製造處已配置的SBU
	 * @param result
	 * @return
	 */
	public static List<String> getSBU(JsonResult result) {
		List<String> SBUList=new ArrayList<String>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			SBUList.add(result.getData().get(i));
		}
		return SBUList;
	}
	/**
	 * 將ProId用分割符進行拼接
	 * @param checkItemList
	 * @return
	 */
	public static String getProIdStr(List<List<CheckItem>> childItem) {
		String proIdStr="";
		for (int j = 0; j < childItem.size(); j++) {
			List<CheckItem> checkItem=childItem.get(j);
			for (int z = 0; z < checkItem.size(); z++) {
				//只拼接選中項的proId
				if(checkItem.get(z).getChecked().equals(ReportConfig.CHECKED)){
					proIdStr+=checkItem.get(z).getProId()+ReportConfig.SPLIT;
				}
			}
		}
		return proIdStr;
	}
	/**
	 * 查詢用戶搜尋的報表名對應的信息（包含即可）
	 * @param allReportInfoList
	 * @param reportName
	 */
	public static List<ReportInfo> searchReport(List<ReportInfo> allReportInfoList,
			String reportName) {
		List<ReportInfo> reportInfoList = new ArrayList<ReportInfo>();
		for (int i = 0; i < allReportInfoList.size(); i++) {
			ReportInfo reportInfo=allReportInfoList.get(i);
			if(reportInfo.getReportName().contains(reportName)){
				reportInfoList.add(reportInfo);
			}
		}
		return reportInfoList;
	}
	

}
