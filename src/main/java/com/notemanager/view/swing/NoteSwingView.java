package com.notemanager.view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.ListSelectionModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.notemanager.controller.NoteController;
import com.notemanager.model.Category;
import com.notemanager.model.Note;

public class NoteSwingView extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean editMode = false;
	private String editingNoteId = null;

	private JComboBox<CategoryItem> categoryComboBox;
	private DefaultComboBoxModel<CategoryItem> categoryComboBoxModel;
	private JTextArea noteTextArea;
	private JButton saveButton;
	private JList<Note> notesList;
	private DefaultListModel<Note> notesListModel;
	private JButton editButton;
	private JButton deleteButton;

	private NoteController noteController;

	public NoteSwingView() {
		setTitle("Note Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setLayout(new BorderLayout());

		categoryComboBoxModel = new DefaultComboBoxModel<>();
		categoryComboBox = new JComboBox<>(categoryComboBoxModel);
		categoryComboBox.setName("categoryComboBox");

		noteTextArea = new JTextArea(5, 30);
		noteTextArea.setName("noteTextArea");

		saveButton = new JButton("Save");
		saveButton.setName("saveButton");
		saveButton.addActionListener(e -> {
			String text = noteTextArea.getText();
			CategoryItem selectedCategory = (CategoryItem) categoryComboBox.getSelectedItem();
			String categoryId = selectedCategory != null ? selectedCategory.getId() : null;
			noteController.newNote(text, categoryId);
		});

		notesListModel = new DefaultListModel<>();
		notesList = new JList<>(notesListModel);
		notesList.setName("notesList");
		
		notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notesList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				boolean hasSelection = notesList.getSelectedIndex() != -1;
				editButton.setEnabled(hasSelection);
				deleteButton.setEnabled(hasSelection);
			}
		});

		editButton = new JButton("Edit");
		editButton.setName("editButton");
		editButton.setEnabled(false);
		
		editButton.addActionListener(e -> {
			Note selected = notesList.getSelectedValue();
			if (selected != null) {
				editMode = true;
				editingNoteId = selected.getId();
				noteTextArea.setText(selected.getText());
				saveButton.setText("Update");
			}
		});

		deleteButton = new JButton("Delete");
		deleteButton.setName("deleteButton");
		deleteButton.setEnabled(false);
		
		deleteButton.addActionListener(e -> {
			Note selected = notesList.getSelectedValue();
			if (selected != null) {
				noteController.deleteNote(selected.getId());
			}
		});

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(categoryComboBox, BorderLayout.NORTH);
		topPanel.add(new JScrollPane(noteTextArea), BorderLayout.CENTER);
		topPanel.add(saveButton, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(new JScrollPane(notesList), BorderLayout.CENTER);
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void setNoteController(NoteController noteController) {
		this.noteController = noteController;
	}

	public void showAllCategories(List<Category> categories) {
		categoryComboBoxModel.removeAllElements();
		for (Category category : categories) {
			categoryComboBoxModel.addElement(new CategoryItem(category));
		}
	}
	
	public void showAllNotes(List<Note> notes) {
		notesListModel.clear();
		for (Note note : notes) {
			notesListModel.addElement(note);
		}
	}

	private static class CategoryItem {
		private final Category category;

		public CategoryItem(Category category) {
			this.category = category;
		}

		public String getId() {
			return category.getId();
		}

		@Override
		public String toString() {
			return category.getName();
		}
		
	}
}