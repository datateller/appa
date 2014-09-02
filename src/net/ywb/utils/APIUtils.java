package net.ywb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import net.ywb.app.AppException;
import net.ywb.app.bean.Status;

import org.json.JSONObject;
import android.util.Log;

import com.google.gson.Gson;


public class APIUtils {

	//从http返回的流数据中解析出json数据（result部分）
	public static String getDataFromJson(InputStream stream) throws AppException, IOException{
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			String jsonstr = buffer.toString();
			Log.d("testing", "APIUtil getDataFromJson full json string: " + jsonstr);
			JSONObject jsonObj = new JSONObject(jsonstr);
			String data = jsonObj.getJSONObject("result").toString();
			Log.d("testing", "APIUtil getDataFromJson data json string:  " + data);
			return data;
		} catch (Exception e) {
			Log.e("exception", e.getMessage());
			throw AppException.json(e);
		}
	}
	
	//从http返回的流数据中解析出json操作结果（status部分）
	public static String getStatusFromJson(InputStream stream) throws AppException, IOException{
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			String jsonstr = buffer.toString();
			Log.d("testing", "getStatusFromJson full json string : " + jsonstr);
			JSONObject jsonObj = new JSONObject(jsonstr);
			String fromJson = jsonObj.getString("status");
			return fromJson;
		} catch (Exception e) {
			Log.e("exception", e.getMessage());
			throw AppException.json(e);
		}
	}
	
}
