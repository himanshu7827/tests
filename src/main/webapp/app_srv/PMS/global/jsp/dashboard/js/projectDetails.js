$(document).ready(function() {
		
		  $('.select2Option').select2({
		    	 width: '100%'
		    });
		  

	});

function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }	



function paymentRealizedDetails(encProjectId){			
			paymentReveivedDetail(encProjectId);
			pendingInvoicesDetail(encProjectId);
		}
	
	function paymentReveivedDetail(encProjectId){
		
		//alert("encId"+encProjectId);
	$('#payment_dataTable').DataTable().clear().destroy();
	var tableData = '';
	
	$.ajaxRequest.ajax({

				type : "POST",
				url : "/PMS/mst/getProjectPaymentReceivedWithInvoiceByProject",
				data : "encProjectId=" + encProjectId,
				success : function(response) {
					//console.log(response);
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						var invoiceRef=row.invoiceRefNo;
						if(invoiceRef==null){
							invoiceRef='';
						}
						//console.log(row);
						/*---- Add Amount Break-up modal click event [05-12-2023]-----------------------------*/
						tableData += '<tr id=dataRow_'+(i+1)+'> <td class="font_16">'+ row.dtpaymentDate	+ '</td>  <td class="font_16">';
						tableData +=  "<a title='Click here to view Amount Break-up details' data-toggle='modal' data-target='#amountDetails' onclick=viewAmountDetails("+(i+1)+") class='text-center'>"+row.strReceivedAmount + '</a> </td>  <td class="font_16"> ';
						tableData +=  row.strPaymentMode + '</td> <td class="font_16">';
						tableData += row.strUtrNumber +'</td> <td class="font_16">';						
						tableData +="<a title='Click here to view Invoice details' data-toggle='modal' data-target='#invoiceDetail' onclick=viewInvoiceDetails('"+row.encInvoiceId+"') class='text-center'>"+invoiceRef+"</a> </td> ";
						tableData +="<td class='hidden'>"+row.itTDS+"</td>";
						tableData +="<td class='hidden'>"+row.gstTDS+"</td>";
						tableData +="<td class='hidden'>"+row.shortPayment+"</td>";
						tableData +="<td class='hidden'>"+row.ldPayment+"</td>";
						tableData +="<td class='hidden'>"+row.otherRecovery+"</td>";
						tableData +="<td class='hidden'>"+row.excessPaymentAmount+"</td>";
						tableData +="<td class='hidden'>"+row.numReceivedAmount+"</td>";
						tableData +="</tr>";  
						/*---- EOL Add Amount Break-up modal click event -----------------------------*/
								
					}

					//$('#payment_dataTable > tbody').empty();
					$('#payment_dataTable').append(tableData);

					$('#payment_dataTable').DataTable( {
				        "paging":   false,
				        "ordering": false,
				        "info":     false,
				        "filter":   false
				    } );
				}

			});
	}

	
	function pendingInvoicesDetail(encProjectId){
	//alert("encId"+encProjectId);
	$('#invoices_dataTable').DataTable().clear().destroy();
	var tableData = '';
	
	$.ajaxRequest.ajax({

				type : "POST",
				url : "/PMS/mst/getProjectInvoiceDetailsByProjectId",
				data : "encProjectId=" + encProjectId,
				success : function(response) {
					//console.log(response);
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						//console.log(row);
						var strInvoiceStatus=row.strInvoiceStatus;
						if(strInvoiceStatus==null){
							strInvoiceStatus='';
						}
						tableData += '<tr> <td class="font_16">'
								+ row.strInvoiceDate
								+ '</td>  <td class="font_16">'
								+ row.strInvoiceRefno
								+ ' </td>  <td class="font_16"> '
								+ row.strInvoiceAmt
								+ '</td> <td class="font_16">'
								+ row.strTaxAmount 
								+ '</td> <td class="font_16">'
                                + row.strInvoiceTotalAmt
                                + '</td> <td class="font_16">'
                                + strInvoiceStatus
                                + '</td> </tr>';  
								
					}

					
					$('#invoices_dataTable').append(tableData);

					$('#invoices_dataTable').DataTable( {
				        "paging":   false,
				        "ordering": false,
				        "info":     false,
				        "filter":   false
				    } );
				}

			});
	}
	
