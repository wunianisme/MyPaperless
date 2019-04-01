package com.foxconn.paperless.main.function.presenter;

import android.content.Intent;

public interface AuditBaseInfoPresenter {

	void getAuditBaseInfo(String RNO, String reportNum, String reportName, String checkType);

	void goAuditCheckResultPage(int pos,String checkId,String checkStatus);

	void onActivityResult(int requestCode, int resultCode, Intent data);

}
