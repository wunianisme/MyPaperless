package com.foxconn.paperless.ui.widget;


import com.foxconn.paperless.activity.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
/**
 * 郵箱地址自動提示文本框
 *@ClassName EmailAutoCompleteTextView
 *@Author wunian
 *@Date 2017/10/26 下午2:35:58
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView implements OnFocusChangeListener,TextWatcher{
	private static final String TAG = "EmailAutoCompleteTextView";

	private String[] emailSufixs = new String[] { "@mail.foxconn.com",
			"@foxconn.com" };
	private Drawable clearDrawable;
	private boolean hasFocus;
	
	public EmailAutoCompleteTextView(Context context) {
		super(context);
		init(context);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
		this(context,attrs , android.R.attr.autoCompleteTextViewStyle);
		init(context);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void setAdapterString(String[] es) {
		if (es != null && es.length > 0)
			this.emailSufixs = es;
	}

	private void init(final Context context) {
		// adapter中使用默认的emailSufixs中的数据，可以通过setAdapterString来更改
		this.setAdapter(new EmailAutoCompleteAdapter(context,
				R.layout.layout_spinner_item, emailSufixs));
		clearDrawable=getCompoundDrawables()[2];//left top right bottom 所以right的索引為2
		if(clearDrawable==null){
			clearDrawable=getResources().getDrawable(R.drawable.ic_delete);
		}
		//設置圖標的寬高
		clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
		// 使得在输入1个字符之后便开启自动完成
		this.setThreshold(1);
		this.setOnFocusChangeListener(this);
		this.setClearIconVisible(false);
	}
	
	//設置圖標是否可見
	public void setClearIconVisible(boolean visible){
		Drawable right=visible?clearDrawable:null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	protected void replaceText(CharSequence text) {
		// 当我们在下拉框中选择一项时，android会默认使用AutoCompleteTextView中Adapter里的文本来填充文本域
		// 因为这里Adapter中只是存了常用email的后缀
		// 因此要重新replace逻辑，将用户输入的部分与后缀合并
		//Log.i(TAG + " replaceText", text.toString());
		String t = this.getText().toString();
		int index = t.indexOf("@");
		if (index != -1)
			t = t.substring(0, index);
		super.replaceText(t + text);
	}

	protected void performFiltering(CharSequence text, int keyCode) {
		// 该方法会在用户输入文本之后调用，将已输入的文本与adapter中的数据对比，若它匹配
		// adapter中数据的前半部分，那么adapter中的这条数据将会在下拉框中出现
		//Log.i(TAG + " performFiltering", text.toString() + "   " + keyCode);
		String t = text.toString();

		// 因为用户输入邮箱时，都是以字母，数字开始，而我们的adapter中只会提供以类似于"@163.com"
		// 的邮箱后缀，因此在调用super.performFiltering时，传入的一定是以"@"开头的字符串
		int index = t.indexOf("@");
		if (index == -1) {
			if (t.matches("^[a-zA-Z0-9_]+$")) {
				super.performFiltering("@", keyCode);
			} else
				this.dismissDropDown();// 当用户中途输入非法字符时，关闭下拉提示框
		} else {
			super.performFiltering(t.substring(index), keyCode);
		}
	}

	private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {

		public EmailAutoCompleteAdapter(Context context,
				int textViewResourceId, String[] email_s) {
			super(context, textViewResourceId, email_s);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i(TAG, "in GetView");
			View v = convertView;
			if (v == null)
				v = LayoutInflater.from(getContext()).inflate(
						R.layout.layout_spinner_item, null);
			TextView tv = (TextView) v.findViewById(R.id.tvItem);

			String t = EmailAutoCompleteTextView.this.getText().toString();
			int index = t.indexOf("@");
			if (index != -1)
				t = t.substring(0, index);
			// 将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示
			tv.setText(t + getItem(position));
			Log.i(TAG, tv.getText().toString());
			return v;
		}
	}

	public String getItem(int position) {
		return null;
	}
	
	@Override
	public void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		if(hasFocus){
			setClearIconVisible(getText().length()>0);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFocus=hasFocus;
		if (hasFocus) {
			String text = EmailAutoCompleteTextView.this.getText()
					.toString();
			// 当该文本域重新获得焦点后，重启自动完成
			if (!"".equals(text))
				performFiltering(text, 0);
			
			setClearIconVisible(getText().length()>0);
		}else{
			setClearIconVisible(false);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_UP){
			if(getCompoundDrawables()[2]!=null){
				if(event.getX()>(getWidth()-getTotalPaddingRight())&&event.getX()<(getWidth()-getPaddingRight())){
					this.setText("");
				}
			}
		}
		return super.onTouchEvent(event);
	}
}