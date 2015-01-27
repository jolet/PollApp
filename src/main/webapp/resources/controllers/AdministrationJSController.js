'use strict';

/**
 * AdministrationController
 */
var AdministrationController = function($scope, $http, growl){
	$scope.getUsers = function(){
		$http.get('administration/users')
		.success(function(userListWrapped) {
			$scope.usersWrappedList = userListWrapped;
		})
		.error(function(errorLog) {
			console.log(errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		})
	}
	
	$scope.updateUser = function(userId, $index){
		console.log('Flipping user\'s state', userId);
		$http.post('administration/updateUser/'+userId)
		.success(function() {
			console.log('updated')
			$scope.usersWrappedList[$index].payload.active = !$scope.usersWrappedList[$index].payload.active;
//			$scope.getUsers(); //something reordering users order after fetch (grrr), resorting to this hack
		})
		.error(function(errorLog) {
			console.log(errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		})
	}
	
	$scope.fetchUserActivites = function(userId, $index){
		console.log('fetching activities...')
		$http.get('administration/userActivities/'+userId)
		.success(function(userActivitesWrapper) {
			$scope.usersWrappedList[$index].payload.activities = userActivitesWrapper;
//			$scope.usersWrappedList[$index].payload.activities. = userActivitesWrapper.payload;
		})
		.error(function(errorLog) {
			console.log(errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		})
	}
	
	$scope.getUsers();
};