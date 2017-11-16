var todoApp = angular.module("toDoApp");

todoApp.factory('homeService', function($http, $location){
	
	var cards = {};
	
	cards.cardUser = function() {
		return $http({
			method : "GET",
			url : 'user/getallnotes',
			data : note
		});
	}
	return cards;
});
