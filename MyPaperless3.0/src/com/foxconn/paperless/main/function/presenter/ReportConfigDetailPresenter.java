package com.foxconn.paperless.main.function.presenter;

import java.util.List;

import com.foxconn.paperless.bean.CheckItem;

public interface ReportConfigDetailPresenter {

	void getCheckItem(String reportNum);

	void selectAll(boolean checked);

	void submitSave(List<List<CheckItem>> childItem);
	void setSBU(String SBU);

	void saveReportConfig();

	

}
