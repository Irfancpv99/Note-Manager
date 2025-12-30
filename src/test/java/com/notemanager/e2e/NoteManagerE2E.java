package com.notemanager.e2e;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.swing.launcher.ApplicationLauncher.*;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class NoteManagerE2E extends AssertJSwingJUnitTestCase {

	private static final String MONGO_URL = "mongodb://localhost:27017";
	private static final String DB_NAME = "notemanager";

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> categoryCollection;
	private MongoCollection<Document> noteCollection;
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		client = MongoClients.create(MONGO_URL);
		database = client.getDatabase(DB_NAME);
		categoryCollection = database.getCollection("categories");
		noteCollection = database.getCollection("notes");
		categoryCollection.drop();
		noteCollection.drop();

		addTestCategories();

		application("com.notemanager.app.NoteManagerApp")
			.withArgs("--mongo-url=" + MONGO_URL, "--db-name=" + DB_NAME)
			.start();

		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Note Manager".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}

	@Override
	protected void onTearDown() {
		client.close();
	}

	private void addTestCategories() {
		categoryCollection.insertOne(new Document().append("name", "PERSONAL"));
		categoryCollection.insertOne(new Document().append("name", "WORK"));
	}

	@Test
	@GUITest
	public void testAddNote() {
		window.comboBox("categoryComboBox").selectItem(0);
		window.textBox("noteTextArea").enterText("E2E Test Note");
		window.button(JButtonMatcher.withText("Save")).click();

		assertThat(noteCollection.countDocuments()).isEqualTo(1);
	}
}