var addressRegex='';
var addressErrorMessage='';
var numericRegex='';
var numericRegexMessage='';
var mobileRegex='';
var mobileErrorMessage='';
var nameRegex='';
var nameErrorMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){	
	numericRegex = messageResource.get('numeric.regex', 'regexValidationforjs'); 
	numericRegexMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');	
	
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	
	mobileRegex = messageResource.get('mobileno.regex', 'regexValidationforjs'); 
	mobileErrorMessage = messageResource.get('mobileno.regex.message', 'regexValidationforjs');
	
	addressRegex = messageResource.get('address.regex', 'regexValidationforjs');
	addressErrorMessage = messageResource.get('address.regex.message', 'regexValidationforjs');
	
	
	});


$(document).ready(function() {
	
		$('.select2Option').select2({
			width: '100%'
		});
		$('#thirdPartyDataTable').DataTable();
		$('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
				ThirdPartyDelete();
			});

		$("#startDate").datepicker({ 
				dateFormat: 'dd/mm/yy', 
				changeMonth: true,
				changeYear:true,           
				maxDate: '+5y',
				onSelect: function(date){
					$('#startDate').trigger('input');
					var dateData = date.split('/');
					var monthval = parseInt(dateData[1])-1;
					var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

					//Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
					$("#endDate").datepicker( "option", "minDate", selectedDate );
					$("#endDate").datepicker( "option", "maxDate", '+5y' );
				}
		});

		$("#endDate").datepicker({ 
			dateFormat: 'dd/mm/yy', 
			changeMonth: true,
			changeYear:true,
			onSelect: function(date){
				$('#endDate').trigger('input');
			}
		});
		
	});

	$(document).on('click','#edit',function(e){
		
			var resultArray = $(this).closest('tr').find('td').map( function()
				{
					return $(this).text();
				}).get();
			//console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#agencyName').val(resultArray[1].trim());
		$('#agencyAddress').val(resultArray[2].trim());
		$('#contactPerson').val(resultArray[3].trim());
		$('#mobileNumber').val(resultArray[4].trim());
		$('#contactNumber').val(resultArray[5].trim());
		$('#startDate').val(resultArray[6].trim());
		$('#endDate').val(resultArray[7].trim());
		if(resultArray[8].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
	});
	
	


$(document).ready(function(){	
	
	$('#reset').click(function(){
		resetForm();
	});
	
	$('#form1').bootstrapValidator({

		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  agencyName: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Agency Name is required and cannot be left empty'
	                  },
	                  stringLength: {
	                        max: 50,
	                        message: 'Agency Name can not be greater than 50 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },agencyAddress:{
	        	  group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Agency Address is required and cannot be left empty'
	                  },
	                  regexp: {
	                       regexp: addressRegex,
	                       message: addressErrorMessage
	                    }
	              }
	          },
	          contactPerson: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Person Name is required and cannot be left empty'
	                  },
	                  stringLength: {
	                        max: 50,
	                        message: 'Contact Person Name can not be greater than 50 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },mobileNumber: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Mobile Number is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 14,
	                        message: 'Mobile Number must be less than 14 characters'
	                    },
	                    regexp: {
	                        regexp: mobileRegex,
	                       message: mobileErrorMessage
	                    }
	              }
	          },
	          contactNumber: {
	              group: '.col-md-6',
	              validators: {
	                 /* notEmpty: {
	                      message: 'Contact Number is required and cannot be empty'
	                  },*/
	                  stringLength: {
	                        max: 15,
	                        message: 'Contact Number must be less than 15 characters'
	                    },
	                    regexp: {
	                        regexp: numericRegex,
	                       message: numericRegexMessage
	                    }
	              }
	          },
	          startDate:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Start Date is required and cannot be left empty'
	                    }
	                }
	          },endDate:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'End Date is required and cannot be left empty'
	                    }
	                }
	          }       	         
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
					$( "#form1")[0].submit();
					return true;
		    	}
		    else{
				console.log('Not Validated');
				
			  }
		});
		
});

function resetForm(){
	
	$('#numId').val('0');
	$('#agencyName').val('');
	$('#agencyAddress').val('');
	$('#contactPerson').val('');
	$('#mobileNumber').val('');
	$('#contactNumber').val('');
	$('#startDate').val('');
	$('#endDate').val('');
	$('#save').text('Save');
	$('#form1').data('bootstrapValidator').resetForm(true);
}


function ThirdPartyDelete(){
	
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
    });
    console.log(chkArray);
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
			    	  $("#numIds").val(selected);
			  		  submit_form_delete();
			      }
	 
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Record to delete");  
}
 
}


function submit_form_delete()
{
document.getElementById("form1").action='/PMS/mst/deleteThirdPartyMaster';
document.getElementById("form1").submit();
/*document.form1.action ="/PMS/mst/deleteThirdPartyMaster";
document.getElementById("form1").submit();*/
resetForm();
}

$('#reset').click(function(){
	resetForm();
});

