<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
	#team-modal{
	padding-top: 5%;	
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
						<li class="active"><spring:message code="menu.project.underApproval" /></li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
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
		

	
	<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<input type="hidden" value="${encWorkflowId}" id="encWorkflowId"/>		
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
				
					<tr>										
 					<th><spring:message code="project.projectRefNo"/></th>						
 						<th><spring:message code="Project_Payment_Schedule.projectName"/></th>
						<th style="width:8%;"><spring:message code="projectMaster.type"/></th>
						<th><spring:message code="projectMaster.startDate"/></th>
						<th><spring:message code="projectMaster.endDate"/></th>
	
						<th style="width:8%;"><spring:message code="projectMaster.duration"/> <br>(in months)</th>
						<th style="width:8%;"><spring:message code="projectMaster.Cost"/></th>
						<th><spring:message code="forms.status"/></th>
						<th style="width:3%;"><spring:message code="forms.action"/> </th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${data}" var="projectData" varStatus="outerLoopStatus">						
						<tr
            				<c:if test="${projectData.projectRefrenceNo == null}">
                				style="color:#800080"
            				</c:if>>
							<td>${projectData.projectRefrenceNo}</td>	
 							<td class="font_16"><a title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectData.encProjectId}')"
 													<c:if test="${projectData.projectRefrenceNo == null}">
                        								style="color: #800080;"
                    								</c:if>>
 													${projectData.strProjectName}
 												</a>
 							</td>
							<td>${projectData.businessType}</td>
							<td>${projectData.startDate}</td>
							<td>${projectData.endDate}</td>							
							<td>${projectData.projectDuration}</td>
							<td>
								${projectData.strTotalCost}
								<!--------------------- Check Project Team contains HOD or not [ 27/07/2023 added by_Anuj ] ----------------------------------------------->
								<c:forEach items="${HODStatus}" var="CurrentHOD" varStatus="innerLoopStatus">
									<c:if test="${outerLoopStatus.index == innerLoopStatus.index}">
										<c:set value="${CurrentHOD}" var="CurrentHODStatus"/>
									</c:if>
								</c:forEach>
								<input type="text" class="${projectData.encProjectId} hidden" value="${CurrentHODStatus}" readonly>
								<!------------------------------- END [ 27/07/2023 added by_Anuj ] --------------------------------------------------------------------->	
							</td>
							<td>${projectData.workflowModel.strActionPerformed} by <b>${projectData.workflowModel.employeeName}</b> at ${projectData.workflowModel.transactionAt}</td>
							<td><div class="dropdown">																						
							 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick=viewAllowedActions('${projectData.encProjectId}') aria-haspopup="true" aria-expanded="false">
								<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
							<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}"></ul></div>
							</td>								
						</tr>
						
					</c:forEach>
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
<!-- Added by Devesh on 17/7/23 for view proceeding modal -->
<div class="modal amount_receive_deatil_modal" id="proceedingModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Proceeding Details</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body table-responsive">
					<table id="proceedingTbl" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						   <th><spring:message code="forms.serialNo" /></th> 
						   <th><spring:message code="employee_Role.employee_Name"/></th> 
						   <th><spring:message code="forms.action"/></th>
						   <th>Remarks</th>
						   <th>Date</th><!-- Added by devesh on 18/7/23 for displaying transaction date -->
							
						</tr>
					</thead>
					<tbody class="">
					
				</tbody>
			</table>
				
				</div>
				<div class="modal-footer" id="modelFooter">
					
				</div>
			</div>
		</div>
	</div>
<!-- End of View Proceeding modal -->
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/underApprovalProjects.js"></script>
</body>
</html>