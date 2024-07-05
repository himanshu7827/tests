$(document).on('click','#projectId',function(e){	
	var projectId = $('#projectId').val();
	var tableData = "";
	
		$.ajax({
	        type: "POST",
	        url: "/PMS/mst/getProjectClientContactPersonDetails",
	        data: "projectId=" + projectId,			
			success : function(response) {		
				console.log(response);
				$('#contactPersonId').empty();
				$("#clientId").val(response.clientId);
				$('#clientName').text(response.clientName);				
					for ( var i = 0; i < response.contactPersonList.length; ++i) {
					
							$('#contactPersonId').append(
									$('<option/>').attr("value", response.contactPersonList[i].numId)
											.text(response.contactPersonList[i].strContactPersonName));					
					}	
					$('#contactPersonId').val(response.contactPersonId).trigger('change');
					
			},				
			error : function(e) {
				$('#RecurringManpowerAmt').val(0);
				alert('Error: ' + e);	
			}
		});	
});



$('#save').click(function() {
	
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



$(document).ready(function(){
	
	$('.select2Option').select2({
		width : '100%'
	});
	
	
$('#form1').bootstrapValidator({
	excluded: ':disabled',
      message: 'This value is not valid',
      feedbackIcons: {
          valid: 'glyphicon glyphicon-ok',
          invalid: 'glyphicon glyphicon-remove',
          validating: 'glyphicon glyphicon-refresh'
      },
      fields: {
          projectId:{
        	  validators: {
                  callback: {
                      message: 'Please choose Project Name',
                      callback: function(value, validator) {
                          // Get the selected options
                          var options = validator.getFieldElements('projectId').val();
                          console.log("option--"+options)
                          return (options != 0);
                      }
                  }
              }
          },
          contactPersonId:{
        	  validators: {
                  callback: {
                      message: 'Please choose Contact Person ',
                      callback: function(value, validator) {
                          // Get the selected options
                          var options = validator.getFieldElements('contactPersonId').val();
                          return (options != 0);
                      }
                  }
              }
          },
        
          
      
         
      }
  });
});
