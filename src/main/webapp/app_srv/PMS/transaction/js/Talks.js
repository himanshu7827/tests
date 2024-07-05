var nameRegex='';
var nameErrorMessage='';
var numericRegex='';
var numericErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	});

$(document).ready(function() {
    var table = $('#example').DataTable( {
    	"paging":   false,
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
} );

$('#example tbody').on('click', 'tr', function () {
	//alert("amit1");
	 $('.CheckBox').click(function() {
		// alert("amit2");
	        $('.CheckBox').not(this).prop('checked', false);
	
});
});


$(document).on('click','#edit',function(e){
	$('#form1').data('bootstrapValidator').resetForm(true);
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		//$('#numCateoryId').val(resultArray[2].trim());
		$('#numTalkID').val(resultArray[3].trim());
		$('#strEventName').val(resultArray[4].trim());
		$('#strAgencyName').val(resultArray[5].trim());
		$('#strTalkTitle').val(resultArray[6].trim());	
		$('#dtDate').val(resultArray[7].trim());
		$('#strNameSpeaker').val(resultArray[8].trim());	
		$('#strCityLocation').val(resultArray[9].trim());
		$('#save').text('Update');
		document.getElementById("strEventName").focus();
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
	    	  strEventName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Event Name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 500,
	                        message: 'Event Name must be less than 1000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          strAgencyName: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Agency Name is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Agency Name must be less then 500 characters.'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          strTalkTitle: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Talk Title is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Talk Title must be less then 500 characters.'
	                    },
	                   	              }
	          },
	          
	          strNameSpeaker: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Name of Speaker is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Name of Speaker must be less then 500 characters.'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          
	          strCityLocation: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'City/Location is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'City/Location must be less then 500 characters.'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          dtDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'From Date is required and cannot be empty'
	                  },    
	              }
	          },
	         
	      }
	  });
	
	 $('#dtDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		   // maxDate:"-18y",
		   // yearRange: '-65y:c+nn',
		 
		    onSelect: function(date){
		    	$('#dtFromDate').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);
		        var year = selectedDate.getFullYear();
		        var month = selectedDate.getMonth();
		        var day = selectedDate.getDate();
		        var dt = new Date(year , month, day);
		        var currentDate=new Date();
         
               var range=dt.getFullYear() +':'+currentDate.getFullYear();
		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		       // $("#dateOfJoining").datepicker( "option", "yearRange",range );
		       // $("#dateOfCopyright").datepicker( "option", "minDate", dt );
		        //$("#dateOfJoining").datepicker( "option", "maxDate", currentDate );
		    }
		   
 });
	 
	
	$('#save').click(function(){
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){				
				document.getElementById("save").disabled = true; 
				/*document.form1.submit();*/
				$( "#form1")[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
	}
			 //return false;
	});
		
});

function resetForm(){
	$('#numTalkID').val('0');
	$('#strEventName').val('');
	$('#strAgencyName').val('');
	$('#strTalkTitle').val('');
	$('#dtDate').val('');
	$('#strNameSpeaker').val('');
	$('#strCityLocation').val('');
	
	$('#save').text('Save as Draft');
}


var inputArray = [];
$('#save').click(function(){
$('#form1').submit();
	
});

