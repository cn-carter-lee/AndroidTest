package com.pys.liwuso.bean;

public class Product extends Entity {

	public final static String NODE_TOTAL_COUNT = "total_count";
	public final static String NODE_START = "item";
	public final static String NODE_ID = "id";
	public final static String NODE_URL = "url";
	public final static String NODE_NAME = "name";
	public final static String NODE_TAGS = "tags";
	public final static String NODE_PRICE = "price";
	public final static String NODE_LIKED = "liked";
	public final static String NODE_IMG = "img";

	public String Name;
	public String Tags;
	public String Price;
	public String Url;
	public String ImageUrl;
	public String Liked;

	public boolean favorite;
}
