package com.foxconn.paperless.main.function.presenter;

import java.util.List;

import com.foxconn.paperless.bean.audit.CheckInfo;

public interface AuditBatchPresenter {

	void selectAll(boolean checked);

	void init(List<CheckInfo> checkInfoList);

	void showPassDialog();

	void auditPass();

}
