package com.notemanager.model;

import java.util.Objects;

public class Note {

	private String id;
	private String text;
	private String categoryId;

	public Note(String text, String categoryId) {
		validateText(text);
		validateCategoryId(categoryId);
		this.text = text;
		this.categoryId = categoryId;
	}

	private void validateText(String text) {
		if (text == null || text.isEmpty()) {
			throw new IllegalArgumentException("Text cannot be null or empty");
		}
	}

	private void validateCategoryId(String categoryId) {
		if (categoryId == null || categoryId.isEmpty()) {
			throw new IllegalArgumentException("CategoryId cannot be null or empty");
		}
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

	public void setText(String text) {
		validateText(text);
		this.text = text;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		validateCategoryId(categoryId);
		this.categoryId = categoryId;
	}

	@Override
	public int hashCode() {
		if (id != null) return id.hashCode();
		return Objects.hash(text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (id != null && other.id != null) {
			return Objects.equals(id, other.id);
		}
		return Objects.equals(text, other.text);
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", text=" + text + ", categoryId=" + categoryId + "]";
	}
}