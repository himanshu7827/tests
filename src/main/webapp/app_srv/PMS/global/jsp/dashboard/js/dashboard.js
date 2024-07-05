 function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
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
						//console.log(data);
						for (var i = 0; i < data.length; i++) {
							var row = data[i];
							//console.log(row);
							var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
									+ row.encNumId + "')";
							tableData += '<tr><td > <a class="font_17" title="Click to View Complete Information" onclick='+clickFunction+'>'
									+ row.strProjectName + ' </a> </td>';
							tableData += ' <td class="font_16">'
									+ row.startDate
									+ '</td>  <td class="font_16">'
									+ row.endDate
									+ ' </td>  <td class="font_16"> '
									+ row.projectCost + '</td> </tr>';
						}

						$('#example_det > tbody').html('');
						$('#example_det').append(tableData);

						$('#example_det').DataTable();
					}

				});

	}
	


		///for table scroll	
	$(document).ready(function() {
		
		$('#example_det').DataTable();

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


	

	
	
	function loadGroupWiseProposals(encGroupId){
		$('#groupWiseProject').removeClass('hidden');
		$('#groupWiseProjectDtl').find('div').remove();
		
		var url = "/PMS/mst/ViewProjectDetails?encGroupId="+encGroupId;
		
		var data='<div > <iframe id="test" src='+url+' width="100%;" height="100%;" frameborder="0" ></iframe> </div>';
		
		$('#groupWiseProjectDtl').append(data);
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
		
		$("#asOnDate").text(dateString);
		//$("#asOnDate1").text(dateString);
		$("#asOnDateExp").text(dateString);
		//$("#asOnDateExp1").text(dateString);
		
	$("#dtStartDate").datepicker({ 
		
		 showOn: "both", 
	      buttonText: "<i class='fa fa-calendar  black '></i>",
	dateFormat: 'dd/mm/yy', 
    changeMonth: true,
    changeYear:true,           
    maxDate: '0',
    
   onSelect: function() {
	   var date = $('#dtStartDate').val();
	   //alert("Date Value"+date);
	   
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/getIncomeByDate",
			data : "startDate="+date ,
			success : function(
					response) {
				//console.log(response);			
					// var k = response;
					
					// $('#incomeOndate').html(k);

					// $("#asOnDate").text(date);
				var fieldValue = response;
				 var convertedStringAmount = convertINRToAmount(fieldValue);
				 
					console.log(convertedStringAmount);
					var amount = (parseFloat(convertedStringAmount))/100000;
					console.log(response + " "+ amount);
					//console.log(roundUpTo2Decimal(amount));
					var convertedIntegerAmount=convertAmountToINR(roundUpTo2Decimal(amount,2));
					
					$('#incomeOndate').html(convertedIntegerAmount +" Lakhs");

				 $("#asOnDate").text(date);

		}
	});
		
		
      }
	});

	
	$("#dtExpStartDate").datepicker({ 
		
		 showOn: "both", 
  buttonText: "<i class='fa fa-calendar black'></i>",
		
    /*   buttonImage: "/PMS/resources/app_srv/PMS/global/images/slider/slider4.jpg",
         buttonImageOnly: true,*/
		dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '0',
	    showOn: 'both',
	    
	   onSelect: function() {
		   var date = $('#dtExpStartDate').val();
		   //alert("Date Value"+date);
		   
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/getExpenditureByDate",
				data : "startDate="+date ,
				success : function(
						response) {
					//console.log(response);			
				// var k = response;
						
						// $('#expenditureOndate').html(k);
						// $("#asOnDateExp").text(date);
					var fieldValue = response;
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
	
	
	
	$('#dash-income-modal').on('show.bs.modal', function(event) {
		initialzeIncomeChart();
	}); 
	$('#dash-expenditure-modal').on('show.bs.modal', function(event) {
		initialzeExpenditureChart();
	});
	/*$('#dash-grpwise-bargraph-modal').on('show.bs.modal', function(event) {
		showGraph();
	});*/
function incomeTable(){
	/*Bhavesh(08-09-2023) commented the below line*/
	/* table = $('#income_dataTable').DataTable().clear().destroy();*/
	
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
	
	var tableData = '';
	$.ajaxRequest.ajax({
		
		type : "POST",
		url : "/PMS/mst/getPaymentReceivedDetails",
		data:{
			"startDate":startDate,
			"endDate":endDate
		},
		success : function(response) {
			 var totalAmount = 0;
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
				//console.log(row);
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
						+ row.encProjectId + "')";
				tableData +='<tr > <td>'+(i+1)+'</td><td  class="font_14" style="width:15%;">'+row.groupName+'</td>';
				tableData += '<td > <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.projectName + ' </a> </td>';
				tableData += ' <td class="font_14 text-right"> <span  class="">'
					+ row.strdtPayment
					+  '</span></td>';
				tableData += ' <td class="font_14 text-right">'
						+ row.strReceivedAmount
						+  '</td> </tr>';
				totalAmount += row.numReceivedAmountLakhs;
			}
			if(totalAmount >0){
				tableData +='<tr><td class="bold" colspan="4"><span class="pull-right">Total</span></td> <td class="bold pull-right">'+convertAmountToINR(totalAmount)+'</td></tr>';
			}
			
			
			$('#income_dataTable > tbody').html('');
			$('#income_dataTable').append(tableData);

			$('#income_dataTable').DataTable( {
				 destroy: true,
			        dom: 'Bfrtip',
			        destroy: true,
			        "ordering": false,
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ]
			    });	
		                              
		}
	
	});

	
		
	
}
	//Expenditure Table
function expenditureTable(){
	 table = $('#expenditure_dataTable').DataTable().clear().destroy();
	
	
	var tableData = '';
	$.ajaxRequest.ajax({
		
		type : "POST",
		url : "/PMS/mst/getExpenditureByGroupByProject",
		success : function(response) {
			//console.log(response);
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
				//console.log(row);
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"
						+ row.encProjectId + "')";
				tableData +='<tr ><td  class="font_14" style="width:15%;">'+row.groupName+'</td>';
				tableData += '<td > <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'
						+ row.projectName + ' </a> </td>';
				tableData += ' <td class="font_14 text-right">'
						+ row.strTotalExpenditure
						+  '</td> </tr>';
			}

			$('#expenditure_dataTable > tbody').html('');
			$('#expenditure_dataTable').append(tableData);

			 table= $('#expenditure_dataTable').DataTable();
			
			$('#expenditure_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
		                                $('#expenditure_dataTable .filters th[class="textBox"]').each( function () {                 
		                                    $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );
		             
		              } );
		                             
		                                // DataTable
		                                var table = $('#expenditure_dataTable').DataTable();
		            
		             // Apply the search
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

	//$('#example').DataTable();
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
   
   table.columns().eq( 0 ).each( function ( colIdx ) {
       $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
           table
                       .column( colIdx )
                       .search( this.value )
                       .draw() ;
               
                                       } );
} );
   
	
});


$('#amtreceive').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	var inputs = input.split(';');
	var projectId= inputs[0];
	
	var resultArray = $('#'+projectId).find('td').map( function()
			{
			return $(this).text();
			}).get();
	var result=resultArray[2].trim();
	var resultSplit=result.split('\n');
	var split1=resultSplit[0];
	var split2=resultSplit[1];
	$('#projectForPayment').text(split1);
	$('#clientIdForPayment').text(split2);
	var startDate= inputs[1];
	var projectCost= inputs[2].trim();
	//console.log(projectCost);
	var tableData = "";
$.ajax({
		type : "POST",
		url : "/PMS/mst/getProjectPaymentReceivedByProjectId",
		data : "encProjectId=" + projectId,
		async:false,
		success : function(response) {
		//console.log(response);
			  var SumOfReceivedAmt=0
			  var paymentReceive=0;
			  var paymentDue=0;
 			  //paymentDue =parseFloat(convertINRToAmount(projectCost))*100000;
 			 paymentDue =parseFloat(convertINRToAmount(projectCost));
			   tableData+="<tr><td>"+startDate+"</td><td><b>Total Project Cost</b></td><td class='text-right'><b>"+convertAmountToINR(paymentDue)+"</b></td><td class='text-right'>₹ "+paymentReceive+"</td></tr>";

		   for(var i =0;i<response.length;i++){
				var row = response[i]; 
			   var newAmt =row.numReceivedAmount;
			   SumOfReceivedAmt+=newAmt;
			       tableData+="<tr><td>"+row.strdtPayment+"</td> ";
			  tableData+="<td><p>"+row.strPaymentMode+" , "+row.strUtrNumber+"</p><p class='pad-left'><b>" ;
			  if(row.encInvoiceId!=null){
				  tableData+="Payment Received Against Invoice Ref No. : </b><a title='Click here to view Invoice details' data-toggle='modal' data-target='#invoiceDetail' onclick=viewInvoiceDetails('"+row.encInvoiceId+"') class='text-center'>"+row.invoiceCode+"</a></p></td>";
					tableData+="<td class='text-right'>"+row.paymentDue+"</td> <td class='text-right'>"+convertAmountToINR(newAmt)+"</td></tr>";

			  }else{
				  tableData+="Payment Received without Invoice Details : </b></p></td>";  
					tableData+="<td class='text-right'>"+row.paymentDue+"</td> <td class='text-right'>"+convertAmountToINR(row.numBalanceAmount)+"</td></tr>";

			  }
			}
		if(SumOfReceivedAmt && SumOfReceivedAmt>0){
			tableData+="<tr><td colspan='3' class='font_eighteen text-right'><b>Total Payment Received:</b></td><td class='font_eighteen text-right'><b>"+convertAmountToINR(SumOfReceivedAmt)+"</b></td> </tr>";
			tableData+="<tr><td colspan='3' class='font_eighteen text-right'><b>Payment Remaining:</b></td><td class='font_eighteen text-right'><b>"+row.paymentDue+"</b></td> </tr>";

		}
			tableData+='</tbody>';
			$('#datatable tbody').empty();						
			$('#datatable').append(tableData);		
		},
		error : function(e) {
			alert('Error: ' + e);			
		}
	});				
});



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

