package com.notemanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Frame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notemanager.app.NoteManagerApp;

public class NoteManagerAppE2E extends AssertJSwingJUnitTestCase {

    private static final String DB_NAME = "notemanager_e2e";

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> notes;

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase(DB_NAME);
        notes = database.getCollection("notes");

        database.drop();

        NoteManagerApp.start(DB_NAME);

        Frame frame = robot().finder().findByType(Frame.class, true);
        window = new FrameFixture(robot(), frame);
        window.show();

        window.comboBox("categoryComboBox").requireItemCount(3);
    }

    @Override
    protected void onTearDown() {
        client.close();
    }

    @Test
    @GUITest
    public void testAddNote() {
        window.comboBox("categoryComboBox").selectItem("WORK");
        window.textBox("noteTextArea").enterText("E2E Note");
        window.button("saveButton").click();

        assertThat(notes.countDocuments()).isEqualTo(1);
        window.list("notesList").requireItemCount(1);
    }

    @Test
    @GUITest
    public void testEditNoteTextAndCategory() {
    	testAddNote();

        window.list("notesList").selectItem(0);
        window.button("editButton").click();

        window.textBox("noteTextArea").deleteText();
        window.textBox("noteTextArea").enterText("Updated E2E Note");
        window.comboBox("categoryComboBox").selectItem("PERSONAL");
        window.button("saveButton").click();

        Document updated = notes.find().first();
        assertThat(updated.getString("text")).isEqualTo("Updated E2E Note");
    }

    @Test
    @GUITest
    public void testDeleteNote() {
    	testAddNote();

        window.list("notesList").selectItem(0);
        window.button("deleteButton").click();

        assertThat(notes.countDocuments()).isZero();
    }
    @Test
    @GUITest
    public void emptyNoteShowsError() {
        window.comboBox("categoryComboBox").selectItem("WORK");
        window.button("saveButton").click();

        window.label("errorLabel")
              .requireText("Note text cannot be empty");

        assertThat(notes.countDocuments()).isZero();
    }
    
}
