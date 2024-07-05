var nameRegex='';
var nameErrorMessage='';
var amountRegex='';
var amountErrorMessage='';
var numberRegex='';
var numberErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	amountRegex = messageResource.get('amount.regex', 'regexValidationforjs'); 
	amountErrorMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	numberRegex = messageResource.get('integer.regex', 'regexValidationforjs');
	numberErrorMessage = messageResource.get('integer.regex.message', 'regexValidationforjs');
});




$(document).ready(function() {
	
		var table = $('#datatable').DataTable();
		table.order([2, 'asc']).draw(); //Added by devesh on 03-04-24 to sort table according to serial number
		
		var startDate = $('#startDate').text();
		var endDate = $('#endDate').text();		
		
		/*$("#strPaymentDueDate").datepicker({ 
			dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true, 
	    minDate : startDate,
	    maxDate: endDate,
	    onSelect: function(date){
	    	$('#strPaymentDueDate').trigger('input');
	    }
				
	});*/
	});




$(document).on('click','#new_record',function(e){	
	$('#addnewrecord').addClass('hidden');
});





$('.select2Option').select2({
	 width: '100%'
});
	$(document).on('click','#edit',function(e){
		$('#form1').data('bootstrapValidator').resetForm(true);
		$('#frm').removeClass('hidden');
		$('#addnewrecord').addClass('hidden');
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		//console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#numPaymentSequence').val(resultArray[1].trim()).trigger('input');
		$('#strPaymentDueDate').val(resultArray[2].trim()).trigger('input');
		$('#numAmount').val(convertINRToAmount(resultArray[3].trim())).trigger('input');
		
		$('#strPurpose').val(resultArray[4].trim()).trigger('input');
		$('#strRemarks').val(resultArray[5].trim()).trigger('input');
		
		/*if(resultArray[7].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}*/
		if(resultArray[6].trim()=='Yes'){	
			
			$('#linkedWithMilestone1').attr('checked',true);
		}else{
			
			$('#linkedWithMilestone1').attr("checked", false);
		}
		
		$('#linkedWithMilestone1').trigger('change');
		
		$('#numMilestoneId').val(resultArray[7].trim()).change();
		$('#save').text('Update');
		document.getElementById("numPaymentSequence").focus();
	});
	
	
	
	
	/*$(document).on('click','#radioButton',function(e){*/	
	/*$(document).on('click','input[name="linkedWithMilestone"]',function(e){*/
	$(document).on('change',"#linkedWithMilestone1",function(e){
		console.log('Event Triggered');
		if($(this).is(":checked")){	
			console.log('Checked');
				$('#numMilestoneDiv').removeClass('hidden');				
				$('#numMilestoneId').validatebox({					
					required: true,				   
				});
		  }else{			 
				$('#numMilestoneDiv').addClass('hidden');
				$('#numMilestoneId').validatebox({					
					required: false,				   
				});
		  }
	});		
	


$(document).ready(function(){
	
	$('#reset').click(function(){
		resetForm();		
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
	    	  numPaymentSequence: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Payment Sequence Number is required and cannot be empty'
	                  },stringLength: {
	                        max: 3,
	                        message: 'The Payment Sequence Number must be less than 4 characters'
	                    },
	                    regexp: {
	                        regexp: numberRegex,
	                        message: numberErrorMessage
	                    }	                    
	              }
	          },numAmount: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Amount is required and cannot be empty'
	                  },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },strPurpose: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Payment Purpose is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 3000,
	                        message: 'The Payment Purpose must be less than 3000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          strPurpose: {
	              group: '.col-md-12',
	             stringLength: {
	                        max: 3000,
	                        message: 'Remarks must be less than 3000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }

	         /*,numMilestoneId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose MilestoneId Status',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numMilestoneId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          }*/
	          
	      
	         
	      }
	  });

	
	
	
	
	$('#save').click(function(){
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
	   	 
	   	 if($('#linkedWithMilestone1').is(':checked')) {
	   		$('#linkedWithMilestone').val(true);
	   	 }else{
	   		$('#linkedWithMilestone').val(false);
	   	 }

	   	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){				
				document.getElementById("save").disabled = true; 				
				 $( "#form1")[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
			document.getElementById("save").disabled = false; 
		}
	   	 
	});
		
});

function resetForm(){
	
	$('#numId').val('0');	
	var paymentSequence = $('#paymentSequence').text().trim();
	console.log(paymentSequence);
	$('#numPaymentSequence').val(paymentSequence).trigger('input');
	$('#strPaymentDueDate').val('');
	$('#numAmount').val('0');
	$('#strPurpose').val('');
	$('#strRemarks').val('');
	$('#numMilestoneId').val('0');
	
	$('#linkedWithMilestone1').attr("checked", false);
	$('#linkedWithMilestone1').trigger('change');
	
	$('#save').text('Save');
}
 
function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectid").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}



function gonext()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectid").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}

/*$(document).ready(function(){
	$('#numPaymentSequence').val($('#paymentSequence').text().trim());
});*/

function redirectPage(path){
	var encProjectId = $('#encProjectId').val();
	//alert(projectId);
	openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
}

/*Added by devesh on 06-11-23 to add multiple rows for Paymentschedule*/
var totalRowInTable = 4;
var editmode=1;

function hideAddButton() {
	
    $('.addRowButton1').addClass('hidden');
  
    $('.addRowButton1:last').removeClass('hidden');
    $('.deleteRowButton').addClass('hidden');
   
     $('.deleteRowButton:last').removeClass('hidden');
    $('.addRowButton1:last').addClass('withoutMilstone');
}

