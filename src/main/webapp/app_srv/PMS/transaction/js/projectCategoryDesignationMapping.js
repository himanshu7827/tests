var amountRegex='';
var amountRegexMessage = '';
messageResource.init({
	filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function() {	
	amountRegex= messageResource.get('amount.regex','regexValidationforjs');
	amountRegexMessage = messageResource.get('amount.regex.message','regexValidationforjs');
});

$(document).ready(function() {
	$('.select2Option').select2({
		width : '100%'
	});
	
	//Datepicker added by devesh on 30-04-24
	$("#fromDate").datepicker({
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        onClose: function(selectedDate) {
            $("#toDate").datepicker("option", "minDate", selectedDate);
            $('#form1').bootstrapValidator('revalidateField', 'fromDate');
        }
    });
    $("#toDate").datepicker({
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        onClose: function(selectedDate) {
            $("#fromDate").datepicker("option", "maxDate", selectedDate);
        }
    });
	//End of datepicker 

});

$(document)
		.ready(
				function() {

					$('#reset').click(function() {
		
		resetForm();
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
											cost: {
									              
									              validators: {
									                  notEmpty: {
									                      message: 'Cost is required and cannot be empty'
									                  },
									                  stringLength: {
									                        max: 100,
									                        message: 'Cost must be less than 20 chars'
									                    },
									                    regexp: {
									                        regexp: amountRegex,
									                       message: amountRegexMessage
									                    }
									              }
									          },
									          designationId:{
									        	  group: '.col-md-12',
									        	  validators: {
									                  callback: {
									                      message: 'Please choose Designation',
									                      callback: function(value, validator) {									                          
									                          var options = validator.getFieldElements('designationId').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          }, projectCategoryId:{									        	  
									        	  validators: {
									                  callback: {
									                      message: 'Please choose Project category',
									                      callback: function(value, validator) {									                         
									                          var options = validator.getFieldElements('projectCategoryId').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          },
									          //Validations added by devesh on 30-04-24 for new fields
									           numDeputedAt:{
									        	  group: '.col-md-12',
									        	  validators: {
									                  callback: {
									                      message: 'Please select Deputation',
									                      callback: function(value, validator) {
									                          // Get the selected options
									                          var options = validator.getFieldElements('numDeputedAt').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          },fromDate: {
									              group: '.col-md-12',
									              validators: {
									                  notEmpty: {
									                      message: 'From Date is required and cannot be empty'
									                  },    
									              }
									          }
											//End of new validations

										}
									});

					$('#save')
							.click(
									function() {									
										var bootstrapValidator = $("#form1").data('bootstrapValidator');
										bootstrapValidator.validate();
										if (bootstrapValidator.isValid()) {
											var categoryId = $('#projectCategoryId').val();
											var designationId = $('#designationId').val();
											// new variables added by devesh on 01-05-24 to get value of deputation, fromDate and toDate
											var numDeputedAt = $('#numDeputedAt').val();
											var fromDate = $('#fromDate').val();
											var toDate = $('#toDate').val();
											// End of new variables
											var numId= $('#numId').val();
											var cost = $('#cost').val().trim();
											var isDateRangeNew = extractDateDetails();
											if(isDateRangeNew){
												$.ajax({
													type : "POST",
													url : "/PMS/projectCategorydesignationMapping",
													data : {"projectCategoryId" : categoryId,
														"designationId":designationId,
														// new parameters added by devesh on 01-05-24 to get value of deputation, fromDate and toDate
														"numDeputedAt":numDeputedAt,
														"fromDate":fromDate,
														"toDate":toDate,
														// End of new variables
														"numId":numId,
														"cost":cost
														},												
													success : function(response) {
														console.log(response);
														resetForm();
														if(response == 'save'){
															
															sweetSuccess('Attention','Data saved Successfully');
														}else if(response == 'update'){
															sweetSuccess('Attention','Data updated Successfully');
														}else if(response == 'duplicate'){
															sweetSuccess('Attention','Data updated Successfully');
														}else{
															sweetSuccess('Attention','Something went wrong');
														}	
													}
												})
											}else{
												swal({
												  title: "Attention",
												  text: "Base cost will be modified for this date range. Do you want to proceed?",
												  type: 'success',						  
											      buttons: [					               
											                'No',
											                'Yes'
											              ],	     
											    }).then(function(isConfirm) {
											      if (isConfirm) {
											    	$.ajax({
														type : "POST",
														url : "/PMS/projectCategorydesignationMapping",
														data : {"projectCategoryId" : categoryId,
															"designationId":designationId,
															// new parameters added by devesh on 01-05-24 to get value of deputation, fromDate and toDate
															"numDeputedAt":numDeputedAt,
															"fromDate":fromDate,
															"toDate":toDate,
															// End of new variables
															"numId":numId,
															"cost":cost
															},												
														success : function(response) {
															console.log(response);
															resetForm();
															if(response == 'save'){
																
																sweetSuccess('Attention','Data saved Successfully');
															}else if(response == 'update'){
																sweetSuccess('Attention','Data updated Successfully');
															}else if(response == 'duplicate'){
																sweetSuccess('Attention','Data updated Successfully');
															}else{
																sweetSuccess('Attention','Something went wrong');
															}	
														}
													})
											      } 
											    });
											}
											
											
										} else {
											console.log('Not Validated');

										}

									});

				});

function resetForm() {
	//$('#baseCostDetails tbody').empty();
	var table = $('#baseCostDetails').DataTable();
    table.clear().draw();
    $("#baseCostDetails th:nth-child(4), #baseCostDetails td:nth-child(4)").hide();
    setColumnWidths(["8%", "24%", "27%", "0%", "15%", "13%", "13%"]);
	$('#numId').val('0');
	$('#cost').val('0').trigger('change');
	$('#projectCategoryId').val('0').trigger('change');
	$('#designationId').val('0').trigger('change');
	// reset for deputation, fromDate and toDate added by devesh on 01-05-24
	$('#numDeputedAt').val('0').trigger('change');
	$('#fromDate').val('').trigger('change');
	$('#toDate').val('').trigger('change');
	$("#toDate").datepicker("option", "minDate", null);
	$("#fromDate").datepicker("option", "maxDate", null);
	//End of code
	$('#form1').data('bootstrapValidator').resetForm(true);
}

//Revalidate cost field on change added by devesh  on 01-05-24
$("#cost").on('change', function() {
    $('#form1').bootstrapValidator('revalidateField', 'cost');
});
//End of event handler

//Hide/Unhide Deputed At field on category change added by devesh  on 02-05-24
$("#projectCategoryId").on('change', function() {
    if($("#projectCategoryId").val()==8){
		$("#deputedAtDiv").show();
		$('#form1').bootstrapValidator('enableFieldValidators', 'numDeputedAt',true);
	}
	else{
		$("#deputedAtDiv").hide();
		$("#numDeputedAt").val(0);
		$('#form1').bootstrapValidator('enableFieldValidators', 'numDeputedAt',false);
	}
});
//End of function

$(document).ready(function() {
	$('#baseCostDetails').DataTable();
	$("#baseCostDetails th:nth-child(4), #baseCostDetails td:nth-child(4)").hide();
	$('.data').change(function(){
		var projectCategoryId = $('#projectCategoryId').val();
		var designationId = $('#designationId').val();
		var numDeputedAt = $('#numDeputedAt').val(); //new variable added by devesh on 30-04-24 to get value of deputation
		/*if(projectCategoryId != 0 && designationId != 0){
			loadData(projectCategoryId,designationId);
		}*/
		if(projectCategoryId != 0 && designationId != 0){ // Condition modified by devesh on 30-04-24 to include deputed value
			loadData(projectCategoryId,designationId,numDeputedAt);
			loadDetails(projectCategoryId,designationId); //Added by devesh on 06-05-24 to get baseCost details
		}else{
			$('#numId').val(0);
			$('#cost').val(0).trigger('change'); // Added trigger change by devesh on 01-05-24
		}
	});
});

function loadData(categoryId, designationId, numDeputedAt){ // Function modified by devesh on 30-04-24 to add deputed parameter
	$.ajax({
		type : "POST",
		url : "/PMS/categoryDesignationCost",
		/*data : {"projectCategoryId" : categoryId,"designationId":designationId},*/
		data : {"projectCategoryId" : categoryId,"designationId":designationId,"numDeputedAt":numDeputedAt}, // Data modified by devesh on 30-04-24 to add deputed value
		success : function(response) {
			//console.log(response);
				if(response.hasOwnProperty("numId")){
					$('#numId').val(response.numId);
					if(response.cost){
						$('#cost').val(response.cost).trigger('change'); // Added trigger change by devesh on 01-05-24
					}else{
						$('#cost').val(0).trigger('change'); // Added trigger change by devesh on 01-05-24
					}
					// Added fromDate by devesh on 01-05-24
					if(response.fromDate){
						$('#fromDate').val(response.fromDate).trigger('change'); // Added trigger change by devesh on 01-05-24
					}else{
						$('#fromDate').val('').trigger('change'); // Added trigger change by devesh on 01-05-24
					}
					//End of fromDate
					// Added toDate by devesh on 01-05-24
					if(response.toDate){
						$('#toDate').val(response.toDate).trigger('change'); 
					}else{
						$('#toDate').val('').trigger('change');
					}
					//End of ToDate
					
				}else{
					sweetSuccess('Error','Something went wrong');
				}
			}
		});
}

//Function added by devesh on 06-05-24 to get base cost details
function loadDetails(categoryId, designationId){ // Function modified by devesh on 30-04-24 to add deputed parameter
	$.ajax({
		type : "POST",
		url : "/PMS/getBaseCostDetails",
		data : {"projectCategoryId" : categoryId,"designationId":designationId},
		success : function(data) {
				//console.log(data);
				//$('#baseCostDetails tbody').empty();
            	$('#baseCostDetails').DataTable().clear().destroy();
	            // Append new rows
	            data.forEach(function(item, index) {
					//console.log(item);
	                $('#baseCostDetails tbody').append(
	                    '<tr>' +
	                        '<td>' + (index + 1) + '</td>' +
	                        '<td>' + item.designationName + '</td>' +
	                        '<td>' + item.categoryName + '</td>' +
	                        '<td>' + (item.numDeputedAt == 1 ? 'CDAC' : item.numDeputedAt == 2 ? 'Client' : '') + '</td>' +
	                        '<td>' + item.cost + '</td>' +
	                        '<td>' + (item.fromDate ? item.fromDate : '') + '</td>' +
	                        '<td>' + (item.toDate ? item.toDate : '') + '</td>' +
	                    '</tr>'
	                );
	            });
	            
	            $('#baseCostDetails').DataTable();
	            var columnWidthsWithDeputed = ["7%", "20%", "25%", "11%", "13%", "12%", "12%"];
	            var columnWidthsWithoutDeputed = ["8%", "24%", "27%", "0%", "15%", "13%", "13%"];
	            var $col = $("#baseCostDetails th:nth-child(4), #baseCostDetails td:nth-child(4)");
	            if (categoryId == 8){
					$col.show();
					setColumnWidths(columnWidthsWithDeputed);
				}
				else{
					$col.hide();
					setColumnWidths(columnWidthsWithoutDeputed);
				}
			}
		});
}
//End of function

function setColumnWidths(columnWidths) {
    $("#baseCostDetails th").each(function(index) {
        if ($(this).is(":visible")) {
            $(this).css('width', columnWidths[index]);
        }
    });
    $("#baseCostDetails td").each(function(index) {
        var columnIndex = $(this).index() + 1; // Get column index +1 because nth-child is 1-based
        var header = $("#baseCostDetails th:nth-child(" + columnIndex + ")");
        if (header.is(":visible")) {
            $(this).css('width', columnWidths[columnIndex - 1]); // Apply width from the header
        }
    });
}

function extractDateDetails() {
	
	function parseDate(dateStr) {
        if (!dateStr) return null;
        const parts = dateStr.split('/');
        // Note: months are 0-based in JavaScript Date
        return new Date(parts[2], parts[1] - 1, parts[0]);
    }
	
    // Fetch values from input fields
    const numDeputedAt = parseInt(document.getElementById("numDeputedAt").value, 10);
    const inputFromDate = parseDate(document.getElementById("fromDate").value);
    let inputToDate = parseDate(document.getElementById("toDate").value);
    if (inputToDate == null) inputToDate = "";
    console.log(inputFromDate);
    console.log(inputToDate);

    // Select the table body
    const tableBody = document.querySelector("#baseCostDetails tbody");
    
    // Check if the table is truly empty (checking for placeholder text in the first row)
    if (tableBody.rows.length === 1 && tableBody.rows[0].cells[0].textContent.trim() === "No data available in table") {
        console.log("The table is empty.");
        return true;
    }

    // Initialize an array to hold the date details from each row
    const dateDetails = [];

    // Iterate over each row in the table body
    for (let i = 0; i < tableBody.rows.length; i++) {
        const row = tableBody.rows[i];

        // Extract the 'deputedAt' text and trim any whitespace
        const deputedAtText = row.cells[3].textContent.trim();

        // Determine the value based on the 'deputedAt' text using a ternary operator
        const deputedAtValue = deputedAtText === "CDAC" ? 1 : (deputedAtText === "Client" ? 2 : 0);

        // Assuming the columns for 'From Date' and 'To Date' are 5th and 6th respectively (0-based index)
        const fromDate = parseDate(row.cells[5].textContent.trim());
        let toDate = parseDate(row.cells[6].textContent.trim());
        if (toDate == null) toDate = "";

        // Check if numDeputedAt matches deputedAtValue
        if (numDeputedAt == deputedAtValue) {
            if (inputToDate == "") {  // if user input toDate is empty
                if (toDate == "") {  // if table toDate is also empty
                    if (inputFromDate.getTime() == fromDate.getTime()) {
                        dateDetails.push({ deputedAt: deputedAtValue, fromDate, toDate });
                        return false; // Overlap found
                    }
                } else {  // if table toDate is not empty
                    if (inputFromDate >= fromDate && inputFromDate <= toDate) {
                        dateDetails.push({ deputedAt: deputedAtValue, fromDate, toDate });
                        return false; // Overlap found
                    }
                }
            } else {  // if user input toDate is not empty
                if (toDate == "") {  // if table toDate is empty
                    if (inputFromDate <= fromDate && inputToDate >= fromDate) {
                        dateDetails.push({ deputedAt: deputedAtValue, fromDate, toDate });
                        return false; // Overlap found
                    }
                } else {  // if table toDate is not empty
                    if (inputFromDate <= toDate && inputToDate >= fromDate) {
                        dateDetails.push({ deputedAt: deputedAtValue, fromDate, toDate });
                        return false; // Overlap found
                    }
                }
            }
        }
    }

    // Output the result
    //console.log(dateDetails);
    return true;
}




