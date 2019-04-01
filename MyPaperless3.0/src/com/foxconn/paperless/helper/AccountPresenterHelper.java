package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.constant.MyEnum.Language;
/**
 * 賬戶模塊的presenter助手
 *@ClassName AccountPresenterHelper
 *@Author wunian
 *@Date 2017/10/23 下午5:13:58
 */
public class AccountPresenterHelper {
	
	/**
	 * 獲取Spinner應該選中的值的索引
	 * @param data
	 * @param regExpString
	 * @return
	 */
	public static int getSelectionIndex(List<String> data,String regExpString){
		int index=0;
		if(data.size()>0){
			for (int i = 0; i < data.size(); i++) {
				if(regExpString.equalsIgnoreCase(data.get(i))){
					index=i;
					break;
				}
			}
		}
		return index;
	}
	
	/**
	 * 整理員工信息集合數據，重新進行封裝到Euser中
	 * @param data
	 */
	public static List<Euser> getEmployeeInfo(JsonResult result) {
		List<Euser> employeeInfoList=new ArrayList<Euser>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			Euser user=new Euser();
			user.setLogonName(result.getData().get(i));
			user.setChineseName(result.getData().get(i+1));
			user.setEnglishName(result.getData().get(i+2));
			user.setTitle(result.getData().get(i+3));
			user.setMaster(result.getData().get(i+4));
			employeeInfoList.add(user);
		}
		return employeeInfoList;
	}
	/**
	 * 整理員工的工號、中英文名稱，重寫添加到List集合中
	 * @param employeeInfoList
	 * @return
	 */
	public static List<String> getEmployeeLogonNameAndName(List<Euser> employeeInfoList){
		List<String> employeeList=new ArrayList<String>();
		for (int i = 0; i < employeeInfoList.size(); i++) {
			Euser user=employeeInfoList.get(i);
			employeeList.add(user.getLogonName());
			employeeList.add(user.getChineseName());
			employeeList.add(user.getEnglishName());
		}
		return employeeList;
	}
	/**
	 * 獲取用戶搜尋之後符合條件的員工信息集合數據，重新進行封裝到Euser中
	 * @param employeeInfoList
	 * @param logonNameOrName
	 * @return
	 */
	public static List<Euser> getSearchEmployeeInfo(
			List<Euser> employeeInfoList, String logonNameOrName) {
		List<Euser> data=new ArrayList<Euser>();
		for (int i = 0; i < employeeInfoList.size(); i++) {
			Euser user=employeeInfoList.get(i);
			if(user.getLogonName().contains(logonNameOrName)
					||user.getChineseName().contains(logonNameOrName)
					||user.getEnglishName().contains(logonNameOrName)){//工號、中文名、英文名其中一個包含輸入文本即可
				data.add(user);
			}
		}
		return data;
	}
}
