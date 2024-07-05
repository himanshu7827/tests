$(document).ready(function() {
	$('.select2Option').select2({width:"element"});
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		 
		 var table = $('#datatable').DataTable();
			table.destroy();
 
	
			
			   
			
			 	  });

$(document).ready(function() {
	var projectstart = $('#projectStartDate').val().trim();	
	//console.log(projectstart);
	var startDateParts = projectstart.split("/");
	var projectStartDate = new Date(startDateParts[2], startDateParts[1] - 1, +startDateParts[0]);

	var projectEnd = $('#projectEndDate').val().trim();	
	//console.log(projectEnd);
	var endDateParts = projectEnd.split("/");
	var projectEndDate = new Date(endDateParts[2], endDateParts[1] - 1, +endDateParts[0]);
	
	 
/* var element = document.getElementById('reviDat');
 console.log(element.value);*/
		  
	
	
	
		/*Showing the alert on same date */
	
	$('#reviewDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    minDate:projectStartDate,
		    maxDate:new Date(),
		    /*beforeShowDay: function(date) {
		        var dateString = $.datepicker.formatDate('dd/mm/yy', date);*/
		        //console.log("element.value");
		        // Array of specific dates to hide
		        
		       /* var hiddenDates = [];
		        
		        // Check if element.value is not null
		        if (element.value !== null && element.value !== null) {
		            hiddenDates.push(element.value);
		        }

		        // Check if the date should be hidden
		        if (hiddenDates.includes(dateString)) {
		            return [false];
		        }

		        return [true];
		    }*/
		       
		           /* var hiddenDates = [element.value];

		            // Check if the date should be hidden
		            if (hiddenDates.includes(dateString)) {
		                return [false];
		            }

		            return [true];
		        
		    }*/
		    onSelect: function(dateText, inst) {
		    	$('#reviewDate').trigger('input');  //trigger the input while select the date [05-04-2024]
		        var elements = document.getElementsByClassName('revtest');
		        // Loop through the elements
		        for (var i = 0; i < elements.length; i++) {
		            var element = elements[i];
		            var elementDate = element.textContent;
		            if (elementDate === dateText) {
		                swal(" Review already completed on this date" + dateText);
		                $('#reviewDate').val('');
		                return;
		            }
		        }
		    }
		    
		        
		    });
	
	
	   
	$('#completionDate').datepicker({
		
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    minDate:projectStartDate,
		    maxDate:projectEndDate,
		   onSelect: function(date){
		    	var x=$('#completionDate').trigger('input');
		    	var y=new Date();		    	
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]); 
		        //console.log(selectedDate);
		        if(selectedDate.getTime() > y.getTime()){
		        	$('#completionStatus').hide();
		        	$('input[name="status"]').prop('checked', false); //un-checked the completed radio button [05-04-2024
		        }
		        else{
		        	$('#completionStatus').show();
	
		        }
		        
		      
		    }
	   });	
})


	/*$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#reviewDate').val(resultArray[1].trim());	
		$('#completionDate').val(resultArray[2].trim());	
		if(resultArray[2].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
	});*/

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
	    	  reviewDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Review Date is required and cannot be empty'
	                  }
	                  }
	          },
	          completionDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Completion Date is required and cannot be empty'
	                  }
	                  }
	          }, 
	          
	          strRemarks: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Remarks is required and cannot be empty'
	                  }
	                  }
	          }, 
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
	 	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
		/*----- Attention after validate the fields [05-04-2024] ---*/
		if(bootstrapValidator.isValid()){
			swal({
				  title: "Attention",
				  text: "Once saved,you would not able to modify the record. Do you want to save?",
				  type: 'success',						  
			      buttons: [					               
			                'No',
			                'Yes'
			              ],	     
			    }).then(function(isConfirm) {
			      if (isConfirm) {
					document.getElementById("save").disabled = true; 
					$('#form1')[0].submit();
					return true;
			      } 
			    });
		}
		/*----- End of Attention after validate the fields [05-04-2024] ---*/
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#reviewDate').val('');
	$('#completionDate').val('');
	$('#strRemarks').val('');
	$('#save').text('Save');
	/*---- Reset the Completed Status radio button [05-04-2024] --*/
	$('#completionStatus').show();
	$('input[name="status"]').prop('checked', false);
}

/*---------- Mandatory Review Remarks According to Completed Status [05-04-2024]---*/
$(document).ready(function() {
    function toggleRemarksField() {
        var isChecked = $('input[name="status"]').prop('checked');
        var remarksField = $('[name="strRemarks"]');
        var bootstrapValidator = $('#form1').data('bootstrapValidator');

        if (isChecked) {
        	$('#reviewRemarks').addClass('hidden');
            bootstrapValidator.enableFieldValidators('strRemarks', false);
        } else {
        	$('#reviewRemarks').removeClass('hidden');
            bootstrapValidator.enableFieldValidators('strRemarks', true);
        }
    }

    toggleRemarksField();

    $('input[name="status"]').change(function() {
        toggleRemarksField();
    });
});
/*---------- End of Mandatory Review Remarks According to Completed Status [05-04-2024]---*/