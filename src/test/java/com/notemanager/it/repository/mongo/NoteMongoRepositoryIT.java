package com.notemanager.it.repository.mongo;

import static org.assertj.core.api.Assertions.*;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
}