<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	

<html lang="en">
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
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script>
	  $(document).on('click','#Save',function(e)
	  {
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
					
	if (flag == true) {
							$('.multiSheets').each(
									function(index) {
										if (!$(this).val()) {

											swal("Oops",
													"Please Enter Sheet Name at row No "
															+ (index + 1),
													"error");
											flag = false;
											return false;
										}
									})
						}
						if (flag == true) {
							/* if ($('#frm').form('validate')) { */
								if ($('#reportID').val() == 0) {
									swal("Oops!", "Please select Report",
											"error");
									return false;
								}
								var str = $('#query').val();
								$('#text_data').val($('#query').html());
							/* 	var scheme = $('#schemeMapSelect').val();

								var flagValue = $('#schemeFlag').val(); */

								/* if ($('#addScheme').is(":checked")
										|| $('#addNewScheme').is(":checked")) { */
									/* if (scheme == 0) {
										swal("Oops!", "Please select scheme",
												"error");
									} else if (!str || 0 === str.length) {
										swal("Oops!", "Query can not be blank",
												"error");
									} */ /* else {

										
										$("#frm").attr(
												"action",
												"AddSchemeSpecificReport?schemeId="
														+ scheme);
									
										$("#frm").submit();
										
									} */

								}

								/* else { */
								/* 	if (scheme == 0) { */
										$("#frm").attr("action", "SaveReport");
									/* } */

								/* 	else {

										$("#frm").attr(
												"action",
												"SaveSchemeSpecificReport?schemeId="
														+ scheme);
									} */

									$("#frm").submit();

								/* }
							} */
						/* } */ /* else {
							return false;
						} */
					});

	$(document)
			.on(
					'click',
					'#Delete',
					function(e) {
						var scheme = $('#schemeMapSelect').val();
						
						if ($('#reportID').val() == null
								|| $('#reportID').val() == 0) {

							swal("Oops!", "Please select Report", "error");
						} else {
							if (scheme == 0) {
								var answer = confirm("Are you sure you want to delete this report?");
								if (answer) {
									$("#frm").attr("action",
											"DeleteReport?schemeId=" + scheme);
									$("#frm").submit();
								}
							} else {
								var answer = confirm("Are you sure you want to delete this Scheme specific Report?");
								if (answer) {
									$("#frm").attr("action",
											"DeleteReport?schemeId=" + scheme);
									$("#frm").submit();
								}
							}
						}

					});
</script>
    
    <script type="text/javascript">

	$(function() {


	$("#strDescription").validatebox({
		 validType: ['AlphaNumericWithSpecial','minLength[1]','maxLength[3000]']
	});
	
	$("#strQuery").validatebox({
		 required: true,

	});

	});
</script>
 
    <script>
    
     $(document).on('change','#schemeMapSelect',function(e){
     var value=$('#reportID :selected').val();
     var scheme = $('#schemeMapSelect').val();
     
     if($.trim(value)=="0"){
     
     }
     else if($('#addScheme').is(":checked") || $('#addNewScheme').is(":checked") ){
    	
    	 $('.multipleQueries').html(""); 
    	 $('.multiSheets').val(""); 
    	 //$('#queryNSheets').children( 'tr:not(:first)' ).remove();
    		var numOfRows = $('#queryNSheets tbody tr').length;
    		
    		if(numOfRows>1){
    		
    			var $tbody = $("#queryNSheets tbody");
    			 var $last = $tbody.find('tr:last');
    			 $last.remove();
    		}
    	
     }
     else{
    	 
	     var html = $(".multipleQueries").html();
	     
	     if($.trim(html)=="")
	     {
	     	populateSchemeTemplate($.trim(value),scheme);
	     }
     	else
     	{	 	var answer = confirm('Are you sure you want to overwrite?');
			if (answer)
			populateSchemeTemplate($.trim(value),scheme);
			else{
				$("#schemeMapSelect").val(0).trigger("liszt:updated");
				
			}
			
     	}
			
	 }
	});
    
    </script>


<script>
    
     $(document).on('change','#reportID',function(e){
     var value = $('#reportID').val();
     if($.trim(value)=="0"){
     
     }
     else{
	     var html = $("#editor").html();
	     if($.trim(html)=="")
	     {
	     	populateTemplate($.trim(value));
	     }
     	else
     	{
		 	var answer = confirm('Are you sure you want to overwrite?');
			if (answer)
			 populateTemplate($.trim(value));
			/* else{
				$("#schemeMapSelect").val(0).trigger("liszt:updated");
			
			} */
			
     	}
			
	 }
	});
    
    </script>


<script type="text/javascript">


