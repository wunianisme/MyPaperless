package com.foxconn.paperless.main.function.presenter;

public interface CheckSearchPresenter {

	void getTime();

	void getLineName(String floorName);

	void getFloorName();

	void getCheckStatus(String flag,String floorName, String lineName,
			String shift, String date);

}
