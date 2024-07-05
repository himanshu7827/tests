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
	$("#schdPay").hide();
	if($("#withInvoice").prop("checked", true))
		$("#remarksDiv").hide();
			
	if($("#withPayment").prop("checked", true))
		$("#PaymentDetails").show();

	
		var table = $('#datatable').DataTable();
		table.destroy();		
		
		$('#addnewrecord').addClass('hidden');
		if( $('#projectId').val()>0){
			$('#projectId').trigger('change');
		}
		
});



$(document).on('click','#addnewrecord',function(e){
	var invoiceId = $('#invoiceId').val();
	if(invoiceId == 0){
		$('#addnewrecord').addClass('hidden');	
	}
	else{
	$('#addnewrecord').addClass('hidden');
$('.select2Option').select2({
	 width: '100%',
		 dropdownCssClass: "myFont" 
})
}
});



$(document).on('c','#invoiceId',function(e){
	
	$('#addnewrecord').removeClass('hidden');});
$('.select2Option').select2({
	 width: '100%',
	 dropdownCssClass: "myFont" 
});


$(document).on('click','#addnewrecord',function(e){
	var invoiceId = $('#invoiceId').val();
	if(invoiceId == 0){
		$('#addnewrecord').addClass('hidden');	
	}
	else{
	$('#addnewrecord').addClass('hidden');
$('.select2Option').select2({
	 width: '100%',
	 dropdownCssClass: "myFont" 
})
}
});
$(document).on('click','#withInvoice',function(e){
	$("#invoice").show();
	$("#remarksDiv").hide();
	$("#isWithPayment").show();
	$("#PaymentDetails").show();
	$("#schdPay").hide();
	/*$('#scheduledPaymentID').find('option').not(':first').remove();
	$('#scheduledPaymentID').val('0').trigger('change');*/
	$("#invoiceId").val(0).trigger('change');


});

$(document).on('click','#withoutInvoice',function(e){	
	//alert(1);
	$("#invoice").hide();
	$("#schdPay").show();
	
	$("#remarksDiv").show();
	$("#isWithPayment").hide();
	$("#PaymentDetails").hide();
		paymentDatepicker('');	
		/*getScheduledPayment();*/
});

$(document).on('click','#withPayment',function(e){	
	$("#PaymentDetails").show();
});