function populateSchemeTemplate(index,scheme){
	
	
	    var txtData="";
	   var sheetNames="";
	   var Finalquery="";
	   var FinalSheet="";
	if(scheme==0){
		
		$("#desc").show();
		$("#interface").show();
	}
	else{
		$("#desc").hide();
		$("#interface").hide();
	}
	   
		
		$.ajax(
   		  {
	        type: "GET",
	        url: "/PMS/ShowSchemeReport/"+index+"/"+scheme,
	        success: function(response)
	        { 
	        	
	        	$.each(response, function(key, value) {
 	        		
 	        	    if(key=='query')
 	        	    	{
 	        	    	txtData=value;
 	        	    	}
 	        	    
 	        	    if(key=='sheetNames'){
 	        	    	sheetNames=value;
 	        	    }
 	        	   
 	        	});	
	        	
	        	if(txtData.includes("#@#@#@#@#")==true){
	        		
					var singleQuery = txtData.split("#@#@#@#@#");
					var singleSheet = sheetNames.split("#@#@#@#@#");
					
					$('#addedRows').empty();
					for (var x = 0; x < singleQuery.length; x++) {
						Finalquery = singleQuery[x];
						FinalSheet = singleSheet[x];
						
						var recRow = '<tr><td class=""><input type="text" id="sheet" class="multiSheets" value="'+FinalSheet+'" style="width:100%;"/></td><td class=""><textarea id="query" class="multipleQueries" style="width:100%;font-size:xx-large;">'+Finalquery+'</textarea></td></tr>';
						$('#addedRows').append(recRow);
					}
					$("#addDelete").show();

					//$('#query').html(txtData);
					
					//$('#sheet').val(sheetNames);
        	 }
        	 else{
        		 $('#addedRows').empty();
        		  if(sheetNames==null){
        			  sheetNames="";
			
			} 
        		 var recRow = '<tr><td class=""><input type="text" id="sheet" class="multiSheets" value="'+sheetNames+'" style="width:100%;"/></td><td class=""><textarea id="query" class="multipleQueries" style="width:100%;font-size:xx-large;">'+txtData+'</textarea></td></tr>';
					$('#addedRows').append(recRow);
        		 	$("#addDelete").show();
        		//	$('#query').html(txtData);
					//$('#strDescription').val(desc);
					
        	 }
	         //$('#query').html(response); 
	        },
   		  }); 
}

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
			
			
	
		if(flag == true){
			var recRow = '<tr><td class=""><input type="text" id="sheet" class="multiSheets" style="width:100%;"/></td><td class=""><textarea id="query" class="multipleQueries" style="width:100%;font-size:xx-large;"></textarea></td></tr>';
			jQuery('#addedRows').append(recRow);
		}
	
}
</script>
<script>
   
   function populateTemplate(index){
	 
	   var txtData="";
	   var listData2="";
	   var desc="";
	   var interfaceName="";
	   var interfaceId="";
	   var sheetNames="";
	   var Finalquery="";
	   var FinalSheet="";
		$.ajax(
        {
	        type: "GET",
	        url: "/PMS/ShowReports/"+index,
	        success: function(response)
	        {  
	        	$.each(response, function(key, value) {
	        		
	        		/* if(key=='flag'){
	        	    	$('#schemeFlag').val(value);
	        	    } */
	        	    if(key=='txtData')
	        	    	{
	        	    	txtData=value;
	        	    	}
	        	    if(key=='Description'){
	        	    	desc=value;
	        	    }
	        	    if(key=='SheetNames'){
	        	    	sheetNames=value;
	        	    }
	        	    
	        	   if(key=='InterfaceName'){
	        	    	interfaceName=value;
	        	    	
	        	    	
	        	    }
					 if(key=='InterfaceId'){
						 interfaceId=value;
						 
	        	    	$("#interfaceID").val(value).trigger("liszt:updated"); 
	        	    } 
	        	
	        	  /*   if(key=='schemeList')
	        	    	{
	        	    	listData2=value;
	        	    	
	        	    	} */
	        	});		
	        	
	        	
	        	 if(txtData.includes("#@#@#@#@#")==true){
						var singleQuery = txtData.split("#@#@#@#@#");
						var singleSheet = sheetNames.split("#@#@#@#@#");
						
						$('#addedRows').empty();
						for (var x = 0; x < singleQuery.length; x++) {
							Finalquery = singleQuery[x];
							FinalSheet = singleSheet[x];
							
							var recRow = '<tr><td class=""><input type="text" id="sheet" class="multiSheets" value="'+FinalSheet+'" style="width:100%;"/></td><td class=""><textarea id="query" class="multipleQueries" style="width:100%;font-size:xx-large;">'+Finalquery+'</textarea></td></tr>';
							$('#addedRows').append(recRow);
						}
						$("#addDelete").show();

						
						$('#strDescription').val(desc);
						/* $('#query').html(txtData); */
	        	 }
	        	 else{
	        		 $("#addDelete").show();
	        			$('#query').html(txtData);
						$('#strDescription').val(desc);
						$('#sheet').val(sheetNames);
	        	 }
					},

						complete : function(event) {
						
						/* $('#schemeMapSelect').empty();
						var options = "<option value=0>------------------------SELECT SCHEME-------------------------</option>";
						for (var i = 0; i < listData2.length; i++) {
							options += "<option value="+listData2[i].numSchemeId+">"
									+ listData2[i].strSchemeName + "</option>";
						}
						$('#schemeMapSelect').append(options);
						$('#schemeMapSelect').trigger("liszt:updated");
						var flag = $('#schemeFlag').val();
						if (flag == 0) {
							$('#addSchemediv').show();

							$('#schemeDiv').hide();
							$('#editSchemeTemplete').hide();

						} else {
							$('#addSchemediv').hide();
							$('#schemeDiv').show();
							$('#editSchemeTemplete').show();

						} */
					},

					error : function(e) {
						alert('Error: ' + e);
					}

				});
	}
