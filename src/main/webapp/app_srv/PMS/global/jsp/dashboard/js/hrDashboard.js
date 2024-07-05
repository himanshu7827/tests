	$(document).ready(function() {
	// alert("hello dsjn");
		var strStart = "01/04/";
		var strEnd = "31/03/";
		var d = new Date();
		var currentYear = d.getFullYear();
		var currentMonth = d.getMonth();
		var lastYear = d.getFullYear() - 1;
		//alert("as on date"+currentYear);
		var dateString = '01/01/'+currentYear;
		
		/*if(currentMonth<=2){
			dateString  += lastYear;
		}
		else{
			dateString  += currentYear;
		}*/
		
		
		
		//alert("dateString"+dateString);
		$("#asOnDate").text(dateString);
		//$("#asOnDate1").text(dateString);
		 $("#asOnDateExp").text(dateString);
		 $("#asOnDateProjects").text(dateString);
		 $("#asOnDateProposals").text(dateString);
		 $("#asOnDateClosedProjects").text(dateString);

	$("#dtExpStartDate").datepicker({ 
		
		 showOn: "both", 
  buttonText: "<i class='fa fa-calendar font_20'></i>",
		
    /*   buttonImage: "/PMS/resources/app_srv/PMS/global/images/slider/slider4.jpg",
         buttonImageOnly: true,*/
		dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '0',
	    showOn: 'both',
	    
	   onSelect: function() {
		   var date = $('#dtExpStartDate').val();
		  // alert("Date Value"+date);
		   
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getExpenditureByDate",
				data : "startDate="+date ,
				success : function(response) {
					//console.log(response);			
						 var fieldValue = response;
						// $('#expenditureOndate').html('&#8377; '+k);
					/*	 $('#expenditureOndate').html(k);
						 $("#asOnDateExp").text(date);
*/
						 var convertedStringAmount = convertINRToAmount(fieldValue);
						 
							//console.log(convertedStringAmount);
							var amount = (parseFloat(convertedStringAmount))/100000;
							//console.log(response + " "+ amount);
							//console.log(roundUpTo2Decimal(amount));
							var convertedIntegerAmount=convertAmountToINR(roundUpTo2Decimal(amount,2));
							
							$('#expenditureOndate').html(convertedIntegerAmount +" Lakhs");
							 $("#asOnDateExp").text(date);
			}
		});
			
			
	      }
		});
	
	
	$('.ui-datepicker-trigger').attr('alt', 'Calender');
	$('.ui-datepicker-trigger').attr('title', 'Select Start Date');
	});
	
$('#dash-closedProjectList-modal').on('shown.bs.modal', function(event) {		
	initializeClosedProjects();
	$(this).off('shown.bs.modal');
});	
$('#dash-closure-pending-modal').on('shown.bs.modal', function(event) {		
	initializeClosurePending();
	$(this).off('shown.bs.modal');
});

function initializeClosedProjects() {		
		closedProjectsTable();
	}
function initializeClosurePending() {
	pendingClosuresTable();
}	
	

$(document).ready(function() {
	  
	  var owl = $("#owl-demo");
	 
	  owl.owlCarousel({
	     
	      navigation : true,
	      items: 4,
        singleItem: true,
        /*autoPlay : 5000,*/

	  navigationText: ["<strong>Prev</strong>","<strong>Next</strong>"]
	  });
	  
	  
	  
	  table = $('#employee_dataTable').DataTable().clear().destroy();
	  
		
		var tableData = '';
		$.ajaxRequest.ajax({
			
			type : "POST",
			url : "/PMS/mst/getEmployeesByGroupByEmployementType",
			success : function(response) {

				for (var i = 0; i < response.length; i++) {
					var row = response[i];
					var serialNo = i + 1;
					//console.log(row);
					tableData +='<tr ><td>'+serialNo+'</td><td  class="font_14" style="width:15%;">'+row.employeeName+'</td>';
					tableData += '<td  class="font_14">'
							+ row.groupName + '</td>';
					tableData += '<td  class="font_14">'
						+ row.employeeTypeName + '</td>';
					tableData += ' <td class="font_14 \">'
							+ row.strDesignation
							+  '</td> ';
					tableData += ' <td class="font_14 \">'
					+ row.dateOfJoining
					+  '</td>';
					//added by devesh on 15/6/23 for setting values in deputed at column
					if(row.numDeputedAt==1)tableData += ' <td class="font_14 \">'+ "CDAC"+'</td></tr>';
					else if(row.numDeputedAt==2)tableData += ' <td class="font_14 \">'+ "Client"+'</td> </tr>';
					else tableData += ' <td class="font_14 \">'+ "NULL"+'</td> </tr>';
					//end of deputed at column
				}

				$('#employee_dataTable > tbody').html('');
				$('#employee_dataTable').append(tableData);

				 table= $('#employee_dataTable').DataTable( {
				        dom: 'Bfrtip',
				        "columnDefs": [ {
				            "orderable": false,
				        "targets": 0
				    } ],
				    /*"order": [[ 1, 'asc' ]],*/
				        buttons: [
				             'excel', 'pdf', 'print'
				        ]
				    } );
				
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
				/*$('#employee_dataTable .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );*/
			        
//Multiselect filters added by devesh on 04-06-24 to employee table		        
		     // Function to escape special characters for regex
		    function escapeRegex(text) {
		        return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
		    }		    
		    
		
		    // Iterate through each column with the comboBox class
		    $('#employee_dataTable .filters th.comboBox').each(function(i) {
		        var colIdx = $(this).index();
		        var select = $('<select multiple="multiple" style="width:100%"></select>')
		            .appendTo($(this).empty())
		            .on('change', function() {
		                var selectedValues = $(this).val();
		                if (selectedValues && selectedValues.length > 0) {
		                    // Escape special characters in each selected value
		                    var escapedValues = selectedValues.map(escapeRegex);
		                    // Create a regex pattern to match exactly any of the selected values
		                    var searchRegex = '^(' + escapedValues.join('|') + ')$';
		                    table.column(colIdx)
		                        .search(searchRegex, true, false)
		                        .draw();
		                } else {
		                    // Clear the search for this column when no values are selected
		                    table.column(colIdx)
		                        .search('', true, false)
		                        .draw();
		                }
		                table.columns.adjust().draw();
		            });
		
		        // Initialize Select2 on the select element
		        select.select2({
		            placeholder: "Select options",
		            allowClear: true
		        })
		
		        // Populate the select element with unique, sorted column data
		        table.column(colIdx).data().unique().sort().each(function(d) {
		            select.append('<option value="' + d + '">' + d + '</option>');
		        });
		    });
//End of code
			        
			        // Setup - add a text input
			                                $('#employee_dataTable .filters th[class="textBox"]').each( function () {                 
			                                    $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );
			             
			              } );
			                             
			                                // DataTable
			                                var table = $('#employee_dataTable').DataTable();
			            
			             // Apply the search
			                                 table.columns().eq( 0 ).each( function ( colIdx ) {
			                            $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			                                table
			                                            .column( colIdx )
			                                            .search( this.value )
			                                            .draw() ;
			                                    
			                                                            } );
			      } );
			      
			      	//Added by devesh on 05-06-24 to close dropdown properly
					function closeInactiveSelect2() {
					    $('.select2-container').each(function() {
					        if (!$(this).hasClass('select2-container-active')) {
					            $(this).select2('close');
					        }
					    });
					}
					
					$('#employee_dataTable div[id^="s2id_autogen"]').on('click', function() {
					        closeInactiveSelect2(); // Close all inactive Select2 dropdowns
					});
					//End of event binders
			                              
			}
		
		});

	});

$(".emp").on("click", getColumnID);

function getColumnID() {
	
	
	  var $td = $(this),
      $th = $td.closest('table').find('th').eq($td.index());
  console.log($th.attr("id"));
  var designation=$th.attr("id");
  var group=$td.closest('tr').children('td:first').text();
  console.log(group);
  
	$.ajaxRequest.ajax({

		type : "POST",
		url : "/PMS/mst/getEmpbyDesignationForGroup",
		data : {"group":group,
				"designation":designation
				},		
		success : function(data) {
			console.log(data);
			var tableData="";
			for(var i =0;i<data.length;i++){
				var row = data[i];
  			    var sno=i+1;    			       			  
				tableData+="<tr><td>"+sno+"</td><td>"+row.employeeId+"</td> <td>"+row.employeeName+"</td></tr>";

 			} 
 			tableData+='</tbody>';
 			
			$('#datatable1 tbody').empty();						
			$('#datatable1').append(tableData);	
			$('#empDetailModal').modal('show');

		}
	});
}

$(document).ready(function() {
    $('#testTbl').DataTable( {
        scrollY:        300,
        scrollX:        true,
        scrollCollapse: true,
        paging:         false,
        "bFilter": false,
        "bInfo" : false,
        fixedColumns:   {
            leftColumns: 1,
            rightColumns: 1
        }
    } );
	
	 $('#testTb2').DataTable( {
        scrollY:        300,
        scrollX:        true,
        scrollCollapse: true,
        paging:         false,
        "bFilter": false,
        "bInfo" : false,
        fixedColumns:   {
            leftColumns: 1,
            rightColumns: 1
        }
    } );
} );

$('#dash-new-joinee-modal').on('shown.bs.modal', function(event) {

	//newJoineeEmpTable();
	newJoineeEmp();
});


$('#dash-resigned-emp-modal').on('shown.bs.modal', function(event) {

//ResignEmpTable();
newResignEmpTable();
	
});
//function for closure pending project