/*	function manpowerUtilization(encProjectId,month,year){
		$('#manpower_utilization_dataTable').DataTable().clear().destroy();
		
		var monthNames = [ "January", "February", "March", "April", "May", "June",
		                   "July", "August", "September", "October", "November", "December" ];
		if(month == 0){
			var d = new Date();
			$('#displayMonth').text(monthNames[d.getMonth()-1]);
			$('#displayYear').text(", "+d.getFullYear());
		}else{
			$('#displayMonth').text(monthNames[month-1]);
			$('#displayYear').text(", "+year);
		}
		var tableData = '';
		$.ajaxRequest.ajax({

			type : "POST",
			url : "/PMS/getManpowerUtilization",
			data : "encProjectId=" + encProjectId + "&month=" + month + "&year=" +year,
			success : function(response) {
				var totalProjectCost = 0;
				
				//console.log(response);
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
				    var sno=i+1;
				    var salaryCost = row.strSalaryCostProject;
				    
				    totalProjectCost+=parseFloat(convertINRToAmount(salaryCost));
			
					tableData += '<tr><td>'+sno+'</td><td class="font_16">'
							+ row.employeeName
							+ '</td> <td align="right"> '+row.strSalaryByAuthority+'</td> <td class="font_16 text-right">'
							+ row.utilization 
							+ ' </td>  <td class="font_16 text-right"> '
							+ row.strSalaryCostProject 
							+ '</td>  </tr>';  
							
				}
				console.log(totalProjectCost);
				if(totalProjectCost && totalProjectCost>0){
					tableData += '<tr><td></td><td></td> <td></td> <td> </td>  <td class="font_16 text-right"><font size="4"><b> Total: '+convertAmountToINR(totalProjectCost)+ '</b></font></td>  </tr>'; 
					
				}

				//$('#payment_dataTable > tbody').empty();
				$('#manpower_utilization_dataTable').append(tableData);

				$('#manpower_utilization_dataTable').DataTable( {
			        "paging":   false,
			        "ordering": false,
			        "info":     false,
			        "bSortable": false,
			        "searching": false
			    } );
				
			},error : function(e) {
				console.log(e);
				
			}		

		});
		}*/
$(document).ready(function() {

	var arr =$("#startdate").val().split("/");
	var months = [ "January", "February", "March", "April", "May", "June",
	    "July", "August", "September", "October", "November", "December" ];
	var month_index =  parseInt(arr[1],10) - 1;

	var startmonth=month_index+1;
	var startYearFrom=arr[2];
	var todaymonth =new Date().getMonth();
	todaymonth=todaymonth+1;
	var todayYear=new Date().getFullYear();
	var totalmonths=todayYear-startYearFrom;
	totalmonths=totalmonths*12;
	if(todaymonth>startmonth){
		var diff=todaymonth-startmonth;
		totalmonths=totalmonths+diff;
		$("#startdate").val(totalmonths);
	}else if(todaymonth<startmonth){
		var diff=startmonth-todaymonth;
		totalmonths=totalmonths-diff;
		$("#startdate").val(totalmonths);
	}else{
		$("#startdate").val(totalmonths);
	}
	
	//Added by devesh on 02-11-23 to fix Monthpicker error
	if ($("#projectstartDate").val()=="" && $("#projectenddate").val()!=""){
		swal("Please fill the Project Start Date.")
	}
	else if ($("#projectstartDate").val()!="" && $("#projectenddate").val()==""){
		swal("Please fill the Project End Date.")
	}
	else if ($("#projectstartDate").val()=="" && $("#projectenddate").val()==""){
		swal("Please fill the Project Start Date and Project End Date.")
	}
	
	var startdate = -$("#startdate").val();
	if(isNaN(startdate)){
		startdate = 0;
	}
	//End of condition

	$("#utilizationDate").MonthPicker( {
		
	    Button: "<i class='pad-left-half font_eighteen fa fa-calendar'></i>",
        changeMonth: true,
        changeYear: true,
        dateFormat: 'mm/yy',
        MaxMonth: -1,
        MinMonth: startdate,
        OnAfterChooseMonth: function(){ 
         
           var date = $(this).val();
          
            var res = date.split("/");
            var month = res[0];
            var year = res[1];          
            if (month > 0 && year > 0){
            	var encProjectId = $('#encProjectId').val();
            	manpowerUtilization(encProjectId,month,year);
            }
        }
        });

			$("div.bhoechie-tab-menu>div.list-group>a").click(
					function(e) {
						e.preventDefault();
						$(this).siblings('a.active').removeClass("active");
						$(this).addClass("active");
						var index = $(this).index();
						$("div.bhoechie-tab>div.bhoechie-tab-content")
								.removeClass("active");
						$("div.bhoechie-tab>div.bhoechie-tab-content").eq(
								index).addClass("active");
						/*$('#mainDiv').focus();*/
						$('html, body').animate({
					        scrollTop: $("#mainDiv").offset().top
					    }, 1000);
					});
			
		
		});


