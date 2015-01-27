'use strict';
var app = angular.module('PollApp', []);

//app.config(['$httpProvider', function($httpProvider) {
//	$httpProvider.defaults.headers.patch = {
//			'Content-Type': 'application/json;charset=utf-8'	
//	}
//}]);

app.controller('LoginCtrl', function($scope, $location, $http) {
	
	if($location.absUrl().indexOf('login?error=true') >= 0){
		$scope.errorMessage = 'Wrong username or password!';
	} else {
		$scope.errorMessage = null;
	}

	$scope.backToLogin = function(){
		
		$scope.reg=!$scope.reg;
//		$scope.errorMessage = null;
		$scope.classGroupSelect = {};
		$scope.registeringUser = {
				classGroup : {}
		};
//		window.location = '/PollApp/auth/login'
	}
	
	
	
// REGISTRATION //
	$scope.classGroupSelect = {}	
	$scope.registeringUser = {
			classGroup : {}
	};
	
	$scope.register = function(){
		//		console.log($scope.registeringUser);
		$scope.errorMessage = null;
		$scope.registeringUser.classGroup.name = $scope.classGroupSelect.name;
		$scope.registeringUser.classGroup.academicYear = $scope.classGroupSelect.academicYear;
		$http.post('/PollApp/auth/register', $scope.registeringUser)
		.success(function(status) {
			console.log("registerStatus: ", status);
			if("success" == status){
				console.log('Registration success!');
				$scope.successMessage = 'Registration success. You will get email with further instructions once administrator checks registration details.';
			} else {
				console.log('Registration fail');
				$scope.successMessage = null;
				$scope.errorMessage = 'Registration failed. Try again or contact your administrator.';
			}
			$scope.backToLogin();
		}).error(function(errorLog) {
			console.log("Error: ", errorLog)
//			growl.error(JSON.stringify(errorLog));
//			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
		
	}
	
	$scope.fetchClassGroups = function(){
		$http.get('/PollApp/auth/classGroups')
		.success(function(classGroupList) {
			$scope.classGroupList = classGroupList;
		}).error(function(errorLog) {
			console.log("Error: ", errorLog)
//			growl.error(JSON.stringify(errorLog));
//			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		})
	}
	
	$scope.fetchClassGroups();
	
	
	
	// FORGOTTEN PASSWORD
	$scope.forgotPassword = function(){
		console.log('Email to reset: ', $scope.regEmail);
		
		$http.post('/PollApp/auth/register/'+$scope.regEmail)
		.success(function(messageBack){
			console.log('success', messageBack)
		}).error(function(errorLog){
			console.log('error', errorLog)
		});
			
		
	}
	
	
});