var toDoApp = angular.module('toDoApp');

toDoApp.controller('homeController', function($scope, homeService, $uibModal, $location, toastr, $state ,$interval ,$filter ) {
		
			/*---------------------------------get valid token-----------------------------------*/
	
						var gettingToken = function() {
							var token =  localStorage.getItem('token');
							if(token==null){
							 token = $location.hash();
							}
							return token;
						}
			
						
			/*---------------------------------get user-----------------------------------*/
						
						getUser();
						
						function getUser(){
							var token =  localStorage.getItem('token');
							var method = "POST";
							var url = 'getUserDetails';
							var a = homeService.service(url,method,token);
							
							a.then(function(response) {
								$scope.UserDetails=response.data;
								console.log($scope.UserDetails);
							}, function(response) {
								
							});
						}
			
			/*---------------------------------Social Share-----------------------------------------*/
						
						// SOCIAL SHARE
						$scope.fbAsyncSocialShare = function(note) {
							console.log(note.title);
							console.log('inside fbAsyncInit');
							FB.init({
								appId : '1845582508804612',
								status : true,
								cookie : true,
								xfbml : true,
								version : 'v2.4'
							});

							FB.ui({
								method : 'share_open_graph',
								action_type : 'og.likes',
								action_properties : JSON.stringify({
									object : {
										'og:title' : note.title,
										'og:description' :note.description
									}
								})
							}, function(response) {
								if (response && !response.error_message) {
									alert('Posting completed.');
								} else {
									alert('Error while posting');
								}
							});
						};
			/*---------------------------------------------------------------------------------*/
						
						
		
			/*---------------------------------Add reminder-------------------------------------*/
						
						$scope.AddReminder='';
					
						$scope.AddReminder='';
						$scope.openAddReminder=function(){
						   	$('#datepicker').datetimepicker();
						   	$scope.AddReminder= $('#datepicker').val();
						}
						
						$scope.reminder ="";
						$scope.openReminder=function(note){
							   	$('.reminder').datetimepicker();
							   	 var id = '#datepicker' + note.noteId;
							   	$scope.reminder = $(id).val();
							   	
							   	if($scope.reminder === "" || $scope.reminder === undefined){
							   		console.log(note);
							   		console.log($scope.reminder);
							   	}
							   	else{
							   		console.log($scope.reminder);
							   		note.reminderStatus=$scope.reminder;
							   		$scope.updateNote(note);
							   		$scope.reminder="";
							   }
						}
						
						$scope.removeReminder=function(note){
							console.log($scope.file);
							note.reminderStatus=null;
							update(note);
						}
						
			/*-----------------------------------------------------------------------------------*/		
							function interVal() {
									$interval(function(){
										var i=0;
										for(i;i<$scope.allGetNotes.length;i++){
											if($scope.allGetNotes[i].reminderStatus!='false'){
												
												var currentDate=$filter('date')(new Date(),'MM/dd/yyyy h:mm a');
												
												if($scope.allGetNotes[i].reminderStatus === currentDate){
													toastr.success($scope.allGetNotes[i].title, 'Reminder');
													$scope.allGetNotes[i].reminderStatus=null;
													update($scope.allGetNotes[i]);
												}
											}
											
										}
									},9000);
							}
	
						
						
			/*---------------------------------Collaborator------------------------------*/

						$scope.openCollboarate = function(note, user, index) {
							$scope.note = note;
							$scope.user = user;
							$scope.indexOfNote = index;
							$scope.modalInstance = $uibModal.open({
								templateUrl : 'Template/Collborate.html',
								scope : $scope,
							});
						}
						
						$scope.getUserlist = function(note, user, index) {
							var obj = {};
							obj.noteId = note;
							obj.ownerId = user;
							obj.shareId = {};

							var url = 'user/collaborate';
							var token = gettingToken();
							var userDetails = homeService.service(url, 'POST', token, obj);
							userDetails.then(function(response) {

								console.log(response.data);
								$scope.users = response.data;
								note.collabratorUsers = response.data;

							}, function(response) {
								$scope.user = {};
							});
						
							console.log(user);
							
						}

						$scope.collborate = function(note, user, index) {
							var obj = {};
							console.log(note);
							obj.noteId = note;
							obj.ownerId = user;
							obj.shareId = $scope.shareWith;

							var url = 'user/collaborate';
							var token = gettingToken();
							var userDetails = homeService.service(url, 'POST', token, obj);
							userDetails.then(function(response) {

								console.log(response.data);
								$scope.users = response.data;
								note.collabratorUsers = response.data;

							}, function(response) {
								$scope.user = {};

							});
							
							console.log(user);

						}

						$scope.getOwner = function(note) {
							var url = 'user/getOwner';
							var token = gettingToken();
							var user = homeService.service(url, 'POST', token, note);
							user.then(function(response) {

								$scope.owner = response.data;

							}, function(response) {
								$scope.users = {};
							});
						}

						$scope.removeCollborator = function(note, user, index) {
							var obj = {};
							var url = 'user/removeCollborator';
							obj.noteId = note;
							obj.ownerId = {
								'email' : ''
							};
							obj.shareId = user;
							var token = localStorage.getItem('token');
							var user = homeService.service(url, 'POST', token, obj);
							user.then(function(response) {
								$scope.collborate(note, $scope.owner);

								console.log(response.data);

							}, function(response) {
								console.log(response.data);

							});
						}

						
						
						
			/*---------------------------------Change color----------------------------------------*/
						
						$scope.changeColor=function(note){
		
							var url = 'user/changeColor';
							var method = 'POST';
							var token = gettingToken();
							
							var a = homeService.service(url,method,token,note);
							a.then(function(response) {
								getAllNotes();
							}, function(response) {
								
							});
						}
						
			/*-------------------------------add Note Color Change ------------------------------------*/

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
						
						$scope.modalInstance="";
						
			/*---------------------------------Edit a note in modal---------------------------------------*/
								
							$scope.open = function (note) {
							$scope.AddNoteColor=note.noteColor;
							$scope.note = note;
							$scope.modalInstance = $uibModal.open({
								templateUrl : 'Template/EditNote.html',
								scope : $scope									
								});
							};
									
									
							$scope.changeColorInModal=function(color){
								$scope.AddNoteColor=color;
							}
									
									
			/*-----------------------Update the header and title from modal----------------------------*/
							$scope.updateNoteModal=function(note){
								note.title = document.getElementById("modifiedtitle").innerHTML;
								note.description = document.getElementById("modifieddescreption").innerHTML;
								note.noteColor=$scope.AddNoteColor;
								$scope.updateNote(note);
								$scope.modalInstance.close('resetmodel');
							}
			/*------------------------------archive a note from a modal------------------------------*/
						
							$scope.UnarchiveandArchiveFromModal=function(note){
								note.title = document.getElementById("modifiedtitle").innerHTML;
								note.description = document.getElementById("modifieddescreption").innerHTML;
								note.noteColor=$scope.AddNoteColor;
								if(note.archiveStatus=="false"){
								note.archiveStatus="true";
								note.noteStatus="false";
								note.pin="false";
								$scope.updateNote(note);
								$scope.modalInstance.close('resetmodel');
								}
								else{
									note.archiveStatus="false";
									note.noteStatus="true";
									$scope.updateNote(note);
									$scope.modalInstance.close('resetmodel');
								}
							}
									
						
			/*---------------------------------------------------------------------------------*/
						if($state.current.name=="home"){
							$scope.topBarColor= "#ffbb33";
							$scope.navBarHeading="Fundoo Keep";
						}
						else if($state.current.name=="archive"){
							$scope.topBarColor= "#669999";
							$scope.navBarHeading="Archive";
						}
						else if($state.current.name=="trash"){
							$scope.topBarColor= "#636363";
							$scope.navBarHeading="Trash";
						}
						else if($state.current.name=="reminders"){
							$scope.topBarColor= "#669999";
							$scope.navBarHeading="Reminders";
						}
						
		/*--------------------------------add a new note ------------------------------------*/
						
						$scope.addNote = function() {
							$scope.note = {};
							$scope.note.title = document.getElementById("notetitle").innerHTML;
							$scope.note.description = document.getElementById("noteDescription").innerHTML;

							if ($scope.note.title == ""&& $scope.note.description == "") {
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

								var note = $scope.note;
								var url = 'user/createNote';
								var method = 'POST';
								var token = gettingToken();
								
								var a = homeService.service(url,method,token,note);
								a.then(function(response) {
								document.getElementById("notetitle").innerHTML = "";
								document.getElementById("noteDescription").innerHTML = "";
								$scope.pinStatus = false;
								$scope.AddReminder = '';
								$scope.AddNoteColor = "#ffffff";
								toastr.success('Note added','successfully');
								getAllNotes();
								}, function(response) {
							});
						}
					}
					
		/*----------------------------add a new note to archive --------------------------------*/
					
					$scope.addArchiveNote = function() {
						$scope.note = {};
						$scope.note.title = document.getElementById("notetitle").innerHTML;
						$scope.note.description = document.getElementById("noteDescription").innerHTML;
						if ($scope.note.title == ""
								&& $scope.note.description == "") {
							$scope.pinStatus = false;
							$scope.AddNoteColor = "#ffffff";
							$scope.AddNoteBox = false;
						} else if ($scope.note.title != ""
								|| $scope.note.description != "") {
							$scope.note.pin = "false";
							$scope.note.noteStatus = "false";
							$scope.note.reminderStatus = $scope.AddReminder;
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
							$scope.AddNoteColor = "#ffffff";
							$scope.AddReminder = '';
								getAllNotes();
							}, function(response) {
						});
					}
				}
					
		/*-------------------------------toggle AddNote box ----------------------------------*/
					
				
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}

		/*-----------------------------------display notes ---------------------------------*/
					
					getAllNotes();

					/* display notes */
					function getAllNotes() {
						var	url='user/getallnotes';
						var	token = gettingToken();
		
						var b = homeService.service(url,'GET',token)
						b.then(function(response) {
							$scope.allGetNotes = response.data;
							
						}, function(response) {
							$scope.logout();
						});
					}
		/*--------------------------------update the note-------------------------------------*/
					
					$scope.updateNote = function(note) {
						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							$scope.modalInstance.close();
							getAllNotes();
						}, function(response) {
						});
					}
		
		/*----------------------------pin unpin the notes ---------------------------------------*/
					
					$scope.pinStatus =false;
					
					$scope.pinUnpin = function() {
							if($scope.pinStatus == false){
							$scope.pinStatus = true;
						}
						else {
							$scope.pinStatus=false;
						}
					}
		
