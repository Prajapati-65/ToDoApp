package com.bridgeit.springToDoApp.Note.DAO;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Log;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;


/**
 * @author Om Prajapti
 *
 */
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
			transaction.commit();
			session.close();
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
			session.close();
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
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Note> notes = criteria.list();
		return notes;
	}

	
	
	
	public int saveCollborator(Collaborater collborate) {
		int collboratorId=0;
		Session session=factory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		collboratorId=(Integer) session.save(collborate);
		transaction.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
		return collboratorId;
	}

	
	public List<User> getListOfUser(int noteId) {
		Session session = factory.openSession();
		Query querycollab = session.createQuery("select c.shareId from Collaborater c where c.noteId= " + noteId);
		List<User> listOfSharedCollaborators = querycollab.list();
		System.out.println("listOfSharedCollaborators "+listOfSharedCollaborators);
		session.close();
		return listOfSharedCollaborators;	
	}
	
	
	public List<Note> getCollboratedNotes(int userId) {
		Session session = factory.openSession();
		Query query = session.createQuery("select c.noteId from Collaborater c where c.shareId= " + userId);
		List<Note> colllboratedNotes = query.list();
		session.close();
		return colllboratedNotes;
	}
	
	
	public int removeCollborator(int shareWith,int noteId) {
		Session session = factory.openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createQuery("delete  Collaborater c where c.shareId= "+shareWith+" and c.noteId="+noteId );
		int status=query.executeUpdate();
		session.close();
		return status;
	}
	
	
	public void deleteScheduleNote() {
		Session session = factory.openSession();
		Date deleteTime = new Date(System.currentTimeMillis() - 7*24*60*60*1000);
		System.out.println("delete time is : "+deleteTime);
		String trash= "true";
		Transaction transaction = session.beginTransaction();
		try {
			Query deleteNote = session.createQuery("delete from Note where modifiedDate<:deleteTime and deleteStatus=:trash");
			deleteNote.setParameter("deleteTime", deleteTime);
			deleteNote.setParameter("trash", trash);
			int count = deleteNote.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void activity(Log log) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(log);
		session.close();
	}
	
	public List<Log> getAllLog(User user) {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(Log.class);
		criteria.add(Restrictions.eq("logUser", user));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Log> logs = criteria.list();
		return logs;
	}
}
