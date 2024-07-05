function startWorkFlow(applicationId,moduleTypeId,businessTypeId,projectTypeId,categoryId){
			openWindowWithPost('POST','/PMS/startProjectWorkflow',{"numId":applicationId,"moduleTypeId":moduleTypeId,"businessTypeId":businessTypeId,"projectTypeId":projectTypeId,"categoryId":categoryId},'_blank');
			}

$(document).ready(function() {
   
	$('#example').DataTable({
	    dom: 'Bfrtip',
		"paging":   false, 
		"ordering": false,
		buttons: [
			           'excel', 'pdf', 'print'
			        ],
		
	});
});


function viewAllowedActions(encCustomId){
	
	var encWorkflowId = $('#encWorkflowId').val();
	$.ajax({
        type: "POST",
        url: "/PMS/getNextRoleActionDetails",
        async:false,
        data: {"encCustomId":encCustomId,"encWorkflowId":encWorkflowId},			
		success : function(res) {		
			$('#'+encCustomId).find('li').remove();	
			if(Array.isArray(res) && res.length >0){
				for(var i=0;i<res.length;i++){					
					if(res[i].numActionId == 1){
						$('#'+encCustomId).append("<li> <a class='font_14'  onclick= startWorkFlow("+res[i].customId+",2,0,0,0) > Update</a></li>");
						//$('#'+encCustomId).append("<li> <a class='font_14' onclick=performAction('"+encCustomId+"','"+res[i].strEncActionId+"',"+res[i].isRemarksReq+") title='"+res[i].strToolTip+"'><span  aria-hidden='true'>"+res[i].strActionName+"</span></a></li>");
					}else{
						/*$('#'+encCustomId).append("<li> <a class='font_14' onclick=performAction('"+encCustomId+"','"+res[i].strEncActionId+"',"+res[i].isRemarksReq+",) title='"+res[i].strToolTip+"'><span  aria-hidden='true'>"+res[i].strActionName+"</span></a></li>");*/
						/*Added by devesh on 14/7/23 for passing action id for view procedings*/
						$('#'+encCustomId).append("<li> <a class='font_14' onclick=performAction('"+encCustomId+"','"+res[i].strEncActionId+"',"+res[i].isRemarksReq+","+res[i].numActionId+") title='"+res[i].strToolTip+"'><span  aria-hidden='true'>"+res[i].strActionName+"</span></a></li>");
					}
					
				}
			}else{
			    /*-------- IF User has No Action than display View Proceeding Action [05-06-2024] ---*/
				$('#'+encCustomId).append("<li> <a class='font_14' onclick=performAction('"+encCustomId+"','',2,19) title='View Proceedings'><span  aria-hidden='true'>View Proceedings</span></a></li>");
				//$('#'+encCustomId).append("<li> <a class='font_14 red'><span  aria-hidden='true'> No Action </span></a></li>");
			}
								
		},
		error : function(e) {					
		}		
	});

}

/*function performAction(customId,encActionId,isRemarks){*/ /*Commented by devesh on 14/7/23 for adding action parameter*/
function performAction(customId,encActionId,isRemarks,actionId){
	var encWorkflowId = $('#encWorkflowId').val();
/*----------------- Check Project Team contains HOD or not [ 27/07/2023 added by_Anuj ] -----------*/
	var hodStatus = document.getElementsByClassName(customId)[0].value;
	if ((hodStatus==='false') && actionId===27) {
	  swal("Mapping of HOD is Pending.")
	  return;
	} 
/*----------------- END [ 27/07/2023 added by_Anuj ] -----------*/
	if(isRemarks==0){
		swal({
			  title: "Do you want to submit?",
			  buttons: [					               
		                'No',
		                'Yes'
		              ],	     
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	  doWorkAccrodingToAction(customId,encActionId,encWorkflowId,"");
		      } else {
		    	  
		      }
		    }); 
		
	}
	else if(isRemarks==1)
		{
			var textarea = document.createElement('textarea');
		    textarea.rows = 6;
		    textarea.className = 'swal-content__textarea';
		    textarea.id = 'textArea';
		    textarea.placeholder = 'You can write remarks';
		    console.log(actionId);
		    if(actionId==11 || actionId==6){
		    	textarea.value = 'Seen and Verified.';
		    }
		    if(actionId==28){
		       textarea.value = 'Reviewed and Verified.';
		    }
			swal({
				  title: 'Do you want to submit?',
				  content: textarea,
				  buttons: [					               
		                'No',
		                'Yes'
		              ],
		           closeOnClickOutside: false // For Prevent the Outside click [05-06-2024]
				}).then(function(isConfirm) {
				      if (isConfirm) {
						 	var textAreaValue=$("#textArea").val();
						 	textAreaValue=textAreaValue.trim();
						 	/*------ Increase the text area length from 300 to 1000 [05-06-2024] ------*/
						 	if(textAreaValue.length < 1000 || textAreaValue.length == 1000){						 		
						 		doWorkAccrodingToAction(customId,encActionId,encWorkflowId,textAreaValue);
						 		/*-------------------------- Generate the Reference Number by HOD [ 21/07/2023 added by_Anuj ] -------------------------------------------------*/					 		
						 		if(actionId==28){
						 			generateTheReferenceNumber(customId);
						 		}
						 		/*---------------------------------------------------------------------------*/
						 	}
						 	else{
						 			swal("Remarks should be less than or equals to 1000 characters.");
						 		}
				      } 
				}); 
		}
	else
		{
		  /*doWorkAccrodingToAction(customId,encActionId,encWorkflowId,"");*/ /*Commented by devesh on 14/7/23 for adding action parameter*/
		  doWorkAccrodingToAction(customId,encActionId,encWorkflowId,"",actionId);//Updated on 14/7/23 by for passing action Id
		}
}

