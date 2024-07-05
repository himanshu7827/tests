<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="en">

<head>
<sec:csrfMetaTags />
<meta charset="utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

<title>PMS</title>
<!-- Disable back button of browser -->

<script type="text/javascript">


javascript:window.history.forward(1);

function openWindowWithPost(verb, url, data, target) {
	  var form = document.createElement("form");
	  form.action = url;
	  form.method = verb;
	  form.target = target || "_self";
	  if (data) {
	    for (var key in data) {
	      var input = document.createElement("textarea");
	      input.name = key;
	      input.value = typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
	      form.appendChild(input);
	    }
	  }
	  form.style.display = 'none';
	  document.body.appendChild(form);
	  form.submit();
}

function downloadDocument(documentId){
	openWindowWithPost('POST','/PMS/mst/downloadProjectFile',{"encNumId" : documentId},'_blank');
}

function downloadProposalFile(documentId){
	openWindowWithPost('POST','/PMS/mst/downloadProposalFile',{"encNumId" : documentId},'_blank');

}

/*Added by devesh on 29-12-23 to download Reco Sheet from project Id*/
function downloadRecoSheetDocument(encProjectId){
	openWindowWithPost('POST','/PMS/mst/downloadRecoSheetFile',{"encProjectId" : encProjectId},'_blank');
}
/* End of Function */
 
/*Added by devesh on 29-12-23 to validate Reco Sheet from project Id*/
function validateRecoSheetFile(encProjectId){
	return new Promise(function(resolve, reject) {
        // Make the AJAX request
        $.ajax({
            type: 'POST',
            url: '/PMS/mst/validateRecoSheetFile',
            contentType: 'application/json',
            data: {"encProjectId": encProjectId},
            success: function (response) {
                if (response) {
                    resolve(true);
                } else {
                    resolve(false);
                }
            },
            error: function (error) {
                console.log('Error:', error);
                reject(error);
            }
        });
    });
}
/* End of Function */

//Added by devesh on 27-09-23 to get proposal file from Rev Id
function downloadProposalFilebyRevId(documentId, numId) {
    var postData = {
        "encNumId": documentId,
        "numId": numId // Add your extra parameter here
    };

    openWindowWithPost('POST', '/PMS/mst/downloadProposalFilebyRevId', postData, '_blank');
}
//End of download function

function viewProjectDetails(url){
	var win = window.open(url, '_blank');
	if (win) {	    
	    win.focus();
	} else {	   
	    alert('Please allow popups for this website');
	}
}

function viewDetails(url){	
	openWindowWithPost('POST',url,{"applicationId" : $("#numId").val()},'_self');	
}
function viewInvoiceDetails(encInvoiceId){
	$('#invoiceDetail').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var tableData = "";
		$.ajax({
    		type : "POST",
    		url : "/PMS/mst/getInviceDetailsData",
    		data : "encInvoiceId=" + encInvoiceId,
    		success : function(response) {
    			console.log(response);
    			for(var i =0;i<response.length;i++){
 					var row = response[i];
     			    var sno=i+1;    			       			  
      			  /*   var InvAmt =row.numInvoiceAmt;
      			    var taxAmt =row.numTaxAmount;
      			    var totalInvAmt =row.numInvoiceTotalAmt; */
      			  	var InvAmt =row.strInvoiceAmt;
      			    var taxAmt =row.strTaxAmount;
      			    var totalInvAmt =row.strInvoiceTotalAmt;
      			    
 				/* tableData+="<tr><td>"+sno+"</td><td>"+row.strInvoiceRefno+"</td> <td>"+row.strInvoiceDate+"</td>";
 				tableData+="<td class='text-right'>"+convertAmountToINR(InvAmt)+"</td> <td class='text-right'>"+convertAmountToINR(taxAmt)+"</td> <td class='text-right'>"+convertAmountToINR(totalInvAmt)+"</td> </tr>";
 */
      			  tableData+="<tr><td>"+sno+"</td><td>"+row.strInvoiceRefno+"</td> <td>"+row.strInvoiceDate+"</td>";
   				tableData+="<td class='text-right'>"+InvAmt+"</td> <td class='text-right'>"+taxAmt+"</td> <td class='text-right'>"+totalInvAmt+"</td> </tr>";

    			} 
    			tableData+='</tbody>';
				$('#invoiceDetailsTbl tbody').empty();						
				$('#invoiceDetailsTbl').append(tableData);	
    		},
    		error : function(e) {
    			alert('Error: ' + e);			
    		}
    	});					
	});	
	
}

