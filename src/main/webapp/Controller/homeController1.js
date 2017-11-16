//YOu can delete this file

var toDoApp = angular.module('toDoApp');

toDoApp
		.controller(
				'homeController',
				function($scope, $location, homeService) {

					$scope.navbarBackground = 'yellow';

					$scope.navbarBrandColor = 'black';

					$scope.navbarBrand = 'Fundoo Keep';

					$scope.pinSrc = 'images/pin.svg';

					$scope.showArchives = false;

					$scope.notes = homeService.notes;
					if ($scope.notes.length == 0) {
						var httpNotes = homeService.getAllNotes();

						httpNotes.then(function(response) {
							$scope.notes = response.data;
							homeService.notes = response.data;
						}, function(response) {
							console.log(response.status);
							if (response.status = '511') {
								$location.path('/loginpage')
							}
						});
					}

					$scope.colors = ['white', '#FC7457', '#FCC357', '#F3F84B',
							'#92FF7C', '#94F2CC', '#16B7F7', '#719CF2',
							'#E4ADF5', '#FCD0F4', '#B8B4B7', '#E8E6E7'];

					$scope.toggleSidebar = function() {

						var width = $('#sidebar-wrapper').width();
						console.log(width);
						if (width == '270') {
							document.getElementById("sidebar-wrapper").style.width = "0px";
							document.getElementById("content-wrapper-inside").style.marginLeft = "150px";
						} else {
							document.getElementById("sidebar-wrapper").style.width = "270px";
							document.getElementById("content-wrapper-inside").style.marginLeft = "300px";
						}

					}

					$scope.pinSrc = 'images/pin.svg';

					$scope.changePinSrc = function() {
						if ($scope.pinSrc == 'images/pin.svg') {
							$scope.pinSrc = 'images/bluepin.svg';
						} else {
							$scope.pinSrc = 'images/pin.svg'
						}
					}

					$scope.createNote = function(isArchived) {
						var newNote = {};
						newNote.title = $('#note-title-input').html();
						newNote.description = $('#note-body-input').html();
						newNote.isArchived = isArchived;
						newNote.color = $('#actual-input-div').css(
								"background-color");

						if ($scope.pinSrc == 'images/bluepin.svg'
								&& !isArchived) {
							newNote.isPinned = true;
						} else {
							newNote.isPinned = false;
						}

						var httpCreateNote = homeService.createNote(newNote);

						httpCreateNote.then(function() {
							console.log('Note successfully created');
							$('#note-title-input').html('');
							$('#note-body-input').html('');
							$('#actual-input-div').css("background-color",
									'white');
							$scope.pinSrc = 'images/pin.svg';
							$scope.showInput = false;
						}, function() {
							console.log('Failed');
						});
					}

					$scope.archiveImg = 'images/archive.svg';

					$scope.updateNote = function(note) {
						var httpUpdate = homeService.updateNote(note);
						httpUpdate.then(function(response) {

						}, function(response) {

						});
					}

					$scope.updateModalNote = function(note) {
						note.title = $('#modal-note-title').html();
						note.description = $('#modal-note-body').html();

						var httpUpdate = homeService.updateNote(note);
						httpUpdate.then(function(response) {

						}, function(response) {

						});
					}

					$scope.showArchives = false;

					$scope.redirectToArchive = function() {
						$location.path('/archive');
					}

					$scope.signOut = function() {
						var httpSignOut = homeService.signOut();

						httpSignOut.then(function() {
							$location.path('/loginpage');
						});
					}
				});