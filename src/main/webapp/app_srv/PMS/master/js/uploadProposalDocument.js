var addressRegex='';
var addressErrorMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	addressRegex = messageResource.get('address.regex', 'regexValidationforjs');
	addressErrorMessage = messageResource.get('address.regex.message', 'regexValidationforjs');
});


function gonext()
{

	 var path=$(location).attr('pathname');  
	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();
    
}

function goprev()
{
	 var path=$(location).attr('pathname');  
	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}

$(document).ready(function(){
	   $('.select2Option').select2({
	    	 width: '100%'
	    });
	   
	   $('#documentDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		   changeMonth: true,
		    changeYear:true,
		    maxDate: '+5y',
		    onSelect: function(date){
		    $('#documentDate').trigger('input');
		    }
	   });
	   
	   $("#periodFrom").datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,	   
		    maxDate: '+5y',
		    onSelect: function(date){
		    	$('#periodFrom').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#periodTo").datepicker( "option", "minDate", selectedDate );
		        $("#periodTo").datepicker( "option", "maxDate", '+5y' );
		    	
		      /*  var selectedDate = new Date(date);
		        
		        $("#periodTo").datepicker( "option", "minDate", selectedDate );
		        $("#periodTo").datepicker( "option", "maxDate", '+5y' );*/
		    }
		});

		$("#periodTo").datepicker({	
			 dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,
		});

});

$(document).on('click','#newUpload',function(e){
	var documentTypeId = $('#documentTypeId').val();
	var projectId = $('#proposalId').val();
	if((documentTypeId == 0 || projectId == 0)){
		swal(" Document Type is Required");
	}else{
		uploadFormatTable(documentTypeId);
	}	
});

// Display Document Details 
$(document).on('click', '#documentTypeId', function(e) {
	$('#newUploadDiv').removeClass('hidden');
	loadUploadedDoc();
});

/*$(document).on('click','#projectId',function(e){
	loadUploadedDoc();	
});*/

function loadUploadedDoc(){
	var documentTypeId = $('#documentTypeId').val();
	var proposalId = $('#proposalId').val();
		
	if((documentTypeId == 0 )){
		$('#example tbody').empty();
		$('#newUploadDiv').addClass('hidden');
		$('#docBasicDetails').addClass('hidden');
		$('#example').addClass('hidden');
		$('#example1').removeClass('hidden');
		
	}else{
		$('#newUploadDiv').removeClass('hidden');
		
		$('#example1').addClass('hidden');
		$('#example').removeClass('hidden');
		
		$.ajaxRequest.ajax({
	        type: "POST",
	        url: "/PMS/mst/uploadedProposalDocument",
	        data: {"proposalId":proposalId,
	        	"documentTypeId" : documentTypeId
	        	},			
			success : function(data) {		
				//console.log("data"+data);
				var tableData ='';
				var numCols = $("#example").find('tr')[0].cells.length;
				for(var i=0;i<data.length;i++){
					var rowData = data[i];
					tableData += "<tr id="+rowData.numId+"><td>"+ rowData.documentDate+" </td>";
					/*if(rowData.periodFrom){
						tableData +="<td>"+rowData.periodFrom+" </td>" ;
					}else{
						tableData +="<td> </td>" ;
					}
					if(rowData.periodTo){
						tableData +=" <td>"+rowData.periodTo+"</td>";
					}else{
						tableData +=" <td></td>";
					}*/
					
					tableData +="<td>"+rowData.documentVersion+"</td>";
					var uploads = rowData.detailsModels;
					downloadItem = '<p>';
					for(var j=0;j<uploads.length;j++){
						var temp = uploads[j];
						if(j<uploads.length-1){
							
							downloadItem +="<a onclick=downloadProposalFile('"+temp.encNumId+"')> "+temp.icon+"</a> || ";
						}else{
							downloadItem +="<a onclick=downloadProposalFile('"+temp.encNumId+"')> "+temp.icon+"</a>";
						}
					}
					downloadItem+='</p>';
					tableData += "<td>"+downloadItem+"</td>";
					
					if(rowData.description){
						tableData += "<td >"+rowData.description+"</td>";
					}else{
						tableData += "<td ></td>";
					}
					tableData +="<td> <span class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' onclick=editDocument("+rowData.numId+") aria-hidden='true'></span></td>";	
					/*reduce the colspan by 6 by varun */
					if(numCols==6){
						//tableData +="<td> <span class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' onclick=editDocument("+rowData.numId+") aria-hidden='true'></span></td>";	
						//tableData +="<td> <span class='fa fa-times btn btn-danger ' onclick=deleteDocument('"+rowData.encNumId+"',"+rowData.numId+") aria-hidden='true'></span></td>";	
					}				
					
					tableData +="</tr>";
				}
				/*reduce the td by  by varun */
				if(tableData==''){
					tableData+="<tr><td></td><td></td><td></td><td>No Data Found</td><td></td></tr>"
				}
				
				$('#example tbody').empty();
				$('#example').append(tableData);
				
				
			},				
			error : function(e) {					
				alert('Error: ' + e);				
			}
		});
		
		
	}
}

