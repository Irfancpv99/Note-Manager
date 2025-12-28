package com.notemanager.it.repository.mongo;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.notemanager.model.Note;
import com.notemanager.repository.mongo.NoteMongoRepository;

class NoteMongoRepositoryIT {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> noteCollection;
    private NoteMongoRepository repository;

    @BeforeEach
    void setUp() {
        client = MongoClients.create("mongodb://localhost:27017");
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

    @Test
    void testFindAllWhenDatabaseHasNotes() {
        noteCollection.insertOne(new Document("text", "Note 1").append("categoryId", "cat1"));
        noteCollection.insertOne(new Document("text", "Note 2").append("categoryId", "cat2"));
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    void testFindByIdNotFound() {
        assertThat(repository.findById("000000000000000000000000")).isNull();
    }
	@Test
	void testFindByIdFound() {
		Document doc = new Document().append("text", "Test note").append("categoryId", "cat1");
		noteCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		Note found = repository.findById(id);

		assertThat(found).isNotNull();
		assertThat(found.getText()).isEqualTo("Test note");
		assertThat(found.getCategoryId()).isEqualTo("cat1");
		assertThat(found.getId()).isEqualTo(id);
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
	@Test
	void testSaveNewNote() {
		Note note = new Note("Test note", "cat1");

		Note saved = repository.save(note);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getText()).isEqualTo("Test note");
		assertThat(saved.getCategoryId()).isEqualTo("cat1");
		assertThat(noteCollection.countDocuments()).isEqualTo(1);
	}
	@Test
	void testSaveExistingNoteUpdates() {
		Document doc = new Document().append("text", "Old text").append("categoryId", "cat1");
		noteCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		Note note = new Note("New text", "cat2");
		note.setId(id);

		Note saved = repository.save(note);

		assertThat(saved.getText()).isEqualTo("New text");
		assertThat(saved.getCategoryId()).isEqualTo("cat2");
		assertThat(noteCollection.countDocuments()).isEqualTo(1);
		Document updated = noteCollection.find(Filters.eq("_id", new ObjectId(id))).first();
		assertThat(updated.getString("text")).isEqualTo("New text");
		assertThat(updated.getString("categoryId")).isEqualTo("cat2");
	}
	@Test
	void testDelete() {
		Document doc = new Document().append("text", "Test").append("categoryId", "cat1");
		noteCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		repository.delete(id);

		assertThat(noteCollection.countDocuments()).isZero();
	}
}