</script>


<script>
   
   function populateAddSchemeTemplate(index){
	   
	   var txtData="";
	   var listData="";
	   var sheetNames="";
	   var Finalquery="";
	   var FinalSheet="";
		$.ajax(
        {
	        type: "GET",
	        url: "/PMS/ShowReport2/"+index,
	        success: function(response)
	        {  
	        	$.each(response, function(key, value) {
	        		if(key=='flag'){
	        	    	$('#schemeFlag').val(value);
	        	    }
	        		 if(key=='schemeList')
	        	    	{
	        	    	listData=value;
	        	
	        	    	}
	        	    if(key=='txtData')
	        	    	{
	        	    	txtData=value;
	        	    	}
	        	    if(key=='sheetNames')
        	    	{
        	    	sheetNames=value;
        	    	}
	        	   
	        	    
	        	});		
	        	if(txtData.includes("#@#@#@#@#")==true){
	        	var singleQuery = txtData.split("#@#@#@#@#");
				var singleSheet = sheetNames.split("#@#@#@#@#");
				
				$('#addedRows').empty();
				for (var x = 0; x < singleQuery.length; x++) {
					Finalquery = singleQuery[x];
					FinalSheet = singleSheet[x];
					
					var recRow = '<tr><td class=""><input type="text" id="sheet" class="multiSheets" value="'+FinalSheet+'" style="width:100%;"/></td><td class=""><textarea id="query" class="multipleQueries" style="width:100%;font-size:xx-large;">'+Finalquery+'</textarea></td></tr>';
					$('#addedRows').append(recRow);
				}
				$("#addDelete").show();
	        	}
	        	else{
	        			$("#addDelete").show();
	        			$('#query').html(txtData);
						$('#sheet').val(sheetNames);
	        	}
	         //$('#query').html(txtData); 
	        },
	       
	        complete:function(event){
	        	
	        	$('#schemeMapSelect').empty();
	        	var options="<option value=0>------------------------SELECT SCHEME-------------------------</option>";
	        	for(var i=0;i<listData.length;i++)
    			{
	        		
    		options+="<option value="+listData[i].numSchemeId+">"+listData[i].strSchemeName+"</option>";
    			}
	        	
    			$('#schemeMapSelect').append(options);
    			$('#schemeMapSelect').trigger("liszt:updated");
	        	var flag=$('#schemeFlag').val();
	        	if(flag==0)
	        		{
	        		$('#addSchemediv').show();
	        		
	        		$('#schemeDiv').hide();
       			 	$('#editSchemeTemplete').hide();

	        		
	        		}
	        	else{
	        		$('#addSchemediv').hide();
	        		$('#schemeDiv').show();
	        			 $('#editSchemeTemplete').show();


	        	    }
	        },
	        
	        error: function(e){
	        	alert('Error: ' + e);
        	}
        
        });
	}

    </script>
<script>
     $(document).ready(function() {
    	 
    	 $("#addDelete").hide();

    	          $('#userinfo-message').delay(5000).fadeOut();
    	   
    	 
    	 $("#frm").submit(function() {
    	    	   // Retrieve the HTML from the plugin
    	    	   var html = $('#query').html();
    	    	   // Put this in the hidden field
    	    	   $("#html").val(html);
    	    	});
    	 var htmltext=$("#html").val();
    	 
    	 $('#query').html(htmltext);
    	 
    	 
    
     $('#schemeDiv').hide();
	 $('#addSchemediv').hide();
	 $('#editSchemeTemplete').hide();
	 $('#strDescription').val('');
	 $('.multipleQueries').html('');
	 $('.multiSheets').val('');
		//$('#ecode').hide();

            });
    
    </script>




<script>
	  $(document).on('click','#View',function(e){
    	 printClick();
		});
	  
	  function resetval()
		{
			$('#reportID').val(0);
			$('#interfaceID').val(0);
	
		} 
    </script>

