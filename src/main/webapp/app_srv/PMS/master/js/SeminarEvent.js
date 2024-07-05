var amountRegex='';
var numericRegex='';
var numericErrorMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	amountRegex= messageResource.get('email.regex', 'regexValidationforjs');
	numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	});

$(document).ready(function() {
	
		/*$('#example').DataTable();*/
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			SeminarDetailDelete();
		});
		
		$('.select2Option').select2({
			 width: '100%'
		});
		
	});

$(document).ready(function() {
    var table = $('#example').DataTable( {
    	"paging":   false,
        "ordering": false,
        "searching": false,
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
} );

	$(document).on('click','#edit',function(e){
	$('#form1').data('bootstrapValidator').resetForm(true);
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$("#anyOtherTypeDetails").val('');
		$('#numId').val(resultArray[12].trim());
		$('#seminarType').val(resultArray[2].trim());
		$('#seminarType').val(resultArray[2].trim()).trigger('change');
		if(resultArray[2].trim()=='AnyOther'){
			$("#anyOtherType").show();
			$('#anyOtherTypeDetails').val(resultArray[11].trim());
		}
		else{
			$("#anyOtherTypeDetails").val('');
			$("#anyOtherType").hide();
		}
		$('#dateOfSeminar').val(resultArray[3].trim());	
		$('#objectives').val(resultArray[4].trim());
		$('#coordinatingPerson').val(resultArray[5].trim());
		$('#cdacRoles').val(resultArray[6].trim());
		$('#venue').val(resultArray[7].trim());
		$('#noOfParticipant').val(resultArray[8].trim());
		$('#strProfileOfParticipant').val(resultArray[9].trim());
		$('#strCollaborators').val(resultArray[10].trim());
		/*$('#numGroupCategoryId').val(resultArray[11].trim());*/

		$('#save').text('Update');
	});
	
	
	$(document).ready(function() {
		
		if($("#noOfParticipant").val()==0){
			$("#noOfParticipant").val('');
		}
		$('#dateOfSeminar').datepicker({
			   dateFormat: 'dd/mm/yy', 
			   changeMonth: true,
			   changeYear:true,
			   onSelect: function(date){
			    	$('#dateOfSeminar').trigger('input');
			    	}
			  
		});
	});
	
	function openAnyOther(){
		//alert($("#seminarType").val());
		if($("#seminarType").val()=='AnyOther'){
			$("#anyOtherType").show();
		}
		else{
			$("#anyOtherType").hide();
		}
	}
$(document).ready(function(){
	
	$("#anyOtherType").hide();

	
	$('#reset').click(function(){
		resetForm();
		$('#form1').data('bootstrapValidator').resetForm(true);

	});
	console.log(amountRegex);
	$('#form1').bootstrapValidator({

		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	 
	          coordinatingPerson: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Coordinating Person is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'Role color must be less than 100 characters'
	                    },
	              }
	          },
	          strCollaborators: {
	              group: '.col-md-12',
	              validators: {
	               /*   notEmpty: {
	                      message: 'Coordinating Person is required and cannot be empty'
	                  },*/
	                  stringLength: {
	                        max: 1000,
	                        message: 'Collaborators must be less than 1000 characters'
	                    },
	              }
	          },
	          anyOtherTypeDetails: {
	              group: '.col-md-12',
	              validators: {
	               /*   notEmpty: {
	                      message: 'Coordinating Person is required and cannot be empty'
	                  },*/
	                  stringLength: {
	                        max: 1000,
	                        message: 'Any Other type must be less than 1000 characters'
	                    },
	              }
	          },
	          cdacRoles: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'C-DAC Role is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'C-DAC Role must be less than 1000 characters'
	                    },
	              }
	          },
	          dateOfSeminar:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          venue: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Venue is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'Venue must be less than 200 characters'
	                    },
	              }
	          },
	         noOfParticipant: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Number of Participant is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 5,
	                        message: 'Number of Participant can not be greater than 5 digits'
	                    },
	                  regexp: {
	                	  regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          },
	          strProfileOfParticipant: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Venue is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Venue must be less than 1000 characters'
	                    },
	              }
	          },
	          objectives: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Objectives is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Objectives must be less than 1000 characters'
	                    },
	              }
	          },
	          
	          seminarType:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please select Type ',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('seminarType').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          } 
  
	      }
	  });
	
	$('#save').click(function(){
		
		var anyOtherDetails=$("#anyOtherTypeDetails").val();
		if($("#seminarType").val()=='AnyOther' && anyOtherDetails==''){
			sweetSuccess("Attention","Any Other type is mandatory");
			return false;
		}
	/*	if($("#seminarType").val()==0){
			sweetSuccess("Attention","Please select Type/Category");
			return false;
		}*/
		var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){	
	    	
				document.getElementById("save").disabled = true; 
				document.form1.submit();
				return true;
			
		}else{
			console.log('Not Validated');
			
		}

	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#objectives').val('');
	$('#strProfileOfParticipant').val('');
	$('#noOfParticipant').val('');
	$('#cdacRoles').val('');
	$('#venue').val('');
	$('#strCollaborators').val('');
	$('#coordinatingPerson').val('');
	$('#dateOfSeminar').val('');
	$('#seminarType').val('');
	$('#seminarType').val(0).trigger('change');
	$("#anyOtherType").hide();
	$("#anyOtherTypeDetails").val('');
	$('#save').text('Save');
}


function SeminarDetailDelete(){
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
    
        swal("Please select at least one record to delete");  
}
 
}

function submit_form_delete()
{
document.form1.action = "/PMS/mst/deleteSeminarDetails";
document.getElementById("form1").submit();
}

function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

