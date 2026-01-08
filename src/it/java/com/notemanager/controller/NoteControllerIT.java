package com.notemanager.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.model.Category;
import com.notemanager.model.Note;
import com.notemanager.repository.mongo.CategoryMongoRepository;
import com.notemanager.repository.mongo.NoteMongoRepository;
import com.notemanager.service.NoteService;
import com.notemanager.view.NoteView;

class NoteControllerIT {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> noteCollection;
	private MongoCollection<Document> categoryCollection;
	private NoteController noteController;
	private AutoCloseable closeable;

	@Mock
	private NoteView noteView;

	@Captor
	private ArgumentCaptor<List<Category>> categoriesCaptor;

	@Captor
	private ArgumentCaptor<List<Note>> notesCaptor;
	
	
	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		client = MongoClients.create("mongodb://localhost:27017");
		database = client.getDatabase("notemanager");
		noteCollection = database.getCollection("notes");
		categoryCollection = database.getCollection("categories");
		noteCollection.drop();
		categoryCollection.drop();
		NoteMongoRepository noteRepository = new NoteMongoRepository(database);
		CategoryMongoRepository categoryRepository = new CategoryMongoRepository(database);
		NoteService noteService = new NoteService(noteRepository, categoryRepository);
		noteController = new NoteController(noteService, noteView);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
		client.close();
	}

	@Test
	void testAllCategoriesFromDatabase() {
		categoryCollection.insertOne(new Document().append("name", "PERSONAL"));
		categoryCollection.insertOne(new Document().append("name", "WORK"));

		noteController.allCategories();

		verify(noteView).showAllCategories(categoriesCaptor.capture());
		List<Category> captured = categoriesCaptor.getValue();
		assertThat(captured).hasSize(2);
		assertThat(captured).extracting(Category::getName)
			.containsExactlyInAnyOrder("PERSONAL", "WORK");
	}
	@Test
	void testAllNotesFromDatabase() {
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 2").append("categoryId", "cat2"));

		noteController.allNotes();

		verify(noteView).showAllNotes(notesCaptor.capture());
		List<Note> captured = notesCaptor.getValue();
		assertThat(captured).hasSize(2);
		assertThat(captured).extracting(Note::getText)
			.containsExactlyInAnyOrder("Note 1", "Note 2");
	}
	@Test
	void testNotesByCategoryIdFromDatabase() {
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 2").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 3").append("categoryId", "cat2"));

		noteController.notesByCategoryId("cat1");

		verify(noteView).showAllNotes(notesCaptor.capture());
		List<Note> captured = notesCaptor.getValue();
		assertThat(captured).hasSize(2);
	}
	@Test
	void testNewNoteToDatabase() {
		noteController.newNote("New note", "cat1");

		verify(noteView).noteAdded(noteCaptor.capture());
		Note captured = noteCaptor.getValue();
		assertThat(captured.getId()).isNotNull();
		assertThat(captured.getText()).isEqualTo("New note");
		assertThat(noteCollection.countDocuments()).isEqualTo(1);
	}
}
