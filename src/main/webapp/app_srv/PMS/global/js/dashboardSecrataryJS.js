 
function LocationOfMonthlyApp(){
	  $.ajax({ 
	      	 type:"GET", 
	      	 url:"/CDSCO/getSecretaryDashboardLocationOfMonthlyApp", 
	    	 success: function(response){
	    		 var resSize=response.length;
	    		if(resSize>0){
	    			var str="Applications : &nbsp;";
	    			for(var i=0;i<resSize;i++){
	    				str=str+response[i].instTypeName+" - "+response[i].instTypeCount+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	    			}
	    			$("#locationofApp").html(str);
	    		}
	    	},
	    	error: function(){
	    		alert("5: No data to display ");
	    	}
		  });
}
 

 
function loadRegionLocationChart(){
	  $.ajax({ 
	      	 type:"GET", 
	      	 url:"/CDSCO/getSecretaryDashboardRegionalLocations", 
	    	  beforeSend: function(){
	    		 	  $('#load11').show();  
	    		    
	       	    },
	       	    complete: function(){
	       	     	$('#load11').hide(); 
	       	        
	       	    },
	       	     data: {
	       	    	 "instTypeId": "0",
	 		        "divisionDataId": "0",
	 		        "formId" : "0",
	 		        "statusId" : "0",
	 		        "amountDivisionId" : "0"
	    			},
	    	success: function(response){
	    	drawChart(response,'RegionLocations','');
	    	},
	    	error: function(){
	    		alert("5: No data to display ");
	    	}
		  });
}
 

 
function getAppMonthAndRevenueWise(){
	var chartYear=$("#chartRevenueYear").val();
	$.ajax({ 
    	 type:"GET", 
    	 url:"/CDSCO/getSecretaryDashboardMonthWiseRevenue", 
  	 	 beforeSend: function(){
     	        $('#load9').show();
     	    },
     	    complete: function(){
     	        $('#load9').hide();
     	    },
     	     data: {
		        "monthYear": chartYear
  			},
  	success: function(response){
  		drawChart(response,'monthWiseRevenueApp','');
  	},
  	error: function(){
  		alert("1: No data to display ");
  	}
	  });
}	

 

 
function getAppMonthWise(){
	var chartYear=$("#chartYear").val();
	$.ajax({ 
    	 type:"GET", 
    	 url:"/CDSCO/getSecretaryDashboardMonthWiseAppCount", 
  	 	 beforeSend: function(){
     	        $('#load8').show();
     	    },
     	    complete: function(){
     	        $('#load8').hide();
     	    },
     	     data: {
		        "monthYear": chartYear
  			},
  	success: function(response){
  		drawChart(response,'monthWiseApp','');
  	},
  	error: function(){
  		alert("1: No data to display ");
  	}
	  });
}	

 

 
function locationChart(){
	$.ajax({ 
     	 type:"GET", 
     	 url:"/CDSCO/getSecretaryDashboardDataInstType", 
   	 	 beforeSend: function(){
      	        $('#load1').show();
      	    },
      	    complete: function(){
      	        $('#load1').hide();
      	    },
      	     data: {
		        "instTypeId": "0",
		        "divisionDataId": "0",
		        "formId" : "0",
		        "statusId" : "0",
		        "amountDivisionId" : "0"
		        //"strCommitteeName": "" 
   			},
   	success: function(response){
   		var seloption = "";
   		seloption+='<option value="0">All</option>';
	  for(var i = 0 ; i <response.length;i++){
	 	 seloption+='<option value='+response[i].instTypeId+'>'+response[i].instTypeName+'</option>';
	  } 
	  $('#dropDown1').append(seloption);
	  $('#dropDown1').trigger('liszt:updated');
	   drawChart(response,'instType','');
   	},
   	error: function(){
   		alert("1: No data to display ");
   	}
	  });
}
 
 
function divisionChart(){
	$.ajax({ 
    	  type:"GET", 
    	  url:"/CDSCO/getSecretaryDashboardDataDivision", 
  	 	 beforeSend: function(){
     	        $('#load2').show();
     	    },
     	    complete: function(){
     	        $('#load2').hide();
     	    },
     	     data: {
     	    	 "instTypeId": v_location,
		        "divisionDataId": "0",
		        "formId" : "0",
		        "statusId" : "0",
		        "amountDivisionId" : "0"
  			},
  	    success: function(response){
  		var seloption = "";
  		seloption+='<option value="0">All</option>';
  		for(var i = 0 ; i <response.length;i++){
		 	  seloption+='<option value='+response[i].divisionDataId+'>'+response[i].divisionName+'</option>';
		    }
  			$('#dropDown2').empty();
  		   $('#dropDown2').append(seloption);
	       $('#dropDown2').trigger('liszt:updated');
	       drawChart(response,'Division','');
	     },
  	 error: function(){
  		 alert("2: No data to display ");    		
  	}
	  });
	  
}
 
 
function loadFormsChart(){
	 $.ajax({ 
      	 type:"GET", 
      	 url:"/CDSCO/getSecretaryDashboardDataForms", 
    	 	 beforeSend: function(){
       	        $('#load3').show();
       	    },
       	    complete: function(){
       	        $('#load3').hide();
       	    },
       	     data: {
       	    	 "instTypeId": v_location,
 		        "divisionDataId": "0",
 		        "formId" : "0",
 		        "statusId" : "0",
 		        "amountDivisionId" : "0"
    			},
    	success: function(response){
    		var seloption = "";
    		seloption+='<option value="0">All</option>';    		
	  for(var i = 0 ; i <response.length;i++){
	 	 seloption+='<option value='+response[i].formId+'>'+response[i].formName+'</option>';
	  } 
	  $('#dropDown3').empty();
	  $('#dropDown3').append(seloption);
	  $('#dropDown3').trigger('liszt:updated');
	  drawChart(response,'Forms','');
	  
    	},
    	error: function(){
    		alert("3: No data to display ");
    	}
	  });
	
}
 

 
function loadStatusWise(){
	  $.ajax({ 
		   	 type:"GET", 
		   	 url:"/CDSCO/getSecretaryDashboardStatusWise", 
		 	  beforeSend: function(){
		    	        $('#load5').show();
		    	    },
		    	    complete: function(){
		    	        $('#load5').hide();
		    	    },
		    	     data: {
		    	    	 "instTypeId": v_location,
		 		        "divisionDataId": "0",
		 		        "formId" : "0",
		 		        "statusId" : "0",
		 		        "amountDivisionId" : "0"
		 			},	
		 	success: function(response){
		 	drawChart(response,'StatusWise','');
		 	},
		 	error: function(){
		 		alert("4: No data to display ");
		 	}
			  });

}
 
 
function loadRegionChart(){
	  $.ajax({ 
	      	 type:"GET", 
	      	 url:"/CDSCO/getSecretaryDashboardRegionWiseApplication", 
	    	  beforeSend: function(){
	    		 	  $('#load4').show();  
	    		    
	       	    },
	       	    complete: function(){
	       	     	$('#load4').hide(); 
	       	        
	       	    },
	       	     data: {
	       	    	 "instTypeId": v_location,
	 		        "divisionDataId": "0",
	 		        "formId" : "0",
	 		        "statusId" : "0",
	 		        "amountDivisionId" : "0"
	    			},
	    	success: function(response){
	    	drawChart(response,'Region','');
	    	},
	    	error: function(){
	    		alert("5: No data to display ");
	    	}
		  });
}
 
 
function loadRegionChartHQ(){
	
	  $.ajax({ 
	      	 type:"GET", 
	      	 url:"/CDSCO/getSecretaryDashboardRegionWiseApplication", 
	    	  beforeSend: function(){
	       	        $('#load7').show();
	       	    },
	       	    complete: function(){
	       	        $('#load7').hide();
	       	    },
	       	     data: {
	       	    	 "instTypeId": v_location,
	 		        "divisionDataId": "0",
	 		        "formId" : "0",
	 		        "statusId" : "0",
	 		        "amountDivisionId" : "0"
	    			},
	    	success: function(response){
	    	drawChart(response,'RegionHQ','');
	    	},
	    	error: function(){
	    		alert("5: No data to display ");
	    	}
		  });
}
 
 
function loadAmountChart(){
	  $.ajax({ 
	      	 type:"GET", 
	      	 url:"/CDSCO/getSecretaryDashboardDivisionWiseAmount", 
	    	  beforeSend: function(){
	       	        $('#load6').show();
	       	    },
	       	    complete: function(){
	       	        $('#load6').hide();
	       	    },
	       	     data: {
	       	    	 "instTypeId": v_location,
	 		        "divisionDataId": "0",
	 		        "formId" : "0",
	 		        "statusId" : "0",
	 		        "amountDivisionId" : "0"
	    			},
	    	success: function(response){
	    	drawChart(response,'Budget','');
	    	},
	    	error: function(){
	    		alert("6: No data to display ");
	    	}
		  });
}
 



 
function selectHandler() {
	//Institute Type Filtering
	var instTypeId=v_location;
	var divisionDataId=$("#dropDown2").val()+"";
	var formId=$("#dropDown3").val()+"";
	
	$('#chartDiv').html('');
	$('#chartDiv2').html('');
	$('#chartDiv3').html('');
	
	$.ajax({ 
     	 type:"GET", 
     	 url:"/CDSCO/getSecretaryDashboardDataInstType", 
   	 	 beforeSend: function(){
      	        $('#load1').show();
      	    },
      	    complete: function(){
      	        $('#load1').hide();
      	    },
      	     data: {
		        "instTypeId": instTypeId,
		        "divisionDataId": divisionDataId,
		        "formId" : formId,
		        "statusId" : "0",
		        "amountDivisionId" : "0"
		        //"strCommitteeName": "" 
   			},
   	success: function(response){
      drawChart(response,'instType','http://www.serbonline.in/SERB/getSecretaryDashboardDataScheme');
   	},
   	error: function(){
   		//alert("1: No data to display ");
   	}
  });
	
	//Division Filtering
	$.ajax({ 
    	  type:"GET", 
    	  url:"/CDSCO/getSecretaryDashboardDataDivision", 
  	 	 beforeSend: function(){
     	        $('#load2').show();
     	    },
     	    complete: function(){
     	        $('#load2').hide();
     	    },
     	     data: {
     	    	 "instTypeId":instTypeId,
		        "divisionDataId": divisionDataId,
		        "formId" : formId,
		        "statusId" : "0",
		        "amountDivisionId" : "0"
  			},
  	    success: function(response){
  		   drawChart(response,'Division','http://www.serbonline.in/SERB/getSecretaryDashboardDataStage');
	     },
  	 error: function(){
  		//alert("2: No data to display ");
  	}
	  });
	  
	//Form Wise Filtering		  
  $.ajax({ 
      	 type:"GET", 
      	 url:"/CDSCO/getSecretaryDashboardDataForms", 
    	 	 beforeSend: function(){
       	        $('#load3').show();
       	    },
       	    complete: function(){
       	        $('#load3').hide();
       	    },
       	     data: {
       	    	 "instTypeId": instTypeId,
 		        "divisionDataId":  divisionDataId,
 		        "formId" : formId,
 		        "statusId" : "0",
 		        "amountDivisionId" : "0"
    			},
    	success: function(response){
    		  drawChart(response,'Forms','http://www.serbonline.in/SERB/getSecretaryDashboardDataStatus');
	 	},
    	error: function(){
    		//alert("3: No data to display ");
    	}
	  });
}
 

 
 function drawChart(dataValues,type,url){
    	  var data = new google.visualization.DataTable();
    	  
    	  if(type=='instType'){ 
    		   data.addColumn('string', 'Locations');
    	       data.addColumn('number', 'Applications');
    	          	       
    	       var data2 = google.visualization.arrayToDataTable([['Location Type', {'label':'Application','type':'number'} ,{role:'style'}]]);
    	       
    	       for (var i = 0; i < dataValues.length; i++) {
   	      		    data.addRow([dataValues[i].instTypeName,dataValues[i].instTypeCount]);
         			data2.addRow([dataValues[i].instTypeName,dataValues[i].instTypeCount,dataValues[i].color_code]);
         		}
         		
    	      var options = {
	                 title: 'Location Type',
	                 is3D: true,
	                 chartArea:{width:'80%',height:'70%'},
	                 width:'25%',
	                 height:283,
	                 legend:'left',
	                 hAxis: {
	                     title: 'Total Applications'	                     
	                   }	                 
               };
         		
        	 	var SchemePie = new google.visualization.BarChart(document.getElementById('chartDiv'));
	            SchemePie.draw(data2, options);
	            
	            /*var tableOptions = {
	                 page: 'enable',
	                 pageSize: 5,
	                 showRowNumber:true
               };
	            
	             var table = new google.visualization.Table(document.getElementById('tableDiv'));
	            table.draw(data,tableOptions);
	            var components = [
		          {type: 'html', datasource: url+'?tq=select strSchemeName,count'},
		          {type: 'csv', datasource: url+'?tq=select strSchemeName,count'}
		      ]; */
		  }
    	  else if(type=='Division'){
		         data.addColumn('string', 'Division');
	    	     data.addColumn('number', 'Applications');
	    	     var data2 = google.visualization.arrayToDataTable([['Location Type', {'label':'Application','type':'number'} ,{role:'colors'}]]);
	    	       
	    	     var arrColors=new Array();
	    	     var total=0;
   	      		for (var i = 0; i < dataValues.length; i++) {
   	      			//alert(dataValues[i].color_code);
         		  data.addRow([dataValues[i].divisionName,dataValues[i].divisionCount]);
         		  //data2.addRow([dataValues[i].divisionName,dataValues[i].divisionCount,dataValues[i].color_code]);
         		  arrColors.push(dataValues[i].color_code);
         		 total=total+dataValues[i].divisionCount;
         		}
         		//alert(arrColors);
         		$("#appInDivisions").text(total);
         		var options = {
	                 title: 'Division',
	                 chartArea:{width:'80%',height:'70%'},
	                 width:'25%',
	                 pieHole: 0.4,
	                 height:283,
	                 legend:'left',
	                 slices: {  4: {offset: 0.2},
	                     12: {offset: 0.3},
	                     14: {offset: 0.4}	                    
	           		}
	                ,
	           		colors: arrColors
               };
        	 	var StagePie = new google.visualization.ColumnChart(document.getElementById('chartDiv2'));
	         
	            StagePie.draw(data, options);
	            
	             var table = new google.visualization.Table(document.getElementById('tableDiv2'));
	             var tableOptions = {
	                 page: 'enable',
	                 pageSize: 5,
	                 showRowNumber:true,
	                 width:'100%'
               };
	            table.draw(data,tableOptions);
	            
		
    	  }
    	  else if(type=='Forms'){
	          data.addColumn('string', 'Forms');
    	      data.addColumn('number', 'Applications');
    	      
    	      var total=0;
   	      		for (var i = 0; i < dataValues.length; i++) {
         		  data.addRow([dataValues[i].formName,dataValues[i].formCount]);
         		 total=total+dataValues[i].formCount;
         		}
   	      		$("#appInForms").text(total);
         		var options = {
	                 title: 'Forms',
	                 is3D: true,
	                 chartArea:{width:'80%',height:'70%'},
	                 width:'25%',
	                 height:283,
	                 legend:'left'
               };
        	 	var CommPie = new google.visualization.PieChart(document.getElementById('chartDiv3'));
	            CommPie.draw(data, options);
	            
	             var table = new google.visualization.Table(document.getElementById('tableDiv3'));
	             var tableOptions = {
	                 page: 'enable',
	                 pageSize: 5,
	                 showRowNumber:true,
	                 width:'100%',
               };
	            table.draw(data,tableOptions);
	           
		
    	  }
    	  else if(type=='StatusWise'){
    		  //var data2 = google.visualization.arrayToDataTable([['Status', {'label':'Application','type':'number'} ,{role:'style'}]]);
    		  var data2 = google.visualization.arrayToDataTable([[{'label':'Status','type':'number'}, {'label':'Application','type':'number'} ,{role:'style'}]]);
    		  		data.addColumn('string', 'Status Type');
        	      data.addColumn('number', 'Applications');
        	      /* for (var i = 0; i < dataValues.length; i++) {
             		  data.addRow([dataValues[i].statusName,dataValues[i].statusCount]);
             		 data2.addRow([dataValues[i].statusName,dataValues[i].statusCount,dataValues[i].color_code]);
             		} */
             		
             		for (var i = 0; i < dataValues.length; i++) {
               		  data.addRow([dataValues[i].statusName,dataValues[i].statusCount]);
               		 data2.addRow([i+1,dataValues[i].statusCount,dataValues[i].color_code]);
               		}
             		
                   var options = {
      	                 title: 'Status',
      	                 is3D: true,
      	                 chartArea:{width:'80%',height:'70%'},
      	                 width:'25%',
      	                 height:283,
      	                 legend:'left',
      	               pointSize: 10,
                     };
        		  	var chart = new google.visualization.LineChart(document.getElementById('chartDiv5'));
    	        	chart.draw(data2, options);
    	        	
    	        	var table = new google.visualization.Table(document.getElementById('tableDiv4'));
   	             	var tableOptions = {
	   	                 page: 'enable',
	   	                 pageSize: 5,
	   	                 showRowNumber:true,
	   	                 width:'100%',
                  	};
   	            	table.draw(data,tableOptions);
   	           
        	  }
    	  else if(type=='Budget'){
    		  
    		//For Non Operational Regions
    	      var data2 = new google.visualization.DataTable();
    	      data2.addColumn('string', 'Division');
    	      data2.addColumn('number', 'Amount');
    	  
    	  		data.addColumn('string', 'Division');
    	        data.addColumn('number', 'Amount');
    	        
    	        /* var rowCountRs=0;
    	        var rowCountDollar=0;
    	        for (var i = 0; i < dataValues.length; i++) {
    	        	if(dataValues[i].currencyType==0)
    	        		rowCountDollar=rowCountDollar+1;
    	        	if(dataValues[i].currencyType==1)
    	        		rowCountRs=rowCountRs+1;
    	        } */
    	        data.addRows(dataValues.length);
    	        data2.addRows(dataValues.length);
    	        var total=0.0;var j=0;var k=0;var totalINR=0.0;
    	        for (var i = 0; i < dataValues.length; i++) {
    	        	if(dataValues[i].currencyType==0){
    	        		
    	        		data.setCell(j, 0, dataValues[i].amountDivisionName); 
        	        	data.setCell(j, 1, dataValues[i].amountDivisionCount);
        	        	total=total+dataValues[i].amountDivisionCount;
        	        	j=j+1;
    	        	}else if(dataValues[i].currencyType==1){
    	        		
    	        		data2.setCell(k, 0, dataValues[i].amountDivisionName); 
        	        	data2.setCell(k, 1, dataValues[i].amountDivisionCount);
        	        	totalINR=totalINR+dataValues[i].amountDivisionCount;
        	        	k=k+1;
    	        	}
    	        }
    	        
    	        $("#revenueInDollar").text(total);
    	        $("#revenueInINR").text(totalINR);
    	        
		        $("#chartDiv6").html('');
		        	var chartDiv6 = document.getElementById('chartDiv6');
			        var options6 = {
			        		//title: 'Revenue Generated: $ '+total,
			        height:270,width:'25%'
			        };
			        var chart2 = new PilesOfMoney(chartDiv6,'dollar_');
			        chart2.draw(data, options6);	
			       
			        var table = new google.visualization.Table(document.getElementById('tableDiv6'));
   	             	var tableOptions = {
	   	                 page: 'enable',
	   	                 pageSize: 5,
	   	                 showRowNumber:true,
	   	                 width:'100%',
                  	};
   	            	table.draw(data,tableOptions);
		        
		        $("#chartDiv13").html(''); 
		        	var chartDiv13 = document.getElementById('chartDiv13');
			        var options13 = {
			        		//title: 'Revenue Generated: Rs. '+totalINR+' /-',
			        height:270,width:'25%'
			        };	
			        var chart13 = new PilesOfMoney(chartDiv13,'INR_');
			        chart13.draw(data2, options13);	
			       
			        var table2 = new google.visualization.Table(document.getElementById('tableDiv13'));
   	             	var tableOptions2 = {
	   	                 page: 'enable',
	   	                 pageSize: 5,
	   	                 showRowNumber:true,
	   	                 width:'100%',
                  	};
   	            	table2.draw(data2,tableOptions2);  
		        
		        
    	  }
    	  else if(type=='Region'){
	    	  data.addColumn('string', 'Provice');
    	      data.addColumn('number', 'Applications');
    	      
    	      var telanganaAndAndhraCount = 0;
    	      var flag = 0;
    	      
    	      for (var i = 0; i < dataValues.length; i++) {
    	      	
    	      	  flag = 0;
    	      	  
	    	      if(dataValues[i].stateName=="Odisha"){
	    	      dataValues[i].stateName = "Orissa"; 
	    	      }
	    	      else if(dataValues[i].stateName == "Uttarakhand"){
	    	      	dataValues[i].stateName = "IN-UT";
	    	      }
	    	      else if(dataValues[i].stateName == "Telangana"){
	    	      	telanganaAndAndhraCount += dataValues[i].stateCount;
	    	      }
	    	      else if(dataValues[i].stateName == "Andhra Pradesh"){
	    	      	telanganaAndAndhraCount += dataValues[i].stateCount;
	    	      }
	    	      if(flag==0){
    	     		  data.addRow([dataValues[i].stateName,dataValues[i].stateCount]);
   	     		  }
         		}
         		data.addRow(['Andhra Pradesh',telanganaAndAndhraCount]);

		        var options = {
		          region: 'IN', 
		          backgroundColor: '#81d4fa',
		            resolution:'provinces',
		            magnifyingGlass:{enable: true, zoomFactor: 10},
		            domain:'IN',
		            height: 310,
		            width:'20%',
		            displayMode: 'regions',
		            tooltip:{showColorCode: true}
		        };

        	var chart = new google.visualization.GeoChart(document.getElementById('chartDiv4'));
       		 chart.draw(data, options);
       		 
       		var table = new google.visualization.Table(document.getElementById('tableDiv14'));
            	var tableOptions = {
	                 page: 'enable',
	                 pageSize: 5,
	                 showRowNumber:true,
	                 width:'100%',
          	};
           	table.draw(data,tableOptions);
        
    	  }else if(type=='RegionLocations'){
	    	  data.addColumn('string', 'State');
    	      data.addColumn('number', 'Regions');
    	      
    	      //For Non Operational Regions
    	      var data2 = new google.visualization.DataTable();
    	      data2.addColumn('string', 'State');
    	      data2.addColumn('number', 'Regions');
    	      
    	      var telanganaAndAndhraCount = 0;
    	      var flag = 0;
    	      
    	      for (var i = 0; i < dataValues.length; i++) {
    	      	
    	      	  flag = 0;
    	      	  
	    	      if(dataValues[i].stateName=="Odisha"){
	    	      dataValues[i].stateName = "Orissa"; 
	    	      }
	    	      else if(dataValues[i].stateName == "Uttarakhand"){
	    	      	dataValues[i].stateName = "IN-UT";
	    	      }
	    	      else if(dataValues[i].stateName == "Telangana"){
	    	      	telanganaAndAndhraCount += dataValues[i].stateCount;
	    	      }
	    	      else if(dataValues[i].stateName == "Andhra Pradesh"){
	    	      	telanganaAndAndhraCount += dataValues[i].stateCount;
	    	      }
	    	      if(flag==0){
	    	    	  if(dataValues[i].stateCount!=0){
	    	    		  data.addRow([dataValues[i].stateName,dataValues[i].stateCount]);  
	    	    	  }
    	     		  
   	     		  }
	    	      if(dataValues[i].stateCount==0){
	    	    	  data2.addRow([dataValues[i].stateName,dataValues[i].stateCount]);
	    	      }
	    	      
         		}
    	      //For Operational
    	      if(telanganaAndAndhraCount!=0){
    	    	  data.addRow(['Andhra Pradesh',telanganaAndAndhraCount]);  
    	      }
         		
    	      //For Non Operational
         		if(telanganaAndAndhraCount==0){
         			data2.addRow(['Andhra Pradesh',telanganaAndAndhraCount]);
         		}

			        var options = {
			          region: 'IN', 
			          backgroundColor: '#81d4fa',
			            resolution:'provinces',
			            magnifyingGlass:{enable: true, zoomFactor: 10},
			            domain:'IN',
			            height: 283,
			            width:'20%',
			            displayMode: 'regions',
			            //displayMode: 'text',
			            tooltip:{showColorCode: true},
			            colorAxis: {colors: ['#00853f', '#00853f','#00853f']},
			            //datalessRegionColor:blue
			            //colorAxis: {colors: ['red', 'green']},
			            //defaultColor: red
			        };

        	var chart = new google.visualization.GeoChart(document.getElementById('chartDiv11'));
       		 chart.draw(data, options);
       		 
       		//For Non Operational Regions
       		
       		var options2 = {
			          region: 'IN', 
			          backgroundColor: '#81d4fa',
			            resolution:'provinces',
			            magnifyingGlass:{enable: true, zoomFactor: 10},
			            domain:'IN',
			            height: 283,
			            width:'20%',
			            displayMode: 'regions',
			            //displayMode: 'text',
			            tooltip:{showColorCode: true},
			            colorAxis: {colors: ['#e31b23', '#e31b23','#e31b23']},
			            //datalessRegionColor:blue
			            //colorAxis: {colors: ['red', 'green']},
			            //defaultColor: red
			        };
       		var chart2 = new google.visualization.GeoChart(document.getElementById('chartDiv12'));
      		 chart2.draw(data2, options2);
      		 
      			var table = new google.visualization.Table(document.getElementById('tableDivOPerationalRegions'));
	             var tableOptions = {
	            		 width:'100%',
	                 page: 'enable',
	                 pageSize: 8,
	     		};
				table.draw(data,tableOptions);
				
				var table2 = new google.visualization.Table(document.getElementById('tableDivNonOPerationalRegions'));
	             var tableOptions2 = {
	            		 width:'100%',
	                 page: 'enable',
	                 pageSize: 8,
	     		};
				table2.draw(data2,tableOptions2);	
       		
    	  }else if(type=='RegionHQ'){
    		  data.addColumn('string', 'State');
    	      data.addColumn('number', 'Applications');
    	      
    	      var telanganaAndAndhraCount = 0;
    	      var flag = 0;
    	      
    	      for (var i = 0; i < dataValues.length; i++) {
    	      	
    	      	  flag = 0;
    	      	  
	    	      if(dataValues[i].stateName=="Odisha"){
	    	      dataValues[i].stateName = "Orissa"; 
	    	      }
	    	      else if(dataValues[i].stateName == "Uttarakhand"){
	    	      	dataValues[i].stateName = "IN-UT";
	    	      }
	    	      else if(dataValues[i].stateName == "Telangana"){
	    	      	telanganaAndAndhraCount += dataValues[i].stateCount;
	    	      }
	    	      else if(dataValues[i].stateName == "Andhra Pradesh"){
	    	      	telanganaAndAndhraCount += dataValues[i].stateCount;
	    	      }
	    	      if(flag==0){
    	     		  data.addRow([dataValues[i].stateName,dataValues[i].stateCount]);
   	     		  }
         		}
         		data.addRow(['Andhra Pradesh',telanganaAndAndhraCount]);

		        var options = {
		          region: 'IN', 
		          backgroundColor: '#81d4fa',
		            resolution:'provinces',
		            magnifyingGlass:{enable: true, zoomFactor: 10},
		            domain:'IN',
		            height: 283,
		            width:'25%',
		            displayMode: 'regions',
		            tooltip:{showColorCode: true}
		        };
		        
        			$('#chartDiv7').html('');
        			var chart = new google.visualization.GeoChart(document.getElementById('chartDiv7'));
       		 		chart.draw(data, options);
       		 		
       		 	var table = new google.visualization.Table(document.getElementById('tableDivRecvdAppinRegions'));
	             var tableOptions = {
	            		 width:'100%',
	                 page: 'enable',
	                 pageSize: 8,
	     		};
				table.draw(data,tableOptions);
       		 	
       		 
    	  }else if(type=='monthWiseApp'){
        	  data.addColumn('string', 'Month');
    	      data.addColumn('number', 'Applications');
    	      
    	      var total=0;
        	   for (var i = 0; i < dataValues.length; i++) {
  	      		    data.addRow([dataValues[i].monthName,dataValues[i].totalAppinMonth]);
  	      			total=total+parseInt(dataValues[i].totalAppinMonth);
        		}
        	 
        	   $("#appInMonthYear").text(total);
        	   
              var options = {
            	pointSize: 10,
                hAxis: {title: 'Month'},
                vAxis: {title: 'Applications'},
           	    legend: 'none',
                height: 340,
              };
              
              $('#chartDiv8').html('');
              var chart = new google.visualization.LineChart(document.getElementById('chartDiv8'));
              chart.draw(data, options);
              
               var table = new google.visualization.Table(document.getElementById('tableDivAppRecvd'));
	             var tableOptions = {
	            		 width:'100%',
	        };
	   
	         data.addRow(["Total",total]);
	         data.setCell(12, 0, 'Total', null, {'className': 'bold-font large-font'});
	         data.setCell(12, 1, total, null, {'className': 'bold-font right-text large-font dollarColor'});
	            
	            table.draw(data,tableOptions);
	            var components = [
		          {type: 'html', datasource: url+'?tq=select strCommitteeName,count'},
		          {type: 'csv', datasource: url+'?tq=select strCommitteeName,count'}
		      ]; 
              
             
        	 }
    	  else if(type=='monthWiseRevenueApp'){
        		 
								data.addColumn('string', 'Month');
								data.addColumn('number', 'In Dollar ($)');
								data.addColumn('number', 'INR (Rs./-)');
								
								var totInRupee=0.00;
								var totInDollar=0.00;
								for (var i = 0; i < dataValues.length; i++) {
								   data.addRow([dataValues[i].monthName,dataValues[i].monthlyRevenue,dataValues[i].monthlyRevenueInRupee]);
									totInDollar=totInDollar+parseFloat(dataValues[i].monthlyRevenue);
									totInRupee=totInRupee+parseFloat(dataValues[i].monthlyRevenueInRupee);
								}
           	   
           	
					           	$("#revenueInMonthYear").text(totInRupee);
					           	$("#revenueInMonthYearDollar").text(totInDollar);
					
					                 var options = {
					                		 pointSize: 16,
					                		 series: {
					                			  1: {pointSize: 12,
					                				  color: '#FF8918',
					                			  }
					                			},
					                		 animation:{
					                		        duration: 1000,
					                		        easing: 'out',
					                		      },
					                  // title: 'Applications Received in Year : 2016',
					                   hAxis: {title: 'Month'},
					                   vAxis: {title: 'Revenue'},
					                   legend: 'none',
					                   height: 340,
					                   width:'80%',
					                      // Draw a trendline for data series 0.
					                 };
                 
					                 $('#chartDiv9').html('');
					                 //var chart = new google.visualization.ScatterChart(document.getElementById('chartDiv9'));
					                 var chart = new google.visualization.LineChart(document.getElementById('chartDiv9'));
					                 chart.draw(data, options);

					                 google.visualization.events.addListener(chart, 'select', function() {
					                	  alert("Inside");
					                	});
					                 
					                 
					                 
					                 var table = new google.visualization.Table(document.getElementById('tableDivRevenueRecvd'));
					  	             var tableOptions = {
					  	            		 width:'100%',
					  	                 //page: 'enable',
					  	                 //pageSize: 12,
					  	                 //showRowNumber:true
					              };
  	           
			  	           data.addRow(["Total",totInDollar,totInRupee]);
			  	         data.setCell(12, 0, 'Total', null, {'className': 'bold-font large-font'});
			  	         data.setCell(12, 1, totInDollar, null, {'className': 'bold-font right-text large-font dollarColor'});
			  	       data.setCell(12, 2, totInRupee, null, {'className': 'bold-font right-text large-font rupeeColor'});
  	         
		  	            table.draw(data,tableOptions);
		  	            var components = [
		  		          {type: 'html', datasource: url+'?tq=select strCommitteeName,count'},
		  		          {type: 'csv', datasource: url+'?tq=select strCommitteeName,count'}
		  		      ]; 
     	 		 }else if(type == "LoadchartLocationDiv"){
     	 			//var data2 = google.visualization.arrayToDataTable([[{'label':'Lat','type':'number'}, {'label':'Long','type':'number'} ,{'label':'Address'}]]);
     	    		data.addColumn('string', 'Office Type ');
					data.addColumn('string', 'Address');
					data.addColumn('boolean', 'Operational');
					//alert(dataValues.length);
     	 	
				 	/* var arr=[];
     				var initData=[];
     				initData.push("Lat");
     				initData.push("Long");
     				initData.push("Name");
     				arr.push(initData);
     				for(var i=0;i<dataValues.length;i++){
     						var subArr=[];
     						subArr.push(parseFloat(dataValues[i].latitude));
     						subArr.push(parseFloat(dataValues[i].longitude));
     						subArr.push(dataValues[i].address);
     						arr.push(subArr);
     				} 
     				alert(arr);
					var data2 = google.visualization.arrayToDataTable(arr); */
		
					
					locationTableRecvdData
				 			
						   var data2 = google.visualization.arrayToDataTable([
                                         ['Lat', 'Long', 'Name'],
                                         [28.674208, 77.455442, 'NORTH ZONE: Central Drug Standard Control Organisation, North Zone, C.G.O. Building-I, Kamla Nehru Nagar, Hapur Chungi, Ghaziabad'],
                                         [28.556162, 77.099958, 'PORT OFFICE DELHI: Customs House, Indira Gandhi International Airport, New Delhi'],
                                         [30.744190,	76.729323,	'SUBZONAL OFFICE (CHANDIGARH): CDSCO Sub zonal office, DGHS, MOH & FW, Sector-39 C, Chandigarh'],
                                         [32.728609, 74.847058, 'SUBZONAL OFFICE (JAMMU & KASHMIR): Central Drugs Standard Control Organization, CDSCO Sub-Zone Jammu Indian Institute of Integrative Medicine (IIIM), Canal Road, Jammu'],
                                         [22.528647, 88.356761,	'EAST ZONE: Central Drug Standard Control Organisation, East Zone, C.G.O. Bldgs.,  (Nizam Palace) West, 2nd Floor, 234/4, Lower Circular Road, Kolkata'],
                                         [22.574514,	88.346375,	'PORT OFFICE KOLKATA: Customs House, 15/1, Strand Road, Kolkata'],
                                         [22.651078,	88.442811,	'PORT OFFICE KOLKATA: Air Cargo Complex, N.S.C. Bose International Airport, Kolkata'],
                                         [18.968876,	72.819972,	'WEST ZONE: Central Drug Standards Control Organization, West  Zone, 4th Floor, Zonal FDA Bhawan, GMSD Compound, Bellasis Road, Mumbai Central, Mumbai'],
                                         [19.075984,	72.877656,	'PORT OFFICE MUMBAI (AIRPORT):  (Air Port), Central Drug Standard Control Organisation, International Air Cargo Complex, Sahar Village, Andheri, Mumbai'],
                                         [18.936238,	72.838255,	'PORT OFFICE MUMBAI (SEA PORT): Central Drugs Standard Control Organization 6th Floor, Annex. Building, New Custom House, Ballard Estate, Fort, Mumbai'],
                                         [15.299326,	74.123996,	'SUBZONAL OFFICE (GOA): Central Drugs Standard Control Orgnization Sub zonal office, 3rd Floor, Customs Building, Custom House, Marmagoa, Goa'],
                                         [22.601292,	75.302465,	'SUBZONAL OFFICE (INDORE): Central Drugs Standard Control Orgnization Sub zonal Indore C/o. Container Corporation of India Ltd., Inland Container Depot, 113, Concor Complex, Sector-3, Pithampur Industrial Area, Pithampur, Dist. Dhar, Madhya Pradesh'],
                                         [23.022505,	72.571362,	'AHMEDABAD ZONAL OFFICE: Zonal Office, Ahmedabad, Central Drug Standards Control Organization, Old Terminal Building, Air Cargo Complex, Airport, Ahmedabad'],
                                         [13.064464,	80.247166,	'SOUTH ZONE: Central Drug Standard Control Organisation, South Zone, 2nd Floor, Shastri Bhawan, Annexe, 26, Haddows Road, Chennai'],
                                         [13.08268,	80.270718,	'PORTS OFFICES (Sea-Port, Chennai): Port, Room No. 23, IV Floor, Annexe Bldg., Custom House, Chennai'],
                                         [12.986951,	80.171503,	'PORTS OFFICES (Airport, Chennai): Air Port Office, Air Cargo Complex, Meenambakkam, Chennai'],
                                         [9.931233,	76.267304,	'PORTS OFFICES (COCHIN): Central Drug Standard Control Organisation, Ministry of Health & Family Welfare, Custom House, Cochin'],
                                         [17.44365,	78.445826,	'HYDERABAD ZONAL OFFICE: CDSCO, Zonal office, Hyderabad ,  CDSCO BHAVAN, Beside T.B. & Demonstration Centre, S.R. Nagar, Hyderabad'],
                                         [17.385044,	78.486671,	'PORT OFFICE HYDERABAD: Unit No. 16,17& 18  Second Floor, Cargo Satellite Building, RGI Air Port, SHAMSHABAD, RR Dist., Hyderabad'],
                                         [28.632541,	77.235367,	'HEAD OFFICE: FDA Bhawan, Kotla Road, New Delhi']
                                       ]); 		 				
     	 				//alert(data2);
     	 				var options = { 
     	 						showTip: true,
     	 						zoomLevel: 5,
     	 						mapType:'hybrid'
     	 						
     	 						//center:new google.maps.LatLng(22.601292,75.302465)
     	 						//useMapTypeControl: true,
     	 					    //mapTypeId:google.maps.MapTypeId.ROADMAP
     	 					    };
     	 				//Map Type:'normal', 'terrain', 'satellite', 'hybrid',
     	 			    var map = new google.visualization.Map(document.getElementById('chartLocationDiv'));
     	 			    map.draw(data2, options);
     	 			    
     	 			  for (var i = 0; i < dataValues.length; i++) {
						   data.addRow([dataValues[i].office_type,dataValues[i].address,dataValues[i].is_active]);
						}
     	 			  	var table = new google.visualization.Table(document.getElementById('locationTableRecvdData'));
     	             	var tableOptions = {
     	            		 width:'100%',
     	        		};
     	   	         table.draw(data,tableOptions);
     	   	     $("#CDSCO-Offices-div").addClass("hide");
     	 		 }
        }
 
 
