package com.phicomm.LL;

import com.phicomm.LL.umengPush.AndroidNotification;
import com.phicomm.LL.umengPush.PushClient;
import com.phicomm.LL.umengPush.android.AndroidBroadcast;
import com.phicomm.LL.umengPush.android.AndroidUnicast;
import com.phicomm.LL.umengPush.ios.IOSBroadcast;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author haifeng.zhou
 * @date 2018-07-09 14:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UmengPushTest {

	private String appkey = "5b3d8de0f29d981ab1000026";
	private String appMasterSecret = "dkavfacpj0afb7sn5pvtfj3yvxlum1gh";
	private String timestamp = null;
	private PushClient client = new PushClient();

	@Test
	public void sendAndroidBroadcast() throws Exception {
//		AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
//
//		broadcast.setTicker("Android broadcast ticker");
//		broadcast.setTitle("中文的title");
//		broadcast.setText("测试广播程序是否正常运行");
//		broadcast.goAppAfterOpen();
//		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
//		broadcast.setProductionMode(Boolean.FALSE);
//		broadcast.setCustomField(" 当display_type=message时, 必填custom字段");
//		broadcast.setExtraField("test", "helloworld");
//
//		client.send(broadcast);
	}

	@Test
	public void sendAndroidUnicast() throws Exception {
//		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
//		unicast.setDeviceToken("Aq7Zq9u69scnpJzt4TBhWm-3HwtesFxR87xCxLZlUTNg");
//
//		unicast.setTicker("Android unicast ticker");
//		unicast.setTitle("中文的title");
//		unicast.setText("Android unicast text");
//		unicast.goAppAfterOpen();
//		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
//		unicast.setProductionMode();
//		unicast.setExtraField("test", "helloworld");
//		client.send(unicast);
	}
}