function pendingClosuresTable(){	
		
		
		$('#pendingCLosure_dataTable').DataTable().clear().destroy();
	 var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getPendingClosureDetail",
			success : function(response) {
				//console.log(response);
				if(response){
				var totalAmount = 0;
				/*Bhavesh(26-07-23)*/ 
				var valuesList = []; // Initialize an empty array to store the values
			    $(".underclos").each(function() {
			      var value = $(this).text().trim(); // Get the text content and remove any leading/trailing spaces
			      valuesList.push(value); // Add the value to the array
			    });

			   
			    //console.log(valuesList); 
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
					var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
							+ row.encNumId + "')";
					if(!valuesList.includes(row.projectRefrenceNo)){
					tableData += '<tr> <td class="grey-text">'+(i+1)+'</td> <td class="font_14 grey-text">'+ row.strGroupName +  '</td><td> <a class="font_14 grey-text" title="Click to View Complete Information" onclick='+clickFunction+'>'
					+ row.strProjectName + ' </a><p class="bold grey-text font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange grey-text">'+row.clientName+'</td>';
					/*tableData += '<tr> <td>'+(i+1)+'</td><td>'+row.strGroupName+'</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
							+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.clientName+'</i></p><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td>';*/
					
				if(row.strPLName==null || row.strPLName==''){
						row.strPLName='';
					}
					tableData += ' <td class="font_16 grey-text">'+ row.strPLName +  '</td>';
					tableData += ' <td class="font_14 text-right grey-text">'+ row.startDate +  '</td> ';
					
					tableData += ' <td class="font_14 text-right grey-text">'+ row.endDate +  '</td>';
					//Added by devesh on 21-09-23 to get cdac outlay and status column in pending closure table
					tableData += ' <td class="font_14 text-right grey-text"> <span id="projectCost_'+row.encProjectId+'">'
					+ row.strProjectCost + '</span></td>';
		
					totalAmount += row.numProjectAmountLakhs;
					
					if (row.workflowModel && row.workflowModel.strActionPerformed) {
						  if (row.workflowModel.strActionPerformed.includes("Sent back to PM")) {
						    tableData += ' <td class="font_14 text-center grey-text">Pending At PL</span></td> </tr>';
						  } else if (row.workflowModel.strActionPerformed.includes("HOD")) {
						    tableData += ' <td class="font_14 text-center grey-text">Pending At HOD</span></td> </tr>';
						  } else if (row.workflowModel.strActionPerformed.includes("GC")||row.workflowModel.strActionPerformed.includes("Project Closure Initiated")) {
						    tableData += ' <td class="font_14 text-center grey-text">Pending At GC</span></td> </tr>';
						  } else if (row.workflowModel.strActionPerformed.includes("PMO")) {
						    tableData += ' <td class="font_14 text-center grey-text">Pending At PMO</span></td> </tr>';
						  }
						} 
					else {
						  // Handle the case when row.workflowModel.strActionPerformed is null
						  tableData += ' <td class="font_14 text-center grey-text">Project Closure Not Initiated</span></td> </tr>';
						}
					//End of added columns
					
				}
					else if(valuesList.includes(row.projectRefrenceNo)){
						tableData += '<tr> <td class="golden-text">'+(i+1)+'</td> <td class="font_14 golden-text">'+ row.strGroupName +  '</td><td> <a class="font_14 golden-text" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.strProjectName + ' </a><p class="bold golden-text font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange golden-text">'+row.clientName+'</td>';
						/*tableData += '<tr> <td>'+(i+1)+'</td><td>'+row.strGroupName+'</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
								+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.clientName+'</i></p><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td>';*/
						
					if(row.strPLName==null || row.strPLName==''){
							row.strPLName='';
						}
						tableData += ' <td class="font_16 golden-text">'+ row.strPLName +  '</td>';
						tableData += ' <td class="font_14 text-right golden-text">'+ row.startDate +  '</td> ';
						
						tableData += ' <td class="font_14 text-right golden-text">'+ row.endDate +  '</td>';
						//Added by devesh on 21-09-23 to get cdac outlay and status column in pending closure table
						tableData += ' <td class="font_14 text-right golden-text"> <span id="projectCost_'+row.encProjectId+'">'
						+ row.strProjectCost + '</span></td>';
			
						totalAmount += row.numProjectAmountLakhs;
						
						if (row.workflowModel && row.workflowModel.strActionPerformed) {
							  if (row.workflowModel.strActionPerformed.includes("Sent back to PM")) {
							    tableData += ' <td class="font_14 text-center golden-text">Pending At PL</span></td> </tr>';
							  } else if (row.workflowModel.strActionPerformed.includes("HOD")) {
							    tableData += ' <td class="font_14 text-center golden-text">Pending At HOD</span></td> </tr>';
							  } else if (row.workflowModel.strActionPerformed.includes("GC")||row.workflowModel.strActionPerformed.includes("Project Closure Initiated")) {
							    tableData += ' <td class="font_14 text-center golden-text">Pending At GC</span></td> </tr>';
							  } else if (row.workflowModel.strActionPerformed.includes("PMO")) {
							    tableData += ' <td class="font_14 text-center golden-text">Pending At PMO</span></td> </tr>';
							  }
							} 
						else {
							  // Handle the case when row.workflowModel.strActionPerformed is null
							  tableData += ' <td class="font_14 text-center golden-text">Pending (No Action)</span></td> </tr>';
							}

						//End of added columns
						
					}
				}

					
				$('#pendingCLosure_dataTable').append(tableData);	        
				var  table= $('#pendingCLosure_dataTable').DataTable( {
					dom: 'Bfrtip',		       
			        "ordering": false,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
				"columnDefs": [ {
			            "orderable": false,
		            "targets": 0
		        } ],
		        "order": [[ 1, 'asc' ]]
		    } );
				
				table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
				}).draw();
				
				$('#pendingCLosure_dataTable .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
					/*------------------------ Get the Filter of Status By Order [26-09-2023] ------------------------------------------------*/
						$('#pendingCLosure_dataTable .filters th[id="statusComboBox"]').each( function ( i ) {
					        var colIdx=$(this).index();
					            var select = $('<select style="width:100%" ><option value="">All</option></select>')
					                .appendTo( $(this).empty() )
					                .on( 'change', function () {
					                 var val = $.fn.dataTable.util.escapeRegex(
					                                $(this).val()
					                            );
					                 table.column(colIdx)
					                        .search( val ? '^'+val+'$' : '', true, false)
					                        .draw();
					                } );
				            		var projectStatus = [
						                               "Project Closure Not Initiated",
						                               "Pending At PL",
						                               "Pending At HOD",
						                               "Pending At GC",
						                               "Pending At PMO",
						                               ];
						            projectStatus.forEach(function (d) {
				                         select.append('<option value="' + d + '">' + d + '</option>');
				                     });
					        } );
						/*----------------------------------- EOF [29-08-2023] -------------------------------------------*/		

				 // Setup - add a text input
		       $('#pendingCLosure_dataTable .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

               // DataTable
               var table = $('#pendingCLosure_dataTable').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );
		       		                     
			}
			}
		});
	}
	
//Function for Closed Projects

function closedProjectsTable(){
	//alert("closedProjectsTable");
	var groupColumn = 2;
	var startDate = null;
	var endDate = null;
	var selectedDateRange = $('#asOnDateClosedProjects').text().trim();
	//console.log("selectedDateRange inside function"+selectedDateRange);
	$('#asOnDateClosedProjects1').text(selectedDateRange);
	if(selectedDateRange){
		if(selectedDateRange.includes("to")){
			var inputArray = selectedDateRange.split('to');
			if(inputArray.length >= 2){
				startDate = inputArray[0].trim();
				endDate = inputArray[1].trim();
			}else if(inputArray.length == 1){
				startDate = inputArray[0].trim();				
			}
		}else{
			startDate = selectedDateRange;		
		}		
	}
	if(startDate){		
		/*------------------------------------- Add Some Columns For Closed Projects Tile [12-10-2023] ----------------------------------*/	
		 $('#closedProjects_dataTable').DataTable().clear().destroy();
		 var tableData = '';
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getAllClosedProjectByDate",
				data:{
					"startDate":startDate,
					"endDate":endDate
				},
				success : function(response) {				
					if(response){
						$('#closedProjectsOnDate').text(response.length); 
					}else{
						$('#closedProjectsOnDate').text(0); 
					}
					var totalAmount = 0;
					for (var i = 0; i < response.length; i++) {
						var row = response[i];	
						var strReferenceNumber=row.strReferenceNumber;
						if(!strReferenceNumber){
							strReferenceNumber='';
						}
						
						var rowColor="black";  // update the row color for closed projects [07-03-2024]

						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
						tableData += '<tr style="color:'+rowColor+'"><td class="font_14 text-center">'+ (i+1)+'</td><td class="font_14 text-center">'+ row.strGrouptName+  '</span></td><td><a style="color:'+rowColor+'" class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
								+ row.strProjectName + ' </a><p class="bold font_14 text-left">'+ strReferenceNumber+'<p> </td>';				
						tableData += ' <td><p class="font_14"><i>'+row.strClientName+'</i></p></td><td class="font_16 text-left">'+row.strPLName+'</td> ';
						tableData += ' <td class="font_14 text-center"> <span  class="" >'+ row.startDate+  '</span></td> ';				
						tableData += ' <td class="font_14 text-center">'+ row.endDate+'</td>';
						tableData += ' <td class="font_14 text-center">'+row.projectDuration+'</span></td>';
						tableData += ' <td class="font_14 text-right">'+ row.strTotalCost+  '</span></td>';
						tableData += ' <td class="font_14 text-right">'+ row.strProjectCost+  '</span></td>';
						tableData += ' <td class="font_14 text-right">'+ row.strReceivedAmount+  '</span></td>';
						/*-------- Add Remarks entered by GC Finance While Closing Projects [07-03-2024]  ------*/
						if(row.workflowModel.strRemarks==null || row.workflowModel.strRemarks==''){
							tableData += '<td class="font_14"></td></tr>';
						}else{
							tableData += '<td class="font_14">'+ row.workflowModel.strRemarks+  '</td></tr>';
						}
						/*-------- End of Remarks entered by GC Finance While Closing Projects [07-03-2024]  ------*/
					}
					$('#closedProjects_dataTable').append(tableData); 
					var table= $('#closedProjects_dataTable').DataTable( {
						dom: 'Bfrtip',		       
				        "ordering": false,
				        "paging":   true,
				        buttons: [
				             'excel', 'pdf', 'print'
				        ],
				        "columnDefs": [ {
				            "orderable": false,
			            "targets": 0
			        	} ],
			        	"order": [[ 1, 'asc' ]]
					});	
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					$('#closedProjects_dataTable .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                           
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        });
				}
			});
		}
		/*------------------------------------- END of Closed Projects Tile Table [12-10-2023] ----------------------------------*/		
	}
