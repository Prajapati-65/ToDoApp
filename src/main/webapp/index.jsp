<html>
<head>

<script type="text/javascript"
	src="bower_components/angular/angular.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
<meta name="viewport" content="width=device-width, initial-scale=1">

<script type="text/javascript"
	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script type="text/javascript"
	src="bower_components/angular-sanitize/angular-sanitize.js"></script>
<script type="text/javascript" src="Script/app.js"></script>

<script type="text/javascript" src="Controller/RegistrationController.js"></script>
<script type="text/javascript" src="Controller/loginController.js"></script>
<script type="text/javascript" src="Controller/resetpwdController.js"></script>
<script type="text/javascript" src="Controller/homeController.js"></script>

<script type="text/javascript" src="Service/RegistrationService.js"></script>
<script type="text/javascript" src="Service/loginService.js"></script>
<script type="text/javascript" src="Service/forgotpasswordService.js"></script>
<script type="text/javascript" src="Service/homeService.js"></script>

<link rel="stylesheet" href="Style/registration.css">
<link rel="stylesheet" href="Style/login.css">
<link rel="stylesheet" href="Style/SideNavigationaBar.css">
<link rel="stylesheet" href="Style/TopNavigationBar.css">
<link rel="stylesheet" href="Style/forgetpassword.css">
<link rel="stylesheet" href="Style/resetpassword.css">
<link rel="stylesheet" href="Style/home.css">
<link rel="stylesheet" href="Style/AddNote.css">

<script type="text/javascript" src="directives/AllDirective.js"></script>

</head>
<body ng-app="toDoApp">
	<div ui-view></div>
</body>
</html>
