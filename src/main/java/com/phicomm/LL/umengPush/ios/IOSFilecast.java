package com.phicomm.LL.umengPush.ios;

import com.phicomm.LL.umengPush.IOSNotification;

/**
 * 开发者将批量的device_token或者alias存放到文件，通过文件ID进行消息发送。
 *
 * @author haifeng.zhou
 * @date 2018-07-09 17:00
 */
public class IOSFilecast extends IOSNotification {

	public IOSFilecast(String appkey,String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "filecast");
	}

	public void setFileId(String fileId) throws Exception {
		setPredefinedKeyValue("file_id", fileId);
	}
}
