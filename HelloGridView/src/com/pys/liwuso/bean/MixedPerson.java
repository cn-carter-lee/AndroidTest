package com.pys.liwuso.bean;

public class MixedPerson extends Entity {
	public Person female;
	public Person male;

	public MixedPerson(Person female, Person male) {
		this.female = female;
		this.male = male;
	}

}
