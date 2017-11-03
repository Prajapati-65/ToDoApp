var toDoApp = angular.module('toDoApp', [ 'ui.router']);

toDoApp.config(function($stateProvider, $urlRouterProvider) {
	
	$stateProvider.state('registerUser', {
		url : '/registerUser',
		templateUrl : 'Template/registration.html',
		controller : 'registerController'
	}).state('login', {
		url : '/login',
		templateUrl : 'Template/login.html',
		controller : 'loginController'
	});
	$urlRouterProvider.otherwise('registerUser');
});










