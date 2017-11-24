var toDoApp = angular.module('toDoApp');

toDoApp.controller('homeController', function($scope, homeService, $uibModal, $location, $state) {
					
	
						//toggle side bar
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
						

						$scope.showModal = function(note) {
							$scope.note = note;
							modalInstance = $uibModal.open({
								templateUrl : 'Template/EditNote.html',
								scope : $scope,
								size : 'md'
							});
						};
						
						$scope.changeColor=function(note){
		
							var url = 'user/changeColor';
							var method = 'POST';
							var token =  localStorage.getItem('token');
		
							var a = homeService.service(url,method,token,note);
							a.then(function(response) {
								getAllNotes();
							}, function(response) {
								
							});
						}
						
						$scope.AddNoteColor="#ffffff";
						
						$scope.addNoteColorChange=function(color){
							$scope.AddNoteColor=color;
						}
						
						
						$scope.colors=[
							
							{
								"color":'#ffffff',
								"path":'image/white.png'
							},
							{
								"color":'#e74c3c',
								"path":'image/Red.png'
							},
							{
								"color":'#ff8c1a',
								"path":'image/orange.png'
							},
							{
								"color":'#fcff77',
								"path":'image/lightyellow.png'
							},
							{
								"color":'#80ff80',
								"path":'image/green.png'
							},
							{
								"color":'#99ffff',
								"path":'image/skyblue.png'
							},
							{
								"color":'#0099ff',
								"path":'image/blue.png'
							},
							{
								"color":'#1a53ff',
								"path":'image/darkblue.png'
							},
							{
								"color":'#9966ff',
								"path":'image/purple.png'
							},
							{
								"color":'#ff99cc',
								"path":'image/pink.png'
							},
							{
								"color":'#d9b38c',
								"path":'image/brown.png'
							},
							{
								"color":'#bfbfbf',
								"path":'image/grey.png'
							}
						];
						
						
						if($state.current.name=="home"){
							$scope.navBarColor= "#ffbb33";
							$scope.navBarHeading="Fundoo Keep";
						}
					
						 //add a new note
						$scope.addNote = function() {
						$scope.note = {};
						$scope.note.title = document.getElementById("notetitle").innerHTML;
						$scope.note.description = document.getElementById("noteDescription").innerHTML;
						$scope.note.pin = $scope.pinStatus;
						$scope.note.noteStatus = "true";
						$scope.note.reminderStatus= "true";
						$scope.note.archiveStatus= "false";
						$scope.note.deleteStatus = "false";
						$scope.note.noteColor=$scope.AddNoteColor;
					
						var note = $scope.note;
						var url = 'user/createNote';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							$scope.AddNoteColor="#ffffff";
							getAllNotes();
							}, function(response) {
						});
					}
					
					/*add a new note to archive*/
					$scope.addArchiveNote = function() {
						$scope.note = {};
						$scope.note.title = document.getElementById("notetitle").innerHTML;
						$scope.note.description = document.getElementById("noteDescription").innerHTML;
						$scope.note.pin = "false";
						$scope.note.noteStatus = "false";
						$scope.note.archiveStatus = "true";
						$scope.note.deleteStatus = "false";
						$scope.note.reminderStatus = "false";
						
						var note = $scope.note;
						var url = 'user/createNote';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							getAllNotes();
							}, function(response) {
						});
					}
					
					/* toggle AddNote box */
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}

					getAllNotes();

					/* display notes */
					function getAllNotes() {
						
						var	url='user/getallnotes';
						var	token = localStorage.getItem('token');
		
						var b = homeService.service(url,'GET',token)
						b.then(function(response) {
							console.log("all note are : "+response.data);
							$scope.allGetNotes = response.data;
						}, function(response) {
							$scope.error = response.data.message;
						});
					}
		
					/*update the note*/
					$scope.updateNote = function(note) {
						
					
						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					

					$scope.pinStatus =false;
					
					/*pin unpin the notes*/
					$scope.pinUnpin = function() {
							if($scope.pinStatus == false){
							$scope.pinStatus = true;
						}
						else {
							$scope.pinStatus=false;
						}
					}
					
					
					/*archive notes*/
					$scope.archiveNote=function(note){
						note.noteStatus="false";
						note.archiveStatus="true";
						note.pin="false";
						
						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					/*unarchive notes*/
					$scope.unarchiveNote=function(note){
						note.noteStatus="true";
						note.archiveStatus="false";
						note.pin="false";
						
						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					 /*restore note*/ 
					$scope.restoreNote=function(note){
						note.pin="false";
						note.deleteStatus="false";

						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					 /*Add notes to trash*/ 
					$scope.deleteNote=function(note){
						note.pin="false";
						note.deleteStatus="true";
						note.reminderStatus="false";

						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					/*delete note forever*/
					$scope.deleteNoteForever = function(id) {
						
						console.log("id is ..." +id);
						
						var url = 'user/delete/'+id;
						var method = 'DELETE';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,id);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					/* logout user */
					$scope.logout = function() {
						
						var url = 'logout';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token);
						
						a.then(function(response) {
							localStorage.removeItem('token');
							$location.path('/login');
						})
					}
					
					/*make a copy of the note*/
					$scope.copy = function(note) {
						note.id = 0;
						note.noteStatus="true";
						note.archiveStatus="false";
						note.deleteStatus="false";
						note.reminderStatus="false";
						note.pin = "false";
						
						
						var url = 'user/createNote';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
				});
