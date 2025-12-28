package com.notemanager.repository.mongo;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.notemanager.model.Note;

@Testcontainers
class NoteMongoRepositoryIT {

	@Container
	private static final MongoDBContainer mongoContainer = new MongoDBContainer("mongo:6.0");

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> noteCollection;
	private NoteMongoRepository repository;

	@BeforeEach
	void setUp() {
		client = MongoClients.create(mongoContainer.getConnectionString());
		database = client.getDatabase("notemanager");
		noteCollection = database.getCollection("notes");
		noteCollection.drop();
		repository = new NoteMongoRepository(database);
	}

	@AfterEach
	void tearDown() {
		client.close();
	}

	@Test
	void testFindAllWhenDatabaseIsEmpty() {
		assertThat(repository.findAll()).isEmpty();
	}

	@Override
	public Note findById(String id) {
		try {
			Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
			if (doc == null) {
				return null;
			}
			return documentToNote(doc);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public List<Note> findByCategoryId(String categoryId) {
		List<Note> notes = new ArrayList<>();
		for (Document doc : collection.find(Filters.eq("categoryId", categoryId))) {
			notes.add(documentToNote(doc));
		}
		return notes;
	}

	@Test
	void testFindByIdWithInvalidId() {
		assertThat(repository.findById("invalid")).isNull();
	}

	@Test
	void testFindByCategoryIdWhenNoMatches() {
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));

		assertThat(repository.findByCategoryId("cat2")).isEmpty();
	}

	@Test
	void testFindByCategoryIdWhenMatches() {
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 2").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 3").append("categoryId", "cat2"));

		List<Note> found = repository.findByCategoryId("cat1");

		assertThat(found).hasSize(2);
	}

	@Override
	public void delete(String id) {
		try {
			collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
		} catch (IllegalArgumentException e) {
		}
	}

	private Note documentToNote(Document doc) {
		Note note = new Note(doc.getString("text"), doc.getString("categoryId"));
		note.setId(doc.getObjectId("_id").toString());
		return note;
	}
}