package com.foxconn.paperless.main.function.presenter;

public interface MTInadvanceByLinePresenter {

	void init();

	void setFloor(int position);

	void getLineName();

	String getFloor();

	void setLineInfo(int index);

	void selectShift();

	void setShift(int position);

	void selectDate();

	void setDate(int year, int month, int date);

	void submitMTInadvance();

}
