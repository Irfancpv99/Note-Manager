package com.notemanager.unit.view.swing;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.notemanager.controller.NoteController;
import com.notemanager.model.Category;
import com.notemanager.model.Note;
import com.notemanager.view.swing.NoteSwingView;

public class NoteSwingViewTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private NoteSwingView noteSwingView;
	private AutoCloseable closeable;

	@Mock
	private NoteController noteController;

	@Override
	protected void onSetUp() {
		closeable = MockitoAnnotations.openMocks(this);
		GuiActionRunner.execute(() -> {
			noteSwingView = new NoteSwingView();
			noteSwingView.setNoteController(noteController);
			return noteSwingView;
		});
		window = new FrameFixture(robot(), noteSwingView);
		window.show();
	}

	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	@Test
	@GUITest
	public void testControlsInitialState() {
		window.comboBox("categoryComboBox").requireEnabled();
		window.textBox("noteTextArea").requireEnabled();
		window.button(JButtonMatcher.withText("Save")).requireEnabled();
		window.list("notesList").requireEnabled();
		window.button(JButtonMatcher.withText("Edit")).requireDisabled();
		window.button(JButtonMatcher.withText("Delete")).requireDisabled();
	}

	@Test
	@GUITest
	public void testSaveButtonCallsControllerNewNote() {
		Category category = new Category("WORK");
		category.setId("1");
		GuiActionRunner.execute(() -> 
			noteSwingView.showAllCategories(Arrays.asList(category))
		);

		window.comboBox("categoryComboBox").selectItem(0);
		window.textBox("noteTextArea").enterText("Test note text");
		window.button(JButtonMatcher.withText("Save")).click();

		verify(noteController).newNote("Test note text", "1");
	}

	@Test
	@GUITest
	public void testSaveButtonCallsControllerWithNullCategoryWhenNoneSelected() {
		window.textBox("noteTextArea").enterText("Test note");
		window.button(JButtonMatcher.withText("Save")).click();

		verify(noteController).newNote("Test note", null);
	}

	@Test
	@GUITest
	public void testSelectNoteFromListEnablesButtons() {
		Note note = new Note("Test note", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> 
			noteSwingView.showAllNotes(Arrays.asList(note))
		);

		window.list("notesList").selectItem(0);

		window.button(JButtonMatcher.withText("Edit")).requireEnabled();
		window.button(JButtonMatcher.withText("Delete")).requireEnabled();
	}

	@Test
	@GUITest
	public void testDeleteButtonCallsControllerDelete() {
		Note note = new Note("Test note", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> 
			noteSwingView.showAllNotes(Arrays.asList(note))
		);

		window.list("notesList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete")).click();

		verify(noteController).deleteNote("1");
	}

	@Test
	@GUITest
	public void testEditButtonLoadsNoteIntoTextArea() {
		Category category = new Category("WORK");
		category.setId("cat1");
		Note note = new Note("Test note content", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> {
			noteSwingView.showAllCategories(Arrays.asList(category));
			noteSwingView.showAllNotes(Arrays.asList(note));
		});

		window.list("notesList").selectItem(0);
		window.button(JButtonMatcher.withText("Edit")).click();

		window.textBox("noteTextArea").requireText("Test note content");
	}

	@Test
	@GUITest
	public void testEditModeChangesButtonText() {
		Category category = new Category("WORK");
		category.setId("cat1");
		Note note = new Note("Test note", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> {
			noteSwingView.showAllCategories(Arrays.asList(category));
			noteSwingView.showAllNotes(Arrays.asList(note));
		});

		window.list("notesList").selectItem(0);
		window.button(JButtonMatcher.withText("Edit")).click();

		window.button(JButtonMatcher.withText("Update"));
	}

	@Test
	@GUITest
	public void testUpdateButtonCallsControllerUpdate() {
		Category category = new Category("PERSONAL");
		category.setId("cat1");
		Note note = new Note("Original", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> {
			noteSwingView.showAllCategories(Arrays.asList(category));
			noteSwingView.showAllNotes(Arrays.asList(note));
		});

		window.list("notesList").selectItem(0);
		window.button(JButtonMatcher.withText("Edit")).click();

		window.textBox("noteTextArea").deleteText();
		window.textBox("noteTextArea").enterText("Updated text");
		window.button(JButtonMatcher.withText("Update")).click();

		verify(noteController).updateNote("1", "Updated text", "cat1");
	}

	@Test
	@GUITest
	public void testNoteUpdatedRefreshesInList() {
		Note note = new Note("Original text", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> 
			noteSwingView.showAllNotes(Arrays.asList(note))
		);

		Note updatedNote = new Note("Updated text", "cat1");
		updatedNote.setId("1");
		GuiActionRunner.execute(() -> noteSwingView.noteUpdated(updatedNote));

		String[] listContents = window.list("notesList").contents();
		assertThat(listContents[0]).contains("Updated text");
	}

	@Test
	@GUITest
	public void testNoteDeletedRemovedFromList() {
		Note note1 = new Note("Note 1", "cat1");
		note1.setId("1");
		Note note2 = new Note("Note 2", "cat1");
		note2.setId("2");
		GuiActionRunner.execute(() -> 
			noteSwingView.showAllNotes(Arrays.asList(note1, note2))
		);

		GuiActionRunner.execute(() -> noteSwingView.noteDeleted(note1));

		String[] listContents = window.list("notesList").contents();
		assertThat(listContents).hasSize(1);
	}
}