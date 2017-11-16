var toDoApp = angular.module('toDoApp', [ 'ui.router']);

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
	}).state('info',{
		url:'/info',
		templateUrl : 'Template/info.html'
	});
	$urlRouterProvider.otherwise('login');
});

