package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.ReportConfig;
import com.foxconn.paperless.helper.ReportConfigPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ReportConfigModel;
import com.foxconn.paperless.main.function.model.ReportConfigModelImpl;
import com.foxconn.paperless.main.function.view.ReportConfigDetailView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 報表配置詳情邏輯處理
 *@ClassName ReportConfigDetailPresenterImpl
 *@Author wunian
 *@Date 2018/1/6 上午9:21:38
 */
public class ReportConfigDetailPresenterImpl implements ReportConfigDetailPresenter,
		OnModelListener {
	
	private ReportConfigDetailView reportConfigDetailView;
	private Context context;
	private ReportConfigModel reportConfigModel;
	private Euser user;
	private Params params;
	private String reportNum ;
	private List<String> groupItem;//點檢項
	private List<List<CheckItem>> childItem;//點檢子項
	private List<String> SBUList;//報表已配置的SBU列表
	private String configCheckItem;//是否已配置過點檢項  1：是 0：否
	private String SBU;
	
	public ReportConfigDetailPresenterImpl(ReportConfigDetailView reportConfigDetailView,
			Context context){
		this.reportConfigDetailView=reportConfigDetailView;
		this.context=context;
		this.reportConfigModel=new ReportConfigModelImpl(this);
		this.user=(Euser) context.getApplicationContext();
		this.params=new Params(reportConfigModel);
		this.groupItem=new ArrayList<String>();
		this.childItem=new ArrayList<List<CheckItem>>();
		this.SBUList=new ArrayList<String>();
	}

	@Override
	public void success(JsonResult result) {
		reportConfigDetailView.dismissLoading();
		if(result.getAction().equals(Action.GET_REPORT_CHECKITEM)){
			groupItem=ReportConfigPresenterHelper.getGroupItem(result);//獲得點檢項
			childItem=ReportConfigPresenterHelper.getChildItem(result,groupItem);
			//獲取已配置點檢的點檢子項
			params=ParamsUtil.getParam(params, Action.GET_CONFIG_PROID, new String[]{
					reportNum,
					user.getSite(),
					user.getBu(),
					user.getMfg(),
					user.getSbu()
			});
			reportConfigModel.getConfigProId(params);
		}
		if(result.getAction().equals(Action.GET_CONFIG_PROID)){//獲取已配置點檢的點檢子項
			childItem=ReportConfigPresenterHelper.getCheckdProId(result,childItem);
			reportConfigDetailView.setCheckItemAdapter(groupItem,childItem);
			configCheckItem=ReportConfig.CHECKED;//已配置過點檢項
			getSBU();//獲得製造處下的SBU列表
		}
		if(result.getAction().equals(Action.GET_SBU)){//獲得製造處下的所有SBU
			SBUList=ReportConfigPresenterHelper.getSBU(result);
		}
		if(result.getAction().equals(Action.SAVE_REPORT_CONFIG)){//保存報表配置信息成功
			reportConfigDetailView.showToastSuccessMsg(R.string.reportconfig_success);
			reportConfigDetailView.back();
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_REPORT_CHECKITEM)){//此報表還未配置
			reportConfigDetailView.showToastFailedMsg(R.string.getsbucheckitem_failed);
		}
		if(result.getAction().equals(Action.GET_CONFIG_PROID)){
			reportConfigDetailView.setCheckItemAdapter(groupItem,childItem);
			configCheckItem=ReportConfig.NOCHECKED;//未配置過點檢項
			getSBU();//獲得製造處下的SBU列表
		}
		if(result.getAction().equals(Action.GET_SBU)){//獲得製造處下的SBU列表
			reportConfigDetailView.showToastFailedMsg(R.string.getsbu_failed);
		}
		if(result.getAction().equals(Action.SAVE_REPORT_CONFIG)){//保存報表配置信息失敗
			reportConfigDetailView.showToastFailedMsg(R.string.reportconfig_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		reportConfigDetailView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲得製造處的SBU
	 */
	private void getSBU(){
		params=ParamsUtil.getParam(params, Action.GET_SBU, new String[]{
			user.getSite(),
			user.getBu(),
			user.getMfg()
		});
		reportConfigModel.getSBU(params);
	}
	
	/**
	 * 獲得報表點檢項
	 */
	@Override
	public void getCheckItem(String reportNum) {
		this.reportNum=reportNum;
		params=ParamsUtil.getParam(params, Action.GET_REPORT_CHECKITEM, new String[]{
			user.getSite(),
			user.getBu(),
			reportNum
		});
		reportConfigModel.getCheckItem(params);
		reportConfigDetailView.showLoading();
	}
	/**
	 * 選擇所有點檢子項或全不選
	 */
	@Override
	public void selectAll(boolean checked) {
		childItem=ReportConfigPresenterHelper.getSelectAll(childItem,checked);
		reportConfigDetailView.setCheckItemAdapter(groupItem, childItem);
	}
	/**
	 * 提交保存
	 */
	@Override
	public void submitSave(List<List<CheckItem>> childItem) {
		this.childItem=childItem;
		if(this.childItem.size()<1){//此報表還未配置
			reportConfigDetailView.showToastFailedMsg(R.string.getsbucheckitem_failed);
			return;
		}
		//獲取選擇的點檢項數目
		int count=ReportConfigPresenterHelper.getSelectItemCount(childItem);
		if(count<1){
			reportConfigDetailView.showToastFailedMsg(R.string.select_checkitem);
			return;
		}
		if(SBUList.size()<0){//暫無可選擇的SBU
			reportConfigDetailView.showToastFailedMsg(R.string.getsbu_failed);
			return;
		}
		reportConfigDetailView.showSelectSBUDialog(R.string.selectsbu_title,
				R.string.btn_saveconfig,R.string.btn_no,SBUList);
	}
	/**
	 * 保存報表配置信息
	 */
	@Override
	public void saveReportConfig() {
		if(TextUtil.isEmpty(SBU)){//請選擇SBU
			reportConfigDetailView.showToastFailedMsg(R.string.select_sbu);
			return;
		}
		String proIdStr=ReportConfigPresenterHelper.getProIdStr(childItem);
		params=ParamsUtil.getParam(params, Action.SAVE_REPORT_CONFIG, new String[]{
				reportNum,
				user.getSite(),
				user.getBu(),
				user.getMfg(),
				SBU,
				proIdStr
		});
		reportConfigModel.saveReportConfig(params);
		reportConfigDetailView.showLoading();
		
	}

	@Override
	public void setSBU(String SBU) {
		this.SBU=SBU;
	}

}