// function for Joinee Employee-

function newJoineeEmpTable(){
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var dateRange=startDate + " to "+ endDate;
		$("#asOnDateJoinee1").text(dateRange);
		$("#asOnDateJoin").text(dateRange);//Added by devesh on 20-10-23 to update date on filters
		$("#fromJoin").val("");
		$("#toJoin").val("");
		$('#joinee_dataTable').DataTable().clear().destroy();
	 var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getNewJoinedEmployee",
		
			success : function(response) {
				//console.log(response);
				if(response){
					$('#newJoineEmp').text(response.length); 
				}else{
					$('#newJoineEmp').text(0); 
				}
				
				var totalAmount = 0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
					if(row.employmentStatus =='Working'){
					tableData += '<tr> <td>'+(i+1)+'</td>';	
					tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
					
					tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
					tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
				}
					else{
						tableData += '<tr class="red"> <td>'+(i+1)+'</td>';	
						tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
						tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
						tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
						
						tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
						tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					}}

				$('#joinee_dataTable').append(tableData);	        
				var  table= $('#joinee_dataTable').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {

				        "orderable": true,
				            
			            "targets": 0
			        } ]
			       /* "order": [[ 1, 'asc' ]]*/
			    } );
					
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
					$('#joinee_dataTable .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
					

					 // Setup - add a text input
			       $('#joinee_dataTable .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#joinee_dataTable').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );	                     
			}
		});
	}

$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromJoining" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $( "#toJoining" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });

	  $('.go-btn-join-new').click(function() {
		  var startRange = $("#fromJoining").val();
		  var endRange = $("#toJoining").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateJoinee1').text(selectedRange);
		  
		  //alert('Called');
		  newJoineeEmpNew();
	  });
	  
	  
	  
	  function getDate( element ) {
	    var date;
	    try {
	      date = $.datepicker.parseDate( dateFormat, element.value );
	    } catch( error ) {
	      date = null;
	    }

	    return date;
	  }
	});
$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromJoin" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $( "#toJoin" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });

	  $('.go-btn-join').click(function() {
		  var startRange = $("#fromJoin").val();
		  var endRange = $("#toJoin").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateJoinee').text(selectedRange);
		  
		  //alert('Called');
		  newJoineeEmp();
	  });
	  
	  
	  
	  function getDate( element ) {
	    var date;
	    try {
	      date = $.datepicker.parseDate( dateFormat, element.value );
	    } catch( error ) {
	      date = null;
	    }

	    return date;
	  }
	});

function ResignEmpTable(){
	
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var dateRange=startDate + " to "+ endDate;
		$("#asOnDateResign1").text(dateRange);
		$("#fromRes").val("");
		$("#toRes").val("");
		$('#resigned_dataTable').DataTable().clear().destroy();
		var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getResignedEmployee",
		
			success : function(response) {
				//console.log(response);
				if(response){
					$('#newResignEmp').text(response.length); 
				}else{
					$('#newResignEmp').text(0); 
				}
				
				var totalAmount = 0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
		
					tableData += '<tr> <td>'+(i+1)+'</td>';	
					tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
					
					tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td>';			
					tableData += ' <td class="font_14 text-right">'+ row.dateOfResignation +  '</td> <td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
				}

				$('#resigned_dataTable').append(tableData);	        
				var  table= $('#resigned_dataTable').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {
						
						
				            "orderable": true,
				            
			            "targets": 0
			        } ]
			        /*"order": [[ 1, 'asc' ]]*/
			    } );
					
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
					$('#resigned_dataTable .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
					

					 // Setup - add a text input
			       $('#resigned_dataTable .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#resigned_dataTable').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );			                     
			}
		});
	}
$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromRes" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $( "#toRes" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });

	  $('.go-btn-resign').click(function() {
		  var startRange = $("#fromRes").val();
		  var endRange = $("#toRes").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateResign').text(selectedRange);
		  
		  //alert('Called');
		  newResignEmpTable();
	  });
	  
	  function getDate( element ) {
	    var date;
	    try {
	      date = $.datepicker.parseDate( dateFormat, element.value );
	    } catch( error ) {
	      date = null;
	    }

	    return date;
	  }
	});

$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromResign" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $( "#toResign" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });

	  $('.go-btn-resign-new').click(function() {
		  var startRange = $("#fromResign").val();
		  var endRange = $("#toResign").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateResignNew').text(selectedRange);
		  
		  //alert('Called');
		  newResignEmpTableNew();
	  });
	  
	  function getDate( element ) {
	    var date;
	    try {
	      date = $.datepicker.parseDate( dateFormat, element.value );
	    } catch( error ) {
	      date = null;
	    }

	    return date;
	  }
	});
	
function newResignEmpTable(){
	var startDate = null;
	var endDate = null;
	var dateRange='';
	var newdateRange='';
	//var selectedDateRange = $('#asOnDateResign').text().trim();

	var fromDate=$("#fromRes").val();
	var toDate=$("#toRes").val();
	var sDate=$("#startDate").val();
	var eDate=$("#endDate").val();
	if(fromDate==''){
		
		startDate=sDate;
		endDate=eDate;
	
	}
	else{
		startDate=fromDate;
		endDate=toDate;
	}
	if(endDate!=''){
	 dateRange=startDate + " to "+ endDate;
	}
	else{
		dateRange=startDate;
	}
	if(endDate!=''){
	 newdateRange="Since: "+ startDate + " to "+ endDate;
	}
	else{
		 newdateRange="Since: "+ startDate
	}
	$("#asOnDateResign1").text(dateRange);
	//Commented by devesh on 20-10-23 to fix date range changing on tile
	/*$("#newResDate").text(newdateRange);
	document.getElementById("newResDate").style.fontWeight = "600";
	document.getElementById("newResDate").style.fontSize = "13px";*/
	if(startDate){	
		$('#resigned_dataTable').DataTable().clear().destroy();
	 var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getResignedEmployeeDetails",
			data:{
				"startDate":startDate,
				"endDate":endDate
			},
			success : function(response) {
				//console.log(response);
				if(response){
					$('#newResignEmp').text(response.length); 
				}else{
					$('#newResignEmp').text(0); 
				}
				
				/*$("#resignCount").text(response.length);*/ //Commented by devesh on 20-10-23 to stop change of count on tiles
				//$('#resignCount').append('<div class="s-r col-md-3 pad-top" ><h2 ><span id="resignCount"><a style="color:white;" data-toggle="modal" data-target="#dash-resigned-emp-modal">'+response.length+'<span class="pad-left"><i class="material-icons emp_icon">people_outline</i></span></a></span></h2></div>');
				var totalAmount = 0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
		
					tableData += '<tr> <td>'+(i+1)+'</td>';	
					tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
					
					tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td>';			
					tableData += ' <td class="font_14 text-right">'+ row.dateOfResignation +  '</td> <td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
				}

				$('#resigned_dataTable').append(tableData);	        
				var  table= $('#resigned_dataTable').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {
						
						
				            "orderable": true,
				            
			            "targets": 0
			        } ]
			        /*"order": [[ 1, 'asc' ]]*/
			    } );
					
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
					$('#resigned_dataTable .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
					

					 // Setup - add a text input
			       $('#resigned_dataTable .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#resigned_dataTable').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );			                     
			}
		});
	}
	}

