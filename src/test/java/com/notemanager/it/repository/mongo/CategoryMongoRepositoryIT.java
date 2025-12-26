package com.notemanager.it.repository.mongo;

import static org.assertj.core.api.Assertions.*;

import org.bson.Document;
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
import com.notemanager.repository.mongo.CategoryMongoRepository;

@Testcontainers
class CategoryMongoRepositoryIT {

	@Container
	private static final MongoDBContainer mongoContainer = new MongoDBContainer("mongo:6.0");

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> categoryCollection;
	private CategoryMongoRepository repository;

	@BeforeEach
	void setUp() {
		client = MongoClients.create(mongoContainer.getConnectionString());
		database = client.getDatabase("notemanager");
		categoryCollection = database.getCollection("categories");
		categoryCollection.drop();
		repository = new CategoryMongoRepository(database);
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
	void testFindAllWhenDatabaseHasCategories() {
		categoryCollection.insertOne(new Document().append("name", "PERSONAL"));
		categoryCollection.insertOne(new Document().append("name", "WORK"));

		assertThat(repository.findAll()).hasSize(2);
	}
	@Test
	void testFindByIdNotFound() {
		assertThat(repository.findById("000000000000000000000000")).isNull();
	}
	@Test
	void testFindByIdFound() {
		Document doc = new Document().append("name", "PERSONAL");
		categoryCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		com.notemanager.model.Category found = repository.findById(id);

		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("PERSONAL");
		assertThat(found.getId()).isEqualTo(id);
	}
}