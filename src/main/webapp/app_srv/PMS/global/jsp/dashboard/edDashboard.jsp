<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/dashboards.css">

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






		<div class="row padded tiles" >
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-grpwise-bargraph-modal">
				<!--Bhavesh 28-07-23 Added class new-tiles-purple  -->
					<div class="info-box   new-tiles  new-tiles-light-green">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-dash ">
								<div class="icon ">
									<i class="material-icons">playlist_add_check</i>
								</div>
							</div>
							<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

								<div class="content">
									<div class="text">
										<spring:message code="dashboard.project.label" />
									</div>
									<div class="text-small ">
										<sec:authentication
											property="principal.assignedOrganisationName" />
									</div>
									<div class="number">${projectCount}</div>
									<%--  <div class="number">${groupProjectDetails1}</div> --%>
									<!-- Get Total outlay share on 22/05/2023 -->
									<h5>
										<span style="font-weight: 300; color: yellow;">Total
											Outlay: </span><span>${cdaccost} Lakhs</span>
									</h5>
									<!-- C-DAC outlay share in 19/05/2023  -->
									<h5>
										<span style="font-weight: 300; color: yellow;">C-DAC
											Outlay: </span><span>${ongoingProjectCost} Lakhs</span>
									</h5>

								</div>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<!-- updated by devesh on 16/6/23 for adding onclick function -->
				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-employee-modal"> <!-- <a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-employee-modal" onclick="setTimeout(function() {drawdeputedChart();drawEmploymentTypeWiseChart();}, 1000)"> -->
					<!-- end -->
					<div class="info-box  new-tiles new-tiles-cyan-dash">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-dash ">
								<div class="icon">
									<i class="material-icons">wc</i>
								</div>
							</div>
							<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">
								<div class="content">
									<div class="text">
										<spring:message code="dashboard.employee.label" />
									</div>
									<div class="text-small ">
										<sec:authentication
											property="principal.assignedOrganisationName" />
									</div>
									<div class="number">${employeeCount}</div>
									<div>at CDAC : ${employeeAtCDACCount}</div>
									<!-- added for showing deputed at others on employee type by devesh on 14/6/23 -->
									<div >at Client Site : ${employeeAtCLIENTCount}</div>
									<div class="my-div"></div>
									
									
									<%-- <div>NULL : ${employeeAtOthersCount}</div> --%>
									<!-- End -->
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-income-modal">
					<div class="info-box  new-tiles  new-tiles-purple">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-dash ">
								<div class="icon">
									<i class="material-icons">trending_up</i>
								</div>
							</div>
							<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">
								<div class="content">
									<div class="text">
										<spring:message code="dashboard.income.label" />
									</div>
									<div class="text-small ">
										<sec:authentication
											property="principal.assignedOrganisationName" />
									</div>

									<div class="number" id="incomeOndate">${income}</div>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero ">
										<span class="pull-right white  "> <span>since: <span
												class="col-yellow1 bold " id="asOnDate"> ${startRange}</span>
										</span> <input class="input-field" type="hidden" id="dtStartDate">
										</span>
										<div class="my-div"></div>
										<div class="my-div"></div>
										<div class="my-div"></div>
										<!-- <div class="my-div"></div>
										<div class="my-div"></div>
										<div class="my-div"></div> -->
										
									</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-expenditure-modal">
					<div class="info-box new-tiles new-tiles-deep-yellow">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-dash ">
								<div class="icon">
									<i class="material-icons">sort</i>
								</div>
							</div>
							<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">
								<div class="content">

									<div class="text">
										<spring:message code="dashboard.expenditure.label" />
									</div>
									<div class="text-small ">
										<sec:authentication
											property="principal.assignedOrganisationName" />
									</div>
									<div class="number convert_lakhs" id="expenditureOndate">${expenditure}</div>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<span class="pull-right"> <span>since: </span><span
											class="col-yellow1 bold" id="asOnDateExp"></span> <input
											class="input-field" type="hidden" id="dtExpStartDate">
										</span>
										<div class="my-div"></div>
										<div class="my-div"></div>
										<div class="my-div"></div>
										<!-- <div class="my-div"></div>
										<div class="my-div"></div>
										<div class="my-div"></div> -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>

		</div>
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
				<!--2nd panel of tiles  -->

<div class="col-md-8 col-lg-8 col-sm-12 col-xs-12  ">
				<div class="panel panel-default panel-glance"
					style="background: #ececec !important;">

					<div class="panel-body zero">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items zero">
				
						<!--1st Tile  -->
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-newProposalList-modal">
								<div class="card card-stats" style="min-height: 130px;">
									<div class="card-header  card-header-icon">
										<div class="card-icon" style="background: #c9e7f9; border: 4px solid #a6d6f5;">
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/proposals.png"/>
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
												class="bold asOnDateProposals1" id="asOnDateProposals"
												style="font-size: 14px; color: #4d4b4b;"> </span>
											</span>

										</div>

									</div>
									
								</div>
							</a>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-newProjectList-modal">
								<div class="card card-stats" style="min-height: 130px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ddd5ff;border: 4px solid #bdaeff;">
												<!-- <i class="material-icons">add_alert</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/projects_rec.png"/>
											</div>
											
											<!-- change the label from new to project recieved on 24-05-23  -->
											<%---- Label Change of Projects Received [12-10-2023]-----%>
											<p class="card-category" title="New Projects received but Approval Pending">Project Approval Pending</p>
									<!-- 	<p class="card-category" title="New Projects received">New Projects</p> -->
									<!-- <p class="card-category" style="font-size:medium;color:blue">(Approval Pending)</p> -->
										<h3 class="card-title card-count" id="newProjectsOnDate">
											${newProjectCount}
											<!--  <small>GB</small> -->
										</h3>
									</div>
									<div class="card-footer">
										<div class="stats">
											<span> <span class="pad-top">since:</span> <span
												class="bold asOnDateProposals1" id="asOnDateProjects"
												style="font-size: 14px; color: #4d4b4b;"> </span>
											</span>

										</div>

									</div>
								</div>
							</a>
						</div>
						<!-- End of 1st Tile  -->
						<!-- 2nd tile  -->
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-pending-payments-modal">
								<div class="card card-stats" style="min-height: 130px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #c8ffe8;border: 4px solid #98eaca;">
												<!-- <i class="material-icons">info_outline</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/pending_payments.png"/>
											</div>
										<%----- [04-03-2024] shows the count as Ongoing and Technically Closed --%>
											<p class="card-category"
												title="Invoice raised but Payment is pending"  style="font-size:medium;"  >Outstanding Payments</p><%----- [17-10-2023] change the title of tile --%>
											<h3 class="card-title" style="font-size:medium;">
												${pendingPaymentsListAsOfDate} 
											</h3>
										<%-- 	<h3 class="card-title" style="font-size:10px;color: red;">
												<c:if test = "${countOfUnderApproval >0}">
                                                   Under Approval-${countOfUnderApproval}
                                                   </c:if>
												<!--  <small>GB</small> -->
											</h3> --%>
											<h3 class="card-title" style="font-size:12px;color: red;">
												<c:if test = "${countOfUnderApproval >0}">
                                                   Under Approval &
                                                   </c:if> Ongoing-<span  id="ongoing">${countOfOngoing+countOfUnderApproval}</span>
												<!--  <small>GB</small> -->
											</h3>
											<h3 class="card-title" style="font-size:12px;color: red;">
												Technically Closed-<span id="technicallyClosed">${countOfTechnicallyClosed}</span> <br>
												 <c:if test = "${conTotalAmount1 !=null}">
                                                  (${conTotalAmount1} Lakhs)
                                                   </c:if>
												
												 
												<!--  <small>GB</small> -->
											</h3>
											
											 
										</div>
										<div class="card-footer">
											<div class="stats">
											 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
												<span> <span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDatePendingPayments"
													style="font-size: 14px; color: #4d4b4b;">${startRange}</span>
												</span>

											</div>

										</div>
								</div>
							</a>
						</div>



					</div>
