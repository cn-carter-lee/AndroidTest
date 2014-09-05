package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.liwuso.app.AppException;
import com.liwuso.app.common.StringUtils;

public class SearchItemList extends Entity {

	private int pageSize;
	private int SearchItemCount;

	public int catalog;
	public int totalCount;

	private List<SearchItem> searchItemList = new ArrayList<SearchItem>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(SearchItem product) {
		searchItemList.add(product);
	}

	public int getProductCount() {
		return searchItemList.size();
	}

	public List<SearchItem> getSearchItemList() {
		return searchItemList;
	}

	public static SearchItemList parse(InputStream inputStream)
			throws IOException, AppException {
		SearchItemList searchItemList = new SearchItemList();
		SearchItem searchItem = null;

		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(inputStream, UTF8);		
			int evtType = xmlParser.getEventType();		
			while (evtType != XmlPullParser.END_DOCUMENT) {
				String tag = xmlParser.getName();
				switch (evtType) {
				case XmlPullParser.START_TAG:
					if (tag.equalsIgnoreCase(Product.NODE_TOTAL_COUNT)) {
						searchItemList.totalCount = StringUtils.toInt(
								xmlParser.nextText(), 0);
					} else if (tag.equalsIgnoreCase(Product.NODE_START)) {
						searchItem = new SearchItem();
					} else if (searchItem != null) {
						if (tag.equalsIgnoreCase(Product.NODE_ID)) {
							searchItem.id = StringUtils.toInt(
									xmlParser.nextText(), 0);
						} else if (tag.equalsIgnoreCase(Product.NODE_NAME)) {
							searchItem.Name = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase(Product.NODE_URL)) {
							searchItem.Url = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase(Product.NODE_IMG)) {
							searchItem.ImageUrl = xmlParser.nextText();
						} 
					}
					break;
				case XmlPullParser.END_TAG:
					if (tag.equalsIgnoreCase("item") && searchItem != null) {
						searchItemList.Add(searchItem);
						searchItem = null;
					}
					break;
				}
				int a = xmlParser.next();
				evtType = a;
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw AppException.xml(e);
		} finally {
			inputStream.close();
		}
		return searchItemList;
	}
}
