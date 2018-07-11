package com.phicomm.LL.umengPush.android;

import com.phicomm.LL.umengPush.AndroidNotification;

/**
 * 安卓列播类，用于向指定的device_tokens对应的设备发送信息
 * device_tokens中的token不超过500个(设备不超过500个), 以英文逗号分隔
 *
 * @author haifeng.zhou
 * @date 2018-07-09 15:39
 */
public class AndroidListcast extends AndroidNotification {

	public AndroidListcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
	}

	public void setDeviceTokens(String tokens) throws Exception {
		setPredefinedKeyValue("device_tokens", tokens);
	}
}
