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
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
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
					// 如果遇到标签结束，则把对象添加进集合中
					if (tag.equalsIgnoreCase("item")) {

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

		return version;
	}
}
