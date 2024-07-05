<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>
.oddRow {
	background-color: #c2c3ea;
}
</style>


</head>

<body>


	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
	
			</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>						
						<li class="active">Job Title</li>
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

	<sec:authorize access="hasAuthority('WRITE_JOB_TITLE')">
			<form:form id="form1" modelAttribute="jobTitleModel" action="/PMS/saveJobTitle" method="post">				
			 <form:hidden path=""></form:hidden> 
				<div class="container">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Job Title
								</h4>
							</div>
							<div class="panel-body text-center">
					
								<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

													<label class="label-class"><spring:message
															code="group.groupName" /> :<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" onchange="getProjectDetails()" path="groupId"
															id="groupId">
															<option value="0">-- Select Group --</option>
															<c:forEach items="${groupList}"
																var="groupList">
																<option value="${groupList.numId}">
																	${groupList.groupName}</option>
															</c:forEach>
														</form:select>
														<form:errors path="groupId" cssClass="error" />
													</div>

												</div>
											</div>
										</div>
										
										<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

													<label class="label-class"><spring:message
															code="Project_Module_Master.projectName" /> :<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" onchange="getJobDetails()" path="projectId"
															id="projectId">
															
															<option value="0">-- Select Project --</option>
															
														</form:select>
														<form:errors path="projectId" cssClass="error" />
													</div>

												</div>
											</div>
										</div>
								
										
										
																	<div class="row">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

													<label class="label-class"> Employee :<span
														style="color: red;">*</span></label>
												</div>


												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="empId"
															id="empId">
															
															<option value="0">-- Select Employee --</option>
															
														</form:select>
														<form:errors path="empId" cssClass="error" />
													</div>

												</div>
											</div>
										</div>	
									
									<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

													<label class="label-class">Job Title :<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="jobID"
															id="jobID">
															
															<option value="0">-- Select Job-Code --</option>
															
														</form:select>
														<form:errors path="jobID" cssClass="error" />
													</div>

												</div>
											</div>
										</div>
										
										<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

												<label class="label-class">W.e.f :<span
													style="color: red;">*</span></label>

											</div>

											<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12">
											
												<div class="input-container">
												<i class="fa fa-calendar icon"></i>
													<form:input class="input-field" readonly='true' placeholder="Effective From"
														path="approvedOn"  id="approvedOn"></form:input>
														<%-- <form:hidden path="numId"></form:hidden> --%>
													<form:errors path="approvedOn" cssClass="error" />
												</div>

											</div>
										</div>
									</div>
										
											<div class="row pad-top form_btn center">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>
										<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button>
									
									</div>
					</div>
					</div>
					</div>				
									
				</div>	
			</form:form>
			</sec:authorize>
	</div>
	</section>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/JobTitle.js"></script>
</body>
</html>