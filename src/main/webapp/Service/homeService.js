var todoApp = angular.module("toDoApp");

todoApp.factory('homeService', function($http, $location){
	
	var card = {};
	 
	card.cardUser = function() {
		return $http({
			method : "GET",
			url : 'getallnotes',
			data : note
		});
	}
	return card;
});
