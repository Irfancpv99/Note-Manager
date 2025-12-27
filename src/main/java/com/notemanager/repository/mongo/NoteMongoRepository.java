package com.notemanager.repository.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.model.Note;
import com.notemanager.repository.NoteRepository;

public class NoteMongoRepository implements NoteRepository {

	private final MongoCollection<Document> collection;

	public NoteMongoRepository(MongoDatabase database) {
		this.collection = database.getCollection("notes");
	}

	@Override
	public List<Note> findAll() {
		return new ArrayList<>();
	}

	@Override
	public Note findById(String id) {
		return null;
	}

	@Override
	public List<Note> findByCategoryId(String categoryId) {
		return new ArrayList<>();
	}

	@Override
	public Note save(Note note) {
		return null;
	}

	@Override
	public void delete(String id) {
	}
}
