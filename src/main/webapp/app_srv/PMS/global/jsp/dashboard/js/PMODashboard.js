function resizeIframe(obj) {
	obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
}

//Added by devesh on 22/8/23 to sort Designation wise distribution table alphabetically
$(document).ready(function() {

    $('#testTb2').DataTable({
      order: [[0, 'asc']], // Sort the first column in ascending order
      searching: false, // Disable the search feature
      paging: false,
      info: false,
      columnDefs: [
                    { targets: '_all', orderable: false },
                    { targets: [0], orderable: true }
                 ]
    });
    $('#testTbl').DataTable({
        order: [[0, 'asc']], // Sort the first column in ascending order
        searching: false, // Disable the search feature
        paging: false,
        info: false,
        columnDefs: [
                      { targets: '_all', orderable: false },
                      { targets: [0], orderable: true }
                   ]
      });
  });
//End

function newProposalsTable(){
	var groupColumn = 2;
	var startDate = null;
	var endDate = null;
	var selectedDateRange = $('#asOnDateProposals').text().trim();

	$('#asOnDateProposals1').text(selectedDateRange);
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

	$('#newProposals_dataTable').DataTable().clear().destroy();
	if(startDate){		 
		
	 var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getActiveProposals",
			data:{
				"startDate":startDate,
				"endDate":endDate
			},
			success : function(response) {
				/*-------------- Show the Proposal Count with Project Received Count[12-10-2023] -------------------*/
				var proposalReceivedCount=0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
					if(row.receivedProjectStatus=="Yes"){
						proposalReceivedCount+=1;
					}
				}
				/*--- change the field id of proposal submitted count [08-02-2024] ---*/
				if(response){
					$('#proposalSubmittedCount').text(response.length); 
				}else{
					$('#proposalSubmittedCount').text(0); 
				}
				/*-------------- END the Proposal Count with Project Received Count[12-10-2023] -------------------*/
				// declare the total amountlay and numerictotaloutlay to get the total outlay by varun
				var totalAmount = 0;
				var totalAmountoutlay;
				var totalConvertedToProjCost=0;
				var convertedCount=0;
				var numerictotaloutlay =0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
					// sum the totalamouunt lay by varun 
                    totalAmountoutlay =row.strProposalTotalCost;
					numerictotaloutlay += parseFloat(totalAmountoutlay.replace(/[^0-9.-]/g, ""));
					/*------- row color [12-10-2023] ----------*/
					var rowColor=""
					if(row.receivedProjectStatus=="Yes"){
						rowColor="#1578c2";
					}else{
						rowColor="#0d9b94";
					}
					/*------- row color [12-10-2023] ----------*/
					/*var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
							+ row.encProjectId + "')";*/
				
					/*tableData += '<tr><td class="font_14">'+ row.groupName +  '</td><td><p style="color: #1a7dc4;" class="font_14">'+ row.proposalTitle +'</p></td> ';*/
					//Added by devesh on 3/8/23 to display proposal details on click in proposal tiles
					tableData += '<tr style="color:'+rowColor+'"><td class="font_14">'+ row.groupName +  '</td><td><a href="#" style="color:'+rowColor+'" class="font_14" onclick="viewProjectDetails(\'/PMS/mst/proposalDetails/' + row.encApplicationId + '\')">'+ row.proposalTitle +'</a></td> ';
					
					tableData += ' <td><p class="font_14"><i>'+row.strClientName+'</i></p></td> ';
					
					tableData += ' <td class="font_14 text-right">'+ row.dateOfSubmission +  '</td> ';
					tableData += ' <td class="font_14 text-right">'+ row.duration +  ' months </td> ';
					
					if(row.receivedProjectStatus=='Yes'){
						
							tableData += '<td class="font_14 text-center"> <a title="Click to View Project Details" onclick=openProjectDetails("'+row.encApplicationId+'")>'+ row.receivedProjectStatus + ' </a></td>';
					}
					else{
						tableData += ' <td class="font_14 text-center">'+ row.receivedProjectStatus +  '</td> ';
					}
					
					tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encApplicationId+'" value='+row.encApplicationId+' /> <span id="projectCost_'+row.encApplicationId+'">'
					+ row.strProposalCost + '</span></td>';
					// added the strtotalproposalcost by varun 
	                   tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encApplicationId+'" value='+row.encApplicationId+' /> <span id="projectCost_'+row.encApplicationId+'">'
						+ row.strProposalTotalCost + '</span></td>';
	                //Added button for proposal history by devesh on 27/7/23
					/*tableData += ' <td class="font_14 text-center"> <button type="button" style="font-size: 12px; border-radius: 5px; background-color: #1a7dc4; color: #ffffff;" onclick="viewProposalHistory(\'' + row.encApplicationId + '\')">View Proposal History </button>  </td> </tr>';*/
	                tableData += ' <td class="font_14 text-center"> <a href="#" data-toggle="tooltip" data-placement="top" title="Click here to view proposal history" onclick="viewProposalHistory(\'' + row.encApplicationId + '\')"><img src="/PMS/resources/app_srv/PMS/global/images/proposal_log.png" alt="Proposal History Icon" width="28" height="28"></a> </td> </tr>';
					//End of button
					totalAmount += row.numProposalAmountLakhs;
					totalConvertedToProjCost +=row.totalProjectCost;
					convertedCount+=row.numCountOfConverted;
					
				}				
				
				$('#newProposals_dataTable').append(tableData); 
				/*------------------- Comment Footer [12-10-2023] -----------------------------*/
/*				var foot = $("#newProposals_dataTable").find('tfoot');
				 if(foot.length){
					 $("#newProposals_dataTable tfoot").remove();
				 }
*/				
				if(totalAmount >0){
					
					var totalProCostSpan = $('#totalProCost');
		            var parentDiv = totalProCostSpan.closest('div');
		            parentDiv.show();
					
					var amt1= response.length +" " + " ( " +convertAmountToINR(totalAmount) + " Lakhs "+ "  )";
						
					 $("#totalProCost").text(amt1);/*
					 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#newProposals_dataTable"); 
					// numerictotaloutlay td declare to sum the totaloutlay by varun 
					    foot.append('<tr> <td></td><td></td><td></td> <td></td><td></td><td class="bold text-right">Total</td> <td class="bold text-right amount-cell">'+convertAmountToINR(totalAmount)+' Lakhs </td><td class="bold text-right amount-cells">'+convertAmountToINR(numerictotaloutlay)+' Lakhs </td></tr>');
					  //Added to span tfoot across 8 columns by devesh on 2/8/23
					 	foot.append('<tr> <td></td><td></td><td></td> <td></td><td></td><td class="bold text-right">Total</td> <td class="bold text-right amount-cell">'+convertAmountToINR(totalAmount)+' Lakhs </td><td class="bold text-right amount-cells">'+convertAmountToINR(numerictotaloutlay)+' Lakhs </td><td></td></tr>');
				*/}
				/*Added by devesh on 29-01-2024 to hide Total Proposal Submited if it is zero*/
				else{
					var totalProCostSpan = $('#totalProCost');
		            var parentDiv = totalProCostSpan.closest('div');
		            parentDiv.hide();
				}
				/*End of condition*/
/*				if(totalConvertedToProjCost>0){
					$("#projRec").show();
					var amt2=convertedCount +" " + " ( " + convertAmountToINR(totalConvertedToProjCost)+ " Lakhs "+ " )";
					$("#totalConvCost").text(amt2);
				}
				else{
					
					$("#projRec").hide();
				}*/
				
				var  table= $('#newProposals_dataTable').DataTable( {
				
					  dom: 'Bfrtip',	
					  buttons: [
					             'excel', 'pdf', 'print'
					        ],
					        //Added by devesh on 03-11-23 to sort table according to date
					        "columnDefs": [
					                       {
					                         "type": "datetime-moment",
					                         "targets": 3, // Assuming the 3rd column is the date column (0-based index).
					                         "render": function (data, type, row) {
					                           if (type === "sort") {
					                             return moment(data, "DD/MM/YYYY").format("YYYY-MM-DD");
					                           }
					                           return data;
					                         }
					                       }
					                     ],
					                     "order": [[3, "desc"]]
							//End of Sorting     
		        /*"columnDefs": [
		                       { "visible": false, "targets": groupColumn ,"orderable": false, }
		                   ],
		                   "order": [[ groupColumn, 'asc' ]],
		                   "displayLength": 25,
		                   "drawCallback": function ( settings ) {
		                       var api = this.api();
		                       var rows = api.rows( {page:'current'} ).nodes();
		                       var last=null;
		            
		                       api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
		                           if ( last !== group ) {
		                               $(rows).eq( i ).before(
		                                   '<tr class="group"><td colspan="6">'+group+'</td></tr>'
		                               );
		            
		                               last = group;
		                           }
		                       } );
		                   }*/
		        
		    } );
			   /* $('#newProposals_dataTable tbody').on( 'click', 'tr.group', function () {
			        var currentOrder = table.order()[0];
			        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
			            table.order( [ groupColumn, 'desc' ] ).draw();
			        }
			        else {
			            table.order( [ groupColumn, 'asc' ] ).draw();
			        }
			    } );*/
			
				
				$('#newProposals_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
			              // call the below function getSelectedColumnValues and getSelectedProposalValues by varun 
			                 getSelectedColumnValues(table,colIdx);
			                 getSelectedProposalValues(table, colIdx)
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				
				// function is used to get the dynamic total proposal submitted vales and project recived values by varun 
				function getSelectedProposalValues(table, colIdx) {
				    var values = [];
				    table.rows({ search: 'applied' }).every(function () {
				        var data = this.data();
				        values.push(data[5]+"%"+data[6]);
				    });
				    performgroupfilter(values);
				    return values;
				}
				function performgroupfilter(values) {
				    var totalProjects = values.length;
				    var noCount = 0;
				    var totalCostInLakhs = 0;
				    var totalCostInLakhsC = 0;
				    for (var i = 0; i < values.length; i++) {
				        var parts = values[i].split('%');
				        if (parts.length === 2) {
				            if (parts[0].trim() === "No") {
				                noCount++;
				                var costMatch11 = parts[1].split('&nbsp;');
				                var costMatch12 = costMatch11[1].split('Lakhs');
				                if (costMatch12) {
				                    totalCostInLakhsC += parseFloat(costMatch12[0]);
				                }
				            }
				            var costMatch = parts[1].match(/₹\s*([\d.]+)\s*Lakhs/);
				            var costMatch1 = parts[1].split('&nbsp;');
				            var costMatch2 = costMatch1[1].split('Lakhs');
				            if (costMatch2) {
				                totalCostInLakhs += parseFloat(costMatch2[0]);
				            }
				        }
				    }
				    var amt_1= totalProjects +" " + " ( " +convertAmountToINR(totalCostInLakhs) + " Lakhs "+ "  )";
				    console.log("Total Submitted:"+amt_1);
				    var rCount = totalProjects-noCount;
				    var rCost = totalCostInLakhs-totalCostInLakhsC;
				    var amt_2=rCount +" " + " ( " + convertAmountToINR(rCost)+ " Lakhs "+ " )";
				    console.log("Total Received:"+amt_2);
				    
				    $('#totalProCost').text(amt_1);
				    
				    //$('#totalConvCost').text(amt_2);
				}

                 // function is used for get the CDAC outlay and total outlay while filtering the group name by varun 
				function getSelectedColumnValues(table, colIdx) {
				    var values = [];
				    table.rows({ search: 'applied' }).every(function () {
				        var data = this.data();
				        values.push(data[6]+"%"+data[7]);
				        console.log("data:"+data);
				    });
				    performCalculations(values);
				    
				}
				function performCalculations(values) {
				    var totalProjects = values.length;
				    var noCount = 0;
				    var totalCostInLakhs = 0;
				    var totalCostInLakhsC = 0;
				    for (var i = 0; i < values.length; i++) {
				        var parts = values[i].split('%');
			            var costMatch1 = parts[1].split('&nbsp;');
			            var costMatch2 = costMatch1[1].split('Lakhs');
			            if (costMatch2) {
			                totalCostInLakhs += parseFloat(costMatch2[0]);
			            }
			            
			            var costMatch3 = parts[0].split('&nbsp;');
			            var costMatch4 = costMatch3[1].split('Lakhs');
			            if (costMatch4) {
			            	totalCostInLakhsC += parseFloat(costMatch4[0]);
			            }
				    }
				    
				    var amt_1 = convertAmountToINR(totalCostInLakhsC) + " Lakhs ";
				    $('.amount-cell').text(amt_1); // Update all elements with class "amount-cell"

				    var amt_2 = convertAmountToINR(totalCostInLakhs) + " Lakhs ";
				    $('.amount-cells').text(amt_2);
				   
				}
				

				 // Setup - add a text input
		       $('#newProposals_dataTable .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

               // DataTable
               var table = $('#newProposals_dataTable').DataTable();
		       
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		                                           } );
		} );
		       

			},
			error : function(e) {
				// $('#RecurringManpowerAmt').val(0);
				alert('Error: ' + e);
			}
		});
			
	}
	}

//Added to create proposal history modal by devesh on 2/8/23
function viewProposalHistory(encApplicationId) {
    // Perform actions based on the applicationId
    // For example, you can use this ID to open a modal, show more information, etc.
	var encAppId=encApplicationId;
	$('#HistoryTable').DataTable().clear().destroy();
	var tableData = '';
	$.ajax({
		type : "POST",
		url : "/PMS/mst/proposalHistorydetails",
		data : "encId=" + encAppId,
		success : function(response) {
			if(response!=''||response!=null){
				
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
					revCount=response.length-i;
					revcount1= revCount-1;
					//console.log("revcount1.."+revcount1);
					sNo=i+1;
					if(row[3]==null||row[3]==''){
						row[3]='';
						
					}
										
					tableData += '<tr id="row_' + i + '"> <td class="font_16 center">'+sNo+'</td>' 
							+ '<td class="font_16"><a onclick="getHistoryDetails('+row[0]+','+revCount+')"> Rev '+revcount1+'</a></td>'
							+'<td class="font_16">'+row[1] +'</td>'
							+'<td class="font_16">'+row[2]+'</td>'
							
							+'<td class="font_16">'+row[3]+'</td>'
							+'<td class="font_16">'+row[4]+'</td>'
							+'<td class="font_16">'+row[5]+'</td>'
							+'<td class="font_16">'+row[6]+'</td>'
							+'<td class="font_16">'+row[7]+'</td>'
							+'<td class="font_16">'+row[8]+'</td>'
							+'<td class="font_16">'+row[9]+'</td>'
							+'<td class="font_16">'+row[10]+'</td>'
							+'<td class="font_16">'+row[11]+'</td>'							
							+'<td class="font_16">'+row[12]+'</td>'
							+'<td class="font_16">'+(row[13]?row[13].slice(0, 10):'')+'</td>'
							+'<td class="font_16">'+row[14]+'</td>'
							+'<td class="font_16">'+row[15]+'</td>'
							+'<td class="font_16">'+row[16]+'</td>'
							+'<td class="font_16">'+row[17]+'</td>'
							+'<td class="font_16">'+row[18]+'</td>'
							+'<td class="font_16">'+row[19]+'</td>'
							+'<td class="font_16">'+row[20]+'</td>'
							+'<td class="font_16">'+row[21]+'</td>';
							+'</tr>';  
							revCount-1;			
				}

				$('#HistoryTable').append(tableData);

				$('#HistoryTable').DataTable( {
			        "paging":   false,
			        "ordering": false,
			        "info":     false,
			        "filter": false,
			        "scrollX": "100%",
			        "scrollY": "340px"
			    } );
				
				colorRows();
				$('#history').modal('show');
				const columnIndices = findAllColumnsWithSameValueInAllRows(response);

				if (response.length > 0) {
			        if (columnIndices.length > 0) {
			          //console.log('Column indices with the same value in all rows:', columnIndices);
			          removeColumnsFromTable(columnIndices);
			        } else {
			          //console.log('No columns with the same value in all rows.');
			        }
			      } else {
			        // If there are no rows, hide all columns with an index greater than 4
			    	//console.log('No rows in the table.');
			        const table = $('#HistoryTable').DataTable();
			        for (var colIndex = 5; colIndex < table.columns().count(); colIndex++) {
			          table.column(colIndex).visible(false);
			        }
			      }
			
				
			}
			else{
				alert("empty");
			}

		},
		error : function(e) {
			alert('Error: ' + e);			
		}
	});
	/*alert(encAppId);*/
    //encryptionService.dcrypt(applicationId);
}

function colorRows() {
    const rowCount = $('#HistoryTable tbody tr').length;
    const colCount = $('#HistoryTable tbody tr:first td').length;

    for (var rowIndex = 0; rowIndex < rowCount - 1; rowIndex++) {
    	for (var colIndex = 2; colIndex < colCount; colIndex++) {
            // Skip columns 4 and 5
            if (colIndex === 3 || colIndex === 4) {
                continue;
            }

            const currentValue = $('#row_' + rowIndex + ' td:eq(' + colIndex + ')').text();
            const nextValue = $('#row_' + (rowIndex + 1) + ' td:eq(' + colIndex + ')').text();

            const cell = $('#row_' + rowIndex + ' td:eq(' + colIndex + ')');

            if (currentValue !== nextValue) {
                cell.addClass('different-value');
            } else {
                cell.removeClass('different-value');
            }
        }
    }
}

function findAllColumnsWithSameValueInAllRows(response) {
	  if (!response || response.length === 0) {
	    return [];
	  }

	  const referenceRow = response[0];
	  const numColumns = referenceRow.length;
	  const columnIndices = [];

	  for (var colIndex = 0; colIndex < numColumns; colIndex++) {
	    const columnValue = referenceRow[colIndex];
	    let allRowsHaveSameValue = true;

	    for (var rowIndex = 1; rowIndex < response.length; rowIndex++) {
	      if (response[rowIndex][colIndex] !== columnValue) {
	        allRowsHaveSameValue = false;
	        break;
	      }
	    }

	    if (allRowsHaveSameValue) {
	    	if((colIndex+1)>4)columnIndices.push(colIndex+1);
	    }
	  }

	  return columnIndices;
	}

function removeColumnsFromTable(columnIndices) {
	  const table = $('#HistoryTable').DataTable();

	  // Remove the header cells for the columns with the same value
	  for (var i = columnIndices.length - 1; i >= 0; i--) {
	    table.column(columnIndices[i]).visible(false);
	  }
	}

function getHistoryDetails(r,versionNo){
	var numId=r;
	//alert(numId);
	var ver=versionNo;
	//alert(ver);
	openWindowWithPost('POST','/PMS/mst/proposalVersionDetails',{"numVersion":ver,"numId":numId},'_blank');

}
//End of proposal history modal

//Function for new Projects
function newProjectsTable(){
var groupColumn = 2;
var startDate = null;
var endDate = null;
var selectedDateRange = $('#asOnDateProjects').text().trim();

$('#asOnDateProjects1').text(selectedDateRange);
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

/*------------------------ Display Project Received Data based on their respective statuses [ 21-08-2023 ] -----------------------------------*/
/*	var tableData_PL = '';
	var tableData_HOD = '';
	var tableData_GC = '';
	var tableData_PMO = '';
*/
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getNewProjectsDetail",
		data:{
			"startDate":startDate,
			"endDate":endDate
		},
		success : function(response) {
			/*
				$('#newProjects_dataTable_PL').DataTable().clear().destroy();
				$('#newProjects_dataTable_GC').DataTable().clear().destroy();
				$('#newProjects_dataTable_HOD').DataTable().clear().destroy();
				$('#newProjects_dataTable_PMO').DataTable().clear().destroy();
			*/
			$('#newProjects_dataTable').DataTable().clear().destroy();
			
			if(response){
				$('#newProjectsOnDate').text(response.length); 
			}else{
				$('#newProjectsOnDate').text(0); 
			}
			
			var pendingAtPMOCount = 0; 
			var totalAmount = 0;
			for (var i = 0; i < response.length; i++) {
				var row = response[i];	

				var mouDate=row.strMouDate;
				var workorderDate= row.strWorkOrderDate;
				if(row.strMouDate==null){
					mouDate='';
				}
				if(row.strWorkOrderDate==null){
					workorderDate='';
				}
				var strReferenceNumber=row.strReferenceNumber;
				if(!strReferenceNumber){
					strReferenceNumber='';
					tableData+='<tr style="color:#800080">'
				}else{
					tableData += '<tr style="color:#1578c2">'
				}
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
						+ row.encProjectId + "')";
			
				if(!strReferenceNumber){
					tableData += '<td class="font_14">'+ row.strGrouptName +  '</td><td> <a class="font_14" style="color:#800080" title="Click to View Complete Information" onclick='+clickFunction+'>'
					+ row.strProjectName + ' </a><p class="bold blue font_14 text-left">'+ strReferenceNumber+'</p></td>';
				}else{
					tableData += '<td class="font_14">'+ row.strGrouptName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
					+ row.strProjectName + ' </a><p class="bold blue font_14 text-left" style="color:#1578c2">'+ strReferenceNumber+'</p></td>';
				}
				
				
				if(!strReferenceNumber){
					tableData += '<td><p class="font_14" style="color:#800080"><i>' + row.strClientName + '</i></p></td><td class="font_14 text-right">';
				}else{
					tableData += '<td><p class="font_14" style="color:#1578c2"><i>' + row.strClientName + '</i></p></td><td class="font_14 text-right">';
				}
						
				

				if (mouDate || workorderDate) {
					var EmptyDash ="-- Not Available --";
					if(mouDate){
						tableData += mouDate;
						if(workorderDate){
							tableData += '  <i class="fas fa-grip-lines-vertical"></i>  '+workorderDate;
						}else{
							tableData += '  <i class="fas fa-grip-lines-vertical"></i>  '+EmptyDash;
						}
					}else{
						tableData += EmptyDash;
						if(workorderDate){
							tableData += '  <i class="fas fa-grip-lines-vertical"></i>  '+workorderDate;
						}else{
							tableData += '  <i class="fas fa-grip-lines-vertical"></i>  '+EmptyDash;
						}
					}
				}

				tableData += '</td> <td class="font_14 text-right">'+ row.startDate +  '</td> ';
				tableData += ' <td class="font_14 text-right">'+ row.endDate +  '</td> ';	
				tableData += ' <td class="font_14 text-right"> <span id="projectCost_'+row.encProjectId+'">'
				+ row.strProjectCost + '</span></td>';
	
				totalAmount += row.numProjectAmountLakhs;
				
				if (row.workflowModel && row.workflowModel.strActionPerformed) {
					if (row.workflowModel.strActionPerformed.includes("Converted Proposal to Project") || row.workflowModel.strActionPerformed.includes("PL")){
				    	tableData += ' <td class="font_14 text-center">Pending At PL</span></td> </tr>';
				    }else if (row.workflowModel.strActionPerformed.includes("HOD")) {
				    	tableData += ' <td class="font_14 text-center">Pending At HOD</span></td> </tr>';
				    }else if (row.workflowModel.strActionPerformed.includes("GC")) {
				    	tableData += ' <td class="font_14 text-center">Pending At GC</span></td> </tr>';
				    } else if (row.workflowModel.strActionPerformed.includes("PMO")) {
				    	tableData += ' <td class="font_14 text-center">Pending At PMO</span></td> </tr>';
				    	pendingAtPMOCount++; 
				    }
				}
/*				if (row.workflowModel && row.workflowModel.strActionPerformed) {
				    if (row.workflowModel.strActionPerformed.includes("GC")) {
				    	tableData_GC +=tableData;
				    } else if (row.workflowModel.strActionPerformed.includes("PMO")) {
				    	tableData_PMO +=tableData;
				    }else if (row.workflowModel.strActionPerformed.includes("HOD")) {
				    	tableData_HOD +=tableData;
				    }else if (row.workflowModel.strActionPerformed.includes("Converted Proposal to Project") || row.workflowModel.strActionPerformed.includes("PL")){
				    	tableData_PL +=tableData;
				    }
				}*/
			}
			/*$('#newProjects_dataTable_PL').append(tableData_PL);
			$('#newProjects_dataTable_GC').append(tableData_GC);
			$('#newProjects_dataTable_HOD').append(tableData_HOD);
			$('#newProjects_dataTable_PMO').append(tableData_PMO);*/
			  $('#pendingAtPMOCount').text(pendingAtPMOCount);
			$('#newProjects_dataTable').append(tableData);
			var ReceivedAmt= response.length +" " + " ( " +convertAmountToINR(totalAmount) + " Lakhs "+ "  )";
			$('#totalProjectReceivedCost').text(ReceivedAmt);
			
			var  table= $('#newProjects_dataTable').DataTable( {
				  dom: 'Bfrtip',	
				  buttons: [
				             'excel', 'pdf', 'print'
				        ],	                
			});

			$('#newProjects_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
				$('#newProjects_dataTable .filters th[id="projectStatusCol"]').each( function ( i ) {
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
			                               "Pending At PL",
			                               "Pending At HOD",
			                               "Pending At GC",
			                               "Pending At PMO"
			                               ];
			            	projectStatus.forEach(function (d) {
	                         select.append('<option value="' + d + '">' + d + '</option>');
	                     });
			        });
	     var table = $('#newProjects_dataTable').DataTable();
	     table.columns().eq( 0 ).each( function ( colIdx ) {
	           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
	               table
	                           .column( colIdx )
	                           .search( this.value )
	                           .draw() ;  
	                                           });
	     	});	

/*------------------------ End of Display Project Received Data based on their respective statuses ----------------------------------*/	     
	     
