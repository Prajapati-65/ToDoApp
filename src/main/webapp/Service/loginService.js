var toDoApp = angular.module('toDoApp');

toDoApp.factory('loginService', function($http) {
	
	var login = {};
	
	login.loginuser = function(user) {
		return $http({
			method : "POST",
			url : 'login',
			data : user
		});
	}
	return login;
});