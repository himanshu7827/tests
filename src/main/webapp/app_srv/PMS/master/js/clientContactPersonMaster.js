var nameRegex='';
var nameErrorMessage='';
var mobileNoRegex='';
var mobileErrorMessage='';
var emailRegex='';
var emailErrorMessage='';
var addressRegex='';
var addressErrorMessage='';
var contactNoRegex='';
var contactNoErrorMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	
	mobileNoRegex = messageResource.get('mobileno.regex', 'regexValidationforjs'); 
	mobileErrorMessage = messageResource.get('mobileno.regex.message', 'regexValidationforjs');
	
	emailRegex = messageResource.get('email.regex', 'regexValidationforjs');
	emailErrorMessage = messageResource.get('email.regex.message', 'regexValidationforjs');
	
	addressRegex = messageResource.get('address.regex', 'regexValidationforjs');
	addressErrorMessage = messageResource.get('address.regex.message', 'regexValidationforjs');
	
	contactNoRegex=messageResource.get('contact.regex', 'regexValidationforjs');
	contactNoErrorMessage=messageResource.get('contact.regex.message', 'regexValidationforjs');
	});





$(document).ready(function() {
	$('#toggle-off').removeAttr('Checked');
	$('#toggle-on').attr('checked',true);
			
		var table = $('#datatable').DataTable();
		table.destroy();		
		/*$('#frm').addClass('hidden');*/		
		$('#addnewrecord').addClass('hidden');
		/*$('#datatable').addClass('hidden');*/
		
	});



$(document).on('click','#addnewrecord',function(e){
	$('#addnewrecord').addClass('hidden');});


/*$(document).on('change','#organisationId',function(e){
	$('#addnewrecord').addClass('hidden');});*/



$('.select2Option').select2({
	 width: '100%'
});
	$(document).on('click','#edit',function(e){
		$('#frm').removeClass('hidden');
		$('#addnewrecord').addClass('hidden');
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#strContactPersonName').val(resultArray[1].trim()).trigger('input');
		
		$('#strDesignation').val(resultArray[2].trim()).trigger('input');
		$('#strMobileNumber').val(resultArray[3].trim()).trigger('input');	
		$('#strEmailId').val(resultArray[4].trim()).trigger('input');
		$('#strRoles').val(resultArray[5].trim()).trigger('input');
		$('#strResponsibility').val(resultArray[6].trim()).trigger('input');
		$('#strOfficeAddress').val(resultArray[7].trim()).trigger('input');
		$('#strResidenceAddress').val(resultArray[8].trim());
		
		if(resultArray[9].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
	});
	
	
	$('#new_record').click(function(){	
		
		$('#frm').removeClass('hidden');
	
	});

	
	

	
	$('#organisationId').change(function(){		
		var clientId = $('#organisationId').val();
		
		var tableData = "";
		if(clientId == 0){
			console.log('If block');
			$('#frm').addClass('hidden');
			$('#datatable').addClass('hidden');
			$('stagedetails').addClass('hidden');			
			$('#addnewrecord').addClass('hidden');
		}else{
			console.log('Else block');
			$.ajax({
    	        type: "POST",
    	        url: "/PMS/mst/getContactPersonByClientId",
    	        data: "clientId=" + clientId,			
				success : function(data) {		
					console.log(data);
					for(var i =0;i<data.length;i++){
						var row = data[i];
						var recordStatus= '';
						if(row.valid==true){
							recordStatus='Active';
								}else{recordStatus='Inactive';
									}
						tableData+="<tr><td><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" />"+row.numId+"</td> row.numId" +
								"<td>"+row.strContactPersonName+"</td>  <td>"+row.strDesignation+"</td> <td>"+row.strMobileNumber+"</td> <td>"+row.strEmailId+"</td> <td>"+row.strRoles+"</td> <td>"+row.strResponsibility+"</td> <td>"+row.strOfficeAddress+"</td> <td>"+row.strResidenceAddress+"</td>" +
								"<td>"+recordStatus+"</td> <td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td> </tr>"
								
					}
					tableData+='</tbody>';
					$('#datatable tbody').empty();
					$('#datatable').append(tableData);		
					
				},				
				error : function(e) {					
					alert('Error: ' + e);				
				}
			});
			
			
			$('#datatable').removeClass('hidden');
			$('#stagedetails').removeClass('hidden');
			$('#addnewrecord').removeClass('hidden');		
		}
		
		
	});
	
	

$(document).ready(function(){
	
	$('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
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
	    	  strContactPersonName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 150,
	                        message: 'The Contact Person name must be less than 150 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },strDesignation: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person Designation is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 150,
	                        message: 'The Contact Person Designation must be less than 150 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },strMobileNumber: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Number is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 13,
	                        message: 'Contact Number must be less than 14 characters'
	                    },
	                    regexp: {
	                        regexp: contactNoRegex,
	                       message: contactNoErrorMessage
	                    }
	              }
	          },strEmailId: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person Email Id is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 150,
	                        message: 'The Contact Person Email Id must be less than 150 characters'
	                    },
	                    regexp: {
	                        regexp: emailRegex,
	                       message: emailErrorMessage
	                    }
	              }
	          },/*strRoles: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person Roles is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'The Contact Person Roles must be less than 200 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },strResponsibility: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person Responsibility is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'The Contact Person Responsibility must be less than 200 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          }*/strOfficeAddress: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person Office Address is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'The Contact Person Office Address must be less than 1000 characters'
	                    },
	                    regexp: {
	                        regexp: addressRegex,
	                       message: addressErrorMessage
	                    }
	              }
	          },
	          organisationId : {
					validators : {
						callback : {
							message : 'Please choose Organization Name',
							callback : function(
									value,
									validator) {
								var options = validator
										.getFieldElements(
												'organisationId').val();
								return (options != 0);
							}
						}
					}
				},
	          /*strResidenceAddress: {
	              group: '.col-md-12',
	              validators: {
	                  stringLength: {
	                	  	min: 0,
	                        max: 1000,
	                        message: 'The Contact Person Residence Address must be less than 1000 characters'
	                    },
	                    regexp: {
	                        regexp: addressRegex,
	                       message: addressErrorMessage
	                    }
	              }
	          }*/
	          
	      
	      }
	      
	  });
	
	
	
	
	$('#save').click(function(){
		
		
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
		
		
		   
		   var bootstrapValidator = $("#form1").data('bootstrapValidator');
			bootstrapValidator.validate();
		    if(bootstrapValidator.isValid()){				
					document.getElementById("save").disabled = true; 
					/*document.form1.submit();*/
					 $( "#form1")[0].submit();
					return true;
				
			}else{
				console.log('Not Validated');
				 //return false;
			}
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#organisationId').val('0').trigger('change');
	$('#strContactPersonName').val('');
	$('#strDesignation').val('');
	$('#strMobileNumber').val('');
	$('#strEmailId').val('');
	$('#strRoles').val('');
	$('#strResponsibility').val('');
	$('#strOfficeAddress').val('');
	$('organisationId').val(0).trigger('change')
	$('#strResidenceAddress').val('');
	$('#save').text('Save');
	$('#form1').data('bootstrapValidator').resetForm(true);
}


$(document).ready(function(){
	$('#backPage').click(function(){
		var referrerValue = $('#referrerValue').val();
		if(referrerValue){
			window.location.href = referrerValue;
		}
	});
});