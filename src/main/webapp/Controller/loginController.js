var toDoApp = angular.module('toDoApp');

toDoApp.controller('loginController',
		function($scope, loginService, $location) {

			$scope.user = {};

			$scope.loginUser = function() {

				var loginVariable = loginService.loginuser($scope.user);

				loginVariable.then(function(responce) {
					console.log(responce.data);
					$location.path('/home')
				});
			}

		});
