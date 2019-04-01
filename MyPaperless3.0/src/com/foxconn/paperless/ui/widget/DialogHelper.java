package com.foxconn.paperless.ui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
/**
 * Dialog助手
 *@ClassName DialogHelper
 *@Author wunian
 *@Date 2017/9/19 下午1:39:12
 */
public class DialogHelper{
	
	/**
	 * 顯示有兩個按鈕的AlertDialog
	 * @param context
	 * @param icon
	 * @param titleId
	 * @param message
	 * @param positiveId
	 * @param negativeId
	 * @param positiveListener
	 * @param negativeListener
	 */
	public static void showDoubleButtonAlert(Context context,int icon,int titleId,String message,
			int positiveId,int negativeId,boolean cancelOutSide,DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener){
		AlertDialog dialog=createAlertDialog(context, icon, titleId, positiveId, negativeId,
				positiveListener, negativeListener, message);
		dialog.setCanceledOnTouchOutside(cancelOutSide);
		dialog.show();
	}
	/**
	 * 創建AlertDialog
	 * @param context
	 * @param icon
	 * @param titleId
	 * @param positiveId
	 * @param negativeId
	 * @param positiveListener
	 * @param negativeListener
	 * @param message
	 * @return
	 */
	private static AlertDialog createAlertDialog(Context context, int icon,
			int titleId, int positiveId, int negativeId,
			DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener, String message) {
		AlertDialog.Builder dialog=new AlertDialog.Builder(context);
		dialog.setIcon(icon);
		dialog.setTitle(titleId);
		dialog.setMessage(message);
		dialog.setPositiveButton(positiveId, positiveListener);
		dialog.setNegativeButton(negativeId, negativeListener);
		return dialog.create();
	}
	/**
	 * 顯示修改頭像菜單
	 * @param context
	 * @param layout
	 * @param listener
	 * @return
	 */
	public static Dialog showChoosePictureDialog(Context context,int layout,OnClickListener listener){
		View view=LayoutInflater.from(context).inflate(layout, null);
		Dialog dialog=new Dialog(context,R.style.Theme_Light_Dialog);
		dialog.setContentView(view);
		Button btnGallery=(Button) dialog.findViewById(R.id.btnGallery);
		Button btnCamera=(Button) dialog.findViewById(R.id.btnCamera);
		Button btnCancel=(Button) dialog.findViewById(R.id.btnCancel);
		btnGallery.setOnClickListener(listener);
		btnCamera.setOnClickListener(listener);
		btnCancel.setOnClickListener(listener);
		Window window=dialog.getWindow();
		//設置dialog彈出時的動畫效果，從屏幕底部向上彈出
		window.setWindowAnimations(R.style.choosepic_dialog_animstyle);
		//設置dialog在屏幕底部
		window.setGravity(Gravity.BOTTOM);
		window.getDecorView().setPadding(0, 0, 0, 0);
		dialog.onWindowAttributesChanged( getDialogParams(window));//重寫設置屬性
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		return dialog;
	}
	/**
	 * 設置Window的寬高屬性
	 * @param window
	 * @return
	 */
	private static WindowManager.LayoutParams getDialogParams(Window window){
		WindowManager.LayoutParams params=window.getAttributes();
		// 以下这两句是为了保证按钮可以水平满屏
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		return params;
	}
	
	/**
	 * 自定義對話框
	 *@ClassName CustomerDialog
	 *@Author wunian
	 *@Date 2017/10/26 上午9:22:24
	 */
	public static class CustomerDialog extends Dialog{
		private Context context;
		private View view;
		
		private ProgressBar pbDownload;
		private TextView tvProgress;
		private TextView tvSavePath;
		
		private EditText etContent;//點檢內容輸入框
		//選擇簽核人
		private Spinner spTeam;//Team下拉框
		private EditText etMaster;//工號姓名輸入框
		private ListView lvMaster;//主管列表
		private ListView lvCheckBy;//選擇的簽核人列表
		
		//異常提交
		private EditText etException;
		private ImageView ivGallery;
		private ImageView ivCamera;
		private GridView gvPhoto;
		private ListView lvSelectedCheckBy;
		//ListView單選
		private ListView lvItem;
		
		public static final int ACTION_SUBMITEXCEPTION=0x01;//提交異常
		
