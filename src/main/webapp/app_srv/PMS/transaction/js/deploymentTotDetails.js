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


$('.select2Option').select2({
	 width: '100%'
});
$("#dtDeploymenTotDate").datepicker({ 
	dateFormat: 'dd/mm/yy', 
    changeMonth: true,
    changeYear:true,
    onSelect: function(date){
    	$('#dtDeploymenTotDate').trigger('input');
    }
});
$(document).ready(function() {     
	var table = $('#deploymentDetails').DataTable( {     
		"paging":   false,        
		"searching":false,
		"ordering": false,
	    "info":     false,
		rowReorder: true,
		responsive: {
	        details: {
	            type: 'column',
	            target: 'tr'
	        }
	    },
	    columnDefs: [ {
	        className: 'control',
	        orderable: false,
	        targets:   1
	    } ],
	    order: [ 1, 'asc' ]
		} );
	
var newTable = $('#imagesDetailsTable').DataTable( {     
	"paging":   false,        
	"searching":false,
	"ordering": false,
    "info":     false,
	rowReorder: true  
	
	} );
});



$(function() {
	
	
		$('#form').bootstrapValidator({
		excluded: ':disabled',
		     message: 'This value is not valid',
		     feedbackIcons: {
		         valid: 'glyphicon glyphicon-ok',
		         invalid: 'glyphicon glyphicon-remove',
		         validating: 'glyphicon glyphicon-refresh'
		     },
		     fields: {

		    	strServiceName: {		             
		             validators: {
		                 notEmpty: {
		                     message: 'Name of service is required and cannot be empty'
		                 },
		                 stringLength: {
		                       max: 250,
		                       message: 'Name of service can not be greater than 250 chacters'
		                   },
		                 regexp: {
			                 regexp: nameRegex,
			                 message: nameRegexMessage
		                   }
		             }
		         },
		         numAgencyStateId:{		        	  
		        	  validators: {
		                  callback: {
		                      message: 'Please choose Agency State',
		                      callback: function(value, validator) {		                          
		                          var options = validator.getFieldElements('numAgencyStateId').val();
		                          return (options != 0);
		                      }
		                  }
		              }
		          },

			         numDeploymentStateId:{		        	  
			        	  validators: {
			                  callback: {
			                      message: 'Please choose Deployment State',
			                      callback: function(value, validator) {		                          
			                          var options = validator.getFieldElements('numDeploymentStateId').val();
			                          return (options != 0);
			                      }
			                  }
			              }
			          },

		          numUnitsDeployedOrSold: {
			             group: '.col-md-12',
			             validators: {
			                 notEmpty: {
			                     message: 'Number of units deployed/sold is required and cannot be empty'
			                 },
			                 stringLength: {
			                       max: 10,
			                       message: 'Number of units deployed/sold can not be greater than 10 digits'
			                   },
			                 regexp: {
			                 regexp: numericRegex,
			                      message: numericRegexMessage
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
			                       message: 'Description can not be greater than 2000 characters'
			                   },
			                 regexp: {
			                 regexp: nonRestrictnameRegex,
			                      message: nonRestrictnameRegexMessage
			                   }
			             }
			         },
			         strAgencyName: {
			             group: '.col-md-12',
			             validators: {
			                 notEmpty: {
			                     message: 'Agency name is required and cannot be empty'
			                 },
			                 stringLength: {
			                       max: 500,
			                       message: 'Agency name can not be greater than 500 characters'
			                   },
			                 regexp: {
			                 regexp: nameRegex,
			                      message: nameRegexMessage
			                   }
			             }
			         },
			        	 
			         strAgencyCity: {
			             group: '.col-md-12',
			             validators: {
			                 /*notEmpty: {
			                     message: 'Agency city is required and cannot be empty'
			                 },*/
			            	 stringLength: {
			                       max: 200,
			                       message: 'Agency city can not be greater than 200 characterss'
			                   },
			                 regexp: {
			                 regexp: nameRegex,
			                      message: nameRegexMessage
			                   }
			             }
			         },
			    	 strDeploymentCity: {
			             group: '.col-md-12',
			             validators: {
			                /* notEmpty: {
			                     message: 'Deployment city is required and cannot be empty'
			                 },*/ stringLength: {
			                       max: 200,
			                       message: 'Deployment city can not be greater than 200 characterss'
			                   },
			                 regexp: {
			                 regexp: nameRegex,
			                      message: nameRegexMessage
			                   }
			             }
			         },
			         dtDeploymenTotDate:{
		             validators: {
		                       notEmpty: {
		                           message: 'Deployment Date is required and cannot be empty'
		                       },
		             date:{
		             format: 'DD/MM/YYYY'
		             }
		             }
		             },
		     
		         
		         
		     }
		 });


		$('#reset').click(function(){
			resetForm();
			 $('#form').data('bootstrapValidator').resetForm(true);
		});
		

	$('#delete').click(function(){
	  deleteDeployementDetails();
	});
$('#save').click(function(e){	
	  
	    //var catgId=$("#numCategoryId").val();
	    //var projId=$("#numProjectId").val();
	    //var monthYear=$("#strMonthAndYear").val();
	    //var strGroupCategoryId=catgId+projId+monthYear;
	    $("#numGroupCategoryId").val("12072020");
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
		
	 
	   	// $('#valid').val(true);
	//$('#form').bootstrapValidator('enableFieldValidators', 'strServiceName', true);
	
	
});
$(document).on('click','#edit',function(e){
	$('#form').data('bootstrapValidator').resetForm(true);
	var resultArray = $(this).closest('tr').find('td').map( function()
			{
			return $(this).text();
			}).get();
	
	$('#numDeploymentId').val(resultArray[14].trim());
	$('#strServiceName').val(resultArray[2].trim());
	$('#strDescription').val(resultArray[11].trim());
	$('#numUnitsDeployedOrSold').val(resultArray[9].trim())
	$('#dtDeploymenTotDate').val(resultArray[3].trim());
	$('#strAgencyName').val(resultArray[4].trim());
	$('#numAgencyStateId').val(resultArray[12].trim()).trigger('change');
	$('#strAgencyCity').val(resultArray[6].trim());
	$('#numDeploymentStateId').val(resultArray[13].trim()).trigger('change');
	$('#strDeploymentCity').val(resultArray[8].trim());
	$('#strDocumentIds').val(resultArray[15].trim());
	$('#save').text("update");
	document.getElementById("strServiceName").focus();
	
	
});


function deleteDeployementDetails(){
	
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
		    	  
			    	  $("#numDeploymentIds").val(selected);
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
	document.getElementById("form").action="/PMS/deleteDeployementDetails";
	document.getElementById("form").method="POST";
	document.getElementById("form").submit();
	
}

$('#deleteImages').click(function(){
	  deleteImageDetails();
	});

});

function uploadQualityImages(input){
	var formData = new FormData();
	
	formData.append("strDocumentCaption",$("#strDocumentCaption").val());
	formData.append("encCategoryId",$("#encCategoryId").val());
	formData.append("encMonthlyProgressId",$("#encMonthlyProgressId").val());
	formData.append("progressReportQualityImages",input.files[0]);
	var docIds =[];  
	var docId= $("#strDocumentIds").val();
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
	          else
	        	  {
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
	 var $el = $('#progressReportQualityImages');
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
		    	  
			    	  //$("#numDocumentsIds").val(selected);
			    	  //var formData = new FormData();
			    	 // formData.append("numDocumentsIds",numDocumentsIds);
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

function resetForm(){
	$('#numDeploymentId').val(0);
	$('#strServiceName').val('');
	$('#strDescription').val('');
	$('#numUnitsDeployedOrSold').val(0)
	$('#dtDeploymenTotDate').val('');
	$('#strAgencyName').val('');
	$('#numAgencyStateId').val(0).trigger('change');
	$('#strAgencyCity').val('');
	$('#numDeploymentStateId').val(0).trigger('change');
	$('#strDeploymentCity').val('');
	$('#strDocumentIds').val('');

	
	$('#save').text('Save as Draft');
}
  
