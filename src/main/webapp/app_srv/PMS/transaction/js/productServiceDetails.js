var nameRegex ='';
var nameRegexMessage ='';
var numericRegex='';
var numericRegexMessage='';
var nonRestrictnameRegex='';
var nonRestrictnameRegexMessage='';

	messageResource.init({	  
	filePath : '/PMS/resources/app_srv/PMS/global/js'
	});

	messageResource.load('regexValidationforjs', function(){
		nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
		nameRegexMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
		
		numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
		numericRegexMessage= messageResource.get('numeric.regex.message', 'regexValidationforjs');
		
		nonRestrictnameRegex=messageResource.get('nonRestrictname.regex', 'regexValidationforjs');
		nonRestrictnameRegexMessage= messageResource.get('nonRestrictname.regex.message', 'regexValidationforjs');
	});

	$(document).ready(function(){
		
		
		
		$('#form').bootstrapValidator({
			excluded: ':disabled',
			     message: 'This value is not valid',
			     feedbackIcons: {
			         valid: 'glyphicon glyphicon-ok',
			         invalid: 'glyphicon glyphicon-remove',
			         validating: 'glyphicon glyphicon-refresh'
			     },
			     fields: {

			    	 strServiceTitle: {		             
			             validators: {
			                 notEmpty: {
			                     message: 'Title of service is required and cannot be empty'
			                 },
			                 stringLength: {
			                       max: 250,
			                       message: 'Title of service is can not be greater than 250 digits'
			                   },
			                 regexp: {
				                 regexp: nameRegex,
				                 message: nameRegexMessage
			                   }
			             }
			         },
			         numStateId:{		        	  
			        	  validators: {
			                  callback: {
			                      message: 'Please choose State',
			                      callback: function(value, validator) {		                          
			                          var options = validator.getFieldElements('numStateId').val();
			                          return (options != 0);
			                      }
			                  }
			              }
			          },

				       
     strDescription: {
				             group: '.col-md-12',
				             validators: {
				                 notEmpty: {
				                     message: 'Description is required and cannot be empty'
				                 },
				                 stringLength: {
				                       max: 2000,
				                       message: 'Description is can not be greater than 2000 digits'
				                   },
				                 regexp: {
				                 regexp: nonRestrictnameRegex,
				                      message: nonRestrictnameRegexMessage
				                   }
				             }
				         },
	
				         strObjective: {
				             group: '.col-md-12',
				             validators: {
				                 notEmpty: {
				                     message: 'Objective is required and cannot be empty'
				                 },
				                 stringLength: {
				                       max: 2000,
				                       message: 'Objective is can not be greater than 2000 digits'
				                   },
				                 regexp: {
				                 regexp: nonRestrictnameRegex,
				                      message: nonRestrictnameRegexMessage
				                   }
				             }
				         },
	
				         
				         strPastDeploymentDetails: {
				             group: '.col-md-12',
				             validators: {
				                 notEmpty: {
				                     message: 'Past Deployment Details is required and cannot be empty'
				                 },
				                 stringLength: {
				                       max: 500,
				                       message: 'Agency name is can not be greater than 500 digits'
				                   },
				                 regexp: {
				                 regexp: nameRegex,
				                      message: nameRegexMessage
				                   }
				             }
				         },
				        	 
				         strCdacRole: {
				             group: '.col-md-12',
				             validators: {
				                 notEmpty: {
				                     message: 'Cdac role is required and cannot be empty'
				                 },
				                 regexp: {
				                 regexp: nameRegex,
				                      message: nameRegexMessage
				                   }
				             }
				         },
				         strInauguratedBy: {
				             group: '.col-md-12',
				             validators: {
				                 notEmpty: {
				                     message: 'Launch/Inaugurated By  is required and cannot be empty'
				                 },
				                 regexp: {
				                 regexp: nameRegex,
				                      message: nameRegexMessage
				                   }
				             }
				         },
				         dtProductServiceDate:{
			             validators: {
			                       notEmpty: {
			                           message: 'Date is required and cannot be empty'
			                       },
			             date:{
			             format: 'DD/MM/YYYY'
			             }
			             }
			             },
			             strCity: {
				             group: '.col-md-12',
				             validators: {
				                 notEmpty: {
				                     message: 'City is required and cannot be empty'
				                 },
				                 regexp: {
				                 regexp: nameRegex,
				                      message: nameRegexMessage
				                   }
				             }
				         },
			             
			         
			         
			     }
			 });
		
		
	})


