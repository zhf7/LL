package com.phicomm.LL.Umeng;

import java.io.Serializable;
import java.util.HashMap;

public class PushBody implements Serializable {
	private static final long serialVersionUID = -8395605393463858067L;
	private String noticeDesc;                        //通知栏的正文
	private String noticeTitle;                        //通知栏的标题
	private String ticker;                            //push的滚动文案
	private String toneUrl;                            //提示音的文件路径和文件名
	private HashMap<String, String> params;        //额外参数
	private boolean isOpenTone = true;            //是否开启提示音, 默认开启
	private boolean isOpenVibration = true;        //是否开启振动，默认开启
	private boolean isOpenLight = true;                //是否开启呼吸灯
	private String token;                            //
	private String appIcon;
	private int pushType;                            //push的类型
	//通知栏的类型，因为可能多种pushType对应的都是同一类的noticeType,
	//比如说任务的不同阶段,pushType不一样，但是noticeType是一样的
	private int noticeType;
	//应用最小化时显示的数量, 若为 null，则覆盖原来的数量
	private Integer badgeNum;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToneUrl() {
		return toneUrl;
	}

	public void setToneUrl(String toneUrl) {
		this.toneUrl = toneUrl;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public boolean isOpenTone() {
		return isOpenTone;
	}

	public void setOpenTone(boolean isOpenTone) {
		this.isOpenTone = isOpenTone;
	}

	public boolean isOpenVibration() {
		return isOpenVibration;
	}

	public void setOpenVibration(boolean isOpenVibration) {
		this.isOpenVibration = isOpenVibration;
	}

	public String getNoticeDesc() {
		return noticeDesc;
	}

	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public boolean isOpenLight() {
		return isOpenLight;
	}

	public void setOpenLight(boolean isOpenLight) {
		this.isOpenLight = isOpenLight;
	}

	public String toPrint() {
		return "desc=" + noticeDesc + ",title=" + noticeTitle + ",ticker=" + ticker
				+ ",params=" + params + ",token=" + token + ",pushType=" + pushType
				+ ",noticeType=" + noticeType;
	}

	public Integer getBadgeNum() {
		return badgeNum;
	}

	public void setBadgeNum(Integer badgeNum) {
		this.badgeNum = badgeNum;
	}


}
