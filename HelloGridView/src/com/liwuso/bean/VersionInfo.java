package com.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.liwuso.app.AppException;
import com.liwuso.app.common.StringUtils;

public class VersionInfo extends Entity {
	public final static String NODE_START = "think";
	public final static String NODE_ID = "id";
	public final static String NODE_NAME = "title";
	public final static String NODE_CONTENT = "content";
	public final static String NODE_URL = "url";

	public String Name;
	public String Content;
	public String Url;

	public static VersionInfo parse(InputStream inputStream)
			throws IOException, AppException {
		VersionInfo version = new VersionInfo();

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
					if (tag.equalsIgnoreCase(VersionInfo.NODE_ID)) {
						version.id = StringUtils.toInt(xmlParser.nextText(), 0);
					} else if (tag.equalsIgnoreCase(VersionInfo.NODE_NAME)) {
						version.Name = xmlParser.nextText();
					} else if (tag.equalsIgnoreCase(VersionInfo.NODE_CONTENT)) {
						version.Content = xmlParser.nextText();
					} else if (tag.equalsIgnoreCase(VersionInfo.NODE_URL)) {
						version.Url = xmlParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					// ���������ǩ��������Ѷ�����ӽ�������
					if (tag.equalsIgnoreCase("item")) {

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

		return version;
	}
}