function milestoneActivityDetails(milestoneId){
	$('#milestoneDetails').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var tableData = "";
		$.ajax({
    		type : "POST",
    		url : "/PMS/mst/milestoneActivityBymilestoneId",
    		data : "numId=" + milestoneId,
    		success : function(response) {
    			//console.log(response);
    			 for(var i =0;i<response.length;i++){
 					var row = response[i];
     			    var sno=i+1;    			       			  
      			   
      			    
     			   tableData+="<tr><td>"+sno+"</td><td>"+row.description+"</td> ";
     			   if(row.startDate){
     				   tableData+="<td>"+row.startDate+"</td>";
     			   }
     			   else{
     				   tableData+="<td></td>";

     			   }
    				if(row.endDate){
    					tableData+="<td>"+row.endDate+"</td>";
    				}
    				else{
    					tableData+="<td></td>";

    				}
    				if(row.strModuleName && row.strModuleDescription){
    					tableData+="<td><p>"+row.strModuleName+"</p><p ><b>Module description:</b>"+row.strModuleDescription+"</p></td></tr>";
    				}
    				else{
    					tableData+="<td></td>";
    				}    			
    			}  
    			tableData+='</tbody>';
				$('#datatable1 tbody').empty();						
				$('#datatable1').append(tableData);	
    		},
    		error : function(e) {
    			/* alert('Error: ' + e);		 */	
    		}
    	});					
	});	
	
}

function milestoneDetails(encMilestoneId){
	$('#milestoneModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var tableData = "";
		$.ajax({
    		type : "POST",
    		url : "/PMS/mst/getMilestoneDetail",
    		data : {"encMilestsoneId": encMilestoneId},
    		success : function(row) {
    			
     			   tableData+="<tr><td>"+row.milestoneName+"</td> ";
     			   if(row.expectedStartDate){
     				   tableData+="<td>"+row.expectedStartDate+"</td>";
     			   }else{
     				   tableData+="<td></td>";
     			   }
     			   tableData+="<td>"+row.strDesription+"</td>";		 
    			 
    			
				$('#milestoneTbl tbody').empty();						
				$('#milestoneTbl').append(tableData);	
    		},
    		error : function(e) {    			
    		}
    	});					
	});	
	
}

function viewTeamDetails(encId){
	 $
		.ajax({ 
			
			type : "GET",
			url : "/PMS/mst/getProjectTeamDetails/"+encId,
		
			success : function(response) {
	            /*  console.log(response); */
	               if(response){
	            	  var datascource = jQuery.parseJSON(response);

	   	               $('#chart-container').empty();
	   	               $('#chart-container').orgchart({      
	   	            	   
	   	                 'pan': true,
	   	                 'data' : datascource,
	   	                 'nodeContent': 'title',
	   	             	/*  'nodeClicked': onNodeClicked, */
	   	                 'createNode': function($node, data) {
	   	                   $node.on('click', function(event) {
	   	                     if (!$(event.target).is('.edge, .toggleBtn')) {
	   	                       var $this = $(this);
	   	                       var $chart = $this.closest('.orgchart');
	   	                       var newX = window.parseInt(($chart.outerWidth(true)/2) - ($this.offset().left - $chart.offset().left) - ($this.outerWidth(true)/2));
	   	                       var newY = window.parseInt(($chart.outerHeight(true)/2) - ($this.offset().top - $chart.offset().top) - ($this.outerHeight(true)/2));
	   	                       $chart.css('transform', 'matrix(1, 0, 0, 1, ' + newX + ', ' + newY + ')');
	   	                     }
	   	                   });
	   	               
	   	                 }              

	   	               });   
	               }
	               else{
	            	   sweetSuccess('Team Detail is unavailable');
	               }				 
			},
			error : function(e) {
				 console.log(e);
			}
		});  


	
}
function convertToIndianCurrency(inputtext){
	 inputtext=$.trim(inputtext);
	  var lastThree = inputtext.substring(inputtext.length-3);
	  var otherNumbers = inputtext.substring(0,inputtext.length-3);
	  if(otherNumbers != '')
	      lastThree = ',' + lastThree;
	  var result = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree;

	 return result;
}

