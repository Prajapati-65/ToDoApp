package com.bridgeit.springToDoApp.Note.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Log;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.Note.Service.NoteService;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;
import com.bridgeit.springToDoApp.Utility.Jsoup.LinkScrapper;
import com.bridgeit.springToDoApp.Utility.Jsoup.UrlData;
import com.bridgeit.springToDoApp.Utility.token.VerifiedJWT;

/**
 * @author Om Prajapati
 *
 */
@RestController
@RequestMapping(value = "/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	/**
	 * @param note
	 * @param request
	 * 
	 *            using request get the userID and then find the User and the
	 *            and then set the set the user and create the Date object the
	 *            and set the created date and modifiedDate in a note
	 * 
	 * @return integer object a note
	 */
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody Note note, HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		note.setUser(user);
		if (user != null) {
			Date date = new Date();
			note.setCreatedDate(date);
			note.setModifiedDate(date);
			noteService.createNote(note);
			CustomResponse customResponse = new CustomResponse();
			customResponse.setMessage("Note create successfully");
			return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		}
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Please login first");
		return new ResponseEntity<Response>(customResponse, HttpStatus.CONFLICT);
	}

	/**
	 * @param int
	 *            noteId
	 * @return this method return boolean object
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int noteId ,HttpServletRequest request ) {
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		Note note = noteService.getNoteById(noteId);
		if (note.getUser().getId() != id) {
			return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
		}
		
		note.setNoteId(noteId);
		
		Log log = new Log();
		log.setAction("Delete");
		Date currentDate = new Date();
		log.setActionTime(currentDate);
		log.setTitle(note.getTitle());
		log.setLogUser(user);
		log.setReferenceId(note);
		
		noteService.activity(log);

		boolean delete = noteService.deleteNote(note);

		CustomResponse customResponse = new CustomResponse();

		if (delete != true) {

			customResponse.setMessage("Note could not be deleted");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);

		} else {

			customResponse.setMessage("Note deleted successfully");
			return ResponseEntity.ok(customResponse);
		}
	}

	/**
	 * @param Note
	 *            objcet
	 * 
	 *            using Note object we get the note id and then using note id we
	 *            create other Note object and then using New Note object we get
	 *            createdDate object of Date and then set the new object of
	 *            createdDate and using that new note find the User then set the
	 *            user of that note and then find the new modifiedDate object
	 *            and then set the modifiedDate
	 * 
	 * @return this api return the boolean value
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Response> update(@RequestBody Note note) {

		int noteid = note.getNoteId();

		Note noteById = noteService.getNoteById(noteid);

		Date createDate = noteById.getCreatedDate();
		note.setCreatedDate(createDate);

		User user = noteById.getUser();
		note.setUser(user);

		Date modifiedDate = new Date();
		note.setModifiedDate(modifiedDate);
		
		
		
		

		boolean isUpdated = noteService.updateNote(note);

		CustomResponse customResponse = new CustomResponse();

		if (isUpdated != true) {

			customResponse.setMessage("Note could not be updated...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);

		} else {

			customResponse.setMessage("Note updated successfully...");
			return ResponseEntity.ok(customResponse);
		}
	}

	/**
	 * @param Note
	 *            note
	 * @param User
	 *            user
	 * @return this api return boolean value
	 */
	@RequestMapping(value = "/changeColor", method = RequestMethod.POST)
	public ResponseEntity<Response> updateColor(@RequestBody Note note, @RequestAttribute("loginedUser") User user) {
		CustomResponse customResponse = new CustomResponse();
		note.setUser(user);
		noteService.updateNote(note);
		customResponse.setMessage("note updated.");
		customResponse.setNotes(null);
		return ResponseEntity.ok(customResponse);
	}

	/**
	 * @param HttpServletRequest
	 *            request
	 * @return List of AllNotes
	 */
	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public ResponseEntity<List> getAllNotes(HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		CustomResponse customResponse = new CustomResponse();

		List<Note> allNotes = noteService.getAllNotes(user);
		List<Note> collborated = noteService.getCollboratedNotes(user.getId());
		List<Note> listNote = new ArrayList<Note>();
		for (int i = 0; i < allNotes.size(); i++) {
			listNote.add(allNotes.get(i));
		}

		for (int j = 0; j < collborated.size(); j++) {
			listNote.add(collborated.get(j));
		}

		customResponse.setMessage("note found.");
		customResponse.setNotes(listNote);

		return ResponseEntity.ok(customResponse.getNotes());
	}

	
	/*-------------------------------------------------Collaborator Start-----------------------------------------------*/

	/**
	 * @param collborator
	 * @param request
	 * 
	 *            Create collaborator object then find Note and shareUser and
	 *            ownerUser using shareUser we check the validate email and
	 *            using token find the userId and the verify the User object and
	 *            then check the shareUser and owner is valid and set the Note,
	 *            shareUser and owner object to collaborator object
	 * 
	 * @return List of User
	 */
	@RequestMapping(value = "/collaborate", method = RequestMethod.POST)
	public ResponseEntity<List<User>> getNotes(@RequestBody Collaborater collborator, HttpServletRequest request) {
		List<User> userList = new ArrayList<User>();

		Collaborater collaborate = new Collaborater();

		Note note = (Note) collborator.getNoteId();
		User shareUser = (User) collborator.getShareId();
		User owner = (User) collborator.getOwnerId();

		shareUser = userService.emailValidate(shareUser.getEmail());

		String accessToken = request.getHeader("token");

		User user = userService.getUserById(VerifiedJWT.verify(accessToken));

		userList = noteService.getListOfUser(note.getNoteId());

		if (user != null) {
			if (shareUser != null && shareUser.getId() != owner.getId()) {
				int i = 0;
				int variable = 0;
				while (userList.size() > i) {
					if (userList.get(i).getId() == shareUser.getId()) {
						variable = 1;
					}
					i++;
				}
				if (variable == 0) {
					collaborate.setNoteId(note);
					collaborate.setOwnerId(owner);
					collaborate.setShareId(shareUser);
					if (noteService.saveCollborator(collaborate) > 0) {
						userList.add(shareUser);
					} else {
						ResponseEntity.ok(userList);
					}
				}
			}
		}
		return ResponseEntity.ok(userList);
	}

	/**
	 * @param Note
	 *            note
	 * @param request
	 *            using Note object find the get the noteId and then that id
	 *            find the New Note object and using new note object the getUser
	 *            object
	 * 
	 * @return User object
	 */
	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Note note, HttpServletRequest request) {

		String accessToken = request.getHeader("token");

		User user = userService.getUserById(VerifiedJWT.verify(accessToken));

		if (user != null) {
			Note noteComplete = noteService.getNoteById(note.getNoteId());
			User owner = noteComplete.getUser();
			return ResponseEntity.ok(owner);
		} else {
			return ResponseEntity.ok(null);
		}
	}

	/**
	 * @param collborator
	 * @param request
	 * @return User object
	 */
	@RequestMapping(value = "/removeCollborator", method = RequestMethod.POST)
	public ResponseEntity<Response> removeCollborator(@RequestBody Collaborater collborator,
			HttpServletRequest request) {

		CustomResponse response = new CustomResponse();

		int shareWith = collborator.getShareId().getId();
		int noteId = collborator.getNoteId().getNoteId();
		Note note = noteService.getNoteById(noteId);

		User owner = note.getUser();
		String token = request.getHeader("token");

		User user = userService.getUserById(VerifiedJWT.verify(token));
		if (user != null) {
			if (owner.getId() != shareWith) {
				if (noteService.removeCollborator(shareWith, noteId) > 0) {
					response.setMessage("Collborator removed");
					return ResponseEntity.ok(response);

				} else {
					response.setMessage("database problem");
					return ResponseEntity.ok(response);
				}
			} else {
				response.setMessage("Can't remove owner");
				return ResponseEntity.ok(response);
			}
		}

		else {
			response.setMessage("Token expired");
			return ResponseEntity.ok(response);
		}
	}

	/*-----------------------------------------Jsoup UrlData----------------------------------*/
	/**
	 * @param request
	 * @return UrlData
	 */
	@RequestMapping(value = "/getUrlData", method = RequestMethod.POST)
	public ResponseEntity<?> getUrlData(HttpServletRequest request) {

		String urlmap = request.getHeader("url");
		LinkScrapper link = new LinkScrapper();

		UrlData urlData = null;

		try {
			urlData = link.getUrlMetaData(urlmap);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(urlData);
	}

	
	/*--------------------------------------GetAllLog-----------------------------*/
	
	@RequestMapping(value = "/getAllLog", method = RequestMethod.GET)
	public List<Log> getAllLog(HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");
		CustomResponse customResponse = new CustomResponse();
		
		User user = userService.getUserById(userId);
		
		List<Log> allLog = noteService.getAllLog(user);
		
		customResponse.setMessage("Log found");
		customResponse.setStatus(2);
		return allLog;

	}

}
