'use strict';

/**
 * VoteController
 */
var VoteController = function($scope, $http, growl, $rootScope){
	
	$scope.fetchSurveyList = function(){
//		$rootScope.showMenu = false;
		$http.get('vote/activeSurveys')
		.success(function(surveyList){
			$scope.surveyList = surveyList;
			console.log("surveys: ", $scope.surveyList);
			if(!surveyList || surveyList.length == 0){
				growl.info("No active surveys.")
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
	
	$scope.vote = function(optionId, surveyId, $index){
		console.log("Chosen option: ", optionId, " - ", surveyId, "index", $index);
		$scope.surveyList.splice($index, 1)
		growl.success("Your vote has been counted in. Thanks.")
		$http.post('vote/sendAnswer/'+optionId)
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
	
	$scope.unlockSurvey = function(secret){
		console.log("key: ", secret);
		if(secret){
			$http.post('vote/unlockSurvey/'+secret)
			.success(function(unlockedSurveyListWrapper) {
				console.log("response:" , unlockedSurveyListWrapper);
				$scope.surveyList = unlockedSurveyListWrapper.payload;
				var status = unlockedSurveyListWrapper.info;
				if(status){
					growl.info("Unlocked - Secret is yours!");
				} else {
					growl.warning("That's just plain wrong :/");
				}
			}).error(function(errorLog) {
				console.log("Error: ", errorLog)
				growl.error(JSON.stringify(errorLog));
				growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
			});
		} else {
			growl.warning("Type secret!!! >:|/")
		}
	}
};