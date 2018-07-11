package com.phicomm.LL.umengPush.android;

import com.phicomm.LL.umengPush.AndroidNotification;

/**
 * 开发者通过自有的alias进行推送，可以针对单个或者一批alias进行推送，也可以将alias存放到文件进行发送。
 * 通过alias进行推送，包括以下两种case:
 * alias: 对单个或者多个alias进行推送
 * file_id: 将alias存放到文件后，根据file_id来推送
 *
 * @author haifeng.zhou
 * @date 2018-07-09 16:12
 */
public class AndroidCustomizedcast extends AndroidNotification {

	public AndroidCustomizedcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "customizedcast");
	}

	public void setAlias(String alias, String aliasType) throws Exception {
		setPredefinedKeyValue("alias", alias);
		setPredefinedKeyValue("alias_type", aliasType);
	}

	public void setFileId(String fileId, String aliasType) throws Exception {
		setPredefinedKeyValue("file_id", fileId);
		setPredefinedKeyValue("alias_type", aliasType);
	}
}
