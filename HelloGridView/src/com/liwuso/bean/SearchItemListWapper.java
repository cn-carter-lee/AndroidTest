package com.liwuso.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchItemListWapper extends Entity {

	private int pageSize;
	public  int searchItemCount;

	public int catalog;
	public int totalCount;

	private List<SearchItemWapper> searchItemList = new ArrayList<SearchItemWapper>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(SearchItemWapper product) {
		searchItemList.add(product);
	}

	public int getProductCount() {
		return searchItemList.size();
	}

	public List<SearchItemWapper> getSearchItemList() {
		return searchItemList;
	}

}