function loadProjectWiseDocuments(encProjectId){
	
	//alert(encProjectId);
	$('#projectWiseDocuments').removeClass('hidden');
	$('#projectWiseDocumentsDtl').find('div').remove();
	
	var url = "/PMS/mst/documentDetails/"+encProjectId;
	

	//var data='<div> <iframe id="test" src='+url+' ></iframe> </div>';
	//var data='<div> <iframe id="test" src='+url+' width="100%;" frameborder="0" scrolling="no" onload="resizeIframe(this)"></iframe> </div>';

	var data='<div> <iframe id="test" src='+url+' width="100%;" height="400px;" frameborder="0" ></iframe> </div>';

	$('#projectWiseDocumentsDtl').append(data);
}

function projectBudgetDetails(encProjectId){
//	alert(encProjectId);
	if ( $.fn.DataTable.isDataTable('#project_budget_dataTable') ) {
		  $('#project_budget_dataTable').DataTable().clear().destroy();
		}
	//$('#project_budget_dataTable').DataTable().clear().destroy();
	var tableData = '';
	
	$.ajaxRequest.ajax({

				type : "POST",
				url : "/PMS/mst/getAmountAgainstBudgetHeadByProjectId",
				data : "encProjectId=" + encProjectId,
				success : function(response) {
					console.log(response);
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						//console.log(row);
				if(i==0){
					tableData += '<thead><tr class="datatable_thead bold "> ';
						for(var j=0;j<row.length;j++){
							tableData+='<th>'+row[j]+'</th>';
						}
					tableData +='</tr> </thead> <tbody>';  
				}else{
					tableData += '<tr> ';
					for(var k=0;k<row.length;k++){
						if(k==0){
							tableData+='<td>'+row[k]+'</td>';
						}else{
							tableData+='<td class="text-right">'+row[k]+'</td>';
						}
						
					}
				tableData +='</tr>';  
				}
						
								
					}

					tableData+='</tbody>';
					
					$('#project_budget_dataTable').empty();
					$('#project_budget_dataTable').append(tableData);

					$('#project_budget_dataTable').DataTable( {
				        "paging":   false,
				        "ordering": false,
				        "info":     false
				    } );
				},error : function(e) {
					console.log(e);
					
				}	

			});
	
}

function showProjectDocumentRevision(documentId){
			//console.log(documentId);
			window.open("/PMS/mst/showProjectDocumentRevision/"+documentId);	
		}
			

