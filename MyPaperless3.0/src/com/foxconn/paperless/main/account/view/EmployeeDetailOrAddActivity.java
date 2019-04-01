package com.foxconn.paperless.main.account.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.main.account.presenter.EmployeeDetailOrAddPresenter;
import com.foxconn.paperless.main.account.presenter.EmployeeDetailOrAddPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 顯示員工詳細信息或添加員工賬號
 *@ClassName EmployeeDetailOrAdd
 *@Author wunian
 *@Date 2017/10/24 上午9:57:35
 */
public class EmployeeDetailOrAddActivity extends BaseActivity implements EmployeeDetailOrAddView {
	
	//員工詳細信息部分控件
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	@ViewInject(id=R.id.svContainer)ScrollView svContainer;
	@ViewInject(id=R.id.tvLogonName)TextView tvLogonName;
	@ViewInject(id=R.id.tvChineseName)TextView tvChineseName;
	@ViewInject(id=R.id.tvEnglishName)TextView tvEnglishName;
	@ViewInject(id=R.id.tvExt)TextView tvExt;
	@ViewInject(id=R.id.tvEmail)TextView tvEmail;
	@ViewInject(id=R.id.tvJob)TextView tvJob;
	@ViewInject(id=R.id.tvMaster)TextView tvMaster;
	@ViewInject(id=R.id.tvUserlevel)TextView tvUserlevel;
	@ViewInject(id=R.id.tvMFG)TextView tvMFG;
	@ViewInject(id=R.id.tvSBU)TextView tvSBU;
	@ViewInject(id=R.id.tvTeam)TextView tvTeam;
	@ViewInject(id=R.id.tvSection)TextView tvSection;
	@ViewInject(id=R.id.tvBU)TextView tvBU;
	@ViewInject(id=R.id.tvSite)TextView tvSite;
	@ViewInject(id=R.id.tvLastEditby)TextView tvLastEditby;
	@ViewInject(id=R.id.tvLastEditdt)TextView tvLastEditdt;
	@ViewInject(id=R.id.lasteditbyLayout)LinearLayout lasteditbyLayout;
	@ViewInject(id=R.id.lasteditdtLayout)LinearLayout lasteditdtLayout;
	
	//添加員工部分控件
	@ViewInject(id=R.id.etLogonName)EditText etLogonName;
	@ViewInject(id=R.id.etChineseName)EditText etChineseName;
	@ViewInject(id=R.id.etEnglishName)EditText etEnglishName;
	@ViewInject(id=R.id.etExt)EditText etExt;
	@ViewInject(id=R.id.etEmail)AutoCompleteTextView etEmail;
	@ViewInject(id=R.id.etMaster)EditText etMaster;
	@ViewInject(id=R.id.spJob)Spinner spJob;
	@ViewInject(id=R.id.spUserlevel)Spinner spUserlevel;
	@ViewInject(id=R.id.spMFG)Spinner spMFG;
	@ViewInject(id=R.id.spSBU)Spinner spSBU;
	@ViewInject(id=R.id.spTeam)Spinner spTeam;
	@ViewInject(id=R.id.spSection)Spinner spSection;
	@ViewInject(id=R.id.ibJobDrop,click="btnClick")ImageButton ibJobDrop;
	@ViewInject(id=R.id.ibUserlevelDrop,click="btnClick")ImageButton ibUserlevelDrop;
	@ViewInject(id=R.id.ibMFGDrop,click="btnClick")ImageButton ibMFGDrop;
	@ViewInject(id=R.id.ibSBUDrop,click="btnClick")ImageButton ibSBUDrop;
	@ViewInject(id=R.id.ibTeamDrop,click="btnClick")ImageButton ibTeamDrop;
	@ViewInject(id=R.id.ibSectionDrop,click="btnClick")ImageButton ibSectionDrop;

