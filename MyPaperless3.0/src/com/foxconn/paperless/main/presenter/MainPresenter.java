package com.foxconn.paperless.main.presenter;

import android.app.Activity;
import android.content.Intent;

public interface MainPresenter {

	void changeFragment(Activity activity,int checkId);

	void openScanQRPage();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void selectCheckReport(int pos);
}
