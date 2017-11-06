var toDoApp = angular.module('toDoApp');

toDoApp.controller('loginController',
		function($scope, loginService, $location) {

			$scope.user = {};

			$scope.loginUser = function() {

				var loginVariable = loginService.loginuser($scope.user);

				loginVariable.then(function(responce) {
					$location.path('/login')
				});
			}
			a
		});