	private Context context;
	private EmployeeDetailOrAddPresenter employeeDetailOrAddPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employeedetailoradd);
		init();
		getIntentData();
	}
	
	@Override
	protected void init() {
		context=EmployeeDetailOrAddActivity.this;
		employeeDetailOrAddPresenter=new EmployeeDetailOrAddPresenterImpl(this, context);
	}
	
	@Override
	public void getIntentData() {
		employeeDetailOrAddPresenter.getIntent(this);
	}
	
	@Override
	public void setEmployeeInfo(Euser euser) {
		tvTitle.setText(R.string.employeedetail_title);
		tvLogonName.setText(euser.getLogonName());
		tvChineseName.setText(euser.getChineseName());
		tvEnglishName.setText(euser.getEnglishName());
		tvExt.setText(euser.getExt());
		tvEmail.setText(euser.getEmail());
		tvJob.setText(euser.getTitle());
		tvMaster.setText(euser.getMaster());
		tvUserlevel.setText(euser.getUserlevel());
		tvMFG.setText(euser.getMfg());
		tvSBU.setText(euser.getSbu());
		tvTeam.setText(euser.getTeam());
		tvSection.setText(euser.getSection());
		tvBU.setText(euser.getBu());
		tvSite.setText(euser.getSite());
		tvLastEditby.setText(euser.getLasteditby());
		tvLastEditdt.setText(euser.getLasteditdt());
	}
	
	@Override
	public void showEmployeeAddView(Euser user) {
		tvTitle.setText(R.string.addemployee_title);
		tvRight.setText(R.string.addemployee_submit);
		tvRight.setVisibility(View.VISIBLE);
		//隱藏詳細信息頁面控件
		tvLogonName.setVisibility(View.GONE);
		tvChineseName.setVisibility(View.GONE);
		tvEnglishName.setVisibility(View.GONE);
		tvExt.setVisibility(View.GONE);
		tvEmail.setVisibility(View.GONE);
		tvJob.setVisibility(View.GONE);
		tvMaster.setVisibility(View.GONE);
		tvUserlevel.setVisibility(View.GONE);
		tvMFG.setVisibility(View.GONE);
		tvSBU.setVisibility(View.GONE);
		tvTeam.setVisibility(View.GONE);
		tvSection.setVisibility(View.GONE);
		lasteditbyLayout.setVisibility(View.GONE);
		lasteditdtLayout.setVisibility(View.GONE);
		//顯示添加員工頁面的控件
		etLogonName.setVisibility(View.VISIBLE);
		etChineseName.setVisibility(View.VISIBLE);
		etEnglishName.setVisibility(View.VISIBLE);
		etExt.setVisibility(View.VISIBLE);
		etEmail.setVisibility(View.VISIBLE);
		spJob.setVisibility(View.VISIBLE);
		etMaster.setVisibility(View.VISIBLE);
		spUserlevel.setVisibility(View.VISIBLE);
		spMFG.setVisibility(View.VISIBLE);
		spSBU.setVisibility(View.VISIBLE);
		spTeam.setVisibility(View.VISIBLE);
		spSection.setVisibility(View.VISIBLE);
		ibJobDrop.setVisibility(View.VISIBLE);
		ibUserlevelDrop.setVisibility(View.VISIBLE);
		ibMFGDrop.setVisibility(View.VISIBLE);
		ibSBUDrop.setVisibility(View.VISIBLE);
		ibTeamDrop.setVisibility(View.VISIBLE);
		ibSectionDrop.setVisibility(View.VISIBLE);
		
		etMaster.setText(user.getLogonName());
		tvBU.setText(user.getBu());
		tvSite.setText(user.getSite());
		
		spMFG.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				employeeDetailOrAddPresenter.notifySBUDataChanged(
						parent.getItemAtPosition(pos).toString());//通知SBU數據更新
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		spSBU.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				employeeDetailOrAddPresenter.notifyTeamDataChanged(
						parent.getItemAtPosition(pos).toString(),
						spMFG.getSelectedItem().toString());//通知Team數據更新
				
				employeeDetailOrAddPresenter.notifySectionDataChanged(
						parent.getItemAtPosition(pos).toString());//通知工站數據更新
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			svContainer.scrollTo(0, 0);//點擊標題滑動到頂部
			break;
		case R.id.tvRight:
			employeeDetailOrAddPresenter.saveEmployeeInfo(
					etLogonName.getText().toString(),
					etChineseName.getText().toString(),
					etEnglishName.getText().toString(),
					etExt.getText().toString(),
					etEmail.getText().toString(),
					spJob.getSelectedItem().toString(),
					etMaster.getText().toString(),
					spUserlevel.getSelectedItem().toString(),
					spMFG.getSelectedItem().toString(),
					spSBU.getSelectedItem().toString(),
					spTeam.getSelectedItem().toString(),
					spSection.getSelectedItem().toString(),
					tvBU.getText().toString(),
					tvSite.getText().toString());
			break;
		case R.id.ibJobDrop:
			spJob.performClick();
			break;
		case R.id.ibUserlevelDrop:
			spUserlevel.performClick();
			break;
		case R.id.ibMFGDrop:
			spMFG.performClick();
			break;
		case R.id.ibSBUDrop:
			spSBU.performClick();
			break;
		case R.id.ibTeamDrop:
			spTeam.performClick();
			break;
		case R.id.ibSectionDrop:
			spSection.performClick();
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
	
	public void setJobAdapter(String[] jobArray){
		//String[] jobArray=getResources().getStringArray(R.array.job_array);
		ArrayAdapter<String> MFGAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, jobArray);
		spJob.setAdapter(MFGAdapter);
	}

	@Override
	public void setMFGAdapter(List<String> MFGList) {
		ArrayAdapter<String> MFGAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, MFGList);
		spMFG.setAdapter(MFGAdapter);
	}

	@Override
	public void setSBUAdapter(List<String> SBUList,int index) {
		ArrayAdapter<String> SBUAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, SBUList);
		spSBU.setAdapter(SBUAdapter);
		spSBU.setSelection(index, true);
	}

	@Override
	public void setTeamAdapter(List<String> TeamList,int index) {
		ArrayAdapter<String> teamAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, TeamList);
		spTeam.setAdapter(teamAdapter);
		spTeam.setSelection(index, true);
	}

	@Override
	public void setSectionAdapter(List<String> sectionList,int index) {
		ArrayAdapter<String> sectionAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, sectionList);
		spSection.setAdapter(sectionAdapter);
		spSection.setSelection(index, true);
	}

	@Override
	public void setMFGSelection(int index) {
		spMFG.setSelection(index, true);//選中index索引位置的條目，由此可觸發OnItemSelected事件
	}

	@Override
	public void setUserlevelAdapter(String[] userlevelArray) {
		ArrayAdapter<String> userlevelAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, userlevelArray);
		spUserlevel.setAdapter(userlevelAdapter);
	}

	@Override
	public void showAddEmployeeSuccessDialog(int titleId, String message,int btnOK) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, message,false);
		dialog.setOKBtn(btnOK, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(getIntent().getExtras().getString("mode")//添加帳號時彈出提示退出框
				.equals(EmployeeDetailOrAddPresenterImpl.MODE_ADDEMPLOYEE)){
			cancel(context, keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
