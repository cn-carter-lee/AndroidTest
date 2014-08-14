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
			list.Add(new Person(1, "Ů����"));
			list.Add(new Person(3, "Ů��"));
			list.Add(new Person(2, "Ů������"));
			list.Add(new Person(9, "СŮ��"));
			list.Add(new Person(5, "����"));
			list.Add(new Person(4, "���"));
			list.Add(new Person(35, "����"));
			list.Add(new Person(6, "����"));
			list.Add(new Person(7, "Ůͬѧ"));
			list.Add(new Person(8, "Ů�Գ���"));
			list.Add(new Person(10, "Ů��"));
			list.Add(new Person(11, "Ů��ʦ"));
			list.Add(new Person(12, "Ů�쵼"));
			list.Add(new Person(13, "Ůͬ��"));
			list.Add(new Person(14, "Ů������"));
			list.Add(new Person(15, "Ů�ͻ�"));
			list.Add(new Person(16, "Ů-�����"));
			list.Add(new Person(17, "����Ů��"));
		} else if (catalog == 1) {
			list.Add(new Person(18, "������"));
			list.Add(new Person(21, "����"));
			list.Add(new Person(19, "��������"));
			list.Add(new Person(20, "�Ϲ�"));
			list.Add(new Person(22, "�ְ�"));
			list.Add(new Person(23, "���"));
			list.Add(new Person(24, "���쵼"));
			list.Add(new Person(25, "��ͬѧ"));
			list.Add(new Person(26, "���Գ���"));
			list.Add(new Person(27, "С�к�"));
			list.Add(new Person(28, "��������"));
			list.Add(new Person(29, "��ͬ��"));
			list.Add(new Person(30, "����"));
			list.Add(new Person(31, "����ʦ"));
			list.Add(new Person(32, "�пͻ�"));
			list.Add(new Person(33, "��-�����"));
			list.Add(new Person(34, "��������"));
			list.Add(new Person(36, "�ܵ�"));
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
		String key = "age_list_" + catalog + "_" + pageIndex + "_" + PAGE_SIZE;
		for (int i = 0; i < 20; i++) {
			Age age = new Age();
			age.Name = "OOOOO";
			list.Add(age);
		}
		return list;
	}

	public PurposeList getPurposeList(int catalog, int pageIndex,
			boolean isRefresh) throws AppException {
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

	public ProductList getFavoriteProductList(int catalog, int pageIndex,
			boolean isRefresh) throws AppException {
		ProductList list = new ProductList();
		String key = "product_list_" + catalog + "_" + pageIndex + "_"
				+ PAGE_SIZE;
		String[] urls = {s
				"http://g.hiphotos.baidu.com/image/pic/item/42166d224f4a20a4e0b49b3e92529822730ed0a5.jpg",
				"http://img10.360buyimg.com/n1/g16/M00/02/11/rBEbRlNsQowIAAAAAAFkrAtZTckAAAfyQM6YFsAAWTE992.jpg",
				"http://images.sports.cn/Image/2014/06/29/0832351428.jpg" };
		for (int i = 0; i < 20; i++) {
			Product product = new Product();
			product.Name = "FFFFFFFF";
			product.Price = "1200";
			product.ImageUrl = urls[i % 3];
			list.Add(product);
		}
		return list;
	}

}
