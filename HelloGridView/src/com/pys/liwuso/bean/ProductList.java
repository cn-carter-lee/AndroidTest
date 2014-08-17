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

public class ProductList extends Entity {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int productCount;

	// �÷���������
	public int totalCount;

	private List<Product> productlist = new ArrayList<Product>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(Product product) {
		productlist.add(product);
	}

	public int getProductCount() {
		return productlist.size();
	}

	public List<Product> getProductList() {
		return productlist;
	}

	public static ProductList parse(InputStream inputStream)
			throws IOException, AppException {
		ProductList productList = new ProductList();
		Product product = null;

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
					if (tag.equalsIgnoreCase(Product.NODE_TOTAL_COUNT)) {
						productList.totalCount = StringUtils.toInt(
								xmlParser.nextText(), 0);
					} else if (tag.equalsIgnoreCase(Product.NODE_START)) {
						product = new Product();
					} else if (product != null) {
						if (tag.equalsIgnoreCase(Product.NODE_ID)) {
							product.id = StringUtils.toInt(
									xmlParser.nextText(), 0);
						} else if (tag.equalsIgnoreCase(Product.NODE_NAME)) {
							product.Name = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase(Product.NODE_PRICE)) {
							product.Price = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase(Product.NODE_URL)) {
							product.Url = xmlParser.nextText();
						} else if (tag.equalsIgnoreCase(Product.NODE_IMG)) {
							product.ImageUrl = URLs.PRODUCT_IMG_URL_PREFIX
									+ xmlParser.nextText();
						} else if (tag.equalsIgnoreCase(Product.NODE_LIKED)) {
							product.Liked = xmlParser.nextText();
						}
					}

					break;
				case XmlPullParser.END_TAG:
					// ���������ǩ��������Ѷ�����ӽ�������
					if (tag.equalsIgnoreCase("item") && product != null) {
						productList.Add(product);
						product = null;
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

		return productList;
	}
}
