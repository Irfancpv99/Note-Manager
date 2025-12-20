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
}