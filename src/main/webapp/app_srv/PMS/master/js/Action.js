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
		$('#numActionId').val(resultArray[2].trim());
		$('#strName').val(resultArray[3].trim());
		$('#strActionPerformed').val(resultArray[4].trim());
		if(resultArray[5].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
		document.getElementById("strName").focus();
		
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
	    	  strName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Action name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 250,
	                        message: 'Action name must be less than 250 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          strActionPerformed: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'This field is required and cannot be empty.'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 250,
	                        message: 'Characters must be less then 250.'
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
	$('#numActionId').val('0');
	$('#strName').val('');
	$('#strActionPerformed').val('');
	
	$('#save').text('Save');
}


var inputArray = [];
$('#save').click(function(){
$('#form1').submit();
	
});