function convertINRToAmount(amountInINR){
	amountInINR = amountInINR.replace(/,/g, '');
	amountInINR = amountInINR.replace(/₹/g, '');
	amountInINR = amountInINR.replace('?', '');	
	return amountInINR.trim();
}

function convertAmountToINR(amount){
	var amountInINR =0;
	 $.ajaxRequest.ajax({ 
		
		type : "GET",
		url : "/PMS/convertAmountToINR",
		async: false,
		data:{"amount":amount},
		success : function(response) {
			amountInINR=  response;
			amountInINR= amountInINR.replace("?", "₹");
             
		},
		error : function(e) {
			console.log(e);
			
		}
	})
	return amountInINR;
}

</script>
<script>
function roundUpTo2Decimal(value, exp) {
	  if (typeof exp === 'undefined' || +exp === 0)
	    return Math.round(value);

	  value = +value;
	  exp = +exp;

	  if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0))
	    return NaN;

	  // Shift
	  value = value.toString().split('e');
	  value = Math.round(+(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp)));

	  // Shift back
	  value = value.toString().split('e');
	  return +(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp));
	}



</script>

<script src="/PMS/resources/app_srv/PMS/global/js/moment.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/datetime-moment.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/jquery3.3.1.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/xlsx.full.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/exceljs.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/FileSaver.min.js"></script>   
<script src="/PMS/resources/app_srv/PMS/global/js/new/ajax-setup.js"></script>  <!-- Added by devesh on 09-10-23 to get special characters from inputs-->
<script src="/PMS/resources/app_srv/PMS/global/js/new/bootstrap.min-3.4.0.js"></script> 
<script src="/PMS/resources/app_srv/PMS/global/js/globalRequest.js"></script> 
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/bootstrapValidator.css">	
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/validateBox.css">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/new/font-awesome.min.css"> 
<link rel="stylesheet" type="text/css" href="/PMS/resources/app_srv/PMS/global/css/mobile.css">
<link rel="stylesheet" type="text/css" href="/PMS/resources/app_srv/PMS/global/css/mobileInternal.css">
<link rel="stylesheet" type="text/css" href="/PMS/resources/app_srv/PMS/global/css/core.css">
<link rel="stylesheet" type="text/css" href="/PMS/resources/app_srv/PMS/global/css/responsive.css">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/new/bootstrap.min-3.3.7.css">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/style.bundle.css">	
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/new/style.css">	
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/home_form2.css">
<link href="/PMS/resources/app_srv/PMS/global/css/new/style-responsive.css" rel="stylesheet"/> 
<link href='//fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/new/font.css" type="text/css"/>
<link href="/PMS/resources/app_srv/PMS/global/css/font-awesome.min.css" rel="stylesheet"> 
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/new/morris.css" type="text/css"/>
<link rel="stylesheet"href="/PMS/resources/app_srv/PMS/global/css/select2.css">	
<link  rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css//sweetalert.min.css" />
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/DataTable/dataTables.bootstrap.min.css">		
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/jquery-ui.css">	
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/DataTable/fixedColumns.dataTables.min.css">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/mobile_new.css">
<!-- <link rel="stylesheet" type="text/css"  href="/PMS/resources/app_srv/PMS/global/css/daterangepicker.css"> -->
<!--  <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">  -->
<!-- //Js -->
<!-- <script src="/PMS/resources/app_srv/PMS/global/js/new/jquery-3.3.1.js"></script> -->

