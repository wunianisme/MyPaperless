package com.foxconn.paperless.main.account.presenter;

import android.widget.TextView;

import com.foxconn.paperless.bean.Euser;

public interface EmployeeInfoPresenter {

	void getEmployeeInfo();

	void setName(TextView tvName,Euser euser);

	void goEmployeeDetailPage(int pos);

	void onSearchItemSelected(String logonNameOrName);

	void search(String text);

	void goEmployeeAddPage();

	void showAddMenu();

}
