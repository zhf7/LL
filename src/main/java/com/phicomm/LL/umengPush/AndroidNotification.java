package com.phicomm.LL.umengPush;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 安卓通知的抽象类，继承了友盟通知抽象类
 * 请求友盟消息API时需要一个JSON字符串，安卓和IOS的json格式不同
 * json格式可以查看友盟官方文档:  https://developer.umeng.com/docs/66632/detail/68343#h2-u53D1u9001u9650u52363
 */
public abstract class AndroidNotification extends UmengNotification {

	/**
	 * Keys can be set in the payload level
	 * 请求友盟消息API时需要一个JSON字符串
	 * 此set中的所有key需要放在JSON字符串的payload级
	 */
	protected static final HashSet<String> PAYLOAD_KEYS = new HashSet<>(Arrays.asList("display_type"));

	/**
	 * Keys can be set in the body level
	 * 请求友盟消息API时需要一个JSON字符串
	 * 此set中的所有key需要放在JSON字符串的body级
	 */
	protected static final HashSet<String> BODY_KEYS = new HashSet<>(Arrays.asList("ticker", "title",
			"text", "builder_id", "icon", "largeIcon", "img", "play_vibrate", "play_lights",
			"play_sound", "sound", "after_open", "url", "activity", "custom"));

	public enum DisplayType {
		//通知:消息送达到用户设备后，由友盟SDK接管处理并在通知栏上显示通知内容。
		NOTIFICATION {
			public String getValue() {
				return "notification";
			}
		},
		//消息:消息送达到用户设备后，消息内容透传给应用自身进行解析处理。
		MESSAGE {
			public String getValue() {
				return "message";
			}
		};

		public abstract String getValue();
	}

	/**
	 * 通知到达App之后的操作
	 */
	public enum AfterOpenAction {
		go_app,        //打开应用
		go_url,        //跳转到URL
		go_activity,//打开特定的activity
		go_custom    //用户自定义内容。
	}

	/**
	 * Set key/value in the rootJson, for the keys can be set please see ROOT_KEYS, PAYLOAD_KEYS, BODY_KEYS and POLICY_KEYS.
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
		} else if (PAYLOAD_KEYS.contains(key)) {
			// This key should be in the payload level
			JSONObject payloadJson = null;
			if (rootJson.containsKey("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}
			payloadJson.put(key, value);
		} else if (BODY_KEYS.contains(key)) {
			// This key should be in the body level
			JSONObject bodyJson = null;
			JSONObject payloadJson = null;
			// 'body' 在 'payload'的下面, 所以当payload不存在时需要新建一个payload
			if (rootJson.containsKey("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}
			// Get body JSONObject, generate one if not existed
			if (payloadJson.containsKey("body")) {
				bodyJson = payloadJson.getJSONObject("body");
			} else {
				bodyJson = new JSONObject();
				payloadJson.put("body", bodyJson);
			}
			bodyJson.put(key, value);
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
			if (key.equals("payload") || key.equals("body") || key.equals("policy") || key.equals("extra")) {
				throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
			} else {
				throw new Exception("Unknown key: " + key);
			}
		}
		return true;
	}

	/**
	 * Set extra key/value for Android notification
	 * JSON字符串中有一个extra块，用于存放一些额外的参数（用户自定义）
	 *
	 * @return 是否插入成功
	 * @throws Exception
	 */
	public boolean setExtraField(String key, String value) throws Exception {
		JSONObject payloadJson = null;
		JSONObject extraJson = null;
		if (rootJson.containsKey("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}

		if (payloadJson.containsKey("extra")) {
			extraJson = payloadJson.getJSONObject("extra");
		} else {
			extraJson = new JSONObject();
			payloadJson.put("extra", extraJson);
		}
		extraJson.put(key, value);
		return true;
	}

	//展示类型：通知(NOTIFICATION)或消息(MESSAGE)
	public void setDisplayType(DisplayType d) throws Exception {
		setPredefinedKeyValue("display_type", d.getValue());
	}

	///通知栏提示文字
	public void setTicker(String ticker) throws Exception {
		setPredefinedKeyValue("ticker", ticker);
	}

	///通知标题
	public void setTitle(String title) throws Exception {
		setPredefinedKeyValue("title", title);
	}

	///通知文字描述
	public void setText(String text) throws Exception {
		setPredefinedKeyValue("text", text);
	}

	///用于标识该通知采用的样式。使用该参数时, 必须在SDK里面实现自定义通知栏样式。
	public void setBuilderId(Integer builder_id) throws Exception {
		setPredefinedKeyValue("builder_id", builder_id);
	}

	///状态栏图标ID, R.drawable.[smallIcon],如果没有, 默认使用应用图标。
	public void setIcon(String icon) throws Exception {
		setPredefinedKeyValue("icon", icon);
	}

	///通知栏拉开后左侧图标ID
	public void setLargeIcon(String largeIcon) throws Exception {
		setPredefinedKeyValue("largeIcon", largeIcon);
	}

	///通知栏大图标的URL链接。该字段的优先级大于largeIcon。该字段要求以http或者https开头。
	public void setImg(String img) throws Exception {
		setPredefinedKeyValue("img", img);
	}

	///收到通知是否震动,默认为"true"
	public void setPlayVibrate(Boolean play_vibrate) throws Exception {
		setPredefinedKeyValue("play_vibrate", play_vibrate.toString());
	}

	///收到通知是否闪灯,默认为"true"
	public void setPlayLights(Boolean play_lights) throws Exception {
		setPredefinedKeyValue("play_lights", play_lights.toString());
	}

	///收到通知是否发出声音,默认为"true"
	public void setPlaySound(Boolean play_sound) throws Exception {
		setPredefinedKeyValue("play_sound", play_sound.toString());
	}

	///通知声音，R.raw.[sound]. 如果该字段为空，采用SDK默认的声音
	public void setSound(String sound) throws Exception {
		setPredefinedKeyValue("sound", sound);
	}

	///收到通知后播放指定的声音文件
	public void setPlaySound(String sound) throws Exception {
		setPlaySound(true);
		setSound(sound);
	}

	///点击"通知"的后续行为，默认为打开app。
	public void goAppAfterOpen() throws Exception {
		setAfterOpenAction(AfterOpenAction.go_app);
	}

	///点击"通知"的后续行为
	public void goUrlAfterOpen(String url) throws Exception {
		setAfterOpenAction(AfterOpenAction.go_url);
		setUrl(url);
	}

	public void goActivityAfterOpen(String activity) throws Exception {
		setAfterOpenAction(AfterOpenAction.go_activity);
		setActivity(activity);
	}

	public void goCustomAfterOpen(String custom) throws Exception {
		setAfterOpenAction(AfterOpenAction.go_custom);
		setCustomField(custom);
	}

	public void goCustomAfterOpen(JSONObject custom) throws Exception {
		setAfterOpenAction(AfterOpenAction.go_custom);
		setCustomField(custom);
	}

	///点击"通知"的后续行为，默认为打开app。原始接口
	public void setAfterOpenAction(AfterOpenAction action) throws Exception {
		setPredefinedKeyValue("after_open", action.toString());
	}

	public void setUrl(String url) throws Exception {
		setPredefinedKeyValue("url", url);
	}

	public void setActivity(String activity) throws Exception {
		setPredefinedKeyValue("activity", activity);
	}

	///can be a string of json
	public void setCustomField(String custom) throws Exception {
		setPredefinedKeyValue("custom", custom);
	}

	public void setCustomField(JSONObject custom) throws Exception {
		setPredefinedKeyValue("custom", custom);
	}

}
