
$(document).ready(function(){
	/*added the date format picker by varun on 06-07-2023*/
	 var dateFormat = "dd/mm/yy",
	    from = $( "#from" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	    minDate : '-5Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $( "#to" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      minDate : '-5Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });
	var groupColumn = 0;
			 $('.mileDataTable').DataTable({
				 dom: 'Bfrtip',	
				 "paging":   false,
			        "ordering": false,
			        "info":     false,
			        buttons: [
					             'excel', 'pdf', 'print'
					        ]
			 
			
			 
			 });
	        		 
	        		 
			 $('#datatable1').DataTable( {					
			        dom: 'Bfrtip',			        
			       /* "ordering": false,*/
			        "paging":   false,
			        buttons: [
			             'excel', 'pdf', 'print'
			        ],
			      /*  "columnDefs": [
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
			                                   '<tr class="group"><td colspan="6">'+group+'</td></tr>'
			                               );
			          
			                               last = group;
			                           }
			                       } );
			                   }*/
			    });
				
			    $('#datatable1 tbody').on( 'click', 'tr.group', function () {
			        var currentOrder = table.order()[0];
			        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
			            table.order( [ groupColumn, 'desc' ] ).draw();
			        }
			        else {
			            table.order( [ groupColumn, 'asc' ] ).draw();
			        }
			    } );       	
	        	
});

/*added the date format picker by varun on 06-07-2023*/
function getDate( element ) {
    var date;
    try {
      date = $.datepicker.parseDate( dateFormat, element.value );
    } catch( error ) {
      date = null;
    }

    return date;
  }
/*added the go btn function  by varun on 06-07-2023*/
$('.go-btn').click(function() {
	  var startRange = $("#from").val();
	  var endRange = $("#to").val();
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
	  MilestonetablebyGc(startRange,endRange);
});
function calculateDates(){
	
	var d = new Date();
	var symbol= $("#filterSymbol").val();
	var selValue=$("#months").val();

	if(selValue==0){
	
		$('#datatable1').empty();
		$('#datatable1').DataTable().destroy();
		var foot = $("#datatable1").find('tfoot');
		 if(foot.length){
			 $("#datatable1 tfoot").remove();
		 }
		var  table= $('#datatable1').DataTable( {
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
	 if(selValue!=0)
		{
			$("#from").val("01"+"/"+fromMonth+"/"+d.getFullYear());
			$("#to").val(lastDayOfLastMonth+"/"+toMonth+"/"+lastMonth.getFullYear());
			
		}
		else
			{
				$("#from").val("");
				$("#to").val("");
				
			}
		var compDate="01"+"/"+fromMonth+"/"+d.getFullYear();

	pendingPaymentsTableWithInvoiceDate(compDate,symbol);
	}
}

var lastday = function(y,m){
	return  new Date(y, m, 0).getDate();
	}	

function pendingPaymentsTableWithInvoiceDate(compDate,symbol){	
	$('#datatable1').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/MilestoneExceededDeadlineDetailByDates",
		data : {"compDate":compDate,
				"symbol":symbol
				},
		success : function(response) {
			console.log(response);
		
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
			var strReferenceNumber=row.strProjectReference;
				if(!strReferenceNumber){
					strReferenceNumber='';
				}
				/*added and count the serial number on 05-06-2023 */
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
			
				tableData += '<tr><td>' + (i+1) + '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
						+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';
		
				tableData += ' <td>'+ row.milestoneName +'</td>';
				
				if(row.completionDate!=null){
				tableData += ' <td class=" text-center">'	+ row.completionDate + '</td>';
				}
				if(row.expectedStartDate!=null){
				tableData += ' <td class=" text-center">'	+ row.expectedStartDate + '</td>';
				}
				tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'+ row.encMilestsoneId + '")>Review</a></td> </tr>';			
			
			}		
		
			$('#datatable1  tbody').append(tableData); 
		          
			$('#datatable1').DataTable( {					
		        dom: 'Bfrtip', 
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
		        /*"columnDefs": [
		                       { "visible": false, "targets": groupColumn }
		                   ],
		                   "order": [[ groupColumn, 'asc' ]],		                  
		                   "drawCallback": function ( settings ) {
		                       var api = this.api();
		                       var rows = api.rows( {page:'current'} ).nodes();
		                       var last=null;
		            
		                       api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
		                           if ( last !== group ) {
		                               $(rows).eq( i ).before(
		                                   '<tr class="group"><td colspan="1">'+group+'</td></tr>'
		                               );
		          
		                               last = group;
		                           }
		                       } );
		                   }*/
		    });
			
		   /* $('#datatable1 tbody').on( 'click', 'tr.group', function () {
		        var currentOrder = table.order()[0];
		        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
		            table.order( [ groupColumn, 'desc' ] ).draw();
		        }
		        else {
		            table.order( [ groupColumn, 'asc' ] ).draw();
		        }
		    } ); 
*/
                        
		}
	
	});

	
		
	
}
/*added the milestonetable function  by varun on 06-07-2023*/
function MilestonetablebyGc(startDate,endDate){	
	$('#datatable1').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/MilestoneExceededDeadlineDetailByDates",
		data : {"compDate":startDate,
				"symbol":endDate
				},
		success : function(response) {
			console.log(response);
		
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
			var strReferenceNumber=row.strProjectReference;
				if(!strReferenceNumber){
					strReferenceNumber='';
				}
				/*added and count the serial number on 05-06-2023 */
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
			
				tableData += '<tr><td>' + (i+1) + '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
						+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';
		
				tableData += ' <td>'+ row.milestoneName +'</td>';
				
				if(row.completionDate!=null){
				tableData += ' <td class=" text-center">'	+ row.completionDate + '</td>';
				}
				if(row.expectedStartDate!=null){
				tableData += ' <td class=" text-center">'	+ row.expectedStartDate + '</td>';
				}
				tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'+ row.encMilestsoneId + '")>Review</a></td> </tr>';			
			
			}		
		
			$('#datatable1  tbody').append(tableData); 
		          
			$('#datatable1').DataTable( {					
		        dom: 'Bfrtip', 
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
		        /*"columnDefs": [
		                       { "visible": false, "targets": groupColumn }
		                   ],
		                   "order": [[ groupColumn, 'asc' ]],		                  
		                   "drawCallback": function ( settings ) {
		                       var api = this.api();
		                       var rows = api.rows( {page:'current'} ).nodes();
		                       var last=null;
		            
		                       api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
		                           if ( last !== group ) {
		                               $(rows).eq( i ).before(
		                                   '<tr class="group"><td colspan="1">'+group+'</td></tr>'
		                               );
		          
		                               last = group;
		                           }
		                       } );
		                   }*/
		    });
			
		   /* $('#datatable1 tbody').on( 'click', 'tr.group', function () {
		        var currentOrder = table.order()[0];
		        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
		            table.order( [ groupColumn, 'desc' ] ).draw();
		        }
		        else {
		            table.order( [ groupColumn, 'asc' ] ).draw();
		        }
		    } ); 
*/
                        
		}
	
	});

	
		
	
}

