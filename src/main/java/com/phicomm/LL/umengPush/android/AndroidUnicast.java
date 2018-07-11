package com.phicomm.LL.umengPush.android;

import com.phicomm.LL.umengPush.AndroidNotification;

/**
 * 安卓单播类，用于向指定的device_tokens对应的设备发送信息
 * 此时的device_tokens只能包含一个token
 *
 * @author haifeng.zhou
 * @date 2018-07-09 14:53
 */
public class AndroidUnicast extends AndroidNotification {

	public AndroidUnicast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "unicast");
	}

	public void setDeviceToken(String token) throws Exception {
		setPredefinedKeyValue("device_tokens", token);
	}
}
