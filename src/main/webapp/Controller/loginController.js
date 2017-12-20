var toDoApp = angular.module('toDoApp');

toDoApp.controller('loginController',function($scope, loginService, $location) {

			$scope.user = {};

			$scope.loginUser = function() {

				var loginVariable = loginService.loginuser($scope.user);
				
				loginVariable.then(function(response) {
					console.log(response.data.message);
					localStorage.setItem('token',response.data.message)
					$location.path('/home')
				}, function(response) {
					$scope.errorMessage = response.data.message;
				});
			}
		});
