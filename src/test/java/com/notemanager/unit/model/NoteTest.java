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
	@Test
	void testSetTextEmptyThrowsException() {
		Note note = new Note("Test", "cat1");
		assertThatThrownBy(() -> note.setText(""))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Text cannot be null or empty");
	}
	@Test
	void testSetCategoryIdUpdatesCategoryId() {
		Note note = new Note("Test", "cat1");
		note.setCategoryId("cat2");
		assertThat(note.getCategoryId()).isEqualTo("cat2");
	}
	@Test
	void testSetCategoryIdNullThrowsException() {
		Note note = new Note("Test", "cat1");
		assertThatThrownBy(() -> note.setCategoryId(null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("CategoryId cannot be null or empty");
	}
	@Test
	void testSetCategoryIdEmptyThrowsException() {
		Note note = new Note("Test", "cat1");
		assertThatThrownBy(() -> note.setCategoryId(""))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("CategoryId cannot be null or empty");
	}
	@Test
	void testEqualsSameIdReturnsTrue() {
		Note note1 = new Note("Text 1", "cat1");
		note1.setId("1");
		Note note2 = new Note("Text 2", "cat2");
		note2.setId("1");
		assertThat(note1).isEqualTo(note2);
	}
	@Test
	void testEqualsDifferentIdReturnsFalse() {
		Note note1 = new Note("Text", "cat1");
		note1.setId("1");
		Note note2 = new Note("Text", "cat1");
		note2.setId("2");
		assertThat(note1).isNotEqualTo(note2);
	}
	
	@Test
	void testEqualsNullReturnsFalse() {
		Note note = new Note("Text", "cat1");
		note.setId("1");
		assertThat(note).isNotEqualTo(null);
	}

	@Test
	void testEqualsDifferentClassReturnsFalse() {
		Note note = new Note("Text", "cat1");
		note.setId("1");
		assertThat(note).isNotEqualTo("not a note");
	}

	@Test
	void testEqualsSameObjectReturnsTrue() {
		Note note = new Note("Text", "cat1");
		note.setId("1");
		assertThat(note).isEqualTo(note);
	}

	@Test
	void testEqualsBothIdsNullSameTextReturnsTrue() {
		Note note1 = new Note("Same text", "cat1");
		Note note2 = new Note("Same text", "cat2");
		assertThat(note1).isEqualTo(note2);
	}

	@Test
	void testEqualsBothIdsNullDifferentTextReturnsFalse() {
		Note note1 = new Note("Text 1", "cat1");
		Note note2 = new Note("Text 2", "cat1");
		assertThat(note1).isNotEqualTo(note2);
	}
	@Test
	void testHashCodeConsistentWithEquals() {
		Note note1 = new Note("Text", "cat1");
		note1.setId("1");
		Note note2 = new Note("Text", "cat1");
		note2.setId("1");
		assertThat(note1.hashCode()).isEqualTo(note2.hashCode());
	}
	@Test
	void testToStringContainsText() {
		Note note = new Note("My note", "cat1");
		assertThat(note.toString()).contains("My note");
	}
	@Test
	void testToStringContainsCategoryId() {
		Note note = new Note("Test", "cat1");
		assertThat(note.toString()).contains("cat1");
	}
}