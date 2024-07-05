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
			MediaDelete();
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
		$("#anyOtherDetails").val('');
		$('#numId').val(resultArray[8].trim());
		$('#source').val(resultArray[2].trim());
		$('#source').val(resultArray[2].trim()).trigger('change');
		if(resultArray[2].trim()=='Others'){
			$("#anyOtherType").show();
			$('#anyOtherDetails').val(resultArray[7].trim());
		}
		else{
			$("#anyOtherDetails").val('');
			$("#anyOtherType").hide();
		}
		$('#sourceDetails').val(resultArray[3].trim());	
		$('#details').val(resultArray[4].trim());
		$('#location').val(resultArray[6].trim());
		$('#dateOfmedia').val(resultArray[5].trim());
		/*$('#numGroupCategoryId').val(resultArray[8].trim());*/
		

		$('#save').text('Update');
	});
	
	
	$(document).ready(function() {
		
	/*	if($("#noOfParticipant").val()==0){
			$("#noOfParticipant").val('');
		}*/
		$('#dateOfmedia').datepicker({
			   dateFormat: 'dd/mm/yy', 
			   changeMonth: true,
			   changeYear:true,
			   onSelect: function(date){
			    	$('#dateOfmedia').trigger('input');
			    	}
			  
		});
	});
	
	function openAnyOther(){
		//alert($("#source").val());
		if($("#source").val()=='Others'){
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
	    	
	          location: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Location is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Location must be less than 1000 characters'
	                    },
	              }
	          },
	          dateOfmedia:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          sourceDetails: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Source Details is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Source Details must be less than 1000 characters'
	                    },
	              }
	          },
	        
	          details: {
	              group: '.col-md-12',
	              validators: {
	                 notEmpty: {
	                      message: 'Source Details is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Source Details must be less than 1000 characters'
	                    },
	              }
	          },

	          source:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please select Source ',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('source').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          anyOtherDetails: {
	            
	              validators: {
	                
	                  stringLength: {
	                        max: 1000,
	                        message: 'Any other Source must be less than 1000 characters'
	                    },
	              }
	          },
  
	      }
	  });
	
	$('#save').click(function(){
		
		var anyOtherDetails=$("#anyOtherDetails").val();
		if($("#source").val()=='Others' && anyOtherDetails==''){
			sweetSuccess("Attention","Any Other type is mandatory");
			return false;
		}
		
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
	$('#location').val('');
	$('#details').val('');
	$('#sourceDetails').val('');
	$('#dateOfmedia').val('');
	$('#source').val('');
	$('#source').val(0).trigger('change');
	$("#anyOtherDetails").val('');
	$("#anyOtherType").hide();
	$('#save').text('Save');
}


function MediaDelete(){
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
document.form1.action = "/PMS/mst/deleteMedia";
document.getElementById("form1").submit();
}

function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

