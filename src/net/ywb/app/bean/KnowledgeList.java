package net.ywb.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import net.ywb.app.AppException;
import net.ywb.app.common.StringUtils;
import net.ywb.utils.APIUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import android.util.Xml;

/**
 * 帖子列表实体类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class KnowledgeList extends Entity{

	public final static int CATALOG_ASK = 1;
	public final static int CATALOG_SHARE = 2;
	public final static int CATALOG_OTHER = 3;
	public final static int CATALOG_JOB = 4;
	public final static int CATALOG_SITE = 5;
	
	private int pageSize;
	private int knowledgeCount;
	private List<Knowledge> knowledgelist = new ArrayList<Knowledge>();
	
	public int getPageSize() {
		return pageSize;
	}
	public int getKnowledgeCount() {
		return knowledgeCount;
	}
	public List<Knowledge> getKnowledgelist() {
		return knowledgelist;
	}
	
	public static KnowledgeList parse(InputStream stream) throws IOException, AppException {
		KnowledgeList klist = new KnowledgeList();
		Type type = new TypeToken<ArrayList<Knowledge>>() {
		}.getType();
		try {
			APIResult apiresult = APIResult.parse(stream);
			String status = apiresult.status;
			if (!apiresult.OK()) {
				Log.e("knowledge", "get knowledge status :" + status);
				throw new IOException(status);
			} else {
				String jsonstr = apiresult.result;;
				Log.d("testing", "test Knowledge jsonobj " + jsonstr);
				Gson gson = new Gson();
				ArrayList<Knowledge> fromJson = gson.fromJson(jsonstr, type);
				klist.knowledgelist = fromJson;
				klist.pageSize = fromJson.size();
				klist.knowledgeCount = fromJson.size();
				return klist;
			}
		} catch (Exception e) {
			Log.e("exception", e.getMessage());
			throw e;
		}
	}
}
