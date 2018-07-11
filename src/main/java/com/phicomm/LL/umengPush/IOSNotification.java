package com.phicomm.LL.umengPush;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * IOS通知的抽象类，继承了友盟通知抽象类
 * 请求友盟消息API时需要一个JSON字符串，安卓和IOS的json格式不同
 * json格式可以查看友盟官方文档:  https://developer.umeng.com/docs/66632/detail/68343#h2-u53D1u9001u9650u52363
 *
 * @author haifeng.zhou
 * @date 2018-07-06 16:34
 */
public class IOSNotification extends UmengNotification {

	/**
	 * Keys can be set in the aps level
	 * IOS的json字符串特有的aps模块
	 */
	protected static final HashSet<String> APS_KEYS = new HashSet<>(Arrays.asList("alert", "badge", "sound", "content-available"));

	/**
	 * 请求友盟通知推送API时需要一个JSON字符串，此方法就是向该JSON对象添加一对key、value键值对
	 * 添加时根据key来判断参数中的键值对添加到JSON对象的具体位置
	 *
	 * @return 是否添加成功
	 * @throws Exception
	 */
	@Override
	public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
		if (ROOT_KEYS.contains(key)) {
			// This key should be in the root level
			rootJson.put(key, value);
		} else if (APS_KEYS.contains(key)) {
			// This key should be in the aps level
			JSONObject apsJson = null;
			JSONObject payloadJson = null;

			if (rootJson.containsKey("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}

			if (payloadJson.containsKey("aps")) {
				apsJson = payloadJson.getJSONObject("aps");
			} else {
				apsJson = new JSONObject();
				payloadJson.put("aps", apsJson);
			}

			apsJson.put(key, value);
		} else if (POLICY_KEYS.contains(key)) {
			// This key should be in the body level
			JSONObject policyJson = null;

			if (rootJson.containsKey("policy")) {
				policyJson = rootJson.getJSONObject("policy");
			} else {
				policyJson = new JSONObject();
				rootJson.put("policy", policyJson);
			}

			policyJson.put(key, value);
		} else {
			if (key.equals("payload") || key.equals("aps") || key.equals("policy")) {
				throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
			} else {
				throw new Exception("Unknownd key: " + key);
			}
		}

		return true;
	}

	/**
	 * Set customized key/value for IOS notification
	 * JSON字符串中有一个extra块，用于存放一些额外的参数（用户自定义）
	 *
	 * @return 是否插入成功
	 * @throws Exception
	 */
	public boolean setCustomizedField(String key, String value) throws Exception {
		//rootJson.put(key, value);
		JSONObject payloadJson = null;
		if (rootJson.containsKey("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}
		payloadJson.put(key, value);
		return true;
	}

	public void setAlert(String token) throws Exception {
		setPredefinedKeyValue("alert", token);
	}

	public void setBadge(Integer badge) throws Exception {
		setPredefinedKeyValue("badge", badge);
	}

	public void setSound(String sound) throws Exception {
		setPredefinedKeyValue("sound", sound);
	}

	public void setContentAvailable(Integer contentAvailable) throws Exception {
		setPredefinedKeyValue("content-available", contentAvailable);
	}
}
