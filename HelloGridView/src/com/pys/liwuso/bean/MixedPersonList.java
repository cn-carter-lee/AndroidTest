package com.pys.liwuso.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.liwuso.app.AppException;

public class MixedPersonList extends Entity {

	public final static int CATALOG_ALL = 1;
	public final static int CATALOG_INTEGRATION = 2;
	public final static int CATALOG_SOFTWARE = 3;

	private int catalog;
	private int pageSize = 5;
	private int personCount;
	private List<MixedPerson> person_list = new ArrayList<MixedPerson>();

	public int getCatalog() {
		return catalog;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void Add(MixedPerson person) {
		person_list.add(person);
	}

	public int getPersonCount() {
		return person_list.size();
	}

	public List<MixedPerson> getMixedPersonList() {
		return person_list;
	}

	public static MixedPersonList parse(InputStream inputStream) throws IOException,
			AppException {
		MixedPersonList personlist = new MixedPersonList();
		return personlist;
	}
}
