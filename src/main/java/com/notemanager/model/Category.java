package com.notemanager.model;

public class Category {

	private String name;
	private String id;


	public Category(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
}