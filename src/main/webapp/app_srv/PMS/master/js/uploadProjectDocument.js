var addressRegex='';
var addressErrorMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	addressRegex = messageResource.get('address.regex', 'regexValidationforjs');
	addressErrorMessage = messageResource.get('address.regex.message', 'regexValidationforjs');
});

function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectID").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}



function gonext(){
	 var path=$(location).attr('pathname');  
	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectID").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();
}


$(document).ready(function(){
	   $('.select2Option').select2({
	    	 width: '100%'
	    });
	   
	
	
	   var projectStartDate = $('#projectStartDate').text().trim();
		var projectEndDate = $('#projectEndDate').text().trim();
		
		var minDate;
		var maxDate;
		if(projectStartDate){
			var temp = projectStartDate.split("/");
			if(temp.length == 3){
				minDate = new Date(temp[2],temp[1]-1,temp[0]);
			}			
		}
		if(projectEndDate){
			var temp = projectEndDate.split("/");
			if(temp.length == 3){
				maxDate = new Date(temp[2],temp[1]-1,temp[0]);
			}			
		}
	   
	   $('#documentDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		   changeMonth: true,
		    changeYear:true,
		    minDate: minDate,
		    maxDate:maxDate,
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
	var projectId = $('#projectId').val();
	if((documentTypeId == 0 || projectId == 0)){
		swal("Project Name and Document Type is Required");
	}else{
		uploadFormatTable(documentTypeId);
	}	
});


$(document).on('click','#documentTypeId',function(e){
	loadUploadedDoc();	
	// On Select the document Name display all document according to document name 
	$('#example1').addClass('hidden');
	$('#example').removeClass('hidden');
});

$(document).on('click','#projectId',function(e){
	loadUploadedDoc();
	callprojectdate();	
});

function loadUploadedDoc(){
	var documentTypeId = $('#documentTypeId').val();
	var projectId = $('#projectId').val();
	blankFormAndResetDate(projectId);
	if((documentTypeId == 0 || projectId == 0)){
		$('#example tbody').empty();
		$('#newUploadDiv').addClass('hidden');
		$('#docBasicDetails').addClass('hidden');
		// If No Document Name Selected hide the example div
		$('#example').addClass('hidden');
	}else{
		$('#newUploadDiv').removeClass('hidden');
		$.ajax({
	        type: "POST",
	        url: "/PMS/mst/uploadedProjectDocument",
	        data: {"projectId" : projectId,
	        	"documentTypeId" : documentTypeId
	        	},			
			success : function(data) {		
				//console.log(data);
				var tableData ='';
				var numCols = $("#example").find('tr')[0].cells.length;
				//console.log(numCols);
				for(var i=0;i<data.length;i++){
					var rowData = data[i];
					tableData += "<tr id="+rowData.numId+"><td>"+ rowData.documentDate+" </td>";
					/*hide the if else condition of valid from by varun on 01-11-2023*/
					if(rowData.periodFrom){
						tableData +=' <td class="hidden">'+rowData.periodFrom+ '</td>' ;
					}else{
						tableData +=' <td class="hidden"> </td>';
					}
					
					/*hide the if else condition of valid upto by varun on 01-11-2023*/
					if (rowData.periodTo) {
					    tableData += ' <td class="hidden">' + rowData.periodTo + '</td>';
					} else {
					    tableData += ' <td class="hidden"> </td>';
					}
					
					tableData +="<td>"+rowData.documentVersion+"</td>";
					var uploads = rowData.detailsModels;
					downloadItem = '<p>';
					for(var j=0;j<uploads.length;j++){
						var temp = uploads[j];
						if(j<uploads.length-1){
							downloadItem +="<a onclick=downloadDocument('"+temp.encNumId+"')> "+temp.icon+"</a> || ";
						}else{
							downloadItem +="<a onclick=downloadDocument('"+temp.encNumId+"')> "+temp.icon+"</a>";
						}
					}
					downloadItem+='</p>';
					tableData += "<td>"+downloadItem+"</td>";
					
					if(rowData.description){
						tableData += "<td >"+rowData.description+"</td>";
					}else{
						tableData += "<td ></td>";
					}
					/*reduce the colspan by 7 by varun */
					if(numCols==7){
						tableData +="<td> <span class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' onclick=editDocument("+rowData.numId+") aria-hidden='true'></span></td>";	
						tableData +=" <td class=\"hidden\"> <span class='fa fa-times btn btn-danger ' onclick=deleteDocument('"+rowData.encNumId+"',"+rowData.numId+") aria-hidden='true'></span></td>";	
					}	
					
					
					tableData +="</tr>";
				}
				if(data.length<=0){
					tableData+="<tr><td>No Data Found</td></tr>"
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
	                    regexp: {
	                        regexp: addressRegex,
	                       message: addressErrorMessage
	                    }
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
	
	if($('#numId').val() && $('#numId').val()!=0){
		flag = true;
	}
	
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
		var projectId = $('#projectId').val();
		if((documentTypeId == 0 || projectId == 0)){
			swal("Project Name and Document Type is Required");
		}else{
			$('#newUpload').addClass('hidden');
			$('#docBasicDetails').removeClass('hidden');
			var resultArray = $('#'+documentId).find('td').map( function()
					{
					return $(this).text();
					}).get();
			console.log(resultArray);
			
			$('#documentDate').val(resultArray[0].trim()).trigger('input');
			if(resultArray[1].trim()){
				$('#periodFrom').val(resultArray[1].trim()).trigger('input');
			}
			
			if(resultArray[2].trim()){
				$('#periodTo').val(resultArray[2].trim()).trigger('input');
			}
			
			if(resultArray[3].trim()){
				$('#documentVersion').val(resultArray[3].trim()).trigger('input');
			}
			//change .text to .val by varun on 15-11-2023
			if(resultArray[5].trim()){
				$('#description').val(resultArray[5].trim()).trigger('input');
			}
			
			uploadFormatTable(documentTypeId);
			
			$('#numId').val(documentId);
		}
	}
	
	function uploadFormatTable(documentTypeId){
		$('#docBasicDetails').removeClass('hidden');
		$.ajax({
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
						// Changes to get MIME TYPE of file format
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
	// Function for Validating the file type
	  function fileValidation(a){
		  var fileformat = document.getElementById('formats'+a+'').value;		  
		  const fileTypeArr = fileformat.split(",");
		  const formatlist = [];
		  for(var i=0;i<fileTypeArr.length;i++){
			  formatlist.push(fileTypeArr[i]); 
		  }
	
	        var fileUpload = document.getElementById('file'+a+'');
	        var isValid=1;
	        
	        if (fileUpload.files.length > 0) {
	            // Check file size
	            var fileSize = fileUpload.files[0].size; // in bytes
	            var maxSize = 15 * 1024 * 1024; // 15 MB in bytes

	            if (fileSize > maxSize) {
	                swal("The file size exceeds the limit.");
	                fileUpload.value = null;
	                return;
	            }
	        }
	    	for(var k=0;k<formatlist.length;k++)
	    	{
	    		if(fileUpload.files[0].type===formatlist[k]){
	    			isValid=1;
	    			return;
	    		}else{
	    			isValid=2;
	    		}
	    	}
	        if (isValid===2) {
	          swal("Please Select Appropriate File Type !");
	          fileUpload.value = null;
	        }
	      }
	  // End Changes
	function viewAllProjectDocument(){
		var projectId = $('#projectId').val();
		if(projectId>0){
			var encProjectId ='';
			//changed $.ajaxRequest.ajax to $.ajax
			$.ajax({
			    type: "POST",
			    url: "/PMS/getEncryptedKey",
			    async: false,
			    data: {
			        "id": projectId
			    },
			    success: function(data) {
			        encProjectId = data;
			    },
			    error: function(e) {
			        alert('Error: ' + e);
			    }
			});
			
			viewProjectDetails('/PMS/mst/documentDetails/'+encProjectId);
		}
	}
			/*$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/getEncryptedKey",
		        async:false,
		        data: {
		        	"id" : projectId
		        	},			
				success : function(data) {		
					encProjectId=data;
					
				},				
				error : function(e) {					
					alert('Error: ' + e);				
				}
			});
			
			viewProjectDetails('/PMS/mst/documentDetails/'+encProjectId);
		}
	}*/
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
					        url: "/PMS/mst/deleteProjectDocument",
					        async:false,
					        data: {
					        	"encNumId" : recordId
					        	},			
							success : function(data) {
								//console.log(data);
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
	/*function submit_form_delete()
	{
		alert("inside submit_form_delete ");
	document.form_document_mst.action = "/PMS/mst/deleteDocument";
	document.getElementById("form_document_mst").submit();
	}*/
	
	
	$(document).on('change','#documentTypeId',function(e){
		var projectId = $('#projectId').val();
		var documentTypeId = $('#documentTypeId').val();
		if((projectId == 0)){
			swal("Project Name is Required");
		}else{
			uploadFormatTable(documentTypeId);
		}	
	}); 
	

	function redirectPage(path){
		var encProjectId = $('#encProjectId').val();
		//alert(projectId);
		openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
	}
	
	function blankFormAndResetDate(projectId){
		$('#documentDate').datepicker('setDate', null);
		$('#periodFrom').datepicker('setDate', null);
		$('#periodTo').datepicker('setDate', null);
		$('#documentVersion').val('');
		$('#description').val('');
		
	}
	
	
	function callprojectdate(){
		var projectId = $('#projectId').val();
		$.ajax({
	        type: "POST",
	        url: "/PMS/mst/projectDate",
	        data: {
	        	"numProjectId" : projectId
	        	},			
			success : function(data) {		
				
					
					var projectStartDate = data.startDate;
					var projectEndDate = data.endDate;
					
					var minDate;
					var maxDate;
					if(projectStartDate){
						var temp = projectStartDate.split("/");
						if(temp.length == 3){
							minDate = new Date(temp[2],temp[1]-1,temp[0]);
						}			
					}
					if(projectEndDate){
						var temp = projectEndDate.split("/");
						if(temp.length == 3){
							maxDate = new Date(temp[2],temp[1]-1,temp[0]);
						}			
					}
				 
				 if( minDate && maxDate){
				 $('#documentDate').datepicker('option', { minDate:minDate,
                                  maxDate: maxDate });
				}else if (minDate){
					 $('#documentDate').datepicker('option', { minDate:minDate});
				}else if (maxDate){
				 $('#documentDate').datepicker('option', {maxDate: maxDate });
				}
				
				
			},				
			error : function(e) {					
				alert('Error: ' + e);				
			}
		});
		
		}
	
	//declare below function by varun the get the dynamic date value on 15-11-2023
	function ondyncamicchange(){
		
		  //console.log( "dynamic date print" + $('#dynamicDate').text().trim());
		   const myArray = $('#dynamicDate').text().trim().split("--");
		   //console.log(myArray[0].trim());
		   if(myArray[0].trim()=="Administrative Approval"){
		  // if (myArray[0].trim().includes("Administrative Approval") && myArray[0].trim().includes("Proposal")){
			   $('#administrativeapproval').removeClass("hidden");
			   $('#nonapprovals').addClass("hidden");
		   }
		   else if(myArray[0].trim()=="Proposal"){
			   $('#administrativeapproval').removeClass("hidden");
			   $('#nonapprovals').addClass("hidden");
		   }
		   else if(myArray[0].trim()=="Memorandum of Understanding"){
			   $('#administrativeapproval').removeClass("hidden");
			   $('#nonapprovals').addClass("hidden");
		   }
		   else if(myArray[0].trim()=="Work Order"){
			   $('#administrativeapproval').removeClass("hidden");
			   $('#nonapprovals').addClass("hidden");
		   }
		   
		   else if(myArray[0].trim()=="Estimated Project Cost"){
			   $('#administrativeapproval').removeClass("hidden");
			   $('#nonapprovals').addClass("hidden");
		   }
		  
		   else{
			   $('#administrativeapproval').addClass("hidden");
			   $('#nonapprovals').removeClass("hidden");
		   }
	}
	
	