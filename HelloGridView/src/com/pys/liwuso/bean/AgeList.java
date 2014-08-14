package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.liwuso.app.AppException;

public class AgeList extends Entity{

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

	public static AgeList parse(InputStream inputStream) throws IOException,
			AppException {
		AgeList agelist = new AgeList();

		return agelist;
	}
}
