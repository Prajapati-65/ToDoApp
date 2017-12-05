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

toDoApp.directive("ngFileSelect", function(fileReader, $timeout) {
	return {
		scope : {
			ngModel : '='
		},
		link : function($scope, el) {
			function getFile(file) {
				fileReader.readAsDataUrl(file, $scope).then(function(result) {
					$timeout(function() {
						$scope.ngModel = result;
					});
				});
			}

			el.bind("change", function(e) {
				var file = (e.srcElement || e.target).files[0];
				getFile(file);
			});
		}
	};
});

toDoApp.factory('fileReader', function($q, $log) {

	var onLoad = function(reader, deferred, scope) {
		return function() {
			scope.$apply(function() {
				deferred.resolve(reader.result);
			});
		};
	};

	var onError = function(reader, deferred, scope) {
		return function() {
			scope.$apply(function() {
				deferred.reject(reader.result);
			});
		};
	};

	var onProgress = function(reader, scope) {
		return function(event) {
			scope.$broadcast("fileProgress", {
				total : event.total,
				loaded : event.loaded
			});
		};
	};

	var getReader = function(deferred, scope) {
		var reader = new FileReader();
		reader.onload = onLoad(reader, deferred, scope);
		reader.onerror = onError(reader, deferred, scope);
		reader.onprogress = onProgress(reader, scope);
		return reader;
	};

	var readAsDataURL = function(file, scope) {
		var deferred = $q.defer();
		var reader = getReader(deferred, scope);
		reader.readAsDataURL(file);

		return deferred.promise;
	};

	return {
		readAsDataUrl : readAsDataURL
	};
});
