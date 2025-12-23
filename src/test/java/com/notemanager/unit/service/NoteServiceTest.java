package com.notemanager.unit.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.notemanager.model.Category;
import com.notemanager.model.Note;
import com.notemanager.repository.CategoryRepository;
import com.notemanager.repository.NoteRepository;
import com.notemanager.service.NoteService;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

	@Mock
	private NoteRepository noteRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private NoteService noteService;

	@Test
	void testGetAllCategoriesReturnsAllCategories() {
		Category cat1 = new Category("PERSONAL");
		cat1.setId("1");
		Category cat2 = new Category("WORK");
		cat2.setId("2");
		when(categoryRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

		List<Category> result = noteService.getAllCategories();

		assertThat(result).containsExactly(cat1, cat2);
		verify(categoryRepository).findAll();
	}

	@Test
	void testGetAllCategoriesEmptyReturnsEmptyList() {
		when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

		List<Category> result = noteService.getAllCategories();

		assertThat(result).isEmpty();
	}

	@Test
	void testGetAllNotesReturnsAllNotes() {
		Note note1 = new Note("Note 1", "cat1");
		note1.setId("1");
		Note note2 = new Note("Note 2", "cat2");
		note2.setId("2");
		when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

		List<Note> result = noteService.getAllNotes();

		assertThat(result).containsExactly(note1, note2);
		verify(noteRepository).findAll();
	}

	@Test
	void testGetAllNotesEmptyReturnsEmptyList() {
		when(noteRepository.findAll()).thenReturn(Collections.emptyList());

		List<Note> result = noteService.getAllNotes();

		assertThat(result).isEmpty();
	}

	@Test
	void testGetNotesByCategoryIdReturnsFilteredNotes() {
		Note note1 = new Note("Note 1", "cat1");
		note1.setId("1");
		Note note2 = new Note("Note 2", "cat1");
		note2.setId("2");
		when(noteRepository.findByCategoryId("cat1")).thenReturn(Arrays.asList(note1, note2));

		List<Note> result = noteService.getNotesByCategoryId("cat1");

		assertThat(result).containsExactly(note1, note2);
		verify(noteRepository).findByCategoryId("cat1");
	}

	@Test
	void testGetNotesByCategoryIdEmptyReturnsEmptyList() {
		when(noteRepository.findByCategoryId("cat1")).thenReturn(Collections.emptyList());

		List<Note> result = noteService.getNotesByCategoryId("cat1");

		assertThat(result).isEmpty();
	}

	@Test
	void testCreateNoteSavesAndReturnsNote() {
		Note savedNote = new Note("New note", "cat1");
		savedNote.setId("1");
		when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

		Note result = noteService.createNote("New note", "cat1");

		assertThat(result.getId()).isEqualTo("1");
		assertThat(result.getText()).isEqualTo("New note");
		assertThat(result.getCategoryId()).isEqualTo("cat1");
		verify(noteRepository).save(any(Note.class));
	}

	@Test
	void testCreateNoteNullTextThrowsException() {
		assertThatThrownBy(() -> noteService.createNote(null, "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testCreateNoteEmptyTextThrowsException() {
		assertThatThrownBy(() -> noteService.createNote("", "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testCreateNoteNullCategoryIdThrowsException() {
		assertThatThrownBy(() -> noteService.createNote("Test", null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("CategoryId cannot be null or empty");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testCreateNoteEmptyCategoryIdThrowsException() {
		assertThatThrownBy(() -> noteService.createNote("Test", ""))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("CategoryId cannot be null or empty");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testFindNoteByIdReturnsNote() {
		Note note = new Note("Test note", "cat1");
		note.setId("1");
		when(noteRepository.findById("1")).thenReturn(note);

		Note result = noteService.findNoteById("1");

		assertThat(result).isEqualTo(note);
		verify(noteRepository).findById("1");
	}

	@Test
	void testFindNoteByIdNotFoundReturnsNull() {
		when(noteRepository.findById("nonexistent")).thenReturn(null);

		Note result = noteService.findNoteById("nonexistent");

		assertThat(result).isNull();
	}

	@Test
	void testFindCategoryByIdReturnsCategory() {
		Category category = new Category("PERSONAL");
		category.setId("1");
		when(categoryRepository.findById("1")).thenReturn(category);

		Category result = noteService.findCategoryById("1");

		assertThat(result).isEqualTo(category);
		verify(categoryRepository).findById("1");
	}

	@Test
	void testFindCategoryByIdNotFoundReturnsNull() {
		when(categoryRepository.findById("nonexistent")).thenReturn(null);

		Category result = noteService.findCategoryById("nonexistent");

		assertThat(result).isNull();
	}

	@Test
	void testUpdateNoteExistingUpdatesNote() {
		Note existingNote = new Note("Old text", "cat1");
		existingNote.setId("1");
		when(noteRepository.findById("1")).thenReturn(existingNote);
		when(noteRepository.save(any(Note.class))).thenAnswer(inv -> inv.getArgument(0));

		Note result = noteService.updateNote("1", "New text", "cat2");

		assertThat(result.getText()).isEqualTo("New text");
		assertThat(result.getCategoryId()).isEqualTo("cat2");
		verify(noteRepository).save(existingNote);
	}

	@Test
	void testUpdateNoteNotFoundThrowsException() {
		when(noteRepository.findById("nonexistent")).thenReturn(null);

		assertThatThrownBy(() -> noteService.updateNote("nonexistent", "Text", "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Note not found with id: nonexistent");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testUpdateNoteNullTextThrowsException() {
		Note existingNote = new Note("Old text", "cat1");
		existingNote.setId("1");
		when(noteRepository.findById("1")).thenReturn(existingNote);

		assertThatThrownBy(() -> noteService.updateNote("1", null, "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testUpdateNoteEmptyTextThrowsException() {
		Note existingNote = new Note("Old text", "cat1");
		existingNote.setId("1");
		when(noteRepository.findById("1")).thenReturn(existingNote);

		assertThatThrownBy(() -> noteService.updateNote("1", "", "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
		verify(noteRepository, never()).save(any());
	}

	@Test
	void testDeleteNoteCallsRepository() {
		noteService.deleteNote("1");

		verify(noteRepository).delete("1");
	}
}
