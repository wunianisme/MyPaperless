package com.foxconn.paperless.main.function.presenter;

import android.content.Intent;

public interface MTInadvancePresenter {

	void init(int type);
	
	void setDate();
	
	void setTime();

	void scanQR(String date,String time);
	
	void onActivityResult(int requestCode, int resultCode, Intent data);

	void selectCheckReport(int pos);

	void setDate(int year, int month, int date);

	void setTime(int hour, int minute);

}
