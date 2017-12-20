var toDoApp = angular.module('toDoApp');

toDoApp.controller('dummyController', function($scope, $http, $location) {

	$http({
		method : "GET",
		url : 'social',
	}).then(function(response) {
		localStorage.setItem('token',response.data.message);
		$location.path("home");
	})
});
