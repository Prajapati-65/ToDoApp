var toDoApp = angular.module('toDoApp');

toDoApp.controller('registerController', function($scope, registrationService, $location) {
	
	$scope.user = {};
	$scope.registerUser = function() {
		
		var a = registrationService.registeruser($scope.user);
		
		a.then(function(responce) {
			$location.path('/login')
		});
	}
});
