

//added by komal yadav

'use strict';
var module = angular.module('Form29NOC',['ngResource','ui.select2']);

module.controller('Form29NOCctrl',function($http,$scope,$window,getDrugClass,getDrugUnit,getCountryList1,getDistrictList1,getStateList1,getDrugMonoGraph,getdrugDosage,saveform29noc,saveform29noc1,fetchDrugDtl29,getForm29Data)
		{
	
	
	
	getDrugClass.viewDrugClass(function(drugclass){
		$scope.drugClass = drugclass;
		
		$scope.drugClass.push({numDrugClassId:-1,strDrugClassName:'others'});
	});
	getDrugUnit.viewDrugUnit(function(drugunit){
		$scope.DrugUnit = drugunit;
		
		$scope.DrugUnit.push({drugUnitId:-1,unitName:'others'});
	});
	
	
	getCountryList1.viewCountryList(function(Country){
		$scope.countrylist = Country;
	});
	getStateList1.viewStateList(function(State){
		$scope.statelist = State;
	});
	
	
	
	getDrugMonoGraph.viewDrugMonoGraph(function(drugmonograph){
		$scope.drugMonograph = drugmonograph;
	});
	
	
	getdrugDosage.viewdrugDosage(function(drugdosage){
		$scope.drugDosage = drugdosage;
		
		$scope.drugDosage.push({dosageFormId:-1,dosageName:'others'});
	});
	
	$scope.getDistrict = function(numHospitalState){
		getDistrictList1.viewDistrictList(function(District){
			$scope.districtlist = District;
			//$scope.districtlist2.push(District);
		},numHospitalState);
		};
	
	
		$scope.testStatus=[{id:1,value:'Chemical testing'},{id:2,value:'Biological Testing'}];
		//$scope.numpurpose=;
$scope.form29noc = {
		numDrugId:0,
		 strDrugName:'',
		 numClassDrug:{},
		 strClassDrug:'',
		 drugtype:0,
		 numDosageForm:{},
		 strDosageForm:'',
		 strTestSiteName:'',
		 strTestAdd1:'',
		 strTestAdd2:'',
		 numTestCountryId:0,
		 numTestState:{},
		 testDistrict:{},
		 strTestCity:'',
		 numTestPincode:0,
		 strTestContactDetails:'',
		 strTestFaxDetails:'',
		 numtestStatus:0,
		 numpurpose:$scope.testStatus[1].id,
		 strPackMatrl1:[],
		 strpacksize:'',
		 strengthtbl:[],
		 compositiontbl:[]
		
	
	};

$scope.tempval=angular.copy($scope.form29noc);
var datasave=[];
 var adddata=function(){
	//console.log(result);
	$scope.datasave.push({
	
		strDrugName:$scope.form29noc.strDrugName,
		numClassDrug:$scope.form29noc.numClassDrug,
		 
		 strClassDrug:$scope.form29noc.strClassDrug,
		 numDosageForm:$scope.form29noc.numDosageForm,
		 strDosageForm:$scope.form29noc.strDosageForm,
		 strPackMatrl1:$scope.form29noc.strPackMatrl1,
		 strpacksize:$scope.form29noc.strpacksize,
		 strength:$scope.form29noc.strengthtbl.strength,
		 strengthunit:$scope.form29noc.strengthtbl.strengthunit,
		 ingredient:$scope.form29noc.compositiontbl.ingredient,
			
		active:$scope.form29noc.compositiontbl.active,
			
		quantitative:$scope.form29noc.compositiontbl.quantitative,
		
	});
	$scope.form29noc= $scope.datasave;
	$scope.form29noc = angular.copy($scope.tempval);
};
var field=0;
$scope.compositiontbl={};
$scope.compositiontbl[field]={
		ingredient:'',
		active:0,
		quantitative:[]
};
$scope.strengthtbl={};
$scope.strengthtbl={
		strength:0,
		strengthunit:{},
		strDrugUnit:''
};

$scope.origStren = angular.copy($scope.strengthtbl);
$scope.listStreng = [];

$scope.addStrngthdata = function(){
	//alert('in strength');
	if($scope.strengthtbl.strengthunit.drugUnitId===-1)
		{
		$scope.strengthtbl.strengthunit.unitName=$scope.strengthtbl.strDrugUnit;
		$scope.strengthtbl.strengthunit.drugUnitId=-1;
		}
	
	$scope.listStreng.push({
		
		
		strength:$scope.strengthtbl.strength,
		strDrugUnit:$scope.strengthtbl.strDrugUnit,
		strengthunit:$scope.strengthtbl.strengthunit,
		
	});
	$scope.form29noc.strengthtbl = $scope.listStreng;
	$scope.strengthtbl = angular.copy($scope.origStren);
	
};



$scope.origComp = angular.copy($scope.compositiontbl[field]);
$scope.listComp=[];
$scope.listComp[field] = [];

$scope.addCompdata = function(field){
	if($scope.listComp[field] == undefined)
	{
		$scope.listComp[field]=[];
	}
	
	$scope.listComp[field].push({
		ingredient:$scope.compositiontbl[field].ingredient,
		
		active:$scope.compositiontbl[field].active,
		
		quantitative:$scope.compositiontbl[field].quantitative,
		
	});
	$scope.form29noc.compositiontbl = $scope.listComp;
	$scope.compositiontbl = angular.copy($scope.origComp);
};

$scope.removeComposition = function(parentind,index) {

	$scope.form29noc.compositiontbl[parentind].splice(index, 1);						
};

$scope.removeStrength = function(index) {
	$scope.form29noc.strengthtbl.splice(index, 1);
		
};


$scope.form29part1 =function()
{
	getForm29Data.viewForm29Data(function(form29part1)
			{
			//$scope.getDistrict(form29part1.numTestState.numStateId);
			$scope.form29noc=form29part1;
			if($scope.form29noc.numTestState !=null)
				{
					$scope.getDistrict(form29part1.numTestState);
				}
			console.log("edit mode");
			console.log(form29part1);
			});
	
};




$scope.saveFormData = function(form29noc){
	
		 console.log(form29noc);
		//alert("inside controller");
		$("#loading").show();
		
		$scope.result = saveform29noc.save(form29noc);
		
		$scope.result.then(function(result)
			{
		
				console.log("here before success result");
				console.log(result);
				
				$scope.datasave = fetchDrugDtl29.query();
				console.log(datasave);
				$window.location.reload();
			},function(status){
				$("#loading").hide();
		console.log("here before error status");
		console.log(status);
	});
	 
	
};

$scope.saveFormData1 = function(form29noc){
	
	 console.log(form29noc);
	//alert("inside controller");
	$("#loading").show();
	
	$scope.result = saveform29noc1.save(form29noc);
	
	$scope.result.then(function(result)
		{
	
			console.log("here before success result");
			console.log(result);
			
			
			//$scope.datasave = fetchDtl29.query();
			console.log(datasave);
			location.href="/CDSCO/viewForm29NOCnew";
			//$window.location.reload();
		},function(status){
			$("#loading").hide();
	console.log("here before error status");
	console.log(status);
});


};


});
module.factory('getDrugMonoGraph', function($http,$log){
	return{
		viewDrugMonoGraph:function(successcb){
			$http({method:'GET',url:'/CDSCO/FetchDrugMonoGraph' 
				}).success(function(data,status,header,config){
					successcb(data);
					//console.log(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});

module.factory('getForm29Data', function($http,$log){
	return{
		viewForm29Data:function(successcb){
			$http({method:'GET',url:'/CDSCO/FetchDtl29' 
				}).success(function(data,status,header,config){
					successcb(data);
					//console.log(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});
module.factory('getDrugClass', function($http,$log){
	return{
		viewDrugClass:function(successcb){
			$http({method:'GET',url:'/CDSCO/FetchDrugClass' 
				}).success(function(data,status,header,config){
					successcb(data);
					//console.log(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});
module.factory('getdrugDosage', function($http,$log){
	return{
		viewdrugDosage:function(successcb){
			$http({method:'GET',url:'/CDSCO/FetchdrugDosage' 
				}).success(function(data,status,header,config){
					successcb(data);
					//console.log(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});
module.factory('getDrugUnit', function($http,$log){
	return{
		viewDrugUnit:function(successcb){
			$http({method:'GET',url:'/CDSCO/FetchDrugUnit' 
				}).success(function(data,status,header,config){
					successcb(data);
					//console.log(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});
module.factory('getCountryList1', function($http,$log){
	return{
		viewCountryList:function(successcb){
			$http({method:'GET',url:'/CDSCO/getCountryList1' 
				}).success(function(data,status,header,config){
					successcb(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});

module.factory('getDistrictList1', function($http,$log){
	return{
		viewDistrictList:function(successcb,numHospitalState){
			$http({method:'GET',url:'/CDSCO/getDistrictList1/'+ numHospitalState.numStateId
				}).success(function(data,status,header,config){
					successcb(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});

module.factory('getStateList1', function($http,$log){
	return{
		viewStateList:function(successcb){
			$http({method:'GET',url:'/CDSCO/getStateList1' 
				}).success(function(data,status,header,config){
					successcb(data);
				}).error(function(data,status,header,config){
					$log.warn(data,status,header,config);
				});
		}
	 };
});
