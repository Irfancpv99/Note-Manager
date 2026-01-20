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
		noteSwingView = GuiActionRunner.execute(NoteSwingView::new);
		window = new FrameFixture(robot(), noteSwingView);
		window.show();
		GuiActionRunner.execute(() -> noteSwingView.setNoteController(noteController));
	}

	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	@Test
	@GUITest
	public void testControlsInitialState() {
		window.button(JButtonMatcher.withText("Edit")).requireDisabled();
		window.button(JButtonMatcher.withText("Delete")).requireDisabled();
		assertThat(window.button(JButtonMatcher.withText("Edit")).isEnabled()).isFalse();
	}
	@Test
	@GUITest
	public void testSaveButtonCallsControllerNewNote() {
		Category category = new Category("WORK");
		category.setId("1");
		GuiActionRunner.execute(() -> noteSwingView.showAllCategories(Arrays.asList(category)));

		window.comboBox("categoryComboBox").selectItem(0);
		window.textBox("noteTextArea").enterText("Test note");
		window.button(JButtonMatcher.withText("Save")).click();

		verify(noteController).newNote("Test note", "1");
	}

	@Test
	@GUITest
	public void testSaveButtonWithNullCategory() {
		window.textBox("noteTextArea").enterText("Test note");
		window.button(JButtonMatcher.withText("Save")).click();

		verify(noteController).newNote("Test note", null);
	}

	@Test
	@GUITest
	public void testSelectNoteEnablesButtons() {
		Note note = new Note("Test note", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> noteSwingView.showAllNotes(Arrays.asList(note)));

		window.list("notesList").selectItem(0);

		window.button(JButtonMatcher.withText("Edit")).requireEnabled();
		window.button(JButtonMatcher.withText("Delete")).requireEnabled();
		assertThat(window.button(JButtonMatcher.withText("Edit")).isEnabled()).isTrue();
	}

	@Test
	@GUITest
	public void testDeleteButtonCallsController() {
		Note note = new Note("Test note", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> noteSwingView.showAllNotes(Arrays.asList(note)));

		window.list("notesList").selectItem(0);
		window.button(JButtonMatcher.withText("Delete")).click();

		verify(noteController).deleteNote("1");
	}

	@Test
	@GUITest
	public void testEditAndUpdateFlow() {
		Category category = new Category("WORK");
		category.setId("cat1");
		Note note = new Note("Original", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> {
			noteSwingView.showAllCategories(Arrays.asList(category));
			noteSwingView.showAllNotes(Arrays.asList(note));
		});

		window.list("notesList").selectItem(0);
		window.button(JButtonMatcher.withText("Edit")).click();

		assertThat(window.textBox("noteTextArea").text()).isEqualTo("Original");
		assertThat(window.button("saveButton").text()).isEqualTo("Update");

		window.textBox("noteTextArea").deleteText();
		window.textBox("noteTextArea").enterText("Updated");
		window.button(JButtonMatcher.withText("Update")).click();

		verify(noteController).updateNote("1", "Updated", "cat1");
	}

	@Test
	@GUITest
	public void testNoteUpdatedRefreshesList() {
		Note note = new Note("Original", "cat1");
		note.setId("1");
		GuiActionRunner.execute(() -> noteSwingView.showAllNotes(Arrays.asList(note)));

		Note updated = new Note("Updated", "cat1");
		updated.setId("1");
		GuiActionRunner.execute(() -> noteSwingView.noteUpdated(updated));

		assertThat(window.list("notesList").contents()[0]).contains("Updated");
	}

	@Test
	@GUITest
	public void testNoteDeletedRemovesFromList() {
		Note note1 = new Note("Note 1", "cat1");
		note1.setId("1");
		Note note2 = new Note("Note 2", "cat1");
		note2.setId("2");
		GuiActionRunner.execute(() -> noteSwingView.showAllNotes(Arrays.asList(note1, note2)));

		GuiActionRunner.execute(() -> noteSwingView.noteDeleted(note1));

		assertThat(window.list("notesList").contents()).hasSize(1);
	}

	@Test
	@GUITest
	public void testShowError() {
		GuiActionRunner.execute(() -> noteSwingView.showError("Error message"));
		assertThat(window.label("errorLabel").text()).isEqualTo("Error message");
	}

	@Test
	@GUITest
	public void testNoteUpdatedWhenIdNotFound() {
		Note existing = new Note("existing", "cat1");
		existing.setId("id1");
		Note different = new Note("other", "cat1");
		different.setId("different-id");
		GuiActionRunner.execute(() -> {
			noteSwingView.noteAdded(existing);
			noteSwingView.noteUpdated(different);
		});
		assertThat(window.list("notesList").contents()).hasSize(1);
	}

	@Test
	@GUITest
	public void testNoteDeletedWhenIdNotFound() {
		Note existing = new Note("existing", "cat1");
		existing.setId("id1");
		Note different = new Note("other", "cat1");
		different.setId("different-id");
		GuiActionRunner.execute(() -> {
			noteSwingView.noteAdded(existing);
			noteSwingView.noteDeleted(different);
		});
		assertThat(window.list("notesList").contents()).hasSize(1);
	}
}