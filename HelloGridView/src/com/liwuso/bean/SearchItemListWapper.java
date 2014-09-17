package com.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.liwuso.app.AppException;
import com.liwuso.app.common.StringUtils;

public class SearchItemListWapper extends Entity {

	private int pageSize;
	private int SearchItemCount;

	public int catalog;
	public int totalCount;

	private List<SearchItemWapper> searchItemList = new ArrayList<SearchItemWapper>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(SearchItemWapper product) {
		searchItemList.add(product);
	}

	public int getProductCount() {
		return searchItemList.size();
	}

	public List<SearchItemWapper> getSearchItemList() {
		return searchItemList;
	}

}
