package com.foxconn.paperless.ui.adapter;

import java.util.List;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.main.presenter.HomePresenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 功能管理的GridView適配器
 *@ClassName CheckBUGridViewAdapter
 *@Author wunian
 *@Date 2017/11/1 上午11:22:55
 */
public class FunctionManageGridViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private int[] iconArray;
	private int[] itemArray;
	private int[] msgNumArray;
	private HomePresenter presenter;
	
	public FunctionManageGridViewAdapter(Context context,int[] iconArray,int[] itemArray,
			int[] msgNumArray,HomePresenter presenter){
		this.itemArray=itemArray;
		this.iconArray=iconArray;
		this.msgNumArray=msgNumArray;
		this.inflater=LayoutInflater.from(context);
		this.presenter=presenter;
	}
	
	@Override
	public int getCount() {
		return iconArray.length;
	}

	@Override
	public Object getItem(int position) {
		return itemArray[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.gridview_functionmanage_item, null);
			holder.ivIcon=(ImageView) convertView.findViewById(R.id.ivIcon);
			holder.tvItem=(TextView) convertView.findViewById(R.id.tvItem);
			holder.tvMsgNum=(TextView) convertView.findViewById(R.id.tvMsgNum);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvItem.setText(itemArray[position]);
		holder.ivIcon.setImageResource(iconArray[position]);
		if(msgNumArray[position]>0){//顯示消息總數
			holder.tvMsgNum.setVisibility(View.VISIBLE);
			String msgNum=msgNumArray[position]+"";
			if(msgNumArray[position]>99){//add by wunian 
				msgNum="99+";
			}
			holder.tvMsgNum.setText(msgNum);
		}else{
			holder.tvMsgNum.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private class ViewHolder{
		ImageView ivIcon;
		TextView tvItem;
		TextView tvMsgNum;
	}

}
