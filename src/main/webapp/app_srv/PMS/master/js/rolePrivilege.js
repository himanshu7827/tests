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

});

$(document)
		.ready(
				function() {

					$('#reset').click(function() {
		
		resetForm();
	});

	 
	$('#form_role_privilege').bootstrapValidator({
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
											roleName : {
												group : '.col-md-12',
												validators : {
													notEmpty : {
														message : 'Role name is required and cannot be empty'
													},
													stringLength : {
														max : 100,
														message : 'The Document name must be less than 100 characters'
													},
													regexp : {
														regexp : nameRegex,
														message : nameErrorMessage
													}
												}
											},
										/*	lstBox2:{
												 validators: {
									                  callback: {
									                      message: 'Please select Privilege between 1 and 30',
									                      callback: function(value, validator) {
									                          // Get the selected options
									                    	  var options = validator.getFieldElements('lstBox2').val();
									  	                      return (options.length >= 1 && options.length<=30);
									                      }
									                  }
									              }
											}*/

										}
									});

					$('#save')
							.click(
									function() {									
										var bootstrapValidator = $("#form_role_privilege").data('bootstrapValidator');
										bootstrapValidator.validate();
										if (bootstrapValidator.isValid()) {
											
											if($('#lstBox2> option').length>0){
												$('#selectedPrivilege').val(($("select#lstBox2 option").map(function() {return $(this).val();}).get()));
												document.getElementById("save").disabled = true;
												$('#form_role_privilege')[0].submit();
												return true;
											}else{
												sweetSuccess('Error','Please Select atleast one Privilege');
												return false;
											}	
										

										} else {
											console.log('Not Validated');

										}

									});

				});

function resetForm() {
	$('#numId').val('0');
	$('#roleName').val('');
	$('#save').text('Save');
	$('#form_role_privilege').data('bootstrapValidator').resetForm(true);
}

$(document).on('change', '#roleId', function(e) {
	var roleId = $('#roleId').val();	
	var role = $("#roleId option:selected").text().trim();	
	$('#roleName').val(role);	
	if($('#lstBox2 option').length >0){
		$('#btnAllLeft').trigger("click");
	}	
	
	if(roleId != 0){
		
		$.ajaxRequest.ajax({
				type:"POST",
				url:"/PMS/getPrivilegeByRoleId",
				data:{roleId:roleId},
				success:function(response){
					console.log(response);
					console.log(typeof response);
					console.log(response.toString().split(','));					
					$('#lstBox1').val(response.toString().split(',')).attr('selected', true);
					if(response){
						$('#btnRight').trigger('click');
						$('#lstBox2').trigger('change');
					}					
				}			       
	        });
	}
	
});


$('#btnRight').click(function (e) {
    $('select').moveToListAndDelete('#lstBox1', '#lstBox2');
    $('#lstBox2').trigger('change');
    e.preventDefault();
});

$('#btnAllRight').click(function (e) {
    $('select').moveAllToListAndDelete('#lstBox1', '#lstBox2');
    $('#lstBox2').trigger('change');
    e.preventDefault();
});

$('#btnLeft').click(function (e) {
    $('select').moveToListAndDelete('#lstBox2', '#lstBox1');
    $('#lstBox2').trigger('change');
    e.preventDefault();
});

$('#btnAllLeft').click(function (e) {
    $('select').moveAllToListAndDelete('#lstBox2', '#lstBox1');
    $('#lstBox2').trigger('change');
    e.preventDefault();
});


