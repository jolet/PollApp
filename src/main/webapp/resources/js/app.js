'use strict';

var PollApp = {};

var App = angular.module('PollApp', ['ngRoute', 'PollApp.filters', 'PollApp.services','PollApp.directives','ui.bootstrap']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/anketaTemplate', {
        templateUrl: 'anketaTemplate/layout',
        controller: AnketaTemplateController
    });

    $routeProvider.when('/anketaActivation', {
    	templateUrl: 'anketaActivation/layout',
    	controller: AnketaActivationController
    });
    
    $routeProvider.when('/anketaStudenti', {
    	templateUrl: 'anketaStudenti/layout',
    	controller: AnketaStudentiController
    });

    $routeProvider.when('/anketaResults', {
    	templateUrl: 'anketaResults/layout',
    	controller: AnketaResultsController
    });
    
    $routeProvider.when('/temp', {
    	templateUrl: 'temp/layout',
    	controller: TempController
    });
    
    $routeProvider.otherwise({redirectTo: '/anketaTemplate'});
}]);

