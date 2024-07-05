var fileUploadClass =[];
$(document).ready(function(){
	initiateCalendar();
	
	$('#save').click(function(){
		var test = document.getElementsByClassName("test");
		
		for (var i = 0; i < test.length; i++) {
			var b = test[i];
			const input = b.querySelector("input[type='file']");
			if (input && input.value) {
				console.log("Input field with 'Choose File' selected:");
				var fieldId = input.id;
				var classId = $('#'+fieldId).attr("class").split('_')[1];		
				var tempId = fieldId.split('_');
				var docTypeFormat = $('#docTypeFormat_'+tempId[1]).val();
				var docTypeFormatTemp = docTypeFormat.split('_');
				
				var encDocTypeId= docTypeFormatTemp[0];
				var encFormatId= docTypeFormatTemp[1];
				var encProjectId = $('#encProjectId').val();
				
				var formData = new FormData();
				formData.append("encProjectId",encProjectId);
				formData.append("encDocumentFormatId",encFormatId);
				formData.append("encDocumentTypeId",encDocTypeId);
				formData.append("projectDocumentFile",input.files[0]);
				
				uploadProjectFileAfterCheck(formData,classId);
			}
		}
		validateForm();
	});
	
	var currentDate = new Date();
	
	var projectStart = $('#projectStartDate').text().trim();	
	var startDateParts = projectStart.split("/");
	var projectStartDate = new Date(+startDateParts[2], startDateParts[1] - 1, +startDateParts[0]);
	
	$('#closureDate').datepicker({
		dateFormat:'dd/mm/yy',
		  changeMonth: true,
		  changeYear: true,
		  minDate: projectStartDate,
		  maxDate: currentDate,
		  defaultDate:currentDate,
		  onClose: function(selectedDate) {
			  initiateCalendar();
		     }
		});
	
});

function initiateCalendar(){
	
	var closure= $('#closureDate').val();
	var baseEndDate;
	
	if(closure){
		var closureParts = closure.split("/");
		baseEndDate = new Date(+closureParts[2], closureParts[1] - 1, +closureParts[0]);
		$(".datePicker").datepicker("destroy");
		$(".datePicker").each(function(index){
			var currentPickerValue = $('#picker_'+index).val();
				
			if(currentPickerValue){
				var endDateParts =currentPickerValue.split("/");
				var currentDatePicker = new Date(+endDateParts[2], endDateParts[1] - 1, +endDateParts[0]);
				if(currentDatePicker > baseEndDate){					
					$('#picker_'+index).val('');
				}
			}
		});
		
	}else{
		var projectEnd = $('#projectEndDate').text().trim();	
		var endDateParts = projectEnd.split("/");
		var projectEndDate = new Date(+endDateParts[2], endDateParts[1] - 1, +endDateParts[0]);
		var currentDate = new Date();	
		if(currentDate < projectEndDate){
			baseEndDate = currentDate;
		}else{
			baseEndDate = projectEndDate;
		}
	}

	$('.startDate').each(function(){
		var fieldId = this.id;
		var defaultDate;
		var selectId = fieldId.split('startDate_')[1];		
		var dateString = $('#'+fieldId).text().trim(); 
		
		var dateParts = dateString.split("/");		
		var dateObject= new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
		
		var endString = $('#end_'+selectId).text().trim();
		if(endString){
			var endParts = endString.split("/");		
			defaultDate= new Date(+endParts[2], endParts[1] - 1, +endParts[0]);
		}
		
		if(!defaultDate){
			defaultDate = baseEndDate;
		}
		
		$('#picker_'+selectId).datepicker({
			dateFormat:'dd/mm/yy',
			  changeMonth: true,
			  changeYear: true,
			  minDate: dateObject,
			  maxDate: baseEndDate,
			  defaultDate:defaultDate
			});
	});
}

