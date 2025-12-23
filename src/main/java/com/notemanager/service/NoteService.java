package com.notemanager.service;

import java.util.List;

import com.notemanager.model.Category;
import com.notemanager.repository.CategoryRepository;
import com.notemanager.repository.NoteRepository;

public class NoteService {

	private final NoteRepository noteRepository;
	private final CategoryRepository categoryRepository;

	public NoteService(NoteRepository noteRepository, CategoryRepository categoryRepository) {
		this.noteRepository = noteRepository;
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
}
