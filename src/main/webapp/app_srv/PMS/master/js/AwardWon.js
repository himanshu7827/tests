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
			AwardDetailsDelete();
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
		$('#numId').val(resultArray[9].trim());
		/*$('#awardType').val(resultArray[2].trim());
		$('#awardType').val(resultArray[2].trim()).trigger('change');*/
		
		$('#awardName').val(resultArray[2].trim());	
		$('#recipientName').val(resultArray[3].trim());
		$('#achievementDescription').val(resultArray[4].trim());
		$('#dateOfAward').val(resultArray[5].trim());
		$('#location').val(resultArray[6].trim());
		$('#awardBy').val(resultArray[7].trim());
		$('#projectAwardedFor').val(resultArray[8].trim());
		
		$('#save').text('Update');
		document.getElementById("awardName").focus();
	});
	
	
	$(document).ready(function() {
		
	/*	if($("#noOfParticipant").val()==0){
			$("#noOfParticipant").val('');
		}*/
		$('#dateOfAward').datepicker({
			   dateFormat: 'dd/mm/yy', 
			   changeMonth: true,
			   changeYear:true,
			   onSelect: function(date){
			    	$('#dateOfAward').trigger('input');
			    	}
			  
		});
	});
	
	/*function openAnyOther(){
		
		if($("#seminarType").val()=='AnyOther'){
			$("#anyOtherType").show();
		}
		else{
			$("#anyOtherType").hide();
		}
	}*/
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
	    	
	          projectAwardedFor: {
	              group: '.col-md-12',
	              validators: {
	               /* notEmpty: {
	                      message: 'For which project/product awarded field is required and cannot be empty'
	                  },*/
	                  stringLength: {
	                        max: 200,
	                        message: 'For which project/product awarded field must be less than 200 characters'
	                    },
	              }
	          },
	          awardBy: {
	              group: '.col-md-12',
	              validators: {
	               /* notEmpty: {
	                      message: 'Awarded By field is required and cannot be empty'
	                  },*/
	                  stringLength: {
	                        max: 200,
	                        message: 'Awarded By must be less than 200 characters'
	                    },
	              }
	          },
	          location: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Location is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'Location must be less than 200 characters'
	                    },
	              }
	          },
	          dateOfAward:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          achievementDescription: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Achievement Description is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Achievement Description must be less than 1000 characters'
	                    },
	              }
	          },
	        
	          recipientName: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Recipient Name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'Recipient Name must be less than 200 characters'
	                    },
	              }
	          },
	          awardName: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Award Name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'Award Name must be less than 200 characters'
	                    },
	              }
	          }/*,
	          
	          awardType:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please select Type ',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('awardType').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          }*/ 
  
	      }
	  });
	
	$('#save').click(function(){
		
		/*var anyOtherDetails=$("#anyOtherTypeDetails").val();
		if($("#seminarType").val()=='AnyOther' && anyOtherDetails==''){
			sweetSuccess("Attention","Any Other type is mandatory");
			return false;
		}*/
		/*if($("#awardType").val()==0){
			sweetSuccess("Attention","Please select Type");
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
	$('#awardName').val('');
	$('#recipientName').val('');
	$('#achievementDescription').val('');
	$('#awardBy').val('');
	$('#projectAwardedFor').val('');
	$('#location').val('');
	$('#dateOfAward').val('');
	/*$('#awardType').val('');
	$('#awardType').val(0).trigger('change');*/
	
	$('#save').text('Save');
}


function AwardDetailsDelete(){
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
document.form1.action = "/PMS/mst/deleteAwardWon";
document.getElementById("form1").submit();
}

function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

