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
.bg_grey{
background: LemonChiffon !important;
}
</style>
<body>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<li class="active"><spring:message code="project.closure.head"/></li>
					</ol>
				</div>
			</div>
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

<sec:authorize access="hasAnyAuthority('PROJECT_CLOSURE','UNDER_CLOSURE_PROJECTS')">
<%-- <c:choose> --%>
	<%-- <c:when test="${editMode == 1 }"> --%>
		<div class="container pad-bottom">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">				
					<div class="panel-body">						
	 					<c:if test = "${not empty projectDetails.projectRefNo}">
							<p><span class="bold  font_14 ">Project Reference Number: </span> <span class="bold blue font_14"><i>${projectDetails.projectRefNo}</i></span></p>
						</c:if>
						<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${projectDetails.strProjectName}</i></span></p>
						<p><span class="bold  font_16 ">Start Date: </span> <span class="bold blue font_16" id="projectStartDate">${projectDetails.startDate}</span></p>
						<p><span class="bold  font_16 ">End date: </span> <span class="bold blue font_16" id="projectEndDate">${projectDetails.endDate}</span></p>										
						
						
					</div>
				</div>
			</div>
		</div>
					
	<form:form id="form1" modelAttribute="projectClosureModel" action="/PMS/mst/saveUpdateEmployeeRoleMaster" method="post">
			<form:hidden path="encProjectId" value="${projectDetails.encProjectId}"/>	
				<div class="container">
					
			
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.date"/>:</label> ${projectTempMasterDetail.closureDate}
						</div>
					
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.remarks"/>:</label> ${projectTempMasterDetail.closureRemark}
						</div>

					
					</div> 
					
					
			</div>
			<%------  Add Container for table[14-09-2023] --%>
			<div class="container">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">				
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:5%;"><spring:message code="serial.no"/></th>
 					    <th style="width:12%;"><spring:message code="document.documentName"/></th>
						<th style="width:10%;"><spring:message code="task.download" /></th>
		
					    
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${docDetails}" var="documentdetail" varStatus="theCount">
						<tr >
						<%-- <c:out value='${documentdetail[theCount.index-1].value.documentTypeName}'/> --%>
						    <td>${theCount.count}</td>
						    <td ><p class="bold">${documentdetail.documentTypeName}</p>
						    <c:choose>
						    <c:when test="${documentdetail.periodFrom != null}">
						   
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">Valid to: ${documentdetail.periodTo}</span></p>
						    </c:when>
						    <c:otherwise>
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">${documentdetail.periodTo}</span></p>
						    </c:otherwise>
						    </c:choose>
						    </td>
    						
							<td align="center">
								<c:forEach items="${documentdetail.detailsModels}" var="details" varStatus="theCount">
									<span> <a onclick=downloadTempDocument("${details.encNumId}")>${details.icon}</a> </span>
								</c:forEach>			
 							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			</div>
			<%------  EOF Container for table[14-09-2023] --%>
			<c:if test="${not empty teamDetails}">
		<div class="container pad-top">
			<fieldset>
	    		<legend><spring:message code="project.closure.deallocate.team"/>:</legend>
	    		
	    		<div class="pad-top pad-bottom"><b class="red">Note : </b> if Effective Upto Date is not chosen explicitly, closure date will be considered. </div>
	    		
	    			<table class="table">
						<thead>
							<tr>
								<th width="30%"><spring:message code="Designation_Master.designationName"/></th>
								<th width="25%"><spring:message code="project_Team_Details.role_Name"/></th>								
								<th width="15%"><spring:message code="project_Team_Details.effective_From"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/> </th>
								<%-- <th width="15%"><spring:message code="project_Team_Details.effective_Upto"/></th> --%>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach items="${teamDetails}" var="member" varStatus="loop">
								<tr>
									
									<td> <input id="role_${loop.index}" type="hidden" value="${member.encRoleId}"/>
									<input type="hidden" id="empId_${loop.index}" value="${member.encEmployeeId}"/>
										${member.strEmpName}</td>
									<td>${member.strRoleName}</td>
									<td class="startDate" id="startDate_${loop.index}">${member.strStartDate}</td> 
									<td id="end_${loop.index}">${member.strEndDate}</td>
									<%-- <td>
									<input type="hidden" id="empRole_${loop.index}" value="${member.encId}"/>
									<input class="datePicker input-field" id="picker_${loop.index}" readonly="readonly">
									</td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
	    	</fieldset>
    		</div>
    	</c:if>
			
			<br/>
		 
					
					<div class="row pad-top pad-bottom form_btn center">
					<%-- 	<c:if test="${editMode == 1}">
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Close Project
						</button></c:if> --%>
						<a href="/PMS/mst/underClosureProjects" class="btn btn-orange font_14">												
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
						</a>
					</div>
	</form:form>
	<%-- </c:when> --%>
	<%-- <c:otherwise>
		<div class="container pad-bottom">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">				
					<div class="panel-body">						
	 					<c:if test = "${not empty projectDetails.projectRefNo}">
							<p><span class="bold  font_14 ">Project Reference Number: </span> <span class="bold blue font_14"><i>${projectDetails.projectRefNo}</i></span></p>
						</c:if>
						<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${projectDetails.strProjectName}</i></span></p>
						<p> <span class="bold blue font_16">Already Project status is set to ${projectDetails.status} </span></p>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.date"/>:</label>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="input-container">
								{projectDetails.tempProjectClosureDate}
							
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.remarks"/>:<span
									style="color: red;">*</span></label>
						</div>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="input-container">
								{projectDetails.strProjectRemarks}
							
							</div>
						</div>
					</div>
					
						<div class="col-md-12 col-lg-12 col-xs-12 pad-bottom">
							<div class="pull-right">
								<a href="/PMS/mst/ViewAllProjects" class="btn btn-orange font_14">												
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
								</a>
							</div>
						</div>				
					</div>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose> --%>
		
	
</sec:authorize> 
	</div>
	</section>

	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/projectClosure.js"></script>
</body>
</html>