		//文本內容為字符串（一般）
		public CustomerDialog(Context context,int titleId,String message,boolean cancelOutSide) {
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initComponent(titleId,message,0,cancelOutSide);
		}
		//文本內容為ID（一般）
		public CustomerDialog(Context context,int titleId,int msgId,boolean cancelOutSide) {
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initComponent(titleId,"",msgId,cancelOutSide);
		}
		//文件下載專用
		public CustomerDialog(Context context,int titleId,boolean cancelOutSide){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initDownloadComponent(titleId, cancelOutSide);
		}
		//含ListView
		public CustomerDialog(Context context,int titleId,ArrayAdapter<String> adapter,OnItemClickListener listener,boolean cancelOutSide){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initListViewComponent(titleId, adapter, listener, cancelOutSide);
		}
		//含ListView（字符串）
		public CustomerDialog(Context context,String title,ArrayAdapter<String> adapter,OnItemClickListener listener,boolean cancelOutSide){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initListViewComponent(title, adapter, listener, cancelOutSide);
		}
		//含ListView(基本適配器)
		public CustomerDialog(Context context,int titleId,BaseAdapter adapter,OnItemClickListener listener,boolean cancelOutSide){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initListViewComponent(titleId, adapter, listener, cancelOutSide);
		}
		//含輸入框
		public CustomerDialog(Context context,String title,String hint,String content,boolean cancelOutSide){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initInputComponent(title, hint, content, cancelOutSide);
		}
		//選擇審核人彈出框
		public CustomerDialog(Context context,String title,
				OnItemSelectedListener listener,boolean cancelOutSide){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			initSelectCheckByComponent(title, listener,  cancelOutSide);
		}
		
		//其他
		public CustomerDialog(Context context,String title,boolean cancelOutSide,int action){
			super(context,R.style.Theme_Light_Dialog);
			this.context=context;
			if(action==ACTION_SUBMITEXCEPTION){
				initSubmitExceptionComponent(title, cancelOutSide);
			}
		}
		
