package com.notemanager.unit.controller;

import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.notemanager.controller.NoteController;
import com.notemanager.model.Category;
import com.notemanager.model.Note;

import com.notemanager.service.NoteService;
import com.notemanager.view.NoteView;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

	@Mock
	private NoteService noteService;

	@Mock
	private NoteView noteView;

	@InjectMocks
	private NoteController noteController;

	@Test
	void testAllCategoriesCallsServiceAndUpdatesView() {
		Category cat1 = new Category("PERSONAL");
		cat1.setId("1");
		Category cat2 = new Category("WORK");
		cat2.setId("2");
		List<Category> categories = Arrays.asList(cat1, cat2);
		when(noteService.getAllCategories()).thenReturn(categories);

		noteController.allCategories();

		verify(noteView).showAllCategories(categories);
	}
	@Test
	void testAllNotesCallsServiceAndUpdatesView() {
		Note note1 = new Note("Note 1", "cat1");
		note1.setId("1");
		Note note2 = new Note("Note 2", "cat2");
		note2.setId("2");
		List<Note> notes = Arrays.asList(note1, note2);
		when(noteService.getAllNotes()).thenReturn(notes);

		noteController.allNotes();

		verify(noteView).showAllNotes(notes);
	}
	
	@Test
	void testAllNotesEmptyList() {
		when(noteService.getAllNotes()).thenReturn(Collections.emptyList());

		noteController.allNotes();

		verify(noteView).showAllNotes(Collections.emptyList());
	}
	
	@Test
	void testNotesByCategoryIdCallsServiceAndUpdatesView() {
		Note note1 = new Note("Note 1", "cat1");
		note1.setId("1");
		List<Note> notes = Arrays.asList(note1);
		when(noteService.getNotesByCategoryId("cat1")).thenReturn(notes);

		noteController.notesByCategoryId("cat1");

		verify(noteView).showAllNotes(notes);
	}
	@Test
	void testNewNoteSuccessCreatesAndUpdatesView() {
		Note savedNote = new Note("New note", "cat1");
		savedNote.setId("1");
		when(noteService.createNote("New note", "cat1")).thenReturn(savedNote);

		noteController.newNote("New note", "cat1");

		verify(noteService).createNote("New note", "cat1");
		verify(noteView).noteAdded(savedNote);
	}
	@Test
	void testNewNoteEmptyTextShowsError() {
		noteController.newNote("", "cat1");

		verify(noteView).showError("Note text cannot be empty");
		verify(noteService, never()).createNote(anyString(), anyString());
	}
	@Test
	void testNewNoteBlankTextShowsError() {
		noteController.newNote("   ", "cat1");

		verify(noteView).showError("Note text cannot be empty");
		verify(noteService, never()).createNote(anyString(), anyString());
	}
	@Test
	void testNewNoteNullCategoryIdShowsError() {
		noteController.newNote("Test note", null);

		verify(noteView).showError("Please select a category");
		verify(noteService, never()).createNote(anyString(), anyString());
	}
}