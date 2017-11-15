var toDoApp = angular.module('toDoApp');

toDoApp.controller('registerController', function($scope, registrationService, $location) {
	$scope.user = {};
	$scope.registerUser = function() {

		var regVariable = registrationService.registeruser($scope.user);

		regVariable.then(function(response) {
			$location.path('/info')
		}, function(response) {
			$scope.errorMessage = response.data.message;
		});
	}
});
