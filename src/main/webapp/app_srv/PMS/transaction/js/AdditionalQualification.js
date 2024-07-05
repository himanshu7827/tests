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
        "filter": false,
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
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numGroupCategoryId').val(resultArray[2].trim());
		$('#numQualID').val(resultArray[3].trim());
		$('#numEmployeeID').val(resultArray[4].trim()).trigger('change');
		$('#strCertificateName').val(resultArray[6].trim());
		$('#strFocusArea').val(resultArray[7].trim());	
		$('#strDescriptionProgram').val(resultArray[8].trim());
		$('#save').text('Update');
		document.getElementById("strCertificateName").focus();
		
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
	    	  strCertificateName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Course/Certificate Name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'Course/Certificate Name must be less than 200 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          numEmployeeID:{
			         validators: {
		                  callback: {
		                      message: 'Please choose Employee Name',
		                      callback: function(value, validator) {
		                          // Get the selected options
		                          var options = validator.getFieldElements('numEmployeeID').val();
		                          return (options != 0);
		                      }
		                  }
		              }
		          },
		          strFocusArea: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Focus Area is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Focus Area must be less then 200 Characters.'
	                    },
	              }
	          },
	          
	          strDescriptionProgram: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Description of Program is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 1000,
	                        message: 'Description of Program must be less then 1000 Characters.'
	                    },
	              }
	          },
	                 
	       
	      
	         
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
	$('#numQualID').val('0');
	$('#strCertificateName').val('');
	$('#strFocusArea').val('');
	$('#strDescriptionProgram').val('');
	$('#numEmployeeID').val(0).trigger('change');
	
	$('#save').text('Save');
}

$('.select2Option').select2({
	 width: '100%'
});

var inputArray = [];
$('#save').click(function(){
$('#form1').submit();
	
});

