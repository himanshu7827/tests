
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
table.dt-rowReorder-float{position:absolute !important;opacity:0.8;table-layout:fixed;outline:2px solid #888;outline-offset:-2px;z-index:2001}
tr.dt-rowReorder-moving{outline:2px solid #555;outline-offset:-2px}
body.dt-rowReorder-noOverflow{overflow-x:hidden}
table.dataTable td.reorder{text-align:center;cursor:move}
</style>
	
	</head>
 
 
<body>

<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">Copyright</li>
		</ol>
	</div>
</div>
<div class="row ">
		
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
		<div class="container pad-top pad-bottom">
        <div class="panel panel-info  ">
<div class="panel-body">
<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strProjectName}</i></span></p>
<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyProgressModel.strMonth}-${monthlyProgressModel.year}</i></span></p>
</div>
</div>
</div> 
			<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">	
			<div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
        <%--  <div class="hidden" id="encCategoryId">${categoryId}</div>  --%>
		<form:form id="form1" modelAttribute="copyRightModel" action="/PMS/saveUpdateCopyRight" method="post">
			<form:hidden path="numCopyRightID"/>
			
			
			
				 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
				 <form:hidden path="encCategoryId"  value="${categoryId}"/>
			
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Copyright Details</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class">Title :<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i> 
									<form:input class="input-field" path="strTitle" placeholder="Title of Copyright"/>
									<form:errors path="strTitle" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
								<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" >Date : <span
									style="color: red;">*</span></label>
							</div>
							
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dateOfCopyright" placeholder="Date of Copyright"/>							
								<form:errors path="dateOfCopyright" cssClass="error" />
								</div>
							</div>
							
							
						</div>
					</div>
						
						<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" >Agency : <span
									style="color: red;">*</span></label>
							</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i>
									<form:input class="input-field"  path="strAgency" placeholder="Agency of Copyright"/>
									<form:errors path="strAgency" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
										<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify"><label class="label-class"><spring:message
														code="forms.status"/>:<span
														style="color: red;">*</span></label>
												</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
														<form:radiobutton onclick="filedate()" class="toggle-on" path="strCopyrightFiledAwarded" value="Y"
														 id="isfiledy" name="radio"/>
														<form:label path="strCopyrightFiledAwarded" for="toggle-on1"
															class="btn inline  zero round no-pad">
															<span>Filed</span>
														</form:label>
												<div>&nbsp; &nbsp; &nbsp; </div>
													<!-- <div class="col-md-3 col-lg-3 col-sm-5 col-xs-13 "> -->
														<form:radiobutton onclick="awarddate()" class="toggle-off" path="strCopyrightFiledAwarded" value="N"
															id="strIsAwardedy" name="radio" />

														<form:label path="strCopyrightFiledAwarded" for="toggle-off2"
															class="btn round inline zero no-pad">
															<span class="">Awarded</span>
														</form:label>
													</div>
												</div>
											</div>
										
								<div id="filingdate">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.filingDate"/>:<span
														style="color: red;">*</span></label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
													<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" id="dtFilingDate" name="dtFilingDate" path="dtFilingDate" autocomplete="off" />							
								<form:errors path="dtFilingDate" cssClass="red" />
														
												</div>
											</div>
										</div>
										</div>
										
										<div id="Awarddate">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class">Award Date:<span
														style="color: red;">*</span></label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
													<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" id="dtAwardDate" name="dtAwardDate" path="dtAwardDate" autocomplete="off"/>							
								<form:errors path="dtAwardDate" cssClass="red" />
														
												</div>
											</div>
										</div>
										</div>
										
										</div>
						<%-- <div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" >Award : <span
									style="color: red;">*</span></label>
							</div>
							
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="copyrightAwarded"  value="true" id="toggle-on1"	 />
									<form:label path="copyrightAwarded" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Yes</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="copyrightAwarded" value="false" id="toggle-off1" />

									<form:label path="copyrightAwarded" value="false" for="toggle-off"
										 class="btn round inline zero no-pad">
										<span class="">No</span>
									</form:label>
