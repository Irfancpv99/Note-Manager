package com.notemanager.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.model.Category;
import com.notemanager.repository.mongo.CategoryMongoRepository;
import com.notemanager.repository.mongo.NoteMongoRepository;

class NoteServiceIT {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> noteCollection;
	private MongoCollection<Document> categoryCollection;
	private NoteService noteService;

	@BeforeEach
	void setUp() {
		client = MongoClients.create("mongodb://localhost:27017");
		database = client.getDatabase("notemanager");
		noteCollection = database.getCollection("notes");
		categoryCollection = database.getCollection("categories");
		noteCollection.drop();
		categoryCollection.drop();
		NoteMongoRepository noteRepository = new NoteMongoRepository(database);
		CategoryMongoRepository categoryRepository = new CategoryMongoRepository(database);
		noteService = new NoteService(noteRepository, categoryRepository);
	}

	@AfterEach
	void tearDown() {
		client.close();
	}

	@Test
	void testGetAllCategoriesFromDatabase() {
		categoryCollection.insertOne(new Document().append("name", "PERSONAL"));
		categoryCollection.insertOne(new Document().append("name", "WORK"));

		List<Category> categories = noteService.getAllCategories();

		assertThat(categories).hasSize(2);
		assertThat(categories).extracting(Category::getName)
			.containsExactlyInAnyOrder("PERSONAL", "WORK");
	}
	@Test
	void testGetAllNotesFromDatabase() {
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 2").append("categoryId", "cat2"));

		List<Note> notes = noteService.getAllNotes();

		assertThat(notes).hasSize(2);
		assertThat(notes).extracting(Note::getText)
			.containsExactlyInAnyOrder("Note 1", "Note 2");
	}
}