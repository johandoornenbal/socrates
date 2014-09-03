'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', function ISISwww($scope, $http) {
	$http({
		method: "GET",
//		url: '/restful/services/Parties/actions/allParties/invoke',
		url: 'http://socrates.johandoornenbal.eu.cloudbees.net/restful/services',
		headers: {'Authorization': 'Basic c3ZlbjpwYXNz' }
	}).
	success(function(isisdata, status) {
		$scope.isisdata = isisdata;
		$scope.status = status;
	}).
	error(function(isisdata, status){
		$scope.isisdata = "mislukt";
		$scope.status = status;
	});
});
