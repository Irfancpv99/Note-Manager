package com.notemanager.model;
import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash(text);
	}
	@Override
	public String toString() {
		return "Note [id=" + id + ", text=" + text + ", categoryId=" + categoryId + "]";
	}
}