$(document).ready(function() {
	var startDate=$("#startDate").text();
	var endDate=$("#endDate").text();
	var dateData = startDate.split('/');
	var monthval = parseInt(dateData[1])-1;
	var selectedDate = new Date(dateData[2],monthval,dateData[0]);	
	var enddateData = endDate.split('/');
	var endMonthval = parseInt(enddateData[1])-1;
	var selectedEndDate = new Date(enddateData[2],endMonthval,enddateData[0]);
	

	if (startDate === "" || endDate === "") {

    }
	else{
		for (var i = 0; i < totalRowInTable; i++) {
	        var tableRow = $("<tr id=" + i + ">");
	        $("#strPaymentDueDate_" + i).datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: selectedDate,
	            maxDate: selectedEndDate
	        });
	    }
	}	
	
	hideAddButton();
	
	$(document).on("click", '.PaymentDueDate', function() {
		if (startDate === "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date and End Date are not available. Please set them before entering the Payment Due Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate === "" && endDate !== "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date is not available. Please set it before entering the Payment Due Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate !== "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project End Date is not available. Please set it before entering the Payment Due Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
	});
	
	/*---------------- (+) Click the plus button ----------------*/
    $(document).on("click", '.addRowButton', function() {
    	 var totrow=removedElementArray.length
    	var validation=true;
    	 var it;
    	for(var i=0;i<totrow;i++){
    		var PaymentScheduleId = $.trim($("input[id='PaymentScheduleId_" + (i) + "']").val());
    		var numPaymentSequence = $.trim($("input[id='numPaymentSequence_" + (i) + "']").val());
    	    var dateValue = $.trim($("input[id='strPaymentDueDate_" + (i) + "']").val());
    	    var numAmount = $.trim($("input[id='numAmount_" + (i) + "']").val());
    	    var strPurpose = $("#strPurpose_" + (i)).val();
    	    var strRemarks = $("#strRemarks_" + (i)).val();
    	    var numMilestoneId = $("#numMilestoneId_" + (i)).val();
    	    var PaymentScheduleValid = $("#validField_" + (i)).val();

    	    $('.strPaymentDueDate_'+ (i)).text("");
    	    $('.numAmount_'+ (i)).text("");
    	    $('.strPurpose_'+ (i)).text("");
    	    $('.strRemarks_'+ (i)).text("");
    	    $('.numMilestoneId_'+ (i)).text("");
    	    $('.numPaymentSequence_'+ (i)).text("");
    	    
    	    if(numPaymentSequence == ''){
    	    	$('.numPaymentSequence_'+ (i)).text("Payment Sequence Number is required and cannot be empty");
    	    	validation=false;
    	    }
    	    
    	    if(dateValue == ''){
    	    	$('.strPaymentDueDate_'+ (i)).text("Payment Due Date is required and cannot be empty");
    	    	validation=false;
    	    }
    	    if(numAmount == ''){
    	    	$('.numAmount_'+ (i)).text("Amount is required and cannot be empty");
    	    	validation=false;
    	    }
    	    if(strPurpose == ''){
    	    	$('.strPurpose_'+ (i)).text("Purpose is required and cannot be empty");
    	    	validation=false;
    	    }    
    	    if (strRemarks == '') {
    	    	$('.strRemarks_'+ (i)).text("Remarks is required and cannot be empty");
    	    	validation=false;
    	    }
    	    if (numMilestoneId == 0) {
    	    	$('.numMilestoneId_'+ (i)).text("Milestone is required and cannot be empty");
    	    	validation=false;
    	    }
    	    if(dateValue == '' && numAmount == '' && strPurpose == '' && strRemarks == '' && numMilestoneId == 0){
    	    	$('.strPaymentDueDate_'+ (i)).text("");
    	        $('.numAmount_'+ (i)).text("");
    	        $('.strPurpose_'+ (i)).text("");
    	        $('.strRemarks_'+ (i)).text("");
    	        $('.numMilestoneId_'+ (i)).text("");
    	        $('.numPaymentSequence_'+ (i)).text("");
    	        validation=true;
    	    }
	        
    	}
        
        if (validation) {
        	
            i = totrow;
            i++;
            console.log("totalRowInTable "+totrow);
           
            // Create a new row element
            var newRow = $("<tr id='" + i + "trow'></tr>");

            // Create and append the cells to the row
            newRow.append("<td class='hidden edithide td_" + i + "'>" +
            		"<input class='hidden' id='validField_" + i + "'/>" +
            	    "<input class='hidden' id='PaymentScheduleId_" + i + "'/>" +
            	    "<div class='input-container'>" +
            	    "<i class=''></i>" +
            	    "<input class='input-field' id='numPaymentSequence_" + i + "'  />" +
            	    "</div>" +
            	    "<div class='alert-danger text-center numPaymentSequence_"+i+" errorMessageClass' role='alert'></div>" +
            	    "</td>");

            newRow.append("<td class='edithide td_" + i + "'>" +
            	    "<div class='input-container-paymentSchedule'>" +
            	    "<i class='fa fa-calendar icon_paymentSchedule'></i>" +
            	    "<input class='input-field PaymentDueDate' readonly='true' id='strPaymentDueDate_" + i + "' />" +
            	    "</div>" +
            	    "<div class='alert-danger text-center strPaymentDueDate_"+i+" errorMessageClass' role='alert'></div>" +
            	    "</td>");

            newRow.append("<td class='edithide td_" + i + "'>" +
            	    "<div class='input-container-paymentSchedule'>" +
            	    "<i class='fa fa-inr icon_paymentSchedule'></i>" +
            	    "<input class='input-field' id='numAmount_" + i + "'/>" +
            	    "</div>" +
            	    "<div class='alert-danger text-center numAmount_"+i+" errorMessageClass' role='alert'></div>" +
            	    "</td>");

            newRow.append("<td class='edithide td_" + i + "'>" +
            	    "<div class='input-container-paymentSchedule'>" +
            	    "<i class='fa fa-th icon_paymentSchedule'></i>" +
            	    "<textarea class='input-field' rows='2' id='strPurpose_" + i + "'></textarea>" +
            	    "</div>" +
            	    "<div class='alert-danger text-center strPurpose_"+i+" errorMessageClass' role='alert'></div>" +
            	    "</td>");

            newRow.append("<td class='edithide td_" + i + "'>" +
            	    "<div class='input-container-paymentSchedule'>" +
            	    "<i class='fa fa-th icon_paymentSchedule'></i>" +
            	    "<textarea class='input-field' rows='2' id='strRemarks_" + i + "'></textarea>" +
            	    "</div>" +
            	    "<div class='alert-danger text-center strRemarks_"+i+" errorMessageClass' role='alert'></div>" +
            	    "</td>");

            newRow.append("<td width='15%' class='edithide td_" + i + "'>" +
            	    "<div class='input-container-paymentSchedule' id='numMilestoneName_" + i + "'>" /*+projectNamesArray[i]*/
            	    /*"<select id='numMilestoneId_" + i + "' class='select2Option'>" +
            	    "<option value='0'>-- Select Milestone --</option>" +
            	    "<c:forEach items='${milestoneData}' var='milestone'>" +
            	    "<option value='${milestone.numId}'>" +
            	    "<c:out value='${milestone.milestoneName}'></c:out>" +
            	    "</option>" +
            	    "</c:forEach>" +
            	    "</select>"*/ +
            	    "</div>" +"<div class='input-container-paymentSchedule hidden' id='numMilestoneId_" + i + "'>" +/*removedElementArray[i]+*/
            	     "</div>" +
            	    /*"<div class='alert-danger text-center numMilestoneId_"+i+" errorMessageClass' role='alert'></div>" +*/
            	    "</td>");

            newRow.append("<td class='ActionColumn'>" +
                "<div align='center' class='pad-top pad-bottom' style='display: inline-flex'>" +
                "<button type='button' class='btn_paymentSchedule btn-primary addRowButton'>" +
                "<i class='fa fa-plus-circle btn_paymentSchedule btn-primary'></i>" +
                "</button>&nbsp;&nbsp;" +
                "<button type='button' class='btn_paymentSchedule btn-danger deleteRowButton' onclick='deletePaymentSchedule("+i+")'>" +
                "<i class='fa fa-minus-circle btn_paymentSchedule btn-danger'></i>" +
                "</button>" +
                "</div>" +
                "</td>");

            // Append the new row to the table
            $("#paymentScheduleMultipleData").append(newRow);
            hideAddButton();
            $("#strPaymentDueDate_" + i).datepicker({
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
        		changeYear:true,
        	    minDate: selectedDate,
        	    maxDate:selectedEndDate
            });
        } 
    });
    var resultArray;
    $(document).on('click','#editPaymentSchedule',function(e){ 
		 resultArray = $(this).closest('tr').find('td').map( function(){
			return $(this).text();}).get();
			//console.log(resultArray);
		EditPaymentScheduleDetails(resultArray);
		
    });
    
    var blank=false;
    $(document).on('click','#savePaymentSchedule',function(e){ 
    	
    	/*alert("save");*/
    	var validation=true;
    	var isallblank=true;
    	//variables added by devesh on 27-03-24 to set validation of payment schedule amount
	    var projectCost = parseFloat($("#projectCost").val());  // Parse as float
	    var totalPaymentScheduleAmount = parseFloat($("#totalPaymentScheduleAmount").val());  // Parse as float
	    var totalAmount = totalPaymentScheduleAmount;
	    //End of code
    	for(var i=0;i<removedElementArray.length;i++){
    		var PaymentScheduleId = $.trim($("input[id='PaymentScheduleId_" + (i) + "']").val());
    		var numPaymentSequence = $.trim($("input[id='numPaymentSequence_" + (i) + "']").val());
    	    var dateValue = $.trim($("input[id='strPaymentDueDate_" + (i) + "']").val());
    	    var numAmount = $.trim($("input[id='numAmount_" + (i) + "']").val());
    	    var strPurpose = $("#strPurpose_" + (i)).val();
    	    var strRemarks = $("#strRemarks_" + (i)).val();
    	    var numMilestoneId = $("#numMilestoneId_" + (i)).val();
    	    var PaymentScheduleValid = $("#validField_" + (i)).val();
    	    var linkedWithMilestonE
    	   
    	    if(numAmount != '') totalAmount += parseFloat(numAmount); //added by devesh on 27-03-24 to get total payment schedule amount

    	    $('.strPaymentDueDate_'+ (i)).text("");
    	    $('.numAmount_'+ (i)).text("");
    	    $('.strPurpose_'+ (i)).text("");
    	    $('.strRemarks_'+ (i)).text("");
    	    $('.numMilestoneId_'+ (i)).text("");
    	    $('.numPaymentSequence_'+ (i)).text("");

    	   
    	    
    	    if(numPaymentSequence == ''){
    	    	$('.numPaymentSequence_'+ (i)).text("Payment Sequence Number is required and cannot be empty");
    	    	validation=false;
    	    	isallblank=false;
    	    }
    	   
    	    if(dateValue == ''){
    	    	$('.strPaymentDueDate_'+ (i)).text("Payment Due Date is required and cannot be empty");
    	    	validation=false;
    	    	isallblank=false;
    	    }
    	    if(numAmount == ''){
    	    	$('.numAmount_'+ (i)).text("Amount is required and cannot be empty");
    	    	validation=false;
    	    	isallblank=false;
    	    }
    	    if (!/^\d+(\.\d{1,2})?$/.test(numAmount)){
    	    	
    	    	$('.numAmount_'+ (i)).text("Amount must be a valid numeric value with up to two decimal places.");
    	    	validation=false;
    	    	isallblank=false;
    	    }
    	    if (parseFloat(numAmount) <= 0) {
    	    	$('.numAmount_'+ (i)).text("Amount should be greater than 0.");
    	    	validation=false;
    	    	isallblank=false;
    	      }

    	      // Check if cost is less than 20 characters
    	      if (numAmount.length > 20) {
    	    	  $('.numAmount_'+ (i)).text("Amount must be less than 20 characters.");
    	      	validation=false;
    	      	isallblank=false;
    	      }
    	    if(strPurpose == ''){
    	    	$('.strPurpose_'+ (i)).text("Purpose is required and cannot be empty");
    	    	validation=false;
    	    	isallblank=false;
    	    }    
    	    
    	    if(dateValue == '' && numAmount == '' && strPurpose == '' && strRemarks == '' && numMilestoneId == 0){
    	    	$('.strPaymentDueDate_'+ (i)).text("");
    	        $('.numAmount_'+ (i)).text("");
    	        $('.strPurpose_'+ (i)).text("");
    	        $('.strRemarks_'+ (i)).text("");
    	        $('.numMilestoneId_'+ (i)).text("");
    	        $('.numPaymentSequence_'+ (i)).text("");
    	        validation=true;
    	        
    	    }
	        
    	}
    	
    	if(editmode==1 && !isallblank){
    		/*alert("totalAmount.."+totalAmount);
        	alert("projectCost.."+projectCost);*/
        	//condition added by devesh on 27-03-24 to set validation of payment schedule amount
            if (totalAmount > projectCost) {
            	swal("Total Project Payment Schedule Amount is more than CDAC Outlay.");
            	validation=false;
            }
            //End of code
	    	if(validation){
	    		
	    		var counter=0;
	    		var validCount=0;
	    		var validateRow;
		    	for(var i=0;i<removedElementArray.length;i++){
		    		PaymentScheduleId = $.trim($("input[id='PaymentScheduleId_" + (i) + "']").val());
		    		numPaymentSequence =  parseInt($('#paymentSequence').text(), 10);               /* $.trim($("input[id='numPaymentSequence_" + (i) + "']").val());*/
		    		
		    	    dateValue = $.trim($("input[id='strPaymentDueDate_" + (i) + "']").val());
		    	    numAmount = $.trim($("input[id='numAmount_" + (i) + "']").val());
		    	    strPurpose = $("#strPurpose_" + (i)).val();
		    	    strRemarks = $("#strRemarks_" + (i)).val();
		    	    var numMilestoneIds = $("#numMilestoneId_" + (i)).text();
		    	    var numMilestoneId = parseInt(numMilestoneIds,10);
		    	    var PaymentScheduleValid = $("#validField_" + (i)).val();
		    	    var validation=true;
		    	   /* alert("numMilestoneId "+numMilestoneId);*/
		    	    if (numMilestoneId == 0){
		    	    	 var linkedWithMilestonE='No';	
		    	    }
		    	    else{
		    	    	 var linkedWithMilestonE='Yes';	
		    	    }
                     
		    	   
		    	    
		    	    
		    	  
		    	
			        validation=false;
			        if((numAmount != '' || dateValue != '' || strPurpose != '' || strRemarks != '') && PaymentScheduleValid == ''){
			        	blank=true;
			        	validateRow = validationForsave(i);
			        	if(validateRow){
			        		break;
			        	}
			        	
			        	validCount=numPaymentSequence;
			        	validCount=validCount+counter
			    	     counter++;

			    	     numPaymentSequence=validCount;
			    	     if(numAmount==null||numAmount==''){
			    	    	 
			    	    	 numAmount =0;
			    	     }
			    	     
			    	     
			        	savePaymentScheduleDetails(PaymentScheduleId,numAmount,dateValue,strPurpose,strRemarks,numPaymentSequence,linkedWithMilestonE,numMilestoneId,'Multiple');
			        	validation=true;
			        }
		    	}
		    	if(!blank){
		    		swal("Please Enter atleast one Payment Schedule.");
		    		
		    	}else if(blank && !validateRow){
		    		//location.reload();
		    	}
		    	else{
		    		
		    		
		    	}
		    	
	    	}  		
    	}
    	/*else if(isallblank){
    		swal("Please enter atleast one Payment Schedule.");
    	}*/
    	else{
    		/*alert("totalAmount.."+totalAmount);
        	alert("projectCost.."+projectCost);*/
        	//condition added by devesh on 27-03-24 to set validation of payment schedule amount
            if (totalAmount > projectCost) {
            	swal("Project Payment Schedule Amount is more than CDAC Outlay.");
            	validation=false;
            }
            //End of code
	    	if(validation){
	    		
	    		var counter1=0;
	    		var validCount1=0;
	    		var validator=false;
			    	for(var i=0;i<removedElementArray.length;i++){
			    		PaymentScheduleId = $.trim($("input[id='PaymentScheduleId_" + (i) + "']").val());
			    		numPaymentSequence =  parseInt($('#paymentSequence').text(), 10);               /* $.trim($("input[id='numPaymentSequence_" + (i) + "']").val());*/
			    	    dateValue = $.trim($("input[id='strPaymentDueDate_" + (i) + "']").val());
			    	    numAmount = $.trim($("input[id='numAmount_" + (i) + "']").val());
			    	    strPurpose = $("#strPurpose_" + (i)).val();
			    	    strRemarks = $("#strRemarks_" + (i)).val();
			    	    var numMilestoneIds = $("#numMilestoneId_" + (i)).text();
			    	    var numMilestoneId = parseInt(numMilestoneIds,10);
			    	    PaymentScheduleValid = $("#validField_" + (i)).val();
			    	    if (numMilestoneId == 0){
			    	    	 var linkedWithMilestonE='No';	
			    	    }
			    	    else{
			    	    	 var linkedWithMilestonE='Yes';	
			    	    }
			    	    
			    	   
			    	    
			    	    validCount1=numPaymentSequence;
			    	   
			    	  		//console.log(resultArray);
			    	    	if(resultArray !=null){
			    	    	if(resultArray[8].trim()=='0'){
			    	    		 var linkedWithMilestonE='No';
			    	    		 numMilestoneId=0;
			    	    		 numPaymentSequence =  parseInt(resultArray[1].trim(),10);
			    	    		 validator=true;
			    	    	}
			    	    	else if(resultArray[8].trim()!='0'){
				    	    		 var linkedWithMilestonE='Yes';
				    	    		 numMilestoneId= parseInt(resultArray[8].trim(),10);
				    	    		 numPaymentSequence =  parseInt(resultArray[1].trim(),10);
				    	    		 validator=true;
				    	    	}
			    	    	}
			    	    
			    	    
			    	    validation=false;
				        if((numAmount != '' || dateValue != '' || strPurpose != '' || strRemarks != '')&& PaymentScheduleValid == '' ){
				        	
				        	if(validator==false){
				        		
				        		
					        	validCount1=validCount1+counter1
					    	     counter1++;
					    	    
					    	     numPaymentSequence=validCount1;
					    	     
					    	     if(numAmount==null||numAmount==''){
					    	    	 
					    	    	 numAmount =0;
					    	     }
					    	     savePaymentScheduleDetails(PaymentScheduleId,numAmount,dateValue,strPurpose,strRemarks,numPaymentSequence,linkedWithMilestonE,numMilestoneId,'Multiple');
						        	validation=true;
				        		
				        	}
				        	else{
				        		
				        		 if(numAmount==null||numAmount==''){
					    	    	 
					    	    	 numAmount =0;
					    	     }
				        		savePaymentScheduleDetails(PaymentScheduleId,numAmount,dateValue,strPurpose,strRemarks,numPaymentSequence,linkedWithMilestonE,numMilestoneId,'Multiple');
					        	validation=true;
				        	}
				        	
				        	
				        }
			    	}
			    	//location.reload();
	    	}
    	}
    	
    });
});

