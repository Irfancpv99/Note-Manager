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
		
		MongoClient client = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = client.getDatabase("notemanager");
		
		
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