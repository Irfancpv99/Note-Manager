package com.notemanager.repository.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.notemanager.model.Note;
import com.notemanager.repository.NoteRepository;

public class NoteMongoRepository implements NoteRepository {

	private final MongoCollection<Document> collection;

	public NoteMongoRepository(MongoDatabase database) {
		this.collection = database.getCollection("notes");
	}

	@Override
	public List<Note> findAll() {
		List<Note> notes = new ArrayList<>();
		for (Document doc : collection.find()) {
			notes.add(documentToNote(doc));
		}
		return notes;
	}

	private Note documentToNote(Document doc) {
		Note note = new Note(doc.getString("text"), doc.getString("categoryId"));
		note.setId(doc.getObjectId("_id").toString());
		return note;
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
