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

public class AimList extends Entity {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize = 0;
	private int purposeCount;
	private List<Aim> purposelist = new ArrayList<Aim>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(Aim purpose) {
		purposelist.add(purpose);
	}

	public int getProductCount() {
		return purposelist.size();
	}

	public List<Aim> getPurposeList() {
		return purposelist;
	}

	public static AimList parse(InputStream inputStream) throws IOException,
			AppException {
		AimList purposelist = new AimList();
		Aim purpose = null;

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
					if (tag.equalsIgnoreCase(Aim.NODE_START)) {
						purpose = new Aim();
					} else if (purpose != null) {
						if (tag.equalsIgnoreCase(Aim.NODE_ID)) {
							purpose.id = StringUtils.toInt(
									xmlParser.nextText(), 0);
						} else if (tag.equalsIgnoreCase(Aim.NODE_NAME)) {
							purpose.Name = xmlParser.nextText();
						}
					}

					break;
				case XmlPullParser.END_TAG:
					// ���������ǩ��������Ѷ�����ӽ�������
					if (tag.equalsIgnoreCase("item") && purpose != null) {
						purposelist.Add(purpose);
						purpose = null;
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

		return purposelist;
	}
}
