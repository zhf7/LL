package com.phicomm.LL.Umeng.controller;

import com.phicomm.LL.Umeng.UmengPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author haifeng.zhou
 * @date 2018-07-05 11:06
 */
//@RestController
//@RequestMapping("/umeng")
public class UmengPushContoller {

	@Autowired
	UmengPush umengPush;

	@GetMapping("/pushAll")
	public String umengPush(String title, String desc) {
		LocalDate now = LocalDate.now();
		System.out.println(now.toString() + "  接收到推送请求！");
		System.out.println("title: " + title + ", desc: " + desc);
		String r = umengPush.push(title, desc);
		System.out.println(r);
		return r;
	}
}
