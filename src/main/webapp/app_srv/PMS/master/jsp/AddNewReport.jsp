<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">	  
  
<style>
#queryNSheets {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#queryNSheets td, #queryNSheets th {
  border: 1px solid #ddd;
  padding: 8px;
}

#queryNSheets tr:nth-child(even){background-color: #f2f2f2;}

#queryNSheets tr:hover {background-color: #ddd;}

#queryNSheets th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #1B6596;
  color: white;
}
</style>
<script>
	$(document).on('click', '#Save', function(e) {
		
		var queries = $('.multipleQueries').map(function(){
		    return this.value;
		}).get().join('#@#@#@#@#'); 
		
		var sheets = $('.multiSheets').map(function(){
		    return this.value;
		}).get().join('#@#@#@#@#');
		
		
		$("#strQuery").val(queries);
		$("#strSheetNames").val(sheets);
		var flag= true;
		$('.multipleQueries').each(function(index){
				   if(!$(this).val()){
					   
					   swal("Oops","Please Enter Query at row No "+(index+1),"error");
					   flag=false;
					   return false;
				   }
				})
		if(flag == true){		
			$('.multiSheets').each(function(index){
					if(!$(this).val()){
						
						   swal("Oops","Please Enter Sheet Name at row No "+(index+1),"error");
						   flag=false;
						   return false;
					   }
			})
		}
		if($('#frm').form('validate')){
			
		
		$('#text_data').val($('#editor').html());
		$.ajax(
				{
				type: "POST",
				url: "/PMS/isDuplicateRecord",
				data: {
					"strEcode": $("#strEcode").val(),
				},
				success : function(response) {
					/* if(response){
						swal("Oops!", "E-code can not be Duplicate", "error");
					}
					else{ */
						$("#frm").attr("action", "SaveReportDetails");
						$("#frm").submit();
					/* } */
				 },
		        error: function(e){
		        	
	    		}
			}); 
	
		}
		else{
			return false;
		}
	});

	$(document).on('click', '#Back', function(e) {
		$("#frm").attr("action", "EditReport");
		$("#frm").submit();

	});
</script>

<script type="text/javascript">

	$(function() {
		 $("#strName").validatebox({
		 required: true,
		 validType: ['AlphaNumericWithSpecial','minLength[1]','maxLength[250]']
	});

	$("#strDescription").validatebox({
		 required: true,
		 validType: ['AlphaNumericWithSpecial','minLength[1]','maxLength[3000]']

	});
	
	$("#strEcode").validatebox({
		 required: true,
		 validType: ['AlphaNumericWithSpecial','minLength[1]','maxLength[100]']
	});
	
	/* $("#strQuery").validatebox({
		 required: true,

	}); */

	});
</script>

<script>
function deleteRecord(){
	
	var numOfRows = $('#queryNSheets tbody tr').length;
	
	if(numOfRows == 1){
		swal("Oops",'Delete Operation not possible',"error");
	}else{
		var $tbody = $("#queryNSheets tbody");
		 var $last = $tbody.find('tr:last');
		 $last.remove();
	}
}
</script>

<script>
function addMoreRows() {
	//alert("here");
	
	var flag= true;
	$('.multipleQueries').each(function(index){
			   if(!$(this).val()){
				   //alert('Please Enter Query at row No '+(index+1));
				   swal("Oops","Please Enter Query at row No "+(index+1),"error");
				   flag=false;
				   return false;
			   }
			})
	if(flag == true){		
		$('.multiSheets').each(function(index){
				if(!$(this).val()){
					
					   swal("Oops","Please Enter Sheet Name at row No "+(index+1),"error");
					   flag=false;
					   return false;
				   }
		})
	}
			
			
	
		if(flag == true){
			var recRow = '<tr><td class=""><input type="text" id="sheet" class="multiSheets" style="width:100%;"/></td><td class=""><textarea id="query" class="multipleQueries" style="width:100%;font-size:xx-large;"></textarea></td></tr>';
			jQuery('#addedRows').append(recRow);
		}
	
}
</script>
<script>
	$(document).ready(function() {
		
		$('#schemeDiv').hide();
		$('#strDescription').val('');
		$('#strQuery').val('');
		
		/*toggle scheme div*/
		$(document).on('change','#numScheme',function(){
		 var checkEle=$(this).prop('checked');
			if(checkEle)
				{
				$('#schemeDiv').slideToggle();
				}
			else
				$('#schemeDiv').slideToggle();
		});
		
	});