<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items zero ">
						<input type="hidden" id="month" value="" /> <input type="hidden"
							id="year" value="" />
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-pending-invoices-modal">
								<div class="card card-stats" style="min-height: 130px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ffd5df;border: 4px solid #fbb6ac;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/due_payments.png"/>
											</div>
											<%----- [04-03-2024] shows the count as Ongoing and Technically Closed --%>
										<p class="card-category"
											title="Payment due as per payment milestone. Invoice not yet generated." style="font-size:medium" >Due
											For Invoicing</p> <%----- [17-10-2023] change the title of tile --%>
										<h3 class="card-title" style="font-size:medium;">
												${pendingInvoiceList} 
											</h3>
											<%-- <h3 class="card-title" style="font-size:10px;color: red;">
												<c:if test = "${countOfUnderApprovalDueInvoice >0}">
                                                   Under Approval-${countOfUnderApprovalDueInvoice}
                                                   </c:if>
												<!--  <small>GB</small> -->
											</h3> --%>
											<h3 class="card-title" style="font-size:12px;color: red;">
											
												<c:if test = "${countOfUnderApprovalDueInvoice >0}">
                                                   Under Approval &
                                                   </c:if> Ongoing-<span  id="ongoing">${countOfOngoingDueInvoice+countOfUnderApprovalDueInvoice}</span>
												<!--  <small>GB</small> -->
											</h3>
												
											<h3 class="card-title" style="font-size:12px;color: red;">
												
												Technically Closed-${countOfTechnicallyClosedDueInvoice}<br>
												 <c:if test = "${dueConTotalAmount1!=null}">
                                                 (${dueConTotalAmount1} Lakhs)
                                               </c:if>
												 
                                              
												<!--  <small>GB</small> -->
											</h3>
									</div>
									<div class="card-footer">
										<div class="stats">
											 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
												<span> <span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateDueInvoice"
													style="font-size: 14px; color: #4d4b4b;">${startRange}</span>
												</span>

											</div>
									</div>
									
									
								</div>
							</a>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-closure-pending-modal">
									<div class="card card-stats" style="min-height: 130px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #efd1b9; border: 4px solid #d8ae8b;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closure_pending.png"/>
											</div>
										<!-- <p class="card-category"
											title="Closing date passed.Closure still pending.">Closure/Extension Pending</p> -->
										<p class="card-category"
													title="Closing date passed.Closure still pending.">Technical Closure/Extension Pending</p><!-- Name changed by devesh on 22-09-23 -->
										<h3 class="card-title">
											${pendingClosureCount}
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
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click here to view Projects Pending for Financial Approval." data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal3">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #efd1b9; border: 4px solid #ca9a72;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/bill_pending.png"/>
											</div>
											<p class="card-category"
												title="Click here to view Projects Pending for Financial Approval.">Financial Closure Pending</p>
											<h3 class="card-title">
												${underClosureCount1.size()}
												<!--  <small>GB</small> -->
											</h3>
										</div>
										<div class="card-footer">
											<%--------- Get the Count Hide fields for filters [12-10-2023] ----------%>
												<div class="stats hidden">
													<span> <span class="pad-top">since:</span> <span
														class="bold" id="asOnDateFinanceClosurePendingProjects"
														style="font-size: 14px; color: #4d4b4b;"> </span>
													</span>
												</div>
										</div>
									</div>
								</a>
							</div>
						
					</div>
					
					<!--Bhavesh (22-09-23) added new tile for Underclosure projects for Finance  -->
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items zero ">
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-closedProjectList-modal">
								<div class="card card-stats" style="min-height: 130px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ffe0bc;border: 4px solid #f7c287;">
												<!-- <i class="material-icons">backspace</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closed_projects1.png"/>
											</div>
										<p class="card-category"
											title="Click Here to View Technically and Financially Closed Projects.">Closed Projects</p>
										<h3 class="card-title" id="closedProjectsOnDate">
											${closedProjectsCount}
											<!--  <small>GB</small> -->
										</h3>
									</div>
									<div class="card-footer">


										<div class="stats">
											<span> <span class="pad-top">since:</span> <span
												class="bold asOnDateProposals1" id="asOnDateClosedProjects"
												style="font-size: 14px; color: #4d4b4b;"> </span>
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
								</div>
								
								
								
								
				</div>
			</div>
			</div>
			<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
			

				<div class="panel panel-default panel-glance">
					<div class="panel-heading font_eighteen bold"
						style="background: #d0d4fc !important; color: #000; border-color: #d0d4fc;">
						Milestone</div>
					<div class="panel-body " style="background: #ececec !important;">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12  ">
			<%------------      Add data target to call the Milestone due in one month modal [21-08-2023]   ------------------%>
							<a class="" href="#"
										title="Click Here to View Milstone Due in next one month"
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-milestone-dueInOneMonth-modal">
								<div class="serviceBox-MilestoneNew purple">
									<div class="service-icon">
										<span><i class="material-icons">view_list</i></span>
									</div>
									<h4 class="title">Due in Next One Month</h4>
									<h4 class="card-title">
										${countMilestone}
										<!--  <small>GB</small> -->
									</h4>
								</div>

							</a>
						</div>
       <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12  pad-top">
       <%------------      Add data target to call the Milestone exceeded deadline deatils modal [21-08-2023]   ------------------%>
							<a class="" href="#"
										title="Click Here to View Exceeded Deadline"
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-milestone-exceededDeadline-modal">	
								<div class="serviceBox-MilestoneNew ">
									<div class="service-icon">
										<span><i class="material-icons">report_problem</i></span>
									</div>
									<h4 class="title">Exceeded Deadline</h4>
									<h4 class="card-title">
										${countExceededMilestone}
										<!--  <small>GB</small> -->
									</h4>
								</div>

							</a>
						</div>
			</div>
			</div>
			
			</div>
				<%------------- Tile For Project Document Details [18-03-2024] ---------%>
				<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
					<div class="panel panel-default panel-glance">
						<div class="panel-heading font_eighteen bold"
							style="background: #d0d4fc !important; color: #000; border-color: #d0d4fc;">
							Documents
						</div>
						<div class="panel-body pb-4" style="background: #ececec !important;padding:7px">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12  ">
								<a class="" href="#"
										title="Click Here to View Project Document Details ."
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-document-details-modal">
									<div class="serviceBox-Document">
										<div class="service-icon" style="border: 4px solid #0288d1; color: #0288d1">
											<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/documentPLDashboardIcon.png" style="width:45px;height:45px;margin:-1px 3px;padding:1px;"/>
										</div>
										<h4 class="title pb-2">View Project Document Details</h4>
									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
				<%------------- End of Tile For Project Document Details [18-03-2024] ---------%>
			</div>
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
			<div class="panel panel-default panel-glance">
				<!-- <div class="panel-heading font_eighteen bold"
					style="background: #d0d4fc !important; color: #000; border-color: #d0d4fc;" id="dateDisplay">
					Employee</div> -->
				<div class="panel-heading bold" style="background: #d0d4fc !important; color: #000; border-color: #d0d4fc;">
				    <span class="font_eighteen">Employee</span>
				    <span style="color: #000480;font-size: 16px;">&nbsp;<span id="dateDisplay"></span></span>
				</div><!-- Modified by devesh on 13-10-23 to display Last updated At-->
				<div class="panel-body zero" style="background: #ececec;">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items ">
						<div class="col-md-3 col-sm-6 col-lg-3 col-xs-6">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-new-joinee-modal">
								<div class="serviceBoxEmployeesDtl">
									<div class="service-icon">
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/newEmp.png"/>
									</div>
									<h4 class="title" title="List of New Joinee Employee">New
										Joinee</h4>
									<h4 class="card-title card-count description " id="newJoineEmp">
										${listOfJoinedEmp}
										<!--  <small>GB</small> -->
									</h4>
									<div class="read-more">
										<div class="card-footer card-footer-new">
											<div class="stats ">
												<span> <span class="pad-top" style="color: #fff;">since:</span>
													<span class="bold asOnDateProposals1" id="asOnDateJoinee"
													style="font-size: 14px; color: #fff;">${startRange}
												</span>
												</span>

											</div>

										</div>
									</div>
								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-lg-3 col-xs-6">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#Employee-Details-modal">
								<div class="serviceBoxEmployeesDtl ">
									<div class="service-icon">
									<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/nonMap.png"/>
									</div>
									<h4 class="title" title="Non-Mapped">Non-Mapped</h4>
									<h4 class="card-title card-count description"
										id="empDetailsCount"></h4>
									<div class="read-more">
										<div class="card-footer card-footer-new">
											<div class="stats ">View Details</div>

										</div>
									</div>
								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-lg-3 col-xs-6">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#Employee-UnderUti-Details-modal">
								<div class="serviceBoxEmployeesDtl ">
									<div class="service-icon">
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/empUtilization.png"/>
									</div>
									<h4 class="title" title="Under Utilization">Under
										Utilization</h4>
									<h4 class="card-title card-count description"
										id="empUnderUtilCount"></h4>
									<div class="read-more">
										<div class="card-footer card-footer-new">
											<div class="stats ">View Details</div>

										</div>
									</div>

								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-lg-3 col-xs-6">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-resigned-emp-modal">
								<div class="serviceBoxEmployeesDtl ">
									<div class="service-icon">
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/empResigned.png"/>
									</div>
									<!-- <h4 class="title" title="List of Resigned Employees">Resigned</h4> -->
									<h4 class="title" title="List of Relieved Employees">Relieved</h4><!-- Name Modified by devesh on 19-10-23 -->
									<h4 class="card-title card-count description"
										id="newResignEmp">${listOfResignedEmp}</h4>
									<div class="read-more">
										<div class="card-footer card-footer-new">
											<div class="stats">
												<span> <span class="pad-top" style=" color: #fff;">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateResign"
													style="font-size: 14px; color: #fff;">${startRange}
												</span>
												</span>

											</div>

										</div>
									</div>

								</div>
							</a>
						</div>
						
						<%-- <div class="col-md-3 col-sm-6 col-lg-3 col-xs-6">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-rejoin-emp-modal">
								<div class="serviceBoxEmployeesDtl">
									<div class="service-icon">
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/newEmp.png"/>
									</div>
									<h4 class="title" title="List of Rejoined Employees">Rejoined
										</h4>
									<h4 class="card-title card-count description" id="newRejoinEmp">
										${rejoinEmp}
										<!--  <small>GB</small> -->
									</h4>
									<div class="read-more">
										<div class="card-footer card-footer-new">
											<div class="stats ">
												<span> <span class="pad-top" style="color: #fff;">since:</span>
													<span class="bold" id="asOnDateRejoin"
													style="font-size: 14px; color: #fff;">${startRange}
												</span>
												</span>

											</div>

										</div>
									</div>
								</div>
							</a>
						</div> --%>
					</div>
				</div>
			</div>
		</div>

		
			<%-- <div
			class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-bottom "
			id="glance">
			<div class="panel panel-info panel-glance">
				<div class="panel-heading font_eighteen"><spring:message code="dashboard.glance.label" /></div>
				<div class="panel-body">
					<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 groupdiv"
							id="groupdiv">							
								<c:forEach items="${groupnames}" var="grp">
									<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">
										<button class="btn btn1 "
											data-toggle="tooltip" data-placement="top"
											title="Click here to see details"
											style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
									</div>
									</c:forEach>

						</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<div
							class="table-wrapper-scroll-y my-custom-scrollbar table-responsive"
							id="contain">

							<table class="table table-bordered table-striped mb-0" id="glance_data_table">
								<thead class="my-custom-scrollbar-thead datatable_thead">
									<tr>
										<th scope="col"><spring:message code="dashboard.serial.shortcode.label" /></th>
										<th scope="col"><spring:message code="dashboard.project.label" /></th>
										<th scope="col"><spring:message code="dashboard.groups.label" /></th>
										<th scope="col"><spring:message code="dashboard.status.label" /></th>
									</tr>
								</thead>
								<tbody class="my-custom-scrollbar-tbody">
									<c:forEach items="${activeProjects}" var="project"
										varStatus="loop">
										<tr>
											<td>${loop.index+1}</td>
											<td>${project.projectName}</td>
											<td>${project.groupName}</td>
											<td>
												
											</td>
										</tr>



									</c:forEach>

					
								</tbody>
							</table>

						</div>


					</div> 

				</div>

			</div>
		</div> --%>
			<!--End of  table-->

			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
				<!--3rd panel of tiles  -->


				<div class="panel panel-default panel-glance">
					<div class="panel-heading font_eighteen bold"
						style="background: #d0d4fc !important; color: #000; border-color: #d0d4fc;">
						Group wise Summary</div>
					<div class="panel-body zero">


						<div
							class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-top">

							<label class="font_16" for="from">From</label> <input type="text"
								id="fromDet" name="from" value="${startRange}" readonly /> <label
								class="font_16" for="to">To</label> <input type="text"
								id="toDet" name="to" value="${endRange}" readonly /> &nbsp;
							&nbsp;
							<button type="button" class="btn btn-success go-btn-det font_16">Go</button>

						</div>

						<div
							class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-bottom-double">

							<table id="allDetailsTable"
								class="table table-striped table-bordered allDetailsTable"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th width="2%" class="sorting_disabled">S.No.</th>
										<th width="8%">Department</th>
										<th width="10%">Proposals Submitted</th>
										<th width="10%">Projects Received</th>
										<th width="10%">Projects Closed</th>
										<th width="10%">Income <br> <span class="font_10">(Payment
												Realized)</span></th>
										<th width="10%">New Joinees</th>
										<!-- <th width="10%">Resigned</th> -->
										<th width="10%">Relieved</th><!-- Name Modified by devesh on 19-10-23 -->
										<th width="10%">Employees <br> <span class="font_10">(as
												on date)</span></th>
										<th width="10%">Projects <br> <span class="font_10">(as
												on date)</span></th>

									</tr>
								</thead>

								<tbody id="datatable_tbody" class="datatable_tbody">
									<c:forEach var="entry" items="${getallDetails}"
										varStatus="loop">
										<%-- <c:if test="${entry.last}">${item}</c:if> --%>
										<c:choose>

											<c:when test="${entry.key eq 'Total'}">
												<tfoot class="datatable_tfoot" style="background: #1578c2;">
													<tr class="no-sort sorting_disabled white">
														<td></td>
														<td class="bold">${entry.key}</td>
														<td class=" text-right bold">${entry.value.submittedProposalCount}
															<c:if
																test="${entry.value.submittedProposalCount ne null}">
																<br>
																<span class="white bold">
																	${entry.value.strTotalCost} Lakhs</span>
															</c:if>
														</td>
														<td class=" text-right bold">
															${entry.value.projectReceviedCount}<c:if
																test="${entry.value.projectReceviedCount ne null}">
																<br>
																<span class="white  bold">
																	${entry.value.strProjectTotalCost} Lakhs</span>
															</c:if>
														</td>
														<td class=" text-right  bold">
															${entry.value.closedProposalCout}</td>
														<td class=" text-right bold">${entry.value.income}
															Lakhs</td>
														<td class=" text-right bold">${entry.value.joinEmpCout}</td>
														<td class=" text-right bold">${entry.value.resignEmpCount}</td>
														<td class=" text-right bold">${entry.value.employeeCount}</td>
														<td class=" text-right bold">${entry.value.totalProjects}<c:if
																test="${entry.value.totalProjects ne null}">
																<br>
																<span class="bold white">
																	${entry.value.totalOutlayOfProject} Lakhs</span>
															</c:if></td>
													</tr>
												</tfoot>
											</c:when>

											<c:otherwise>
												<tr class="">
													<td>${loop.count}</td>
													<td class=" font_blue">${entry.key}</td>
													<td class=" text-right"><a
														title="Click here to view details" class="black bold"
														data-toggle="modal"
														data-target="#dash-newProposalListByGroup-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.submittedProposalCount}</a>
														<c:if test="${entry.value.submittedProposalCount ne null}">
															<br>
															<span class="dark-grey-font bold">
																${entry.value.strTotalCost} Lakhs</span>
														</c:if></td>
													<td class=" text-right "><a
														title="Click here to view details" class="black bold"
														data-toggle="modal"
														data-target="#dash-newProjectListByGroup-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.projectReceviedCount}</a>
														<c:if test="${entry.value.projectReceviedCount ne null}">
															<br>
															<span class="dark-grey-font bold">
																${entry.value.strProjectTotalCost} Lakhs </span>
														</c:if></td>
													<td class=" text-right "><a
														title="Click here to view details" class="black bold"
														data-toggle="modal"
														data-target="#dash-closedProjectListByGroup-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.closedProposalCout}</a>
													</td>
													<td class=" text-right "><a
														title="Click here to view details"
														class="dark-grey-font bold" data-toggle="modal"
														data-target="#dash-incomeByGroup-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.income}
															<c:if test="${entry.value.income ne null}">Lakhs</c:if>
													</a></td>
													<td class=" text-right "><a
														title="Click here to view details" class="black bold"
														data-toggle="modal"
														data-target="#dash-new-joinee-ByGroup-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.joinEmpCout}
													</a></td>
													<td class=" text-right "><a
														title="Click here to view details" class="black  bold"
														data-toggle="modal"
														data-target="#dash-resigned-empByGroup-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.resignEmpCount}</a></td>
													<td class=" text-right "><a
														title="Click here to view details" class="black  bold"
														data-toggle="modal"
														data-target="#dash-employee-group-modal"
														data-whatever="${entry.value.encGroupId},${entry.value.groupName}">${entry.value.employeeCount}
													</a></td>
													<td class=" text-right "><a
														title="Click here to view details" class="black  bold"
														onclick="loadGroupWiseProjects('${entry.value.encGroupId}')">${entry.value.totalProjects}</a>
														<c:if test="${entry.value.totalProjects ne null}">
															<br>
															<span class="dark-grey-font bold">
																${entry.value.totalOutlayOfProject} Lakhs</span>
														</c:if></td>

												</tr>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

				</div>

			</div>

<!--Bhavesh 28-07-23 for comparison  -->
			<c:forEach var="item" items="${underClosureCount1}">
				<p class="underclos hidden">${item.projectRefrenceNo}</p>
			</c:forEach>
	<%-- 
		<div class="row">
<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<!-- Grouped Bar Chart for proposals submitted in last 3 years group-wise -->
		<div
			class="col-md-6 col-sm-12 col-xs-12 col-lg-6 pad-bottom-double pad-top">

			<div class="panel panel-info panel-glance">
				<div class="panel-heading font_eighteen"><spring:message code="dashboard.proposalsubmitted.label" /> <span class="font_12"><spring:message code="dashboard.last3years.label" /> </span></div>
				<div class="panel-body">
					<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 ">
					<div id="columnchart_proposals" style="width:100%; height:400px;" ></div>
					</div>
				</div>
			</div>
</div>
<!--End Grouped Bar Chart for proposals submitted in last 3 years group-wise -->
		<!-- Grouped Bar Chart for projects received in last 3 years group-wise -->
		<div
			class="col-md-6 col-sm-12 col-xs-12 col-lg-6 pad-bottom-double pad-top">

			<div class="panel panel-info panel-glance">
				<div class="panel-heading font_eighteen"><spring:message code="dashboard.receivedprojects.label" /> <span class="font_12"><spring:message code="dashboard.last3years.label" /> </span></div>
				<div class="panel-body">
					<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 ">
					<div id="columnchart_material" style="width:100%; height:400px;" ></div>
					</div>
				</div>
			</div>
</div>
<!--End Grouped Bar Chart for projects received in last 3 years group-wise -->
</div>
</div>
 --%>
	


	<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-bottom ">

		<div class="panel panel-info panel-glance">
			<div class="panel-heading font_eighteen">
				<spring:message code="dashboard.projectlist.label" />
			</div>
			<div class="panel-body">
				<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 groupdiv"
					id="groupdiv">
					<c:forEach items="${groupnames}" var="grp">
						<%-- <c:choose>
										<c:when test="${fn:length(grp.groupName) >= 41}">
											<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
										</c:when>
										<c:when test="${fn:length(grp.groupName) >= 34}">
											<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
										</c:when>
										<c:when test="${fn:length(grp.groupName) >= 29}">
											<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
										</c:when>
										<c:otherwise> --%>
						<c:if test="${grp.showProjects eq 1}">
						<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
							<%-- </c:otherwise>
									</c:choose>	 --%>
							<button class="btn btn1 " data-toggle="tooltip"
								data-placement="top" title="Click here to see details"
								style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
								onclick="loadGroupWiseProjects('${grp.encGroupId}')">${grp.groupShortName}</button>
						</div>
						</c:if>
					</c:forEach>

				</div>

				<!-- GroupWise Data	-->
				<div class="row pad-top" id="GroupWiseTable">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class=" datatable_row pad-bottom">
							<fieldset class="fieldset-border">

								<!-- 	<legend class="bold legend_details">Details :</legend> -->
								<div class="table-responsive">
									<table id="example" class="table table-striped table-bordered"
										style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>
												<th style="width: 2%;"><spring:message code="serial.no" /></th>
												<%--  <th><spring:message code="project.projectRefNo"/></th>	 --%>
												<th><spring:message
														code="Project_Module_Master.projectName" /></th>
												<%-- <th><spring:message code="application.project.category.label"/></th> --%>
												<%-- <th><spring:message code="employee_Role.start_Date"/></th>
						<th><spring:message code="employee_Role.end_Date"/></th> --%>
												<th style="width: 10%;"><p>
														<spring:message code="project.details.duration" />
													</p> <!-- <p>(months)</p> --></th>
												<th><spring:message code="project.details.leader" /></th>
												<th style="width: 10%;"><p>
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
												<th><i class="fa fa-th-list"></i></th>
												<%-- <th class=""><spring:message code="project.projectRefNo"/></th> --%>
												<th class="">
													<%-- <spring:message code="Project_Module_Master.projectName"/> --%>
												</th>

												<%-- <th class="comboBox" id="businessTypeSelect"><spring:message code="application.project.category.label"/></th> --%>
												<%-- <th class="comboBox" style="width:10%;"><spring:message code="employee_Role.start_Date"/></th>
				<th class="comboBox" style="width:10%;"><spring:message code="employee_Role.end_Date"/></th> --%>
												<th style="width: 10%;" class="comboBox"><spring:message
														code="project.details.duration" /></th>
												<th class="">
													<%-- <spring:message code="project.details.leader"/> --%>
												</th>
												<!-- <th></th> -->
												<th style="width: 10%;" class="">
													<%-- <spring:message code="project.details.totalcost.lakhs"/> --%>
												</th>
												<th class="hidden" style="width: 10%;" class="">
													<%-- <spring:message code="project.details.amountreceived.lakhs"/> --%>
												</th>
