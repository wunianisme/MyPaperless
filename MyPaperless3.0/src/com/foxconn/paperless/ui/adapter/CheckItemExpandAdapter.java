package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.constant.MyEnum.ReportConfig;
import com.foxconn.paperless.main.function.presenter.ReportConfigDetailPresenter;
/**
 * 報表配置點檢項ExpandListView適配器
 *@ClassName CheckItemExpandAdapter
 *@Author wunian
 *@Date 2018/1/6 下午2:51:37
 */
public class CheckItemExpandAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<String> groupItem;//點檢項
	private List<List<CheckItem>> childItem;//點檢子項 
	private LayoutInflater inflater;
	private ReportConfigDetailPresenter presenter;

	public CheckItemExpandAdapter(Context context, List<String> groupItem,
			List<List<CheckItem>> childItem,
			ReportConfigDetailPresenter presenter) {
		this.context = context;
		this.groupItem = groupItem;
		this.childItem = childItem;
		this.presenter = presenter;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return childItem.get(arg0).get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(int pos, int pos2, boolean expand, View childView,
			ViewGroup arg4) {
		final ChildHolder holder;
		if(childView==null){
			holder=new ChildHolder();
			childView=inflater.inflate(R.layout.expandlistview_checkitem_childitem, null);
			holder.tvCheckProName=(TextView) childView.findViewById(R.id.tvCheckProName);
			holder.cbSelect=(CheckBox) childView.findViewById(R.id.cbSelect);
			childView.setTag(holder);
		}else{
			holder=(ChildHolder) childView.getTag();
		}
		holder.tvCheckProName.setTag(pos);
		holder.cbSelect.setTag(pos2);
		CheckItem checkItem=childItem.get(pos).get(pos2);
		holder.tvCheckProName.setText(checkItem.getCheckProName());
		if(checkItem.getChecked().equals(ReportConfig.CHECKED)){
			holder.cbSelect.setChecked(true);
		}else{
			holder.cbSelect.setChecked(false);
		}
		holder.cbSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int groupIndex=(Integer) holder.tvCheckProName.getTag();
				int childIndex=(Integer) holder.cbSelect.getTag();
				//選中或不選
				if(childItem.get(groupIndex).get(childIndex).getChecked()
						.equals(ReportConfig.CHECKED)){//是選中狀態，點擊后設置不選
					childItem.get(groupIndex).get(childIndex).setChecked(ReportConfig.NOCHECKED);
				}else{
					childItem.get(groupIndex).get(childIndex).setChecked(ReportConfig.CHECKED);
				}
			}
		});
		return childView;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return childItem.get(arg0).size();
	}

	@Override
	public Object getGroup(int arg0) {
		return groupItem.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return groupItem.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder;
		if(convertView==null){
			holder=new GroupHolder();
			convertView=inflater.inflate(R.layout.expandlistview_checkitem_groupitem, null);
			holder.tvCheckName=(TextView) convertView.findViewById(R.id.tvCheckName);
			convertView.setTag(holder);
		}else{
			holder=(GroupHolder) convertView.getTag();
		}
		holder.tvCheckName.setText(groupItem.get(groupPosition));
		return convertView;
	}
	
	public List<List<CheckItem>> getChildItem(){
		return childItem;
	} 

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	private class GroupHolder{
		TextView tvCheckName;
	}
	
	private class ChildHolder{
		TextView tvCheckProName;
		CheckBox cbSelect;
	}

}
