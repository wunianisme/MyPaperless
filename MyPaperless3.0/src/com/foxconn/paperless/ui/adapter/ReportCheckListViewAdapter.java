package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.constant.MyEnum.CheckFlag;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.constant.MyEnum.TakePhoto;
import com.foxconn.paperless.helper.CheckPresenterHelper;
import com.foxconn.paperless.main.check.presenter.ReportCheckPresenter;
import com.foxconn.paperless.main.check.presenter.ReportCheckPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.FileUtil;
/**
 * 報表點檢項目ListView適配器
 *@ClassName ReportCheckListViewAdapter
 *@Author wunian
 *@Date 2017/12/9 上午9:58:05
 */
public class ReportCheckListViewAdapter extends BaseAdapter{
	private List<CheckItem> checkItemList;
	private Context context;
	private LayoutInflater inflater;
	private ReportCheckPresenter reportCheckPresenter;
	private CustomerDialog inputDialog;
	private CustomerDialog selectCheckByDialog;
	private SelectCheckByListViewAdapter checkByAdapter;
	private SelectCheckByListViewAdapter selectedCheckByAdapter;
	
	public ReportCheckListViewAdapter(Context context, List<CheckItem> checkItemList,
			ReportCheckPresenter reportCheckPresenter) {
		this.checkItemList = checkItemList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.reportCheckPresenter = reportCheckPresenter;
	}

	@Override
	public int getCount() {
		return checkItemList.size();
	}

