package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.main.function.presenter.ParameterManagePresenter;
import com.foxconn.paperless.main.function.presenter.ParameterManagePresenterImpl;
import com.foxconn.paperless.ui.adapter.ParamInfoListViewAdapter;
/**
 * 參數管理
 *@ClassName ParameterManageActivity
 *@Author wunian
 *@Date 2018/1/11 下午4:19:26
 */
public class ParameterManageActivity extends BaseActivity implements
		ParameterManageView {
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	@ViewInject(id=R.id.tvPoint)TextView tvPoint;
	
	@ViewInject(id=R.id.spParamTable)Spinner spParamTable;
	@ViewInject(id=R.id.spFloorName)Spinner spFloorName;
	@ViewInject(id=R.id.spLineName)Spinner spLineName;
	@ViewInject(id=R.id.ibParamTableDropdown,click="btnClick")ImageButton ibParamTableDropdown;
	@ViewInject(id=R.id.ibFloorNameDropdown,click="btnClick")ImageButton ibFloorNameDropdown;
	@ViewInject(id=R.id.ibLineNameDropdown,click="btnClick")ImageButton ibLineNameDropdown;
	@ViewInject(id=R.id.ivScanSkuNo,click="btnClick")ImageView ivScanSkuNo;
	@ViewInject(id=R.id.ibSearch,click="btnClick")ImageButton ibSearch;
	@ViewInject(id=R.id.etSkuNo)EditText etSkuNo;
	@ViewInject(id=R.id.lvParamItem)ListView lvParamItem;
			
	private Context context;
	private ParameterManagePresenter presenter;
	private String reportNum;
	private ParamInfoListViewAdapter paramInfoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametermanage);
		init();
	}
	
	@Override
	protected void init() {
		context=ParameterManageActivity.this;
		presenter=new ParameterManagePresenterImpl(this, context);
		tvTitle.setText(R.string.parameter_manage);
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(R.string.audit_message);
		
		Bundle bundle=getIntent().getExtras();
		//顯示有待簽核參數提示的小圓點
		int msgNum=bundle.getInt("msgNum");
		if(msgNum>0){
			tvPoint.setVisibility(View.VISIBLE);
		}
		presenter.getFloorName();
		spParamTable.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				switch (pos) {
				case 0://SMT印刷機參數表
					reportNum=ReportNum.SMT_PRINTER;
					break;
				case 1://SMT回焊爐參數表
					reportNum=ReportNum.SMT_REFLOW;				
					break;
				case 2://PTH波峰焊參數表
					reportNum=ReportNum.PTH_WS;
					break;
				default:
					break;
				}
				presenter.setReportNum(reportNum);
				presenter.getFloorName();
				ibSearch.performClick();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		spFloorName.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				presenter.getLineName(arg0.getItemAtPosition(pos).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		lvParamItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				presenter.goParameterManageDetailPage(pos);
			}
		});
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			scrollParamItem();
			break;
		case R.id.tvRight://添加參數
			presenter.auditMessage();
			break;
		case R.id.ibParamTableDropdown:
			spParamTable.performClick();
			break;
		case R.id.ibFloorNameDropdown:
			spFloorName.performClick();
			break;
		case R.id.ibLineNameDropdown:
			spLineName.performClick();
			break;
		case R.id.ibSearch://查詢參數信息
			presenter.search(
					spFloorName.getSelectedItem().toString(),
					spLineName.getSelectedItem().toString(),
					etSkuNo.getText().toString()
				);
			break;
		case R.id.ivScanSkuNo:
			presenter.scanSkuNo();
			break;
		default:
			break;
		}
	}
	/**
	 * 滾動點檢條目
	 */
	private void scrollParamItem() {
		lvParamItem.post(new Runnable() {
			@Override
			public void run() {
				if(paramInfoAdapter!=null){
					paramInfoAdapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					if(lvParamItem.getFirstVisiblePosition()!=0){//滾動到頂部
						lvParamItem.setSelection(0);
					}else{
						lvParamItem.setSelection(paramInfoAdapter.getCount()-1);
					}
				}
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
	public void setFloorNameAdapter(List<String> floorNameList) {
		ArrayAdapter<String> floorNameAdapter=new ArrayAdapter<String>(context,
				R.layout.layout_spinner_item, floorNameList);
		spFloorName.setAdapter(floorNameAdapter);
	}

	@Override
	public void setLineNameAdapter(List<String> lineNameList) {
		ArrayAdapter<String> lineNameAdapter=new ArrayAdapter<String>(context,
				R.layout.layout_spinner_item, lineNameList);
		spLineName.setAdapter(lineNameAdapter);
	}

	@Override
	public void setParamInfoAdapter(List<ParamInfo> paramInfoList) {
		paramInfoAdapter=new ParamInfoListViewAdapter(context, paramInfoList);
		lvParamItem.setAdapter(paramInfoAdapter);
	}

	@Override
	public void gotoActivity(Class<?> cls, Bundle bundle) {
		goActivity(cls, bundle);
	}

	@Override
	public void gotoActivityForResult(Class<?> cls, Bundle bundle,
			int requestCode) {
		goActivityForResult(cls, bundle, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		presenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void inputSkuNo(String skuNo) {
		etSkuNo.setText(skuNo);
	}

	@Override
	public void showAuditMessageView(int visible) {
		tvRight.setVisibility(visible);
	}

}
