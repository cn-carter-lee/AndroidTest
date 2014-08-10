package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.liwuso.app.AppException;

public class PurposeList {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int purposeCount;
	private List<Purpose> purposelist = new ArrayList<Purpose>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(Purpose purpose) {
		purposelist.add(purpose);
	}

	public int getProductCount() {
		return purposelist.size();
	}

	public List<Purpose> getPurposeList() {
		return purposelist;
	}

	public static PurposeList parse(InputStream inputStream)
			throws IOException, AppException {
		PurposeList purposelist = new PurposeList();

		return purposelist;
	}
}
