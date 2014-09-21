package com.liwuso.bean;

public class Person extends Entity {
	public Person(int id, String name, String aliasName, int sex) {
		this.Name = name;
		this.AliasName = aliasName;
		this.Sex = sex;
		this.id = id;
	}

	public Person(int id, String name, int sex) {
		this.Name = name;
		this.AliasName = name;
		this.Sex = sex;
		this.id = id;
	}

	public String Name;

	public String AliasName;

	public int Sex;
}
