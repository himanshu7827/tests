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
        rowReorder: true
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
		
		$('#numAppreciationID').val(resultArray[2].trim());
		$('#strDescription').val(resultArray[3].trim());
		$('#strAppreciaitionAgency').val(resultArray[4].trim());
		$('#dateOfAppreciation').val(resultArray[5].trim());	
		$('#strRecipientName').val(resultArray[6].trim());
		$('#save').text('Update');
		document.getElementById("strDescription").focus();
		
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
	    	  strDescription: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Description is required and cannot be empty.'
	                  },
	                  stringLength: {
	                        max: 1000,
	                        message: 'Description must be less than 1000 characters.'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          strAppreciaitionAgency: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'This field is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Characters must be less then 500.'
	                    },
	              }
	          },
	          strRecipientName: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Recipient Name is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Recipient Name must be less than 500 characters.'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	        
	          dateOfAppreciation: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Date Of Apprecation is required and cannot be empty.'
	                  },    
	              }
	          },
	      
	         
	      }
	  });
	
	 $('#dateOfAppreciation').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		   // maxDate:"-18y",
		   // yearRange: '-65y:c+nn',
		 
		    onSelect: function(date){
		    	$('#dateOfAppreciation').trigger('input');
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
	$('#numAppreciationID').val('0');
	$('#strDescription').val('');
	$('#strAppreciaitionAgency').val('');
	$('#dateOfAppreciation').val('');
	$('#strRecipientName').val('');
	
	$('#save').text('Save as Draft');
}


var inputArray = [];
$('#save').click(function(){
$('#form1').submit();
	
});

