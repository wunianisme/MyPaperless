package com.foxconn.paperless.main.function.view;

import java.util.List;
import net.tsz.afinal.annotation.view.ViewInject;
import android.R.anim;
import android.R.integer;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.MTLineInfo;
import com.foxconn.paperless.main.function.presenter.MTInadvanceByLinePresenter;
import com.foxconn.paperless.main.function.presenter.MTInadvanceByLinePresenterImpl;
import com.foxconn.paperless.ui.adapter.MTLineInfoListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 樓層纖體維護頁面
 * @author F1333452
 *
 */
public class MTInadvanceByLineActivity extends BaseActivity implements
		MTInadvanceByLineView {
	//標題欄
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	@ViewInject(id=R.id.lvLine)ListView lvLine;
	
	private Context context;
	private MTInadvanceByLinePresenter presenter;
	private CustomerDialog floorDialog;
	private CustomerDialog shiftDialog;
	private DatePickerDialog dialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mtinadvancebyline);
		init();
	}
	
	@Override
	protected void init() {
		context=MTInadvanceByLineActivity.this;
		presenter=new MTInadvanceByLinePresenterImpl(this, context);
		tvTitle.setText(R.string.mt_inadvance_byline);
		presenter.init();
		lvLine.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				presenter.setLineInfo(position);
				presenter.selectShift();
			}
		});
	}
	
	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
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
	public void showSelectFloorDialog(int titleId, List<String> floorNameList) {
		ArrayAdapter<String> floorAdapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, floorNameList);
		floorDialog=new CustomerDialog(context, titleId, floorAdapter, new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				floorDialog.dismiss();
				presenter.setFloor(position);
				presenter.getLineName();
			}
		}, false);
	}

	@Override
	public void setLineInfoAdapter(List<MTLineInfo> lineInfoList) {
		MTLineInfoListViewAdapter lineInfoAdapter=new MTLineInfoListViewAdapter(context, lineInfoList, presenter);
		lvLine.setAdapter(lineInfoAdapter);
	}

	@Override
	public void showSelectShiftDialog(String title, String[] shiftArray) {
		ArrayAdapter<String> shiftAdapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, shiftArray);
		shiftDialog=new CustomerDialog(context, title, shiftAdapter, new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				shiftDialog.dismiss();
				presenter.setShift(position);
				presenter.selectDate();
			}
		}, true);
	}

	@Override
	public void showSelectDateDialog(int titleId,int[] date) {
		dialog = new DatePickerDialog(context,
				new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month,
					int date) {
				presenter.setDate(year,month,date);
				presenter.submitMTInadvance();
			}
		}, date[0], date[1], date[2]);
		dialog.setTitle(titleId);
		dialog.show();
	}

	@Override
	public void back() {
		finish();
	}
}
