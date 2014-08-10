package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.liwuso.app.AppException;

public class AgeList {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int ageCount;
	private List<Age> agelist = new ArrayList<Age>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(Age age) {
		agelist.add(age);
	}

	public int getProductCount() {
		return agelist.size();
	}

	public List<Age> getAgeList() {
		return agelist;
	}

	public static AgeList parse(InputStream inputStream)
			throws IOException, AppException {
		AgeList agelist = new AgeList();

		return agelist;
	}
}