function savePaymentScheduleDetails(PaymentScheduleId,numAmount,dateValue,strPurpose,strRemarks,numPaymentSequence,linkedWithMilestonE,numMilestoneId,callByMethod)
{
	if(PaymentScheduleId == ''){
		PaymentScheduleId = 0;
	}
	var project_id = $("#projectId").val();
	
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/saveUpdateProjectPaymentScheduleMaster",
		data : {
			numId:PaymentScheduleId,
			numAmount: numAmount,
			strPaymentDueDate: dateValue,
	        projectId: project_id,
	        strPurpose: strPurpose,
	        strRemarks: strRemarks,
	        numPaymentSequence:numPaymentSequence,
	        linkedWithMilestone: linkedWithMilestonE,
	        numMilestoneId: numMilestoneId
	    },
		async:false,
		success : function(response) {
			// response will be an array of strings
	        if (response.length >= 2) {
	            var status = response[0];
	            var message = response[1];
	            if (status === "Success") {
	                // Handle success
	                console.log("Success");
	                location.reload();
	            } else {
	                // Handle failure
	                swal("Failed!");
	            }
	        } else {
	            console.error("Invalid response format.");
	        }

		}, error: function(data) {
	 	  
	   }
	});
}


function EditPaymentScheduleDetails(resultArray)
{
	$('.ActionColumn').addClass("hide");
	$('.edithide').addClass("hide");
	$('.td_0').removeClass("hide");
	$('#savePaymentSchedule').text('Update');
	editmode=0;
	var allowDate= $('#allowDateEdit').val();

	if(allowDate==0){
		$("#strPaymentDueDate_").datepicker("option", "disabled", true);
	}
	else{
		$("#strPaymentDueDate_").datepicker("enable");
	}

	
	
	$('#PaymentScheduleId_0').val(resultArray[0].trim());
	$('#numPaymentSequence_0').val(resultArray[1].trim());
	$('#strPaymentDueDate_0').val(resultArray[3]);
	$('#numAmount_0').val(convertINRToAmount(resultArray[4].trim())).trigger('input');
	$('#strPurpose_0').val(resultArray[5]);
	$('#strRemarks_0').val(resultArray[6]);
	$('#numMilestoneId_0').val(resultArray[8].trim());
	$('#numMilestoneName_0').text(resultArray[9]);
	$('.strPaymentDueDate_0').text("");
    $('.numAmount_0').text("");
    $('.strPurpose_0').text("");
    $('.strRemarks_0').text("");
    $('.numMilestoneId_0').text("");
    $('.numPaymentSequence_0').text("");
    
    //Added by devesh on 28-03-24 to remove duplicate sum of amount in edit mode
    var editAmount = parseFloat($("#totalPaymentScheduleAmount").val()) - parseFloat($("#numAmount_0").val());
	$("#totalPaymentScheduleAmount").val(editAmount);
	/*alert(parseFloat($("#totalPaymentScheduleAmount").val()));*/
	//End of code
	
}
var notMilstoneRow;
function deletePaymentSchedule(i)
{
	
	
	var paymentScheduleId = $('#PaymentScheduleId_'+i+'').val();
	if ($('#'+i+"trow").is(':last-child')) {
		
		
		
		/*$('#'+i+"trow").addClass("hidden");*/
		 /*$('.deleteRowButton').closest('tr').remove();*/
		if(i==notMilstoneRow-1){
		console.log(notMilstoneRow);	
		}
		else{
			$('#PaymentScheduleId_'+i).val("");
			$('#numPaymentSequence_'+i).val("");	
			$('#strPaymentDueDate_'+i).val("");
			$('#numAmount_'+i).val("");
			$('#strPurpose_'+i).val("");
			$('#strRemarks_'+i).val("");
			$('#strRemarks_'+i).val("");
			$('#numMilestoneId_'+i).val("0");
			console.log("w "+i);	
			$('#deleteButton'+i).closest('tr').remove();
			hideAddButton();
		}
		
	} else {
		
		$('#PaymentScheduleId_'+i).val("");
		$('#numPaymentSequence_'+i).val("");	
		$('#strPaymentDueDate_'+i).val("");
		$('#numAmount_'+i).val("");
		$('#strPurpose_'+i).val("");
		$('#strRemarks_'+i).val("");
		$('#strRemarks_'+i).val("");
		$('#numMilestoneId_'+i).val("0");
		$('#'+i+"trow").addClass("hidden");
		$("#validField_" + (i)).val("True");
	}

	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/deleteProjectPaymentSchedule",
		data : {
			paymentScheduleId :paymentScheduleId,
	    },
		success : function(response) {

		}, error: function(data) {

	   }
	});
}

