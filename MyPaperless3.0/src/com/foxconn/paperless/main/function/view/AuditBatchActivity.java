package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.main.function.presenter.AuditBatchPresenter;
import com.foxconn.paperless.main.function.presenter.AuditBatchPresenterImpl;
import com.foxconn.paperless.ui.adapter.audit.AuditBatchListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 批量簽核
 *@ClassName AuditBatchActivity
 *@Author wunian
 *@Date 2018/2/3 下午4:52:00
 */
public class AuditBatchActivity extends BaseActivity implements AuditBatchView {

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.lvQueryItem)ListView lvQueryItem;
	@ViewInject(id=R.id.cbSelectAll,click="btnClick")CheckBox cbSelectAll;
	@ViewInject(id=R.id.btnPass,click="btnClick")Button btnPass;
	
	private Context context;
	private AuditBatchPresenter presenter;
	private AuditBatchListViewAdapter batchAdapter;
	private CustomerDialog passDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditbatch);
		init();
	}
	
	@Override
	protected void init() {
		context=AuditBatchActivity.this;
		presenter=new AuditBatchPresenterImpl(this, context);
		tvTitle.setText(R.string.auditbatch_title);
		//Bundle bundle=getIntent().getExtras();
		//List<CheckInfo> checkInfoList=(List<CheckInfo>) bundle.getSerializable("checkInfoList");
		//獲取待簽核列表信息
		List<CheckInfo> checkInfoList=MyConstant.CHECKINFOLIST;
		MyConstant.CHECKINFOLIST=null;
		presenter.init(checkInfoList);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			scrollQueryItem();
			break;
		case R.id.cbSelectAll:
			presenter.selectAll(cbSelectAll.isChecked());
			break;
		case R.id.btnPass:
			presenter.showPassDialog();
			break;

		default:
			break;
		}
	}
	
	
	private void scrollQueryItem() {
		lvQueryItem.post(new Runnable() {
			
			@Override
			public void run() {
				if(batchAdapter!=null){
					batchAdapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					if(lvQueryItem.getFirstVisiblePosition()!=0){//滾動到頂部
						lvQueryItem.setSelection(0);
					}else{
						lvQueryItem.setSelection(batchAdapter.getCount()-1);
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
	public void setAuditBatchAdapter(List<CheckInfo> checkInfoList) {
		if(batchAdapter==null){
			batchAdapter=new AuditBatchListViewAdapter(context, checkInfoList, presenter);
			lvQueryItem.setAdapter(batchAdapter);
		}else{
			batchAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void showPassDialog(int titleId, String message, int btnOk, int btnNo) {
		passDialog=new CustomerDialog(context, titleId, message, false);
		passDialog.setOKBtn(btnOk, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				passDialog.dismiss();
				presenter.auditPass();
			}
		});
		passDialog.setNoBtn(btnNo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				passDialog.dismiss();
				
			}
		});
		
	}
	
	@Override
	public void back() {
		setResult(RESULT_OK);
		AuditBatchActivity.this.finish();
	}

}