<!-----------------  For Download the Excel of ongoing projects feature [ added by Anuj ] ------------------------------------------------------------->
												<!-- <th><i class="fa fa-download"></i></th> -->
												<th><i class="fa fa-download btn btn-success" onClick="downloadOngoingProjects()"></i><input type="hidden" id="groupId"/></th>
											</tr>
										</thead>
										<tbody class="">

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
					</div>
				</div>

				<div class=" hidden" id="groupWiseProject">

					<div class="col-md-12 col-lg-12 pad-top" id="groupWiseProjectDtl">

					</div>
					<div class="hidden " id="groupWiseProject">

						<div class="col-md-12 col-lg-12" id="groupWiseProjectDtl"></div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End Group Tiles  -->


	<div class="modal dash-grpwise-bargraph-modal dash-newProposalList-modal"
		id="dash-grpwise-bargraph-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.ongoingprojects.label" />
						in
						<sec:authentication property="principal.assignedOrganisationName" />
					</h3>
					<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
                    
                </div>
				</div>

				<div class="modal-body ">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<span class="orange">${ongoingProjectCount}</span> ongoing
							projects worth <span class="orange">${ongoingProjectCost}
							</span> lakhs
						</h4>
						<%-- <h5><span class="orange ">&#42; </span><spring:message code="dashbaord.amount.label" />.</h5> --%>

					</div>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div class="table-responsive">

							<table class="table table-bordered table-striped mb-0" id="">
								<thead class="my-custom-scrollbar-thead datatable_thead">
									<tr>
										<th><spring:message
												code="dashboard.serial.shortcode.label" /></th>
										<th scope="col"><spring:message
												code="dashboard.group.label" /></th>
										<th scope="col"><spring:message
												code="dashboard.number.projects.label" /></th>
										<%-- <th scope="col"><spring:message
												code="dashboard.project.outlay.label" /> (in INR)</th> --%>
												<!---------- CDAC and Total Outlay Display -->
												<th scope="col">C-DAC Outlay</th>
												<th scope="col">Total Outlay</th>
					<!----------CDAC and Total Outlay Display  -->	
									</tr>
								</thead>
								<!--Bhavesh 28-07-23 added sumProjectCost and sumTotalCost in tfoot   -->
										<c:set var="sumProjectCost" value="0.0" />
										<c:set var="sumTotalCost" value="0.0" />

										<c:forEach items="${countProjects}" var="count">
											<c:set var="sumProjectCost"
												value="${sumProjectCost +count.projectCost}" />

											<c:set var="sumTotalCost"
												value="${sumTotalCost + count.totalCost}" />
										</c:forEach>

										<tbody class="">

											<c:forEach items="${countProjects}" var="count"
												varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<%-- <td>${count.groupName}</td> --%>
													<!-- Added by devesh on 27-09-23 to make group name hyperlink to show ongoing projects -->
													<td><a href="#" onclick="closeModalAndClickButton('${count.groupName}'); return false;">${count.groupName}</a></td>
													<td class="text-right">${count.projectCount}</td>
													<!---------- CDAC and Total Outlay Display -->
													<td class="text-right currency-inr">${count.projectCost}</td>
													<td class="text-right currency-inr">${count.totalCost}</td>
												</tr>



											</c:forEach>
										</tbody>
                                       <!--Bhavesh 28-07-23 added tfoot to count total  -->
										<tfoot>
											<tr style="background: #1580d0">
												<td colspan="2" class="text-right"><b>Total:</b></td>
												<td class="text-right" id="totalProjectCount"><b>${projectCount}</b></td>
												<td class="text-right currency-inr"><b>${sumProjectCost}</b></td>
												<td class="text-right currency-inr"><b>${sumTotalCost}</b></td>
											</tr>
										</tfoot>
								
							</table>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>



	<div class="modal in pad-7-Percentage"
		id="dash-grpwise-bargraphNew-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.ongoingprojects.label" />
					</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body ">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div class="table-responsive">

							<table class="table table-bordered table-striped mb-0"
								id="ongoingProjectList">
								<thead class="my-custom-scrollbar-thead datatable_thead">
									<tr>
										<th><spring:message
												code="dashboard.serial.shortcode.label" /></th>
										<th scope="col"><spring:message
												code="dashboard.group.label" /></th>
										<th scope="col"><spring:message
												code="dashboard.number.projects.label" /></th>
										<th scope="col"><spring:message
												code="dashboard.project.outlay.label" /> (in INR)</th>
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

	<div class="modal dash-newProposalList-modal dash-modal"
				id="dash-newProposalList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<%-- <spring:message code="dashboard.newproposals.list" /> --%>
								List of Proposals Submitted & Project Received  <%---- Add Project Received [12-10-2023]-----%>
							</h3>
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom ">
									<%-- <spring:message code="dashboard.newproposals.label" /> --%>
									<%---- Add Project Received [12-10-2023]-----%>
								Proposals Submitted	& Project Received since : <span class="orange asOnDateProposals1" id="asOnDateProposals1"></span>
								</h4>
								</div>
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<!--Bhavesh(17-07-23) changed grid class from col-md-5 col-lg-5 col-sm-5 col-xs-6 to col-md-9 col-lg-9 col-sm-9  -->
								<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 pad-bottom text-right "> <label class="font_16">Total Proposals Submitted :</label>  <span id="totalProCost"> </span></div>
								<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom text-right " id="projRec"><label class="font_16"> Project Received : </label> <span id="totalConvCost"> </span></div>
								</div>
							<div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label> <select class="months1 inline form-control months" id="months" onchange="calculateDates(this.value,'fromProposal','toProposal','goProposal',1)" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								<%-- Bhavesh (17-10-23) extended the filter to 3 years and 5 years --%>
								<option value="36">3 Years</option> 
							    <option value="60">5 Years</option>
								</select>
			                 <!--  Bhavesh (18-10-23) hided custom Duration -->
							<!-- <div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" onclick="openProposalAnotherFilter()" id="propSubCust"><i>Custom Duration</i></a></div> -->
							</div>
							<div id="proposalsAnotherFilter">
							
							<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">

								<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input class="fromProposal1" type="text"
									id="fromProposal" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1" type="text" id="toProposal"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button" id="goProposal" class="btn btn-success ">Go</button>

							</div>
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom p-2" style="display: -webkit-inline-box;">
								 <label class="font_16" for="incomecalendarYearDropdown"> <span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year:</label>
                       <select id="incomecalendarYearDropdown" class="form-control" onchange="getYearIncomeRange()" style="width: auto; margin-left: 20px;height: 28px;">
                                <option value="0">Select Year's</option>
                                 </select>
						    </div>
							</div>
							</div>
							
							
						    
							<%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<!-- 
								<br> <br> -->
								<table id="newProposals_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											
											<th width="14%"><spring:message code="group.groupName" /></th>
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
									<thead class="filters">
										<tr>
											<th width="14%" class="comboBox"><spring:message code="group.groupName" /></th>
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
									<tfoot>
										<tr>
											<td colspan="9">
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
														<th width="14%"><spring:message code="group.groupName" /></th>
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
												<thead class="filters ">
													<tr>
														<th width="14%" class="comboBox"><spring:message code="group.groupName" /></th>
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
												<tfoot>
													<tr>
														<td colspan="9">
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
														<th width="14%"><spring:message code="group.groupName" /></th>
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
														<th width="14%" class="comboBox"><spring:message code="group.groupName" /></th>
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
														<td colspan="8">
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

			<!-- Proposal History Modal added by devesh on 2/8/23 -->
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

	<div class="modal dash-newProposalListByGroup-modal dash-modal"
		id="dash-newProposalListByGroup-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.newproposals.list" />
					</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.newproposals.label" />
							since : <span class="orange" id="asOnDateProp1"></span>
						</h4>
						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="propSubGroupName"></span>
						</h4>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

						<label class="" for="from">From</label> <input type="text"
							id="fromProposalNew" name="from" readonly /> <label class=""
							for="to">To</label> <input type="text" id="toProposalNew"
							name="to" readonly /> &nbsp; &nbsp; <input type="hidden"
							id="proposalGrpId">
						<button type="button" id="goProposal"
							class="btn btn-success btn-successNew">Go</button>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

						<table id="newProposalsByGroup_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<%--  <th width="15%"><spring:message code="group.groupName" /></th> --%>
									<th width="40%"><spring:message code="menu.proposal.label" /></th>

									<%-- <th width="30%"><spring:message code="dashboard.client.name" /></th> --%>
									<th width="15%"><spring:message
											code="dashboard.newproposals.submittedOn" /></th>

									<th width="15%"><spring:message
											code="project.details.duration" /></th>
									<th width="15%"><spring:message
											code="dashboard.newproposals.received.project" /></th>
									<th width="15%"><spring:message
											code="dashboard.lable.outlay" /></th>


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

	<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-newProjectList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
							<%---- Remove Received [12-10-2023]-----%>
								List of Projects Approval Pending <%-- <spring:message code="dashboard.newprojects.list" /> --%>
							</h3>
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									Projects <%-- <spring:message code="dashboard.newprojects.label" /> --%><%---- Remove Received [12-10-2023]-----%>
									since : <span class="orange asOnDateProposals1" id="asOnDateProjects1"></span>
								</h4>
								 <%------------------  Display project count with total cost of projects [ 21-08-2023 ]  --------------------%>
								<div class="text-right"><label class="font_16"> Project Received : </label> <span id="totalProjectReceivedCost"> </span></div>
							</div>

								<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Last:</label> <select id="months" onchange="calculateDates(this.value,'from','to','go-btn',0)" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<label class="" for="from">From</label> <input type="text"
									id="from" name="from" readonly /> <label class="" for="to">To</label>
								<input type="text" id="to" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn">Go</button>

							</div> -->
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

							<div
								class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label> <select
									class="months1 inline form-control months" id="months"
									onchange="calculateDates(this.value,'from','to','go-btn',0)"
									style="width: 80%">
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
										class="fromProposal1" type="text" id="from" name="from"
										readonly /> <label class="font_16" for="to">To</label> <input
										class="toProposal1" type="text" id="to" name="to" readonly />
									&nbsp; &nbsp;
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
						
 <%------------------  Table contains all projects with its pending status [ 21-08-2023 ]  --------------------%>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

							<table id="newProjects_dataTable"
								class="table table-striped table-bordered" style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th width="15%"><spring:message code="group.groupName" /></th>
										<th width="18%"><spring:message code="master.name" /></th>
										<th width="12%"><spring:message code="client.name" /></th>
										<th width="12%"><spring:message
												code="projectMaster.MOUdate" /> <i
											class="fas fa-grip-lines-vertical"></i> <spring:message
												code="projectMaster.WorkorderDate" /></th>
										<th width="10%"><spring:message code="dashboard.project.startDate" /></th>
										<th width="10%"><spring:message code="projectMaster.endDate" /></th>
										<th width="8%"><spring:message
												code="dashboard.lable.outlay" /></th>
										<th width="10%">Status</th>
									</tr>
								</thead>
								<thead class="filters">
									<tr>
										<th width="15%" class="comboBox"></th>
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
								<%--------- Add footer in the table [12-10-2023] ----------%>
								 <tfoot>
										<tr>
											<td colspan="8">
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
						 <%------------------  End of Table contains all projects with its pending status --------------------%>
						<%-- <!-------------------------------------------- PENDING AT PL [04-08-2023] --------------------------------------------------------------------->
							<div class="panel-group" id="accordionGroupClosed" role="tablist" aria-multiselectable="true">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<div class="panel panel-info  ">
										<div class="panel-heading font_16" data-toggle="collapse" data-target="#multiCollapse10" style="cursor: pointer;background: #1e2787 !important;">
											<a class="collapsed" style="color: white;"
													data-toggle="collapse" href="#multiCollapse10"
													aria-expanded="false" aria-controls="multiCollapse10">
													Pending at PL
											</a>
										</div>
										<div class="panel-collapse collapse" id="multiCollapse10" role="tabpanel" aria-labelledby="headingTwo">
											<div class="panel-body">
												<div class="table-responsive">
													<table id="newProjects_dataTable_PL"
														class="table table-striped table-bordered"
														style="width: 100%">
														<thead class="datatable_thead bold ">
														<tr>
															<th width="15%"><spring:message
																	code="group.groupName" /></th>
															<th width="25%"><spring:message code="master.name" /></th>

															<!-- change the label clientname on 23-05-23 -->
															<th width="12%"><spring:message
																	code="dashboard.client.name" /></th>
																<th width="12%"><spring:message
																		code="projectMaster.MOUdate" /> <i class="fas fa-grip-lines-vertical"></i> <spring:message
																		code="projectMaster.WorkorderDate" /></th>
															<th width="18%"><spring:message
																	code="dashboard.project.startDate" /></th>
															<th width="18%"><spring:message
																	code="projectMaster.endDate" /></th>
															<th width="20%"><spring:message
																	code="dashboard.lable.outlay" /></th>
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
<!-------------------------------------------- PENDING AT HOD [04-08-2023] --------------------------------------------------------------------->
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<div class="panel panel-info">
										<div class="panel-heading font_16" data-toggle="collapse" data-target="#multiCollapse11" style="cursor: pointer;background: #1e2787 !important;">
										  <a class="collapsed" style="color: white;"
													data-toggle="collapse" href="#multiCollapse11"
													aria-expanded="false" aria-controls="multiCollapse11">
													Pending at HOD
											</a>
										</div>
										<div class="panel-collapse collapse" id="multiCollapse11" role="tabpanel" aria-labelledby="headingTwo">
											<div class="panel-body">
												<div class="table-responsive">
													<table id="newProjects_dataTable_HOD"
														class="table table-striped table-bordered"
														style="width: 100%">
														<thead class="datatable_thead bold ">
															<tr>
															<th width="15%"><spring:message
																	code="group.groupName" /></th>
															<th width="25%"><spring:message code="master.name" /></th>

															<!-- change the label clientname on 23-05-23 -->
															<th width="12%"><spring:message
																	code="dashboard.client.name" /></th>
																<th width="12%"><spring:message
																		code="projectMaster.MOUdate" /> <i class="fas fa-grip-lines-vertical"></i> <spring:message
																		code="projectMaster.WorkorderDate" /></th>
															<th width="18%"><spring:message
																	code="dashboard.project.startDate" /></th>
															<th width="18%"><spring:message
																	code="projectMaster.endDate" /></th>
															<th width="20%"><spring:message
																	code="dashboard.lable.outlay" /></th>
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