function showProposalDocumentRevision(documentId){
	//console.log(documentId);
	window.open("/PMS/mst/showProposalDocumentRevision/"+documentId);	
}
	

		
		$(document).ready(function() {
			$('#example').DataTable();
			
			$(".btn-pref .btn").click(function () {
			    $(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");
			    // $(".tab").addClass("active"); // instead of this do the below 
			    $(this).removeClass("btn-default").addClass("btn-primary");   
			});
			});
		
		$('.cont').click(function(){

			  var nextId = $(this).parents('.tab-pane').next().attr("id");
			  $('[href=#'+nextId+']').tab('show');

			})
			
		
		
		function milestoneDetail(encProjectId){
			
			//alert("encId"+encProjectId);
		$('#milestone_dataTable').DataTable().clear().destroy();
		var tableData = '';
		
		$.ajaxRequest.ajax({

					type : "POST",
					url : "/PMS/mst/milestoneDetailsByProjectId",
					data : "encProjectId=" + encProjectId,
					success : function(response) {
						console.log(response);
						var noOfColumn = $("#milestone_dataTable").find("tr:first th").length;
						console.log(noOfColumn);
						for (var i = 0; i < response.length; i++) {
							var row = response[i];
							sNo=i+1;
												
							//comment the below line and add new p tag to remove the hyper link of milestone details by varun on 19-09-2023			
						/*	tableData += '<tr> <td class="font_16 center">'
									+ sNo
									+ '</td>  <td class="font_16"><a title="Click here to view milestone activity details" data-toggle="modal" data-target="#milestoneDetails" onclick=milestoneActivityDetails('+row.numId+') class="text-center">'
									+ row.milestoneName
									+ '&nbsp;&nbsp;&nbsp;&nbsp;</a>';*/

							tableData += '<tr> <td class="font_16 center">'
								+ sNo
								+ '</td>  <td class="font_16"><p>'
								+ row.milestoneName
								+ '&nbsp;&nbsp;&nbsp;&nbsp;</p>';

							if(row.thisDate){
								tableData +='<a class="pull-right" title="Click here to view History" data-toggle="modal" data-target="#history" onclick=milestoneActivityHistory("'+row.encMilestsoneId+'") class="text-center"><i class="fa fa-history"></i></a>';
							}
							else{
								tableData +='<a class="pull-right" class="text-center"><i>&nbsp;&nbsp;&nbsp;</i></a>';
									
							}
							if(noOfColumn == 5){
								if(row.strStatus=="Completed"){
									tableData +='<a title="Milestone Completed"><i class="fa fa-check green pull-right">&nbsp;&nbsp;</i></a></td> ';
								}else{
									tableData +='<a class="pull-right" title="Click here to review this Milestone." onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'+row.encMilestsoneId+'")><i class="fa fa-file-text-o">&nbsp;&nbsp;</i></a></td>';
								}
							}else{
								tableData +='</td> ';
							}
							tableData +='<td class="font_16">'+row.expectedStartDate +'</td>';
								
							if(row.thisDate){
								tableData +='<td class="font_16">'+row.thisDate +'</td>';
							}else{
								tableData +='<td></td>';
							}
							
							
							/*if(noOfColumn == 5){
								tableData +='<td></td>';
							}*/
							
							tableData +='</tr>';  
									
						}

						//$('#payment_dataTable > tbody').empty();
						$('#milestone_dataTable').append(tableData);

						$('#milestone_dataTable').DataTable( {
					        "paging":   false,
					        "ordering": false,
					        "info":     false
					    } );
					}

				});
		}	
		function milestoneActivityHistory(encMilestsoneId){
			$('#HistoryTable').DataTable().clear().destroy();
			var tableData = '';
			$.ajax({
	    		type : "POST",
	    		url : "/PMS/mst/milestoneReviewMasterHistory",
	    		data : "encId=" + encMilestsoneId,
	    		success : function(response) {
	    			console.log(response);
				
						for (var i = 0; i < response.length; i++) {
							var row = response[i];
							sNo=i+1;
												
							tableData += '<tr> <td class="font_16 center">'+sNo+'</td>' 
									+ '<td class="font_16">'+row.reviewDate +'</td>'
									+'<td class="font_16">'+row.completionDate +'</td>'
									+'<td class="font_16">'+row.strRemarks +'</td>';
									+'</tr>';  
									
						}

						$('#HistoryTable').append(tableData);

						$('#HistoryTable').DataTable( {
					        "paging":   false,
					        "ordering": false,
					        "info":     false,
					        "filter": false
					    } );
					
	 
	    		},
	    		error : function(e) {
	    			alert('Error: ' + e);			
	    		}
	    	});					
		
		}
		
		
	$(document).ready(function(){
		
		$('#paymentBaselineTbl').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "searching": false
	    } );
		
		$('#manpowerBaselineTbl').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "searching": false
	    } );
		
		$('#milestoneBaselineTbl').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "searching": false
	    } );
		
		
		
	});
	
	$(document).ready(function() {
		var scheduleAmtTotal=0;
		$('.scheduleStrAmt').each(function(){
			scheduleAmtTotal+=parseFloat(convertINRToAmount($(this).text().trim()));			
		});
		$('#scheduleAmtTotal').text(convertAmountToINR(scheduleAmtTotal));
	});
	
	
	function monthlyProgressReport(encProjectId,month,year){
		
		var monthNames = [ "January", "February", "March", "April", "May", "June",
		                   "July", "August", "September", "October", "November", "December" ];
		if(month == 0){
			var d = new Date();
			$('#displayMonth').text(monthNames[d.getMonth()-1]);
			$('#displayYear').text(", "+d.getFullYear());
		}else{
			$('#displayMonth').text(monthNames[month-1]);
			$('#displayYear').text(", "+year);
		}
		//alert(month);
		//alert(year)
		$.ajaxRequest.ajax({

			type : "POST",
			url : "/PMS/getMonthlyProgress",
			data : {"encProjectId": encProjectId , 
					"month":month ,
					"year" :year},			
			success : function(response) {
				if(response!=null || response!=''){
					
					$('#monthlyParentId').val(response.encMonthlyProgressId);
					if(response.categoryModel){
						$('#numCategoryId').find('option').not(':first').remove();
						$('.hiddenControls').remove();
						var options = '';
						var tempInputs = '';
						for(var j=0;j<response.categoryModel.length;j++){
							var temp = response.categoryModel[j];
							options +='<option value="'+temp.encCategoryId+'">'+temp.strCategoryName+'</option>';
							
							tempInputs +='<input type="hidden" class="hiddenControls" id="strCatgController_'+temp.encCategoryId+'" value="'+temp.strCategoryController+'"/>'
							
						}
						$('#numCategoryId').append(options).select2("destroy").select2({
					    	 width: '100%'
					    });
						
						$('#monthlyProress').append(tempInputs);						
					}
					getNextRoleActions(response.encMonthlyProgressId,response.encPageId,response.encWorkflowId);
				}
				else{
					alert(error);
				}
				
				}
		});
	}
	
	$(document).on('change','#numCategoryId',function(e){	
		//alert("change");
		$("#hrefDiv").removeClass('hidden');	
		

		});
	$(document).ready(function() {
				
		
		var arr =$("#startdateForProgress").val().split("/");
		var months = [ "January", "February", "March", "April", "May", "June",
		    "July", "August", "September", "October", "November", "December" ];
		var month_index =  parseInt(arr[1],10) - 1;
		
		var startmonth=month_index+1;
		var startYearFrom=arr[2];
		var todaymonth =new Date().getMonth();
		todaymonth=todaymonth+1;
		var todayYear=new Date().getFullYear();
		var totalmonths=todayYear-startYearFrom;
		totalmonths=totalmonths*12;
		if(todaymonth>startmonth){
			var diff=todaymonth-startmonth;
			totalmonths=totalmonths+diff;
			$("#startdateForProgress").val(totalmonths);
		}else if(todaymonth<startmonth){
			var diff=startmonth-todaymonth;
			totalmonths=totalmonths-diff;
			$("#startdateForProgress").val(totalmonths);
		}else{
			$("#startdateForProgress").val(totalmonths);
		}
	
	//Added by devesh on 02-11-23 to fix Monthpicker error
	var startdateForProgress = -$("#startdateForProgress").val();
	if(isNaN(startdateForProgress)){
		startdateForProgress = 0;
	}
	//End of condition
	$("#strMonthAndYear").MonthPicker( {
		
	    Button: "<i class='pad-left-half font_eighteen fa fa-calendar'></i>",
	    changeMonth: true,
	    changeYear: true,
	    dateFormat: 'mm/yy',
	    MaxMonth: 0,
	    MinMonth: startdateForProgress,
	    OnAfterChooseMonth: function(){ 
	    	
 	var selectedMonth = $('#projectenddate').val();
	    	
       
       var list = selectedMonth.split("/");
       
        	const enddate= new Date(list[2],list[1]-1,list[0]);
        	// To show the alert of project end date elapsed.
        	const currentdate = new Date();
        	if (enddate < currentdate) {
        		  sweetSuccess("Project end date is elapsed", "Please extend the Project End Date" );
        		  return
        		} 
        	
        	
        	
	    	$("#divCat").removeClass('hidden');	    	
	       var date = $(this).val();
	      
	        var res = date.split("/");
	        var month = res[0];
	        var year = res[1];
	        //alert(month);
	        //alert(year);
	        if (month > 0 && year > 0){
	        	
	        	var monthNames = [ "January", "February", "March", "April", "May", "June",
	                "July", "August", "September", "October", "November", "December" ];
					if(month == 0){
						var d = new Date();
						$('#displayMonth').text(monthNames[d.getMonth()-1]);
						$('#displayYear').text(", "+d.getFullYear());
					}else{
						$('#displayMonth').text(monthNames[month-1]);
						$('#displayYear').text(", "+year);
					}
					
					
		            	var encProjectId = $('#encProjectId').val();
		            	monthlyProgressReport(encProjectId,month,year);
		            	
	        }
	    }
	
	
	

	    });
	});
	
	function monthlyReport(){
		//alert("here");
		var encParentId=$('#monthlyParentId').val();
		var encCategoryId='';
		var categoryId=0;
		var requestURL='';
		var subcat=$('#numSubCategoryId').val();
		if(subcat!=0){
			//alert("subcatId"+subcat);
			categoryId=$('#numSubCategoryId').val();
			 requestURL = $('#strCatgController_'+categoryId).val();
			//alert("sub cat url"+requestURL);
		}
		else{
			 categoryId=$('#numCategoryId').val();
			//alert(categoryId);
		requestURL = $('#strCatgController_'+categoryId).val();
		//alert(requestURL);
		}
		
		
		if(encParentId==''||encParentId==0){
			swal("Please Select Month of Progress Report");
		}
		else if($('#isSubCatPresent').val()==1 && subcat==0){
			swal("Please Select Sub-Category");
		}
		else if(categoryId==0&&subcat==0){
			swal("Please Select Category");
		}
		else if(requestURL){
			//alert(categoryId);
			openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encParentId,"encCategoryId":categoryId},'_self');
		}else{
			//alert(requestURL);
			console.log("ÚRL Not mapped");
		}		
	}
	
	$(document).on('change','#numCategoryId',function(e){	
		
		categoryId = $('#numCategoryId').val();		
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/getChildCategoryByParentId",
		        async:false,
		        data: {"encCategoryId":categoryId,
		        	"parentId":$('#monthlyParentId').val()
		        },			
				success : function(data) {
					//alert(data);
					if(data!=''){
					//alert("sub category present");
					var options= '<option value="0"> -- Select Sub Category-- </option>';
					var value='<span></span>';
					if(categoryId!=0)
						{
							jQuery(data).each(function(j, item){
								
								value+='<input type="hidden" id=strCatgController_'+item.encCategoryId+' value="'+item.strCategoryController+'">';
								options+='<option value="'+item.encCategoryId+'">'+item.strCategoryName+'</option>';
							});
						}
					$("#subCatDiv").removeClass('hidden');	
					$('#numSubCategoryId').empty();
					$('#numSubCategoryId').append(options);
					$('#isSubCatPresent').val(1);	
					$('#hiddenFields').html(value);
					}
					else{
						$('#numSubCategoryId').val(0);
						$('#isSubCatPresent').val(0);	
						
						$("#subCatDiv").addClass('hidden');	
					}}
				});
	});
	
	function previewOfReport(){
		
		if($("#strMonthAndYear").val()==''){
			sweetSuccess('Attention','Progress Report For Month is mandatory!');
			return false;
		}
		var encParentId=$('#monthlyParentId').val();
		openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encParentId},'_blank');
		
		}

