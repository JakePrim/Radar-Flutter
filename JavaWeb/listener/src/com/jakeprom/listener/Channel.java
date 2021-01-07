package com.jakeprom.listener;

public class Channel {

	private String channelName;
	
	private String url;
	
	
	public Channel(String channelName, String url) {
		this.channelName = channelName;
		this.url = url;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
