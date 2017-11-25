var toDoApp = angular.module('toDoApp', [ 'ui.router','ngSanitize','ui.bootstrap']);

toDoApp.config(function($stateProvider, $urlRouterProvider) {
	
	$stateProvider.state('login', {
		url : '/login',
		templateUrl : 'Template/login.html',
		controller : 'loginController'
	}).state('registerUser', {
		url : '/registerUser',
		templateUrl : 'Template/registration.html',
		controller : 'registerController'
	}).state('forgetpassword',{
		url:'/forgetpassword',
		templateUrl : 'Template/forgetpassword.html',
		controller : 'resetController'
	}).state('resetpassword',{
		url:'/resetpassword',
		templateUrl : 'Template/resetpassword.html',
		controller : 'resetController'
	}).state('home',{
		url:'/home',
		templateUrl : 'Template/home.html',
		controller : 'homeController'
	}).state('sociolLoginhome',{
		url:'/home/',
		templateUrl : 'Template/home.html',
		controller : 'homeController'
	}).state('info',{
		url:'/info',
		templateUrl : 'Template/info.html'
	}).state('archive', {
		url : '/archive',
		templateUrl : 'Template/Archive.html',
		controller: 'homeController'
	}).state('trash',{
		url : '/trash',
		templateUrl : 'Template/Trash.html',
		controller: 'homeController'
	}).state('reminders',{
		url : '/reminders',
		templateUrl : 'Template/reminders.html',
		controller: 'homeController'
	});
	
	$urlRouterProvider.otherwise('login');
});