function populateRadio(){
	/*var value = document.getElementsByName('radioButton');*/
	var selectedValue = $("input:radio[name=radioButton]:checked").val();
	var encProjectId = $("#encProjectId").val();
	/*alert(encProjectId);*/
	if(selectedValue==0){
		swal({
			  title:"Are you Sure?",
			  type: 'success',
			  text: " This will stop Monthly Progress Report sharing with Corporate.",
			  
			  
		      buttons: [
		                'No',
		                'Yes'
		              ],	     
		    }).then(function(isConfirm) {
		      if (isConfirm) {
				 console.log('Submit');
					$.ajaxRequest.ajax({
						type : "POST",
						url : "/PMS/mst/updateProjectDetails",
						data : {									
								"encProjectId":encProjectId,
								"selectedVal":selectedValue,
							},
						success : function(response) {
							if (response === "Done"){
								sweetSuccess('Success!','Done Successfully!');
								
							}						
						}, error: function(data) {
							sweetSuccess('Error','Something went wrong!');
					   }
				});
		      } else {
		    	  $( "#yes" ).prop( "checked", true );
		    	  return false;
		      }
		    });
	}
	else{
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/updateProjectDetails",
			data : {									
					"encProjectId":encProjectId,
					"selectedVal":selectedValue,
				},
			success : function(response) {
				if (response === "Done"){
					sweetSuccess('Success!','Monthly Progress Report successfully shared with Corporate!');
					
				}						
			}, error: function(data) {
				/*sweetSuccess('Error','Something went wrong!');*/
		   }
	});
	}
}
// added below function to store the validation status by varun on 15-12-2023

