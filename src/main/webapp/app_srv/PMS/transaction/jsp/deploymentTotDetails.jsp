<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
#prog-file-upload{
	padding-top: 5%;
}
</style>
</head>
<body>
 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
	<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
		<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active">Deployment Details</li>
					</ol>
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
		        <div class="container pad-top pad-bottom">
        	<div class="panel panel-info  ">					
			<div class="panel-body">
					<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strProjectName}</i></span></p>
					<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyProgressModel.strMonth}-${monthlyProgressModel.year}</i></span></p>
					</div>
				</div>
			</div>
		<div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
        <%--  <div class="hidden" id="encCategoryId">${encCategoryId}</div> --%>
<form:form id="form" modelAttribute="deploymentTotDetailsModel"
							action="/PMS/saveUpdateDeploymentDetails" enctype="multipart/form-data" method="post">
<div class="container">
								<div class="panel panel-info panel-info1">
										<div class="panel-heading">
											<h3 class="text-center text-shadow">
												Deployment Details
											</h3>
										</div>
										<div class="panel-body">
													

<form:hidden path="numDeploymentId" id="numDeploymentId"/>	
<form:hidden path="numDeploymentIds" id="numDeploymentIds"/>
<form:hidden path="strDocumentIds" id="strDocumentIds"/>	
<form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
<form:hidden path="encCategoryId"  value="${encCategoryId}"/>		
<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.service.name"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<form:input class="input-field" placeholder="Service Name" path="strServiceName"  />
									<form:errors path="strServiceName" cssClass="error" />
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.service.description"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<form:textarea class="form-control " 
														path="strDescription" rows="5" placeholder="Description"></form:textarea>
														<form:errors path="strDescription" cssClass="error" />
							</div>
						</div>
</div>

<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.service.unit.sold"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<form:input class="input-field" placeholder="Number of units deployed/sold" path="numUnitsDeployedOrSold"  />
									<form:errors path="numUnitsDeployedOrSold" cssClass="error" />
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.date"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dtDeploymenTotDate" id="dtDeploymenTotDate" placeholder="Deployment Date"/>							
								<form:errors path="dtDeploymenTotDate" cssClass="error" />
								</div>
							</div>
						</div>
</div>


<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.agency.name"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<form:input class="input-field" placeholder="Agency Name" path="strAgencyName"  />
									<form:errors path="strAgencyName" cssClass="error" />
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							
														<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.agency.state"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<form:select path="numAgencyStateId" id="numAgencyStateId"
																class="select2Option">
																<form:option value="0">----Select State----</form:option>
																<c:forEach items="${StateList}" var="listState">
																	<form:option value="${listState.numStateId}">
																		<c:out value="${listState.strStateName}"></c:out>
																	</form:option>
																</c:forEach>
																<form:errors path="numAgencyStateId" cssClass="error" />
													</form:select>
							</div>
							
													</div>
</div>


<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.agency.city"/>:<!-- <span
									style="color: red;">*</span> --></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<form:input class="input-field" placeholder="Agency City" path="strAgencyCity"  />
									<form:errors path="strAgencyCity" cssClass="error" />
							</div>
							
						</div>
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="deployment.state"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<form:select path="numDeploymentStateId" id="numDeploymentStateId"
																class="select2Option">
																<form:option value="0">----Select State----</form:option>
																<c:forEach items="${StateList}" var="listState">
																	<form:option value="${listState.numStateId}">
																		<c:out value="${listState.strStateName}"></c:out>
																	</form:option>
																</c:forEach>
																<form:errors path="numDeploymentStateId" cssClass="error" />
													</form:select>
							</div>
						</div>
						
</div>




<div class="row pad-top">
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

									<label class="label-class"><spring:message code="deployment.city"/>:<!-- <span
									style="color: red;">*</span> --></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
									<form:input class="input-field" placeholder="Deployment City" path="strDeploymentCity"  />
									<form:errors path="strDeploymentCity" cssClass="error" />
							
							</div>
						</div>
