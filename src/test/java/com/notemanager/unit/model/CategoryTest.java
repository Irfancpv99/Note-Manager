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
}