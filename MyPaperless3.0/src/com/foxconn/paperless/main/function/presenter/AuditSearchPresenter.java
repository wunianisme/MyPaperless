package com.foxconn.paperless.main.function.presenter;

import android.content.Intent;

public interface AuditSearchPresenter {

	void setQueryType(String checkType);

	void getMFGReport();

	void selectReport(int pos);

	void getReportList();

	void search(String where);

	void setQueryBy(String queryBy);

	void setReportNum(String reportName);

	void selectDate();

	void scanQR();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void goAuditBaseInfoPage(int position);

	void goAuditBatchPage();



}
