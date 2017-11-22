var todoApp = angular.module("toDoApp");

todoApp.factory('homeService', function($http, $location){
	
	var cards = {};
	
	cards.addNote = function(notes) {
		return $http({
			method : "POST",
			url : 'user/createNote',
			headers: {
				'token':localStorage.getItem('token')
			},
			data : notes
		})
	}

	
	cards.changeColor=function(note){
		return $http({
			method : "POST",
			url : 'user/changeColor',
			headers : {
				'token' : localStorage.getItem('token')
			},
			data : note
		})
	}
	
	cards.deleteNoteForever = function(id){
		return $http({
			method : "DELETE",
			url : 'user/delete/'+id,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	cards.logout = function(){
		return $http({
			method : "POST",
			url : 'logout',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	cards.updateNote=function(notes){
		console.log("inside the update service...");
		return $http({
			method : "POST",
			url : 'user/update',
			headers: {
				'token':localStorage.getItem('token')
			},
			data: notes
		})
	}
	
	cards.allNotes = function() {
		console.log("home page service2 all notes");
		return $http({
			method : "GET",
			url : 'user/getallnotes',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	return cards;

});