$('.select2Option').select2({
	 width: '100%'
});
$(document).ready(function() {     
	var table = $('#productServiceDetails').DataTable( {     
		"paging":   false,        
		"searching":false,
		"ordering": false,
	    "info":     false,
		rowReorder: true   
		} );
	
var newTable = $('#imagesDetailsTable').DataTable( {     
	"paging":   false,        
	"searching":false,
	"ordering": false,
    "info":     false,
	rowReorder: true  
	
	} );
});
$("#dtProductServiceDate").datepicker({ 
	dateFormat: 'dd/mm/yy', 
    changeMonth: true,
    changeYear:true,
    onSelect: function(date){
    	$('#dtProductServiceDate').trigger('input');
    }
});

function resetForm(){
	$('#numProductServiceDetailsId').val(0);
	$('#strServiceTitle').val('');
	$('#strDescription').val('');
	$('#dtProductServiceDate').val('');
	$('#strInauguratedBy').val('');
	$('#strCdacRole').val('');
	$('#numStateId').val(0).trigger('change');
	$('#strPastDeploymentDetails').val('');
	$('#strCity').val('');
	$('#strObjective').val('');
	$('#strCoordinator').val('');
	$('#strCollaborator').val('');
	$('#strCollaboratorsTotParners').val('');
	$('#strDocumentIds').val('');
	$('#save').text('Save');
}
$(document).ready(function() {
	
	$('#reset').click(function(){
		resetForm();
		 $('#form').data('bootstrapValidator').resetForm(true);
	});
	
	$('#save').click(function(e){	
		  
	   
	    $("#numGroupCategoryId").val("56072020");
	    var bootstrapValidator = $("#form").data('bootstrapValidator');
	    bootstrapValidator.validate();
	        if(bootstrapValidator.isValid()){
	        	document.getElementById("save").disabled = true;
		      $('#form')[0].submit();
		      return true;
	        }
	        else
	        	{
	        	  console.log('Not Validated');
	        	  return false;
	        	}
		
	
});
	
	
	$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		
		$('#numProductServiceDetailsId').val(resultArray[15].trim());
		$('#strServiceTitle').val(resultArray[1].trim());
		$('#strDescription').val(resultArray[9].trim());
		$('#dtProductServiceDate').val(resultArray[2].trim())
		$('#strInauguratedBy').val(resultArray[3].trim());
		$('#strCdacRole').val(resultArray[4].trim());
		$('#numStateId').val(resultArray[10].trim()).trigger('change');
		$('#strPastDeploymentDetails').val(resultArray[5].trim());
		$('#strCity').val(resultArray[7].trim());
		$('#strObjective').val(resultArray[11].trim());
		$('#strCoordinator').val(resultArray[12].trim());
		$('#strCollaborator').val(resultArray[13].trim());
		$('#strCollaboratorsTotParners').val(resultArray[14].trim());
		$('#strDocumentIds').val(resultArray[16].trim());
		$('#save').text("update");
		document.getElementById("strServiceTitle").focus();
		
	});
	
});

$(document).ready(function() {
	
	$('#delete').click(function(){
		deleteProductServiceDetails();
		});
function deleteProductServiceDetails(){
	
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
        
    });
    
    var selected;
    selected = chkArray.join(','); 
    
    if(selected.length >= 1){
    	
    	
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
		    	  
			    	  $("#numProductServiceDetailsIds").val(selected);
			  		  submit_form_delete();
			      }
	  	 
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Detail to delete");  
}
 
}
function submit_form_delete()
{
	document.getElementById("form").action="/PMS/deleteProductServiceDetails";
	document.getElementById("form").method="POST";
	document.getElementById("form").submit();
	
}


$('#deleteImages').click(function(){
	  deleteImageDetails();
	});
});