<!-------------------------------------------- PENDING AT GC [04-08-2023] --------------------------------------------------------------------->
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<div class="panel panel-info  ">
										<div class="panel-heading font_16" data-toggle="collapse" data-target="#multiCollapse12" style="cursor: pointer;background: #1e2787 !important;">
											<a class="collapsed" style="color: white;"
													data-toggle="collapse" href="#multiCollapse12"
													aria-expanded="false" aria-controls="multiCollapse12">
													Pending at GC
											</a>
										</div>
										<div class="panel-collapse collapse" id="multiCollapse12" role="tabpanel" aria-labelledby="headingTwo">
											<div class="panel-body">
												<div class="table-responsive">
													<table id="newProjects_dataTable_GC"
														class="table table-striped table-bordered"
														style="width: 100%">
														<thead class="datatable_thead bold ">
															<tr>
															<th width="15%"><spring:message
																	code="group.groupName" /></th>
															<th width="25%"><spring:message code="master.name" /></th>

															<!-- change the label clientname on 23-05-23 -->
															<th width="12%"><spring:message
																	code="dashboard.client.name" /></th>
																<th width="12%"><spring:message
																		code="projectMaster.MOUdate" /> <i class="fas fa-grip-lines-vertical"></i> <spring:message
																		code="projectMaster.WorkorderDate" /></th>
															<th width="18%"><spring:message
																	code="dashboard.project.startDate" /></th>
															<th width="18%"><spring:message
																	code="projectMaster.endDate" /></th>
															<th width="20%"><spring:message
																	code="dashboard.lable.outlay" /></th>
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
<!-------------------------------------------- PENDING AT PMO [04-08-2023] --------------------------------------------------------------------->							
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<div class="panel panel-info  ">
										<div class="panel-heading font_16" data-toggle="collapse" data-target="#multiCollapse13" style="cursor: pointer;background: #1e2787 !important;">
											<a class="collapsed" style="color: white;"
													data-toggle="collapse" href="#multiCollapse13"
													aria-expanded="false" aria-controls="multiCollapse13">
													Pending at PMO
											</a>
										</div>
										<div class="panel-collapse collapse" id="multiCollapse13" role="tabpanel" aria-labelledby="headingTwo">
											<div class="panel-body">
												<div class="table-responsive">
													<table id="newProjects_dataTable_PMO"
														class="table table-striped table-bordered"
														style="width: 100%">
														<thead class="datatable_thead bold ">
															<tr>
															<th width="15%"><spring:message
																	code="group.groupName" /></th>
															<th width="25%"><spring:message code="master.name" /></th>

															<!-- change the label clientname on 23-05-23 -->
															<th width="12%"><spring:message
																	code="dashboard.client.name" /></th>
															<th width="12%"><spring:message
																		code="projectMaster.MOUdate" /> <i class="fas fa-grip-lines-vertical"></i> <spring:message
																		code="projectMaster.WorkorderDate" /></th>
															<th width="18%"><spring:message
																	code="dashboard.project.startDate" /></th>
															<th width="18%"><spring:message
																	code="projectMaster.endDate" /></th>
															<th width="20%"><spring:message
																	code="dashboard.lable.outlay" /></th>
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
<!-----------------------------------  panel-group End  ------------------------------------------------------------>--%>
						</div>
					</div>
				</div>
			</div>


	<div class="modal dash-newProjectListByGroup-modal dash-modal"
		id="dash-newProjectListByGroup-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.newprojects.list" />
					</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.newprojects.label" />
							since : <span class="orange" id="asOnDateProj1"></span>
						</h4>

						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="projRecGroupName"></span>
						</h4>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

						<label class="" for="from">From</label> <input type="text"
							id="fromNew" name="from" readonly /> <label class="" for="toNew">To</label>
						<input type="text" id="toNew" name="to" readonly /> &nbsp; &nbsp;
						<input type="hidden" id="projectGroupId">
						<button type="button" class="btn btn-success go-btnNew">Go</button>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

						<table id="newProjectsByGroups_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<%--  <th width="20%"><spring:message code="group.groupName" /></th> --%>
									<th width="35%"><spring:message code="master.name" /></th>
									<%-- 				<th width="20%"><spring:message code="project.projectRefNo" /></th>
													<th width="30%"><spring:message code="dashboard.client.name" /></th> --%>
									<th width="15%"><spring:message
											code="projectMaster.MOUdate" /></th>
									<th width="15%"><spring:message
											code="projectMaster.WorkorderDate" /></th>
									<th width="15%"><spring:message
											code="dashboard.project.startDate" /></th>
									<th width="15%"><spring:message
											code="dashboard.lable.outlay" /></th>

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

<!-- Previous History of Emp -->
<div class="modal amount_receive_deatil_modal " id="empPreHistoryDetailModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Previous Employement</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body emp_detail_modal">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
             <table id="previousHistory" class="table table-striped table-bordered example_det"
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						   <th>S.No</th>
						   <th>Employee Id</th> 
						   <th>Designation</th>  
							<th>Department</th>
							<th>Date of Joining</th>
							<th>Date of Release</th>
							
						</tr>
					</thead>
					<tbody class="center">
					
				</tbody>
			</table>
			</div>
				</div>
				
			</div>
		</div>
	</div>
<div class="modal dash-newProjectList-modal dash-modal"
				id="dash-rejoin-emp-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								Rejoined
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									Rejoind
									since : <span class="orange" id="asOnDateRejoin1"></span>
								</h4>

							</div>
							

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<!-- <div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label> <select id="months" onchange="calculateDates(this.value,'fromRej','toRej','go-btn-rejoin',0)" class="inline form-control" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			
<div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" id="rejoinCustom" onclick="openRejoinAnotherFilter()"><i>Custom Duration</i></a></div>
							</div> -->
							<div id="rejoinAnotherFilter">
							<!-- <div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div> -->
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">

								<label class="font_16" for="from">From</label> <input type="text"
									id="fromRej" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input type="text" id="toRej"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn-rejoin">Go</button>

							</div>
							</div>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="rejoin_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											 <th width="10%" class="comboBox">Group Name
											</th> 
											<th width="15%">Employee Id</th>
									
											<th width="25%"><spring:message
													code="employee_Role.employee_Name" /></th>
											<th width="25%"><spring:message
													code="employee.designation" /></th>
											<th width="20%">Date of Joining</th>
											<th width="15%">Status</th>

										</tr>
									</thead>
									<thead class="filters ">
												<tr>
											<th width="5%"></th>
											<th width="10%"  class="comboBox">Group Name</th>
											<th width="15%"></th>
											<th width="25%" ></th>
											<th width="25%" class="comboBox"><spring:message
													code="employee.designation" /></th>
											<th width="20%"></th>
											<th width="15%"></th>
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
			
	<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
		id="dash-new-joinee-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		     <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">New Joinees</h3>
					<button type="button" class="close"  onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>
				
              <!-- added the filter from last year on 09-06-2023  -->
              
				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							New Joinees since : <span class="orange asOnDateProposals1" id="asOnDateJoinee1"></span>
						</h4>

					</div>
                      
                      <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label> <select class="months1" id="months" onchange="calculateDates(this.value,'fromJoin','toJoin','go-btn-join',0)" class="inline form-control" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
								
								<!-- Hide the custom duration on 09-06-2023 -->
