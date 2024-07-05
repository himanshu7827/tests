var nameRegex='';
var nameErrorMessage='';
var amountRegex='';
var amountErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	amountRegex = messageResource.get('amount.regex', 'regexValidationforjs'); 
	amountErrorMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	});




$(document).ready(function() {
	
	$('.select2Option').select2({
		 width: '100%'
	})
	
		var table = $('#datatable').DataTable();
		table.destroy();		
		
		$("#dtExpenditureDate").datepicker({ 
			dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '+5y',
	    onSelect: function(date){
	    	$('#dtExpenditureDate').trigger('input');
	    }
				
	});
		
});



$(document).on('change','#numProjectId',function(e){
	var numProjectId = $('#numProjectId').val();
	if(numProjectId == 0){
	
		$('#datatable').addClass('hidden');	
	}else{
		$('#datatable').removeClass('hidden');
			$.ajax({
    	        type: "POST",
    	        url: "/PMS/mst/getProjectExpenditureDetailsByProjectId",
    	        data: "numProjectId=" + numProjectId,			
				success : function(data) {
					console.log(data);
					var tableData = '';
					for(var i =0;i<data.length;i++){
						var row = data[i];
						var recordStatus= '';
						if(row.valid==true){
							recordStatus='Active';
								}else{recordStatus='Inactive';
									}
						tableData+="<tr><td><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" />"+row.numId+"</td> row.numId" +
						"<td>"+row.strProjectName+"</td>  <td>"+row.strBudgetHeadName+"</td> <td>"+row.strExpenditureHeadName+"</td> <td>"+row.dtExpenditureDate+"</td> <td>"+row.numExpenditureAmount+"</td> <td>"+row.strExpenditureDescription+"</td>"+
						"<td class='hidden'>"+row.numProjectId+"</td><td class='hidden'>"+row.numBudgetHeadId+"</td> <td class='hidden'>"+row.numExpenditureHeadId+"</td>"+
						"<td>"+recordStatus+"</td> <td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td> </tr>"
						
			}
					tableData+='</tbody>';
					$('#datatable tbody').empty();						
					$('#datatable').append(tableData);		
					
				},				
				error : function(e) {
					//$('#RecurringManpowerAmt').val(0);
					alert('Error: ' + e);
			 	}
		 });
		 
		
	 	}
	
	});


	$(document).on('click','#edit',function(e){
		 $('#form1').data('bootstrapValidator').resetForm(true);
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#numProjectId').val(resultArray[7].trim()).trigger('input');
		$('#numBudgetHeadId').val(resultArray[8].trim()).trigger('change');
		$('#numExpenditureHeadId').val(resultArray[9].trim()).trigger('change');
		$('#dtExpenditureDate').val(resultArray[4].trim()).trigger('input');
		$('#numExpenditureAmount').val(resultArray[5].trim()).trigger('input');
		$('#strExpenditureDescription').val(resultArray[6].trim()).trigger('input');
		
		if(resultArray[10].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		
		
		$('#save').text('Update');
	});
	
	
	

	

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
	    	  numProjectId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numProjectId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },numBudgetHeadId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose BudgetHead Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numBudgetHeadId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },numExpenditureHeadId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose ExpenditureHead Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numExpenditureHeadId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          numExpenditureAmount: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Expenditure Amount is required and cannot be empty'
	                  },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },
	          dtExpenditureDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Expenditure Date is required and cannot be empty'
	                  },
	                 
	              }
	          },
	          
	          strExpenditureDescription: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Expenditure Description is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2000,
	                        message: 'The Expenditure Description must be less than 2000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          }
	          
	      
	         
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
					document.getElementById("save").disabled = true; 
					/*document.form1.submit();*/
					 $( "#form1")[0].submit();
					return true;
				
			}else{
				console.log('Not Validated');
				 //return false;
			}
	   	 
	});
		
});






function resetForm(){
	$('#numId').val('0');
	$('#numProjectId').val('0').trigger('change');
	$('#numBudgetHeadId').val("0").trigger('change');
	$('#numExpenditureHeadId').val("0").trigger('change');
	$('#dtExpenditureDate').val('');
	$('#numExpenditureAmount').val('');
	$('#strExpenditureDescription').val('');
	$('#save').text('Save');
}