<script>
	  $(document).on('click','#Add',function(e){
		 
      document.frm.method="POST";
	  document.frm.action="/PMS/AddNewReport";
	  document.frm.submit();
	  });
</script>
<script>
$(document).on('click','#addScheme',function(e){
	
	if($('#addScheme').is(":checked")){
	
		$('#desc').hide();
		$('#interface').hide();
		
	
	  $.ajax(
		   		{
			        type: "POST",
			        url: "/PMS/getAllSchemesForReport",
		   		success: function(response)
		   		{
		   		  	$('#schemeDiv').show();
		   			$('#schemeMapSelect').empty();
		   			var options="<option value=0>------------------------SELECT SCHEME-------------------------</option>";
		   			for(var i=0;i<response.length;i++)
		   			{
		   				options+="<option value="+response[i].numSchemeId+">"+response[i].strSchemeName+"</option>";
		   			}
		   				$('#schemeMapSelect').append(options);
		   				$('#schemeMapSelect').trigger("liszt:updated");
			     },
			        
			       
				});
	}	else{
		//$('#ecode').hide();
		$('#schemeDiv').hide();
		$('#desc').show();
		$('#interface').show();
	}
			});



</script>
<script>
function checkOption(){
	 var value = $('#reportID').val();

	if (document.getElementById('editScheme').checked) {
		
		$("#desc").show();
		$("#interface").show();
		populateTemplate(value);

		}
		
		if (document.getElementById('addNewScheme').checked) {
			
			$("#desc").hide();
			$("#interface").hide();
			populateAddSchemeTemplate(value);
			

		}
	
	
	
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
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Create Report</li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
			<c:if test="${message != null && message != ''}">
				<c:choose>
					<c:when test="${status=='success'}">
						<div id="userinfo-message">
							<p class="success_msg">${message}</p>
						</div>
					</c:when>
					<c:otherwise>
						<div id="userinfo-message">
							<p class="error_msg">${message}</p>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<%-- <sec:authorize access="hasAuthority('WRITE_THRUSTAREA_MST')">	 --%>
			<form:form action="AddNewReport" id="frm" name="frm" method="post"
				modelAttribute="reportMasterModel">
				<form:hidden path="strQuery"/>
				<form:hidden path="strSheetNames"/>
				<input type="hidden" id="schemeFlag" value="" />
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Create Report</h4>
							</div>
							<div class="panel-body text-center">

								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><form:label
													path="numReportId">Select Report</form:label>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">

												<form:select path="numReportId" id="reportID"
													class="select2Option" style="width:100%">
													<form:option value="0">------------------------Select Report-------------------------</form:option>
													<c:forEach items="${reportTypelist}" var="l">

														<form:option value="${l.numReportId}">${l.strName}</form:option>

													</c:forEach>

												</form:select>
											</div>
										</div>
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<input type="button" id="Add" value="Add new Report" 
												class="btn btn-primary" name="Add" />

										</div>
									</div>
								</div>

								
								<div class="row " id="interface">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><form:label
													path="numInterfaceId">Interface Name</form:label>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">

												<form:select path="numInterfaceId" id="interfaceID"
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
								
										<div class="row " id="desc">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><form:label path="strDescription"> Description</form:label>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										
													<form:input class="input-field"
														type="text" path="strDescription" />
										
										</div>

									</div>
								</div>
								
										<div class="row " >
									<%-- <div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><form:label path="strQuery"> Query</form:label>:<span
												style="color: red;">*</span></label>
										</div>
									

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<form:textarea class="input-field"
										path="strQuery" rows="6" id="query"></form:textarea>
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
						<td><textarea id="query" class="multipleQueries"
								style="width:100%;font-size:xx-large;"></textarea>

						</td>
						

					</tr>


				</tbody>
							</table>
								</div>
<div id="addDelete" class=" pad-right double" style="float:right;"><input type="button" class=" btn btn-primary font_14 addMore" value="Add More" onclick="addMoreRows();"/> <input type="button" class=" btn btn-primary font_14 deleteLastRecord" value="Delete Record" onclick="deleteRecord();"/></div>
								<div class="row pad-top  form_btn">

									<button type="button" class="btn btn-primary font_14" id="Save" value="Save">
										<span class="pad-right"><i class="fa fa-folder"></i></span>Save
									</button>
									
								</div>
							</div>
						</div>

					</div>
				</div>
			</form:form>
			<%-- </sec:authorize> --%>

			<!-- </div> -->
			<!--End of panel-->
			<!--Start data table-->
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						
					</div>
					<!--End of datatable_row-->
				</div>
			</div>
		</div>
	</section>

	<!-- <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/ThrustArea.js"></script> -->
<script>resetval();</script>


</body>
</html>