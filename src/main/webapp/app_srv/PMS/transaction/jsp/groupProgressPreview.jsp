<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">

<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link href="/SERB/resources/app_srv/serb/gl/theme/dataTables.responsive.css"  rel="stylesheet" type="text/css" />
<script src="/SERB/resources/app_srv/serb/gl/jssrc/dataTables.responsive.js" type="text/javascript"></script>
<script src="/SERB/resources/app_srv/serb/gl/jssrc/dataTables.responsive.js" type="text/javascript"></script>
 -->
             
             <script src="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.0.0/jquery.magnific-popup.min.js"></script>
             <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.0.0/magnific-popup.min.css"> 
  <script type="text/javascript"
		src="/PMS/src/main/webapp/app_srv/PMS/global/js/jquery.magnific-popup.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
<!-- <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/progressPreview.js"></script> -->
<!-- <link href="/SERB/resources/app_srv/serb/gl/theme/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="/SERB/resources/app_srv/serb/gl/theme/dataTables.responsive.css"  rel="stylesheet" type="text/css" />
<script src="/SERB/resources/app_srv/serb/gl/jssrc/jquery.dataTables.js" type="text/javascript"></script>
< -->
<script type="text/javascript">
$('document').ready(function(){
	
	var encMonthlyProgressId = '${encMonthlyProgressId}';
	var encPageId ='${encPageId}';
	var encWorkflowId ='${encWorkflowId}';
	getNextRoleActions(encMonthlyProgressId,encPageId,encWorkflowId);
	
});
</script>
<script type="text/javascript">
function getProdServiceDocs(deploymentId){
	//alert(categoryId);
	var monthlyProgressId=$('#encMonthlyProgressId').val();
	var PAGE_URL = "/PMS/ViewTotDocs?encMonthlyProgressId="+ monthlyProgressId+"&numCategoryId="+16+"&numDeploymentId="+deploymentId;
	$('.tooltip').hide();
			$.magnificPopup.open({closeOnBgClick: false,
				items : {
					src : PAGE_URL,
					type : 'iframe'
				}
			});
			$.magnificPopup.instance.close = function() {
				$('.tooltip').show();
				$.magnificPopup.proto.close.call(this);
				
			}; 
}
</script>
<script type="text/javascript">
function getTotDocs(deploymentId){
	//alert(categoryId);
	var monthlyProgressId=$('#encMonthlyProgressId').val();
	 /*  var strIds="";
		$('.docIds').each(function(){
			strIds+=this.value+",";
		});
		strIds=strIds.substring(0,strIds.length-1);
	alert(monthlyProgressId); */
	
	//openWindowWithPost('GET','/PMS/ViewTotDocs',{"encMonthlyProgressId":monthlyProgressId,"numCategoryId":3,"numDeploymentId":deploymentId},'_blank');
	 var PAGE_URL = "/PMS/ViewTotDocs?encMonthlyProgressId="+ monthlyProgressId+"&numCategoryId="+3+"&numDeploymentId="+deploymentId;
	$('.tooltip').hide();
			$.magnificPopup.open({closeOnBgClick: false,
				items : {
					src : PAGE_URL,
					type : 'iframe'
				}
			});
			$.magnificPopup.instance.close = function() {
				$('.tooltip').show();
				$.magnificPopup.proto.close.call(this);
				
			}; 
}
</script>
<script type="text/javascript">
			function getPreviewData(categoryId, encCategoryId, encDetailsId){
				//console.log(categoryId);
				//console.log(encCategoryId);
				//console.log(encDetailsId);
				$('#main_'+categoryId).empty();
				$.ajax({
				    type: "POST",
				    url: "/PMS/getPreviewData",
				    data: {"encProgressDetailsId": encDetailsId,
				    	"encCategoryId":encCategoryId	},
				    
				    success: function(response) {
				    	console.log(response);
				    	
				    	 if ( $.fn.DataTable.isDataTable('#tbl_'+categoryId+'') ) {
							  $('#tbl_'+categoryId+'').DataTable().clear().destroy();
							} 
				    	if(response && response.length ==1){
				    		var rowData = response[0];
				    		console.log(rowData);
				    		if(rowData.length == 1){
				    			$('#main_'+categoryId).append(rowData[0]);
				    		}else if(rowData.length == 2){
				    			$('#main_'+categoryId).append('<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">'+rowData[0] +'</div> <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">'+rowData[1]+'</div>');
				    		}else if(rowData.length == 3){
				    			$('#main_'+categoryId).append('<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">'+rowData[0] +'</div> <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">'+rowData[1]+'</div> <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">'+rowData[2]+'</div>');
				    		}
				    	}else if(response && response.length >=2){
				    		if(categoryId==3){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 100px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 300px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==3||j==4||j==5||j==6){
					    					tableData+='<th class="none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 300px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==3||j==4||j==5||j==6){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    				/* 		else if(j==8){
				    							tableData+='<td class="docIds"><a'+rowData[j]+'</td>';	    					
		
					    					} */
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==3||j==4||j==5||j==6){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    							 else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		
				    		
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==6){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==1){
				    					tableData+='<th class="details-control" style="width: 209px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==0||j==2||j==6||j==8){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					else if(j==5||j==7){
				    						tableData+='<th class="control" style="width: 120px;">'+rowData[j]+'</th>';			    					
	
				    					}
				    					else{
				    						tableData+='<th class="control" style="width: 300px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==0||j==2||j==8||j==6){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==0||j==2||j==8||j==6){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==4){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered compact display  stripe" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 220px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==2||j==3||j==4||j==7){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==2||j==3||j==4||j==7){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==2||j==3||j==4||j==7){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==5){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 500px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==8){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==8){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==8){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==10){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered compact display hover stripe" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 500px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==5||j==7){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==5||j==7){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==5||j==7){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==15){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered compact display hover stripe" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 500px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==3){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==3){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==3){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==12){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered compact display hover" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 500px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==2||j==1){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==1||j==2){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==1||j==2){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}else if(categoryId==17){
				    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered compact display hover" style="background-color: whitesmoke">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				tableData+='<th style="width: 50px;"></th>';
				    				for(var j=0;j<rowData.length;j++){	
				    					if(j==0){
				    					tableData+='<th class="details-control" style="width: 500px;">'+rowData[j]+'</th>';		    					
				    				}
				    					else if(j==3){
					    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					
	
				    					}
				    					
				    					else{
				    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
				    					}
				    					
				    					}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				tableData+='<td></td>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						if(j==3){
				    						tableData+='<td>'+rowData[j]+'</td>';}
				    						else{
				    							tableData+='<td>'+rowData[j]+'</td>';	
				    						}
				    					}else{
				    						if(j==3){
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}else{
				    							tableData+='<td>'+rowData[j]+'</td>';
				    						}
				    						
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    		$('#tbl_'+categoryId+'').DataTable({
				    			"paging":   false,
				    	        "ordering": false,
				    	        "info":     false,
				    	        "searching": false,
				    		responsive: {
					                details: {
					                    type: 'column',
					                    target: 'tr'
					                }
					            },
					            columnDefs: [ {
					                className: 'control',
					                orderable: false,
					                targets:   0
					            } ],
					            order: [ 1, 'asc' ]
					    	
					    	});
				    	}
				    	else if(categoryId==14){
			    			var tableData = '<table id="tbl_'+categoryId+'" class="table table-responsive table-bordered compact display hover" style="background-color: whitesmoke">';
			    		for(var i = 0 ; i<response.length ;i++){
			    			var rowData = response[i];
			    			if(i == 0){
			    				//Header Row
			    				tableData+='<thead class="datatable_thead bold"> <tr>';
			    				tableData+='<th style="width: 50px;"></th>';
			    				for(var j=0;j<rowData.length;j++){	
			    					if(j==0){
			    					tableData+='<th class="details-control" style="width: 600px;">'+rowData[j]+'</th>';		    					
			    				}
			    					else if(j==2){
				    					tableData+='<th class="hide  none">'+rowData[j]+'</th>';		    					

			    					}
			    					else if(j==3||j==5||j==2||j==4){
			    						tableData+='<th class="control" style="width: 120px;">'+rowData[j]+'</th>';		    					

			    					}
			    					
			    					else{
			    						tableData+='<th class="control" style="width: 500px;">'+rowData[j]+'</th>';	
			    					}
			    					
			    					}
			    				tableData+='</thead> </tr><tbody>';
			    			}else{
			    				tableData+='<tr>';
			    				tableData+='<td></td>';
			    				for(var j=0;j<rowData.length;j++){
			    					//tableData+='<td>'+rowData[j]+'</td>';
			    					if($.isNumeric(rowData[j])){
			    						if(j==2){
			    						tableData+='<td>'+rowData[j]+'</td>';}
			    						else{
			    							tableData+='<td>'+rowData[j]+'</td>';	
			    						}
			    					}else{
			    						if(j==2){
			    							tableData+='<td>'+rowData[j]+'</td>';
			    						}else{
			    							tableData+='<td>'+rowData[j]+'</td>';
			    						}
			    						
			    					}
			    				}
			    				tableData+='</tr>';
			    			}
			    		}
			    		tableData+='</tbody></table>';
			    		$('#main_'+categoryId).append(tableData);
			    		$('#tbl_'+categoryId+'').DataTable({
			    			"paging":   false,
			    	        "ordering": false,
			    	        "info":     false,
			    	        "searching": false,
			    		responsive: {
				                details: {
				                    type: 'column',
				                    target: 'tr'
				                }
				            },
				            columnDefs: [ {
				                className: 'control',
				                orderable: false,
				                targets:   0
				            } ],
				            order: [ 1, 'asc' ]
				    	
				    	});
			    	}else{
				    		var tableData = '<table id="tbl_'+categoryId+'" class="table table-bordered">';
				    		for(var i = 0 ; i<response.length ;i++){
				    			var rowData = response[i];
				    			if(i == 0){
				    				//Header Row
				    				tableData+='<thead class="datatable_thead bold"> <tr>';
				    				for(var j=0;j<rowData.length;j++){				    					
				    					tableData+='<th class="text-center">'+rowData[j]+'</th>';		    					
				    				}
				    				tableData+='</thead> </tr><tbody>';
				    			}else{
				    				tableData+='<tr>';
				    				for(var j=0;j<rowData.length;j++){
				    					//tableData+='<td>'+rowData[j]+'</td>';
				    					if($.isNumeric(rowData[j])){
				    						tableData+='<td class="text-right">'+rowData[j]+'</td>';
				    					}else{
				    						tableData+='<td class="text-left">'+rowData[j]+'</td>';
				    					}
				    				}
				    				tableData+='</tr>';
				    			}
				    		}
				    		tableData+='</tbody></table>';
				    		$('#main_'+categoryId).append(tableData);
				    	}
				    		}else if(!response){}
				    	else if(response.length == 0){
				    		var noDataDiv= '<div class="text-center red"> No Data Found </div>';
				    		$('#main_'+categoryId).append(noDataDiv);
				    	}else{
				    		var noDataDiv= '<div class="text-center red"> Something went wrong </div>';
				    		$('#main_'+categoryId).append(tableData);
				    	}
				    },error: function(response){
				    	console.error(response);
				    }
					
				});	
				
			}
			
			</script>

