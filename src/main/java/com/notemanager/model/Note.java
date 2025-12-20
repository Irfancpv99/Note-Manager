package com.notemanager.model;

public class Note {

	private String text;
	private String categoryId;

	public Note(String text, String categoryId) {
		this.text = text;
		this.categoryId = categoryId;
	}

	public String getText() {
		return text;
	}

	public String getCategoryId() {
		return categoryId;
	}
}