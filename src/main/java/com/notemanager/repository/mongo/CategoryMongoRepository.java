package com.notemanager.repository.mongo;

import java.util.List;
import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.notemanager.model.Category;
import com.notemanager.repository.CategoryRepository;

import org.bson.Document;
import org.bson.types.ObjectId;

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
		try {
			Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
			if (doc == null) {
				return null;
			}
			return documentToCategory(doc);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public Category save(Category category) {
		if (category.getId() == null) {
			Document doc = new Document().append("name", category.getName());
			collection.insertOne(doc);
			category.setId(doc.getObjectId("_id").toString());
		} else {
			Document doc = new Document().append("name", category.getName());
			collection.replaceOne(Filters.eq("_id", new ObjectId(category.getId())), doc);
		}
		return category;
	}

	@Override
	public void delete(String id) {
		try {
			collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
		} catch (IllegalArgumentException e) {
			// Invalid ObjectId  - ignore as the category not exist 
		}
	}
}