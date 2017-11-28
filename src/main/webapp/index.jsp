<html>
<head>

<script type="text/javascript" src="bower_components/angular/angular.js"></script>
	
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- 	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
	
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->

<script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>


<!-- FOR FACEBOOK SHARE -->
<script src="https://connect.facebook.net/enUS/all.js"></script>
	
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="Style/registration.css">
<link rel="stylesheet" href="Style/login.css">
<link rel="stylesheet" href="Style/SideNavigationaBar.css">
<link rel="stylesheet" href="Style/TopNavigationBar.css">
<link rel="stylesheet" href="Style/forgetpassword.css">
<link rel="stylesheet" href="Style/resetpassword.css">
<link rel="stylesheet" href="Style/home.css">
<link rel="stylesheet" href="Style/AddNote.css">
<link rel="stylesheet" href="Style/style.css">

<script type="text/javascript"
	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script type="text/javascript"
	src="bower_components/angular-sanitize/angular-sanitize.js"></script>
<script type="text/javascript" src="Script/app.js"></script>
<script type="text/javascript" src="Script/SideBarNavShow.js"></script>



<script type="text/javascript" src="Controller/RegistrationController.js"></script>
<script type="text/javascript" src="Controller/loginController.js"></script>
<script type="text/javascript" src="Controller/resetpwdController.js"></script>
<script type="text/javascript" src="Controller/homeController.js"></script>
<script type="text/javascript" src="Controller/dummyController.js"></script>

<script type="text/javascript" src="Service/RegistrationService.js"></script>
<script type="text/javascript" src="Service/loginService.js"></script>
<script type="text/javascript" src="Service/forgotpasswordService.js"></script>
<script type="text/javascript" src="Service/homeService.js"></script>

<script type="text/javascript" src="directives/AllDirective.js"></script>

</head>
<body ng-app="toDoApp">
	<div ui-view></div>
</body>
</html>
