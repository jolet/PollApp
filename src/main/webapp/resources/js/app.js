'use strict';

var PollApp = {};

var App = angular.module('PollApp', ['ngRoute', 'ngAnimate', 'PollApp.filters', 
                                     'PollApp.services','PollApp.directives',
                                     'angular-growl','mgcrea.ngStrap', 'ui.bootstrap.datetimepicker', 'ui.bootstrap']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider','growlProvider','$httpProvider', 
            function ($routeProvider, growlProvider, $httpProvider) {
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
    $routeProvider.when('/tempActivate', {
    	templateUrl: 'tempActivate/layout',
    	controller: TempActivationController
    });
    
    $routeProvider.otherwise({redirectTo: '/anketaTemplate'});
	
//    Growl notifications
//	growlProvider.globalTimeToLive(3000);
//	growlProvider.globalPosition('bottom-center');
	growlProvider.globalInlineMessages(true);
	growlProvider.globalTimeToLive({success: 3000, error: -1, warning: 3000, info: 4000});
	growlProvider.globalDisableCountDown(true);
	growlProvider.globalReversedOrder(true);
	
//    growlProvider.messagesKey("my-messages");
//    growlProvider.messageTextKey("message-text");
//    growlProvider.messageTitleKey("message-title");
//    growlProvider.messageSeverityKey("severity-level");
	$httpProvider.interceptors.push(growlProvider.serverMessagesInterceptor);
	
//	angular.extend($datepickerProvider.defaults, {
//		dateFormat: 'dd.MM.yyyy',
//		startWeek: '1',
////		dateType: 'string',
////		placement: 'top-left',
//		autoclose: false
//	});
//	angular.extend($timepickerProvider.defaults, {
//		timeFormat: 'HH:mm', 
//		animation: 'am-fade',
////		placement: 'top-left',
//		autoclose: false
//	});

}]);