</div>

 <div class="row pad-top" id="uploadFileDiv">
 
 
 
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<label class="label-class"><spring:message code="deployment.images"/>:</label>
												</div>
					
												 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<!-- <div class="input-container">
													<input type="file" class="input-field fileUpload" name="deploymentDocumentFile" id="deploymentDocumentFile" />
													</div> -->
													<a class="" href="#" onclick="getImageDetails();" data-image-id="" data-toggle="modal"
															data-title="" data-target="#prog-file-upload">Click here to upload</a>
												</div>
												</div>
										</div>
										
										
										<div class="row pad-top form_btn center">
										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save as Draft
										</button>
										<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button>
										<button type="button" class="btn btn-success font_14" id="previewDetailsBtn">
											<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
										</button>
										<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
									</div>
									<div class="row pad-top" id="mainPrevNext">
						
						</div>
									
									
									
									
									<!-- Modal for Projects -->
		<div class="modal dash-grpwise-bargraph-modal pad-top"
			id="prog-file-upload" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true"
			data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title center" id="">
							File Upload
						</h3>
						
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
					</div>

					<div class="modal-body ">
					
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class">Photo Caption:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								
								<form:input class="input-field" placeholder="Photo Caption" path="strDocumentCaption" id="strDocumentCaption" />
								<form:errors path="strDocumentCaption" cssClass="error" />
									
							</div>
							
						</div>
						
						</div>
						<div class="row pad-top">

						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class bold">Upload:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
								<input type="file" class="input-field fileUpload" name="progressReportQualityImages" id="progressReportQualityImages" onchange="uploadQualityImages(this)" />
								</div>
								
							</div>
							
						</div>
						</div>
						
							 <!-- Start Table for Uploaded images -->
		 <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 table-responsive pad-top pad-bottom hidden" id="imageTable">
				<fieldset class="fieldset-border">

						<legend class="bold legend_details"> Details :</legend>
					<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="button" value="Delete" id="deleteImages"
										class="btn btn-primary a-btn-slide-text">
								</div> 
							</div>
						</div>
		 <table class="table table-striped table-bordered" id="imagesDetailsTable">
												
												<thead class="datatable_thead">
													<tr>
													    
													    <th style="width: 1px;" class="check">Select</th>
													    <th width="95%">Uploaded Images</th>	
														
																		
													</tr>
												</thead>
											
											<tbody class="">
										
											</tbody>
											</table>
				
		 </fieldset>
		 </div>
		 
		 
		 <!-- End Table -->
		 
							
					
						
					</div>
	</div>
		 </div>
		 
		 
				 
			  </div>
			</div>
			</div>
			</div>
								
</form:form>
<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 table-responsive pad-top pad-bottom">
				<fieldset class="fieldset-border">

						<legend class="bold legend_details"> Details :</legend>
						
						<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="button" value="Delete" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div> 
							</div>
						</div>
									
									<table class="table table-responsive table-bordered compact display hover stripe" id="deploymentDetails">
												<!-- <table id="example" class="table table-responsive table-bordered compact display hover stripe" style="width: 100%"> -->
												<thead class="datatable_thead">
													<tr>
													    <th width="3%" class="check">Select</th>
													    <th width="2%"></th>
														<th width="15%" class="details-control">Service</th>
														<th width="10%" class="control">Date</th>	
														<th width="15%" class="control"><spring:message code="deployment.agency.name"/></th>
														<th width="10%" class="none"><spring:message code="deployment.agency.state"/></th>
														<th width="10%" class="none"><spring:message code="deployment.agency.city"/></th>
														<th width="10%" class="none"><spring:message code="deployment.state"/></th>
														<th width="10%" class="none"><spring:message code="deployment.city"/></th>
														<th width="10%" class="control"><spring:message code="deployment.service.unit.sold"></spring:message></th>
														
														<th width="5%">Edit</th>
														<th class="hidden"><spring:message code="deployment.service.description"/></th>
														<th class="hidden"></th>
														<th class="hidden"></th>
														<th class="hidden"></th>
														<th class="hidden"></th>
														
																		
													</tr>
												</thead>
											
											<tbody>
													<c:forEach items="${deploymentList}" var="deploymentList">
													
													<tr>
													<td><input type="checkbox" path="checkbox"
														class="CheckBox" id="Checkbox"
															value="${deploymentList.numDeploymentId}" autocomplete="off"></td>
															<td></td>
													<td>${deploymentList.strServiceName}</td>
													<td>${deploymentList.strDeploymentTotDate}</td>
													<td>${deploymentList.strAgencyName}</td>
													<td>${deploymentList.strAgencyState}</td>
													<td>${deploymentList.strAgencyCity}</td>
													<td>${deploymentList.strDeploymentState}</td>
													<td>${deploymentList.strDeploymentCity}</td>
													<td>${deploymentList.numUnitsDeployedOrSold}</td>
													<td><span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
															id="edit" aria-hidden="true"></span>
										 
													</td>
													
													<td class="hidden">${deploymentList.strDescription}</td>
													<td class="hidden">${deploymentList.numAgencyStateId}</td>
													<td class="hidden">${deploymentList.numDeploymentStateId}</td>
													<td class="hidden">${deploymentList.numDeploymentId}</td>
													<td class="hidden">${deploymentList.strDocumentIds}</td>
													</tr>
													
													</c:forEach>
											</tbody>
							
											</table>
											</fieldset>
										
		</div>
		
</div>
							
</section>

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/deploymentTotDetails.js?v=1"></script>
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
		
</sec:authorize>
</body>
</html>
