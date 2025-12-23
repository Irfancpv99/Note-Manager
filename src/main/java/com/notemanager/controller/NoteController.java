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
}