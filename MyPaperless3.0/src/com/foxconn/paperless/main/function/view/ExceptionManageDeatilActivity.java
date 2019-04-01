package com.foxconn.paperless.main.function.view;

import java.io.File;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ExceptionFeedback;
import com.foxconn.paperless.editimage.ZoomImageActivity;
import com.foxconn.paperless.main.function.presenter.ExceptionManageDetailPresenter;
import com.foxconn.paperless.main.function.presenter.ExceptionManageDetailPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.util.DensityUtil;
/**
 * 異常詳細信息
 *@ClassName ExceptionManageDeatilActivity
 *@Author wunian
 *@Date 2018/1/20 上午8:19:06
 */
public class ExceptionManageDeatilActivity extends BaseActivity implements
		ExceptionManageDetailView {
	
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	
	@ViewInject(id=R.id.tvProId)TextView tvProId;
	@ViewInject(id=R.id.tvReportName)TextView tvReportName;
	@ViewInject(id=R.id.tvEquipment)TextView tvEquipment;
	@ViewInject(id=R.id.tvCheckName)TextView tvCheckName;
	@ViewInject(id=R.id.tvCheckProName)TextView tvCheckProName;
	@ViewInject(id=R.id.tvRNO)TextView tvRNO;
	@ViewInject(id=R.id.tvMFGFloor)TextView tvMFGFloor;
	@ViewInject(id=R.id.tvCheckId)TextView tvCheckId;
	@ViewInject(id=R.id.tvUserNameStr)TextView tvUserNameStr;
	@ViewInject(id=R.id.tvUserName)TextView tvUserName;
	@ViewInject(id=R.id.tvContent) TextView tvContent;
	@ViewInject(id=R.id.picLayout)LinearLayout picLayout;
	@ViewInject(id=R.id.imgLayout)LinearLayout imgLayout;
	@ViewInject(id=R.id.ivPic)ImageView ivPic;
	@ViewInject(id=R.id.ivPic2)ImageView ivPic2;
	@ViewInject(id=R.id.ivPic3)ImageView ivPic3;
	
	@ViewInject(id=R.id.tvCommitTime)TextView tvCommitTime;
	@ViewInject(id=R.id.tvDealState)TextView tvDealState;
	@ViewInject(id=R.id.tvBackContent)TextView tvBackContent;
	@ViewInject(id=R.id.tvCompleteTime)TextView tvCompleteTime;
	@ViewInject(id=R.id.btnLayout)LinearLayout btnLayout;
	@ViewInject(id=R.id.btnPass,click="btnClick")Button btnPass;
	@ViewInject(id=R.id.btnReject,click="btnClick")Button btnReject;
	
	
	private ExceptionManageDetailPresenter presenter;
	private Context context;
	private int type;
	private CustomerDialog backContentDialog;
	
	private ImageView[] imgArray;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exceptionmanagedetail);
		init();
	}
	
	@Override
	protected void init() {
		context=ExceptionManageDeatilActivity.this;
		presenter=new ExceptionManageDetailPresenterImpl(this, context);
		imgArray=new ImageView[]{ivPic,ivPic2,ivPic3};
		tvTitle.setText(R.string.ecdetail_title);
		Bundle bundle=getIntent().getExtras();
		type=bundle.getInt("type");
		String id=bundle.getString("ID");
		presenter.init(type,id);
		presenter.getExceptionDetail();//獲得異常詳細信息
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.btnPass:
			showPassDialog();
			break;
		case R.id.btnReject:
			showRejectDialog();
			break;

		default:
			break;
		}
	}
	/**
	 * 顯示通過的彈出框
	 */
	private void showPassDialog(){
		String title=context.getResources().getString(R.string.ecpass_title);
		String hint=context.getResources().getString(R.string.backcontent_hint);
		backContentDialog=new CustomerDialog(context, title, hint, null, false);
		backContentDialog.setOKBtn(R.string.btn_submitec, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.pass(backContentDialog.getContent());
			}
		});
		backContentDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backContentDialog.dismiss();
			}
		});
	}
	/**
	 * 顯示拒簽的彈出框
	 */
	private void showRejectDialog(){
		String title=context.getResources().getString(R.string.ecreject_title);
		String hint=context.getResources().getString(R.string.rejectcontent_hint);
		backContentDialog=new CustomerDialog(context, title, hint, null, false);
		backContentDialog.setOKBtn(R.string.btn_submitec, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.reject(backContentDialog.getContent());
			}
		});
		backContentDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backContentDialog.dismiss();
			}
		});
	}

	@Override
	public void showToastSuccessMsg(int strId) {
		showSuccess(strId);
	}

	@Override
	public void showToastFailedMsg(int strId) {
		showError(strId);
	}

	@Override
	public void showToastExceptionMsg(String msg) {
		showException(msg);
	}

	@Override
	public void showLoading() {
		showLoadingDialog();
	}

	@Override
	public void dismissLoading() {
		dismissLoadingDialog();
	}

	@Override
	public void showBtnLayout(int btnLayoutVisible,int picLayoutVisible,int userNameId) {
		btnLayout.setVisibility(btnLayoutVisible);
		picLayout.setVisibility(picLayoutVisible);
		tvUserNameStr.setText(userNameId);
	}

	@Override
	public void showExceptionDetail(ExceptionFeedback exception,
			int dealStateId, String userName) {
		tvProId.setText(exception.getProId());
		tvReportName.setText(exception.getReportName());
		tvEquipment.setText(exception.getEquipment());
		tvCheckName.setText(exception.getCheckName());
		tvCheckProName.setText(exception.getCheckProName());
		tvRNO.setText(exception.getRNO());
		tvMFGFloor.setText(exception.getMFG()+"-"+exception.getFloor());
		tvCheckId.setText(exception.getCheckId());
		tvUserName.setText(userName);
		tvContent.setText(exception.getContent());
		tvCommitTime.setText(exception.getCommitTime());
		tvDealState.setText(dealStateId);
		tvBackContent.setText(exception.getBackContent());
		tvCompleteTime.setText(exception.getCompleteDate());
	}

	@Override
	public void back() {
		if(backContentDialog!=null) backContentDialog.dismiss();
		setResult(RESULT_OK);
		ExceptionManageDeatilActivity.this.finish();
	}

	@Override
	public void loadingImage(final File file,int index) {
		imgArray[index].setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
		imgArray[index].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle =new Bundle();
				bundle.putString("imageData", "");
				bundle.putString("imgPath", file.getAbsolutePath());
				goActivity(ZoomImageActivity.class, bundle);
			}
		});
	}
}
