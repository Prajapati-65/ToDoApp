var toDoApp = angular.module('toDoApp');

toDoApp.directive('sideBar', function() {
	return {
		templateUrl : 'Template/SideNavigationBar.html'
	}
});