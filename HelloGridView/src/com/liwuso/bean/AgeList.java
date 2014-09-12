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

public class AgeList extends Entity {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int ageCount;
	private Person person;
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

	public static AgeList parseAge(InputStream inputStream) throws IOException,
			AppException {
		AgeList agelist = new AgeList();
		Age age = null;

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
					if (tag.equalsIgnoreCase(Age.NODE_START)) {
						age = new Age();
					} else if (age != null) {
						if (tag.equalsIgnoreCase(Age.NODE_ID)) {
							age.id = StringUtils.toInt(xmlParser.nextText(), 0);
						} else if (tag.equalsIgnoreCase(Age.NODE_NAME)) {
							age.Name = xmlParser.nextText();
						}
					}

					break;
				case XmlPullParser.END_TAG:
					// ���������ǩ��������Ѷ�����ӽ�������
					if (tag.equalsIgnoreCase("item") && age != null) {
						agelist.Add(age);
						age = null;
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

		return agelist;
	}
}
