package com.pys.liwuso.bean;

import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLs {
	public final static String HOST = "www.liwuso.com";
														
	public final static String HTTP = "http://";
	

	private final static String URL_SPLITTER = "/";
	private final static String URL_UNDERLINE = "_";

	private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;

	public final static String AGE_LIST = URL_API_HOST
			+ "api/age_list";
}
