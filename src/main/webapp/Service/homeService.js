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
	
	cards.deleteNoteForever = function(id){
		return $http({
			method : "DELETE",
			url : 'user/delete/'+id,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	cards.updateNote=function(notes){
		return $http({
			method : "PUT",
			url : 'user/update',
			headers: {
				'token':localStorage.getItem('token')
			},
			data: notes
		})
	}
	
	cards.allNotes = function() {
		return $http({
			method : "GET",
			url : 'user/getallnotes',
			headers: {
				'token':localStorage.getItem('token')
			}
		});
	}
	
	return cards;
});