function newJoineeEmp(){

	var startDate = null;
	var endDate = null;
	var fromDate=$("#fromJoin").val();
	var toDate=$("#toJoin").val();
	var sDate=$("#startDate").val();
	var eDate=$("#endDate").val();
	var dateRange='';
	var newdateRange='';

		
	if(fromDate==''){
		
		startDate=sDate;
		endDate=eDate;
	
	}
	else{
		startDate=fromDate;
		endDate=toDate;
	}
	if(endDate!=''){
		 dateRange=startDate + " to "+ endDate;
		}
		else{
			dateRange=startDate;
		}
	if(endDate!=''){
		 newdateRange="Since: "+ startDate + " to "+ endDate;
		}
		else{
			 newdateRange="Since: "+ startDate
		}
	$("#asOnDateJoinee1").text(dateRange);
	$("#asOnDateJoin").text(dateRange);//Added by devesh on 20-10-23 to update date on filters
	/*$("#newJoinDate").text(newdateRange);
	document.getElementById("newJoinDate").style.fontWeight = "600";
	document.getElementById("newJoinDate").style.fontSize = "13px";*/ //Commented by devesh on 20-10-23 to stop changing of date on new joinee tiles
	
	
	if(startDate){	
		$('#joinee_dataTable').DataTable().clear().destroy();
	 var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getNewJoinedEmployeeDetails",
			data:{
				"startDate":startDate,
				"endDate":endDate
			},
			success : function(response) {
				//console.log(response);
				if(response){
					$('#newJoineEmp').text(response.length); 
				}else{
					$('#newJoineEmp').text(0); 
				}
				/*$("#joinCount").text(response.length);*/ //Commented by devesh on 20-10-23 to stop changing of count on tiles
				var totalAmount = 0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
					if(row.employmentStatus =='Working'){
					tableData += '<tr> <td>'+(i+1)+'</td>';	
					tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
					
					tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
					tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
				}
					else{
						tableData += '<tr class="red"> <td>'+(i+1)+'</td>';	
						tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
						tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
						tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
						
						tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
						tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					}}

				$('#joinee_dataTable').append(tableData);	        
				var  table= $('#joinee_dataTable').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {

				        "orderable": true,
				            
			            "targets": 0
			        } ]
			       /* "order": [[ 1, 'asc' ]]*/
			    } );
					
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
					$('#joinee_dataTable .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
					

					 // Setup - add a text input
			       $('#joinee_dataTable .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#joinee_dataTable').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );	                     
			}
		});
	}
	}

function openEmployee(){
	
	window.scrollBy(0,1550); 
}

function contractExpDetails(selectedMonth){
	
	//var interval=$("#selectedMonth").val();
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/getContractExpEmployess",
		data:{
			"interval":selectedMonth,
			
		},
		success : function(data) {
			//console.log(data);	
			var tableData = '';
			var involvment='';
			var pendingInv='';
			for(var i=0;i<data.length;i++){
				
				var row = data[i];
				
			
				tableData +='<tr><td class="text-right">'+(i+1)+'  </td><td><p style="color: #1a7dc4;" class="font_14">'+row.employeeId +'</p></td>';
				tableData +='<td class="">'+row.employeeName+'</td></tr>';
				/*tableData +='<td>'+row.strDesignation+'</td></tr>';*/
				/*tableData +='<td>'+row.mobileNumber+'</td>';*/
				/*tableData +='<td><a class="font_14" data-toggle="modal" data-target="#empProjectDetails" data-whatever="'+row.encEmployeeId +';'+row.employeeName+'">'+ involvment + ' </a></td>';
				tableData +='<td>'+pendingInv+'</td></tr>';*/
				
			}
			
			 
			$('#ContractExpEmpDet').DataTable().clear().destroy();
			$('#ContractExpEmpDet tbody').append(tableData);
	
			var  table= $('#ContractExpEmpDet').DataTable( {
				destroy: true,
				dom: 'Bfrtip',		       
		        "ordering": true,
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
					"columnDefs": [ {

			        "orderable": true,
			            
		            "targets": 0
		        } ]
		       /* "order": [[ 1, 'asc' ]]*/
		    } );
				
/*				table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
				}).draw();
				
				$('#employeeDetails .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				

				 // Setup - add a text input
		       $('#employeeDetails .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

               // DataTable
               var table = $('#employeeDetails').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );*/	                     

		}
		
	});
}

function getContractExpDetail(){
	var selectedMonth= $("#selectedMonth").val();
	/*alert(selectedMonth);*/
	if(selectedMonth==''){
		sweetSuccess("Attention","Please Select Month");
	}
	else{
	contractExpDetails(selectedMonth);
	$('#dash-contract-expiring-modal').modal('show');
}}

function generateContractExpReport(){
	var selectedMonth= $("#selectedMonth").val();

	if(selectedMonth!=0){
	openWindowWithPost('POST', '/PMS/generateReportForContractExpEmp', {"eCode":"contractExp","interval":selectedMonth}, '_blank');
}
	}
	
	$('#Employee-Details-modal').on('shown.bs.modal', function(event) {
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getEmployeeDetails",
		success : function(data) {
			//console.log(data);	
			var tableData = '';
			var involvment='';
			var pendingInv='';
			for(var i=0;i<data.length;i++){
				
				var row = data[i];
				if(row.involvment==-1){
					involvment='<span class="red">Not yet mapped</span>'
				}
				else{
					involvment=row.involvment
				}
				
				if(row.pendingInv==-1){
					pendingInv=''
				}
				else{
					pendingInv=row.pendingInv
				}
			
				tableData +='<tr><td class="text-right">'+(i+1)+'  </td><td>'+row.groupName+'</td><td><p class="font_14">'+row.employeeId +'</p></td>';
				tableData +='<td class="">'+row.employeeName+'</td>';
				tableData +='<td>'+row.strDesignation+'</td></tr>';
				/*tableData +='<td>'+row.mobileNumber+'</td>';*/
				/*tableData +='<td><a class="font_14" data-toggle="modal" data-target="#empProjectDetails" data-whatever="'+row.encEmployeeId +';'+row.employeeName+'">'+ involvment + ' </a></td>';
				tableData +='<td>'+pendingInv+'</td></tr>';*/
				
			}
			
			 
			$('#employeeDetails').DataTable().clear().destroy();
			$('#employeeDetails tbody').append(tableData);
	
			var  table= $('#employeeDetails').DataTable( {
				destroy: true,
				dom: 'Bfrtip',		       
		        "ordering": true,
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
					"columnDefs": [ {

			        "orderable": true,
			            
		            "targets": 0
		        } ]
		       /* "order": [[ 1, 'asc' ]]*/
		    } );
				
				table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
				}).draw();
				
				$('#employeeDetails .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				

				 // Setup - add a text input
		       $('#employeeDetails .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

               // DataTable
               var table = $('#employeeDetails').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );	                     

		}
		
	});
});

$('#Employee-UnderUti-Details-modal').on('shown.bs.modal', function(event) {
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getUnderUtiEmployeeDetails",
		success : function(data) {
			//console.log(data);	
			var tableData = '';
			var involvment='';
			var pendingInv='';
			for(var i=0;i<data.length;i++){
				
				var row = data[i];
				if(row.involvment==-1){
					involvment='<span class="red">Not yet mapped</span>'
				}
				else{
					involvment=row.involvment
				}
				
				if(row.pendingInv==-1){
					pendingInv=''
				}
				else{
					pendingInv=row.pendingInv
				}
			
				tableData +='<tr><td class="text-right">'+(i+1)+'  </td><td>'+row.groupName+'</td><td><p  class="font_14">'+row.employeeId +'</p></td>';
				tableData +='<td class="">'+row.employeeName+'</td>';
				tableData +='<td>'+row.strDesignation+'</td>';
				/*tableData +='<td>'+row.mobileNumber+'</td>';*/
				tableData +='<td><a class="font_14" data-toggle="modal" data-target="#empProjectDetails" data-whatever="'+row.encEmployeeId +';'+row.employeeName+'">'+ involvment + ' </a></td>';
				tableData +='<td>'+pendingInv+'</td></tr>';
				
			}
			
			 
			$('#underUtiEmployeeDetails').DataTable().clear().destroy();
			$('#underUtiEmployeeDetails tbody').append(tableData);
	
			var  table= $('#underUtiEmployeeDetails').DataTable( {
				destroy: true,
				dom: 'Bfrtip',		       
		        "ordering": true,
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
					"columnDefs": [ {

			        "orderable": true,
			            
		            "targets": 0
		        } ]
		       /* "order": [[ 1, 'asc' ]]*/
		    } );
				
				table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
				}).draw();
				
				$('#underUtiEmployeeDetails .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				

				 // Setup - add a text input
		       $('#underUtiEmployeeDetails .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

               // DataTable
               var table = $('#underUtiEmployeeDetails').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );	                     

		}
		
	});
});

$('#empProjectDetails').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	var inputs=input.split(';');
	var empId=inputs[0];
	var empName=inputs[1];

	$('#employeeName').text(empName);
	
	var tableData = "";
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/activeRolesByEmpId",
		data : {"encEmpId": empId},
		async:false,
		success : function(response) {
		//console.log(response);
		for(var i =0;i<response.length;i++){
			var row=response[i];
			
			var serialNo=i+1;
			if(row.primaryProject == 1)
			tableData+='<tr class="orange bg_grey"><td >'+serialNo+'</td>';
			else
				tableData+='<tr ><td >'+serialNo+'</td>';

			tableData+='<td >'+row.strProjectName+'</td>';
			tableData+='<td >'+row.strRoleName+'</td>';
			tableData+='<td >'+row.strStartDate+'</td>';
			if(row.strEndDate !=null){
				tableData+='<td >'+row.strEndDate+'</td>';
			}
			else{
				tableData+='<td></td>'
			}
			tableData+='<td >'+row.involvement+'</td>';
			
			tableData+='<td class="hidden ">'+row.primaryProject+'</td>  ';

			tableData+='</tr>';
			//console.log(row.primaryProject);
			

		}
		
			tableData+='</tbody>';
			$('#empInvTable tbody').empty();						
			$('#empInvTable').append(tableData);	
			
		},
		error : function(e) {
			alert('Error: ' + e);			
		}
	});				
});