$(document).on('click','#withoutPayment',function(e){	
	$("#PaymentDetails").hide();
});


	$(document).on('click','#edit',function(e){
		$('#frm').removeClass('hidden');
		$('#addnewrecord').addClass('hidden');
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		//console.log("edit:"+resultArray);
		$('#numId').val(resultArray[1].trim());
		$('#dtPayment').val(resultArray[2].trim()).trigger('input');
		$('#numReceivedAmount').val(resultArray[3].trim()).trigger('input');
		$('#strPaymentMode').val(resultArray[6].trim()).trigger('change');
		$('#strUtrNumber').val(resultArray[7].trim()).trigger('input');
	
		if(resultArray[8].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		
		//$('#invoiceId').val(resultArray[6].trim()).trigger('input');

		//alert("with paym"+resultArray[9].trim());
		if(resultArray[14].trim()=='false'){
			$('#withInvoice').attr('checked',false);
			$('#withoutInvoice').prop("checked", true).trigger("click");
		}else{
			$('#withoutInvoice').attr('checked',false);
			$('#withInvoice').prop("checked", true).trigger("click");

		}
		$('#paymentId').val(resultArray[15].trim()).trigger('change');
		$("#remarks").val(resultArray[17].trim()).trigger('input');
		var  scheduleid=resultArray[18].trim().split(',');
		/*alert(scheduleid);*/
		/*---------- After the Edit Button Click Reflect the value of [itTDS,gstTDS,shortPayment,LD,excessPaymentAmount and otherRecovery] fields [05-12-2023] -------------------*/	
		$('#itTDS').val(checkNull(resultArray[19].trim())).trigger('input');
		$('#gstTDS').val(checkNull(resultArray[20].trim())).trigger('input');
		$('#shortPayment').val(checkNull(resultArray[21].trim())).trigger('input');
		$('#ldPayment').val(checkNull(resultArray[22].trim())).trigger('input');
		$('#otherRecovery').val(checkNull(resultArray[23].trim())).trigger('input');
		$('#excessPaymentAmount').val(checkNull(resultArray[5].trim())).trigger('input');
		$("#scheduledPaymentID").val(scheduleid).change();
		$('#save').text('Update');
		$('html, body').animate({
		        scrollTop: $("#myDiv").offset().top
		    }, 2000);
		if(resultArray[13].trim()=='false'){
			$('#withPayment').removeAttr('checked');
			$('#withoutPayment').prop("checked", true).trigger("click");
		}else{
			$('#withoutPayment').removeAttr('checked');
			$('#withPayment').prop("checked", true).trigger("click");
			$("#PaymentDetails").hide();
		}
		if(resultArray[11].trim() != ''){
			$('#invoiceId').val(resultArray[11].trim()).trigger('change');
		}
	    /*---------- END of After the Edit Button Click Reflect the value of [itTDS,gstTDS,shortPayment,LD,excessPaymentAmount and otherRecovery] fields [05-12-2023] -------------------*/
	});
	/*---------- check field data if null [05-12-2023] -------------------*/
	function checkNull(value) {
	    if (value === null || value === 'null') {
	        return 0.0;
	    }
	    return value;
	}
	/*---------- check field data if null [05-12-2023] -------------------*/
	
	
	$('#new_record').click(function(){	
		
		$('#frm').removeClass('hidden');
	
	});
	$('#invoiceId').change(
		function() {
			var invoiceId = $('#invoiceId').val();
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getInvoiceDetailsByInvoiceId",
				data : "invoiceId=" + invoiceId,
				success : function(data) {
					//console.log(data);
					if (data.length > 0) {
						var invoiceDate = data[0].strInvoiceDate;
						var dateParts = invoiceDate.split("/");
						var dateObject = new Date(+dateParts[2],
								dateParts[1] - 1, +dateParts[0]);
						//console.log(dateObject);
						
						paymentDatepicker(dateObject);
					}
				}

			});
		});
	
	function getScheduledPayment(){
		$('#scheduledPaymentID').find('option').not(':first').remove();
		var projectId = $('#projectId').val();
		$.ajax({
			type : "POST",
			url : "/PMS/mst/getScheduledPayment",
			 data: "projectId=" + projectId,	
			success : function(response) {
				//console.log(response);
				var seloption = "";
					for (var i = 0; i < response.length; i++) {
					
						if(!response[i].milestoneName){
							
							seloption += "<option  value="+response[i].numId+">"
							+ response[i].strPurpose +" </option>";
							
						}
						else{
							seloption += "<option  value="+response[i].numId+">"
							+ response[i].strPurpose +" ("+response[i].milestoneName+")</option>";
				}	    }    
					
					$('#scheduledPaymentID').append(seloption);
					//$("#empId").trigger("change"); 	
			},
			error : function(e) {
				alert('Error: ' + e);			
			}
		});	
		}

	$('#projectId').change(function(){
		
		$('#frm').addClass('hidden');
		$('#datatable').addClass('hidden');
		$('stagedetails').addClass('hidden');			
		$('#addnewrecord').addClass('hidden');	
		$('#paymentId').select2('destroy');
		$('#paymentId').val(0).select2({
			width : "100%",
			dropdownCssClass : "myFont",
			placeholder : "Choose Option",
			allowClear : true,
			formatResult : formatCustom
		});
		
		$('#invoiceId').select2('destroy');
		$('#invoiceId').val(0).select2({
			width:"100%",
	    	dropdownCssClass: "myFont" ,
	    	placeholder: "Choose Option",
	        allowClear: true,	      
	        formatResult : formatCustom
		});
		
		
		var projectId = $('#projectId').val();
		
		var tableData = "";
		if(projectId == 0){
			$('#frm').addClass('hidden');
			$('#datatable').addClass('hidden');
			$('stagedetails').addClass('hidden');			
			$('#addnewrecord').addClass('hidden');			
			
			
			
		}else{
			$.ajaxRequest.ajax({
    	        type: "POST",
    	        url: "/PMS/mst/getProjectInvoiceRefNoByProjectId",
    	        data: "projectId=" + projectId,			
				success : function(data){		
					//console.log(data);
					$('#invoiceId option').remove();
					$('#invoiceId').append($('<option>', {
						 value: 0,
						 text: "---Select Details---"
					   
					}));
					for(var i =0;i<data.length;i++){
						var row = data[i]; 
						
						$('#invoiceId').append($('<option>', {
						    value: row.numId,
						    text: row.strInvoiceRefno +"[ Dated " +row.strInvoiceDate+": (INR)"+row.numInvoiceAmt+" ] <p class='foo pad-left-25' style='color:#808080; font-size:16px;'> <strong> Invoice Amount :"+row.numInvoiceAmt +"</p>"
						}));
					
					}
			
				},				
				error : function(e) {					
					//alert('Error: ' + e);
					
				}
			});
			
			$.ajax({
    	        type: "POST",
    	        url: "/PMS/mst/getProjectPaymentReceivedByProjectId",
    	        data: "projectId=" + projectId,			
				success : function(data) {
					var tableData = '';
					//console.log(data);
					/*----------- Add some columns value in the table [05-12-2023]--------------------*/
					for(var i =0;i<data.length;i++){
						var row = data[i];
						var recordStatus= '';
						var finalReceivedAmount='';
						if(row.valid==true){
							recordStatus='Active'
						}else{
							recordStatus='Inactive';
						}
						tableData+="<tr><td class='text-center'>"+(i+1)+"</td><td class='hidden'><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" />"+row.numId+"</td> row.numId" +
						"<td>"+row.strdtPayment+"</td>  ";
						finalReceivedAmount = (row.numReceivedAmount)-(row.excessPaymentAmount);
						var finalOtherPaymentAmount = (row.itTDS)+(row.gstTDS)+(row.shortPayment)+(row.ldPayment)+(row.otherRecovery);
						finalReceivedAmount = finalReceivedAmount + finalOtherPaymentAmount;
						if(row.encInvoiceId!=null){
							tableData +="<td>"+finalReceivedAmount+"</td>"
							
							tableData += "<td style='width:13%'>";
							if (row.itTDS !== null && row.itTDS !== 0) {
							    tableData += "<b>IT TDS :</b>" + row.itTDS + "<br/>";
							}
							if (row.gstTDS !== null && row.gstTDS !== 0) {
							    tableData += "<b>GST TDS :</b>" + row.gstTDS + "<br/>";
							}
							if (row.shortPayment !== null && row.shortPayment !== 0) {
							    tableData += "<b>Short Payment :</b>" + row.shortPayment + "<br/>";
							}
							if (row.ldPayment !== null && row.ldPayment !== 0) {
							    tableData += "<b>LD Payment :</b>" + row.ldPayment + "<br/>";
							}
							if (row.otherRecovery !== null && row.otherRecovery !== 0) {
							    tableData += "<b>Other Recovery :</b>" + row.otherRecovery + "<br/>";
							}
							if (
								    (row.itTDS === null || row.itTDS === 0) &&
								    (row.gstTDS === null || row.gstTDS === 0) &&
								    (row.shortPayment === null || row.shortPayment === 0) &&
								    (row.ldPayment === null || row.ldPayment === 0) &&
								    (row.otherRecovery === null || row.otherRecovery === 0)
								) {
								    tableData += "<spna class='text-center'>--</span><br/>";
								}
							tableData += "</td>";

							
							tableData +="<td>"+row.excessPaymentAmount+"</td>";
							tableData +="<td>"+row.strPaymentMode+"</td> <td>"+row.strUtrNumber+"</td><td>"+recordStatus+"</td>";
							tableData +="<td>"+row.numReceivedAmount+"</td>";
							tableData+="<td><a title='Click here to view Invoice details' data-toggle='modal' data-target='#invoiceDetail' onclick=viewInvoiceDetails('"+row.encInvoiceId+"') class='text-center'>"+row.invoiceCode+" </a></td> <td class='hidden'>"+row.invoiceId+"</td>"
							if(row.strInvoiceStatus=="Generated" || row.strInvoiceStatus=="Cancelled" || row.strInvoiceStatus=="Payment Partially Paid"){
								tableData+="<td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td>" ;
							}else{
								tableData+=" <td></td>"
							}
						}else{
							tableData +="<td>"+finalReceivedAmount+"</td>";
							
							tableData += "<td style='width:13%'>";
							if (row.itTDS !== null && row.itTDS !== 0) {
							    tableData += "<b>IT TDS :</b>" + row.itTDS + "<br/>";
							}
							if (row.gstTDS !== null && row.gstTDS !== 0) {
							    tableData += "<b>GST TDS :</b>" + row.gstTDS + "<br/>";
							}
							if (row.shortPayment !== null && row.shortPayment !== 0) {
							    tableData += "<b>Short Payment :</b>" + row.shortPayment + "<br/>";
							}
							if (row.ldPayment !== null && row.ldPayment !== 0) {
							    tableData += "<b>LD Payment :</b>" + row.ldPayment + "<br/>";
							}
							if (row.otherRecovery !== null && row.otherRecovery !== 0) {
							    tableData += "<b>Other Recovery :</b>" + row.otherRecovery + "<br/>";
							}
							if (
								    (row.itTDS === null || row.itTDS === 0) &&
								    (row.gstTDS === null || row.gstTDS === 0) &&
								    (row.shortPayment === null || row.shortPayment === 0) &&
								    (row.ldPayment === null || row.ldPayment === 0) &&
								    (row.otherRecovery === null || row.otherRecovery === 0)
								) {
								    tableData += "<spna class='text-center'>--</span><br/>";
								}

							tableData += "</td>";

							
							
							tableData +="<td>"+row.excessPaymentAmount+"</td>";
							tableData +="<td>"+row.strPaymentMode+"</td> <td>"+row.strUtrNumber+"</td>" +"<td>"+recordStatus+"</td>";
							tableData +="<td>"+row.numReceivedAmount+"</td>";
							tableData+="<td>INR "+row.numBalanceAmount+" is yet to be mapped to Invoice </td> <td class='hidden'></td>"
	
							
							if(row.numBalanceAmount>0){
								tableData+="<td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td>" ;
							}else{
								tableData+=" <td></td>"
							}
						}
						tableData+="<td class='hidden'>"+row.prevPayment+"</td>";
						tableData+="<td class='hidden'>"+row.withInvoice+"</td>";
						tableData+="<td class='hidden'>"+row.paymentId+"</td>";
						tableData+="<td class='hidden'>"+row.strPrevPaymentDetails+"</td>";
						tableData+="<td class='hidden'>"+row.remarks+"</td>";
						tableData+="<td class='hidden'>"+row.scheduledPaymentIDs+"</td>";
						/*------------------------- after edit click reflect the data of itTDS,gstTDS,shortPayment,LD and otherRecovery fields [05-12-2023]--------*/ 
						tableData+="<td class='hidden'>"+row.itTDS+"</td>";
						tableData+="<td class='hidden'>"+row.gstTDS+"</td>";
						tableData+="<td class='hidden'>"+row.shortPayment+"</td>";
						tableData+="<td class='hidden'>"+row.ldPayment+"</td>";
						tableData+="<td class='hidden'>"+row.otherRecovery+"</td>";
						/*------------------------- end of fields values [05-12-2023]--------*/
						tableData+=" </tr>";
					}
				tableData+='</tbody>';
				$('#datatable tbody').empty();
				$('#datatable').append(tableData);
				getScheduledPayment();
				},				
				error : function(e) {
					$('#RecurringManpowerAmt').val(0);
					//alert('Error: ' + e);
					
				}
			});
			$('#datatable').removeClass('hidden');
			$('#stagedetails').removeClass('hidden');
			//$('#addnewrecord').removeClass('hidden');	
			
	
			$.ajax({
		        type: "POST",
		        url: "/PMS/mst/getProjectPaymentWithoutInvoice",
		        data: "projectId=" + projectId,			
				success : function(data){		
					//console.log(data);
					$('#paymentId option').remove();
					$('#paymentId').append($('<option>', {
						 value: 0,    
						 text: "---Select Details---"
						    
					}));
					for(var i =0;i<data.length;i++){
						var row = data[i];
						var availableAmount = row.numReceivedAmount - row.numMappedAmount;
						$('#paymentId').append($('<option>', {
						    value: row.numId,
						    allowClear: true,
						    text: row.strUtrNumber +"[ Dated " + row.strPaymentRcvdDate+" : (INR)"+row.numReceivedAmount +" ]<p class='foo pad-left-25' style='color:#808080; font-size:16px;'> <strong> Available Amount :"+availableAmount +"</p>"
						    
						}));
					
					}
				},				
				error : function(e) {					
					//alert('Error: ' + e);
					
				}
			});
			
		}
		
		
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
	    	  paymentId:{
	    		  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Payment ID',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('paymentId').val();
	                          return (options != null && options != '0');
	                      }
	                  }
	              }
	    	  },
	    	  invoiceId:{
	    		  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Invoice Number',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('invoiceId').val();
	                          return (options != null && options != '0');
	                      }
	                  }
	              }
	    	  },strPaymentMode:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Payment Mode',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('strPaymentMode').val();
	                          return (options != null && options != '0');
	                      }
	                  }
	              }
	          },
	    	  dtPayment:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Payment Date is required and cannot be left empty'
	                    }
	                }
	          },
	    	  numReceivedAmount: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Received Amount is required and cannot be empty'
	                  },   stringLength: {
	                        max: 20,
	                        message: 'Received Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },strUtrNumber: {
	              group: '.col-md-12',
	              validators: {
	                  
	                  stringLength: {
	                        max: 50,
	                        message: 'The UTR Number must be less than 50 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },remarks: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Remarks is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 50,
	                        message: 'Remarks must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          /*------ Add Validation in itTDS,gstTDS,shortPayment,otherRecovery,excessPaymentAmount and ldPayment[05-12-2023] ------*/
	          itTDS: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: 'IT TDS Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },
	          excessPaymentAmount: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: 'Access Payment Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },
	          gstTDS: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: 'GST TDS Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },
	          shortPayment: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: 'Short Payment Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },
	          ldPayment: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: 'LD Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          },
	          otherRecovery: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: 'Other Recovery Amount must be less than 20 digits'
	                    },
	                    regexp: {
	                        regexp: amountRegex,
	                        message: amountErrorMessage
	                    }
	              }
	          /*------ End of add Validation in itTDS,gstTDS,shortPayment,otherRecovery and ldPayment[01-11-2023] ------*/
	          }
	          
	      
	         
	      }
	  });


	
	
	$('#save').click(function(){
		
		var paymentWithoutInvoice = $("input[name='paymentWithoutInvoice']:checked").val();
		
		var prevPayment = $("input[name='prevPayment']:checked").val();
		
		var scheduledPaymentID= $("#scheduledPaymentID").val();


		if(paymentWithoutInvoice=='true'){
			  $('#form1').bootstrapValidator('enableFieldValidators', 'remarks', true);
			  $('#form1').bootstrapValidator('enableFieldValidators', 'invoiceId', false);
			  if(scheduledPaymentID=='' || scheduledPaymentID==0){
				  sweetSuccess("Attention","Please select Scheduled Payment");
				  return false;
			  }
			  
		}else{
			  $('#form1').bootstrapValidator('enableFieldValidators', 'remarks', false);
			  $('#form1').bootstrapValidator('enableFieldValidators', 'invoiceId', true);
			  
			 
		
		}
		
			

		if(paymentWithoutInvoice == 'false' && prevPayment =='true'){
			 $('#form1').bootstrapValidator('enableFieldValidators', 'paymentId', true);
		}else if(paymentWithoutInvoice == 'false' && prevPayment =='false'){
			$('#form1').bootstrapValidator('enableFieldValidators', 'paymentId', false);
		}else if(paymentWithoutInvoice == 'true'){
			$('#form1').bootstrapValidator('enableFieldValidators', 'paymentId', false);
		}
		

		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
		
		/*   if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
	   	 
			var bootstrapValidator = $("#form1").data('bootstrapValidator');
			bootstrapValidator.validate();
		    if(bootstrapValidator.isValid()){				
					document.getElementById("save").disabled = true; 
					/*document.form1.submit();*/
					 $( "#form1")[0].submit();
					
					return true;
				
			}else{
				document.getElementById("save").disabled = false; 
				console.log('Not Validated');
			
				 //return false;
			}
	   	 
	});
		
});