$(document).ready(function(){
	alert("Inside");
	loadOptions();
	$("#divOther").addClass("hide");
	
	$(".Loctabs").click(function(){
		v_location=$(this).attr("id");
		loadOptions();
		updateTabStyles(v_location);

		if(v_location==0){
			//document.getElementById("divOther").style.display="none";
			$('#divOther').addClass('hide');
			document.getElementById("divHQ").style.display="block";
		}else{
		//	document.getElementById("divOther").style.display="block";
			$('#divOther').removeClass('hide');
			document.getElementById("divHQ").style.display="none";
		}
		//selectHandler();
	});
	
	$("#xlnIndia").click(function(){		
			$('#divOther').addClass('hide');
			document.getElementById("divHQ").style.display="none";
			$('#divOther').addClass('hide');
			document.getElementById("divHQ").style.display="none";
			$('#divxlnIndia').removeClass('hide');
			document.getElementById("divxlnIndia").style.display="block";
			$('#CDSCO-Offices-div').removeClass('hide');
			document.getElementById("CDSCO-Offices-div").style.display="block";
			
			updateTabStyles($(this).attr("id"));
			$(this).removeClass("btn-default");
			$(this).addClass("btn-primary");
			xlnInit();
			//loadxlnIndiaDivCombo();
	});
	
	$("#CDSCO-Offices").click(function(){	
		$('#divOther').addClass('hide');
		document.getElementById("divHQ").style.display="none";
		$('#divOther').addClass('hide');
		document.getElementById("divHQ").style.display="none";
		$('#divxlnIndia').addClass('hide');
		document.getElementById("divxlnIndia").style.display="none";
		$('#CDSCO-Offices-div').removeClass('hide');
		document.getElementById("CDSCO-Offices-div").style.display="block";
		
		
		updateTabStyles($(this).attr("id"));
		$(this).removeClass("btn-default");
		$(this).addClass("btn-primary");
		
		
		
	});
		
	
	
});
 
 
function xlnInit(){
	alert("Hi");
	$.ajax({ 
   	 type:"GET", 
   	 url:"/CDSCO/getXlnIndiaCombo", 
 	 	 beforeSend: function(){
    	        $('#loadxlnCombo').show();
    	    },
    	    complete: function(){
    	        $('#loadxlnCombo').hide();
    	    },
	  	success: function(response){
	  		var seloptionState = "";
	  		var seloptionYear = "";
	  		var seloptionMonth = "";
	  		var seloptionType = "";
	  		
	  		seloptionState+='<option value="0">All</option>';
	  		seloptionYear+='<option value="0">All</option>';
	  		seloptionMonth+='<option value="0">All</option>';
	  		seloptionType+='<option value="0">All</option>';
	  		
		  for(var i = 0 ; i <response.length;i++){
			 
		  } 		  
		  
		  
	  	},
	  	error: function(){
	  		alert("1: No data to display ");
	  	}
	  });

}
 
 
function updateTabStyles(currentLoc){
	$( ".Loctabs" ).each(function() {
		  //$( this ).addClass( "foo" );
			  if(! $(this).hasClass("disabled")){
				 if($(this).attr("id")==currentLoc){
					 $(this).removeClass("btn-default");
					 $(this).addClass("btn-primary");
				 }else{
					 $(this).removeClass("btn-primary");
					 $(this).addClass("btn-default");					 
				 }
			  }		  
		});	
	
	 $("#xlnIndia").removeClass("btn-primary");
	 $("#xlnIndia").addClass("btn-default");
	 
	 $("#CDSCO-Offices").removeClass("btn-primary");
	 $("#CDSCO-Offices").addClass("btn-default");
}
 



 
function loadOptions() {
	if(v_location==0){
			getAppMonthWise();
			loadRegionChartHQ();
			getAppMonthAndRevenueWise();
			loadRegionLocationChart();
			LocationOfMonthlyApp();
			//LoadchartLocationDiv();
		}else{
			divisionChart();
			loadFormsChart();
			loadStatusWise();
			loadRegionChart();
			loadAmountChart();  
	    }
	
	}

 
