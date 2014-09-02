package net.ywb.app.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Log;
import com.google.gson.Gson;

import net.ywb.app.AppException;
import net.ywb.utils.APIUtils;

public class BabyInfo extends Entity{

	private Integer userid;
	private String username;
	private String password;
	private String babyname;
	private String birthday;
	private String sex;
	private String weight;
	private String height;
	private String city;
	private String homeaddr;
	private String schooladdr;

	public BabyInfo() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHomeaddr() {
		return homeaddr;
	}

	public void setHomeaddr(String homeaddr) {
		this.homeaddr = homeaddr;
	}

	public String getSchooladdr() {
		return schooladdr;
	}

	public void setSchooladdr(String schooladdr) {
		this.schooladdr = schooladdr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 解析调用结果
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static BabyInfo parse(InputStream stream) throws IOException, AppException {
		try {
			APIResult apiresult = APIResult.parse(stream);
			String status = apiresult.status;
			if (!apiresult.OK()) {
				Log.e("baby", "get babyinfo status :" + status);
				throw new IOException(status);
			} else {
				Log.d("baby", "test BabyInfo data " + apiresult.result);
				Gson gson = new Gson();
				BabyInfo fromJson = gson.fromJson(apiresult.result, BabyInfo.class);
				return fromJson;
			}
		} catch (Exception e) {
			Log.e("exception", e.getMessage());
			throw e;
		} finally {
			stream.close();
		}
	}
	
	@Override
	public String toString() {
		return "Baby [userId=" + userid + ", userName=" + username
				+ ", password=" + password + ", babyname=" + babyname + ", birthday" + birthday 
				+ ", homeaddr" + homeaddr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BabyInfo other = (BabyInfo) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
}