function performAction1() {
	  var textarea = document.createElement('textarea');

	  textarea.rows = 6;
	  textarea.className = 'swal-content__textarea';
	  textarea.id = 'textArea';
	  textarea.placeholder = 'You can write remarks';

	  swal({
	    title: "Review of Payment Schedule",
	    content: textarea,
	    buttons: {
	      No: 'No',
	      Yes: 'Yes'
	    },
	    showCloseButton: false, // Add this line to show the close button
	  }).then(function(result) {
	    userSelectionMade = true;
	    var strValidatedRemarks = $("#textArea").val();
	  //  console.log("hi result" + strValidatedRemarks);
	    if (result == 'Yes') {
	      storeValidationDetails(strValidatedRemarks, 'Yes');
	      location.reload(); 
	    } else if (result == 'No') {
	      if (!strValidatedRemarks.trim()) {
	        swal("Remarks are required when selecting 'No'", {
	          icon: 'warning',
	        });
	        return;
	      }

	      storeValidationDetails(strValidatedRemarks, 'No');
	      location.reload();
	    }
	  });

	  // Add the close button manually to the modal
	  var closeButton = document.createElement('button');
	  closeButton.className = 'swal2-close';
	  closeButton.onclick = function() {
	    swal.close();
	  };
	  closeButton.textContent = '×';
	  closeButton.style.position = 'fixed';
	  closeButton.style.top = '9px';
	  closeButton.style.right = '6px';
	  closeButton.style.fontSize = '20px';
	  closeButton.style.backgroundColor = 'lightskyblue';
	  var modal = document.querySelector('.swal-modal');
	  modal.appendChild(closeButton);
	}

