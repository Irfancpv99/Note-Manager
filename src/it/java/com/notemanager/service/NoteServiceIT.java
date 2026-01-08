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
import com.notemanager.model.Note;
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
	@Test
	void testGetNotesByCategoryIdFromDatabase() {
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 2").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 3").append("categoryId", "cat2"));

		List<Note> notes = noteService.getNotesByCategoryId("cat1");

		assertThat(notes).hasSize(2);
		assertThat(notes).extracting(Note::getText)
			.containsExactlyInAnyOrder("Note 1", "Note 2");
	}
	@Test
	void testCreateNoteInDatabase() {
		Note created = noteService.createNote("New note", "cat1");

		assertThat(created.getId()).isNotNull();
		assertThat(created.getText()).isEqualTo("New note");
		assertThat(noteCollection.countDocuments()).isEqualTo(1);
	}
	@Test
	void testFindNoteByIdFromDatabase() {
		Document doc = new Document().append("text", "Test").append("categoryId", "cat1");
		noteCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		Note found = noteService.findNoteById(id);

		assertThat(found).isNotNull();
		assertThat(found.getText()).isEqualTo("Test");
	}
	@Test
	void testFindCategoryByIdFromDatabase() {
		Document doc = new Document().append("name", "PERSONAL");
		categoryCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		Category found = noteService.findCategoryById(id);

		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("PERSONAL");
	}
	@Test
	void testUpdateNoteInDatabase() {
		Document doc = new Document().append("text", "Old").append("categoryId", "cat1");
		noteCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		Note updated = noteService.updateNote(id, "New", "cat2");

		assertThat(updated.getText()).isEqualTo("New");
		assertThat(updated.getCategoryId()).isEqualTo("cat2");
		assertThat(noteCollection.countDocuments()).isEqualTo(1);
	}
}