function validateForm(){
	
	var submissionFlag = true;
	
	$('.true').each(function(index){		
		var num_id = $(this).attr('class').match(/\d+/)[0];
		var encProjectId = $('#encProjectId').val();
		var docTypeId=$('#docuId_'+num_id).val();
		var docTypeName=$('#docuName_'+num_id).val();
		/*alert(docTypeId);
		alert("projectId"+encProjectId);
		alert(docTypeName);*/
		$.ajax({
		    url: "/PMS/mst/isFileUploaded", 
		    type: "POST",	
		    data : {"encProjectId":encProjectId,
				"encStrId":docTypeId
				},
		  
			success :function(e){
				
		     if(e=='notUploaded'){
		    	 $('#docName_'+num_id).addClass('validatebox-invalid');
		    	 //sweetSuccess('Attention','Please upload Document for '+docTypeName);
					submissionFlag = false;
					return false;		
		     }
		        }
		});	
	/*	if(!(jQuery.inArray(num_id, fileUploadClass) !== -1)){		
			$('#docName_'+num_id).addClass('validatebox-invalid');
			sweetSuccess('Attention','Please upload Document in Row No '+(index+1));
			submissionFlag = false;
			return false;			
		}	*/	
	})
	
	var closureRemark = $('#closureRemark').val().trim();
	if(!closureRemark){
		sweetSuccess('Attention','Closure Remark is required');
		submissionFlag = false;
		return false;
	}else{
		var closureLength = closureRemark.length;
		if(closureLength > 2000){
			sweetSuccess('Attention','Only 2000 chars are allowed in Closure Remark. Current length is '+closureLength);
			submissionFlag = false;
			return false;
		}
	}

	
	if(submissionFlag == true){
		//Save Details
		var closureDate = $('#closureDate').val();	
		
		var teamArray = [];
		$('.datePicker').each(function(){
			 var rowObject = new Object();
			 var fieldId = this.id;
			 var tempId = fieldId.split('_');
			 var empRoleId = $('#empRole_'+tempId[1]).val();
			 
			 var fieldVal = this.value;			 
			 if(!fieldVal){
				 fieldVal = $('#end_'+tempId[1]).text().trim();
			 }			 
			
			 rowObject.empRoleId = empRoleId;
			 rowObject.releaseDate = fieldVal;
			 teamArray.push(rowObject);
		});
		var team = JSON.stringify(teamArray);
		var encProjectId = $('#encProjectId').val();
		
		var formData = new FormData();
		formData.append('closureDate',closureDate);
		formData.append('closureRemark',closureRemark);
		formData.append("teamDetails",team);
		formData.append("encProjectId",encProjectId);
		formData.append("status","Completed");
		
		
		
	$.ajax({
		    url: "/PMS/mst/projectClosure", 
		    type: "POST",		   
		    cache: false,
		    contentType: false,
		    processData: false,
		    data: formData,
			success :function(e){		    	            
		          if(e == true){
		        	  
		        	  //sweetSuccess('Attention', 'Closure request initiated, Updates on this closure request will be available under Menu-> Projects -> Under Closure Projects');
		        	  //openWindowWithPost('GET','/PMS/mst/underClosureProjects','_self');
		        	  // window.location.href='/PMS/mst/getAllCompletedProject';
		        	 // window.close();
		        		//openWindowWithPost('GET','/PMS/mst/projectClosure',{"encProjectId" : encProjectId},'_self');
		        	  /*-------------------- hold the alert message [14-09-2023] ---------------------*/
		        	  
		        	  /*---------------------------- Update from Menu or Dashboard [06-12-2023] ---------*/
		        	  if($('#backButtonStatus').val()){
			        	  swal({
			        		  title: "Attention",
			        		  text: "Update Closure request Successfully, Updates on this closure request will be available under Menu-> Projects -> Under Closure Projects",
			        		  button: "Ok",
			        		}).then(function(isConfirm) {
								if (isConfirm) {
										openWindowWithPost(
												'GET',
												'/PMS/dashboard',{
													"shortCode":"true"
												},
												'_self');
								}
							});
		        	  }else{
			        	  swal({
			        		  title: "Attention",
			        		  text: "Closure request initiated, Updates on this closure request will be available under Menu-> Projects -> Under Closure Projects",
			        		  button: "Ok",
			        		}).then(function(isConfirm) {
								if (isConfirm) {
										openWindowWithPost(
												'GET',
												'/PMS/mst/underClosureProjects',
												'_self');
								}
							});
		        	  }
		        	  /*---------------------------- End Update from Menu or Dashboard [06-12-2023] ---------*/
		        	  return false;
		        	
		        	    setTimeout(function(){
		        		  window.location.href='/PMS/mst/getAllCompletedProject';
		        	  });
		        	  //Updates on this closure request will be available under Menu-> Projects -> Under Closure Projects
		          }else if(e == false){
		        	  sweetSuccess('Attention', 'Something went wrong');
		        	  return false;
		          }else{
		        	  sweetSuccess('Attention', 'You are not authorised');
		        	  return false;  
		          }
		          
		        }
		});	
		
	}
	
}

	function checkForFileUpload(classForTest){
		var flag = false;
		var uploadedFlag = new Array();
		
			$('.file_'+classForTest).map(function() {
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
		
		return flag;
	}

	function uploadProjectFile(input){
		var editMode=$('#editMode').val();

		var fieldId = input.id;
		var classId = $('#'+fieldId).attr("class").split('_')[1];		
		var tempId = fieldId.split('_');
		var docTypeFormat = $('#docTypeFormat_'+tempId[1]).val();
		var docTypeFormatTemp = docTypeFormat.split('_');
		
		var encDocTypeId= docTypeFormatTemp[0];
		var encFormatId= docTypeFormatTemp[1];
		var encProjectId = $('#encProjectId').val();
		
		var formData = new FormData();
		formData.append("encProjectId",encProjectId);
		formData.append("encDocumentFormatId",encFormatId);
		formData.append("encDocumentTypeId",encDocTypeId);
		formData.append("projectDocumentFile",input.files[0]);
		
		if(editMode==1){
			$.ajax({
			    url: "/PMS/mst/updateUploadProjectFile", 
			    type: "POST", 
			    cache: false,
			    contentType: false,
			    processData: false,
			    data: formData
			    }).done(function(e){		    	            
			          $('#'+fieldId).val('');
			          if(e == 1){
			        	  fileUploadClass.push(classId);
			        	  sweetSuccess('Attention','File uploaded successfully');
			        	  return true;
			          }else if(e == 2 || e == -11){		        	  
			        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
			        	  return false;
			          }else if(e == -10){
			        	  sweetSuccess('Attention','Please choose file');
			        	  return false;
			          }else if(e == -12){
			        	  sweetSuccess('Attention','File Type not supported');
			        	  return false;
			          }else{
			        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
			        	  return false;
			          }
			        });	
		}
		else{
			$.ajax({
			    url: "/PMS/mst/uploadProjectFile", 
			    type: "POST", 
			    cache: false,
			    contentType: false,
			    processData: false,
			    data: formData
			    }).done(function(e){		    	            
			          $('#'+fieldId).val('');
			          if(e == 1){
			        	  fileUploadClass.push(classId);
			        	  sweetSuccess('Attention','File uploaded successfully');
			        	  return true;
			          }else if(e == 2 || e == -11){		        	  
			        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
			        	  return false;
			          }else if(e == -10){
			        	  sweetSuccess('Attention','Please choose file');
			        	  return false;
			          }else if(e == -12){
			        	  sweetSuccess('Attention','File Type not supported');
			        	  return false;
			          }else{
			        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
			        	  return false;
			          }
			        });	
		}
		
	}

	function downloadTempDocument(documentId){
		
		openWindowWithPost('POST','/PMS/mst/downloadTempProjectFile',{"encNumId" : documentId},'_blank');
	}
	
	function changeEffectiveDate(){
		var closureDate= $("#closureDate").val();
		
		$( ".effectiveUpto" ).each(function( index ) {
			 
			  if($( this ).val()=='' || $( this ).val()==null){
				  $("#picker_"+index).val(closureDate);
			  }
			});
	}
	
	
/*---------------- Check File Format and Upload File -------------------*/
	function checkFileFormat(input){
		var editMode=$('#editMode').val();

		var fieldId = input.id;
		var classId = $('#'+fieldId).attr("class").split('_')[1];		
		var tempId = fieldId.split('_');
		var docTypeFormat = $('#docTypeFormat_'+tempId[1]).val();
		var docTypeFormatTemp = docTypeFormat.split('_');
		
		var encDocTypeId= docTypeFormatTemp[0];
		var encFormatId= docTypeFormatTemp[1];
		var encProjectId = $('#encProjectId').val();
		
		var formData = new FormData();
		formData.append("encProjectId",encProjectId);
		formData.append("encDocumentFormatId",encFormatId);
		formData.append("encDocumentTypeId",encDocTypeId);
		formData.append("projectDocumentFile",input.files[0]);

		$.ajax({
		    url: "/PMS/mst/checkUploadProjectFile", 
		    type: "POST", 
		    cache: false,
		    contentType: false,
		    processData: false,
		    data: formData
		    }).done(function(e){		    	            
		          if(e == 1){
		        	  //uploadProjectFileAfterCheck(formData,classId);
		        	  return true;
		          }else if(e == -10){
		        	  sweetSuccess('Attention','Please choose file');
		        	  return false;
		          }else if(e == -12){
		        	  sweetSuccess('Attention','File Type not supported');
			          $('#'+fieldId).val('');
		        	  return false;
		          }else{
		        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
		        	  return false;
		          }
		        });	
	}
	
		function uploadProjectFileAfterCheck(formData,classId){
			var editMode=$('#editMode').val();
			
			if(editMode==1){
				$.ajax({
				    url: "/PMS/mst/updateUploadProjectFile", 
				    type: "POST", 
				    cache: false,
				    contentType: false,
				    processData: false,
				    data: formData
				    }).done(function(e){		    	            
				          if(e == 1){
				        	  fileUploadClass.push(classId);
				        	  //sweetSuccess('Attention','File uploaded successfully');
				        	  return true;
				          }else if(e == 2 || e == -11){		        	  
				        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
				        	  return false;
				          }else if(e == -10){
				        	  sweetSuccess('Attention','Please choose file');
				        	  return false;
				          }else if(e == -12){
				        	  sweetSuccess('Attention','File Type not supported');
					          $('#'+fieldId).val('');
				        	  return false;
				          }else{
				        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
				        	  return false;
				          }
				        });	
			}
			else{
				$.ajax({
				    url: "/PMS/mst/uploadProjectFile", 
				    type: "POST", 
				    cache: false,
				    contentType: false,
				    processData: false,
				    data: formData
				    }).done(function(e){		    	            
				          if(e == 1){
				        	  fileUploadClass.push(classId);
				        	  //sweetSuccess('Attention','File uploaded successfully');
				        	  return true;
				          }else if(e == 2 || e == -11){		        	  
				        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
				        	  return false;
				          }else if(e == -10){
				        	  sweetSuccess('Attention','Please choose file');
				        	  return false;
				          }else if(e == -12){
				        	  sweetSuccess('Attention','File Type not supported');
				        	  return false;
				          }else{
				        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
				        	  return false;
				          }
				        });	
			}
			
		}
		
//Added by devesh on 25-10-23 to hide rows for PM, HOD and PI
$(document).ready(function() {
    $("tr:has(td:contains('Head of Department')), " +
      "tr:has(td:contains('Project In-charge')), " +
      "tr:has(td:contains('Project Manager'))").hide();
});
//End of function