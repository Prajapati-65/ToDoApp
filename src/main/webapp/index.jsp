<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet" href="Style/loginAndRegistration.css">
<link rel="stylesheet" href="Style/NavigationaBar.css">
<link rel="stylesheet" href="Style/home.css">
<link rel="stylesheet" href="Style/AddNote.css">
<link rel="stylesheet" href="Style/style.css">
<link rel="stylesheet" href="bower_components/angular-toastr/dist/angular-toastr.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="bower_components/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css">

<script src="bower_components/angular/angular.js"></script>

<script src="bower_components/jquery/dist/jquery.min.js"></script>
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>  
<script type="text/javascript" src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>

<script type="text/javascript" src="bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
<script src="https://connect.facebook.net/enUS/all.js"></script>
<script src="bower_components/angular-toastr/dist/angular-toastr.tpls.js"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/adonespitogo/angular-base64-upload/master/src/angular-base64-upload.js"></script>
<script src="bower_components/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>

<script type="text/javascript" src="bower_components/angular-sanitize/angular-sanitize.js"></script>
<script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>
	
	
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
<script type="text/javascript" src="directives/filter.js"></script>


</head>
<body ng-app="toDoApp">
	<div ui-view></div>
</body>



</html>
