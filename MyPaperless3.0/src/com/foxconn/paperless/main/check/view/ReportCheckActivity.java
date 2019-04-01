package com.foxconn.paperless.main.check.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.QRReportInfo;
import com.foxconn.paperless.constant.MyEnum.ReportCheck;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.main.check.presenter.ReportCheckPresenter;
import com.foxconn.paperless.main.check.presenter.ReportCheckPresenterImpl;
import com.foxconn.paperless.ui.adapter.BitmapGridViewAdapter;
import com.foxconn.paperless.ui.adapter.ReportCheckListViewAdapter;
import com.foxconn.paperless.ui.adapter.SelectCheckByListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 報表點檢（三種報表）
 *@ClassName ReportCheckActivity
 *@Author wunian
 *@Date 2017/12/4 上午9:42:35
 */
public class ReportCheckActivity extends BaseActivity implements ReportCheckView{

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.ivSubmit,click="btnClick")ImageView ivSubmit;
	//公共控件
	@ViewInject(id=R.id.tvCheckTip)TextView tvCheckTip;
	@ViewInject(id=R.id.lvCheckItem)ListView lvCheckItem;
	//CheckPdInput/IPQC
	@ViewInject(id=R.id.etWO)EditText etWO;
	@ViewInject(id=R.id.ivToggle,click="btnClick")ImageView ivToggle;
	@ViewInject(id=R.id.reportInfoLayout)LinearLayout reportInfoLayout;
	@ViewInject(id=R.id.ivScanWO,click="btnClick")ImageView ivScanWO;
	@ViewInject(id=R.id.checkpdInputLayout)LinearLayout checkpdInputLayout;
	@ViewInject(id=R.id.checkIdLayout)LinearLayout checkIdLayout;//IPQC
	@ViewInject(id=R.id.tvCheckId)TextView tvCheckId;//IPQC節次
	@ViewInject(id=R.id.tvRNO)TextView tvRNO;
	@ViewInject(id=R.id.tvLineName)TextView tvLineName;
	@ViewInject(id=R.id.tvSkuno)TextView tvSkuno;
	@ViewInject(id=R.id.tvSkuVersion)TextView tvSkuVersion;
	@ViewInject(id=R.id.tvQty)TextView tvQty;
	@ViewInject(id=R.id.etDeviation,click="btnClick")EditText etDeviation;
	@ViewInject(id=R.id.etSide,click="btnClick")EditText etSide;
	@ViewInject(id=R.id.spCheckRemark)Spinner spCheckRemark;
	@ViewInject(id=R.id.spCheckRemarkReason)Spinner spCheckRemarkReason;
	@ViewInject(id=R.id.spCheckType)Spinner spCheckType;
	//CheckNotInput
	@ViewInject(id=R.id.checkNoInputLayout)LinearLayout checkNoInpuLayout;
	@ViewInject(id=R.id.reportInfoLayout2)LinearLayout reportInfoLayout2;
	@ViewInject(id=R.id.tvRNO2)TextView tvRNO2;
	@ViewInject(id=R.id.ivToggle2,click="btnClick")ImageView ivToggle2;
	@ViewInject(id=R.id.tvFloorName)TextView tvFloorName;
	@ViewInject(id=R.id.tvLineName2)TextView tvLineName2;
	//提前維護時顯示備註選項
	@ViewInject(id=R.id.checkRemarkLayout)LinearLayout checkRemarkLayout;
	@ViewInject(id=R.id.spCheckRemark2)Spinner spCheckRemark2;
	@ViewInject(id=R.id.spCheckRemarkReason2)Spinner spCheckRemarkReason2;
	//新增的控件（增加提前維護功能）
	
	private Context context;
	private ReportCheckPresenter reportCheckPresenter;
	private ReportCheckListViewAdapter checkItemAdapter;
	private CustomerDialog submitExceptionDialog;
	private CustomerDialog submitCheckDialog;
	private CustomerDialog deviationDialog;
	private CustomerDialog sideDialog;
	private String reportNum;
	private int type;
	//提前維護日期時間
	private String date;
	private String time;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportcheck);
		init();
	}
	
	@Override
	protected void init() {
		context=ReportCheckActivity.this;
		reportCheckPresenter=new ReportCheckPresenterImpl(this, context);
		Bundle bundle=getIntent().getExtras();
		QRReportInfo qrReportInfo=(QRReportInfo)bundle.getSerializable("qrReportInfo");
		reportNum=qrReportInfo.getReportNum();
		//獲取數據
		type=bundle.getInt("type");
		if(type!=ReportCheck.NORMAL){//提前維護/補點檢
			date=bundle.getString("date");
			time=bundle.getString("time");
		}
		reportCheckPresenter.init(type,date,time);//初始化
		reportCheckPresenter.setTitle(qrReportInfo);//設置頂部標題
		reportCheckPresenter.selectCheckRemark();
		
		reportCheckPresenter.getCheckItem();//獲得點檢項
		
		etWO.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				reportCheckPresenter.getBaseInfo(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		//備註(SMT開線&換線點檢表-FD3NMD219001A)選擇開線/換線時，部分點檢項要帶參數
		spCheckRemark.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				reportCheckPresenter.setCheckRemark(spCheckRemark.getItemAtPosition(arg2).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	
	@Override
	public void setPageTitle(String reportName){
		tvTitle.setText(reportName);
		tvTitle.setTextSize(16f);
		ivSubmit.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void showSaveInputInfo(String workorderNo) {
		if(etWO.getVisibility()==View.VISIBLE&&!TextUtil.isEmpty(workorderNo)){
			etWO.setText(workorderNo);
		}
		checkItemAdapter.notifyDataSetChanged();
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			showCancelDialog();//顯示退出的提示框
			break;
		case R.id.tvTitle://點擊標題，滑動點檢項
			scrollCheckItem();
			break;
		case R.id.ivSubmit:
			if(checkpdInputLayout.getVisibility()==View.VISIBLE){
				reportCheckPresenter.submitCheckPdOrIPQC(
						spCheckRemark.getSelectedItem().toString(),
						spCheckRemarkReason.getSelectedItem().toString(),
						spCheckType.getSelectedItem().toString(),
						etDeviation.getText().toString(),
						etSide.getText().toString());
			}else{
				if(type!=ReportCheck.NORMAL){//提前維護/補點檢
					reportCheckPresenter.submitCheckNoInput(
							spCheckRemark2.getSelectedItem().toString(),
							spCheckRemarkReason2.getSelectedItem().toString());
				}else{
					reportCheckPresenter.submitCheckNoInput();
				}
				
			}
			break;
		case R.id.ivToggle://顯示或隱藏頂部基本信息（PD/IPQC）
			reportCheckPresenter.toggleTopInfo(reportInfoLayout.getVisibility());
			break;
		case R.id.ivToggle2://顯示或隱藏頂部基本信息(NoInput)
			reportCheckPresenter.toggleTopInfo(reportInfoLayout2.getVisibility());
			break;
		case R.id.ivScanWO://掃碼輸入工單
			reportCheckPresenter.scanWO();
			break;
		case R.id.etDeviation://輸入deviation欄位
			showInputDeviationDialog();
			break;
		case R.id.etSide://輸入side欄位
			showInputSideDialog();
		default:
			break;
		}
	}
	/**
	 * 顯示輸入side的彈出框
	 */
	private void showInputSideDialog() {
		String title=context.getResources().getString(R.string.sideinput_title);
		String hint=etSide.getHint().toString();
		String content=etSide.getText().toString();
		
		sideDialog=new CustomerDialog(context,title, hint, content, false);
		sideDialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sideDialog.dismiss();
				if(ReportNum.REPORT_SIDE.contains(reportNum)){//只有部分報表可以修改side
					etSide.setText(sideDialog.getContent());
				}
				reportCheckPresenter.clearFocus();
			}
		});
		sideDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sideDialog.dismiss();
				reportCheckPresenter.clearFocus();
			}
		});
	}

	/**
	 * 顯示輸入deviation的彈出框
	 */
	private void showInputDeviationDialog(){
		String title=context.getResources().getString(R.string.deviationinput_title);
		String hint=etDeviation.getHint().toString();
		String content=etDeviation.getText().toString();
		
		deviationDialog=new CustomerDialog(context,title, hint, content, false);
		deviationDialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deviationDialog.dismiss();
				if(ReportNum.REPORT_DEVIATION.contains(reportNum)){//只有部分報表可以修改deviation
					etDeviation.setText(deviationDialog.getContent());
				}
				reportCheckPresenter.clearFocus();
			}
		});
		deviationDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deviationDialog.dismiss();
				reportCheckPresenter.clearFocus();
			}
		});
	}
	
	/**
	 * 滾動點檢條目
	 */
	private void scrollCheckItem() {
		//int target=lvCheckItem.getLastVisiblePosition();//滾動到底部
		int target = 0;
		if(checkItemAdapter!=null) target=checkItemAdapter.getCount()-1;
		if(lvCheckItem.getFirstVisiblePosition()!=0){//滾動到頂部
			target=0;
		}
		final int position=target;
		lvCheckItem.post(new Runnable() {
			
			@Override
			public void run() {
				if(checkItemAdapter!=null){
					checkItemAdapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					lvCheckItem.setSelection(position);
				}
			}
		});
	}
	
	private void showCancelDialog(){
		final CustomerDialog dialog=new CustomerDialog(context, R.string.cancel_tip,
				R.string.cancel_message, true);
		dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				reportCheckPresenter.cancel(false);
			}
		});
		dialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
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
	public void toggleTopInfo(int visibility,int icon) {
		reportInfoLayout.setVisibility(visibility);
		ivToggle.setImageResource(icon);
	}
	
	@Override
	public void toggleNoInputTopInfo(int visibility, int icon) {
		reportInfoLayout2.setVisibility(visibility);
		ivToggle2.setImageResource(icon);
	}

	@Override
	public void setCheckPdInputRNO(String RNO, String lineName) {
		tvRNO.setText(RNO);
		tvLineName.setText(lineName);
	}
	
	@Override
	public void setCheckNoInputRNO(String RNO, String floorName,
			String equipment) {
		tvRNO2.setText(RNO);
		tvFloorName.setText(floorName);
		tvLineName2.setText(equipment);
	}

	@Override
	public void setBaseInfo(String skuNo, String skuVersion, String qty,
			String deviation) {
		
		tvSkuno.setText(skuNo);
		tvSkuVersion.setText(skuVersion);
		tvQty.setText(qty);
		etDeviation.setText(deviation);
	}

	@Override
	public void showCheckId() {
		checkIdLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void showTopLayout(int checkInputVisibility,
			int checkNoInputVisibility) {
		checkpdInputLayout.setVisibility(checkInputVisibility);
		checkNoInpuLayout.setVisibility(checkNoInputVisibility);
		//不帶工單表單且提前維護或補點檢時顯示備註選擇框
		if(checkNoInputVisibility==View.VISIBLE&&type!=ReportCheck.NORMAL){
			checkRemarkLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void setCheckItemAdapter(List<CheckItem> checkItemList) {
		checkItemAdapter=new ReportCheckListViewAdapter(context, checkItemList, reportCheckPresenter);
		lvCheckItem.setAdapter(checkItemAdapter);
	}

	@Override
	public void setCheckNode(String checkNode) {
		tvCheckId.setText(checkNode);
	}

	@Override
	public void gotoActivityForResult(Class<?> cls, Bundle bundle,
			int requestCode) {
		goActivityForResult(cls, bundle, requestCode);
	}

	@Override
	public void setWO(String WO) {
		etWO.setText(WO);
	}

	@Override
	public void setSide(String side) {
		etSide.setText(side);
	}

	@Override
	public void showGetParamsSuccessTip(int visibility) {
		tvCheckTip.setVisibility(visibility);
	}

	@Override
	public void showTipDialog(int titleId, int msgId) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, msgId, false);
		dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	@Override
	public List<CheckItem> getCheckItemList() {
		return checkItemAdapter.getChecKItemList();
	}

	@Override
	public void showTipDialog(int titleId, String message) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, message, false);
		dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	@Override
	public void clearFocus() {
		etWO.clearFocus();
	}

	@Override
	public void setCheckByAdapter(List<Euser> checkByList) {
		checkItemAdapter.setCheckByAdapter(checkByList);
	}

	@Override
	public void setSelectedCheckByAdapter(List<Euser> selectedCheckByList) {
		checkItemAdapter.setSelectedCheckByAdapter(selectedCheckByList);
	}

	@Override
	public void dismissSelectCheckByDialog() {
		checkItemAdapter.dismissSelectCheckByDialog();
	}

	@Override
	public void showSubmitExceptionDialog(String title,
			List<Euser> selectedCheckByList) {
		submitExceptionDialog=new CustomerDialog(
				context, title, false,CustomerDialog.ACTION_SUBMITEXCEPTION );
		submitExceptionDialog.setOKBtn(R.string.btn_submitexception, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportCheckPresenter.submitException(
						submitExceptionDialog.getEtException().getText().toString());
			}
		});
		submitExceptionDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitExceptionDialog.dismiss();
			}
		});
		submitExceptionDialog.getIvGallery().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportCheckPresenter.OpenGallery();
			}
		});
		submitExceptionDialog.getIvCamera().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportCheckPresenter.openCamera();
			}
		});
		SelectCheckByListViewAdapter adapter=new SelectCheckByListViewAdapter(
				context, selectedCheckByList, reportCheckPresenter,2);
		submitExceptionDialog.getLvSelectedCheckBy().setAdapter(adapter);
	}

	@Override
	public void goActivityForResult(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		reportCheckPresenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void setBitmapAdapter(List<Bitmap> bitmapList) {
		BitmapGridViewAdapter adapter=new BitmapGridViewAdapter(
				context, bitmapList);
		submitExceptionDialog.getGvPhoto().setAdapter(adapter);
	}

	@Override
	public void dismissSubmitExceptionDialog() {
		submitExceptionDialog.dismiss();
	}

	@Override
	public void cancel() {
		finish();
	}

	@Override
	public void setCheckContent(int tag, String checkContent) {
		checkItemAdapter.setCheckContent(tag,checkContent);
	}

	@Override
	public void setTakePhotoBitmap(int tag, String path) {
		checkItemAdapter.setTakePhotoBitmap(tag,path);
	}

	@Override
	public void showCheckByDialog(String title, int btnOK, int btnNo) {
		submitCheckDialog=new CustomerDialog(context,title,
				new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						//根據Team獲取具有簽核權限的人員名單
						reportCheckPresenter.getCheckBy(arg0.getItemAtPosition(pos).toString());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				},false);
		//提交點檢信息
		submitCheckDialog.setOKBtn(btnOK,
				new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportCheckPresenter.submitCheck();//提交點檢信息
			}
		});
		submitCheckDialog.setNoBtn(btnNo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitCheckDialog.dismiss();
			}
		});
		submitCheckDialog.getEtMaster().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				reportCheckPresenter.queryCheckBy(s.toString());
			}
		});
	}

	@Override
	public void setSubmitCheckAdapter(List<Euser> checkByList) {
		SelectCheckByListViewAdapter checkByAdapter=new SelectCheckByListViewAdapter(
				context, checkByList, reportCheckPresenter,0);
		submitCheckDialog.getLvMaster().setAdapter(checkByAdapter);
	}

	@Override
	public void setSubmitSelectedCheckAdapter(List<Euser> selectedCheckByList) {
		SelectCheckByListViewAdapter selectedCheckByAdapter=new SelectCheckByListViewAdapter(
				context, selectedCheckByList, reportCheckPresenter,1);
		submitCheckDialog.getLvCheckBy().setAdapter(selectedCheckByAdapter);
	}

	@Override
	public void dismissSubmitCheckDialog() {
		submitCheckDialog.dismiss();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK 
				&& event.getAction() == KeyEvent.ACTION_DOWN){
			showCancelDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void notifyCheckItemDataSetChanged() {
		checkItemAdapter.notifyDataSetChanged();
	}

	@Override
	public void showCheckRemarkSpinner(String[] checkRemarkArray) {
		ArrayAdapter<String> checkRemarkAdapter=new ArrayAdapter<String>(context,
				R.layout.layout_spinner_item, checkRemarkArray);
		if(type!=ReportCheck.NORMAL){//提前維護/補點檢
			spCheckRemark2.setAdapter(checkRemarkAdapter);
		}else{//普通點檢(輸工單類型)
			if(checkpdInputLayout.getVisibility()==View.VISIBLE){
				spCheckRemark.setAdapter(checkRemarkAdapter);
			}
		}
	}
}
