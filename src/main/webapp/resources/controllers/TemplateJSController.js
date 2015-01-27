'use strict';

/**
 * TemplateController
 */
var TemplateController = function($scope, $http, growl) {

//	$scope.fetchAnketeList1 = function() {
//		$http.get('temp/ankete')
//		.success(function(ankete) {
//			console.log("ankete: ", ankete);
//		}).error(function(errorLog) {
//			console.log(errorLog)
//			growl.error(JSON.stringify(errorLog));
//			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
//		});
//	};
//
//	 $scope.fetchAnketeList1();
	
	$scope.survey = { 
		id : null,
		question : null,
		scored : false,
		maxVotes : null,
		classGroup : {
			id : null,
			name : null,
			academicYear : null
		},
		options : []
	};
	
	$scope.optionName = '';
	
	$scope.addOption = function() {

		var option = {
			id : null,
			name : null,
			state : false,
			count : 0
		}
		
		if ($scope.optionName) {
			var match = $scope.survey.options.reduce(function(prev, curr) {
				return ($scope.optionName === curr.name) || prev;
			}, false);

//			console.log(match ? 'match' : 'no match');
			if (!match) {
				option.name = this.optionName;
				$scope.survey.options.push(option);
			}
			$scope.optionName = '';
		}
	};

	$scope.optionRemove = function($index) {
		$scope.survey.options.splice($index, 1);
	}

	$scope.setActive = function($index, value) {
		$scope.survey.options[$index].state = value;
//		console.log("active: ", $scope.survey.options[$index].state);
	}

	$scope.setScored = function(value) {
		$scope.survey.scored = value;
	}

	$scope.clearTemplate = function() {
		$scope.survey.question = null
		$scope.survey.scored = false
		$scope.survey.maxVotes = null
		$scope.survey.classGroup = {}
		$scope.survey.options = [];
		$scope.classGroupSelect = '';
	}

	$scope.save = function() {
//		console.log("Anketa to save: ", JSON.stringify($scope.survey));
		return $http.post('template/save', $scope.survey)
			.success(function() {
				growl.success("Survey saved.")
				$scope.fetchClassGroups();
				$scope.clearTemplate();
			})
			.error(function(errorLog) {
				console.log('Error ', errorLog);
				growl.error('Thats an error: ' + JSON.stringify(errorLog));
				growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
			});
	}
	
	$scope.fetchClassGroups = function(){
//		console.log('fetch cg hit')
		$http.get('template/classGroups').success(function(classGroupList) {
//			console.log("Classgroups: ", classGroupList);
			$scope.classGroupList = classGroupList;
		}).error(function(errorLog) {
			console.log("Error: ", errorLog)
			growl.error(JSON.stringify(errorLog));
			growl.error("<b>Awww snap!</b> <i>Something</i> went wrong!", {ttl: -1}, {title: 'ALERT WE GOT ERROR'});
		})
	}
	
	$scope.fetchClassGroups();
	
	$scope.classGroupSelect = '';
	
	$scope.classChange = function(){
		console.log("Selected: ", $scope.classGroupSelect);
		if($scope.classGroupSelect) {
			$scope.survey.classGroup.name = $scope.classGroupSelect.name;
			$scope.survey.classGroup.academicYear = $scope.classGroupSelect.academicYear;
		} else {
			$scope.survey.classGroup.name = "";
			$scope.survey.classGroup.academicYear = "";
		}
	}
	
	
}
