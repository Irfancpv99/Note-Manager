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
		List<Category> categories = new ArrayList<>();
		for (Document doc : collection.find()) {
			categories.add(documentToCategory(doc));
		}
		return categories;
	}

	private Category documentToCategory(Document doc) {
		Category category = new Category(doc.getString("name"));
		category.setId(doc.getObjectId("_id").toString());
		return category;
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