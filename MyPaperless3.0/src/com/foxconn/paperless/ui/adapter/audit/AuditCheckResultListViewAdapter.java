package com.foxconn.paperless.ui.adapter.audit;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.audit.CheckResult;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.main.function.presenter.AuditCheckResultPresenter;
import com.foxconn.paperless.main.function.presenter.AuditCheckResultPresenterImpl;
import com.foxconn.paperless.main.view.AccountFragment;
import com.foxconn.paperless.util.FileUtil;

public class AuditCheckResultListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<CheckResult> checkResultList;
	
	private AuditCheckResultPresenter auditCheckResultPresenter;
	private String checkType;
	
	
	public AuditCheckResultListViewAdapter(Context context,List<CheckResult> checkResultList,
			 AuditCheckResultPresenter auditCheckResultPresenter,String checkType){
		this.context=context;
		this.checkResultList=checkResultList;
		this.auditCheckResultPresenter=auditCheckResultPresenter;
		inflater=LayoutInflater.from(context);
		this.checkType=checkType;
	}
	
	@Override
	public int getCount() {
		return checkResultList.size();
	}

	@Override
	public Object getItem(int position) {
		return checkResultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_auditcheckresult_item, null);
			holder.tvProId=(TextView) convertView.findViewById(R.id.tvProId);
			holder.tvCheckName=(TextView) convertView.findViewById(R.id.tvCheckName);
			holder.tvCheckYield=(TextView) convertView.findViewById(R.id.tvCheckYield);
			holder.tvCheckProName=(TextView) convertView.findViewById(R.id.tvCheckProName);
			holder.tvCheckResult=(TextView) convertView.findViewById(R.id.tvCheckResult);
			holder.tvCheckContent=(TextView) convertView.findViewById(R.id.tvCheckContent);
			holder.tvIcarNo=(TextView) convertView.findViewById(R.id.tvIcarNo);
			holder.picLayout= (LinearLayout) convertView.findViewById(R.id.picLayout);
			holder.ivPic= (ImageView) convertView.findViewById(R.id.ivPic);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.ivPic.setTag(position);
		holder.tvProId.setText(checkResultList.get(position).getProId());
		holder.tvCheckName.setText(checkResultList.get(position).getCheckName());
		holder.tvCheckYield.setText(checkResultList.get(position).getCheckYeild());
		holder.tvCheckProName.setText(checkResultList.get(position).getCheckProName());
		
		holder.tvCheckContent.setText(checkResultList.get(position).getCheckContent());
		holder.tvIcarNo.setText(checkResultList.get(position).getIcarNo());
		
		String checkResult=checkResultList.get(position).getCheckResult();
		if(checkResult.equals(Report.ISCHECKCONTENT)){//正常點檢項
			holder.tvCheckResult.setText(R.string.checkresult_normal);
			holder.tvCheckResult.setTextColor(Color.BLACK);
		}else{//提交異常后的點檢項
			holder.tvCheckResult.setText(R.string.checkresult_exception);
			holder.tvCheckResult.setTextColor(Color.RED);
		}
		String imageData=checkResultList.get(position).getImageData();
		if(imageData.equals(Report.DEFAULT_VALUE)){
			holder.picLayout.setVisibility(View.GONE);
		}else{
			holder.picLayout.setVisibility(View.VISIBLE);
			holder.ivPic.setImageBitmap(FileUtil.byteStringToBitmap(imageData));
			holder.ivPic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int tag=(Integer) holder.ivPic.getTag();
					String imageData=checkResultList.get(tag).getImageData();
					//放大圖片
					auditCheckResultPresenter.goZoomImagePage(imageData);
				}
			});
		}
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvProId;
		TextView tvCheckName;
		TextView tvCheckYield;
		TextView tvCheckProName;
		TextView tvCheckResult;
		TextView tvCheckContent;
		TextView tvIcarNo;
		LinearLayout picLayout;
		ImageView ivPic;
	}
}
