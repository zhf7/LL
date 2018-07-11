package com.phicomm.LL.umengPush.ios;

import com.phicomm.LL.umengPush.IOSNotification;

/**
 * IOS单播类，用于向指定的device_tokens对应的设备发送信息
 * 此时的device_tokens只能包含一个token
 *
 * @author haifeng.zhou
 * @date 2018-07-09 16:55
 */
public class IOSUnicast extends IOSNotification {

	public IOSUnicast(String appkey,String appMasterSecret) throws Exception{
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "unicast");
	}

	public void setDeviceToken(String token) throws Exception {
		setPredefinedKeyValue("device_tokens", token);
	}

}
