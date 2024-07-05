<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bhavesh (17-07-23)added style in text -->
<style>
	#team-modal{
	padding-top: 5%;	
	}
	
	.golden-text {
  color: #cc9900;
}
.grey-text {
	color: #808080; /* Replace this with the shade of grey you prefer */
}

.brown-text {
  color: #993300;
}
.legend {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f1f1f1;
    padding: 10px;
    border: 1px solid #ccc;
}

.legend-item {
    display: flex;
    align-items: center;
    margin-right: 15px;
}

.legend-color {
    width: 20px;
    height: 20px;
    margin-right: 5px;
    border-radius: 50%;
}
	
</style>

</head>
<body>

<section id="main-content" class="main-content merge-left">
<input type="hidden"  id="roleId" value="${roleId}">
<input type="hidden"  id="encActionId" value="">
<input type="hidden"  id="customId" value="">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>						
						<li class="active"><spring:message code="menu.project.underClosure" /></li>
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
 					<th style="width:2%;"><spring:message code="serial.no"/></th>
 					<%------------------ Add roleId 13 for Fin Executive [30-11-2023] -----------------------------------------%>
 					<c:if test="${roleId!=7 || roleId!=13}">
 					<th><spring:message code="group.groupName"/></th>	
 					</c:if>
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
				
				<thead  class="filters">
										<tr>
										<th></th>
										<c:if test="${roleId==9}">										
											<th class="comboBox"><spring:message code="group.groupName"/></th></c:if>
											<!-- Added to fix search filter on underclosure datatable for PL and GC on 18/8/23 by devesh -->
											<c:if test="${roleId==5}"><th></th></c:if>
											<c:if test="${roleId==5}"><th></th></c:if>
											<c:if test="${roleId==3}"><th></th></c:if>
											<c:if test="${roleId==3}"><th></th></c:if>
											<%------------------ Add roleId 13 for Fin Executive [30-11-2023] -----------------------------------------%>
											<c:if test="${roleId==7 || roleId==13}"><th></th></c:if>
											<c:if test="${roleId==7}"><th></th></c:if>
											<c:if test="${roleId==7}"><th></th></c:if>
											<c:if test="${roleId==15}"><th></th></c:if>
											<c:if test="${roleId==15}"><th></th></c:if>
											<!-- --------------------------------------------------------------- -->
											<th></th>
											<th></th>
											<c:if test="${roleId==9}">		<th class="comboBox"><spring:message code="projectMaster.type"/></th></c:if>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<!-- <th></th>	 -->																		
										</tr>
									</thead>
				
			
				<tbody>
				
					<%-- <c:forEach items="${data}" var="projectData"> --%>
					<c:forEach items="${data}" var="projectData" varStatus="outerLoopStatus"><!-- Added by devesh on 06-09-23 to add condition for checking HODstatus  -->
						<c:choose>
						<%------------------ Add roleId 13 for Fin Executive [30-11-2023] -----------------------------------------%>
                <c:when test="${roleId==7 || roleId==13}">									
						<tr>
                			<td class="brown-text text-center">${outerLoopStatus.count}</td> 
                			<td class="brown-text text-center">${projectData.strGroupName}</td>
                   			<td class="brown-text">${projectData.projectRefrenceNo}</td>
							<td class="font_16"><a title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectData.encProjectId}')"><span  class="brown-text">${projectData.strProjectName}</span></a></td>
							<td class="brown-text">${projectData.businessType}</td>
							<td class="brown-text" style="width:10%;">${projectData.startDate}</td>
							<td class="brown-text" style="width:10%;">${projectData.endDate}</td>
							<td class="brown-text" align="center">${projectData.projectDuration}</td>
							<%-- <td style="width:10%;"><span class="font_16">${projectdetail.strPLName}</span></td> --%>
							<td  class="brown-text">${projectData.strTotalCost}</td>
							<td  class="brown-text">${projectData.workflowModel.strActionPerformed} by <b>${projectData.workflowModel.employeeName}</b> at ${projectData.workflowModel.transactionAt}</td>
							<td><div class="dropdown">																						
							 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick=viewAllowedActions('${projectData.encProjectId}') aria-haspopup="true" aria-expanded="false">
								<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
							<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}"></ul></div>
							</td>
							</tr>
                </c:when>
                <c:otherwise>
                	<c:set value="golden-text" var="rowColor"/>
                	<c:if test="${projectData.workflowModel.strActionPerformed=='Sent back to PM'}">
                		<c:if test="${projectData.workflowModel.numLastRoleId == 5}">
                			<c:set value="grey-text" var="rowColor"/>
                		</c:if>
                	</c:if>
                <tr class="${rowColor}">	
                    <!-- Add your content for the "otherwise" case here -->
                    <td class="text-center">${outerLoopStatus.count}</td> 
                   <td>${projectData.strGroupName}</td>
                   <td>${projectData.projectRefrenceNo}</td>	
 							<td class="font_16"><a title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectData.encProjectId}')"><span class="${rowColor }">${projectData.strProjectName}</span></a></td>
							<td>${projectData.businessType}</td>
							<td>${projectData.startDate}</td>
							<td>${projectData.endDate}</td>							
							<td>${projectData.projectDuration}</td>
							<td>
								${projectData.strTotalCost}
								<!-- Added by devesh on 01-09-23 to Check Project Team contains HOD or not -->
								<c:forEach items="${HODStatus}" var="CurrentHOD" varStatus="innerLoopStatus">
									<c:if test="${outerLoopStatus.index == innerLoopStatus.index}">
										<c:set value="${CurrentHOD}" var="CurrentHODStatus"/>
									</c:if>
								</c:forEach>
								<input type="text" class="${projectData.encProjectId} hidden" value="${CurrentHODStatus}" readonly>
								<!-- END -->
							</td>
							<td  class="">${projectData.workflowModel.strActionPerformed} by <b>${projectData.workflowModel.employeeName}</b> at ${projectData.workflowModel.transactionAt}</td>
							<%--
							<td>			
								<div class="dropdown">																						
								 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick=viewAllowedActions('${projectData.encProjectId}') aria-haspopup="true" aria-expanded="false">
									<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
								<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}"></ul></div>
							</td>	 
							--%>
							<td>
							<%-------------------- Action With selected role id [15-09-2023] -----------------------------%>
				            <c:choose>
				                <c:when test="${(projectData.projectId == selectedRoleProjectId) || (selectedRoleProjectId==0) || (allRolesAreSame==true)}">
				                    <div class="dropdown">																						
								 		<a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick=viewAllowedActions('${projectData.encProjectId}') aria-haspopup="true" aria-expanded="false">
										<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
										<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}"></ul>
									</div>
				                </c:when>
				                <c:otherwise>
				                    <div class="dropdown">																						
								 		<a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
										<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}">
											<li> <a class='font_14 red'><span  aria-hidden='true'> No Action </span></a></li>
										</ul>
									</div>
				                </c:otherwise>
				                </c:choose>
				             <%-------------------- END Action With selected role id -----------------------------%>
							</td>
							</tr>
                </c:otherwise>
            </c:choose>
				<!--Bhavesh(17-08-23)-->		
					 <%-- <c:if test="${roleId==9}"> 
						<td class="golden-text">${projectData.strGroupName}</td>
					</c:if> 
								
							<td class="golden-text">${projectData.projectRefrenceNo}</td>	
 							<td class="font_16"><a title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectData.encProjectId}')"><span  class="golden-text">${projectData.strProjectName}</span></a></td>
							<td  class="golden-text">${projectData.businessType}</td>
							<td  class="golden-text">${projectData.startDate}</td>
							<td  class="golden-text">${projectData.endDate}</td>							
							<td  class="golden-text">${projectData.projectDuration}</td>
							<td  class="golden-text">${projectData.strTotalCost}</td>
							<td  class="golden-text">${projectData.workflowModel.strActionPerformed} by <b>${projectData.workflowModel.employeeName}</b> at ${projectData.workflowModel.transactionAt}</td>
							<td><div class="dropdown">																						
							 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick=viewAllowedActions('${projectData.encProjectId}') aria-haspopup="true" aria-expanded="false">
								<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
							<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}"></ul></div>
							</td> --%>									
					</c:forEach>
				</tbody>
				<c:choose>
                <c:when test="${roleId==7}"> 
                   <!--Bhavesh 18-08-23 added legends brown color for finacial closure and golden yellow for technical closure  -->
										<tfoot>
											<tr >
												<td colspan="11">
      <div class="legend">
        
        
        <div class="legend-item">
          <div class="legend-color" style="background-color: #993300;"></div>
          <span>Pending for Financial Closure </span>
        </div>
        <!-- Add more legend items as needed -->
      </div>
    </td>
											</tr>
										</tfoot>
                </c:when>
                <c:otherwise>
                     <!--Bhavesh 18-08-23 added legends brown color for finacial closure and golden yellow for technical closure  -->
										<tfoot>
											<tr >
												<td colspan="11">
      <div class="legend">
                <div class="legend-item">
          <div class="legend-color" style="background-color: #808080;"></div>
          <span>Closure Not Initiated</span>
        </div>
        
        <div class="legend-item">
          <div class="legend-color" style="background-color:#cc9900;"></div>
          <span> Closure Initiated </span>
        </div>
        <!-- Add more legend items as needed -->
      </div>
    </td>
											</tr>
										</tfoot>
                </c:otherwise>
            </c:choose>
			</table>
			
			<!--End of data table-->
		</fieldset>
	</div>
	<c:if test="${flag==1}">
	<c:if test="${roleId!=7}">
	<div class="row">
		
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">
<legend class="bold" style="width: 80%;display: block;width: 20% !important;padding: 0;
    margin-bottom: 0px;
    font-size: 15px;
    line-height: inherit;
    color: black !important;
    border: 0;"> Projects pending for Financial Closure :</legend>
			<!-- <legend class="bold legend_details">Projects pending for financial Closure :</legend>  -->
		<div class="table-responsive">
			<table id="example1" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
					   <th style="width:2%;"><spring:message code="serial.no"/></th>
					    <th><spring:message code="project.projectRefNo"/></th>											    
						<th><spring:message code="Project_Module_Master.projectName"/></th>
						<th><spring:message code="application.project.category.label"/></th>
						<th><spring:message code="employee_Role.start_Date"/></th>
						<th><spring:message code="employee_Role.end_Date"/></th>
						<th style="width:10%;"><p><spring:message code="project.details.duration"/></p><p>(months)</p></th>
						<%-- <th><spring:message code="project.details.leader"/></th> --%>
						<th style="width:10%;"><p><spring:message code="project.details.totaloutlay"/></p><p>(in Lakhs)</p></th>
						<%-- <th style="width:10%;"><p><spring:message code="project.details.amountreceived"/></p><p>(in Lakhs)</p></th> --%>
						<%-- <th style="width:10%;"><p><spring:message code="project.details.closureDate"/></p></th> --%>
						<th style="width:10%;"><p><spring:message code="project.details.TechclosureDate"/></p></th>
						<%-- <th><spring:message code="task.download" /></th> --%>
						<th style="width:3%;"><spring:message code="forms.action"/> </th>
		
					    
					</tr>
				</thead>
		<%-- <thead class="filters">
		<tr>     
		<th><i class="fa fa-th-list"></i></th>
		<th class="textBox"><spring:message code="project.projectRefNo"/></th>
		<th class="textBox"><spring:message code="Project_Module_Master.projectName"/></th>
		                                                           
				<th class="comboBox" id="businessTypeSelect"><spring:message code="application.project.category.label"/></th>
				<th class="comboBox" style="width:10%;"><spring:message code="employee_Role.start_Date"/></th>
				<th class="comboBox" style="width:10%;"><spring:message code="employee_Role.end_Date"/></th>
				<th style="width:10%;" class="comboBox" ><spring:message code="project.details.duration"/></th>
				<th class="textBox" ><spring:message code="project.details.leader"/></th>
				<th style="width:10%;" class="textBox"><spring:message code="project.details.totalcost.lakhs"/></th>
				<th style="width:10%;" class="textBox"><spring:message code="project.details.amountreceived.lakhs"/></th>
				<th style="width:10%;"></th>
				<!-- <th style="width:10%;"></th> -->
				<th style="width:3%;"></th>
		</tr>
