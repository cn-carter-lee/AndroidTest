package com.liwuso.app;

import com.pys.liwuso.bean.Age;
import com.pys.liwuso.bean.AgeList;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;
import com.pys.liwuso.bean.Product;
import com.pys.liwuso.bean.ProductList;
import com.pys.liwuso.bean.Purpose;
import com.pys.liwuso.bean.PurposeList;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppContext extends Application {
	public static final int PAGE_SIZE = 20;

	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public PersonList getPersonList(int catalog, int pageIndex,
			boolean isRefresh) throws AppException {
		PersonList list = new PersonList();
		String key = "person_list_" + catalog + "_" + pageIndex + "_"
				+ PAGE_SIZE;
		if (catalog == 0) {
			list.Add(new Person("Ů����"));
			list.Add(new Person("����"));
			list.Add(new Person("Ů������"));
			list.Add(new Person("����"));
			list.Add(new Person("Ů��"));

		} else if (catalog == 1) {
			list.Add(new Person("������"));
			list.Add(new Person("�Ϲ�"));
			list.Add(new Person("��������"));
			list.Add(new Person("�ְ�"));
			list.Add(new Person("����"));

		}

		for (int i = 0; i < 50; i++) {
			list.Add(new Person("YYYY--789"));
		}
		//
		// if(isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
		// try{
		// list = ApiClient.getNewsList(this, catalog, pageIndex, PAGE_SIZE);
		// if(list != null && pageIndex == 0){
		// Notice notice = list.getNotice();
		// list.setNotice(null);
		// list.setCacheKey(key);
		// saveObject(list, key);
		// list.setNotice(notice);
		// }
		// }catch(AppException e){
		// list = (PersonList)readObject(key);
		// if(list == null)
		// throw e;
		// }
		// } else {
		// list = (PersonList)readObject(key);
		// if(list == null)
		// list = new PersonList();
		// }

		return list;
	}

	public AgeList getAgeList(int catalog, int pageIndex, boolean isRefresh)
			throws AppException {
		AgeList list = new AgeList();
		String key = "age_list_" + catalog + "_" + pageIndex + "_"
				+ PAGE_SIZE;
	
		for (int i = 0; i < 20; i++) {
			Age age = new Age();
			age.Name = "OOOOO";
			list.Add(age);
		}
		return list;
	}

	public PurposeList getPurposeList(int catalog, int pageIndex, boolean isRefresh)
			throws AppException {
		PurposeList list = new PurposeList();
		String key = "purpose_list_" + catalog + "_" + pageIndex + "_"
				+ PAGE_SIZE;
	
		for (int i = 0; i < 20; i++) {
			Purpose purposea = new Purpose();
			purposea.Name = "PPPPPPPPPPPPPPPPPP";
			list.Add(purposea);
		}
		return list;
	}
	
	public ProductList getProductList(int catalog, int pageIndex,
			boolean isRefresh) throws AppException {
		ProductList list = new ProductList();
		String key = "product_list_" + catalog + "_" + pageIndex + "_"
				+ PAGE_SIZE;
		String[] urls = {
				"http://g.hiphotos.baidu.com/image/pic/item/42166d224f4a20a4e0b49b3e92529822730ed0a5.jpg",
				"http://www.liwuso.com/Uploads/cpc/86.jpg",
				"http://images.sports.cn/Image/2014/06/29/0832351428.jpg" };
		for (int i = 0; i < 20; i++) {
			Product product = new Product();
			product.Name = "OOOOO";
			product.Price = "1200";
			product.ImageUrl = urls[i % 3];
			list.Add(product);
		}
		return list;
	}
}
