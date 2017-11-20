var toDo = angular.module('toDo');

toDo.controller('homeController',function($scope, homePageService, $location){
					
					$scope.showSideBar = true;
					$scope.sidebarToggle = function() {
						$scope.showSideBar = !$scope.showSideBar;
					}
					
					$scope.AddNoteBox = false;
					
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}
					
					
					getAllNotes();
					
					function getAllNotes() {
						var b = homePageService.allNotes();
						b.then(function(response) {
							console.log(response.data);
							$scope.userNotes = response.data;
						}, function(response) {
						});
					}

					
					$scope.deleteNote=function(note){
						note.noteStatus="delete";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					$scope.deleteNoteForever = function(id) {
						console.log("id is ..." + id);
						var a = homePageService.deleteNoteForever(id);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					$scope.updateNote = function(note) {
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					$scope.addNote = function() {
						$scope.notes = {};
						$scope.notes.title = document.getElementById("notetitle").innerHTML;
						$scope.notes.description = document.getElementById("noteDescription").innerHTML;
						$scope.notes.pin = $scope.pinStatus;
						$scope.notes.noteStatus = "note";
						var a = homePageService.addNote($scope.notes);
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							getAllNotes();
						}, function(response) {
							});
					}	
	
					$scope.logout = function() {
						console.log("this is logout ");
						localStorage.removeItem('token');
						$location.path('/login')
					}

				});