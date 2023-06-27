package com.notesapplication;

import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.notesapplication.model.Note;
import com.notesapplication.repository.NoteRepository;
import com.notesapplication.repository.NoteRepositoryImpl;

public class NotesApplication {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static final NoteRepository noteRepository = new NoteRepositoryImpl(sessionFactory);

	public static void main(String[] args) {
		noteRepository.setSessionFactory(sessionFactory);
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("1. Create note");
			System.out.println("2. View note");
			System.out.println("3. List all notes");
			System.out.println("4. Update note");
			System.out.println("5. Delete note");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				createNote();
				break;
			case 2:
				viewNote();
				break;
			case 3:
				listAllNotes();
				break;
			case 4:
				updateNote();
				break;
			case 5:
				deleteNote();
				break;
			case 6:
				exit = true;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}

		sessionFactory.close();
	}

	private static void createNote() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter title: ");
		String title = scanner.nextLine();

		System.out.print("Enter content: ");
		String content = scanner.nextLine();

		Note note = new Note();
		note.setTitle(title);
		note.setContent(content);

		noteRepository.create(note);

		System.out.println("Note created successfully!");
	}

	private static void viewNote() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter note ID: ");
		int id = scanner.nextInt();

		Note note = noteRepository.findById(id);

		if (note != null) {
			System.out.println("ID: " + note.getId());
			System.out.println("Title: " + note.getTitle());
			System.out.println("Content: " + note.getContent());
		} else {
			System.out.println("Note not found!");
		}
	}

	private static void listAllNotes() {
		List<Note> notes = noteRepository.findAll();

		if (!notes.isEmpty()) {
			System.out.println("All Notes:");
			for (Note note : notes) {
				System.out.println("ID: " + note.getId());
				System.out.println("Title: " + note.getTitle());
				System.out.println("Content: " + note.getContent());
				System.out.println("------------------------");
			}
		} else {
			System.out.println("No notes found!");
		}
	}

	private static void updateNote() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter note ID: ");
		int id = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		Note note = noteRepository.findById(id);

		if (note != null) {
			System.out.print("Enter new title: ");
			String title = scanner.nextLine();

			System.out.print("Enter new content: ");
			String content = scanner.nextLine();

			note.setTitle(title);
			note.setContent(content);

			noteRepository.update(note);

			System.out.println("Note updated successfully!");
		} else {
			System.out.println("Note not found!");
		}
	}

	private static void deleteNote() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter note ID: ");
		int id = scanner.nextInt();

		Note note = noteRepository.findById(id);

		if (note != null) {
			noteRepository.delete(note);
			System.out.println("Note deleted successfully!");
		} else {
			System.out.println("Note not found!");
		}
	}
}