function saveField(i){
	var PaymentScheduleId = $.trim($("input[id='PaymentScheduleId_" + (i) + "']").val());
	var numPaymentSequence =  parseInt($('#paymentSequence').text(), 10);                        /*  $.trim($("input[id='numPaymentSequence" + (i) + "']").val());*/
    var dateValue = $.trim($("input[id='strPaymentDueDate_" + (i) + "']").val());
    var numAmount = $.trim($("input[id='numAmount_" + (i) + "']").val());
    var strPurpose = $("#strPurpose_" + (i)).val();
    var strRemarks = $("#strRemarks_" + (i)).val();
    var numMilestoneIds = $("#numMilestoneId_" + (i)).text();
    var numMilestoneId = parseInt(numMilestoneIds,10);
    var PaymentScheduleValid = $("#validField_" + (i)).val();
    //variables added by devesh on 27-03-24 to set validation of payment schedule amount
    var doubleNumAmount = parseFloat(numAmount);
    var projectCost = parseFloat($("#projectCost").val());  // Parse as float
    var totalPaymentScheduleAmount = parseFloat($("#totalPaymentScheduleAmount").val());  // Parse as float
    var totalAmount = doubleNumAmount + totalPaymentScheduleAmount;
    //End of code
    var validation=true;
    
    if (numMilestoneId == 0){
    	 var ab='No';	
    }
    else{
    	 var ab='Yes';	
    }

    $('.strPaymentDueDate_'+ (i)).text("");
    $('.numAmount_'+ (i)).text("");
    $('.strPurpose_'+ (i)).text("");
    $('.strRemarks_'+ (i)).text("");
    $('.numMilestoneId_'+ (i)).text("");
    $('.numPaymentSequence_'+ (i)).text("");
    
    //condition added by devesh on 27-03-24 to set validation of payment schedule amount
    if (totalAmount > projectCost) {
    	swal("Project Payment Schedule Amount is more than CDAC Outlay.");
    	validation=false;
    }
    //End of code
    
    if(numPaymentSequence == ''){
    	$('.numPaymentSequence_'+ (i)).text("Payment Sequence Number is required and cannot be empty");
    	validation=false;
    }
    if(dateValue == ''){
    	$('.strPaymentDueDate_'+ (i)).text("Payment Due Date is required and cannot be empty");
    	validation=false;
    }
    if(numAmount == ''){
    	$('.numAmount_'+ (i)).text("Amount is required and cannot be empty");
    	validation=false;
    }
   if (!/^\d+(\.\d{1,2})?$/.test(numAmount)){
    	
    	$('.numAmount_'+ (i)).text("Amount must be a valid numeric value with up to two decimal places.");
    	validation=false;
    }
    if (parseFloat(numAmount) <= 0) {
    	$('.numAmount_'+ (i)).text("Amount should be greater than 0.");
    	validation=false;
      }

      // Check if cost is less than 20 characters
      if (numAmount.length > 20) {
    	  $('.numAmount_'+ (i)).text("Amount must be less than 20 characters.");
      	validation=false;
      }

    if(strPurpose == ''){
    	$('.strPurpose_'+ (i)).text("Purpose is required and cannot be empty");
    	validation=false;
    }    
  
    if(dateValue == '' && numAmount == '' && strPurpose == '' && strRemarks == '' && numMilestoneId == 0){
    	$('.strPaymentDueDate_'+ (i)).text("");
        $('.numAmount_'+ (i)).text("");
        $('.strPurpose_'+ (i)).text("");
        $('.strRemarks_'+ (i)).text("");
        $('.numMilestoneId_'+ (i)).text("");
        $('.numPaymentSequence_'+ (i)).text("");
        validation=true;
    }   

    if (validation) {
    	$('html, body').animate({ scrollTop: 0 }, 'fast');
    	if(PaymentScheduleId == ''){
    		PaymentScheduleId = 0;
    	}
    	var project_id = $("#projectId").val();
    	
    	if(numAmount==null||numAmount==''){
	    	 
	    	 numAmount = 0;
	     }
    	
    	/*$.ajaxRequest.ajax({
    		type : "POST",
    		url : "/PMS/mst/saveUpdateProjectPaymentScheduleMaster",
    		data : {
    			numId:PaymentScheduleId,
    			numAmount: numAmount,
    			strPaymentDueDate: dateValue,
    	        projectId: project_id,
    	        strPurpose: strPurpose,
    	        strRemarks: strRemarks,
    	        numPaymentSequence:numPaymentSequence,
    	        linkedWithMilestone: ab,
    	        numMilestoneId: numMilestoneId
    	    },
    		async:false,
    		success : function(response) {
    			
    	            var userinfoMessageDiv = $(response).find('#userinfo-message');
    	            if (userinfoMessageDiv.length > 0) {
    	                var successMessage = userinfoMessageDiv.find('p.success_msg').text();
    	                $('#userinfo-message').removeClass("hidden");
    	                $('#test').text(successMessage);
    	             // Add "hidden" class after 3 seconds
    	                setTimeout(function() {
    	                    $('#userinfo-message').addClass('hidden');
    	                }, 3000); // 3000 milliseconds = 3 seconds
    	                
    	               location.reload();
    	                
    	               var idMatch = successMessage.match(/Id (\d+)/);
    	                if (idMatch) {
    	                    var id = idMatch[1];
    	                    var elementId = '#PaymentScheduleId_' + (i);
    	                    $(elementId).val(id);
    	                }

    	            }
    	                			
    	            
    		}, error: function(data) {
    			
    	   }
    	})*/
    	//Modified by devesh on 21-03-24 to fix ajax call
    	$.ajaxRequest.ajax({
    		type : "POST",
    		url : "/PMS/mst/saveUpdateProjectPaymentScheduleMaster",
    		data : {
    			numId:PaymentScheduleId,
    			numAmount: numAmount,
    			strPaymentDueDate: dateValue,
    	        projectId: project_id,
    	        strPurpose: strPurpose,
    	        strRemarks: strRemarks,
    	        numPaymentSequence:numPaymentSequence,
    	        linkedWithMilestone: ab,
    	        numMilestoneId: numMilestoneId
    	    },
    		async:false,
    		success : function(response) {
    			// response will be an array of strings
    	        if (response.length >= 2) {
    	            var status = response[0];
    	            var message = response[1];
    	            if (status === "Success") {
    	                // Handle success
    	                console.log("Success");
    	                location.reload();
    	            } else {
    	                // Handle failure
    	                swal("Failed!");
    	            }
    	        } else {
    	            console.error("Invalid response format.");
    	        }

    		}, error: function(data) {
    	 	  
    	   }
    	});
    	//End of ajax
    	}
    }
