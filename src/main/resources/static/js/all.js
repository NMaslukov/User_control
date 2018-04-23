var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http, $window) {

  
   $scope.execute = function() {
    if($scope.value != ""){
	   $scope.person_data_byid = true;

		$http.get("http://localhost:8080/rest/getById/" + $scope.value).then(function (response) {
			$scope.name = response.data.name;
			$scope.surname = response.data.surname;
			$scope.role = response.data.role;
			$scope.age = response.data.age;
			$scope.login = response.data.login;
			$scope.password = response.data.password;
	});
  
    }else{
	 $scope.person_data_byid = false;
	}
        
    }
});
