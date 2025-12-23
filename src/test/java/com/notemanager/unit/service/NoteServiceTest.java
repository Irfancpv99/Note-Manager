package com.notemanager.unit.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.notemanager.model.Category;
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
}