</head>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
					
					</ol>
				</div>
			</div>	
						<div class="row "></div>
				
				<!-- for flashing the success message -->
			
		 <c:if test="${message != null && message != ''}">
		 	<c:choose>
		 		<c:when test="${status=='success'}">
		 			<div id="userinfo-message"><p class="success_msg">${message}</p></div> 
		 		</c:when>
		 		<c:otherwise>
		 			<div id="userinfo-message"><p class="error_msg">${message}</p></div> 
		 		</c:otherwise>		 	
		 	</c:choose>       		
        </c:if>	
        <div class="container pad-top pad-bottom">
        	<div class="">					
			<div class="">
								<p><span class="bold  font_16 ">Group: </span> <span class="bold blue font_16"><i>${monthlyModel.strGroupName}</i></span></p>
			
					<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyModel.strMonth}-${monthlyModel.year}</i></span></p>
					 <c:if test="${transactionList.size()>0}">
					<a href="#" title="Click here to see Proceedings !" onclick="ViewProceedings('${encMonthlyProgressId}','${group}')" class="bold blue font_16"><i><u>Proceddings</u></i></a>
					</c:if>
					</div>
				</div>
			</div>
         <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
         
       <%--   <div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
         <div class="hidden" id="encCategoryId">${encCategoryId}</div>  --%>
         
         
        <form:form id="form1" name="form1" modelAttribute="monthlyProgressModel"  method="post" autocomplete="off">
			<input type="hidden" name=numProjectPublicationId id="numProjectPublicationId" value="0" />
			<input type="hidden" id="encMonthlyProgressId" value="${encMonthlyProgressId}"/>

			<%--  <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/> --%>
			          
			             <div class="container">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info panel-info1">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">Monthly Progress Report (Group Level Activities)</h4>
										
									</div>
									</div></div></div></form:form></sec:authorize> 
									
			</div>
			
			<c:forEach items="${progressDetails}" var="data" varStatus="loop">			
			
					
					<div class="container">
						<fieldset id="">
							<legend class="info">${data.strCategoryName}</legend>
							<div id="main_${data.numCateoryId}"></div>	
								<script>
								getPreviewData('${data.numCateoryId}','${data.encCategoryId}','${data.encNumId}');
								</script>
								<c:forEach items="${data.objects}" var ="child">
									<div class="padl5Per">
										<fieldset id="">
											<legend class="info">${child.strCategoryName}</legend>
											<div id="main_${child.numCateoryId}"><c:if test=""></c:if><span id="viewDoc"></span></div>
											<script>
											getPreviewData('${child.numCateoryId}','${child.encCategoryId}','${child.encNumId}');
											</script>
										</fieldset>
									</div>
								</c:forEach>
							
							
						</fieldset><br/>
					</div>
				
	</c:forEach>
			</section>
			
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/progressReportWorkflow.js"></script>	
			
		<div class="row pad-top form_btn center" id="nextAction">
		</div>	
		
			
			
</body>
</html>
