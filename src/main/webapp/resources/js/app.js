'use strict';

var PollApp = {};

var App = angular.module('PollApp', ['ngRoute', 'ngAnimate', 'PollApp.filters', 'PollApp.services','PollApp.directives','ui.bootstrap','angular-growl']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider','growlProvider', function ($routeProvider, growlProvider) {
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
	
//    Growl notifications
//	growlProvider.globalTimeToLive(3000);
//	growlProvider.globalPosition('bottom-center');
	growlProvider.globalInlineMessages(true);
	growlProvider.globalTimeToLive({success: 3000, error: -1, warning: 3000, info: 4000});
	growlProvider.globalDisableCountDown(true);
	
}
]);