/*//----------------------------  FOR Project Received Pending at PL Table ----------------------------------------------------------------------	
			$('#newProjects_dataTable_PL .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

				});
               // DataTable
				var table = $('#newProjects_dataTable_PL').DataTable();
				table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
		                           .column( colIdx )
		                           .search( this.value )
		                           .draw() ;
		                   
		          });
		       });					
//----------------------------  FOR Project Received Pending at GC Table ----------------------------------------------------------------------	
				$('#newProjects_dataTable_GC .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

					});
	               // DataTable
					var table = $('#newProjects_dataTable_GC').DataTable();
					table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			          });
			       });					
//----------------------------  FOR Project Received Pending at HOD Table ----------------------------------------------------------------------	
					$('#newProjects_dataTable_HOD .filters th[class="textBox"]').each( function () {                 
				           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

						});
		               // DataTable
						var table = $('#newProjects_dataTable_HOD').DataTable();
						table.columns().eq( 0 ).each( function ( colIdx ) {
				           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
				               table
				                           .column( colIdx )
				                           .search( this.value )
				                           .draw() ;
				                   
				          });
				       });					
//----------------------------  FOR Project Received Pending at PMO Table ----------------------------------------------------------------------	
						$('#newProjects_dataTable_PMO .filters th[class="textBox"]').each( function () {                 
					           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

							});
			               // DataTable
							var table = $('#newProjects_dataTable_PMO').DataTable();
							table.columns().eq( 0 ).each( function ( colIdx ) {
					           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
					               table
					                           .column( colIdx )
					                           .search( this.value )
					                           .draw() ;
					                   
					          });
					       });		   */
		}
	});
}
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
					        "paging":   false,
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
						table.on('order.dt search.dt', function () {
							table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
						           cell.innerHTML = i+1;
						           table.cell(cell).invalidate('dom');
						     });
						}).draw();
						
					}
				});
			}
			/*------------------------------------- END of Closed Projects Tile Table [12-10-2023] ----------------------------------*/			
		}
	
	
	function validatedProjectsTable(){
		 $('#validatedProjects_dataTable').DataTable().clear().destroy();
		var groupColumn = 2;
		var startDate = null;
		var endDate = null;
		var selectedDateRange = $('#asOnValidatedProjects').text().trim();
		//console.log("selectedDateRange inside function"+selectedDateRange);
		$('#asOnDatevalidationProject').text(selectedDateRange);
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
			
		 var tableData = '';
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/mst/getValidatedProjects",
					data:{
						"startDate":startDate,
						"endDate":endDate
					},
					success : function(response) {
						var sysDate = new Date();
						
						var valuesList = []; 
						
					    $(".closureList").each(function() {
					      var value = $(this).text().trim();
					      valuesList.push(value); 
					    });
					    
                     var validateList = []; 
						
					    $(".validateList").each(function() {
					      var value = $(this).text().trim();
					      validateList.push(value); 
					      //console.log("underapproval" +validateList );
					    });
					    
					    
					    // added below condition to get the checked and unchecked values in tiles 
						 if (response) {
							 
							 var checked = 0;
						        var unchecked = 0;
						        var countValidated = response.filter(function(item) {
						        	
						        	if(item.strValidateStatus === 'Yes' || item.strValidateStatus === 'No'){
						        		checked++
						        		
						        	}else
						        	{
						        		
						        		unchecked++
						        	}
						        	 
						        }).length;
						       
						        $('#countValidated').addClass('small-text').text('Financially unchecked: ' + unchecked);

						        $('#countValidatedchecked').addClass('small-texch').text('Financially checked: ' + checked);

						    }
						 
						/* if (response) {
						        var countValidated = response.filter(function(item) {
						        	  return item.strValidateStatus === 'Yes' || item.strValidateStatus === 'No';
						        }).length;

						        $('#countValidated').text(countValidated);
						    } else {
						        $('#countValidated').text(0);
						    }*/
						
						var totalAmount = 0;
						for (var i = 0; i < response.length; i++) {
							var row = response[i];	
							var strReferenceNumber=row.projectRefrenceNo;
							if(!strReferenceNumber){
								strReferenceNumber='';
							}
							
							var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
							//console.log("asdasda:"+strReferenceNumber);
							 var endDateString = row.endDate;
							 if(endDateString!=null && endDateString !=''){
								//console.log("what is end date" + endDateString);
							    var endDateParts = endDateString.split("/");
							    var endDate = new Date(endDateParts[2], endDateParts[1] - 1, endDateParts[0]);
							 }
							 if (!strReferenceNumber) {
							tableData += '<tr style="color:#800080"><td class="font_14 text-center">'+ (i+1)+'</td><td class="font_14 text-center">'+row.strGroupName+'</td><td><a style="color:#800080" class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
									+ row.strProjectName + ' </a><p class="bold font_14 text-left">'+ strReferenceNumber+'<p> </td>';				
							tableData += ' <td><p class="font_14"><i>'+row.clientName+'</i></p></td><td class="font_16 text-left">'+row.strPLName+'</td> ';
							tableData += ' <td class="font_14 text-center"> <span  class="" >'+ row.startDate+  '</span></td> ';				
							
							tableData += ' <td class="font_14 text-center">'+row.strValidatedRemarks+'</span></td>';
							tableData += ' <td class="font_14 text-right">'+ row.strValidateStatus+  '</span></td>';
								
						}
							/* else if(endDate.getTime() < sysDate.getTime() && !valuesList.includes(row.projectRefrenceNo)){
								 tableData += '<tr style="color:#808080"><td class="font_14 text-center">'+ (i+1)+'</td><td class="font_14 text-center">'+row.strGroupName+'</td><td><a style="color:#808080" class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
									+ row.strProjectName + ' </a><p class="bold font_14 text-left">'+ strReferenceNumber+'<p> </td>';				
							tableData += ' <td><p class="font_14"><i>'+row.clientName+'</i></p></td><td class="font_16 text-left">'+row.strPLName+'</td> ';
							tableData += ' <td class="font_14 text-center"> <span  class="" >'+ row.startDate+  '</span></td> ';				
						
							tableData += ' <td class="font_14 text-center">'+row.strValidatedRemarks+'</span></td>';
							tableData += ' <td class="font_14 text-right">'+ row.strValidateStatus+  '</span></td>'; 
								 
							 }
							  else if((validateList.includes(row.projectRefrenceNo))) {
									 tableData += '<tr style="color:#800080"><td class="font_14 text-center">'+ (i+1)+'</td><td class="font_14 text-center">'+row.strGroupName+'</td><td><a style="color:#800080" class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
										+ row.strProjectName + ' </a><p class="bold font_14 text-left">'+ strReferenceNumber+'<p> </td>';				
								tableData += ' <td><p class="font_14"><i>'+row.clientName+'</i></p></td><td class="font_16 text-left">'+row.strPLName+'</td> ';
								tableData += ' <td class="font_14 text-center"> <span  class="" >'+ row.startDate+  '</span></td> ';				
								
								tableData += ' <td class="font_14 text-center">'+row.strValidatedRemarks+'</span></td>';
								tableData += ' <td class="font_14 text-right">'+ row.strValidateStatus+  '</span></td>'; 
							  }*/
							  else{
								  tableData += '<tr style="color:#1578c2"><td class="font_14 text-center">'+ (i+1)+'</td><td class="font_14 text-center">'+row.strGroupName+'</td><td><a style="color:#1578c2" class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
									+ row.strProjectName + ' </a><p class="bold font_14 text-left">'+ strReferenceNumber+'<p> </td>';				
							tableData += ' <td><p class="font_14"><i>'+row.clientName+'</i></p></td><td class="font_16 text-left">'+row.strPLName+'</td> ';
							tableData += ' <td class="font_14 text-center"> <span  class="" >'+ row.startDate+  '</span></td> ';				
							
							tableData += ' <td class="font_14 text-center">'+row.strValidatedRemarks+'</span></td>';
							tableData += ' <td class="font_14 text-right">'+ row.strValidateStatus+  '</span></td>'; 
							  }
							  }
						$('#validatedProjects_dataTable').append(tableData); 
						
						var table= $('#validatedProjects_dataTable').DataTable( {
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
						});	
						filterTableByValues(table,$('#validationStatusDropdown').val());
						
						 $('#validationStatusDropdown').on('change', function () {
							 filterTableByValues(table,$(this).val());
						 });
							
/*						$(document).ready(function () {
							
						   $('#validationStatusDropdown').on('change', function () {
						        var selectedStatus = $(this).val();
						        var validationStatus;

						        if (selectedStatus === 'All') {
						            validationStatus = ''; 
						        } else if (selectedStatus === 'Yes') {
						        	validationStatus = '^(Yes|No)$';
						        } else {
						            validationStatus = '^' + selectedStatus + '$';
						        }
						        
						        if (selectedStatus === 'Yes') {
						        	validationStatus = '^(Yes|No)$';
						        } else {
						            validationStatus = '^' + selectedStatus + '$';
						        }
						       

						        table.column(8)
						            .search(validationStatus, true, false)
						            .draw();
						    });
						});*/
						




                        $('#validatedProjects_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
			
			
						   table.on('order.dt search.dt', function () {
							table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
						           cell.innerHTML = i+1;
						           table.cell(cell).invalidate('dom');
						     });
						}).draw();
						
					}
				});		
		}
	}
	
	function filterTableByValues(table,status){
		console.log("table"+table);
		console.log("status"+status);
		var selectedStatus = status;
        var validationStatus;

        if (selectedStatus === 'All') {
            validationStatus = ''; 
        } else if (selectedStatus === 'Yes') {
        	validationStatus = '^(Yes|No)$';
        } else {
            validationStatus = '^' + selectedStatus + '$';
        }
        
        if (selectedStatus === 'Yes') {
        	validationStatus = '^(Yes|No)$';
        } else {
            validationStatus = '^' + selectedStatus + '$';
        }
       

        table.column(7)
            .search(validationStatus, true, false)
            .draw();
	}
	/*$(document).ready(function () {
		changeOption();
	
		
	});
	
	  function changeOption() {
		 
	      // Method 1: Using selectedIndex
	      // document.getElementById("myComboBox").selectedIndex = 2; // Change to the third option (index 2)

	      // Method 2: Using value
	      document.getElementById("validationStatusDropdown").value = "Yes"; // Change to the option with value "orange"
	    }*/
	
	//Bhavesh(01-03-2024) getting pending Payments by current year
	function pendingPaymentsTable(){
		
		var selectedDateRange = $('#asOnDatePendingPayments').text().trim();
		$('#asOnDatePendingPayments1').text(selectedDateRange);
		var symbol=">="
		 $('#pendingPayments_dataTable').DataTable().clear().destroy();   //12-10-2023

		 var tableData = '';
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getPendingPaymentsDetailsByInvoiceDt",
				data : {"invoiceDate":selectedDateRange,
						"symbol":symbol
						},
				success : function(response) {
					//console.log(response);
					var totalInvoiceAmt=0;
					var totalTaxAmt = 0;
					var totalAmt = 0;
					
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						 totalInvoiceAmt += row.numInvoiceAmt;
						 totalTaxAmt += row.numTaxAmount;
						 totalAmt +=row.numInvoiceTotalAmt;
						var strInvoiceStatus= row.strInvoiceStatus;
						if(row.strInvoiceStatus==null){
							strInvoiceStatus='';
						}
						var strReferenceNumber=row.strReferenceNumber;
						if(!strReferenceNumber){
							strReferenceNumber='';
						}
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
					
						tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.strGroupName +  '</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
								+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';
						
						tableData += '<td class="font_14 orange">'+row.strClientName+'</td><td class="font_14 ">'
						+ row.strInvoiceRefno +'</td>';
						
						tableData += ' <td class="font_14">'+ row.strInvoiceDate+ '</td>';
						
						tableData += ' <td class="font_14">'+ strInvoiceStatus+  '</td>';
						
						tableData += ' <td class="font_14 text-right">'	+ row.strInvoiceAmt +'</td>';
						
						tableData += ' <td class="font_14 text-right">'	+ row.strTaxAmount + '</td>';
						
						tableData += ' <td class="font_14 text-right">'+ row.strInvoiceTotalAmt+ '</td> </tr>';			
						
					}		
				
					$('#pendingPayments_dataTable').append(tableData); 
					var foot = $("#pendingPayments_dataTable").find('tfoot');
					 if(foot.length){
						 $("#pendingPayments_dataTable tfoot").remove();
					 }
					
					if(totalInvoiceAmt >0){
									
						 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#pendingPayments_dataTable"); 
						    foot.append('<tr><td></td> <td></td><td></td><td></td> <td></td><td></td> <td class="bold text-right">Total</td><td class="bold text-right totalinvoice">'+convertAmountToINR(totalInvoiceAmt)+'</td> <td class="bold text-right totaltax">'+convertAmountToINR(totalTaxAmt)+'</td> <td class="bold text-right totalAmount">'+convertAmountToINR(totalAmt)+'</td></tr>');
					}          
					var  table= $('#pendingPayments_dataTable').DataTable( {
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
					
				
						
						table.on('order.dt search.dt', function () {
							table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
						           cell.innerHTML = i+1;
						           table.cell(cell).invalidate('dom');
						     });
							updateTotalAmount(table);
						}).draw();
						
						$('#pendingPayments_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
					                 updateTotalAmount(table);
					                } );
					     
					            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
					                select.append( '<option value="'+d+'">'+d+'</option>' )
					            } );
					        } );
						

						 // Setup - add a text input
				       $('#pendingPayments_dataTable .filters th[class="textBox"]').each( function () {                 
				           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

				       } );

		               // DataTable
		               var table = $('#pendingPayments_dataTable').DataTable();
				       
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
	//declare the function by varun to get the sum by selecting groupname by varun 
	   function updateTotalAmount(table) {
	      
		   var totalinvoiceAmt = 0;
		    var totalwithouttaxAmt = 0;
		    var totalAmt = 0;
		  
		    table.rows({ search: 'applied' }).every(function () {
		        var data = this.data();
		        var valueinvoice = parseFloat(data[7].split(";")[1].replace(/,/g, ''));
		        totalinvoiceAmt += isNaN(valueinvoice) ? 0 : valueinvoice;
		        if(data[8]!=null && data[9]!=null){
			        var valuewithouttax = parseFloat(data[8].split(";")[1].replace(/,/g, ''));
			        var valueamnt = parseFloat(data[9].split(";")[1].replace(/,/g, ''));

			       
			        totalwithouttaxAmt += isNaN(valuewithouttax) ? 0 : valuewithouttax;
			        totalAmt += isNaN(valueamnt) ? 0 : valueamnt;
			        $('.totalinvoice').text( convertAmountToINR(totalinvoiceAmt));
			        $('.totaltax').text( convertAmountToINR(totalwithouttaxAmt));
			        $('.totalAmount').text( convertAmountToINR(totalAmt));
			        }
		        else{
		        	
		        	 $('.totalinvoice').text( convertAmountToINR(totalinvoiceAmt)+" Lakhs");
		        }
			        
		    });

	       
	       
	       
	    }	
	function pendingInvoicesTable(){
		
		var selectedDateRange = $('#asOnDateDueInvoice').text().trim();
		$('#asOnDateDueInvoice1').text(selectedDateRange);
		var symbol=">=";
		
		//Bhavesh (18-10-23) changed $('#pendingInvoices_dataTable').DataTable().destroy() to $('#pendingInvoices_dataTable').DataTable().clear().destroy();
		$('#pendingInvoices_dataTable').DataTable().clear().destroy();
		var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getPendingInvoiceDetailbyDate",
			data : {"dueDate":selectedDateRange,
				   "symbol":symbol
				},
			success : function(response) {
				var totalAmt =0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];	
					var strPurpose=row.strPurpose;
					var strRemarks=row.strRemarks;
					totalAmt +=row.numInvoiceAmtInLakhs;
					if(row.strPurpose==null){
						strPurpose='';
					}
					if(row.strRemarks==null){
						strRemarks='';
					}
					var strReferenceNumber=row.strReferenceNumber;
					if(!strReferenceNumber){
						strReferenceNumber='';
					}
					var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
							+ row.encProjectId + "')";
				
					tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.strGroupName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
							+ row.strProjectName + ' </a> <p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p></td>';
					
					tableData += '<td class="font_14 orange">'+row.strClientName+'</td><td class="font_14 "> <span  class="" >'
					+ row.strPaymentDueDate
					+  '</span></td>';
					
					tableData += ' <td class="font_14">'+ strPurpose+  '</td>';
					
					tableData += ' <td class="font_14"> <span  class="" >'
						+strRemarks +  '</span></td>';
					tableData += ' <td class="font_14 text-right">'+row.strAmount+ ' Lakhs</td> </tr>';
					
				}

				/*if(totalAmt>0){
					tableData +='<tr><td></td><td></td> <td></td> <td></td>  <td></td><td class="bold text-right">Total</td> <td class="bold text-right">'+convertAmountToINR(totalAmt)+' Lakhs</td></tr>';
				}*/
				
				$('#pendingInvoices_dataTable > tbody').html('');
				$('#pendingInvoices_dataTable').append(tableData); 
				var foot = $("#pendingInvoices_dataTable").find('tfoot');
				 if(foot.length){
					 $("#pendingInvoices_dataTable tfoot").remove();
				 }
				
				if(totalAmt >0){
									
					 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#pendingInvoices_dataTable"); 
					    foot.append('<tr><td></td><td></td><td></td> <td></td> <td></td>  <td></td><td class="bold text-right">Total</td> <td class="bold text-right totalinvoice ">'+convertAmountToINR(totalAmt)+' Lakhs</td></tr>');
				}
	           
				var  table= $('#pendingInvoices_dataTable').DataTable( {
					destroy: true,
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
						 updateTotalAmount(table);
						
					}).draw();
					
					$('#pendingInvoices_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
				                 updateTotalAmount(table);
				                 
				                
				                } );
				     
				            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
				                select.append( '<option value="'+d+'">'+d+'</option>' )
				            } );
				        } );
			
					
					
					

					 // Setup - add a text input
			       $('#pendingInvoices_dataTable .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

	               // DataTable
	               var table = $('#pendingInvoices_dataTable').DataTable();
			       
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
	
	function newResignEmpTable(){
		var startDate = null;
		var endDate = null;
		var selectedDateRange = $('#asOnDateResign').text().trim();

		$('#asOnDateResign1').text(selectedDateRange);
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

	// function for Joinee Employee-

	function newJoineeEmpTable(){
		var startDate = null;
		var endDate = null;
		var selectedDateRange = $('#asOnDateJoinee').text().trim();

		$('#asOnDateJoinee1').text(selectedDateRange);
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

function showDetails(groupkey) {
	$("#charts_datatable").removeClass('hide');
	$("#charts_datatable").show();
	$('#example_det').DataTable().clear().destroy();
	var tableData = '';
	$
			.ajax({

				type : "POST",
				url : "/PMS/projectDetailsByGroupName",
				data : "groupName=" + groupkey,
				success : function(data) {
					// console.log(data);
					for (var i = 0; i < data.length; i++) {
						var row = data[i];
						// console.log(row);
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encNumId + "')";
						tableData += '<tr><td > <a class="font_17 " title="Click to View Complete Information" onclick='
								+ clickFunction
								+ '>'
								+ row.strProjectName
								+ ' </a> </td>';
						tableData += ' <td class="font_16">' + row.startDate
								+ '</td>  <td class="font_16">' + row.endDate
								+ ' </td>  <td class="font_16"> '
								+ row.projectCost + '</td> </tr>';
					}

					$('#example_det > tbody').html('');
					$('#example_det').append(tableData);

					$('#example_det').DataTable();
				}

			});

}

// /for table scroll
/*
 * $(document).ready(function() {
 * 
 * pageScroll(); $("#contain").mouseover(function() { clearTimeout(my_time);
 * }).mouseout(function() { pageScroll(); });
 * 
 * getWidthHeader('glance_data_table','table_scroll');
 * 
 * });
 * 
 * var my_time; function pageScroll() { var objDiv =
 * document.getElementById("contain"); objDiv.scrollTop = objDiv.scrollTop + 1;
 * if ((objDiv.scrollTop + 100) == objDiv.scrollHeight) { objDiv.scrollTop = 0; }
 * my_time = setTimeout('pageScroll()', 25); }
 * 
 * function getWidthHeader(id_header, id_scroll) { var colCount = 0; $('#' +
 * id_scroll + ' tr:nth-child(1) td').each(function () { if
 * ($(this).attr('colspan')) { colCount += +$(this).attr('colspan'); } else {
 * colCount++; } });
 * 
 * for(var i = 1; i <= colCount; i++) { var th_width = $('#' + id_scroll + ' >
 * tbody > tr:first-child > td:nth-child(' + i + ')').width(); $('#' + id_header + ' >
 * thead th:nth-child(' + i + ')').css('width',th_width + 'px'); } }
 */
$(document).ready(function() {	
	var t = $('#allDetailsTable').DataTable( {
		destroy: true,
		"paging":   false,
		 "info": false,
        "columnDefs": [ {
            "searchable": false,
            "orderable": false,
            "targets": 0
        } ],
        "order": [[ 1, 'asc' ]]
    } );
 
    t.on( 'order.dt search.dt', function () {
        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        } );
    } ).draw();

});
$(document).ready(function() {
	
	setTimeout(function() {
    newProjectsTable();
}, 2000);

	$('#example_det').DataTable();
	// $('#glance_data_table').dataTable({searching: false, paging: false, info:
	// false});
	/*
	 * var $el = $(".table-responsive"); function anim() { var st =
	 * $el.scrollTop(); var sb = $el.prop("scrollHeight") - $el.innerHeight();
	 * $el.animate({ scrollTop : st < sb / 2 ? sb : 0 }, 30000, anim); }
	 * function stop() { $el.stop(); } anim(); $el.hover(stop, anim);
	 */
	//validatedProjectsTable();
});

var my_time;
$(document).ready(function() {
	pageScroll();
	$("#contain").mouseover(function() {
		clearTimeout(my_time);
	}).mouseout(function() {
		pageScroll();
	});
});

function pageScroll() {
	var objDiv = document.getElementById("contain");
	objDiv.scrollTop = objDiv.scrollTop + 1;
	if (objDiv.scrollTop == (objDiv.scrollHeight - 250)) {
		objDiv.scrollTop = 0;
	}
	my_time = setTimeout('pageScroll()', 25);
}

function loadGroupWiseProjects(encGroupId) {
	//alert(encGroupId);
	$('#example').DataTable().clear().destroy();
	var tableData = "";
	 
	
	
	$.ajaxRequest
			.ajax({
				type : "POST",
				url : "/PMS/mst/ViewProjectDetails",
				data : "encGroupId=" + encGroupId,
				success : function(data) {
					console.log(data);
					var sysDate = new Date();
					/*Bhavesh(26-07-23)*/ 
					var valuesList = []; // Initialize an empty array to store the values
				    $(".closureList").each(function() {
				      var value = $(this).text().trim(); // Get the text content and remove any leading/trailing spaces
				      valuesList.push(value); // Add the value to the array
				    });

				    
				    console.log(valuesList); 
					for (var i = 0; i < data.length; i++) {
						var row = data[i];
						var serialNo = i + 1;
						$('#groupId').val(encGroupId);
						
						/*--------------------------  Color change after enddate passed  --------------------------------------------------*/
						if (row.endDate != null || row.endDate === '') {
						    var endDateString = row.endDate;
						    var endDateParts = endDateString.split("/");
						    var endDate = new Date(endDateParts[2], endDateParts[1] - 1, endDateParts[0]);
						    if (valuesList.includes(row.projectRefrenceNo)) {
						        tableData += "<tr id='" + row.encProjectId + "' style='color:#cc9900'><td>" + serialNo + "</td>";
						        tableData += "<td class='font_16' style='width:35%;' id='td2_"+row.encProjectId+"'><p><a class='bold golden-text' title='Click to View Complete Information' onclick=viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId+ "')>"+ row.strProjectName+ "</a> </p>"
								+ "<p class='font_14'><i><a class='orange golden-text' title='Click here to view Funding org details' data-toggle='modal' data-target='#clientDetails' data-whatever='"
								+ row.encClientId+ ";"+ row.encProjectId+ ";"+ row.endUserId+ "' class='text-center'>"+ row.clientName+ "</a></i></p> <p class='bold golden-text font_14 text-left'> "+row.projectRefrenceNo+"</p><p class=' font_12' >"+row.businessType+"</p></td>";
						    }else if(endDate.getTime() < sysDate.getTime() && !valuesList.includes(row.projectRefrenceNo)){
						    	 tableData += "<tr id='" + row.encProjectId + "' style='color:#808080'><td>" + serialNo + "</td>";
							        tableData += "<td class='font_16' style='width:35%;' id='td2_"+row.encProjectId+"'><p><a class='bold grey-text' title='Click to View Complete Information' onclick=viewProjectDetails('/PMS/projectDetails/"
									+ row.encProjectId+ "')>"+ row.strProjectName+ "</a> </p>"
									+ "<p class='font_14'><i><a class='orange grey-text' title='Click here to view Funding org details' data-toggle='modal' data-target='#clientDetails' data-whatever='"
									+ row.encClientId+ ";"+ row.encProjectId+ ";"+ row.endUserId+ "' class='text-center'>"+ row.clientName+ "</a></i></p> <p class='bold grey-text font_14 text-left'> "+row.projectRefrenceNo+"</p><p class=' font_12' >"+row.businessType+"</p></td>";
						    	
						    }
						    else {
						        tableData += "<tr id='" + row.encProjectId + "' style='color:#4CAF50'><td>" + serialNo + "</td>";
						        tableData += "<td class='font_16' style='width:35%;' id='td2_"+row.encProjectId+"'><p><a class='bold success-text' title='Click to View Complete Information' onclick=viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId+ "')>"+ row.strProjectName+ "</a> </p>"
								+ "<p class='font_14'><i><a class='orange success-text' title='Click here to view Funding org details' data-toggle='modal' data-target='#clientDetails' data-whatever='"
								+ row.encClientId+ ";"+ row.encProjectId+ ";"+ row.endUserId+ "' class='text-center'>"+ row.clientName+ "</a></i></p> <p class='bold success-text font_14 text-left'> "+row.projectRefrenceNo+"</p><p class=' font_12' >"+row.businessType+"</p></td>";
						    }
						} else {
						    tableData += "<tr id='" + row.encProjectId + "'><td>" + serialNo + "</td>";
						}
						/*-----------------------------------------------------------------------------------------------------------------*/
						
						//tableData += "<tr id='"+row.encProjectId+"'><td>" + serialNo + "</td>";
						/*if (row.projectRefrenceNo != null) {
							tableData += "<td>" + row.projectRefrenceNo
									+ "</td>";
						} else {
							tableData += "<td></td>";
						}*/
						
						 
						/*tableData += "<td class='font_16' style='width:35%;' id='td2_"+row.encProjectId+"'><p><a class='bold' title='Click to View Complete Information' onclick=viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId+ "')>"+ row.strProjectName+ "</a> </p>"
								+ "<p class='font_14'><i><a class='orange' title='Click here to view Funding org details' data-toggle='modal' data-target='#clientDetails' data-whatever='"
								+ row.encClientId+ ";"+ row.encProjectId+ ";"+ row.endUserId+ "' class='text-center'>"+ row.clientName+ "</a></i></p> <p class='bold blue font_14 text-left'> "+row.projectRefrenceNo+"</p><p class=' font_12' >"+row.businessType+"</p></td>";*/
						/*tableData += "<td style='width:10%;'>"
								+ row.businessType + "</td>";*/
						/*tableData += "<td style='width:10%;'>" + row.startDate
								+ "</td>";
						tableData += "<td style='width:10%;'>" + row.endDate
								+ "</td>";*/
						tableData += "<td align='center' style='width:23%;'>"+ row.startDate + " to  " + row.endDate+ "<br> ("+ row.projectDuration + " Months)</td>";
						if (row.strPLName != null) {
							tableData += "<td style='width:15%;'><span class='font_16'>"
									+ row.strPLName
									+ "</span><p><a title='Team details' data-toggle='modal' data-target='#team-modal' onclick=viewTeamDetails('"
									+ row.encProjectId
									+ "')><span class='font_12 pull-right'><i class='fa fa-users'></i></span></a></p></td>";
						} else {
							tableData += "<td></td>";
						}
						tableData += "<td align='right'><input type='hidden' id='hiddenTotalAmt_"
								+ row.encProjectId
								+ "' value="
								+ row.strTotalCost
								+ "/><span  class='convertLakhs' id='totalAmt_"
								+ row.encProjectId
								+ "'>"
								+ row.strTotalCost
								+ " </span></td>";
						if (row.strReceivedAmout != null) {
							
							tableData += "<td class='hide'  align='right'> <input type='hidden' id='hiddenAmt_"+row.encProjectId+"' value='"+row.strReceivedAmout+"'/> <a title='Click here to view received details' data-toggle='modal' data-target='#amtreceive' class='text-center' data-whatever='"+row.encProjectId+";"+row.startDate+";"+row.strTotalCost+"'> <span   id='amt_"+row.encProjectId+"'>"
									+ row.strReceivedAmout + " </span></a></td>";
						} else {
							tableData += "<td class='hide'></td>";
						}
						/*bhavesh(2-08-23)(removed the  style='width:10%;' from  td)*/
						tableData += "<td >  <ul>";
						
							for(var j =0;j<row.projectDocument.length;j++)
								tableData +="<li ><a class='"+row.projectDocument[j].classColor+" bold' onclick='downloadDocument(\""+row.projectDocument[j].encNumId+"\")'>"+ row.projectDocument[j].documentTypeName+"</a></li>";
							tableData +=  "</ul><a style='float:right;' onclick=viewProjectDetails('/PMS/mst/documentDetails/"+row.encProjectId+"')>View All</a></td> </tr>";
						 

					}
					tableData += '</tbody>';
					$('#example tbody').empty();
					$('#example').show();
					$('#GroupWiseTable').show();
					$('#example').append(tableData);
					
					
					
					var  table= $('#example').DataTable( {
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
					
					$('#example .filters th[class="comboBox"]').each( function ( i ) {
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
			       $('#example .filters th[class="textBox"]').each( function () {                 
			           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

			       } );

                   // DataTable
                   var table = $('#example').DataTable();
			       
			       table.columns().eq( 0 ).each( function ( colIdx ) {
			           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			               table
			                           .column( colIdx )
			                           .search( this.value )
			                           .draw() ;
			                   
			                                           } );
			} );
			       convertToLakhWithoutLabel();

				},
				error : function(e) {
					// $('#RecurringManpowerAmt').val(0);
					alert('Error: ' + e);
				}
			});
	window.scrollBy(0,1400);
	/*Modified by devesh on 27-09-23 to fix the scrolling to table*/
	/*setTimeout(function () {
        var targetTable = document.getElementById('example');
        if (targetTable) {
            targetTable.scrollIntoView({ behavior: 'smooth' });
        }
    }, 500);*/
    /*End of Scroll*/
}

/*
 * function loadGroupWiseProjects(encGroupId){
 * $('#groupWiseProject').removeClass('hidden');
 * $('#groupWiseProjectDtl').find('div').remove();
 * 
 * var url = "/PMS/mst/ViewProjectDetails?encGroupId="+encGroupId;
 * 
 * var data='<div > <iframe id="test" src='+url+' width="100%;" height="100%;"
 * margin-top="0px;" frameborder="0" ></iframe> </div>';
 * 
 * $('#groupWiseProjectDtl').append(data); }
 */
$(document)
		.ready(
				function() {
					$('#GroupWiseTable').hide();
					$('#example').hide();
					var strStart = "01/04/";
					var strEnd = "31/03/";
					var d = new Date();
					var currentYear = d.getFullYear();
					var currentMonth = d.getMonth();
					var lastYear = d.getFullYear() - 1;
					//alert("as on date"+currentYear);
					var dateString = '01/04/';
					if(currentMonth<=2){
						dateString  += lastYear;
					}
					else{
						dateString  += currentYear;
					}
					// alert("dateString"+dateString);
					//$("#asOnDate").text(dateString);
					// $("#asOnDate1").text(dateString);
					$("#asOnDateExp").text(dateString);
					// $("#asOnDateExp1").text(dateString);

					$("#dtStartDate")
							.datepicker(
									{

										showOn : "both",
										buttonText : "<i class='fa fa-calendar font_20 green1'></i>",
										dateFormat : 'dd/mm/yy',
										changeMonth : true,
										changeYear : true,
										maxDate : '0',

										onSelect : function() {
											var date = $('#dtStartDate').val();
											// alert("Date Value"+date);

											$.ajaxRequest
													.ajax({
														type : "POST",
														url : "/PMS/mst/getIncomeByDate",
														data : "startDate="
																+ date,
														success : function(
																response) {
															// console.log(response);
															// var k = response;

															// $('#incomeOndate').html(k);

															// $("#asOnDate").text(date);
															var fieldValue = response;
															var convertedStringAmount = convertINRToAmount(fieldValue);

															console
																	.log(convertedStringAmount);
															var amount = (parseFloat(convertedStringAmount)) / 100000;
															console
																	.log(response
																			+ " "
																			+ amount);
															// console.log(roundUpTo2Decimal(amount));
															var convertedIntegerAmount = convertAmountToINR(roundUpTo2Decimal(
																	amount, 2));

															$('#incomeOndate')
																	.html(
																			convertedIntegerAmount
																					+ " Lakhs");

															$("#asOnDate")
																	.text(date);

														}
													});

										}
									});

					$("#dtExpStartDate")
							.datepicker(
									{

										showOn : "both",
										buttonText : "<i class='fa fa-calendar font_20'></i>",

										/*
										 * buttonImage:
										 * "/PMS/resources/app_srv/PMS/global/images/slider/slider4.jpg",
										 * buttonImageOnly: true,
										 */
										dateFormat : 'dd/mm/yy',
										changeMonth : true,
										changeYear : true,
										maxDate : '0',
										showOn : 'both',

										onSelect : function() {
											var date = $('#dtExpStartDate')
													.val();
											// alert("Date Value"+date);

											$.ajaxRequest
													.ajax({
														type : "POST",
														url : "/PMS/mst/getExpenditureByDate",
														data : "startDate="
																+ date,
														success : function(
																response) {
															// console.log(response);
															// var k = response;

															// $('#expenditureOndate').html(k);
															// $("#asOnDateExp").text(date);
															var fieldValue = response;
															var convertedStringAmount = convertINRToAmount(fieldValue);

															// console.log(convertedStringAmount);
															var amount = (parseFloat(convertedStringAmount)) / 100000;
															// console.log(response
															// + " "+ amount);
															// console.log(roundUpTo2Decimal(amount));
															var convertedIntegerAmount = convertAmountToINR(roundUpTo2Decimal(
																	amount, 2));

															$(
																	'#expenditureOndate')
																	.html(
																			convertedIntegerAmount
																					+ " Lakhs");
															$("#asOnDateExp")
																	.text(date);

														}
													});

										}
									});

					$('.ui-datepicker-trigger').attr('alt', 'Calender');
					$('.ui-datepicker-trigger').attr('title',
							'Select Start Date');
				});

$('#dash-income-modal').on('show.bs.modal', function(event) {
	initialzeIncomeChart();
	$(this).off('shown.bs.modal');
});
$('#dash-expenditure-modal').on('show.bs.modal', function(event) {
	initialzeExpenditureChart();
	$(this).off('shown.bs.modal');
});

$('#dash-newProposalList-modal').on('shown.bs.modal', function(event) {		
	initializeNewProposals();
	$(this).off('shown.bs.modal');
});

$('#dash-newProjectList-modal').on('shown.bs.modal', function(event) {		
	initializeNewProjects();
	$(this).off('shown.bs.modal');
});

$('#dash-closedProjectList-modal').on('shown.bs.modal', function(event) {		
	initializeClosedProjects();
	$(this).off('shown.bs.modal');
});

// decalare the validated  modal by varun on 05-12-2023
$('#dash-validationProject-modal').on('shown.bs.modal', function(event) {		
	initializeValidatedProjects();
	$(this).off('shown.bs.modal');
});
$('#dash-pending-payments-modal').on('shown.bs.modal', function(event) {
	/*alert("inside newProjectList-modal");*/
	initializePendingPayments();
	$(this).off('shown.bs.modal');
});
$('#dash-pending-invoices-modal').on('shown.bs.modal', function(event) {
	/*alert("inside newProjectList-modal");*/
	initializePendingInvoices();
	$(this).off('shown.bs.modal');
});
$('#dash-closure-pending-modal').on('shown.bs.modal', function(event) {		
	initializeClosurePending();
	$(this).off('shown.bs.modal');
});
$('#dash-new-joinee-modal').on('shown.bs.modal', function(event) {		
	initializeNewJoinee();
	$(this).off('shown.bs.modal');
});
$('#dash-resigned-emp-modal').on('shown.bs.modal', function(event) {		
	initializeResignedEmp();
	$(this).off('shown.bs.modal');
});

$('#dash-closedProjectListByGroup-modal').on('shown.bs.modal', function(event) {	
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	$("#grpId").val(input);
	closedProjectsNew(input,0);
	
	/*$(this).off('shown.bs.modal');*/
});

$('#dash-newProposalListByGroup-modal').on('shown.bs.modal', function(event) {	
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	$("#proposalGrpId").val(input);
	proposalSubmittedNew(input,0);
	
	/*$(this).off('shown.bs.modal');*/
});

$('#dash-incomeByGroup-modal').on('shown.bs.modal', function(event) {
	
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	$("#incomeGroupId").val(input);
	 incomeTableNew(input,0);
	
	/*$(this).off('shown.bs.modal');*/
});

$('#dash-resigned-empByGroup-modal').on('shown.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	
	$("#resignGroupId").val(input);
	resignEmployeelist(input,0);
	
	/*$(this).off('shown.bs.modal');*/
});

$('#dash-newProjectListByGroup-modal').on('shown.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	$("#projectGroupId").val(input);
	projectReceivedNew(input,0);
	
	/*$(this).off('shown.bs.modal');*/
});

$('#dash-new-joinee-ByGroup-modal').on('shown.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	$("#joinGroupId").val(input);
	
	joineeEmployeeList(input,0);
	
	//$(this).off('shown.bs.modal');
});

