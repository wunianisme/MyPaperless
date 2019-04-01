package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.constant.MyEnum.ParamManage;
import com.foxconn.paperless.main.function.presenter.ParameterManagePresenter;
/**
 * 參數管理簽核信息ListView適配器
 *@ClassName ParamInfoListViewAdapter
 *@Author wunian
 *@Date 2018/1/10 下午4:40:10
 */
public class ParamAuditInfoListViewAdapter extends BaseAdapter {
	private Context context;
	private List<ParamInfo> paramInfoList;
	private LayoutInflater inflater;
	private int type;
	
	public ParamAuditInfoListViewAdapter(Context context,List<ParamInfo> paramInfoList,
			int type){
		this.context=context;
		this.paramInfoList=paramInfoList;
		this.inflater=LayoutInflater.from(context);
		this.type=type;
	}

	@Override
	public int getCount() {
		return paramInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return paramInfoList.get(position);
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
			convertView=inflater.inflate(R.layout.listview_paramaudit_item, null);
			holder.tvRowNum=(TextView) convertView.findViewById(R.id.tvRowNum);
			holder.tvSequence=(TextView) convertView.findViewById(R.id.tvSequence);
			holder.tvSkuNo=(TextView) convertView.findViewById(R.id.tvSkuNo);
			holder.tvUpdateType=(TextView) convertView.findViewById(R.id.tvUpdateType);
			holder.tvCheckState=(TextView) convertView.findViewById(R.id.tvCheckState);
			holder.tvCheckDateStr=(TextView) convertView.findViewById(R.id.tvCheckDateStr);
			holder.tvCheckDate=(TextView) convertView.findViewById(R.id.tvCheckDate);
			holder.tvCheckByStr=(TextView) convertView.findViewById(R.id.tvCheckByStr);
			holder.tvCheckBy=(TextView) convertView.findViewById(R.id.tvCheckBy);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ParamInfo paramInfo=paramInfoList.get(position);
		if(position==0){
			holder.tvRowNum.setVisibility(View.VISIBLE);
			String rowCount=context.getResources().getString(R.string.queryrow)+paramInfoList.size();
			holder.tvRowNum.setText(rowCount);
		}else{
			holder.tvRowNum.setVisibility(View.GONE);
		}
		holder.tvSequence.setText((position+1)+"");
		holder.tvSkuNo.setText(paramInfo.getProductName());
		String updateType=paramInfo.getUpdateType();
		if(updateType.equals(ParamManage.UPDATETYPE_UPDATE)){//修改
			holder.tvUpdateType.setText(R.string.update);
		}
		if(updateType.equals(ParamManage.UPDATETYPE_DELETE)){//刪除
			holder.tvUpdateType.setText(R.string.delete);
		}
		if(updateType.equals(ParamManage.UPDATETYPE_ADD)){//添加
			holder.tvUpdateType.setText(R.string.add);
		}
		String checkState=paramInfo.getCheckState();
		int green=context.getResources().getColor(R.color.green);
		int red=context.getResources().getColor(R.color.red);
		if(checkState.equals(ParamManage.CHECKSTATE_PASS)){//通過
			holder.tvCheckState.setText(R.string.check_pass);
			holder.tvCheckState.setTextColor(green);
		}
		else if(checkState.equals(ParamManage.CHECKSTATE_FAILED)){//駁回
			holder.tvCheckState.setText(R.string.check_failed);
			holder.tvCheckState.setTextColor(green);
		}
		else if(checkState.equals(ParamManage.CHECKSTATE_REVIEW)){//待簽核
			holder.tvCheckState.setText(R.string.check_review);
			holder.tvCheckState.setTextColor(red);
		}
		if(type==ParamManage.AUDITTYPE_MYSUBMIT){//我提交的簽核信息
			String checkDateStr=context.getResources().getString(R.string.check_date);
			holder.tvCheckDateStr.setText(checkDateStr);
			holder.tvCheckDate.setText(paramInfo.getCheckDate());
			String checkByStr=context.getResources().getString(R.string.audit_by);
			holder.tvCheckByStr.setText(checkByStr);
			holder.tvCheckBy.setText(paramInfo.getCheckBy());
		}else{//我簽核的參數信息
			String createDateStr=context.getResources().getString(R.string.create_date);
			holder.tvCheckDateStr.setText(createDateStr);
			holder.tvCheckDate.setText(paramInfo.getCreateDate());
			String createByStr=context.getResources().getString(R.string.submit_by);
			holder.tvCheckByStr.setText(createByStr);
			holder.tvCheckBy.setText(paramInfo.getCreateBy());
		}
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvRowNum;
		TextView tvSequence;
		TextView tvSkuNo;
		TextView tvUpdateType;
		TextView tvCheckState;
		TextView tvCheckDateStr;
		TextView tvCheckDate;
		TextView tvCheckByStr;
		TextView tvCheckBy;
		
	}

}
