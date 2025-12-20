package com.notemanager.model;

public class Note {

	private String text;
	private String categoryId;
	private String id;

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
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setText(String text) {
		if (text == null || text.isEmpty()) {
			throw new IllegalArgumentException("Text cannot be null or empty");
		}
		this.text = text;
	}
	public void setCategoryId(String categoryId) {
		if (categoryId == null || categoryId.isEmpty()) {
			throw new IllegalArgumentException("CategoryId cannot be null or empty");
		}
		this.categoryId = categoryId;
	}
}