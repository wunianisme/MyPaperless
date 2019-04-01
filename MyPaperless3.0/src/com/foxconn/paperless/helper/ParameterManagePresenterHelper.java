package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ParamInfo;

public class ParameterManagePresenterHelper {
	/**
	 * 封裝參數基本信息到ParamInfo類
	 * @param result
	 * @return
	 */
	public static List<ParamInfo> getParamInfo(JsonResult result) {
		List<ParamInfo> paramInfoList=new ArrayList<ParamInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			ParamInfo paramInfo=new ParamInfo();
			paramInfo.setParameterNum(result.getData().get(i));
			paramInfo.setBuilding(result.getData().get(i+1));
			paramInfo.setLine(result.getData().get(i+2));
			paramInfo.setProductName(result.getData().get(i+3));
			paramInfo.setLastEditDate(result.getData().get(i+4));
			paramInfoList.add(paramInfo);
		}
		return paramInfoList;
	}	
	/**
	 * 清空參數詳細信息列表
	 * @param paramList
	 * @return
	 */
	public static List<String> clearParamList(List<String> paramList) {
		paramList.clear();
		for (int i = 0; i < 50; i++) {
			paramList.add("");
		}
		return paramList;
	}
	/**
	 * 將上級簽核主管信息封裝到Euser表
	 * @param result
	 * @return
	 */
	public static List<Euser> getMaster(JsonResult result) {
		List<Euser> masterList=new ArrayList<Euser>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			Euser master=new Euser();
			master.setLogonName(result.getData().get(i));
			master.setChineseName(result.getData().get(i+1));
			masterList.add(master);
		}
		return masterList;
	}
	/**
	 * 獲得上級主管的姓名
	 * @param masterList
	 * @return
	 */
	public static List<String> getMasterName(List<Euser> masterList) {
		List<String> masterNameList=new ArrayList<String>();
		for (int i = 0; i < masterList.size(); i++) {
			String name=masterList.get(i).getChineseName()
					+"（"+masterList.get(i).getLogonName()+"）";
			masterNameList.add(name);
		}
		return masterNameList;
	}
	/**
	 * 封裝簽核參數內容到ParamInfo類
	 * @param result
	 * @return
	 */
	public static List<ParamInfo> getAuditParamInfo(JsonResult result) {
		List<ParamInfo> paramInfoList=new ArrayList<ParamInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			ParamInfo paramInfo=new ParamInfo();
			paramInfo.setParameterNum(result.getData().get(i));
			paramInfo.setProductName(result.getData().get(i+1));
			paramInfo.setUpdateType(result.getData().get(i+2));
			paramInfo.setCreateBy(result.getData().get(i+3));
			paramInfo.setCreateDate(result.getData().get(i+4));
			paramInfo.setCheckState(result.getData().get(i+5));
			paramInfo.setCheckBy(result.getData().get(i+6));
			paramInfo.setCheckDate(result.getData().get(i+7));
			paramInfoList.add(paramInfo);
		}
		return paramInfoList;
	}

}
