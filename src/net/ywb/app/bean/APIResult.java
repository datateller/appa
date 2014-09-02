package net.ywb.app.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import android.util.Log;

import net.ywb.app.AppException;
import net.ywb.app.bean.Comment.Refer;
import net.ywb.app.bean.Comment.Reply;
import net.ywb.app.common.StringUtils;


/**
 * API操作结果实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class APIResult extends Base {

	public String status;
	public String result;
	
	public APIResult() {
		status = "NULL";
		result = "";
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
	public static APIResult parse(InputStream stream) throws IOException, AppException {
		APIResult res = new APIResult();
		try{
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			String jsonstr = buffer.toString();
			JSONObject jsonObj = new JSONObject(jsonstr);
			String status = jsonObj.getString("status");
			Log.d("testing", "APIUtil getDataFromJson status json string:  " + status);
			String result = jsonObj.getString("result");
			Log.d("testing", "APIUtil getDataFromJson data json string:  " + result);
			res.status = status;
			res.result = result;
			return res;
		} catch (Exception e) {
			throw AppException.json(e);
		} finally {
			stream.close();
		}
	}

	@Override
	public String toString(){
		return String.format("RESULT: status:%d,data:%s", status, result);
	}
}
