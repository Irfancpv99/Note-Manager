package com.notemanager.app;

import java.awt.EventQueue;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.notemanager.controller.NoteController;
import com.notemanager.repository.mongo.CategoryMongoRepository;
import com.notemanager.repository.mongo.NoteMongoRepository;
import com.notemanager.service.NoteService;
import com.notemanager.view.swing.NoteSwingView;

public class NoteManagerApp {

	public static void main(String[] args) {
		String mongoUrl = "mongodb://localhost:27017";
		String dbName = "notemanager";

		for (String arg : args) {
			if (arg.startsWith("--mongo-url=")) {
				mongoUrl = arg.substring("--mongo-url=".length());
			} else if (arg.startsWith("--db-name=")) {
				dbName = arg.substring("--db-name=".length());
			}
		}

		MongoClient client = MongoClients.create(mongoUrl);
		MongoDatabase database = client.getDatabase(dbName);

		CategoryMongoRepository categoryRepository = new CategoryMongoRepository(database);
		NoteMongoRepository noteRepository = new NoteMongoRepository(database);
		NoteService noteService = new NoteService(noteRepository, categoryRepository);

		EventQueue.invokeLater(() -> {
			NoteSwingView view = new NoteSwingView();
			NoteController controller = new NoteController(noteService, view);
			view.setNoteController(controller);
			view.setVisible(true);
			controller.allCategories();
			controller.allNotes();
		});
	}
}