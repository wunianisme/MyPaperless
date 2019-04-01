package com.foxconn.paperless.ui.widget;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
/**
 * 自定义带清除按钮的EditText
 *@ClassName ClearEditText
 *@Author wunian
 *@Date 2017/9/4 上午9:42:13
 */
public class ClearEditText extends EditText implements OnFocusChangeListener,TextWatcher{

	private Drawable clearDrawable;
	private boolean hasFocus;
	public ClearEditText(Context context) {
		this(context,null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context,attrs , android.R.attr.editTextStyle);
	}
	
	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs , defStyle);
		init();
	}
	//初始化刪除按鈕
	public void init(){
		clearDrawable=getCompoundDrawables()[2];//left top right bottom 所以right的索引為2
		if(clearDrawable==null){
			clearDrawable=getResources().getDrawable(R.drawable.ic_input_delete);
		}
		//設置圖標的寬高
		clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
		setClearIconVisible(false);//設置圖標是否可見
		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}
	//設置圖標是否可見
	public void setClearIconVisible(boolean visible){
		Drawable right=visible?clearDrawable:null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}
	//點擊事件
	//圖標的坐標位置範圍在（EditText的寬度-圖標的寬度-右邊距,EditText的寬度-右邊距）之間
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
	
	//文本改變的時候調用
	@Override
	public void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		if(hasFocus){
			setClearIconVisible(getText().length()>0);
		}
		
	}

	@Override
	public void afterTextChanged(Editable arg0) {}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {}
	//獲得焦點的時候調用
	@Override
	public void onFocusChange(View arg0, boolean hasFocus) {
		this.hasFocus=hasFocus;
		if(hasFocus){
			setClearIconVisible(getText().length()>0);
		}else{
			setClearIconVisible(false);
		}
	}

}
