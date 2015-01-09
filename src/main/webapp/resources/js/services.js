'use strict';

/* Services */

var AppServices = angular.module('PollApp.services', []);

AppServices.value('version', '0.1');

//AppServices.service('hasVoted',function(){
//var hasVoted = 0;
//
//this.get = function() {
//    // do your ajax call to get your user data and in the response data = response;
//}
//
//this.hasVoted = function() {
//    return hasVoted;
//}
//});