function loadEmployeeDetails(){
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/loadEmployeeDetailsCount",
	
		success : function(data) {	
			//console.log(data);
			if($.isNumeric(data)){
				
				$('#empDetailsCount').text(data);
			}else{
				$('#empDetailsCount').text(0);
			}
			
		}
	});
}

function loadUnderUtiEmpDetails(){
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/loadUnderUtiEmployeeCount",
	
		success : function(data) {	
			//console.log(data);
			if($.isNumeric(data)){
				
				$('#empUnderUtilCount').text(data);
			}else{
				$('#empUnderUtilCount').text(0);
			}
			
		}
	});
}
$(document).ready(function(){
	loadEmployeeDetails();
	loadUnderUtiEmpDetails();
});

function loadEmployeeDataByEmploymentType(employmentType){
	$('#employmentType_datatable').DataTable().clear().destroy();
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getEmployeesByEmploymentType",
		data : {"employeeTypeName": employmentType},
		success : function(data) {
			//Third Party Contract
			console.log(data);
			var tableData ='';
			for(var i=0; i< data.length ;i++){
				var rowData = data[i];
				tableData +="<tr> <td>"+rowData.numId+"</td> <td>"+rowData.employeeName+"</td>";
				tableData +="<td>"+rowData.officeEmail+"</td> <td>"+rowData.strDesignation+"</td> <td>"+rowData.groupName+"</td>";
				if(employmentType=='Third Party Contract'){
					console.log('if');
					tableData +="<td>"+rowData.thirdPartyName+"</td>";
				}else{
					console.log('else');
					tableData +="<td class='hidden'>"+rowData.thirdPartyName+"</td>";
				}
				
				tableData +="</tr>";
			}
			$('#employmentType_datatable > tbody').html('');
			$('#employmentType_datatable').append(tableData);

			 table= $('#employmentType_datatable').DataTable( {
			        dom: 'Bfrtip',
			        "columnDefs": [ {
			            "orderable": false,
			        "targets": 0
			    } ],
			    "order": [[ 1, 'asc' ]],
			        buttons: [
			             'excel', 'pdf', 'print'
			        ]
			    } );
			 
			 table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
				}).draw();
				
				$('#employmentType_datatable .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				

				 // Setup - add a text input
		       $('#employmentType_datatable .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

            // DataTable
            var table = $('#employmentType_datatable').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );	                     

		}
	});
	
	if(employmentType=='Third Party Contract'){
		$('#agencyId_col').removeClass('hidden');
	}else{
		$('#agencyId_col').addClass('hidden');
	}
	
	$('#employeeDataByEmploymentType').modal('show');
}
function loadEmployeeDataBy(groupName){

//alert("loadEmployeeDataBy");
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	
	function drawChart() {
		
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getEmployeeCountByEmployementType",
			data : {"groupName": groupName},
			success : function(data) {
				employmentTypeWiseCount = data;

				console.log(employmentTypeWiseCount);
				
				var data = google.visualization
						.arrayToDataTable(employmentTypeWiseCount);
				var count = data.getNumberOfRows();
				var values = Array(count).fill().map(function(v, i) {
					return data.getValue(i, 1);
				});
				var total = google.visualization.data.sum(values);
				// console.log(total);
				values.forEach(function(v, i) {
					var key = data.getValue(i, 0);
					// console.log(key);
					var val = data.getValue(i, 1);
					//console.log(val);
					data.setFormattedValue(i, 0, key + ' (' + (val) + ')');
				});
				var options = {

					is3D : true,
					width : 800,
					height : 300,

					pieSliceText : 'value',
					pieSliceTextStyle : {
						color : 'white',
						fontSize : 14,
					},
					legend : {
						text : 'value',
						position : 'right',
						textStyle : {
							fontSize : 14,
							color : 'red'
						}
					},

					colors : [ '#b36686', '#532aa1', '#90a603', '#3fbbc4' ],
				};

				var chart = new google.visualization.PieChart(document
						.getElementById('chart-container-empNew'));
				
				/* google.visualization.events.addListener(chart, 'select', selectHandler);*/
		
					chart.draw(data, options);

			}
		});
				
	}

$('#employeeWithInvolvementsTbl').DataTable().clear().destroy();
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/employeeDetailsWithInvolvementsGroupWise",
		data : {"groupName": groupName},
		success : function(data) {
			//Third Party Contract
			console.log(data);
			var tableData ='';
			
			for(var i=0; i< data.length ;i++){
				var rowData = data[i];
				tableData +="<tr> <td>"+rowData.numId+"</td> <td>"+rowData.employeeId+"</td>";
				tableData +="<td>"+rowData.employeeName+"</td> <td>"+rowData.strDesignation+"</td> <td>"+rowData.employeeTypeName+"</td>";
			
				
				tableData +="</tr>";
			}
			$('#employeeWithInvolvementsTbl > tbody').html('');
			$('#employeeWithInvolvementsTbl').append(tableData);

			/* table= $('#employeeWithInvolvementsTbl').DataTable( {
			        dom: 'Bfrtip',
			        "columnDefs": [ {
			            "orderable": false,
			        "targets": 0
			    } ],
			    "order": [[ 1, 'asc' ]],
			        buttons: [
			             'excel', 'pdf', 'print'
			        ]
			    } );*/
			 
				var  table= $('#employeeWithInvolvementsTbl').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {

				        "orderable": true,
				            
			            "targets": 0
			        } ],
			        "order": [[ 1, 'asc' ]]
			    } );
					
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
					$('#employeeWithInvolvementsTbl .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
					

					 // Setup - add a text input
			       $('#employeeWithInvolvementsTbl .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#employeeWithInvolvementsTbl').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );
		}
	});
	
	loadEmployeeGenderWise(groupName);
	loadDesignationWiseDistribution(groupName);

	$('#dash-employee-modal').modal('show');
}

function loadEmployeeGenderWise(groupName){
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);  
	
	
	function drawChart() {
		
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getGroupGenderWiseEmployeeCount",
			data : {"groupName": groupName},
			success : function(data) {
				barGraphEmployees = data;
				
				var data = google.visualization
						.arrayToDataTable(barGraphEmployees);
				var count = data.getNumberOfRows();
				var values = Array(count).fill().map(function(v, i) {
					return data.getValue(i, 1);
				});
				var total = google.visualization.data.sum(values);
				// console.log(total);
				values.forEach(function(v, i) {
					var key = data.getValue(i, 0);
					// console.log(key);
					var val = data.getValue(i, 1);
					//console.log(val);
					data.setFormattedValue(i, 0, key + ' (' + (val) + ')');
				});
				var options = {
					is3D : true,
					width : 800,
					height : 300,

					pieSliceText : 'value',
					pieSliceTextStyle : {
						color : 'white',
						fontSize : 14,
					},
					legend : {
						text : 'value',
						position : 'right',
						textStyle : {
							fontSize : 14,
							color : 'red'
						}
					},

					colors : [ '#e31b7f', '#0291d9' ],
				};

				var chart = new google.visualization.PieChart(document
						.getElementById('chart-container-empNew1'));
				
				chart.draw(data, options);
			}
		});
		
	}
}

function loadDesignationWiseDistribution(groupName){
	//$('#testTblNew').DataTable().clear().destroy();
/*	if ( $.fn.DataTable.isDataTable('#testTblNew') ) {
		  $('#testTblNew').DataTable().clear().destroy();
		} */
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getDesignationWiseEmployee",
		data : {"groupName": groupName},
		success : function(response) {
			//Third Party Contract
			console.log(response);
			var tableHead ='<thead><tr> <th>Group</th>';
			var tableData='<tbody><tr><td>'+groupName+'</td>';
			
			//tableHead +="<tr><th>"+groupName+"</th>";
       		for(var i=0; i< response.length ;i++){
				var rowData = response[i];
				tableHead +="<th id='"+rowData.strDesignationName+"' class='sorting'> "+rowData.strDesignationName+"</th>";
				tableData +="<td class='text-right emp'><a title='Click here to view Employee details'> "+rowData.count+"</a></td>";
	
			}
       		
       		tableHead +='</tr></thead>';
			tableData +='</tr></tbody>';
       		
			$('#testTblNew > thead').html('');
			$('#testTblNew > tbody').html('');
			$('#testTblNew').append(tableHead);
			$('#testTblNew').append(tableData);
		
				var  table= $('#testTblNew').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {

				        "orderable": true,
				            
			            "targets": 0
			        } ],
			        "order": [[ 1, 'asc' ]]
			    } );
				$(".emp").on("click", getColumnID);
		}
	});
}

