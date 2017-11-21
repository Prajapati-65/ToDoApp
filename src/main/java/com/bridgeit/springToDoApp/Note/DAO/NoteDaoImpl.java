package com.bridgeit.springToDoApp.Note.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

		noteId = (int) session.save(note);

		return noteId;
	}

	@Override
	public boolean updateNote(Note note) {
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(note);
		return true;
	}
	

	@Override
	public Note getNoteById(int noteId) {
		Session session = factory.getCurrentSession();

		Note note = session.get(Note.class, noteId);

		return note;
	}

	@Override
	public void deleteNote(Note note) {
		Session session = factory.getCurrentSession();

		session.delete(note);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Note> getAllNotes(User user) {

		Session session = factory.getCurrentSession();

		Criteria criteria = session.createCriteria(Note.class);

		criteria.add(Restrictions.eq("user", user));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Note> notes1 = criteria.list();

		criteria = session.createCriteria(Note.class);
		criteria.createAlias("sharedUsers", "u");
		criteria.add(Restrictions.eq("u.userId", user.getId()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Note> notes2 = criteria.list();

		notes1.removeAll(notes2);
		notes1.addAll(notes2);
		
		return notes1;
	}
	
}