<!-- 			<div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" id="joinCustom" onclick="openJoiningAnotherFilter()"><i>Custom Duration</i></a></div> -->

							</div>
							<div id="joineeAnotherFilter">
							<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div>
					<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar "> -->
                           <div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
						<label class="" for="from">From</label> <input class="fromProposal1" type="text"
							id="fromJoin" name="from" readonly /> <label class="" for="to">To</label>
						<input class="toProposal1" type="text" id="toJoin" name="to" readonly /> &nbsp;
						&nbsp;
						<button type="button" class="btn btn-success go-btn-join">Go</button>

					</div>
                    </div>
                    </div>
					<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

						<label class="" for="from">From</label> <input type="text"
							id="fromJoin" name="from" readonly /> <label class="" for="to">To</label>
						<input type="text" id="toJoin" name="to" readonly /> &nbsp;
						&nbsp;
						<button type="button" class="btn btn-success go-btn-join">Go</button>

					</div> -->
					 

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

						<table id="joinee_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<th width="20%"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="15%">Employee Id</th>

									<th width="25%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="20%"><spring:message
											code="employee.designation" /></th>
									<th width="15%"><spring:message
											code="employee.dateofJoining" /></th>
									<th width="15%">Status</th>

								</tr>
							</thead>
							<thead class="filters ">
								<tr>
									<th width="5%"></th>
									<th width="20%" class="comboBox"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="15%"></th>
									<th width="25%"></th>
									<th width="20%" class="comboBox"><spring:message
											code="employee.designation" /></th>
									<th width="15%"></th>
									<th width="15%"></th>
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


	<div class="modal dash-newProjectList-modal dash-modal"
		id="dash-new-joinee-ByGroup-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">New Joinees</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							New Joinees since : <span class="orange" id="asOnDateJoin1"></span>
						</h4>

						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="joineeGroupName"></span>
						</h4>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

						<label class="" for="from">From</label> <input type="text"
							id="fromJoinNew" name="from" readonly /> <label class=""
							for="to">To</label> <input type="text" id="toJoinNew" name="to"
							readonly /> &nbsp; &nbsp; <input type="hidden" id="joinGroupId">
						<button type="button" class="btn btn-success go-btn-joinNew">Go</button>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

						<table id="joineeBygroup_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<%-- 	<th width="20%"><spring:message code="employee_Role.group_Name" /> --%>
									</th>
									<th width="15%">Employee Id</th>

									<th width="25%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="20%"><spring:message
											code="employee.designation" /></th>
									<th width="15%"><spring:message
											code="employee.dateofJoining" /></th>
									<th width="15%">Status</th>

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

	<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
		id="dash-resigned-emp-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
		<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<!-- <h3 class="modal-title center" id="">Resigned Employees</h3> -->
					<h3 class="modal-title center" id="">Relieved Employees</h3><!-- Name Modified by devesh on 19-10-23 -->
					<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<!-- Resigned since : <span class="orange asOnDateProposals1" id="asOnDateResign1"></span> -->
							Relieved since : <span class="orange asOnDateProposals1" id="asOnDateResign1"></span><!-- Name Modified by devesh on 19-10-23 -->
						</h4>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

						<label class="" for="from">From</label> <input class="fromProposal1" type="text"
							id="fromRes" name="from" readonly /> <label class="" for="to">To</label>
						<input class="toProposal1" type="text" id="toRes" name="to" readonly /> &nbsp; &nbsp;
						<button type="button" class="btn btn-success go-btn-resign">Go</button>

					</div>
					
                    <div class="col-md-12"> 
                        <!-- Nav tabs -->
                           <div class="card">
                                 <ul class="nav nav-tabs proj_details center pad-left-double pad-right-double" role="tablist">
        
                                    <li role="presentation" class="active " style="width: 20%;"><a class="color1" href="#employeeWise" aria-controls="about" role="tab" data-toggle="tab"><i class="fa fa-street-view fa-2x"></i>  <h6>Employee Wise Listing</h6></a></li>
                                    <li role="presentation" style="width: 20%;"><a class="color2" href="#groupWise" onclick="" aria-controls="budget" role="tab" data-toggle="tab"><i class="fa fa-street-view fa-2x "></i>  <h6>Group/Designation Wise listing</h6></a></li>
                                  </ul>
                                  <!-- Tab panes -->
                                   <div class="tab-content ">
                                      <div role="tabpanel" class="tab-pane active" id="employeeWise"> 
                                             <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                                                 <table id="resigned_dataTable"
							                          class="table table-striped table-bordered" style="width: 100%">
							                            <thead class="datatable_thead bold ">
								           		          <tr>
									      			        <th width="5%"><spring:message code="forms.serialNo" /></th>
													        <th width="20%"><spring:message code="employee_Role.group_Name" /></th>
									                          <th width="15%">Employee Id</th>

									                           <th width="25%"><spring:message
											       			code="employee_Role.employee_Name" /></th>
															<th width="20%"><spring:message
															code="employee.designation" /></th>
															<!-- <th width="15%">Date of Resign/Release</th> -->
															<th width="15%">Date of Release</th><!-- Name Modified by devesh on 19-10-23 -->
															<th width="15%">Status</th>

														</tr>
														</thead>
														<thead class="filters ">
														<tr>
															<th width="5%"></th>
															<th width="20%" class="comboBox"><spring:message
															code="employee_Role.group_Name" /></th>
															<th width="15%"></th>
															<th width="25%"></th>
															<th width="20%" class="comboBox"><spring:message
															code="employee.designation" /></th>
															<th width="15%"></th>
															<th width="15%"></th>
														</tr>
														</thead>
													<tbody class="">
												</tbody>
											</table>
                                       </div>
                                   </div>
                                    
         
         
          <div role="tabpanel" class="tab-pane" id="groupWise"> 
               <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
                    <table id="group_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
						
							<thead class="datatable_thead bold ">
								<tr>
								
									<th width="40%" class="comboBox"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="40%" class="comboBox"><spring:message
											code="employee.designation" /></th>
									<th width="20%">Count</th>
									

								</tr>
							</thead>
							 <thead class="filters ">
							    <tr>
							        <th width="40%" class="comboBox"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="40%" class="comboBox"><spring:message
											code="employee.designation" /></th>
									<th width="20%"></th>
							    </tr>
							 </thead>
						  <tbody class="" id="groupBody">
							</tbody> 
						</table>
                     </div>
                </div>
         </div> 
					
					

					
					   <!-- second table in resigned modal end here -->
				</div>
			</div>
		</div>
	       </div>
	    </div> 
	  </div>    

	<div class="modal dash-newProjectListByGroup-modal dash-modal"
		id="dash-resigned-empByGroup-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <h3 class="modal-title center" id="">Resigned Employees</h3> -->
					<h3 class="modal-title center" id="">Relieved Employees</h3><!-- Name Modified by devesh on 19-10-23 -->
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<!-- Resigned since : <span class="orange" id="asOnDateRes1"></span> -->
							Relieved since : <span class="orange" id="asOnDateRes1"></span><!-- Name Modified by devesh on 19-10-23 -->
						</h4>

						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="resignGroupName"></span>
						</h4>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

						<label class="" for="from">From</label> <input type="text"
							id="fromResNew" name="from" readonly /> <label class="" for="to">To</label>
						<input type="text" id="toResNew" name="to" readonly /> &nbsp;
						&nbsp; <input type="hidden" id="resignGroupId">
						<button type="button" class="btn btn-success go-btn-resignNew">Go</button>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

						<table id="resignedByGroup_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<%-- <th width="20%"><spring:message code="employee_Role.group_Name" />
											</th> --%>
									<th width="15%">Employee Id</th>

									<th width="25%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="20%"><spring:message
											code="employee.designation" /></th>
									<!-- <th width="15%">Date of Resign/Release</th> -->
									<th width="15%">Date of Release</th><!-- Name Modified by devesh on 19-10-23 -->
									<th width="15%">Status</th>

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


	<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
		id="Employee-Details-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
		<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">List of not mapped Employees</h3>
					<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<table id="employeeDetails"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>

									<th width="20%"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="10%"><spring:message code="employee.EmployeeId" /></th>
									<th width="20%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="13%"><spring:message
											code="employee.designation" /></th>
									<%-- <th width="15%"><spring:message code="thirdParty.contact.number" /></th> --%>
									<!-- <th width="15%">Mapped Involvement</th>
									<th width="15%">Non-Mapped Involvement</th> -->
								</tr>
							</thead>

							<thead class="filters ">
								<tr>
									<th width="5%"></th>
									<th width="20%" class="comboBox"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="10%"></th>
									<th width="20%"></th>
									<th width="13%"></th>
									<!-- <th width="15%"></th>
									<th width="15%"></th> -->
								</tr>
							</thead>

							<tbody class=""></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
		id="Employee-UnderUti-Details-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">List of under utilization employees</h3>
					<button type="button" class="close"  onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<table id="underUtiEmployeeDetails"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>

									<th width="20%"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="10%"><spring:message code="employee.EmployeeId" /></th>
									<th width="20%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="13%"><spring:message
											code="employee.designation" /></th>
									<%-- <th width="15%"><spring:message code="thirdParty.contact.number" /></th> --%>
									<th width="15%">Mapped Involvement</th>
									<th width="15%">Non-Mapped Involvement</th>
								</tr>
							</thead>

							<thead class="filters ">
								<tr>
									<th width="5%"></th>
									<th width="20%" class="comboBox"><spring:message
											code="employee_Role.group_Name" /></th>
									<th width="10%"></th>
									<th width="20%"></th>
									<th width="13%"></th>
									<th width="15%"></th>
									<th width="15%"></th>
								</tr>
							</thead>

							<tbody class=""></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal amount_receive_deatil_modal dash-details-modal"
		id="empProjectDetails" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static"
		style="z-index: 99999 !important;">
		<div class="modal-dialog modal-lg" role="document"
			>
			<div class="modal-content" style="margin-left: 10%; width: 75%">
				<div class="modal-header" style="background: #15c2b2 !important;">
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
					<table id="empInvTable"
						class="table table-striped table-bordered example_det "
						style="width: 100%">
						<thead class="datatable_thead bold">
							<tr>
								<th>S.No</th>
								<th>Project Name</th>
								<th>Role</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Involvement</th>
							</tr>
						</thead>
						<tbody class="">

						</tbody>
					</table>
				</div>
				<!-- <div class="modal-footer" id="modelFooter"></div> -->
			</div>
		</div>
	</div>


	<%-- 	<div class="modal amount_receive_deatil_modal" id="allDetails"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content" style="margin-left:10%;width:75%">
				<div class="modal-header"  style="background: #15c2b2 !important;" >
					<h4 class="modal-title  center" id="exampleModalLabel" >All Details</h4>
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
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6" id="employeeName">
											
										</div>
									</div>
									</div>
             <table id="allDetailsTable" class="table table-striped table-bordered example_det "
						style="width: 100%">
					<thead class="datatable_thead bold">
						<tr>
						    <th>Department</th>
						    <th>Proposals Submitted </th>  
						    <th>Projects Received</th>
							<th>Projects Closed</th>
							<th>Joined Employees</th>
							<th>Resigned Employees</th>							
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
	</div> --%>

	<%--
		
 --%>
	<!-- End of modal for New Project -->

	<!-- Modal for Closed Projects -->
	<div class="modal dash-closedProjectList-modal dash-modal dash-newProposalList-modal "
				id="dash-closedProjectList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.closedprojects.list" />
							</h3>
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
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
							<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Last:</label> <select id="months" onchange="calculateDates(this.value,'from1','to1','go-btn1',0)" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			

							</div> -->
							<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar">

								<label class="" for="from">From</label> <input type="text"
									id="from1" name="from1" readonly /> <label class="pad-left"
									for="to">To</label> <input type="text" id="to1" name="to1"
									readonly />

								<button type="button" class="btn btn-success go-btn1 pad-left">Go</button>

							</div> -->
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label> <select class="months1 inline form-control months" id="months" onchange="calculateDates(this.value,'from1','to1','go-btn1',0)" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								<%-- Bhavesh (17-10-23) extended the filter to 3 years and 5 years --%>
								<option value="36">3 Years</option> 
							    <option value="60">5 Years</option>
								</select>
			
 <!--  Bhavesh (18-10-23) hided custom Duration -->
									<!-- <div style="text-align: right; olor: #1578c2;">
										<a href="javascript:void()"
											onclick="openClosedAnotherFilter()" id="closedCustom"><i>Custom
												Duration</i></a>
									</div> -->
							</div>
							<div id="closedAnotherFilter">
						
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
											<th width="5%"><spring:message code="group.groupName" /></th>
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
									<thead class="filters ">
										<tr>
											<th width="2%"></th>
											<th  width="5%" class="comboBox"></th>
											<th width="8%"></th> 
											<th width="5%"></th>
											<th width="5%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="8%"></th> <%--- Add new column for remarks [07-03-2024] -----%>
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
              
	<div class="modal dash-closedProjectListByGroup-modal dash-modal"
		id="dash-closedProjectListByGroup-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.closedprojects.list" />
					</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.closedprojects.label" />
							since : <span class="orange" id="asOnDateClosedProj1"></span>
						</h4>
						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="projClosedGroupName"></span>
						</h4>
					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar">

						<label class="" for="from">From</label> <input type="text"
							id="fromGroup" name="from1" readonly /> <label class="pad-left"
							for="to">To</label> <input type="text" id="toGroup" name="to1"
							readonly /> <input type="hidden" id="grpId">
						<button type="button"
							class="btn btn-success go-btn-group pad-left">Go</button>

					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

						<table id="closedProjectsByGroup_dataTable"
							class="table table-striped table-bordered">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<%-- <th width="20%"><spring:message code="group.groupName" /></th> --%>
									<th width="35%"><spring:message code="master.name" /></th>
									<%-- <th width="20%"><spring:message code="project.projectRefNo" /></th>
													<th width="30%"><spring:message code="dashboard.client.name" /></th> --%>
									<th width="10%"><spring:message
											code="dashboard.project.startDate" /></th>
									<th width="10%"><spring:message
											code="dashboard.project.closureDate" /></th>
									<th width="10%"><spring:message
											code="dashboard.lable.outlay" /></th>
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
											<th width="5%"><spring:message code="group.groupName" /></th>
											<th width="8%"><spring:message code="Project_Module_Master.projectName" /></th>
											<th width="5%"><spring:message code="client.name" /></th>
											<th width="5%"><spring:message code="project.details.leader" /></th>
											<th width="4%">Work-Order Date</th>
											
										    <th width="4%">Validation Remarks </th>
											<th width="4%">Validation Status </th>
										</tr>
									</thead>
									<thead class="filters ">
										<tr>
											<th width="2%"></th>
											<th  width="5%" class="comboBox"></th>
											<th width="8%"></th> 
											<th width="5%"></th>
											<th width="5%"></th>
											<th width="4%"></th>
											
											<th width="4%"></th>
											<th width="4%"></th>
										</tr>
									</thead>

									<tbody class="">
									</tbody>
									<tfoot>
													<tr>
														<td colspan="8">
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
		  				<%---------------- End of financial validated projects  [05-12-2023]---------------%>
		  				

	
	<%-- <!-- start of modal for financial validation Project -->
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
									class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">

									<label class="font_16" for="from">Last:</label> <select
										id="months"
										onchange="calculateDates(this.value,'fromvalidated','tovalidated','go-btnvalidate',0)"
										class="inline form-control valdprojects" style="width: 80%">
										<option value="0">Select Months/Year's</option>
										<option value="3">3 Months</option>
										<option value="6">6 Months</option>
										<option value="12">1 Years</option>
										<option value="24">2 Years</option>
										Bhavesh (17-10-23) extended the filter to 3 years and 5 years
								        <option value="36">3 Years</option> 
								        <option value="60">5 Years</option>
										
									</select>
                                   
								</div>
								<div id="closedAnotherFilter">
									<div class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom "
										style="padding-top: 4px;">
										<h3 style="color: #1578c2;">
											<strong>OR</strong>
										</h3>
									</div>
									<div
										class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">

										<label class="font_16" for="from">From</label> <input
											type="text" id="fromvalidated" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											id="tovalidated" name="to" readonly /> &nbsp; &nbsp;
										<button type="button" class="btn btn-success go-btnvalidate pad-left">Go</button>
									</div>
								</div>
					         <label class="font_16" for="validationStatusDropdown"></label>
						    <select id="validationStatusDropdown" class="form-control" style="width: auto;margin-left: 20px;">
						        
						        <option value="Yes">Financially Checked</option>
						        <option value="">Financially UnChecked</option>
						    </select>
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
											<th width="5%"><spring:message code="group.groupName" /></th>
											<th width="8%"><spring:message code="Project_Module_Master.projectName" /></th>
											<th width="5%"><spring:message code="client.name" /></th>
											<th width="5%"><spring:message code="project.details.leader" /></th>
											<th width="4%"><spring:message code="employee_Role.start_Date" /></th>
											<th width="4%"><spring:message code="employee_Role.end_Date" /></th>
										    <th width="4%">Validation Remarks </th>
											<th width="4%">Validation Status </th>
										</tr>
									</thead>
									<thead class="filters ">
										<tr>
											<th width="2%"></th>
											<th  width="5%" class="comboBox"></th>
											<th width="8%"></th> 
											<th width="5%"></th>
											<th width="5%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
											<th width="4%"></th>
										</tr>
									</thead>

									<tbody class="">
									</tbody>
									
								</table>
								
							</div>
						</div>
					</div>
				</div>
			</div> --%>
		  				<%---------------- End of financial validated projects  [05-12-2023]---------------%>
		  				

	<!-- Modal for Pending Closur -->
	<div class="modal dash-closure-pending-modal dash-modal dash-newProposalList-modal"
				id="dash-closure-pending-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
			  <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								List Of Projects Pending for Technical Closure
							</h3>
							<button type="button" class="close"  onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">
			
								<select id="filterClosureSymbol" onchange="calculateClosureDates()" class="inline form-control" style="width:32%">
								<option value=">=">Less than (<) </option>
								<option value="<=">Greater than (>)</option>
								
								</select>
			
			
								<label class="" for="from">Last:</label> <select class="months1" id="monthsClosure" onchange="calculateClosureDates()" class="inline form-control" style="width:32%">
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

					</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="pendingCLosure_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="2%"><spring:message  code="forms.serialNo" /></th>
											<th width="14%"><spring:message code="group.groupName" /></th>
										<th width="24%"><spring:message code="master.name" /></th>
										<%-- 	<th width="15%"><spring:message
																		code="project.projectRefNo" /></th>
												<th width="15%"><spring:message
													code="dashboard.client.name" /></th> --%>
											<th width="14%"><spring:message code="client.name" /></th>
												<th width="7%"><spring:message
																		code="project.details.leader" /></th>
											<th width="8%"><spring:message code="projectMaster.startDate" /></th>
											<th width="9%"><spring:message code="projectMaster.endDate" /></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="6%"><spring:message
													code="dashboard.lable.outlay" /></th>
											<th width="16%">Status</th>
											<!-- End of Columns -->

										</tr>
									</thead>
									<thead class="filters">
										<tr>
											<th width="2%"></th>
											<th width="14%"class="comboBox"><spring:message code="group.groupName" /></th>
											<th width="24%" class=""></th>
											<th width="14%" class="comboBox"><spring:message code="publicationdetail.Organization" /></th>
											<th width="7%"></th>
											<th width="8%"></th>
											<th width="9%"></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="6%"></th>
											<th width="16%" id="statusComboBox"></th>
											<!-- End of Columns -->

										</tr>
									</thead>
									<tbody class="">
									</tbody>
									<!--Bhavesh 28-07-23 added tfoot to count total  -->
										<tfoot>
											<tr >
												<td colspan="9">
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
<!-- added new class and added button by varun on 26-06-2023 -->
	<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
				id="dash-pending-payments-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
				<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.pendingpayments.label" />
							</h3>
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
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


											<th width="5%"><spring:message code="forms.serialNo" /></th>

											<th width="15%"><spring:message code="group.groupName" /></th>
											 <th scope="col" width="20%">Project Name</th>
										<%-- 	<th scope="col" width="20%"><spring:message
													code="project.projectRefNo" /></th> --%>
											<th width="15%"><spring:message
													code="client.name" /></th> 
													
											<th width="10%" scope="col"><spring:message
													code="Project_Invoice_Master.invoiceReferenceNumber" /></th>

											<th width="10%" scope="col"><spring:message
													code="Project_Invoice_Master.invoiceDate" /></th>
											<th width="10%" scope="col"><spring:message
													code="Project_Invoice_Master.invoiceStatus" /></th>
											<th width="10%" scope="col"><spring:message
													code="Project_Invoice_Master.invoiceAmount" /></th>
											<th width="10%" scope="col"><spring:message
													code="Project_Invoice_Master.invoiceTaxAmount" /></th>
											<th width="10%" scope="col"><spring:message
													code="Project_Invoice_Master.invoiceTotalAmount" /></th>
										</tr>
									</thead>
									
									<thead class="filters ">
										<tr>


											<th width="5%"></th>

											 <th  width="20%" class="comboBox"><spring:message code="group.groupName" /></th>
								<th class=""></th>
											<th width="15%" class="comboBox"><spring:message
													code="publicationdetail.Organization" /></th> 
													
											<th width="10%" scope="col"></th>

											<th width="10%" scope="col"></th>
											<th width="10%" scope="col"></th>
											<th width="10%" scope="col"></th>
											<th width="10%" scope="col"></th>
											<th width="10%" scope="col"></th>
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

	<div class="modal dash-pending-invoices-modal dash-modal dash-newProposalList-modal"
				id="dash-pending-invoices-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.pendinginvoices.label" />
							</h3>
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
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
			
			
								<label class="" for="from">Last:</label> <select class="months1" id="monthsDue" onchange="calculateDueDates()" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
								
								
							</div> 
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;right: 20%;top: 8px;">
							 <label class="font_16" for="dueInvoiceCalendarYearDropdown"><span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year</label>
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
											<th width="15%"><spring:message code="group.groupName" /></th>
											<th width="40%"><spring:message code="master.name" /></th>
										<%-- 	<th width="15%"><spring:message
													code="project.projectRefNo" /></th> --%>
											<th width="10%"><spring:message
													code="client.name" /></th> 
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
									
									<thead class="filters ">
								<tr>
									<th width="5%"></th>
									<th class="comboBox"><spring:message code="group.groupName" /></th>
											<th width="40%" class=""></th>
										
											<th width="10%" class="comboBox">><spring:message
													code="publicationdetail.Organization" /></th> 
											<th width="10%">
											</th>
											<th width="15%"></th>
											<th width="15%"></th>
											<th width="10%"></th>
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

