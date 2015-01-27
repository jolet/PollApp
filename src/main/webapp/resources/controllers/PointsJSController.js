'use strict';

/**
 * PointsController
 */
var PointsController = function($scope, $http, growl){
	$scope.points = function(){
		$http.get('points/total')
		.success(function(score) {
			$scope.points = score;
		}).error(function(errorLog) {
			console.log("Error: ", errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		})
	}
	
	$scope.points();
}