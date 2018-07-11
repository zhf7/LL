package com.phicomm.LL.controller;

import com.phicomm.LL.umengPush.AndroidNotification;
import com.phicomm.LL.umengPush.PushClient;
import com.phicomm.LL.umengPush.android.AndroidBroadcast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haifeng.zhou
 * @date 2018-07-09 15:02
 */
@RestController
@RequestMapping("/umengPush")
public class UmengPushController {

	@Value("${umeng.androidAppKey}")
	private String appkey;

	@Value("${umeng.androidMasterSecret}")
	private String appMasterSecret;

	private PushClient client = new PushClient();

	@GetMapping("/broadcast")
	public String pushBroadcast(String ticker, String title, String text/*, String displayType, String custom*/) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);

		broadcast.setTicker(ticker);
		broadcast.setTitle(title);
		broadcast.setText(text);
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		broadcast.setProductionMode(Boolean.FALSE);
//		broadcast.setCustomField(custom);
		broadcast.setExtraField("test", "helloworld");

		Boolean result = client.send(broadcast);
		return result.toString();
	}
}
