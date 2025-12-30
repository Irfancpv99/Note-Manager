package com.notemanager.e2e;

import static org.assertj.core.api.Assertions.*;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.controller.NoteController;
import com.notemanager.repository.mongo.CategoryMongoRepository;
import com.notemanager.repository.mongo.NoteMongoRepository;
import com.notemanager.service.NoteService;
import com.notemanager.view.swing.NoteSwingView;

public class NoteManagerSwingViewE2E extends AssertJSwingJUnitTestCase {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> categoryCollection;
	private MongoCollection<Document> noteCollection;

	private CategoryMongoRepository categoryRepository;
	private NoteMongoRepository noteRepository;
	private NoteService noteService;
	private NoteController noteController;
	private NoteSwingView noteSwingView;

	private FrameFixture window;

	@Override
	protected void onSetUp() {
		client = MongoClients.create("mongodb://localhost:27017");
		database = client.getDatabase("notemanager");
		categoryCollection = database.getCollection("categories");
		noteCollection = database.getCollection("notes");
		categoryCollection.drop();
		noteCollection.drop();

		categoryRepository = new CategoryMongoRepository(database);
		noteRepository = new NoteMongoRepository(database);
		noteService = new NoteService(noteRepository, categoryRepository);

		GuiActionRunner.execute(() -> {
			noteSwingView = new NoteSwingView();
			noteController = new NoteController(noteService, noteSwingView);
			noteSwingView.setNoteController(noteController);
			return noteSwingView;
		});

		window = new FrameFixture(robot(), noteSwingView);
		window.show();
	}

	@Override
	protected void onTearDown() {
		client.close();
	}

	@Test
	@GUITest
	public void testAddNote() {
	   
		Document category = new Document().append("name", "WORK");
	    categoryCollection.insertOne(category);
	    
	     GuiActionRunner.execute(() -> noteController.allCategories());
	    
	     assertThat(window.comboBox("categoryComboBox").contents()).isNotEmpty();
	    
	    window.comboBox("categoryComboBox").selectItem(0);
	    window.textBox("noteTextArea").enterText("E2E test note");
	    window.button("saveButton").click(); 
	    
	    assertThat(noteCollection.countDocuments()).isEqualTo(1);
	}
	
	@Test
	@GUITest
	public void testDeleteNote() {
	    
		Document category = new Document().append("name", "WORK");
	    categoryCollection.insertOne(category);
	    String categoryId = category.getObjectId("_id").toString();
	    
	    Document note = new Document()
	        .append("text", "To Delete")
	        .append("categoryId", categoryId);
	    noteCollection.insertOne(note);
	    
	    GuiActionRunner.execute(() -> {
	        noteController.allCategories();
	        noteController.allNotes();
	    });
	    
	  
	    window.list("notesList").selectItem(0);
	    window.button(JButtonMatcher.withText("Delete")).click();
	    
	    assertThat(noteCollection.countDocuments()).isZero();
	}
	
	@Test
	@GUITest
	public void testEditNote() {
	   Document category = new Document().append("name", "WORK");
	    categoryCollection.insertOne(category);
	    String categoryId = category.getObjectId("_id").toString();
	    
	    Document note = new Document()
	        .append("text", "Original")
	        .append("categoryId", categoryId);
	    noteCollection.insertOne(note);
	    
	     GuiActionRunner.execute(() -> {
	        noteController.allCategories();
	        noteController.allNotes();
	    });
	    
	    window.list("notesList").selectItem(0);
	    window.button(JButtonMatcher.withText("Edit")).click();
	    window.textBox("noteTextArea").deleteText();
	    window.textBox("noteTextArea").enterText("Updated");
	    window.button("saveButton").click();
	    
	    Document updated = noteCollection.find().first();
	    assertThat(updated.getString("text")).isEqualTo("Updated");
	}
}

