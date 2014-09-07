'use strict';

angular.module('myApp.view5', ['ngRoute', 'myApp.services'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view5', {
    templateUrl: 'view5/view5.html',
    controller: 'View5Ctrl'
  });
}])
.controller('View5Ctrl', function($scope, restService) {
	restService.isisRestObj('/restful/services/Persons/actions/allPersons/invoke').then(function(data){
		$scope.allpersonsObj = data.result.value;
		$scope.Person_Title = data.result.value[0].title;
	});
	// init of details
	restService.isisRestObj('/restful/objects/nl.socrates.dom.party.Person/L_1').then(function(data){
		$scope.Person_Obj = data;
	});
	$scope.showMember = function(memberType){
		if (memberType === 'action'){
			return false;
		}
		else{
			return true;
		}
	};
	$scope.detailAction = function(path, title){
		$scope.pathchosen = path;
		restService.isisRestObj(path).then(function(data){
			$scope.Person_Obj = data;
		});
		$scope.Person_Title = title;
	}
});
