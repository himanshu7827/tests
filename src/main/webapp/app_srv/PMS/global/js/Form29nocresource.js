'use strict';

module.factory('saveform29noc', function($resource,$q){
	
	var resource = $resource('/CDSCO/saveForm29');
	return{
		save:function(form29noc){
			//alert("inside resource");
			console.log("Helloooooooo");
			console.log(form29noc);			
			var deferred = $q.defer();
			
			resource.save(form29noc,
					function(response){deferred.resolve(response);},
					function(response){deferred.reject(response);});
			//console.log(response);
			return deferred.promise;
		}
	};
	

});


module.factory('saveform29noc1', function($resource,$q){
	
	var resource = $resource('/CDSCO/saveForm291');
	return{
		save:function(form29noc){
			//alert("inside resource");
			console.log("Helloooooooo");
			console.log(form29noc);			
			var deferred = $q.defer();
			
			resource.save(form29noc,
					function(response){deferred.resolve(response);},
					function(response){deferred.reject(response);});
			//console.log(response);
			return deferred.promise;
		}
	};
	

});

module.factory('fetchDrugDtl29', function($resource,$q){

	var resource = $resource('/CDSCO/FetchDrugDtl29');
	return{
		query:function(){
			//alert("response");
			var deferred = $q.defer();
			resource.query(
					function(response){deferred.resolve(response);},
					function(response){deferred.reject(response);});
			//console.log(response);
			return deferred.promise;
		}
	};
	

});


module.factory('fetchDtl29', function($resource,$q){

	var resource = $resource('/CDSCO/FetchDtl29');
	return{
		query:function(){
			//alert("response");
			var deferred = $q.defer();
			resource.query(
					function(response){deferred.resolve(response);},
					function(response){deferred.reject(response);});
			//console.log(response);
			return deferred.promise;
		}
	};
	

});