<script src="/PMS/resources/app_srv/PMS/global/js/new/bootstrap.min-3.4.0.js"></script> 
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/md5.js"></script>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/html5shiv.js"></script>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/respond.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/raphael-min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/morris.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/bootstrap.js"></script> 
<script src="/PMS/resources/app_srv/PMS/global/js/new/jquery.dcjqaccordion.2.7.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/scripts.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/jquery.slimscroll.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/jquery.nicescroll.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/new/jquery.scrollTo.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/messageResource.js"></script>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/jquery.easyui.js"></script>
<!-- <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/ValidationFunctions.js"></script> -->
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/select2.min.js"></script>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/bootstrapValidator.js"></script> 
<script src="/PMS/resources/app_srv/PMS/global/js/sweetalert.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/jquery.dataTables.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/dataTables.bootstrap4.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/dataTables.fixedColumns.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/moment.min.js"></script>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/daterangepicker.min.js"></script> -->
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/OrgChart/jquery.orgchart.js"></script>
	 
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/dataTables.buttons.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/buttons.flash.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/jszip.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/pdfmake.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/vfs_fonts.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/buttons.html5.min.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/buttons.print.min.js"></script>
<!-- code for favicon -->
<!-- <link rel="apple-touch-icon" sizes="57x57"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/favicon-16x16.png">
<link rel="manifest"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage"
	content="/NewMedDev/resources/app_srv/NMD/global/img/favicon/ms-icon-144x144.png"> -->
<meta name="theme-color" content="#ffffff">
<!-- favicon ends here -->
<script>
$(document).ready(function() {
$('#userinfo-message').delay(5000).fadeOut();
///Only for div,td and span values
convertToLakh();
convertToLakhWithoutLabel();

});

function convertToLakh(){
	$(".convert_lakhs").each(function() {	
		
		var fieldId=this.id;
		var fieldValue = this.innerText;
		//console.log("fieldId "+fieldId +" "+"fieldValue"+fieldValue);
		if(fieldId){
			var convertedStringAmount = convertINRToAmount(fieldValue);
			//console.log(convertedStringAmount);
			var amount = (parseFloat(convertedStringAmount))/100000;
			//console.log(amount);
			//console.log(roundUpTo2Decimal(amount));
			var convertedIntegerAmount=convertAmountToINR(roundUpTo2Decimal(amount,2));
			//console.log(convertedIntegerAmount);
			$("#"+fieldId).text(convertedIntegerAmount +' Lakhs');
		}
	});
}


function convertToLakhWithoutLabel(){
	$(".convertLakhs").each(function() {	
		
		var fieldId=this.id;
		var fieldValue = this.innerText;
		//console.log("fieldId "+fieldId +" "+"fieldValue"+fieldValue);
		if(fieldId){
			var convertedStringAmount = convertINRToAmount(fieldValue);
			//console.log(convertedStringAmount);
			var amount = (parseFloat(convertedStringAmount))/100000;
			//console.log(amount);
			//console.log(roundUpTo2Decimal(amount));
			var convertedIntegerAmount=convertAmountToINR(roundUpTo2Decimal(amount,2));
			//console.log(convertedIntegerAmount);
			$("#"+fieldId).text(convertedIntegerAmount);
		}
	});
}
</script>


</head>


<script>
function sweetSuccess(title,msg){
	swal({
		  title: title,
		  text: msg,
		  timer: 3000
		});
}

function confirmWithRedirect(title,msg,type,successURL,dismissURL){
	
	  swal({
		  title: title,
		  text: msg,
		  type: type,
	      buttons: [
	                'No',
	                'Yes'
	              ],	     
	    }).then(function(isConfirm) {
	      if (isConfirm) {
			if(successURL){
				window.location.href=successURL;
			}
	      } else {
	       	if(dismissURL){
	       		window.location.href=dismissURL;
	       	}
	      }
	    }); 
		
}



$(document).ready(function() {
	
	$('.currency-inr').each(function() { 
	    var monetary_value = $(this).text(); 
	    var i = new Intl.NumberFormat('en-IN', { 
	        style: 'currency', 
	        currency: 'INR' ,
	        minimumFractionDigits: 0,
	        maximumFractionDigits: 20
	    }).format(monetary_value); 
	    $(this).text(i); 
	}); 
});
	
