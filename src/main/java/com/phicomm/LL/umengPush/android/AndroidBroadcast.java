package com.phicomm.LL.umengPush.android;

import com.phicomm.LL.umengPush.AndroidNotification;

/**
 * 安卓广播类，实现向所有安卓用户广播
 *
 * @author haifeng.zhou
 * @date 2018-07-06 16:46
 */
public class AndroidBroadcast extends AndroidNotification {

	public AndroidBroadcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");
	}
}
