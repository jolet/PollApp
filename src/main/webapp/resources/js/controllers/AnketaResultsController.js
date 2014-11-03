'use strict'

var AnketaResultsController = function ($scope, $http, $interval){
	
	$scope.fetchAnketaResults = function(){
		$http.get('anketaResults/results.json').success(function(anketaResultsList){
			$scope.anketaResults = anketaResultsList;
			$scope.updateResultInit = anketaResultsList;
		});
		
	};

//	  query for updated results List
	  var updateCheck = function(){
		  $http.get('anketaResults/results.json').success(function(anketaResultsList){
				$scope.updateResultTemp = anketaResultsList;
				if(anketaResultsList.length > 0){
					if(anketaResultsList[0].initialMaxAllowedVoters > 0){
						$scope.max = anketaResultsList[0].initialMaxAllowedVoters;
						$scope.totalTempVotes = $scope.max - anketaResultsList[0].tempMaxAllowedVoters;
					}
					if(anketaResultsList[0].initialMaxAllowedVoters < 0){
						$scope.max = -(anketaResultsList[0].tempMaxAllowedVoters+1);
						$scope.totalTempVotes = $scope.max;
					}
				}
			}).error(function(){
				$scope.connectionLost = true;
				$interval.cancel(interval);
			});
		  
	  }
	  
	  
	  $scope.connectionLost = false;
	  $scope.fetchAnketaResults();	  
	  updateCheck();
	  var interval = $interval(  function() {
		  updateCheck();	  
		  if($scope.anketaResults.length>0){
			  if(!angular.equals($scope.updateResultInit[0].question1,$scope.updateResultTemp[0].question1)){
				  
				  $scope.anketaResults = $scope.updateResultTemp; //bad, destroys animation
			  }
			  $scope.updateResultInit = $scope.updateResultTemp;
			  
			  
//			  if(!angular.isUndefined($scope.updateResultInit)){
			  $scope.progressValues = $scope.updateResultInit[0].answersResults;
//				  console.log("progressbar values: ", $scope.progressValues.join(", "))
//			  }
		  }
	  }, 330);
};
