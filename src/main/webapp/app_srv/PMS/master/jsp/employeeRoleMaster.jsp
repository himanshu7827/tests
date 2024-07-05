<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

  
    <link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/jquery-ui.css">	

<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/bootstrapValidator.css">	

<style>


.foo { color: #808080; text-size: smaller; }
.select2-container--default .select2-selection--single .select2-selection__arrow {
    background-image: -khtml-gradient(linear, left top, left bottom, from(#1e2787), to(#0a1acd));
    background-image: -moz-linear-gradient(top, #1e2787, #0a1acd);
    background-image: -ms-linear-gradient(top, #1e2787, #0a1acd);
    background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #1e2787), color-stop(100%, #0a1acd));
    background-image: -webkit-linear-gradient(top, #1e2787, #0a1acd);
    background-image: -o-linear-gradient(top, #1e2787, #0a1acd);
    background-image: linear-gradient(#1e2787, #0a1acd);
    width: 40px;
    color: #fff;
    font-size: 17px !important;
    padding: 4px 12px;
      height: 27px;
  position: absolute;
  top: 0px;
  right: 0px;
  width: 20px;
}
.pad-right-half{
padding-right:5px;
}
  .select2-container .select2-selection--single .select2-selection__rendered{
font-size:17px !important;
}
/* .select2-container--default .select2-selection--single .select2-selection__arrow b {
    border-color: #fff transparent transparent transparent !important;
    }
	.select2-container--default.select2-container--open .select2-selection--single .select2-selection__arrow b{
	 border-color: #fff transparent transparent transparent !important;
	 border-width: 6px 4px 5px 4px !important;
	} */
.select2-container--default .select2-selection--single .select2-selection__rendered{
font-weight:600 ;
} 
.myFont {
font-size:17px !important;
font-weight:600;
}
.select2-container .select2-choice span {
font-size: 17px !important;
font-weight:600;
}
/* .select2-results .select2-highlighted {
color: #fff !important;
}
.select2-container--default .select2-results__option--highlighted[aria-selected] {
background-color: #eee !important;
}
.select2-container--default .select2-results__option--highlighted[aria-selected] {
    color: #000 !important;
	}
	li.select2-results__option[role=group]:hover {
 color: #fff !important;
}
 */
.select2-results .select2-highlighted {
    background: #eee !important;
    color: #000 !important;
}
</style>

</head>



<body>
	<section id="main-content" class="main-content merge-left">
	<div class=" container wrapper1">
		<div class="row">
			<div class=" col-md-12 pad-top-double  text-left">
				<ol class="breadcrumb bold">
					<li>Home</li>

					<li class="active">Employee Role Mapping</li>
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
			<sec:authentication property="principal.selectedEmployeeRole"
											var="selectedRole" />
		<input type="hidden" id="roleId" value="${selectedRole.numRoleId}"/>

		<sec:authorize access="hasAuthority('READ_EMPLOYEE_ROLE_MST')"> 
			<form:form id="form1" modelAttribute="employeeRoleMasterModel"
				action="/PMS/mst/saveUpdateEmployeeRoleMaster" method="post">
				<form:hidden path="numId" />
				<form:hidden path="idCheck" />
				<form:hidden path="strManReqDetails" id="strManReqDetails"/>
				<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class="panel panel-info panel-info1">
						<div class="panel-heading">
							<h3 class="text-center text-shadow"><spring:message code="employee.project.role.mapping"/></h3>
						</div>
						<div class="panel-body">
						<input type="hidden" id="categoryId"></input>
						<input type="hidden" id="ProjectOrg">
						<input type="hidden" id="ProjectGrp">
						<input type="hidden" id="joinDate">
						<input type="hidden" id="userRole" value="${userRole}">
<form:errors path="numRoleId" cssClass="error" />
<form:errors path="numOrganisationId" cssClass="error" />
<form:errors path="numGroupId" cssClass="error" />
<form:errors path="numProjectId" cssClass="error" />
<form:errors path="numManReqId" cssClass="error" />
<input type="hidden" id="projectEndDate" value=""/>
	<div class="row pad-top" id="stage_name_row">

								<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-3 col-sm-6 col-xs-12">
										<label class="label-class font_16"><spring:message code="employee_Role.employee_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="numEmpId" class="select2" style="width:100%">
												<%-- <form:option value="0" data-foo=".">-- Select Employee --</form:option> --%>
												 <option></option>
												<c:forEach items="${employeeList}"
													var="employeeList">
													<form:option  class="font_16" value="${employeeList.numId}" data-foo="${employeeList.strDesignation} [${employeeList.numId}]">
														<c:out value="${employeeList.employeeName}"></c:out>
													</form:option> 
												</c:forEach> 
											</form:select>
											<form:errors path="numEmpId" cssClass="error" />
											
										</div>
									</div>
								</div>
								</div>
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 hidden"
				id="stagedetails">
				<div class=" datatable_row pad-bottom">
					<fieldset class="fieldset-border">

						<legend class="bold" style="width: 65%;display: block;width: 10% !important;padding: 0;
    margin-bottom: 0px;
    font-size: 15px;
    line-height: inherit;
    color: black !important;
    border: 0;"> Existing Roles :</legend>
						<!-- <sec:authorize access="hasAuthority('WRITE_EMPLOYEE_ROLE_MST')">  -->
						<div class="row">
							<div class="pull-right">
								<!-- <div class="col-md-4">
									<input type="button" value="Delete" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div> -->
							</div>
						</div>
						<!--  </sec:authorize>  -->

						

						<table id="datatable"
							class="table table-striped table-bordered hidden"
							style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th class="width20 check hidden"><spring:message code="master.select"/></th>
									<th class="hidden"><spring:message code="employee_Role.employee_Name"/></th>
									<th class="width50" ><spring:message code="employee_Role.role_Name"/></th>									
									<th style="width: 33%;"><spring:message code="Project_Payment_Schedule.projectName"/></th>
									<%-- <th><spring:message code="employee_Role.group_Name"/></th>
									<th><spring:message code="employee_Role.organization_Name"/></th> --%>
									<%-- <c:if test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5}"> 
									<th><spring:message code="employee_Role.group_Name"/></th>
									</c:if> --%>
									
									 <c:choose>
  <c:when test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5 && selectedRole.numRoleId != 2 && selectedRole.numRoleId != 4}">
    <th><spring:message code="employee_Role.group_Name"/></th>
  </c:when>
  <c:otherwise>
   
  </c:otherwise>
</c:choose>						
									<%-- <c:if test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5}"> 
									<th><spring:message code="employee_Role.organization_Name"/></th>
									</c:if>  --%>
									 <c:choose>
  <c:when test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5 && selectedRole.numRoleId != 4 && selectedRole.numRoleId != 2}">
    <th><spring:message code="employee_Role.organization_Name"/></th>
  </c:when>
  <c:otherwise>
    
  </c:otherwise>
</c:choose>						 			
									<th><spring:message code="employee_Role.start_Date"/></th>
									<th><spring:message code="employee_Role.end_Date"/></th>									
									<th><spring:message code="label.project.map"/></th>	
									<th class="hidden" >Employee Id</th>
									<th class="hidden">Role Id</th>
									<th class="hidden">Organization Id</th>
									<th class="hidden">Group Id</th>
									<th class="hidden">Project Id</th>
									<th class="hidden">Manpower Id</th>
									<th><spring:message code="manpower.involvement"/></th>
									<th><spring:message code="forms.edit"/></th>									
								</tr>
							</thead>
							<tbody class="">						
							</tbody>
						</table>

						<!--End of data table-->
					</fieldset>
				</div>
				<!--End of datatable_row-->
				<button type="button" class="btn btn-primary font_14 pull-right" id="showForm">Assign another role to employee</button>
			</div>
			
			<div id="wholeForm" class="hidden" style="margin-top: 10%;">
						<div class="row">		
						<c:choose>
							<c:when test="${applicationError or groupError}">
								<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group hidden" id="groupDiv">
							</c:when>
							<c:otherwise>
								<div class="col-md-6 col-lg-12 col-sm-12 col-xs-12 form-group hidden" id="groupDiv">
							</c:otherwise>
						</c:choose>
						
									
							
								<%-- ${status.error ? 'has error' : ''} --%>
									
									<div class="col-md-6 col-lg-3 col-sm-6 col-xs-6">
										<label class="label-class" style="margin-left: 4%;"><spring:message code="employee_Role.group_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-3 col-sm-6 col-xs-12">
										<div class="input-container" style="width: 119%;margin-left: -8%;">
											
											
											
										</div>
									</div></div>
							<div class="row pad-top col-md-6 col-lg-6 col-sm-6 col-xs-12">

								<c:choose>
								<c:when test="${applicationError}">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="applicationDiv">
								</c:when>
								<c:otherwise>
									<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group" id="applicationDiv">
								</c:otherwise>
							</c:choose>									
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class" style="margin-left: 2%;">Project Name:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container" style="width: 229%;margin-left: 1%;">
											<form:select path="numProjectId" id="numProjectId" class="select2Option">
												<form:option class="font_16"  value="0">-- Select Project --</form:option> 
												
											</form:select>
										<form:errors path="numProjectId" cssClass="error" />
											
										</div>
									</div>
								</div>

						
							
								<div class="row" >
								<c:choose>
							<c:when test="${applicationError}">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="manpowerDiv">
							</c:when>
							<c:otherwise>
								<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group pad-top" id="manpowerDiv">
							</c:otherwise>
						</c:choose>
								 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
									 
										<label class="label-class" style="margin-left: 7%;">Role in Project:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container" style="width: 216%;">
										<form:select path="numRoleId" id="numRoleId" class="select2Option">
												<form:option class="font_16" value="0">-- Select Role --</form:option> 
												
												  
											 	<c:forEach items="${roleList}"
													var="roleList">
													<form:option class="font_16" value="${roleList.numId}">
														<c:out value="${roleList.roleName}"></c:out>
													</form:option> 
												</c:forEach> 
											</form:select>
											<form:errors path="numRoleId" cssClass="error" />
											
										</div>
									</div>
								</div>
							
		<div class="row pad-top">
		<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class" style="margin-left: 12%;">Project Requirement Mapping:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container" style="width: 205%;">
											
											<form:select path="numManReqId" class="select2Option">
												<form:option class="font_16" data-toggle="tooltip" data-placement="right" title="Tooltip on right" value="0">-- Select Project --</form:option> 
												
											</form:select>
										<form:errors path="numManReqId" cssClass="error" />
											
										</div>
									</div>
								</div>
								
									<%-- 	<c:choose>
								<c:when test="${applicationError}">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="involvementDiv">
								</c:when>
								<c:otherwise>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="involvementDiv">
								</c:otherwise>
							</c:choose>
								 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class"><spring:message code="manpower.involvement"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										 <div class="input-container">
										<i class=""></i>
									 	<form:input class="input-field" id="involvement" path="involvement" placeholder="Involvement in percentage"/>							
								<form:errors path="involvement" cssClass="error" />
								</div>
								</div> --%>
							</div>						
						

							
	
	<div class="row pad-top">
		<c:choose>
								<c:when test="${applicationError}">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="involvementDiv">
								</c:when>
								<c:otherwise>
									<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group" id="involvementDiv">
								</c:otherwise>
							</c:choose>
								 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class" style="margin-left: 11%;"><spring:message code="manpower.involvement"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									
											 <div class="input-container" style="width: 106%;">
										<i class=""></i>
									 	<form:input class="input-field" id="involvement" path="involvement" placeholder="Involvement in percentage"/>							
								<form:errors path="involvement" cssClass="error" />
								</div>
									</div>
	</div>

	
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
							<button type="button" class="close" id="closeModal" data-dismiss="modal">
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
												<p class="bold" id="projectName"></p>
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
												<select id="projectRole" class="select2Option" >
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
											<label class="label-class"><spring:message code="manpower.involvement"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												 <div class="input-container">
										<i class=""></i>
									 	<input class="input-field" id="Additionalinvolvement" path="involvement" placeholder="Involvement in percentage"/>							
								</div>
										</div>
									</div>
								</div>
									<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="employee_Role.start_Date"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										 <div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <input class="input-field" readonly='true' id="additionalStartDate" placeholder="Start Date"/>							
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
									 <input class="input-field" readonly='true' id="additionalEndDate"/>							
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
			
	<div class="row " >
	
	

								<div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group">
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<label class="label-class" style="margin-left: 16%;"><spring:message code="employee_Role.start_Date"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							 <div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="strStartDate" id="dtStartDate" placeholder="Start Date"/>							
								<form:errors path="strStartDate" cssClass="error" />
								</div> 
	
							</div>
						</div> 
								
							
								 <div class="col-md-6 col-lg-12 col-sm-6 col-xs-12 form-group"> 
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<label class="label-class" style="margin-left: 16%;" ><spring:message code="employee_Role.end_Date"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							 <div class="input-container" style="width: 100%;">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' onblur="showAdditional()" path="strEndDate" id="dtEndDate" placeholder="End Date"/>							
								<form:errors path="strEndDate" cssClass="error" />
								</div> 
	
							</div>
						</div> 
								</div>
								 <spring:hasBindErrors name="employeeRoleMasterModel">
								 <c:if test="${errors.hasFieldErrors('numProjectId')}">
								 	<c:set var ="applicationError" value="true"></c:set>
								  </c:if>
								  
								 <c:if test="${errors.hasFieldErrors('numGroupId')}">
								 	<c:set var ="groupError" value="true"></c:set>
								  </c:if>
								  
								<c:if test="${errors.hasFieldErrors('numOrganisationId')}">
								 	<c:set var ="organisationError" value="true"></c:set>
								  </c:if>
							
								  </spring:hasBindErrors>
								  <c:if test="${userRole==5}">
												<div class="row" >
							<c:choose>
								<c:when test="${applicationError}">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="additionalRequirement">
								</c:when>
								<c:otherwise>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="additionalRequirement">
								</c:otherwise>
							</c:choose>
								 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class" style="margin-left: 5%;"></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										 <div class="input-container">
										<i class=""></i>
										<a class="" id="modalAnchor" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#additionRequirement-modal">
									<span></span></a>
<%-- 									 	<form:input class="input-field"  path="involvement" placeholder="Involvement in percentage"/>							
 --%>								
								</div>
								</div>
							</div>
							</c:if>
						<div class="row pad-top" >
						<c:choose>
							<c:when test="${applicationError or groupError or organisationError }">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="organisationDiv">
							</c:when>
							<c:otherwise>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="organisationDiv">
							</c:otherwise>
						</c:choose>
								
									
								
						
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class" style="margin-left: 5%;"><spring:message code="employee_Role.organization_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
										<form:hidden path="numOrganisationId"/>
										<form:hidden path="numGroupId"/>
											
											
											
										</div>
									</div>
							</div>	
			
								
							
							
						
							<%-- <div class="row" >
							<c:choose>
								<c:when test="${applicationError}">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="applicationDiv">
								</c:when>
								<c:otherwise>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="applicationDiv">
								</c:otherwise>
							</c:choose>
								 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class">Project Name:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="numProjectId" id="numProjectId" class="select2Option">
												<form:option value="0">-- Select Project --</form:option> 
												
											</form:select>
										<form:errors path="numProjectId" cssClass="error" />
											
										</div>
									</div>
								</div> --%>
								
								
								
						
							
															
					
					
					<%-- <div class="row" >
							<c:choose>
								<c:when test="${applicationError}">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group " id="involvementDiv">
								</c:when>
								<c:otherwise>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="involvementDiv">
								</c:otherwise>
							</c:choose>
								 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class"><spring:message code="manpower.involvement"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										 <div class="input-container">
										<i class=""></i>
									 	<form:input class="input-field"  path="involvement" placeholder="Involvement in percentage"/>							
								<form:errors path="involvement" cssClass="error" />
								</div>
								</div>
							</div> --%>
							



								<div class="row pad-top    center form_btn">

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
			</div>		
	
			</form:form>
 </sec:authorize> 

			<%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 hidden"
				id="stagedetails">
				<div class=" datatable_row pad-bottom">
					<fieldset class="fieldset-border">

						<legend class="bold legend_details"> Existing Roles :</legend>
						<!-- <sec:authorize access="hasAuthority('WRITE_EMPLOYEE_ROLE_MST')">  -->
						<div class="row">
							<div class="pull-right">
								<!-- <div class="col-md-4">
									<input type="button" value="Delete" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div> -->
							</div>
						</div>
						<!--  </sec:authorize>  -->

						

						<table id="datatable"
							class="table table-striped table-bordered hidden"
							style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th class="width20 check"><spring:message code="master.select"/></th>
									<th><spring:message code="employee_Role.employee_Name"/></th>
									<th><spring:message code="employee_Role.role_Name"/></th>									
									<th><spring:message code="Project_Payment_Schedule.projectName"/></th>
									<th><spring:message code="employee_Role.group_Name"/></th>
									<th><spring:message code="employee_Role.organization_Name"/></th>
									<c:if test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5}"> 
									<th><spring:message code="employee_Role.group_Name"/></th>
									</c:if>
									
									 <c:choose>
  <c:when test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5}">
    <th><spring:message code="employee_Role.group_Name"/></th>
  </c:when>
  <c:otherwise>
   
  </c:otherwise>
</c:choose>						
									<c:if test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5}"> 
									<th><spring:message code="employee_Role.organization_Name"/></th>
									</c:if> 
									 <c:choose>
  <c:when test="${selectedRole.numRoleId != 3 && selectedRole.numRoleId != 5}">
    <th><spring:message code="employee_Role.organization_Name"/></th>
  </c:when>
  <c:otherwise>
    
  </c:otherwise>
</c:choose>						 			
									<th><spring:message code="employee_Role.start_Date"/></th>
									<th><spring:message code="employee_Role.end_Date"/></th>									
									<th><spring:message code="label.project.map"/></th>	
									<th class="hidden" >Employee Id</th>
									<th class="hidden">Role Id</th>
									<th class="hidden">Organization Id</th>
									<th class="hidden">Group Id</th>
									<th class="hidden">Project Id</th>
									<th class="hidden">Manpower Id</th>
									<th><spring:message code="manpower.involvement"/></th>
									<th><spring:message code="forms.edit"/></th>									
								</tr>
							</thead>
							<tbody class="">						
							</tbody>
						</table>

						<!--End of data table-->
					</fieldset>
				</div>
				<!--End of datatable_row-->
			</div> --%>
			
		</div>
		</div>
		</div>
	</section>

	 

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/employeeRoleMaster.js"></script>
	  <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/bootstrapValidator.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/jquery-ui.js"></script>
	


</body>
</html>