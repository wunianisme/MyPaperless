package com.foxconn.paperless.main.function.presenter;

import com.foxconn.paperless.bean.ParamInfo;

public interface ParameterManageAuditDetailPresenter {

	void init(String reportNum, int type, ParamInfo paramInfo);
	
	void getAuditDetailInfo();

	void pass();

	void failed();

	

}
