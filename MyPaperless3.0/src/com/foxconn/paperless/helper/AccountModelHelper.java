package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
/**
 * 賬戶模塊的Model助手
 *@ClassName AccountModelHelper
 *@Author wunian
 *@Date 2017/11/3 上午9:19:32
 */
public class AccountModelHelper {

	/**
	 * 根據製造處獲取SBU列表
	 * @param MFG
	 * @param SBUResult
	 * @return
	 */
	public static List<String> getSBUList(String MFG,JsonResult SBUResult){
		List<String> SBUList=new ArrayList<String>();
		for (int i = 0; i < SBUResult.getData().size(); i+=SBUResult.getColumnNum()) {
			if(SBUResult.getData().get(i+1).equals(MFG)){//篩選指定的製造處下的SBU
				SBUList.add(SBUResult.getData().get(i));//SBU
				//SBUList.add(SBUResult.getData().get(i+1));//MFG
			}
		}
		
		return SBUList;
	}
	/**
	 * 根據製造處和SBU獲取Team列表
	 * @param SBU
	 * @param MFG
	 * @param teamResult
	 * @return
	 */
	public static List<String> getTeamList(String SBU,String MFG,JsonResult teamResult){
		List<String> teamList=new ArrayList<String>();
		for (int i = 0; i < teamResult.getData().size(); i+=teamResult.getColumnNum()) {
			if(teamResult.getData().get(i+1).equals(SBU)
					&&teamResult.getData().get(i+2).equals(MFG)){//篩選指定的製造處和SBU下的Team
				teamList.add(teamResult.getData().get(i));//Team
				//teamList.add(teamResult.getData().get(i+1));//SBU
				//teamList.add(teamResult.getData().get(i+2));//MFG
			}
		}
		return teamList;
	}
	/**
	 * 根據SBU獲取工站列表
	 * @param SBU
	 * @param sectionResult
	 * @return
	 */
	public static List<String> getSectionList(String SBU,JsonResult sectionResult){
		List<String> sectionList=new ArrayList<String>();
		for (int i = 0; i < sectionResult.getData().size(); i+=sectionResult.getColumnNum()) {
			if(sectionResult.getData().get(i+1).equals(SBU)){//篩選指定SBU下的Team
				sectionList.add(sectionResult.getData().get(i));//section
				//sectionList.add(sectionResult.getData().get(i+1));//SBU
			}
		}
		return sectionList;
	}
	/**
	 * 只篩選出同一個MFG、SBU、Team下的員工的工號、中文名、英文名、職務和主管五項信息
	 * @param result
	 * @return
	 */
	public static JsonResult getSimpleEmployeeInfo(JsonResult result) {
		List<String> data=new ArrayList<String>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			data.add(result.getData().get(i));//工號
			data.add(result.getData().get(i+2));//中文名
			data.add(result.getData().get(i+3));//英文名
			data.add(result.getData().get(i+4));//職務
			data.add(result.getData().get(i+8));//主管
		}
		JsonResult employeeInfoResult=new JsonResult(result.getAction(), result.getResultCode(), result.getResultMsg(), 
				data, 5);
		return employeeInfoResult;
	}
	/**
	 * 根據工號篩選出單個員工的信息
	 * @param result
	 * @param logonName
	 * @return
	 */
	public static Euser getSingleEmployeeInfo(
			JsonResult result, String logonName) {
		Euser euser=new Euser();
		List<String> data=result.getData();
		for (int i = 0; i < data.size(); i+=result.getColumnNum()) {
			
			if(data.get(i).equalsIgnoreCase(logonName)){//根據工號獲得單個用戶完整信息
				euser.setEuser(
						data.get(i).toString(),
						data.get(i+1).toString(), 
						data.get(i+2).toString(), 
						data.get(i+3).toString(), 
						data.get(i+4).toString(), 
						data.get(i+5).toString(), 
						data.get(i+6).toString(), 
						data.get(i+7).toString(), 
						data.get(i+8).toString(), 
						data.get(i+9).toString(), 
						data.get(i+10).toString(), 
						data.get(i+11).toString(), 
						data.get(i+12).toString(), 
						data.get(i+13).toString(), 
						data.get(i+14).toString(), 
						data.get(i+15).toString(), 
						data.get(i+16).toString(), 
						data.get(i+17).toString());
				return euser;
			}
		}
		return euser;
	}
}
