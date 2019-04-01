package com.foxconn.paperless.bean;

import java.io.Serializable;
import java.util.List;

import net.tsz.afinal.core.Arrays;

import android.app.Application;
/**
 * 用户表对应的bean 可以在整个应用内被访问
 *@ClassName Euser
 *@Author wunian
 *@Date 2017/9/5 下午1:47:46
 */
public class Euser extends Application implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String logonName;
	private String password;
	private String chineseName;
	private String EnglishName;
	private String title;
	private String ext;
	private String email;
	private String userlevel;
	private String master;
	private String team;
	private String section;
	private String sbu;
	private String mfg;
	private String bu;
	private String site;
	private String lasteditby;
	private String lasteditdt;
	private String bg;
	
	public Euser(){}
	
	public  Euser(String logonName, String password, String chineseName,
			String englishName, String title, String ext, String email,
			String userlevel, String master, String team, String section,
			String sbu, String mfg, String bu, String site, String lasteditby,
			String lasteditdt, String bg) {
		this.logonName = logonName;
		this.password = password;
		this.chineseName = chineseName;
		EnglishName = englishName;
		this.title = title;
		this.ext = ext;
		this.email = email;
		this.userlevel = userlevel;
		this.master = master;
		this.team = team;
		this.section = section;
		this.sbu = sbu;
		this.mfg = mfg;
		this.bu = bu;
		this.site = site;
		this.lasteditby = lasteditby;
		this.lasteditdt = lasteditdt;
		this.bg = bg;
	}
	
	public void setEuser(String logonName, String password, String chineseName,
			String englishName, String title, String ext, String email,
			String userlevel, String master, String team, String section,
			String sbu, String mfg, String bu, String site, String lasteditby,
			String lasteditdt, String bg) {
		this.logonName = logonName;
		this.password = password;
		this.chineseName = chineseName;
		this.EnglishName = englishName;
		this.title = title;
		this.ext = ext;
		this.email = email;
		this.userlevel = userlevel;
		this.master = master;
		this.team = team;
		this.section = section;
		this.sbu = sbu;
		this.mfg = mfg;
		this.bu = bu;
		this.site = site;
		this.lasteditby = lasteditby;
		this.lasteditdt = lasteditdt;
		this.bg = bg;
	}
	
	public List<String> getEuser(){
		String[] data={
				this.logonName,
				this.password ,
				this.chineseName,
				EnglishName,
				this.title,
				this.ext,
				this.email,
				this.userlevel,
				this.master,
				this.team ,
				this.section,
				this.sbu ,
				this.mfg,
				this.bu ,
				this.site,
				this.lasteditby,
				this.lasteditdt,
				this.bg	
		};
		return Arrays.asList(data);
	}
	
	
	/**
	 * 設置Euser屬性，用於保存修改后的賬號信息
	 * @param chineseName
	 * @param englishName
	 * @param ext
	 * @param email
	 * @param team
	 * @param section
	 * @param sbu
	 * @param mfg
	 */
	public void setEuser(String[] userInfo){
		this.chineseName = userInfo[0];
		this.EnglishName = userInfo[1];
		this.ext = userInfo[2];
		this.email = userInfo[3];
		this.team = userInfo[4];
		this.section = userInfo[5];
		this.sbu = userInfo[6];
		this.mfg = userInfo[7];
	}

	public String getLogonName() {
		return logonName;
	}
	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return EnglishName;
	}
	public void setEnglishName(String englishName) {
		EnglishName = englishName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSbu() {
		return sbu;
	}
	public void setSbu(String sbu) {
		this.sbu = sbu;
	}
	public String getMfg() {
		return mfg;
	}
	public void setMfg(String mfg) {
		this.mfg = mfg;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getLasteditby() {
		return lasteditby;
	}
	public void setLasteditby(String lasteditby) {
		this.lasteditby = lasteditby;
	}
	public String getLasteditdt() {
		return lasteditdt;
	}
	public void setLasteditdt(String lasteditdt) {
		this.lasteditdt = lasteditdt;
	}
	public String getBg() {
		return bg;
	}
	public void setBg(String bg) {
		this.bg = bg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Euser [logonName=" + logonName + ", password=" + password
				+ ", chineseName=" + chineseName + ", EnglishName="
				+ EnglishName + ", title=" + title + ", ext=" + ext
				+ ", email=" + email + ", userlevel=" + userlevel + ", master="
				+ master + ", team=" + team + ", section=" + section + ", sbu="
				+ sbu + ", mfg=" + mfg + ", bu=" + bu + ", site=" + site
				+ ", lasteditby=" + lasteditby + ", lasteditdt=" + lasteditdt
				+ ", bg=" + bg + "]";
	}
	
	
}
