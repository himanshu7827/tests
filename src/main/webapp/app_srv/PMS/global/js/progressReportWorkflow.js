function getNextRoleActions(encMonthlyProgressId,encPageId,encWorkflowId)
	{
		
	console.log("inside getNextRoleActions");
		$.ajax({
	        type: "POST",
	        url: "/PMS/getNextRoleActionDetails",
	        async:false,
	        data: {"encMonthlyProgressId":encMonthlyProgressId,"encPageId":encPageId,"encWorkflowId":encWorkflowId},			
			success : function(res) {
				
				console.log(res);

					var actionButtons="";
					var previewFlag=$("#preview").val();
					//alert(previewFlag);
					for(var i=0;i<res.length;i++)
					{ 
					if(res[i].strActionName=="Update"){
						$('#tempAction').text("Update");
					}	
						if(res[i].strActionName=="Update" && previewFlag==1){
						
						
							/* if(previewFlag==1){*/
							var encCatId='';
							$.ajax({
								type : "POST",
								url : "/PMS/getMinCategoryId",
								data : {"encProgressDetailsId":encMonthlyProgressId	
								},
								async:false,
								success : function(response) {
								
									encCatId=response;
								},
								error : function(e) {
									alert('Error: ' + e);			
								}
							});	
							
							actionButtons+="<button type='button' class='btn btn-primary font_14' onclick=performActionNew('"+encMonthlyProgressId+"','"+res[i].strEncActionId+"',"+res[i].isRemarksReq+",'"+encPageId+"','"+encWorkflowId+"','"+encCatId+"') title='"+res[i].strToolTip+"'><span  aria-hidden='true'>"+res[i].strActionName+"</span></button>&nbsp;";
							 }/*}*/
						else{
							actionButtons+="<button type='button' class='btn btn-primary font_14' onclick=performAction('"+encMonthlyProgressId+"','"+res[i].strEncActionId+"',"+res[i].isRemarksReq+",'"+encPageId+"','"+encWorkflowId+"') title='"+res[i].strToolTip+"'><span  aria-hidden='true'>"+res[i].strActionName+"</span></button>&nbsp;";
					}}
					
					$("#nextAction").html(actionButtons);
				
					
				
				
			},
			error : function(e) {
				
				
			}


		
		});

		
	}
function performAction(encMonthlyProgressId,encActionId,isRemarks,encPageId,encWorkflowId)
{
	/*----- Add Alert While sent the empty progress report [ 21-05-2024 ] ----*/
	if($('#tableDataExists').val()=='false'){
		sweetSuccess('Attention','There is no data in the report.');
		return;
	}
	if(isRemarks==0)
	{
		swal({
			  title: "Do you want to submit?",
			  buttons: [					               
		                'No',
		                'Yes'
		              ],	     
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	  doWorkAccrodingToAction(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,"");
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
						 	if(textAreaValue.length < 1000 || textAreaValue.length == 1000)
						 	{
						 		
						 		doWorkAccrodingToAction(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,textAreaValue);
						 	}
						 	else
						 		{
						 			swal("Remarks should be less than or equals to 1000 characters.");
						 		}
				      } else {
				    	  
				      }
				}); 
		}
	else
		{
		  doWorkAccrodingToAction(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,"");
		}
}
function doWorkAccrodingToAction(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,strRemarks)
{
	
	$.ajax({
        type: "POST",
        url: "/PMS/doWorkAccrodingToAction",
        async:false,
        data: {"encMonthlyProgressId":encMonthlyProgressId,"strEncActionId":encActionId,"encPageId":encPageId,"encWorkflowId":encWorkflowId,"strRemarks":strRemarks},			
		success : function(res) {
			if(res.numActionId==1)
			{
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
					    		  openWindowWithPost('GET','/PMS/mst/ViewAllProjects','_self');
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
function ViewProceedings(encMonthlyProgressId,type)
{
	var MonthlyProgressId=$("#encMonthlyProgressId").val();
	var Type = type;
	if(MonthlyProgressId){
		//window.ope= "/PMS/projectProceedings/"+encMonthlyProgressId;
		openWindowWithPost('POST','/PMS/projectProceedings',{"encMonthlyProgressId" : MonthlyProgressId, "Type" : Type},'_blank');

	}
}

function performActionNew(encMonthlyProgressId,encActionId,isRemarks,encPageId,encWorkflowId,numCatId)
{
	if(isRemarks==0)
	{
		swal({
			  title: "Do you wan to submit?",
			 /* text: "<textarea id='textArea'></textarea>",
              html: true,*/
			  //input: 'textarea',
			  buttons: [					               
		                'No',
		                'Yes'
		              ],
		              
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	  doWorkAccrodingToActionNew(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,"",numCatId);
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
		  textarea.disabled = false;
		  
		 
		  
		
			swal({
				  title: 'Do you want to submit?',
				  content: textarea,
				  //text: '<textarea id='textArea'></textarea>',
	              //html: true,
				  //input: 'textarea',
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
						 	if(textAreaValue.length < 1000 || textAreaValue.length == 1000)
						 	{
						 		
						 		doWorkAccrodingToActionNew(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,textAreaValue,numCatId);
						 	}
						 	else
						 		{
						 			swal("Remarks should be less than or equals to 1000 characters.");
						 		}
				      } else {
				    	  
				      }
				}); 
		}
	else
		{
		  doWorkAccrodingToActionNew(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,"",numCatId);
		}
}

function doWorkAccrodingToActionNew(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,strRemarks,numCatId)
{
	
	$.ajax({
        type: "POST",
        url: "/PMS/doWorkAccrodingToAction",
        async:false,
        data: {"encMonthlyProgressId":encMonthlyProgressId,"strEncActionId":encActionId,"encPageId":encPageId,"encWorkflowId":encWorkflowId,"strRemarks":strRemarks},			
		success : function(res) {
			//alert(res.numActionId);
			if(res.numActionId==1)
			{
				monthlyReportNew(encMonthlyProgressId,numCatId);
			}
			else if(res.numActionId==2)
				{
				 previewOfReportNew(encMonthlyProgressId);
				}
			else if(res.numActionId==3)
				{
				  
				 previewOfReportNew(encMonthlyProgressId);
				}
			else{
				   if(res.strSuccessMsg!='' && res.strSuccessMsg!='error') 
				   {   
					  
					   sweetSuccess("Attention!","Data Submitted Successfully.");
					 /*  swal({
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
					    		  openWindowWithPost('GET','/PMS/mst/ViewAllProjects','_self');
					    	  }
					    	});*/
					        
				   }
				   else if(res.strSuccessMsg!='error')
					   swal("There are some problem.Please contact to Admin.");
			}
		},
		error : function(e) {
			
			
		}


	
	});
	
}

function monthlyReportNew(encMonthlyProgressId,numCatId){

	var requestURL='';
	$.ajax({
		type : "POST",
		url : "/PMS/getCategoryURL",
		data : {"encCategoryId":numCatId	
		},
		async:false,
		success : function(response) {
			//alert(response);
			requestURL=response;
		},
		error : function(e) {
			alert('Error: ' + e);			
		}
	});	

	//alert(requestURL);
	
		openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encMonthlyProgressId,"encCategoryId":numCatId},'_blank');
		
}

function previewOfReportNew(encMonthlyProgressId){
	
	openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encMonthlyProgressId},'_blank');
	
	}
