package com.foxconn.paperless.ui.adapter;

import java.util.List;

import com.foxconn.paperless.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
/**
 * 顯示位圖GridView適配器
 *@ClassName BitmapGridViewAdapter
 *@Author wunian
 *@Date 2017/12/19 下午5:44:59
 */
public class BitmapGridViewAdapter extends BaseAdapter {
	private Context context;
	private List<Bitmap> bitmapList;
	private LayoutInflater inflater;
	
	public BitmapGridViewAdapter(Context context, List<Bitmap> bitmapList) {
		this.context = context;
		this.bitmapList = bitmapList;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return bitmapList.size();
	}

	@Override
	public Object getItem(int position) {
		return bitmapList.get(position);
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
			convertView=inflater.inflate(R.layout.gridview_bitmap_item, null);
			holder.ivPhoto=(ImageView) convertView.findViewById(R.id.ivPhoto);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.ivPhoto.setImageBitmap(bitmapList.get(position));
		return convertView;
	}
	
	private class ViewHolder{
		ImageView ivPhoto; 
	}

}
