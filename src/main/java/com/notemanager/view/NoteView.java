package com.notemanager.view;

import java.util.List;

import com.notemanager.model.Category;
import com.notemanager.model.Note;

public interface NoteView {

	void showAllNotes(List<Note> notes);

	void showAllCategories(List<Category> categories);

	void noteAdded(Note note);

	void noteUpdated(Note note);

	void noteDeleted(Note note);

	void showError(String message);
	
}
