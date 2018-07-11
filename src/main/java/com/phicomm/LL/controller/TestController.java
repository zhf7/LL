package com.phicomm.LL.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author haifeng.zhou
 * @date 2018-06-27 16:50
 */
@RestController
public class TestController {

	@RequestMapping("/hello")
	public String hello() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return "<h2>" + df.format(now) + "</h2><br/>" + new Date();
	}

//	@RequestMapping("/")
//	public void get
}
