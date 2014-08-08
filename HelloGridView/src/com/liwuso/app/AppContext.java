package com.liwuso.app;

import com.pys.liwuso.bean.Person;
import com.pys.liwuso.bean.PersonList;

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
			list.Add(new Person("女朋友"));
			list.Add(new Person("老婆"));
			list.Add(new Person("女性朋友"));
			list.Add(new Person("妈妈"));
			list.Add(new Person("女生"));

		} else if (catalog == 1) {
			list.Add(new Person("男朋友"));
			list.Add(new Person("老公"));
			list.Add(new Person("男性朋友"));
			list.Add(new Person("爸爸"));
			list.Add(new Person("男生"));

		}
		int j = 0;
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
}
