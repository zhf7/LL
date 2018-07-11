package com.phicomm.LL.Umeng;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;


/**
 * 经测试，友盟透传只有当应用活着的时候可以直接透到应用内，如果应用挂了，这次的透传只能把应用拉活而已，但是消息透传不进来
 * 友盟的通知栏，可以控制声音，振动，但是不能做分类覆盖，只支持覆盖模式或全部平铺模式，
 * 所以综合考虑，友盟采用通知栏覆盖逻辑
 *
 * @author zyguo
 */
@Component
public class UmengPush {
	private String url = "http://msg.umeng.com/api/send";
	private String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 " +
			"(KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
	private static String appKey;
	private static String masterSecret;

	protected String dealPushResponse(HttpResponse response, PushBody pushBody) {
		try {
			HttpEntity entity = response.getEntity();// 响应实体/内容
			String ret = EntityUtils.toString(entity, "gbk");
//			log.debug("umeng ret=" + ret);
//			if( BUtils.isNotBlank( ret )  || ret.indexOf( "SUCCESS") > 0 ){
			if (ret.indexOf("SUCCESS") > 0) {
				return "SUCCESS, " + ret;
			}
			return "umeng push fail, ret=" + ret;
		} catch (Exception e) {
//			log.error("umeng dealPushResponse 异常", e);
			return "umeng dealPushResponse Exception ";
		}
	}

	protected JSONObject pushBody2message(PushBody pushBody, boolean isToApp) {
		JSONObject payload = new JSONObject();
		JSONObject body = new JSONObject();
		if (isToApp) { //透传模式
			payload.put("display_type", "message");
			body.put("custom", pushBody);
		} else { // 通知模式
			payload.put("display_type", "notification");
			body.put("title", pushBody.getNoticeTitle());
			body.put("ticker", pushBody.getTicker());
			body.put("text", pushBody.getNoticeDesc());
			body.put("icon", "");                        //小图标
			body.put("largeIcon", "");                //大图标
			body.put("sound", pushBody.getToneUrl());                        //声音文件
			body.put("after_open", "go_app");                                //点击通知栏后处理方式
			body.put("sound", pushBody.getToneUrl());                        //声音文件
			body.put("play_vibrate", pushBody.isOpenVibration() + "");            //是否开启震动
			body.put("play_lights", pushBody.isOpenLight() + "");                //是否开启呼吸灯
			body.put("play_sound", pushBody.isOpenTone() + "");                    //是否开启提示音

		}
		payload.put("body", body);

		long timestamp = System.currentTimeMillis();
		String validationToken = DigestUtils.md5Hex(appKey.toLowerCase() + masterSecret.toLowerCase() + timestamp);
		JSONObject json = new JSONObject();
		json.put("appkey", appKey);
		json.put("timestamp", timestamp);
		json.put("type", "unicast");                                        //单播模式
		json.put("payload", payload);
		json.put("validation_token", validationToken);
		if (pushBody.getParams() != null && pushBody.getParams().isEmpty() == false) {
			json.put("extra", pushBody.getParams());
		}
		json.put("device_tokens", pushBody.getToken());                //device_token的方式
		//json.put("alias", pushBody.getToken());					//别名的方式
		return json;
	}

	protected HttpResponse sendPush(JSONObject msg, String token, boolean isToApp) {
		try {
			HttpClient client = new DefaultHttpClient();
			String postBody = msg.toString();
			String now = LocalDateTime.now().plusMinutes(3).toString().replace("T", " ");
			postBody = "{" +
					"    \"policy\": {" +
					"        \"expire_time\": \"" + now + "\"" +
					"    }," +
					"    \"description\": \"啊啊啊777啊\"," +
					"    \"production_mode\": \"false\"," +
					"    \"appkey\": \"5b3d8de0f29d981ab1000026\"," +
					"    \"payload\": {" +
					"        \"body\": {" +
					"            \"title\": \"标题\"," +
					"            \"ticker\": \"标题\"," +
					"            \"text\": \"111111111134243221111111111111111111111111111\"," +
					"            \"after_open\": \"go_app\"," +
					"            \"play_vibrate\": \"false\"," +
					"            \"play_lights\": \"false\"," +
					"            \"play_sound\": \"true\"" +
					"        }," +
					"        \"display_type\": \"notification\"" +
					"    }," +
					"    \"type\": \"broadcast\"," +
					"    \"timestamp\": \"" + System.currentTimeMillis() + "\"" +
					"}";
//	        log.debug("postBody=" + postBody );
			//String sign = DigestUtils.md5Hex(("POST" + url + postBody + masterSecret).getBytes("utf8"));
			//url = url + sign;
//	        log.debug("url=" + url );
			String sign = "?sign=" + DigestUtils.md5Hex("POST" + url + postBody + masterSecret);
			System.out.println(url + sign);
			HttpPost post = new HttpPost(url + sign);
			post.setHeader("User-Agent", USER_AGENT);
			StringEntity se = new StringEntity(postBody, "UTF-8");
			post.setEntity(se);
			// Send the post request and get the response
			HttpResponse response = client.execute(post);
			return response;

		} catch (Exception e) {
//			log.error("umeng sendPush 异常", e);
		}
		return null;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	public String push(String title, String desc) {
		System.out.println("test umeng Push!!");
		setAppKey("5b3d8de0f29d981ab1000026");
		setMasterSecret("dkavfacpj0afb7sn5pvtfj3yvxlum1gh");
		String token = "AvGtJ06FlKtlkkQETTjQOu2LKFZoUIH6R4RL0MSeH08B";
		boolean isToApp = true;

		PushBody pushBody = createPushBody(token, 1, title, desc);
		JSONObject msg = pushBody2message(pushBody, isToApp);
		HttpResponse response = sendPush(msg, pushBody.getToken(), isToApp);

		if (response != null) {
			String result = dealPushResponse(response, pushBody);
			System.out.println("send push result=," + result);
			return result == null ? "" : result;
		}
		return "";
	}

	private PushBody createPushBody(String token, int noticeType, String title, String desc) {
		PushBody body = new PushBody();
		long t = System.currentTimeMillis();
		body.setNoticeTitle(title);
		body.setNoticeDesc(desc);
		body.setTicker("test ticker," + t);
		HashMap<String, String> params = new HashMap<>();
		params.put("fid", 10 + "");
		params.put("tid", 300 + "");
		body.setParams(params);
		body.setOpenTone(true);
		body.setOpenVibration(true);
		body.setToken(token);
		body.setToneUrl("default");
		body.setNoticeType(noticeType);
		return body;
	}
}
