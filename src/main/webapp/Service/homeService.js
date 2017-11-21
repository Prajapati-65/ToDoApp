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
	return cards;
});
