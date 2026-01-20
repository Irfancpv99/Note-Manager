package com.notemanager.service;

import com.notemanager.model.Category;
import com.notemanager.repository.CategoryRepository;

public class DatabaseInitializer {

	private CategoryRepository categoryRepository;

	public DatabaseInitializer(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void initializeDefaultCategories() {
		if (categoryRepository.findAll().isEmpty()) {
			categoryRepository.save(new Category("PERSONAL"));
			categoryRepository.save(new Category("WORK"));
			categoryRepository.save(new Category("STUDY"));
		}
	}
}