<head>
				<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha384-******************" crossorigin="anonymous">

				</head>

	<!-- Modal for Employees -->
	<div class="modal dash-employee-modal dash-newProposalList-modal" id="dash-employee-modal"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg mymodal1" style="width:97%; height:93%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.employee.label" />
						in
						<sec:authentication property="principal.assignedOrganisationName" />
					</h3>
					<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>

				<div class="modal-body ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.total.label" />
							:<span class="orange">${employeeCount}</span>
						</h4>


					</div>
					<!-- Commented by devesh on 27/6/23 to add collapsed panel -->
					<%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16">
								<spring:message code="dashboard.employment.type.label" />
							</div>
							<div class="panel-body">
								<div id="chart-container-emp"></div>
							</div>
						</div>
					</div> --%>
					<div class="panel-group" id="accordionGroupClosed" role="tablist" aria-multiselectable="true">
					<!-- added by devesh on 27/6/23 for employee type graph -->
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info  " >
									<div class="panel-heading font_16" role="tab" id="headingTwo">
									
										<a class="collapsed" style="color:white;" data-toggle="collapse" href="#multiCollapse2"  aria-expanded="false" aria-controls="multiCollapse2"
											onclick="setTimeout(function() {drawEmploymentTypeWiseChart();}, 100)">
										<spring:message code="dashboard.employment.type.label" />  </a>	
												
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse2" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
										<div id="chart-container-emp"></div>
										</div>
									</div>
								</div>
								</div>
					<!-- End of employee type graph-->
					<!-- added by devesh on 9/6/23 for deputed graph -->
					<!-- Commented by devesh on 27/6/23 to add collapsed panel -->
					<%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class="panel panel-info  " >
						<div class="panel-heading font_16">
								<spring:message code="dashboard.gc.deputed.distribution.label" />  </a>			
						</div>
						<div class="panel-body">
							<div id="chart-container-emp2"></div>
					    </div>
					</div>
					</div> --%><div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info  " >
									<div class="panel-heading font_16" role="tab" id="headingTwo">
									
										<a class="collapsed" style="color:white;" data-toggle="collapse" href="#multiCollapse3"  aria-expanded="false" aria-controls="multiCollapse3"
											onclick="setTimeout(function() {drawdeputedChart();}, 100)">
										<spring:message code="dashboard.gc.deputed.distribution.label" />  </a>	
												
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse3" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
										<div id="chart-container-emp2"></div>
										</div>
									</div>
								</div>
								</div>
					<!-- End -->
					<!-- Commented by devesh on 27/6/23 to add collapsed panel -->
					<%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16">
								<spring:message code="dashboard.group.distribution.label" />
							</div>
							<div class="panel-body">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
									<div id="chart-container-emp1"></div>
								</div>
								<div
									class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
									<div id="group_emptype_div" class="">
										<table id="group_emptype_tbl"
											class="table table-responsive table-bordered">

										</table>
									</div>
								</div>
							</div>
						</div>
					</div> --%>
					<!-- Added by devesh on 27/6/23 to add collapse panel in Group Wise Distribution Table -->
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info  " >
									<div class="panel-heading font_16" role="tab" id="headingTwo">
									
										<a class="collapsed" style="color:white;" data-toggle="collapse" href="#multiCollapse1"  aria-expanded="false" aria-controls="multiCollapse1"
											>
										<spring:message code="dashboard.group.distribution.label" />  </a>	
												
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse1" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
										<div id="chart-container-emp1"></div>
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
											<div id="group_emptype_div" class="">
												<table id="group_emptype_tbl"
													class="table table-responsive table-bordered">

												</table>
											</div>
										</div>
										</div>
									</div>
								</div>
					</div>
					<!-- End of Group Wise Distribution Table-->
					</div>

					<%-- <div
						class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16">
								<spring:message code="dashboard.designation.distribution.label" />
							</div>
							<div class="panel-body">
								<div class="table-responsive">

									<table id="testTbl"
										class="table table-bordered table-striped mb-0">


										<c:forEach var="employeeCountByGroupDesignation"
											items="${employeeCountByGroupandDesignation}"
											varStatus="loop">

											<c:choose>
												<c:when test="${loop.index==0}">

													<tr class="my-custom-scrollbar-thead datatable_thead">
														<th class="bold font_12">
															${employeeCountByGroupDesignation.key}</th>
														<c:forEach var="counter"
															items="${employeeCountByGroupDesignation.value}">
															<th class="font_12" id="${counter}">${counter}</th>
														</c:forEach>

													</tr>

												</c:when>
												<c:otherwise>
													<tr>
														<td class="bold font_14">
															${employeeCountByGroupDesignation.key}</td>
														<c:forEach var="counter"
															items="${employeeCountByGroupDesignation.value}"
															varStatus="status">

															<c:choose>
																<c:when test="${not status.last}">
																	<td class="text-right emp"><a
																		title='Click here to view Employee details'>${counter}</a>
																	</td>
																</c:when>
																<c:otherwise>
																	<td class="text-right bold">${counter}</td>
																</c:otherwise>
															</c:choose>
														</c:forEach>

													</tr>
												</c:otherwise>
											</c:choose>


										</c:forEach>



									</table>
								</div>
							</div>
						</div>
					</div> --%>
					<!--Added by devesh on 21/6/23 for technical group -->
            		<div class="row">
                          <div
							class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
							<div class="panel panel-info  ">
								<div class="panel-heading font_16">
									<spring:message code="dashboard.designation.distribution.tech.label" />
								</div>
								<div class="panel-body">
	<div class="table-responsive">
		<table id="testTb2" class="table table-striped table-bordered mb-0" style="width: 100%">
			<c:forEach var="employeeCountByGroupDesignationTech" items="${employeeCountByGroupandDesignationTechnical}" varStatus="loop">

				<c:choose>
					<c:when test="${loop.index==0}">
                         <thead>
							<tr class="my-custom-scrollbar-thead datatable_thead">
								<!-- <th class="bold font_14" style="border: 1px solid #ddd;"> -->
								<th class="bold " style="border: 1px solid #ddd;font-size:11px;padding: 4px;"><!-- fontsize decreased on 27/6/23 -->
								${employeeCountByGroupDesignationTech.key}</th>
								<c:forEach var="counter"
								items="${employeeCountByGroupDesignationTech.value}">
								<!-- font size added on 27/6/23 -->
								<th id="${counter}" style="border: 1px solid #ddd;font-size:11px;padding: 4px;">${counter}</th>
								</c:forEach>

							</tr>
                         </thead>
					</c:when>
					
					<c:otherwise>
                       <c:if test="${loop.index==1}">
                          <tbody>
                            </c:if>
                             
                              <%-- <c:if test="${not loop.last}"> --%>
								<tr>
									<!-- <td class="bold font_14"> -->
									<td class="bold " style="font-size:11px;padding: 4px;"><!-- fontsize decreased on 27/6/23 -->
									${employeeCountByGroupDesignationTech.key}</td>
									<c:forEach var="counter"
								    items="${employeeCountByGroupDesignationTech.value}"
									varStatus="status">
                                   <c:if test="${employeeCountByGroupDesignationTech.key != 'Total'}">
									<c:choose>
										  <c:when test="${not status.last}">
											<td class="text-right emp"><a title='Click here to view Employee details' >${counter}</a> </td>
										 </c:when>
										 
										 <c:otherwise>
											<td class="text-right bold">${counter}</td>
										</c:otherwise>
									</c:choose>
								  </c:if>
								  <c:if test="${employeeCountByGroupDesignationTech.key == 'Total'}">
								  <td class="text-right bold">${counter}</td>
								   </c:if>
								  	
									</c:forEach>

								</tr>
							 
							 <c:if test="${loop.last}">
							    </tbody>
                               </c:if>
							 	
                           
					 </c:otherwise>
				   </c:choose>


		     </c:forEach>



		</table>
	</div>
   </div>
  </div>
 </div>
</div>
    
    <!-- technical group end -->
    
    
   <!-- Added by devesh on 21/6/23 for support group -->
            
			<div class="row">
                <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
					<div class="panel panel-info  ">
						<div class="panel-heading font_16">
							<spring:message code="dashboard.designation.distribution.support.label" />
						</div>
						<div class="panel-body">
                           <div class="table-responsive">
							<table id="testTbl" class="table table-striped table-bordered" style="width: 100%">
									
								<c:forEach var="employeeCountByGroupDesignation" items="${employeeCountByGroupandDesignationSupport}"
												varStatus="loop">

												<c:choose>
													<c:when test="${loop.index==0}">
                                                      <thead>
														<tr class="my-custom-scrollbar-thead datatable_thead">
															<!-- <th class="bold font_14" style="border: 1px solid #ddd;"> -->
															<th class="bold " style="border: 1px solid #ddd;font-size:11px;padding: 4px;"><!-- fontsize decreased on 27/6/23 -->
																${employeeCountByGroupDesignation.key}</th>
															<c:forEach var="counter"
																items="${employeeCountByGroupDesignation.value}">
																<!-- font size added on 27/6/23 -->
																<th id="${counter}" style="border: 1px solid #ddd;font-size:11px;padding: 4px;">${counter}</th>
															</c:forEach>

														</tr>
                                                      </thead>
													</c:when>
												 <c:otherwise>
                                                 <c:if test="${loop.index==1}">
                                                  <tbody>
                                                 </c:if>
													<tr>
													  <!-- <td class="bold font_14"> -->
													  <td class="bold " style="font-size:11px;padding: 4px;"><!-- fontsize decreased on 27/6/23 -->
														${employeeCountByGroupDesignation.key}</td>
														
														  <c:forEach var="counter"
																items="${employeeCountByGroupDesignation.value}"
																varStatus="status">
                                                             <c:if test="${employeeCountByGroupDesignation.key!='Total'}">
																<c:choose>
																	<c:when test="${not status.last}">
																		<td class="text-right emp"><a title='Click here to view Employee details' >${counter}</a> </td>
																	</c:when>
																	<c:otherwise>
																		<td class="text-right bold">${counter}</td>
																	</c:otherwise>
																</c:choose>
															  </c:if>	
														  <c:if test="${employeeCountByGroupDesignation.key == 'Total'}">
														   <td class="text-right bold">${counter}</td>
														  </c:if>	  
															</c:forEach>
														
														
														</tr>
                                              <c:if test="${loop.last}">
                                                 </tbody>
                                              </c:if>
													</c:otherwise>
												</c:choose>


											</c:forEach>



										</table>
									</div>
								</div>
							</div>
						</div>
			</div>
		
	<!-- support group end -->

					<!-- employee Details With Involvements -->
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<table id="employeeWithInvolvementsTbl"
							class="table table-striped" width="100%">
							<thead>
								<tr class="datatable_thead">
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<th width="15%"><spring:message
											code="dashboard.group.label" /></th>
									<th width="10%"><spring:message code="employee.EmployeeId" /></th>
									<th width="20%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="13%"><spring:message
											code="employee.designation" /></th>
									<th width="12%"><spring:message
											code="employee.Employment.type" /></th>
									<th width="12%"><spring:message code="employee.deputedat"/></th> <!--Deputed at Column added by devesh on 09/06/23  -->
									<%-- <th width="20%"><spring:message code="Project_Module_Master.projectName"/></th> --%>
								</tr>
							</thead>
							<thead class="filters">
								<tr>
									<th></th>
									<th class="comboBox"></th>
									<th></th>
									<th></th>
									<th class="comboBox"></th>
									<th class="comboBox"></th>
									<th class="comboBox"></th><!-- added by devesh on 14/6/23 for deputed filter -->
									<!--  <th></th>	 -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${employeeWithInvolvement}" var="employee"
									varStatus="theCount">
									<tr>
										<td>${theCount.count}</td>
										<td>${employee.groupName}</td>
										<td>${employee.employeeId}</td>
										<td>${employee.employeeName}<c:if
												test="${employee.projectCount >0}">
												<a class='btn btn-info white emp_info' data-toggle='modal'
													data-target='#empProjectDetails'
													data-whatever='${employee.encEmployeeId};${employee.employeeName}'><span>${employee.projectCount}</span></a>
											</c:if>
										</td>
										<td>${employee.strDesignation}</td>
										<td>${employee.employeeTypeName}</td>
										<!--added by devesh on 9/6/23 for deputed at value -->
										<td><c:choose>
											<c:when test="${employee.numDeputedAt==1}">CDAC</c:when>
											<c:when test="${employee.numDeputedAt==2}">Client</c:when>
											<c:otherwise>NULL</c:otherwise>
											</c:choose>
										</td>
										<!-- End -->
					
										<%-- <td>${employee.teamDetails}</td> --%>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- End of modal for employees -->




	<div class="modal in pad-7-Percentage" id="dash-employee-group-modal"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.employee.label" />
					</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<!-- <div class="modal-body "> -->
				<div class="modal-body dash-modal-body"><!-- dash-modal-body class added to add scroll to employees modal by devesh on 23-10-23 -->
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">



						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="empGroupName"></span>
						</h4>

					</div>
					<%-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
								<div class="panel panel-info  ">
									<div class="panel-heading font_16">
										<spring:message
											code="dashboard.designation.distribution.label" />
									</div>
									<div class="panel-body">
										<div class="table-responsive">

											<table id="testTbl"
												class="table table-bordered table-striped mb-0">


											</table>
										</div>
									</div>
								</div>
							</div> --%>
					<!-- employee Details With Involvements -->
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<table id="employeeWithInvolvementsTblNew"
							class="table table-striped">
							<thead>
								<tr class="datatable_thead bold ">
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									<%-- <th width="15%"><spring:message code="dashboard.group.label"/></th> --%>
									<th width="10%"><spring:message code="employee.EmployeeId" /></th>
									<th width="20%"><spring:message
											code="employee_Role.employee_Name" /></th>
									<th width="13%"><spring:message
											code="employee.designation" /></th>
									<th width="12%"><spring:message
											code="employee.Employment.type" /></th>
									<%-- <th width="40%"><spring:message code="Project_Module_Master.projectName"/></th> --%>
								</tr>
							</thead>

							<tbody>
								<%-- 	<c:forEach items="${employeeWithInvolvement}" var="employee" varStatus="theCount">
											<tr>
												<td>${theCount.count}</td>
												<td>${employee.employeeId}</td>
												<td>${employee.employeeName}</td>
												<td>${employee.strDesignation}</td>
												<td>${employee.employeeTypeName}</td>
												
											</tr>
										</c:forEach> --%>
							</tbody>
						</table>

					</div>

				</div>

			</div>
		</div>
	</div>



	<!-- Modal for Income -->
	
	<!-- added new class and added button by varun on 26-06-2023 -->
<div class="modal dash-income-modal dash-newProposalList-modal" id="dash-income-modal"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;"> 
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.income.label" />
						in
						<sec:authentication property="principal.assignedOrganisationName" />
					</h3>
					<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>

				<div class="modal-body ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.income.label" />
							since <span class="orange asOnDateProposals1"  id="asOnDate1"></span>
						</h4>

					</div>
					
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label>
								 <select class="months1" id="months" onchange="calculateDates(this.value,'incomefrom','incometo','go-btn2',0)" class="inline form-control" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			                <!--  Bhavesh (18-10-23) hided custom Duration -->
							<!-- <div style="text-align:right;olor: #1578c2;">
							<a href="javascript:void()" onclick="openIncomeAnotherFilter()" id="incomCustom">
							<i>Custom Duration</i></a></div> -->
							</div>
							<div id="incomeAnotherFilter">
							<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">

								<label class="font_16" for="from">From</label> <input class="fromProposal1" type="text"
									id="incomefrom" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1" type="text" id="incometo"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn2">Go</button>
							</div>
							</div>
							</div>
					
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16">
								<spring:message code="dashboard.income.distribution.label" />
							</div>
							<div class="panel-body ">
								<div id="chart-container-income"
									style="width: 100%; height: 100%;" align='center'></div>
							</div>
						</div>
					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16"></div>
							<div class="panel-body">
								<div id="col-md-12">
									<table id="income_dataTable"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>

												<th><spring:message code="dashboard.group.label" /></th>
												<th><spring:message code="dashboard.project.label" /></th>
												<%-- <th><spring:message code="project.projectRefNo" /></th> --%>
												<th><spring:message code="dashboard.income.label" /></th>
											</tr>
										</thead>
										<%-- <thead class="filters ">
											<tr>
												<th class="comboBox" id="incomeSelect"><spring:message
														code="dashboard.group.label" /></th>
												<th class="">
													<spring:message code="project.projectRefNo" />
												</th>
												<!-- <th class=""> -->
												<spring:message code="dashboard.project.label" />
												</th>
												<th class="">
													<spring:message code="dashboard.income.label" />
												</th>

											</tr>
										</thead> --%>
										<tbody class="">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>


	<div class="modal in pad-7-Percentage " id="dash-incomeByGroup-modal"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.projectwise.income" />
					</h3>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.income.label" />
							since <span class="orange" id="asOnDateNew1"></span>
						</h4>
						<h4 class="pad-bottom">
							<spring:message code="group.groupName" />
							: <span class="orange" id="incomeGroupName"></span>
						</h4>

					</div>
					<div
						class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

						<label class="" for="from">From</label> <input type="text"
							id="incomefromNew" name="from2" readonly /> <label
							class="pad-left" for="to">To</label> <input type="text"
							id="incometoNew" name="to2" readonly /> <input type="hidden"
							id="incomeGroupId">
						<button type="button" class="btn btn-success go-btn2-New">Go</button>

					</div>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div id="col-md-12">
							<table id="incomeByGroup_dataTable"
								class="table table-striped table-bordered" style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>

										<%-- 	<th><spring:message code="dashboard.group.label" /></th> --%>
										<th><spring:message code="dashboard.project.label" /></th>
										<%-- 	<th><spring:message code="project.projectRefNo" /></th> --%>
										<th><spring:message code="dashboard.income.label" /></th>

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

	<%-- <div class="modal dash-incomeByGroup-modal" id="dash-incomeByGroup-modal" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id=""> <spring:message code="dashboard.income.label" /> in <sec:authentication
							property="principal.assignedOrganisationName" /></h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>
						
						<div class="modal-body ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						
						<h4 class="pad-bottom"><spring:message code="dashboard.income.label" /> since <span class="orange" id="asOnDate1"></span></h4>
						
						</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
					<div class="panel-heading font_16"><spring:message code="dashboard.income.distribution.label" /> </div>
					<div class="panel-body ">
							<div id="chart-container-income" style="width:100%; height:100%;" align='center'>
							
							</div>
							</div>
							</div>
							</div>
							
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
					<div class="panel-heading font_16"> </div>
					<div class="panel-body">
							<div id="col-md-12">
							<table id="incomeByGroup_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										
										<th><spring:message code="dashboard.group.label" /></th>
										<th><spring:message code="dashboard.project.label" /></th>
										<th><spring:message code="project.projectRefNo" /></th>
										<th><spring:message code="dashboard.income.label" /></th>
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
			</div>
