package com.notesapplication.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.notesapplication.model.Note;

public class NoteRepositoryImpl implements NoteRepository {

	private SessionFactory sessionFactory;

	public NoteRepositoryImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean create(Note note) {
		Session session = sessionFactory.openSession();
		boolean isCreated = false;
		try {
			session.save(note);
			isCreated = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return isCreated;
	}

	@Override
	public Note findById(int id) {
		Session session = sessionFactory.openSession();
		Note note = (Note) session.get(Note.class, id);
		session.close();
		return note;
	}

	@Override
	public List<Note> findAll() {
		Session session = sessionFactory.openSession();
		List<Note> notes = session.createQuery("FROM Note").list();
		session.close();
		return notes;
	}

	@Override
	public void update(Note note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(note);
		transaction.commit();
		session.close();
	}

	@Override
	public void delete(Note note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(note);
		transaction.commit();
		session.close();
	}

	public void setSessionFactory(SessionFactory sessionFactory2) {
		this.sessionFactory = sessionFactory2;
	}
}