/*function doWorkAccrodingToAction(encCustomId,encActionId,encWorkflowId,strRemarks){*/  /*Commented by devesh on 14/7/23 for adding action parameter*/
function doWorkAccrodingToAction(encCustomId,encActionId,encWorkflowId,strRemarks,actionId){

	//Added by devesh on 14/7/23 for adding view proceedings in action menu
if(actionId==19){
		$.ajax({
	        type: "POST",
	        url: "/PMS/loadTransactionDetails",
	        async:false,
	        data: {"encCustomId":encCustomId,"encWorkflowId":encWorkflowId},			
			success : function(res) {
				
				
				var tableData ='';
				for(var i=0; i< res.length ;i++){
					var rowData = res[i];
					var remarks;
					
					if(!rowData.strRemarks){
						remarks='';
					}
					else
						{
						remarks=rowData.strRemarks;
						}
					tableData +="<tr> <td>"+(i+1)+"</td><td>"+rowData.employeeName+"</td> ";
					/*tableData +="<td>"+rowData.strActionPerformed+"</td> <td>"+remarks+"</td> ";*/
					tableData +="<td>"+rowData.strActionPerformed+"</td> <td>"+remarks+"</td><td>"+rowData.transactionAt.split(' ')[0]+"</td>";//Added by devesh on 18/7/23 for displaying transaction date on view proceedings
				
					
					tableData +="</tr>";
				}
				$('#proceedingTbl > tbody').html('');
				$('#proceedingTbl').append(tableData);
			},
		})
		$('#proceedingModal').modal('show');
		return false;
	}//End of view proceedings action
else{
	
	$.ajax({
        type: "POST",
        url: "/PMS/doWorkAccrodingToAction",
        async:false,
        data: {"encCustomId":encCustomId,"strEncActionId":encActionId,"encWorkflowId":encWorkflowId,"strRemarks":strRemarks},			
		success : function(res) {
			if(res.numActionId==1){
				monthlyReport();
			}
			else if(res.numActionId==2)
				{
				 previewOfReport();
				}
			else if(res.numActionId==3)
				{
				  
				 previewOfReport();
				}
			else{
				   if(res.strSuccessMsg!='' && res.strSuccessMsg!='error') 
				   {   
					   
					   swal({
					    	  title: "",
					    	  text: res.strSuccessMsg,    	
					    	  showCancelButton: false,
					    	  confirmButtonColor: "#DD6B55",    	  
					    	  confirmButtonColor: "#34A534",
					    	  confirmButtonText: "Ok",
					    	  cancelButtonText: "Cancel",
					    	  closeOnConfirm: true,
					    	  closeOnCancel: true
					    	}).then(
					    	 function(isConfirm){
					    	  if (isConfirm) {
					    		  openWindowWithPost('GET','/PMS/mst/underApprovalProjects','_self');
					    	  }
					    	});
					        
				   }
				   else if(res.strSuccessMsg!='error')
					   swal("There are some problem.Please contact to Admin.");
			}
		},
		error : function(e) {
			
			
		}


	
	});
}	
}

/*-------------------------- Generate the Reference Number by HOD [ 21/07/2023 added by_Anuj ] -------------------------------------------------*/	
function generateTheReferenceNumber(customId)
{
	$.ajax({
		type : "POST",
		url : "/PMS/mst/actionProjectDetailsbyHOD/"+customId,			
		success : function(response) {				
			$('#errorLog').empty();
				if(response[0] == "success"){
					var refNumber=response[1];
					swal("Project with Reference Number (" + refNumber + ") Submitted and Sent for Approval To GC").then(function(){
		                location.reload(); // Reload the page after the user clicks "OK"
		            });
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
}
