var toDoApp = angular.module('toDoApp');

toDoApp.factory('loginService', function($http) {
	
	var abc = {};
	
	abc.xyz = function(user) {
		return $http({
			method : "POST",
			url : 'login',
			data : user
		});
	}
	return abc;
});