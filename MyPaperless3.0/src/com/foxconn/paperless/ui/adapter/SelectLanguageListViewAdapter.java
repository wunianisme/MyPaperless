package com.foxconn.paperless.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.util.DensityUtil;
/**
 * 選擇語言的適配器
 *@ClassName SelectLanguageListViewAdapter
 *@Author wunian
 *@Date 2018/1/20 下午4:19:48
 */
public class SelectLanguageListViewAdapter extends BaseAdapter {
	private Context context;
	private String[] languageArray;
	private LayoutInflater inflater;
	private int languageId;
	
	public SelectLanguageListViewAdapter(Context context, String[] languageArray,
			int languageId) {
		this.context = context;
		this.languageArray = languageArray;
		this.languageId = languageId;
		this.inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return languageArray.length;
	}
	@Override
	public Object getItem(int position) {
		return languageArray[position];
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=inflater.inflate(R.layout.layout_spinner_item, null);
		TextView tvItem=(TextView) convertView.findViewById(R.id.tvItem);
		tvItem.setHeight(DensityUtil.dpTopx(context, 40f));
		tvItem.setText(languageArray[position]);
		if(position==languageId){//默認選中保存的語言
			tvItem.setBackgroundColor(Color.GREEN);
		}else{
			tvItem.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

}
