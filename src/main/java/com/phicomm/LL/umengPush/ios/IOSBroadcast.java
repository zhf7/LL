package com.phicomm.LL.umengPush.ios;

import com.phicomm.LL.umengPush.IOSNotification;

/**
 * iOS广播类，实现向所有iOS用户广播
 *
 * @author haifeng.zhou
 * @date 2018-07-09 14:33
 */
public class IOSBroadcast extends IOSNotification {

	public IOSBroadcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");
	}
}
