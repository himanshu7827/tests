<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<style>
.bg_grey {
	background: LemonChiffon !important;
}

.highlight {
	border: 1px solid red;
}

body {
            height: 2000px;
        }

</style>
<body>

	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<li class="active">Project Team Mapping</li>
					</ol>
					<div class="row padded"></div>
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

					<sec:authorize access="hasAuthority('PROJECT_TEAM_MAPPING')">
						<form:form id="form1" modelAttribute="employeeRoleMasterModel"
							action="/PMS/mst/saveUpdateEmployeeRoleMaster" method="post">
							<form:hidden path="activityCheckedValue" />
							<input type="hidden" id="userRole" value="${userRole}"></input>
							<input type="hidden" id="categoryId"></input>
							<%-- <input type="hidden" id="roleList" value="${roleList}"/> --%>
							<select class="hidden allroles" id="allroles" >
								<c:forEach items="${roleList}" var="role">
								<option  value="${role.numId}">${role.roleName}</option>
									
								</c:forEach>
							</select>
                            
                           <%-- bhavesh (13-11-23) added col-md-12 col-lg-12 col-sm-12 col-xs-12  --%>
							<div class="container col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
									<div class="panel panel-info panel-info1">
										<div class="panel-heading">
											<h3 class="text-center text-shadow">
												<spring:message code="project.team.mapping" />
											</h3>
										</div>
										<div class="panel-body">
											<c:if test="${not empty referrer}">
												<div class="col-md-12 col-lg-12 col-xs-12 pad-bottom">
													<div class="pull-right">
														<a href="${referrer}" class="btn btn-orange font_14">
															<span class="pad-right"><i
																class="fa fa-arrow-left"></i></span>Back
														</a>
													</div>
												</div>

											</c:if>

											<div class="row pad-top">

												<div
													class="col-md-9 col-lg-9 col-sm-12 col-xs-12 form-group">
													<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="Project_Payment_Schedule.projectName" />:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-9 col-lg-9 col-sm-6 col-xs-12">
														<c:choose>
															<c:when test="${not empty projectMaster}">
																<form:hidden path="numProjectId"
																	value="${projectMaster.numId}" />
																<script>
																	$(document)
																			.ready(
																					function() {
																						$(
																								'#numProjectId')
																								.change();
																					});
																</script>
															${projectMaster.strProjectName}
															<p id="pName" class="hidden">${projectMaster.strProjectName}</p>
														</c:when>
															<c:otherwise>
																<div class="input-container">
																	<form:select  path="numProjectId" class="select2Option" >
																		<form:option value="0">-- Select Project --</form:option>
																		<c:forEach items="${projectsList}" var="project">
																			<form:option value="${project.numId}">
																				<%-- <c:out value="${project.strProjectName}"></c:out> --%>
																				<!-- Added by devesh on 29-11-23 to show project reference number along with project name -->
																				<c:choose>
															                        <c:when test="${not empty project.projectRefNo}">
															                            <c:out value="<strong>${project.projectRefNo}</strong> - ${project.strProjectName}" />
															                        </c:when>
															                        <c:otherwise>
															                            <c:out value="${project.strProjectName}" />
															                        </c:otherwise>
															                    </c:choose>
															                    <!-- End of dropdown -->
																			</form:option>
																		</c:forEach>
																	</form:select>
																	<form:errors path="numProjectId" cssClass="error" />
																</div>
															</c:otherwise>
														</c:choose>

													</div>
												</div>

											</div>
										 <div class="detailsDiv hidden"> 
											<div class="row ">
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="employee_Role.start_Date" />:</label>
																<span id="projectStartDate" class="bold pad-left" style="color: #1578c2;"></span>
													</div>
													
												</div>


												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="employee_Role.end_Date" />:</label>
																<span id="projectEndDate" class="bold pad-left" style="color: #1578c2;"></span>
													</div>
													
												</div>
											</div>
										</div>
									</div>
										<div class="detailsDiv hidden">


											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top pad-bottom">
												<div class="panel panel-info panel-glance">
													<div class="panel-heading font_eighteen bold">
														Existing Team</div>

													<div class="panel-body zero">
														<div
															class="col-md-12 col-lg-12 col-sm-12 col-xs-12 table-responsive pad-top">
															<table class="table table-striped table-bordered "
																id="existingTeamTable" style="width:100%;">
																<thead class="datatable_thead">
																	<tr>
																	<th width="15%"><spring:message
																				code="label.project.map"></spring:message></th>
																	<th width="15%">Role Mapping</th>
																		
																		<th width="15%"><spring:message
																				code="employee_Role.employee_Name" /></th>
																				<!-- //Bhavesh(6-07-2023)added employee.deputedat -->
																				<th width="5%"><spring:message
																				code="employee.deputedat" /></th>
																				
																		
																		
																		<th width="15%"><spring:message
																				code="projectMaster.startDate" /></th>
																		<th width="15%"><spring:message
																				code="projectMaster.endDate" /></th>
																		<th width="5%"><spring:message
																				code="manpower.involvement" /></th>
																				
																				
																		<th width="5%"><spring:message
																				code="label.primary.role" /></th>
																				
																				
																		<th width="15%"><spring:message
																				code="forms.action" /></th>
																	</tr>
																</thead>
																<tbody>
																</tbody>
															</table>

														</div>
													</div>
												</div>
											</div>

											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top pad-bottom">


												<div class="panel panel-info panel-glance">
													<div class="panel-heading font_eighteen bold">New
														Member Mapping</div>
														
                                                    <!--Bhavesh(13-11-23) added button for creating additional requirement  -->
													<div id="addRequirement" class="hide"><br>

														<div class="pull-right pad-bottom">
															<a class="btn btn-orange" > Create Additional Requirement <i class="fa fa-plus-circle "></i></a>
														</div>

													</div>
                                                    <!--Bhavesh(13-11-23) END OF added button for creating additional requirement  -->
													<div class="panel-body zero">
														<div
															class="col-md-12 col-lg-12 col-sm-12 col-xs-12 table-responsive pad-top">
															<table class="table table-striped table-bordered "
																id="newMemberTable">
																<thead class="datatable_thead">
																	<tr>
																	<th width="20%"><spring:message
																				code="label.project.map"></spring:message></th>
																	<th width="10%">Role</th>
																		
																	
																		<th width="20%"><spring:message
																				code="employee_Role.employee_Name" /></th>
																				<!-- //Bhavesh(6-07-2023)added employee.deputedat -->
																				<th width="5%"><spring:message
																				code="employee.deputedat" /></th> 
																		
																		<th width="12%"><spring:message
																				code="projectMaster.startDate" /></th>
																		<th width="12%"><spring:message
																				code="projectMaster.endDate" /></th>
																		<th width="5%"><spring:message
																				code="manpower.involvement" /></th>
																		<th width="5%"><spring:message
																				code="label.primary.role" /></th>
																		<th width="10%"><spring:message
																				code="forms.action" /></th>
																	</tr>
																</thead>
																<tbody>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="row pad-top pad-bottom form_btn center">
											<c:if test="${not empty referrer}">
												<a href="${referrer}" class="btn btn-orange font_14"> <span
													class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
												</a>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</form:form>
					</sec:authorize>

				</div>
			</div>
		</div>



	</section>
