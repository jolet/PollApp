'use strict';

/**
 * AnketaTemplateController
 */
var AnketaTemplateController = function($scope, $http) {
    $scope.fetchAnketaOptionsList = function() {
        $http.get('anketaTemplate/anketeList.json').success(function(anketeList){
            $scope.anketaOptionsList = anketeList;
            $scope.getTemplateQuestion();
            $scope.anketaSaved = false;
            
        });
    };

    $scope.addNewQuestion = function(newQuestion) {
    	$http.post('anketaTemplate/addQuestion/' + newQuestion).success(function() {
// $scope.templatequestion = newQuestion;
    		$scope.questionName = newQuestion;
    		$scope.buttonQuestion = "Change Question";
    		$scope.fetchAnketaOptionsList();
    	});
// $scope.questionName = '';
    };
    
    $scope.getTemplateQuestion = function(){
    	$http.get('anketaTemplate/question').success(function(question){
    		$scope.questionName = question;
    	});
    };
    $scope.addNewOption = function(newOption) {
        $http.post('anketaTemplate/addOption/' + newOption).success(function() {
            $scope.fetchAnketaOptionsList();
        });
        $scope.optionName = '';

    };

    $scope.removeOption = function(option) {
        $http.delete('anketaTemplate/removeOption/' + option).success(function() {
            $scope.fetchAnketaOptionsList();

        });
    };

    $scope.removeAllOptions = function() {
        $http.delete('anketaTemplate/removeAllOptions').success(function() {
            $scope.fetchAnketaOptionsList();

        });

    };
    
    $scope.saveAnketa = function() {
        $http.post('anketaTemplate/saveAnketa/').success(function() {
        	$scope.anketaSaved = true;
        });
    };
    
    $scope.setMaxAllowed = function(maxVotes){
    	$http.post('anketaTemplate/maxAllowed/'+ maxVotes).success(function() {
    		$scope.fetchAnketaOptionsList();
    	});
    };
    
    $scope.fetchAnketaOptionsList();
    $scope.buttonQuestion = "Add question";
};