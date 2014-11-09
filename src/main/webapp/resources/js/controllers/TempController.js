'use strict';

/**
 * TempController
 */
var TempController = function($scope, $http) {
    $scope.fetchAnketeList = function() {
        $http.get('temp/ankete').success(function(anketeList){
            $scope.anketeList = anketeList;
            console.log("ankete: ", anketeList);
        });
    };

//    $scope.fetchAnketeList();   
    
//    $scope.inputMaxVotesMessage = ''
    
    $scope.optionName = '';
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
	

	
	$scope.addOption = function(){
		
		var option = {
				id : null,
				name : null,
				state : false
				
		}
		if($scope.optionName){
//			console.log("optionName: ", $scope.optionName);
			  var match = $scope.survey.options.reduce(function(prev, curr) {
				    return ($scope.optionName === curr.name) || prev;
				  }, false);
			  
			  console.log(match ? 'match' : 'no match');
			  if (!match) {
				  option.name = this.optionName;
				  $scope.survey.options.push(option);
			  }
				  
			
//			console.log("varoption: ", option)
//			$scope.survey.options.push(option);
//			console.log("$scope.survey.options.push", $scope.survey);
			$scope.optionName='';
		}
//		console.log("options: ", $scope.survey.options); 
	};
	
	$scope.optionRemove = function($index){
//		console.log("opt: ", opt)
		  $scope.survey.options.splice($index, 1);
	}
	
	$scope.setActive = function($index, value){
		$scope.survey.options[$index].state = value;
		console.log("active: ", $scope.survey.options[$index].state);
	}
	
	$scope.setScored = function(value){
		$scope.survey.scored = value;
	}
	
	$scope.save = function() {
		console.log(JSON.stringify($scope.survey));
		return $http.post('temp/save', $scope.survey);
	}
};

