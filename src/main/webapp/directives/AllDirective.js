var toDoApp = angular.module('toDoApp');

toDoApp.directive('abcBar', function() {
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

