<html>
<head>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
<meta name="viewport" content="width=device-width, initial-scale=1">

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"></script>

<script type="text/javascript" src="Script/app.js"></script>

<script type="text/javascript"
	src="Controller/RegistrationController.js"></script>
<script type="text/javascript" src="Service/RegistrationService.js"></script>

<script type="text/javascript" src="Controller/loginController.js"></script>
<script type="text/javascript" src="Service/loginService.js"></script>

<script type="text/javascript" src="Controller/resetpwdController.js"></script>
<script type="text/javascript" src="Service/forgotpasswordService.js"></script>

<script type="text/javascript" src="Controller/homeController.js"></script>
<script type="text/javascript" src="Service/homeService.js"></script>

<link rel="stylesheet" href="Style/registration.css">
<link rel="stylesheet" href="Style/login.css">
<link rel="stylesheet" href="Style/SideNavigationaBar.css">
<link rel="stylesheet" href="Style/TopNavigationBar.css">



<script type="text/javascript" src="directives/SideNavigationBar.js"></script>
<script type="text/javascript" src="directives/TopNavigationBar.js"></script>

</head>
<body ng-app="toDoApp">
	<div ui-view></div>
</body>
</html>
