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
	
		var table = $('#datatable').DataTable();
		table.destroy();		
			
		$('#addnewrecord').addClass('hidden');
		
		/*$('#dtInvoice').datepicker({
			  
		    autoclose: true
		})*/
		
		$("#dtInvoice").datepicker({ 
			dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '+5y',
	    onSelect: function(date){
	    	$('#dtInvoice').trigger('input');
	    }
				
	});

		});



$(document).on('click','#addnewrecord',function(e){
	
	$('#addnewrecord').addClass('hidden');});





$('.select2Option').select2({
	 width: '100%'
});
$(document).on('keyup','#numInvoiceAmt',function(e){
	calculateTaxAmount();
	calculateTotalAmount();
});

$(document).on('keyup','#numTaxAmount',function(e){
	
	calculateTotalAmount();
});

$('#numTaxComponent').change(function(){

		if ($('#numTaxComponent').val() != 0){
		$('#numTaxAmount').attr("readonly", true); 
		}
		else{
		$('#numTaxAmount').attr("readonly", false); 
		}
    calculateTaxAmount();
	
	
	
});
function calculateTaxAmount(){

var numTaxComponent = $('#numTaxComponent').val();
	var numInvoiceAmt = $('#numInvoiceAmt').val();
	if(numInvoiceAmt && !isNaN(numInvoiceAmt) && numTaxComponent && !isNaN(numTaxComponent)){
	var calculatedTax = (numInvoiceAmt * numTaxComponent)/ 100;
	$('#numTaxAmount').attr("value",calculatedTax);
 	calculateTotalAmountNew(calculatedTax);
	}
	else{
	$('#numTaxAmount').attr("value",0);
	
	$('#numInvoiceTotalAmt').attr("value",0);
	
	}
}


	$(document).on('click','#edit',function(e){
		
		$('#frm').removeClass('hidden');
		$('#addnewrecord').addClass('hidden');
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		
		$('#numId').val(resultArray[0].trim());
		var  scheduleid=resultArray[1].trim().split(',');
		$("#scheduledPaymentID").val(scheduleid).change();
		/*$('#scheduledPaymentID').val(resultArray[1].trim()).trigger('change');*/
		$('#strInvoiceRefno').val(resultArray[2].trim()).trigger('input');
		$('#dtInvoice').val(resultArray[3].trim()).trigger('input');
		$('#numInvoiceAmt').val(resultArray[4].trim()).trigger('input');
		$('#numTaxAmount').val(resultArray[5].trim()).trigger('input');
		$('#numInvoiceTotalAmt').val(resultArray[6].trim());
		$('#strInvoiceType').val(resultArray[7].trim()).trigger('change');
		$('#strTaxComponent').val(resultArray[8].trim()).trigger('change');
		/*$('#strInvoiceStatus').val(resultArray[8].trim()).trigger('change');*/
	
		if(resultArray[8].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
		
	});
	

	
	

	
	
	
	$('#new_record').click(function(){	
		
		$('#frm').removeClass('hidden');
	
	});



	$(document).on('change','#projectId',function(e){	

		setproject();
		
	});
	

	function getScheduledPayment(){
		$('#scheduledPaymentID').find('option').not(':first').remove();
		var projectId = $('#projectId').val();
		$.ajax({
			type : "POST",
			url : "/PMS/mst/getScheduledPayment",
			 data: "projectId=" + projectId,	
			success : function(response) {
				console.log(response);
				var seloption = "";
					for (var i = 0; i < response.length; i++) {
						seloption += "<option  value="+response[i].numId+">"
							+ response[i].strPurpose +" </option>";
				}	        
					
					$('#scheduledPaymentID').append(seloption);
					//$("#empId").trigger("change"); 	
			},
			error : function(e) {
				alert('Error: ' + e);			
			}
		});	
		}

	

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
	    	  projectId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('projectId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          dtInvoice:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Invoice Date is required and cannot be left empty'
	                    }
	                }
	          },
	    	  strInvoiceRefno: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Invoice Reference Number is required and cannot be empty'
	                  }, stringLength: {
	                        max: 50,
	                        message: 'The Invoice Reference Number must be less than 50 characters'
	                    }
	                    
	              }
	          },numInvoiceAmt: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Invoice Amount is required and cannot be empty'
	                  }, stringLength: {
	                        max: 20,
	                        message: 'Invoice Amount must be less than 20 digits'
	                    },
	                 
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },numTaxAmount: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Tax Amount is required and cannot be empty'
	                  },stringLength: {
	                        max: 20,
	                        message: 'Tax Amount must be less than 20 digits'
	                    },
	                 
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
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
	
	$('#projectId').val('0').trigger('change');
	$('#scheduledPaymentID').find('option').not(':first').remove();
	$('#scheduledPaymentID').val('0').trigger('change');
	$('#strInvoiceRefno').val('');
	$('#dtInvoice').val('');
	$('#numInvoiceAmt').val(0);
	$('#numTaxAmount').val(0);
	$('#numInvoiceTotalAmt').val(0);
	$('#strInvoiceStatus').val('0').trigger('change');
	$('#strInvoiceType').val('0').trigger('change');
	$('#strTaxComponent').val('0').trigger('change');
	
	$('#numId').val(0);
	
	$('#save').text('Save');
}

