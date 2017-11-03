var toDoApp = angular.module('toDoApp');

toDoApp.factory('registrationService', function($http, $location) {
	var register = {};
	
	register.registeruser = function(user) {
		$http({
			method : "POST",
			url : 'registerUser',
			data : user
		});
	}
	return register;
});