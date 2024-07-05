$(function() {
$('#exitByFla').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var input = button.data('whatever');
		var inputs = input.split('@#');
		$('#exitInterviewId').val(inputs[0]);
		$('#approvalId').val(inputs[1]);
		$('#statusValue').val(inputs[2]);

	});
});

$(function() {

	$('#submit').on('click', function(e) {
	    e.preventDefault();
	    $.ajax({
	        type: "POST",
	        url: "/PMS/saveFlaNdSlaRemarks",
	        data: $('form.exitInterviewModel').serialize(),
	        success: function(response) {
	            if(response.status == true){
				  	sweetSuccess('Attention','Record Saved Successfully');
	            	$("#exitByFla").modal('hide');
	            	
	            }
	            else{
				  	sweetSuccess('Attention','Record rejected');
	            	$("#exitByFla").modal('hide');

	            }
	        },
	        error: function() {
	            alert('Error');
	        }
	    });
	    return false;
	});
	});