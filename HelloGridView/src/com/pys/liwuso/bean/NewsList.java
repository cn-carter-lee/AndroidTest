package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.liwuso.app.AppException;

public class NewsList {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int newsCount;
	private List<Product> newslist = new ArrayList<Product>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getNewsCount() {
		return newsCount;
	}

	public List<Product> getNewslist() {
		return newslist;
	}

	public static NewsList parse(InputStream inputStream) throws IOException,
			AppException {
		NewsList newslist = new NewsList();

		return newslist;
	}
}