	@Override
	public Object getItem(int pos) {
		return checkItemList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		final int pos=position;
		CheckItem checkItem = checkItemList.get(pos);
		if(null==convertView){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_checkitem_item, null);
			holder.checkNameLayout=(LinearLayout) convertView.findViewById(R.id.checkNameLayout);
			holder.tvCheckName=(TextView) convertView.findViewById(R.id.tvCheckName);
			holder.tvCheckItemCount=(TextView) convertView.findViewById(R.id.tvCheckItemCount);
			holder.tvCheckProName=(TextView) convertView.findViewById(R.id.tvCheckProName);
			
			holder.etCheckContent=(EditText) convertView.findViewById(R.id.etCheckContent);
			holder.ivScanContent=(ImageView) convertView.findViewById(R.id.ivScanContent);
			holder.ivOpenCamera=(ImageView) convertView.findViewById(R.id.ivOpenCamera);
			holder.ivSubmitException=(ImageView) convertView.findViewById(R.id.ivsubmitException);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.ivScanContent.setTag(pos);
		holder.etCheckContent.setTag(pos);
		holder.ivOpenCamera.setTag(pos);
		holder.ivSubmitException.setTag(pos);
		if(checkItem.isFirstProName()){//當點檢子項為第一項時，顯示點檢項欄位
			holder.checkNameLayout.setVisibility(View.VISIBLE);
			holder.tvCheckName.setText(checkItem.getCheckName());
			holder.tvCheckItemCount.setText(checkItem.getCheckItemCount());
		}else{
			holder.checkNameLayout.setVisibility(View.GONE);
		}
		holder.tvCheckProName.setText(checkItem.getCheckProName());//點檢子項
		//以下這段注釋掉后輸入框錯位問題解決
		/*if(checkItem.getCheckContent()!=null){//如果內容不為空則顯示
			holder.etCheckContent.setText(checkItem.getCheckContent());
		}*/
		holder.ivScanContent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int tag=(Integer) holder.ivScanContent.getTag();
				//非單選可掃碼輸入點檢內容
				if(!checkItemList.get(tag).getCheckFlag().equals(CheckFlag.INPUT_OK)){
					reportCheckPresenter.scanCheckContent(tag);
				}else{
					//只能輸入OK
					ToastHelper.showError(context, R.string.input_onlyok, 0);
				}
			}
		});
		holder.etCheckContent.setText(checkItem.getCheckContent());
		holder.etCheckContent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				//使用綁定的tag來定位，避免滑動時數據錯位
				int tag=(Integer) holder.etCheckContent.getTag();
				checkItemList.get(tag).setCheckContent(s.toString());
				//ToastHelper.showInfo(context,"tag:"+tag,0);
			}
		});
		
		//輸入框監聽事件
		holder.etCheckContent.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				
			
				int tag=(Integer) holder.etCheckContent.getTag();
				CheckItem checkItem=checkItemList.get(tag);
				if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_OK)){//單選，觸發點擊事件默認OK
					holder.etCheckContent.setText(CheckFlag.INPUT_OK_VALUE);
					checkItemList.get(tag).setCheckContent(CheckFlag.INPUT_OK_VALUE);
				}
				//OBA開線點檢表參數表只能掃描二維碼輸入內容，不能手動輸入
				else if(checkItem.getReportNum().equals(ReportNum.OBA_OPEN)){
					ToastHelper.showError(context,R.string.input_notpermission,0);
				}
				else{
					String title=checkItem.getCheckName()+"/"+checkItem.getCheckProName();
					String hint=holder.etCheckContent.getHint().toString();
					String content=holder.etCheckContent.getText().toString();
					inputDialog=new CustomerDialog(context,title , hint, content, false);
					inputDialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							inputDialog.dismiss();
							holder.etCheckContent.setText(inputDialog.getContent());
							checkItemList.get((Integer) holder.etCheckContent.getTag())
							.setCheckContent(inputDialog.getContent());
							reportCheckPresenter.clearFocus();
						}
					});
					inputDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							inputDialog.dismiss();
							reportCheckPresenter.clearFocus();
						}
					});
				}
				/*else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_KEYWORD)){//請輸入關鍵字
					
				}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_TR_SN)){//請輸入TR_SN
				
				}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_SN)){//請輸入SN
				
				}*/
			}
		});
		//顯示文本提示框內容
		if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_MANUAL)){//手寫
			holder.etCheckContent.setHint(R.string.checkcontent_hint);
		}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_OK)){//單選
			holder.etCheckContent.setHint(R.string.inputok_hint);
		}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_WO)){//輸入工單帶出參數
			holder.etCheckContent.setHint(R.string.checkcontent_hint);
		}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_KEYWORD)){//請輸入關鍵字
			holder.etCheckContent.setHint(R.string.inputkeyword_hint);
		}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_TR_SN)){//請輸入TR_SN
			holder.etCheckContent.setHint(R.string.inputtrsn_hint);
		}else if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_SN)){//請輸入SN
			holder.etCheckContent.setHint(R.string.inputsn_hint);
		}
		
		if(checkItem.getTakePhoto().equals(TakePhoto.NEED)){//配置拍照項目顯示相機圖標
			holder.ivOpenCamera.setVisibility(View.VISIBLE);
			holder.ivOpenCamera.setImageBitmap(checkItem.getTakePhotoBitmap());
			holder.ivOpenCamera.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int tag=(Integer) holder.ivOpenCamera.getTag();
					reportCheckPresenter.openCheckCamera(tag);
				}
			});
		}else{
			holder.ivOpenCamera.setVisibility(View.INVISIBLE);
		}
		
		holder.ivSubmitException.setImageResource(R.drawable.ic_submitexception);
		holder.ivSubmitException.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final int tag=(Integer) holder.ivSubmitException.getTag();
				CheckItem checkItem=checkItemList.get(tag);
				String title=CheckPresenterHelper.getTitle(
						context, checkItem, R.string.submitexception_title);
				//設置為提交異常的Action
				reportCheckPresenter.setSubmitAction(
						ReportCheckPresenterImpl.SUBMITEXCEPTION);
				//彈出選擇簽核人的提示框
				selectCheckByDialog=new CustomerDialog(context, title, new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						//根據Team獲取具有簽核權限的人員名單
						reportCheckPresenter.getCheckBy(arg0.getItemAtPosition(pos).toString());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				}, false);
				//進入下一步，添加照片和內容
				selectCheckByDialog.setOKBtn(R.string.btn_nextstep,
						new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						reportCheckPresenter.submitNextStep(tag);//進入下一步
					}
				});
				selectCheckByDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						selectCheckByDialog.dismiss();
					}
				});
				selectCheckByDialog.getEtMaster().addTextChangedListener(new TextWatcher() {
					
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
		});
		return convertView;
	}
	
	private class ViewHolder{
		LinearLayout checkNameLayout;
		TextView tvCheckName;
		TextView tvCheckItemCount;
		TextView tvCheckProName;
		ImageView ivScanContent;
		EditText etCheckContent;
		ImageView ivOpenCamera;
		ImageView ivSubmitException;
	}
	/**
	 * 獲取點檢數據
	 * @return
	 */
	public List<CheckItem> getChecKItemList(){
		return checkItemList;
	}
	/**
	 * 設置具有簽核權限人員信息的適配器
	 * @param checkByAdapter
	 */
	public void setCheckByAdapter(List<Euser> checkByList) {
		checkByAdapter=new SelectCheckByListViewAdapter(
				context, checkByList, reportCheckPresenter,0);
		selectCheckByDialog.getLvMaster().setAdapter(checkByAdapter);
	}
	/**
	 * 設置已簽核人員信息的適配器
	 * @param selectedCheckByList
	 */
	public void setSelectedCheckByAdapter(List<Euser> selectedCheckByList) {
		selectedCheckByAdapter=new SelectCheckByListViewAdapter(
				context, selectedCheckByList, reportCheckPresenter,1);
		selectCheckByDialog.getLvCheckBy().setAdapter(selectedCheckByAdapter);
		selectedCheckByAdapter.notifyDataSetChanged();
	}
	/**
	 * 關閉選擇簽核人彈出框
	 */
	public void dismissSelectCheckByDialog() {
		selectCheckByDialog.dismiss();
	}
	/**
	 * 設置掃碼輸入的內容
	 * @param tag
	 * @param checkContent
	 */
	public void setCheckContent(int tag, String checkContent) {
		checkItemList.get(tag).setCheckContent(checkContent);
		notifyDataSetChanged();
	}
	/**
	 * 設置拍照圖片顯示照片
	 * @param tag
	 * @param path
	 */
	public void setTakePhotoBitmap(int tag, String path) {
		Bitmap bitmap =FileUtil.compressBitmap(path,400f,400f);
		//轉出base64編碼字符串
		String imageData=FileUtil.bitmapToByteString(bitmap);
		checkItemList.get(tag).setTakePhotoBitmap(bitmap);
		checkItemList.get(tag).setImageData(imageData);
		notifyDataSetChanged();
	}
}
