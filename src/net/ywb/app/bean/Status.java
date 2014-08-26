package net.ywb.app.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import net.ywb.app.AppException;
import net.ywb.app.bean.Comment.Refer;
import net.ywb.app.bean.Comment.Reply;
import net.ywb.app.common.StringUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.annotations.Expose;

/**
 * 数据操作结果实体类
 * @author shengeng
 * @version 1.0
 * @created 2014-8-17
 */
public class Status extends Base {

	private String status;
	
	@Expose(deserialize = false)
	private Map<String, String> result;
	
	public void Status() {
		
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}
	
	public boolean OK() {
		return status.equals("OK");
	}

	/**
	 * 解析调用结果
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static Status parse(InputStream stream) throws IOException, AppException {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			String jsonstr = buffer.toString();
//			JSONObject jsonObj = new JSONObject(jsonstr); 
//			Log.d("testing", "test jsonobj " + jsonObj.getJSONObject("result").toString());
			Gson gson = new Gson();
			Status fromJson = gson.fromJson(jsonstr, Status.class);
			return fromJson;
		} catch (Exception e) {
			Log.e("exception", e.getMessage());
			throw AppException.xml(e);
		} finally {
			stream.close();
		}
	}

}
