<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-------- Add Style for Fields Validation Error [15-11-2023] ----------%>
<style>
.errorMessageClass{
    margin-top: 4px;
    border-radius: 9px;
    }

.input-container-manpower{
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    height: 40;
    font-size: 14px;
	}
	
.icon_manpower{
    /* padding: 15px; */
    background: /* #d2b47e */ #cbdeff;
    color: white;
    min-width: 50px;
    height: 40;
    text-align: center;
    color: #5577c1;
    padding-top: 13px;
    padding-bottom: 13px;
}
.btn_manpower{
    display: inline-block;
    padding: 0px 9px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
}
</style>

</head>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
		<div class="row">
		<c:if test="${projectId>0 }">
			<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
				<jsp:param name="moduleTypeId" value="2"/>
				<jsp:param name="applicationId" value="0"/>
			</jsp:include>
			<div class="hidden" id="projectStartDate"><fmt:formatDate pattern = "yyyy,MM,dd" 
         value = "${projectDetail.dtProjectStartDate}" /></div>
			<div class="hidden" id="projectEndDate"><fmt:formatDate pattern = "yyyy,MM,dd" 
         value = "${projectDetail.dtProjectEndDate}" /></div>
		</c:if>
</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active"><spring:message code="menu.project.manpower.label"/></li>
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
		<sec:authentication property="principal.selectedEmployeeRole"
											var="selectedRole" />
		<input type="hidden" id="roleId" value="${selectedRole.numRoleId}"/>
			<form:form id="form1" modelAttribute="manpowerRequirementModel"
				action="/PMS/mst/saveManpowerRequirementMaster" method="post">
				<form:hidden path="numIds" />
				<form:hidden path="numId" />
				
				<input type="hidden" id="categoryId" value="${categoryId}"/>
				<form:hidden path="actualRatePerManMonth"/>
				<input type="hidden" id="projectid" value="${projectId}" />
				<input type="hidden" id="encProjectId" value="${encProjectId}"/>
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center "><spring:message code="menu.project.manpower.label"/></h4>
							</div>
							<div class="panel-body text-center">
								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-right" style="padding: 0;margin-left: 45px;margin-top: -0.2rem;">
											<label class="label-class" style="font-size: 15px;"><spring:message code="Project_Payment_Schedule.projectName"/>:<!-- <span
												style="color: red;">*</span> --></label>
										</div>

									<c:choose>
									<c:when test="${projectId==0}">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 text-left" style="padding-left: 8px;">
											<div class="input-container">
												<form:select path="projectId"  class="select2Option"  style="font-size:15px; width:100%">
													<option value="0"> -- Select Project -- </option>
													<c:forEach items="${projectData}" var="project">
														<option value="${project.numId}"> ${project.strProjectName} </option>
													</c:forEach>
													<form:errors path="projectId" cssClass="error" />
												</form:select>
											</div>
										</div>
										</c:when>
									<c:otherwise>
									
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 text-left" style="padding-left: 8px;">
								<form:hidden path="projectId" value="${projectId}"/>
									<h5 class="bold" style="font-size: 15px;">${projectDetail.strProjectName}
									<c:if test="${not empty projectDetail.strProjectRefNo}">
 									<span class="bold blue font_14">&nbsp;(${projectDetail.strProjectRefNo}) </span>
									</c:if>
									</h5>
							
								</div>
									</c:otherwise>
								</c:choose>
									</div>
								</div>
								<%-- <div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="label.project.based.role"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select path="numProjectRoles" class="select2Option" style="width:100%">
													<option value="0"> -- Select Project Roles -- </option>
													<c:forEach items="${projectRoleDetails}" var="prDetails">
														<option value="${prDetails.numId}"> ${prDetails.strRoleName} </option>
													</c:forEach>
												
												</form:select>
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
												<form:select path="designationId" class="select2Option" style="width:100%">
													<option value="0"> -- Select Designation -- </option>
													<c:forEach items="${designations}" var="designation">
														<option value="${designation.numId}"> ${designation.designationName} </option>
													</c:forEach>
													<form:errors path="designationId" cssClass="error" />
												</form:select>
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
												<form:select path="deputedAt" class="select2Option" style="width:100%">
													<option value="3"> -- Select -- </option>
													
														<option value="0">CDAC Noida </option>
														<option value="1">Client Site</option>
												
												</form:select>
											</div>
										</div>
									</div>
								</div>
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
												<form:input class="input-field"
													 path="purpose" />
												<form:errors path="purpose" cssClass="error" />
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
												<form:input class="input-field"
													 path="ratePerManMonth" />
												<form:errors path="ratePerManMonth" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								
								
								
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="manpower.count"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<form:input class="input-field"
													 path="count" />
												<form:errors path="count" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								<div class="row  ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message code="manpower.involvement"/>:<span
												style="color: red;">(in %)*</span> </label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-th-large icon"></i>
												<form:input class="input-field" 
													path="involvement" />
												<form:errors path="involvement" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								
						<div class="row  ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" for="pwd"><spring:message code="employee_Role.start_Date"/>:<span
									style="color: red;">*</span> </label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="startDate" />							
								<form:errors path="startDate" cssClass="error" />
								</div>
							</div>
				</div>
				</div>
				<div class="row  ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" for="pwd"><spring:message code="employee_Role.end_Date"/>:<span
												style="color: red;">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="endDate" />							
								<form:errors path="endDate" cssClass="error" />
								</div>
							</div>
				</div>
				</div>

		<div class="row  ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<label class="label-class"><spring:message code="document.description"/>:
										</label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<div class="input-container">
											<i class="fa fa-th-large icon pad-top-double"></i>
												<form:textarea class="form-control" placeholder="Description" 
									path="strDescription"></form:textarea>
									<form:errors path="strDescription" cssClass="error" />
										</div>
									</div>
								</div>
								 
								</div>
								
								
								 <c:if test="${selectedRole.numRoleId == 5}"> 
										<div class="row  ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<label class="label-class"><spring:message code="label.additional.manpower"/>:
										</label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										 <div class="input-container center"> 											
												<input type="checkbox" id="required" name="required" />
												<form:hidden path="requiredType" id="requiredType" value="1"/>
									
										</div> 
									</div>
								</div>
								 
								</div>
						
									</c:if> 
								
								
								
								<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="forms.status" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid"  value="true" id="toggle-on"	/>
									<form:label path="valid" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Active</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid" value="false" id="toggle-off" />

									<form:label path="valid" for="toggle-off"
										 class="btn round inline zero no-pad">
										<span class="">Inactive</span>
									</form:label>
