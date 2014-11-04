'use strict';

/**
 * TempController
 */
var TempController = function($scope, $http) {
    $scope.fetchAnketeList = function() {
        $http.get('temp/ankete').success(function(anketeList){
            $scope.anketeList = anketeList;
            
        });
    };

    $scope.fetchAnketeList();
    
	$scope.survey = {
			id : null,
			question : null
	};
	
	$scope.save = function(survey) {
		console.log(JSON.stringify($scope.survey));
		return $http.post('temp/save', survey);
	}
};