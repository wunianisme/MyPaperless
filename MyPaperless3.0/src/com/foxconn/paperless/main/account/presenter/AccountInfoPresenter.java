package com.foxconn.paperless.main.account.presenter;

public interface AccountInfoPresenter {

	void getAccountInfo();
	
	void notifySBUDataChanged(String MFG);
	
	void notifyTeamDataChanged(String SBU,String MFG);
	
	void notifySectionDataChanged(String SBU);
	
	void saveAccountInfo(String ChineseName,String EnglishName,String ext,
			String email,String MFG,String SBU,String team,String section);
}
