var addressRegex = '';
var addressErrorMessage = '';
var numericRegex = '';
var numericRegexMessage = '';
var amountRegex = '';
var amountRegexMessage = '';
var nameRegex='';
var nameErrorMessage='';
var mobileRegex='';
var mobileErrorMessage='';
var contactRegex='';
var contactErrorMessage='';

messageResource.init({
	filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs',
		function() {
			numericRegex = messageResource.get('numeric.regex',
					'regexValidationforjs');
			numericRegexMessage = messageResource.get('numeric.regex.message',
					'regexValidationforjs');

			addressRegex = messageResource.get('address.regex',
					'regexValidationforjs');
			addressErrorMessage = messageResource.get('address.regex.message',
					'regexValidationforjs');

			amountRegex = messageResource.get('amount.regex',
					'regexValidationforjs');
			amountRegexMessage = messageResource.get('amount.regex.message',
					'regexValidationforjs');
			
			nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
			nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
			
			mobileRegex= messageResource.get('mobileno.regex', 'regexValidationforjs');
			mobileErrorMessage = messageResource.get('mobileno.regex.message', 'regexValidationforjs');
			
			contactRegex= messageResource.get('contact.regex', 'regexValidationforjs');
			contactErrorMessage = messageResource.get('contact.regex.message', 'regexValidationforjs');
		});

$(document).ready(function() {
		$('#example').DataTable({
			 "columnDefs": [
			                {
			                    "targets": [ 2 ],
			                    "visible": false,
			                    "searchable": true
			                }
			            ]
		});
		
		$('#form1').bootstrapValidator({
//		      live: 'disabled',
			excluded: ':disabled',
		      message: 'This value is not valid',
		      feedbackIcons: {
		          valid: 'glyphicon glyphicon-ok',
		          invalid: 'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {
		    	           
		          
		    	  clientName: {
		              group: '.col-md-12',
		              validators: {
		            	  notEmpty: {
		                      message: 'Client Name is required and cannot be empty'
		                  },
		                 
		                  stringLength: {
		                        max: 500,
		                        message: 'The Client Name must be less than 500 characters'
		                    },
		                    regexp: {
		                        regexp: nameRegex,
		                       message: nameErrorMessage
		                    }
		              }
		          },
		          contactNumber: {
		              group: '.col-md-12',
		              validators: {
		                  notEmpty: {
		                      message: 'Contact Number is required and cannot be empty'
		                  },
		                  regexp: {
		                        regexp: contactRegex,
		                       message: contactErrorMessage
		                    }
		                    
		              }
		          },
		          clientAddress: {
		              group: '.col-md-12',
		              validators: {
		                  notEmpty: {
		                      message: 'Client Address is required and cannot be empty'
		                  },stringLength: {
		                        max: 500,
		                        message: 'The Client Address must be less than 500 characters'
		                    },
		                    regexp: {
		                        regexp: addressRegex,
		                       message: addressErrorMessage
		                    }
		              }
		          },
		          
		         
		      }
		  });
		
	});
	
	
	let editClicked = false; //============LINE BY - JAY KUMAR=============== 31/05/2024=========
	$(document).on('click','#edit',function(e){
		editClicked = true; //============LINE BY - JAY KUMAR=============== 31/05/2024=========
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#clientName').val(resultArray[1].trim());
		$('#clientAddress').val(resultArray[2].trim());
		$('#contactNumber').val(resultArray[3].trim());		
		$('#shortCode').val(resultArray[4].trim());
		/*if(resultArray[5].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}*/
		$('#save').text('Update');
	});
	
let table;
$(document).ready(function(){	
	
	//============CODE BY - JAY KUMAR=============== 21/05/2024=========auto suggestion for client name and short code
	// Function to decode HTML entities
	function decodeHTMLEntities(text) {
	    let textarea = document.createElement('textarea');
	    textarea.innerHTML = text;
	    return textarea.value;
	}
	if (!$.fn.DataTable.isDataTable('#example')) {
        table = $('#example').DataTable();
    } else {
        table = $('#example').DataTable();
    }

    let rows = table.rows().data();
    let allClientName = [];
    let allClientShortCode = [];

    for (let i = 0; i < rows.length; i++) {
        allClientName.push(decodeHTMLEntities(rows[i][1]));
        allClientShortCode.push(decodeHTMLEntities(rows[i][5]));
    }
	console.warn(allClientName);
    // Autocomplete for clientName
    $('#clientName').autocomplete({
        source: allClientName, // Provide the list of suggestions
        minLength: 1, // Minimum number of characters before the autocomplete activates
        autoFocus: true // Automatically focus on the first item in the autocomplete list
    });

    // Autocomplete for shortCode
    $('#shortCode').autocomplete({
        source: allClientShortCode, // Provide the list of suggestions
        minLength: 1, // Minimum number of characters before the autocomplete activates
        autoFocus: true // Automatically focus on the first item in the autocomplete list
    });
    
    //============CODE BY - JAY KUMAR=============== 21/05/2024
    
	$('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);

	});
	
	$('#save').click(function(){
		
		//============CODE BY - JAY KUMAR=============== 17/05/2024===========validation for duplicate entry
		//let table = $('#example').DataTable();
		//let rows = table.rows().data();
		/*console.log(rows);
		console.log($('#clientName').val());
		console.log($('#clientAddress').val());
		console.log($('#contactNumber').val());
		console.log($('#shortCode').val().toLowerCase());*/
		let containsClient = false;
		let str = '';
		for (let i = 0; i < rows.length; i++) {
		    if (rows[i][1].toLowerCase().includes($('#clientName').val().toLowerCase())) {
				str = "\""+$('#clientName').val()+"\"" + " organisation name with name \""+decodeHTMLEntities(rows[i][1])+"\" is ";
		        containsClient = true;
		        break;
		    }else if (rows[i][4] == $('#contactNumber').val()) {
				str = $('#contactNumber').val() + " contact number is ";
		        containsClient = true;
		        break;
		    } else if (rows[i][5].toLowerCase() == $('#shortCode').val().toLowerCase() && $('#shortCode').val().length != 0) {
				str = $('#shortCode').val() + " short code is ";
		        containsClient = true;
		        break;
		    }
		}
		//console.log(containsClient);
		if (containsClient && !editClicked) {
		    swal(str + "already present. Please change/update it.")
		        .then(function () {
		            // Code to execute after the alert is closed
		            event.preventDefault();
		        });
		
		    return false;
		}
		//============CODE BY - JAY KUMAR=============== 17/05/2024

		/*var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   		 }
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);*/
	   	 
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
		
		   /*if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
	});
		
});

function resetForm(){
	$('#numId').val('');
	$('#clientName').val('');
	$('#contactNumber').val('');
	$('#clientAddress').val('');
	$('#shortCode').val('');
	$('#save').text('Save');
}

$(document).ready(function(){
	$('#backPage').click(function(){
		var referrerValue = $('#referrerValue').val();
		if(referrerValue){
			window.location.href = referrerValue;
		}
	});
});
