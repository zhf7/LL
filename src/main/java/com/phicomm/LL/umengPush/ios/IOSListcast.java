package com.phicomm.LL.umengPush.ios;

import com.phicomm.LL.umengPush.IOSNotification;

/**
 * IOS列播类，用于向指定的device_tokens对应的设备发送信息
 * device_tokens中的token不超过500个(设备不超过500个), 以英文逗号分隔
 *
 * @author haifeng.zhou
 * @date 2018-07-09 16:56
 */
public class IOSListcast extends IOSNotification {

	public IOSListcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
	}

	public void setDeviceTokens(String tokens) throws Exception {
		setPredefinedKeyValue("device_tokens", tokens);
	}
}
