var toDoApp = angular.module('toDoApp');

toDoApp.factory('forgotpasswordService', function($http, $location) {
	var reset = {};

	reset.sendLinkToEmail = function(user) {
		return $http({
			method : "POST",
			url : 'forgotpassword',
			data : user
		})
	}

	reset.resetPassword = function(user,token) {
		return $http({
			method : "PUT",
			url : 'resetpassword',
			data : user,
			headers:{
				token:token
			}
		})
	}
	return reset;
});