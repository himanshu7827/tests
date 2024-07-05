$('.select2Option').select2({
width: '70%'
});

$(document).ready(function() {

var currentDate = new Date();
    
    $('#startDate').datepicker({
 	   dateFormat: 'dd/mm/yy', 
 	    changeMonth: true,
 	    changeYear:true,
 	    //maxDate:currentDate,
 	    onSelect: function(date){ 	    	
 	    	var dateData = date.split('/');
 	    	var monthval = parseInt(dateData[1])-1;
 	    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);	    	  
 	      
 	        $("#endDate").datepicker( "option", "minDate",selectedDate );  
 	     
 	    }
   
   });
    
    $('#endDate').datepicker({
 	   dateFormat: 'dd/mm/yy', 
 	   changeMonth: true,
 	   changeYear:true ,
 	 // maxDate:currentDate,
 	 onSelect: function(date){ 	
 	
 	 }
    });
}); 

function generateReport(){

	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var groupIds=$("#selectedGroup").val().toString();
	var Ids = [];
	if(!groupIds){
	
		$('#selectedGroup').find('option').each(function() {
			Ids.push($(this).val());
		});
		groupIds=Ids.toString();
	};
	var reportId= $("#report").val();
	
	if(reportId!=15){
	if(startDate=='' || startDate==null){
		swal("Please Select From Date to Generate Report!", "", "warning");
		return false;
	}
	}
	/*else if(endDate=='' || endDate==null){
		swal("Please Select To Date to Generate Report!", "", "warning");
		return false;
	}*/
	/*else if(groupIds==''||groupIds==null){
		swal("Please Select Group to Generate Report!", "", "warning");
		return false;
	}*/
	else if(reportId==''||reportId==null){
		swal("Please Select Report Type to Generate Report!", "", "warning");
		return false;
		}

	openWindowWithPost('POST', '/PMS/generateReportForClientSitEmpDetails', {"startDate":startDate,"endDate":endDate,"groupIds":groupIds,"reportId":reportId}, '_blank');  
				

	}
