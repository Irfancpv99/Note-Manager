package com.notemanager.model;

public class Note {

	private String text;
	private String categoryId;

	public Note(String text, String categoryId) {
		if (text == null || text.isEmpty()) {
			throw new IllegalArgumentException("Text cannot be null or empty");
		}
		if (categoryId == null || categoryId.isEmpty()) {
			throw new IllegalArgumentException("CategoryId cannot be null or empty");
		}
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