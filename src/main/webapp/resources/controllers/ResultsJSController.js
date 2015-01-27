'use strict';

/**
 * ResultsController
 */
 var ResultsController = function($scope, $http, growl, $interval){
 
	 var counter = 0;
	 $scope.fetchResults = function(){
		 $http.get("results/activeSurveys")
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
		 
		 $scope.maxVotes = [];
		 
		 for(var survey in newList){
			 var counter = 0;
			 
			 for(var opt in newList[survey].options){
				 counter = counter + newList[survey].options[opt].count;
			 }
			 $scope.maxVotes.push(counter)

		 }
		 
		 }, true);
	 
	 var interval = $interval(  function() {
		 $scope.fetchResults();
	 	},2000);
	    
	 $scope.$on("$destroy", function() {

	        if (interval) {
	            $interval.cancel(interval);
	            console.log("interval destroyed")
	        }
	    });
	 
	 $scope.optionVotersMap = {};
	 
	 $scope.fetchVotingUsers = function(optionId){
		 console.log("option id: ", optionId);
		 $http.get("results/votingUsers/"+optionId)
		 .success(function(users){
//			 $scope.votingUsers = users; 
			 $scope.optionVotersMap.optionId = optionId;
			 $scope.optionVotersMap[optionId] = users;
		 }).error(function(errorLog){
			 console.log("Error: ", errorLog);
			 growl.error(JSON.stringify(errorLog));
			 growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		 });
	 }
	 
 };