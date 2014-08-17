package com.liwuso.app.common;

public class SortItem {
	private String Name;
	private int TypeId;
	private int imageFlag; // Populate it with our resource ID for the correct
							// image.

	public SortItem(String cName, int cPopulation) {
		Name = cName;
		TypeId = cPopulation;		
	}

	
	public SortItem(String cName, int typeid, int flagImage) {
		Name = cName;
		TypeId = typeid;
		imageFlag = flagImage;
	}

	public String getItemName() {
		return Name;
	}

	public int getTypeId() {
		return TypeId;
	}

	public int getIamgeFlag() {
		return imageFlag;
	}

	public String toString() {
		return Name;
	}
}
