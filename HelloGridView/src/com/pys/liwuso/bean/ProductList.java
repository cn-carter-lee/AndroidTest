package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.liwuso.app.AppException;

public class ProductList {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize;
	private int productCount;
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
		ProductList newslist = new ProductList();

		return newslist;
	}
}