</div>
 --%>
	<!-- End of modal for income -->

	<!-- Modal for Expenditure -->
	<!-- added new class and added button by varun on 26-06-2023 -->
	<div class="modal dash-expenditure-modal dash-newProposalList-modal" id="dash-expenditure-modal"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;"> 
				<div class="modal-header">
					<h3 class="modal-title center" id="">
						<spring:message code="dashboard.expenditure.label" />
						in
						<sec:authentication property="principal.assignedOrganisationName" />
					</h3>
					<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
				</div>

				<div class="modal-body ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<h4 class="pad-bottom">
							<spring:message code="dashboard.expenditure.label" />
							since <span class="orange" id="asOnDateExp1"></span>
						</h4>

					</div>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16">
								<spring:message code="dashboard.expenditure.distribution.label" />
							</div>
							<div class="panel-body">
								<div id="chart-container-expenditure"
									style="width: 100%; height: 100%;" align='center'></div>
							</div>
						</div>
					</div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<div class="panel panel-info  ">
							<div class="panel-heading font_16"></div>
							<div class="panel-body">
								<div id="col-md-12">
									<table id="expenditure_dataTable"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>

												<th><spring:message code="dashboard.group.label" /></th>
												<th><spring:message code="dashboard.project.label" /></th>
												<th><spring:message code="dashboard.expenditure.label" /></th>
											</tr>
										</thead>
										<thead class="filters ">
											<tr>
												<th class="comboBox" id="expenditureSelect"><spring:message
														code="dashboard.group.label" /></th>
												<th class="">
													<%-- <spring:message code="dashboard.project.label" /> --%>
												</th>
												<th class="">
													<%-- <spring:message code="dashboard.expenditure.label" /> --%>
												</th>

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
		</div>
	</div>
	<!-- End of modal for Expenditure -->



	<!-- Modal For Emp Details -->
	<div class="modal amount_receive_deatil_modal " id="empDetailModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Employee
						Detail</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body emp_detail_modal">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<table id="datatable1"
							class="table table-striped table-bordered example_det"
							style="width: 100%">
							<thead class="datatable_thead bold">
								<tr>
									<th>S.No</th>
									<th>Employee Id</th>
									<th>Employee Name</th>

								</tr>
							</thead>
							<tbody class="center">

							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- End of Modal For Emp Details -->

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
							<u>Contact Person (For Funding Org)</u>
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
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
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
	<!-- Modal for Amount -->
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

	<!-- End of Modal for Amount -->

	</div>


	<div class="modal dash-newProjectList-modal dash-modal" id="allDetails"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title center" id="">All Details</h3>

				</div>
				<div class="card-footer">
					<div class="stats">
						<span> <span class="pad-top">since:</span> <span
							class="bold" id="asOnDateDetails"
							style="font-size: 14px; color: #4d4b4b;">${startRange} </span>
						</span>

					</div>
					<div class="modal-body dash-modal-body">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

							<h4 class="pad-bottom">
								Details since : <span class="orange" id="asOnDateDetails1"></span>
							</h4>

						</div>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

							<label class="" for="from">From</label> <input type="text"
								id="fromDet" name="from" readonly /> <label class="" for="to">To</label>
							<input type="text" id="toDet" name="to" readonly /> &nbsp;
							&nbsp;
							<button type="button" class="btn btn-success go-btn-det">Go</button>
							<input type="hidden" id="fromDetNew"> <input
								type="hidden" id="fromDetailNew" value="${startRange}">
							<input type="hidden" id="toDetailNew">
						</div>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

							<table id="" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th width="15%">Department</th>
										<th>Proposals Submitted</th>
										<th>Projects Received</th>
										<th>Projects Closed</th>
										<th>New Joinees</th>
										<!-- <th>Resigned Employees</th> -->
										<th>Relieved Employees</th><!-- Name Modified by devesh on 19-10-23 -->
										<th>Employees</th>
										<th>Income</th>
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
</section>

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
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6" id="employeeName"></div>
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
					<span aria-hidden="true"> </span><span class="sr-only">Close</span>

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
								<thead class="filters">
									<tr>
										<th width="5%"></th>
										<th width="10%" style="width: 50px;" class="comboBox"><spring:message
												code="group.groupName" /></th>
										<th width="35%"></th>
										<th width="18%"></th>
										<th width="10%"></th>
										<th width="8%"></th>
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
						<tr class="${row_color}">
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
										<td colspan="6">
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
					<span aria-hidden="true"> </span><span class="sr-only">Close</span>

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
								<thead class="filters">
									<tr>
										<th width="5%"></th>
										<th width="10%" style="width: 50px;" class="comboBox"><spring:message
												code="group.groupName" /></th>
										<th width="35%"></th>
										<th width="18%"></th>
										<th width="10%"></th>
										<th width="8%"></th>
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
										<tr class="${row_color }">
											<td>${theCount.count}</td>
											<td>${milestoneData.groupName}</td>
											<td><a class="font_14 ${row_color }"
												title="Click to View Complete Information"
												onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a>
											<p class="font_14 ${row_color}">${milestoneData.clientName}</p>
												<p class="bold font_14 text-left ${row_color}">${milestoneData.strProjectReference}</p></td>
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
										<td colspan="6">
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
<%-------------- EOL Get data of closure projects and pending for closure [29-08-2023]   ------------------%>

	<!--Bhavesh(22-09-23) Modal for under Closure Projects for Finance -->
			<div class="modal dash-closure-pending-modal3 dash-modal"
				id="dash-closure-pending-modal3" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myPending3"
					style="width: 99%; height: 95%;">
					<div class="modal-content pendings" id="myContentPending"
						style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">Pending For Financial Closure</h3><%---- Modal Label Change (PRS 189) [11-03-2024] --%>
							<button type="button" class="close" onclick="restorepending3()"
								data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>

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
							<%--------- Get the Months fields for filters [12-10-2023] ----------%>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
									<label class="font_16" for="from">Last:</label> <select
										id="months"
										onchange="calculateDates(this.value,'fromFinanceClosurePending','toFinanceClosurePending','go-btn-FinanceClosurePending',0)"
										class="inline form-control financeClosurePending" style="width: 80%">
										<option value="0">Select Months/Year's</option>
										<option value="3">3 Months</option>
										<option value="6">6 Months</option>
										<option value="12">1 Years</option>
										<option value="24">2 Years</option>
									</select>
								</div>
								<div id="closedAnotherFilter">
									<div class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom "
										style="padding-top: 4px;">
										<h3 style="color: #1578c2;">
											<strong>OR</strong>
										</h3>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
										<label class="font_16" for="from">From</label> <input
											type="text" id="fromFinanceClosurePending" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											id="toFinanceClosurePending" name="to" readonly /> &nbsp; &nbsp;
										<button type="button" class="btn btn-success go-btn-FinanceClosurePending pad-left">Go</button>
									</div>
								</div>
							</div>
							<%--------- End of fields for filters [12-10-2023] ----------%>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<fieldset class="fieldset-border">
								<!--  //Bhavresh(01=05-2024) action for pending at finance -->
							<input type="hidden" value="${encWorkflowId_Closure}" id="encWorkflowId_Closure"/>
									<table id="exampleUnderClosureFinance"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>
												<th style="width: 2%;"><spring:message code="serial.no" /></th>
												<th style="width: 8%;"><spring:message
														code="group.groupName" /></th>
												<th style="width: 15%;"><spring:message
														code="Project_Module_Master.projectName" /></th>
												<th width="15%"><spring:message
														code="client.name" /></th>
												<th><spring:message code="project.details.leader" /></th>
												<th><spring:message code="employee_Role.start_Date" /></th>
												<th><spring:message code="employee_Role.end_Date" /></th>
												<th style="width: 5%;"><p>Actual</p>
													<p>
														<spring:message code="project.details.duration" />
													</p>
													<p>(In months)</p></th>
												<th style="width: 5%;"><p>
														<spring:message code="centre.outlay" />
													</p>
													<p>(INR)</p></th>
												<th style="width: 5%;"><p>Payment Received</p>
													<p>(INR)</p></th>
												<th style="width: 5%;"><p>Payment Pending</p>
													<p>(INR)</p></th>
													<!--  //Bhavresh(01=05-2024) action for pending at finance -->
											<th width="3%"><spring:message code="forms.action"/> </th>
											</tr>
										</thead>
										<thead class="filters">
											<tr>
												<th></th>
												<th class="comboBox"><spring:message
														code="group.groupName" /></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<%-- <c:forEach items="${underClosureCount1}" var="projectdetail"
												varStatus="loop">
												------------------------  SET Row color according to Received Amount [12-10-2023] -------
												<c:choose>
												    <c:when test="${empty projectdetail.rowColor}">
												        <c:set var="rowcolor" value="#993300" />
												    </c:when>
												    <c:otherwise>
												        <c:set var="rowcolor" value="${projectdetail.rowColor}" />
												    </c:otherwise>
												</c:choose>

												<tr style="color:${rowcolor}">
													<td style="width: 2%;">${loop.count}</td>
													<td>${projectdetail.strGroupName}</td>
													<td class="font_16" style="width: 15%;"
														id="td2_${projectdetail.encProjectId}"><p>
															<a style="color:${rowcolor}" class="bold" title='Click to View Complete Information'
																onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')"><span>${projectdetail.strProjectName}</span></a>
														</p></td>
													<td class="font_16" style="width: 15%;"
														id="td2_${projectdetail.encProjectId}">
														<p class="font_14 ">${projectdetail.clientName}</p>
													</td>
													<td style="width: 10%;"><span class="font_16 ">${projectdetail.strPLName}</span></td>
													<td style="width: 5%;">${projectdetail.startDate}</td>
													<td style="width: 5%;">${projectdetail.endDate}</td>
													<td align="center">${projectdetail.projectDuration}</td>
													<td align="left" class=""><span>${projectdetail.strTotalCost}
													</span></td>
													<td><span>${projectdetail.numReceivedAmountInr}</span></td>
													<td>${projectdetail.numReceivedAmountTemp}</td>
												</tr>
											</c:forEach> --%>
										</tbody>
										<tfoot>
											<tr>
												<td colspan="12">
													<div class="legend">
														<div class="legend-item">
															<div class="legend-color" style="background-color: #993300;"></div>
															<span>Pending for Financial Closure </span>
														</div>
														<div class="legend-item">
															<div class="legend-color" style="background-color: red;"></div>
															<span>No Payment Received </span>
														</div>
														<!-- Add more legend items as needed -->
													</div>
												</td>
											</tr>
										</tfoot>
										<%-----------------------------------  END Of Table [12-10-2023] ----------------------------%>
									</table>
									<!--End of data table-->
								</fieldset>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- End of modal for Pending Closure -->
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
							View Project Document Details
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
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3">
								<label class="font_16" for="groupName">Group Name :</label> 
								<select id="groupNameForDocumentTile"
									class="form-control" onChange="getProject()">
									<option value="0">Select</option>
									<c:forEach var="groupNameForDocument" items="${groupnames}">
										<c:if test="${groupNameForDocument.showProjects eq 1}">
											<option value="${groupNameForDocument.numId}">${groupNameForDocument.groupName}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
								<label class="font_16" for="projectName">Project Name :</label> 
								<select id="projectNameForDocumentTile"
									class="form-select select2Option" multiple="false" name="projectName" onChange="getProjectDocumentData()">
								</select>
							</div>
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3">
							    <label class="font_16" for="projectDocumentTypeName">Document Type Name :</label> 
							    <select id="projectDocumentTypeForDocumentTile" class="form-select select2Option" name="projectDocumentTypeName" onChange="getProjectDocumentData()" multiple>
							        <c:forEach var="documents" items="${documentTypeList}">
							            <c:forEach items="${documents.value}" var="document">
							                <c:if test="${document.numId == 1 || document.numId == 2 || document.numId == 3 || document.numId == 48}">
							                    <option value="${document.numId}">${document.docTypeName}</option>
							                </c:if>
							            </c:forEach>
							        </c:forEach>
							    </select>
							</div>
							<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">						  
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
		<%---------------   Add Modal For View Proceeding Action in Closure Request Tile [21-09-2023] ------%>	
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
	<%---------------   END of View Proceeding Action in Closure Request Tile------%>	
		

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

<script
	src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/edDashboard.js"></script>

<!-- Proposals submitted in last 3 years group wise -->
<!-- <script type="text/javascript">
			google.charts.load('current', {
				'packages' : [ 'bar' ]
			});
			google.charts.setOnLoadCallback(drawChart);

			function drawChart() {
				
				countProposals = ${proposalGraphData}	;
				/* console.log(countProposals); */
				var colors = (countProposals[1]);
				countProposals.splice(1,1);
				/* console.log(countProposals); */
				 var data = google.visualization
						.arrayToDataTable(countProposals); 

				
				var options = {

						bar : {
							groupWidth : '30%'
						},
						colors : colors,
					
				};
				
					

				var chart = new google.charts.Bar(document
						.getElementById('columnchart_proposals'));

				chart.draw(data, google.charts.Bar.convertOptions(options));

			}
		</script>


			
			Projects received in last 3 years group wise
		<script type="text/javascript">
			google.charts.load('current', {
				'packages' : [ 'bar' ]
			});
			google.charts.setOnLoadCallback(drawChart);

			function drawChart() {
				
				countbarGraph = ${projcountbarGraphData}	;
				//console.log(countbarGraph);
				var colors = (countbarGraph[1]);
				countbarGraph.splice(1,1);
				//console.log(countbarGraph);
				 var data = google.visualization
						.arrayToDataTable(countbarGraph); 

				
				var options = {

						bar : {
							groupWidth : '30%'
						},
						colors : colors,
					
				};
				
					

				var chart = new google.charts.Bar(document
						.getElementById('columnchart_material'));

				chart.draw(data, google.charts.Bar.convertOptions(options));

			}
		</script>

 -->
<!--End of total projects  -->

