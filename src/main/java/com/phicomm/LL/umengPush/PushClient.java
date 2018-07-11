package com.phicomm.LL.umengPush;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 执行具体的请求操作
 *
 * @author haifeng.zhou
 * @date 2018-07-06 16:51
 */
@Component
public class PushClient {

	@Value("${umeng.userAgent}")
	private String USER_AGENT;

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();

	protected static final String host = "http://msg.umeng.com";

	protected static final String uploadPath = "/upload";

	protected static final String postPath = "/api/send";

	/**
	 * 向友盟消息中心发送请求，同时生成签名
	 *
	 * @param msg 发送的具体内容
	 * @return
	 * @throws Exception
	 */
	public boolean send(UmengNotification msg) throws Exception {
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		String url = host + postPath;
		String postBody = msg.getPostBody();
		//生成签名，对请求方式（POST，大写）、url、masterSecret拼接在一起的值进行md5求值，放在url的后面
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
		url = url + "?sign=" + sign;
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		//请求友盟接口时需要此JSON字符串作为参数
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);

		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		int status = response.getStatusLine().getStatusCode();
		System.out.println("Response Code : " + status);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		if (status == 200) {
			System.out.println("Notification sent successfully.");
		} else {
			System.out.println("Failed to send the notification!");
		}
		return true;
	}


	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = (JSONObject) JSONObject.parse(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue

		return fileId;
	}

}
