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
		templateUrl : 'Template/AddNote.html'
	};
});

toDoApp.directive("pinNote", function() {
	return {
		templateUrl : 'Template/pinNote.html'
	};
});

toDoApp.directive("otherNote", function() {
	return {
		templateUrl : 'Template/otherNote.html'
	};
});

toDoApp.directive('contenteditable', [ '$sce', function($sce) {

	return {
		restrict : 'A',
		require : '?ngModel',

		link : function(scope, element, attrs, ngModel) {
			if (!ngModel)
				return;

			ngModel.$render = function() {
				element.html($sce.getTrustedHtml(ngModel.$viewValue || ''));
				read();
			};

			element.on('blur keyup change', function() {
				scope.$evalAsync(read);
			});

			function read() {
				var html = element.html();
				if (attrs.stripBr && html == '<br>') {
					html = '';
				}
				if (attrs.stripDiv && html == '<div>' && html == '</div>') {
					html = '';
				}
				ngModel.$setViewValue(html);
			}
		}
	};
} ]);
