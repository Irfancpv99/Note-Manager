package com.notemanager.unit.view.swing;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

import com.notemanager.view.swing.NoteSwingView;

public class NoteSwingViewTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private NoteSwingView noteSwingView;

	@Override
	protected void onSetUp() {
		GuiActionRunner.execute(() -> {
			noteSwingView = new NoteSwingView();
			return noteSwingView;
		});
		window = new FrameFixture(robot(), noteSwingView);
		window.show();
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
}