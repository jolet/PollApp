'use strict'

var AnketaStudentiController = function ($scope, $http){

	$scope.fetchAktivneAnkete = function(){
		$http.get('anketaStudenti/aktivneAnkete.json').success(function(aktivneAnketeList){
			$scope.aktivneAnketeList = aktivneAnketeList;
			
		});
		
	};
	
	$scope.sendAnswer = function(answer){
		console.log("maxAllowed ", $scope.aktivneAnketeList[0].initialMaxAllowedVoters)
		// 2 slučaja - anketa sa ogranicenjem i bez ogranicenja
		if($scope.aktivneAnketeList[0].tempMaxAllowedVoters > 0 || $scope.aktivneAnketeList[0].tempMaxAllowedVoters < 0){
			$http.post('anketaStudenti/sendAnswer/' + answer).success(function(){
		});
		$scope.hasVoted = 1;
		$scope.isVisible = 0;
		$scope.chosenAnswer = answer;
		
		}
		//kraj anketiranja kod ankete sa ogranicenjem (broj preostalih glasova == 0)
		else{
			$scope.maxVotesLimit = true;
		}
	};

	$scope.fastTesting = false;
	$scope.maxVotesLimit = false;
	$scope.fetchAktivneAnkete();
	
};