function resetForm(){
	$('#numId').val(0);
	$('#projectId').val('0').trigger('change');
	$('#dtPayment').val('');
	$('#numReceivedAmount').val(0);
	$('#strPaymentMode').val('0').trigger('change');
	$('#strUtrNumber').val('');
	$('#invoiceId').val('0').trigger('change');
	$('#scheduledPaymentID').find('option').not(':first').remove();
	$('#scheduledPaymentID').val('').trigger('change');
	$('#paymentId').find('option').not(':first').remove();
	$('#paymentId').val('').trigger('change');
	$('#save').text('Save');
	/*------ Reset the value of itTDS,gstTDS,shortPayment,otherRecovery,excessPayment and ldPayment[05-12-2023] ------*/
	$('#itTDS').val(0);
	$('#gstTDS').val(0);
	$('#shortPayment').val(0);
	$('#ldPayment').val(0);
	$('#otherRecovery').val(0);
	$('#excessPaymentAmount').val(0);
}





/*$(document).ready(function(){
	$('#projectId').on('change', function(){
		resetData();
		 $('#form1').data('bootstrapValidator').resetForm(true);
});
})*/
/*function resetData(){
	$('#numId').val(0);
	$('#dtPayment').val('');
	$('#numReceivedAmount').val('');
	$('#strPaymentMode').val('0').trigger('change');;
	$('#strUtrNumber').val('');
	$('#invoiceId').val('0').trigger('change');;

	$('#save').text('Save');
}*/


