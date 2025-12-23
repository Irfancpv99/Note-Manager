package com.notemanager.repository;

import java.util.List;

import com.notemanager.model.Note;

public interface NoteRepository {

	Note save(Note note);

	Note findById(String id);

	List<Note> findAll();

	List<Note> findByCategoryId(String categoryId);

	void delete(String id);
}