function incomeTable(){
	var groupColumn = 0;
	var startDate = null;
	var endDate = null;
	var selectedDateRange = $('#asOnDate').text().trim();

	$('#asOnDate1').text(selectedDateRange);
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
	$('#income_dataTable').DataTable().clear().destroy();
	 
	var tableData = '';
	$.ajaxRequest.ajax({
		
		type : "POST",
		url : "/PMS/mst/getPaymentReceivedDetails",
		data:{
			"startDate":startDate,
			"endDate":endDate
		},
		success : function(response) {
			
			//console.log(response);
			var totalAmount = 0;
			for (var i = 0; i < response.length; i++) {
				var row = response[i];	
				var strReferenceNumber=row.strReferenceNumber;
				if(row.strReferenceNumber==null){
					strReferenceNumber='';
				}
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
					+ row.encProjectId + "')";
				tableData += '<tr ><td  class="font_14" style="width:15%;">'
					+ row.groupName + '</td>';
			tableData += '<td > <a class="font_14" title="Click to View Complete Information" onclick='
					+ clickFunction
					+ '>'
					+ row.projectName
					+ ' </a><p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p></td>';
			tableData += '<td class="font_14 text-right"> <input type="hidden" id="hiddenIncomeAmt_'
					+ row.encProjectId
					+ '" value='
					+ row.encProjectId
					+ '/> <span  class="convert_lakhs_td" id="incomeAmt_'
					+ row.encProjectId
					+ '">'
					+ row.strReceivedAmount + '</span></td> </tr>';
				
				totalAmount += row.numReceivedAmountLakhs;
			}
			$('#income_dataTable > tbody').html('');
				
			$('#income_dataTable').append(tableData);
			$('#income_dataTable').DataTable().destroy();
			   
			var foot = $("#income_dataTable").find('tfoot');
			 if(foot.length){
				 $("#income_dataTable tfoot").remove();
			 }
			
			if(totalAmount >0){
				var tempConverted = convertAmountToINR(totalAmount);
				
				$('#incomeOndate').text(tempConverted +' Lakhs');				
				 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#income_dataTable"); 
				    foot.append('<tr> <td></td> <td class="bold"><span class="text-right">Total</span></td> <td class="bold text-right">'+tempConverted+' Lakhs </td></tr>');
			}else{
				$('#incomeOndate').text(0); 
			}	
			var  table= $('#income_dataTable').DataTable( {
				 	dom: 'Bfrtip',
			       /* "ordering": false,*/
			        "paging":   false,			        
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
			        "columnDefs": [
			                       { "visible": false, "targets": groupColumn }
			                   ],
			                   "order": [[ groupColumn, 'asc' ]],
			                   "displayLength": 25,
			                   "drawCallback": function ( settings ) {
			                       var api = this.api();
			                       var rows = api.rows( {page:'current'} ).nodes();
			                       var last=null;
			            
			                       api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
			                           if ( last !== group ) {
			                               $(rows).eq( i ).before(
			                                   '<tr class="group"><td colspan="2" class="font_16 bold">'+group+'</td></tr>'
			                               );
			            
			                               last = group;
			                           }
			                       } );
			                   }
			    });	
			 
			    $('#income_dataTable tbody').on( 'click', 'tr.group', function () {
			        var currentOrder = table.order()[0];
			        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
			            table.order( [ groupColumn, 'desc' ] ).draw();
			        }
			        else {
			            table.order( [ groupColumn, 'asc' ] ).draw();
			        }
			    } );		    
			     
		}
		
	});
	
}		
	
}
// Expenditure Table
function expenditureTable() {
	table = $('#expenditure_dataTable').DataTable().clear().destroy();
	var expenditureTableId = $('#expenditure_dataTable').attr('id')

	var tableData = '';
	$.ajaxRequest
			.ajax({

				type : "POST",
				url : "/PMS/mst/getExpenditureByGroupByProject",
				success : function(response) {
					// console.log(response);
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						// console.log(row);
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId + "')";
						tableData += '<tr ><td  class="font_14" style="width:15%;">'
								+ row.groupName + '</td>';
						tableData += '<td > <a class="font_14" title="Click to View Complete Information" onclick='
								+ clickFunction
								+ '>'
								+ row.projectName
								+ ' </a> </td>';
						tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenExpenditureAmt_'
								+ row.encProjectId
								+ '" value='
								+ row.encProjectId
								+ '/> <span  class="convert_lakhs_td" id="expenditureAmt_'
								+ row.encProjectId
								+ '">'
								+ row.strTotalExpenditure + '</td> </tr>';
					}

					$('#expenditure_dataTable > tbody').html('');
					$('#expenditure_dataTable').append(tableData);
					// convertToLakhsForComponent(expenditureTableId);
					table = $('#expenditure_dataTable').DataTable();

					$('#expenditure_dataTable .filters th[class="comboBox"]')
							.each(
									function(i) {
										var colIdx = $(this).index();
										var select = $(
												'<select style="width:100%" ><option value="">All</option></select>')
												.appendTo($(this).empty())
												.on(
														'change',
														function() {
															var val = $.fn.dataTable.util
																	.escapeRegex($(
																			this)
																			.val());
															table
																	.column(
																			colIdx)
																	.search(
																			val ? '^'
																					+ val
																					+ '$'
																					: '',
																			true,
																			false)
																	.draw();
														});

										table
												.column(colIdx)
												.data()
												.unique()
												.sort()
												.each(
														function(d, j) {
															select
																	.append('<option value="'
																			+ d
																			+ '">'
																			+ d
																			+ '</option>')
														});
									});

					// Setup - add a text input
					$('#expenditure_dataTable .filters th[class="textBox"]')
							.each(
									function() {
										$(this)
												.html(
														'<div class="lighter"><input type="text"  style="width:100%" /></div>');

									});

					// DataTable
					var table = $('#expenditure_dataTable').DataTable();

					// Apply the search
					table.columns().eq(0).each(
							function(colIdx) {
								$('input', $('.filters th')[colIdx]).on(
										'keyup change',
										function() {
											table.column(colIdx).search(
													this.value).draw();

										});
							});

				}

			});

}
/*
 * function convertToLakhsForComponent(tableId){
 * 
 * $(".convert_lakhs_td").each(function() { var fieldId=this.id;
 * 
 * var fieldValue = this.innerText;
 * 
 * if(fieldId){ var convertedStringAmount = convertINRToAmount(fieldValue);
 * 
 * var amount = (parseFloat(convertedStringAmount))/100000;
 * 
 * var convertedIntegerAmount=convertAmountToINR(roundUpTo2Decimal(amount,2));
 * 
 * $("#"+fieldId).text(convertedIntegerAmount +' Lakhs'); } }); }
 */
$(".emp").on("click", getColumnID);

function getColumnID() {

	var $td = $(this), $th = $td.closest('table').find('th').eq($td.index());
	// console.log($th.attr("id"));
	var designation = $th.attr("id");
	var group = $td.closest('tr').children('td:first').text();
	// console.log(group);

	$.ajaxRequest.ajax({

		type : "POST",
		url : "/PMS/mst/getEmpbyDesignationForGroup",
		data : {"group":group,
			"designation":designation
		},
		success : function(data) {
			// console.log(data);
			var tableData = "";
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				var sno = i + 1;
				tableData += "<tr><td>" + sno + "</td><td>" + row.employeeId
						+ "</td> <td>" + row.employeeName + "</td></tr>";

			}
			tableData += '</tbody>';

			$('#datatable1 tbody').empty();
			$('#datatable1').append(tableData);
			$('#empDetailModal').modal('show');

		}
	});
}

$(document)
		.ready(
				function() {

					// $('#example').DataTable();
					var table = $('#example').DataTable({
						"columnDefs" : [ {
							"orderable" : false,
							"targets" : 0
						} ],
						"order" : [ [ 1, 'asc' ] ]
					});

					table.on('order.dt search.dt', function() {
						table.column(0, {
							search : 'applied',
							order : 'applied'
						}).nodes().each(function(cell, i) {
							cell.innerHTML = i + 1;
							table.cell(cell).invalidate('dom');
						});
					}).draw();

					$('#example .filters th[class="comboBox"]')
							.each(
									function(i) {
										var colIdx = $(this).index();
										var select = $(
												'<select style="width:100%" ><option value="">All</option></select>')
												.appendTo($(this).empty())
												.on(
														'change',
														function() {
															var val = $.fn.dataTable.util
																	.escapeRegex($(
																			this)
																			.val());
															table
																	.column(
																			colIdx)
																	.search(
																			val ? '^'
																					+ val
																					+ '$'
																					: '',
																			true,
																			false)
																	.draw();
														});

										table
												.column(colIdx)
												.data()
												.unique()
												.sort()
												.each(
														function(d, j) {
															select
																	.append('<option value="'
																			+ d
																			+ '">'
																			+ d
																			+ '</option>')
														});
									});

					// Setup - add a text input
					$('#example .filters th[class="textBox"]')
							.each(
									function() {
										$(this)
												.html(
														'<div class="lighter"><input type="text"  style="width:100%" /></div>');

									});

					table.columns().eq(0).each(
							function(colIdx) {
								$('input', $('.filters th')[colIdx]).on(
										'keyup change',
										function() {
											table.column(colIdx).search(
													this.value).draw();

										});
							});

				});

$('#amtreceive')
		.on(
				'show.bs.modal',
				function(event) {
					var button = $(event.relatedTarget);
					var input = button.data('whatever');
					var inputs = input.split(';');
					var projectId = inputs[0];
					
					var split1 = $('#td2_'+projectId).find('p:eq(0)').text();
					var split2 = $('#td2_'+projectId).find('p:eq(1)').text();
					
					$('#projectForPayment').text(split1);
					$('#clientIdForPayment').text(split2);
					var startDate = inputs[1];
					var projectCost = inputs[2].trim();
					// console.log(projectCost);
					var tableData = "";
					$.ajax({
								type : "POST",
								url : "/PMS/mst/getProjectPaymentReceivedByProjectId",
								data : "encProjectId=" + projectId,
								async : false,
								success : function(response) {
									// console.log(response);
									var SumOfReceivedAmt = 0
									var paymentReceive = 0;
									var paymentDue = 0;
									
									paymentDue = parseFloat(convertINRToAmount(projectCost));
									tableData += "<tr><td>"
											+ startDate
											+ "</td><td><b>Total Project Cost</b></td><td class='text-right'><b>"
											+ convertAmountToINR(paymentDue)
											+ "</b></td><td class='text-right'>â‚¹ "
											+ paymentReceive + "</td></tr>";

									for (var i = 0; i < response.length; i++) {
										var row = response[i];
										var newAmt = row.numReceivedAmount;
										SumOfReceivedAmt += newAmt;
										tableData += "<tr><td>"
												+ row.strdtPayment + "</td> ";
										tableData += "<td><p>"
												+ row.strPaymentMode + " , "
												+ row.strUtrNumber
												+ "</p><p class='pad-left'><b>";
										if (row.encInvoiceId != null) {
											tableData += "Payment Received Against Invoice Ref No. : </b><a title='Click here to view Invoice details' data-toggle='modal' data-target='#invoiceDetail' onclick=viewInvoiceDetails('"
													+ row.encInvoiceId
													+ "') class='text-center'>"
													+ row.invoiceCode
													+ "</a></p></td>";
											tableData += "<td class='text-right'>"
													+ row.paymentDue
													+ "</td> <td class='text-right'>"
													+ convertAmountToINR(newAmt)
													+ "</td></tr>";

										} else {
											tableData += "Payment Received without Invoice Details : </b></p></td>";
											tableData += "<td class='text-right'>"
													+ row.paymentDue
													+ "</td> <td class='text-right'>"
													+ convertAmountToINR(row.numBalanceAmount)
													+ "</td></tr>";

										}
									}
									if (SumOfReceivedAmt
											&& SumOfReceivedAmt > 0) {
										tableData += "<tr><td colspan='3' class='font_eighteen text-right'><b>Total Payment Received:</b></td><td class='font_eighteen text-right'><b>"
												+ convertAmountToINR(SumOfReceivedAmt)
												+ "</b></td> </tr>";
										tableData += "<tr><td colspan='3' class='font_eighteen text-right'><b>Payment Remaining:</b></td><td class='font_eighteen text-right'><b>"
												+ row.paymentDue
												+ "</b></td> </tr>";

									}
									tableData += '</tbody>';
									$('#datatable tbody').empty();
									$('#datatable').append(tableData);
								},
								error : function(e) {
									alert('Error: ' + e);
								}
							});
					$(this).off('shown.bs.modal');
				});

