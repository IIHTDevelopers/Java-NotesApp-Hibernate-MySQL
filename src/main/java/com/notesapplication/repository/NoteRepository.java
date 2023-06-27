package com.notesapplication.repository;

import java.util.List;

import org.hibernate.SessionFactory;

import com.notesapplication.model.Note;

public interface NoteRepository {
	boolean create(Note note);

	Note findById(int id);

	List<Note> findAll();

	void update(Note note);

	void delete(Note note);

	void setSessionFactory(SessionFactory sessionFactory);
}
