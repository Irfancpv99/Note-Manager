package com.notemanager.controller;

import java.util.List;

import com.notemanager.model.Category;
import com.notemanager.model.Note;
import com.notemanager.service.NoteService;
import com.notemanager.view.NoteView;

public class NoteController {

	private final NoteService noteService;
	private final NoteView noteView;

	public NoteController(NoteService noteService, NoteView noteView) {
		this.noteService = noteService;
		this.noteView = noteView;
	}

	public void allCategories() {
		List<Category> categories = noteService.getAllCategories();
		noteView.showAllCategories(categories);
	}

	public void allNotes() {
		List<Note> notes = noteService.getAllNotes();
		noteView.showAllNotes(notes);
		
	}

	public void notesByCategoryId(String categoryId) {
		List<Note> notes = noteService.getNotesByCategoryId(categoryId);
		noteView.showAllNotes(notes);
	}
	public void newNote(String text, String categoryId) {
		if (text == null || text.trim().isEmpty()) {
			noteView.showError("Note text cannot be empty");
			return;
		}
		if (categoryId == null || categoryId.trim().isEmpty()) {
			noteView.showError("Please select a category");
			return;
		}
		try {
			Note savedNote = noteService.createNote(text, categoryId);
			noteView.noteAdded(savedNote);
		} catch (Exception e) {
			noteView.showError("Error creating note: " + e.getMessage());
		}
	}
	public void updateNote(String id, String newText, String newCategoryId) {
		if (newText == null || newText.trim().isEmpty()) {
			noteView.showError("Note text cannot be empty");
			return;
		}
		try {
			Note updatedNote = noteService.updateNote(id, newText, newCategoryId);
			noteView.noteUpdated(updatedNote);
		} catch (IllegalArgumentException e) {
			noteView.showErrorNoteNotFound(e.getMessage(), null);
		}
	}

	public void deleteNote(String id) {
		Note note = noteService.findNoteById(id);
		if (note == null) {
			noteView.showErrorNoteNotFound("Note not found with id: " + id, null);
			return;
		}
		noteService.deleteNote(id);
		noteView.noteDeleted(note);
	}
}