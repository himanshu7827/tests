<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>						
						<li class="active"><spring:message code="approved.job.detail"/></li>
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
		
		<sec:authorize access="hasAuthority('VIEW_APPROVED_JOB')">	
			<form:form id="form1" modelAttribute="employeeApprovedJobMappingModel" 
						action="/PMS/saveMonthlyUtilizationForEmployee" method="post">	
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd">
						<h4 class="text-center "><spring:message code="approved.job.detail"/></h4>
					</div>
					<div class="panel-body">
						<div class="row pad-top ">
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-1 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
									<label class="label-class"><spring:message code="job.id.status" /> :<span style="color: red;">*</span></label>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-6 col-xs-12">								
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="jobStatus" id="inlineRadio1" value="ALL">
									  <label class="form-check-label" for="inlineRadio1">Created</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="jobStatus" id="inlineRadio2" value="NYF">
									  <label class="form-check-label" for="inlineRadio2">Not yet Filled</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="jobStatus" id="inlineRadio3" value="ASS">
									  <label class="form-check-label" for="inlineRadio3">Assigned</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="jobStatus" id="inlineRadio4" value="CLS">
									  <label class="form-check-label" for="inlineRadio4">Closed</label>
									</div>
									<div class="form-check form-check-inline">
									  <input class="form-check-input" type="radio" name="jobStatus" id="inlineRadio5" value="EXP">
									  <label class="form-check-label" for="inlineRadio5">Expired</label>
									</div>
								</div>
							</div>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-1 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
									<label class="label-class"><spring:message code="group.groupName" /> :</label>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-6 col-xs-12">								
									<div class="input-container">
												<form:select path="groupId" class="select2Option" multiple="true">													
													<c:forEach items="${groupList}" var="group">
														<form:option value="${group.numId}">${group.groupName}</form:option>
													</c:forEach>
												</form:select>												
											</div>
									
								</div>
							</div>							
							</div>
						</div>	
						<div class="row dateDiv">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 col-md-push-1 col-sm-push-1 pad-top pad-bottom"> <span class="bold red">Note :</span><span class="blue"> If From Date not selected, Data will display for 1 Month </span></div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-1 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
									<label class="label-class"><spring:message code="from.date" /> :</label>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-6 col-xs-12">								
									<div class="input-container"> 
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="startDate"/>								
								</div>
									
								</div>
							</div>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-1 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
									<label class="label-class"><spring:message code="to.date" /> :</label>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-6 col-xs-12">								
									<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="endDate"/>						
								</div>
									
								</div>
							</div>
							</div>
						</div>				
						
											
					 <div class="col-md-12 col-xs-12 col-lg-12 col-sm-12 pad-top ">  
						<div class="row pad-bottom center  form_btn">
						 <button type="button" class="btn btn-primary" id="submit"><span class="pad-right">
						 <i class="fa fa-folder"></i></span>Load Data</button>						
						</div>
					</div>
					</div>
				</div>
			</form:form>				
		</sec:authorize>
		</div>
		
		
	</section>
	
	<section id="dataSection" class="hidden">
		
			<fieldset>
				<legend class="font_12">Details</legend>
				
				<div class= "container">
					<table class="table table-striped table-bordered" id="detailsTable">
					<thead id="jobDetailHead"></thead>
					<tbody id="jobDetailBody"></tbody>					
					</table>
				</div>
			</fieldset>
		
	 </section>


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/viewApprovedJob.js"></script>
</body>
</html>