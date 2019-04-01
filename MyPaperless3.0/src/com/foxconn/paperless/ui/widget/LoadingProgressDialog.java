package com.foxconn.paperless.ui.widget;


import com.foxconn.paperless.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * 加载进度条
 *@ClassName LoadingProgressDialog
 *@Author wunian
 *@Date 2017/9/9 下午5:02:54
 */
public class LoadingProgressDialog extends Dialog {

	public LoadingProgressDialog(Context context) {
		super(context,R.style.CustomProgressDialog);
		loading(context);
	}

	public LoadingProgressDialog(Context context,int theme) {
		super(context,theme);
		//loading(context);
	}
	/**
	 * 加载框
	 * @param context
	 */
	public void loading(Context context) {  
        setContentView(R.layout.layout_loadingdialog);  
        // 按返回键是否取消  
        setCancelable(false);  
        setCanceledOnTouchOutside(false);
        // 设置居中  
        getWindow().getAttributes().gravity = Gravity.CENTER;  
        WindowManager.LayoutParams lp =getWindow().getAttributes();  
        // 设置背景层透明度  
        lp.dimAmount = 0.8f;  
        getWindow().setAttributes(lp);  
    }
    
    public static void  loading(Context context,String msg) {  
    	LoadingProgressDialog dialog = new LoadingProgressDialog(context,R.style.CustomProgressDialog);  
        dialog.setTitle("");  
        dialog.setContentView(R.layout.layout_loadingdialog);  
        TextView tvLoading=(TextView) dialog.findViewById(R.id.tvLoading);
        tvLoading.setText(msg);
        // 按返回键是否取消  
        dialog.setCancelable(false);  
        dialog.setCanceledOnTouchOutside(false);
        // 监听返回键处理  
       // dialog.setOnCancelListener(cancelListener);  
        // 设置居中  
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;  
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
        // 设置背景层透明度  
        lp.dimAmount = 0.2f;  
        dialog.getWindow().setAttributes(lp);  
        dialog.show();  
        //return dialog;  
    }
    
    @Override
    public void show() {
    	super.show();
    }
    
    @Override
    public void dismiss() {
    	super.dismiss();
    }
	
}
