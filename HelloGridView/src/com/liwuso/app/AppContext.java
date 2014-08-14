package com.liwuso.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.liwuso.net.ApiClient;
import com.pys.liwuso.bean.Age;
import com.pys.liwuso.bean.AgeList;
import com.pys.liwuso.bean.Notice;
import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;
import com.pys.liwuso.bean.Product;
import com.pys.liwuso.bean.ProductList;
import com.pys.liwuso.bean.Purpose;
import com.pys.liwuso.bean.PurposeList;

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

	public PersonList getPersonList(int catalog, int pageIndex,
			boolean isRefresh) throws AppException {
		PersonList list = new PersonList();
		String key = "person_list_" + catalog + "_" + pageIndex + "_"
				+ PAGE_SIZE;
		if (catalog == 0) {
			list.Add(new Person(1, "女朋友"));
			list.Add(new Person(3, "女生"));
			list.Add(new Person(2, "女性朋友"));
			list.Add(new Person(9, "小女孩"));
			list.Add(new Person(5, "老婆"));
			list.Add(new Person(4, "姐姐"));
			list.Add(new Person(35, "妹妹"));
			list.Add(new Person(6, "妈妈"));
			list.Add(new Person(7, "女同学"));
			list.Add(new Person(8, "女性长辈"));
			list.Add(new Person(10, "女儿"));
			list.Add(new Person(11, "女老师"));
			list.Add(new Person(12, "女领导"));
			list.Add(new Person(13, "女同事"));
			list.Add(new Person(14, "女性老人"));
			list.Add(new Person(15, "女客户"));
			list.Add(new Person(16, "女-外国人"));
			list.Add(new Person(17, "其他女性"));
		} else if (catalog == 1) {
			list.Add(new Person(18, "男朋友"));
			list.Add(new Person(21, "男生"));
			list.Add(new Person(19, "男性朋友"));
			list.Add(new Person(20, "老公"));
			list.Add(new Person(22, "爸爸"));
			list.Add(new Person(23, "哥哥"));
			list.Add(new Person(24, "男领导"));
			list.Add(new Person(25, "男同学"));
			list.Add(new Person(26, "男性长辈"));
			list.Add(new Person(27, "小男孩"));
			list.Add(new Person(28, "男性老人"));
			list.Add(new Person(29, "男同事"));
			list.Add(new Person(30, "儿子"));
			list.Add(new Person(31, "男老师"));
			list.Add(new Person(32, "男客户"));
			list.Add(new Person(33, "男-外国人"));
			list.Add(new Person(34, "其他男性"));
			list.Add(new Person(36, "弟弟"));
		}

		return list;
	}

	public AgeList getAgeList(int catalog, int pageIndex, boolean isRefresh)
			throws AppException {
		String key = "age_list_" + catalog + "_" + pageIndex + "_" + PAGE_SIZE;
		AgeList list;
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = ApiClient
						.getAgeList(this, catalog, pageIndex, PAGE_SIZE);
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
		} else {
			list = (AgeList) readObject(key);
			if (list == null)
				list = new AgeList();
		}

		list = new AgeList();

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
		String[] urls = {
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
