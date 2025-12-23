package com.notemanager.service;

import java.util.List;

import com.notemanager.model.Category;
import com.notemanager.model.Note;
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

	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}
	public List<Note> getNotesByCategoryId(String categoryId) {
		return noteRepository.findByCategoryId(categoryId);
	}
	public Note createNote(String text, String categoryId) {
		Note note = new Note(text, categoryId);
		return noteRepository.save(note);
	}
	public Note findNoteById(String id) {
		return noteRepository.findById(id);
	}
}
