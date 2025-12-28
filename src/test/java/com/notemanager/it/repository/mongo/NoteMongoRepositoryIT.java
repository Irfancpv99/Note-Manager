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

import com.notemanager.repository.mongo.NoteMongoRepository;

class NoteMongoRepositoryIT {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> categoryCollection;
    private NoteMongoRepository repository;

    @BeforeEach
    void setUp() {
        client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase("notemanager");
        categoryCollection = database.getCollection("categories");
        categoryCollection.drop();
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
		noteCollection.insertOne(new Document().append("text", "Note 1").append("categoryId", "cat1"));
		noteCollection.insertOne(new Document().append("text", "Note 2").append("categoryId", "cat2"));

		assertThat(repository.findAll()).hasSize(2);
	}
}