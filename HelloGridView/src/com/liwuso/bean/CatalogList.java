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

public class CatalogList extends Entity {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int catalogCount;

	public int totalCount;

	private List<Catalog> cataloglist = new ArrayList<Catalog>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(Catalog catalog) {
		cataloglist.add(catalog);
	}

	public int getProductCount() {
		return cataloglist.size();
	}

	public List<Catalog> getCatalogList() {
		return cataloglist;
	}

	public static CatalogList parse(InputStream inputStream)
			throws IOException, AppException {
		CatalogList catalogList = new CatalogList();
		Catalog defaultCatalog = new Catalog();
		defaultCatalog.id = 0;
		defaultCatalog.Name = "ȫ��";
		catalogList.Add(defaultCatalog);
		Catalog catalog = null;

		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(inputStream, UTF8);
			// ��ý��������¼���������п�ʼ�ĵ��������ĵ�����ʼ��ǩ��������ǩ���ı��ȵ��¼���
			int evtType = xmlParser.getEventType();
			// һֱѭ����ֱ���ĵ�����
			while (evtType != XmlPullParser.END_DOCUMENT) {
				String tag = xmlParser.getName();
				switch (evtType) {
				case XmlPullParser.START_TAG:
					if (tag.equalsIgnoreCase(Catalog.NODE_START)) {
						catalog = new Catalog();
					} else if (catalog != null) {
						if (tag.equalsIgnoreCase(Catalog.NODE_ID)) {
							catalog.id = StringUtils.toInt(
									xmlParser.nextText(), 0);
						} else if (tag.equalsIgnoreCase(Catalog.NODE_NAME)) {
							catalog.Name = xmlParser.nextText();
						}
					}

					break;
				case XmlPullParser.END_TAG:
					// ���������ǩ��������Ѷ�����ӽ�������
					if (tag.equalsIgnoreCase("item") && catalog != null) {
						catalogList.Add(catalog);
						catalog = null;
					}
					break;
				}
				// ���xmlû�н������򵼺�����һ���ڵ�
				int a = xmlParser.next();
				evtType = a;
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw AppException.xml(e);
		} finally {
			inputStream.close();
		}

		return catalogList;
	}
}
