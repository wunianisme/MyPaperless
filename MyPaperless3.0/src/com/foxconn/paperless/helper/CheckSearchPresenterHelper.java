package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.CheckSearch;
import com.foxconn.paperless.bean.JsonResult;
/**
 * 點檢查詢的presenter助手
 *@ClassName CheckSearchPresenterHelper
 *@Author wunian
 *@Date 2017/12/28 下午3:25:36
 */
public class CheckSearchPresenterHelper {
	/**
	 * 將點檢狀態數據封裝到CheckSearch類中
	 * @param result
	 * @return
	 */
	public static List<CheckSearch> getCheckStatus(JsonResult result) {
		List<CheckSearch> checkStatusList=new ArrayList<CheckSearch>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			CheckSearch checkSearch=new CheckSearch();
			checkSearch.setReportNum(result.getData().get(i));
			checkSearch.setReportName(result.getData().get(i+1));
			checkSearch.setMFG(result.getData().get(i+2));
			checkSearch.setFloorName(result.getData().get(i+3));
			checkSearch.setEquipment(result.getData().get(i+4));
			checkSearch.setLineName(result.getData().get(i+5));
			checkSearch.setQrInfo(result.getData().get(i+6));
			checkSearch.setBU(result.getData().get(i+7));
			checkSearch.setYieldName(result.getData().get(i+8));
			checkSearch.setCreateBy(result.getData().get(i+9));
			checkSearch.setSection(result.getData().get(i+10));
			checkSearch.setCheckFlag(result.getData().get(i+11));
			checkSearch.setChineseName(result.getData().get(i+12));
			checkStatusList.add(checkSearch);
		}
		return checkStatusList;
	}

	
}
