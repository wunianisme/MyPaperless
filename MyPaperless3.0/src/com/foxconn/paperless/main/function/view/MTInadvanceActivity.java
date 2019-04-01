package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.constant.MyEnum.ReportCheck;
import com.foxconn.paperless.main.function.presenter.MTInadvancePresenter;
import com.foxconn.paperless.main.function.presenter.MTInadvancePresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 提前維護(或補點檢)
 *@ClassName MTInadvanceActivity
 *@Author wunian
 *@Date 2018/1/16 下午5:18:59
 */
public class MTInadvanceActivity extends BaseActivity implements MTInadvanceView {
	
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	
	@ViewInject(id=R.id.btnSelectDate,click="btnClick")Button btnSelectDate;
	@ViewInject(id=R.id.btnSelectTime,click="btnClick")Button btnSelectTime;
	@ViewInject(id=R.id.tvDate,click="btnClick")TextView tvDate;
	@ViewInject(id=R.id.tvTime,click="btnClick")TextView tvTime;
	@ViewInject(id=R.id.btnScanQR,click="btnClick")Button btnScanQR;
	
	private Context context;
	private MTInadvancePresenter presenter;
	private CustomerDialog dialog;
	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mtinadvance);
		init();
	}
	
	@Override
	protected void init() {
		context=MTInadvanceActivity.this;
		presenter=new MTInadvancePresenterImpl(this, context);
		
		type=getIntent().getExtras().getInt("type");
		if(type==ReportCheck.SUPPLEMENT){//補點檢
			tvTitle.setText(R.string.supplement_title);
			btnScanQR.setText(R.string.supplement_scanqr);
		}else{//提前維護
			tvTitle.setText(R.string.maintenance_inadvance);
			btnScanQR.setText(R.string.scanqr_check);
		}
		presenter.init(type);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.btnSelectDate:
			presenter.setDate();
			break;
		case R.id.btnSelectTime:
			presenter.setTime();
			break;
		case R.id.tvDate:
			presenter.setDate();
			break;
		case R.id.tvTime:
			presenter.setTime();
			break;
		case R.id.btnScanQR:
			presenter.scanQR(tvDate.getText().toString(),tvTime.getText().toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void showToastSuccessMsg(int strId) {
		showToastSuccessMsg(strId);
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
	public void setDatePickDialog(int year, int month, int day) {
		//ToastHelper.showInfo(context, year+"/"+month+"/"+day, 0);
		DatePickerDialog dialog = new DatePickerDialog(context,
				new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month,
					int date) {
				
				tvDate.setText(TextUtil.getDateStr(year, month, date));
				presenter.setDate(year,month,date);
				
			}
		}, year, month, day);
		dialog.show();
	}

	@Override
	public void setTimePickDialog(int hour, int minute, final int second,
			final int milliSecond) {
		TimePickerDialog dialog=new TimePickerDialog(context, 
				new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hour, int minute) {
				tvTime.setText(TextUtil.getTimeStr(hour, minute, second, milliSecond));
				presenter.setTime(hour,minute);
			}
		}, hour, minute, true);
		dialog.show();
	}

	@Override
	public void setDate(int year, int month, int day) {
		tvDate.setText(TextUtil.getDateStr(year, month, day));
	}

	@Override
	public void setTime(int hour, int minute, int second, int milliSecond) {
		tvTime.setText(TextUtil.getTimeStr(hour, minute, second, milliSecond));
	}

	@Override
	public void showSelectReportDialog(int titleId, List<String> itemList) {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				context, android.R.layout.simple_list_item_1, itemList);
		dialog = new CustomerDialog(context, titleId, adapter, 
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						dialog.dismiss();
						presenter.selectCheckReport(pos);//選擇點檢報表
					}
		}, true);
	}

	@Override
	public void gotoActivity(Class<?> cls, Bundle bundle) {
		goActivity(cls, bundle);
	}

	@Override
	public void goActivityForResult(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		presenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void showToastFailedMsg(String msg) {
		ToastHelper.showError(context, msg, 0);
	}

}
