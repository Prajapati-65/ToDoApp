var toDoApp = angular.module('toDoApp');

toDoApp.controller('resetController', function($scope, forgotpasswordService,
		$location) {

	$scope.sendLink = function() {

		var httpSendLink = forgotpasswordService.sendLinkToEmail($scope.user);

		httpSendLink.then(function(response) {
			$location.path('resetpassword');
		});
	}

	$scope.resetPassword = function() {

		var httpReset = forgotpasswordService.resetPassword($scope.user,$scope.token);
		
		httpReset.then(function(response) {
				$location.path('login');
		});
	}
});