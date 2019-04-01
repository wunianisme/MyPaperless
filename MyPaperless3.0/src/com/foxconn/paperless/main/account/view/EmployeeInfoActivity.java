package com.foxconn.paperless.main.account.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.main.account.presenter.EmployeeInfoPresenter;
import com.foxconn.paperless.main.account.presenter.EmployeeInfoPresenterImpl;
import com.foxconn.paperless.ui.adapter.EmployeeInfoListViewAdapter;
/**
 * 員工信息
 *@ClassName EmployeeInfoActivity
 *@Author wunian
 *@Date 2017/10/21 上午11:23:50
 */
public class EmployeeInfoActivity extends BaseActivity implements EmployeeInfoView{
	
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	@ViewInject(id=R.id.actvSearch)AutoCompleteTextView actvSearch;
	@ViewInject(id=R.id.ibSearch,click="btnClick")ImageButton ibSearch;
	@ViewInject(id=R.id.lvEmployeeInfo)ListView lvEmployeeInfo;

	private Context context;
	private EmployeeInfoPresenter employeeInfoPresenter;
	private EmployeeInfoListViewAdapter employeeInfoAdapter;
	private ArrayAdapter<String> searchAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employeeinfo);
		init();
		getEmployeeInfo();
	}
	
	@Override
	protected void init() {
		context=EmployeeInfoActivity.this;
		employeeInfoPresenter=new EmployeeInfoPresenterImpl(this, context);
		tvTitle.setText(R.string.employeeinfo);
		employeeInfoPresenter.showAddMenu();//根據等級判斷是否顯示添加菜單
		lvEmployeeInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				employeeInfoPresenter.goEmployeeDetailPage(pos);
			}
		});
		actvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				employeeInfoPresenter.onSearchItemSelected(arg0.getItemAtPosition(pos).toString());
			}
		});
	}
	
	@Override
	public void showAdd(int visible) {
		tvRight.setText(R.string.addemployee);
		tvRight.setVisibility(visible);
	}
	
	@Override
	public void getEmployeeInfo() {
		employeeInfoPresenter.getEmployeeInfo();
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvRight:
			employeeInfoPresenter.goEmployeeAddPage();
			break;
		case R.id.ibSearch:
			employeeInfoPresenter.search(actvSearch.getText().toString());
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

	@Override
	public void setEmployeeInfoAdapter(List<Euser> employeeInfoList) {
		employeeInfoAdapter=new EmployeeInfoListViewAdapter(context, employeeInfoList, employeeInfoPresenter);
		lvEmployeeInfo.setAdapter(employeeInfoAdapter);
	}

	@Override
	public void gotoActivity(Class<EmployeeDetailOrAddActivity> cls,
			Bundle bundle) {
		goActivity(cls, bundle);
	}

	@Override
	public void setSearchAdapter(List<String> employeeList) {
		searchAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, employeeList);
		actvSearch.setAdapter(searchAdapter);
	}
}
