var toDoApp = angular.module('toDoApp');

toDoApp.directive('topBar', function() {
	return {
		templateUrl : 'Template/TopNavigationBar.html'
	}
});