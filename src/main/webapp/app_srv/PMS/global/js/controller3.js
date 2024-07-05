
var helloApp = angular.module("premises", []);

helloApp.controller("premisesCtrl", [ '$scope', '$http',
	function($scope, $http) {
		$http({
			method : 'GET',
			url : '/CDSCO/getSiteType'
		}).success(function(data, status, headers, config) {
			$scope.options=data;
		}).error(function(data, status, headers, config) {
			alert( "failure");
		});
		
		$http({
			method : 'GET',
			url : '/CDSCO/getCountry'
		}).success(function(data, status, headers, config) {
			$scope.country=data;
		}).error(function(data, status, headers, config) {
			alert( "failure");
		});
		
		
		$scope.manu = [];
		$scope.dadd = function() {
			$scope.AddNew = true;

			$scope.manu.push({
				'siteType' : $scope.siteType,
				'scountry' : $scope.scountry,
				'sname'	:$scope.sname,
				'sadd1':$scope.sadd1,
				'sadd2':$scope.sadd2,
				'sstate':$scope.sstate,
				'scity':$scope.scity,
				'spin':$scope.spin,
				'stele':$scope.stele,
				'sfax':$scope.sfax
			});

			$scope.siteType = '';
			$scope.dcountry = '';
			$scope.sname='';
			$scope.sadd1='';
			$scope.sadd2='';
		};

		$scope.showStatus = function(details) {
			var selected = [];
		
			if (details.dselect) {
				selected = $filter('filter')($scope.options, {value : details.dselect});
			}
			
			return selected.length ? selected[0].text : 'Nothing selected';
		};
		
		$scope.removeUser = function(index) {
			$scope.manu.splice(index, 1);

		};
		
		
} ]);