function loadEmployeeDataByCategory(categoryName){

	$('#employeeCategoryWiseTbl').DataTable().clear().destroy();
		
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/employeeDetailsByCategory",
			data : {"category": categoryName},
			success : function(data) {
				//Third Party Contract
				console.log(data);
				var tableData ='';
				for(var i=0; i< data.length ;i++){
					var rowData = data[i];
					tableData +="<tr> <td>"+rowData.numId+"</td> <td>"+rowData.numId+"</td>";
					tableData +="<td>"+rowData.employeeName+"</td> <td>"+rowData.strDesignation+"</td> <td>"+rowData.employeeTypeName; 
					if(null!=rowData.thirdPartyName){
					tableData +="("+rowData.thirdPartyName +")";
				    }
				    
					tableData += "</td><td>"+rowData.groupName+"</td>";	
					
					
					tableData +="</tr>";
				}
				$('#employeeCategoryWiseTbl > tbody').html('');
				$('#employeeCategoryWiseTbl').append(tableData);

				/* table= $('#employeeWithInvolvementsTbl').DataTable( {
				        dom: 'Bfrtip',
				        "columnDefs": [ {
				            "orderable": false,
				        "targets": 0
				    } ],
				    "order": [[ 1, 'asc' ]],
				        buttons: [
				             'excel', 'pdf', 'print'
				        ]
				    } );*/
				 
					var  table= $('#employeeCategoryWiseTbl').DataTable( {
						destroy: true,
						dom: 'Bfrtip',		       
				        "ordering": true,
				        "paging":   false,
				        buttons: [
				             'excel', 'pdf', 'print'
				        ],
							"columnDefs": [ {

					        "orderable": true,
					            
				            "targets": 0
				        } ]
				       /* "order": [[ 1, 'asc' ]]*/
				    } );
						
						table.on('order.dt search.dt', function () {
							table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
						           cell.innerHTML = i+1;
						           table.cell(cell).invalidate('dom');
						     });
						}).draw();
						
						$('#employeeCategoryWiseTbl .filters th[class="comboBox"]').each( function ( i ) {
					        var colIdx=$(this).index();
					            var select = $('<select style="width:100%" ><option value="">All</option></select>')
					                .appendTo( $(this).empty() )
					                .on( 'change', function () {
					                 var val = $.fn.dataTable.util.escapeRegex(
					                                $(this).val()
					                            );
					                 table.column(colIdx)
					                        .search( val ? '^'+val+'$' : '', true, false)
					                        .draw();
					                } );
					     
					            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
					                select.append( '<option value="'+d+'">'+d+'</option>' )
					            } );
					        } );
						

						 // Setup - add a text input
				       $('#employeeCategoryWiseTbl .filters th[class="textBox"]').each( function () {                 
				           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

				       } );

		               // DataTable
		               var table = $('#employeeCategoryWiseTbl').DataTable();
				       
				       table.columns().eq( 0 ).each( function ( colIdx ) {
				           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
				               table
				                           .column( colIdx )
				                           .search( this.value )
				                           .draw() ;
				                   
				                                           } );
				} );
			}
		});
		

		$('#dash-employee-category-modal').modal('show');
	}



$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#from1" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	        $('.monthsClosedProjects').val(0); //reset months fields [12-10-2023]
	      }),
	    to = $( "#to1" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	      $('.monthsClosedProjects').val(0);
	    });
	  /*----- Change the go button class name of closed project [12-10-2023]] ---------*/
	  $('.goClosed-btn').click(function() {
		  var startRange = $("#from1").val();
		  var endRange = $("#to1").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		 
		  $('#asOnDateClosedProjects').text(selectedRange);
		  //alert('Called');
		  closedProjectsTable();
	  });
	  
	  function getDate( element ) {
	    var date;
	    try {
	      date = $.datepicker.parseDate( dateFormat, element.value );
	    } catch( error ) {
	      date = null;
	    }

	    return date;
	  }
	});

function loadEmployeeJoinResignDetails(year, type){
	var fromDate='01/01/'+year+'';
	var toDate='31/12/'+year+'';
	
	if(type==1){
		$('#dash-new-joinee-modal-New').modal('show');
		$("#fromJoining").val(fromDate);
		$("#toJoining").val(toDate);
		newJoineeEmpNew();
	}
	else{
		$('#dash-resigned-emp-modal_New').modal('show');
		$("#fromResign").val(fromDate);
		$("#toResign").val(toDate);
		newResignEmpTableNew();
	}
	
}

function newJoineeEmpNew(){

	var startDate = null;
	var endDate = null;

	var sDate=$("#startDate").val();
	var eDate=$("#endDate").val();
	var fromJoin=$("#fromJoining").val();
	var toJoin=$("#toJoining").val();
	var dateRange='';
	var newdateRange='';

		if(fromJoin==''){
			
			startDate=sDate;
			endDate=eDate;
		
		}
		else{
			startDate=fromJoin;
			endDate=toJoin;
		}
		if(endDate!=''){
			 dateRange=startDate + " to "+ endDate;
			}
			else{
				dateRange=startDate;
			}
		if(endDate!=''){
			 newdateRange="Since: "+ startDate + " to "+ endDate;
			}
			else{
				 newdateRange="Since: "+ startDate
			}
		$("#asOnDateJoinee1").text(dateRange);
		//$("#newJoinDate").text(newdateRange);
		document.getElementById("newJoinDate").style.fontWeight = "600";
		document.getElementById("newJoinDate").style.fontSize = "13px";

	if(startDate){	
		$('#joinee_dataTable_new').DataTable().clear().destroy();
	 var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getNewJoinedEmployeeDetails",
			data:{
				"startDate":startDate,
				"endDate":endDate
			},
			success : function(response) {
				//console.log(response);
				if(response){
					$('#newJoineEmp').text(response.length); 
				}else{
					$('#newJoineEmp').text(0); 
				}
				//$("#joinCount").text(response.length);
				var totalAmount = 0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
					if(row.employmentStatus =='Working'){
					tableData += '<tr> <td>'+(i+1)+'</td>';	
					tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
					tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
					
					tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
					tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
				}
					else{
						tableData += '<tr class="red"> <td>'+(i+1)+'</td>';	
						tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
						tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
						tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
						
						tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
						tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
					}}

				$('#joinee_dataTable_new').append(tableData);	        
				var  table= $('#joinee_dataTable_new').DataTable( {
					destroy: true,
					dom: 'Bfrtip',		       
			        "ordering": true,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
						"columnDefs": [ {

				        "orderable": true,
				            
			            "targets": 0
			        } ]
			       /* "order": [[ 1, 'asc' ]]*/
			    } );
					
					table.on('order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
					           cell.innerHTML = i+1;
					           table.cell(cell).invalidate('dom');
					     });
					}).draw();
					
					$('#joinee_dataTable_new .filters th[class="comboBox"]').each( function ( i ) {
				        var colIdx=$(this).index();
				            var select = $('<select style="width:100%" ><option value="">All</option></select>')
				                .appendTo( $(this).empty() )
				                .on( 'change', function () {
				                 var val = $.fn.dataTable.util.escapeRegex(
				                                $(this).val()
				                            );
				                 table.column(colIdx)
				                        .search( val ? '^'+val+'$' : '', true, false)
				                        .draw();
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
					

					 // Setup - add a text input
			       $('#joinee_dataTable_new .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#joinee_dataTable_new').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );	                     
			}
		});
	}
	}

function newResignEmpTableNew(){
var startDate = null;
var endDate = null;
var dateRange='';
var newdateRange='';
//var selectedDateRange = $('#asOnDateResign').text().trim();

var fromDate=$("#fromResign").val();
var toDate=$("#toResign").val();
var sDate=$("#startDate").val();
var eDate=$("#endDate").val();
if(fromDate==''){
	
	startDate=sDate;
	endDate=eDate;

}
else{
	startDate=fromDate;
	endDate=toDate;
}
if(endDate!=''){
 dateRange=startDate + " to "+ endDate;
}
else{
	dateRange=startDate;
}
if(endDate!=''){
 newdateRange="Since: "+ startDate + " to "+ endDate;
}
else{
	 newdateRange="Since: "+ startDate
}
$("#asOnDateResignNew").text(dateRange);

/*document.getElementById("newResDate").style.fontWeight = "600";
document.getElementById("newResDate").style.fontSize = "13px";*/
if(startDate){	
	$('#resigned_dataTable_new').DataTable().clear().destroy();
 var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getResignedEmployeeDetails",
		data:{
			"startDate":startDate,
			"endDate":endDate
		},
		success : function(response) {
			//console.log(response);
			if(response){
				$('#newResignEmp').text(response.length); 
			}else{
				$('#newResignEmp').text(0); 
			}
			
			//$("#resignCount").text(response.length);
			//$('#resignCount').append('<div class="s-r col-md-3 pad-top" ><h2 ><span id="resignCount"><a style="color:white;" data-toggle="modal" data-target="#dash-resigned-emp-modal">'+response.length+'<span class="pad-left"><i class="material-icons emp_icon">people_outline</i></span></a></span></h2></div>');
			var totalAmount = 0;
			for (var i = 0; i < response.length; i++) {
				var row = response[i];	
	
				tableData += '<tr> <td>'+(i+1)+'</td>';	
				tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
				tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
				tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
				
				tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td>';			
				tableData += ' <td class="font_14 text-right">'+ row.dateOfResignation +  '</td> <td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
				/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
			}

			$('#resigned_dataTable_new').append(tableData);	        
			var  table= $('#resigned_dataTable_new').DataTable( {
				destroy: true,
				dom: 'Bfrtip',		       
		        "ordering": true,
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
					"columnDefs": [ {
					
					
			            "orderable": true,
			            
		            "targets": 0
		        } ]
		        /*"order": [[ 1, 'asc' ]]*/
		    } );
				
				table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
				}).draw();
				
				$('#resigned_dataTable_new .filters th[class="comboBox"]').each( function ( i ) {
			        var colIdx=$(this).index();
			            var select = $('<select style="width:100%" ><option value="">All</option></select>')
			                .appendTo( $(this).empty() )
			                .on( 'change', function () {
			                 var val = $.fn.dataTable.util.escapeRegex(
			                                $(this).val()
			                            );
			                 table.column(colIdx)
			                        .search( val ? '^'+val+'$' : '', true, false)
			                        .draw();
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				

				 // Setup - add a text input
		       $('#resigned_dataTable_new .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

               // DataTable
               var table = $('#resigned_dataTable_new').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );			                     
		}
	});
}
}

