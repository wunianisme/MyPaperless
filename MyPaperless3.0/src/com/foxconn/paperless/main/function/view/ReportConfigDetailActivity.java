package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.main.function.presenter.ReportConfigDetailPresenter;
import com.foxconn.paperless.main.function.presenter.ReportConfigDetailPresenterImpl;
import com.foxconn.paperless.ui.adapter.CheckItemExpandAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 報表配置詳情頁
 *@ClassName ReportConfigDetailActivity
 *@Author wunian
 *@Date 2018/1/6 上午9:22:26
 */
public class ReportConfigDetailActivity extends BaseActivity implements
		ReportConfigDetailView{
	
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.elvCheckItem)ExpandableListView elvCheckItem;
	@ViewInject(id=R.id.cbSelectAll,click="btnClick")CheckBox cbSelectAll;
	@ViewInject(id=R.id.btnSave,click="btnClick")Button btnSave;
	private Context context;
	private ReportConfigDetailPresenter presenter;
	private CheckItemExpandAdapter checkItemAdapter;
	private CustomerDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportconfigdetail);
		init();
	}
	
	@Override
	protected void init() {
		context=ReportConfigDetailActivity.this;
		presenter=new ReportConfigDetailPresenterImpl(this, context);
		tvTitle.setText(R.string.report_config);
		String reportNum=getIntent().getExtras().getString("reportNum");
		presenter.getCheckItem(reportNum);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.cbSelectAll://全選
			presenter.selectAll(cbSelectAll.isChecked());
			break;
		case R.id.btnSave://保存
			presenter.submitSave(checkItemAdapter.getChildItem());
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
	public void setCheckItemAdapter(List<String> groupItem,
			List<List<CheckItem>> childItem) {
		if(checkItemAdapter==null){
			checkItemAdapter=new CheckItemExpandAdapter(
					context, groupItem, childItem, presenter);
			elvCheckItem.setAdapter(checkItemAdapter);
		}else{
			checkItemAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void showSelectSBUDialog(int titleId, int btnOK,
			int btnNo, List<String> SBUList) {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				context, R.layout.layout_spinner_item, SBUList);
		
		dialog=new CustomerDialog(context, titleId, adapter, new OnItemClickListener() {
			int preIndex=0;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if(preIndex!=pos){//每次點擊條目上一個被點擊的條目恢復白色背景
					arg0.getChildAt(preIndex).setBackgroundColor(Color.WHITE);
				}
				//被點擊的條目顯示為綠色背景
				arg1.setBackgroundColor(Color.GREEN);
				preIndex=pos;
				String SBU=arg0.getItemAtPosition(pos).toString();
				presenter.setSBU(SBU);
			}
		}, true);
		dialog.setOKBtn(btnOK, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.saveReportConfig();//保存
			}
		});
		dialog.setNoBtn(btnNo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.setSBU("");
				dialog.dismiss();
			}
		});
	}

	@Override
	public void back() {
		if(dialog!=null&&dialog.isShowing()) dialog.dismiss();
		finish();
	}
}
