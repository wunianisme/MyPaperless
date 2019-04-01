package com.foxconn.paperless.main.account.presenter;

import android.app.Activity;

public interface EmployeeDetailOrAddPresenter {

	void getIntent(Activity activity);
	
	void getMFGItem();

	void notifySBUDataChanged(String string);

	void notifyTeamDataChanged(String string, String string2);

	void notifySectionDataChanged(String string);

	void saveEmployeeInfo(String logonName, String ChineseName, String EnglishName,
			String ext, String email, String job, String master,
			String userlevel, String MFG, String SBU, String team,
			String section, String BU, String site);

}