</div>
							</div>
						</div>						
					</div> --%> 
<%------------- Add Verticle Columns for Manpower Fields [15-11-2023] ----------%>	
				<div class="row pad-top ">
					<div
						class="form-group" style="width: auto;">
						<table class="table table-striped table-bordered table-responsive" id="manpowerMultipleData">
								    <thead>
								        <tr>
								        	<th class="text-center"><spring:message code="label.project.based.role"/>:<span style="color: red;">*</span></th>
								            <th class="text-center"><spring:message code="employee.designation"/>:<span style="color: red;">*</span></th>     
								            <th class="text-center"><spring:message code="to.be.Deputed"/>:<span style="color: red;">*</span></th>
								            <th class="text-center"><spring:message code="Project_Payment_Schedule.purpose"/>:
								            <th class="text-center"><spring:message code="manpower.actual.cost"/> (<span class="font_12"><spring:message code="lable.in.ruppee"/></span>): </th>
								            <th class="text-center"><spring:message code="manpower.req.manmonth.rate"/> (<span class="font_12"><spring:message code="lable.in.ruppee"/></span>): <span style="color: red;">*</span></th>
								            <th class="text-center"><spring:message code="manpower.count"/>:<span style="color: red;">*</span></th>
								            <th class="text-center"><spring:message code="manpower.involvement"/>:<span style="color: red;">(in %)*</span></th>
								            <th class="text-center"><spring:message code="employee_Role.start_Date"/>:<span style="color: red;">*</span></th>
								            <th class="text-center"><spring:message code="employee_Role.end_Date"/>:<span style="color: red;">*</span></th>
								            <c:if test="${selectedRole.numRoleId == 5}"> 
								            	<th class="text-center"><spring:message code="label.additional.manpower"/>:</th>
								            </c:if>
								            <th class="text-center statusColumn"><spring:message code="forms.status" /> :</th>
								            <th class="text-center ActionColumn">Action</th>
								        </tr>
								    </thead>
								    
								    <tbody>
								    	<tr id="0row">
								    		<td class="">
								    			<input class="hidden" id="validField_0"/>
								            	<input class="hidden" id="ManpowerId_0"/>
												<div class="input-container-manpower">
													<select id="numProjectRoles_0" class="select2Option" onchange="saveField('0')" style="width:120px">
														<option value="0"> -- Select Project Roles -- </option>
														<c:forEach items="${projectRoleDetails}" var="prDetails">
															<option value="${prDetails.numId}"> ${prDetails.strRoleName} </option>
														</c:forEach>
													
													</select>
												</div>
												<div class="alert-danger text-center numProjectRoles_0 errorMessageClass" role="alert"></div>
											</td>
										
											<td class="">
												<div class="input-container-manpower">
													<select id="designationId_0" class="select2Option" onchange="saveField('0')" style="width:120px">
														<option value="0"> -- Select Designation -- </option>
														<c:forEach items="${designations}" var="designation">
															<option value="${designation.numId}"> ${designation.designationName} </option>
														</c:forEach>
														<%-- <form:errors path="designationId" cssClass="error" /> --%>
													</select>
												</div>
												<div class="alert-danger text-center designationId_0 errorMessageClass" role="alert"></div>
											</td>
					
											<td class="">
												<div class="input-container-manpower">
													<select id="deputedAt_0" class="select2Option" onchange="saveField('0')" style="width:120px">
														<option value="3"> -- Select -- </option>
														<option value="0">CDAC Noida </option>
														<option value="1">Client Site</option>													
													</select>
												</div>
												<div class="alert-danger text-center deputedAt_0 errorMessageClass" role="alert"></div>
											</td>
					
											<td class="">
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="purpose_0" onblur="saveField('0')" style="width:200px"/>
													<%-- <form:errors path="purpose" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center purpose_0 errorMessageClass" role="alert"></div>
											</td>
					
											<td class="bold" id="actualCost_0" style="width:5%">
												
											</td>
					
											<td class="">
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="ratePerManMonth_0" onblur="saveField('0')" style="width:90px"/>
													<%-- form:errors path="ratePerManMonth" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center ratePerManMonth_0 errorMessageClass" role="alert"></div>
											</td>
					
											<td class="">
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="count_0" onblur="saveField('0')" />
													<%-- <form:errors path="count" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center count_0 errorMessageClass" role="alert"></div>
											</td>
					
											<td class="">
												<div class="input-container-manpower">
													<!-- <i class="fa fa-th-large icon_manpower"></i> -->
													<input class="input-field" id="involvement_0" onblur="saveField('0')" />
													<%-- <form:errors path="involvement" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center involvement_0 errorMessageClass" role="alert"></div>
											</td>
					
											<td class="">
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field startDate" readonly='true' id="startDate_0" onchange="saveField('0')" style="width:90px"/>							
													<%-- <form:errors path="startDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center startDate_0 errorMessageClass" role="alert"></div>
											</td>
				
											<td class="">
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field endDate" readonly='true' id="endDate_0" onchange="saveField('0')" style="width:90px"/>							
													<%-- <form:errors path="endDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center endDate_0 errorMessageClass" role="alert"></div>
											</td>												
												
											<c:if test="${selectedRole.numRoleId == 5}"> 
													<td class="">
														 <div class="input-container-manpower center"> 											
																<input type="checkbox" id="required_0" name="required_0" onclick="saveField('0')" />
																<input class="hidden" name="requiredType_0" id="requiredType_0" value="1"/>													
														</div> 
														<div class="alert-danger text-center required_0 errorMessageClass" role="alert"></div>
													</td>						
											</c:if> 
				
											<td class=" statusColumn">
												<input type="radio" name="valid_0" value="true" id="toggle-on_0" style="width:70px" onchange="saveField('0')" checked/>
												<label for="toggle-on" class="btn inline  zero round no-pad"><span>Active</span></label>

												<input type="radio" name="valid_0" value="false" id="toggle-off_0" style="width:70px" onchange="saveField('0')" />
												<label for="toggle-off" class="btn round inline zero no-pad"><span class="">Inactive</span></label>
											</td>
											
											<td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_manpower btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_manpower btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_manpower btn-danger deleteRowButton deleteRowButton_0' onclick="deleteManpowerDetails(0)">
								                        <i class='fa fa-minus-circle btn_manpower btn-danger'></i>
								                    </button>
								                </div>
								            </td>
											
										</tr>
										<tr id="1row">
											<td >
								    			<input class="hidden" id="validField_1"/>
								            	<input class="hidden" id="ManpowerId_1"/>
												<div class="input-container-manpower">
													<select id="numProjectRoles_1" class="select2Option" onchange="saveField('1')" style="width:120px">
														<option value="0"> -- Select Project Roles -- </option>
														<c:forEach items="${projectRoleDetails}" var="prDetails">
															<option value="${prDetails.numId}"> ${prDetails.strRoleName} </option>
														</c:forEach>
													
													</select>
												</div>
												<div class="alert-danger text-center numProjectRoles_1 errorMessageClass" role="alert"></div>
											</td>
										
											<td >
												<div class="input-container-manpower">
													<select id="designationId_1" class="select2Option" onchange="saveField('1')" style="width:120px">
														<option value="0"> -- Select Designation -- </option>
														<c:forEach items="${designations}" var="designation">
															<option value="${designation.numId}"> ${designation.designationName} </option>
														</c:forEach>
														<%-- <form:errors path="designationId" cssClass="error" /> --%>
													</select>
												</div>
												<div class="alert-danger text-center designationId_1 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<select id="deputedAt_1" class="select2Option" onchange="saveField('1')" style="width:120px">
														<option value="3"> -- Select -- </option>
														<option value="0">CDAC Noida </option>
														<option value="1">Client Site</option>													
													</select>
												</div>
												<div class="alert-danger text-center deputedAt_1 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="purpose_1" onblur="saveField('1')" style="width:200px"/>
													<%-- <form:errors path="purpose" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center purpose_1 errorMessageClass" role="alert"></div>
											</td>
					
											<td class=" bold" id="actualCost_1" style="width:5%">
												
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="ratePerManMonth_1" onblur="saveField('1')" style="width:90px"/>
													<%-- form:errors path="ratePerManMonth" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center ratePerManMonth_1 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="count_1" onblur="saveField('1')"/>
													<%-- <form:errors path="count" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center count_1 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-th-large icon_manpower"></i> -->
													<input class="input-field" id="involvement_1" onblur="saveField('1')"/>
													<%-- <form:errors path="involvement" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center involvement_1 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field" readonly='true' id="startDate_1" onchange="saveField('1')" style="width:90px"/>							
													<%-- <form:errors path="startDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center startDate_1 errorMessageClass" role="alert"></div>
											</td>
				
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field" readonly='true' id="endDate_1" onchange="saveField('1')" style="width:90px"/>							
													<%-- <form:errors path="endDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center endDate_1 errorMessageClass" role="alert"></div>
											</td>												
												
											<c:if test="${selectedRole.numRoleId == 5}"> 
													<td class="" >
														 <div class="input-container-manpower center"> 											
																<input type="checkbox" id="required_1" name="required_1" onclick="saveField('1')"/>
																<input class="hidden" name="requiredType_1" id="requiredType_1" value="1"/>													
														</div> 
														<div class="alert-danger text-center required_1 errorMessageClass" role="alert"></div>
													</td>						
											</c:if> 
				
											<td class=" statusColumn" >
												<input type="radio" name="valid_1" value="true" id="toggle-on_1" style="width:70px" onchange="saveField('1')" checked/>
												<label for="toggle-on" class="btn inline  zero round no-pad"><span>Active</span></label>

												<input type="radio" name="valid_1" value="false" id="toggle-off_1" style="width:70px" onchange="saveField('1')"/>
												<label for="toggle-off" class="btn round inline zero no-pad"><span class="">Inactive</span></label>
											</td>
											
											<td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_manpower btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_manpower btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_manpower btn-danger deleteRowButton' onclick="deleteManpowerDetails(1)">
								                        <i class='fa fa-minus-circle btn_manpower btn-danger'></i>
								                    </button>
								                </div>
								            </td>
											
										</tr>
										<tr id="2row">
											<td >
								    			<input class="hidden" id="validField_2"/>
								            	<input class="hidden" id="ManpowerId_2"/>
												<div class="input-container-manpower">
													<select id="numProjectRoles_2" class="select2Option" onchange="saveField('2')" style="width:120px">
														<option value="0"> -- Select Project Roles -- </option>
														<c:forEach items="${projectRoleDetails}" var="prDetails">
															<option value="${prDetails.numId}"> ${prDetails.strRoleName} </option>
														</c:forEach>
													
													</select>
												</div>
												<div class="alert-danger text-center numProjectRoles_2 errorMessageClass" role="alert"></div>
											</td>
										
											<td >
												<div class="input-container-manpower">
													<select id="designationId_2" class="select2Option" onchange="saveField('2')" style="width:120px">
														<option value="0"> -- Select Designation -- </option>
														<c:forEach items="${designations}" var="designation">
															<option value="${designation.numId}"> ${designation.designationName} </option>
														</c:forEach>
														<%-- <form:errors path="designationId" cssClass="error" /> --%>
													</select>
												</div>
												<div class="alert-danger text-center designationId_2 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<select id="deputedAt_2" class="select2Option" onchange="saveField('2')" style="width:120px">
														<option value="3"> -- Select -- </option>
														<option value="0">CDAC Noida </option>
														<option value="1">Client Site</option>													
													</select>
												</div>
												<div class="alert-danger text-center deputedAt_2 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="purpose_2" onblur="saveField('2')" style="width:200px"/>
													<%-- <form:errors path="purpose" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center purpose_2 errorMessageClass" role="alert"></div>
											</td>
					
											<td class=" bold" id="actualCost_2">
												
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="ratePerManMonth_2" onblur="saveField('2')" style="width:90px"/>
													<%-- form:errors path="ratePerManMonth" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center ratePerManMonth_2 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="count_2" onblur="saveField('2')"/>
													<%-- <form:errors path="count" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center count_2 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-th-large icon_manpower"></i> -->
													<input class="input-field" id="involvement_2" onblur="saveField('2')"/>
													<%-- <form:errors path="involvement" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center involvement_2 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field" readonly='true' id="startDate_2" onchange="saveField('2')" style="width:90px"/>							
													<%-- <form:errors path="startDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center startDate_2 errorMessageClass" role="alert"></div>
											</td>
				
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field" readonly='true' id="endDate_2" onchange="saveField('2')" style="width:90px"/>							
													<%-- <form:errors path="endDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center endDate_2 errorMessageClass" role="alert"></div>
											</td>												
												
											<c:if test="${selectedRole.numRoleId == 5}"> 
													<td class="" >
														 <div class="input-container-manpower center"> 											
																<input type="checkbox" id="required_2" name="required_2" onclick="saveField('2')"/>
																<input class="hidden" name="requiredType_2" id="requiredType_2" value="1"/>													
														</div> 
														<div class="alert-danger text-center required_2 errorMessageClass" role="alert"></div>
													</td>						
											</c:if> 
				
											<td class=" statusColumn" >
												<input type="radio" name="valid_2" value="true" id="toggle-on_2" style="width:70px" onchange="saveField('2')" checked/>
												<label for="toggle-on" class="btn inline  zero round no-pad"><span>Active</span></label>

												<input type="radio" name="valid_2" value="false" id="toggle-off_2" style="width:70px" onchange="saveField('2')"/>
												<label for="toggle-off" class="btn round inline zero no-pad"><span class="">Inactive</span></label>
											</td>
											
											<td class="ActionColumn" >
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_manpower btn-primary addRowButton'>
								                        <!-- <i class='fa fa-plus-circle btn_manpower btn-primary'></i> -->
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_manpower btn-danger deleteRowButton' onclick="deleteManpowerDetails(2)">
								                        <i class='fa fa-minus-circle btn_manpower btn-danger'></i>
								                    </button>
								                </div>
								            </td>
											
										</tr>
										<tr id="3row">
											<td >
								    			<input class="hidden" id="validField_3"/>
								            	<input class="hidden" id="ManpowerId_3"/>
												<div class="input-container-manpower">
													<select id="numProjectRoles_3" class="select2Option" onchange="saveField('3')" style="width:120px">
														<option value="0"> -- Select Project Roles -- </option>
														<c:forEach items="${projectRoleDetails}" var="prDetails">
															<option value="${prDetails.numId}"> ${prDetails.strRoleName} </option>
														</c:forEach>
													
													</select>
												</div>
												<div class="alert-danger text-center numProjectRoles_3 errorMessageClass" role="alert"></div>
											</td>
										
											<td >
												<div class="input-container-manpower">
													<select id="designationId_3" class="select2Option" onchange="saveField('3')" style="width:120px">
														<option value="0"> -- Select Designation -- </option>
														<c:forEach items="${designations}" var="designation">
															<option value="${designation.numId}"> ${designation.designationName} </option>
														</c:forEach>
														<%-- <form:errors path="designationId" cssClass="error" /> --%>
													</select>
												</div>
												<div class="alert-danger text-center designationId_3 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<select id="deputedAt_3" class="select2Option" onchange="saveField('3')" style="width:120px">
														<option value="3"> -- Select -- </option>
														<option value="0">CDAC Noida </option>
														<option value="1">Client Site</option>													
													</select>
												</div>
												<div class="alert-danger text-center deputedAt_3 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="purpose_3" onblur="saveField('3')" style="width:200px"/>
													<%-- <form:errors path="purpose" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center purpose_3 errorMessageClass" role="alert"></div>
											</td>
					
											<td class=" bold" id="actualCost_3">
												
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="ratePerManMonth_3" onblur="saveField('3')" style="width:90px"/>
													<%-- form:errors path="ratePerManMonth" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center ratePerManMonth_3 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-user icon_manpower"></i> -->
													<input class="input-field" id="count_3" onblur="saveField('3')"/>
													<%-- <form:errors path="count" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center count_3 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-th-large icon_manpower"></i> -->
													<input class="input-field" id="involvement_3" onblur="saveField('3')"/>
													<%-- <form:errors path="involvement" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center involvement_3 errorMessageClass" role="alert"></div>
											</td>
					
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field" readonly='true' id="startDate_3" onchange="saveField('3')" style="width:90px"/>							
													<%-- <form:errors path="startDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center startDate_3 errorMessageClass" role="alert"></div>
											</td>
				
											<td >
												<div class="input-container-manpower">
													<!-- <i class="fa fa-calendar icon_manpower"></i> -->
													 <input class="input-field" readonly='true' id="endDate_3" onchange="saveField('3')" style="width:90px"/>							
													<%-- <form:errors path="endDate" cssClass="error" /> --%>
												</div>
												<div class="alert-danger text-center endDate_3 errorMessageClass" role="alert"></div>
											</td>												
												
											<c:if test="${selectedRole.numRoleId == 5}"> 
													<td class="" >
														 <div class="input-container-manpower center"> 											
																<input type="checkbox" id="required_3" name="required_3" onclick="saveField('3')"/>
																<input class="hidden" name="requiredType_3" id="requiredType_3" value="1"/>													
														</div> 
														<div class="alert-danger text-center required_3 errorMessageClass" role="alert"></div>
													</td>						
											</c:if> 
				
											<td class=" statusColumn" >
												<input type="radio" name="valid_3" value="true" id="toggle-on_3" style="width:70px" onchange="saveField('3')" checked/>
												<label for="toggle-on" class="btn inline  zero round no-pad"><span>Active</span></label>

												<input type="radio" name="valid_3" value="false" id="toggle-off_3" style="width:70px" onchange="saveField('3')"/>
												<label for="toggle-off" class="btn round inline zero no-pad"><span class="">Inactive</span></label>
											</td>
											
											<td class="ActionColumn" >
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_manpower btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_manpower btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_manpower btn-danger deleteRowButton' onclick="deleteManpowerDetails(3)">
								                        <i class='fa fa-minus-circle btn_manpower btn-danger'></i>
								                    </button>
								                </div>
								            </td>
											
										</tr>
									</tbody>
						</table>
					</div>						
				</div>