var scrollPos = 0;
$('#clientDetails').on('show.bs.modal', function(event) {
	//console.log("inside client modal");
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	// console.log("data"+input);
	if (typeof input !== 'undefined') {
		var inputs = input.split(';');
		var clientId = inputs[0];
		var projectId = inputs[1];
		var endUserId= inputs[2];
		loadClientData(clientId,projectId,endUserId);
	}
	$('body').css({
		overflow : 'hidden',
		position : 'fixed',
		top : -scrollPos
	});
}).on('hide.bs.modal', function() {
	$('body').css({
		overflow : '',
		position : '',
		top : ''
	}).scrollTop(scrollPos);
});

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
		  newJoineeEmpTable();
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
	    });
	  
	  $('.go-btn1').click(function() {
		  var startRange = $("#from1").val();
		  var endRange = $("#to1").val();
		 // console.log("go-btn1 startRange"+startRange)
		  //console.log("go-btn1 endRange"+endRange)
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

// below function to get the date on calendar by varun on 06-12-2023
$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromvalidated" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	        $('.valdprojects').val(0); // 12-10-2023
	      }),
	    to = $( "#tovalidated" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	     
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });
	  
	  $('.go-btnvalidate').click(function() {
		  var startRange = $("#fromvalidated").val();
		  var endRange = $("#tovalidated").val();
		 // console.log("go-btn1 startRange"+startRange)
		  //console.log("go-btn1 endRange"+endRange)
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		 
		  $('#asOnValidatedProjects').text(selectedRange);
		  //alert('Called');
		  validatedProjectsTable();
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
	    from = $( "#fromGroup" )
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
	    to = $( "#toGroup" ).datepicker({
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
	  
	  $('.go-btn-group').click(function() {
		  var startRange = $("#fromGroup").val();
		  var endRange = $("#toGroup").val();
		  var groupId=$("#grpId").val();
		  $("#fromDetNew").val('');
		 // console.log("go-btn1 startRange"+startRange)
		 // console.log("go-btn1 endRange"+endRange)
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		 
	 /* $('#asOnDateClosedProjects').text(selectedRange);*/
		  closedProjectsNew(groupId,1);
		  
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
		    from = $( "#fromJoinNew" )
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
		    to = $( "#toJoinNew" ).datepicker({
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
		  
		  $('.go-btn-joinNew').click(function() {
			  var startRange = $("#fromJoinNew").val();
			  var endRange = $("#toJoinNew").val();
			  var groupId=$("#joinGroupId").val();
			  $("#fromDetNew").val('');
			 // console.log("go-btn1 startRange"+startRange)
			 // console.log("go-btn1 endRange"+endRange)
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			 
			 
			  //$('#asOnDateJoinee').text(selectedRange);
			 joineeEmployeeList(groupId,1);
			
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
		    from = $( "#fromProposalNew" )
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
		    to = $( "#toProposalNew" ).datepicker({
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
		  
		  $('.btn-successNew').click(function() {
			  var startRange = $("#fromProposalNew").val();
			  var endRange = $("#toProposalNew").val();
			  var groupId=$("#proposalGrpId").val();
			  var hiddenFromVal=$("#fromDetNew").val();
				
			  $("#hiddenFromVal").val('');
			  //$("#toDet").val('');
			 /* $("#toResNew").val('');
			  $("#asOnDateRes1").text('');
			  $("#toJoinNew").val('');
			  $("#fromJoinNew").val('');
			  $("#asOnDateJoin1").text('');
			  
			  $("#fromGroup").val('');
			  $("#toGroup").val('');
			  $("#asOnDateClosedProj1").text('');
			  
			  $("#fromNew").val('');
			  $("#toNew").val('');
			  $("#asOnDateProj1").text('');
			  
			  $("#fromProposalNew").val('');
			  $("#toProposalNew").val('');
			  $("#asOnDateProp1").text('');*/
			
			 // console.log("go-btn1 startRange"+startRange)
			 // console.log("go-btn1 endRange"+endRange)
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			 
			 // $('#asOnDateProposals').text(selectedRange);
			  proposalSubmittedNew(groupId,1);
			
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
		    from = $( "#fromNew" )
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
		    to = $( "#toNew" ).datepicker({
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
		  
		  $('.go-btnNew').click(function() {
			  var startRange = $("#fromNew").val();
			  var endRange = $("#toNew").val();
			  var groupId=$("#projectGroupId").val();
			  $("#fromDetNew").val('');
			  //console.log("go-btn1 startRange"+startRange)
			 // console.log("go-btn1 endRange"+endRange)
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			 
			/*  $('#asOnDateProjects').text(selectedRange);*/
			  projectReceivedNew(groupId,1);
			
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
		    from = $( "#fromResNew" )
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
		    to = $( "#toResNew" ).datepicker({
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
		  
		  $('.go-btn-resignNew').click(function() {
			  var startRange = $("#fromResNew").val();
			  var endRange = $("#toResNew").val();
			  var groupId=$("#resignGroupId").val();
			  $("#fromDetNew").val('');
			//  console.log("go-btn1 startRange"+startRange)
			 // console.log("go-btn1 endRange"+endRange)
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			 
				 
			  //$('#asOnDateResign').text(selectedRange);
			  resignEmployeelist(groupId,1);
			
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
	  
	});






$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#incomefromNew" )
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
	    to = $( "#incometoNew" ).datepicker({
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
	  
	  $('.go-btn2-New').click(function() {
		  var startRange = $("#incomefromNew").val();
		  var endRange = $("#incometoNew").val();
		  var groupId= $("#incomeGroupId").val();
		  $("#fromDetNew").val('');
		  //console.log("go-btn2 startRange"+startRange)
		  //console.log("go-btn2 endRange"+endRange)
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		 
		 /* $('#asOnDate').text(selectedRange);*/
		  //alert('Called');
		  incomeTableNew(groupId,1);
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
	    from = $( "#incomefrom" )
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
	    to = $( "#incometo" ).datepicker({
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
	  
	  $('.go-btn2').click(function() {
		  var startRange = $("#incomefrom").val();
		  var endRange = $("#incometo").val();
		 // console.log("go-btn2 startRange"+startRange)
		 // console.log("go-btn2 endRange"+endRange)
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		 
		  $('#asOnDate').text(selectedRange);
		  //alert('Called');
		  incomeTable();
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
	 var maxDate = new Date(2024,11, 31); // changes in proposal year 2024 
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromProposal" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: maxDate,
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	     // reset the select month years zero by varun 
		      $('#months').val(0);
	      }),
	    to = $( "#toProposal" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: maxDate,
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });
	  
         // below function added by varun to reset the dates of calendar year on 18-01-2024
	  $('#fromProposal').change(function() {
		  $("#incomecalendarYearDropdown").val(0) ;
	  });
	  
		$('#toProposal').change(function() {
			 $("#incomecalendarYearDropdown").val(0) ;
			  });
		
		$('#from').change(function() {
			  $("#projectcalendarYearDropdown").val(0) ;
		});
		
		$('#to').change(function() {
		$("#projectcalendarYearDropdown").val(0) ;
		});
		
		 $('#from1').change(function() {
			  $("#closedcalendarYearDropdown").val(0) ;
		  });
		  
			$('#to1').change(function() {
				 $("#closedcalendarYearDropdown").val(0) ;
				  });
			
			$('#fromvalidated').change(function() {
				  $("#calendarYearDropdown").val(0) ;
			});
			
			$('#tovalidated').change(function() {
			$("#calendarYearDropdown").val(0) ;
			});

	  $('#goProposal').click(function() {
		
		  var startRange = $("#fromProposal").val();
		  var endRange = $("#toProposal").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateProposals').text(selectedRange);
		  
		  //alert('Called');
		  newProposalsTable();
		  projectsReceivedTable();  // call the project received function [08-02-2024]
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
	 var maxDate = new Date(2024, 11, 31); // changes in project recived year 2024 
	  var dateFormat = "dd/mm/yy",
	    from = $( "#from" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: maxDate,
	  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	        $('.listprojects').val(0); // reset [12-10-2023]
	      }),
	    to = $( "#to" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: maxDate,
	      /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	      $('.listprojects').val(0);  // reset [12-10-2023]
	    });

	  $('.go-btn').click(function() {
		  var startRange = $("#from").val();
		  var endRange = $("#to").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateProjects').text(selectedRange);
		  
		  //alert('Called');
		  newProjectsTable();
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


function viewAllProjectData(){
	openWindowWithPost('POST','/PMS/generateReportForInterface3',{"eCode":"EPRJ"},'_blank');
	}

$("#fromDt").MonthPicker( {

    Button: "<i class='pad-left-half font_eighteen fa fa-calendar '></i>",
    changeMonth: true,
    changeYear: true,
    dateFormat: 'mm/yy',
    autoclose: true,
    MaxMonth: 0,
    Position: {
        collision: 'fit flip'
    },
    OnAfterChooseMonth: function(){
    $("#divCat").removeClass('hidden');    
       var date = $(this).val();
     
        var res = date.split("/");
        var month = res[0];
        var year = res[1];
     /* alert(month);
      alert(year);*/
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
$("#month").val(month);
$("#year").val(year);
 
        }
    },
    beforeShow: function (input) {
                    $(input).css({
                        "position": "relative",
                        "z-index": 1060 
                    });
                },
                onClose: function () { $('.month-picker').css({ 'z-index': 0  } ); }   
                });
                
 $(".reports").click(function(){

  $(".month-picker").css({ 'z-index': '1060'  } );
  
});
$(".monthClose").click(function(){

  $(".month-picker").css({ 'z-index': '-1'  } );
  
  
});            

function initializeNewProposals() {
	newProposalsTable();
	projectsReceivedTable();  // call the project received function [08-02-2024]
}
function initializeNewProjects() {
	newProjectsTable();
}
function initializeClosurePending() {
	pendingClosuresTable();
}

	function initializeClosedProjects() {		
		closedProjectsTable();
	}
	
	// declare the below function and call validatedProjectsTable by varun on 05-12-2023
	function initializeValidatedProjects(){
		validatedProjectsTable();
	}
	
	function initializeNewJoinee() {
		newJoineeEmpTable();
	}
	
	function initializeResignedEmp() {
		newResignEmpTable();
	}

	
	function getEncProjectId(encApplicationId){
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getProjectIdByApplicationId",
			data : {"encApplicationId":encApplicationId,
			async:false,		
			},
			success : function(response) {
			if(response){
				window.location.href= "/PMS/projectDetails/"+response;
			}
			else{
				sweetSuccess('Attention!','No project found');
				return false;
			}
			

		}
	});
	}

	function openProjectDetails(encApplicationId){
		getEncProjectId(encApplicationId);
		
		
	}
	$(document).ready(function() {
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
		//$("#asOnDate").text(dateString);
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

	// Changes in table data on 08-06-2023
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
			    $(".closureList").each(function() {
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
	
	$(document).ready(function(){			
		var  table= $('#employeeWithInvolvementsTblByGroup').DataTable( {		
			destroy: true,
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
	})

	$(document).ready(function(){			
		var  table= $('#employeeWithInvolvementsTbl').DataTable( {		
			destroy: true,
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
			
			/*$('#employeeWithInvolvementsTbl .filters th[class="comboBox"]').each( function ( i ) {
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
		    $('#employeeWithInvolvementsTbl .filters th.comboBox').each(function(i) {
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
	       $('#employeeWithInvolvementsTbl .filters th[class="textBox"]').each( function () {                 
	           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

	       } );

	      	       
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
			
			$('#dash-employee-modal').on('shown.bs.modal', function () {
			        $('#employeeWithInvolvementsTbl .filters th.comboBox select').each(function() {
			            if (!$(this).hasClass('select2-hidden-accessible')) {
			                $(this).select2({
			                    placeholder: "Select options",
			                    allowClear: true
			                });
			            }
			        });
			        
			        $('#employeeWithInvolvementsTbl div[id^="s2id_autogen"]').on('click', function() {
				        closeInactiveSelect2(); // Close all inactive Select2 dropdowns
					});
			 });
			//End of event binders

	});	
	
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
				
					tableData +='<tr><td class="text-right">'+(i+1)+'  </td><td>'+row.groupName+'</td><td><p style="color: #1a7dc4;" class="font_14">'+row.employeeId +'</p></td>';
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
			
				tableData +='<tr><td class="text-right">'+(i+1)+'  </td><td>'+row.groupName+'</td><td><p class="font_14">'+row.employeeId +'</p></td>';
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
	
	function filterTable(empType){
		$('#employeeWithInvolvementsTbl').get(0).scrollIntoView()
							var  table= $('#employeeWithInvolvementsTbl').DataTable( {		
			destroy: true,
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
			
			
		        var colIdx=5;
		           
		           var val = empType;
		           
		                 table.column(colIdx)
		                        .search( val ? '^'+val+'$' : '', true, false)
		                        .draw();
		                
		     
		            

				


		
		
}		
			
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
	
	function allDetailsTable(){
		var startDate = null;
		var endDate = null;
		var selectedDateRange = $('#asOnDateDetails').text().trim();

		$('#asOnDateDetails1').text(selectedDateRange);
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
		$('#allDetailsTable').DataTable().clear().destroy();
			
		 var tableData = '';
		 var footerData='';
		 var data='';
		 var groupName='';
		 var submittedProposalCount='';
		 var projectReceviedCount='';
		 var closedProposalCout='';
		 var  joinEmpCout='';
		 var employeeCount='';
		 var resignEmpCount='';
		 var income='';
		 var totalProjects='';
		
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getAllDetails",
				data:{
					"startDate":startDate,
					"endDate":endDate
				},
				success : function(response) {
					/*$('.datatable_tfoot').hide();*/
					var curr = "Lakhs";
					var Lakhs = curr.fontcolor("#a4a4a4");
					var count =0;
					$.each( response, function(index,value){
					
		        		 data=value;
		        		 groupName=index;
		        			 
		        		// alert(data.submittedProposalCount);
		        		 if(data.submittedProposalCount!=null){
		        			 submittedProposalCount=data.submittedProposalCount;
		        		 }
		        		 else{
		        			 submittedProposalCount='';
		        		 }
		        		 
		        		 if(data.resignEmpCount!=null){
		        			 resignEmpCount=data.resignEmpCount;
		        		 }
		        		 else{
		        			 resignEmpCount='';
		        		 }
		        		 if(data.joinEmpCout!=null){
		        			 joinEmpCout=data.joinEmpCout;
		        		 }
		        		 else{
		        			 joinEmpCout='';
		        		 }
		        		 if(data.closedProposalCout!=null){
		        			 closedProposalCout=data.closedProposalCout;
		        		 }
		        		 else{
		        			 closedProposalCout='';
		        		 }
		        		 if(data.projectReceviedCount!=null){
		        			 projectReceviedCount=data.projectReceviedCount;
		        		 }
		        		 else{
		        			 projectReceviedCount='';
		        		 }
		        		 if(data.income!=null){
		        			 income=data.income  + " Lakhs";
		        			 income1 =data.income;
		        		 }
		        		 else{
		        			 income='';
		        		 }
		        		 
		        		 if(data.employeeCount!=null){
		        			 employeeCount=data.employeeCount;
		        		 }
		        		 else{
		        			 employeeCount='';
		        		 }
		        		 if(data.totalProjects!=null){
		        			 totalProjects=data.totalProjects;
		        		 }
		        		 else{
		        			 totalProjects='';
		        		 }
		        		 
		        		 if(groupName=='Total'){
		        			 footerData += '<tr class="no-sort sorting_disabled white">';	
		        			 footerData += ' <td></td> ';
		        			 footerData += ' <td class="bold">'+ groupName +  '</td> ';
							 if(data.submittedProposalCount!=null){
								 footerData += ' <td class="bold text-right">'+ submittedProposalCount +  '<br><span class="white bold  ">'+data.strTotalCost+' Lakhs</span></td> ';
							 }
							 else{
								 footerData += ' <td class="bold text-right">'+ submittedProposalCount +  '</td> ';
							 }
							 if(data.projectReceviedCount!=null){
								 footerData += ' <td class=" bold text-right">'+ projectReceviedCount +  '<br/><span class="white  bold ">'+data.strProjectTotalCost+' Lakhs</span></td> ';
							 }
							 else{
								 footerData += ' <td class="bold text-right">'+ projectReceviedCount +  '</td> ';
							 }
							 footerData += ' <td class="bold text-right ">'+ closedProposalCout +  '</td> ';
							 footerData += ' <td class="bold text-right">'+ income1 +  ' Lakhs</td>';
							 footerData += ' <td class="bold text-right">'+joinEmpCout +  '</td> ';
							 footerData += ' <td class="bold text-right">'+ resignEmpCount +  '</td> ';
							 footerData += ' <td class="bold text-right">'+ employeeCount +  '</td> ';
							if(data.totalProjects!=null){
								footerData += ' <td class="bold text-right">'+ totalProjects +  '<br/><span class="bold ">'+data.totalOutlayOfProject+' Lakhs</span></td> ';
							}
							else{
								footerData += ' <td class="white bold text-right">'+ totalProjects +  '</td> ';	
							}
							footerData += ' </tr> ';
		        		 }
		        		 else{
		        			 count += 1;
		        			 tableData += '<tr>';	
		        			 tableData += ' <td >'+count+'</td> ';
								tableData += ' <td class="font_blue">'+ groupName +  '</td> ';
								 if(data.submittedProposalCount!=null){
								tableData += ' <td class="text-right"><a title="Click here to view details" class="black bold" data-toggle="modal" data-target="#dash-newProposalListByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+'" >'+ submittedProposalCount + ' </a><br/><span class="dark-grey-font bold">'+data.strTotalCost+' Lakhs</span></td> ';
								 }
								 else{
									 tableData += ' <td class=" text-right"><a title="Click here to view details" class="black  bold"  data-toggle="modal" data-target="#dash-newProposalListByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ submittedProposalCount +  '</a></td> ';
								 }
								 if(data.projectReceviedCount!=null){
								tableData += ' <td class="text-right"><a title="Click here to view details" class="black bold"  data-toggle="modal" data-target="#dash-newProjectListByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ projectReceviedCount +  '</a><br/><span class="dark-grey-font bold">'+data.strProjectTotalCost+' Lakhs</span></td> ';
								 }
								 else{
									 tableData += ' <td class=" text-right"><a title="Click here to view details" class="black bold" data-toggle="modal" data-target="#dash-newProjectListByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ projectReceviedCount +  '</a></td> ';
								 }
								tableData += ' <td class=" text-right "><a title="Click here to view details" class="black  bold" data-toggle="modal" data-target="#dash-closedProjectListByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ closedProposalCout +  '</a></td> ';
								tableData += ' <td class=" text-right"><a title="Click here to view details" class="dark-grey-font  bold" data-toggle="modal" data-target="#dash-incomeByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ income +  ' </a></td> ';
								tableData += ' <td class=" text-right"><a title="Click here to view details" class="black  bold" data-toggle="modal" data-target="#dash-new-joinee-ByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+joinEmpCout +  '</a></td> ';
								tableData += ' <td class=" text-right"><a title="Click here to view details" class="black  bold" data-toggle="modal" data-target="#dash-resigned-empByGroup-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ resignEmpCount +  '</a></td> ';
								tableData += ' <td class=" text-right"><a title="Click here to view details" class="black  bold"  data-toggle="modal" data-target="#dash-employee-group-modal" data-whatever="'+data.encGroupId+','+data.groupName+' ">'+ employeeCount +  '</a></td> ';
								
								if(data.totalProjects!=null){
									tableData += ' <td class=" text-right"><a title="Click here to view details" class="black  bold" onclick=loadGroupWiseProjects("'+data.encGroupId+'")>'+ totalProjects +  '</a><br/><span class=" dark-grey-font bold ">'+data.totalOutlayOfProject+' Lakhs</span></td> ';
								}
								else{
									tableData += ' <td class=" text-right"><a title="Click here to view details" class="black  bold" onclick=loadGroupWiseProjects("'+data.encGroupId+'")>'+ totalProjects +  '</a></td> ';
								}
								tableData += ' </tr> ';
		        		 }
		        			
					})				

					
					$('#allDetailsTable').append(tableData);	
					 var foot = $("#allDetailsTable").find('tfoot');
					 if(foot.length){
						 $("#allDetailsTable tfoot").remove();
					 }
					 if(footerData){
					    foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#allDetailsTable"); 
					    foot.append($(footerData));
					 }
					var  table= $('#allDetailsTable').DataTable( {
						destroy: true,
						 "info": false,
				        "paging":   false,
				        "columnDefs": [ {
				            "searchable": false,
				            "orderable": false,
				            "targets": 0
				        } ],
				        "order": [[ 1, 'asc' ]]
				    } );
				 
					table.on( 'order.dt search.dt', function () {
						table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				            cell.innerHTML = i+1;
				        } );
				    } ).draw();
				      
					
				       
				  
						
						/*table.on('order.dt search.dt', function () {
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
		*/		}
			});
		}
		}
		
	$( function() {
		  var dateFormat = "dd/mm/yy",
		    from = $( "#fromDet" )
		      .datepicker({
		    	dateFormat: 'dd/mm/yy', 
		  	    changeMonth: true,
		  	    changeYear:true,
		  	     autoclose: true,
		  	    maxDate: '0',
		  	  /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
		  	    minDate : '-10Y'
		      })
		      .on( "change", function() {
		        to.datepicker( "option", "minDate", getDate( this ) );
		      }),
		      
   			
		    to = $( "#toDet" ).datepicker({
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

		  $('.go-btn-det').click(function() {
			  var startRange = $("#fromDet").val();
			  var endRange = $("#toDet").val();
			  $("#fromDetailNew").val(startRange);
			  $("#toDetailNew").val(endRange);
			  $("#fromResNew").val('');
			  $("#toResNew").val('');
			  $("#asOnDateRes1").text('');
			  
			  $("#toJoinNew").val('');
			  $("#fromJoinNew").val('');
			  $("#asOnDateJoin1").text('');
			  
			  $("#fromGroup").val('');
			  $("#toGroup").val('');
			  $("#asOnDateClosedProj1").text('');
			  
			  $("#fromNew").val('');
			  $("#toNew").val('');
			  $("#asOnDateProj1").text('');
			  
			  $("#fromProposalNew").val('');
			  $("#toProposalNew").val('');
			  $("#asOnDateProp1").text('');
			  
			  $("#incomefromNew").val('');
			  $("#incometoNew").val('');
			  $("#asOnDateNew1").text('');
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			  $('#asOnDateDetails').text(selectedRange);
			  
			  allDetailsTable();
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
	
	function proposalSubmittedNew(input,requestFrom){
	/*$("#fromProposalNew").val('');
	$("#toProposalNew").val('');*/
	var data=input.split(",");
	var encGroupId=data[0];
	var groupName= data[1];
	var startDate = null;
	var endDate = null;
	
	$("#propSubGroupName").text(groupName);
	var toProposalNew=$("#toProposalNew").val();
	var fromProposalNew=$("#fromProposalNew").val();
	var fromDet=$("#fromDetailNew").val();
	var toDet=$("#toDet").val();
	var hiddenFromVal=$("#fromDetNew").val();
	hiddenFromVal=fromDet;
	if(requestFrom == 0){
		
		$("#fromProposalNew").val('');
		$("#toProposalNew").val('');
	}
	if($("#fromProposalNew").val()=='' && hiddenFromVal!=''){
		
	
		startDate=fromDet;
		endDate=toDet;
		
	}
	else{
		
		startDate=fromProposalNew;
		endDate=toProposalNew;
		
}
	$("#fromProposalNew").val(startDate);
	$("#toProposalNew").val(endDate);
	var finalDateRange=startDate +' to '+ endDate;
	
	$('#asOnDateProp1').text(finalDateRange);
	
		if(startDate){	
			$('#newProposalsByGroup_dataTable').DataTable().clear().destroy();
		 var tableData = '';
		
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getActiveProposalsByGroup",
				data:{
					"startDate":startDate,
					"endDate":endDate,
					"encGroupId":encGroupId
				},
				success : function(response) {
				
					
					var totalAmount = 0;
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
									
						tableData += '<tr> <td>'+(i+1)+'</td>';
						
						tableData += ' <td style="color: #1a7dc4;" class="font_14">'+ row.proposalTitle +  '<p class="font_14 orange"><i>'+row.strClientName+'</i></p></td> ';
						/*tableData += ' <td class="font_14 ">'+ row.strClientName +  '</td> ';*/
						tableData += ' <td class="font_14 text-right">'+ row.dateOfSubmission +  '</td> ';
						tableData += ' <td class="font_14 text-right">'+ row.duration +  ' months </td> ';
						
						
						if(row.receivedProjectStatus=='Yes'){
							
							tableData += '<td class="font_14"> <a title="Click to View Project Details" onclick=openProjectDetails("'+row.encApplicationId+'")>'
						+ row.receivedProjectStatus + ' </a></td>';
					}
					else{
						tableData += ' <td class="font_14">'+ row.receivedProjectStatus +  '</td> ';
					}
					
					tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encApplicationId+'" value='+row.encApplicationId+' /> <span id="projectCost_'+row.encApplicationId+'">'
					+ row.strProposalCost + '</span></td> </tr>';
		
					totalAmount += row.numProposalAmountLakhs;
				
					}

					if(totalAmount >0){
						tableData +='<tr> <td></td> <td>Total of All Group</td><td></td> <td></td><td class="bold text-right">Total</td> <td class="bold text-right">'+convertAmountToINR(totalAmount)+' Lakhs </td></tr>';
					}
					
					$('#newProposalsByGroup_dataTable').append(tableData);	        

					
					var  table= $('#newProposalsByGroup_dataTable').DataTable( {
						destroy: true,
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
					
								       

				},
				error : function(e) {
					
					alert('Error: ' + e);
				}
			});
				
		};
		}

			function closedProjectsNew(input,requestFrom){
			var startDate = null;
			var endDate = null;
			var data=input.split(",");
			var encGroupId=data[0];
			var groupName= data[1];
			$("#projClosedGroupName").text(groupName);
			var toGroup=$("#toGroup").val();
			var fromGroup=$("#fromGroup").val();
			var fromDet=$("#fromDetailNew").val();
			var toDet=$("#toDet").val();
			var hiddenFromVal=$("#fromDetNew").val();
			hiddenFromVal=fromDet;
			if(requestFrom == 0){
				
				$("#fromGroup").val('');
				$("#toGroup").val('');
			}
			
			if($("#fromGroup").val()=='' && hiddenFromVal!=''){
			
				startDate=fromDet;
				endDate=toDet;
			
			}else{
				startDate=fromGroup;
				endDate=toGroup;
			}
			
			$("#fromGroup").val(startDate);
			$("#toGroup").val(endDate);
			var finalDateRange=startDate +' to '+ endDate;
			
			$('#asOnDateClosedProj1').text(finalDateRange);
			if(startDate){		
			
			 $('#closedProjectsByGroup_dataTable').DataTable().clear().destroy();
			 var tableData = '';
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/mst/getAllClosedProjectByGroup",
					data:{
						"startDate":startDate,
						"endDate":endDate,
						"encGroupId":encGroupId
					},
					success : function(response) {				
				/*		if(response){
						$('#closedProjectsOnDate').text(response.length); 
					}else{
						$('#closedProjectsOnDate').text(0); 
					}*/
						var totalAmount = 0;
						for (var i = 0; i < response.length; i++) {
							var row = response[i];	
							var strReferenceNumber=row.strReferenceNumber;
							if(!strReferenceNumber){
								strReferenceNumber='';
							}
							var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
									+ row.encProjectId + "')";
						
							tableData += '<tr><td>'+(i+1)+'</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
									+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.strClientName+'</i></p><p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p> </td>';
							
					
							tableData += ' <td class="font_14 text-right"> <span  class="" >'
							+ row.startDate
							+  '</span></td> ';
							
							
							tableData += ' <td class="font_14 text-right"> <span  class="" >'
								+ row.projectClosureDate
								+  '</span></td>';
							tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encProjectId+'" value='+row.encProjectId+'/> <span  class="" id="projectCost_'+row.encProjectId+'">'
							+ row.strProjectCost
							+  '</span></td> </tr>';
						
							
							totalAmount += row.numProjectAmountLakhs;
						}

						if(totalAmount >0){
							tableData +='<tr><td> </td> <td> Total of All Group</td>  <td> </td> <td class="bold text-right">Total</td> <td class="bold text-right">'+convertAmountToINR(totalAmount)+' Lakhs </td></tr>';
						}
						
						$('#closedProjectsByGroup_dataTable').append(tableData);
				        
					
						
						var  table= $('#closedProjectsByGroup_dataTable').DataTable( {
							destroy: true,
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
					

			               // DataTable
			               var table = $('#closedProjectsByGroup_dataTable').DataTable();
					       
					     
					}

				});
			}

			}
		
	
		function projectReceivedNew(input,requestFrom){
		var startDate = null;
		var endDate = null;
		var data=input.split(",");
		var encGroupId=data[0];
		var groupName= data[1];
		$("#projRecGroupName").text(groupName);
		var toNew=$("#toNew").val();
		var fromNew=$("#fromNew").val();
		var fromDet=$("#fromDetailNew").val();
		var toDet=$("#toDet").val();
		var hiddenFromVal=$("#fromDetNew").val();
		if(requestFrom == 0){
		
			$("#fromNew").val('');
			$("#toNew").val('');
		}
	
		hiddenFromVal=fromDet;
	
		if($("#fromNew").val()=='' && hiddenFromVal!=''){
		
			startDate=fromDet;
			endDate=toDet;
			
		}else{
			
			startDate=fromNew;
			endDate=toNew;
		
		}
		
		$("#fromNew").val(startDate);
		$("#toNew").val(endDate);
		var finalDateRange=startDate +' to '+ endDate;
		
		
		$('#asOnDateProj1').text(finalDateRange);
		if(startDate){	
			$('#newProjectsByGroups_dataTable').DataTable().clear().destroy();
		 var tableData = '';
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getNewProjectsByGroupDetail",
				data:{
					"startDate":startDate,
					"endDate":endDate,
					"encGroupId":encGroupId
				},
				success : function(response) {
					//console.log(response);
					/*if(response){
						$('#newProjectsOnDate').text(response.length); 
					}else{
						$('#newProjectsOnDate').text(0); 
					}*/
					
					var totalAmount = 0;
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						//$('#incomeSelect select').val(row.strGrouptName).trigger('change');
						var mouDate=row.strMouDate;
						var workorderDate= row.strWorkOrderDate
						if(row.strMouDate==null){
							mouDate='';
						}
						if(row.strWorkOrderDate==null){
							workorderDate='';
						}
						var strReferenceNumber=row.strReferenceNumber;
						if(!strReferenceNumber){
							strReferenceNumber='';
						}
						
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId + "')";
					
						tableData += '<tr> <td>'+(i+1)+'</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
								+ row.strProjectName + ' </a><p class="font_14 orange"><i>'+row.strClientName+'</i></p><p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p> </td>';
						
						/*tableData += ' <td class="bold blue font_14 text-right">'+strReferenceNumber+'</td><td class="font_14"> <input type="hidden" id="hiddenClientName_'+row.encProjectId+'" value='+row.encProjectId+' /> <span  id="clientName_'+row.encProjectId+'">'
						+ row.strClientName
						+  '</span></td> ';*/
						
						tableData += ' <td class="font_14 text-right">'+ mouDate +  '</td> ';
						tableData += ' <td class="font_14 text-right">'+ workorderDate +  '</td> ';
						tableData += ' <td class="font_14 text-right">'+ row.startDate +  '</td> ';
						
						
						
						tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encProjectId+'" value='+row.encProjectId+' /> <span id="projectCost_'+row.encProjectId+'">'
						+ row.strProjectCost + '</span></td> </tr>';
			
						totalAmount += row.numProjectAmountLakhs;
						
						
					}

					if(totalAmount >0){
						tableData +='<tr> <td></td> <td>Total of All Group</td>  <td></td><td></td><td class="bold text-right">Total</td> <td class="bold text-right">'+convertAmountToINR(totalAmount)+' Lakhs </td></tr>';
					}
					
					$('#newProjectsByGroups_dataTable').append(tableData);	        

					
					var  table= $('#newProjectsByGroups_dataTable').DataTable( {
						destroy: true,
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
					
								       

				},
				error : function(e) {
					// $('#RecurringManpowerAmt').val(0);
					alert('Error: ' + e);
				}
			});
				
		}
		};
		
		
	
		function joineeEmployeeList(input,requestFrom){
			
			var startDate = null;
			var endDate = null;
			var data=input.split(",");
			var encGroupId=data[0];
			var groupName= data[1];
			$("#joineeGroupName").text(groupName);
			var toJoinNew=$("#toJoinNew").val();
			var fromJoinNew=$("#fromJoinNew").val();
			var fromDet=$("#fromDetailNew").val();
			var toDet=$("#toDet").val();
			var hiddenFromVal=$("#fromDetNew").val();
			hiddenFromVal=fromDet;
			if(requestFrom == 0){
				
				$("#fromJoinNew").val('');
				$("#toJoinNew").val('');
			}
			if($("#fromJoinNew").val()==''&&  hiddenFromVal!='' ){
				
				startDate=fromDet;
				endDate=toDet;
				
			}else{
				
				startDate=fromJoinNew;
				endDate=toJoinNew;
					}
			$("#fromJoinNew").val(startDate);
			$("#toJoinNew").val(endDate);
			var finalDateRange=startDate +' to '+ endDate;
	
			$('#asOnDateJoin1').text(finalDateRange);
			 
			if(startDate){	
				$('#joineeBygroup_dataTable').DataTable().clear().destroy();
			 var tableData = '';
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/mst/getNewJoinedEmployeeByGroupDetails",
					data:{
						"startDate":startDate,
						"endDate":endDate,
						"encGroupId":encGroupId
					},
					success : function(response) {
						
						
						var totalAmount = 0;
						for (var i = 0; i < response.length; i++) {
							var row = response[i];	
							if(row.employmentStatus =='Working'){
							tableData += '<tr> <td>'+(i+1)+'</td>';	
							/*tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';*/
							tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
							tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
							
							tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
							tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
							/*tableData += ' <td class="font_14 >'+ row.employmentStatus +  '</td></tr> ';*/
						}
							else{
								tableData += '<tr class="red"> <td>'+(i+1)+'</td>';	
								/*tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';*/
								tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
								tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
								
								tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';			
								tableData += ' <td class="font_14 text-right">'+ row.dateOfJoining +  '</td><td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
							}}

						$('#joineeBygroup_dataTable').append(tableData);	        
						var  table= $('#joineeBygroup_dataTable').DataTable( {
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
					      
					    } );
							
                     
					}
				});
			};
			}
		
		
		
		function resignEmployeelist(input,requestFrom){
			var data=input.split(",");
			var startDate = null;
			var endDate = null;
			var encGroupId=data[0];
			var groupName= data[1];
			$("#resignGroupName").text(groupName);
			var toResNew=$("#toResNew").val();
			var fromResNew=$("#fromResNew").val();
			var fromDet=$("#fromDetailNew").val();
			var toDet=$("#toDet").val();
			var hiddenFromVal=$("#fromDetNew").val();
			hiddenFromVal=fromDet;
			if(requestFrom == 0){
				
				$("#fromResNew").val('');
				$("#toResNew").val('');
			}
			if($("#fromResNew").val()=='' && hiddenFromVal!=''){
				
				startDate=fromDet;
				endDate=toDet;
				
			}else{
				startDate=fromResNew;
				endDate=toResNew;
				}
			$("#fromResNew").val(startDate);
			$("#toResNew").val(endDate);
			
			var finalDateRange=startDate +' to '+ endDate;
			
			$('#asOnDateRes1').text(finalDateRange);
			if(startDate){	
				$('#resignedByGroup_dataTable').DataTable().clear().destroy();
			 var tableData = '';
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/mst/getResignedEmployeeByGroupDetails",
					data:{
						"startDate":startDate,
						"endDate":endDate,
						"encGroupId":encGroupId
					},
					success : function(response) {
						//console.log(response);
						/*if(response){
							$('#newResignEmp').text(response.length); 
						}else{
							$('#newResignEmp').text(0); 
						}*/
						
						var totalAmount = 0;
						for (var i = 0; i < response.length; i++) {
							var row = response[i];	
				
							tableData += '<tr> <td>'+(i+1)+'</td>';	
							/*tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';*/
							tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
							tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
							
							tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td>';			
							tableData += ' <td class="font_14 text-right">'+ row.dateOfResignation +  '</td> <td class="font_14 ">'+ row.employmentStatus +  '</td></tr>';
						
						}

						$('#resignedByGroup_dataTable').append(tableData);	        
						var  table= $('#resignedByGroup_dataTable').DataTable( {
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
					      
					    } );
							
                     
					}
				});
			};
			}
		
		
		
		function incomeTableNew(input,requestFrom){
			
			var startDate = null;
			var endDate = null;
			var data=input.split(",");
			var encGroupId=data[0];
			var groupName= data[1];
			$("#incomeGroupName").text(groupName);
			var incometoNew=$("#incometoNew").val();
			var incomefromNew=$("#incomefromNew").val();
			var fromDet=$("#fromDetailNew").val();
			var toDet=$("#toDet").val();
			var hiddenFromVal=$("#fromDetNew").val();
			hiddenFromVal=fromDet;
			if(requestFrom == 0){
				
				$("#incomefromNew").val('');
				$("#incomefromNew").val('');
			}
			if($("#incomefromNew").val()=='' && hiddenFromVal!=''){
				
				startDate=fromDet;
				endDate=toDet;
				
			}else{
				startDate=incomefromNew;
				endDate=incometoNew;
				}
			$("#incomefromNew").val(startDate);
			$("#incometoNew").val(endDate);
			
			var finalDateRange=startDate +' to '+ endDate;
			
			$('#asOnDateNew1').text(finalDateRange);

		var incomeTableId = $('#incomeByGroup_dataTable').attr('id');
		table = $('#incomeByGroup_dataTable').DataTable().clear().destroy();
		var tableData = '';
		$.ajaxRequest
				.ajax({

					type : "POST",
					url : "/PMS/mst/getPaymentReceivedDetails",
					data:{
						"startDate":startDate,
						"endDate":endDate,
						"encGroupId":encGroupId
					},
					success : function(response) {
						// console.log(response);
						for (var i = 0; i < response.length; i++) {
							var row = response[i];
							// console.log(row);
							var strReferenceNumber=row.strReferenceNumber;
							if(!strReferenceNumber){
								strReferenceNumber='';
							}
							var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
									+ row.encProjectId + "')";
							tableData += '<tr >';
							tableData += '<td > <a class="font_14" title="Click to View Complete Information" onclick='
									+ clickFunction
									+ '>'
									+ row.projectName
									+ ' </a><p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p></td>';
							tableData += '<td class="font_14 text-right"> <input type="hidden" id="hiddenIncomeAmt_'
									+ row.encProjectId
									+ '" value='
									+ row.encProjectId
									+ '/> <span  class="convert_lakhs_td" id="incomeAmt_'
									+ row.encProjectId
									+ '">'
									+ row.strReceivedAmount + '</span></td> </tr>';
						}

						$('#incomeByGroup_dataTable > tbody').html('');
						$('#incomeByGroup_dataTable').append(tableData);
						// convertToLakhsForComponent(incomeTableId);
						table = $('#incomeByGroup_dataTable').DataTable();




					}

				});
		}

		$('#dash-grpwise-bargraphNew-modal').on('shown.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var input = button.data('whatever');

			
			$('#ongoingProjectList').DataTable().clear().destroy();
			 var tableData = '';
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/getOngoinProjectlist",
					data:{
						
						"encGroupId":input
					},
					success : function(response) {
			
						
						var totalAmount = 0;
						for (var i = 0; i < response.length; i++) {
							var row = response[i];	
							
								tableData += '<tr class=""> <td>'+(i+1)+'</td>';	
								tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';
								tableData += ' <td class="font_14 ">'+ row.projectCount +  '</td> ';
								tableData += ' <td class="font_14 ">'+ row.projectCost +  '</td> ';
								
								/*tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';	*/		
								tableData += ' </tr>';
							}

						$('#ongoingProjectList').append(tableData);	        
						var  table= $('#ongoingProjectList').DataTable( {
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
					      
					    } );
							
                     
					}
				});
			
			});
		
		
		$('#dash-employee-group-modal').on('shown.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var input = button.data('whatever');
			var data= input.split(",");
			var encGroupId=data[0];
			var groupName=data[1];
			$("#empGroupName").text(groupName);
			$('#employeeWithInvolvementsTblNew').DataTable().clear().destroy();
			 var tableData = '';
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/getEmployeelist",
					data:{
						
						"encGroupId":encGroupId
					},
					success : function(response) {
			
						
						var totalAmount = 0;
						for (var i = 0; i < response.length; i++) {
							var row = response[i];	
							//alert(row.groupName);
								tableData += '<tr class=""> <td>'+(i+1)+'</td>';	
								/*tableData += ' <td class="font_14 ">'+ row.groupName +  '</td> ';*/
								tableData += ' <td class="font_14 ">'+ row.employeeId +  '</td> ';
								tableData += ' <td class="font_14 ">'+ row.employeeName +  '</td> ';
								tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';
								tableData += ' <td class="font_14 ">'+ row.employeeTypeName +  '</td> ';
								/*tableData += ' <td class="font_14 ">'+ row.strDesignation +  '</td> ';	*/		
								tableData += ' </tr>';
							}

						$('#employeeWithInvolvementsTblNew').append(tableData);	        
						var  table= $('#employeeWithInvolvementsTblNew').DataTable( {
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
			//console.log(input);
			
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
					tableData+='<td class="hidden ">'+row.primaryProject+'</td>';

					tableData+='</tr>';
										

				}
				
					tableData+='</tbody>';
					$('#datatable1 tbody').empty();						
					$('#datatable1').append(tableData);	
					
				},
				error : function(e) {
					alert('Error: ' + e);			
				}
			});				
		});
		
		/*$('#Employee-UnderUti-Details-modal').on('shown.bs.modal', function(event) {*//*
			
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
					
						tableData +='<tr><td class="text-right">'+(i+1)+'  </td><td>'+row.groupName+'</td><td><p style="color: #1a7dc4;" class="font_14">'+row.employeeId +'</p></td>';
						tableData +='<td class="">'+row.employeeName+'</td>';
						tableData +='<td>'+row.strDesignation+'</td>';
						tableData +='<td>'+row.mobileNumber+'</td>';
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
				        "order": [[ 1, 'asc' ]]
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
		               var table = $('underUtiEmployeeDetails').DataTable();
				       
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
		*//*});*/
		
		/*	------*/
		function calculateyearDates(setFirstDay,setLastDay,fromId,toId,btnIdOrClass,type){
			$("#"+fromId).val(setFirstDay);
			$("#"+toId).val(setLastDay);
			if(type==1)
				$( "#"+btnIdOrClass ).trigger( "click" );	
				else	
				$( "."+btnIdOrClass ).trigger( "click" );
			
		}
		function calculateDates(selValue,fromId,toId,btnIdOrClass,type){
			  $("#incomecalendarYearDropdown").val(0) ;
			  $("#projectcalendarYearDropdown").val(0) ;
			  $("#closedcalendarYearDropdown").val(0) ;
			  $("#calendarYearDropdown").val(0) ;
			  
			  
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
		
		
		
		var lastday = function(y,m){
			return  new Date(y, m, 0).getDate();
			}	
		
		
		$('.go-btn2').click(function() {
			  var startRange = $("#incomefrom").val();
			  var endRange = $("#incometo").val();
			 // console.log("go-btn2 startRange"+startRange)
			 // console.log("go-btn2 endRange"+endRange)
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			 
			  $('#asOnDate').text(selectedRange);
			  //alert('Called');
			  incomeTable();
			  drawIncomeChart();
		  });
		

		$(document).ready(function() {
			
			var scrollPos = 0;
			$('#clientDetails').on('show.bs.modal', function(event) {
				//console.log("inside client modal");
				var button = $(event.relatedTarget);
				var input = button.data('whatever');
				//console.log("data"+input);
				if (typeof input !== 'undefined')
				{
				var inputs = input.split(';');
				var clientId= inputs[0];
				var projectId= inputs[1];
				var endUserId= inputs[2];
				loadClientData(clientId,projectId,endUserId);
				}
				  $('body').css({
			          overflow: 'hidden',
			          position: 'fixed',
			          top : -scrollPos
			      });
			})
			 .on('hide.bs.modal', function (){
			        $('body').css({
			            overflow: '',
			            position: '',
			            top: ''
			        }).scrollTop(scrollPos);
			    });
			
		});




		
		$( function() {
			  var dateFormat = "dd/mm/yy",
			    from = $( "#fromProposal" )
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
			    to = $( "#toProposal" ).datepicker({
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
/*
			  $('#goProposal').click(function() {
				  var startRange = $("#fromProposal").val();
				  var endRange = $("#toProposal").val();
				  if(!startRange){
					  sweetSuccess('Attention','Please select Start Range');
					  return false;
				  }
				  var selectedRange = startRange;
				  if(endRange){
					  selectedRange = selectedRange +' to '+endRange;
				  }
				  $('#asOnDateProposals').text(selectedRange);
				  
				  //alert('Called');
				  newProposalsTable();
			  });
*/
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

		//Added code for jquery ui daterangepicker

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
				  newJoineeEmpTable();
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
		  from = $( "#from" )
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
		  to = $( "#to" ).datepicker({
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
		    from = $( "#from" )
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
		    to = $( "#to" ).datepicker({
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

		  $('.go-btn').click(function() {
			  var startRange = $("#from").val();
			  var endRange = $("#to").val();
			  if(!startRange){
				  sweetSuccess('Attention','Please select Start Range');
				  return false;
			  }
			  var selectedRange = startRange;
			  if(endRange){
				  selectedRange = selectedRange +' to '+endRange;
			  }
			  $('#asOnDateProjects').text(selectedRange);
			  
			  //alert('Called');
			  newProjectsTable();
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
			        $('.closesprojects').val(0); // 12-10-2023
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
			      $('.closesprojects').val(0); // 12-10-2023
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

		function getEncProjectId(encApplicationId){
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getProjectIdByApplicationId",
				data : {"encApplicationId":encApplicationId,
				async:false,		
				},
				success : function(response) {
				if(response){
					window.location.href= "/PMS/projectDetails/"+response;
				}
				else{
					sweetSuccess('Attention!','No project found');
					return false;
				}
				

			}
		});
		}

		function openProjectDetails(encApplicationId){
			getEncProjectId(encApplicationId);
			
			
		}




		function loadOngoingList(){

			 window.scrollBy(0,1450); 
			 
		}	
		
			/*function initialzeIncomeChart() {
				incomeTable();
			}*/
				
			function initializeNewProposals() {
				newProposalsTable();
				projectsReceivedTable();  // call the project received function [08-02-2024]
			}

				function initializeNewProjects() {
					newProjectsTable();
				}

				function initializeClosedProjects() {		
					closedProjectsTable();
				}
				function initializePendingPayments() {
					pendingPaymentsTable();
				}
				
				
				/*$("#fromDt").MonthPicker( {
					
				    Button: "<i class='pad-left-half font_eighteen fa fa-calendar front'></i>",
				    changeMonth: true,
				    changeYear: true,
				    dateFormat: 'mm/yy',
				    MaxMonth: 0,
				    Position: {
				        collision: 'fit flip'
				    },
				    
				    OnAfterChooseMonth: function(){
				    $("#divCat").removeClass('hidden');    
				       var date = $(this).val();
				     
				        var res = date.split("/");
				        var month = res[0];
				        var year = res[1];
				      alert(month);
				      alert(year);
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


				            //var encProjectId = $('#encProjectId').val();
				            //monthlyProgressReport(encProjectId,month,year);
				$("#month").val(month);
				$("#year").val(year);
									           
				        }
				    },
				    beforeShow: function() {
				        setTimeout(function(){
				            $('.month-picker').css('z-index', 99999999999999);
				        }, 0);
				    }
				    });*/
				
				function callGo(){					
						  var startRange = $("#fromDt").val();
							var reportType= $("#reportType").val();
							  //alert(startRange);
						 if(!startRange){
								  sweetSuccess('Attention','Please select Month for Report');
								  return false;
							  }
							  
							 
						 else if(reportType==0){
								 sweetSuccess('Attention','Please select Report Type');
								  return false;
							 }

							  monthlyPR();
						/*});*/
				}
				function openIncomeAnotherFilter(){           
					$("#incomeAnotherFilter").toggle();
					$("#incomCustom").toggle();
				 }
				$(document).ready(function() {		
					/*$("#incomeAnotherFilter").hide();*/
				});			
			
				function monthlyPR(){

					var reportType= $("#reportType").val();
					var startRange = $("#fromDt").val();
					var month=$("#month").val();
					var year=$("#year").val();
					
					var monthNames = [ "January", "February", "March", "April", "May", "June",
					                   "July", "August", "September", "October", "November", "December" ];
					if(reportType==3){
					
						$.ajaxRequest.ajax({
							type : "POST",
							url : "/PMS/activeProgressReportsDetails",
							async:false,
							data : {"actionId": 6,				
								"month":month,
								"year":year
								
							},
							success : function(data) {
								//console.log(data);	
								var tableData = '';
								if(data.length>0){
								for(var i=0;i<data.length;i++){
									
									var row = data[i];
														
									tableData +='<tr><td class="text-right">'+(i+1)+'</td><td>'+row.strGroupName+'</td><td><a style="color: #1a7dc4;" class="font_14" onclick="viewProjectDetails(\'/PMS/projectDetails/' + row.encProjectId + '\')">'+row.strProjectName+'</a><p class="bold blue font_14 text-left">'+ row.strProjectReferenceNo+'<p></td>';
									tableData +=' <td><b>'+row.strActionName+' </b></br><span class="text-center blue font_12"> on <i>'+row.transactionDate+'</i></td><td class="fa fa-file-text blue text-center" onclick=previewOfReport("'+row.encNumId+'") title="Preview of Report"></td></tr>';
									/*tableData +='<td class="text-right">'+row.transactionDate+'</td></tr>';*/																					
								}
								//$('#projectProgressTblAll tbody').empty();
								$('#projectProgressTblAll').DataTable().clear().destroy();
								$('#projectProgressTblAll tbody').append(tableData);
								initializeProgressTable();
								}
								else{
									//$('#projectProgressTblAll tbody').empty();
									$('#projectProgressTblAll').DataTable().clear().destroy();
									/*sweetSuccess("Attention","No Record Exist for this month");*/
									initializeProgressTable();
									//  return false;
								}
								
							}
						});
						
					} else if(reportType==2){
					
						$.ajaxRequest.ajax({
							type : "POST",
							url : "/PMS/PendingProgressReportsAtGC",
							async:false,
							data : {"actionId": 6,			
								"month":month,
								"year":year
								
							},
							success : function(data) {
								//console.log(data);	
								var tableData = '';
								if(data.length>0){
								for(var i=0;i<data.length;i++){
									
									var row = data[i];
														
									tableData +='<tr><td class="text-right">'+(i+1)+'</td><td>'+row.strGroupName+'</td><td><a style="color: #1a7dc4;" class="font_14" onclick="viewProjectDetails(\'/PMS/projectDetails/' + row.encProjectId + '\')">'+row.strProjectName+'</a><p class="bold blue font_14 text-left">'+ row.strProjectReferenceNo+'<p></td>';
									/*tableData +='<td class="text-right">'+row.strProjectReferenceNo+'</td>';*/
									tableData +=' <td><b>'+row.strActionName+' </b></br><span class="text-center blue font_12"> on <i>'+row.transactionDate+'</i></td><td class="fa fa-file-text blue text-center" onclick=previewOfReport("'+row.encNumId+'") title="Preview of Report"></td></tr>';
									/*tableData +='<td class="text-right">'+row.transactionDate+'</td></tr>';*/
								}
								//$('#projectProgressTblAll tbody').empty();
								$('#projectProgressTblAll').DataTable().clear().destroy();
								$('#projectProgressTblAll tbody').append(tableData);
								initializeProgressTable();
								}
								else{
									//$('#projectProgressTblAll tbody').empty();
									$('#projectProgressTblAll').DataTable().clear().destroy();
									/*sweetSuccess("Attention","No Record Exist for this month");*/
									initializeProgressTable();
								}
							}
						});
					} else if(reportType==1){
						
						$.ajaxRequest.ajax({
							type : "POST",
							url : "/PMS/PendingProgressReportsAtPL",
							async:false,
							data : {"actionId": 6,
								/*"dtFromDate":startRange,*/
								"month":month,
								"year":year
								
							},
							success : function(data) {
								//console.log(data);	
								var tableData = '';
								var strProjectReferenceNo='';
								var monthNYear="";
								var action="";
								if(data.length>0){
								for(var i=0;i<data.length;i++){					
									var row = data[i];					
									if(row.strProjectReferenceNo!='NA'){
										strProjectReferenceNo=row.strProjectReferenceNo;
									}
									else{
										strProjectReferenceNo="";
									}
									if(row.month!=0 && row.year!=0){
										monthNYear=monthNames[row.month-1] +'/'+row.year;
									}
									else{
										monthNYear="";
									}
									if(row.strActionName!="Not yet Submitted"){

										action="<span class='bold'>"+row.strActionName +"</span> <br/> <span class='text-center blue font_12'> on <i>"+ row.transactionDate +"</i></span>";
									}
									else{
										
										action=row.strActionName
									}
									tableData +='<tr><td class="text-right">'+(i+1)+'</td><td>'+row.strGroupName+'</td><td><a style="color: #1a7dc4;" class="font_14" onclick="viewProjectDetails(\'/PMS/projectDetails/' + row.encProjectId + '\')">'+row.strProjectName+'</a><p class="bold blue font_14 text-left">'+ strProjectReferenceNo+'<p></td>';
									/*tableData +='<td class="text-right">'+strProjectReferenceNo+'</td>';*/
									tableData +='<td><b>'+action+'</b></td>';
									if(row.strActionName!="Not yet Submitted"){
									tableData +='<td id="preview" class=" fa fa-file-text-o blue" onclick=previewOfReport("'+row.encNumId+'") title="Preview of Report"></td></tr>';
								}			
									else{
										tableData +='<td></td></tr>';
									}}
								//$('#projectProgressTblAll tbody').empty();
								$('#projectProgressTblAll').DataTable().clear().destroy();
								$('#projectProgressTblAll tbody').append(tableData);
								initializeProgressTable();
								}
								else{
									//$('#projectProgressTblAll tbody').empty();
									$('#projectProgressTblAll').DataTable().clear().destroy();
									/*sweetSuccess("Attention","No Record Exist for this month");*/
									initializeProgressTable();
									//  return false;
								}
								

							}
						});
					}
					else if(reportType==4){
						
						$.ajaxRequest.ajax({
							type : "POST",
							url : "/PMS/PendingProgressReportsAtPI",
							async:false,
							data : {"actionId": 9,			
								"month":month,
								"year":year
								
							},
							success : function(data) {
							
								var tableData = '';
								if(data.length>0){
								for(var i=0;i<data.length;i++){
									
									var row = data[i];
														
									tableData +='<tr><td class="text-right">'+(i+1)+'</td><td>'+row.strGroupName+'</td><td><a style="color: #1a7dc4;" class="font_14" onclick="viewProjectDetails(\'/PMS/projectDetails/' + row.encProjectId + '\')">'+row.strProjectName+'</a><p class="bold blue font_14 text-left">'+ row.strProjectReferenceNo+'<p></td>';
									
									tableData +=' <td><b>'+row.strActionName+' </b></br><span class="text-center blue font_12"> on <i>'+row.transactionDate+'</i></td><td class="fa fa-file-text-o blue" onclick=previewOfReport("'+row.encNumId+'") title="Preview of Report"></td></tr>';
									
								}
							
								$('#projectProgressTblAll').DataTable().clear().destroy();
								$('#projectProgressTblAll tbody').append(tableData);
								initializeProgressTable();
								}
								else{
									
									$('#projectProgressTblAll').DataTable().clear().destroy();
								
									initializeProgressTable();
								}
							}
						});
					}
					
				}

				$(document).ready(function(){
					loadAllApprovedProgressReportforLastMonth();
					loadApprovedProgressReportforCurrentMonth();
				});
				function loadApprovedProgressReportforCurrentMonth(){
					$.ajaxRequest.ajax({
						type : "POST",
						url : "/PMS/activeProgressReportsbyGCforCurrentMonth",
						data : {"actionId": 6},
						success : function(data) {	
							//console.log(data);
							if($.isNumeric(data)){
								
								$('#allAppPRforCurrentMonth').text(data);
							}else{
								$('#allAppPRforCurrentMonth').text(0);
							}
							
						}
					});

				}

				function loadAllApprovedProgressReportforLastMonth(){
					$.ajaxRequest.ajax({
						type : "POST",
						url : "/PMS/activeProgressReportsbyGCforlastMonth",
						data : {"actionId": 6},
						success : function(data) {	
							//console.log(data);
							if($.isNumeric(data)){
								
								$('#allAppPRforLastMonth').text(data);
							}else{
								$('#allAppPRforLastMonth').text(0);
							}
							
						}
					});

				}

				
				function initializeProgressTable(){					
					var  table= $('#projectProgressTblAll').DataTable( {		
						destroy: true,
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
						
						$('#projectProgressTblAll .filters th[class="comboBox"]').each( function ( i ) {
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
				       $('#projectProgressTblAll .filters th[class="textBox"]').each( function () {                 
				           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

				       } );

				       // DataTable
				      // var table = $('#projectProgressTblAll').DataTable();
				       
				       table.columns().eq( 0 ).each( function ( colIdx ) {
				           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
				               table
				                           .column( colIdx )
				                           .search( this.value )
				                           .draw() ;
				                   
				                                           } );
				} );

				}


				function viewAllowedActionsMonthly(encMonthlyProgressId, numCatId){
							/*alert("1"+numCatId);*/
							//console.log(encMonthlyProgressId);
							/*$.ajaxRequest.ajax({
								type : "POST",
								url : "/PMS/viewApplicationStatus",
								data : {
										"applicationId":applicationId				
									},
								success : function(response) {
									 $('#'+applicationId).find('li').remove();
									 var encNumId=response.encNumId;
									 console.log(encNumId);
									 if(response.convertedToProject==true){
											$('#'+applicationId).append('<li class="padded-half text-center"><span class="font_14 red ">No Action!</span></li>');
										}else{					
											$('#'+applicationId).append('<li> <a class="font_14" onclick ="openApplication(\''+encNumId+'\')"><span class="fa fa-pencil-square-o btn btn-info " id="edit" aria-hidden="true"></span><span class="pad-left-half">Edit</span></a> </li>');
											$('#'+applicationId).append('<li> <a class="font_14" onclick ="convertToProject('+applicationId+')"><span class="fa fa-share-square-o btn btn-info" id="edit" aria-hidden="true"></span> <span class="pad-left-half">Convert To Project</span> </a> </li>');					
										}		
								}, error: function(data) {
							 	  
							   }
						});	*/
					  
							var encWorkflowId = $('#encWorkflowId').val();
							var encPageId = $('#encPageId').val();
							var actionName="View Report";
							$.ajax({
						        type: "POST",
						        url: "/PMS/getNextRoleActionDetails",
						        async:false,
						        data: {"encMonthlyProgressId":encMonthlyProgressId,"encPageId":encPageId,"encWorkflowId":encWorkflowId},			
								success : function(res) {
								
									$('#'+encMonthlyProgressId).find('li').remove();
									$('#'+encMonthlyProgressId).append("<li> <a class='font_14' onclick=previewOfReport('"+encMonthlyProgressId+"') title='Preview of Report'><span  aria-hidden='true'>"+actionName+"</span></a></li>");
										for(var i=0;i<res.length;i++)
										{	
											/*var actionName=res[i].strActionName;
											actionName.push("View Report");
											alert(actionName);*/
											//$('#'+applicationId).append('<li> <a class="font_14" onclick ="openApplication(\''+encNumId+'\')"><span class="fa fa-pencil-square-o btn btn-info " id="edit" aria-hidden="true"></span><span class="pad-left-half">Edit</span></a> </li>');
											/*alert(res[i].strActionName)*/
											$('#'+encMonthlyProgressId).append("<li> <a class='font_14' onclick=performActionMonthly('"+encMonthlyProgressId+"','"+res[i].strEncActionId+"',"+res[i].isRemarksReq+",'"+encPageId+"','"+encWorkflowId+"','"+numCatId+"') title='"+res[i].strToolTip+"'><span  aria-hidden='true'>"+res[i].strActionName+"</span></a></li>");
										}					
								},
								error : function(e) {					
								}		
							});

						}
						
						function performActionMonthly(encMonthlyProgressId,encActionId,isRemarks,encPageId,encWorkflowId,numCatId)
						{ /*alert(isRemarks);*/
							if(isRemarks==0)
							{
								swal({
									  title: "Do you wan to submit?",
									 /* text: "<textarea id='textArea'></textarea>",
						              html: true,*/
									  //input: 'textarea',
									  buttons: [					               
								                'No',
								                'Yes'
								              ],
								              
								    }).then(function(isConfirm) {
								      if (isConfirm) {
								    	  doWorkAccrodingToActionMonthly(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,"",numCatId);
								      } else {
								    	  
								      }
								    }); 
								
							}
							else if(isRemarks==1)
								{
									var textarea = document.createElement('textarea');
								  textarea.rows = 6;
								  textarea.className = 'swal-content__textarea';
								  textarea.id = 'textArea';
								  textarea.placeholder = 'You can write remarks';
								  textarea.disabled = false;
								  
								 
								  
								
									swal({
										  title: 'Do you want to submit?',
										  content: textarea,
										  //text: '<textarea id='textArea'></textarea>',
							              //html: true,
										  //input: 'textarea',
										  buttons: [					               
								                'No',
								                'Yes'
								              ],
		                                   closeOnClickOutside: false // For Prevent the Outside click [05-06-2024]
										}).then(function(isConfirm) {
										      if (isConfirm) {
												 	var textAreaValue=$("#textArea").val();
												 	textAreaValue=textAreaValue.trim();
												 	/*------ Increase the text area length from 300 to 1000 [05-06-2024] ------*/
												 	if(textAreaValue.length < 1000 || textAreaValue.length == 1000)
												 	{
												 		
												 		doWorkAccrodingToActionMonthly(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,textAreaValue,numCatId);
												 	}
												 	else
												 		{
												 			swal("Remarks should be less than or equals to 1000 characters.");
												 		}
										      } else {
										    	  
										      }
										}); 
								}
							else
								{
								  doWorkAccrodingToActionMonthly(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,"",numCatId);
								}
						}
						
						function doWorkAccrodingToActionMonthly(encMonthlyProgressId,encActionId,encPageId,encWorkflowId,strRemarks,numCatId)
						{
							
							$.ajax({
						        type: "POST",
						        url: "/PMS/doWorkAccrodingToAction",
						        async:false,
						        data: {"encMonthlyProgressId":encMonthlyProgressId,"strEncActionId":encActionId,"encPageId":encPageId,"encWorkflowId":encWorkflowId,"strRemarks":strRemarks},			
								success : function(res) {
									//alert(res.numActionId);
									if(res.numActionId==1)
									{
										monthlyReport(encMonthlyProgressId,numCatId);
									}
									else if(res.numActionId==2)
										{
										 previewOfReport(encMonthlyProgressId);
										}
									else if(res.numActionId==3)
										{
										  
										 previewOfReport(encMonthlyProgressId);
										}
									else{
										//alert(res.strSuccessMsg);
										   if( res.strSuccessMsg!='error') 
										   {   
											  
											   sweetSuccess("Attention!","Data Submitted Successfully.");
											 /*  swal({
											    	  title: "",
											    	  text: res.strSuccessMsg,    	
											    	  showCancelButton: false,
											    	  confirmButtonColor: "#DD6B55",    	  
											    	  confirmButtonColor: "#34A534",
											    	  confirmButtonText: "Ok",
											    	  cancelButtonText: "Cancel",
											    	  closeOnConfirm: true,
											    	  closeOnCancel: true
											    	}).then(
											    	 function(isConfirm){
											    	  if (isConfirm) {
											    		  openWindowWithPost('GET','/PMS/mst/ViewAllProjects','_self');
											    	  }
											    	});*/
												projectProgressTblLastMonth();
												projectProgressTblCurrentMonth();
												loadAllApprovedProgressReportforLastMonth();
												loadApprovedProgressReportforCurrentMonth();				        
										   }
										   else if(res.strSuccessMsg!='error')
											   swal("There are some problem.Please contact to Admin.");
									}
								},
								error : function(e) {
									
									
								}


							
							});
							
						}
						
						function monthlyReport(encMonthlyProgressId,numCatId){
					//alert(numCatId);
							var requestURL='';
							$.ajax({
								type : "POST",
								url : "/PMS/getCategoryURL",
								data : {"encCategoryId":numCatId	
								},
								async:false,
								success : function(response) {
									//alert(response);
									requestURL=response;
								},
								error : function(e) {
									alert('Error: ' + e);			
								}
							});	

							//alert(requestURL);
							
								openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encMonthlyProgressId,"encCategoryId":numCatId},'_blank');
								
						}
						
						function previewOfReport(encMonthlyProgressId){
							//alert(encMonthlyProgressId);
							if(encMonthlyProgressId==null || encMonthlyProgressId==''){
								sweetSuccess('Attention','No Report Available as Project is not yet Submiited');
								  return false;
							
							}
							else{
								openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encMonthlyProgressId},'_blank');
							}
							}
						
// changes in pending and due payment filter on 23-05-2023
						
						function calculateInvoiceDates(){
							var d = new Date();
							var symbol= $("#filterSymbol").val();
							var selValue=$("#monthsInvoice").val();
							
							if(selValue==0){
							
								$('#pendingPayments_dataTable').empty();
								$('#pendingPayments_dataTable').DataTable().destroy();
								
								var foot = $("#pendingPayments_dataTable").find('tfoot');
								 if(foot.length){
									 $("#pendingPayments_dataTable tfoot").remove();
								 }
								var  table= $('#pendingPayments_dataTable').DataTable( {
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
							
								var invoiceDate="01"+"/"+fromMonth+"/"+d.getFullYear();
								$('#asOnDatePendingPayments1').text(invoiceDate);
								
							
							/*if(type==1)
							$( "#"+btnIdOrClass ).trigger( "click" );	
							else	
							$( "."+btnIdOrClass ).trigger( "click" );*/
								$("#outstandingcalendarYearDropdown").val("0");
						
							pendingPaymentsTableWithInvoiceDate(invoiceDate,symbol);
							
							}
							
							}
						
						function pendingPaymentsTableWithInvoiceDate(invoiceDate,symbol){
							
							 $('#pendingPayments_dataTable').DataTable().clear().destroy();

							var tableData = '';
							$.ajaxRequest.ajax({
								type : "POST",
								url : "/PMS/mst/getPendingPaymentsDetailsByInvoiceDt",
								data : {"invoiceDate":invoiceDate,
										"symbol":symbol
										},
								success : function(response) {
									var totalInvoiceAmt=0;
									var totalTaxAmt = 0;
									var totalAmt = 0;
									
									for (var i = 0; i < response.length; i++) {
										var row = response[i];
										 totalInvoiceAmt += row.numInvoiceAmt;
										 totalTaxAmt += row.numTaxAmount;
										 totalAmt +=row.numInvoiceTotalAmt;
										var strInvoiceStatus= row.strInvoiceStatus;
										if(row.strInvoiceStatus==null){
											strInvoiceStatus='';
										}
										var strReferenceNumber=row.strReferenceNumber;
										if(!strReferenceNumber){
											strReferenceNumber='';
										}
										var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
									
										tableData += '<tr><td>'+(i+1)+'</td> <td class="font_14">'+ row.strGroupName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
												+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';
										
										tableData += '<td class="font_14 orange">'+row.strClientName+'</td><td class="font_14 ">'
										+ row.strInvoiceRefno +'</td>';
										
										tableData += ' <td class="font_14">'+ row.strInvoiceDate+ '</td>';
										
										tableData += ' <td class="font_14">'+ strInvoiceStatus+  '</td>';
										
										tableData += ' <td class="font_14 text-right">'	+ row.strInvoiceAmt +'</td>';
										
										tableData += ' <td class="font_14 text-right">'	+ row.strTaxAmount + '</td>';
										
										tableData += ' <td class="font_14 text-right">'+ row.strInvoiceTotalAmt+ '</td> </tr>';			
										
									}		
								
									$('#pendingPayments_dataTable').append(tableData); 
									var foot = $("#pendingPayments_dataTable").find('tfoot');
									 if(foot.length){
										 $("#pendingPayments_dataTable tfoot").remove();
									 }
									
									if(totalInvoiceAmt >0){
													
										 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#pendingPayments_dataTable"); 
										    foot.append('<tr><td></td> <td></td><td></td><td></td> <td></td><td></td> <td class="bold text-right">Total</td><td class="bold text-right totalinvoice">'+convertAmountToINR(totalInvoiceAmt)+'</td> <td class="bold text-right totaltax">'+convertAmountToINR(totalTaxAmt)+'</td> <td class="bold text-right totalAmount">'+convertAmountToINR(totalAmt)+'</td></tr>');
									}          
									var  table= $('#pendingPayments_dataTable').DataTable( {
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
									
								
										
										table.on('order.dt search.dt', function () {
											table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
										           cell.innerHTML = i+1;
										           table.cell(cell).invalidate('dom');
										     });
											 updateTotalAmount(table);
										}).draw();
										
										$('#pendingPayments_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
									                 updateTotalAmount(table);
									                } );
									     
									            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
									                select.append( '<option value="'+d+'">'+d+'</option>' )
									            } );
									        } );
										

										 // Setup - add a text input
								       $('#pendingPayments_dataTable .filters th[class="textBox"]').each( function () {                 
								           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

								       } );

						               // DataTable
						               var table = $('#pendingPayments_dataTable').DataTable();
								       
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
						
						function calculateDueDates(){
							var d = new Date();
							var symbol= $("#filterDueSymbol").val();
							var selValue=$("#monthsDue").val();
						
							if(selValue=='0'){
								$('#pendingInvoices_dataTable').empty();
								$('#pendingInvoices_dataTable').DataTable().destroy();
								var foot = $("#pendingInvoices_dataTable").find('tfoot');
								 if(foot.length){
									 $("#pendingInvoices_dataTable tfoot").remove();
								 }
								var  table= $('#pendingInvoices_dataTable').DataTable( {
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
							
								var dueDate="01"+"/"+fromMonth+"/"+d.getFullYear();
								$('#asOnDateDueInvoice1').text(dueDate);
								
								
								$("#dueInvoiceCalendarYearDropdown").val("0");
						
																				
								pendingInvoicesTableByDate(dueDate,symbol);
							}
							}
						
						function pendingInvoicesTableByDate(dueDate,symbol){	
							$('#pendingInvoices_dataTable').DataTable().clear().destroy();
							var tableData = '';
							$.ajaxRequest.ajax({
								type : "POST",
								url : "/PMS/mst/getPendingInvoiceDetailbyDate",
								data : {"dueDate":dueDate,
									"symbol":symbol
									},
								success : function(response) {
									var totalAmt =0;
									for (var i = 0; i < response.length; i++) {
										
										var row = response[i];	
										var strPurpose=row.strPurpose;
										var strRemarks=row.strRemarks;
										totalAmt +=row.numInvoiceAmtInLakhs;
										if(row.strPurpose==null){
											strPurpose='';
										}
										if(row.strRemarks==null){
											strRemarks='';
										}
										var strReferenceNumber=row.strReferenceNumber;
										if(!strReferenceNumber){
											strReferenceNumber='';
										}
										var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
												+ row.encProjectId + "')";
									
										tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.strGroupName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
												+ row.strProjectName + ' </a> <p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p></td>';
										
										tableData += '<td class="font_14 orange">'+row.strClientName+'</td><td class="font_14 "> <span  class="" >'
										+ row.strPaymentDueDate
										+  '</span></td>';
										
										tableData += ' <td class="font_14">'+ strPurpose+  '</td>';
										
										tableData += ' <td class="font_14"> <span  class="" >'
											+strRemarks +  '</span></td>';
										tableData += ' <td class="font_14 text-right">'+row.strAmount+ ' Lakhs</td> </tr>';
										
									}

									/*if(totalAmt>0){
										tableData +='<tr><td></td><td></td> <td></td> <td></td>  <td></td><td class="bold text-right">Total</td> <td class="bold text-right">'+convertAmountToINR(totalAmt)+' Lakhs</td></tr>';
									}*/
									
									$('#pendingInvoices_dataTable > tbody').html('');
									$('#pendingInvoices_dataTable').append(tableData); 
									var foot = $("#pendingInvoices_dataTable").find('tfoot');
									 if(foot.length){
										 $("#pendingInvoices_dataTable tfoot").remove();
									 }
									
									if(totalAmt >0){
														
										 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#pendingInvoices_dataTable"); 
										    foot.append('<tr><td><td></td></td><td></td> <td></td> <td></td>  <td></td><td class="bold text-right">Total</td> <td class="bold text-right totalinvoice">'+convertAmountToINR(totalAmt)+' Lakhs</td></tr>');
									}
						           
									var  table= $('#pendingInvoices_dataTable').DataTable( {
										destroy: true,
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
											 updateTotalAmount(table);
										}).draw();
										
										$('#pendingInvoices_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
									                 updateTotalAmount(table);
									                } );
									     
									            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
									                select.append( '<option value="'+d+'">'+d+'</option>' )
									            } );
									        } );
										

										 // Setup - add a text input
								       $('#pendingInvoices_dataTable .filters th[class="textBox"]').each( function () {                 
								           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

								       } );

						               // DataTable
						               var table = $('#pendingInvoices_dataTable').DataTable();
								       
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
						function calculateClosureDates(){
							var d = new Date();
							var symbol= $("#filterClosureSymbol").val();
							var selValue=$("#monthsClosure").val();
							
							if(selValue=='0'){
								
								
								$('#pendingCLosure_dataTable').empty();
								$('#pendingCLosure_dataTable').DataTable().destroy();
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
								    $(".closureList").each(function() {
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
												  tableData += ' <td class="font_14 text-center golden-text">Project Closure Not Initiated</span></td> </tr>';
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
						function initializeUnderClosure(){
							 openWindowWithPost('GET','/PMS/mst/underClosureProjectsforPMO','_self');
						}

						//Added by devesh on 26-09-23 for adding expand button on proposal history modal
						var isMaximizedproposal = false;
						var scrollHeadInnerDivflag = true;
						var originalWidth = "";
						function toggleproposalhistory() {
							  var myModal = document.getElementById("proposalhistorymodal");
							  
							  if (myModal) {
							  var scrollHeadInnerDiv = myModal.querySelector(".dataTables_scrollHeadInner");
							  // Check if the scrollHeadInnerDiv element exists
							  if (scrollHeadInnerDiv && scrollHeadInnerDivflag) {
							  // Get the original width of the scrollHeadInnerDiv
							  originalWidth = scrollHeadInnerDiv.style.width;
							  scrollHeadInnerDivflag = false;
							  }
							  }
							  //console.log("originalWidth: "+originalWidth);
							  
							  if (isMaximizedproposal) {
							    /*myModal.classList.remove("maximized");*/
								
							    document.body.classList.remove("modal-open");
							    document.getElementById('proposalhistorymodal').style.height = '';
							    document.getElementById('proposalhistorymodal').style.width = '';
							    scrollHeadInnerDiv.style.width = originalWidth;
							  } else {
							    /*myModal.classList.add("maximized");*/
							    document.body.classList.add("modal-open");

							    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
							    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
							    var modal = document.getElementById('proposalhistorymodal');
							    modal.style.height = (windowHeight * 0.75) + 'px'; 
							    modal.style.width = (windowWidth * 0.98) + 'px';
							    modal.style.transformOrigin = 'center';
							    if (myModal) {
								    // Find the div with class "dataTables_scrollBody" inside the modal
								    var scrollBodyDiv = myModal.querySelector(".dataTables_scrollBody");

								    // Check if the scrollBodyDiv element exists
								    if (scrollBodyDiv) {
								      // Get the width of the scrollBodyDiv
								      var scrollBodyWidth = scrollBodyDiv.clientWidth;
								      //console.log("Width of scrollBodyDiv:", scrollBodyWidth, "pixels");
								    } else {
								      console.log("Element with class 'dataTables_scrollBody' not found inside the modal.");
								    }
								  }
							    scrollHeadInnerDiv.style.width = scrollBodyWidth;
							  }

							  isMaximizedproposal = !isMaximizedproposal; 

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

						function restoreproposalsize() {
							  var myModal = document.getElementById("proposalhistorymodal");
							    document.body.classList.remove("modal-open");
							    document.getElementById('proposalhistorymodal').style.height = '';
							    document.getElementById('proposalhistorymodal').style.width = '';
							    if (myModal) {
									  var scrollHeadInnerDiv = myModal.querySelector(".dataTables_scrollHeadInner");
							    }
							    scrollHeadInnerDiv.style.width = originalWidth;
							    scrollHeadInnerDivflag = true;
							    isMaximizedproposal=false;
							}
						//End of expand button for proposal history modal
						
						var isMaximized = false; 

						function toggleMaximize() {
						  var myModal = document.getElementById("myModal");
						  
						  if (isMaximized) {
						 
						    myModal.classList.remove("maximized");
						    document.body.classList.remove("modal-open");
						    document.getElementById('dash-grpwise-bargraph-modal').style.height = '';
						    document.getElementById('dash-grpwise-bargraph-modal').style.width = '';
						  } else {
						   
						     myModal.classList.add("maximized");
						    document.body.classList.add("modal-open");

						    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						    var modal = document.getElementById('dash-grpwise-bargraph-modal');
						    modal.style.height = (windowHeight * 0.75) + 'px'; 
						     //modal.style.width = (windowWidth * 0.98) + 'px'; 
						     modal.style.transformOrigin = 'center';

						    

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
							  var myModal = document.getElementById("myModal");
							  myModal.classList.remove("maximized");
							  document.body.classList.remove("modal-open");
							  document.getElementById('dash-grpwise-bargraph-modal').style.height = '';
							  document.getElementById('dash-grpwise-bargraph-modal').style.width = '';
							  

							}
						 function restoreprop(){
							 isMaximized = true;
							toggleMaximize();
							 restoreModalSize();
						} 
						
						 var isMaximized = false; 

						 function toggleEmployee() {
						   var myModal = document.getElementById("myemps");
						   
						   if (isMaximized) {
						  
						     myModal.classList.remove("maximized");
						     document.body.classList.remove("modal-open");
						     document.getElementById('dash-employee-modal').style.height = '';
						     document.getElementById('dash-employee-modal').style.width = '';
						   } else {
						    
						      myModal.classList.add("maximized");
						     document.body.classList.add("modal-open");

						     var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						     var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						     var modal = document.getElementById('dash-employee-modal');
						     modal.style.height = (windowHeight * 0.75) + 'px'; 
						     modal.style.width = (windowWidth * 0.98) + 'px'; 
						      modal.style.transformOrigin = 'center';

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

						 function restoreempSize() {
						 	  var myModal = document.getElementById("myemps");
						 	  myModal.classList.remove("maximized");
						 	  document.body.classList.remove("modal-open");
						 	  document.getElementById('dash-employee-modal').style.height = '';
						 	  document.getElementById('dash-employee-modal').style.width = '';
						 	  

						 	}
						  function restoremp(){
						  /*-----------------------------  Collapse all the Employee Chart table [04-08-2023] --------------------*/
							    $('#multiCollapse1,#multiCollapse2,#multiCollapse3').filter('.in').collapse('hide');
							    isMaximized = true;
								toggleEmployee();
							 	restoreempSize();
							 	
	
								/* Bhavesh (08-09-23) destroyed employee with involvment table*/
							    /*$('#employeeWithInvolvementsTbl').dataTable().fnDestroy();
							 	$('#employeeWithInvolvementsTbl').DataTable( {		
									destroy: true,
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
							    } );*/
							    //Modified by devesh on 04-06-24 to properly reset table filters
							    $('#employeeWithInvolvementsTbl .filters select').each(function() {
						            $(this).val(null).trigger('change'); // Reset Select2
						        });
						        $('#employeeWithInvolvementsTbl').DataTable().search('').draw();
						        $('#employeeWithInvolvementsTbl').DataTable().columns.adjust().draw();
						        //End of code
						 	} 			
						  
						  var isMaximized = false;
						  function toggleIncome() {
						  	  var myModal = document.getElementById("myIncome");
						  	  
						  	  if (isMaximized) {
						  	    myModal.classList.remove("maximized");
						  	    document.body.classList.remove("modal-open");
						  	    document.getElementById('dash-income-modal').style.height = '';
						  	    document.getElementById('dash-income-modal').style.width = '';
						  	  } else {
						  	    myModal.classList.add("maximized");
						  	    document.body.classList.add("modal-open");

						  	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						  	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						  	    var modal = document.getElementById('dash-income-modal');
						  	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						  	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						  	    modal.style.transformOrigin = 'center';
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
						  function restoreIncomeSize() {
						  	  var myModal = document.getElementById("myIncome");
						  	  myModal.classList.remove("maximized");
						  	  document.body.classList.remove("modal-open");
						  	  document.getElementById('dash-income-modal').style.height = '';
						  	  document.getElementById('dash-income-modal').style.width = '';
						  	}

						  	
						  	  // Code for restoring the proposals

						  	 

						   function restoreIncome(){
							   isMaximized = true;
						  	 $(".incomesmon").val("0");
						  	$("#incomefrom").val("");
						  	$("#incometo").val("");
						  	 
						  	  
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
						  	  $('#asOnDate').text(selectedRange);
						  	  
						  	  //alert('Called');
						  	  incomeTable();
						  	  $('#asOnDate').text(fromDate);
						  	  $("#asOnDate1").text(fromDate);
						  	  toggleIncome();
						  	  restoreIncomeSize();
						  	
						  }    
						   
						   var isMaximized = false;
						   function toggleexpend() {
						   	  var myModal = document.getElementById("myexpend");
						   	  
						   	  if (isMaximized) {
						   	    myModal.classList.remove("maximized");
						   	    document.body.classList.remove("modal-open");
						   	    document.getElementById('dash-expenditure-modal').style.height = '';
						   	    document.getElementById('dash-expenditure-modal').style.width = '';
						   	  } else {
						   	    myModal.classList.add("maximized");
						   	    document.body.classList.add("modal-open");

						   	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						   	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						   	    var modal = document.getElementById('dash-expenditure-modal');
						   	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						   	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						   	    modal.style.transformOrigin = 'center';
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
						   function restoreexpendsize() {
						   	  var myModal = document.getElementById("myexpend");
						   	  myModal.classList.remove("maximized");
						   	  document.body.classList.remove("modal-open");
						   	  document.getElementById('dash-expenditure-modal').style.height = '';
						   	  document.getElementById('dash-expenditure-modal').style.width = '';
						   	}

						   	
						   	  // Code for restoring the proposals

						   	 

						    function restoreexpend(){
						   	
						    	 isMaximized = true;
						   	 toggleexpend();
						   	 restoreexpendsize();
						   	
						   }      
						    
						    var isMaximized = false;
						    function togglepmoprop() {
						    	  var myModal = document.getElementById("myProp");
						    	  
						    	  if (isMaximized) {
						    	    myModal.classList.remove("maximized");
						    	    document.body.classList.remove("modal-open");
						    	    document.getElementById('dash-newProposalList-modal').style.height = '';
						    	    document.getElementById('dash-newProposalList-modal').style.width = '';
						    	  } else {
						    	    myModal.classList.add("maximized");
						    	    document.body.classList.add("modal-open");

						    	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						    	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						    	    var modal = document.getElementById('dash-newProposalList-modal');
						    	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						    	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						    	    modal.style.transformOrigin = 'center';
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
						    function restorepropSize() {
						    	  var myModal = document.getElementById("myProp");
						    	  myModal.classList.remove("maximized");
						    	  document.body.classList.remove("modal-open");
						    	  document.getElementById('dash-newProposalList-modal').style.height = '';
						    	  document.getElementById('dash-newProposalList-modal').style.width = '';
						    	}

						    	
						    	  // Code for restoring the proposals

						    	 

						     function restorepmoprop(){
						    	 isMaximized = true;
						    	$("#months").val("0");
						    	 $("#fromProposal").val("");
						    	$("#toProposal").val("");
						    	$("#incomecalendarYearDropdown").val("0");
						    	 
						    	
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
						        console.log(toDate);
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
						    	  $('#asOnDateProposals').text(selectedRange);
						    	  
						    	  //alert('Called');
						    	  newProposalsTable();
						    	  projectsReceivedTable();  // call the project received function [08-02-2024]
						    	  $('#asOnDateProposals').text(fromDate);
						    	  $("#asOnDateProposals1").text(fromDate);
						    	 togglepmoprop();
						    	 restorepropSize();
						    	
						    }      
						     
						     var isMaximized = false;
						     function toggleProjects() {
						     	  var myModal = document.getElementById("myProject");
						     	  
						     	  if (isMaximized) {
						     	    myModal.classList.remove("maximized");
						     	    document.body.classList.remove("modal-open");
						     	    document.getElementById('dash-newProjectList-modal').style.height = '';
						     	    document.getElementById('dash-newProjectList-modal').style.width = '';
						     	  } else {
						     	    myModal.classList.add("maximized");
						     	    document.body.classList.add("modal-open");

						     	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						     	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						     	    var modal = document.getElementById('dash-newProjectList-modal');
						     	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						     	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						     	    modal.style.transformOrigin = 'center';
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
						     function restoreProjectsSize() {
						     	  var myModal = document.getElementById("myProject");
						     	  myModal.classList.remove("maximized");
						     	  document.body.classList.remove("modal-open");
						     	  document.getElementById('dash-newProjectList-modal').style.height = '';
						     	  document.getElementById('dash-newProjectList-modal').style.width = '';
						     	}

						     	
						     	  // Code for restoring the proposals

						     	 

						      function restoreProjects(){
						    	  isMaximized = true;
						     	$(".listprojects").val("0");
						     	 $("#from").val("");
						     	$("#to").val("");
						    	$("#projectcalendarYearDropdown").val("0");
				               /*-----------------------------  Collapse all the Project received table [04-08-2023] --------------------*/
				 	           $('#multiCollapse10, #multiCollapse11,#multiCollapse12,#multiCollapse13').filter('.in').collapse('hide');
				 	           /*-------------------------------------------------------------------------------------------------------*/
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
						     	  $('#asOnDateProjects').text(selectedRange);
						     	  
						     	  //alert('Called');
						     	  newProjectsTable();
						     	  $('#asOnDateProjects').text(fromDate);
						     	  $("#asOnDateProjects1").text(fromDate);
						     	 toggleProjects();
						     	restoreProjectsSize();
						     	
						     }  
						      
						      var isMaximized = false;
						      function toggleClosed() {
						      	  var myModal = document.getElementById("myclosed");
						      	  
						      	  if (isMaximized) {
						      	    myModal.classList.remove("maximized");
						      	    document.body.classList.remove("modal-open");
						      	    document.getElementById('dash-closedProjectList-modal').style.height = '';
						      	    document.getElementById('dash-closedProjectList-modal').style.width = '';
						      	  } else {
						      	    myModal.classList.add("maximized");
						      	    document.body.classList.add("modal-open");

						      	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						      	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						      	    var modal = document.getElementById('dash-closedProjectList-modal');
						      	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						      	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						      	    modal.style.transformOrigin = 'center';
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
						      function restoreclosedSize() {
						      	  var myModal = document.getElementById("myclosed");
						      	  myModal.classList.remove("maximized");
						      	  document.body.classList.remove("modal-open");
						      	  document.getElementById('dash-closedProjectList-modal').style.height = '';
						      	  document.getElementById('dash-closedProjectList-modal').style.width = '';
						      	}

						      	
						      	  // Code for restoring the proposals

						      	 

						       function restoreClosed(){
						    	   isMaximized = true;
						      	$(".closesprojects").val("0");
						      	 $("#from1").val("");
						      	$("#to1").val("");
						      	$("#closedcalendarYearDropdown").val("0");
						      	
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
						      	  //$('#asOnDateProjects').text(selectedRange);  [ disable in 12-10-2023 ]
						      	  
						      	  //alert('Called');
						      	 
						      	  $('#asOnDateClosedProjects').text(fromDate);
						      	  $("#asOnDateClosedProjects1").text(fromDate);
							      	 closedProjectsTable(); // call the closed table function 12-10-2023
						      	 toggleClosed();
						      	restoreclosedSize();
						      	
						      }  
						       
						       
						       // below function added restore the financial vetting validation by varun on 06-12-2023
						       
						       var isMaximized = false;
							      function toggleValidate() {
							      	  var myModal = document.getElementById("myvalidate");
							      	  
							      	  if (isMaximized) {
							      	    myModal.classList.remove("maximized");
							      	    document.body.classList.remove("modal-open");
							      	    document.getElementById('dash-validationProject-modal').style.height = '';
							      	    document.getElementById('dash-validationProject-modal').style.width = '';
							      	  } else {
							      	    myModal.classList.add("maximized");
							      	    document.body.classList.add("modal-open");

							      	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
							      	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
							      	    var modal = document.getElementById('dash-validationProject-modal');
							      	    modal.style.height = (windowHeight * 0.75) + 'px'; 
							      	    modal.style.width = (windowWidth * 0.98) + 'px'; 
							      	    modal.style.transformOrigin = 'center';
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
							      function restorevalidateSize() {
							      	  var myModal = document.getElementById("myvalidate");
							      	  myModal.classList.remove("maximized");
							      	  document.body.classList.remove("modal-open");
							      	  document.getElementById('dash-validationProject-modal').style.height = '';
							      	  document.getElementById('dash-validationProject-modal').style.width = '';
							      	}

							      	function restoreValidate(){
							    	   isMaximized = true;
							      	$(".valdprojects").val("0");
							      	 $("#fromvalidated").val("");
							      	$("#tovalidated").val("");
							    	$("#calendarYearDropdown").val("0");
							      	$("#validationStatusDropdown").val("");
							      	 
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
							      	
							      	  $('#asOnValidatedProjects').text(fromDate);
							      	  $("#asOnDatevalidationProject").text(fromDate);
							      	validatedProjectsTable(); 
								      	toggleValidate();
							      	restorevalidateSize();
							      	
							      }  
						       
						       
						       var isMaximized = false;
						       function togglePending() {
						       	  var myModal = document.getElementById("myPending");
						       	  
						       	  if (isMaximized) {
						       	    myModal.classList.remove("maximized");
						       	    document.body.classList.remove("modal-open");
						       	    document.getElementById('dash-closure-pending-modal').style.height = '';
						       	    document.getElementById('dash-closure-pending-modal').style.width = '';
						       	  } else {
						       	    myModal.classList.add("maximized");
						       	    document.body.classList.add("modal-open");

						       	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						       	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						       	    var modal = document.getElementById('dash-closure-pending-modal');
						       	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						       	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						       	    modal.style.transformOrigin = 'center';
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
						       function restorePendingSize() {
						       	  var myModal = document.getElementById("myPending");
						       	  myModal.classList.remove("maximized");
						       	  document.body.classList.remove("modal-open");
						       	  document.getElementById('dash-closure-pending-modal').style.height = '';
						       	  document.getElementById('dash-closure-pending-modal').style.width = '';
						       	}
						       
						       function restorepending(){
						    	   isMaximized = true;
						      		$("#monthsClosure").val("0");
						      		//$("#filterClosureSymbol").val("");
						      		togglePending();
						      		restorePendingSize();
						      		pendingClosuresTable();
						       }
						       
						       //Bhavesh(16-08-23)
						       var isMaximized2 = false;
						       function togglePending2() {
						       	  var myModal = document.getElementById("myPending2");
						       	  
						       	  if (isMaximized2) {
						       	    myModal.classList.remove("maximized");
						       	    document.body.classList.remove("modal-open");
						       	    document.getElementById('dash-closure-pending-modal2').style.height = '';
						       	    document.getElementById('dash-closure-pending-modal2').style.width = '';
						       	  } else {
						       	    myModal.classList.add("maximized");
						       	    document.body.classList.add("modal-open");

						       	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						       	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						       	    var modal = document.getElementById('dash-closure-pending-modal2');
						       	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						       	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						       	    modal.style.transformOrigin = 'center';
						       	  }

						       	  isMaximized2 = !isMaximized2; 

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
						       function restorePendingSize2() {
						       	  var myModal = document.getElementById("myPending");
						       	  myModal.classList.remove("maximized");
						       	  document.body.classList.remove("modal-open");
						       	  document.getElementById('dash-closure-pending-modal2').style.height = '';
						       	  document.getElementById('dash-closure-pending-modal2').style.width = '';
						       	}
						       
						       function restorepending2(){
						      		/*$("#monthsClosure").val("0");*/
						      		//$("#filterClosureSymbol").val("");
						    	   $('#exampleUnderClosure').dataTable().fnDestroy();
						    	   //----- Set Closure Table Data properties [21-09-2023]-------------------
						    	   /*setTablePropertiesForClosureRequestTile*/
						    	   getClosureRequestTileData();//Added by devesh on 16-10-23 to reload table on modal close
						      		 isMaximized2 = true;
						      		togglePending2();
						      		restorePendingSize2();
						      		
						       }
						       
						       //Bhavesh(16-08-23)
						       
						       var isMaximized = false;
						       function toggleJoinee() {
						       	  var myModal = document.getElementById("myJionee");
						       	  
						       	  if (isMaximized) {
						       	    myModal.classList.remove("maximized");
						       	    document.body.classList.remove("modal-open");
						       	    document.getElementById('dash-new-joinee-modal').style.height = '';
						       	    document.getElementById('dash-new-joinee-modal').style.width = '';
						       	  } else {
						       	    myModal.classList.add("maximized");
						       	    document.body.classList.add("modal-open");

						       	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						       	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						       	    var modal = document.getElementById('dash-new-joinee-modal');
						       	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						       	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						       	    modal.style.transformOrigin = 'center';
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
						       function restoreJoineeSize() {
						       	  var myModal = document.getElementById("myJionee");
						       	  myModal.classList.remove("maximized");
						       	  document.body.classList.remove("modal-open");
						       	  document.getElementById('dash-new-joinee-modal').style.height = '';
						       	  document.getElementById('dash-new-joinee-modal').style.width = '';
						       	}

						       	
						       	  // Code for restoring the proposals

						       	 

						        function restoreJoinee(){
						       	
						      	  $(".monthy").val("0");
						       	 $("#fromJoin").val("");
						       	$("#toJoin").val("");
						       	 
						       	
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
						           console.log(toDate);
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
						       	  $('#asOnDateJoinee').text(selectedRange);
						       	  
						       	  //alert('Called');
						       	  newJoineeEmpTable();
						       	  $('#asOnDateJoinee').text(fromDate);
						       	  $("#asOnDateJoinee1").text(fromDate);
						       	 toggleJoinee();
						       	 restoreJoineeSize();
						       	
						       }      
						        
						        var isMaximized = false;
						        function toggleMaping() {
						        	  var myModal = document.getElementById("myMap");
						        	  
						        	  if (isMaximized) {
						        	    myModal.classList.remove("maximized");
						        	    document.body.classList.remove("modal-open");
						        	    document.getElementById('Employee-Details-modal').style.height = '';
						        	    document.getElementById('Employee-Details-modal').style.width = '';
						        	  } else {
						        	    myModal.classList.add("maximized");
						        	    document.body.classList.add("modal-open");

						        	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						        	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						        	    var modal = document.getElementById('Employee-Details-modal');
						        	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						        	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						        	    modal.style.transformOrigin = 'center';
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
						        function restoreMapSize() {
						        	  var myModal = document.getElementById("myMap");
						        	  myModal.classList.remove("maximized");
						        	  document.body.classList.remove("modal-open");
						        	  document.getElementById('Employee-Details-modal').style.height = '';
						        	  document.getElementById('Employee-Details-modal').style.width = '';
						        	}

						        	
						        	  // Code for restoring the proposals

						        	 

						         function restoreMaping(){
						        
						        	 toggleMaping();
						        	restoreMapSize();
						        	
						        }      
						         
						         
						         var isMaximized = false;
						         function toggleutil() {
						         	  var myModal = document.getElementById("myutil");
						         	  
						         	  if (isMaximized) {
						         	    myModal.classList.remove("maximized");
						         	    document.body.classList.remove("modal-open");
						         	    document.getElementById('Employee-UnderUti-Details-modal').style.height = '';
						         	    document.getElementById('Employee-UnderUti-Details-modal').style.width = '';
						         	  } else {
						         	    myModal.classList.add("maximized");
						         	    document.body.classList.add("modal-open");

						         	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						         	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						         	    var modal = document.getElementById('Employee-UnderUti-Details-modal');
						         	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						         	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						         	    modal.style.transformOrigin = 'center';
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
						         function restoreUtilSize() {
						         	  var myModal = document.getElementById("myutil");
						         	  myModal.classList.remove("maximized");
						         	  document.body.classList.remove("modal-open");
						         	  document.getElementById('Employee-UnderUti-Details-modal').style.height = '';
						         	  document.getElementById('Employee-UnderUti-Details-modal').style.width = '';
						         	}

						         	
						         	  // Code for restoring the proposals

						         	 

						          function restoreutil(){
						         
						        	  toggleutil();
						         	restoreUtilSize();
						         	
						         }      
						          
						          var isMaximized = false;
						          function toggleresigned() {
						          	  var myModal = document.getElementById("myresigned");
						          	  
						          	  if (isMaximized) {
						          	    myModal.classList.remove("maximized");
						          	    document.body.classList.remove("modal-open");
						          	    document.getElementById('dash-resigned-emp-modal').style.height = '';
						          	    document.getElementById('dash-resigned-emp-modal').style.width = '';
						          	  } else {
						          	    myModal.classList.add("maximized");
						          	    document.body.classList.add("modal-open");

						          	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						          	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						          	    var modal = document.getElementById('dash-resigned-emp-modal');
						          	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						          	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						          	    modal.style.transformOrigin = 'center';
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
						          function restoreresignedsize() {
						          	  var myModal = document.getElementById("myresigned");
						          	  myModal.classList.remove("maximized");
						          	  document.body.classList.remove("modal-open");
						          	  document.getElementById('dash-resigned-emp-modal').style.height = '';
						          	  document.getElementById('dash-resigned-emp-modal').style.width = '';
						          	}

						          	
						          	  // Code for restoring the proposals

						          	 

						           function restoreresigned(){
						        	   isMaximized = true;
						          	 $("#fromRes").val("");
						          	$("#toRes").val("");
						          	 
						          	
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
						              console.log(toDate);
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
						          	  $('#asOnDateResign').text(selectedRange);
						          	  
						          	  //alert('Called');
						          	  newResignEmpTable();
						          	  $('#asOnDateResign').text(fromDate);
						          	  $("#asOnDateResign1").text(fromDate);
						          	  toggleresigned();
						          	  restoreresignedsize();
						          	
						          }      
						           
						           var isMaximized = false;
						           function toggleapprovreport() {
						           	  var myModal = document.getElementById("myapprove");
						           	  
						           	  if (isMaximized) {
						           	    myModal.classList.remove("maximized");
						           	    document.body.classList.remove("modal-open");
						           	    document.getElementById('Progress-Reports-modal-current-month').style.height = '';
						           	    document.getElementById('Progress-Reports-modal-current-month').style.width = '';
						           	  } else {
						           	    myModal.classList.add("maximized");
						           	    document.body.classList.add("modal-open");

						           	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						           	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						           	    var modal = document.getElementById('Progress-Reports-modal-current-month');
						           	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						           	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						           	    modal.style.transformOrigin = 'center';
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
						           function restoreapproveSize() {
						           	  var myModal = document.getElementById("myapprove");
						           	  myModal.classList.remove("maximized");
						           	  document.body.classList.remove("modal-open");
						           	  document.getElementById('Progress-Reports-modal-current-month').style.height = '';
						           	  document.getElementById('Progress-Reports-modal-current-month').style.width = '';
						           	}

						           	
						           	  // Code for restoring the proposals

						           	 

						            function restoreapprovreport(){
						            	 isMaximized = true;
						           	 toggleapprovreport();
						           	 restoreapproveSize();
						           	
						           } 
						            
						            var isMaximized = false;
						            function togglemontreport() {
						            	  var myModal = document.getElementById("myreport");
						            	  
						            	  if (isMaximized) {
						            	    myModal.classList.remove("maximized");
						            	    document.body.classList.remove("modal-open");
						            	    document.getElementById('Progress-Reports-modal-last-month').style.height = '';
						            	    document.getElementById('Progress-Reports-modal-last-month').style.width = '';
						            	  } else {
						            	    myModal.classList.add("maximized");
						            	    document.body.classList.add("modal-open");

						            	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						            	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						            	    var modal = document.getElementById('Progress-Reports-modal-last-month');
						            	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						            	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						            	    modal.style.transformOrigin = 'center';
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
						            function restoremonthSize() {
						            	  var myModal = document.getElementById("myreport");
						            	  myModal.classList.remove("maximized");
						            	  document.body.classList.remove("modal-open");
						            	  document.getElementById('Progress-Reports-modal-last-month').style.height = '';
						            	  document.getElementById('Progress-Reports-modal-last-month').style.width = '';
						            	}

						            	
						            	  // Code for restoring the proposals

						            	 

						             function restoremontreport(){
						            	 isMaximized = true;
						            	 togglemontreport();
						            	 restoremonthSize();
						            	
						            }      
						             
						             var isMaximized = false;
						             function togglePayment() {
						             	  var myModal = document.getElementById("mypayment");
						             	  
						             	  if (isMaximized) {
						             	    myModal.classList.remove("maximized");
						             	    document.body.classList.remove("modal-open");
						             	    document.getElementById('dash-pending-invoices-modal').style.height = '';
						             	    document.getElementById('dash-pending-invoices-modal').style.width = '';
						             	  } else {
						             	    myModal.classList.add("maximized");
						             	    document.body.classList.add("modal-open");

						             	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						             	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						             	    var modal = document.getElementById('dash-pending-invoices-modal');
						             	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						             	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						             	    modal.style.transformOrigin = 'center';
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
						             function restorepaymentsize() {
						             	  var myModal = document.getElementById("mypayment");
						             	  myModal.classList.remove("maximized");
						             	  document.body.classList.remove("modal-open");
						             	  document.getElementById('dash-pending-invoices-modal').style.height = '';
						             	  document.getElementById('dash-pending-invoices-modal').style.width = '';
						             	}

						             	
						             	  // Code for restoring the proposals

						             	 

						              function restorePayment(){
						             	
						            	  isMaximized = true;
						             	$("#monthsDue").val("0");
						             	$("#dueInvoiceCalendarYearDropdown").val("0");						             	//pendingInvoicesTableByDate();
						             	
						             	pendingInvoicesTable();
						             	//console.log(pendingInvoicesTable());
						             	togglePayment();
						             	restorepaymentsize();
						             } 
						              
						              var isMaximized = false;
						              function toggleProgress() {
						              	  var myModal = document.getElementById("myprogress");
						              	  
						              	  if (isMaximized) {
						              	    myModal.classList.remove("maximized");
						              	    document.body.classList.remove("modal-open");
						              	    document.getElementById('Progress-Reports-modal').style.height = '';
						              	    document.getElementById('Progress-Reports-modal').style.width = '';
						              	  } else {
						              	    myModal.classList.add("maximized");
						              	    document.body.classList.add("modal-open");

						              	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						              	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						              	    var modal = document.getElementById('Progress-Reports-modal');
						              	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						              	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						              	    modal.style.transformOrigin = 'center';
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
						              function restoreprogresssize() {
						              	  var myModal = document.getElementById("myprogress");
						              	  myModal.classList.remove("maximized");
						              	  document.body.classList.remove("modal-open");
						              	  document.getElementById('Progress-Reports-modal').style.height = '';
						              	  document.getElementById('Progress-Reports-modal').style.width = '';
						              	}

						              	
						              	  // Code for restoring the proposals

						              	 

						               function restoreprogress(){
						            	   isMaximized = true;
						              	 $("#fromDt").val("");
						              	$("#reportType").val("");
						              	  monthlyPR();
						              	  toggleProgress();
						              	  restoreprogresssize();
						              	
						              }   
						               var isMaximized = false;
						               function toggleJoineelist() {
						               	  var myModal = document.getElementById("mypenlist");
						               	  
						               	  if (isMaximized) {
						               	    myModal.classList.remove("maximized");
						               	    document.body.classList.remove("modal-open");
						               	    document.getElementById('dash-pending-payments-modal').style.height = '';
						               	    document.getElementById('dash-pending-payments-modal').style.width = '';
						               	  } else {
						               	    myModal.classList.add("maximized");
						               	    document.body.classList.add("modal-open");

						               	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						               	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
						               	    var modal = document.getElementById('dash-pending-payments-modal');
						               	    modal.style.height = (windowHeight * 0.75) + 'px'; 
						               	    modal.style.width = (windowWidth * 0.98) + 'px'; 
						               	    modal.style.transformOrigin = 'center';
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
						               function restorelistSize() {
						               	  var myModal = document.getElementById("myModal");
						               	  myModal.classList.remove("maximized");
						               	  document.body.classList.remove("modal-open");
						               	  document.getElementById('dash-pending-payments-modal').style.height = '';
						               	  document.getElementById('dash-pending-payments-modal').style.width = '';
						               	}

						               	
						               	  // Code for restoring the proposals

						               	 

						                function Joineelist(){
						               	$("#monthsInvoice").val("0");
						               	$("#outstandingcalendarYearDropdown").val("0");
						               
						                isMaximized = true;
						               	  //alert('Called');
						               	 
						               	pendingPaymentsTable();
						               	toggleJoineelist();
						               	restorelistSize();
						               	
						               }  			
						
	/*-----------------  For Download the Excel of ongoing projects feature [ added by Anuj ] -------------------------------------------------------------*/
										function downloadOngoingProjects() {
											var encGroupId = document.getElementById('groupId').value;
											$.ajaxRequest
													.ajax({
														type : "POST",
														url : "/PMS/downloadOngoingProjects",
														data : "encGroupId=" + encGroupId,
														success : function(data) {
															if(data){
																downloadTemplate('Project Details.xlsx');
															}else{
																swal("File download Failed.!");
															}
														}});
										}
										
										function downloadTemplate(documentName){
											openWindowWithPost('POST','/PMS/downloadTemplate',{"template" : "EMPLOYEE_REG_TEMPLATE","documentTypeName":documentName},'_blank');
										}
										/*$(document).ready(function() {
											  // Function to store values from elements with class "underclos"
											  function storeValuesInList() {
											    var valuesList = []; // Initialize an empty array to store the values
											    $(".underclos").each(function() {
											      var value = $(this).text().trim(); // Get the text content and remove any leading/trailing spaces
											      valuesList.push(value); // Add the value to the array
											    });

											    // Here, you have the values stored in the JavaScript array 'valuesList'.
											    console.log(valuesList); // You can check the values in the browser console.
											    // You can perform any further actions with the values as needed.
											  }
											  
											  
												    storeValuesInList();
												  

											  
											});
*/
										//Bhavesh (16-08-23) For Download the Excel,pdf and print feature
										$(document).ready(function(){			
											var  table= $('#exampleUnderClosure').DataTable( {		
												destroy: true,
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
												
												
												
												$('#exampleUnderClosure .filters th[class="comboBox"]').each( function ( i ) {
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
										       $('#exampleUnderClosure .filters th[class="textBox"]').each( function () {                 
										           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

										       } );

										      	       
										       table.columns().eq( 0 ).each( function ( colIdx ) {
										           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
										               table
										                           .column( colIdx )
										                           .search( this.value )
										                           .draw() ;
										                   
										                } );
												} );


										      

										});
										
										//Bhavesh (16-08-23)
//------------------------------------------ Milestone Exceeded Deadlines filters [17-08-2023] -----------------------------------------
$(document)
		.ready(
				function() {
					var dateFormat = "dd/mm/yy", from = $(
							"#fromDate_MilestoneExceeded").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '0',
						/* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
				  	    minDate : '-10Y'
					}).on("change", function() {
						to.datepicker("option", "minDate", getDate(this));
					}), to = $("#toDate_MilestoneExceeded").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '0',
						/* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
				  	    minDate : '-10Y'
					}).on("change", function() {
						from.datepicker("option", "maxDate", getDate(this));
					});

					setMilestoneExceededtableProperties();
					setMilestoneDueInOneMonthProperties();

				});

//------------------------------------------ Milestone Exceeded Deadlines filter by clicking the GoButton [17-08-2023] ----------------------------
$('.milestoneExceeded_GoBtn').click(function() {
	var startRange = $("#fromDate_MilestoneExceeded").val();
	var endRange = $("#toDate_MilestoneExceeded").val();
	if (!startRange) {
		sweetSuccess('Attention', 'Please select Start Range');
		return false;
	}
	var selectedRange = startRange;
	if (endRange) {
		selectedRange = selectedRange + ' to ' + endRange;
	}

	$('#asOnDate').text(selectedRange);
	MilestonesTable(startRange, endRange);
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
//------------------------------------------ Set the DataTable Properties in Milestone Exceeded Deadlines Table [17-08-2023] ------------------
function setMilestoneExceededtableProperties()
{
	var  table= $('#milestoneExceededDeadlineTable').DataTable( {
		destroy: true,
		dom: 'Bfrtip',		       
        "ordering": false,
        "paging":   true,
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
		
		$('#milestoneExceededDeadlineTable .filters th[class="comboBox"]').each( function ( i ) {
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
       $('#milestoneExceededDeadlineTable .filters th[class="textBox"]').each( function () {                 
           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

       } );
       var table = $('#milestoneExceededDeadlineTable').DataTable();
       
       table.columns().eq( 0 ).each( function ( colIdx ) {
           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
               table
                           .column( colIdx )
                           .search( this.value )
                           .draw() ;
                   
                                           } );
           });	
}
//------------------------------------------ Set the DataTable Properties in Milestone Due In One Month Table [17-08-2023] ------------------
function setMilestoneDueInOneMonthProperties()
{
	var table1 = $('#milestoneDueInOneMonthTable').DataTable({
		dom : 'Bfrtip',
		"paging" : true,
		buttons : [ 'excel', 'pdf', 'print' ],
		"columnDefs" : [ {
			"orderable" : false,
			"targets" : 0
		} ],
		"order" : [ [ 1, 'asc' ] ]
	});

	table1.on('order.dt search.dt', function() {
		table1.column(0, {
			search : 'applied',
			order : 'applied'
		}).nodes().each(function(cell, i) {
			cell.innerHTML = i + 1;
			table1.cell(cell).invalidate('dom');
		});
	}).draw();

	$('#milestoneDueInOneMonthTable .filters th[class="comboBox"]').each( function ( i ) {
        var colIdx=$(this).index();
            var select = $('<select style="width:100%" ><option value="">All</option></select>')
                .appendTo( $(this).empty() )
                .on( 'change', function () {
                 var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );
                           
                 table1.column(colIdx)
                        .search( val ? '^'+val+'$' : '', true, false)
                        .draw();
                } );
     
            table1.column(colIdx).data().unique().sort().each( function ( d, j ) {
                select.append( '<option value="'+d+'">'+d+'</option>' )
            } );
        } );
	
	table1.columns().eq(0).each(
			function(colIdx) {
				$('input', $('.filters th')[colIdx]).on(
						'keyup change',
						function() {
							table1.column(colIdx).search(
									this.value).draw();

						});
			});
}

//------------------------------------------ Using Filter Symbols Calculate Dates in Milestone Exceeded Deadlines Table [17-08-2023] ------------------
function calculateMilestoneExceededDates() {
	var d = new Date();
	var symbol = $("#filterSymbol_milestoneExceeded").val();
	var selValue = $("#months_milestoneExceeded").val();

	if (selValue == 0) {

		$('#milestoneExceededDeadlineTable').empty();
		$('#milestoneExceededDeadlineTable').DataTable().destroy();
		var table = $('#milestoneExceededDeadlineTable').DataTable({
			destroy : true,
			dom : 'Bfrtip',
			"ordering" : false,
			"paging" : true,
			buttons : [ 'excel', 'pdf', 'print' ],
			dom : 'Bfrtip',
			"columnDefs" : [ {

				"orderable" : false,
				"targets" : 0
			} ],
			"order" : [ [ 1, 'asc' ] ]
		});

	} else {

		var lastMonth = new Date();
		lastMonth.setMonth(lastMonth.getMonth() - 1);
		var durationBefore = selValue;
		d.setMonth(d.getMonth() - durationBefore);
		var lastDayOfLastMonth = lastday(lastMonth.getFullYear(), lastMonth
				.getMonth());
		var fromMonth = d.getMonth() + 1;
		var toMonth = lastMonth.getMonth() + 1;
		fromMonth = fromMonth + "";
		toMonth = toMonth + "";
		lastDayOfLastMonth = lastDayOfLastMonth + "";

		if (fromMonth.length == 1) {
			fromMonth = "0" + fromMonth;
		}
		if (toMonth.length == 1) {
			toMonth = "0" + toMonth;
		}
		if (selValue != 0) {
			$("#fromDate_MilestoneExceeded").val(
					"01" + "/" + fromMonth + "/" + d.getFullYear());
			$("#toDate_MilestoneExceeded").val(
					lastDayOfLastMonth + "/" + toMonth + "/"
							+ lastMonth.getFullYear());

		} else {
			$("#fromDate_MilestoneExceeded").val("");
			$("#toDate_MilestoneExceeded").val("");

		}

		var compDate = "01" + "/" + fromMonth + "/" + d.getFullYear();
		getAllMilestoneExceededDeadlineTable(compDate, symbol);
	}
}

var lastday = function(y, m) {
	return new Date(y, m, 0).getDate();
}

//------------------------------------------ Get All Milestone Exceeded Deadlines Details By Symbol Filter [17-08-2023] ------------------
function getAllMilestoneExceededDeadlineTable(compDate, symbol) {
	$('#milestoneExceededDeadlineTable').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest
			.ajax({
				type : "POST",
				url : "/PMS/mst/MilestoneExceededDeadlineDetailByDates",
				data : {
					"compDate" : compDate,
					"symbol" : symbol
				},
				success : function(response) {
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						var strReferenceNumber = row.strProjectReference;
						if (!strReferenceNumber) {
							strReferenceNumber = '';
						}
						
						/*-----------  Get All Projects data from Closure and Pending for Closure [ 29-08-2023 ] ----------------------------------------*/
						var closureList = []; 
					    $(".closureList").each(function() {
					      var value = $(this).text().trim(); 
					      closureList.push(value); 
					    });
					    
					    var pendingClosureList = []; 
					    $(".pendingClosureList").each(function() {
					      var value = $(this).text().trim();
					      pendingClosureList.push(value); 
					    });
					    
					    var rowColor="";
					    if(closureList.includes(row.strProjectReference)){
					    	rowColor='golden-text';
					    }else{
					    	if(pendingClosureList.includes(row.strProjectReference)){
					    		rowColor='grey-text';
					    	}else{
					    		rowColor='success-text';
					    	}
					    }
					    /*----------- EOL [ 29-08-2023 ] ----------------------------------------*/
					    
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId + "')";
						tableData += '<tr class="'+rowColor+'"><td>'
								+ (i + 1)
								+ '</td><td class="font_14">'
								+ row.groupName
								+ '</td><td> <a class="font_14 '+rowColor+'" title="Click to View Complete Information" onclick="'
								+ clickFunction
								+ '"> '
								+ row.strProjectName
								+ ' </a> <br/> <span class="bold font_14 text-left '+rowColor+'">'
								+ strReferenceNumber + '</span> </td>';
						tableData += ' <td>' + row.milestoneName + '</td>';

						if (row.completionDate != null) {
							tableData += ' <td class=" text-center">'
									+ row.completionDate + '</td>';
						}
						if (row.expectedStartDate != null) {
							tableData += ' <td class=" text-center">'
									+ row.expectedStartDate + '</td>';
						}
						tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'
								+ row.encMilestsoneId
								+ '")>Review</a></td> </tr>';

					}

					$('#milestoneExceededDeadlineTable  tbody').append(tableData);
				
					setMilestoneExceededtableProperties();
				}
			});
}

//------------------------------------------ Get All Milestone Exceeded Deadlines Details By Start and End Date Filter [17-08-2023] ------------------
function MilestonesTable(startDate, endDate) {
	$('#milestoneExceededDeadlineTable').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest
			.ajax({
				type : "POST",
				url : "/PMS/mst/MilestoneExceededDeadlineDetailByDates",
				data : {
					"compDate" : startDate,
					"symbol" : endDate
				},
				success : function(response) {
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						var strReferenceNumber = row.strProjectReference;
						if (!strReferenceNumber) {
							strReferenceNumber = '';
						}
						
						/*-----------   Get All Projects data from Closure and Pending for Closure [ 29-08-2023 ] ----------------------------------------*/
						var closureList = []; 
					    $(".closureList").each(function() {
					      var value = $(this).text().trim(); 
					      closureList.push(value); 
					    });
					    
					    var pendingClosureList = []; 
					    $(".pendingClosureList").each(function() {
					      var value = $(this).text().trim();
					      pendingClosureList.push(value); 
					    });
					    
					    var rowColor="";
					    if(closureList.includes(row.strProjectReference)){
					    	rowColor='golden-text';
					    }else{
					    	if(pendingClosureList.includes(row.strProjectReference)){
					    		rowColor='grey-text';
					    	}else{
					    		rowColor='success-text';
					    	}
					    }
					    /*----------- EOL [ 29-08-2023 ] ----------------------------------------*/
						
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId + "')";
						tableData += '<tr class="'+rowColor+'"><td>'
								+ (i + 1)
								+ '</td><td class="font_14">'
								+ row.groupName
								+ '</td><td> <a class="font_14 '+rowColor+'" title="Click to View Complete Information" onclick="'
								+ clickFunction
								+ '"> '
								+ row.strProjectName
								+ ' </a> <br/> <span class="bold font_14 text-left '+rowColor+'">'
								+ strReferenceNumber + '</span> </td>';
						tableData += ' <td>' + row.milestoneName + '</td>';

						if (row.completionDate != null) {
							tableData += ' <td class=" text-center">'
									+ row.completionDate + '</td>';
						}
						if (row.expectedStartDate != null) {
							tableData += ' <td class=" text-center">'
									+ row.expectedStartDate + '</td>';
						}
						tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'
								+ row.encMilestsoneId
								+ '")>Review</a></td> </tr>';

					}

					$('#milestoneExceededDeadlineTable  tbody').append(
							tableData);

					setMilestoneExceededtableProperties();
				}
			});
}
//------------------------------------------ Reset the Milestone Exceeded Deadlines Details By Start and End Date Filter [17-08-2023] ------------------
function ResetTheMileStones() {
	$('#milestoneExceededDeadlineTable').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest
			.ajax({
				type : "POST",
				url : "/PMS/mst/getAllMilestoneExceededDeadlineDetailMaster",
				success : function(response) {
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						var strReferenceNumber = row.strProjectReference;
						if (!strReferenceNumber) {
							strReferenceNumber = '';
						}
						
						/*-----------   Get All Projects data from Closure and Pending for Closure [ 29-08-2023 ] ----------------------------------------*/
						var closureList = []; 
					    $(".closureList").each(function() {
					      var value = $(this).text().trim(); 
					      closureList.push(value); 
					    });
					    
					    var pendingClosureList = []; 
					    $(".pendingClosureList").each(function() {
					      var value = $(this).text().trim();
					      pendingClosureList.push(value); 
					    });
					    
					    var rowColor="";
					    if(closureList.includes(row.strProjectReference)){
					    	rowColor='golden-text';
					    }else{
					    	if(pendingClosureList.includes(row.strProjectReference)){
					    		rowColor='grey-text';
					    	}else{
					    		rowColor='success-text';
					    	}
					    }
					    /*----------- EOL [ 29-08-2023 ] ----------------------------------------*/
					    
						
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId + "')";

						tableData += '<tr class="'+rowColor+'"><td>'
								+ (i + 1)
								+ '</td><td>'
								+ row.groupName
								+ '</td><td> <a class="font_14 '+rowColor+'" title="Click to View Complete Information" onclick='
								+ clickFunction
								+ ' > '
								+ row.strProjectName
								+ ' </a> <br/> <span class="bold font_14 text-left '+rowColor+'">'
								+ strReferenceNumber + '</span> </td>';

						tableData += ' <td>' + row.milestoneName + '</td>';

						if (row.completionDate != null) {
							tableData += ' <td class=" text-center">'
									+ row.completionDate + '</td>';
						}
						if (row.expectedStartDate != null) {
							tableData += ' <td class=" text-center">'
									+ row.expectedStartDate + '</td>';
						}
						tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'
								+ row.encMilestsoneId
								+ '")>Review</a></td> </tr>';
					}
					$('#milestoneExceededDeadlineTable tbody')
							.append(tableData);

					setMilestoneExceededtableProperties();
				}

			});
}
//------------------------------------------ End of Milestone Exceeded Deadlines filters -----------------------------------------------------------------------

//----------------------------------------- Minimize and maximize and reset the table of milestone exceeded deadlines ------------
function restoreMilestoneExceeded() {
	$("#months_milestoneExceeded").val(0);
	$("#fromDate_MilestoneExceeded").val("");
	$("#toDate_MilestoneExceeded").val("");
	isMaximized = true;
	ResetTheMileStones();
	toggleMilestoneExceeded();
	restoreMilestoneExceededSize();

}
var isMaximized = false;
function toggleMilestoneExceeded() {
	var myModal = document.getElementById("milestoneExceededModal");

	if (isMaximized) {
		myModal.classList.remove("maximized");
		document.body.classList.remove("modal-open");
		document.getElementById('dash-milestone-exceededDeadline-modal').style.height = '';
		document.getElementById('dash-milestone-exceededDeadline-modal').style.width = '';
	} else {
		myModal.classList.add("maximized");
		document.body.classList.add("modal-open");

		var windowHeight = window.innerHeight
				|| document.documentElement.clientHeight
				|| document.body.clientHeight;
		var windowWidth = window.innerWidth
				|| document.documentElement.clientWidth
				|| document.body.clientWidth;
		var modal = document
				.getElementById('dash-milestone-exceededDeadline-modal');
		modal.style.height = (windowHeight * 0.75) + 'px';
		modal.style.width = (windowWidth * 0.98) + 'px';
		modal.style.transformOrigin = 'center';
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
function restoreMilestoneExceededSize() {
	var myModal = document.getElementById("milestoneExceededModal");
	myModal.classList.remove("maximized");
	document.body.classList.remove("modal-open");
	document.getElementById('dash-milestone-exceededDeadline-modal').style.height = '';
	document.getElementById('dash-milestone-exceededDeadline-modal').style.width = '';
}

//----------------------------------------- Minimize and maximize and reset the table of milestone due in one month ----------------------
function restoreMilestoneDueInOneMonth() {
	  $('#milestoneDueInOneMonthTable').dataTable().fnDestroy();
	  isMaximized = true;
	  setMilestoneDueInOneMonthProperties();
	  toggleMilestoneDueInOneMonth();
	  restoreMilestoneDueInOneMonthSize();

}
var isMaximized = false;
function toggleMilestoneDueInOneMonth() {
	var myModal = document.getElementById("milestoneDueInOneMonthModal");

	if (isMaximized) {
		myModal.classList.remove("maximized");
		document.body.classList.remove("modal-open");
		document.getElementById('dash-milestone-dueInOneMonth-modal').style.height = '';
		document.getElementById('dash-milestone-dueInOneMonth-modal').style.width = '';
	} else {
		myModal.classList.add("maximized");
		document.body.classList.add("modal-open");

		var windowHeight = window.innerHeight
				|| document.documentElement.clientHeight
				|| document.body.clientHeight;
		var windowWidth = window.innerWidth
				|| document.documentElement.clientWidth
				|| document.body.clientWidth;
		var modal = document
				.getElementById('dash-milestone-dueInOneMonth-modal');
		modal.style.height = (windowHeight * 0.75) + 'px';
		modal.style.width = (windowWidth * 0.98) + 'px';
		modal.style.transformOrigin = 'center';
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
function restoreMilestoneDueInOneMonthSize() {
	var myModal = document.getElementById("milestoneDueInOneMonthModal");
	myModal.classList.remove("maximized");
	document.body.classList.remove("modal-open");
	document.getElementById('dash-milestone-dueInOneMonth-modal').style.height = '';
	document.getElementById('dash-milestone-dueInOneMonth-modal').style.width = '';
}
//----------------------------------------- End of Minimize and maximize and reset the table of milestone exceeded deadlines and milestone due in one month ------------
/*-----------------------------------------  Closure Action Handling in Closure Request Tile Data [21-09-2023]    ----------------------------------------------------------------------*/
function startWorkFlow(applicationId, moduleTypeId, businessTypeId,projectTypeId, categoryId) {
	openWindowWithPost('POST', '/PMS/startProjectWorkflow', {
		"numId" : applicationId,
		"moduleTypeId" : moduleTypeId,
		"businessTypeId" : businessTypeId,
		"projectTypeId" : projectTypeId,
		"categoryId" : categoryId
	}, '_blank');
}

function viewAllowedActions(encCustomId) {
	/*--------------- For Write Remarks [30-11-2023] --------*/
	$('#proceedingModal').addClass('hidden');
	$('#proceedingModal').modal('show');
	$('#proceedingModal').modal('hide');
	$('#proceedingModal').removeClass('hidden');
	var encWorkflowId = $('#encWorkflowId_Closure').val();
	$.ajax({
				type : "POST",
				url : "/PMS/getNextRoleActionDetails",
				async : false,
				data : {
					"encCustomId" : encCustomId,
					"encWorkflowId" : encWorkflowId
				},
				success : function(res) {
					$('#' + encCustomId).find('li').remove();
					if (Array.isArray(res) && res.length > 0) {
						validateRecoSheetFile(encCustomId)
					    .then(function(result) {
					        var validate = result;
					        for(var i=0;i<res.length;i++){	
								
					        	if(res[i].numActionId == 30 && !validate){
									continue;
								}
								
								$('#' + encCustomId).append(
										"<li> <a class='font_14' onclick=performAction('"
												+ encCustomId + "','"
												+ res[i].strEncActionId + "',"
												+ res[i].isRemarksReq + ","
												+ res[i].numActionId + ") title='"
												+ res[i].strToolTip
												+ "'><span  aria-hidden='true'>"
												+ res[i].strActionName
												+ "</span></a></li>");
								
							}
					    })
					} else {
						$('#' + encCustomId)
								.append(
										"<li> <a class='font_14 red'><span  aria-hidden='true'> No Action </span></a></li>");
					}
				},
				error : function(e) {
				}
			});
}
function performAction(customId, encActionId, isRemarks, actionId) {
	var roleId = $("#roleId").val();
	var encWorkflowId = $('#encWorkflowId_Closure').val();
	var elements = document.getElementsByClassName(customId+"_1");
	if (actionId == 23) {
		window.scrollTo({
			top : 0,
			behavior : 'smooth'
		});
	}
	if (elements.length > 0) {
		var hodStatus = elements[0].value;
	}
	if ((hodStatus === 'false') && actionId === 27) {
		swal("Mapping of HOD is Pending.")
		return;
	}
	if (isRemarks == 0 && actionId!=30) {
		swal({
			title : "Do you want to submit?",
			buttons : [ 'No', 'Yes' ],
		}).then(
				function(isConfirm) {
					if (isConfirm) {
						doWorkAccrodingToAction(customId, encActionId,encWorkflowId, "", actionId);
					} else {
					}
				});
	}/*Added by Bhavesh on 01-05-24 to download Reco Sheet on Action Download*/
	else if(isRemarks==0 && actionId==30){
		downloadRecoSheetDocument(customId);
	}
	/*End of Condition*/
	
	else if (isRemarks == 1 && actionId == 23 && roleId == 7) {
		$('#financialApprovalModel').modal('show');
		$('#encActionId').val(encActionId);
		$('#customId').val(customId);
		return false;
	} else if (isRemarks == 1 && actionId != 23) {
		var textarea = document.createElement('textarea');
		textarea.rows = 6;
		textarea.className = 'swal-content__textarea';
		textarea.id = 'textArea';
		textarea.placeholder = 'You can write remarks';

		swal({
			title : 'Do you want to submit?',
			content : textarea,
			buttons : [ 'No', 'Yes' ],
		  closeOnClickOutside: false // For Prevent the Outside click [05-06-2024]
		}).then(function(isConfirm) {
							if (isConfirm) {
								var textAreaValue = $("#textArea").val();
								textAreaValue = textAreaValue.trim();
								/*------ Increase the text area length from 300 to 1000 [05-06-2024] ------*/
								if (textAreaValue.length < 1000 || textAreaValue.length == 1000) {
									doWorkAccrodingToAction(customId,encActionId, encWorkflowId,textAreaValue);
								} else {
									swal("Remarks should be less than or equals to 1000 characters.");
								}
							}
						});
	} else {
		doWorkAccrodingToAction(customId, encActionId, encWorkflowId,"",actionId);
	}
}

function doWorkAccrodingToAction(encCustomId, encActionId, encWorkflowId,strRemarks, actionId) {
	if (actionId == 21) {
		viewProjectClosureDetails(encCustomId);
		return false;
	} else if (actionId == 25) {
		openWindowWithPost('GET', '/PMS/mst/projectClosure', {
			"encProjectId" : encCustomId
		}, '_self');
		return false;
	} else if (actionId == 19) {
		$.ajax({
			type : "POST",
			url : "/PMS/loadTransactionDetails",
			async : false,
			data : {
				"encCustomId" : encCustomId,
				"encWorkflowId" : encWorkflowId
			},
			success : function(res) {
				var tableData = '';
				for (var i = 0; i < res.length; i++) {
					var rowData = res[i];
					var remarks;

					if (!rowData.strRemarks) {
						remarks = '';
					} else {
						remarks = rowData.strRemarks;
					}
					tableData += "<tr> <td>" + (i + 1) + "</td><td>"
							+ rowData.employeeName + "</td> ";
					tableData += "<td>" + rowData.strActionPerformed
							+ "</td> <td>" + remarks + "</td><td>"
							+ rowData.transactionAt.split(' ')[0] + "</td>";
					tableData += "</tr>";
				}
				$('#proceedingTbl > tbody').html('');
				$('#proceedingTbl').append(tableData);
			},
		})
		$('#proceedingModal').modal('show');
		return false;
	}
	$.ajax({
		type : "POST",
		url : "/PMS/doWorkAccrodingToAction",
		async : false,
		data : {
			"encCustomId" : encCustomId,
			"strEncActionId" : encActionId,
			"encWorkflowId" : encWorkflowId,
			"strRemarks" : strRemarks
		},
		success : function(res) {
			if (res.strSuccessMsg != '' && res.strSuccessMsg != 'error') {
				swal({
					title : "",
					text : res.strSuccessMsg,
					showCancelButton : false,
					confirmButtonColor : "#DD6B55",
					confirmButtonColor : "#34A534",
					confirmButtonText : "Ok",
					cancelButtonText : "Cancel",
					closeOnConfirm : true,
					closeOnCancel : true
				}).then(
						function(isConfirm) {
							if (isConfirm) {
								/*openWindowWithPost('GET',
										'/PMS/dashboard',
										'_self');*/
								/*window.location.href = '/PMS/dashboard';
							    $(window).on('load', function () {
							        $('#dash-closure-pending-modal1').modal('show');
							    });*/
								getClosureRequestTileData();//Added by devesh on 13-12-23 to update table after action
							}
						});
			} else if (res.strSuccessMsg != 'error')
				swal("There are some problem.Please contact to Admin.");
		},
		error : function(e) {
		}
	});
}
function viewProjectClosureDetails(encCustomId){
	$('#viewProjectClosure').modal('show');
	$.ajax({
		type : "POST",
		url : "/PMS/mst/viewProjectClosureDetailFromDashboard/"+encCustomId,	
		dataType:"json",
		success : function(response) {
			var TeamDetailTable="";
			for(var i=0;i<response.teamDetails.length;i++)
			{
				var teamMember="";
				teamMember+="<tr><td>"+response.teamDetails[i].strEmpName+"</td>";
				teamMember+="<td>"+response.teamDetails[i].strRoleName+"</td><td class='startDate' id='startDate_${loop.index}'>"+response.teamDetails[i].strStartDate+"</td>";
				teamMember+="<td id='end_"+i+"'>"+response.teamDetails[i].strEndDate+"</td></tr>";
				TeamDetailTable+=teamMember;
			}
			$('#teamDetailsTable').html('');
			$('#teamDetailsTable').append(TeamDetailTable);
			
			
			$('#closureDate').text(response.projectTempMasterDetail.closureDate);
			$('#closureRemark').text(response.projectTempMasterDetail.closureRemark);
			$('#projectReferenceNumber').text(response.projectDetails.projectRefNo);
			$('#projectStrProjectName').text(response.projectDetails.strProjectName);
			$('#projectStartDate').text(response.projectDetails.startDate);
			$('#projectEndDate').text(response.projectDetails.endDate);
			
			
			var documentsDetails="";
			if(response.details.length>0){
				for (var i = 0; i < response.details.length; i++) {
				    var doc = response.details[i];
				    var docRow = "<tr>";
				    docRow+="<td>"+(i+1)+"</td><td><p class='bold'>"+response.details[i].documentTypeName+"</p></td>";
				    docRow+="<td align='center'>";
					for(var k=0;k<doc.detailsModels.length;k++)
					{
						var detailsModel = doc.detailsModels[k];
						docRow+="<span> <a onclick=downloadTempDocument('"+detailsModel.encNumId+"')>"+detailsModel.icon+"</a></span>";
					}
					docRow+="</td>";
				    docRow += "</tr>";
				    documentsDetails += docRow;
				}
			}else{
				documentsDetails+="<tr><td class='text-center' colspan='3'>No Document Found.</td></tr>";
			}
			$('#documentDetailsTable').html('');
			$('#documentDetailsTable').append(documentsDetails);
		}, error: function(data) {
	 	  
	   }
	})
}
function downloadTempDocument(documentId){
	openWindowWithPost('POST','/PMS/mst/downloadTempProjectFile',{"encNumId" : documentId},'_blank');
}
/*-----------------------------------------  EOL Closure Action Handling in Closure Request Tile Data ----------------------------------------------------------------------*/
$(document).ready(function() {
	// set financial closure pending table properties while the page load [22-09-2023] ------------------------
	getClosureRequestTileData();//Added by devesh on 13-10-23 to load table on document ready
	FinancialClosurePendingProjectsTable();//Added by devesh on 12-10-23 to load table on document ready
	 const today = new Date();
     const year = today.getFullYear();
     const firstDayOfYear = new Date(year, 0, 1);
         const dd = String(firstDayOfYear.getDate()).padStart(2, '0');
     const mm = String(firstDayOfYear.getMonth() + 1).padStart(2, '0'); // January is 0
     const yyyy = firstDayOfYear.getFullYear();
      const fromDate = dd + "/" + mm + "/" + yyyy;
	  $('#asOnValidatedProjects').text(fromDate);
	validatedProjectsTable();// added by varun on 06-12-2023 to load table 
});
/*----------------- Adding the DataTable feature in the closure request table data [21-09-2023] -------------------------------------------------------------*/
function setTablePropertiesForClosureRequestTile(){				        
		var table= $('#exampleUnderClosure').DataTable( {
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
    });
		
		table.on('order.dt search.dt', function () {
			table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
		           cell.innerHTML = i+1;
		           table.cell(cell).invalidate('dom');
		     });
		}).draw();

		$('#exampleUnderClosure .filters th[class="comboBox"]').each( function ( i ) {
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


       // DataTable
       var table = $('#exampleUnderClosure').DataTable();
       
       table.columns().eq( 0 ).each( function ( colIdx ) {
           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
               table
                           .column( colIdx )
                           .search( this.value )
                           .draw() ;
                   
                                           } );
       			});	                     
	}
/*----------------- EOF Adding the DataTable feature in the closure request table data ------------------------------------------------------------*/
//Bhavesh(22-09-23) toggle for resize in financial closure pending tile
var isMaximized3 = false;
function togglePending3() {
	  var myModal = document.getElementById("myPending3");
	  if (isMaximized3) {
	    myModal.classList.remove("maximized");
	    document.body.classList.remove("modal-open");
	    document.getElementById('dash-closure-pending-modal3').style.height = '';
	    document.getElementById('dash-closure-pending-modal3').style.width = '';
	  } else {
	    myModal.classList.add("maximized");
	    document.body.classList.add("modal-open");
	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
	    var modal = document.getElementById('dash-closure-pending-modal3');
	    modal.style.height = (windowHeight * 0.75) + 'px'; 
	    modal.style.width = (windowWidth * 0.98) + 'px'; 
	    modal.style.transformOrigin = 'center';
	  }

	  isMaximized3 = !isMaximized3; 
	  var btn = document.getElementsByClassName('close-btn')[0];
	  var icon = btn.getElementsByTagName('i')[0];
	  
	  if (icon.classList.contains('fa-window-maximize')) {
	    icon.classList.remove('fa-window-maximize');
	    icon.classList.add('fa-window-restore');
	  } else {
	    icon.classList.remove('fa-window-restore');
	    icon.classList.add('fa-window-maximize');
	  }
}
function restorePendingSize3() {
	  var myModal = document.getElementById("myPending3");
	  myModal.classList.remove("maximized");
	  document.body.classList.remove("modal-open");
	  document.getElementById('dash-closure-pending-modal3').style.height = '';
	  document.getElementById('dash-closure-pending-modal3').style.width = '';
}
function restorepending3(){
	   isMaximized3 = true;
		togglePending3();
		restorePendingSize3();
		//------ reset the properties of closed table [12-10-2023] ------*/
		$("#fromFinanceClosurePending").val("");
	  	$("#toFinanceClosurePending").val("");
	  	$("#asOnDateFinanceClosurePendingProjects").text("");
	  	$('.financeClosurePending').val(0);
	 	FinancialClosurePendingProjectsTable();//Added by devesh on 13-10-23 to load table on modal close	
	 	//------ reset the properties of closed table [12-10-2023] ------*/
}
//Bhavesh(22-09-23)
/*----------------- Adding the DataTable feature in the financial closure pending table data [22-09-2023] -------------------------------------------------------------*/
function setUnderClosureFinanceTableProperties(){				        
		var table= $('#exampleUnderClosureFinance').DataTable( {
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
		$('#exampleUnderClosureFinance .filters th[class="comboBox"]').each( function ( i ) {
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
/*----------------- EOF Adding the DataTable feature in the financial closure pending table data ------------------------------------------------------------*/

//Added by devesh on 27-09-23 for redirecting ongoing projects from group name
function closeModalAndClickButton(groupName) {
    // Close the modal (you may need to adjust this part based on your modal implementation)
	var modalParent = document.getElementById('dash-grpwise-bargraph-modal');
    var closeButton = modalParent.querySelector('.close');
    if (closeButton) {
        closeButton.click();
    }

    window.scrollBy(0,1400);
    var groupButtonMappings = {
            'Education & Training': 'E&T',
            'e-Governance': 'e-Gov',
            'Embedded Systems': 'Embedded',
            'Health Informatics - I': 'HI - I',
            'Health Informatics - II': 'HI - II',
            'Speech & Natural Language Processing': 'NLP',
            'Data Centre & Information Services': 'DC',
            'PMO and SQA': 'PMO'
        };

        // If the groupName is in the mapping, find and click the corresponding button
        if (groupButtonMappings[groupName]) {
            var buttons = document.querySelectorAll('.btn1');
            for (var i = 0; i < buttons.length; i++) {
                if (buttons[i].innerText === groupButtonMappings[groupName]) {
                    buttons[i].click();
                    break; // Stop searching after clicking the matching button
                }
            }
        }
}
//End of redirecting to ongoing projects

//Added by devesh on 12-10-23 to update tiles count after a given interval
function getClosureRequestTileData()
{
	$('#exampleUnderClosure').DataTable().clear().destroy();
	$.ajax({
		type : "POST",
		url : "/PMS/mst/getClosureRequestTileDataPMO",	
		dataType:"json",
		success : function(response) {
			var tableContent="";
			$('#ClosureRequestCount').text(response.underClosureProjectCount);
			$('#TechnicalClosurePendingCount').text(response.TechnicalClosurePendingCount);
			
			if(response.underClosureCountPMO && response.underClosureCountPMO.length>0){
			for(var i=0;i<response.underClosureCountPMO.length;i++)
			{
				var row = response.underClosureCountPMO[i];
				var hodstatus = response.HODStatus[i];
				var projectLink = "/PMS/projectDetails/" + row.encProjectId;
				tableContent+="<tr class='golden-text'><td class='golden-text text-center'>"+(i+1)+"</td><td>"+row.strGroupName+"</td><td class='font_16'><a title='Click to View Complete Information' onclick='viewProjectDetails(\"" + projectLink + "\")'><span class='golden-text'>"
							+row.strProjectName+"</span></a></td><td class='font_16' id=td2_"+row.encProjectId+"'>"+row.clientName+"</td>";
				tableContent+="<td style='width: 10%;'><span class='font_16'>"+row.strPLName+"</span></td>";
				tableContent+="<td  class='golden-text' style='width:5%;'>"+row.startDate+"</td>";
				tableContent+="<td  class='golden-text' style='width:5%;'>"+row.endDate+"</td>";
				tableContent+="<td  class='golden-text'>"+row.workflowModel.strActionPerformed+" by <b>"+row.workflowModel.employeeName+"</b> at "+row.workflowModel.transactionAt+"</td>";							
				tableContent+="<td  class='golden-text text-center'>"+row.closureDate+"</td>";
				/*----------- Get Remarks from WorkflowModel Model [06-12-2023] ---------*/
				//tableContent+="<td  class='golden-text'>"+row.strProjectRemarks+"</td>";
				if(row.workflowModel.strRemarks==null || row.workflowModel.strRemarks==''){
					tableContent+="<td></td>";
				}else{
					tableContent+="<td>"+row.workflowModel.strRemarks+"</td>";	
				}
				tableContent+="<td><div class='dropdown text-center'>";																	
                tableContent += "<a class='btn btn-warning dropdown-toggle' data-toggle='dropdown' onclick='viewAllowedActions(\"" + row.encProjectId + "\")' aria-haspopup='true' aria-expanded='false'>";
                tableContent += "<i class='icon-th-large icon-1halfx blue pad-top' aria-hidden='true'></i></a>";
				tableContent+="<ul class='dropdown-menu pull-right' aria-labelledby='dropdownMenuLink' id="+row.encProjectId+"></ul></div>";	
				tableContent+="<input type='text' class='"+row.encProjectId+"_1 hidden' value='"+hodstatus+"' readonly />";
				tableContent+="</td>";											
				tableContent+="</tr>";
			}

			}/*else{
				tableContent+="<tr><td colspan='11' class='text-center'>No data available in table</td></tr>";
			}*/
			$('#ClosureRequestTableList').append(tableContent);
			setTablePropertiesForClosureRequestTile();	

            $('#closureListID').empty();
            $.each(response.closureData, function(index,item ) {
                var element = $('<p>').addClass('closureList hidden').text(item.projectRefrenceNo);
                $('#closureListID').append(element);
            });
		}, error: function(data) {
	 	  
	   }
	})	
}

function reloadPage() {
	updateCounts();
	$('#dash-closure-pending-modal').off('shown.bs.modal').on('shown.bs.modal', function(event) {		
		pendingClosuresTable();
        $(this).off('shown.bs.modal');
    });
	$('#dash-newProposalList-modal').off('shown.bs.modal').on('shown.bs.modal', function(event) {		
		newProposalsTable();
		projectsReceivedTable();  // call the project received function [08-02-2024]
        $(this).off('shown.bs.modal');
    });
	$('#dash-newProjectList-modal').off('shown.bs.modal').on('shown.bs.modal', function(event) {		
		newProjectsTable();
        $(this).off('shown.bs.modal');
    });
	$('#dash-closure-pending-modal2').off('shown.bs.modal').on('shown.bs.modal', function(event) {		
		getClosureRequestTileData();
        $(this).off('shown.bs.modal');
    });
	$('#dash-closure-pending-modal3').off('shown.bs.modal').on('shown.bs.modal', function(event) {		
		FinancialClosurePendingProjectsTable(); //12-10-2023
        $(this).off('shown.bs.modal');
    });
}

// Set the interval (in milliseconds) for page reload
var reloadInterval = 30000; // 5 seconds

// Set up the interval to reload the page
var intervalId = setInterval(reloadPage, reloadInterval);

function updateCounts() {
	  $.ajax({
	    url: "/PMS/mst/UpdateCounts", // The endpoint you created
	    method: "POST",
	    dataType: "json",
	    cache: false,
	    success: function(data) {
	      // Update count values on the tiles
	      $('#newProposalsOnDate').text(data.proposalListCount+" / "+data.proposalReceivedCount);
	      $('#TechnicalClosurePendingCount').text(data.pendingClosureCount);
	      $('#FinancialPendingCount').text(data.FinancialPendingProjectDetails);
	      $('#newProjectsOnDate').text(data.newProjectCount);
	      $('#ClosureRequestCount').text(data.underClosureProjectCount);
	    },
	    error: function(error) {
	      console.error('Error fetching count data:', error);
	    }
	  });
	}

//End of updation

// Function to populate the table body with an AJAX call added by Devesh on 13-10-23
function populateFinancialTableBody() {
	if ($.fn.DataTable.isDataTable('#exampleUnderClosureFinance')) {
        $('#exampleUnderClosureFinance').DataTable().clear().destroy();
    }
    // Make an AJAX call to fetch data using POST method
    $.ajax({
      url: "/PMS/getFinancialPendingList",
      method: "POST",
      data: {/* Include any data you need to send with the request */},
      success: function (data) {
        var tableBody = "";
        
        for (var i = 0; i < data.length; i++) {
          var projectdetail = data[i];

        tableBody += '<tr class="brown-text">';
        tableBody += '<td style="width: 2%">' + (i + 1) + '</td>';
        tableBody += '<td class="brown-text">' + projectdetail.strGroupName + '</td>';
        tableBody += '<td class="font_16 brown-text" style="width: 15%;" id="td2_' + projectdetail.encProjectId + '">';
        tableBody += '<p><a class="bold" title="Click to View Complete Information" onclick="viewProjectDetails(\'/PMS/projectDetails/' + projectdetail.encProjectId + '\')">';
        tableBody += '<span class="brown-text">' + projectdetail.strProjectName + '</span></a></p></td>';
        tableBody += '<td class="font_16 brown-text" style="width: 15%;" id="td2_' + projectdetail.encProjectId + '">';
        tableBody += '<p class="font_14">' + projectdetail.clientName + '</p></td>';
        tableBody += '<td style="width: 10%"><span class="font_16">' + projectdetail.strPLName + '</span></td>';
        tableBody += '<td class="brown-text" style="width: 5%">' + projectdetail.startDate + '</td>';
        tableBody += '<td class="brown-text" style="width: 5%">' + projectdetail.endDate + '</td>';
        tableBody += '<td class="brown-text" align="center">' + projectdetail.projectDuration + '</td>';
        tableBody += '<td class="brown-text" align="left"><span>' + projectdetail.strTotalCost + '</span></td>';
        tableBody += '<td class="brown-text"><span>' + projectdetail.numReceivedAmountInr + '</span></td>';
        tableBody += '<td class="brown-text">' + projectdetail.numReceivedAmountTemp + '</td></tr>';
      }

      // Append the table body to the container
      document.getElementById('financialTableBody').innerHTML = tableBody;
      setUnderClosureFinanceTableProperties();
    },
    error: function () {
      console.log("Error fetching data from the server.");
    }
  });
}
//End of Financial Table

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
/*------------------- Get the Finance Closure Pending data in onload and by filters [12-10-2023] ---------------------------------*/
$( function() {
	  var dateFormat = "dd/mm/yy",
	    from = $( "#fromFinanceClosurePending" )
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
	        $('.financeClosurePending').val(0);  //09-10-2023
	      }),
	    to = $( "#toFinanceClosurePending" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	       /* Bhavesh (18-10-23) extend the min date year to 8 Year from minDate : '-5Y'*/
	  	    minDate : '-10Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	      $('#months').val(0);
	    });
	  
	  $('.go-btn-FinanceClosurePending').click(function() {
		  var startRange = $("#fromFinanceClosurePending").val();
		  var endRange = $("#toFinanceClosurePending").val();
		  if(!startRange){
			  sweetSuccess('Attention','Please select Start Range');
			  return false;
		  }
		  var selectedRange = startRange;
		  if(endRange){
			  selectedRange = selectedRange +' to '+endRange;
		  }
		  $('#asOnDateFinanceClosurePendingProjects').text(selectedRange);
		  FinancialClosurePendingProjectsTable();
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
function FinancialClosurePendingProjectsTable(){
    $('#exampleUnderClosureFinance').DataTable().clear().destroy();
	var startDate = null;
	var endDate = null;
	var selectedDateRange = $('#asOnDateFinanceClosurePendingProjects').text().trim();
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
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getAllFinancialClosurePendingProjectByDate",
		data:{
			"startDate":startDate,
			"endDate":endDate
		},
		success : function(response) {
				var tableData = "";
				for (var i = 0; i < response.length; i++) {
				    var row = response[i];
				    var rowColor = "";
	
				    if (row.rowColor === '' || row.rowColor === null) {
				        rowColor = "#993300";
				    } else {
				        rowColor = row.rowColor;
				    }
				    tableData += "<tr style='color:" + rowColor + "'>";
				    tableData += "<td style='width: 2%;'>" + (i + 1) + "</td>";
				    tableData += "<td>" + row.strGroupName + "</td>";
				    tableData += "<td class='font_16' style='width:15%;' id='td2_" + row.encProjectId + "'><p>";
				    tableData += "<a class='bold' title='Click to View Complete Information' onclick='viewProjectDetails(\"/PMS/projectDetails/" + row.encProjectId + "\")'>";
				    tableData += "<span style='color:" + rowColor + "'>" + row.strProjectName + "</span></a></p>";
				    tableData += "</td>";
				    tableData += "<td class='font_16' style='width:15%;' id='td2_" + row.encProjectId + "'><p class='font_14'>" + row.clientName + "</p></td>";
				    tableData += "<td style='width: 10%;'><span class='font_16'>" + row.strPLName + "</span></td>";
				    tableData += "<td style='width:5%;'>" + row.startDate + "</td>";
				    if (row.endDate === '' || row.endDate === null) {
				    	tableData += "<td style='width:5%;'></td>";
				    } else {
				    	tableData += "<td style='width:5%;'>" + row.endDate + "</td>";
				    }
				    tableData += "<td align='center'>" + row.projectDuration + "</td>";
				    tableData += "<td align='left'><span>" + row.strTotalCost + "</span></td>";
				    tableData += "<td><span>" + row.numReceivedAmountInr + "</span></td>";
				    tableData += "<td>" + row.numReceivedAmountTemp + "</td>";
				    //Bhavesh(01=05-2024) action for pending at finance
				    tableData+="<td><div class='dropdown text-center'>";																	
	                tableData += "<a class='btn btn-warning dropdown-toggle' data-toggle='dropdown' onclick='viewAllowedActions(\"" + row.encProjectId + "\")' aria-haspopup='true' aria-expanded='false'>";
	                tableData += "<i class='icon-th-large icon-1halfx  pad-top ' aria-hidden='true'></i></a>";
					tableData+="<ul class='dropdown-menu pull-right' aria-labelledby='dropdownMenuLink' id="+row.encProjectId+"></ul></div>";	
					tableData+="<input type='text' class='"+row.encProjectId+"_1 hidden' value='' readonly />";
					tableData+="</td>";

				    tableData += "</tr>";
				}
			$('#exampleUnderClosureFinance').append(tableData);
			setUnderClosureFinanceTableProperties();
		}});
}
/*------------------- End of Get the Finance Closure Pending data in onload and by filters [12-10-2023] ---------------------------------*/

/*---------- Function for get the project received data where project start date is given if not the current year data will display [08-02-2024] ----*/
function projectsReceivedTable(){
	var groupColumn = 1;
	var startDate = null;
	var endDate = null;
	
	var selectedDateRange = $('#asOnDateProposals').text().trim();

	$('#asOnDateProposals1').text(selectedDateRange);
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
		$('#newProjectReceived_dataTable').DataTable().clear().destroy();
		var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getActiveProjects",
			data:{
				"startDate":startDate,
				"endDate":endDate
			},
			success : function(response) {
				if(response){
					$('#projectProposalCount').text(response.length); 
				}else{
					$('#projectProposalCount').text(0); 
				}
				
				var totalAmount = 0;
				var totalAmountoutlay;
				var totalConvertedToProjCost=0;
				var convertedCount=0;
				var numerictotaloutlay =0;
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
                    totalAmountoutlay =row.strProposalTotalCost;
					numerictotaloutlay += parseFloat(totalAmountoutlay.replace(/[^0-9.-]/g, ""));

					var rowColor="";
					var strReferenceNumber=row.strProjectRefNo;
					if(!strReferenceNumber){
						strReferenceNumber='';
						rowColor="#800080";
					}else{
						rowColor="#1578c2";
					}

					tableData += '<tr style="color:'+rowColor+'"><td class="font_14">'+ row.groupName +  '</td><td><a style="color:'+rowColor+'" class="font_14" onclick="viewProjectDetails(\'/PMS/mst/proposalDetails/' + row.encApplicationId + '\')">'+ row.proposalTitle +'</a></td> ';
					var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
					if(!strReferenceNumber){
						tableData += '<td> <a class="font_14" style="color:#800080" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.strProjectName + ' </a><p class="bold blue font_14 text-left">'+ strReferenceNumber+'</p></td>';
					}else{
						tableData += '<td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.strProjectName + ' </a><p class="bold blue font_14 text-left" style="color:#1578c2">'+ strReferenceNumber+'</p></td>';
					}
					tableData += ' <td><p class="font_14"><i>'+row.strClientName+'</i></p></td> ';
					tableData += ' <td class="font_14 text-right">'+ row.strProjectWorkOrderDate +  '</td> ';
					tableData += ' <td class="font_14 text-right">'+ row.duration +  ' months </td> ';
								
					tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encApplicationId+'" value='+row.encApplicationId+' /> <span id="projectCost_'+row.encApplicationId+'">'
					+ row.strProjectCost + '</span></td>';
	                tableData += ' <td class="font_14 text-right"> <input type="hidden" id="hiddenProjectCost_'+row.encApplicationId+'" value='+row.encApplicationId+' /> <span id="projectCost_'+row.encApplicationId+'">'
						+ row.strProposalTotalCost + '</span></td></td> </tr>';

					totalAmount += row.numProposalAmountLakhs;
					totalConvertedToProjCost += row.totalProjectCost;
					convertedCount+=row.numCountOfConverted;		
				}
		
				$('#newProjectReceived_dataTable').append(tableData); 

				if(totalConvertedToProjCost>0){
					$("#projRec").show();
					var amt2=convertedCount +" " + " ( " + convertAmountToINR(totalConvertedToProjCost)+ " Lakhs "+ " )";
					$("#totalConvCost").text(amt2);
				}else{	
					$("#projRec").hide();
				}
				
				var  table= $('#newProjectReceived_dataTable').DataTable( {
				  dom: 'Bfrtip',	
				  buttons: [
				             'excel', 'pdf', 'print'
				        ],
				        "columnDefs": [{
	                         "type": "datetime-moment",
	                         "targets": 2, 
	                         "render": function (data, type, row) {
	                           if (type === "sort") {
	                             return moment(data, "DD/MM/YYYY").format("YYYY-MM-DD");
	                           }
	                           return data;
	                         }
                       }],
				       "order": [[3, "desc"]]
				});
			
				$('#newProjectReceived_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
			                 
				                 // call the below function 
				                 getSelectedGroupProposalValues(table, colIdx);
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        });
				
				function getSelectedGroupProposalValues(table, colIdx) {
				    var values = [];
				    table.rows({ search: 'applied' }).every(function () {
				        var data = this.data();
				        console.log("sad:"+data);
				        values.push(data[5]+"%"+data[6]);
				    });
				    performSelectedGroupFilter(values);
				    return values;
				}
				function performSelectedGroupFilter(values) {
				    var totalProjects = values.length;
				    var noCount = 0;
				    var totalCostInLakhs = 0;
				    var totalCostInLakhsC = 0;
				    for (var i = 0; i < values.length; i++) {
				        var parts = values[i].split('%');
				        if (parts.length === 2) {
				            if (parts[0].trim() === "No") {
				                noCount++;
				                var costMatch11 = parts[1].split('&nbsp;');
				                var costMatch12 = costMatch11[1].split('Lakhs');
				                if (costMatch12) {
				                    totalCostInLakhsC += parseFloat(costMatch12[0]);
				                }
				            }
				            var costMatch = parts[1].match(/₹\s*([\d.]+)\s*Lakhs/);
				            var costMatch1 = parts[1].split('&nbsp;');
				            var costMatch2 = costMatch1[1].split('Lakhs');
				            if (costMatch2) {
				                totalCostInLakhs += parseFloat(costMatch2[0]);
				            }
				        }
				    }
				    var rCount = totalProjects-noCount;
				    var rCost = totalCostInLakhs-totalCostInLakhsC;
				    var amt_2=rCount +" " + " ( " + convertAmountToINR(rCost)+ " Lakhs "+ " )";		    
				    $('#totalConvCost').text(amt_2);
				}

		       $('#newProjectReceived_dataTable .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       });
		       table.columns().eq( 0 ).each( function ( colIdx ) {
		           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
		               table
                           .column( colIdx )
                           .search( this.value )
                           .draw() ;          
		           });
		       });
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});		
	}
}
/*------- End of Project Received function [08-02-2024] ----*/

//Bhavesh(1-03-2024) for Oustanding Payments
var oustandingcurrentYear = new Date().getFullYear();
var oustandingyearDropdown = $("#outstandingcalendarYearDropdown");
for (var i = 0; i < 5; i++) {
  var year = oustandingcurrentYear - i;
  oustandingyearDropdown.append('<option value="' + year + '">' + year + '</option>');
} 
function getYearprojectRangeForOutstanding() {
	 
	  $(".months").val(0) ;
	    var selectedYear = parseInt(oustandingyearDropdown.val());

	    if (selectedYear > 0) {
	      var firstDate = new Date(selectedYear, 0, 1); // January 1st
	      var lastDate = new Date(selectedYear, 11, 31); // December 31st
       
	     
	     
	      var formattedFromDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
	      var formattedToDate = $.datepicker.formatDate('dd/mm/yy', lastDate);
	      
	     
	    
	  	$("#monthsInvoice").val("0"); 
	      pendingPaymentsTableWithInvoiceYear(formattedFromDate,formattedToDate );
	  } else {
	      
		  $('#pendingPayments_dataTable').empty();
			$('#pendingPayments_dataTable').DataTable().destroy();
			var foot = $("#pendingPayments_dataTable").find('tfoot');
			 if(foot.length){
				 $("#pendingPayments_dataTable tfoot").remove();
			 }
			var  table= $('#pendingPayments_dataTable').DataTable( {
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
	    
	   

	  }


function pendingPaymentsTableWithInvoiceYear(invoiceFromDate,invoiceToDate){
	
	var selectedDateRange = invoiceFromDate+" to "+invoiceToDate;
	$('#asOnDatePendingPayments1').text(selectedDateRange);
	 $('#pendingPayments_dataTable').DataTable().clear().destroy();
  
  
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getPendingPaymentsDetailsByInvoiceYear",
		data : {"invoiceFromDate":invoiceFromDate,
				"invoiceToDate":invoiceToDate
				},
		success : function(response) {
			//console.log(response);
			var totalInvoiceAmt=0;
			var totalTaxAmt = 0;
			var totalAmt = 0;
			
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
				 totalInvoiceAmt += row.numInvoiceAmt;
				 totalTaxAmt += row.numTaxAmount;
				 totalAmt +=row.numInvoiceTotalAmt;
				var strInvoiceStatus= row.strInvoiceStatus;
				if(row.strInvoiceStatus==null){
					strInvoiceStatus='';
				}
				var strReferenceNumber=row.strReferenceNumber;
				if(!strReferenceNumber){
					strReferenceNumber='';
				}
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
			
				tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.strGroupName +  '</td> <td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
						+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';
				
				tableData += '<td class="font_14 orange">'+row.strClientName+'</td><td class="font_14 ">'
				+ row.strInvoiceRefno +'</td>';
				
				tableData += ' <td class="font_14">'+ row.strInvoiceDate+ '</td>';
				
				tableData += ' <td class="font_14">'+ strInvoiceStatus+  '</td>';
				
				tableData += ' <td class="font_14 text-right">'	+ row.strInvoiceAmt +'</td>';
				
				tableData += ' <td class="font_14 text-right">'	+ row.strTaxAmount + '</td>';
				
				tableData += ' <td class="font_14 text-right">'+ row.strInvoiceTotalAmt+ '</td> </tr>';			
				
			}		
		
			$('#pendingPayments_dataTable').append(tableData); 
			var foot = $("#pendingPayments_dataTable").find('tfoot');
			 if(foot.length){
				 $("#pendingPayments_dataTable tfoot").remove();
			 }
			
			if(totalInvoiceAmt >0){
							
				 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#pendingPayments_dataTable"); 
				    foot.append('<tr><td></td> <td></td><td></td><td></td> <td></td><td></td> <td class="bold text-right">Total</td><td class="bold text-right totalinvoice">'+convertAmountToINR(totalInvoiceAmt)+'</td> <td class="bold text-right totaltax">'+convertAmountToINR(totalTaxAmt)+'</td> <td class="bold text-right totalAmount">'+convertAmountToINR(totalAmt)+'</td></tr>');
			}          
			var  table= $('#pendingPayments_dataTable').DataTable( {
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
			
		
				
				table.on('order.dt search.dt', function () {
					table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				           cell.innerHTML = i+1;
				           table.cell(cell).invalidate('dom');
				     });
					updateTotalAmount(table);
				}).draw();
				
				$('#pendingPayments_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
			                 updateTotalAmount(table);
			                } );
			     
			            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
			                select.append( '<option value="'+d+'">'+d+'</option>' )
			            } );
			        } );
				

				 // Setup - add a text input
		       $('#pendingPayments_dataTable .filters th[class="textBox"]').each( function () {                 
		           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

		       } );

              // DataTable
              var table = $('#pendingPayments_dataTable').DataTable();
		       
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

//Bhavesh(1-03-2024) for due invoices
var duecurrentYear = new Date().getFullYear();
var dueInvoiceDropdown = $("#dueInvoiceCalendarYearDropdown");
for (var i = 0; i < 5; i++) {
  var year = duecurrentYear - i;
  dueInvoiceDropdown.append('<option value="' + year + '">' + year + '</option>');
} 
function getYearprojectRangeForDueInvoice() {
	 
	  $(".months").val(0) ;
	    var selectedYear = parseInt(dueInvoiceDropdown.val());

	    if (selectedYear > 0) {
	      var firstDate = new Date(selectedYear, 0, 1); // January 1st
	      var lastDate = new Date(selectedYear, 11, 31); // December 31st
       
	     
	     
	      var formattedFromDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
	      var formattedToDate = $.datepicker.formatDate('dd/mm/yy', lastDate);
	      
	     
	    
	  	$("#monthsDue").val("0"); 
	      dueInvoiceTableWithYear(formattedFromDate,formattedToDate );
	  } else {
	      
		  $('#pendingInvoices_dataTable').empty();
			$('#pendingInvoices_dataTable').DataTable().destroy();
			var foot = $("#pendingInvoices_dataTable").find('tfoot');
			 if(foot.length){
				 $("#pendingInvoices_dataTable tfoot").remove();
			 }
			var  table= $('#pendingInvoices_dataTable').DataTable( {
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
	    
	   

	  }


function  dueInvoiceTableWithYear(dueFromDate,dueToDate){
	
	var selectedDateRange = dueFromDate+" to "+dueToDate;
	$('#asOnDateDueInvoice1').text(selectedDateRange);
	 $('#pendingInvoices_dataTable').DataTable().clear().destroy();
  
  
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/getPendingInvoiceDetailbyYear",
		data : {"dueFromDate":dueFromDate,
				"dueToDate":dueToDate
				},
				success : function(response) {
					var totalAmt =0;
					for (var i = 0; i < response.length; i++) {
						
						var row = response[i];	
						var strPurpose=row.strPurpose;
						var strRemarks=row.strRemarks;
						totalAmt +=row.numInvoiceAmtInLakhs;
						if(row.strPurpose==null){
							strPurpose='';
						}
						if(row.strRemarks==null){
							strRemarks='';
						}
						var strReferenceNumber=row.strReferenceNumber;
						if(!strReferenceNumber){
							strReferenceNumber='';
						}
						var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
								+ row.encProjectId + "')";
					
						tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.strGroupName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
								+ row.strProjectName + ' </a> <p class="bold blue font_14 text-left">'+ strReferenceNumber+'<p></td>';
						
						tableData += '<td class="font_14 orange">'+row.strClientName+'</td><td class="font_14 "> <span  class="" >'
						+ row.strPaymentDueDate
						+  '</span></td>';
						
						tableData += ' <td class="font_14">'+ strPurpose+  '</td>';
						
						tableData += ' <td class="font_14"> <span  class="" >'
							+strRemarks +  '</span></td>';
						tableData += ' <td class="font_14 text-right">'+row.strAmount+ ' Lakhs</td> </tr>';
						
					}

					/*if(totalAmt>0){
						tableData +='<tr><td></td><td></td> <td></td> <td></td>  <td></td><td class="bold text-right">Total</td> <td class="bold text-right">'+convertAmountToINR(totalAmt)+' Lakhs</td></tr>';
					}*/
					
					$('#pendingInvoices_dataTable > tbody').html('');
					$('#pendingInvoices_dataTable').append(tableData); 
					var foot = $("#pendingInvoices_dataTable").find('tfoot');
					 if(foot.length){
						 $("#pendingInvoices_dataTable tfoot").remove();
					 }
					
					if(totalAmt >0){
										
						 foot = $('<tfoot class="datatable_tfoot" style="background: #1580d0;">').appendTo("#pendingInvoices_dataTable"); 
						    foot.append('<tr><td><td></td></td><td></td> <td></td> <td></td>  <td></td><td class="bold text-right ">Total</td> <td class="bold text-right totalinvoice">'+convertAmountToINR(totalAmt)+' Lakhs</td></tr>');
					}
		           
					var  table= $('#pendingInvoices_dataTable').DataTable( {
						destroy: true,
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
							 updateTotalAmount(table);
						}).draw();
						
						$('#pendingInvoices_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
					                 updateTotalAmount(table);
					                } );
					     
					            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
					                select.append( '<option value="'+d+'">'+d+'</option>' )
					            } );
					        } );
						

						 // Setup - add a text input
				       $('#pendingInvoices_dataTable .filters th[class="textBox"]').each( function () {                 
				           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

				       } );

		               // DataTable
		               var table = $('#pendingInvoices_dataTable').DataTable();
				       
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

/*---------- Adding the DataTable feature in the View Project Document Details Table Data [ 18-03-2024 ] ------------*/
$(document).ready(function () {
	getProjectDocumentData();
	$('.select2Option').select2({
		width : '100%'
	});
});
function setTableViewProjectDocumentTable(){	
	var table= $('#viewProjectDocumentTable').DataTable( {
		dom: 'Bfrtip',		       
        "ordering": true,
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
	
	$('#viewProjectDocumentTable .filters th[class="comboBox"]').each( function ( i ) {
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
}
/*---------- EOL Adding the DataTable feature in the View Project Document Details Table Data [ 18-03-2024 ] ------------*/

/*---------- Function for get data in View Project Document Details Table [ 18-03-2024 ] ------------*/
function getProjectDocumentData() {
	var groupNum = $('#groupNameForDocumentTile').val();
    var projectId = $('#projectNameForDocumentTile').val();
    var documentTypeId = $('#projectDocumentTypeForDocumentTile').val();
    var calenderYear = $('#calenderYearForDocumentTile').val();

    if($('#projectDetailClose').val()=='true'){
    	$('#projectDetailClose').val('');
    	return;
    }
    
    if(projectId==0 && documentTypeId==0 && calenderYear==0){
    	calenderYear = new Date().getFullYear();
    	$('#calenderYearForDocumentTile').val(calenderYear);
    }
    
    const documentTypeIdList = []
    if(documentTypeId != 0){
	    for(var i=0;i<documentTypeId.length;i++){
	    	documentTypeIdList.push(documentTypeId[i]);
	    }
	    var strDocumentTypeIds = documentTypeIdList.join(',');
    }
    const projectIdList = []
    if(projectId != 0){
	    for(var i=0;i<projectId.length;i++){
	    	projectIdList.push(projectId[i]);
	    }
	    var strProjectIds = projectIdList.join(',');
    }

	$('#viewProjectDocumentTable').DataTable().clear().destroy();
    $.ajax({
        type: "POST",
        url: "/PMS/mst/getUploadedDocumentDetails",
        data: {
            "projectIds": strProjectIds,
            "documentTypeIds": strDocumentTypeIds,
            "documentDate": calenderYear,
            "groupId":groupNum
        },
        success: function (data) {
            var tableData = '';
            for (var i = 0; i < data.ProposalDocumentData.length; i++) {
                var rowData = data.ProposalDocumentData[i];
                var projectRefNo = '';
                if (rowData.strProjectRefNo != null && rowData.strProjectRefNo !== '') {
                    projectRefNo = rowData.strProjectRefNo;
                }
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ rowData.encProjectId + "')";
                tableData += "<tr id=" + rowData.numId + " style='color:#0d9b94;'><td class='text-center'>" + (i + 1) + " </td>";
				tableData += '<td> <a class="font_14" style="color:#0d9b94;" title="Click to View Complete Information" onclick='+clickFunction+'>'+ rowData.strProjectName + ' </a><p class="bold font_14 text-left">'+projectRefNo+'</p></td>';
                tableData += "<td>"+rowData.documentTypeName+"</td>";
                if (rowData.description) {
                    tableData += "<td >" + rowData.description + "</td>";
                } else {
                    tableData += "<td ></td>";
                }
                if (rowData.documentVersion) {
                    tableData += "<td >" + rowData.documentVersion + "</td>";
                } else {
                    tableData += "<td ></td>";
                }
                tableData += "<td>" + rowData.documentDate + " </td>";
                var uploads = rowData.detailsModels;
                downloadItem = '<p>';
                for (var j = 0; j < uploads.length; j++) {
                    var temp = uploads[j];
                    if (j < uploads.length - 1) {
                        downloadItem += "<a onclick=downloadDocument('" + temp.encNumId + "')> " + getFileIcons(temp.documentName) + "</a> || ";
                    } else {
                        downloadItem += "<a onclick=downloadDocument('" + temp.encNumId + "')> " + getFileIcons(temp.documentName) + "</a>";
                    }
                }
                downloadItem += '</p>';
                tableData += "<td class='text-center'>" + downloadItem + "</td>";
                tableData += "</tr>";
            }
            for (var i = 0; i < data.ProjectDocumentData.length; i++) {
                var rowData = data.ProjectDocumentData[i];
                var projectRefNo = '';
                if (rowData.strProjectRefNo != null && rowData.strProjectRefNo !== '') {
                    projectRefNo = rowData.strProjectRefNo;
                }
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ rowData.encProjectId + "')";
                tableData += "<tr id=" + rowData.numId + " style='color:#1578c2;'><td class='text-center'>" + (i + 1) + " </td>";
				tableData += '<td> <a class="font_14" style="color:#1578c2;" title="Click to View Complete Information" onclick='+clickFunction+'>'+ rowData.strProjectName + ' </a><p class="bold font_14 text-left">'+projectRefNo+'</p></td>';
                tableData += "<td>"+rowData.documentTypeName+"</td>";
                if (rowData.description) {
                    tableData += "<td >" + rowData.description + "</td>";
                } else {
                    tableData += "<td ></td>";
                }
                if (rowData.documentVersion) {
                    tableData += "<td >" + rowData.documentVersion + "</td>";
                } else {
                    tableData += "<td ></td>";
                }
                tableData += "<td>" + rowData.documentDate + " </td>";
                var uploads = rowData.detailsModels;
                downloadItem = '<p>';
                for (var j = 0; j < uploads.length; j++) {
                    var temp = uploads[j];
                    if (j < uploads.length - 1) {
                        downloadItem += "<a onclick=downloadDocument('" + temp.encNumId + "')> " + getFileIcons(temp.documentName) + "</a> || ";
                    } else {
                        downloadItem += "<a onclick=downloadDocument('" + temp.encNumId + "')> " + getFileIcons(temp.documentName) + "</a>";
                    }
                }
                downloadItem += '</p>';
                tableData += "<td class='text-center'>" + downloadItem + "</td>";
                tableData += "</tr>";
            }
            $('#viewProjectDocumentTable tbody').empty();
            $('#viewProjectDocumentTable').append(tableData);
            setTableViewProjectDocumentTable();
        },
        error: function (e) {
            alert('Error: ' + e);
        }
    });
}
/*---------- End of Function for get data in View Project Document Details Table [ 18-03-2024 ] ------------*/

/*---------- Function for get data in View Project Document Details Model Project Name Dropdown [ 18-03-2024 ] ------------*/
function getProject() {
	var groupNum = $('#groupNameForDocumentTile').val();
	$('#projectNameForDocumentTile').find('option').remove().end();
	$('#projectDetailClose').val('true');
	$('#projectNameForDocumentTile').val(-1).trigger('change');
	$('#projectDetailClose').val('true');
	$('#projectDocumentTypeForDocumentTile').val(-1).trigger('change');
	$('#calenderYearForDocumentTile').val(0);
	getProjectDocumentData();

	$.ajax({

		type : "POST",
		url : "/PMS/mst/getAllProjectNameByGroupId",
		data : "numGroupId=" + groupNum,
		success : function(response) {
			if(groupNum>0){
				for ( var i = 0; i < response.length; ++i) {
					if(response[i].projectRefNo === null || response[i].projectRefNo === ''){
						$('#projectNameForDocumentTile').append(
								$('<option/>').attr("value", response[i].numId)
										.text(response[i].strProjectName));	
					}else{
						var refNo = " [ "+response[i].projectRefNo+" ]";
				        var projectName = response[i].strProjectName;
						$('#projectNameForDocumentTile').append(
								$('<option/>').attr("value", response[i].numId)
										.text(projectName+refNo));	
					}
				}
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}
/*----------End of Function for get data in View Project Document Details Model Project Name Dropdown [ 18-03-2024 ] ------------*/

/*---------- Adding the Minimize and Maximize Button for View Document Details Tile [ 18-03-2024 ] ------------*/
var isMaximizedViewDocument = false;
function toggleViewDocumentDetail() {
	  var myModal = document.getElementById("viewDocumentsModel");
	  if (isMaximizedViewDocument) {
	    myModal.classList.remove("maximized");
	    document.body.classList.remove("modal-open");
	    document.getElementById('dash-document-details-modal').style.height = '';
	    document.getElementById('dash-document-details-modal').style.width = '';
	  } else {
	    myModal.classList.add("maximized");
	    document.body.classList.add("modal-open");
	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
	    var modal = document.getElementById('dash-document-details-modal');
	    modal.style.height = (windowHeight * 0.75) + 'px'; 
	    modal.style.width = (windowWidth * 0.98) + 'px'; 
	    modal.style.transformOrigin = 'center';
	  }

	  isMaximizedViewDocument = !isMaximizedViewDocument; 
	  var btn = document.getElementsByClassName('close-btn')[0];
	  var icon = btn.getElementsByTagName('i')[0];
	  
	  if (icon.classList.contains('fa-window-maximize')) {
	    icon.classList.remove('fa-window-maximize');
	    icon.classList.add('fa-window-restore');
	  } else {
	    icon.classList.remove('fa-window-restore');
	    icon.classList.add('fa-window-maximize');
	  }
}
function restoreViewDocumentDetail(){
	isMaximizedViewDocument = true;
	toggleViewDocumentDetail();
	var myModal = document.getElementById("viewDocumentsModel");
	myModal.classList.remove("maximized");
	document.body.classList.remove("modal-open");
	document.getElementById('dash-document-details-modal').style.height = '';
	document.getElementById('dash-document-details-modal').style.width = '';
	
	$('#projectDetailClose').val('true');
	$('#projectNameForDocumentTile').val(-1).trigger('change');
	$('#projectDetailClose').val('true');
	$('#projectDocumentTypeForDocumentTile').val(-1).trigger('change');
	$('#calenderYearForDocumentTile').val(0);
	$('#groupNameForDocumentTile').val(0).trigger('change');

}
/*---------- End of Adding the Minimize and Maximize Button for View Document Details Tile [ 18-03-2024 ] ------------*/

/*---------------- Function for get the correct icon by its file extension [18-03-2024] -------*/
function getFileIcons(fileName) {
    var extension = fileName.split('.').pop().toLowerCase();
    var iconClass = '';

    switch (extension) {
        case 'pdf':
            iconClass = 'fa-file-pdf-o red';
            break;
        case 'doc':
        case 'docx':
            iconClass = 'fa-file-word-o';
            break;
        case 'xls':
        case 'xlsx':
            iconClass = 'fa-file-excel-o';
            break;
        case 'ppt':
        case 'pptx':
            iconClass = 'fa-file-powerpoint-o';
            break;
        case 'jpg':
        case 'jpeg':
        case 'png':
        case 'gif':
            iconClass = 'fa-file-image-o';
            break;
        default:
            iconClass = 'fas fa-file-alt';
            break;
    }
    return '<i class="fa ' + iconClass + '" aria-hidden="true"></i>';
}
/*---------------- End of Function for get the correct icon by its file extension [18-03-2024] -------*/

/*--------- Set Monthly Progress Report and Set Table Properties [21-05-2024] ---*/
$(document).ready(function () {
	projectProgressTblLastMonth();
	projectProgressTblCurrentMonth();
});

function setTablePropertiesForProgressReport(tableID){
	var table= $(tableID).DataTable( {
		dom: 'Bfrtip',	
		destroy: true,
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
		
	$(tableID+' .filters th[class="comboBox"]').each( function ( i ) {
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
}

function projectProgressTblLastMonth() {
	var monthNames = [ "January", "February", "March", "April", "May", "June",
	                   "July", "August", "September", "October", "November", "December" ];
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/activeProgressReportsDetailsbyGCforLastMonth",
		data : {"actionId": 6},
		success : function(data) {	
			var tableData = '';
			for(var i=0;i<data.length;i++){
				var row = data[i];
				var action= '<div class="dropdown">';																						
				action += ' <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onmouseover=viewAllowedActionsMonthly("'+row.encNumId+'","'+row.encCategoryId+'") aria-haspopup="true" aria-expanded="false">';
				action+='<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>';
				action+='<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="'+row.encNumId+'"></ul></div>';
				
				tableData +='<tr><td class="text-right">'+(i+1)+'</td><td>'+row.strGroupName+'</td><td><a style="color: #1a7dc4;" class="font_14" onclick="viewProjectDetails(\'/PMS/projectDetails/' + row.encProjectId + '\')">'+row.strProjectName+'</a><p class="bold blue font_14 text-left">'+ row.strProjectReferenceNo+'<p></td>';
				tableData +='<td>'+monthNames[row.month-1] +'/'+row.year+'</td>';
				tableData +='<td>'+row.transactionDate+'</td> <td>'+action+'</td></tr>';
			}

			$('#projectProgressTblLastMonth').DataTable().clear().destroy();
			$('#projectProgressTblLastMonth tbody').append(tableData);
			setTablePropertiesForProgressReport('#projectProgressTblLastMonth');
			loadAllApprovedProgressReportforLastMonth();
		}
	});
}

function projectProgressTblCurrentMonth() {
	var monthNames = [ "January", "February", "March", "April", "May", "June",
	                   "July", "August", "September", "October", "November", "December" ];
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/activeProgressReportsDetailsbyGCforCurrentMonth",
		data : {"actionId": 6},
		success : function(data) {
			console.log(data);
			var tableData = '';
			for(var i=0;i<data.length;i++){			
				var row = data[i];

				var action= '<div class="dropdown">';																						
				action += ' <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onClick=viewAllowedActionsMonthly("'+row.encNumId+'","'+row.encCategoryId+'") aria-haspopup="true" aria-expanded="false">';
				action+='<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>';
				action+='<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="'+row.encNumId+'"></ul></div>';
							
				tableData +='<tr><td class="text-right">'+(i+1)+'</td><td>'+row.strGroupName+'</td><td><a style="color: #1a7dc4;" class="font_14" onclick="viewProjectDetails(\'/PMS/projectDetails/' + row.encProjectId + '\')">'+row.strProjectName+'</a><p class="bold blue font_14 text-left">'+ row.strProjectReferenceNo+'<p></td>';
				tableData +='<td>'+monthNames[row.month-1] +'/'+row.year+'</td>';
				tableData +='<td>'+row.transactionDate+'</td> <td>'+action+'</td></tr>';
			}
			
			$('#projectProgressTblCurrentMonth').DataTable().clear().destroy();
			$('#projectProgressTblCurrentMonth tbody').append(tableData);
			setTablePropertiesForProgressReport('#projectProgressTblCurrentMonth');
			loadApprovedProgressReportforCurrentMonth();
		}
	});
}
/*--------- End of Set Monthly Progress Report and Set Table Properties [21-05-2024] ---*/