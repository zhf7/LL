package com.phicomm.LL.umengPush.android;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.LL.umengPush.AndroidNotification;

/**
 * 安卓组播类，向满足特定条件的设备集合发送消息，例如: "特定版本"、"特定地域"等
 * 按照filter筛选用户群, 请参照filter参数
 *
 * @author haifeng.zhou
 * @date 2018-07-09 15:51
 */
public class AndroidGroupcast extends AndroidNotification {

	public AndroidGroupcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws Exception {
		setPredefinedKeyValue("filter", filter);
	}

}
