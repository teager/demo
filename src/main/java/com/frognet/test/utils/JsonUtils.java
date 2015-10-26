package com.frognet.test.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JsonUtils {

	//static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static String convertToXml(JSONObject json) {
		XMLSerializer xml = new XMLSerializer();
		String str = xml.write(json).toString();
		str = str.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		str = str.replaceFirst("\\s+", "");
		return str;
	}

	public static JSONObject post(String url, String jsonStr) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();

		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();

		HttpPost post = new HttpPost(url);
		post.setConfig(globalConfig);

		StringEntity json = new StringEntity(jsonStr);
		json.setContentEncoding("UTF-8");
		json.setContentType("application/json");
		post.setEntity(json);

		HttpResponse res = client.execute(post);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);

			String s = "";
			StringBuilder result = new StringBuilder();
			while (((s = reader.readLine()) != null)) {
				result.append(s);
			}
			inputStreamReader.close();
			reader.close();
			
			String resultStr = result.toString();
			if(StringUtils.isNotBlank(resultStr)){
				return JSONObject.fromObject(result.toString());
			}
		}

		return null;
	}

	public static JSONObject get(String url, String authorizationStr) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.setHeader("Content-Type", "application/json");
		get.setHeader("Authorization", authorizationStr);
		
		HttpResponse res = client.execute(get);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);

			String s = "";
			StringBuilder result = new StringBuilder();
			while (((s = reader.readLine()) != null)) {
				result.append(s);
			}
			inputStreamReader.close();
			reader.close();
			return JSONObject.fromObject(result.toString());
		}

		return null;
	}
	
	public static JSONObject get(String url) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		
		HttpGet get = new HttpGet(url);
		get.setConfig(globalConfig);  
		get.addHeader("User_Param", "2,2.0.2.1,837964679,Djfh5FTEdAIjzo7ZISHmz/XwjX7AOqW7IkzyDI1gjtg=");
		
		StringEntity json = new StringEntity("");
		json.setContentEncoding("UTF-8");
		json.setContentType("application/json");

		HttpResponse res = client.execute(get);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);

			String s = "";
			StringBuilder result = new StringBuilder();
			while (((s = reader.readLine()) != null)) {
				result.append(s);
			}
			inputStreamReader.close();
			reader.close();

			return JSONObject.fromObject(result.toString());
		}

		return null;
	}

	public static List<Media> paserMediasJson(JSONObject json) {
		List<Media> medias = new ArrayList<Media>();
		if (json != null) {
			JSONArray data = json.getJSONArray("data");
			if (data != null) {
				for (int i = 0; i < data.size(); i++) {
					JSONObject mediaT = data.getJSONObject(i);
					if (mediaT != null) {
						Media media = paserMediaJson(mediaT);
						if(media != null){
							medias.add(media);
						}
					}
				}
			}
		}
		return medias;
	}
	
	public static String paserUserSearchResultJson(String queryName, JSONObject json) {
		String instUserId = null;
		if (json != null) {
			JSONArray data = json.getJSONArray("data");
			if (data != null) {
				for (int i = 0; i < data.size(); i++) {
					JSONObject userTempJson = data.getJSONObject(i);
					if (userTempJson != null) {
						instUserId = userTempJson.getString("id");
						String userName = userTempJson.getString("username");
						if(userName.equalsIgnoreCase(queryName) && instUserId != null){
							return instUserId;
						}
					}
				}
			}
		}
		return instUserId;
	}
	

	public static Media paserMediaJson(JSONObject mediaJson) {
		if (mediaJson != null) {
			JSONObject images = mediaJson.getJSONObject("images");
			JSONObject videos = mediaJson.getJSONObject("videos");
			JSONObject user = mediaJson.getJSONObject("user");
			JSONObject likes = mediaJson.getJSONObject("likes");
			JSONObject caption = mediaJson.getJSONObject("caption");
			String instMediaId = mediaJson.getString("id");
			String type = mediaJson.getString("type");
			String midiaCreatedTime = mediaJson.getString("created_time");
			if (images != null && user != null ) {
				JSONObject low_resolution = images.getJSONObject("low_resolution");
				String low_resolution_url = low_resolution.getString("url");

				JSONObject thumbnail = images.getJSONObject("thumbnail");
				String thumbnail_url = thumbnail.getString("url");

				JSONObject standard_resolution = images.getJSONObject("standard_resolution");
				String standard_resolution_url = standard_resolution.getString("url");

				String instUid = user.getString("id");
				String instUname = user.getString("username");
				String profile_picture = user.getString("profile_picture");
				
				Long likeSize = 0L;
				if(likes != null){
					likeSize = likes.getLong("count");
					
				}
				
				String video_Low_url = null;
				String video_stand_url = null;
				if( videos != null && "video".equals(type)){
					JSONObject video_Low_Object = videos.getJSONObject("low_resolution");
					video_Low_url = video_Low_Object.getString("url");
					
					JSONObject video_stand_Object = videos.getJSONObject("standard_resolution");
					video_stand_url = video_stand_Object.getString("url");
				}
				String captionText = null;
				if(caption != null && !caption.isNullObject()){
					captionText = caption.getString("text");
				}
				

				return null;
			}
		}
		return null;
	}
}
