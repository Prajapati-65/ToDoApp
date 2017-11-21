package com.bridgeit.springToDoApp.Note.DAO;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;


public class NoteDaoImpl implements NoteDao {

	@Autowired
	SessionFactory factory;

	public int createNote(Note note) {
		int noteId = 0;
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			noteId = (Integer) session.save(note);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noteId;
	}

	public boolean updateNote(Note note) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(note);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public Note getNoteById(int noteId) {
		Session session = factory.openSession();
		Note note = session.get(Note.class, noteId);
		return note;
	}

	public boolean deleteNote(Note note) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(note);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Note> getAllNotes(User user) {
		Session session = factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.addOrder(Order.desc("modifiedDate"));
		@SuppressWarnings("unchecked")
		List<Note> notes = criteria.list();
		return notes;
	}
	
}
