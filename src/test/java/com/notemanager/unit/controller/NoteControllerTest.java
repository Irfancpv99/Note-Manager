package com.notemanager.unit.controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.notemanager.controller.NoteController;
import com.notemanager.model.Category;
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
}