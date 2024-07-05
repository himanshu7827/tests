var nameRegex = '';
var nameErrorMessage = '';
messageResource.init({
	filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function() {
	// get value corresponding to a key from regexValidationforjs.properties
	nameRegex = messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message','regexValidationforjs');
});

$(document).ready(function() {
	$('.select2Option').select2({
		width : '100%'
	});
	$('#example').DataTable();
});

$(document)
		.ready(
				function() {

					$('#reset').click(function() {
		
		resetForm();
	});

	 
	$('#form1').bootstrapValidator({
																		// live:
																		// 'disabled',
										excluded : ':disabled',
										message : 'This value is not valid',
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											privlegeName : {
												group : '.col-md-12',
												validators : {
													notEmpty : {
														message : 'Privilege name is required and cannot be empty'
													},
													stringLength : {
														max : 100,
														message : 'Privilege name must be less than 100 characters'
													},
													regexp : {
														regexp : nameRegex,
														message : nameErrorMessage
													}
												}
											}

										}
									});

					$('#save')
							.click(
									function() {									
										var bootstrapValidator = $("#form1").data('bootstrapValidator');
										bootstrapValidator.validate();
										if (bootstrapValidator.isValid()) {		
											$( "#form1")[0].submit();
										} else {
											console.log('Not Validated');

										}

									});

				});

function resetForm() {
	$('#privlegeId').val('0');
	$('#privlegeName').val('');
	$('#save').text('Save');
	$('#form1').data('bootstrapValidator').resetForm(true);
}

$(document).on('click','#edit',function(e){
	var resultArray = $(this).closest('tr').find('td').map( function()
			{
			return $(this).text();
			}).get();
	console.log(resultArray);
	
	$('#privlegeId').val(resultArray[0].trim());
	
	$('#privlegeName').val(resultArray[1].trim()).trigger('input');;
	
	$('#save').text('Update');
	
});

