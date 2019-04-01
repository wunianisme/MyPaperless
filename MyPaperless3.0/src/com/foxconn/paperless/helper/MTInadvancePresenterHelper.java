package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.MTLineInfo;

/**
 * 提前維護纖體的輔助類
 * @author F1333452
 *
 */
public class MTInadvancePresenterHelper {
	/**
	 * 將纖體信息封裝到MTLineInfo對象中
	 * @param result
	 * @return
	 */
	public static List<MTLineInfo> getLineInfoList(JsonResult result) {
		List<MTLineInfo> lineInfoList=new ArrayList<MTLineInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			MTLineInfo lineInfo=new MTLineInfo();
			lineInfo.setLineName(result.getData().get(i));
			lineInfo.setSite(result.getData().get(i+1));
			lineInfo.setBU(result.getData().get(i+2));
			lineInfo.setMFG(result.getData().get(i+3));
			lineInfoList.add(lineInfo);
		}
		return lineInfoList;
	}

	
}
