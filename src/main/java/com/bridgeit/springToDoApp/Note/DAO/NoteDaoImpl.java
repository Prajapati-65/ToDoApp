package com.bridgeit.springToDoApp.Note.DAO;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;


public class NoteDaoImpl implements NoteDao {

	@Autowired
	SessionFactory factory;

	@Override
	public int createNote(Note note) {
		int noteId = 0;
		Session session = factory.getCurrentSession();
		try {
			noteId = (Integer) session.save(note);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noteId;
	}

	public boolean updateNote(Note note) {
		Session session = factory.getCurrentSession();
		try {
			session.saveOrUpdate(note);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	
	public Note getNoteById(int noteId) {
		Session session = factory.getCurrentSession();
		Note note = session.get(Note.class, noteId);
		return note;
	}
	

	public void deleteNote(Note note) {
		Session session = factory.getCurrentSession();
		session.delete(note);
	}
	

	public List<Note> getAllNotes(User user) {
		Session session = factory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.addOrder(Order.desc("modifiedDate"));
		@SuppressWarnings("unchecked")
		List<Note> notes = criteria.list();
		return notes;
	}
	
}