$(document).ready(function(){	

	$('#reset').click(function(){
		resetForm();
	});
	
	$('#form1').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  projectId:{	        	 
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('projectId').val();
	                          return (options != 0);
	                      }
	                  }
	        	  }
	          },
	          documentTypeId:{	        	 
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Document Type',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('documentTypeId').val();
	                          return (options != 0);
	                      }
	                  }
	        	  }
	          },
	          documentDate: {	             
	              validators: {
	                  notEmpty: {
	                      message: 'Document Date is required'
	                  }
	              }
	          },documentVersion: {	             
	              validators: {
	                  notEmpty: {
	                      message: 'Document Version is required'
	                  }
	              }
	          },
	          description:{	        	  
	              validators: {
	                  notEmpty: {
	                      message: 'Description is required and cannot be empty'
	                  },
	                  //Commented by devesh on 11-10-23 to fix page unresponsive error due to regex recursion
	                    /*regexp: {
	                        regexp: addressRegex,
	                       message: addressErrorMessage
	                    }*/
	                  //End of comment
	              }
	        	  
	          }
	      }
	  });
	
	$('#save').click(function(){
		if($('#docBasicDetails').hasClass('hidden')){
			$('#newUpload').trigger('click');
		}
	   	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
	    	if(checkForFileUpload()){
	    		document.getElementById("save").disabled = true; 				
	    		 $( "#form1")[0].submit();
	    		 return true;
	    	}else{
	    		swal('Please Upload atleast one file in any format');
	    	}				
			
		}else{
			console.log('Not Validated');		 
		}
		 
	});
		
});

