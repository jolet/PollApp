'use strict';

/**
 * TempResultsJSController
 */
 
 var TempResultsJSController = function($scope, $http, growl, $interval){
 
	 var counter = 0;
	 $scope.fetchResults = function(){
		 $http.get("tempResults/activeSurveys")
		 .success(function(surveys){
		 console.log('#(' + counter++ +'). querying for update....');
			 $scope.surveyUpdate = surveys;
		 }).error(function(errorLog){
			 console.log("Error: ", errorLog);
			 growl.error(JSON.stringify(errorLog));
			 growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
			 $interval.cancel(interval);
		 });
	 }
	 
	 $scope.fetchResults();
	 
//	 $scope.surveys = $scope.surveyUpdate;
	 $scope.$watch('surveyUpdate', function(newList){ 
		 $scope.surveys = newList;
		 console.log('-- surveys updated --')
		 }, true);
//	 {
//		 if(!angular.equals(newList,oldList)){
//		 for(int i = 0; i< newList.length; ++i){
//			 for (int j = )
//		 }
//		 $scope.surveys = newList;
//		 console.log('wat');

//		 }
//	 })
	 
	 
	 var interval = $interval(  function() {
		 $scope.fetchResults();
//		 $('.progress .progress-bar').progressbar({display_text: 'fill', use_percentage: false, transition_delay: 0});
	 },2000);
	    
	 $scope.$on("$destroy", function() {

	        if (interval) {
	            $interval.cancel(interval);
	            console.log("interval destroyed")
	        }
	    });
	 
//	 $scope.max = 100;
 };