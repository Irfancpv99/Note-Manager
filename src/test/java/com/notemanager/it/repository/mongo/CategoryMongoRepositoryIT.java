package com.notemanager.it.repository.mongo;

import static org.assertj.core.api.Assertions.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.model.Category;
import com.notemanager.repository.mongo.CategoryMongoRepository;

class CategoryMongoRepositoryIT {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> categoryCollection;
    private CategoryMongoRepository repository;

    @BeforeEach
    void setUp() {
        client = MongoClients.create("mongodb://localhost:27017");
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

        Category found = repository.findById(id);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("PERSONAL");
        assertThat(found.getId()).isEqualTo(id);
    }
    @Test
	void testFindByIdWithInvalidId() {
		assertThat(repository.findById("invalid")).isNull();
	}
    @Test
	void testSaveNewCategory() {
		Category category = new Category("STUDY");

		Category saved = repository.save(category);

		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getName()).isEqualTo("STUDY");
		assertThat(categoryCollection.countDocuments()).isEqualTo(1);
	}
    @Test
	void testSaveExistingCategoryUpdates() {
		Document doc = new Document().append("name", "OLD");
		categoryCollection.insertOne(doc);
		String id = doc.getObjectId("_id").toString();

		Category category = new Category("NEW");
		category.setId(id);

		Category saved = repository.save(category);

		assertThat(saved.getName()).isEqualTo("NEW");
		assertThat(categoryCollection.countDocuments()).isEqualTo(1);
		Document updated = categoryCollection.find(Filters.eq("_id", new ObjectId(id))).first();
		assertThat(updated.getString("name")).isEqualTo("NEW");
	}
}