function storeValidationDetails(strValidatedRemarks, strValidateStatus) {
	  if (!userSelectionMade) {

	    return;
	  }

	  var encProjectId = $("#encProjectId").val();
	  $.ajaxRequest.ajax({
	    type: "POST",
	    url: "/PMS/mst/updateProjectValidationDetails",
	    data: {
	      encProjectId: encProjectId,
	      strValidatedRemarks: strValidatedRemarks,
	      strValidateStatus: strValidateStatus,
	    },
	    async: false,
	    success: function(response) {
	    
	    }
	  });
	}


/*var userSelectionMade = false; 

function performAction1() {
  var textarea = document.createElement('textarea');

  textarea.rows = 6;
  textarea.className = 'swal-content__textarea';
  textarea.id = 'textArea';
  textarea.placeholder = 'You can write remarks';

  swal({
    title: "Payment Schedule Correctly Entered?",
    content: textarea,
    buttons: {
      No:'No',
      Yes:'Yes'
    },
  }).then(function(result) {
    userSelectionMade = true; 
    var strValidatedRemarks = $("#textArea").val();
       console.log("hi result" + strValidatedRemarks);
    if (result=='Yes') {
 
      storeValidationDetails(strValidatedRemarks, 'Yes');
    } else if (result=='No'){
 
    	  storeValidationDetails(strValidatedRemarks, 'No');
    }
    
  });
}
function storeValidationDetails(strValidatedRemarks, strValidateStatus) {
  if (!userSelectionMade) {

    return;
  }

  var encProjectId = $("#encProjectId").val();
  $.ajaxRequest.ajax({
    type: "POST",
    url: "/PMS/mst/updateProjectValidationDetails",
    data: {
      encProjectId: encProjectId,
      strValidatedRemarks: strValidatedRemarks,
      strValidateStatus: strValidateStatus,
    },
    async: false,
    success: function(response) {
    
    }
  });
}*/
/*function performAction1() {
	  var textarea = document.createElement('textarea');

	  textarea.rows = 6;
	  textarea.className = 'swal-content__textarea';
	  textarea.id = 'textArea';
	  textarea.placeholder = 'You can write remarks';

	  swal({
	    title: "Payment Schedule Correctly Entered?",
	    content: textarea,
	    buttons: [
	      'No',
	      'Yes'
	    ],
	  }).then(function(result) {
	    if (result) {
	    	var encProjectId = $("#encProjectId").val();
	    	var strValidatedRemarks = $("#textArea").val();
	    	storeValidationDetails(strValidatedRemarks,'Yes');
	    } else {
	      // User clicked "No"
	      // Add your code to handle the "No" button click event here
	      var strValidatedRemarks = $("#textArea").val();
	      storeValidationDetails(strValidatedRemarks,'No');
	      console.log('No button clicked');
	    }
	  });
	}

function storeValidationDetails(strValidatedRemarks,strValidateStatus)
{
	var encProjectId = $("#encProjectId").val();
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/updateProjectValidationDetails",
		data : {
			encProjectId: encProjectId,
			strValidatedRemarks: strValidatedRemarks,
			strValidateStatus: strValidateStatus,
	    },
		async:false,
		success : function(response) {
			//console.log("Helo" + response);
		}
	});	
}*/


       if ($('#validatew').text()==7){
		$('#validatehidden').removeClass('hidden')
		$('#logvalidate').removeClass('hidden')
		
	}