//End of Functions
var involvement;
var allRowCount;
//End of Functions
var numPaymentSequenceArray = [];  // Declare an empty array to store Existing Payment sequence
$(document).ready(function(){
	$('#numPaymentSequence_0').val($('#paymentSequence').text().trim());
	var z=$('#paymentSequence').text();
	 involvement=parseInt(z, 10);
	
	
	 //Bhavesh (23-11-23) ajax call to retrieve  Payment sequence Number
	 $.ajax({
	     url: '/PMS/mst/getProjectPaymentScheduleByProjectId',
	     method: 'POST',
	     data: { projectId: $('#projectid').val() },
	     success: function(data) {
	         // Handle the returned data (list of ProjectPaymentScheduleMasterModel objects)
	         if (data.length > 0) {
	        	 for(var i=0;i<data.length;i++){
	             numPaymentSequenceArray.push(data[i].numPaymentSequence);
	            
	        	 }
	        	 /*console.log(numPaymentSequenceArray);*/
	         } else {
	             /*console.log('No data returned.');*/
	         }
	         addRow(0);
	     },
	     error: function(error) {
	         // Handle the error
	         console.error(error);
	     }
	 });

});

var removedElementArray;
//Bhavesh(23-11-23) 
function addRow(t){
	numPaymentSequenceArray
	
	  // Arrays to store numId and milestoneName
    var numIdArray = [];
    var milestoneNameArray = [];

    // Iterate through each option in the select
    $('#numMilestoneId_n option').each(function() {
      // Extract values and push them into the respective arrays
      numIdArray.push($(this).val());
      milestoneNameArray.push($(this).text());
    });

    /*// Log the arrays (you can use them as needed)
    console.log('numIdArray:', numIdArray);
    console.log('milestoneNameArray:', milestoneNameArray);*/
    
    var projectNamesArray = [];

 // Process each string in the original array
    milestoneNameArray.forEach(function (str) {
   // Remove leading and trailing whitespaces and unwanted characters
   var projectName = str.trim().replace(/\n\t+/g, '');
   
   // Check if the string is not '-- Select Milestone --'
   if (projectName !== '-- Select Milestone --') {
     // Add the cleaned project name to the new array
     projectNamesArray.push(projectName);
   }
 });

 /*// Log the result
 console.log("projectNamesArray "+ projectNamesArray);*/
 
removedElementArray = numIdArray.slice(1);

//Log the results

/*console.log('Removed Element Array:', removedElementArray);*/

	var startDate=$("#startDate").text();
	var endDate=$("#endDate").text();
	var dateData = startDate.split('/');
	var monthval = parseInt(dateData[1])-1;
	var selectedDate = new Date(dateData[2],monthval,dateData[0]);	
	var enddateData = endDate.split('/');
	var endMonthval = parseInt(enddateData[1])-1;
	var selectedEndDate = new Date(enddateData[2],endMonthval,enddateData[0]);
   /* var i = totalRowInTable;
    totalRowInTable += 1;*/
	
	var rowNo;
	 var index;
	if(t!=0){
		
		rowNo=allRowCount+1;
		index=allRowCount;
		allRowCount += 1;
		projectNamesArray[index]="";
		removedElementArray[index]='0';
	}
	else{
		rowNo=removedElementArray.length;
		notMilstoneRow=rowNo
		allRowCount=removedElementArray.length;
		index=0;
	}
    
    for (var i=index;i<rowNo;i++){
   	
    // Create a new row element
    var newRow = $("<tr id='"+i+"trow'></tr>");
    console.log("invol:"+involvement+" and i:"+i);
    // Create and append the cells to the row
    newRow.append("<td class='hidden edithide  td_" + i + "'  >" +
        "<input class='hidden' id='validField_" + i + "'/>" +
        "<input class='hidden' id='PaymentScheduleId_" + i + "'/>" +
        "<div class='input-container'>" +
        "<i class=''></i>" +
        "<input class='input-field' id='numPaymentSequence_" + i + "'  />" +
        "</div>" +
        "<div class='alert-danger text-center numPaymentSequence_"+i+" errorMessageClass' role='alert'></div>" +
        "</td>");

    newRow.append("<td class='edithide resettable1 td_" + i + "'>" +
        "<div class='input-container-paymentSchedule'>" +
        "<i class='fa fa-calendar icon_paymentSchedule '></i>" +
        "<input class='input-field PaymentDueDate resettable' readonly='true' id='strPaymentDueDate_" + i + "'  />" +
        "</div>" +
        "<div class='alert-danger text-center strPaymentDueDate_"+i+" errorMessageClass' role='alert'></div>" +
        "</td>");

    newRow.append("<td class='edithide resettable1 td_" + i + "'>" +
        "<div class='input-container-paymentSchedule'>" +
        "<i class='fa fa-inr icon_paymentSchedule'></i>" +
        "<input class='input-field resettable' id='numAmount_" + i + "' />" +
        "</div>" +
        "<div class='alert-danger text-center numAmount_"+i+" errorMessageClass' role='alert'></div>" +
        "</td>");

    newRow.append("<td class='edithide resettable1 td_" + i + "'>" +
        "<div class='input-container-paymentSchedule'>" +
        "<i class='fa fa-th icon_paymentSchedule'></i>" +
        "<textarea class='input-field resettable' rows='2' id='strPurpose_" + i + "'  ></textarea>" +
        "</div>" +
        "<div class='alert-danger text-center strPurpose_"+i+" errorMessageClass' role='alert'></div>" +
        "</td>");

    newRow.append("<td class='edithide td_" + i + "'>" +
        "<div class='input-container-paymentSchedule'>" +
        "<i class='fa fa-th icon_paymentSchedule'></i>" +
        "<textarea class='input-field resettable' rows='2' id='strRemarks_" + i + "'  ></textarea>" +
        "</div>" +
        "<div class='alert-danger text-center strRemarks_"+i+" errorMessageClass' role='alert'></div>" +
        "</td>");

    newRow.append("<td width='15%' class='edithide td_" + i + "'>" +
        "<div class='' id='numMilestoneName_" + i + "'>" +projectNamesArray[i]
         +
        "</div>" +"<div class='input-container-paymentSchedule hidden' id='numMilestoneId_" + i + "'>" +removedElementArray[i]+
         "</div>" +
        /*"<div class='alert-danger text-center numMilestoneId_"+i+" errorMessageClass' role='alert'></div>" +*/
        "</td>");

    newRow.append("<td class='ActionColumn '>" +
    	    "<div align='center' class='pad-top pad-bottom' style='display: inline-flex'>" +
    	    "<button type='button' id='z' class='btn_paymentSchedule withoutMilestone  btn-primary addRowButton1'>" +
            "<i class='fa fa-plus-circle btn_paymentSchedule btn-primary'></i>" +
            "</button>&nbsp;&nbsp;" +
    	   "<button type='button' id='SaveNew"+i+"' class='fa fa-check-square-o fa-2x  green' onclick='saveField("+i+")'>" +
    	   /* "<i class='fa fa-minus-circle'></i>" +*/
    	    "</button>" +'<button type="button" id="deleteButton'+i+'" class="btn_milestone btn-danger deleteRowButton" onclick="deletePaymentSchedule('+i+')"><i class="fa fa-minus-circle btn_milestone btn-danger"></i></button>'+
    	    "</div>" +
    	    "</td>");

    
    // Append the new row to the table
    $("#paymentScheduleMultipleData").append(newRow);
    $('#numPaymentSequence_'+i).val(involvement+i);
   
    
   
   
    hideAddButton();
    $("#strPaymentDueDate_" + i).datepicker({
        dateFormat: 'dd/mm/yy',
        changeMonth: true,
		changeYear:true,
	    minDate: selectedDate,
	    maxDate:selectedEndDate
    });
    
   
    }
	
	
}

