var toDoApp = angular.module('toDoApp');

toDoApp.controller('homeController', function($scope, homeService, $uibModal, $location, $state) {
					
			/*---------------------------------get user-----------------------------------*/
						getUser();
							
						var getUserDetails = function getUser(){
								var a = homePageService.getUser();
								a.then(function(response) {
									$scope.UserDetails=response.data;
								}, function(response) {
									
								});
							}
	
			/*---------------------------------get valid token-----------------------------------*/
	
						var gettingToken = function() {
							var token =  localStorage.getItem('token');
							if(token==null){
							 token = $location.hash();
							}
							return token;
						}
						
			/*-----------------------------------toggle side bar ----------------------------------*/
					
						$scope.showSideBar = true;
						$scope.sidebarToggle = function() {
							if($scope.showSideBar){
								$scope.showSideBar=false;
								document.getElementById("noteWrapper").style.paddingLeft = "250px";
							}
							else{
								$scope.showSideBar = true;
								document.getElementById("noteWrapper").style.paddingLeft = "300px";
							}
						}
						
						$scope.toggleSideBar = function() {
							var width = $('#sideToggle').width();
							console.log(width);
							if (width == '250') {
								document.getElementById("sideToggle").style.width = "0px";
							} else {
								document.getElementById("sideToggle").style.width = "250px";
							}
						}
						
						
					
						/*function toggleSide(){
							var sideNav=document.getElementById("sideToggle").style.width;
							if(sideNav=="0px"){
								document.getElementById("sideToggle").style.width = "250px";
							    document.getElementById("noteWrapper").style.marginLeft = "250px";
							}
							else{
								document.getElementById("sideToggle").style.width = "0px";
							    document.getElementById("noteWrapper").style.marginLeft = "0px";
							}
						}*/

						
			/*---------------------------------show Modal-----------------------------------------*/
						
						
						$scope.showModal = function(note) {
							$scope.note = note;
							modalInstance = $uibModal.open({
								templateUrl : 'Template/EditNote.html',
								scope : $scope,
								size : 'md'
							});
						};
						
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
						
						if($state.current.name=="home"){
							$scope.topBarColor= "#ffbb33";
							$scope.navBarHeading="Fundoo Keep";
						}
						else if($state.current.name=="sociolLoginhome"){
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
						var token = gettingToken();
						
						if($scope.note.title!='' || $scope.note.description!='')
						{	
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
					}
					
		/*----------------------------add a new note to archive --------------------------------*/

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
					
		/*-------------------------------toggle AddNote box ----------------------------------*/
					
				
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}

		/*-----------------------------------display notes ---------------------------------*/
					
					getAllNotes();

					function getAllNotes() {
						
						var	url='user/getallnotes';
						var	token = gettingToken();
		
						var b = homeService.service(url,'GET',token)
						b.then(function(response) {
							console.log("all note are : "+response.data);
							$scope.allGetNotes = response.data;
						}, function(response) {
							$scope.error = response.data.message;
						});
					}
		/*--------------------------------update the note-------------------------------------*/
					
					$scope.updateNote = function(note) {
						
						console.log("update---> "+note);
						
						console.log("title---> "+note.title);
						
						var url = 'user/update';
						var method = 'POST';
						var token = localStorage.getItem('token');
						
						var a = homeService.service(url,method,token,note);
						
						a.then(function(response) {
							modalInstance.close();
							getAllNotes();
						}, function(response) {
						});
					}
		/*--------------------------------Update the header and title from modal---------------------*/
				
					$scope.updateNoteModal=function(note){
						note.title = document.getElementById("modified-title").innerHTML;
						note.description = document.getElementById("modified-descreption").innerHTML;
						note.noteColor=$scope.EditNoteColor;
						$scope.updateNote(note);
						modalInstance.close('resetmodel');
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
						note.reminderStatus="false";
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
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				});
				
