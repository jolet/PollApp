'use strict';

/**
 * TempResultsJSController
 */
 
 var TempResultsJSController = function($scope, $http, growl, $interval){
 
	 $scope.fetchResults = function(){
		 $http.get("tempResults/activeSurveys")
		 .success(function(surveys){
			 console.log('fetchResults success', surveys);
			 $scope.surveys = surveys;
		 }).error(function(errorLog){
			 console.log("Error: ", errorLog);
			 growl.error(JSON.stringify(errorLog));
			 growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		 });
	 }
	 
	 var interval = $interval(  function() {
		 $scope.fetchResults();
	 },2000);
	 
//	 $scope.max = 100;
//	 $scope.fetchResults();
	 $(document).ready(function() {
		    $('.progress .progress-bar').progressbar({display_text: 'fill', use_percentage: false});
		});
 };