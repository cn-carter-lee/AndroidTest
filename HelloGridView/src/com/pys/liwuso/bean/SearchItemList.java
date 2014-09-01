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
	// 该分类总数量
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
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
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
							searchItem.ImageUrl = URLs.PRODUCT_IMG_URL_PREFIX
									+ xmlParser.nextText();
						}
					}

					break;
				case XmlPullParser.END_TAG:
					// 如果遇到标签结束，则把对象添加进集合中
					if (tag.equalsIgnoreCase("item") && searchItem != null) {
						searchItemList.Add(searchItem);
						searchItem = null;
					}
					break;
				}
				// 如果xml没有结束，则导航到下一个节点
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
