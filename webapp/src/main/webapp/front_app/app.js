'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'restangular',
  'myApp.view1',
  'myApp.view2',
  'myApp.version'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}])
.config(function(RestangularProvider) {
    RestangularProvider.setBaseUrl('/restful');
  })
.config(function(RestangularProvider) {

    // add a response intereceptor
    RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
      var extractedData;
      // .. to look for getList operations
      if (operation === "getList") {
        // .. and handle the data and meta data
        extractedData = data.value;
//        extractedData.meta = data.data.meta;
      } 
//      else {
//        extractedData = data.data;
//      }
      return extractedData;
    });

});  

