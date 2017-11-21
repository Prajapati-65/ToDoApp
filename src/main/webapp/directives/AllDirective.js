var toDoApp = angular.module('toDoApp');

toDoApp.directive('sideBar', function() {
	return {
		templateUrl : 'Template/SideNavigationBar.html'
	}
});

toDoApp.directive('topBar', function() {
	return {
		templateUrl : 'Template/TopNavigationBar.html'
	}
});

toDoApp.directive("addNotes", function() {
    return {
    	templateUrl :'Template/AddNote.html'
    };
});

