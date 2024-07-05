
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/dashboards.css">
	<!-- Bhavesh(26-06-2023) added stylesheet link  --> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha384-******************" crossorigin="anonymous">

<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>

<style>
.month-picker {
	z-index: 10000 !important;
	left: 15%;
	position: relative;
	width: max-content;
}
/* <!-- Bhavesh(21-07-2023) added stylesheet link  --> */
.success-text {
  color: #4CAF50; /* Replace this with the shade of green you prefer */
}
.grey-text {
  color: #808080; /* Replace this with the shade of grey you prefer */
}

.golden-text {
	color: #cc9900;
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

<script type="text/javascript">
	var d = new Date();
</script>

<section id="main-content" class="main-content merge-left">
	<div class=" container wrapper1">
		<div class="row">
			<div class=" col-md-12 pad-top-double  text-left">
				<ol class="breadcrumb bold">
					<li>Home</li>

					<li class="active"><spring:message
							code="dashboard.breadcrumb.label" /></li>
				</ol>
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
			</div>
		</div>





		<div class="pl_dashboard" style="background: #ececec;">
			<input type="hidden" id="encGroupId" value="${encGroupId}" />
			<input type="hidden" id="encProjId" value="" />
			<input type="hidden" id="encPageId" value="${encPageId}" />
			<input type="hidden" id="encWorkflowId" value="${encWorkflowId}" />
			<!--  //Bhavresh(01=05-2024) action for pending at finance -->
             <input type="hidden" value="${encWorkflowId_Closure}" id="encWorkflowId_Closure"/>	
               <input type="hidden" value="${roleId1}" id="roleId"/>
			<!-- 	<div class="icon-tbar" style="top: 55%;">
				<a href="/PMS/resources/app_srv/PMS/global/images/GC_flowchart.png"
					target="_blank" class="question" title="Help" style="padding: 5px;"><i
					class="fa fa-question-circle "></i></a>
			</div> -->
			 <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
			
			 
			<div id="tempAction"></div>
			</div>
			<!--  <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="project_docs">
					
					<a class="btn icon-btn-upload btn-warning" href="${pageContext.request.contextPath}/mst/uploadProjectDocument"><span
						class="fa fa-upload img-circle btn-upload-icon"></span><spring:message code="dashboard.upload.projectDocuments.label" /></a>
				</div>
			</div>-->
			<div class="row padded tiles">
				<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12  pad-bottom pad-top pad-right-zero">


					<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">Proposals/Projects</div>
						<div class="panel-body">
						
					<!-- 	Proposal 1st tile added by varun on 11-10-2023  -->
						
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-newProposalList-modal">
								<div class="card card-stats" style="min-height: 140px;background-color: lightblue;">
									<div class="card-header  card-header-icon">
										<div class="card-icon"
													style="background: #c9e7f9; border: 4px solid #a6d6f5;">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/proposals.png" />
												</div>
										<%---- Label Change of New Proposal [08-02-2024]-----%>
										<p class="card-category" title="Proposals Submitted">
											<span style="font-size:medium">								
												Proposals Submitted : <strong><span id="proposalSubmittedCount">${proposalListCount}</span></strong><br/>
												Project Received : <strong><span id="projectProposalCount">${proposalReceivedCount}</span></strong>
											</span>
										</p> 
										<%-- <p class="card-category" title="Proposals Submitted">
											<span style="font-size:medium">Proposals Submitted / Project Received</span></p>
										<h3 class="card-title card-count" id="newProposalsOnDate">
											<span id="proposalSubmittedCount">${proposalListCount}</span>
											 / 
											 <span id="projectProposalCount">${proposalReceivedCount}</span>
										</h3> --%>
									</div>
									<div class="card-footer">
												<div class="stats">
													<span> <span class="pad-top">since:</span> <span
														class="bold" id="asOnDateProposals"
														style="font-size: 14px; color: #4d4b4b;"> ${startRange} </span>
													</span>

												</div>

											</div>
									
								</div>

							</a>
						</div>
						
						<!-- 	Project recieved 2nd tile added by varun on 11-10-2023  -->
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding:17px;">
								<a class="" href="#" data-image-id="" data-toggle="modal"
										data-title="" data-target="#dash-newProjectList-modal">
										<div class="card card-stats" style="min-height: 140px; background: #ddd5ff;">
											<div class="card-header  card-header-icon">
												<div class="card-icon"
													style="background: #ddd5ff; border: 4px solid #bdaeff;">
													<!-- <i class="material-icons">add_alert</i> -->
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/projects_rec.png" />
												</div>

												<!-- 	changes the label from new to project recieved on 24-05-23 -->
												<p class="card-category" title="New Projects received but Approval Pending">
													Project Approval Pending</p>
												<!-- <p class="card-category" title="New Projects received">New
											Projects</p> -->
											<!-- <p class="card-category" style="font-size:x-small;color:blue">(Project Approval Pending)</p> -->
												<h3 class="card-title card-count" id="newProjectsOnDate">
													${newProjectCount}
													<!--  <small>GB</small> -->
												</h3>
											</div>
											<div class="card-footer">
												<div class="stats">
													<span> <span class="pad-top">since:</span> <span
														class="bold" id="asOnDateProjects"
														style="font-size: 14px; color: #4d4b4b;"> ${startRange} </span>
													</span>

												</div>

											</div>
										</div>
									</a>
			</div>
						
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad-top" style="padding:21px;">
								<%-- <a class=""
									href="${pageContext.request.contextPath}/mst/ViewAllProjects"> --%>
								<a class="" href="#ongoing_projects">

									<div class="info-box bg-deep-purple new-tiles-light-green pad-top-double">

										<div
											class="s-l align-items-center justify-content-between col-md-7 col-lg-7 col-sm-12 col-xs-12 pad-top-25 uppercase ">
											<span class="font-18 bold  " title="Click here to view List of Ongoing Projects">Ongoing Projects <%-- <spring:message code="dashboard.ongoingprojects.label" /> --%>
											</span>

										</div>

										<div class="s-r col-md-5 col-lg-5 col-sm-12 col-xs-12 ">

											<div
												class="col-md-4 col-lg-4 col-sm-12 col-xs-12 pad-top-double">
												<span class=" projectsCount number">${projectCount}</span>
											</div>
											<div class="col-md-8 col-lg-8 col-sm-12 col-xs-12 ">
												<span class=""><i class="material-icons pay_icon">playlist_add_check</i></span>
											</div>

										</div>
									</div>

								</a>


							</div>
							
								
							
							</div>
					</div>
					
					
				</div>
				
				<div class="col-md-8 col-sm-12 col-xs-12 col-lg-8 pad-bottom pad-top">

					<div
						class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-left-zero pad-right-zero">
						<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">PAYMENTS</div>
							<div class="panel-body " style="background: #1578c2;">

								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12  ">
									<a class="pLDash-box" href="#" data-image-id=""
										data-toggle="modal" data-title=""
										data-target="#dash-income-modal">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<div class="image payment-image">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/income.png">

												</div>
												<div class="info " style="padding: 7px 15px 30px 50px;">
													<h3 class="title payment-content-heading">
														<spring:message code="dashboard.income.label" />
													</h3>
													<div class="paymentsCount number" id="incomeOndate">
														<span>${income} Lakhs</span>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 " >
													<!-- Bhavesh(26-06-2023) added class-asOnDateProposals1  -->
														<span class="pull-right "> <span class=""
															style="color: red;">since: </span><label class=" bold asOnDateProposals1"
															id="asOnDate" style="color: black;">${startRange}</label>
														</span><br><br><br><br>
													</div>
													<div></div><br><div></div><br>
												</div>
												
											</div>

                                    
										</div>
									</a>
								</div>


								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 ">
									<a class="pLDash-box" href="#" data-image-id=""
										data-toggle="modal" data-title=""
										data-target="#dash-pending-payments-modal">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<div class="image payment-image">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/pending_payment1.png">

												</div>
												<div class="info " style="padding: 7px 15px 30px 50px;" >
													<h3 class="title payment-content-heading" >Pending
														Invoices</h3>
													<div class=" paymentsCount number" id="incomeOndate">
														<span>${pendingPaymentsListAsOfDate}</span><br> 
														<c:if test = "${countOfUnderApproval >0}">
                                                   <b><span style="color:red;font-size:12px">Under Approval &</span> </b>
                                                   </c:if> <b>  <span  id="ongoing" style="color:red;font-size:12px">Ongoing-${countOfOngoing+countOfUnderApproval}</span></b> <br>
												
												 <div class="">
												  
												  <b><span style="color:red;font-size:12px">Technically Closed-<span id="technicallyClosed">${countOfTechnicallyClosed}</span></span> </b>
												
												  </div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
													<!-- Bhavesh(26-06-2023) added class-asOnDateProposals1  -->
													
													 <c:if test = "${conTotalAmount1 !=null}">
                                                   <b><span class="text-left " style="color:red;font-size:12px">(${conTotalAmount1} Lakhs) </span></b>
                                               </c:if>
													
														<span class="pull-right "> <span class=""
															style="color: red;">since: </span><label class=" bold asOnDateProposals1"
															id="asOnDatePendingPayments" style="color: black;">${startRange}</label>
														</span>
													</div>

												</div>
											</div>


										</div>
									</a>
								</div>


								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 ">
									<a class="pLDash-box" href="#" data-image-id=""
										data-toggle="modal" data-title=""
										data-target="#dash-pending-invoices-modal">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<div class="image payment-image">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/invoice1.png">

												</div>
												<div class="info " style="padding: 7px 15px 30px 50px;">
													<h3 class="title payment-content-heading">Due for Invoicing
													</h3>
													<div class=" paymentsCount number " id="incomeOndate">
														<span class="">${pendingInvoiceList}</span><br>
														<c:if test = "${countOfUnderApprovalDueInvoice >0}">
                                                   <b><span style="color:red;font-size:12px">Under Approval & </span></b>
                                                   </c:if> <b>  <span  id="ongoing" style="color:red;font-size:12px">Ongoing-${countOfOngoingDueInvoice+countOfUnderApprovalDueInvoice}</span></b>
												 
												 <div class="">
												 
												  <b><span style="color:red;font-size:12px">Technically Closed-<span id="technicallyClosed">${countOfTechnicallyClosedDueInvoice}</span></span> </b>
												 
												  </div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
													<!-- Bhavesh(26-06-2023) added class-asOnDateProposals1  -->
													 <c:if test = "${dueConTotalAmount1!=null}">
                                                  <b><span class="text-left " style="color:red;font-size:12px">(${dueConTotalAmount1} Lakhs) </span></b>
                                               </c:if>
													
														<span class="pull-right "> <span class=""
															style="color: red;">since: </span><label class=" bold asOnDateProposals1"
															id="asOnDateDueInvoice" style="color: black;">${startRange}</label>
														</span>
													</div>

												</div>
											</div>


										</div>
									</a>
								</div>

							</div>
						</div>
					</div>
					<div
						class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-left-zero pad-right-zero pad-top">
						<div class="panel panel-info panel-glance">
                              <div class="panel-heading font_eighteen">Teams</div>
							<div class="panel-body ">
								<c:choose>
									<c:when test="${roleId==15}">
										<sec:authorize access="hasAuthority('ACCESS_MY_TEAM')">
											<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 " style="width: 20%;">
												<!-- <a class="pLDash-box" href="#" data-image-id=""
													data-toggle="modal" data-title=""
													data-target="#my-team-modal"> -->
												<a class="pLDash-box" href="#" data-image-id=""
													data-toggle="modal" data-title=""
													data-target="#my-team-selection-modal"> <!-- Modified by devesh on 30-11-23 to open slection modal on click -->
													<div
														class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
														<div class="circularTiles circulatTilesColor1">
															<i class="fa fa-users fa-2x"></i>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<h4 class="pad-top  pLTabs">Team</h4>
													</div>
												</a>
											</div>
										</sec:authorize>
										<sec:authorize
											access="hasAnyAuthority('ROLE_ADMIN','ROLE_PREVIOUS_ADMINISTRATOR')">
											<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 " style="width: 20%;">
												<a class="pLDash-box" href="#" data-image-id=""
													data-toggle="modal" data-title=""
													data-target="#my-teamMapping-modal">
													<div
														class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
														<div class="circularTiles circulatTilesColor4">
															<i class="fa fa-tags fa-2x"></i>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<h4 class="pad-top  pLTabs">Team Mapping</h4>
													</div>
												</a>
											</div>
										</sec:authorize>
										
										<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 " style="width: 20%;">
											<a href="${pageContext.request.contextPath}/manpowerUtilization">
												<div
													class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
													<div class="circularTiles circulatTilesColor3">
														<i class="fa fa-calendar fa-2x"></i>
													</div>
												</div>
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
													<h4 class="pad-top  pLTabs">
														<spring:message code="manpower.utilization.form.label" />
													</h4>
												</div>
											</a>
										</div>
										
										<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 " style="width: 20%;">
											<a href="${pageContext.request.contextPath}/manpowerExpensesForProjects">
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
												    <div class="circularTiles circulatTilesColor6">
												        <i class="fa fa-bar-chart fa-2x"></i>
												    </div>
												</div>
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
													<h4 class="pad-top  pLTabs">
														Manpower Expenses
													</h4>
												</div>
											</a>
										</div>
										
										<sec:authorize access="hasAuthority('VIEW_PROJECT_DOCUMENT')">
											<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 " style="width: 20%;">
												<a
													href="${pageContext.request.contextPath}/mst/uploadProjectDocument">
													<div
														class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
														<div class="circularTiles circulatTilesColor5">
															<i class="fa fa-upload fa-2x"></i>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<h4 class="pad-top  pLTabs">
															<spring:message code="dashboard.upload.projectDocuments.label" />
														</h4>
													</div>
												</a>
											</div>
										</sec:authorize>
									</c:when>
									<c:otherwise>
										<sec:authorize access="hasAuthority('ACCESS_MY_TEAM')">
											<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 ">
												<!-- <a class="pLDash-box" href="#" data-image-id=""
													data-toggle="modal" data-title=""
													data-target="#my-team-modal"> -->
												<a class="pLDash-box" href="#" data-image-id=""
													data-toggle="modal" data-title=""
													data-target="#my-team-selection-modal"> <!-- Modified by devesh on 30-11-23 to open slection modal on click -->
													<div
														class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
														<div class="circularTiles circulatTilesColor1">
															<i class="fa fa-users fa-2x"></i>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<h4 class="pad-top  pLTabs">Team</h4>
													</div>
												</a>
											</div>
										</sec:authorize>
										<sec:authorize
											access="hasAnyAuthority('ROLE_ADMIN','ROLE_PREVIOUS_ADMINISTRATOR')">
											<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 ">
												<a class="pLDash-box" href="#" data-image-id=""
													data-toggle="modal" data-title=""
													data-target="#my-teamMapping-modal">
													<div
														class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
														<div class="circularTiles circulatTilesColor4">
															<i class="fa fa-tags fa-2x"></i>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<h4 class="pad-top  pLTabs">Team Mapping</h4>
													</div>
												</a>
											</div>
										</sec:authorize>
										
										<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 ">
											<a href="${pageContext.request.contextPath}/manpowerUtilization">
												<div
													class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
													<div class="circularTiles circulatTilesColor3">
														<i class="fa fa-calendar fa-2x"></i>
													</div>
												</div>
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
													<h4 class="pad-top  pLTabs">
														<spring:message code="manpower.utilization.form.label" />
													</h4>
												</div>
											</a>
										</div>
										<sec:authorize access="hasAuthority('VIEW_PROJECT_DOCUMENT')">
											<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 ">
												<a
													href="${pageContext.request.contextPath}/mst/uploadProjectDocument">
													<div
														class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center">
														<div class="circularTiles circulatTilesColor5">
															<i class="fa fa-upload fa-2x"></i>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<h4 class="pad-top  pLTabs">
															<spring:message code="dashboard.upload.projectDocuments.label" />
														</h4>
													</div>
												</a>
											</div>
										</sec:authorize>
									</c:otherwise>
								</c:choose>
								
							</div>
						</div>
					</div>
					<!-- Monthly Progress Report -->
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-left-zero pad-right-zero pad-top">
				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						<spring:message code="project.progress.report" />
					</div>
					<div class="panel-body">
					
					<sec:authorize
									access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
									<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 " style="top: 14px;">
										<a
											href="${pageContext.request.contextPath}/monthlyProgressForProjects">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 justify-center" >
												<div class="circularTiles circulatTilesColor2">
													<i class="fa fa-line-chart fa-2x"></i>
												</div>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<h4 class="pad-top  pLTabs">
													<spring:message code="project.progress.report" />
												</h4>
											</div>
										</a>
									</div>
								</sec:authorize>
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" style="left:25px;" >
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-target="#Progress-Reports-modal-rev" data-whatever="4">
								<div class="card card-stats" style="min-height: 117px; background-color: #ffffb3;">
									<div class="card-header card-header-yellow card-header-icon">
										<div class="card-icon">
											<i class="material-icons">report</i>
										</div>
										<p class="card-category" title="Sent for Revision">
											Received back for Revision<br>
										</p>
										<h3 class="card-title card-count " id="sentForRevCount"></h3>
									</div>
									<div class="card-footer">
										<div class="stats"></div>
									</div>
								</div>

							</a>
						</div> 
						
							
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" style="left:70px;">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-target="#Progress-Reports-modal-pending" data-whatever="7">
								<div class="card card-stats" style="min-height: 117px; background-color: #ffb3b3;">
									<div class="card-header card-header-danger  card-header-icon">
										<div class="card-icon">
											<i class="material-icons">report</i>
										</div>
										<p class="card-category" title="Pending Reports">
											Pending for Approval<br>
										</p>
										<h3 class="card-title card-count " id="allPendingReports">
										</h3>
									</div>
									<div class="card-footer">
										<div class="stats"></div>
									</div>
								</div>
							</a>
						</div>
						
					</div>
				</div>
			</div>
				</div>
			</div>
			
			
			<div class="row padded pad-top-zero">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">MILESTONES</div>
						<div class="panel-body ">
							<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
								<%-- <a class=""
									href="${pageContext.request.contextPath}/mst/MilestoneDueInOneMonthDetailMaster"> --%>
												<%------------      Add data target to call the Milestone due in one month modal [21-08-2023]   ------------------%>
							<a class="" href="#"
										title="Click Here to View Milstone Due in next one month"
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-milestone-dueInOneMonth-modal">
									<div class="card card-stats card-stats-new"
										style="background: #fff7e4; border-bottom: 4px solid #e8a718; border-top: 4px solid #e8a718;">
										<div
											class="card-header card-header-orange-gradient card-header-icon">
											<div class="card-icon card-icon-new">
												<i class="material-icons">view_list</i>
											</div>
											<p class="card-category" title="Due in next one month">
												Due in Next One Month</p>
											<h3 class="card-title">
												${countMilestone}
												<!--  <small>GB</small> -->
											</h3>
										</div>

									</div>
								</a>
							</div>

							<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
<%-- 								<a class=""
									href="${pageContext.request.contextPath}/mst/MilestoneExceededDeadlineDetailMaster"
									title="Milestones which have Exceeded Deadline Detail"> --%>
									       <%------------      Add data target to call the Milestone exceeded deadline deatils modal [21-08-2023]   ------------------%>
							<a class="" href="#"
										title="Click Here to View Exceeded Deadline"
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-milestone-exceededDeadline-modal">
									<div class="card card-stats card-stats-new"
										style="background: #ffe5e9; border-bottom: 4px solid #d71065; border-top: 4px solid #d71065;">
										<div
											class="card-header card-header-red-gradient card-header-icon">
											<div class="card-icon card-icon-new">
												<i class="material-icons">report_problem</i>
											</div>
											<p class="card-category"
												title="Milestones which have Exceeded Deadline">
												Exceeded Deadline</p>
											<h3 class="card-title">
												${countExceededMilestone}
												<!--  <small>GB</small> -->
											</h3>
										</div>

									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-left-zero pad-right-zero pad-top">
			      <div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						<h4>Closed Projects</h4>
					</div>
					
					<div class="panel-body" style="padding: 0px !important;">
					<!-- <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"> </div> -->
					<!-- <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"> -->
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" style="top:26px;">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal">
									<div class="info-box bg-teal  pad-top-double" style="background-color:cadetblue;">
									
										<div
											class="s-l align-items-center justify-content-between  col-md-7 col-lg-7 col-sm-12 col-xs-12 pad-top-25 uppercase " style="padding-top:12px">
											<!-- <span class="font-18 bold "
												title="Closing date passed.Closure still pending.">Closure/Extension Pending</span> -->
											<span class="font-18 bold "
												title="Closing date passed.Closure still pending." style="color: white;">Technical Closure/Extension Pending</span><!-- Name changed by devesh on 22-09-23 -->

										</div>

										<div class="s-r col-md-5 col-lg-5 col-sm-12 col-xs-12 ">

											<div
												class="col-md-4 col-lg-4 col-sm-12 col-xs-12 pad-top-double">
												<span class="projectsCount number">${pendingClosureCount}</span>
											</div>
											<div class="col-md-8 col-lg-8 col-sm-12 col-xs-12 ">
												<span class=""><i class="material-icons pay_icon">access_time</i></span>
											</div>

										</div>
									</div>

								</a>
							</div>
							
					
				<!-- financial closure  request tile -->
							
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click here to view Projects Pending for Financial Approval." data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal3">
									<div class="card card-stats" style="min-height: 125px; background-color: bisque;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #efd1b9;border: 4px solid #ca9a72;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/bill_pending.png"/>
											</div>
											<p class="card-category"
												title="Click here to view Projects Pending for Financial Approval.">Financial Closure Pending</p>
											<h3 class="card-title">
												${FinancialPendingProjectDetails.size()}
												<!--  <small>GB</small> -->
											</h3>
										</div>
										<div class="card-footer">
											<div class="stats">
												<!--<p>since : 01/01/2020</p>
											<span class="pull-right"><i class="material-icons">calendar_today</i></span>-->
											</div>
										</div>
									</div>
								</a>
							</div>
							
							<!-- closed project tile added the id class testscroll -->
							
							<div id="testscroll" class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
									<a class="" href="#" data-image-id="" data-toggle="modal"
										data-title="" data-target="#dash-closedProjectList-modal">
										<div class="card card-stats" style="min-height: 130px; background-color: cornsilk;">
											<div class="card-header  card-header-icon">
												<div class="card-icon"
													style="background: #ffe0bc; border: 4px solid #f7c287;">
													<!-- <i class="material-icons">backspace</i> -->
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/closed_projects1.png" />
												</div>
												<p class="card-category"
													title="Click Here to View Technically and Financially Closed Projects.">Closed
													Projects</p>
												<h3 class="card-title" id="closedProjectsOnDate">
													${closedProjectsCount}
													<!--  <small>GB</small> -->
												</h3>
											</div>
											<div class="card-footer">


												<div class="stats">
													<span> <span class="pad-top">since:</span> <span
														class="bold" id="asOnDateClosedProjects"
														style="font-size: 14px; color: #4d4b4b;"> ${startRange} </span>
													</span>

												</div>


											</div>
										</div>
									</a>
								</div>
								
								<!-- 	new tile of validation projects by varun on 05-12-2023 -->
								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
									<a class="" href="#" data-image-id="" data-toggle="modal"
										data-title="" data-target="#dash-validationProject-modal">
										<div class="card card-stats" style="min-height: 130px;">
											<div class="card-header  card-header-icon">
												<div class="card-icon"
													style="background: #ffe0bc; border: 4px solid #f7c287;">
													<!-- <i class="material-icons">backspace</i> -->
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/closed_projects1.png" />
												</div>
												<p class="card-category"
													title="Click Here to View validated and unvalidated projects .">Financial Vetting Validation</p>
												 <h3 class="card-title" id="validatedproject">
												 <span id= "countValidated"> </span> <br>
                                                  <span id= "countValidatedchecked"> </span>
													
													
												</h3> 
											</div>
											<div class="card-footer">
											<div class="stats">
													<span> <span class="pad-top">since:</span> <span
														class="bold" id="asOnValidatedProjects"
														style="font-size: 14px; color: #4d4b4b;"> </span>
													</span>

												</div>
                                            </div>
										</div>
									</a>
								</div>
					
							<%----- New Tile for View Project Document Detais [18-03-2024]  ----%>
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-document-details-modal">
									<div class="card card-stats" style="min-height: 130px;">
										<div class="card-header card-header-icon">
											<div class="card-icon"
												style="border: 4px solid #047fa7;">
												<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/documentPLDashboardIcon.png" style="width: 74px;height: 74px;"/>
											</div>
											<p class="card-category"
												title="Click Here to View Project Document Details .">View Project Document Details</p>
											<!-- <h3 class="card-title" id="validatedproject">Count</h3> -->
										</div>
									</div>
								</a>
							</div>
							<%----- End of New Tile for View Project Document Detais [18-03-2024]  ----%>
								</div>
								</div>
								</div>
			 	
								
								<div class="modal dash-newProposalList-modal dash-modal"
				id="dash-newProposalList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myProp"
					style="width: 99%; height: 95%;">
					<div class="modal-content newprop" id="myContentProp"
						style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<%-- <spring:message code="dashboard.newproposals.list" /> --%>
								List of Proposals Submitted / Projects Recieved
							</h3>
							<button type="button" class="close" onclick="restorepmoprop()"
								data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn"
									onclick="togglepmoprop()">
									<span aria-hidden="true"><i
										class="far fa-window-restore fa-2xs"></i></span>
								</button>

							</div>

						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom ">
									<%-- <spring:message code="dashboard.newproposals.label" /> --%>
									Proposals Submitted & Projects Recieved since : <span class="orange"
										id="asOnDateProposals1"></span>
								</h4>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<!--Bhavesh(17-07-23) changed grid class from col-md-5 col-lg-5 col-sm-5 col-xs-6 to col-md-9 col-lg-9 col-sm-9  -->
								<div
									class="col-md-9 col-lg-9 col-sm-9 col-xs-9 pad-bottom text-right ">
									<label class="font_16">Total Proposals Submitted:</label> <span
										id="totalProCost"> </span>
								</div>
								<div
									class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom text-right "
									id="projRec">
									<label class="font_16"> Project Received : </label> <span
										id="totalConvCost"> </span>
								</div>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

								<div
									class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">

									<label class="font_16" for="from">Last:</label> <select
										id="months"
										onchange="calculateDates(this.value,'fromProposal','toProposal','goProposal',1)"
										class="inline form-control months" style="width: 80%">
										<option value="0">Select Months/Year's</option>
										<option value="3">3 Months</option>
										<option value="6">6 Months</option>
										<option value="12">1 Years</option>
										<option value="24">2 Years</option>
									<%-- Bhavesh (17-10-23) extended the filter to 3 years and 5 years --%>
								       <option value="36">3 Years</option>
								       <option value="60">5 Years</option>
									</select>

									<!-- changes and hide the custom duration  -->
									<div style="text-align: right; olor: #1578c2;">
										<a href="javascript:void()"
											onclick="openProposalAnotherFilter()" id="propSubCust"></a>
									</div>
									<!-- 			<div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" onclick="openProposalAnotherFilter()" id="propSubCust"><i>Custom Duration</i></a></div> -->
								</div>
								<div id="proposalsAnotherFilter">
									
									<div
										class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">

										<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input
											type="text" id="fromProposal" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											id="toProposal" name="to" readonly /> &nbsp; &nbsp;
										<button type="button" id="goProposal" class="btn btn-success ">Go</button>

									</div>
									<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom  p-2" style="display: -webkit-inline-box;">
								 <label class="font_16" for="incomecalendarYearDropdown"> <span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year:</label>
                       <select id="incomecalendarYearDropdown" class="form-control" onchange="getYearIncomeRange()" style="width: auto; margin-left: 20px;height: 28px;">
                                <option value="0">Select Year's</option>
                                 </select>
						    </div>
								</div>
							</div>
							
<%-- 							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<!-- 
								<br> <br> -->
								<table id="newProposals_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>

											
									       <th width="22%"><spring:message code="menu.proposal.label" /></th>
											<th width="19%"><spring:message code="client.name" /></th>
											<th width="8%"><spring:message code="dashboard.newproposals.submittedOn" /></th>
											<th width="8%"><spring:message code="project.details.duration" /></th>
											<th width="2%"><spring:message code="dashboard.newproposals.received.project" /></th>
											<th width="10%"><spring:message code="dashboard.lable.outlay" /></th>
											<!-- new column added of total outlay by varun -->
											<th width="10%">Value of Proposal(Total Outlay) </th>
											<!-- Added heading for proposal history column in new proposal tiles by devesh on 2/8/23 -->
											<th width="6%">View Proposal History</th>
											<!-- End of heading -->


										</tr>
									</thead>
			----------------  Add Filter in Proposal List by Group Name [ 01/08/2023 ]   --------------------------------								
									<thead class="filters ">
										<tr>
											
											<th width="22%"></th> 
											<th width="19%"></th> 
											<th width="8%"></th>
											<th width="8%"></th>
											<th width="2%"></th>
											<th width="10%"></th>
											<th width="10%"></th>
											<th width="6%"></th>
										</tr>
									</thead>
									
									<tbody class="">
									</tbody>
								</table>
							</div> --%>
							
							<%--------------------- Proposal Submitted and Project Received Tile Modal Tables [08-02-2024] ---------------------%>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
			            		<div class="panel panel-info">
				            		<div class="panel-heading font_16" style="background: #1e2787 !important;color: #fff;border-color: #1e2787;">
				                        <h4>Proposals Submitted	</h4>
				                    </div>
			                    	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
										<fieldset class="fieldset-border">
											<table id="newProposals_dataTable"
													class="table table-striped table-bordered" style="width: 100%">
												<thead class="datatable_thead bold ">
													<tr>
														<th width="22%"><spring:message code="menu.proposal.label" /></th>
														<th width="19%"><spring:message code="client.name" /></th>
														<th width="8%"><spring:message code="dashboard.newproposals.submittedOn" /></th>
														<th width="8%"><spring:message code="project.details.duration" /></th>
														<th width="2%"><spring:message code="dashboard.newproposals.received.project" /></th>
														<th width="10%"><spring:message code="dashboard.lable.outlay" /></th>
														<th width="10%">Value of Proposal(Total Outlay) </th>
														<th width="6%">View Proposal History</th>
													</tr>
												</thead>						
												<tbody class="">
												</tbody>
												<tfoot>
													<tr>
														<td colspan="8">
									      					<div class="legend">
														        <div class="legend-item">
														          <div class="legend-color" style="background-color: #0d9b94;"></div>
														          <span>Proposal</span>
														        </div>
														        <div class="legend-item">
														          <div class="legend-color" style="background-color:#1578c2;"></div>
														          <span>Converted Proposal to Project</span>
														        </div>
													        <!-- Add more legend items as needed -->
													      </div>
										   	 			</td>
													</tr>
												</tfoot>
											</table>
										</fieldset>
									</div>
								</div>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
			            		<div class="panel panel-info">
				            		<div class="panel-heading font_16" style="background: #1e2787 !important;color: #fff;border-color: #1e2787;">
				                        <h4>Projects Received </h4>
				                    </div>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									</div>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
										<fieldset class="fieldset-border">
											<table id="newProjectReceived_dataTable"
													class="table table-striped table-bordered" style="width: 100%">
												<thead class="datatable_thead bold ">
													<tr>
														<th width="15%"><spring:message code="menu.proposal.label" /></th>
														<th width="15%">Project Name</th>
														<th width="19%"><spring:message code="client.name" /></th>
														<th width="8%">Work-Order Date</th>
														<th width="8%"><spring:message code="project.details.duration" /></th>
														<th width="10%"><spring:message code="dashboard.lable.outlay" /></th>
														<th width="10%">Value of Proposal(Total Outlay) </th>
													</tr>
												</thead>
												<thead class="filters ">
													<tr>
														<th width="15%"></th> 
														<th width="15%"></th> 
														<th width="19%"></th>
														<th width="8%"></th>
														<th width="8%"></th>
														<th width="10%"></th>
														<th width="10%"></th>
													</tr>
												</thead>
												<tbody class="">
												</tbody>
												<tfoot>
													<tr>
														<td colspan="7">
									      					<div class="legend">
														        <div class="legend-item">
														          <div class="legend-color" style="background-color: #1578c2;"></div>
														          <span>Project Reference Number Generated</span>
														        </div>
														        <div class="legend-item">
														          <div class="legend-color" style="background-color:#800080;"></div>
														          <span>Project Reference Number Not Generated</span>
														        </div>
													        <!-- Add more legend items as needed -->
													      </div>
										   	 			</td>
													</tr>
												</tfoot>
											</table>
										</fieldset>
									</div>
								</div>
							</div>
							<%--------------------- End of Proposal Tile Modal Tables [08-02-2024] ---------------------%>
							
						</div>
					</div>
				</div>
			</div>
			
			<!-- Proposal History Modal added by devesh on 27/7/23 -->
			<div class="modal amount_receive_deatil_modal" id="history"
			tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			aria-hidden="true" data-keyboard="true" data-backdrop="true">
				<div class="modal-dialog modal-lg" id="proposalhistorymodal" role="document" style="bottom:55px;">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title  center" id="exampleModalLabel">Proposal History</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close" onclick="restoreproposalsize()">
								<span aria-hidden="true">&times;</span>
							</button>
							<div class="button-container custom-margin-left" style="margin-left:93%;">
			                    <button type="button" class="close-btn" onclick="toggleproposalhistory()">
			                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
			                    </button>
							</div>
						</div>
						<div class="modal-body">
							<table id="HistoryTable" class="table table-striped table-bordered example_det "
								style="width: 100%">
							<thead class="datatable_thead bold">
								<tr>
									<th><spring:message code="forms.serialNo"/></th>
								  	<th>Revision Number</th>
									<th>Modified By</th>
									<th>Date On:</th>
									<th>Remarks</th>
									<th>Total Proposal Cost</th>
									<th>Project Category</th>
									<th>Project Type</th>
									<th>Title</th>
									<th>Submitted To</th>
									<th>Corporate Approval</th>
									<th>Clearance Recieve Date</th>
									<th>End User Id</th>
									<th>Is Collaborative</th>
									<th>Date of Submission</th>
									<th>Contact Person</th>
									<th>Duration</th>
									<th>Objectives</th>
									<th>CDAC Outlay Cost</th>
									<th>Submitted By</th>
									<th>Description</th>
									<th>Background</th>
									<th>FTS File No</th>	
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
			<!-- End of proposal history modal -->
			
			<div class="modal dash-newProjectList-modal dash-modal"
				id="dash-newProjectList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myProject"
					style="width: 99%; height: 95%;">
					<div class="modal-content projects" id="myContentProject"
						style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								List of Project Approval Pending
								<%-- <spring:message code="dashboard.newprojects.list" /> --%>
							</h3>
							<button type="button" class="close" onclick="restoreProjects()"
								data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn"
									onclick="toggleProjects()">
									<span aria-hidden="true"><i
										class="far fa-window-restore fa-2xs"></i></span>
								</button>

							</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									Project Approval Pending
									<%-- <spring:message code="dashboard.newprojects.label" /> --%>
									since : <span class="orange" id="asOnDateProjects1"></span>
								</h4>
								<%------------      Add Project Received Count with total Project Cost [21-08-2023]   ------------------%>
								<div class="text-right"><label class="font_16"> Project Approval Pending : </label> <span id="totalProjectReceivedCost"> </span></div>
							</div>

							
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

								<div
									class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">

									<label class="font_16" for="from">Last:</label> <select
										id="months"
										onchange="calculateDates(this.value,'from','to','go-btn',0)"
										class="inline form-control listprojects months" style="width: 80%">
										<option value="0">Select Months/Year's</option>
										<option value="3">3 Months</option>
										<option value="6">6 Months</option>
										<option value="12">1 Years</option>
										<option value="24">2 Years</option>
									</select>

									
								</div>
								<div id="projectsAnotherFilter">
								
									<div
										class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">

										<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input
											type="text" id="from" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											id="to" name="to" readonly /> &nbsp; &nbsp;
										<button type="button" class="btn btn-success go-btn ">Go</button>

									</div>
									 <div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;">
							 <label class="font_16" for="projectcalendarYearDropdown"><span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year</label>
						    <select id="projectcalendarYearDropdown" class="form-control" onchange="getYearprojectRange()" style="width: auto;margin-left: 20px;height: 28px;">
						        <option value="0">Select Year's</option>
						        
						    </select>
						    </div>
								</div>
							</div>
						

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="newProjects_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											
											<th width="18%"><spring:message code="master.name" /></th>
											<th width="12%"><spring:message
													code="client.name" /></th>
											<th width="12%"><spring:message
													code="projectMaster.MOUdate" /> <i
												class="fas fa-grip-lines-vertical"></i> <spring:message
													code="projectMaster.WorkorderDate" /></th>
											<th width="10%"><spring:message
													code="dashboard.project.startDate" /></th>
											<th width="10%"><spring:message
													code="projectMaster.endDate" /></th>
											<th width="8%"><spring:message
													code="dashboard.lable.outlay" /></th>
											<th width="10%">Status</th>
										</tr>
									</thead>
									<thead class="filters">
										<tr>
											
											<th width="18%"></th>
											<th width="12%"></th>
											<th width="12%"></th>
											<th width="10%"></th>
											<th width="10%"></th>
											<th width="8%"></th>
											<th width="10%" id="projectStatusCol"></th>

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
			
				
				<!--  Modal for financial Closed Projects -->
			<div class="modal dash-closure-pending-modal3 dash-modal"
				id="dash-closure-pending-modal3" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myPending3"
					style="width: 99%; height: 95%;">
					<div class="modal-content pendings" id="myContentPending"
						style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">Pending For Financial Closure</h3> <%---- Modal Label Change (PRS 189) [11-03-2024] --%>
							<button type="button" id="close1" class="close" onclick="restorepending3()"
								data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn"
									onclick="togglePending3()">
									<span aria-hidden="true"><i
										class="far fa-window-restore fa-2xs"></i></span>
								</button>
							</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"></div>
                       			<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 pad-bottom text-left ">
                       	 			<label class="font_16 brown-text">Total Projects Pending For Financial Closure : ${FinancialPendingProjectDetails.size()}</label> 
                         		</div>
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<fieldset class="fieldset-border">
										
										<table id="exampleUnderClosureFinance" class="table table-striped table-bordered" style="width: 100%">
											<thead class="datatable_thead bold ">
												<tr>										
													<th style="width: 2%;"><spring:message code="serial.no" /></th>
													
													<th style="width: 15%;"><spring:message code="Project_Module_Master.projectName" /></th>
													<th style="width: 15%;"><spring:message code="Client_Contact_Person_Master.name" /></th>
													<th><spring:message code="project.details.leader" /></th>
													<th><spring:message code="employee_Role.start_Date"/></th>
													<th><spring:message code="employee_Role.end_Date"/></th>
													<th style="width:5%;"><p>Actual</p><p><spring:message code="project.details.duration"/></p><p>(In months)</p></th>
													<th style="width:5%;"><p><spring:message code="centre.outlay" /></p><p>(INR)</p></th>
													<!-- <th style="width:5%;"><p>Payment Received</p><p>(INR)</p></th>
													<th style="width:5%;"><p>Payment Pending</p><p>(INR)</p></th> -->
												     <!--  //Bhavresh(01=05-2024) action for pending at finance -->
													<th width="3%"><spring:message code="forms.action"/> </th>	
													
												</tr>
											</thead>
													
											<tbody>
												<c:forEach items="${FinancialPendingProjectDetails}" var="projectdetail" varStatus="loop">						
													<tr class="brown-text">	
													 	<td style="width: 2%;">${loop.count}</td>
													  	
													 	<td class="font_16 brown-text " style="width:15%;" id="td2_${projectdetail.encProjectId}"><p><a class="bold" title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')"><span   class="brown-text">${projectdetail.strProjectName}</span></a></p>
															  </td>
														<td class="font_16 brown-text " style="width:15%;" id="td2_${projectdetail.encProjectId}"> <p class="font_14 ">${projectdetail.clientName}</p> 
															</td>		
														<td style="width: 10%;"><span class="font_16 ">${projectdetail.strPLName}</span></td>																  
														<td class="brown-text" style="width:5%;">${projectdetail.startDate}</td>
														<td class="brown-text" style="width:5%;">${projectdetail.endDate}</td>	
														<td class="brown-text" align="center">${projectdetail.projectDuration}</td>
														<td class="brown-text" align="left" class=""><span >${projectdetail.strTotalCost} </span></td>
														<%-- <td class="brown-text" ><span >${projectdetail.numReceivedAmountInr}</span></td>
														<td class="brown-text" >${projectdetail.numReceivedAmountTemp}</td>	 --%>	
														<td><div class="dropdown">																						
							                             <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onclick="viewAllowedActionsForPending('${projectdetail.encProjectId}')" aria-haspopup="true" aria-expanded="false">
							                               <i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
							                            <ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="new${projectdetail.encProjectId}"></ul></div>
							                           </td>											
													</tr>
												</c:forEach>
											</tbody>
											<!--Bhavesh 15-09-23 added legends Pending for Financial Closure in brown color  -->
											<tfoot>
												<tr>
													<td colspan="9">
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
										<!--End of data table-->
									</fieldset>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- End Modal for financial Closed Projects -->
				
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-closedProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-closedProjectList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.closedprojects.list" />
							</h3>
							<!-- Bhavesh(23-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(23-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									<spring:message code="dashboard.closedprojects.label" />
									since : <span class="orange asOnDateProposals1" id="asOnDateClosedProjects1"></span>
								</h4>

							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">
									<label class="font_16" for="from">Last:</label> <select class="months1 inline form-control" id="months" onchange="calculateDates(this.value,'from1','to1','go-btn1',0)" style="width:80%">
									<option value="0">Select Months/Year's</option>
									<option value="3">3 Months</option>
									<option value="6">6 Months</option>
									<option value="12">1 Years</option>
									<option value="24">2 Years</option>
									<%-- Bhavesh (17-10-23) extended the filter to 3 years and 5 years --%>
								    <option value="36">3 Years</option>
								    <option value="60">5 Years</option>
									</select>
								</div>
								<div>
									
									<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">
										<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input class="fromProposal1" type="text"
											id="from1" name="from" readonly /> <label class="font_16"
											for="to">To</label> <input class="toProposal1" type="text" id="to1"
											name="to" readonly /> &nbsp; &nbsp;
										<button type="button"  class="btn btn-success go-btn1 pad-left">Go</button>
									</div>
									<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;">
								 <label class="font_16" for="closedcalendarYearDropdown"><span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year:</label>
						    <select id="closedcalendarYearDropdown" class="form-control" onchange="getClosedYearDateRange()" style="width: auto;margin-left: 20px;height: 28px;">
						        <option value="0">Select Year's</option>
						        
						    </select>
						    </div>
								</div>
							</div>
								
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<%---------------- ADD some Columns in the closed project table [12-10-2023]---------------%>
								<table id="closedProjects_dataTable"
									class="table table-striped table-bordered">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="2%"><spring:message code="serial.no" /></th>
											<th width="8%"><spring:message code="Project_Module_Master.projectName" /></th>
											<th width="5%"><spring:message code="client.name" /></th>
											<th width="5%"><spring:message code="project.details.leader" /></th>
											<th width="4%"><spring:message code="employee_Role.start_Date" /></th>
											<th width="4%"><spring:message code="employee_Role.end_Date" /></th>
											<th width="4%">
												<p>
													Actual
												</p>
												<p>
													<spring:message code="project.details.duration" />
												</p>
												<p>(In months)</p>
											</th>
											<th width="4%">
												<p>
													Total Value of The Project
												</p>
												<p>(INR)</p>
											</th>
											<th width="4%">
												<p>
													<spring:message code="centre.outlay" />
												</p>
												<p>(INR)</p>
											</th>
											<th width="4%">
												<p>
													Total Payment Received
												</p>
												<p>(INR)</p>
											</th>
											<th width="8%">Remarks</th> <%--- Add new column for remarks [07-03-2024] -----%>
										</tr>
									</thead>

									<tbody class="">
									</tbody>
								</table>
								<%---------------- End of closed project table [12-10-2023]---------------%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- End of modal for Closed Project -->
			
			<!-- start of modal for financial validation Project -->
			<div class="modal dash-validationProject-modal dash-modal"
				id="dash-validationProject-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myvalidate"
					style="width: 99%; height: 95%;">
					<div class="modal-content validates" id="myContentvalidates"
						style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								Validated Projects
							</h3>
							<button type="button" class="close" onclick="restoreValidate()"
								data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn" onclick="toggleValidate()">
									<span aria-hidden="true"><i
										class="far fa-window-restore fa-2xs"></i></span>
								</button>

							</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									
									since : <span class="orange" id="asOnDatevalidationProject"></span>
								</h4>

							</div>
					     	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

								<div
									class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">

									<label class="font_16" for="from">Last:</label> <select
										id="months"
										onchange="calculateDates(this.value,'fromvalidated','tovalidated','go-btnvalidate',0)"
										class="inline form-control valdprojects months" style="width: 80%">
										<option value="0">Select Months/Year's</option>
										<option value="3">3 Months</option>
										<option value="6">6 Months</option>
										<option value="12">1 Years</option>
										<option value="24">2 Years</option>
										<%-- Bhavesh (17-10-23) extended the filter to 3 years and 5 years --%>
								        <option value="36">3 Years</option> 
								        <option value="60">5 Years</option>
										
									</select>
                                   
								</div>
								<div id="closedAnotherFilter">
									
									<div
										class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">

										<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input
											type="text" id="fromvalidated" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											id="tovalidated" name="to" readonly /> &nbsp; &nbsp;
										<button type="button" class="btn btn-success go-btnvalidate pad-left">Go</button>
									</div>
										<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;">
								 <label class="font_16" for="calendarYearDropdown"><span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar year:</label>
						    <select id="calendarYearDropdown" class="form-control" onchange="getYearDateRange()" style="width: auto;margin-left: 20px;height: 28px;">
						        <option value="0">Select Year's</option>
						        
						    </select>
						    </div>
								</div>
								<div style=" width: fit-content;">
					         <label class="font_16" for="validationStatusDropdown"></label>
						    <select id="validationStatusDropdown" class="form-control" style="width: auto;margin-left: 20px;">
						        <option value="">Financially UnChecked</option>
						        <option value="Yes">Financially Checked</option>
						        
						    </select>
						    </div>
							</div>
				          
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<!-- <label class="font_16" for="validationStatusDropdown"></label>
						    <select id="validationStatusDropdown" class="form-control" style="width: auto;">
						        <option value="All" selected>Show All</option>
						        <option value="Yes">Checked</option>
						        <option value="">UnChecked</option>
						    </select> -->
								<table id="validatedProjects_dataTable"
									class="table table-striped table-bordered">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="2%"><spring:message code="serial.no" /></th>
										<%-- 	<th width="5%"><spring:message code="group.groupName" /></th> --%>
											<th width="8%"><spring:message code="Project_Module_Master.projectName" /></th>
											<th width="5%"><spring:message code="client.name" /></th>
											<%-- <th width="5%"><spring:message code="project.details.leader" /></th>--%>
											<th width="4%">Work-Order Date</th>
											
										    <th width="4%">Validation Remarks </th>
											<th width="4%">Validation Status </th>
										</tr>
									</thead>
									<thead class="filters ">
										<tr>
											<th width="2%"></th>
											<!-- <th  width="5%" class="comboBox"></th> -->
											<th width="8%"></th> 
											<th width="5%"></th>
											<!-- <th width="5%"></th> -->
											<th width="4%"></th>
											
											<th width="4%"></th>
											<th width="4%"></th>
										</tr>
									</thead>

									<tbody class="">
									</tbody>
									<tfoot>
													<tr>
														<td colspan="5">
									      					<div class="legend">
														        <div class="legend-item">
														          <div class="legend-color" style="background-color: #1578c2;"></div>
														          <span>Project Reference Number Generated</span>
														        </div>
														        <div class="legend-item">
														          <div class="legend-color" style="background-color:#800080;"></div>
														          <span>Project Reference Number Not Generated</span>
														        </div>
													        <!-- Add more legend items as needed -->
													      </div>
										   	 			</td>
													</tr>
												</tfoot>
								</table>
								
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div
				class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-top pad-bottom"
				id="ongoing_projects">
				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">List of Ongoing
						Projects</div>
					<div class="panel-body">
						<div class="row">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class=" datatable_row pad-bottom">
									<fieldset class="fieldset-border">

										<!-- <legend class="bold legend_details">Details :</legend> -->
										<div class="table-responsive">
											<table id="example"
												class="table table-striped table-bordered"
												style="width: 100%">
												<thead class="datatable_thead bold ">
													<tr>
														<th style="width: 2%;"><spring:message
																code="serial.no" /></th>
														<%-- <th><spring:message code="project.projectRefNo" /></th> --%>
														<th style="width: 35%;"><spring:message
																code="Project_Module_Master.projectName" /></th>
														<%-- <th><spring:message
																		code="application.project.category.label" /></th> --%>
														<%-- <th><spring:message code="employee_Role.start_Date" /></th>
																<th><spring:message code="employee_Role.end_Date" /></th> --%>
														<th style="width: 23%;"><p>
																<spring:message code="project.details.duration" />
															</p> <!-- <p>(months)</p> --></th>
														<th><spring:message code="project.details.leader" /></th>
														<th  style="width: 10%;"><p>
																<spring:message code="dashboard.lable.outlay" />
															</p>
															<p>(in Lakhs)</p></th>
														<th class="hidden" style="width: 10%;"><p>
																<spring:message code="project.details.amountreceived" />
															</p>
															<p>(in Lakhs)</p></th>
														<th><spring:message code="task.download" /></th>


													</tr>
												</thead>
												<thead class="filters">
													<tr>
														<th style="width: 2%;"><i class="fa fa-th-list"></i></th>
														<%-- <th class="textBox"><spring:message
																		code="project.projectRefNo" /></th> --%>
														<th class="textBox" style="width: 35%;"><spring:message
																code="Project_Module_Master.projectName" /></th>

														<%-- <th class="comboBox" id="businessTypeSelect"><spring:message
																		code="application.project.category.label" /></th> --%>
														<%-- <th class="comboBox" style="width: 10%;"><spring:message
																		code="employee_Role.start_Date" /></th>
																<th class="comboBox" style="width: 10%;"><spring:message
																		code="employee_Role.end_Date" /></th> --%>
														<th style="width: 23%;" class="comboBox"><spring:message
																code="project.details.duration" /></th>
														<th class="textBox"><spring:message
																code="project.details.leader" /></th>
														<th style="width: 10%;" class="textBox"><spring:message
																code="dashboard.lable.outlay" /></th>
														<th style="width: 10%;" class="textBox hidden"><spring:message
																code="project.details.amountreceived.lakhs" /></th>
														<th><i class="fa fa-download"></i></th>
													</tr>
												</thead>
												<tbody class="">
												<!-- Bhavesh(27-07-23) fetching the underclosure data for comparison -->
									<c:forEach var="item" items="${underClosureCount1}">
										<p class="underclos hidden">${item.projectRefrenceNo}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosureCount1}">
										<p class="underclos1 hidden">${item.strProjectName}</p>
									</c:forEach>
													<c:forEach items="${groupProjectDetails}"
														var="projectdetail" varStatus="theCount">
														<c:choose>
															<c:when test="${projectdetail.dtEndDate < sysDate}">
															<!--Bhavesh(21-09-23) if item1.projectRefrenceNo==budgetHead.projectRefrenceNo then add golden color otherwise grey  -->
															<c:set var="test_data" value="grey-text"/>
																		<c:forEach var="item" items="${closureData}">
																			<c:if test="${item.projectRefrenceNo== projectdetail.projectRefrenceNo}">
																				<c:set var="test_data" value="golden-text"/>
																			</c:if>
																		</c:forEach>
																<tr id='${projectdetail.encProjectId}'
																	class="${ test_data}">
																	<td></td>
																	<%-- <td>${projectdetail.projectRefrenceNo}</td> --%>
																	<td class="font_16   "><a class="bold   ${ test_data}"
																		title='Click to View Complete Information'
																		onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a>
																		<p class="font_14 ">
																			<i><a class="orange   ${ test_data}"
																				title="Click here to view Funding org details"
																				data-toggle='modal' data-target='#clientDetails'
																				data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}'
																				class='text-center'>${projectdetail.clientName}</a></i>
																		</p>
																		<p class="bold unique1  ${ test_data} font_14 text-left">${projectdetail.projectRefrenceNo}</p>
																		<p class=" font_12   ${ test_data}">${projectdetail.businessType}</p>
																	</td>
																	<%-- 	<td>${projectdetail.businessType}</td> --%>
																	<%-- <td style="width: 10%;">${projectdetail.startDate}</td>
																			 <td>red</td><td style="width:10%;">${projectdetail.endDate}</td>
																			<td style="width: 10%;">${projectdetail.endDate}</td> --%>
																	<td class=" ${ test_data}" align="center">${projectdetail.startDate} to
																		${projectdetail.endDate}<br>
																		(${projectdetail.projectDuration} months)
																	</td>
																	<td style="width: 10%;"><span class="font_16  ${ test_data}">${projectdetail.strPLName}</span>
																		<p>
																			<a title="Team details" data-toggle="modal"
																				data-target="#team-modal"
																				onclick="viewTeamDetails('${projectdetail.encProjectId}')"><span
																				class="font_12 pull-right"><i
																					class="fa fa-users"></i></span></a>
																		</p></td>
																	<td align="right" class=""><input type="hidden"
																		id="hiddenTotalAmt_${projectdetail.encProjectId}"
																		value="${projectdetail.strTotalCost}" /><span
																		class="convertLakhs  ${ test_data}"
																		id="totalAmt_${projectdetail.encProjectId}">${projectdetail.strTotalCost}
																	</span></td>
																	<td class="hidden" align="right"><input type="hidden"
																		id="hiddenAmt_${projectdetail.encProjectId}"
																		value="${projectdetail.strReceivedAmout}" /> <a
																		title="Click here to view received details"
																		data-toggle='modal' data-target='#amtreceive'
																		data-whatever='${projectdetail.encProjectId};${projectdetail.startDate};${projectdetail.strTotalCost}'
																		class='text-center  ${ test_data}'>${projectdetail.strReceivedAmout}
																	</a></td>
																	<%--
 --%>
																	<td style="width: 10%;" style="color:#d59a83">
																		<ul>
																			<c:forEach items="${projectdetail.projectDocument}"
																				var="projectDocument">
																				<%-- <li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadDocument("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li> --%>

																				<c:choose>
																					<c:when
																						test="${projectDocument.uploadedFor == 'Proposal'}">

																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadProposalFile("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																					</c:when>
																					<c:otherwise>

																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadDocument("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																					</c:otherwise>
																				</c:choose>
																			</c:forEach>

																		</ul> <a style="float: right;"
																		onclick="viewProjectDetails('/PMS/mst/documentDetails/${projectdetail.encProjectId}')">View
																			All</a>
																	</td>
																</tr>
															</c:when>
															<c:otherwise>
																<c:set var="test_data1" value="success-text"/>
																<c:forEach var="item" items="${closureData}">
																	<c:if test="${item.projectRefrenceNo== projectdetail.projectRefrenceNo}">
																		<c:set var="test_data1" value="golden-text"/>
																	</c:if>
																</c:forEach>
																<tr id='${projectdetail.encProjectId}' class="${test_data1}">
																	<td></td>
																	<%-- <td>${projectdetail.projectRefrenceNo}</td> --%>
																	<td class="font_16 ${test_data1} "><a class="bold ${test_data1}"
																		title='Click to View Complete Information'
																		onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a>
																		<p class="font_14  ">
																			<i><a class="orange ${test_data1} "
																				title="Click here to view Funding org details"
																				data-toggle='modal' data-target='#clientDetails'
																				data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}'
																				class='text-center '>${projectdetail.clientName}</a></i>
																		</p>
																		<p class="bold ${test_data1} font_14 text-left">${projectdetail.projectRefrenceNo}</p>
																		<p class=" font_12">${projectdetail.businessType}</p>
																	</td>
																	<%-- <td>${projectdetail.businessType}</td> --%>
																	<%-- <td style="width: 10%;">${projectdetail.startDate}</td>
																			 <td>red</td><td style="width:10%;">${projectdetail.endDate}</td>
																			<td style="width: 10%;">${projectdetail.endDate}</td> --%>
																	<td class="${test_data1}" align="center">${projectdetail.startDate} to
																		${projectdetail.endDate}<br>
																		(${projectdetail.projectDuration} months)
																	</td>
																	<td style="width: 10%;"><span class="font_16 ${test_data1}">${projectdetail.strPLName}</span>
																		<p>
																			<a title="Team details" data-toggle="modal"
																				data-target="#team-modal"
																				onclick="viewTeamDetails('${projectdetail.encProjectId}')"><span
																				class="font_12 pull-right ${test_data1}"><i
																					class="fa fa-users"></i></span></a>
																		</p></td>
																	<td align="right" class=""><input type="hidden"
																		id="hiddenTotalAmt_${projectdetail.encProjectId}"
																		value="${projectdetail.strTotalCost}" /><span
																		class="convertLakhs ${test_data1}"
																		id="totalAmt_${projectdetail.encProjectId}">${projectdetail.strTotalCost}
																	</span></td>
																	<td class="hidden" align="right"><input type="hidden"
																		id="hiddenAmt_${projectdetail.encProjectId}"
																		value="${projectdetail.strReceivedAmout}" /> <a
																		title="Click here to view received details"
																		data-toggle='modal' data-target='#amtreceive'
																		data-whatever='${projectdetail.encProjectId};${projectdetail.startDate};${projectdetail.strTotalCost}'
																		class='text-center ${test_data1}'>${projectdetail.strReceivedAmout}
																	</a></td>
																	<%--
 --%>
																	<td style="width: 10%;">
																		<ul>
																			<%-- <c:forEach items="${projectdetail.projectDocument}"
																						var="projectDocument">
																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadDocument("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																					</c:forEach> --%>
																			<c:forEach items="${projectdetail.projectDocument}"
																				var="projectDocument">

																				<c:choose>
																					<c:when
																						test="${projectDocument.uploadedFor == 'Proposal'}">

																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadProposalFile("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																					</c:when>
																					<c:otherwise>

																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadDocument("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																					</c:otherwise>
																				</c:choose>

																			</c:forEach>

																		</ul> <a style="float: right;"
																		onclick="viewProjectDetails('/PMS/mst/documentDetails/${projectdetail.encProjectId}')">View
																			All</a>
																	</td>
																</tr>


															</c:otherwise>
														</c:choose>
													</c:forEach>
												</tbody>
												<!--Bhavesh 28-07-23 added tfoot to count total  -->
										<tfoot>
											<tr >
												<td colspan="7">
      <div class="legend">
        <div class="legend-item">
          <div class="legend-color" style="background-color: #4CAF50;"></div>
          <span>Ongoing</span>
        </div>
        <div class="legend-item">
          <div class="legend-color" style="background-color: #cc9900;"></div>
          <span>Date elapsed and Closure Initiated</span>
        </div>
        <div class="legend-item">
          <div class="legend-color" style="background-color: #808080;"></div>
          <span>Date elapsed but Closure Not Initiated</span>
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
					</div>
				</div>
			</div>

            <!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
				id="Progress-Reports-modal-rev" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                  <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="project.progress.report" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>
						<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Reports for</label> <input type="text"
									id="fromReport" name="from2" readonly /> 
		
								<button type="button" id="" class="btn btn-success " onclick="callGo()">Go</button>
								

							</div> -->
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="projectProgressTbl"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="45%"><spring:message
													code="Project_Payment_Schedule.projectName" /></th>
											<%-- <th width="20%"><spring:message	code="project.projectRefNo" /></th> --%>
											<th width="15%"><spring:message code="label.report.of" /></th>
											<th width="20%"><spring:message code="label.recevied.on" /></th>
											<th width="20%"><spring:message code="forms.action" /></th>
										</tr>
									</thead>

									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

            <!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-payments-modal dash-modal "
				id="Progress-Reports-modal-pending" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
               <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="project.progress.report" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>
						<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Reports for</label> <input type="text"
									id="fromReport" name="from2" readonly /> 
		
								<button type="button" id="" class="btn btn-success " onclick="callGo()">Go</button>
								

							</div> -->
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="projectProgressTblPending"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="45%"><spring:message
													code="Project_Payment_Schedule.projectName" /></th>
											<%-- <th width="20%"><spring:message	code="project.projectRefNo" /></th> --%>
											<th width="15%"><spring:message code="label.report.of" /></th>
											<%-- <th width="20%"><spring:message code="label.sent.on" /></th> --%>
											<th width="20%">Last Action</th>
											<th width="10%">Action</th>  <%---- Add new Column [21-05-2024] ---%>
										</tr>
									</thead>

									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>





			<!-- Modal for Income -->
			<!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-income-modal dash-newProposalList-modal  " id="dash-income-modal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                  <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.projectwise.income" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
								<!-- Bhavesh(26-06-2023) added class-asOnDateProposals1  -->
									<spring:message code="dashboard.income.label" />
									since <span class="orange asOnDateProposals1" id="asOnDate1"></span>
								</h4>

							</div>
							<div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">
                                   <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="" for="from">From</label> <input class="fromProposal1" type="text"
									id="incomefrom" name="from2" readonly /> <label
									class="pad-left" for="to">To</label> <input class="toProposal1" type="text"
									id="incometo" name="to2" readonly />

								<button type="button" class="btn btn-success go-btn2">Go</button>

							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<div id="col-md-12">
									<table id="income_dataTable"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>

												<th width="5%"><spring:message code="forms.serialNo"></spring:message>
												<th width="45%"><spring:message
														code="dashboard.project.label" /></th>
												<%-- 	<th width="15%"><spring:message
														code="project.projectRefNo" /></th> --%>
												<th width="15%"><spring:message
														code="dashboard.payment.received.date" /></th>
												<th width="20%"><spring:message
														code="Project_Payment_Schedule.amount" /></th>

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
			</div>
			<!-- End of modal for income -->

			<!-- Modal for Pending Closure -->
			<!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-closure-pending-modal dash-modal dash-newProposalList-modal"
				id="dash-closure-pending-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">List Of Projects
								Pending for Technical Closure</h3>
								<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"></div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">



							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="pendingCLosure_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="3%"><spring:message code="forms.serialNo" /></th>
											<th width="22%"><spring:message code="master.name" /></th>
											<%-- 				<th width="15%"><spring:message
													code="project.projectRefNo" /></th> --%>
											<th width="15%"><spring:message
													code="client.name" /></th>

											<th width="7%"><spring:message
													code="project.details.leader" /></th>
											<th width="7%"><spring:message
													code="projectMaster.startDate" /></th>
											<th width="8%"><spring:message
													code="projectMaster.endDate" /></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="5%"><spring:message
													code="dashboard.lable.outlay" /></th>
											<th width="13%">Status</th>
											<!-- End of Columns -->

										</tr>
									</thead>
									<tbody class="">
									</tbody>
									 <!--Bhavesh 28-07-23 added tfoot to count total  -->
										<tfoot>
											<tr >
												<!-- <td colspan="7"> -->
												<td colspan="8"><!-- Modified to fix loading of closure pending table on 16/8/23 by devesh -->
      <div class="legend">
        
        <div class="legend-item">
          <div class="legend-color" style="background-color: #cc9900;"></div>
          <span>Date elapsed and Closure Initiated</span>
        </div>
        <div class="legend-item">
          <div class="legend-color" style="background-color: #808080;"></div>
          <span>Date elapsed but Closure Not Initiated</span>
        </div>
        <!-- Add more legend items as needed -->
      </div>
    </td>
											</tr>
										</tfoot>
									
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- End of modal for Pending Closure -->


			<!--Modal For Pending Payments  -->

            <!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
				id="dash-pending-payments-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                  <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.pendingpayments.label" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom pt-4">
									Projects since : <span class="orange asOnDateProposals1" id="asOnDatePendingPayments1"></span>
								</h4>
								
							</div>

						<div class="modal-body dash-modal-body">
						<div
								class="col-md-9 col-lg-9 col-sm-9 col-xs-9 date-bar pad-bottom">
								
			
								 <select id="filterSymbol" onchange="calculateInvoiceDates()" class="inline form-control" style="width:32%">
								<option value="<=">Less than (<) </option>
								<option value=">=">Greater than (>)</option>
								
								</select>
			
			                      <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="" for="from">Last:</label> <select class="months1" id="monthsInvoice" onchange="calculateInvoiceDates()" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
								 
								
								
							</div> 
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;right: 20%;top: 8px;">
							 <label class="font_16" for="outstandingcalendarYearDropdown"><span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year</label>
						    <select id="outstandingcalendarYearDropdown" class="form-control" onchange="getYearprojectRangeForOutstanding()" style="width: auto;margin-left: 20px;height: 28px;">
						        <option value="0">Select Year's</option>
						        
						    </select>
						    </div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="pendingPayments_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>


											<th><spring:message code="forms.serialNo" /></th>

											<th scope="col"><spring:message code="master.name" /></th>
											<%-- 			<th scope="col" width="20%"><spring:message
													code="project.projectRefNo" /></th>
											<th width="18%"><spring:message
													code="dashboard.client.name" /></th> --%>
											<th scope="col"><spring:message
													code="Project_Invoice_Master.invoiceReferenceNumber" /></th>

											<th scope="col"><spring:message
													code="Project_Invoice_Master.invoiceDate" /></th>
											<th scope="col"><spring:message
													code="Project_Invoice_Master.invoiceStatus" /></th>
											<th scope="col"><spring:message
													code="Project_Invoice_Master.invoiceAmount" /></th>
											<th scope="col"><spring:message
													code="Project_Invoice_Master.invoiceTaxAmount" /></th>
											<th scope="col"><spring:message
													code="Project_Invoice_Master.invoiceTotalAmount" /></th>
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

			<!--Modal For Pending Invoices  -->
            <!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-invoices-modal dash-modal dash-newProposalList-modal"
				id="dash-pending-invoices-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
					
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.pendinginvoices.label" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom pt-4">
									Projects since : <span class="orange asOnDateProposals1" id="asOnDateDueInvoice1"></span>
								</h4>
								
							</div>
								
								
								
							
							

						<div class="modal-body dash-modal-body">
						<div
								class="col-md-9 col-lg-9 col-sm-9 col-xs-9 date-bar pad-bottom">
			
								<select id="filterDueSymbol" onchange="calculateDueDates()" class="inline form-control" style="width:32%">
								<option value="<=">Less than (<) </option>
								<option value=">=">Greater than (>)</option>
								
								</select>
			
			                     <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="" for="from">Last:</label> <select class="months1" id="monthsDue" onchange="calculateDueDates()" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select> 
								
								
							</div> 
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;right: 20%;top: 8px;">
							 <label class="font_16" for="dueInvoiceCalendarYearDropdown"><span  style="color: #1578c2;" ><b>OR </b> &nbsp; &nbsp;  </span>Calendar Year</label>
						    <select id="dueInvoiceCalendarYearDropdown" class="form-control" onchange="getYearprojectRangeForDueInvoice()" style="width: auto;margin-left: 20px;height: 28px;">
						        <option value="0">Select Year's</option>
						        
						    </select>
						    </div>
						

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="pendingInvoices_dataTable"
									class="table table-striped table-bordered" width="100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="40%"><spring:message code="master.name" /></th>
											<%-- 							<th width="15%"><spring:message
													code="project.projectRefNo" /></th>
											<th width="18%"><spring:message
													code="dashboard.client.name" /></th> --%>
											<th width="10%"><spring:message code="Schedule_DueDate" />
											</th>
											<th width="15%"><spring:message
													code="Project_Payment_Schedule.purpose" /></th>
											<th width="15%"><spring:message
													code="Project_Payment_Schedule.remarks" /></th>
											<th width="10%"><spring:message
													code="Project_Payment_Schedule.amount" /></th>
										</tr>
									</thead>
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>


			<!--End of Modal for Pending Invoice  -->

			<!--Modal For Team  -->
            <!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-invoices-modal dash-modal dash-newProposalList-modal"
				id="my-team-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                  <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                   <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="projectDetails.team" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="myteam_dataTable"
									class="table table-striped table-bordered" width="100%">
									<thead class="datatable_thead bold ">
										<tr>

											<th width="30%"><spring:message
													code="Patch_Tracker.teamMembers" /></th>
											<th width="15%"><spring:message
													code="employee.designation" /></th>
											<th width="50%"><spring:message
													code="table.label.project" /></th>

										</tr>
									</thead>
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>


			<!--End of Modal for My team -->
			
			<!--Modal For Team Project Wise -->
            <!-- Bhavesh(26-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-invoices-modal dash-modal dash-newProposalList-modal"
				id="my-team-modal-projectwise" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(26-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                  <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                   <!-- Bhavesh(26-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="projectDetails.team" />
							</h3>
							<!-- Bhavesh(26-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(26-06-2023) added resize Button"  -->
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="myteam_projectwise_dataTable"
									class="table table-bordered" width="100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="30%"><spring:message
													code="table.label.project" /></th>
											<th width="15%"><spring:message
													code="employee.designation" /></th>
											<th width="50%"><spring:message
													code="Patch_Tracker.teamMembers" /></th>

										</tr>
									</thead>
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!--End of Modal for My team Project Wise -->
			
			<!--Selection Modal For Team Mapping added by devesh on 30-11-23-->

			<div class="modal dash-pending-invoices-modal dash-modal"
				id="my-team-selection-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-md">
					<div class="modal-content">
						<div class="modal-header" style="border-bottom: none !important;">
							<h3 class="modal-title center" id="">
								<spring:message code="projectDetails.team" />
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="modal-body dash-modal-body"
							style="border: 10px solid #1578c2; background: aliceblue;">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<div
									class="col-lg-6 col-md-6 col-sm-12 col-xs-12 gc-dash-service-box ">
									<div class="serviceBox blue">
										<div class="service-icon">
											<span><i class="fa fa-users"></i></span>
										</div>
										<h3 class="title">
											EMPLOYEE WISE TEAM
										</h3>

										<a
											href="#" data-image-id=""
											data-toggle="modal" data-title="" data-dismiss="modal"
											data-target="#my-team-modal" title="Employee Wise Team"
											class="read-more">Click Here</a>
									</div>
								</div>
								<div
									class="col-lg-6 col-md-6 col-sm-12 col-xs-12 gc-dash-service-box ">
									<div class="serviceBox cyan">
										<div class="service-icon">
											<span><i class="fa fa-file-text-o"></i></span>
										</div>
										<h3 class="title">
											PROJECT WISE TEAM
										</h3>

										<a
											href="#" data-image-id=""
											data-toggle="modal" data-title="" data-dismiss="modal"
											data-target="#my-team-modal-projectwise" title="Project Wise Team"
											class="read-more">Click Here</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!--End of Selection Modal for My Team -->
			
			<!--Modal For Team Mapping -->

			<div class="modal dash-pending-invoices-modal dash-modal"
				id="my-teamMapping-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-md">
					<div class="modal-content">
						<div class="modal-header" style="border-bottom: none !important;">
							<h3 class="modal-title center" id="">
								<spring:message code="team.mapping" />
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="modal-body dash-modal-body"
							style="border: 10px solid #1578c2; background: aliceblue;">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<div
									class="col-lg-6 col-md-6 col-sm-12 col-xs-12 gc-dash-service-box ">
									<div class="serviceBox blue">
										<div class="service-icon">
											<span><i class="fa fa-users"></i></span>
										</div>
										<h3 class="title">
											<spring:message code="dashboard.individual.employee.mapping" />
										</h3>

										<a
											href="${pageContext.request.contextPath}/mst/employeeRoleMaster"
											target="_blank" title="Individual Employee Mapping"
											class="read-more">Click Here</a>
									</div>
								</div>
								<div
									class="col-lg-6 col-md-6 col-sm-12 col-xs-12 gc-dash-service-box ">
									<div class="serviceBox cyan">
										<div class="service-icon">
											<span><i class="fa fa-file-text-o"></i></span>
										</div>
										<h3 class="title">
											<spring:message code="dashboard.project.team.mapping" />
										</h3>

										<a
											href="${pageContext.request.contextPath}/mst/projectTeamMapping"
											target="_blank" title="Project Team Mapping"
											class="read-more">Click Here</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<!--End of Modal for team Mapping -->


			<!-- Modal For Amount Receive -->
			<div class="modal amount_receive_deatil_modal" id="amtreceive"
				tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title  center" id="exampleModalLabel">Payment
								Realized</h4>

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
													code="Project_Payment_Schedule.projectName" /> :</b></label>

									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6"
										id="projectForPayment"></div>
								</div>
							</div>
							<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
									<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">

										<label class="label-class"><b><spring:message
													code="client.name" /> :</b></label>

									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6"
										id="clientIdForPayment"></div>
								</div>
							</div>

							<table id="datatable"
								class="table table-striped table-bordered example_det pad-top"
								style="width: 100%">
								<thead class="datatable_thead bold">
									<tr>
										<th>Date</th>
										<th>Particulars</th>
										<th>Payment Due</th>
										<th>Payment Received</th>
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
						</div>
						<div class="modal-footer" id="modelFooter"></div>
					</div>
				</div>
			</div>
			<!-- Date range picker model for Income-->
			<div class="modal amount_receive_deatil_modal" id="dateRangeModal"
				role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-md" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title  center" id="exampleModalLabel">Select
								Date Range</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<input type="hidden" id="initiatedFor" /> <input
								id="dtStartDate" class="input-field" autocomplete="off"
								readonly="readonly" placeholder="Select Date Range" />
						</div>
						<div class="modal-footer" id="modelFooter"></div>
					</div>
				</div>
			</div>
			<!-- Date range picker model-->

			<!-- Modal for team member details-->
			<div class="modal" id="team-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">


				<!-- Modal content-->
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">Team Details</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 center">

								<div id="chart-container"></div>
							</div>

						</div>

					</div>

				</div>



			</div>
			
			
			<!-------------------------------------- Modal For Milestone Due In One Months [21-08-2023] ----------------------------------->
<div class="modal dash-closure-pending-modal2 dash-modal dash-newProposalList-modal"
	id="dash-milestone-dueInOneMonth-modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false"
	data-backdrop="static">
	<div class="modal-dialog modal-lgmy myModal1" id="myPending2"
		style="width: 99%; height: 95%;">
		<div class="modal-content pendings" id="myContentPending"
			style="height: 100%;">
			<div class="modal-header">
				<h3 class="modal-title center" id="">Milestones Due In Next One Month</h3>
				<button type="button" class="close" onclick="restoreprop()"
					data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>

				</button>

				<div class="button-container custom-margin-left">
					<button type="button" class="close-btn" onclick="toggleMaximize()">
						<span aria-hidden="true"><i
							class="far fa-window-restore fa-2xs"></i></span>
					</button>

				</div>
			</div>

			<div class="modal-body dash-modal-body">
				<fieldset class="fieldset-border">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class=" datatable_row pad-bottom" style="padding-top: 25px;">
							<table id="milestoneDueInOneMonthTable"
								class="table table-striped table-bordered mileDataTable"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th width="5%"><spring:message code="serial.no" /></th>
										<th width="10%" style="width: 50px;"><spring:message
												code="group.groupName" /></th>
										<th width="35%"><spring:message
												code="Project_Module_Master.projectName" /></th>
										<th width="18%"><spring:message
												code="Project_Payment_Schedule.milestoneName" /></th>
										<th width="10%"><spring:message
												code="Expected_CompletionDate" /></th>
										<th width="8%"><spring:message code="forms.action" /></th>
									</tr>
								</thead>
								<tbody class="">
					<c:forEach items="${data1}" var="milestoneData" varStatus="theCount">
										<%----------------- Set the Row data according to Project Status   [29-08-2023] -----------------------------------------%>
										    <c:set var="row_color" value="success-text" />
										
										    <c:forEach var="closureProjectData" items="${closureData}">
										        <c:choose>
										            <c:when test="${closureProjectData.projectRefrenceNo == milestoneData.strProjectReference}">
										                <c:set var="row_color" value="golden-text" />
										            </c:when>
										        </c:choose>
										    </c:forEach>
										
										    <c:choose>
										        <c:when test="${row_color != 'golden-text'}">
										            <c:forEach var="pendingClosureProject" items="${pendingClosureProjectList}">
										                <c:choose>
										                    <c:when test="${pendingClosureProject.projectRefrenceNo == milestoneData.strProjectReference}">
										                        <c:set var="row_color" value="grey-text" />
										                    </c:when>
										                </c:choose>
										            </c:forEach>
										        </c:when>
										    </c:choose>
										<%------------- EOL Set the Row data according to Project Status  [29-08-2023] -----------------------------------------%>	
						<tr class="${row_color }">
							<td>${theCount.count}</td>	
							<td>${milestoneData.groupName}</td>							
							<td><a class="font_14 ${row_color}" title="Click to View Complete Information" onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a><p class="font_14 ${row_color}">${milestoneData.clientName}
																				</p><p class="bold font_14 text-left ${row_color}">${milestoneData.strProjectReference}</p></td>						
							<%-- <td class="bold blue font_14 text-right">${milestoneData.strProjectReference}</td>
							<td class="bold orange font_12">${milestoneData.clientName}</td> --%>
							<td>${milestoneData.milestoneName}</td>
 							<c:choose>
								<c:when test="${not empty milestoneData.completionDate}">
									<td class="text-right">${milestoneData.completionDate}</td>
								</c:when>
								<c:when test="${not empty milestoneData.expectedStartDate}">
									<td class="text-right">${milestoneData.expectedStartDate}</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
 								
 							<td class="text-center"><a class='btn btn-primary' onclick="viewProjectDetails('/PMS/mst/MilestoneReviewMaster/${milestoneData.encMilestsoneId}')">Review</a></td>
							
						</tr>
					</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="7">
											<div class="legend">
												<div class="legend-item">
													<div class="legend-color"
														style="background-color: #4CAF50;"></div>
													<span>Ongoing</span>
												</div>
												<div class="legend-item">
													<div class="legend-color"
														style="background-color: #cc9900;"></div>
													<span>Date elapsed and Closure Initiated</span>
												</div>
												<div class="legend-item">
													<div class="legend-color"
														style="background-color: #808080;"></div>
													<span>Date elapsed but Closure Not Initiated</span>
												</div>
												<!-- Add more legend items as needed -->
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</div>
<!-------------------------------------- End Modal For Milestone Due In One Month ----------------------------------->

<!-------------------------------------- Modal For Milestone Exceeded Details [21-08-2023] ----------------------------------->
<div class="modal dash-closure-pending-modal2 dash-modal dash-newProposalList-modal"
	id="dash-milestone-exceededDeadline-modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false"
	data-backdrop="static">
	<div class="modal-dialog modal-lg myModal1" id="myPending2"
		style="width: 99%; height: 95%;">
		<div class="modal-content pendings" id="myContentPending"
			style="height: 100%;">
			<div class="modal-header">
				<h3 class="modal-title center" id="">Milestones Exceeded
					Deadline</h3>
				<button type="button" class="close" onclick="restoreprop()"
					data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>

				</button>

				<div class="button-container custom-margin-left">
					<button type="button" class="close-btn" onclick="toggleMaximize()">
						<span aria-hidden="true"><i
							class="far fa-window-restore fa-2xs"></i></span>
					</button>

				</div>
			</div>

			<div class="modal-body dash-modal-body">
				<fieldset class="fieldset-border">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div
							class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
							<select id="filterSymbol_milestoneExceeded" onchange="calculateMilestoneExceededDates()"
								class="inline form-control" style="width: 43%">
								<option value=">=">Less than (<)</option>
								<option value="<=">Greater than (>)</option>

							</select> <label class="" for="from">Last:</label> <select id="months_milestoneExceeded"
								onchange="calculateMilestoneExceededDates()" class="months1 inline form-control"
								style="width: 43%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
							</select>
						</div>
						<div id="milesAnotherFilter">
							<div class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom "
								style="padding-top: 4px;">
								<h3 style="color: #1578c2;">
									<strong>OR</strong>
								</h3>
							</div>
									<div
										class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
										<label class="font_16" for="from">From</label> <input
											class="fromProposal1" type="text"
											id="fromDate_MilestoneExceeded" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											class="toProposal1" id="toDate_MilestoneExceeded" name="to"
											readonly /> &nbsp; &nbsp;
										<button type="button" class="btn btn-success"
											id="milestoneExceeded_GoBtn">Go</button>
									</div>
						</div>
					</div>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class=" datatable_row pad-bottom" style="padding-top: 25px;">
							<table id="milestoneExceededDeadlineTable"
								class="table table-striped table-bordered mileDataTable"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th width="5%"><spring:message code="serial.no" /></th>
										<th width="10%" style="width: 50px;"><spring:message
												code="group.groupName" /></th>
										<th width="35%"><spring:message
												code="Project_Module_Master.projectName" /></th>
										<th width="18%"><spring:message
												code="Project_Payment_Schedule.milestoneName" /></th>
										<th width="10%"><spring:message
												code="Expected_CompletionDate" /></th>
										<th width="8%"><spring:message code="forms.action" /></th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data2}" var="milestoneData"
										varStatus="theCount">
										<%----------------- Set the Row data according to Project Status   [29-08-2023] -----------------------------------------%>
										    <c:set var="row_color" value="success-text" />
										
										    <c:forEach var="closureProjectData" items="${closureData}">
										        <c:choose>
										            <c:when test="${closureProjectData.projectRefrenceNo == milestoneData.strProjectReference}">
										                <c:set var="row_color" value="golden-text" />
										            </c:when>
										        </c:choose>
										    </c:forEach>
										
										    <c:choose>
										        <c:when test="${row_color != 'golden-text'}">
										            <c:forEach var="pendingClosureProject" items="${pendingClosureProjectList}">
										                <c:choose>
										                    <c:when test="${pendingClosureProject.projectRefrenceNo == milestoneData.strProjectReference}">
										                        <c:set var="row_color" value="grey-text" />
										                    </c:when>
										                </c:choose>
										            </c:forEach>
										        </c:when>
										    </c:choose>
										<%------------- EOL Set the Row data according to Project Status  [29-08-2023] -----------------------------------------%>										
										<tr class="${row_color}">
											<td>${theCount.count}</td>
											<td>${milestoneData.groupName}</td>
											<td><a class="font_14 ${row_color}"
												title="Click to View Complete Information"
												onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a>
											<p class="font_14 ${row_color}">${milestoneData.clientName}</p>
												<p class="bold ${row_color} font_14 text-left">${milestoneData.strProjectReference}</p></td>
											<td>${milestoneData.milestoneName}</td>
											<c:choose>
												<c:when test="${not empty milestoneData.completionDate}">
													<td class="text-right">${milestoneData.completionDate}</td>
												</c:when>
												<c:when test="${not empty milestoneData.expectedStartDate}">
													<td class="text-right">${milestoneData.expectedStartDate}</td>
												</c:when>
												<c:otherwise>
													<td></td>
												</c:otherwise>
											</c:choose>
											<td class="text-center"><a class='btn btn-primary'
												onclick="viewProjectDetails('/PMS/mst/MilestoneReviewMaster/${milestoneData.encMilestsoneId}')">Review</a></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="7">
											<div class="legend">
												<div class="legend-item">
													<div class="legend-color"
														style="background-color: #4CAF50;"></div>
													<span>Ongoing</span>
												</div>
												<div class="legend-item">
													<div class="legend-color"
														style="background-color: #cc9900;"></div>
													<span>Date elapsed and Closure Initiated</span>
												</div>
												<div class="legend-item">
													<div class="legend-color"
														style="background-color: #808080;"></div>
													<span>Date elapsed but Closure Not Initiated</span>
												</div>
												<!-- Add more legend items as needed -->
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
</div>
<!-------------------------------------- End Modal For Milestone Exceeded Details ----------------------------------->

<%-------------- Get data of closure projects and pending for closure [29-08-2023]   ------------------%>
			<c:forEach var="item" items="${closureData}">
				<p class="closureList hidden">${item.projectRefrenceNo}</p>
			</c:forEach>
				<c:forEach var="item" items="${underapprovaldata}">
		<p class="validateList hidden">${item.projectRefrenceNo}</p>
	</c:forEach>
			<c:forEach var="item2" items="${pendingClosureProjectList}">
				<p class="pendingClosureList hidden">${item2.projectRefrenceNo}</p>
			</c:forEach>
<%------------- EOL Get data of closure projects and pending for closure  ---------------------%>
		</div>

	</div>
		<%------------- Project Document Details Tile Modal [18-03-2024] ---------%>
		<div class="modal dash-validationProject-modal dash-modal"
			id="dash-document-details-modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true"
			data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg" id="viewDocumentsModel"
				style="width: 99%; height: 95%;">
				<div class="modal-content validates" id="viewDocumentsModelContent"
					style="height: 100%;">
					<div class="modal-header">
						<h3 class="modal-title center" id="">
							View Project Documents Details
						</h3>
						<button type="button" class="close" onclick="restoreViewDocumentDetail()"
								data-dismiss="modal">
							<span aria-hidden="true"> </span><span class="sr-only">Close</span>
						</button>
						<div class="button-container custom-margin-left">
							<button type="button" class="close-btn"
								onclick="toggleViewDocumentDetail()">
								<span aria-hidden="true"><i
									class="far fa-window-restore fa-2xs"></i></span>
							</button>
						</div>
					</div>
					<div class="modal-body dash-modal-body">
						<input type="hidden" id="projectDetailClose"/>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
								<label class="font_16" for="projectName">Project Name :</label> 
								<select id="projectNameForDocumentTile"
									class="form-select select2Option" multiple="false" name="projectName" onChange="getProjectDocumentData()">
									<option value="0">Select</option>
									<c:forEach var="projectNameForDocument" items="${AllProjectDataForDocument}">
										<c:choose>
									        <c:when test="${empty projectNameForDocument.projectRefNo}">
									            <option value="${projectNameForDocument.numId}">${projectNameForDocument.strProjectName}</option>
									        </c:when>
									        <c:otherwise>
									            <option value="${projectNameForDocument.numId}">
									                ${projectNameForDocument.strProjectName} [ ${projectNameForDocument.projectRefNo} ]
									            </option>
									        </c:otherwise>
									    </c:choose>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
							    <label class="font_16" for="projectDocumentTypeName">Document Type Name :</label> 
							    <select id="projectDocumentTypeForDocumentTile" class="form-select select2Option" name="projectDocumentTypeName" onChange="getProjectDocumentData()" multiple>
							        <c:forEach var="documents" items="${documentTypeList}">
							            <c:forEach items="${documents.value}" var="document">
							                <c:if test="${document.numId == 1 || document.numId == 2 || document.numId == 3 || document.numId == 48}">
							                    <option value="${document.numId}">${document.docTypeName}</option>
							                </c:if>
							            </c:forEach>
							        </c:forEach>
							        <optgroup id="moreOptions" label="More Document Type" style="display: none;">
							            <c:forEach var="documents" items="${documentTypeList}">
							                <c:forEach items="${documents.value}" var="document">
							                    <c:if test="${document.numId != 1 && document.numId != 2 && document.numId != 3 && document.numId != 48}">
							                        <option value="${document.numId}">${document.docTypeName}</option>
							                    </c:if>
							                </c:forEach>
							            </c:forEach>
							        </optgroup>
							    </select>
							</div>
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">						  
                               	<label class="font_16" for="incomecalendarYearDropdown">Calendar Year:</label>
                    				<select id="calenderYearForDocumentTile" class="form-control" onChange="getProjectDocumentData()">
                    				<option value="0">Select</option>
									<c:forEach var="calenderYear" items="${calenderYearForDocument}">
							            <option value="${calenderYear}">${calenderYear}</option>
							        </c:forEach>
                              	</select>
                            </div>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
		            		<div class="panel panel-info">
			            		<div class="panel-heading font_16" style="background: #1e2787 !important;color: #fff;border-color: #1e2787;">
			                        <h4>View Project Document Details </h4>
			                    </div>
		                    	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<fieldset class="fieldset-border">
										<table id="viewProjectDocumentTable" class="table table-striped table-bordered"
											style="width: 100%">
											<thead class="datatable_thead bold ">	
												<tr>										
							 						<th width="2%"><spring:message code="serial.no" /></th>
													<th width="15%"><spring:message code="Project_Module_Master.projectName" /></th>
													<th width="8%">Document Type Name</th>
													<th width="15%">Document Description</th>
													<th width="5%">Document Version</th>
													<th width="5%">Document Date</th>
													<th width="5%">Download</th>
												</tr>
											</thead>
											<tbody id="">
											</tbody>
											<tfoot>
												<tr>
												<td colspan="7">
											    	<div class="legend">
											        	<div class="legend-item">
										          			<div class="legend-color" style="background-color:#0d9b94;"></div>
										          			<span>Document Uploaded at Proposal Stage</span>
										        		</div>
													    <div class="legend-item">
													    	<div class="legend-color" style="background-color:#1578c2;"></div>
													        <span>Document Uploaded at Project Stage</span>
													    </div>
										      		</div>
											    </td>
												</tr>
											</tfoot>
										</table>
									</fieldset>
								</div>
		                    </div>
	                    </div>	          
					</div>
				</div>
			</div>
		</div>
		<%------------- End of Project Document Details Tile Modal [18-03-2024] ---------%>
				<!--  //Bhavresh(01=05-2024) action for pending at finance -->
		<%---------------   Add Modal For View Proceeding  [21-09-2023] ------%>	
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
				<div class="modal-body">
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
	<%---------------   END of View Proceeding  Tile------%>
		
	<%------------------- When PL Login , Trigger and show the Modal  [28-02-2024] ----------%>
	<c:if test="${loginModalOpen eq true}">
	    <script>
	        var currentYear = new Date().getFullYear();
	        swal({
	            content: {
	                element: "div",
	                attributes: {
	                    innerHTML: "<style>.swal-modal { width: 600px; }</style>" + 
	                               "<div class='alert alert-primary' role='alert'><div style='font-weight:bold;text-align: justify;'>Please ensure 'Projects' entries are completed in all respects e.g. <br/> a) Project Milestones. <br/> b) Project Payment Schedules. <br/> c) Manpower Requirements.<br/>d) Uploading of Project Documents.</div></div>",
	                }
	            },
	        });
	    </script>
	</c:if>
	<%------------------- End of When PL Login , Trigger and show the Modal  [28-02-2024] ----------%>


	
	
	
</section>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

<script
	src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/pLDashboard.js"></script>
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/progressReportWorkflow.js"></script>
<!-- Expenditure -->

<script type="text/javascript">
	function initialzeExpenditureChart() {

		expenditureTable();

	}
</script>
 <script>
  
   var currentYear = new Date().getFullYear();


  var yearDropdown = $("#calendarYearDropdown");

  for (var i = 0; i < 5; i++) {
    var year = currentYear - i;
    yearDropdown.append('<option value="' + year + '">' + year + '</option>');
  } 
  function getYearDateRange() {
	  $(".months").val(0) ;
	    var selectedYear = parseInt(yearDropdown.val());
      if (selectedYear <= 0) {
	    	
        	selectedYear= currentYear;
	     
	    }
	   
	      var firstDate = new Date(selectedYear, 0, 1); // January 1st
	      var lastDate = new Date(selectedYear, 11, 31); // December 31st

	     
	      var formattedFirstDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
	      var formattedLastDate = $.datepicker.formatDate('dd/mm/yy', lastDate);

	    
	      calculateyearDates(formattedFirstDate,formattedLastDate,'fromvalidated','tovalidated','go-btnvalidate',0);
	  
	  }

	  
 // for income 
	  var incomecurrentYear = new Date().getFullYear();
      var incomeyearDropdown = $("#incomecalendarYearDropdown");
      for (var i = 0; i < 5; i++) {
	    var year = incomecurrentYear - i;
	    incomeyearDropdown.append('<option value="' + year + '">' + year + '</option>');
	  } 
	  function getYearIncomeRange() {
		 
		  $(".months").val(0) ;
		    var selectedYear = parseInt(incomeyearDropdown.val());

		    if (selectedYear > 0) {
		      var firstDate = new Date(selectedYear, 0, 1); // January 1st
		      var lastDate = new Date(selectedYear, 11, 31); // December 31st

		     
		      var formattedFirstDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
		      var formattedLastDate = $.datepicker.formatDate('dd/mm/yy', lastDate);

		    
		      calculateyearDates(formattedFirstDate,formattedLastDate,'fromProposal','toProposal','goProposal',1);
		  } else {
		      
		      console.log('Please select a valid year.');
		    }
		  }

		
	
		  
	  // for project approval pending
	  var projectcurrentYear = new Date().getFullYear();
      var projectyearDropdown = $("#projectcalendarYearDropdown");
      for (var i = 0; i < 5; i++) {
	    var year = projectcurrentYear - i;
	    projectyearDropdown.append('<option value="' + year + '">' + year + '</option>');
	  } 
	  function getYearprojectRange() {
		 
		  $(".months").val(0) ;
		    var selectedYear = parseInt(projectyearDropdown.val());

		    if (selectedYear > 0) {
		      var firstDate = new Date(selectedYear, 0, 1); // January 1st
		      var lastDate = new Date(selectedYear, 11, 31); // December 31st

		     
		      var formattedFirstDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
		      var formattedLastDate = $.datepicker.formatDate('dd/mm/yy', lastDate);

		    
		      calculateyearDates(formattedFirstDate,formattedLastDate,'from','to','go-btn',0);
		  } else {
		      
		      console.log('Please select a valid year.');
		    }
		  }

		
	
	  
	  // for closed project pending
	  var closedcurrentYear = new Date().getFullYear();
      var closedyearDropdown = $("#closedcalendarYearDropdown");
      for (var i = 0; i < 5; i++) {
	    var year = closedcurrentYear - i;
	    closedyearDropdown.append('<option value="' + year + '">' + year + '</option>');
	  } 
	  function getClosedYearDateRange() {
		 
		  $(".months1").val(0) ;
		    var selectedYear = parseInt(closedyearDropdown.val());

		    if (selectedYear > 0) {
		      var firstDate = new Date(selectedYear, 0, 1); // January 1st
		      var lastDate = new Date(selectedYear, 11, 31); // December 31st

		     
		      var formattedFirstDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
		      var formattedLastDate = $.datepicker.formatDate('dd/mm/yy', lastDate);

		    
		      calculateyearDates(formattedFirstDate,formattedLastDate,'from1','to1','go-btn1',0);
		  } else {
		      
		      console.log('Please select a valid year.');
		    }
		  }
	  
	//Bhavesh(1-03-2024) for Oustanding Payments
	  var oustandingcurrentYear = new Date().getFullYear();
	  var oustandingyearDropdown = $("#outstandingcalendarYearDropdown");
	  for (var i = 0; i < 5; i++) {
	    var year = oustandingcurrentYear - i;
	    oustandingyearDropdown.append('<option value="' + year + '">' + year + '</option>');
	  } 
	  function getYearprojectRangeForOutstanding() {
	  	 
	  	  $(".months").val(0) ;
	  	    var selectedYear = parseInt(oustandingyearDropdown.val());

	  	    if (selectedYear > 0) {
	  	      var firstDate = new Date(selectedYear, 0, 1); // January 1st
	  	      var lastDate = new Date(selectedYear, 11, 31); // December 31st
	         
	  	     
	  	     
	  	      var formattedFromDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
	  	      var formattedToDate = $.datepicker.formatDate('dd/mm/yy', lastDate);
	  	      
	  	     
	  	    $("#monthsInvoice").val("0"); 
	  	  	
	  	      pendingPaymentsTableWithInvoiceYear(formattedFromDate,formattedToDate );
	  	  } else {
	  	      
	  		  $('#pendingPayments_dataTable').empty();
	  			$('#pendingPayments_dataTable').DataTable().destroy();
	  			var foot = $("#pendingPayments_dataTable").find('tfoot');
	  			 if(foot.length){
	  				 $("#pendingPayments_dataTable tfoot").remove();
	  			 }
	  			var  table= $('#pendingPayments_dataTable').DataTable( {
	  				destroy: true,
	  				dom: 'Bfrtip',		       
	  		        "ordering": false,
	  		        "paging":   false,
	  		        buttons: [
	  		             'excel', 'pdf', 'print'
	  		        ],
	  		        dom: 'Bfrtip',
	  				"columnDefs": [ {
	  					
	  			        "orderable": false,
	  		            "targets": 0
	  		        } ],
	  		        "order": [[ 1, 'asc' ]]
	  		    } );
	  	    }
	  	    
	  	   

	  	  }
	//Bhavesh(1-03-2024) for due invoices
	  var duecurrentYear = new Date().getFullYear();
	  var dueInvoiceDropdown = $("#dueInvoiceCalendarYearDropdown");
	  for (var i = 0; i < 5; i++) {
	    var year = duecurrentYear - i;
	    dueInvoiceDropdown.append('<option value="' + year + '">' + year + '</option>');
	  } 
	  function getYearprojectRangeForDueInvoice() {
	  	
	  	  $(".months").val(0) ;
	  	    var selectedYear = parseInt(dueInvoiceDropdown.val());

	  	    if (selectedYear > 0) {
	  	      var firstDate = new Date(selectedYear, 0, 1); // January 1st
	  	      var lastDate = new Date(selectedYear, 11, 31); // December 31st
	         
	  	     
	  	     
	  	      var formattedFromDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
	  	      var formattedToDate = $.datepicker.formatDate('dd/mm/yy', lastDate);
	  	      
	  	     
	  	    $("#monthsDue").val("0"); 
	  	   
	  	      dueInvoiceTableWithYear(formattedFromDate,formattedToDate );
	  	  } else {
	  	      
	  		  $('#pendingInvoices_dataTable').empty();
	  			$('#pendingInvoices_dataTable').DataTable().destroy();
	  			var foot = $("#pendingInvoices_dataTable").find('tfoot');
	  			 if(foot.length){
	  				 $("#pendingInvoices_dataTable tfoot").remove();
	  			 }
	  			var  table= $('#pendingInvoices_dataTable').DataTable( {
	  				destroy: true,
	  				dom: 'Bfrtip',		       
	  		        "ordering": false,
	  		        "paging":   false,
	  		        buttons: [
	  		             'excel', 'pdf', 'print'
	  		        ],
	  		        dom: 'Bfrtip',
	  				"columnDefs": [ {
	  					
	  			        "orderable": false,
	  		            "targets": 0
	  		        } ],
	  		        "order": [[ 1, 'asc' ]]
	  		    } );
	  	    }
	  	    
	  	   

	  	  }

	//declare the function by varun to get the sum by selecting groupname by varun 
	  function updateTotalAmount(table) {
	     
	  	   var totalinvoiceAmt = 0;
	  	    var totalwithouttaxAmt = 0;
	  	    var totalAmt = 0;
	  	  
	  	    table.rows({ search: 'applied' }).every(function () {
	  	        var data = this.data();
	  	        var valueinvoice = parseFloat(data[5].split(";")[1].replace(/,/g, ''));
	  	      totalinvoiceAmt += isNaN(valueinvoice) ? 0 : valueinvoice;
	  	      
	  	        if(data[6]!=null && data[7]!=null){
	  	        var valuewithouttax = parseFloat(data[6].split(";")[1].replace(/,/g, ''));
	  	        var valueamnt = parseFloat(data[7].split(";")[1].replace(/,/g, ''));

	  	       
	  	        totalwithouttaxAmt += isNaN(valuewithouttax) ? 0 : valuewithouttax;
	  	        totalAmt += isNaN(valueamnt) ? 0 : valueamnt;
	  	      $('.totalinvoice').text( convertAmountToINR(totalinvoiceAmt)); 
	  	    $('.totaltax').text( convertAmountToINR(totalwithouttaxAmt));
		       $('.totalAmount').text( convertAmountToINR(totalAmt));
	  	        
	  	        }
	  	        else{
	  	        	
	  	        	 $('.totalinvoice').text( convertAmountToINR(totalinvoiceAmt)+" Lakhs"); 
	  	        }
	  	        
	  	    });

	  	 
	  	 
	  		  
	  		
	  	 
	      
	      
	       
	   }
		
	 
	  
	  
  </script>

<!-- Bhavesh(26-06-2023) added style for resize button  -->
<style>

 

.custom-margin-left {
  margin-left: 94%; 

  } 
  
 
 .maximized .modal-content {
  top: 52%;
    left: 53%;
    transform: translate(-52%, -50%);
    /* right: -104%; */
    /* transition: right -0.7s ease-in-out; */
    /* margin-left: auto; */
    /* margin-right: auto; */
    width: 80.5vw;
  
 
} 


 .modal-open .modal {
      overflow-y: clip;
}
  
.close-btn {
  border: none;
  background-color: transparent;
   color: white; 
  cursor: pointer;
   font-size: 16px;
  transform: scale(1.8);

  line-height: 2;
  margin-top: -23px;
} 

.grey-row {
        background-color: #f2f2f2; /* Grey background color */
    }
/* added below style by varun for financialy validation tiles  */
.small-text {
    font-size: 14px; 
}

.small-texch{
font-size: 14px; 
}

/* Added by devesh on 09/08/23 to colour the changed value in proposal log table */
.different-value {
    color: red; /* Change this to the desired color */
}
/* End of proposal log colour */

</style>