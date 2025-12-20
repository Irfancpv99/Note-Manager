package com.notemanager.unit.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.notemanager.model.Note;

class NoteTest {

	@Test
	void testConstructorSetsTextAndCategoryId() {
		Note note = new Note("Test note", "cat1");
		assertThat(note.getText()).isEqualTo("Test note");
		assertThat(note.getCategoryId()).isEqualTo("cat1");
	}
	
	@Test
	void testConstructorNullTextThrowsException() {
		assertThatThrownBy(() -> new Note(null, "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
	}
	@Test
	void testConstructorEmptyTextThrowsException() {
		assertThatThrownBy(() -> new Note("", "cat1"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
	}
	@Test
	void testConstructorNullCategoryIdThrowsException() {
		assertThatThrownBy(() -> new Note("Test", null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("CategoryId cannot be null or empty");
	}
	@Test
	void testConstructorEmptyCategoryIdThrowsException() {
		assertThatThrownBy(() -> new Note("Test", ""))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("CategoryId cannot be null or empty");
	}
	@Test
	void testGetIdReturnsNullWhenNotSet() {
		Note note = new Note("Test", "cat1");
		assertThat(note.getId()).isNull();
	}
	@Test
	void testSetIdSetsId() {
		Note note = new Note("Test", "cat1");
		note.setId("123");
		assertThat(note.getId()).isEqualTo("123");
	}
	@Test
	void testSetTextUpdatesText() {
		Note note = new Note("Original", "cat1");
		note.setText("Updated");
		assertThat(note.getText()).isEqualTo("Updated");
	}
	
	@Test
	void testSetTextNullThrowsException() {
		Note note = new Note("Test", "cat1");
		assertThatThrownBy(() -> note.setText(null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
	}
}