function loadClientData(clientId,projectId,endUserId){
	$.ajax({
		type : "POST",
		url : "/PMS/mst/getClientDetailsByClientId",
		data : "encClientId=" + clientId+"&encProjectId="+projectId+"&endUserId="+endUserId,
		async:false,
		success : function(response) {
			/* console.log(response); */
			$('#clientName').text(response.clientName);
			$('#shortCode').text(response.shortCode);
			$('#address').text(response.clientAddress);
			$('#contactNo').text(response.contactNumber);
			if(response.endUserName  != null && response.endUserName!= 'undefined'){
				$('#endUserName').text(response.endUserName);
				if(response.endUserShortCode!= null && response.endUserShortCode!= 'undefined')
					$('#endUserName').append("("+response.endUserShortCode+")");
				$('#endUserName').closest('tr').removeClass('hide');
				}else{
					$('#endUserName').closest('tr').addClass('hide');
				}
			var data="";
			$('#clientcontactDetails').html('');
			response.contactPersonList.forEach(function(item){
				 data+="<tr><td>"+item.strContactPersonName+"</td>";
				 data+="<td>"+item.strDesignation+"</td>"
				 data+="<td>"+item.strMobileNumber+"</td>"
				 data+="<td>"+item.strEmailId+"</td>"
				 data+="<td>"+item.strOfficeAddress+"</td></tr>"
				});
			//console.log(data);
			$('#clientcontactDetails').append(data);
		},
		error : function(e) {
			/* alert('Error: ' + e);		 */	
		}
	});					
}

</script>

<script>

function viewEmployeeDetails(encId){
console.log(encId);
	 $.ajaxRequest.ajax({			
			type : "POST",
			url : "/PMS/mst/employeeBasicDetails",
			data:{"encEmployeeId":encId},
		
			success : function(response) { 
	            	  var data = jQuery.parseJSON(response);
	   	               $('#employeeDetailsTableModelDiv').empty();
	   	               if(data.result == "Success"){
	   	            	   var details ="<table class='table table-striped table-bordered'> <tr> <th> Employee Name </th> <td> "+data.employeeName+" </td> </tr>";
	   	            		details+="<tr><th>Employee Id </th> <td> "+data.empId+" </td> </tr>";
				        	 details+="<tr><th>Designation </th> <td> "+data.employeeDesignation+" </td> </tr>";
	   	            		details+="<tr><th> Email </th> <td>"+data.employeeEmail+" </td> </tr>";
	   	            		//details+="<tr><th> Mobile No. </th> <td> "+data.mobileNo+"</td> </tr>";
	   	            		details+="<tr><th> Group Name </th> <td>"+data.groupName+" </td> </tr>";
	   	            		//details+="<tr><th> Joining Date </th> <td> "+data.joiningDate+"</td> </tr>";	
	   	            		
	   	            		$('#employeeDetailsTableModelDiv').append(details);
	   	               }          
	               	               
			},			
			error : function(e) {
				alert(e);
			}
		});  	
}

</script>

<style>

.main_div1{
overflow-x:hidden;

}
 .content-wrapper{
min-height: 100%;
} 
.cf {
    padding-right: 0px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
}

  #chart-container {
    overflow: auto !important;
  }

</style>

<body class="home main_div1 ">

	<div class="wrapper">
		
		<div class="row">
			<tiles:insertAttribute name="header" />
		</div>


		<div class="container-fluid">
		<div class="row content-wrapper ">
			<tiles:insertAttribute name="body" />
			
					<!-- User Details Global Modal Start -->
		
		      <div class="modal amount_receive_deatil_modal" id="employeeDetailsModel"
				tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
		              <div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="label.employee.details" /></h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>
		        <div class="modal-body">
		         	<div id="employeeDetailsTableModelDiv"></div>
		        </div>       
		      </div>
		      
		    </div>
		  </div>
   <!-- User Details Global Modal End -->
	
						
		</div>

		</div>


		
		<div class="container-fluid cf">	
		<div class="push"></div>	
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
	
</body>


</html>