<!-- End of rows added for multiple Manpower Requirements -->	
				
						<sec:authorize access="hasAuthority('WRITE_MANPOWER_MST')">	
			
				<div class="row pad-top  form_btn">
					
						<!-- <button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button> -->
						<!-- Modified by devesh on 16-11-23 to save multiple Manpower Req -->
						<button type="button" class="btn btn-primary font_14" id="saveManpower">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<!-- End of Button -->
								</div>		
									
									<c:if test="${projectId>0 }"> 
																			
										<div class="row padded pull-left">
													<button type="button" class="btn btn-info reset font_14 "
														id="prev" onclick="goprev()">
														<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
													</button>
												</div>
												<div class="row padded pull-right">
													<button type="button" class="btn btn-info reset font_14 "
														id="next" onclick="gonext()">
														<span class="pad-right"><i
															class="fa fa-arrow-right"></i></span>Next
													</button>
												</div>
						
									</c:if>
									
					
					</sec:authorize>
					</div>
					</div>
					</div>
					</div>
			</form:form>
			

		<!-- </div> -->
		<!--End of panel-->
		<!--Start data table-->


	<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
			<table id="datatable" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20"><spring:message code="master.select"/></th>							
						<th><spring:message code="employee.designation"/></th>
						<th><spring:message code="Project_Payment_Schedule.purpose"/></th>
						<th><spring:message code="manpower.req.manmonth.rate"/></th>	
						<th><spring:message code="manpower.count"/></th>
						<th><spring:message code="manpower.involvement"/></th>			
						<th><spring:message code="employee_Role.start_Date"/></th>
						<th><spring:message code="employee_Role.end_Date"/></th>
						<th class="hidden"><spring:message code="document.description"/></th>
						<th><spring:message code="to.be.Deputed"/></th>
						<th><spring:message code="required.Type"/></th>
						<th><spring:message code="forms.status"/></th>
						<th class="hidden"></th>
						<th><spring:message code="label.project.based.role"/></th>  
						<th><spring:message code="forms.edit"/></th>
						<th class="hidden"></th>
						<th class="hidden"></th>
						<th class="hidden"></th>
						<th class="hidden"></th>
					</tr>
				</thead>
				<tbody class="">
					
				</tbody>
			</table>
			
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
	</div>
		</div> 
		
	</section>


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/ManpowerRequirement.js"></script>
</body>
</html>