var toDoApp = angular.module('toDoApp', [ 'ui.router']);

toDoApp.config(function($stateProvider, $urlRouterProvider) {
	
	$stateProvider.state('login', {
		url : '/login',
		templateUrl : 'Template/login.html',
		controller : 'registerController'
	});
	
	$stateProvider.state('registerUser', {
		url : '/registerUser',
		templateUrl : 'Template/registration.html',
		controller : 'registerController'
	});
	
	$urlRouterProvider.otherwise('login');
});