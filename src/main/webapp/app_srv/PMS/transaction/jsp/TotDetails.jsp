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

						<li class="active">Deployment/ToT Details</li>
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
							action="/PMS/saveUpdateTotDetails" enctype="multipart/form-data" method="post">
<div class="container">
								<div class="panel panel-info panel-info1">
										<div class="panel-heading">
											<h3 class="text-center text-shadow">
												ToT Details
											</h3>
										</div>
										<div class="panel-body">
													

<form:hidden path="numTotId" id="numTotId"/>	
<form:hidden path="numTotIds" id="numTotIds"/>
<form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
<form:hidden path="encCategoryId"  value="${encCategoryId}"/>	

<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="deployment.agency.name"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container"><form:input class="input-field" placeholder="Agency Name" path="strAgencyName"  />
									<form:errors path="strAgencyName" cssClass="error" />
							</div>
</div>
					
</div>
</div>

<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Tot_Date"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container"><i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="strDeploymentTotDate" placeholder="ToT Date"/>							
								<form:errors path="strDeploymentTotDate" cssClass="error" />
								</div>
							</div>
						</div>
</div>

<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
<label class="label-class"><spring:message code="Tot_Product"/>:<span
									style="color: red;">*</span></label>
							</div>

						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
								<form:input class="input-field" placeholder="Product Name" path="strAgencyCity"  />
									<form:errors path="strAgencyCity" cssClass="error" />
							</div>
							</div>
						</div>
						
						
</div>

	
<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Tot_ProductDes"/>:<span
									style="color: red;">*</span></label>
							</div>

						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
							<form:textarea class="form-control " 
														path="strDescription" rows="5" placeholder="Product Description"></form:textarea>
														<form:errors path="strDescription" cssClass="error" />
							</div>
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
													    <th class="hidden"></th>
														<th width="10%" ><spring:message code="deployment.agency.name"/></th>
														<th width="10%" ><spring:message code="Tot_Date"/></th>
														<th width="10%" ><spring:message code="Tot_Product"/></th>
														<th width="20%" ><spring:message code="Tot_ProductDes"/></th>
														
														<th width="5%">Edit</th>
																		
													</tr>
												</thead>
											
											<tbody>
													<c:forEach items="${deploymentList}" var="deploymentList">
													
													<tr>
													<td><input type="checkbox" path="checkbox"
														class="CheckBox" id="Checkbox"
															value="${deploymentList.numTotId}" autocomplete="off"></td>
													<td class="hidden">${deploymentList.numTotId}</td>
													<td>${deploymentList.strAgencyName}</td>
													<td>${deploymentList.strDeploymentTotDate}</td>
													<td>${deploymentList.strAgencyCity}</td>
													<td>${deploymentList.strDescription}</td>
													<td><span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
															id="edit" aria-hidden="true"></span>
										 
													</td>
												</tr>
													
													</c:forEach>
											</tbody>
							
											</table>
											</fieldset>
										
		</div>
		
</div>
							
</section>

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/TotDetails.js?v=1"></script>
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
		
</sec:authorize>
</body>
</html>
