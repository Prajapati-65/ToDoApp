var todoApp = angular.module("toDoApp");

todoApp.factory('homeService', function($http, $location){
	
	var cards = {};
	
	cards.service=function(url,method,token,note) {
		
		return $http({
		
		    method: method,
		    url: url,
		    data:note,
		    headers: {
		        'token': token
		    }
		});
	}
	
	return cards;

});