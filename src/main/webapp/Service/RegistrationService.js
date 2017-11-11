var toDoApp = angular.module('toDoApp');

toDoApp.factory('registrationService', function($http, $location) {
	var register = {};
	
	register.registeruser = function(user) {
		return $http({
			method : "POST",
			url : 'registerUser',
			data : user
		});
	}
	return register;
});