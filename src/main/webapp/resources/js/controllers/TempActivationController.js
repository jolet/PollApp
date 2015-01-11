'use strict';

/**
 * TempActivationController
 */
var TempActivationController = function($scope, $http, growl) {
	
	$scope.fetchSurveyList = function(){
		$http.get('tempActivate/surveys')
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
			console.log(errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
	};
	
//	$scope.show;
	
	$scope.setActive = function($index, value){
		var surveyTemp = $scope.surveyList[$index];
		surveyTemp.active = value;
		if(value == false){
			$scope.show=!$scope.show;
		}
		
		if($scope.data && value != null){
			if($scope.data.dateDropDownInputFrom){
				surveyTemp.validFrom = $scope.data.dateDropDownInputFrom;
			} else {
				surveyTemp.validFrom = moment();
			}
			if($scope.data.dateDropDownInputTo) {
				surveyTemp.validTo = $scope.data.dateDropDownInputTo;
			} else {
				surveyTemp.validTo = null;
			}
		} else {
			surveyTemp.validFrom = null; 
			surveyTemp.validTo = null; 
		}
			
		updateSurvey('tempActivate/update', surveyTemp);
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
		JSON.stringify("updated: ", survey);
		$http.post(path, survey)
		.success(function() {
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
			growl.error('Thats an error: ' + JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
	}
	$scope.remove = function($index){
		var surveyTemp = $scope.surveyList[$index];
		
		$http.post('tempActivate/remove/'+surveyTemp.id)
		.success(function() {
			growl.success("Survey " + surveyTemp.question+ " deleted.")
			$scope.surveyList.splice($index, 1);
		})
		.error(function(errorLog) {
			console.log('Error ', errorLog);
			growl.error('Thats an error: ' + JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		});
		
	}
	
	$scope.fetchSurveyList();
}