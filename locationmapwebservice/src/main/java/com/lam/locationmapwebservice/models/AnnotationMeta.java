package com.lam.locationmapwebservice.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnnotationMeta {
	private long annotation_id;
	private int age;
	private String gender;

	public AnnotationMeta(long annotation_id, int age, String gender) {
		this.annotation_id = annotation_id;
		this.age = age;
		this.gender = gender;
	}

	public AnnotationMeta(long annotation_id, int age, boolean isMale) {
		this.annotation_id = annotation_id;
		this.age = age;
		if (isMale)
			this.gender = "male";
		else 
			this.gender = "female";
	}

	public long getAnnotation_id() {
		return annotation_id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "AnnotaionMeta [annotation_id=" + annotation_id + ", age=" + age + ", gender=" + gender + "]";
	}
}