/*function loadxlnIndiaDivCombo(){
	$.ajax({ 
    	 type:"GET", 
    	 url:"/CDSCO/getXlnIndiaCombo", 
  	 	 beforeSend: function(){
     	        $('#loadxlnCombo').show();
     	    },
     	    complete: function(){
     	        $('#loadxlnCombo').hide();
     	    },
	  	success: function(response){
	  		var seloptionState = "";
	  		var seloptionYear = "";
	  		var seloptionMonth = "";
	  		var seloptionType = "";
	  		
	  		seloptionState+='<option value="0">All</option>';
	  		seloptionYear+='<option value="0">All</option>';
	  		seloptionMonth+='<option value="0">All</option>';
	  		seloptionType+='<option value="0">All</option>';
	  		
		  for(var i = 0 ; i <response.length;i++){
			  if(response[i].c=="state"){
				  seloptionState+='<option value='+response[i].a+'>'+response[i].b+'</option>';
			  }else if(response[i].c=="year"){
				  seloptionYear+='<option value='+response[i].a+'>'+response[i].b+'</option>';
			  }else if(response[i].c=="month"){
				  seloptionMonth+='<option value='+response[i].a+'>'+response[i].b+'</option>';
			  }else(response[i].c=="type"){
				  seloptionType+='<option value='+response[i].a+'>'+response[i].b+'</option>';
			  } 
		  } 		  
		  
		  $('#xlnIndiaLicState').empty();
		  $('#xlnIndiaLicState').append(seloption);
		  $('#xlnIndiaLicState').trigger('liszt:updated');
		  
		  $('#xlnIndiaLicYear').empty();
		  $('#xlnIndiaLicYear').append(seloption);
		  $('#xlnIndiaLicYear').trigger('liszt:updated');
		  
		  $('#xlnIndiaLicMonth').empty();
		  $('#xlnIndiaLicMonth').append(seloption);
		  $('#xlnIndiaLicMonth').trigger('liszt:updated');
		  
		  $('#xlnIndiaLicType').empty();
		  $('#xlnIndiaLicType').append(seloption);
		  $('#xlnIndiaLicType').trigger('liszt:updated');
	  	},
	  	error: function(){
	  		alert("1: No data to display ");
	  	}
	  });
}

 
*/