/*---- Set the Value in the Amount Break-up modal [05-12-2023]-----------------------------*/	
function viewAmountDetails(rowNumber)
{
	var rowSelector = "#dataRow_"+rowNumber;
    var tdArray = $(rowSelector).find('td');
    var rowDataArray = [];
    tdArray.each(function() {
        rowDataArray.push($(this).text());
    });
    /*------------Received Amount ---------------------*/
    var receivedAmt = parseFloat(rowDataArray[11]) - parseFloat(rowDataArray[10]);
    var otherPayment = parseFloat(rowDataArray[5]) + parseFloat(rowDataArray[6]) + parseFloat(rowDataArray[7]) + parseFloat(rowDataArray[8]) + parseFloat(rowDataArray[9]);
    receivedAmt += otherPayment;
    /*-------------------------------------------------*/
    var tableData='';
    tableData += '<tr class="text-center">';
    tableData += '<td>1</td><td>'+ receivedAmt+ '</td><td>'+ rowDataArray[10]+ ' </td>';
    tableData += '<td>'+ rowDataArray[5]+ '</td><td>'+ rowDataArray[6]+ '</td><td>'+ rowDataArray[7]+ ' </td>';
    tableData += '<td>'+ rowDataArray[8]+ '</td><td>'+ rowDataArray[9]+ '</td><td>'+ rowDataArray[1]+ ' </td>';
    tableData += '</tr>';
    $('#amountDetailsTable tbody').empty();	
    $('#amountDetailsTable').append(tableData);	
}

	
	