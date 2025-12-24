package com.notemanager.view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.notemanager.model.Note;

public class NoteSwingView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> categoryComboBox;
	private JTextArea noteTextArea;
	private JButton saveButton;
	private JList<Note> notesList;
	private JButton editButton;
	private JButton deleteButton;

	public NoteSwingView() {
		setTitle("Note Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setLayout(new BorderLayout());

		categoryComboBox = new JComboBox<>(new DefaultComboBoxModel<>());
		categoryComboBox.setName("categoryComboBox");

		noteTextArea = new JTextArea(5, 30);
		noteTextArea.setName("noteTextArea");

		saveButton = new JButton("Save");
		saveButton.setName("saveButton");

		notesList = new JList<>(new DefaultListModel<>());
		notesList.setName("notesList");

		editButton = new JButton("Edit");
		editButton.setName("editButton");
		editButton.setEnabled(false);

		deleteButton = new JButton("Delete");
		deleteButton.setName("deleteButton");
		deleteButton.setEnabled(false);

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
}