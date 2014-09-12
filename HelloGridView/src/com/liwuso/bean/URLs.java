package com.liwuso.bean;

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

	public static String PRODUCT_IMG_URL_PREFIX = URL_API_HOST + "Uploads/";

	public final static String BASE_API_URL = URL_API_HOST + "api-";

	public final static String AGE_LIST = URL_API_HOST + "api-";
}