$('#withPayment').change(function(){
	

	
	var projectId = $('#projectId').val();
	var tableData = "";

		$.ajax({
	        type: "POST",
	        url: "/PMS/mst/getProjectPaymentWithoutInvoice",
	        data: "projectId=" + projectId,			
			success : function(data){		
				//console.log(data);
				$('#paymentId option').remove();
				
				$('#paymentId').append($('<option>', {
					 value: 0,
					 text: '---Select Details---'
					//allowClear: true 
				}));
				for(var i =0;i<data.length;i++){
					var row = data[i];
					var availableAmount = row.numReceivedAmount - row.numMappedAmount;
					$('#paymentId').append($('<option>', {
					    value: row.numId,
					    
					    text: row.strUtrNumber +"[ Dated " + row.strPaymentRcvdDate+" : (INR)"+row.numReceivedAmount +" ]<p class='foo pad-left-25' style='color:#808080; font-size:16px;'> <strong> Available Amount :"+availableAmount +"</p>"
					    
					}));
				
				}
			
			},				
			error : function(e) {					
				//alert('Error: ' + e);
				
			}
		});
});
function paymentDatepicker(mindate){
	$("#dtPayment").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,	
		maxDate : '+5y',
		onSelect : function(date) {
			$('#dtPayment').trigger('input');
		}

	});
	
	if	(mindate){		
		$("#dtPayment").datepicker("option", "minDate", mindate);
		$("#dtPayment").datepicker("option", "defaultDate", mindate);
	 }else{
		 $("#dtPayment").datepicker("option", "minDate", '');
			$("#dtPayment").datepicker("option", "defaultDate", ''); 
	 }
	
}

window.onload = function () { 
	var y=$('#currProj').val();
	if(y){
	$('#projectId').val(y).trigger('change');
	}
	}


$(function(){
    $(".select2").select2({
    	width:"100%",
    	dropdownCssClass: "myFont" ,
    	placeholder: "Choose Option",
        allowClear: true,
      /*  matcher: matchCustom,*/
        formatResult : formatCustom
		
    });
})

function stringMatch(term, candidate) {
	/*console.log("stringMatch");*/
    return candidate && candidate.toLowerCase().indexOf(term.toLowerCase()) >= 0;
}

function formatCustom(state) {

	 return $(
		        '<div><div><span style="font-size:18px;"><strong> <i class="fa fa-arrow-circle-right pad-right-half"></i>' + state.text + '</strong></span></div></div>');
}