function checkForFileUpload(){
	var flag = false;
	var uploadedFlag = new Array();
	
	/*$('.detailsId').map(function() {		
		if(this.value != 0){
			flag = true;
		}	    
	});*/
	
	//Commented by devesh on 25-09-23 to make upload file mandatory on edit
	/*if($('#numId').val() && $('#numId').val()!=0){
		flag = true;
	}*/
	
	if(!flag){
		$('.fileUpload').map(function() {
			if(this.files.length == 0){
				uploadedFlag.push(false);
			}else{
				uploadedFlag.push(true);			
			}	  
		});
		
		if(!(jQuery.inArray(true, uploadedFlag) !== -1)){		
			flag=false;			
		}else{
			flag=true;	
		}
	}
	return flag;
}



	function editDocument(documentId){
		//console.log(documentId);
		var documentTypeId = $('#documentTypeId').val();
		//var projectId = $('#projectId').val();
		if((documentTypeId == 0 )){
			swal(" Document Type is Required");
		}else{
			$('#newUpload').addClass('hidden');
			$('#docBasicDetails').removeClass('hidden');
			var resultArray = $('#'+documentId).find('td').map( function()
					{
					return $(this).text();
					}).get();
			console.log(resultArray);
			
			$('#documentDate').val(resultArray[0].trim()).trigger('input');
			/*comment the below lines by varun */
			/*if(resultArray[1].trim()){
				$('#periodFrom').val(resultArray[1].trim()).trigger('input');
			}
			
			if(resultArray[2].trim()){
				$('#periodTo').val(resultArray[2].trim()).trigger('input');
			}*/
			
			if(resultArray[1].trim()){
				$('#documentVersion').val(resultArray[1].trim()).trigger('input');
			}
			
			if(resultArray[3].trim()){
				$('#description').text(resultArray[3].trim()).trigger('input');
			}
         
			uploadFormatTable(documentTypeId);
			
			$('#numId').val(documentId);
		}
	}
	
	function uploadFormatTable(documentTypeId){
		$('#docBasicDetails').removeClass('hidden');
		//Added by devesh on 04-10-23 to set flag to false when file is uploaded
		const ApplicationId = $('#applicationId').val();
		localStorage.removeItem(ApplicationId+'totalProposalCostFlag');
        localStorage.removeItem(ApplicationId+'ProposalCostFlag');
        localStorage.removeItem(ApplicationId+'DurationFlag');
        localStorage.removeItem(ApplicationId+'ObjectivesFlag');
        localStorage.removeItem(ApplicationId+'previousTotalProposalCost');
        localStorage.removeItem(ApplicationId+'previousProposalCost');
        localStorage.removeItem(ApplicationId+'previousDuration');
        localStorage.removeItem(ApplicationId+'previousObjectives');
		//End of setting flag
		$.ajaxRequest.ajax({
	        type: "POST",
	        url: "/PMS/mst/documentFormatByDocumentType",
	        data: {
	        	"documentTypeId" : documentTypeId
	        	},			
			success : function(data) {		
				//console.log(data);
				var tableData='';
				if(data.length >0){
					$('#docUploadTbl tbody').empty();
					$('#docUploadTbl').removeClass('hidden');
					for(var i=0;i<data.length;i++){
						var row = data[i];
						tableData += "<tr><td class='hidden'> <input type='hidden' name='detailsModels["+i+"].documentFormatId' value='"+row.numId+"'/></td> ";
						tableData += "<td class='hidden'> <input type='hidden' class='detailsId' value='0' name='detailsModels["+i+"].numId'/>  </td> <td>"+row.formatName+" </td> ";
						// Changes
						tableData += "<td class='hidden'><input type='hidden' value='"+row.mimeType+"' id='formats"+i+"'/> </td>";
						tableData += "<td><input type='file' class='input-field fileUpload' onchange='return fileValidation("+i+")' name='detailsModels["+i+"].projectDocumentFile' id='file"+i+"'/> </td> </tr>";
						//endChanges
					}
				}				
				
				$('#docUploadTbl').append(tableData);	
				
			},				
			error : function(e) {					
				alert('Error: ' + e);				
			}
		});
	}
	function viewAllProposalDocument(){
		var proposalId = $('#proposalId').val();
	
		if(proposalId>0){
			var encProposalId ='';
			
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/getEncryptedKey",
		        async:false,
		        data: {
		        	"id" : proposalId
		        	},			
				success : function(data) {		
					encProposalId=data;
					
				},				
				error : function(e) {					
					alert('Error: ' + e);				
				}
			});
			
			viewProjectDetails('/PMS/mst/proposalDocumentDetails/'+encProposalId);
		}
	}
	function deleteDocument(recordId,rowId){
		
		//console.log(rowId);
		//console.log(recordId);
		if(recordId){	    	
	    	
	    	 swal({
			      title: "Are you sure you want to delete the record?",
			      icon: "warning",
			      buttons: [
			                'No',
			                'Yes'
			              ],
			      dangerMode: true,
			    }).then(function(isConfirm) {
			      if (isConfirm) {
			    		$.ajaxRequest.ajax({
					        type: "POST",
					        url: "/PMS/mst/deleteUploadedProposalDocument",
					        async:false,
					        data: {
					        	"encNumId" : recordId
					        	},			
							success : function(data) {
								console.log(data);
								if(data==true){
									$('#'+rowId).remove();
								}								
							},				
							error : function(e) {					
								alert('Error: ' + e);				
							}
						});
				      }				   
	    });
	    }
	   
	 
	}
	//Declare the function to get dynamic date as per the select document 
	function ondyncamicproposal(){
		
		  //console.log( "dynamic date print" + $('#dynamicDate').text().trim());
		   const myArray = $('#dynamicProposalDate').text().trim().split("--");
		   //console.log(myArray[0].trim());
		   if(myArray[0].trim()=="Administrative Approval"){
		  // if (myArray[0].trim().includes("Administrative Approval") && myArray[0].trim().includes("Proposal")){
			   $('#approvalProposal').removeClass("hidden");
			   $('#nonproposalapproval').addClass("hidden");
		   }
		   else if(myArray[0].trim()=="Proposal"){
			   $('#approvalProposal').removeClass("hidden");
			   $('#nonproposalapproval').addClass("hidden");
		   }
		   else if(myArray[0].trim()=="Memorandum of Understanding"){
			   $('#approvalProposal').removeClass("hidden");
			   $('#nonproposalapproval').addClass("hidden");
		   }
		   else if(myArray[0].trim()=="Work Order"){
			   $('#approvalProposal').removeClass("hidden");
			   $('#nonproposalapproval').addClass("hidden");
		   }
		   
		   else if(myArray[0].trim()=="Estimated Project Cost"){
			   $('#approvalProposal').removeClass("hidden");
			   $('#nonproposalapproval').addClass("hidden");
		   }
		  
		   else{
			   $('#approvalProposal').addClass("hidden");
			   $('#nonproposalapproval').removeClass("hidden");
		   }
	}
	/*document.addEventListener('DOMContentLoaded', function() {
	    const totalProposalCostFlag = localStorage.getItem('totalProposalCostFlag');
	    const ProposalCostFlag = localStorage.getItem('ProposalCostFlag');

	    if (totalProposalCostFlag === 'true' || ProposalCostFlag === 'true') {
	      // Do something when the flag is true, e.g., show a message or perform an action
	      alert('Flag is true! Value changed in the previous form.');
	      
	      // Optionally, you can reset the flag if needed
	      localStorage.setItem('totalProposalCostFlag', 'false');
	      localStorage.setItem('ProposalCostFlag', 'false');
	    }
	  });*/
	
	/*document.addEventListener('DOMContentLoaded', function() {
	    const ProposalCostFlag = localStorage.getItem('ProposalCostFlag');

	    if (ProposalCostFlag === 'true') {
	      // Do something when the flag is true, e.g., show a message or perform an action
	      alert('Flag is true! Value changed in the previous form.');
	      
	      // Optionally, you can reset the flag if needed
	      localStorage.setItem('ProposalCostFlag', 'false');
	    }
	  });*/
	