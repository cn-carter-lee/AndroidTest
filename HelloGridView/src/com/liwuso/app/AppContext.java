package com.liwuso.app;

import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppContext extends Application {
	public static final int PAGE_SIZE = 20;// 默认分页大小

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

	public PersonList getPersonList(int catalog, int pageIndex, boolean isRefresh)
			throws AppException {
		PersonList list = null;
		String key = "newslist_" + catalog + "_" + pageIndex + "_" + PAGE_SIZE;

		int j = 0;

		for (int i = 0; i < 50; i++) {
			Person p = new Person();
			p.Name = "XXX";
			list.Add(p);
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
}
