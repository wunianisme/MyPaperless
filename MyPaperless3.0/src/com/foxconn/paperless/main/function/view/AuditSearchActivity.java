package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.app.DatePickerDialog;
import android.app.Service;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.constant.MyEnum.CheckType;
import com.foxconn.paperless.main.function.model.AuditSearchModelImpl;
import com.foxconn.paperless.main.function.presenter.AuditSearchPresenter;
import com.foxconn.paperless.main.function.presenter.AuditSearchPresenterImpl;
import com.foxconn.paperless.ui.adapter.audit.AuditSearchListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 簽核查詢
 *@ClassName AuditSearchActivity
 *@Author wunian
 *@Date 2017/12/29 上午10:04:30
 */
public  class AuditSearchActivity extends BaseActivity implements AuditSearchView,
	OnCheckedChangeListener{
	
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.ivSubmit,click="btnClick")ImageView ivSubmit;
	@ViewInject(id=R.id.rgQuerySelect)RadioGroup rgQuerySelect;
	@ViewInject(id=R.id.contentLayout)FrameLayout contentLayout;
	@ViewInject(id=R.id.etWhere,click="btnClick")EditText etWhere;
	@ViewInject(id=R.id.spQueryType)Spinner spQueryType;
	@ViewInject(id=R.id.ibQueryTypeDropdown,click="btnClick")ImageButton ibQueryTypeDropdown;
	@ViewInject(id=R.id.actvReport)AutoCompleteTextView actvReport;
	@ViewInject(id=R.id.ibReportDropdown,click="btnClick")ImageButton ibReportDropdown;
	@ViewInject(id=R.id.ibSearch,click="btnClick")ImageButton ibSearch;
	@ViewInject(id=R.id.lvQueryItem)ListView lvQueryItem;

	private Context context;
	private AuditSearchPresenter auditSearchPresenter;
	private ArrayAdapter<String> reportAdapter;
	private AuditSearchListViewAdapter auditAdapter;
	private CustomerDialog selectReportDialog;
	private String checkType;
	private int pos;
	//private int lastItemPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditsearch);
		init();
	}
	
	@Override
	protected void init() {
		context=AuditSearchActivity.this;
		auditSearchPresenter=new AuditSearchPresenterImpl(this, context);
		tvTitle.setText(R.string.audit_search);
		//顯示掃碼按鈕
		ivSubmit.setVisibility(View.VISIBLE);
		ivSubmit.setImageResource(R.drawable.ic_qrcode2);
		rgQuerySelect.setOnCheckedChangeListener(this);
		checkType=CheckType.REVIEW;
		auditSearchPresenter.getMFGReport();//獲取製造處報表信息
		spQueryType.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String queryBy=AuditSearchPresenterImpl.QUERYBY_RNO;
				switch (arg2) {
				case 0://按點檢生成RNO
					queryBy=AuditSearchPresenterImpl.QUERYBY_RNO;
					break;
				case 1://按選擇時間
					queryBy=AuditSearchPresenterImpl.QUERYBY_DATE;				
					break;
				case 2://按工單
					queryBy=AuditSearchPresenterImpl.QUERYBY_WO;
					break;
				case 3://按樓層線體
					queryBy=AuditSearchPresenterImpl.QUERYBY_LINENAME;
					break;
				case 4://按機種
					queryBy=AuditSearchPresenterImpl.QUERYBY_SKUNO;
					break;
				default:
					break;
				}
				auditSearchPresenter.setQueryBy(queryBy);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {	}
		});
		actvReport.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				auditSearchPresenter.setReportNum(s.toString());
			}
		});
		lvQueryItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				auditSearchPresenter.goAuditBaseInfoPage(position);
			}
		});
		//長按條目批量簽核
		lvQueryItem.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(checkType.equals(CheckType.REVIEW)){
					Vibrator vibrator=(Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
					vibrator.vibrate(new long[] { 0, 100 }, -1);
					auditSearchPresenter.goAuditBatchPage();
				}
				return true;
			}
		
		});
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			scrollQueryItem();
			break;
		case R.id.ivSubmit:
			auditSearchPresenter.scanQR();
			break;
		case R.id.etWhere:
			auditSearchPresenter.selectDate();
			break;
		case R.id.ibQueryTypeDropdown:
			spQueryType.performClick();
			break;
		case R.id.ibReportDropdown:
			auditSearchPresenter.getReportList();
			break;
		case R.id.ibSearch:
			auditSearchPresenter.search(etWhere.getText().toString());
			break;
		default:
			break;
		}
	}
	
	/**
	 * 滾動點檢條目
	 */
	private void scrollQueryItem() {
		lvQueryItem.post(new Runnable() {
			
			@Override
			public void run() {
				if(auditAdapter!=null){
					auditAdapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					if(lvQueryItem.getFirstVisiblePosition()!=0){//滾動到頂部
						lvQueryItem.setSelection(0);
					}else{
						lvQueryItem.setSelection(auditAdapter.getCount()-1);
					}
				}
			}
		});
	}
	
	@Override
	public void showSelectReportDialog(List<String> reportNameList) {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				context, R.layout.layout_spinner_item, reportNameList);
		selectReportDialog=new CustomerDialog(context, R.string.select_report,
				adapter, new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						actvReport.setText(arg0.getItemAtPosition(pos).toString());
						auditSearchPresenter.selectReport(pos);
						selectReportDialog.dismiss();
					}
				}, true);
		
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		//待審核列表
		case R.id.rbReview:
			checkType=CheckType.REVIEW;
			break;
		//已審核列表
		case R.id.rbAudited:
			checkType=CheckType.AUDITED;
			break;
		//拒簽列表
		case R.id.rbReject:
			checkType=CheckType.REJECT;
			break;
		//記錄查詢
		case R.id.rbRecord:
			checkType=CheckType.RECORD;
			break;
		default:
			break;
		}
		//ToastHelper.showInfo(context, checkType, 0);
		auditSearchPresenter.setQueryType(checkType);//設置查詢類型標記
	}

	@Override
	public void setReportAdapter(List<String> reportNameList) {
		reportAdapter=new ArrayAdapter<String>(context,
				R.layout.layout_spinner_item, reportNameList);
		actvReport.setAdapter(reportAdapter);
	}

	@Override
	public void setAuditSearchAdapter(List<CheckInfo> checkInfoList) {
		auditAdapter=new AuditSearchListViewAdapter(
				context, checkInfoList, auditSearchPresenter, checkType);
		lvQueryItem.setAdapter(auditAdapter);
		
	}

	@Override
	public void refreshReportAdapter() {
		if(reportAdapter!=null) reportAdapter.notifyDataSetChanged();
	}

	@Override
	public void refreshAuditSearchAdapter() {
		if(auditAdapter!=null) auditAdapter.notifyDataSetChanged();
	}

	@Override
	public void setDatePickDialog(int year, int month, int date) {
		DatePickerDialog dialog = new DatePickerDialog(context,
				new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month,
					int date) {
				etWhere.setText(TextUtil.getDateStr(year, month, date));
			}
		}, year, month, date);
		dialog.show();
	}

	@Override
	public void gotoActivityForResult(Class<?> cls, Bundle bundle,
			int requestCode) {
		goActivityForResult(cls, bundle, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		auditSearchPresenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void inputWhere(String where) {
		actvReport.setText(where);
	}

	@Override
	public void gotoActivity(Class<?> class1, Bundle bundle) {
		goActivity(class1, bundle);
	}

	@Override
	public void setDefaultReportName(String reportName) {
		actvReport.setText(reportName);
	}
}
	
