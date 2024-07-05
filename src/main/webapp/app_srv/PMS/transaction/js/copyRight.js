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
        "searching": false,
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
resetForm();
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		//$('#numGroupCategoryId').val(resultArray[2].trim());
		$('#numCopyRightID').val(resultArray[2].trim());
		//alert(resultArray[2].trim());
		$('#strTitle').val(resultArray[3].trim()).trigger('input');
		$('#dateOfCopyright').val(resultArray[4].trim());
		$('#strAgency').val(resultArray[5].trim());	
		
		/*if(resultArray[6].trim()=="Yes"){
			$('#toggle-off2').removeAttr('checked');
			$('#toggle-on2').attr('checked',true);
		}else{
			
			$('#toggle-on2').removeAttr('checked');
			$('#toggle-off2').attr('checked',true);
		}
		
		if(resultArray[7].trim()=="Yes"){
			
			$('#toggle-off1').removeAttr('checked');
			$('#toggle-on1').attr('checked',true);

		}else{
		
			$('#toggle-on1').removeAttr('checked');
			$('#toggle-off1').attr('checked',true);
		}*/
	
		if($.trim(resultArray[6])=="Filed"){
			$("#isfiledy").prop("checked", true);
			$("#filingdate").show();
			$('#dtFilingDate').val($.trim(resultArray[7]));
		}
		if($.trim(resultArray[6])=="Awarded"){
			$("#strIsAwardedy").prop("checked", true);
			$("#Awarddate").show();
			$('#dtAwardDate').val($.trim(resultArray[7]));
		}
		
		$('#strReferenceNumber').val(resultArray[8].trim());
		$('#save').text('Update');
		document.getElementById("strTitle").focus();
		
	});
	
	

$(document).ready(function(){	

	$('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
	});
	

	$(document).ready(function(){
		$("#filingdate").hide();
		$("#Awarddate").hide();
		 
	});


	$('#dtFilingDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,  
		    onSelect: function(date){
		    	$('#strDateOfApplication').trigger('input');
		    	}
	});
	$('#dtAwardDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,  
		    onSelect: function(date){
		    	$('#strDateOfApplication').trigger('input');
		    	}
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
	    	  strTitle: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Title is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 500,
	                        message: 'The Title must be less than 500 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          strAgency: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Agency is required and cannot be empty'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Agency must be less than 500 characters'
	                    },
	              }
	          },
	          strReferenceNumber: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Reference Number is required and cannot be empty'
	                  },
	                  stringLength: {
	                	  	min:0,
	                        max: 100,
	                        message: 'Reference Number must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          /*copyrightAwarded:{
	        	  group: '.col-md-12',
	        	  validators: { 
	                  callback: {
	                      message: 'Please choose Copyright Awarded!',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('copyrightAwarded').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },*/
	          dateOfCopyright: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Date Of Copyright is required and cannot be empty'
	                  },    
	              }
	          },
	        /*   copyrightFiled:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Copyright Filed!',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('copyrightFiled').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },*/
	       
	         
	      }
	  });
	
	 $('#dateOfCopyright').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		   // maxDate:"-18y",
		   // yearRange: '-65y:c+nn',
		 
		    onSelect: function(date){
		    	$('#dateOfCopyright').trigger('input');
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
		if(document.getElementById('isfiledy').checked && $('#dtFilingDate').val()==''){
			swal("Filing date cannot be empty")
			
		}
		else if(document.getElementById('strIsAwardedy').checked && $('#dtAwardDate').val()==''){
			swal("Award date cannot be empty")
			
		}
		else if(!($('#isfiledy').is(':checked'))&&(!($('#strIsAwardedy').is(':checked'))))
				
		{ swal("Please select status whether Copyright is Filed or Awarded!."); }
		
		else if(bootstrapValidator.isValid()){				
				document.getElementById("save").disabled = true; 
				/*document.form1.submit();*/
				$( "#form1")[0].submit();
				return true;
			
		}else{
			
		}
	    
	    
	    
	});
		
});
function filedate(){
	// alert();
	if(document.getElementById('isfiledy').checked){ 
		
		 
		  $("#filingdate").show();
		  $("#Awarddate").hide();
		  $('#dtAwardDate').val('');
}
	else{
		$("#filingdate").hide();
		$('#dtFilingDate').val('');
		
	}
	}

function awarddate(){
	// alert();
	if(document.getElementById('strIsAwardedy').checked){ 
		
		 
		  $("#Awarddate").show();
		  $("#filingdate").hide();
		  $('#dtFilingDate').val('');
		
}
	else{
		$("#Awarddate").hide();
		$('#dtAwardDate').val('');
		
	}
	}
function resetForm(){
	$('#numCopyRightID').val('0');
	$('#strTitle').val('');
	$('#strAgency').val('');
	//$('#save').text('Save');
	$('#dateOfCopyright').val('');
	$('#strReferenceNumber').val('');
	$('#copyrightFiled').attr('checked', false);
	$('#dtFilingDate').val('');
	$("#filingdate").hide();
	$('#dtAwardDate').val('');
	$("#Awarddate").hide();
	$('#isfiledy').prop('checked', false);
	$('#strIsAwardedy').prop('checked', false);
	
	$('#form1').data('bootstrapValidator').resetForm(true);
	//$('#copyrightAwarded').attr('checked', false);
	//$('#copyrightAwarded').prop('checked', false);
	//$('#save').text('Save');
}


var inputArray = [];
$('#save').click(function(){
$('#form1').submit();
	
});

