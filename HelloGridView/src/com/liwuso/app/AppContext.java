package com.liwuso.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.liwuso.net.ApiClient;
import com.pys.liwuso.bean.Age;
import com.pys.liwuso.bean.AgeList;
import com.pys.liwuso.bean.MixedPerson;
import com.pys.liwuso.bean.MixedPersonList;
import com.pys.liwuso.bean.Notice;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;
import com.pys.liwuso.bean.Product;
import com.pys.liwuso.bean.ProductList;
import com.pys.liwuso.bean.Aim;
import com.pys.liwuso.bean.AimList;
import com.pys.liwuso.bean.SearchCatalog;
import com.pys.liwuso.bean.SearchItem;
import com.pys.liwuso.bean.SearchItemList;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	private boolean isReadDataCache(String cachefile) {
		return readObject(cachefile) != null;
	}

	public Serializable readObject(String file) {
		if (!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable) ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			// 反序列化失败 - 删除缓存文件
			if (e instanceof InvalidClassException) {
				File data = getFileStreamPath(file);
				data.delete();
			}
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	private boolean isExistDataCache(String cachefile) {
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	private PersonList getMalePersonList() {
		int sex = 2;
		PersonList list = new PersonList();
		list.Add(new Person(18, "男朋友", sex));
		list.Add(new Person(21, "男生", sex));
		list.Add(new Person(19, "男性朋友", sex));
		list.Add(new Person(20, "老公", sex));
		list.Add(new Person(22, "爸爸", sex));
		list.Add(new Person(25, "男同学", sex));
		list.Add(new Person(23, "哥哥", sex));
		list.Add(new Person(36, "弟弟", sex));
		list.Add(new Person(26, "男长辈", sex));
		list.Add(new Person(31, "男老师", sex));
		list.Add(new Person(27, "孩子", sex));
		list.Add(new Person(30, "儿子", sex));
		list.Add(new Person(29, "男同事", sex));
		list.Add(new Person(24, "男领导", sex));
		list.Add(new Person(28, "老人", sex));
		list.Add(new Person(32, "男客户", sex));
		list.Add(new Person(33, "外国人", sex));
		list.Add(new Person(34, "其他人", sex));
		return list;
	}

	private PersonList getFemalePersonList() {
		int sex = 1;
		PersonList list = new PersonList();
		list.Add(new Person(1, "女朋友", sex));
		list.Add(new Person(3, "女生", sex));
		list.Add(new Person(2, "女性朋友", sex));
		list.Add(new Person(5, "老婆", sex));
		list.Add(new Person(6, "妈妈", sex));
		list.Add(new Person(7, "女同学", sex));
		list.Add(new Person(4, "姐姐", sex));
		list.Add(new Person(35, "妹妹", sex));
		list.Add(new Person(8, "女长辈", sex));
		list.Add(new Person(11, "女老师", sex));
		list.Add(new Person(0, "孩子", sex));
		list.Add(new Person(10, "女儿", sex));
		list.Add(new Person(13, "女同事", sex));
		list.Add(new Person(12, "女领导", sex));
		list.Add(new Person(14, "老人", sex));
		list.Add(new Person(15, "女客户", sex));
		list.Add(new Person(16, "外国人", sex));
		list.Add(new Person(17, "其他人", sex));
		return list;
	}

	public PersonList getPersonList(int sexId, boolean isRefresh)
			throws AppException {
		PersonList list = new PersonList();
		String key = "person_list_" + sexId;
		if (sexId == 0)
			list = getFemalePersonList();
		else if (sexId == 1)
			list = getMalePersonList();
		return list;
	}

	public MixedPersonList getMixedPersonList(boolean isRefresh)
			throws AppException {
		PersonList femalelist = getFemalePersonList();
		PersonList malelist = getMalePersonList();
		MixedPersonList list = new MixedPersonList();
		for (int i = 0; i < femalelist.getPersonCount()
				&& i < malelist.getPersonCount(); i++) {
			list.Add(new MixedPerson(femalelist.getPersonList().get(i),
					malelist.getPersonList().get(i)));
		}

		return list;
	}

	public AgeList getAgeList(int catalog, int personId, int pageIndex,
			boolean isRefresh) throws AppException {
		String key = "age_list_" + catalog + "_" + pageIndex + "_" + PAGE_SIZE;
		AgeList list;
		// if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
		try {
			list = ApiClient.getAgeList(this, catalog, personId, pageIndex,
					PAGE_SIZE);
			if (list != null && pageIndex == 0) {
				// Notice notice = list.getNotice();
				// list.setNotice(null);
				list.setCacheKey(key);
				saveObject(list, key);
				// list.setNotice(notice);
			}
		} catch (AppException e) {
			list = (AgeList) readObject(key);
			if (list == null)
				throw e;
		}
		// } else {
		// list = (AgeList) readObject(key);
		// if (list == null)
		// list = new AgeList();
		// }

		return list;
	}

	public AimList getAimList(int sexId, int personId, int ageId,
			boolean isRefresh) throws AppException {
		AimList aimList = new AimList();
		String key = "purpose_list_" + sexId + "_" + "_" + PAGE_SIZE;

		try {
			aimList = ApiClient.getPurposeList(this, sexId, personId, ageId);
			if (aimList != null) {
				// Notice notice = list.getNotice();
				// list.setNotice(null);
				aimList.setCacheKey(key);
				saveObject(aimList, key);
				// list.setNotice(notice);
			}
		} catch (AppException e) {
			aimList = (AimList) readObject(key);
			if (aimList == null)
				throw e;
		}
		return aimList;
	}

	public ProductList getProductList(int sexId, int personId, int ageId,
			int aimId, int pageIndex, boolean isRefresh) throws AppException {
		ProductList productList = new ProductList();
		String key = "product_list_" + sexId + "_" + personId + "_" + ageId
				+ "_" + aimId + "_" + "_" + pageIndex + "_" + PAGE_SIZE;
		try {
			productList = ApiClient.getProductList(this, sexId, personId,
					ageId, aimId, pageIndex);
			if (productList != null) {
				// Notice notice = list.getNotice();
				// list.setNotice(null);
				productList.setCacheKey(key);
				saveObject(productList, key);
				// list.setNotice(notice);
			}
		} catch (AppException e) {
			productList = (ProductList) readObject(key);
			if (productList == null)
				throw e;
		}

		return productList;
	}

	public List<SearchCatalog> getSearchCatalog() {
		List<SearchCatalog> listSearchCatalog = new ArrayList<SearchCatalog>();
		listSearchCatalog.add(new SearchCatalog(-1, "全部"));
		listSearchCatalog.add(new SearchCatalog(1, "创意"));
		listSearchCatalog.add(new SearchCatalog(1, "经典"));
		listSearchCatalog.add(new SearchCatalog(1, "实用"));
		listSearchCatalog.add(new SearchCatalog(1, "健康"));

		return listSearchCatalog;
	}

	public SearchItemList getSearchItemList(int catalogId, int pageIndex,
			boolean isRefresh) throws AppException {
		SearchItemList searchItemList = new SearchItemList();
		String key = "search_list_" + catalogId + "_" + pageIndex + "_"
				+ PAGE_SIZE;

		for (int i = 0; i < 20; i++) {
			SearchItem item = new SearchItem();
			item.ImageUrl = "http://www.liwuso.com/Uploads/cpc/894b.jpg";
			item.Name = "3D浪漫水晶";
			item.Url = "http://redirect.simba.taobao.com/rd?&f=http%3A%2F%2Fai.taobao.com%2Fauction%2Fedetail.htm%3Fe%3DGtKCQq6%252FBMLebLdhAWchHB1%252BqwFLTnE8TCbL1Om%252BR1KLltG5xFicObalFqTViQTOxN35oEuRTJdyHrcqZgjbAe%252FrEZy%252FN1Z0cymvewz5SETed6w8MirRX%252BIZWR1bMnHu%26ptype%3D100011%26rType%3D1%26from%3Dgoldenlink%26eid%3D&k=5ccfdb950740ca16&p=mm_31516171_5574276_21616173&pvid=1409641450_2199802r2_475131414&posid=&b=display_1_625_0_0_0&w=unionapijs&c=un";
			searchItemList.Add(item);
		}

		// try {
		// searchItemList = ApiClient.getSearchItemList(catalogId, pageIndex);
		// if (searchItemList != null) {
		// searchItemList.setCacheKey(key);
		// saveObject(searchItemList, key);
		// }
		// } catch (AppException e) {
		// searchItemList = (SearchItemList) readObject(key);
		// if (searchItemList == null)
		// throw e;
		// }

		return searchItemList;
	}

	public ProductList getFavoriteList(String productIds, int pageIndex,
			boolean isRefresh) throws AppException {
		ProductList productList = new ProductList();
		String key = "favorite_list_" + pageIndex + "_" + PAGE_SIZE;
		try {
			productList = ApiClient
					.getFavoriteList(this, productIds, pageIndex);
			if (productList != null) {
				productList.setCacheKey(key);
				saveObject(productList, key);
			}
		} catch (AppException e) {
			productList = (ProductList) readObject(key);
			if (productList == null)
				throw e;
		}
		return productList;
	}

}
