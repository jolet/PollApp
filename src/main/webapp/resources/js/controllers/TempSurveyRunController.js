'use strict';

/**
 * TempSurveyRunController
 */
var TempSurveyRunController = function($scope, $http, growl){
	
	$scope.fetchSurveyList = function(){
		$http.get('tempSurveyRun/activeSurveys')
		.success(function(surveyList){
			$scope.surveyList = surveyList;
			console.log("surveys: ", $scope.surveyList);
//			if(surveyList.length == 0){
//				$scope.surveyList
//			}
			if(surveyList.length == 0){
//				$scope.surveyList
			}
		}).error(function(errorLog){
//			error(function (data, status, headers, config) {
			console.log(errorLog)
//			throw new Error('Something went wrong...');
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
	};
	
	$scope.fetchSurveyList();
	
	$scope.vote = function(optionId, surveyId){
		console.log("Chosen option: ", optionId, " - ", surveyId);
		$http.post('tempSurveyRun/sendAnswer/'+optionId)
		.error(function(errorLog){
//			.error(function (data, status, headers, config) {
//			console.log(data, status, headers, config)
			console.log("Error: ", errorLog)
//			throw new Error('Something went wrong...');
			growl.error(JSON.stringify(errorLog));
//			growl.error(JSON.stringify([data, status, headers, config]))
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
	}
};