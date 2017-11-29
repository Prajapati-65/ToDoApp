package com.bridgeit.springToDoApp.Note.Controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.Note.Service.NoteService;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;
import com.bridgeit.springToDoApp.Utility.token.VerifiedJWT;

@RestController
@RequestMapping(value = "/user")
public class NoteController {
	
	@Autowired
	NoteService noteService;
	
	@Autowired
	UserService userService;
	
	
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
			CustomResponse  customResponse = new CustomResponse();
			customResponse.setMessage("Note create successfully");
			return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		}
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Please login first");  
		return new ResponseEntity<Response>(customResponse, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int noteId) {
		Note note = new Note();
		note.setNoteId(noteId);
		
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Response> update(@RequestBody Note note) {

		int noteid =note.getNoteId();
		System.out.println("noteid: " + noteid);
		Note noteById = noteService.getNoteById(noteid);

		Date createDate = noteById.getCreatedDate();
		note.setCreatedDate(createDate);
		
		User user = noteById.getUser();
		note.setUser(user);
		
		Date modifiedDate = new Date();
		note.setModifiedDate(modifiedDate);
		
		boolean isUpdated = noteService.updateNote(note);
		
		CustomResponse customResponse = new CustomResponse();
		System.out.println("is update "+isUpdated);
		if (isUpdated != true) {
			
			customResponse.setMessage("Note could not be updated..."); 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
			
		} else {
			
			customResponse.setMessage("Note updated successfully...");
			return ResponseEntity.ok(customResponse);
		}
	}

	/**
	 * @param note(note who's color has to be changes)
	 * @param user(user who is login)
	 * @return OK Status
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


	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public ResponseEntity<List> getAllNotes(HttpServletRequest request) {
		
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		CustomResponse customResponse = new CustomResponse();
		
		List<Note> allNotes = noteService.getAllNotes(user);

		customResponse.setMessage("note found.");
		customResponse.setNotes(allNotes);

		return ResponseEntity.ok(customResponse.getNotes());
	}


	@RequestMapping(value = "/collaborate", method = RequestMethod.POST)
	public ResponseEntity<List<User>> getNotes(@RequestBody Collaborater collborator, HttpServletRequest request){
		List<User> userList=new ArrayList<User>();
		
		Collaborater collaborate =new Collaborater();
		
		Note note= (Note) collborator.getNoteId();
		User shareUser= (User) collborator.getShareId();
		User owner= (User) collborator.getOwnerId();
		
		shareUser=userService.emailValidate(shareUser.getEmail());
		
		
		String token=request.getHeader("token");
		User user=userService.getUserById(VerifiedJWT.verify(token));
		
		
		
		userList =	noteService.getListOfUser(note.getNoteId());
		
		if(user!=null) 
		{
			if(shareUser!=null && shareUser.getId()!=owner.getId()) 
			{
					int i=0;
					int flag=0;
					while(userList.size()>i) 
					{
						if(userList.get(i).getId()==shareUser.getId())
						{
							flag=1;
						}
						i++;
					}
					if(flag==0) 
					{
						collaborate.setNoteId(note);
						collaborate.setOwnerId(owner);
						collaborate.setShareId(shareUser);
						if(noteService.saveCollborator(collaborate)>0) {
							userList.add(shareUser);
						}
						else{
							 ResponseEntity.ok(userList);
						}
					}
			}
		}
		return ResponseEntity.ok(userList);
	}
	
	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Note note, HttpServletRequest request){
		String token=request.getHeader("token");
		User user=userService.getUserById(VerifiedJWT.verify(token));
		if(user!=null) {
		Note noteComplete=noteService.getNoteById(note.getNoteId());
		User owner=noteComplete.getUser();
		return ResponseEntity.ok(owner);
		}
		else{
			return ResponseEntity.ok(null);
		}
	}
	
	@RequestMapping(value = "/removeCollborator", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> removeCollborator(@RequestBody Collaborater collborator, HttpServletRequest request){
		CustomResponse response=new CustomResponse();
		int shareWith=collborator.getShareId().getId();
		int noteId=collborator.getNoteId().getNoteId();
		Note note=noteService.getNoteById(noteId);
		User owner=note.getUser();
		String token=request.getHeader("token");
		User user=userService.getUserById(VerifiedJWT.verify(token));
		if(user!=null) {
				if(owner.getId()!=shareWith){
					if(noteService.removeCollborator(shareWith, noteId)>0){
						response.setMessage("Collborator removed");
						return ResponseEntity.ok(response);
					}else{
						response.setMessage("database problem");
						return ResponseEntity.ok(response);
					}
				}else{
					response.setMessage("Can't remove owner");
					return ResponseEntity.ok(response);
				}
		
	    }else{
	    	response.setMessage("Token expired");
			return ResponseEntity.ok(response);
	    }
	}

}
