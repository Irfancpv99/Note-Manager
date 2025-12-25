package com.notemanager.repository.mongo;

import java.util.List;
import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.model.Category;
import com.notemanager.repository.CategoryRepository;

import org.bson.Document;

public class CategoryMongoRepository implements CategoryRepository {

	private final MongoCollection<Document> collection;

	public CategoryMongoRepository(MongoDatabase database) {
		this.collection = database.getCollection("categories");
	}

	@Override
	public List<Category> findAll() {
		return new ArrayList<>();
	}

	@Override
	public Category findById(String id) {
		return null;
	}

	@Override
	public Category save(Category category) {
		return null;
	}

	@Override
	public void delete(String id) {
	}
}