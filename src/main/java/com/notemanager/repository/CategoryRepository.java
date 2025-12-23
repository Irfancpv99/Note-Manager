package com.notemanager.repository;

import java.util.List;

import com.notemanager.model.Category;

public interface CategoryRepository {

	Category save(Category category);

	Category findById(String id);

	List<Category> findAll();

	void delete(String id);
}