</thead> --%>
				<tbody class="">
					<c:forEach items="${FinancialPendingProjectDetails}" var="projectdetail" varStatus="theCount">
							<tr id='${projectdetail.encProjectId}'>
						    <td class="brown-text text-center">${theCount.count }</td> 
							<td class="brown-text">${projectdetail.projectRefrenceNo}</td>
							<td class="font_16 " style="width:15%;" id="td2_${projectdetail.encProjectId}"><p><a class="bold" title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')"><span   class="brown-text">${projectdetail.strProjectName}</span></a></p>
							 <p class="font_14 "><i><a class="orange" title="Click here to view Funding org details" data-toggle='modal' data-target='#clientDetails' data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}' class='text-center'>${projectdetail.clientName}</a></i></p> 
							</td>
							<td class="brown-text">${projectdetail.businessType}</td>
							<td class="brown-text" style="width:10%;">${projectdetail.startDate}</td>
							<td class="brown-text" style="width:10%;">${projectdetail.endDate}</td>
							<td class="brown-text" align="center">${projectdetail.projectDuration}</td>
							<%-- <td style="width:10%;"><span class="font_16">${projectdetail.strPLName}</span></td> --%>
							<td class="brown-text" align="right" class=""><input type="hidden" id="hiddenTotalAmt_${projectdetail.encProjectId}" value="${projectdetail.strTotalCost}"/><span  class="convertLakhs" id="totalAmt_${projectdetail.encProjectId}">${projectdetail.strTotalCost} </span></td>
 							<%-- <td align="right"><input type="hidden" id="hiddenAmt_${projectdetail.encProjectId}" value="${projectdetail.strReceivedAmout}"/> <a title="Click here to view received details" data-toggle='modal' data-target='#amtreceive' data-whatever='${projectdetail.encProjectId};${projectdetail.startDate};${projectdetail.strTotalCost}' class='text-center'>
							<span  id="amt_${projectdetail.encProjectId}">${projectdetail.strReceivedAmout} </span></a></td> --%>
							<%-- <td>${projectdetail.closureDate}</td> --%>
							<td class="brown-text">${projectdetail.strTechClosureDate}</td>
