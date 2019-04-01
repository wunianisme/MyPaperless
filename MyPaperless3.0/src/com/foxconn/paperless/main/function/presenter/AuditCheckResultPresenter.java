package com.foxconn.paperless.main.function.presenter;


public interface AuditCheckResultPresenter {

	
	void getCheckResult(String checkType, String reportNum,String reportName, String RNO,
			String checkId,String checkSeqno,String checkRemark,String checkRemarkReason,
			String checkBy,String checkByName,String checkStatus,String preCheckStatus,String preCheckId);

	void submitPass();

	void submitReject(String remark);

	void goZoomImagePage(String imageData);


}
