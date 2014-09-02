package net.ywb.app.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Log;
import com.google.gson.Gson;

import net.ywb.app.AppException;
import net.ywb.utils.APIUtils;

public class Knowledge extends Entity{

	private Integer kid;
	private String title;
	private String pic;
	private String icon;
	private String Abstract;
	private String address;
	private String link;

	public Knowledge() {
		// TODO Auto-generated constructor stub
	}

	public Integer getKid() {
		return kid;
	}

	public void setKid(Integer kid) {
		this.kid = kid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAbstract() {
		return Abstract;
	}

	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 解析调用结果
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static Knowledge parse(InputStream stream) throws IOException, AppException {
		try {
			String jsonstr = APIUtils.getDataFromJson(stream);
			Log.d("testing", "test Knowledge jsonobj " + jsonstr);
			Gson gson = new Gson();
			Knowledge fromJson = gson.fromJson(jsonstr, Knowledge.class);
			return fromJson;
		} catch (Exception e) {
			Log.e("exception", e.getMessage());
			throw e;
		} finally {
			stream.close();
		}
	}
	
	@Override
	public String toString() {
		return "Knowledge [kid=" + kid + ", title=" + title
				+ ", pic=" + pic + ", icon=" + icon + ", abstract" + Abstract
				+ ", link" + link + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kid == null) ? 0 : kid.hashCode());
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
		Knowledge other = (Knowledge) obj;
		if (kid == null) {
			if (other.kid != null)
				return false;
		} else if (!kid.equals(other.kid))
			return false;
		return true;
	}
}
