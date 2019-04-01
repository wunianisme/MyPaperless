package com.foxconn.paperless.main.function.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.CheckSearch;
import com.foxconn.paperless.main.function.presenter.CheckSearchPresenter;
import com.foxconn.paperless.main.function.presenter.CheckSearchPresenterImpl;
import com.foxconn.paperless.ui.adapter.CheckSearchListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 點檢查詢
 *@ClassName CheckSearchActivity
 *@Author wyb  update wunian
 *@Date 2017/12/28 下午2:33:00
 */
public class CheckSearchActivity extends BaseActivity implements CheckSearchView {

	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id = R.id.etSitTime, click = "btnClick")
	TextView etSitTime;
	@ViewInject(id = R.id.spTallyCycle)
	Spinner spTallyCycle;
	@ViewInject(id = R.id.spClass)
	Spinner spClass;
	@ViewInject(id = R.id.spFloor)
	Spinner spFloor;
	@ViewInject(id = R.id.spSitLine)
	Spinner spSitLine;
	@ViewInject(id = R.id.tvLeft, click = "btnClick")
	TextView tvLeft;
	@ViewInject(id = R.id.btSelect, click = "btnClick")
	Button btnSelect;
	@ViewInject(id = R.id.lvSearchList)
	ListView refreshListView;

	private Context context;
	private CheckSearchPresenter checkSearchPresenter;
	private String timeDate;

	private List<CheckSearch> list = new ArrayList<CheckSearch>();
	private String flag;//點檢頻率
	private String shift;//班別

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checksearch);
		init();
	}

	@Override
	protected void init() {
		context = CheckSearchActivity.this;
		checkSearchPresenter = new CheckSearchPresenterImpl(this, context);
		tvTitle.setText(R.string.check_search);
		checkSearchPresenter.getFloorName();//獲得樓層
		/* 獲取當前時間并顯示 */
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		timeDate = year + "/" + (month + 1) + "/" + day;
		etSitTime.setText(timeDate);
		//獲取樓層、線別信息
		spFloor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				checkSearchPresenter.getLineName(spFloor.getSelectedItem()
						.toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//選擇點檢頻率
		spTallyCycle.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0://班別
					flag="other";
					spClass.setEnabled(true);
					break;
				case 1://日點
					flag="day";
					spClass.setEnabled(false);
					spClass.setSelection(0);
					break;
				case 2://周
					flag="week";
					spClass.setEnabled(false);
					spClass.setSelection(0);
					break;
				case 3://月
					flag="month";
					spClass.setEnabled(false);
					spClass.setSelection(0);
					break;
				case 4://季
					flag="quarter";
					spClass.setEnabled(false);
					spClass.setSelection(0);
					break;
				default:
					break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//選擇白、晚班
		spClass.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
					case 0://全天
						shift="";
						break;
					case 1://白班
						shift="D";
						break;
					case 2://晚班
						shift="N";
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//長按item彈出報表編號、二維碼編號等信息
		refreshListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// 震动
				Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(new long[] { 0, 100 }, -1);// -1不重复
				//顯示報表名
				String reportName=(pos+1)+"、"+list.get(pos).getReportName();
				//顯示工站
				String Section=context.getResources().getString(R.string.item_Section)+
						list.get(pos).getSection();
				//顯示製造處
				String MFG=context.getResources().getString(R.string.item_MFG)+
						list.get(pos).getMFG();
				//報表編號
				String reportMsg=context.getResources().getString(R.string.item_reportnum)+
						"\n"+list.get(pos).getReportNum();
				//二維碼編號
				String qrInfoMsg=context.getResources().getString(R.string.item_qrInfo)+
						"\n"+list.get(pos).getQrInfo();
				String message=reportName+"\n"+Section+"\n"+MFG+"\n"+reportMsg+"\n"+qrInfoMsg;
				CustomerDialog dialog=new CustomerDialog(context, R.string.check_title, message, true);
				return false;
			}
		});

	}

	@Override
	public void setCheckStatusAdapter(List<CheckSearch> checkStatusList) {
		this.list=checkStatusList;

		CheckSearchListViewAdapter adapter=new CheckSearchListViewAdapter(context, checkStatusList);
		refreshListView.setAdapter(adapter);
	}

	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.etSitTime:
			//獲取當前時間
			checkSearchPresenter.getTime();
			break;
		case R.id.btSelect:
			//按條件獲取點檢狀態信息
			checkSearchPresenter.getCheckStatus(
					flag,
					spFloor.getSelectedItem().toString(),
					spSitLine.getSelectedItem().toString(),
					shift,
					etSitTime.getText().toString()
					);
			break;
		default:
			break;
		}
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
	/**
	 * 顯示所選的日期
	 */
	@Override
	public void setDatePickDialog(int year, int month, int date) {

		DatePickerDialog dialog = new DatePickerDialog(context,
				new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month,
					int date) {
				String dateStr = year + "/" + (month + 1) + "/" + date;
				etSitTime.setText(dateStr);
			}
		}, year, month, date);
		dialog.show();
	}

	@Override
	public void setFloorNameAdapter(List<String> floorNameList) {
		ArrayAdapter<String> floorNameAdapter = new ArrayAdapter<String>(
				context, R.layout.layout_spinner_item, floorNameList);
		spFloor.setAdapter(floorNameAdapter);
		spFloor.setSelection(0, false);

	}

	@Override
	public void setlineNameAdapter(List<String> lineNameList) {
		ArrayAdapter<String> lineNameAdapter = new ArrayAdapter<String>(
				context, R.layout.layout_spinner_item, lineNameList);
		spSitLine.setAdapter(lineNameAdapter);
		spSitLine.setSelection(0, false);
	}
}
