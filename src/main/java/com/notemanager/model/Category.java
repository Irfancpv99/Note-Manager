package com.notemanager.model;

import java.util.Objects;

public class Category {

	private String id;
	private String name;

	public Category(String name) {
		validateName(name);
		this.name = name;
	}

	private void validateName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		validateName(name);
		this.name = name;
	}

	@Override
	public int hashCode() {
		if (id != null) return id.hashCode();
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id != null && other.id != null) {
			return Objects.equals(id, other.id);
		}
		if (id != null || other.id != null) {
	        return false;
	    }
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}