/*---------------------------------archive notes--------------------------------------------*/
					
					$scope.archiveNote=function(note){
						note.noteStatus="false";
						note.archiveStatus="true";
						note.pin="false";
						
						var url = 'user/update';
						var method = 'POST';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					

		/*------------------------------------unarchive notes--------------------------------------------*/

					$scope.unarchiveNote=function(note){
						note.noteStatus="true";
						note.archiveStatus="false";
						note.pin="false";
						
						var url = 'user/update';
						var method = 'POST';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					


		/*------------------------------------ restore note----------------------------------------------*/

					/*restore note*/ 
					$scope.restoreNote=function(note){
						note.pin="false";
						note.deleteStatus="false";

						var url = 'user/update';
						var method = 'POST';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
				
		/*------------------------------------Add notes to trash---------------------------------------------*/

					
					$scope.deleteNote=function(note){		
						
					

						note.pin="false";
						note.deleteStatus="true";
						note.reminderStatus="false";

						var url = 'user/update';
						var method = 'POST';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token,note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
		/*------------------------------------ delete note forever----------------------------------------------*/

					$scope.deleteNoteForever = function(id) {
						toastr.success('Note deleted','successfully');
						console.log("id is ..." +id);
						var url = 'user/delete/'+id;
						var method = 'DELETE';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token,id);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
		/*------------------------------------ logout user----------------------------------------------*/
					
					$scope.logout = function() {
						
						var url = 'logout';
						var method = 'POST';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token);
						
						a.then(function(response) {
							localStorage.removeItem('token');
							history.pushState("", document.title, $location.pathname + $location.search);
							$location.path('/login');
						})
					}
					


		/*------------------------------------make a copy of the note-------------------------------------*/
					
					$scope.copy = function(note) {
					
						note.noteStatus="true";
						note.archiveStatus="false";
						note.deleteStatus="false";
						note.pin = "false";
						
						var url = 'user/createNote';
						var method = 'POST';
						var token = gettingToken();
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
				
			/*-------------------------------------List View and Gride View--------------------------------*/
					
					$scope.ListView=true;
					
					$scope.ListViewToggle=function(){
						if($scope.ListView==true){
							$scope.ListView=false;
							listGrideView();
						}
						else{
						$scope.ListView=true;
						listGrideView();
						}
					}
					
					listGrideView();
					
					function listGrideView(){
						if($scope.ListView){
							var element = document.getElementsByClassName('card');
							for(var i=0;i<element.length;i++){
								element[i].style.width="900px";
							}
						}
						else{
							var element = document.getElementsByClassName('card');
							for(var i=0;i<element.length;i++){
								element[i].style.width="300px";
							}
						}
					}
			/*--------------------------------------------------------------------------*/				
	});
				