<div class="hide"><a class="modalAnchor" id="modalAnchor" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#additionRequirement-modal">
									<span></span></a></div>
													<!-- Modal for New Projects -->
			<div class="modal additionRequirement-modal dash-modal"
				id="additionRequirement-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" style="margin-top: 9%;">
					<div class="modal-content"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								Additional Manpower Requirement
							</h3>
							<button type="button" class="close"  id="closeModal"  onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="modal-body dash-modal-body">
				
							<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="Project_Payment_Schedule.projectName"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<p class="bold x" id="projectName"></p>
											</div>
										</div>
									</div>
								</div>
							
	<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="label.project.based.role"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<select id="projectRole" class="select2Option " >
													<option value="0"> -- Select Project Roles -- </option>
													<c:forEach items="${projectRoleDetails}" var="prDetails">
														<option value="${prDetails.numId}"> ${prDetails.strRoleName} </option>
													</c:forEach> 
												
												</select>
											</div>
										</div>
									</div>
								</div>
																<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="employee.designation"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<select id="projectDesignations" class="select2Option">
													<option value="0"> -- Select Designation -- </option>
													 <c:forEach items="${designations}" var="designation">
														<option value="${designation.numId}"> ${designation.designationName} </option>
													</c:forEach>													
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="to.be.Deputed"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<select id="deputedVal" class="select2Option">
													<option value="3"> -- Select -- </option>
													
														<option value="0">CDAC Noida </option>
														<option value="1">Client Site</option>
												
												</select>
											</div>
										</div>
									</div>
								</div>
								<!-- Bhavesh(03-08-2023)added purpose-->
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="Project_Payment_Schedule.purpose"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<input class="input-field" id="idPurpose"  path="purpose" />
													
												
											</div>
										</div>
									</div>
								</div>
								<!-- Bhavesh(04-08-23) -->
																<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="manpower.actual.cost"/> (<span class="font_12"><spring:message code="lable.in.ruppee"/></span>): </label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 bold" id="actualCost">
											
										</div>
									</div>
								</div>
								
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="manpower.req.manmonth.rate"/> (<span class="font_12"><spring:message code="lable.in.ruppee"/></span>): <span 
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<input class="input-field"
													 id="ratePerManMonth" />
											</div>
										</div>
									</div>
								</div>
								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<!-- Bhavesh(04-08-23)added (in %) -->
											<label class="label-class"><spring:message code="manpower.involvement"/>:<br><span
												style="color: red;">(in %)*</span></label>
										</div>
                                          <!-- Bhavesh(04-08-23) -->
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												 <div class="input-container">
										<i class="fa fa-th-large icon"></i>
									 	<input class="input-field" id="Additionalinvolvement" path="involvement" placeholder="Involvement in percentage"/>							
								</div>
										</div>
									</div>
								</div>
									<div class="row pad-top">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="employee_Role.start_Date"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										 <div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <input class="input-field x" readonly='true' id="additionalStartDate" placeholder="Start Date"/>							
								</div> 
										</div>
									</div>
								</div>
													<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="employee_Role.end_Date"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12"> 
										
										
										 <div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <input class="input-field x" readonly='true' id="additionalEndDate" placeholder="End Date"/>							
								</div> 
										</div>
									</div>
								</div>
										<div class="row pad-top">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<label class="label-class"><spring:message code="document.description"/>:
										</label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<div class="input-container">
											<i class="fa fa-th-large icon pad-top-double"></i>
												<textarea id="strDescription" class="form-control" placeholder="Description" 
									></textarea>
									
										</div>
									</div>
								</div>
								 
								</div>
								<div class="row pad-top  form_btn" style="margin-left: 43%;">
					
						<button type="button" class="btn btn-primary font_14" id="saveAdditionalRequirement">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="resetAdditionalRequirement">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						 
						
								</div>	
								</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				
					<%-- 			<table id="newProjects_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="40%"><spring:message code="master.name" /></th>
											<th width="12%"><spring:message
													code="project.projectRefNo" /></th>
											<th width="12%"><spring:message
													code="dashboard.client.name" /></th>
											<th width="12%"><spring:message
													code="projectMaster.MOUdate" /></th>
											<th width="12%"><spring:message
													code="projectMaster.WorkorderDate" /></th>
											<th width="18%"><spring:message
													code="dashboard.project.startDate" /></th>
											<th width="20%"><spring:message
													code="dashboard.lable.outlay" /></th>

										</tr>
									</thead>
									<tbody class="">
									</tbody>
								</table> --%>
							</div>
						</div>
					</div>
				</div>
				
	<!-- Modal For employee project Details -->
	<div class="modal amount_receive_deatil_modal" id="empProjectDetails"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Employee
						Project Details</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row pad-top ">

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
							<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">

								<label class="label-class"><b><spring:message
											code="employee_Role.employee_Name" />:</b></label>

							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6"
								id="employeeName"></div>
						</div>
					</div>
					<table id="datatable1"
						class="table table-striped table-bordered example_det "
						style="width: 100%">
						<thead class="datatable_thead bold">
							<tr>
								<th>S.No</th>
								<th>Project Name</th>
								<th>Role</th>
								<th>Start Date</th>
								<th>End Date</th>

							</tr>
						</thead>
						<tbody class="">

						</tbody>
					</table>
					 
				</div>
				<div class="modal-footer" id="modelFooter"></div>
			</div>
		</div>
	</div>
 
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/projectTeamMapping.js"></script>
		
</body>
</html>