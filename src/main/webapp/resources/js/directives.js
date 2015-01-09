'use strict';

/* Directives */

var AppDirectives = angular.module('PollApp.directives', []);

AppDirectives.directive('appVersion', ['version', function (version) {
    return function (scope, elm, attrs) {
        elm.text(version);
    };
}]);

AppDirectives.directive('numberOnlyInput', function () {
    return {
        restrict: 'EA',
        template: '<input name="{{inputName}}" ng-model="inputValue" placeholder = "Max number of votes(optional)">',
        scope: {
            inputValue: '=',
            inputName: '='
        },
        link: function (scope) {
            scope.$watch('inputValue', function(newValue,oldValue) {
                var arr = String(newValue).split("");
                console.log('newValue, oldValue, arr', newValue, oldValue, arr)
                if (arr.length === 0) return;
                angular.forEach(arr, function(value){
                	if(value == '.'){
                		scope.inputValue = oldValue;
                		return;
                	}
                	if(value == '-'){
                		scope.inputValue = oldValue;
                		return;
                	}
                });
            	if(isNaN(newValue)){
            		scope.inputValue = oldValue;
            		return;
            	}
            });
        }
    };
});
