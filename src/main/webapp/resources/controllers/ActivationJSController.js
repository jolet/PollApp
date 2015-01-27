'use strict';

/**
 * ActivationController
 */
var ActivationController = function($scope, $http, growl) {
	
	$scope.fetchSurveyList = function(){
		$http.get('activation/surveys')
		.success(function(surveyList){
			$scope.surveyList = surveyList;
//			console.log("surveys: ", $scope.surveyList);
//			if(surveyList.length == 0){
//				$scope.surveyList
//			}
			if(surveyList.length == 0){
//				$scope.surveyList
			}
		}).error(function(errorLog){
			console.log(errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
	};
	
//	$scope.show;
	
	$scope.setActive = function($index, value, dateTime){
		console.log("dateTime", dateTime);
		var surveyTemp = $scope.surveyList[$index];
		surveyTemp.active = value;
		if(value == false){
			$scope.show=!$scope.show;
		}
		
		if(dateTime && value != null){
			if(dateTime.dateDropDownInputFrom){
				surveyTemp.validFrom = dateTime.dateDropDownInputFrom;
			} else {
				surveyTemp.validFrom = moment();
			}
			if(dateTime.dateDropDownInputTo) {
				surveyTemp.validTo = dateTime.dateDropDownInputTo;
			} else {
				surveyTemp.validTo = null;
			}
		} else {
			surveyTemp.validFrom = null; 
			surveyTemp.validTo = null; 
		}
			
		updateSurvey('activation/update', surveyTemp);
	}
//	$scope.setActive = function($index, value){
//		var surveyTemp = $scope.surveyList[$index];
//		surveyTemp.active = value;
//		var myTimepicker = $timepicker(element, ngModelController);
//		console.log($scope.activeFrom);
//		survey.validFrom = 
//		updateSurvey('tempActivate/update', surveyTemp);
//	}
	
	
	var updateSurvey = function(path, survey){
//		JSON.stringify("updated: ", survey);
		$http.post(path, survey)
		.success(function(messageBack) {
			if(survey.active){
				growl.success("Survey " + survey.question+ " activated.")
			} else if(survey.active == null){
				growl.warning("Survey " + survey.question+ " deactivated.")
//				growl.success("Survey " + survey.question+ " activated.")
			} else {
//				growl.success("Survey " + survey.question+ " deactivated.")
			}
//			$scope.surveyList.splice($index, 1);
		})
		.error(function(errorLog) {
			console.log('Error ', errorLog);
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
	}
	$scope.remove = function($index){
		var surveyTemp = $scope.surveyList[$index];
		
		$http.post('activation/remove/'+surveyTemp.id)
		.success(function() {
			growl.success("Survey " + surveyTemp.question+ " sent to history.")
			$scope.surveyList.splice($index, 1);
		})
		.error(function(errorLog) {
			console.log('Error ', errorLog);
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
		
	}
	
	$scope.fetchSurveyList();
}