// Added filters in closure pending on 09-06-2023

function calculateClosureDates(){
	var d = new Date();
	var symbol= $("#filterClosureSymbol").val();
	var selValue=$("#monthsClosure").val();
	
	if(selValue=='0'){
		
		
		$('#pendingCLosure_dataTable').empty();
		$('#pendingCLosure_dataTable').DataTable().destroy();
		var foot = $("#pendingCLosure_dataTable").find('tfoot');
		 if(foot.length){
			 $("#pendingCLosure_dataTable tfoot").remove();
		 }
		var  table= $('#pendingCLosure_dataTable').DataTable( {
			destroy: true,
			dom: 'Bfrtip',		       
	        "ordering": false,
	        "paging":   false,
	        buttons: [
	             'excel', 'pdf', 'print'
	        ],
	        dom: 'Bfrtip',
			"columnDefs": [ {
				
		        "orderable": false,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );
	}
	else{
	var lastMonth = new Date();
	lastMonth.setMonth(lastMonth.getMonth() - 1);
	var durationBefore=selValue;
	d.setMonth(d.getMonth() - durationBefore);
	 var lastDayOfLastMonth=lastday(lastMonth.getFullYear(),lastMonth.getMonth());
	 var fromMonth=d.getMonth()+1;
	 var toMonth=lastMonth.getMonth()+1;
	 fromMonth=fromMonth+"";
	 toMonth=toMonth+"";
	 lastDayOfLastMonth=lastDayOfLastMonth+"";
	 if(fromMonth.length==1)
	 {
		 fromMonth="0"+fromMonth; 
	 }	 
	 if(toMonth.length==1)
		{ 
		 toMonth="0"+toMonth;
		}
	
		var closureDate="01"+"/"+fromMonth+"/"+d.getFullYear();


		pendingClosuresTableByClosureDate(closureDate,symbol);
	}
	}
var lastday = function(y,m){
	return  new Date(y, m, 0).getDate();
	}
function pendingClosuresTableByClosureDate(closureDate,symbol){	
	
	
	$('#pendingCLosure_dataTable').DataTable().clear().destroy();
 var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getPendingClosureDetailByDate",
		data : {"closureDate":closureDate,
			"symbol":symbol
			},
		success : function(response) {
			//console.log(response);
			if(response){
			var totalAmount = 0;
			/*for (var i = 0; i < response.length; i++) {
				var row = response[i];	
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
						+ row.encNumId + "')";
				
				tableData += '<tr> <td>'+(i+1)+'</td> <td class="font_14">'+ row.strGroupName  +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
				+ row.strProjectName + ' </a><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange">'+row.clientName+'</td>';
				tableData += '<tr> <td>'+(i+1)+'</td><td>'+row.strGroupName+'</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
				+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.clientName+'</i></p><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td>';
				
				tableData += '<tr> <td>'+(i+1)+'</td> <td class="font_14">'+ row.strGroupName  +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
				+ row.strProjectName + ' </a><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange">'+row.clientName+'</td>';
				tableData += '<tr> <td>'+(i+1)+'</td> <td class="font_14">'+ row.strGroupName  +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.strProjectName + ' </a><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange">'+row.clientName+'</td>';
				tableData += ' <td class="bold blue font_14 text-right">'+ row.projectRefrenceNo +  '</td>';	
				tableData += ' <td class="bold orange font_12">'+ row.clientName +  '</td> ';	
				
				if(row.strPLName==null || row.strPLName==''){
					row.strPLName='';
				}
				tableData += ' <td class="font_16">'+ row.strPLName +  '</td>';
				tableData += ' <td class="font_14 text-right">'+ row.startDate +  '</td> ';
				
				tableData += ' <td class="font_14 text-right">'+ row.endDate +  '</td>  </tr>';		
				
				
			}*/
			/*Bhavesh(16-08-23)*/ 
			var valuesList = []; // Initialize an empty array to store the values
		    $(".underclos").each(function() {
		      var value = $(this).text().trim(); // Get the text content and remove any leading/trailing spaces
		      valuesList.push(value); // Add the value to the array
		    });

		   
		    //console.log(valuesList); 
			for (var i = 0; i < response.length; i++) {
				var row = response[i];	
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
						+ row.encNumId + "')";
				if(!valuesList.includes(row.projectRefrenceNo)){
				tableData += '<tr> <td class="grey-text">'+(i+1)+'</td> <td class="font_14 grey-text">'+ row.strGroupName +  '</td><td> <a class="font_14 grey-text" title="Click to View Complete Information" onclick='+clickFunction+'>'
				+ row.strProjectName + ' </a><p class="bold grey-text font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange grey-text">'+row.clientName+'</td>';
				/*tableData += '<tr> <td>'+(i+1)+'</td><td>'+row.strGroupName+'</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.clientName+'</i></p><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td>';*/
				
			if(row.strPLName==null || row.strPLName==''){
					row.strPLName='';
				}
				tableData += ' <td class="font_16 grey-text">'+ row.strPLName +  '</td>';
				tableData += ' <td class="font_14 text-right grey-text">'+ row.startDate +  '</td> ';
				
				tableData += ' <td class="font_14 text-right grey-text">'+ row.endDate +  '</td>';
				//Added by devesh on 21-09-23 to get cdac outlay and status column in pending closure table
				tableData += ' <td class="font_14 text-right grey-text"> <span id="projectCost_'+row.encProjectId+'">'
				+ row.strProjectCost + '</span></td>';
	
				totalAmount += row.numProjectAmountLakhs;
				
				if (row.workflowModel && row.workflowModel.strActionPerformed) {
					  if (row.workflowModel.strActionPerformed.includes("Sent back to PM")) {
					    tableData += ' <td class="font_14 text-center grey-text">Pending At PL</span></td> </tr>';
					  } else if (row.workflowModel.strActionPerformed.includes("HOD")) {
					    tableData += ' <td class="font_14 text-center grey-text">Pending At HOD</span></td> </tr>';
					  } else if (row.workflowModel.strActionPerformed.includes("GC")||row.workflowModel.strActionPerformed.includes("Project Closure Initiated")) {
					    tableData += ' <td class="font_14 text-center grey-text">Pending At GC</span></td> </tr>';
					  } else if (row.workflowModel.strActionPerformed.includes("PMO")) {
					    tableData += ' <td class="font_14 text-center grey-text">Pending At PMO</span></td> </tr>';
					  }
					} 
				else {
					  // Handle the case when row.workflowModel.strActionPerformed is null
					  tableData += ' <td class="font_14 text-center grey-text">Project Closure Not Initiated</span></td> </tr>';
					}
				//End of added columns
				
			}
				else if(valuesList.includes(row.projectRefrenceNo)){
					tableData += '<tr> <td class="golden-text">'+(i+1)+'</td> <td class="font_14 golden-text">'+ row.strGroupName +  '</td><td> <a class="font_14 golden-text" title="Click to View Complete Information" onclick='+clickFunction+'>'
					+ row.strProjectName + ' </a><p class="bold golden-text font_14 text-left">'+ row.projectRefrenceNo+'<p></td><td class="font_14 orange golden-text">'+row.clientName+'</td>';
					/*tableData += '<tr> <td>'+(i+1)+'</td><td>'+row.strGroupName+'</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
							+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.clientName+'</i></p><p class="bold blue font_14 text-left">'+ row.projectRefrenceNo+'<p></td>';*/
					
				if(row.strPLName==null || row.strPLName==''){
						row.strPLName='';
					}
					tableData += ' <td class="font_16 golden-text">'+ row.strPLName +  '</td>';
					tableData += ' <td class="font_14 text-right golden-text">'+ row.startDate +  '</td> ';
					
					tableData += ' <td class="font_14 text-right golden-text">'+ row.endDate +  '</td>';
					//Added by devesh on 21-09-23 to get cdac outlay and status column in pending closure table
					tableData += ' <td class="font_14 text-right golden-text"> <span id="projectCost_'+row.encProjectId+'">'
					+ row.strProjectCost + '</span></td>';
		
					totalAmount += row.numProjectAmountLakhs;
					
					if (row.workflowModel && row.workflowModel.strActionPerformed) {
						  if (row.workflowModel.strActionPerformed.includes("Sent back to PM")) {
						    tableData += ' <td class="font_14 text-center golden-text">Pending At PL</span></td> </tr>';
						  } else if (row.workflowModel.strActionPerformed.includes("HOD")) {
						    tableData += ' <td class="font_14 text-center golden-text">Pending At HOD</span></td> </tr>';
						  } else if (row.workflowModel.strActionPerformed.includes("GC")||row.workflowModel.strActionPerformed.includes("Project Closure Initiated")) {
						    tableData += ' <td class="font_14 text-center golden-text">Pending At GC</span></td> </tr>';
						  } else if (row.workflowModel.strActionPerformed.includes("PMO")) {
						    tableData += ' <td class="font_14 text-center golden-text">Pending At PMO</span></td> </tr>';
						  }
						} 
					else {
						  // Handle the case when row.workflowModel.strActionPerformed is null
						  tableData += ' <td class="font_14 text-center golden-text">Pending (No Action)</span></td> </tr>';
						}

					//End of added columns
				}
			}

				
			$('#pendingCLosure_dataTable').append(tableData);	        
			var  table= $('#pendingCLosure_dataTable').DataTable( {
				dom: 'Bfrtip',		       
		        "ordering": false,
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
			"columnDefs": [ {
		            "orderable": false,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );
			
			table.on('order.dt search.dt', function () {
				table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
			           cell.innerHTML = i+1;
			           table.cell(cell).invalidate('dom');
			     });
			}).draw();
			
			$('#pendingCLosure_dataTable .filters th[class="comboBox"]').each( function ( i ) {
		        var colIdx=$(this).index();
		            var select = $('<select style="width:100%" ><option value="">All</option></select>')
		                .appendTo( $(this).empty() )
		                .on( 'change', function () {
		                 var val = $.fn.dataTable.util.escapeRegex(
		                                $(this).val()
		                            );
		                 table.column(colIdx)
		                        .search( val ? '^'+val+'$' : '', true, false)
		                        .draw();
		                } );
		     
		            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
		                select.append( '<option value="'+d+'">'+d+'</option>' )
		            } );
		        } );
				/*------------------------ Get the Filter of Status By Order [26-09-2023] ------------------------------------------------*/
						$('#pendingCLosure_dataTable .filters th[id="statusComboBox"]').each( function ( i ) {
					        var colIdx=$(this).index();
					            var select = $('<select style="width:100%" ><option value="">All</option></select>')
					                .appendTo( $(this).empty() )
					                .on( 'change', function () {
					                 var val = $.fn.dataTable.util.escapeRegex(
					                                $(this).val()
					                            );
					                 table.column(colIdx)
					                        .search( val ? '^'+val+'$' : '', true, false)
					                        .draw();
					                } );
				            		var projectStatus = [
						                               "Project Closure Not Initiated",
						                               "Pending At PL",
						                               "Pending At HOD",
						                               "Pending At GC",
						                               "Pending At PMO"
						                               ];
						            projectStatus.forEach(function (d) {
				                         select.append('<option value="' + d + '">' + d + '</option>');
				                     });
					        } );
						/*----------------------------------- EOF [29-08-2023] -------------------------------------------*/

			 // Setup - add a text input
	       $('#pendingCLosure_dataTable .filters th[class="textBox"]').each( function () {                 
	           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

	       } );

           // DataTable
           var table = $('#pendingCLosure_dataTable').DataTable();
	       
	       table.columns().eq( 0 ).each( function ( colIdx ) {
	           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
	               table
	                           .column( colIdx )
	                           .search( this.value )
	                           .draw() ;
	                   
	                                           } );
	} );	                     
		}
		}
	});
}	

