/**
 * 
 */

//To check empty field validation by Himanshu on 10-05-2024
$(document).ready(function(){
		$('#toggle-off').removeAttr('Checked');
	$('#toggle-on').attr('checked',true);
		
	$('.select2Option').select2({
		width: '100%'
    });
	$('#form1').bootstrapValidator({
		excluded : ':disabled',
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			taskId:{
	        	  validators: {
	                  callback: {
	                      message: 'Please select Task ',
	                      callback: function(value, validator) {
	                          var options = validator.getFieldElements('taskId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          subtaskId:{
	        	  validators: {
	                  callback: {
	                      message: 'Please select Sub Task ',
	                      callback: function(value, validator) {
	                          var options = validator.getFieldElements('subtaskId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	          subtaskDescription:{
	        	  validators: {
	                  callback: {
	                      message: 'Please enter Description ',
	                      callback: function(value, validator) {
	                          var options = validator.getFieldElements('subtaskDescription').val();
	                          return (options != 0);
	                      }
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
		    if(bootstrapValidator.isValid()){
		    	document.getElementById("form1").submit();
				return true;
			}else{
				console.log('Not Validated');
			}
		});		
		
		$('#test').click(function(){
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
		    if(bootstrapValidator.isValid()){
		    	document.getElementById("form1").submit();
				return true;
			}else{
				console.log('Not Validated');
			}
		});		
	});







//Added to get subtask list regarding a task by Himanshu on 10-05-2024
 function getSubTaskBySelectTask() {
    var taskId = $('#taskId').val();
    $('#subtaskId').val(0).trigger('change')
    $('#subtaskId').find('option').remove().end();
    $.ajax({
        type: "POST",
        url: "/PMS/mst/getSubTaskByTaskId",
        data: {
            "taskId": taskId
        },
        success: function(response) {
			console.log("response:"+response);
            $('#subtaskId').append(
        			$('<option/>').attr("value", 0).text("-- Select Sub-Task Name --"));
            if (response && response.length > 0) {
                for (var i = 0; i < response.length; ++i) {
                    $('#subtaskId').append(
                        $('<option/>')
                            .attr("value", response[i].taskId)
                            .text(response[i].taskName)
                    );
                }
                
                var test=$('#subtaskId_hidden').val();
                if(test!=0 && test!='0'){
						$('#subtaskId').val($('#subtaskId_hidden').val()).trigger('change');
				}else{
					$('#subtaskId').val('0').trigger('change');
				}
            }
        },
        error: function(xhr, status, error) {
            $('#subtaskId').append($('<option/>').attr("value", -1).text("Error fetching sub-tasks"));
        }
    });
}

$(document).ready(function(){
	$('#example').DataTable();

	$('#resetMaster').click(function(){
		resetFormTimesheetMaster();
	});
	});


//To edit the Description details by Himanshu on 10-05-2024
$(document).on('click','#edit_timesheet',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('.taskId_freeze').removeClass("hidden");
		$('.subtaskId_hid').addClass("hidden");
		$('#taskId').val(resultArray[1].trim()).trigger('change');;

		$('#subtaskId_hidden').val(resultArray[2].trim()).trigger('change');
		$('#descId').val(resultArray[3].trim());
		$('#subtaskName').val(resultArray[5].trim());
		$('#taskName').val(resultArray[4].trim());
		
		$('#subtaskName').prop('readonly', true);
		$('#taskName').prop('readonly', true);
		
		$('#subtaskDescription').val(resultArray[6].trim());
		
		
		if(resultArray[7].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
			
		$('#save').text('Update');
	});
	
	
//To reset the field details by Himanshu on 10-05-2024 
	function resetFormTimesheetMaster(){	
		$('.subtaskId_hid').removeClass("hidden");
		$('.taskId_freeze').addClass("hidden");
	$('#subtaskDescription').val('');
	$('#taskId').val('0').trigger('change');
	
	$('#descId').val('0');
		$('#subtaskId_hidden').val('0');
	$("#toggle-off").attr("checked", false);
	$("#toggle-on").attr("checked", true);
	$('#save').text('Save');
}