<!--Number of employees in CDAC-Noida based on employement type  -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	
	// Changes and visible the pie chart on 24-05-23
	/* google.charts.setOnLoadCallback(drawChart);
	function drawChart() { */
	//name of funtion updated to call on click employee tile by devesh on 16/6/23
	google.charts.setOnLoadCallback(drawEmploymentTypeWiseChart);
	function drawEmploymentTypeWiseChart() {
		employmentTypeWiseCount = ${employmentTypeWiseCount};
		var data = google.visualization
				.arrayToDataTable(employmentTypeWiseCount);
		var options = {
			is3D : true,
			width : 800,
			height : 300,
			chartArea : {
				width : 400,
				height : 200
			},
			pieSliceTextStyle : {
				color : 'white',
				//fontSize : 15,
				fontSize : 11,//font size updated on 16/6/23
			},
			legend : {
				textStyle : {
					fontSize : 14
				}
			},
			pieSliceText : 'value',
			colors : [ '#b36686', '#532aa1', '#90a603', '#3fbbc4' ],
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-emp'));

		function selectHandler() {
			var selectedItem = chart.getSelection()[0];
			if (selectedItem) {
				var empType = data.getValue(selectedItem.row, 0);
				filterTable(empType);
			}
		}
		google.visualization.events.addListener(chart, 'select', selectHandler);

		chart.draw(data, options);
	}
</script>

<!--End of Number of empoyees in CDAC-Noida based on employee type  -->


<!-- Employees in CDAC Noida employee type wise-->
<script type="text/javascript">
	//console.log('Employee Bar Graph Data');
	/* console.log('${empbarGraphData}'); */
	//console.log('**************************');
	google.charts.load('current', {
		packages : [ 'corechart', 'bar' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		barGraphEmployees = ${empbarGraphData};
		var data = google.visualization.arrayToDataTable(barGraphEmployees);
		var view = new google.visualization.DataView(data);
		view.setColumns([ 0, 1, {
			calc : "stringify",
			sourceColumn : 1,
			type : "string",
			role : "annotation"
		}, 2, {
			calc : "stringify",
			sourceColumn : 2,
			type : "string",
			role : "annotation"
		}, 3, {
			calc : "stringify",
			sourceColumn : 3,
			type : "string",
			role : "annotation"
		}, 4, {
			calc : "stringify",
			sourceColumn : 4,
			type : "string",
			role : "annotation"
		} ]);

		/* label changes on 23-05-23 */
		
		var options = {

			/* width : 1150,
			height : 1400, *///commented on 28/6/23 
			//height: 600 
			/* Updated by devesh on 28/6/23 to reduce the dimentions of graph */
			width : 920,
			height : 1250,
			/* End of height and width */
			
			/* Added by devesh on 28/6/23 to reduce the size of graph */
			chartArea: {
				width: '60%', // Adjust the width of the chart area
			    height: '60%' // Adjust the height of the chart area
			},
			/* End of height and width */						

			legend : {
				position : 'right',

				fontSize : 12
			},
			hAxis : {
				showTextEvery : 1,
				slantedText : true,
				//slantedTextAngle : 40,//commented on 28/6/23 
				/* Updated by devesh on 28/6/23 to change label font and avoid overlapping */
				slantedTextAngle : 60,
				textStyle: {
					fontSize: 12 // Adjust the font size of the x-axis labels
				},
				//end

			},

			vAxis : {
				title : 'Number of Employees'
			},

			bar : {
				groupWidth : '60%'
			},
			isStacked : true,

			colors : [ '#b36686', '#532aa1', '#90a603', '#3fbbc4' ],
		};

		var chart = new google.visualization.ColumnChart(document
				.getElementById('chart-container-emp1'));
		chart.draw(view, options);

		google.visualization.events.addListener(chart, 'select', selectHandler);

		function selectHandler() {
			var selection = chart.getSelection();
			var message = '';
			for (var i = 0; i < selection.length; i++) {
				var item = selection[i];
				if (item.row != null && item.column != null) {
					var str = data.getFormattedValue(item.row, item.column);
					var category = data
							.getValue(chart.getSelection()[0].row, 0)
					var type
					if (item.column == 1) {
						type = " Consolidated Contract";
					} else if (item.column == 2) {
						type = "Regular";
					} else if (item.column == 3) {
						type = "Third Party";
					} else if (item.column == 4) {

						type = "Grade Based Contract";
					}
					message += '{row:' + item.row + ',column:' + item.column
							+ '} = ' + str + '  The Department is:' + category
							+ ' and EmployeeType is  : ' + type + '\n';
				} else if (item.row != null) {
					var str = data.getFormattedValue(item.row, 0);
					message += '{row:' + item.row
							+ ', column:none}; value (col 0) = ' + str
							+ '  The Category is:' + category + '\n';
				} else if (item.column != null) {
					var str = data.getFormattedValue(0, item.column);
					message += '{row:none, column:' + item.column
							+ '}; value (row 0) = ' + str
							+ '  The Category is:' + category + '\n';
				}
			}
			if (message == '') {
				message = 'nothing';
			}
			alert('You selected ' + message);

		}
	}

	$(window).resize(function() {
		drawChart();
	});
</script>

<!--Deputed wise Employees added by devesh on 9/6/23 -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawdeputedChart);  
	
	
	function drawdeputedChart() {
		barGraphEmployees = ${deputedWiseCount};
	
		var data = google.visualization
				.arrayToDataTable(barGraphEmployees);
		var count = data.getNumberOfRows();
		var values = Array(count).fill().map(function(v, i) {
			return data.getValue(i, 1);
		});
		var total = google.visualization.data.sum(values);
		// console.log(total);
		values.forEach(function(v, i) {
			var key = data.getValue(i, 0);
			// console.log(key);
			var val = data.getValue(i, 1);
			//console.log(val);
			data.setFormattedValue(i, 0, key + ' (' + (val) + ')');
		});
		var options = {
			is3D : true,
			width : 800,
			height : 300,
			chartArea : {
				width : 400,
				height : 200
			},
			sliceVisibilityThreshold:0,

			pieSliceText : 'value',
			pieSliceTextStyle : {
				color : 'white',
				fontSize : 14,
			},
			legend : {
				text : 'value',
				position : 'right',
				textStyle : {
					fontSize : 14,
					color : 'red'
				}
			},

			colors : [ '#b36686', '#532aa1', '#90a603', '#3fbbc4' ],
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-emp2'));
		
		 function selectHandler() {
	          var selectedItem = chart.getSelection()[0];
	          if (selectedItem) {
	            var empType = data.getValue(selectedItem.row, 0);	          
	            filterTable(empType);
	         }
	        }
		 google.visualization.events.addListener(chart, 'select', selectHandler);	
		chart.draw(data, options);
	}
	
</script>
<!--End of Deputed wise Employees  -->

<!-- Income -->
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	/* google.charts.setOnLoadCallback(initialzeIncomeChart()); */

	function drawIncomeChart() {

		var incomePieChart = '';
		var colors = '';

		var startDate = $('#asOnDate').text();
		$('#asOnDate1').text(startDate);

		$.ajax({
			type : "POST",
			url : "/PMS/mst/getIncomeByDateGraph",
			data : "startDate=" + startDate,
			async : false,
			success : function(response) {
				incomePieChart = response;

				var temp = response.length - 1;
				colors = (incomePieChart[temp]);
				incomePieChart.splice(temp, temp);

			}

		});

		var data = google.visualization.arrayToDataTable(incomePieChart);

		var options = {

			titleTextStyle : {

				fontName : 'Calibri',
				fontSize : 16,

			},

			width : 900,
			height : 300,

			legend : {
				textStyle : {
					fontSize : 14
				}
			},
			pieSliceTextStyle : {
				color : 'black'
			},
			pieSliceText : 'none',
			colors : colors

		};

		var formatter = new google.visualization.NumberFormat({
			negativeColor : 'black',
			negativeParens : true,
			pattern : '#,##,##,###.##'
		});
		formatter.format(data, 1);

		// Create and draw the visualization.
		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-income'));

		// var chart = new google.visualization.PieChart(document.getElementById('chart-container-income'));
		// Instantiate and draw our chart, passing in some options.

		function selectHandler() {
			var selectedItem = chart.getSelection()[0];
			if (selectedItem) {
				var group = data.getValue(selectedItem.row, 0);
				// console.log('The user selected ' + group);

				/*  var table_row = $('#income_dataTable thead  tr th').eq(0).prevObject;
				 var abc = table_row[3];
				 console.log(abc); */

				$('#incomeSelect select').val(group).trigger('change');

			}
		}

		google.visualization.events.addListener(chart, 'select', selectHandler);

		chart.draw(data, options);
	}

	function initialzeIncomeChart() {

		drawIncomeChart();
		incomeTable();
	}
</script>




<!-- Expenditure -->
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	/* google.charts.setOnLoadCallback(initialzeIncomeChart()); */

	function drawExpenditureChart() {

		var expenditurePieChart = '';
		var colors = '';

		var startDate = $('#asOnDateExp').text();
		$('#asOnDateExp1').text(startDate);

		$.ajax({
			type : "POST",
			url : "/PMS/mst/getExpenditureByDateGraph",
			data : "startDate=" + startDate,
			async : false,
			success : function(response) {
				//console.log(response);
				//console.log(expenditurePieChart);

				expenditurePieChart = response;
				colors = (expenditurePieChart[1]);
				expenditurePieChart.splice(1, 1);

			}

		});

		// console.log(expenditurePieChart)

		var data = google.visualization.arrayToDataTable(expenditurePieChart);

		var options = {

			titleTextStyle : {

				fontName : 'Calibri',
				fontSize : 16,

			},

			width : 900,
			height : 300,

			legend : {
				textStyle : {
					fontSize : 14
				}
			},
			pieSliceTextStyle : {
				color : 'black'
			},
			pieSliceText : 'none',
			colors : colors

		};

		var formatter = new google.visualization.NumberFormat({
			negativeColor : 'red',
			negativeParens : true,
			pattern : '#,##,##,##,###.##'
		});
		formatter.format(data, 1);

		// Create and draw the visualization.
		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-expenditure'));

		// Instantiate and draw our chart, passing in some options.

		function selectHandler() {
			var selectedItem = chart.getSelection()[0];
			if (selectedItem) {
				var group = data.getValue(selectedItem.row, 0);
				// console.log('The user selected ' + group);

				/*  var table_row = $('#income_dataTable thead  tr th').eq(0).prevObject;
				 var abc = table_row[3];
				 console.log(abc); */

				$('#expenditureSelect select').val(group).trigger('change');

			}
		}

		google.visualization.events.addListener(chart, 'select', selectHandler);

		chart.draw(data, options);
	}

	function initialzeExpenditureChart() {

		drawExpenditureChart();
		expenditureTable();

	}
</script>
<script>
	$(document).ready(function() {
		groupEmpTypeTable();
		groupEmpTypeTableTotal();
	});

	function groupEmpTypeTable() {
		var barGraphEmployees = ${empbarGraphData};
		var tableData = '';
		var noOfColumn = 0;
		for (var i = 0; i < barGraphEmployees.length; i++) {
			var rowData = barGraphEmployees[i];
			var tableRow = "<tr> ";
			var totalOfRow = 0;
			for (var j = 0; j < rowData.length; j++) {
				if (i == 0) {
					noOfColumn = rowData.length;
					if (j == 0) {
						tableRow += "<th width='40%'>" + rowData[j] + "</th>";
					} else {
						tableRow += "<th width='10%'>" + rowData[j] + "</th>";
					}
				} else {
					if (j == 0) {
						tableRow += "<th width='40%'>" + rowData[j] + "</th>";
					} else {
						totalOfRow += rowData[j];
						tableRow += "<td width='10%' class='text-right col_"
								+ j + "'>" + rowData[j] + "</td>";
					}

				}
				if (j == rowData.length - 1) {
					if (i == 0) {
						tableRow += "<th width='10%' class='blue'> Total </th> </tr>";
					} else {
						tableRow += "<th width='10%' class='blue text-right col_"
								+ noOfColumn
								+ "'>"
								+ totalOfRow
								+ "</th> </tr>";
					}
				}
			}
			tableData += tableRow;
		}

		$('#group_emptype_tbl').empty();
		$('#group_emptype_tbl').append(tableData);
	}

	function groupEmpTypeTableTotal() {
		var columns = $($('#group_emptype_tbl  tr')[0]).find('th').length;

		var totalRow = '<tr> <th>Total </th>';

		for (var i = 1; i < columns; i++) {
			var columnTotal = 0;
			$(".col_" + i).each(
					function() {
						//console.log($(this).text());
						columnTotal = parseInt(columnTotal)
								+ parseInt($(this).text().trim());
					});
			totalRow += "<th class='blue text-right'>" + columnTotal + "</th>";
		}
		totalRow += "</tr>";
		$('#group_emptype_tbl').append(totalRow);

	}
</script>

<script type="text/javascript">
	function initializePendingPayments() {

		pendingPaymentsTable();

	}
</script>
<script type="text/javascript">
	function initializePendingInvoices() {
		pendingInvoicesTable();
	}
</script>
<script>

 $(document).ready(function() {
     // Function to check screen size on document ready
     function checkScreenSize() {
       var screenWidth = window.innerWidth;

       if (screenWidth >= 1440) {
         $(".my-div").addClass("hidden");
         // Code to execute if the screen width is 1440 pixels or larger (large monitor)
         /* alert("Large Monitor"); */
       } else {
         // Code to execute if the screen width is less than 1440 pixels (laptop or smaller screens)
         /* alert("Laptop or Smaller Screen"); */
       }
     }

     // Call the function immediately after the document is fully loaded
     checkScreenSize();
     
     // You can also call the function on window resize if you want to check screen size dynamically
     $(window).resize(checkScreenSize);
   });
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
	  //console.log("year change ");
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
		 
			$(".months").val(0) ;
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

		
	

</script>
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

/* added by devesh on 28/6/23 for minus sign on panel expand */
.panel-group .panel-heading a:after {
  content: '-';
  float: right;
}
/* End */
/* added by devesh on 28/6/23 for plus sign on panel collapse */
.panel-group .panel-heading a.collapsed:after {
  content: '+';
}
/* End */
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

/* added below style by varun for financialy validation tiles  */
.small-text {
    font-size: 14px; 
}

.small-texch{
font-size: 14px; 
}

.my-div {
    padding-bottom: 20px; /* Adjust the value to set the desired amount of blank space */
  }
/* Added by devesh on 09/08/23 to colour the changed value in proposal log table */
.different-value {
    color: red; /* Change this to the desired color */
}
/* End of proposal log colour */
/* Added by devesh on 20-10-23 to fix table info font-size */
#resigned_dataTable_info,#group_dataTable_info {
    font-size: 1.375rem;
}
/* End of info fontsize */

/*-----  Style for Document Details Tile [18-03-2024] -----*/
.serviceBox-Document:before {
    content: "";
    background: #58afdf;
    width: 100%;
    height: calc(100% - 30px);
    border: 8px solid #0288d1;
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: -1;
}
.serviceBox-Document{
    font-family: 'Noto Sans', sans-serif;
    text-align: center;
    padding: 0 20px 4px;
    position: relative;
    z-index: 1;
}
.serviceBox-Document .service-icon{
    color: #c2005a;
    background: linear-gradient(135deg,#e7e7e7,#fff,#fff);
    font-size: 40px;
    line-height: 60px;
    width: 60px;
    height: 60px;
    margin: 0 auto 10px;
    border-radius: 50%;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.5);
    padding-top: 5px;
    border: 4px solid #f14293;
}
.serviceBox-Document .service-icon i{
	font-size:40px;
	}
.serviceBox-Document .title{
    color: #fff;
    font-size: 17px;
    font-weight: 600;
    text-transform: capitalize;
    margin: 0 0 7px;
}
.serviceBox-Document .card-title{
    color: #fff;
    font-size: 18px;
    font-weight: 600;
}
/*-----  End of Style for Document Details Tile [18-03-2024] -----*/
</style>