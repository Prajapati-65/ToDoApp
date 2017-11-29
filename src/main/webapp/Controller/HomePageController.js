var toDo = angular.module('toDo');

toDo
		.controller(
				'homeController',
				function($scope, homePageService, $uibModal, toastr, $location,
						$filter, $interval, fileReader, $state) {

					
					
					
					$scope.Reminder = false;

					/* add a new note */
					$scope.addNote = function() {
						$scope.note = {};
						$scope.note.title = document.getElementById("notetitle").innerHTML;
						$scope.note.description = document.getElementById("noteDescription").innerHTML;

						if ($scope.note.title == ""&& $scope.note.description == ""&& $scope.imageSrc == "") {
							$scope.pinStatus = false;
							$scope.AddReminder = '';
							$scope.AddNoteColor = "#ffffff";
							$scope.AddNoteBox = false;

						} else if ($scope.note.title != ""|| $scope.note.description != ""|| $scope.note.image != "") {
							$scope.note.pin = $scope.pinStatus;
							$scope.note.noteStatus = "true";
							$scope.note.reminderStatus = $scope.AddReminder;
							$scope.note.archiveStatus = "false";
							$scope.note.deleteStatus = "false";
							$scope.note.image = $scope.imageSrc;
							$scope.imageSrc = "";
							$scope.note.noteColor = $scope.AddNoteColor;

							var a = homePageService.addNote($scope.notes);
							a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							$scope.AddReminder = '';
							$scope.AddNoteColor = "#ffffff";
							$scope.removeImage();
							toastr.success('Note added','successfully');
							getAllNotes();
							}, function(response) {
						});
					}
				}


					/* add a new note to archive */
					$scope.addArchiveNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						if ($scope.notes.title == ""
								&& $scope.notes.description == "") {
							$scope.pinStatus = false;
							$scope.AddNoteColor = "#ffffff";
							$scope.AddNoteBox = false;
						} else if ($scope.notes.title != ""
								|| $scope.notes.description != "") {
							$scope.notes.pin = "false";
							$scope.notes.noteStatus = "false";
							$scope.notes.reminderStatus = $scope.AddReminder;
							$scope.notes.deleteStatus = "false";
							$scope.notes.reminderStatus = "false";
							var a = homePageService.addNote($scope.notes);
							a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							$scope.AddNoteColor = "#ffffff";
							$scope.AddReminder = '';
								getAllNotes();
							}, function(response) {
						});
					}
				}

					

				});