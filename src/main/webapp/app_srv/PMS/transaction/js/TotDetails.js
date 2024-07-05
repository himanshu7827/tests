var nameRegex ='';
var nameRegexMessage ='';
var numericRegex='';
var numericRegexMessage='';
var nonRestrictnameRegex='';
var nonRestrictnameRegexMessage='';

	messageResource.init({	  
	filePath : '/PMS/resources/app_srv/PMS/global/js'
	});

	messageResource.load('regexValidationforjs', function(){
		nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
		nameRegexMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
		
		numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
		numericRegexMessage= messageResource.get('numeric.regex.message', 'regexValidationforjs');
		
		nonRestrictnameRegex=messageResource.get('nonRestrictname.regex', 'regexValidationforjs');
		nonRestrictnameRegexMessage= messageResource.get('nonRestrictname.regex.message', 'regexValidationforjs');
	});


$('.select2Option').select2({
	 width: '100%'
});

$('#strDeploymentTotDate').datepicker({
	   dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true, 
	   // maxDate:"-18y",
	   // yearRange: '-65y:c+nn',
	 
	    onSelect: function(date){
	    	$('#strDeploymentTotDate').trigger('input');
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



/*$("#dtDeploymenTotDate").datepicker({ 
	dateFormat: 'dd/mm/yy', 
    changeMonth: true,
    changeYear:true,
    onSelect: function(date){
    	$('#dtDeploymenTotDate').trigger('input');
    }
});*/
$(document).ready(function() {     
	var table = $('#deploymentDetails').DataTable( {     
		"paging":   false,        
		"searching":false,
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
	
var newTable = $('#imagesDetailsTable').DataTable( {     
	"paging":   false,        
	"searching":false,
	"ordering": false,
    "info":     false,
	rowReorder: true  
	
	} );
});



$(function() {
	
	
		$('#form').bootstrapValidator({
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
			                     message: 'Description is required and cannot be empty'
			                 },
			                 stringLength: {
			                       max: 2000,
			                       message: 'Description can not be greater than 2000 characters'
			                   },
			                 regexp: {
			                 regexp: nonRestrictnameRegex,
			                      message: nonRestrictnameRegexMessage
			                   }
			             }
			         },
			         strAgencyName: {
			             group: '.col-md-12',
			             validators: {
			                 notEmpty: {
			                     message: 'Agency name is required and cannot be empty'
			                 },
			                 stringLength: {
			                       max: 500,
			                       message: 'Agency name can not be greater than 500 characters'
			                   },
			                 regexp: {
			                 regexp: nameRegex,
			                      message: nameRegexMessage
			                   }
			             }
			         },
			        	 
			         strAgencyCity: {
			             group: '.col-md-12',
			             validators: {
			                 notEmpty: {
			                     message: 'Product Name is required and cannot be empty'
			                 },
			                 stringLength: {
			                       max: 500,
			                       message: 'Product Name can not be greater than 500 characters'
			                   },
			                 regexp: {
			                 regexp: nameRegex,
			                      message: nameRegexMessage
			                   }
			             }
			         },
			    	
			         strDeploymentTotDate:{
			        	  group: '.col-md-12',
		             validators: {
		                       notEmpty: {
		                           message: 'ToT Date is required and cannot be empty'
		                       },
		             
		             }
		             },
		     
		         
		         
		     }
		 });


		$('#reset').click(function(){
			resetForm();
			 $('#form').data('bootstrapValidator').resetForm(true);
		});
		

	$('#delete').click(function(){
	  deleteDeployementDetails();
	});
$('#save').click(function(e){	
	  
	    //var catgId=$("#numCategoryId").val();
	    //var projId=$("#numProjectId").val();
	    //var monthYear=$("#strMonthAndYear").val();
	    //var strGroupCategoryId=catgId+projId+monthYear;
	    $("#numGroupCategoryId").val("12072020");
	    var bootstrapValidator = $("#form").data('bootstrapValidator');
	    bootstrapValidator.validate();
	        if(bootstrapValidator.isValid()){
	        document.getElementById("save").disabled = true;
		      $('#form')[0].submit();
		      return true;
	        }
	        else
	        	{
	        	  console.log('Not Validated');
	        	  return false;
	        	}
		
	 
	   	// $('#valid').val(true);
	//$('#form').bootstrapValidator('enableFieldValidators', 'strServiceName', true);
	
	
});
$(document).on('click','#edit',function(e){
	$('#form').data('bootstrapValidator').resetForm(true);
	var resultArray = $(this).closest('tr').find('td').map( function()
			{
			return $(this).text();
			}).get();
	console.log(resultArray);
	$('#numTotId').val(resultArray[1]);
	$('#strDescription').val(resultArray[5]);
	$('#strDeploymentTotDate').val(resultArray[3]);
	$('#strAgencyName').val(resultArray[2]);
	$('#strAgencyCity').val(resultArray[4]);
	$('#save').text("update");
	document.getElementById("strAgencyName").focus();
	
	
});


function deleteDeployementDetails(){
	
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
		      dangerMode: true,
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	  
			    	  $("#numTotIds").val(selected);
			  		  submit_form_delete();
			      }
	  	 
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Detail to delete");  
}
 
}
function submit_form_delete()
{
	document.getElementById("form").action="/PMS/deleteTotDetails";
	document.getElementById("form").method="POST";
	document.getElementById("form").submit();
	
}


});


function resetForm(){
	$('#numTotId').val(0);
	
	$('#strDescription').val('');
	
	$('#strDeploymentTotDate').val('');
	$('#strAgencyName').val('');
	
	$('#strAgencyCity').val('');
	

	
	$('#save').text('Save as Draft');
}
  
