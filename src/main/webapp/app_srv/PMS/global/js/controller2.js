
var helloApp = angular.module("helloApp",[]);






helloApp.controller("CompanyCtrl", [ '$scope', '$http',	function($scope, $http) {
		$http({
			method : 'GET',
			url : '/CDSCO/country'
		}).success(function(data, status, headers, config) {
			alert(data[0]);
			$scope.options=data;
		}).error(function(data, status, headers, config) {
			alert( "failure");
		});
		
		
		$http({
			method : 'GET',
			url : '/CDSCO/country'
		}).success(function(data, status, headers, config) {
			//alert("success1");
			$scope.fsel2=data;
		}).error(function(data, status, headers, config) {
			alert( "failure");
		});
		
		
		/*$http({
			method : 'GET',
			url : '/CDSCO/strength'
		}).success(function(data, status, headers, config) {
		//	alert("success2");
			$scope.fsel1=data;
		}).error(function(data, status, headers, config) {
			alert( "failure");
		});*/
		
		$scope.manu = [];
		$scope.dadd = function() {
			$scope.AddNew = true;

			$scope.manu.push({
				'strDrugName' : $scope.strDrugName,
				'dselect' : $scope.dselect
			});

			$scope.dname = '';
			$scope.dselect = '';

		};

		
		$scope.removeUser = function(index) {
			$scope.manu.splice(index, 1);

		};
		
		
		$scope.fmanu = [];

		$scope.fadd = function() {
			$scope.AddNewFF = true;

			$scope.fmanu.push({
				'fname' : $scope.fname,
				'dform' : $scope.dform,
				'strength' : $scope.strength,
				'fselect1' : $scope.fselect1,
				'fselect2' : $scope.fselect2
			});

			$scope.fname = '';

			$scope.dform = '';
			$scope.strength = '';
			$scope.fselect1 = '';
			$scope.fselect2 = '';

		};

	
	
		$scope.removeDrug = function(index) {
			
			$scope.fmanu.splice(index, 1);

		};
		
} ]);