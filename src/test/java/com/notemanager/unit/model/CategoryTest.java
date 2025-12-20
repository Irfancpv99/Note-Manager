package com.notemanager.unit.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.notemanager.model.Category;

class CategoryTest {

	@Test
	void testConstructorSetsName() {
		Category category = new Category("PERSONAL");
		assertThat(category.getName()).isEqualTo("PERSONAL");
	}
	
	@Test
	void testConstructorNullNameThrowsException() {
		assertThatThrownBy(() -> new Category(null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Name cannot be null or empty");
	}
	
	@Test
	void testConstructorEmptyNameThrowsException() {
		assertThatThrownBy(() -> new Category(""))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Name cannot be null or empty");
	}
	@Test
	void testGetIdReturnsNullWhenNotSet() {
		Category category = new Category("WORK");
		assertThat(category.getId()).isNull();
	}
	@Test
	void testSetIdSetsId() {
		Category category = new Category("PERSONAL");
		category.setId("123");
		assertThat(category.getId()).isEqualTo("123");
	}
	@Test
	void testSetNameUpdatesName() {
		Category category = new Category("PERSONAL");
		category.setName("WORK");
		assertThat(category.getName()).isEqualTo("WORK");
	}
	@Test
	void testSetNameNullThrowsException() {
		Category category = new Category("PERSONAL");
		assertThatThrownBy(() -> category.setName(null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Name cannot be null or empty");
	}
	@Test
	void testEqualsSameIdReturnsTrue() {
		Category cat1 = new Category("PERSONAL");
		cat1.setId("1");
		Category cat2 = new Category("WORK");
		cat2.setId("1");
		assertThat(cat1).isEqualTo(cat2);
	}
}