</div>
							</div>
				
						</div>
						</div> --%>
							<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" >Reference Number :<span
									style="color: red;">*</span> </label>
							</div> 
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i>
									<form:input class="input-field"  path="strReferenceNumber" placeholder="Reference Number of Copyright"/>
									<form:errors path="strReferenceNumber" cssClass="error" />
								</div>
							</div>
						</div>
						</div>		
					
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save as Draft
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						<button type="button" class="btn btn-success font_14" id="previewDetailsBtn">
							<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
						</button>
						<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
					</div>
					<div class="row pad-top" id="mainPrevNext">
						
						</div>
					</div>
					</div>
					</div>
					</div>
		</form:form>
 </sec:authorize> 
	
<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
			<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
			<div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type= "button" value="Delete" id="delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div>
			</sec:authorize> 
			
	
			<script>
			
	   $(document).ready(function() {
			$('#userinfo-message').delay(5000).fadeOut();
			$('#delete').click(function(){
				DegDelete();
			});
		});
	  
	  		 
	  function DegDelete(){
    var chkArray = [];
     
		     
		    $(".CheckBox:checked").each(function() {
		        chkArray.push($(this).val());
		    });
		    
		    var selected;
		    selected = chkArray.join(','); 
		    if(chkArray.length==1)
		    	{
		    if(selected.length >= 1){
		    	
		    	
		    	 swal({
				      title: "Are you sure you want to delete the record?",
				      icon: "warning",
				      buttons: [
				                'No',
				                'Yes'
				              ],
				      dangerMode: true,
				    }).then(function(isConfirm) {
				      if (isConfirm) {
					    	  $("#numCopyRightID").val(selected);
					    	 
					  		  submit_form_delete();
					      }
			  	
					      else{
					    	
					      return false;
			    }
		    });
		    }
		    else{
		        swal("Please select at least one checkbox to delete");  
		    }
		    	}
		    else{
		    	swal("Please select only one checkbox to delete");
		    	
		    }
		    
	  } 
function submit_form_delete()
{

 document.getElementById("form1").action = "/PMS/deleteCopyright";
document.getElementById("form1").method = "POST";
document.getElementById("form1").submit(); 
} 
 


</script>
		<table id="example" class="table table-striped table-bordered display select dataTable"
				style="width: 100%">
				
				<thead class="datatable_thead bold ">
				 
					<tr id="row">
						<th class="width10 check ">Select</th>
						<th class="width10 check ">S.No.</th>
						<!-- <th class="hidden" ></th> -->
						<th class="hidden" ></th>
						<th>Title</th>	
						<th>Date of Copyright</th>
						<th>Agency</th>
						<th>Status</th>
						<th>Date</th>
						<th>Reference Number</th>
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="ui-sortable">
						<c:forEach items="${data}" var="copy" varStatus="loop">
					 	<tr id="row-${loop.index}">
							<td align="center"><input type="checkbox" class="CheckBox" id="Checkbox" value="${copy.numCopyRightID}" autocomplete="off"></td>
							<td align="center">${loop.count}</td>
							<%-- <td class="hidden">${copy.numGroupCategoryId}</td> --%>
							<td class="hidden">${copy.numCopyRightID}</td>
							<td>${copy.strTitle}</td>
						    <td>${copy.dateOfCopyright}</td>
						    <td>${copy.strAgency}</td>
							<td>
								 <c:choose>
									<c:when test="${copy.strCopyrightFiledAwarded == 'Filed'}">Filed</c:when>
									<c:otherwise>Awarded</c:otherwise>									
								 </c:choose>
							 </td>
							<td>
								<c:choose>
									<c:when test="${copy.strCopyrightFiledAwarded == 'Filed'}">${copy.dtFilingDate}</c:when>
									<c:otherwise>${copy.dtAwardDate}</c:otherwise>									
								 </c:choose>
							</td>
							<td>${copy.strReferenceNumber}</td>
							
							<td>
							 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
							 </sec:authorize> 
							</td>

						</tr> 
					</c:forEach>
					
				</tbody>
			
			</table>
		<!-- 		<div class="row pad-top  form_btn center">
			<button type="button" class="btn btn-primary font_14 " >
			<span class="pad-right" ><i class="fa fa-folder"></i></span>Save Hierarchy
			</button>
			</div> -->

			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>
</div>
</section>

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/copyRight.js"></script>
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
	 
	
</body>
</html>