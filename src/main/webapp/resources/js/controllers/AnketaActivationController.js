'use strict';

/**
 * AnketaActivationController
 */
var AnketaActivationController = function($scope, $http){
	$scope.fetchAnketeActivationList = function(){
		$http.get('anketaActivation/anketeList.json').success(function(ankete){
			$scope.anketeActivationList = ankete;
		});
		
	};
	
	$scope.anketaStatusSwitch = function(anketa){
		$http.post('anketaActivation/statusSwitch/' + anketa).success(function(){
			$scope.fetchAnketeActivationList();
			
		});
		$scope.statusAnkete = 1;
//		$http({
//			method: 'POST',
//			url: 'anketaActivation/statusSwitch/',
//			data: 'name=' + anketa.isActive + '&email=' +anketa.,
//			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//		});
	};
	
	$scope.anketaDelete = function(anketa){
		$http.post('anketaActivation/deleteAnketa/' + anketa).success(function(){
			$scope.fetchAnketeActivationList();
			
		});
		$scope.statusAnkete = 3;
				
	};
	$scope.anketaGenerate = function(){
		$http.post('anketaActivation/exampleAnkete/').success(function(){
			$scope.fetchAnketeActivationList();
		});
		
		
	};
	$scope.fetchAnketeActivationList();
	$scope.statusAnkete = 0;
};