function viewAllowedActionOnProject(projectId){
	console.log(projectId);
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/viewAllowedActionOnProject",
		data : {
				"encProjectId":projectId				
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
});	
}

/*<!-- Bhavesh(08-09-2023)) restore code  -->*/

//restore code
var isMaximized = false;
function toggleMaximize() {
	
	 
	  if (isMaximized) {
	
		  $(".myModal1").removeClass("maximized");
		 
	    
		  $("body").removeClass("modal-open");
		  
		 
	 
	    
	    $('.dash-grpwise-bargraph-modal').css('height', '');
	    $('.dash-grpwise-bargraph-modal').css('width', '');
	    
	   
	  } else {
	  
		  $(".myModal1").addClass("maximized");
		  $("body").addClass("modal-open");

	    var windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth; 
	    var modal =$('.dash-grpwise-bargraph-modal');
	 
	    var windowHeight = $(window).height();
	    $('.dash-grpwise-bargraph-modal').height(windowHeight * 0.75 + 'px');

	    
	    
	    var windowWidth = $(window).width();
	    $('.dash-grpwise-bargraph-modal').width(windowWidth * 0.98 + 'px');
	    $('.dash-grpwise-bargraph-modal').css('transform-origin', 'center');
	  }

	  isMaximized = !isMaximized;
	  
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

function restoreModalSize() {
	  
	  $(".myModal1").removeClass("maximized");
	  $("body").removeClass("modal-open");
	 
	  $('.dash-grpwise-bargraph-modal').css('height', '');
	    $('.dash-grpwise-bargraph-modal').css('width', '');
	}





function restoreprop(){
	
	
	  isMaximized=true;
	  toggleMaximize();
	  restoreModalSize();
	  
	 
	
} 