function uploadQualityImages(input){
	var docIds =[];  
	var docId= $("#strDocumentIds").val();
	var formData = new FormData();

	formData.append("strDocumentCaption",$("#strDocumentCaption").val());
	formData.append("encCategoryId",$("#encCategoryId").val());
	formData.append("encMonthlyProgressId",$("#encMonthlyProgressId").val());
	formData.append("progressReportQualityImages",input.files[0]);
	
	if($("#strDocumentCaption").val()!=null && $("#strDocumentCaption").val()!='')
{		
	$.ajax({
	    url: "/PMS/uploadProgressRportImages", 
	    type: "POST", 
	    cache: false,
	    contentType: false,
	    processData: false,
	    data: formData
	    }).done(function(e){		    	            
	        
	    	 if(e == -2 || e == -11){		        	  
	        	  sweetSuccess('Attention','Something went wrong. Please contact Admin');
	        	  return false;
	          }else if(e == -10){
	        	  sweetSuccess('Attention','Please choose file');
	        	  return false;
	          }else if(e == -12){
	        	  sweetSuccess('Attention','File Type not supported');
	        	  return false;
	          }
	          else{
	        	  $("#strDocumentCaption").val("");
     	         
      	        if(docId)
      	         {
      	        	 docIds.splice(0, docIds.length);
      	        	 docIds=docId.split(",");
      	        	 docIds.push(e);
      	        } 
      	        else
      	        	{
      	        	  docIds.push(e);
      	        	}
      	        
      	         $("#strDocumentIds").val(docIds.toString());

	        	  getImageDetails();
	        	  sweetSuccess('Attention','File uploaded successfully');
	        	  return true;
	          }
	        });	
	
	     
}
	else
		{
		  sweetSuccess('Attention','Please filled photo caption first');
		}
	 var $el = $('#productServiceQualityImages');
     $el.wrap('<form>').closest('form').get(0).reset();
     $el.unwrap();
	
}
	

	function getImageDetails()
	{
		var strDocumentIds=$("#strDocumentIds").val();
		var tableRow='';
		$.ajax({
	        type: "POST",
	        url: "/PMS/getUplodedImages",	        
	        data:{"strDocumentIds":strDocumentIds},
	        success: function(data) {
	        	
	        	for(var i=0;i<data.length;i++)
	        	{
	        		var row = data[i];
					var j=i+1;
					
					//var actualName=row.strDocumentName.split('.');
					
	        		tableRow+="<tr><td><input type='checkbox' path='imageCheckbox' class='imageCheckBox' id='imageCheckbox' value="+row.numDocumentId+" autocomplete='off'></td>" ;
	        		tableRow+="<td><a class='bold'  onclick='downloadDeploymentImages("+row.numDocumentId+")'>"+row.strDocumentCaption+"</a></td> </tr>";
	        	
	        		
	        	}
	        	tableRow+='</tbody>';
				$('#imagesDetailsTable tbody').empty();
				$('#imagesDetailsTable').append(tableRow);
	        	
	       },
	       error: function() {
		        	
		            alert('Error');
		        }
		});
		$('#imagesDetailsTable_info').addClass('hidden');
		$('#imageTable').removeClass('hidden');
		
		
	}

	function downloadDeploymentImages(documentId){
		var encCategoryId=$("#encCategoryId").val();
		var encMonthlyProgressId=$("#encMonthlyProgressId").val();
		
		openWindowWithPost('POST','/PMS/downloadDeploymentImages',{"numDocumentId" : documentId,"encCategoryId":encCategoryId,"encMonthlyProgressId":encMonthlyProgressId},'_blank');
		/*openWindowWithPost('POST','/PMS/downloadDeploymentImages',{"numDocumentId" : documentId},'_blank');*/
	}





	function deleteImageDetails()
	{
		
		var docIds =[];
		var newdocIds=$("#strDocumentIds").val();
		var strDocumentIds=$("#strDocumentIds").val();

		
	    var chkArray = [];
	     
	    $(".imageCheckBox:checked").each(function() {
	        chkArray.push($(this).val());
	        
	    });
	    
	    var selected;
	    selected = chkArray.join(','); 
	    
	    if(selected.length >= 1){
	    	
	    	
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
			    	  
			    	  var encCategoryId=$("#encCategoryId").val();
			    		var encMonthlyProgressId=$("#encMonthlyProgressId").val();
			    			
			    	  $.ajax({
				    	        type: "POST",
				    	        url: "/PMS/deleteImageDetails",	        
				    	        data:{"numDocumentsIds":selected,"encCategoryId":encCategoryId,"encMonthlyProgressId":encMonthlyProgressId},
				    	        
				    	        success: function(res) {
				    	        	if(res)
				    	        		{
				    	        		$("#strDocumentIds").val(strDocumentIds);
			    	        			
			    	        			 docIds.splice(0, docIds.length);
			    	        			 docIds = newdocIds.split(",");
				        	        	
				        	        	 for(var i=0;i<chkArray.length;i++)
					        	         {
				        	        		 
					        	        	for(var j=0;j<docIds.length;j++)
					        	        		{
					        	        		
					        	        		   if(chkArray[i]==docIds[j])
					        	        			  {
					        	        			  	docIds.splice(j, 1)
					        	        			  	
					        	        			  }
					        	        		}
				        	        		 
					        	              
				        	        		 
					        	        		
					        	         }
				        	        	 
					        	         
			    	        			    $("#strDocumentIds").val(docIds.toString());
			    	        			    getImageDetails();
				    	        			sweetSuccess('Attention','Image file deleted');
				    	        			return true;
				    	        		
				    	        		}
				    	        	else
				    	        		{
				    	        		  sweetSuccess('Attention','Error in file delete');
					    	        	  return true;
				    	        		
				    	        		}
				    	        	 
				    	       },
				    	       error: function() {
				    		        	
				    		            alert('Error');
				    		        }
				    		});

				    	  
				      }
		  	 
				      else{
				    	  
				      return false;
		    }
	    });
	    }
	    else{
	        swal("Please select at least one Detail to delete");  
	}
	 

		
		
			
	}