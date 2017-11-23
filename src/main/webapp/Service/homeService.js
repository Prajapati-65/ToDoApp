var todoApp = angular.module("toDoApp");

todoApp.factory('homeService', function($http, $location){
	
	var cards = {};
	
	
	cards.allservice = function(method,url,note) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			},
			data : note
		})
	}
	
	
	cards.addNote = function(note) {
		return $http({
			method : "POST",
			url : 'user/createNote',
			headers: {
				'token':localStorage.getItem('token')
			},
			data : note
		})
	}

	cards.allNotes = function() {
		return $http({
			method : "GET",
			url : 'user/getallnotes',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	cards.updateNote=function(note){
		return $http({
			method : "POST",
			url : 'user/update',
			headers: {
				'token':localStorage.getItem('token')
			},
			data: note
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
	
	cards.logout = function(){
		return $http({
			method : "POST",
			url : 'logout',
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	return cards;

});