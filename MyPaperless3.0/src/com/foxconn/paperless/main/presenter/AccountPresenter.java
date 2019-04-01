package com.foxconn.paperless.main.presenter;

import android.content.Intent;
import android.widget.ImageView;

public interface AccountPresenter {

	void getAccountName();
	
	void getHeadPortrait();

	void openGallery();

	void openCamera();

	void onActivityResult(int requestCode, int resultCode, Intent data);

	void logoff();

	void contactUs();

	void checkApkVersion();

	void getApkInfo();

	void downloadApk();

}
