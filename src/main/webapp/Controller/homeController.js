var toDoApp = angular.module('toDoApp');
var modalInstance;
toDoApp.controller('homeController', function($scope, homeService, $location, $state) {
					
					/*toggle side bar*/
					$scope.showSideBar = true;
					$scope.sidebarToggle = function() {
						if($scope.showSideBar){
							$scope.showSideBar=false;
							document.getElementById("mainWrapper").style.paddingLeft = "200px";
						}
						else{
							$scope.showSideBar = true;
							document.getElementById("mainWrapper").style.paddingLeft = "300px";
						}
					}
					
					//toggle side bar
					$scope.toggleSideBar = function() {
						var width = $('#sideToggle').width();
						console.log(width);
						if (width == '250') {
							document.getElementById("sideToggle").style.width = "0px";
						} else {
							document.getElementById("sideToggle").style.width = "250px";
						}
					}
					
					//add a new note 
					$scope.addNote = function() {
						$scope.note = {};
						$scope.note.title = document.getElementById("notetitle").innerHTML;
						$scope.note.description = document.getElementById("noteDescription").innerHTML;
						$scope.note.noteColor = $scope.AddNoteColor;
						console.log($scope.note);

						var a = homeService.addNote($scope.note);
						a.then(function(response) {
						document.getElementById("notetitle").innerHTML = "";
						document.getElementById("noteDescription").innerHTML = "";
								getAllNotes();
							}, function(response) {
						});
					}
					
					if ($state.current.name == "home") {
						$scope.navBarColor = "#ffbb33";
						$scope.navBarHeading = "Fundoo Keep";
					}

					/* toggle AddNote box */
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}

					getAllNotes();

					/* display notes */
					function getAllNotes() {
						var b = homeService.allNotes();
						b.then(function(response) {
							console.log("aasdjsad sdfsdkfjsdfs ---> "+response.data);
							$scope.allGetNotes = response.data;
						}, function(response) {
						});
					}

					/* Add notes to trash */
					$scope.deleteNote = function(note) {
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* delete note forever */
					$scope.deleteNoteForever = function(id) {
						console.log("id is ..." + id);
						var a = homeService.deleteNoteForever(id);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* update the note */
					$scope.updateNote = function(note) {
						var a = homeService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					/* logout user */
					$scope.logout = function() {
						var a = homeService.logout();
						a.then(function(response) {
							localStorage.removeItem('token');
							$location.path('/login');
						})
					}
					
				});
