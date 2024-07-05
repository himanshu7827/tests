
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css"> 

<!-- CSS added by devesh on 22-05-24 to handle page distortion on page zoom -->
<style>

#validatew,
#validatehidden,
#logvalidate,
#milestoneDetails,
#myModal,
#history,
#add-team-modal,
#milestoneModal,
#invoiceDetail,
#amountDetails,
#employeeDetailsModel {
    display: none; /* Hide the modals at this zoom level */
}

.nav-tabs>li>a {
        margin-right: 2px;
        line-height: 1.42857143;
        border: 1px solid transparent;
        border-radius: 4px 4px 0 0;
}

.nav>li>a {
        position: relative;
        display: block;
        padding: 10px 15px;
}

.btn {
        display: inline-block;
        padding: 6px 12px;
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
<!-- End of style -->

</head>


<body>
	<div tabindex='1' id="mainDiv"></div>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active"><spring:message
								code="project.report.projectDetail" /></li>
					</ol>

				</div>
			</div>
<div class="row pad-bottom">

<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
					
					<div class="panel-body">
						<div class="pull-right">
							<a class="btn btn-orange" href="/PMS/dashboard"> <i class="fa fa-home"></i> Home</a>
					</div>
 					<c:if test = "${not empty projectMaster.projectRefrenceNo}">
					<p style="width: 80%;"><span class="bold  font_14 ">Project Reference Number: </span> <span class="bold blue font_14"><i>${projectMaster.projectRefrenceNo}</i></span></p>
					</c:if>
					<p style="width: 80%;"><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${projectMaster.strProjectName}</i></span></p>
					<p style="width: 80%;"><span class="bold  font_16 ">Funding Organization: </span><span class="bold orange font_16"><i>${projectMaster.clientMasterDomain.clientName}</i></span></p>
					<c:if test="${not empty projectMaster.endUserName}">
					<p style="width: 80%;"><span class="bold  font_16 ">End User: </span><span class="bold orange font_16"><i>${projectMaster.endUserName}</i></span></p>
					</c:if>
					<input type="hidden" id="encProjectId" value="${encProjectId}"/>
					
					<input type="hidden" id="startdate" value="${Startdate}"/>	
					<input type="hidden" id="startdateForProgress" value="${Startdate}"/>	
					<input type="hidden" id="monthlyParentId" value=""/>
					<input type="hidden" id="categoryIdback" value="${categoryIdback}"/>				
					</div>
				</div>
			</div>
		</div>
			<div class="row pad-bottom ">
    <div class="col-md-12"> 
      <!-- Nav tabs -->
      <div class="card">
              
        <ul class="nav nav-tabs proj_details center pad-left-double pad-right-double" role="tablist">
        <%-- <c:choose>
        		<c:when test="${categoryIdback != 0 }">
        			 <li role="presentation" class="active "><a class="color1" href="#about" aria-controls="about" role="tab" data-toggle="tab"><i class="fa fa-tasks fa-2x"></i>  <h6><spring:message code="projectDetails.details" /></h6></a></li>
        		</c:when>
        		<c:otherwise>
        			<li role="presentation"><a class="color1" href="#about" aria-controls="about" role="tab" data-toggle="tab"><i class="fa fa-tasks fa-2x"></i>  <h6><spring:message code="projectDetails.details" /></h6></a></li>
        		</c:otherwise>
        </c:choose> --%>
         <li role="presentation" class="active "><a class="color1" href="#about" aria-controls="about" role="tab" data-toggle="tab"><i class="fa fa-tasks fa-2x"></i>  <h6><spring:message code="projectDetails.details" /></h6></a></li>
          <li role="presentation"><a class="color2" href="#budget" onclick="projectBudgetDetails('${encProjectId}')" aria-controls="budget" role="tab" data-toggle="tab"><i class="fa fa-bullseye fa-2x "></i>  <h6><spring:message code="projectDetails.baseline" /></h6></a></li>
          <li role="presentation" style="width: 20%;"><a class="color5" href="#team" onclick="manpowerUtilization('${encProjectId}',0,0)" aria-controls="team" role="tab" data-toggle="tab"><i class="fa fa-street-view fa-2x"></i>  <h6><spring:message code="projectDetails.team_resource" /></h6></a></li>
          <li role="presentation"><a class="color3" href="#milestone" onclick="milestoneDetail('${encProjectId}')" aria-controls="milestone" role="tab" data-toggle="tab"><i class="fa fa-line-chart fa-2x"></i>  <h6><spring:message code="projectDetails.progress" /></h6></a></li>
          <li role="presentation"><a class="color4" href="#invoice" onclick="paymentRealizedDetails('${encProjectId}')" aria-controls="invoice" role="tab" data-toggle="tab"><i class="fa fa-money fa-2x"></i>  <h6><spring:message code="projectDetails.paymentRealized" /></h6></a></li>
        
         <%-- <li role="presentation"><a class="color6" href="#manpower" onclick="manpowerUtilization('${encProjectId}',0,0)" aria-controls="manpower" role="tab" data-toggle="tab"><i class="fa fa-users fa-2x"></i>  <h6><spring:message code="projectDetails.manpower" /></h6></a></li> --%>
        <li role="presentation"><a class="color5" href="#documents" aria-controls="documents" onclick="loadProjectWiseDocuments('${encProjectId}')" role="tab" data-toggle="tab"><i class="fa fa-list-alt fa-2x "></i>  <h6><spring:message code="project.report.documentDetail" /></h6></a></li>
         
         <c:if test="${not empty role}">
             
      		<%-- <c:choose>
        		<c:when test="${categoryIdback != 0 }">        			
        			<li role="presentation" class="active "><a class="color5" href="#monthlyProress" aria-controls="monthlyProress" onclick="" id="monthlytab" role="tab" data-toggle="tab"><i class="fa fa-line-chart fa-2x "></i>  <h6>Monthly Progress</h6></a></li>
        		</c:when>
        		<c:otherwise>
        			<li role="presentation"><a class="color5" href="#monthlyProress" aria-controls="monthlyProress" onclick="" id="monthlytab" role="tab" data-toggle="tab"><i class="fa fa-line-chart fa-2x "></i>  <h6>Monthly Progress</h6></a></li>
        		</c:otherwise>
        </c:choose> --%>
        <li role="presentation"><a class="color5" href="#monthlyProress" aria-controls="monthlyProress" onclick="" id="monthlytab" role="tab" data-toggle="tab"><i class="fa fa-line-chart fa-2x "></i>  <h6>Monthly Progress</h6></a></li>
      	</c:if> 
        </ul>
     
        <!-- Tab panes -->
        <div class="tab-content ">
          <div role="tabpanel" class="tab-pane active" id="about"> <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
         		<table class="table table-bordered bg-white border-grey">

									<tbody>
										<tr>
											<td class="font_15 bold" style="background:#e6f1ff;width: 20%;" >About Project
												</td>
											<td class="font_14">
											<p class="pad-bottom bold blue font_14"><i>${projectMaster.strProjectName}</i></p>
											<p class=" bold pad-bottom">Funding Organization: <span class="bold orange pad-bottom"><i>${projectMaster.clientMasterDomain.clientName}</i></span></p>
											
											<p class=" bold pad-bottom"><u>Description</u></p>
											<p>${projectMaster.description} </p>
											</td>
										</tr>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Project
												Type</td>
											<td class="font_14">${projectMaster.projectType}</td>
										</tr>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Project
												Category</td>
											<td class="font_14">${projectMaster.projectCategory}</td>
										</tr>
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff"><spring:message code="proposal.thrustArea"/></td>
											<td class="font_14">${projectMaster.thrustAreas}</td>
										</tr>
										<c:if test="${not empty projectMaster.mouDate }">
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff" ><spring:message code="projectMaster.MOUdate"/></td>
											<td class="font_14"><%-- <fmt:formatDate pattern="dd-MM-yyyy"
													value="${projectMaster.mouDate}" /> --%>${projectMaster.mouDate}</td>
										</tr>
										</c:if>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff"><spring:message code="projectMaster.WorkorderDate"/></td>
											<td class="font_14"><%-- <fmt:formatDate pattern="dd-MM-yyyy"
													value="${projectMaster.dtWorkOrderDate}" /> --%>${projectMaster.workOrderDate}</td>
										</tr>
										
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff"><spring:message code="proposal.administrationNo"/></td>
											<td class="font_14">${projectMaster.administrationNo}</td>
										</tr>
										
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Duration</td>
											<td class="font_14"><p>${projectMaster.projectDuration} months</p>
											<p><b>Start Date:</b><%--  <fmt:formatDate pattern="dd-MM-yyyy"
													value="${projectMaster.dtProjectStartDate}" /> --%>${projectMaster.startDate}</p>
												
											<p><b>End Date:</b> <%-- <fmt:formatDate pattern="dd-MM-yyyy"
													value="${projectMaster.dtProjectEndDate}" /> --%>${projectMaster.endDate}</p>
													
													<!-- Changes in projectend date  -->
													<input type="hidden" value= "${projectMaster.endDate}" id="projectenddate"/> 
													<!-- Added by devesh on 02-11-23 to get project start date value -->
													<input type="hidden" value= "${projectMaster.startDate}" id="projectstartDate"/> 
											</td>
										</tr>
										<%-- <tr>
											<td class="font_15 bold "  style="background:#e6f1ff" >Total Outlay</td>
											<td class="font_14 currency-inr">${projectMaster.totalOutlay}</td>
											
										</tr> --%>
										<!-- Added by devesh on 05-02-24 to show Total Outlay only when it is not zero -->
										<c:if test="${projectMaster.totalOutlay ne 0}">
										    <tr>
										        <td class="font_15 bold "  style="background:#e6f1ff" >Total Outlay</td>
												<td class="font_14 currency-inr">${projectMaster.totalOutlay}</td>
										    </tr>
										</c:if>
										<!-- End of condition -->
										<%-- <tr>
											<td class="font_15 bold "  style="background:#e6f1ff" ><spring:message code="centre.outlay"/></td>
											<td class="font_14 currency-inr">${projectMaster.projectCost}</td>
											
										</tr> --%>
										<!-- Added by devesh on 05-02-24 to show CDAC Outlay only when it is not zero -->
										<c:if test="${projectMaster.projectCost ne 0}">
										    <tr>
										        <td class="font_15 bold "  style="background:#e6f1ff" ><spring:message code="centre.outlay"/></td>
												<td class="font_14 currency-inr">${projectMaster.projectCost}</td>
										    </tr>
										</c:if>
										<!-- End of condition -->
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Status</td>
											<td class="font_14">${projectMaster.strProjectStatus}</td>
										</tr>
										
										<c:if test="${not empty projectMaster.strFundedScheme}">
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Funded under any specific Scheme</td>
											<td class="font_14">${projectMaster.strFundedScheme}</td>
										</tr>
										</c:if>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Deliverables/Outcome and Objectives</td>
											<td class="font_14">
											<p class="pad-bottom"><b>Deliverables/Outcome:</b> ${projectMaster.strProjectAim}</p>
											<p class="pad-bottom"><b>Objectives:</b> ${projectMaster.strProjectObjective}</p>
											<c:if test="${not empty projectMaster.strProjectRemarks}">
											<p class="pad-bottom"><b>Remarks:</b> ${projectMaster.strProjectRemarks}</p>
											</c:if>
											</td>
										</tr>
										
									<c:if test="${projectMaster.collaborationOrganisationDetails.length()>0 }">
										<tr>										
											<td class="font_15 bold "  style="background:#e6f1ff" ><spring:message code="form.application.collaborative.dtl"/></td>
											<td class="font_14">
												<table class="table table-bordered bg-white border-grey">
													<tr>
														<th>Name</th>
														<th>Address</th>
														<th>Contact No.</th>
														<th>Email</th>
													</tr>
													<c:forEach begin="0" end="${projectMaster.collaborationOrganisationDetails.length() -1}" var="index">
     													<tr>
     														<td>${projectMaster.collaborationOrganisationDetails.getJSONObject(index).getString("organisationName")} </td>
     														<td>${projectMaster.collaborationOrganisationDetails.getJSONObject(index).getString("organisationAddress")} </td>
     														<td> ${projectMaster.collaborationOrganisationDetails.getJSONObject(index).getString("contactNumber")}</td>
     														<td> ${projectMaster.collaborationOrganisationDetails.getJSONObject(index).getString("email")}</td>
     													</tr>
													</c:forEach>
												</table>
											</td>
										</tr>
									</c:if>


									</tbody>
								</table>
         
        </div></div>
 <div role="tabpanel" class="tab-pane" id="budget">
        <fieldset>
          <legend>Manpower</legend>  
          <div class="container table-responsive">       
          	<div class="col-md-12 col-xs-12 col-sm-12 col-xs-12 pad-top">
				<table class="table table-bordered table-strip" id="manpowerBaselineTbl">
					<thead>
						<tr>
							<th width="3%"><spring:message code="forms.serialNo"/></th>
							<th width="25%"><spring:message code="employee_Role.role_Name"/></th>
							<th width="10%"><spring:message code="manpower.req.manmonth.rate"/></th>
							<th width="3%">No.</th>
							<th width="10%"><spring:message code="manpower.involvement"/> <span class="font_12">(in %)</span></th>
							<th width="15%"><spring:message code="employee_Role.start_Date"/></th>
							<th width="15%"><spring:message code="employee_Role.end_Date"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${manpowerRequirement}" var="requirement" varStatus="loop">
							<c:choose>
								<c:when test="${requirement.requiredType==1 }"><tr class="blue"></c:when>
								<c:otherwise><tr></c:otherwise>
							</c:choose>
							
								<td>${loop.index+1}</td>
								<td><c:if test="${requirement.requiredType==1 }"> <i class="magenta">[ Additional Requirement ] </i></c:if> ${requirement.strRolesName}<c:choose><c:when test="${not empty requirement.strRolesName}"><span style="color:grey;"> (${requirement.designationName}) </span></c:when><c:otherwise>${requirement.designationName}</c:otherwise></c:choose>
								<c:if test="${requirement.deputedAt==1}"><span class="blue font_10">(To be deputed at Client Site Site)</span></c:if>
								
								</td>
								<c:choose>
										<c:when test="${requirement.ratePerManMonth eq requirement.actualRatePerManMonth}">
										<td class='text-right'>${requirement.ratePerManMonth}</td>
										</c:when>
										<c:otherwise>
											<td class='text-right red'>${requirement.ratePerManMonth}</td>
										</c:otherwise>
									</c:choose>
									
								<td class='text-right'>${requirement.count}</td>
								<td class='text-right'>${requirement.involvement}</td>
								<td>${requirement.startDate}</td>
								<td>${requirement.endDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>				
		</div>
	</fieldset>
	
		<div class="pad-top"></div>

		<fieldset>
			<legend>Milestone</legend>
				<div class="container table-responsive">
					<div class="col-md-12 col-xs-12 col-sm-12 col-xs-12 pad-top">				
						<table class="table table-bordered table-strip" id="milestoneBaselineTbl">
							<thead>
								<tr>
									<th width="5%"><spring:message code="forms.serialNo"/></th>
									<th width="35%"><spring:message code="Project_Payment_Schedule.milestoneName"/></th>
									<th width="15%"><spring:message code="milestone.baseline.date"/></th>								
									<th width="45%"><spring:message code="milestone.description"/></th>
									
								</tr>
							</thead>
							<tbody>
								<c:set var = "dataIndex"  value = "1"/>
								<c:forEach items="${milestoneDetails}" var="milestone">
									<c:if test="${milestone.valid= true }">
										<tr>
											<td>${dataIndex}</td>
											<%-- <td> <a title="Click here to view milestone activity details" data-toggle="modal" data-target="#milestoneDetails" onclick="milestoneActivityDetails('${milestone.numId}')" class="text-center">${milestone.milestoneName}</a></td> --%>
                                                                                          <!-- remove the hyperlink for milestone details by varun on 19-09-2023 -->
											<td class="text-left">${milestone.milestoneName}</td>
											<td>${milestone.expectedStartDate}</td>	
											<td>${milestone.strDesription}</td>																													
										</tr>
										<c:set var = "dataIndex"  value = "${dataIndex +1}"/>
									</c:if>
									
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>			
		</fieldset>
			
		
		<div class="pad-top"></div>
		
		<fieldset>
			<legend>Payment</legend>
				<div class="container table-responsive">
					<div class="col-md-12 col-xs-12 col-sm-12 col-xs-12 pad-top">			
						<table class="table table-bordered table-strip" id="paymentBaselineTbl">
							<thead>
								<tr>
									<th width="5%"><spring:message code="forms.serialNo"/></th>
									<th width="45%"><spring:message code="Project_Payment_Schedule.payment.term"/></th>
									<th width="25%"><spring:message code="Project_Payment_Schedule.paymentDueDate"/></th>								
									<th width="25%"><spring:message code="Project_Payment_Schedule.amount"/></th>								
								</tr>
							</thead>
							<tbody>
								<c:set var = "dataIndex"  value = "1"/>
								<c:forEach items="${paymentScheduleList}" var="schedule">
									<c:if test="${schedule.valid= true }">
										<tr>
											<td> ${dataIndex}</td>
											<td>											
											<c:choose>
												<c:when test="${schedule.linkedWithMilestone == true}">
													<a title="Click here to view milestone details" data-toggle="modal" data-target="#milestoneModal" onclick="milestoneDetails('${schedule.encMiletoneId}')" class="text-center">${schedule.strPurpose}</a>
												</c:when>
												<c:otherwise>
													${schedule.strPurpose}
												</c:otherwise>
											</c:choose>
											</td>
											<td>${schedule.strPaymentDueDate}</td>	
											<td class='text-right scheduleStrAmt'>${schedule.strAmount}</td>																		
										</tr>
										<c:set var = "dataIndex"  value = "${dataIndex+1}"/>
									</c:if>
								</c:forEach>
								<tr><td colspan="3" class="text-right bold">Total</td> <td class="text-right" id="scheduleAmtTotal"></td></tr>
							</tbody>
						</table>
					</div>
				</div>			
			</fieldset>
		</div>          

      <div role="tabpanel" class="tab-pane" id="milestone">
          <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">
					<div class="panel-heading font_16"> <spring:message code="projectDetails.milestones" /> </div>
					<div class="panel-body">
						<div class="pull-right pad-top pad-bottom">
						<%-- 	<sec:authorize access="hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')">
								<a href="${pageContext.request.contextPath}/mst/MilestoneReviewDetailMaster" class="pad-right btn btn-orange">
							 	<spring:message code="milestone.review.detail" />	</a>
						</sec:authorize> --%>
							<a href="${pageContext.request.contextPath}/mst/ProjectMilestoneMaster?encProjectId=${encProjectId}" class="btn btn-orange">
							 	<spring:message code="add.milestone.label" />	</a>
						</div>
						
							<div class="col-md-12">
							<table id="milestone_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										
										<th><spring:message code="serial.no" /></th>
										<th><spring:message code="Project_Payment_Schedule.milestoneName" /></th>
										<th><spring:message code="Expected_StartDate" /></th>
										<th><spring:message code="form.thisDate" /></th>
										<sec:authorize access="hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')">
											<th class="hidden"></th>
										</sec:authorize>
									</tr>
								</thead>

								<tbody class="">
								</tbody>
								</table>
							</div>
							</div>
							</div>
							</div>
          
          
          </div> 
          <div role="tabpanel" class="tab-pane" id="monthlyProress">
          
	         <c:forEach items="${categoryList}" var="categoryList">
				<input type="hidden" id="isProjectBasedCat_${categoryList.numCategoryId}" value="${categoryList.strProjectGroupFlag}"/>
				<input type="hidden" class="hiddenControls" id="strCatgController_${categoryList.encCategoryId}" value="${categoryList.strCategoryController}"/>
			</c:forEach> 
          <input type="hidden" id="isSubCatPresent">
          <div id="hiddenFields"></div>
          <div class="pad-top pad-bottom"></div>
          	<!--  <p class="font_16 bold">Progress for <i><span class="magenta " id="displayMonth"></span> <span  class="magenta pad-right " id="displayYear"></span></i></p>
		<p class="font_14 bold">
			<span>Choose Month:</span>
			<input id="utilizationDate" class='Default ' type="text" placeholder=""/> 		   
		</p> --><div class="row pad-top">
		<c:if test="${userRole==5 || userRole==8}">
						<sec:authorize access="hasAuthority('WRITE_CORP_DETAILS_SHARE')"> 
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group pad-left-double bold">
						<span  class="label-class pad-left">Would you like to share Monthly Progress Report with Corporate</span>
			
							<c:choose>
							<c:when test="${projectMaster.numCorpMonthlySharing==1}">
							<span class="pad-left-double"> <input type="radio" id="yes"
								name="radioButton" value="1" onclick="populateRadio()" checked> Yes
								
							</span>
						
							<span class="pad-left-double"> <input type="radio"
								name="radioButton" value="0" onclick="populateRadio()"> No
							</span>
							</c:when>
							<c:otherwise>
							<span class="pad-left-double"> <input type="radio"
								name="radioButton" value="1" onclick="populateRadio()" > Yes
								
							</span>
						
							<span class="pad-left-double"> <input type="radio"
								name="radioButton" value="0" onclick="populateRadio()" checked> No
							</span>
							</c:otherwise>	
							</c:choose>
						
							</div>
							</sec:authorize>
							</c:if>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<p  class="label-class">Progress Report For Month:<span style="color: red;">*</span><i><span class="magenta " id="displayMonth"></span> <span  class="magenta pad-right " id="displayYear"></span></i>
															</p>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">													
														
															<p class="font_14 bold">
														
															<input id="strMonthAndYear" class='Default ' type="text" placeholder=""/> 
														
															    </p>
														
													</div>
												</div>
												<br>
											
						
										</div>
										
									<div class="row pad-top">
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden"id="divCat">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
															<label class="label-class"><spring:message
																code="label.progress.category" />:<span
															style="color: red;">*</span></label>
													</div>
													
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<select  id="numCategoryId" class="select2Option">
																<option value="0">----Select Category----<option>
																<c:forEach items="${categoryList}" var="categoryList">
																	<option value="${categoryList.encCategoryId}">
																		${categoryList.strCategoryName}
																	</option>
																</c:forEach>
																
													</select>
													</div>
													</div>
													
													
													</div>
													
													
										
										</div>
											<div class="row pad-top">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="subCatDiv">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="label.progress.subcategory" />:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">													
														<div class="input-container">
															<select id="numSubCategoryId"
																class="select2Option">
																
																<option value="0">----Select Sub Category----</option>
													</select>
													</div>
														
													</div>
												</div>
										</div>
										 <div class="row pad-top hidden" id="hrefDiv">
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">							
															
											</div>
									<!-- 	<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">							
															<a onclick="previewOfReport()">Preview</a>
											</div>  -->
										</div>
										</div>
						
					<!-- <div class="row pad-top pad-bottm double form_btn center">
<button type="button" class="btn btn-primary font_14" onclick="monthlyReport()">Save/Update Details</button>
										<button type="button" class="btn btn-primary font_14"
											id="save" onclick="previewOfReport()">
											<span class="pad-right"><i class=""></i></span>Preview
										</button>
										
									</div>  --> 
									<div class="row pad-top form_btn center" id="nextAction">
		</div>	
          </div>
          
          <div role="tabpanel" class="tab-pane" id="invoice">            
          
      		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">
					<div class="panel-heading font_16"> <spring:message code="projectDetails.paymentReceived" /> </div>
					<div class="panel-body">
							<div class="col-md-12">
							<table id="payment_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										
										<th><spring:message code="Payment_Received.payment_Date" /></th>
										<th><spring:message code="Payment_Received.received_Amount" /></th>
										<th><spring:message code="Payment_Received.payment_Mode" /></th>
										<th><spring:message code="Payment_Received.utr_Number" /></th>
										<th><spring:message code="Payment_Received.project_Invoice_Number" /></th>
									
									</tr>
								</thead>

								<tbody class="">
								</tbody>
								</table>
							</div>
							</div>
							</div>
							</div>
							
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top pad-bottom">
				<div class="panel panel-info  ">
					<div class="panel-heading font_16"> <spring:message code="projectDetails.pendingInvoices" /> </div>
					<div class="panel-body">
							<div class="col-md-12">
							<table id="invoices_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										
										<th><spring:message code="Project_Invoice_Master.invoiceDate" /></th>
										<th><spring:message code="Project_Invoice_Master.invoiceReferenceNumber" /></th>
										<th><spring:message code="Project_Invoice_Master.invoiceAmount" /></th>
										<th><spring:message code="Project_Invoice_Master.invoiceTaxAmount" /></th>
										<th><spring:message code="Project_Invoice_Master.invoiceTotalAmount" /></th>
										<th><spring:message code="Project_Invoice_Master.invoiceStatus" /></th>
									
									</tr>
								</thead>

								<tbody class="">
								</tbody>
								</table>
							</div>
							</div>
							</div>
					<div class="row pad-top  form_btn center">			
						<sec:authorize  access="hasAuthority('READ_PROJECT_INVOICE_MST')">							
								<a class="btn btn-primary font_14" href="/PMS/mst/projectInvoiceMaster/${encProjectId}" target="_blank">Add/Edit Invoice</a>
						</sec:authorize>
						<sec:authorize  access="hasAuthority('READ_PROJECT_PAYMENT_RECEIVED_MST')">
								<a class="btn btn-primary font_14" href="/PMS/mst/projectPaymentReceived/${encProjectId}" target="_blank">Add/Edit Payment</a>
						</sec:authorize>
					</div>
				</div>
							
          <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">
					<div class="panel-heading font_16"> <spring:message code="payment.schedule" /> </div>
					<div class="panel-body">
							<div class="col-md-12">
							<table id="payment_received_with_schedule" class="table table-striped table-bordered"
								style="width: 100%">
								
								<thead>
								<tr>									
									<th width="35%"><spring:message code="Project_Payment_Schedule.payment.term"/></th>
									<th width="15%"><spring:message code="Project_Payment_Schedule.paymentDueDate"/></th>								
									<th width="25%"><spring:message code="Project_Payment_Schedule.amount"/></th>
									<th width="25%"><spring:message code="Payment_Received.received_Amount" /></th>								
								</tr>
							</thead>
							<tbody>
								
								<c:forEach items="${paymentReceivedWithSchedules}" var="schedule">
									
										<tr>
											<c:choose>
												<c:when test="${schedule.strPurpose eq 'Without Schedule Payment'}">
													<c:if test="${not empty schedule.receivedAmount}">
														<td colspan="3" class="bold blue text-center"> ${schedule.strPurpose} </td>
														<td class='text-right'>${schedule.receivedAmount}</td>
													</c:if>													
												</c:when>
												<c:otherwise>
													<td>${schedule.strPurpose}</td>
													<td>${schedule.strPaymentDueDate}</td>	
													<td class='text-right'>${schedule.strAmount}</td>
													<td class='text-right'>${schedule.receivedAmount}</td>
												</c:otherwise>
											</c:choose>										
											
																													
										</tr>
										
								</c:forEach>								
							</tbody>
										
										
								</table>
							</div>
					</div>
				</div>
				
				<div class="hidden" id="validatew"> ${userRole} </div>
			<!-- 	below button validate added by varun on 14-12-2023 -->
				<div class="row pad-top  form_btn center hidden" id="validatehidden">	
				<button type="button" class="btn btn-primary font_14" id="Validate"  onclick= "performAction1()"> 
													Validate
			        </button>

					</div>
			</div>
			
			 <div class=" hidden col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top" id="logvalidate">
				<div class="panel panel-info  ">
					<div class="panel-heading font_16"> Validate History </div>
					<div class="panel-body">
							<div class="col-md-12">
							<table id="Validate_date_history" class="table table-striped table-bordered"
								style="width: 100%">
								
								<thead>
								<tr>									
									<th width="30%">Validate Remarks</th>
									<th width="15%">Validate Status </th>								
									
									<th width="20%">Validate Date</th>								
								</tr>
							</thead>
							<tbody>
								
								<c:forEach items="${validatedloglist}" var="schedule">
									
										<tr>
											<td>${schedule.strValidatedRemarks}</td>
											<td>${schedule.strValidateStatus}</td>	
											
										<td>${schedule.validateDate}</td>								
											 
																													
										</tr>
										
								</c:forEach>								
							</tbody>
										
										
								</table>
							</div>
					</div>
				</div>
				
				<div class="hidden" id="validatew"> ${userRole} </div>
			<!-- 	below button validate added by varun on 14-12-2023 -->
				<div class="row pad-top  form_btn center hidden" id="validatehidden">	
				<button type="button" class="btn btn-primary font_14" id="Validate"  onclick= "performAction1()"> 
													Validate
			        </button>

					</div>
			</div>
			
           </div>
          <div role="tabpanel" class="tab-pane" id="documents">
          	<sec:authorize  access="hasAuthority('WRITE_DOCUMENT_MST')">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"> 
					<div class="pull-right">
					<a class="btn btn-orange" href="/PMS/mst/uploadProjectDocument?encProjectId=${encProjectId}">
						<spring:message code="document.master.add"/>  <i class="fa fa-plus-circle "></i>
					</a>										
					</div>
				</div>
			</sec:authorize>
               <div class="hidden " id="projectWiseDocuments">               
					<div class="col-md-12 col-lg-12 iframeWrapper" id="projectWiseDocumentsDtl">
					</div>
				</div>
           </div>
           
          <div role="tabpanel" class="tab-pane" id="team"> 
          	<div class="col-md-12 col-lg-12 col-sm-12">
          		<sec:authorize access="hasAuthority('PROJECT_TEAM_MAPPING')">
          		 	<div class="pull-right pad-bottom">	          		 	
						<a class="btn btn-orange"  href="/PMS/mst/projectTeamMapping?encProjectId=${encProjectId}">
							<spring:message code="project.team.mapping"/>  <i class="fa fa-plus-circle "></i>
						</a>
          		 	</div>
				</sec:authorize>
				
				
			<div class="container pad-top table-responsive">
				<c:choose>
					<c:when test="${empty assignedManpower}">
						<div class="pad-top red padl5"> Manpower Requirement Not submitted </div>
					</c:when>
					<c:otherwise>
					
						<div class="pad-top pad-bottom"></div>
						
						<table class="table table-striped table-bordered" style="width: 100%">
							<thead>
								<tr>
									<th style="width:50%" colspan="3">Requested Resource</th>
									<th style="width:50%" rowspan=2>Allocated Resource</th>
 								</tr>
								<tr>
									
									<th >Designation</th>
									<th>Number</th>
									<th>Involvement</th>
 								</tr>
							</thead>
							
							<tbody>
							
								<c:forEach var="entry" items="${assignedManpower}" varStatus="loop">
									<%-- <tr>
										<td>${entry.value}</td>
										<td>${entry.key}</td>
										<td></td>
										<td></td>
									</tr> --%>
									
																			    	
							    	<c:choose>
							    		<c:when test="${entry.value.size() > 1}">							    			
										<tr>
											<c:set var = "roleDtl" value = "${fn:split(entry.key, '@#@')}" />	
											<c:set var = "numInv" value = "${fn:split(roleDtl[1], ',')}" />	
							    			<td rowspan="${entry.value.size()}"> 
							    			
							    				<span class="bold black">${roleDtl[0]}</span> <span class="font_12 blue"> ${roleDtl[2]}</span>
							    				<span>@ ${roleDtl[3]}</span> <span>Activity ${roleDtl[4]}</span>
							    				<c:if test="${roleDtl[5]==1}"> <span class="font_12 green bold"> @ Deputed at Client Site</span></c:if>
							    			</td>
									       <td rowspan="${entry.value.size()}">
									       	<c:set var = "tempNum" value = "${fn:split(numInv[1], ':')}" />	
									       	${tempNum[1]}
									        </td>
											<td rowspan="${entry.value.size()}">
									       	<c:set var = "tempNum" value = "${fn:split(numInv[0], ':')}" />	
									       	${tempNum[1]}
									        </td>						    			
							    			<c:forEach items="${entry.value}" var="employee" varStatus="loop1">
							    				<c:choose>
							    					<c:when test="${loop1.index == 0}">
							    						<td>
							    						<c:choose>
							    							<c:when test="${employee.strEmploymentStatus == 'Relieved'}">
							    								<div class="col-md-12 padl5 pad-bottom red">
							    							</c:when>
							    							<c:otherwise>
							    								<div class="col-md-12 padl5 pad-bottom">
							    							</c:otherwise>
							    						</c:choose>							    							
												       			${loop1.index+1}.  ${employee.strEmpName}, ${employee.designation} 
												       			[ <span style='color: #655f5f;' class="font_12 blue"><i> <c:if test="${empty employee.strEndDate}"> 
												       			Since </c:if> ${employee.strStartDate} <c:if test="${not empty employee.strEndDate}"> - ${employee.strEndDate} </c:if>
												       			<c:if test="${not empty employee.duration}">, ${employee.duration}</c:if> </i></span>]
												       			<c:if test="${employee.numDeputedAt==2}"><span class="green bold">[Deputed at Client Site]</span></c:if>
												       		</div>
							    						</td>
							    					
							    					</c:when>
							    					<c:otherwise>
							    						<tr> 
							    							<td> 
							    							
						    								<c:choose>
							    							<c:when test="${employee.strEmploymentStatus == 'Relieved'}">
							    								<div class="col-md-12 padl5 pad-bottom red">
							    							</c:when>
							    							<c:otherwise>
							    								<div class="col-md-12 padl5 pad-bottom">
							    							</c:otherwise>
							    						</c:choose>
													       			${loop1.index+1}.  ${employee.strEmpName}, ${employee.designation} 
													       			[ <span style='color: #655f5f;' class="font_12 blue"><i> <c:if test="${empty employee.strEndDate}"> 
													       			Since </c:if> ${employee.strStartDate} <c:if test="${not empty employee.strEndDate}"> - ${employee.strEndDate} </c:if>
													       			<c:if test="${not empty employee.duration}">, ${employee.duration}</c:if> </i></span>]
													       			<c:if test="${employee.numDeputedAt==2}"><span class="green bold">[Deputed at Client Site]</span></c:if>
													       		</div>
													       		
							    							</td>
							    						</tr>							    						
							    					</c:otherwise>
							    				</c:choose>												       										       										       				
											</c:forEach>							    			
							    		</c:when>
							    		<c:otherwise>
							    		<tr>
							    			<c:set var = "roleDtl" value = "${fn:split(entry.key, '@#@')}" />	
											<c:set var = "numInv" value = "${fn:split(roleDtl[1], ',')}" />	
												
							    			<td>
							    							          	
									          	<span class="bold black">${roleDtl[0]}</span><span class="font_12 blue"> ${roleDtl[2]}</span> <span class="blue1"> @ ${roleDtl[3]}  ${roleDtl[4]} </span> 
									        <c:if test="${roleDtl[5]==1}"> <span class="font_12 green bold"> @ Deputed at Client Site</span></c:if>
									        </td>
									       <td>
									       	<c:set var = "tempNum" value = "${fn:split(numInv[1], ':')}" />	
									       	${tempNum[1]} 
									        </td>
											<td>
									       	<c:set var = "tempNum" value = "${fn:split(numInv[0], ':')}" />	
									       	${tempNum[1]}
									        </td>					    			
							    			<c:choose>
							    				<c:when test="${not empty  entry.value}">
							    					<td>
							    						<c:forEach items="${entry.value}" var="employee" varStatus="loop1">
												       		<!-- <div class="col-md-12 padl5 pad-bottom"> -->
												       		<!-- Added by devesh on 25-10-23 to fix color coding of relieved employees -->
												       		<c:choose>
								    							<c:when test="${employee.strEmploymentStatus == 'Relieved'}">
								    								<div class="col-md-12 padl5 pad-bottom red">
								    							</c:when>
								    							<c:otherwise>
								    								<div class="col-md-12 padl5 pad-bottom">
								    							</c:otherwise>
								    						</c:choose>
								    						<!-- End of condition -->
												       			${loop1.index+1}.  ${employee.strEmpName}, ${employee.designation} 
												       			[ <span style='color: #655f5f;' class="font_12 blue"><i> <c:if test="${empty employee.strEndDate}"> 
												       			Since </c:if> ${employee.strStartDate} <c:if test="${not empty employee.strEndDate}"> - ${employee.strEndDate} </c:if>
												       			<c:if test="${not empty employee.duration}">, ${employee.duration}</c:if> </i></span>]
												       		</div>								       										       				
												       	</c:forEach>
							    					</td>
							    				</c:when>
							    				<c:otherwise>
							    					<td class="bold red padl5">No Resource Mapped</td>
							    				</c:otherwise>
							    			</c:choose>
							    		</tr>
							    		</c:otherwise>					    		
							    	</c:choose>	 
																		
								</c:forEach>
							
							</tbody>
							
					   </table>
					</c:otherwise>
				</c:choose>
			</div>
			
			<%-- <div class="container pad-top">
			 <c:if test="${seenFlag==1}">	
				<c:choose>
				  <c:when test="${empty closedProjectEmployeeList}">
						<div class="pad-top red padl5"> No Resource released on project closure</div>
					</c:when>
					<c:otherwise>
					
						<div class="pad-top pad-bottom"></div>
						
						<table class="table table-striped table-bordered" style="width: 100%">
							<thead>
								<tr>
									<th style="width:100%" colspan="2">Released Resource on Project closure </th>
									
 								</tr>
								<tr>
									
									<th >Sr.No</th>
									<th>Released Resource</th>
									
 								</tr>
							</thead>
							
							<tbody>
							
								<c:forEach var="employee" items="${closedProjectEmployeeList}" varStatus="loop">
									
									
																			    	
							    <tr>
							    <td>${loop.index+1}.</td>
							    <td>
							    <div class="col-md-12 padl5 pad-bottom"> 
							    ${employee.strEmpName},${employee.designation}[<span style='color: #655f5f;' class="font_12 blue"><i>${employee.strEmp}</i></span>]   [<span style='color: #655f5f;' class="font_12 blue"><i>${employee.strStartDate} - ${employee.strEndDate} , ${employee.duration}  , involvment- ${employee.involvement}%</i></span>]</td>
							    </div>
							    </tr>
																		
								</c:forEach>
							
							</tbody>
							
					   </table>
					  </c:otherwise>
					</c:choose>
				</c:if>
			</div> --%>
			<!-- Added by devesh on 26-10-23 to divide resources released on technical and financial closure -->
			<c:set var="isTechnicalResource" value="false" />
			<c:set var="isFinancialResource" value="false" />
			<c:forEach var="employee" items="${closedProjectEmployeeList}" varStatus="loop">
				<c:if test="${employee.idCheck == 'technical'}">
			        <c:set var="isTechnicalResource" value="true" />
			    </c:if>
			    <c:if test="${employee.idCheck == 'financial'}">
			        <c:set var="isFinancialResource" value="true" />
			    </c:if>
			</c:forEach>
			<div class="container pad-top">
			 <c:if test="${seenFlag==1}">	
				<c:choose>
				  <c:when test="${isTechnicalResource}">
				  
						<div class="pad-top pad-bottom"></div>
						
						<table class="table table-striped table-bordered" style="width: 100%">
							<thead>
								<tr>
									<th style="width:100%" colspan="2">Released Resource on Technical Project Closure </th>
									
 								</tr>
								<tr>
									
									<th class="col-md-1">Sr.No</th>
									<th class="col-md-9">Released Resource</th>
									<th class="col-md-2">Released Date</th>
									
 								</tr>
							</thead>
							
							<tbody>
								<c:set var="count" value="1" />
								<c:forEach var="employee" items="${closedProjectEmployeeList}" varStatus="loop">
								<c:if test="${employee.idCheck == 'technical'}">	
									
																			    	
							    <tr>
							    <td>${count}.</td>
							    <td>
							    <div class="col-md-12 padl5 pad-bottom"> 
							    ${employee.strEmpName},${employee.designation}[<span style='color: #655f5f;' class="font_12 blue"><i>${employee.strEmp}</i></span>]   [<span style='color: #655f5f;' class="font_12 blue"><i>${employee.strStartDate} - ${employee.strEndDate} , ${employee.duration}  , involvment- ${employee.involvement}%</i></span>]</td>
							    </div>
							    <td>${employee.strApplicationName}</td>
							    </tr>
							    <c:set var="count" value="${count + 1}" />
								</c:if>										
								</c:forEach>
							
							</tbody>
							
					   </table>
					</c:when>
					<c:otherwise>
						<div class="pad-top red padl5"> No Resource released on Technical Project Closure</div>
					 </c:otherwise>
					</c:choose>
				</c:if>
			</div>
			<div class="container pad-top">
			 <c:if test="${seenFlag==1}">	
				<c:choose>
				  <c:when test="${isFinancialResource}">
				  		<div class="pad-top pad-bottom"></div>
						
						<table class="table table-striped table-bordered" style="width: 100%">
							<thead>
								<tr>
									<th style="width:100%" colspan="2">Released Resource on Financial Project Closure </th>
									
 								</tr>
								<tr>
									
									<th class="col-md-1">Sr.No</th>
									<th class="col-md-9">Released Resource</th>
									<th class="col-md-2">Released Date</th>
									
 								</tr>
							</thead>
							
							<tbody>
								<c:set var="count" value="1" />
								<c:forEach var="employee" items="${closedProjectEmployeeList}" varStatus="loop">
								<c:if test="${employee.idCheck == 'financial'}">	
									
																			    	
							    <tr>
							    <td>${count}.</td>
							    <td>
							    <div class="col-md-12 padl5 pad-bottom"> 
							    ${employee.strEmpName},${employee.designation}[<span style='color: #655f5f;' class="font_12 blue"><i>${employee.strEmp}</i></span>]   [<span style='color: #655f5f;' class="font_12 blue"><i>${employee.strStartDate} - ${employee.strEndDate} , ${employee.duration}  , involvment- ${employee.involvement}%</i></span>]</td>
							    </div>
							    <td>${employee.strOrganisationName}</td>
							    </tr>
							    <c:set var="count" value="${count + 1}" />
								</c:if>										
								</c:forEach>
							
							</tbody>
							
					   </table>
					</c:when>
					<c:otherwise>
						<!-- <div class="pad-top red padl5"> No Resource released on Financial Project Closure</div> -->
					 </c:otherwise>
					</c:choose>
				</c:if>
			</div>
			<!-- End of released resources tables -->	
			
			</div>
			
			<div class="pad-top pad-bottom"></div>
			
			 <p class="font_16 bold">Utilization for <i><span class="magenta " id="displayMonth"></span> <span  class="magenta pad-right " id="displayYear"></span></i></p>
		<p class="font_14 bold">
			<span>Choose Month:</span>
			<input id="utilizationDate" class='Default ' type="text" placeholder=""/> 		   
		</p>
			
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"> 
							
							<table id="manpower_utilization_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th><spring:message code="serial.no" /></th>
										<th><spring:message code="employee_Role.employee_Name" /></th>
										<th><spring:message code="Employee_Salary" /></th>
										<th><spring:message code="manpower.utilization.percentage.label" /></th>
										<th><spring:message code="manpower.salary.cost" /></th>
									
									</tr>
								</thead>

								<tbody class="">
								</tbody>
								</table>
							
							</div>
       
       		</div>
			
		</div>
       <div role="tabpanel" class="tab-pane" id="manpower">
       
        </div>
      </div>
    </div>


			
			</div>
	<div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Document History</h4>
                </div>
                <div id="loadModalBody" >

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
		</div>
		
			<!-- Modal For client Details -->
	<div class="modal amount_receive_deatil_modal" id="milestoneDetails"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel"><spring:message code="milestone.activity"/></h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table id="datatable1" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						   <th><spring:message code="serial.no" /></th>
						   <th><spring:message code="task.activity" /></th>  
							<th><spring:message code="projectMaster.startDate" /></th>
							<th><spring:message code="projectMaster.endDate" /></th>
							<th><spring:message code="module.detail" /></th>
							
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
	
<!-- 	Modal for history -->
			<!-- Modal For client Details -->
	<div class="modal amount_receive_deatil_modal" id="history"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="true" data-backdrop="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel"><spring:message code="Milestone_History"/></h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table id="HistoryTable" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						<th><spring:message code="forms.serialNo"/></th>
						  	<th><spring:message code="milestone.review.date"/></th>
						<th><spring:message code="milestone.review.completion.date"/></th>
						<th><spring:message code="milestone.review.strRemarks"/></th>	
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
	
	<!-- Modal for Add Document -->
			<div class="modal dash-income-modal" id="add-team-modal" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id=""> <spring:message code="document.master.add" /></h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>						
						<div class="modal-body col-md-12 col-lg-12 col-sm-12 col-xs-12" id="addTeamBody">					
							
						</div>
					</div>
				</div>
			</div>
	
	<div class="modal amount_receive_deatil_modal" id="milestoneModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel"><spring:message code="milestone.detail"/></h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table id="milestoneTbl" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						   <th><spring:message code="Project_Payment_Schedule.milestoneName" /></th>
						   <th><spring:message code="Expected_StartDate" /></th>  
							<th><spring:message code="master.description" /></th>											
						</tr>
					</thead>
					<tbody >
					
					</tbody>
			</table>
				
				</div>
				
			</div>
		</div>
		</div>
	
		<!-- Modal For client Details -->
	<div class="modal amount_receive_deatil_modal" id="invoiceDetail"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel"><spring:message code="dashboard.invoice.details"/></h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table id="invoiceDetailsTbl" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						   <th><spring:message code="forms.serialNo" /></th>  
						   <th><spring:message code="Project_Invoice_Master.invoiceReferenceNumber" /></th>  
							<th><spring:message code="Project_Invoice_Master.invoiceDate" /></th>
							<th><spring:message code="Project_Invoice_Master.invoiceAmount" /></th>
							<th><spring:message code="Project_Invoice_Master.invoiceTaxAmount" /></th>
							<th><spring:message code="Project_Invoice_Master.invoiceTotalAmount" /></th>
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
	
	<%------------------------------------- Modal for Amount Break-up [05-12-2023]------------------%>
	<div class="modal amount_receive_deatil_modal" id="amountDetails"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Amount Details</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table id="amountDetailsTable" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
							<th><spring:message code="forms.serialNo" /></th>  
							<th>Received Amount</th>  
							<th>Excess Payment</th>
							<th>IT TDS</th>
							<th>GST TDS</th>
							<th>Short Payment</th>
							<th>LD Payment</th>
							<th>Other Recovery</th>
							<th><spring:message code="Project_Invoice_Master.invoiceTotalAmount" /></th>
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
	<%----------------------------------------------------- End of Amount BreakDown Modal [30-11-2023] ---------------------------%>
	</section>
<script>
// Get the modal
var modal = document.getElementById('history');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>

	
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/projectDetails.js"></script>
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/progressReportWorkflow.js"></script>	
		
		 	
</body>
</html>