package com.foxconn.paperless.bean;

import java.io.Serializable;
/**
 * 意見反饋表對應的bean
 *@ClassName Feedback
 *@Author wunian
 *@Date 2017/10/27 下午5:54:35
 */
public class Feedback implements Serializable {

	private static final long serialVersionUID = 1L;
	private String logonName;
	private String ChineseName;
	private String ext;
	private String Email;
	private String site;
	private String lasteditdt;
	private String BG;
	private String feedback;
	
	public Feedback(){}
	
	public Feedback(String logonName, String chineseName, String ext,
			String email, String site, String lasteditdt, String bG,
			String feedback) {
		this.logonName = logonName;
		ChineseName = chineseName;
		this.ext = ext;
		Email = email;
		this.site = site;
		this.lasteditdt = lasteditdt;
		BG = bG;
		this.feedback = feedback;
	}
	
	public String getLogonName() {
		return logonName;
	}
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getLasteditdt() {
		return lasteditdt;
	}
	public void setLasteditdt(String lasteditdt) {
		this.lasteditdt = lasteditdt;
	}
	public String getBG() {
		return BG;
	}
	public void setBG(String bG) {
		BG = bG;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
	
}
