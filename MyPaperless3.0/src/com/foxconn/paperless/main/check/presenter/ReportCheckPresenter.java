package com.foxconn.paperless.main.check.presenter;

import android.content.Intent;

import com.foxconn.paperless.bean.QRReportInfo;

public interface ReportCheckPresenter {

	void toggleTopInfo(int visibility);
	
	void init(int type, String date, String time);

	void setTitle(QRReportInfo qrReportInfo);
	
	void selectCheckRemark();
	
	void getCheckItem();
	
	void getShiftCheckNode();
	
	void setRNO();

	void getBaseInfo(String workorderNo);

	void scanWO();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void submitCheckNoInput();
	
	void submitCheckNoInput(String checkRemark, String checkRemarkReason);

	void submitCheckPdOrIPQC(String checkRemark, String checkRemarkReason,
			String checkType,String deviation,String side);

	void clearFocus();

	void getCheckBy(String team);

	void getMaster();

	void addCheckBy(int tag);

	void deleteCheckBy(int tag);

	void queryCheckBy(String logonName);

	void submitNextStep(int tag);

	void submitException(String exception);

	void OpenGallery();

	void openCamera();

	void cancel(boolean isSubmit);

	void scanCheckContent(int tag);

	void openCheckCamera(int tag);

	void setSubmitAction(int submitAction);

	void submitCheck();

	void setCheckRemark(String checkRemark);

	

	

	


	

}
