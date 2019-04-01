package com.foxconn.paperless.main.presenter;

public interface HomePresenter {

	void getAllBU();

	void goBUReportPage(String BU);

	void setFunctionMenu();

	void goFunctionMenu(int pos);

	void goMTInadvancePage(int position);

}
