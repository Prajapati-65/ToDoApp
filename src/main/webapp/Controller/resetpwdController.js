var toDoApp = angular.module('toDoApp');

toDoApp.controller('resetController', function($scope, forgotpasswordService,
		$location) {

	$scope.sendLink = function() {

		var httpSendLink = forgotpasswordService.sendLinkToEmail($scope.user);

		httpSendLink.then(function(response) {

			if (response.data.status == 5) {
				$scope.response = 'User not found :';
			} else if (response.data.status == -2) {
				$scope.response = 'Mail could not be sent';
			} else if (response.data.status == 2) {
				$location.path('');
			}
		});
	}

	$scope.resetPassword = function() {

		var httpReset = forgotpasswordService.resetPassword($scope.user);

		httpReset.then(function(response) {

			if (response.data.status == 5) {
				$scope.response = 'Invalid OTP or email address';
			} else if (response.data.status == 5) {
				$scope.response = 'Password could not be updated';
			} else if (response.data.status == 2) {
				$location.path('login');
			}
		});
	}
});