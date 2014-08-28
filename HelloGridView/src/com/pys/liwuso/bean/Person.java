package com.pys.liwuso.bean;

public class Person extends Entity {
	public Person(int id, String name, int sex) {
		this.Name = name;
		this.Sex = sex;
		this.id = id;
	}

	public String Name;
	public int Sex;
}
