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

	public Category findCategoryById(String id) {
		return categoryRepository.findById(id);
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

	public Note updateNote(String id, String newText, String newCategoryId) {
		Note existingNote = noteRepository.findById(id);
		if (existingNote == null) {
			throw new IllegalArgumentException("Note not found with id: " + id);
		}
		existingNote.setText(newText);
		existingNote.setCategoryId(newCategoryId);
		return noteRepository.save(existingNote);
	}

	public void deleteNote(String id) {
		noteRepository.delete(id);
	}
}
