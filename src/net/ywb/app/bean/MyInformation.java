package net.ywb.app.bean;

import java.io.IOException;
import java.io.InputStream;

import net.ywb.app.AppException;
import net.ywb.app.common.StringUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

/**
 * 我的个人信息实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class MyInformation extends Entity{

//	private String name;
	private String face;
	private int gender;
	public User user;
	public BabyInfo baby;
//	private String jointime;
//	private String from;
//	private String devplatform;
//	private String expertise;
//	private int favoritecount;
//	private int fanscount;
//	private int followerscount;
	
	public MyInformation(User user, BabyInfo baby) {
		this.user = user;
		this.baby = baby;
		this.gender = 1;
		this.face = "";
	}
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	
//	public String getJointime() {
//		return jointime;
//	}
//	public void setJointime(String jointime) {
//		this.jointime = jointime;
//	}
//
//	public String getDevplatform() {
//		return devplatform;
//	}
//	public void setDevplatform(String devplatform) {
//		this.devplatform = devplatform;
//	}
//	public String getExpertise() {
//		return expertise;
//	}
//	public void setExpertise(String expertise) {
//		this.expertise = expertise;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getFrom() {
//		return from;
//	}
//	public void setFrom(String from) {
//		this.from = from;
//	}
//	public int getFavoritecount() {
//		return favoritecount;
//	}
//	public void setFavoritecount(int favoritecount) {
//		this.favoritecount = favoritecount;
//	}
//	public int getFanscount() {
//		return fanscount;
//	}
//	public void setFanscount(int fanscount) {
//		this.fanscount = fanscount;
//	}
//	public int getFollowerscount() {
//		return followerscount;
//	}
//	public void setFollowerscount(int followerscount) {
//		this.followerscount = followerscount;
//	}
	
	public static MyInformation parse(User user, BabyInfo baby) throws IOException, AppException {
		MyInformation myinfo = new MyInformation(user, baby);
		myinfo.setGender(1);
		return myinfo;
	}
}
