package com.foxconn.paperless.main.account.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.Euser;

public interface AccountInfoView extends BaseView {
	/**
	 * 獲取賬號信息
	 */
	void getAccountInfo();
	/**
	 * 設置基本賬號信息
	 * @param user
	 */
	void setAccountBaseInfo(Euser user);
	/**
	 * 設置製造處列表適配器
	 * @param MFGList
	 * @param index
	 */
	void setMFGAdapter(List<String> MFGList);
	/**
	 * 設置SBU列表適配器并選中條目
	 * @param SBUList
	 * @param index
	 */
	void setSBUAdapter(List<String> SBUList,int index);
	/**
	 * 設置Team列表適配器并選中條目
	 * @param TeamList
	 * @param index
	 */
	void setTeamAdapter(List<String> TeamList,int index);
	/**
	 * 設置Section列表適配器并選中條目
	 * @param sectionList
	 * @param index
	 */
	void setSectionAdapter(List<String> sectionList,int index);
	/**
	 * 選中MFG下拉條目
	 * @param index
	 */
	void setMFGSelection(int index);
	
	
}