//added the calculate date function on 09-06-2023
function calculateDates(selValue,fromId,toId,btnIdOrClass,type){
	var d = new Date();
	
	var lastMonth = new Date();
	lastMonth.setMonth(lastMonth.getMonth());//Bhavesh [17-10-23] changed (lastMonth.getMonth() - 1) to (lastMonth.getMonth()) to get current month
	var durationBefore=selValue;
	d.setMonth(d.getMonth() - durationBefore);
	 var lastDayOfLastMonth=lastMonth.getDate();/*lastday(lastMonth.getFullYear(),lastMonth.getMonth());*/ //Bhavesh [17-10-23] changed lastday(lastMonth.getFullYear(),lastMonth.getMonth()) to lastMonth.getDate() to get the current Date
	 var fromMonth=d.getMonth()+1;
	 var toMonth=lastMonth.getMonth()+1;
	 fromMonth=fromMonth+"";
	 toMonth=toMonth+"";
	 lastDayOfLastMonth=lastDayOfLastMonth+"";
	 if(fromMonth.length==1)
	 {
		 fromMonth="0"+fromMonth; 
	 }	 
	 if(toMonth.length==1)
		{ 
		 toMonth="0"+toMonth;
		}
	if(selValue!=0)
	{
		$("#"+fromId).val("01"+"/"+fromMonth+"/"+d.getFullYear());
		$("#"+toId).val(lastDayOfLastMonth+"/"+toMonth+"/"+lastMonth.getFullYear());
		
	}
	else
		{
			$("#"+fromId).val("");
			$("#"+toId).val("");
			
		}
	if(type==1)
	$( "#"+btnIdOrClass ).trigger( "click" );	
	else	
	$( "."+btnIdOrClass ).trigger( "click" );
	
	
}

/*added the function for maximize minimize and restore the values on 03-07-23 by varun */
var isMaximized = false;
function toggleMaximize() {
	 // var myModal = $(".myModal1");
	 
	  if (isMaximized) {
	  // myModal.classList.remove("maximized");
		  $(".myModal1").removeClass("maximized");
		 
	    //document.body.classList.remove("modal-open");
		  $("body").removeClass("modal-open");
		  
		  
	   // document.getElementById('dash-newProposalList-modal').style.height = '';
	    //document.getElementById('dash-newProposalList-modal').style.width = '';
	    
	    $('.dash-newProposalList-modal').css('height', '');
	    $('.dash-newProposalList-modal').css('width', '');
	  } else {
	   // myModal.classList.add("maximized");
		  $(".myModal1").addClass("maximized");
		  $("body").addClass("modal-open");

	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
	    var modal =$('.dash-newProposalList-modal');
	   // modal.style.height = (windowHeight * 0.75) + 'px'; 
	    var windowHeight = $(window).height();
	    $('.dash-newProposalList-modal').height(windowHeight * 0.75 + 'px');

	    
	    //modal.style.width = (windowWidth * 0.98) + 'px'; 
	    //modal.style.transformOrigin = 'center';
	    var windowWidth = $(window).width();
	    $('dash-newProposalList-modal').width(windowWidth * 0.98 + 'px');
	    $('.dash-newProposalList-modal').css('transform-origin', 'center');
	  }

	  isMaximized = !isMaximized;
	  
	  var btn = document.getElementsByClassName('close-btn')[0];
	  var icon = btn.getElementsByTagName('i')[0];
	  
	  if (icon.classList.contains('fa-window-maximize')) {
	    // Change maximize to restore
	    icon.classList.remove('fa-window-maximize');
	    icon.classList.add('fa-window-restore');
	  } else {
	    // Change restore to maximize
	    icon.classList.remove('fa-window-restore');
	    icon.classList.add('fa-window-maximize');
	  }
	}

function restoreModalSize() {
	  //var myModal = $(".myModal1");
	 // myModal.classList.remove("maximized");
	 // document.body.classList.remove("modal-open");
	  $(".myModal1").removeClass("maximized");
	  $("body").removeClass("modal-open");
	 // document.getElementById('dash-newProposalList-modal').style.height = '';
	  //document.getElementById('dash-newProposalList-modal').style.width = '';
	  
	  $('.dash-newProposalList-modal').css('height', '');
	    $('.dash-newProposalList-modal').css('width', '');
	}

// Code for restoring the proposals



function restoredash(){
	$(".months1").val("0");
	 $(".fromProposal1").val("");
	$(".toProposal1").val("");
	 
	
	//$("#myContent").load(location.href + " #myContent");
   const today = new Date();
   const year = today.getFullYear();
   const firstDayOfYear = new Date(year, 0, 1);

   const dd = String(firstDayOfYear.getDate()).padStart(2, '0');
   const mm = String(firstDayOfYear.getMonth() + 1).padStart(2, '0'); // January is 0
   const yyyy = firstDayOfYear.getFullYear();

   const fromDate = dd + "/" + mm + "/" + yyyy;
  


   const dd1 = String(today.getDate()).padStart(2, '0');
   const mm1 = String(today.getMonth() + 1).padStart(2, '0'); // January is 0
   const yyyy1 = today.getFullYear();

   const toDate = dd1 + "/" + mm1 + "/" + yyyy1;

   var startRange = dd + "/" + mm + "/" + yyyy;
	  var endRange = dd1 + "/" + mm1 + "/" + yyyy1;
	  if(!startRange){
		  sweetSuccess('Attention','Please select Start Range');
		  return false;
	  }
	  var selectedRange = startRange;
	  if(endRange){
		  selectedRange = selectedRange +' to '+endRange;
	  }
	  /*$('.asOnDateProposals1').text(selectedRange);*/ //Commented by devesh on 20-10-23 to fix date range changing on tile
	 
	  //alert('Called');
	  newJoineeEmpTable();
	  ResignEmpTable();
	  closedProjectsTable();
	  pendingClosuresTable();
	  
	//Commented by devesh on 20-10-23 to fix date range changing on tile
	  /*$('.asOnDateProposals1').text(fromDate);
	  $(".asOnDateProposals1").text(fromDate);*/
	  //End of comment
	 
	  isMaximized = true;
	  toggleMaximize();
	  restoreModalSize();
	
}

//Added by devesh to display Last Updated Date of Employees
$(document).ready(function() {
    $.ajax({
        url: '/PMS/employeesLastUpdatedAtDate', // The URL of your controller endpoint
        type: 'GET',
        success : function(response) {
            if (response && response.length > 0) {
                var row = response[0]; // Assuming there's only one date in the response
                var lastUpdatedDate = row[0]; // Access the first element in the row
                
                $("#dateDisplay").text(' (Last Updated On: ' + lastUpdatedDate+')');
            }
        },
        error: function() {
            $("#dateDisplay").html('<div>Error occurred.</div>');
        }
    });
});
//End of Display

//Function to scroll to the top of the page [12-10-2023]
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth' 
    });
}