package com.foxconn.paperless.main.function.presenter;

import android.content.Intent;

public interface ParameterManageAuditPresenter {

	void init(String reportNum);

	void setAuditType(int type);

	void getMySubmitInfo();

	void getMyCheckInfo();

	void goAuditDetailPage(int pos);

	void onActivityResult(int requestCode, int resultCode, Intent data);

}