$(document).ready(function(){
	$('#numPaymentSequence_0').val($('#paymentSequence').text().trim());
	
	setTimeout(function() {
        $('#userinfo-message1').addClass('hidden');
    }, 3000); // 3000 milliseconds = 3 seconds
	
});




//Bhavesh(23-11-23) to add new row without milestone
$(document).on("click", '.withoutMilestone', function() {
	 addRow(1);	
	
});

//Bhavesh(23-11-23) Select all input fields with the class 'resettable' and reset their values
$('#reset').on('click', function() {
  
    $('.resettable').val('');
  });



function validationForsave(i){
	
	var validation = false;
	
	var PaymentScheduleId = $.trim($("input[id='PaymentScheduleId_" + (i) + "']").val());
	var numPaymentSequence = $.trim($("input[id='numPaymentSequence_" + (i) + "']").val());
    var dateValue = $.trim($("input[id='strPaymentDueDate_" + (i) + "']").val());
    var numAmount = $.trim($("input[id='numAmount_" + (i) + "']").val());
    var strPurpose = $("#strPurpose_" + (i)).val();
    var strRemarks = $("#strRemarks_" + (i)).val();
    var numMilestoneId = $("#numMilestoneId_" + (i)).val();
    var PaymentScheduleValid = $("#validField_" + (i)).val();
    var linkedWithMilestonE
   
   

    $('.strPaymentDueDate_'+ (i)).text("");
    $('.numAmount_'+ (i)).text("");
    $('.strPurpose_'+ (i)).text("");
    $('.strRemarks_'+ (i)).text("");
    $('.numMilestoneId_'+ (i)).text("");
    $('.numPaymentSequence_'+ (i)).text("");

   
    
    if(numPaymentSequence == ''){
    	$('.numPaymentSequence_'+ (i)).text("Payment Sequence Number is required and cannot be empty");
    	validation=true;
    }
   
    if(dateValue == ''){
    	$('.strPaymentDueDate_'+ (i)).text("Payment Due Date is required and cannot be empty");
    	validation=true;
    }
    if(numAmount == ''){
    	$('.numAmount_'+ (i)).text("Amount is required and cannot be empty");
    	validation=true;
    }
    if (!/^\d+(\.\d{1,2})?$/.test(numAmount)){
    	
    	$('.numAmount_'+ (i)).text("Amount must be a valid numeric value with up to two decimal places.");
    	validation=true;
    }
    if (parseFloat(numAmount) <= 0) {
    	$('.numAmount_'+ (i)).text("Amount should be greater than 0.");
    	validation=true;
      }

      // Check if cost is less than 20 characters
      if (numAmount.length > 20) {
    	  $('.numAmount_'+ (i)).text("Amount must be less than 20 characters.");
    	  validation=true;
      }
    if(strPurpose == ''){
    	$('.strPurpose_'+ (i)).text("Purpose is required and cannot be empty");
    	validation=true;
    }    
    
   
    return validation;
	
}