function calculateTotalAmount(){
	
	var numInvoiceAmt = $('#numInvoiceAmt').val();
	var numTaxAmount = $('#numTaxAmount').val();
	var numTaxComponent = $('#numTaxComponent').val();
	if(numInvoiceAmt && !isNaN(numInvoiceAmt) && numTaxAmount && !isNaN(numTaxAmount)){
		//console.log('1');
		/*$('#numInvoiceTotalAmt').val(parseFloat(numInvoiceAmt) + parseFloat(numTaxAmount));*/
		//Added by devesh on 25/8/23 to correct decimal error
		$('#numInvoiceTotalAmt').val((parseFloat(numInvoiceAmt) + parseFloat(numTaxAmount)).toFixed(2));
	}
	else if(numInvoiceAmt && !isNaN(numInvoiceAmt) && numTaxComponent && !isNaN(numTaxComponent)){
		//console.log('1');
		$('#numInvoiceTotalAmt').val(parseFloat(numInvoiceAmt) + parseFloat(numTaxComponent));
	}
	else if(numInvoiceAmt && !isNaN(numInvoiceAmt)){
		//console.log('2');
		$('#numInvoiceTotalAmt').val(parseFloat(numInvoiceAmt));
	}else if(numTaxAmount && !isNaN(numTaxAmount)){
		//console.log('3');
		$('#numInvoiceTotalAmt').val(parseFloat(numTaxAmount));
	}
	else{
	$('#numTaxAmount').attr("value",0);
	
	$('#numInvoiceTotalAmt').attr("value",0);
	
	}
	
}
 function calculateTotalAmountNew(calculatedTax){
 	var numInvoiceAmt = $('#numInvoiceAmt').val();
 	var calculatedTax = calculatedTax;
 	if(numInvoiceAmt && !isNaN(numInvoiceAmt) && calculatedTax && !isNaN(calculatedTax)){
		//console.log('1');
		/*$('#numInvoiceTotalAmt').val(parseFloat(numInvoiceAmt) + parseFloat(calculatedTax));*/
 		//Added by devesh on 25/8/23 to correct decimal error
 		$('#numInvoiceTotalAmt').val((parseFloat(numInvoiceAmt) + parseFloat(calculatedTax)).toFixed(2));
	}
	else if(numInvoiceAmt && !isNaN(numInvoiceAmt)){
		//console.log('2');
		$('#numInvoiceTotalAmt').val(parseFloat(numInvoiceAmt));
	}else if(numTaxAmount && !isNaN(numTaxAmount)){
		//console.log('3');
		$('#numInvoiceTotalAmt').val(parseFloat(numTaxAmount));
	}
}
window.onload = function () { 
var y=$('#currProj').val();
if(y){
$('#projectId').val(y).trigger('change');
//setproject();
}

}

function setproject(){
	var projectId = $('#projectId').val();
	var tableData = "";
		if(projectId == 0){
		$('#frm').addClass('hidden');
		$('#datatable').addClass('hidden');
		$('stagedetails').addClass('hidden');			
		$('#addnewrecord').addClass('hidden');
		$('#scheduledPaymentID').find('option').not(':first').remove();
		$('#scheduledPaymentID').val('0').trigger('change');
		
	}else{
		$.ajax({
	        type: "POST",
	        url: "/PMS/mst/getProjectInvoiceByProjectId",
	        data: "projectId=" + projectId,			
			success : function(data) {		
				//console.log(data);
				for(var i =0;i<data.length;i++){
					var row = data[i];
					var recordStatus= '';
					var invoiceType= '';
					if(row.valid==true){
						recordStatus='Active';
							}else{recordStatus='Inactive';
								}
					invoiceType=row.strInvoiceType;
					if(invoiceType==null){
						invoiceType='';
					}
					
					tableData+="<tr ><td><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" />"+row.numId+"</td> row.numId" ;
										
					tableData+=	"<td class='hidden'>"+row.scheduledPaymentIDs+"</td><td>"+row.strInvoiceRefno+"</td><td>"+row.strInvoiceDate+"</td><td class='text-right'>"+row.numInvoiceAmt+"</td><td class='text-right'>"+row.numTaxAmount+"</td><td>"+row.numInvoiceTotalAmt+"</td><td class='text-right'>"+invoiceType+"</td>";
					/*if(row.strInvoiceStatus == "Generated"){
					tableData+=	"<td class='bg_red'>"+row.strInvoiceStatus+"</td>";
					}
					else if(row.strInvoiceStatus == "Payment Fully Paid"){
						tableData+=	"<td class='bg_green'>"+row.strInvoiceStatus+"</td> ";
						}
					else if(row.strInvoiceStatus == "Payment Partially Paid"){
						tableData+=	"<td class='bg_yellow'>"+row.strInvoiceStatus+"</td> ";
						}*/
					tableData+=	"<td>"+recordStatus+"</td> ";
					/*if(row.strInvoiceStatus=="Generated" || row.strInvoiceStatus=="Cancelled"){
						tableData+="<td ><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td>" ;
					}else{
						tableData+=" <td></td>"
					}*/
					tableData+="<td ><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td>" ;
					tableData+=" </tr>";
							
							
							
							
				}
				tableData+='</tbody>';
				$('#datatable tbody').empty();
				$('#datatable').append(tableData);
				getScheduledPayment();
			},				
			error : function(e) {
				$('#RecurringManpowerAmt').val(0);
				alert('Error: ' + e);
				
			}
		});
		
		$('#details').removeClass('hidden');
		$('#datatable').removeClass('hidden');
		$('#stagedetails').removeClass('hidden');
		$('#addnewrecord').removeClass('hidden');		
		
	}
}