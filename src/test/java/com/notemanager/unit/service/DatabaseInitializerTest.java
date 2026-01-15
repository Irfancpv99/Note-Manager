package com.notemanager.unit.service;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.notemanager.model.Category;
import com.notemanager.repository.CategoryRepository;
import com.notemanager.service.DatabaseInitializer;

@ExtendWith(MockitoExtension.class)
class DatabaseInitializerTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private DatabaseInitializer databaseInitializer;

	@Test
	void testInitializeDefaultCategoriesWhenNoCategories() {
		when(categoryRepository.findAll())
			.thenReturn(emptyList());

		databaseInitializer.initializeDefaultCategories();

		verify(categoryRepository, times(3)).save(any(Category.class));
	}
	@Test
	void testInitializeDefaultCategoriesWhenCategoriesAlreadyExist() {
		Category existingCategory = new Category("EXISTING");
		existingCategory.setId("1");
		when(categoryRepository.findAll())
			.thenReturn(asList(existingCategory));

		databaseInitializer.initializeDefaultCategories();

		verify(categoryRepository, never()).save(any(Category.class));
	}
}