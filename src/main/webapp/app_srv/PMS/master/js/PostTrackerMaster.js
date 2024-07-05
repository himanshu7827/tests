var descriptionRegex='';
var descriptionErrorMessage='';
var numericRegex='';
var numericRegexMessage='';
var amountRegex='';
var amountErrorMessage ='';
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
	
	amountRegex=messageResource.get('amount.regex', 'regexValidationforjs');
	amountErrorMessage =messageResource.get('amount.regex.message', 'regexValidationforjs');

	descriptionRegex= messageResource.get('address.regex', 'regexValidationforjs');
	descriptionErrorMessage= messageResource.get('address.regex.message', 'regexValidationforjs');

});


$(document).ready(function() {
	
		$('.select2Option').select2({
			width: '100%'
		});
		$('#postTrackerMasterTable').DataTable();
		$('#userinfo-message').delay(5000).fadeOut();
		/*$('#delete').click(function(){
				PostTrackerDelete();
			});*/

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
		$('#form1').data('bootstrapValidator').resetForm(true);

			var resultArray = $(this).closest('tr').find('td').map( function()
				{
					return $(this).text();
				}).get();
			console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#postName').val(resultArray[1].trim());
		$('#strCode').val(resultArray[2].trim());
		$('#postDescription').val(resultArray[3].trim());
		$('#baseSalary').val(resultArray[4].trim());
		$('#minExperience').val(resultArray[6].trim());
		$('#vacancyType').val(resultArray[5].trim()).trigger('change');
		$('#approvedPost').val(resultArray[7].trim());
		$('#noticePeriod').val(resultArray[8].trim());
		$("#numValidity").val(resultArray[9].trim());
//		$('#startDate').val(resultArray[9].trim());
//		$('#endDate').val(resultArray[10].trim());
		/*if(resultArray[10].trim()=='Active'){
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

		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	 postName: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Post Name is required and cannot be left empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: "Post Name can't be more than 100 characters"
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },postDescription:{
	        	  group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Post Description is required and cannot be left empty'
	                  },
	                  stringLength: {
	                        max: 1500,
	                        message: 
	                        	"Post Description can't be more than 1500 characters"
	                    },
	                  regexp: {
	                       regexp: descriptionRegex,
	                       message: descriptionErrorMessage
	                    }
	              }
	          },
	         baseSalary: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Base Salary  is required and cannot be left empty'
	                  },
	                  stringLength: {
	                        max: 10,
	                        message: "Base Salary cannot contain more than 10 digits"
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                       message: amountErrorMessage
	                    }
	              }
	          },
	          strCode: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Post Code  is required and can not be left empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: "Post Code can not contain more than 100 digits"
	                    },
	                    
	              }
	          },
	          
	          vacancyType:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose VacancyType ',
	                      callback: function(value, validator) {	                          
	                          var options = validator.getFieldElements('vacancyType').val();
	                          return (options != null && options != '0');
	                      }
	                  }
	              }
	          },minExperience: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Min Experience is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 5,
	                        message: "Min Experience can't be more than 5 characters"
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                       message: amountErrorMessage
	                    }
	              }
	          },
	          approvedPost: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Post Approved is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 4,
	                        message: "Post Approved can't be more than 4 digits"
	                    },
	                    regexp: {
	                        regexp: numericRegex,
	                       message: numericRegexMessage
	                    }
	              }
	          },
	         noticePeriod: {
	              group: '.col-md-6',
	              validators: {
	                  notEmpty: {
	                      message: 'Notice Period is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 4,
	                        message: "Notice Period can't be more than 4 digits"
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
	$('#postName').val('');
	$('#postDescription').val('');
	$('#baseSalary').val('');
	$('#minExperience').val('');
	$('#strVacanyType').val('0').trigger('change');
	$('#postApproved').val('');
	$('#startDate').val('');
	$('#endDate').val('');
	$('#save').text('Save');
	$('#form1').data('bootstrapValidator').resetForm(true);
}


function PostTrackerDelete(){
	
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
document.getElementById("form1").action='/PMS/mst/deletePostTrackerMaster';
document.getElementById("form1").submit();
/*document.form1.action ="/PMS/mst/deleteThirdPartyMaster";
document.getElementById("form1").submit();*/
resetForm();
}

$('#reset').click(function(){
	resetForm();
});