var nameRegex='';
var nameErrorMessage='';
var contactRegex='';
var contactErrorMessage='';
var addressRegex = '';
var addressErrorMessage = '';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	addressRegex = messageResource.get('address.regex','regexValidationforjs');
	addressErrorMessage = messageResource.get('address.regex.message','regexValidationforjs');
	contactRegex= messageResource.get('contact.regex', 'regexValidationforjs');
	contactErrorMessage = messageResource.get('contact.regex.message', 'regexValidationforjs');
	});

$(document).ready(function() {
	
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			OrganisationDelete();
		});
	});

$(document).ready(function() {
		$('#example').DataTable();
		$('.select2Option').select2({width:"100%"});
	});

	$(document).on('click','#edit',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[11].trim());
		$('#organisationName').val(resultArray[1].trim()).trigger('input');
		$('#organisationShortName').val(resultArray[2].trim());
		$('#organisationAddress').val(resultArray[3].trim()).trigger('input');
		$('#contactNumber').val(resultArray[4].trim()).trigger('input');	
		$('#thrustAreaData').val(resultArray[9].trim().split(',')).trigger('change');	
		$('#parentOrganisationId').val(resultArray[10].trim()).trigger('change');	
		$('#strCode').val(resultArray[7].trim());

	
		
/*		if(resultArray[8].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}*/
		$('#save').text('Update');
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
	    	  parentOrganisationId:{
	        	 
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Parent Organisation',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('parentOrganisationId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	    	  organisationName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Organisation name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'The Organisation name must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          organisationAddress: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Organisation Address is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 250,
	                        message: 'Organisation Address must be less than 250 characters'
	                    },
	                    regexp: {
	                        regexp: addressRegex,
	                       message: addressErrorMessage
	                    }
	              }
	          },
	          contactNumber: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Number is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 14,
	                        message: 'Contact Number must be less than 14 characters'
	                    },
	                    regexp: {
	                        regexp: contactRegex,
	                       message: contactErrorMessage
	                    }
	                    
	              }
	          },
	          strCode: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Center code is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 30,
	                        message: 'Center code must be less than 30 characters'
	                    }
	                    
	                    
	              }
	          },
	          thrustAreaData:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Thrust Area',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          /*var options = validator.getFieldElements('thrustAreaData').val();
	                          return (options != 0);*/
	                    	  var options  = validator.getFieldElements('thrustAreaData').val();
	                    	  console.log(options.length);                             
                          return (options.length >= 1);
	                      }
	                  }
	              }
	          }         
	      }
	  });

	$('#save').click(function(){
		
   
		
		/*var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }*/
	   	 /*$('#valid').val(istoggle);*/
	   	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				$('#form1')[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
			 //return false;
		}
		
		
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#organisationName').val('');
	$('#organisationShortName').val('');
	$('#contactNumber').val('');
	$('#organisationAddress').val('');
	$('#parentOrganisationId').val(0).trigger('change');
	$('#thrustAreaData').val('').trigger('change');
	$('#save').text('Save');
}

function OrganisationDelete(){
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
    });
    
    var selected;
    selected = chkArray.join(','); 
    $('#numIds').val(selected);
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
			    	/*  $("#numIds").val(selected);*/
			  		  submit_form_delete();
			      }
	  	
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Organisation to delete");  
}
 
}

function submit_form_delete()
{
	$.ajaxRequest.ajax(
	    {
	        type: "POST",
	        url: "/PMS/mst/deleteOrganisationMaster",
	        data: {"numIds":$('#numIds').val()} ,
	        success: function(response){ 
	        	swal({
		            title: "Deleted!",
		            text: "Organisation details deleted Successfully",
		            type: "success",
		            buttons: false,
		            timer: 9000
		            });
	        	location.reload(); 
	        },
	        
	        error: function(e){
	        	alert('Error: ' + e);
	    	}
	    
	    });
}


