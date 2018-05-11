var app = angular.module("springDemo", []);

app.controller("AppCtrl", function($scope, $http, $window) {
 


    $http.get('https://api.stackexchange.com/2.2/sites?filter=!2--Yion.2OJSStcKSpFvq').success(function(response) {
        $scope.sites = response.items;
		
    });
	
});