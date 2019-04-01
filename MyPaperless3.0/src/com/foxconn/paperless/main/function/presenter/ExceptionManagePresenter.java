package com.foxconn.paperless.main.function.presenter;

import android.content.Intent;

public interface ExceptionManagePresenter {

	void setType(int type);

	void getMyDealInfo();

	void getMyCreateInfo();

	void goExceptionDetailPage(int pos);

	void onActivityResult(int requestCode, int resultCode, Intent data);

}
