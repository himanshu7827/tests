function redirectPage(path){
	var encProjectId = $('#encProjectId').val();
	//alert(projectId);
	openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
}

function goprev(){
	 var path=$(location).attr('pathname'); 
	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectid").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}

$(document).ready(function(){
	$('#save').click(function(){
		var encProjectId = $("#encProjectId").val();
		var projectDocument= $("#projectDocument").val();
		var manpowerRequirement= $("#manpowerRequirement").val();
		var paymentScheduleList=$("#paymentScheduleList").val();
		var milestoneDetails=$("#milestoneDetails").val();
		
		if(projectDocument==0){
			swal("Atleast one document is required before Submit");
			return;
		}
		else if(manpowerRequirement==0){
			swal("Atleast one Manpower Details are required before Submit");
			return;
		}
		else if(paymentScheduleList==0){
			swal("Atleast one Payment Schedule Details are required before Submit");
			return;
		}
		else if(milestoneDetails==0){
			swal("Atleast one Milestones details are required before Submit");
			return;
		}
		$.ajax({
			type : "POST",
			url : "/PMS/mst/validateProjectDetails/"+encProjectId,			
			success : function(response) {				
				$('#errorLog').empty();
				
				
					if(response[0] == "success"){
						var refNumber=response[1];
						
						/*swal("Project with Reference Number (" + refNumber + ") Submitted and Sent for Approval")*/
						//Added by devesh on 13/7/23 for adding Sent for approval alert
						swal("Project with Reference Number (" + refNumber + ") Submitted and Sent for Approval To GC");
						//End of alert
						return;
					}
					else{
					for(var i=0;i<response.length ;i++){						
						$('#errorLog').append("<p>"+response[i]+"</p>");
					}
					}
			}, error: function(data) {
		 	  
		   }
		})
	});
/*------------------------------- Handle Request When Click to Submit and Sent for Approval To HOD [ 21/07/2023 added by_Anuj ]  ----------------------------------------------------------------------------*/
	$('#savehod').click(function(){
		var encProjectId = $("#encProjectId").val();
		var projectDocument= $("#projectDocument").val();
		var manpowerRequirement= $("#manpowerRequirement").val();
		var paymentScheduleList=$("#paymentScheduleList").val();
		var milestoneDetails=$("#milestoneDetails").val();
		
		if(projectDocument==0){
			swal("Atleast one document is required before Submit");
			return;
		}
		else if(manpowerRequirement==0){
			swal("Atleast one Manpower Details are required before Submit");
			return;
		}
		else if(paymentScheduleList==0){
			swal("Atleast one Payment Schedule Details are required before Submit");
			return;
		}
		else if(milestoneDetails==0){
			swal("Atleast one Milestones details are required before Submit");
			return;
		}
		$.ajax({
			type : "POST",
			url : "/PMS/mst/validateProjectDetailsHOD/"+encProjectId,			
			success : function(response) {				
				$('#errorLog').empty();
					if(response[0] == "success"){
						swal("Submitted and Sent for Approval To HOD"); 
						//End of alert
						return;
					}
					else{
						for(var i=0;i<response.length ;i++){						
							$('#errorLog').append("<p>"+response[i]+"</p>");
						}
					}
			}, error: function(data) {
		 	  
		   }
		})
	});
	
	/*------------------------------- Handle Request When Click to Submit and Sent for Approval To PMO [ 31/07/2023 added by_Anuj ]  ----------------------------------------------------------------------------*/
	$('#savepmo').click(function(){
		var encProjectId = $("#encProjectId").val();
		var projectDocument= $("#projectDocument").val();
		var manpowerRequirement= $("#manpowerRequirement").val();
		var paymentScheduleList=$("#paymentScheduleList").val();
		var milestoneDetails=$("#milestoneDetails").val();
		
		if(projectDocument==0){
			swal("Atleast one document is required before Submit");
			return;
		}
		else if(manpowerRequirement==0){
			swal("Atleast one Manpower Details are required before Submit");
			return;
		}
		else if(paymentScheduleList==0){
			swal("Atleast one Payment Schedule Details are required before Submit");
			return;
		}
		else if(milestoneDetails==0){
			swal("Atleast one Milestones details are required before Submit");
			return;
		}
		$.ajax({
			type : "POST",
			url : "/PMS/mst/validateProjectDetailsPMO/"+encProjectId,			
			success : function(response) {				
				$('#errorLog').empty();		
					if(response[0] == "success"){
						var refNumber=response[1];
						swal("Project with Reference Number (" + refNumber + ") Submitted and Sent for Approval To PMO");
						return;
					}
					else{
					for(var i=0;i<response.length ;i++){						
						$('#errorLog').append("<p>"+response[i]+"</p>");
					}
					}
			}, error: function(data) {
		 	  
		   }
		})
	});
});