package com.notemanager.model;


import java.util.Objects;

public class Category {

	private String id;
	private String name;

	public Category(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setId(String id) {
		this.id = id;
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
		return Objects.equals(name, other.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}