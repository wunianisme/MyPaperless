package com.foxconn.paperless.main.function.presenter;

import android.content.Intent;

public interface ParameterManagePresenter {

	void getFloorName();

	void getLineName(String floorName);
	
	void setReportNum(String reportNum);

	void search(String floorName, String lineName, String skuno);

	void goParameterManageDetailPage(int pos);


	void scanSkuNo();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void auditMessage();


}