<%-- 							
 --%>							<td><div class="dropdown">																						
							 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick=viewAllowedActionsForPending('${projectdetail.encProjectId}') aria-haspopup="true" aria-expanded="false">
								<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
							<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="new${projectdetail.encProjectId}"></ul></div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				 <!--Bhavesh 28-07-23 added legends  -->
										<tfoot>
											<tr >
												<td colspan="10">
      <div class="legend">
        
        
        <div class="legend-item">
          <div class="legend-color" style="background-color: #993300;"></div>
          <span>Pending for Financial Closure </span>
        </div>
        <!-- Add more legend items as needed -->
      </div>
    </td>
											</tr>
										</tfoot>
			</table>
			</div>
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>
</c:if>
</c:if>
	<!--End of datatable_row-->
</div>
	</div>
	
</div>
</section>

<!-- added the below model for client funding organisation by varun on 04-10-2023 -->
<!-- Modal For client Details -->
			<div class="modal amount_receive_deatil_modal" id="clientDetails"
				tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title  center" id="exampleModalLabel">
								<spring:message code="client.detail" />
							</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<div
								class="col-md-12 col-lg-12 col-xs-12 col-sm-12 table-responsive pad-top">
								<table class="table table-bordered">
									<tbody>
										<tr>
											<th><h5 class=" blue">
													<spring:message code="client.name" />
												</h5></th>
											<td><span class="black bold" id="clientName"></span></td>
										</tr>
										<tr>
											<th><h5 class=" blue">
													<spring:message code="master.shortCode" />
												</h5></th>
											<td><span class="black" id="shortCode"></span></td>
										</tr>
										<tr>
											<th><h5 class=" blue">
													<spring:message code="group.groupAddress" />
												</h5></th>
											<td><span class="black" id="address"></span></td>
										</tr>
										<tr>
											<th><h5 class=" blue">
													<spring:message code="master.contact" />
												</h5></th>
											<td><span class="black" id="contactNo"></span></td>
										</tr>
										<tr>
											<th><h5 class=" blue">
													<spring:message code="master.endUser" />
												</h5></th>
											<td><span class="black" id="endUserName"></span></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
								<h5>
									<u> Contact Person Details (For funding Org)</u>
								</h5>
							</div>

							<div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 table-responsive pad-top ">

								<table
									class="table table-bordered table-striped table-hover mb-0 "
									id="glance_data_table">
									<thead>
										<tr>
											<td><h5>
													<spring:message
														code="Client_Contact_Person_Master.clientContactPersonName" />
												</h5></td>
											<td><h5>
													<spring:message
														code="Client_Contact_Person_Master.contactPersonDesignation" />
												</h5></td>
											<td><h5>
													<spring:message
														code="Client_Contact_Person_Master.contactPersonMobileNumber" />
												</h5></td>
											<td><h5>
													<spring:message
														code="Client_Contact_Person_Master.contactPersonEmailId" />
												</h5></td>
											<td><h5>
													<spring:message
														code="Client_Contact_Person_Master.contactPersonOfficeAddress" />
												</h5></td>
									</thead>
									<tbody id="clientcontactDetails"></tbody>
								</table>
							</div>
							<%-- 	<div class="row pad-top ">
						<div class="col-md-2 center"><h5><spring:message code="client.name"/>:</h5></div>
						<div class="col-md-4"> <span class="blue" id="clientName"></span> </div>
					</div>
					<div class="row pad-top ">
						<div class="col-md-2 center"><h5><spring:message code="master.shortCode"/>:</h5></div>
						<div class="col-md-7"> <span class="blue" id="shortCode"></span> </div>
					</div>
					<div class="row pad-top ">
						<div class="col-md-2 center"><h5><spring:message code="group.groupAddress"/>:</h5></div>
						<div class="col-md-7"> <span class="blue" id="address"></span> </div>
					</div>
					<div class="row pad-top ">
						<div class="col-md-2 center"><h5><spring:message code="master.contact"/>:</h5></div>
						<div class="col-md-7"> <span class="blue" id="contactNo"></span> </div>
					</div> --%>

						</div>
						<div class="modal-footer" id="modelFooter"></div>
					</div>
				</div>
			</div>


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
	
	<div class="modal amount_receive_deatil_modal" id="financialApprovalModel"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				 <div class="modal-header">
					 <h4 class="modal-title  center" id="exampleModalLabel">Approve Financial Closure</h4> 
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<!-- <div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom "> -->

								<div class="pad-bottom"><label class="font_14 bold" for="from">Financial Closure Date:</label> <span class="red">*</span>   <input type="text"
									id="closureDate" name="from" readonly /> </div>
									<br>
									<!-- <label class="font_16" for="from">Remarks"</label> --> <textarea style="height: 121px;" id="remarksForClosure" class="form-control " placeholder="You can write Remarks" 
									></textarea>
									<div class='text-center pad-top pad-bottom'><input type="button" value="Submit" id="closureSubmit" onclick="submitClosureReq()"></div>
							<!-- </div> -->
				
				</div>
				<div class="modal-footer" id="modelFooter">
					
				</div>
			</div>
		</div>
	</div>
	

	
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/underClosureProjects.js"></script>
</body>
</html>