</script>




<script>
	$(document).on('click', '#View', function(e) {
		printClick();
	});

	function resetval() {
		$('#proID').val(0);
		$('#proID1').val(0);

	}
</script>

<script>
$(document).ready(function() {
	$('.select2Option').select2({width:"100%"});
});
</script> 

</head>
<body>

<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">Add New Report</li>
		</ol>
	</div>
</div>

<div class="row ">
		<%----%>
		 </div>
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
		
			<form:form action="AddNewReport" id="frm" name="frm" method="post" modelAttribute="reportMasterModel">	
				 <form:hidden path="strQuery"/>
				<form:hidden path="strSheetNames"/> 	
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Add New Report</h4></div>
					<div class="panel-body text-center">
			
					<div class="row pad-top" >
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<form:label path="strName">Name<span style="color:red;">*</span></form:label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										
										<form:input type="text" path="strName" class="input-field"></form:input>
										
										</div>

									</div>
								</div>
								
								<div class="row " >
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<form:label path="strDescription"> Description<span style="color:red;">*</span></form:label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										
													<form:input type="text" path="strDescription" class="input-field"></form:input>
										
										</div>

									</div>
								</div>

					<div class="row " >
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><form:label path="numInterfaceId">Interface Name</form:label>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">

												<form:select path="numInterfaceId" id="proID1"
													class="select2Option" style="width: 100%">
													<form:option value="0">---------------------Interface Name---------------------</form:option>
													<c:forEach items="${interfaceDetails}" var="l">

														<form:option value="${l.numInterfaceId}">${l.strInterfaceName}</form:option>
													</c:forEach>

												</form:select>

											</div>
										</div>

									</div>
								</div>
								
									<div class="row " >
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

										<form:label path="strEcode"> E-Code<span style="color:red;">*</span></form:label>										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										
										<form:input type="text" path="strEcode" class="input-field"></form:input>
										</div>

									</div>
								</div>
								
								<div class="row " >
									<%-- <div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

										<form:label path="strQuery"> Query<span style="color:red;">*</span></form:label>									</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										
										<form:textarea  path="strQuery" class="input-field"></form:textarea>
										</div>

									</div> --%>
									
									<table id="queryNSheets" style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										
								<th class="nobreak" width="40%"><b>Sheet Name</b></th>
								<th class="nobreak" width="60%"><b>Query</b></th>
									</tr>
								</thead>
								<tbody id="addedRows">

					<tr class="tr_clone">
						<td><input id="sheet" type="text" class="multiSheets"
							style="width:100%;" /></td>
						<td><textarea id="quary" class="multipleQueries"
								style="width:100%;font-size:xx-large;"></textarea>

						</td>
						

					</tr>


				</tbody>
							</table>
								</div>
					 <div class=" pad-right double" style="float:right;"><input type="button" class=" btn btn-primary font_14 addMore" value="Add More" onclick="addMoreRows();"/> <input type="button" class=" btn btn-primary font_14 deleteLastRecord" value="Delete Record" onclick="deleteRecord();"/></div> 
		
					
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="Save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						
						<button type="button" class="btn btn-primary font_14" id="Back">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Back
						</button>
						
						
						
					</div>
					</div> 
					</div>
					
					</div>
					</div>
		</form:form>
		
		<c:out value="${inserted}">${inserted}</c:out>
	<!-- </div> -->
	<!--End of panel-->
	<!--Start data table-->
	
</div>
</section>

	
</body>
</html>