package com.pys.hellogridview;

public class SortItem {
	private String Name;
	private int TypeId;
	private int imageFlag; // Populate it with our resource ID for the correct
							// image.

	public SortItem(String cName, int cPopulation) {
		Name = cName;
		TypeId = cPopulation;		
	}

	
	public SortItem(String cName, int cPopulation, int flagImage) {
		Name = cName;
		TypeId = cPopulation;
		imageFlag = flagImage;
	}

	public String getItemName() {
		return Name;
	}

	public long getTypeId() {
		return TypeId;
	}

	public int getIamgeFlag() {
		return imageFlag;
	}

	public String toString() {
		return Name;
	}
}