		/**
		 * 初始化容器（一般）
		 */
		private void initComponent(int titleId,String message,int msgId,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_customerdialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(titleId);
			if(msgId==0) ((TextView) view.findViewById(R.id.tvMessage)).setText(message);
			else ((TextView) view.findViewById(R.id.tvMessage)).setText(msgId);
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化下載框
		 * @param layout
		 * @param titleId
		 * @param cancelOutSide
		 */
		private void initDownloadComponent(int titleId,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_downloaddialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(titleId);
			pbDownload=(ProgressBar) view.findViewById(R.id.pbDownload);
			tvProgress=(TextView) view.findViewById(R.id.tvProgress);
			tvSavePath=(TextView) view.findViewById(R.id.tvSavePath);
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化ListView彈出框
		 * @param titleId
		 * @param adapter
		 * @param listener
		 * @param cancelOutSide
		 */
		private void initListViewComponent(int titleId,ArrayAdapter<String> adapter,OnItemClickListener listener,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_listviewdialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(titleId);
			lvItem=(ListView) view.findViewById(R.id.lvItem);
			lvItem.setAdapter(adapter);
			lvItem.setOnItemClickListener(listener);
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化ListView彈出框(字符串)
		 * @param titleId
		 * @param adapter
		 * @param listener
		 * @param cancelOutSide
		 */
		private void initListViewComponent(String title,ArrayAdapter<String> adapter,OnItemClickListener listener,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_listviewdialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(title);
			lvItem=(ListView) view.findViewById(R.id.lvItem);
			lvItem.setAdapter(adapter);
			lvItem.setOnItemClickListener(listener);
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化ListView彈出框(基本適配器)
		 * @param titleId
		 * @param adapter
		 * @param listener
		 * @param cancelOutSide
		 */
		private void initListViewComponent(int titleId,BaseAdapter adapter,OnItemClickListener listener,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_listviewdialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(titleId);
			lvItem=(ListView) view.findViewById(R.id.lvItem);
			lvItem.setAdapter(adapter);
			lvItem.setOnItemClickListener(listener);
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化含輸入框的彈出框
		 * @param title
		 * @param hint
		 * @param content
		 * @param cancelOutSide
		 */
		private void initInputComponent(String title,String hint,String content,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_inputdialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(title);
			etContent=(EditText) view.findViewById(R.id.etContent);
			if(hint!=null) etContent.setHint(hint);
			if(content!=null) etContent.setText(content);
			etContent.setSelection(etContent.getText().length());
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化選擇簽核人彈出框
		 * @param title
		 * @param listener
		 * @param cancelOutSide
		 */
		private void initSelectCheckByComponent(String title,OnItemSelectedListener listener,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_selectcheckbydialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(title);
			etMaster=(EditText) view.findViewById(R.id.etMaster);
			spTeam=(Spinner) view.findViewById(R.id.spTeam);
			lvMaster=(ListView) view.findViewById(R.id.lvMaster);//具有簽核權限的人員
			lvCheckBy=(ListView) view.findViewById(R.id.lvCheckBy);//已選擇的簽核人
			//spTeam.setAdapter(teamAdapter);//數據源已在array文件中
			spTeam.setOnItemSelectedListener(listener);
			setWindow(cancelOutSide);
		}
		/**
		 * 初始化提交異常彈出框
		 * @param title
		 * @param cancelOutSide
		 */
		private void initSubmitExceptionComponent(String title,boolean cancelOutSide){
			view=LayoutInflater.from(context).inflate(R.layout.layout_submitexceptiondialog, null);
			((TextView) view.findViewById(R.id.tvTitle)).setText(title);
			etException=(EditText) view.findViewById(R.id.etException);
			ivGallery=(ImageView) view.findViewById(R.id.ivGallery);
			ivCamera=(ImageView) view.findViewById(R.id.ivCamera);
			gvPhoto=(GridView) view.findViewById(R.id.gvPhoto);
			lvSelectedCheckBy=(ListView) view.findViewById(R.id.lvSelectedCheckBy);
			setWindow(cancelOutSide);
		}
		
		/**
		 * 獲得輸入框輸入的內容（彈出輸入框）
		 * @return
		 */
		public String getContent(){
			return etContent.getText().toString();
		}
		/**
		 * 設置下載進度（APK下載）
		 * @param progress
		 */
		public void setProgress(int progress){
			pbDownload.setProgress(progress);
			tvProgress.setText(progress+"%");
		}
		/**
		 * 設置保存路徑（APK下載）
		 * @param path
		 */
		public void setSavePath(String path){
			tvSavePath.setText(path);
		}
		/**
		 * 設置window屬性
		 * @param cancelOutSide
		 */
		private void setWindow(boolean cancelOutSide){
			Window window=this.getWindow();
			window.setWindowAnimations(R.style.choosepic_dialog_animstyle);
			this.setCanceledOnTouchOutside(cancelOutSide);
			window.setBackgroundDrawableResource(R.color.transparent);
			this.setContentView(view);
			this.onWindowAttributesChanged(getDialogParams(window));//重寫設置屬性
			this.show();
		}
		/**
		 * 設置OK按鈕
		 * @param text
		 * @param listener
		 */
		public void setOKBtn(int strId,android.view.View.OnClickListener listener){
			Button button=(Button) view.findViewById(R.id.btnOK);
			button.setText(strId);
			button.setVisibility(View.VISIBLE);
			button.setOnClickListener(listener);
		}
		/**
		 * 設置NO按鈕
		 * @param text
		 * @param listener
		 */
		public void setNoBtn(int strId,android.view.View.OnClickListener listener){
			Button button=(Button) view.findViewById(R.id.btnNO);
			button.setText(strId);
			button.setVisibility(View.VISIBLE);
			button.setOnClickListener(listener);
		}
		
		public Spinner getSpTeam() {
			return spTeam;
		}
		public void setSpTeam(Spinner spTeam) {
			this.spTeam = spTeam;
		}
		
		public ListView getLvMaster() {
			return lvMaster;
		}
		public void setLvMaster(ListView lvMaster) {
			this.lvMaster = lvMaster;
		}
		
		public ListView getLvCheckBy() {
			return lvCheckBy;
		}
		public void setLvCheckBy(ListView lvCheckBy) {
			this.lvCheckBy = lvCheckBy;
		}
		public EditText getEtMaster() {
			return etMaster;
		}
		public void setEtMaster(EditText etMaster) {
			this.etMaster = etMaster;
		}
		public EditText getEtException() {
			return etException;
		}
		public void setEtException(EditText etException) {
			this.etException = etException;
		}
		
		public ImageView getIvGallery() {
			return ivGallery;
		}
		public void setIvGallery(ImageView ivGallery) {
			this.ivGallery = ivGallery;
		}
		public ImageView getIvCamera() {
			return ivCamera;
		}
		public void setIvCamera(ImageView ivCamera) {
			this.ivCamera = ivCamera;
		}
		public GridView getGvPhoto() {
			return gvPhoto;
		}
		public void setGvPhoto(GridView gvPhoto) {
			this.gvPhoto = gvPhoto;
		}
		public ListView getLvSelectedCheckBy() {
			return lvSelectedCheckBy;
		}
		public void setLvSelectedCheckBy(ListView lvSelectedCheckBy) {
			this.lvSelectedCheckBy = lvSelectedCheckBy;
		}
		public ListView getLvItem() {
			return lvItem;
		}
		public void setLvItem(ListView lvItem) {
			this.lvItem = lvItem;
		}
		
		
	}
}
