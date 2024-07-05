
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
<!-- Bhavesh(23-06-2023) added stylesheet link  --> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha384-******************" crossorigin="anonymous">
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>
<style>
/*  .date-bar .btn-success {
    box-shadow:0 0 0 1px #5cb85c inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #4cae4c, 0 8px 0 1px rgba(0,0,0,0.4), 0 8px 8px 1px rgba(0,0,0,0.5) !important;
    background-color:#5cb85c;
}
.btn3d {
    transition:all .08s linear;
    position:relative;
    outline:medium none;
    -moz-outline-style:none;
  /*  padding: 10px; */
/* padding:1px 6px !important; 
}
.btn3d:focus {
	outline: medium none;
	-moz-outline-style: none;
}
*/

.month-picker{
z-index : 10000 !important;
left: 15%;
position: relative;
width: max-content;
}


.panel-group .panel-heading a:after {
  content: '-';
  float: right;
}

.panel-group .panel-heading a.collapsed:after {
  content: '+';
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
/*---- New Color for table of reconciled projects [06-03-2024]  */
.reconciled-color {
	color:#009999;
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

<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/bootstrap-dropdownhover.css" />

<style>
.btn.dropdown-toggle:after, .nav-link.dropdown-toggle:after{
     content: none !important; 
}
.btn-secondary{
padding: 12px 12px !important;
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





		<div class="gc_dashboard">
			<input type="hidden" id="encGroupId" value="${encGroupId}" /> <input
				type="hidden" id="encProjId" value="" />
				<!--Bhavesh(28-06-2023) hided the question-circle by adding class hide -->
			<div class="icon-tbar hide" style="top: 55%;">
				<a href="/PMS/resources/app_srv/PMS/global/images/GC_flowchart.png"
					target="_blank" class="question" title="Help" style="padding: 5px;"><i
					class="fa fa-question-circle "></i></a>
			</div>
			<div class="row padded tiles">

				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<a href="javascript:void()"  onclick="loadOngoingList()">
						<div class="info-box bg-cyan hover-expand-effect new-tiles new-tiles-cyan">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-green icon-new">
									<div class="icon " style="
    padding-top: 30px;
">
									<!-- 	<i class="material-icons">wc</i> -->
									<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/projects.png"/> 
									
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<div class="text">
												<spring:message code="dashboard.project.label" />
											</div>

											<div class="number">${projectCount}</div>
											<h5>CDAC Outlay</h5>
											<h5>${ongoingProjectCost} Lakhs</h5>
											<!-- added for showing deputed at others on employee type by devesh on 14/6/23 -->
											<%-- <div>at Client Site : ${employeeAtClientCount}</div> --%>
											<%-- <div>at Client Site : ${employeeAtCLIENTCount}</div> --%>
											<%-- <div>NULL : ${employeeAtOthersCount}</div> --%>
											<!-- End -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<a class="" href="#" data-image-id="" data-toggle="modal"
						data-title="" data-target="#dash-employee-modal">
						<div class="info-box bg-pink hover-expand-effect new-tiles new-tiles-pink">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-pink icon-new">
									<div class="icon " style="
    padding-top: 30px;
">
									<!-- 	<i class="material-icons">wc</i> -->
									<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/employee.png"/> 
									
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<div class="text">
												<spring:message code="dashboard.employee.label" />
											</div>

											<div class="number">${employeeCount}</div>
											<div>at CDAC : ${employeeAtCDACCount}</div>
											<!-- added for showing deputed at others on employee type by devesh on 14/6/23 -->
											<%-- <div>at Client Site : ${employeeAtClientCount}</div> --%>
											<div>at Client Site : ${employeeAtCLIENTCount}</div>
											<%-- <div>NULL : ${employeeAtOthersCount}</div> --%>
											<!-- End -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<a class="" href="#" data-image-id="" data-toggle="modal"
						data-title="" data-target="#dash-income-modal">
						<div class="info-box bg-light-green new-tiles new-tiles-green">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-green icon-new">
									<div class="icon " style="
    padding-top: 30px;
">
										<!-- <i class="material-icons">trending_up</i> -->
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/briefcase.png"/>
										
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<div class="text">
												<spring:message code="dashboard.income.fund.label" />
											</div>


											<div class="number" id="incomeOndate">
												<span>${income} Lakhs</span>
											</div>
										</div>

										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
										 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
											<span class="pull-right white"> <span>since: </span><label
												class="col-yellow1 bold asOnDateProposals1" id="asOnDate">${startRange}</label>
											</span>
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
						<div class="info-box bg-deep-purple new-tiles-deep-yellow new-tiles ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-purple icon-new">
									<div class="icon " style="
    padding-top: 30px;
">
										<!-- <i class="material-icons">sort</i> -->
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/money-bag.png"/>
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<div class="text">
												<spring:message code="dashboard.expenditure.label" />
											</div>

											<div class="number convert_lakhs" id="expenditureOndate">${expenditure}</div>
										</div>

										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
										 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
											<span class="pull-right white"> <span>since: </span><span
												class="col-yellow1 bold asOnDateProposals1" id="asOnDateExp">${startRange}</span>
												<input class="input-field" type="hidden" id="dtExpStartDate">
											</span>
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

<div class="col-md-8 col-lg-8 col-sm-12 col-xs-12 pad-top ">
				<div class="panel panel-default panel-glance"
					style="background: #ececec !important;">

					<div class="panel-body zero">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items zero">

							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click Here to View Proposals Submitted" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-newProposalList-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #c9e7f9; border: 4px solid #69bbec;">
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/proposals.png"/>
											</div>
											<%---- Label Change of New Proposal [08-02-2024]-----%>
											<p class="card-category" title="Proposals Submitted">
												<span style="font-size:medium">								
													Proposals Submitted : <strong><span id="proposalSubmittedCount">${proposalListCount}</span></strong><br/>
													Project Received : <strong><span id="projectProposalCount">${proposalReceivedCount}</span></strong>
												</span>
											</p> 
											<%-- <h3 class="card-title card-count" id="newProposalsOnDate">
												<span id="proposalSubmittedCount">${proposalListCount}</span>
												 / 
												 <span id="projectProposalCount">${proposalReceivedCount}</span>
											</h3> --%>
										</div>
										<div class="card-footer">
											<div class="stats">
											 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
												<span> <span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateProposals"
													style="font-size: 14px; color: #4d4b4b;">${startRange}
												</span>
												</span>

											</div>

										</div>
									</div>
								</a>
							</div>


							<!--1st Tile  -->

							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" title="Click Here to View New Projects Received but approval Pending" data-toggle="modal"
									data-title="" data-target="#dash-newProjectList-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ddd5ff;border: 4px solid #a591fb;">
												<!-- <i class="material-icons">add_alert</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/projects_rec.png"/>
											</div>
											<p class="card-category" title="New Projects received">Project Approval Pending</p> <%---- Remove Received word [12-10-2023]-----%>
											<!-- <p class="card-category" style="font-size:medium;color:blue">(Approval Pending)</p> -->
											<h3 class="card-title card-count" id="newProjectsOnDate">
												${newProjectCount}
												<!--  <small>GB</small> -->
											</h3>
										</div>
										<div class="card-footer">
											<div class="stats">
											 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
												<span> <span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateProjects"
													style="font-size: 14px; color: #4d4b4b;">${startRange}</span>
												</span>

											</div>

										</div>
									</div>
								</a>
							</div>
							<!-- End of 1st Tile  -->
							<!-- 2nd tile  -->
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click Here to View Pending Payments" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-pending-payments-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #c8ffe8;border: 4px solid #5ed49e;">
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
							<!-- End of 2nd Tile  -->
							<!-- 3rd Tile  -->
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click Here to View Due Payments" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-pending-invoices-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ffd5df;border: 4px solid #f77c9a;">
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
														
							<!--Bhavesh (14-08-23) added new tile for Underclosure  -->
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Pending for GC Approval." data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal1">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #2bc7ed;border: 4px solid #047fa7;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/projects2.png"/>
											</div>
											<p class="card-category"
												title="Pending for GC Approval.">Closure Request</p>
											<h3 class="card-title" id="ClosureRequestCount">
												${underClosureCount}
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
								<a class="" href="#" title="Click Here to View Technical Closure Pending" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #efd1b9; border: 4px solid #ca9a72;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closure_pending.png"/>
											</div>
											<!-- <p class="card-category"
												title="Closing date passed.Closure still pending.">Closure/Extension
												Pending</p> -->
											<p class="card-category"
													title="Closing date passed.Closure still pending.">Technical Closure/Extension Pending</p><!-- Name changed by devesh on 22-09-23 -->
											<h3 class="card-title" id="TechnicalClosurePendingCount">
												<%-- ${pendingClosureCount} --%>
												<!-- //Added Closure/Extention pending count projects which closure is not initialized by devesh on 17/8/23 -->
												${pendingClosureCountforOngoing}
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
						</div>	
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items zero ">
							<%-- <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click Here to View Under Closure Projects" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-under-closure-modal">
									<div onclick="initializeUnderClosure()" class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div onclick="initializeUnderClosure()" class="card-icon" style="background: #efd1b9; border: 4px solid #ca9a72;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closure_pending.png"/>
											</div>
											<p class="card-category"
												title="Under Closure Projects">Closure Request
												</p>
											<h3 class="card-title" onclick="initializeUnderClosure()">
												${underClosureCount}
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
							</div> --%>
							<!--Bhavesh (10-10-23) added new tile for PMO Closure Pending -->
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click here to view Projects Pending for Financial Approval." data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal4">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #6699ff; border: 4px solid #047fa7;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/pendingTasks.png"/>
											</div>
											<p class="card-category"
												title="Click here to view Projects Pending for Financial Approval.">PMO Confirmation Pending</p>
											<h3 class="card-title" style="font-size: 14;color: red;">
												 Approval-<span id="approvalp"></span>
												<!--  <small>GB</small> -->
											</h3>
											<h3 class="card-title" style="font-size: 14;color: red;">
												 Closure-<span id="underClosureProjectCount">${underClosureProjectCount}</span> 
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
							<!--Bhavesh (22-09-23) added new tile for Underclosure projects for Finance  -->
							<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click here to view Projects Pending for Financial Approval." data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closure-pending-modal3">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #efd1b9; border: 4px solid #ca9a72;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/bill_pending.png"/>
											</div>
											<p class="card-category" style="font-size:medium;"
												title="Click here to view Projects Pending for Financial Approval.">Financial Closure Pending 
													<h3 id="totalFinancialPendingCount" class="card-title" style="font-size:medium;"></h3>
											</p>
											<!-- <h3 class="card-title"> -->
<!-- 											<h3 class="card-title" id="FinancialPendingCount">Added by devesh on 12-10-23 to update financial pending count
												
												 <small>GB</small>
											</h3> -->
											<%--------- Financial Pending and Financial Closure Request Count [ 16-01-2024 ] --%>
											<h3 class="card-title pt-2" style="font-size: 12;color: red;">
												Closure Reconciled - <span id="FinancialClosureRequestCount"></span> 
												<!--  <small>GB</small> -->
											</h3>
											<h3 class="card-title pt-2" style="font-size: 12;color: red;">
												 Reconciliation Pending - <span id="FinancialPendingCount"></span>
												
											</h3>
											<%--------- EOL Financial Pending and Financial Closure Request Count [ 16-01-2024 ] --%>
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
														<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" title="Click Here to View Technically and Financially Closed Projects." data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-closedProjectList-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ffe0bc;border: 4px solid #eaa353;">
												<!-- <i class="material-icons">backspace</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closed_projects1.png"/>
											</div>
											<p class="card-category"
												title="Projects closed during the period">Closed
												Projects</p>
											<h3 class="card-title" id="closedProjectsOnDate">
												${closedProjectsCount}
												<!--  <small>GB</small> -->
											</h3>
										</div>
										<div class="card-footer">


											<!-- <div class="stats"> -->
											<div class="stats" hidden="true"> <!-- Modified by devesh on 16-01-24 to hide filter in closed project tiles -->
											 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
												<span> <span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateClosedProjects"
													style="font-size: 14px; color: #4d4b4b;">${startRange}</span>
												</span>

											</div>


										</div>
									</div>
								</a>
							</div>
							
							
                            </div>
							<!-- <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
								<div class="card card-stats" style="height:100px;">
									<div class="card-header card-header-info card-header-icon">
										<div class="card-icon">
											<i class="material-icons">warning</i>
										</div>
										<p class="card-category">Pending Invoice</p>
										<h3 class="card-title">
											7 <small>GB</small>
										</h3>
									</div>
									 <div class="card-footer">
										<div class="stats">
											<p>since : 01/01/2020</p>
											<span class="pull-right"><i class="material-icons">calendar_today</i></span>
										</div>
									</div> 
								</div>
							</div> -->

						
						<!-- End of 3rd Tile -->
					
					<!-- New Row -->
					<!-- 	new tile of validation projects by varun on 05-12-2023 -->
								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 hidden">
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
				<input type="hidden" id="month" value=""/>
		<input type="hidden" id="year" value=""/>
		<input type="hidden" id="encPageId" value="${encPageId}"/>
		<input type="hidden" id="encWorkflowId" value="${encWorkflowId}"/>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 pad-top ">
					<div
						class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-left-zero pad-right-zero">
						<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">	<spring:message code="project.progress.report"/></div>
							<div class="panel-body " style="background: #b1b7f7;">

								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  ">
									<a class="pLDash-box plDash-box1" href="#" title="Click Here to View Monthly Progress Report Sent by PLs" data-image-id=""
										data-toggle="modal" data-title=""
										 data-target="#Progress-Reports-modal-sentByPL" data-whatever="4">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<div class="image payment-image" style="border: 4px solid #7984f5 !important;">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/pl.png">

												</div>
												<div class="info " style="border: 4px solid #8992f3;">
													<h3 class="title payment-content-heading payment-content-heading-blue">
														Sent by PLs
													</h3>
													<div class="paymentsCount number" id="sentByPLs">
														<span></span>
													</div>
													
												</div>
											</div>


										</div>
									</a>
								</div>


								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad-top">
									<a class="pLDash-box plDash-box1" href="#" title="Click Here to View Monthly Progress Report Back by PMO" data-image-id=""
										data-toggle="modal" data-title=""
										data-target="#Progress-Reports-modal-backByPMO" data-whatever="7">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<div class="image payment-image" style="border: 4px solid #7984f5 !important;">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/pmo.png">

												</div>
												<div class="info " style="border: 4px solid #8992f3;">
													<h3 class="title payment-content-heading payment-content-heading-blue">Back by PMO</h3>
													<div class=" paymentsCount number" id="sentBackByPMO">
														<span></span><br> 
												
													</div>

												</div>
											</div>


										</div>
									</a>
								</div>


								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad-top">
									<a class="pLDash-box plDash-box1" href="#" title="Click Here to View all Reports" data-image-id=""
										data-toggle="modal" data-title=""
										data-target="#Progress-Reports-modal-all" data-whatever="7">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<div class="image payment-image" style="border: 4px solid #7984f5 !important;">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/invoice1.png">

												</div>
												<div class="info " style="border: 4px solid #8992f3;">
													<h3 class="title payment-content-heading payment-content-heading-blue">View all Reports
													</h3>
													<div class=" paymentsCount number" id="allApprovedReportCount">
														<span class=""></span>
												
													</div>

												</div>
											</div>


										</div>
									</a>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!--End of 2nd panel of tiles -->
				
				<%------------- Project Document Details Tile [18-03-2024] ---------%>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 pad-top mt-4">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-left-zero pad-right-zero">
						<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">	
								Documents
							</div>
							<div class="panel-body" style="background: #b1b7f7;">
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad-top">
									<a class="pLDash-box plDash-box1" href="#" title="Click Here to View All Documents" data-image-id=""
										data-toggle="modal" data-title=""
										data-target="#dash-document-details-modal" data-whatever="7">
										<div class="box pad-right pad-left">
											<div class="icon-new">
												<!-- <div class="image payment-image" style="border: 4px solid #7984f5 !important;">
													<img class="img-responsive"
														src="/PMS/resources/app_srv/PMS/global/images/dashboard/a.png">
												</div> -->
												<div class="image" style="border: 4px solid #0288d1; color: #0288d1">
													<img class="img-responsive"
																src="/PMS/resources/app_srv/PMS/global/images/dashboard/documentPLDashboardIcon.png" style="width: 55px;height: 52px;background-color: #0288d1;border-radius: 45%;"/>
												</div>
												<div class="info " style="border: 4px solid #8992f3;">
													<h3 class="title payment-content-heading payment-content-heading-blue">View Project Documents Details
													</h3>
													<div class=" paymentsCount number" id="allApprovedReportCount">
														<span class=""></span>
													</div>
												</div>
											</div>
										</div>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%------------- Project Document Details Tile [18-03-2024] ---------%>
			</div>
			
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12  ">
				<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
					<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">Milestones</div>
						<div class="panel-body " style="background: #ececec !important;">
					<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12 outer-w3-agile tiles outer-w3-agile-new">
							<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12 ">
								<%------------      Add data target to call the Milestone due in one month modal [21-08-2023]   ------------------%>
								<a class="" href="#"
										title="Click Here to View Milstone Due in next one month"
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-milestone-dueInOneMonth-modal">
									<div class="info-box bg-blue-dashboardTile info-box-mile">
										
										
										<div
								class="s-l align-items-center justify-content-between  col-md-8 col-lg-8 col-sm-8 col-xs-8 pad-top-double">
								<h4>
								DUE IN NEXT MONTH
								</h4>

							</div>

							<div class="s-r col-md-4 col-lg-4 col-sm-4 col-xs-4 pad-top " >
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<h2 class="pull-right" >
									<span  class="card-title card-title-milestone">${countMilestone}<span class="pad-left"><i
										class="material-icons emp_icon emp_icon_new">view_list</i></span></span> <!-- <span></span> -->

								</h2>
								</div>

									</div>
									</div>
								</a>
							</div>

							<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12 pad-top">
								  <%------------      Add data target to call the Milestone exceeded deadline deatils modal [21-08-2023]   ------------------%>
								<a class="" href="#"
										title="Click Here to View Exceeded Deadline"
										data-image-id="" data-toggle="modal" data-title=""
										data-target="#dash-milestone-exceeded-modal">
									<div class="info-box bg-blue-dashboardTile info-box-mile">
									<div
								class="s-l align-items-center justify-content-between  col-md-8 col-lg-8 col-sm-8 col-xs-8 pad-top-double">
								<h4>
								EXCEEDED MILESTONE
								</h4>

							</div>

							<div class="s-r col-md-4 col-lg-4 col-sm-4 col-xs-4 pad-top " >
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<h2 class="pull-right" >
									<span  class="card-title card-title-milestone">${countExceededMilestone}<span class="pad-left"><i
										class="material-icons emp_icon emp_icon_new">report_problem</i></span></span> <!-- <span></span> -->

								</h2>
								</div>

									</div>
									</div>
								</a>
							</div>
							
								
							</div>
							</div>
						</div>
					</div>
				
		
			<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12  ">
				<!--2nd panel of tiles  -->

				
				<div class="panel panel-info panel-glance">
	<div class="panel-heading font_eighteen bold" style="background: #d0d4fc !important;
    color: #000;
    border-color: #d0d4fc;">
						Employee
					</div>
					<div class="panel-body " style="background: #ececec;">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items ">

							<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-new-joinee-modal">
									<div class="serviceBoxEmp" >
									
                <div class="service-count" id="newJoineEmp">${listOfJoinedEmp}</div>
                <div class="service-icon">
                    <span><i class="fa fa-user-plus" style="color: #635bca;"></i></span>
                </div>
                <h3 class="title">New Joinee</h3>
                <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
                <p class="read-more"> <span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateJoinee"
													style="font-size: 14px; color: #fff;">${startRange}
												</span>
												

										</p>
            </div>
									
									
								</a>
							</div>
							
											
		 
		
		 	
								<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-resigned-emp-modal">
													<div class="serviceBoxEmp purple1" >
									
                <div class="service-count" id="newResignEmp">${listOfResignedEmp}</div>
                <div class="service-icon">
                    <span><i class="fa fa-user-times" style="color: #860b05;"></i></span>
                </div>
                <!-- <h3 class="title">Resigned</h3> -->
                <h3 class="title">Relieved</h3>
                <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
                <p class="read-more"><span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateResign"
													style="font-size: 14px; color: #fff;">${startRange}
												</span>
												</p>
            </div>
									
								</a>
							</div>
							
							
							<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#dash-rejoin-emp-modal">
													<div class="serviceBoxEmp blue" >
							
                <div class="service-count" id="newRejoinEmp">${rejoinEmp}<br>
               
                </div>
                <div class="service-icon">
                    <span><i class="fa fa-user-plus" style="color: #00adef;"></i></span>
                </div>
                <h3 class="title">Rejoined </h3>
               <h1 class="title" style="font-size:x-small;" > <i>Working</i> :<span id="workCount"> ${workingEmployeeCount}</span><br>
                					<i>Relieved:</i> <span id="relievedCount">${rejoinRelievedEmp}</span></h1>
                					 <!--Bhavesh(23-06-2023)added class class="asOnDateProposals1"  -->
                <p class="read-more"><span class="pad-top">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateRejoin"
													style="font-size: 14px; color: #fff;">${startRange}
												</span>
												</p>
            </div>
									
								</a>
							</div>

			
													</div>
	
					
					</div>
				</div>
				<!--End of 2nd panel of tiles -->
			</div>
			
			
			
			</div>
			
<%-- 
		<input type="hidden" id="month" value=""/>
		<input type="hidden" id="year" value=""/>
		<input type="hidden" id="encPageId" value="${encPageId}"/>
		<input type="hidden" id="encWorkflowId" value="${encWorkflowId}"/>
		
		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-top pad-bottom">				
				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						<spring:message code="project.progress.report"/>
					</div>
					<div class="panel-body">
						<div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
								 data-target="#Progress-Reports-modal" data-whatever="4">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header card-header-yellow card-header-icon">
											<div class="card-icon">
												<i class="material-icons">report</i>
											</div>
											<p class="card-category" title="Progress Report Submitted By PL">Sent<br>
												by PLs</p>
											<h3 class="card-title card-count" id="sentByPLs">												
											</h3>
										</div>
										<div class="card-footer">
											<div class="stats">
											</div>
										</div>										
									</div>
									
								</a>
							</div>
							<div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									 data-target="#Progress-Reports-modal" data-whatever="7">
									<div class="card card-stats" style="min-height:125px;">
										<div class="card-header card-header-danger  card-header-icon">
											<div class="card-icon">
												<i class="material-icons">bug_report</i>
											</div>
											<p class="card-category" title="Progress Report Submitted By PL">Sent<br>
												Back by PMO</p>
											<h3 class="card-title card-count" id="sentBackByPMO">												
											</h3>
										</div>	
										<div class="card-footer">
											<div class="stats">
											</div>
										</div>									
									</div>
								</a>
							</div>
							
							<div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									 data-target="#Progress-Reports-modal-all" data-whatever="7">
									<div class="card card-stats" style="min-height:125px;">
										<div class="card-header card-header-blue card-header-icon">
											<div class="card-icon">
												<i class="material-icons ">report</i>
											</div>
											<p class="card-category" title="View all Reports">View all Reports<br>
												</p>
											<h3 class="card-title card-count" id="allApprovedReportCount">												
											</h3>
										</div>	
										<div class="card-footer">
											<div class="stats">
											</div>
										</div>									
									</div>
								</a>
							</div>
							
					</div>
				</div>
		</div> --%>
		
		
		<!--  <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-top pad-bottom">				
				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						Mapped utilization
					</div>
					<div class="panel-body">
						<div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
								 data-target="#Employee-Details-modal" data-whatever="4">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header card-header-yellow card-header-icon">
											<div class="card-icon">
												<i class="material-icons">report</i>
											</div>
											<p class="card-category" title="">Mapped utilization<br>
												</p>
											<h3 class="card-title card-count" id="empDetailsCount">												
											</h3>
										</div>
										<div class="card-footer">
											<div class="stats">
											</div>
										</div>										
									</div>
									
								</a>
							</div>
		
					</div>
				</div>
		</div> -->
		
	
		
		<!-- Grouped Bar Chart for projects received in last 3 years group-wise -->
	
			<%--  <div
				class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-bottom pad-top">
			
				
				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						<spring:message code="dashboard.gc.receivedprojects.label" />
						<span class="font_12"><spring:message
								code="dashboard.last3years.label" /> </span>
					</div>
					<div class="panel-body">
						<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 ">
							<div id="columnchart_yearwise_projects_count"
								style="width: 85%; height: 385px;"></div>
						</div>
					</div>
				</div>
			</div>  --%>
			<!--End Grouped Bar Chart for projects received in last 3 years group-wise -->

			<%-- <div
				class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-bottom pad-top"
				id="glance">
				<div class="panel panel-info panel-glance">
					<div class="panel-heading ">
						<span class="font_eighteen"><spring:message
								code="dashboard.glance.label" /></span> <span
							class="font_14 font_grey">(<spring:message
								code="dashboard.glance.ongoing" />)
						</span>
					</div>
					<div class="panel-body">


						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<c:choose>
								<c:when test="${not empty  activeProjects}">
									<div class=" table-responsive " id="contain">

										<table
											class="table table-bordered table-striped table-hover mb-0 "
											id="glance_data_table">

											<thead class="my-custom-scrollbar-thead datatable_thead">
												<tr>
													<th scope="col"><spring:message
															code="dashboard.serial.shortcode.label" /></th>
													<th scope="col"><spring:message
															code="dashboard.project.label" /></th>
													
													<th scope="col"><spring:message
															code="dashboard.status.label" /></th>
												</tr>
											</thead>
											<tbody class="my-custom-scrollbar-tbody" id="table_body">
												<c:forEach items="${activeProjects}" var="project"
													varStatus="loop">
													<tr>
														<td>${loop.index+1}</td>
														<td>${project.projectName}</td>
														
														<td>
														
														</td>
													</tr>



												</c:forEach>


											</tbody>
										</table>

									</div>
								</c:when>
								<c:otherwise>
									<h5>Currently data is unavailable.</h5>
								</c:otherwise>
							</c:choose>

						


						</div>

					</div>

				</div>
			</div> --%>
			<!--End of  table-->




			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
				<!--Quick Links  -->
				<!-- <div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 tiles"> -->

				<div class="panel panel-info panel-glance">
					<!-- 	<div class="panel panel-info panel-glance"
					style="background: #d0d4fc !important;"> -->
					<%-- <div class="panel-heading font_eighteen">
							<spring:message code="dashboard.gc.quicklinks.label" />
						</div>--%>
					<div class="panel-body " style="background: #ececec;">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items ">
							<div
								class="col-lg-2 col-md-2 col-sm-12 col-xs-12 gc-dash-service-box ">
								<div class="serviceBox orange">
									<div class="service-icon">
										<!-- <span><i class="fa fa-file-text-o"></i></span> -->
										<span><i class="fa fa-pencil-square-o"></i></span>
									</div>
									
									<a
										href="${pageContext.request.contextPath}/ViewApplicationDetails"
										target="_blank" title="Add/Edit Proposal" class="read-more"><spring:message code="dashboard.add.edit.proposal" /></a>
								</div>
							</div>
							<div
								class="col-lg-2 col-md-2 col-sm-12 col-xs-12 gc-dash-service-box ">
								<div class="serviceBox cyan">
									<div class="service-icon">
										<span><i class="fa fa-files-o"></i></span>
									</div>
									

									<a
										href="${pageContext.request.contextPath}/mst/ViewAllProjects"
										target="_blank" title="Edit Project" class="read-more"><spring:message code="dashboard.edit.project" /></a>
								</div>
							</div>
							<div
								class="col-lg-3 col-md-3 col-sm-12 col-xs-12 gc-dash-service-box ">
								<div class="serviceBox brown">
									<div class="service-icon">
										<span><i class="fa fa-user-plus"></i></span>
									</div>
									
									<a
										href="${pageContext.request.contextPath}/mst/projectTeamMapping"
										target="_blank" title="Map Employee to a project"
										class="read-more"><spring:message code="dashboard.map.employee" /></a>
								</div>
							</div>
							<div
								class="col-lg-2 col-md-2 col-sm-12 col-xs-12 gc-dash-service-box ">
								<div class="serviceBox pink">
									<div class="service-icon">
										<span><i class="fa fa-clipboard"></i></span>
									</div>
									
									<a 
										href="${pageContext.request.contextPath}/mst/projectInvoiceMaster"
										target="_blank" title="Invoice Details" class="read-more"><spring:message code="dashboard.invoice.details" /></a>
								</div>
							</div>
							<div
								class="col-lg-3 col-md-3 col-sm-12 col-xs-12 gc-dash-service-box ">
								<div class="serviceBox green">
									<div class="service-icon">
										<span><i class="fa fa-inr"></i></span>
									</div>
									

									<a
										href="${pageContext.request.contextPath}/mst/projectPaymentReceived"
										target="_blank" title="Payment Received" class="read-more">Add
										<spring:message code="dashboard.payment.received" /></a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--End of Quick Links  -->
			</div>
			<%-- <!-- Group Tiles  -->

			<div
				class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-top pad-bottom">

				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						<spring:message code="dashboard.gc.projectlist.label" />
					</div>
					<div class="panel-body">
						<c:choose>
							<c:when test="${not empty groupnames}">
								<div
									class="col-md-12 col-xs-12 col-sm-12 col-lg-12 groupdiv pad-bottom"
									id="groupdiv">
									<c:forEach items="${groupnames}" var="grp">
										<c:choose>
											<c:when test="${fn:length(grp.groupName) >= 41}">
												<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 pad-top">
											</c:when>
											<c:when test="${fn:length(grp.groupName) >= 34}">
												<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
											</c:when>
											<c:when test="${fn:length(grp.groupName) >= 29}">
												<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3">
											</c:when>
											<c:otherwise>
												<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">
											</c:otherwise>
										</c:choose>
										<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3">
											<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
										</div>

									</c:forEach>

								</div>
								<div class="hidden " id="groupWiseProject">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero"
										id="groupWiseProjectDtl"></div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero" id="groupWiseProjectDtl">
<div > <iframe id="test" src="/PMS/mst/ViewProjectDetails?encGroupId=${encGroupId}" width="100%;" height="100%" style="border: none;"></iframe> </div>
</div>

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
																<th><spring:message code="project.projectRefNo" /></th>
																<th style="width: 35%;"><spring:message
																		code="Project_Module_Master.projectName" /></th>
																<th><spring:message
																		code="application.project.category.label" /></th>
																<th><spring:message code="employee_Role.start_Date" /></th>
																<th><spring:message code="employee_Role.end_Date" /></th>
																<th style="width: 23%;"><p>
																		<spring:message code="project.details.duration" />
																	</p>
																	<!-- <p>(months)</p> --></th>
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
																<th style="width: 2%;"><i class="fa fa-th-list"></i></th>
																<th class="textBox"><spring:message
																		code="project.projectRefNo" /></th>
																<th class="textBox" style="width: 35%;"><spring:message
																		code="Project_Module_Master.projectName" /></th>

																<th class="comboBox" id="businessTypeSelect"><spring:message
																		code="application.project.category.label" /></th>
																<th class="comboBox" style="width: 10%;"><spring:message
																		code="employee_Role.start_Date" /></th>
																<th class="comboBox" style="width: 10%;"><spring:message
																		code="employee_Role.end_Date" /></th>
																<th style="width: 23%;" class="comboBox"><spring:message
																		code="project.details.duration" /></th>
																<th class="textBox"><spring:message
																		code="project.details.leader" /></th>
																<th style="width: 10%;" class="textBox"><spring:message
																		code="project.details.totalcost.lakhs" /></th>
																<th style="width: 10%;" class="textBox hidden"><spring:message
																		code="project.details.amountreceived.lakhs" /></th>
																<!-----------------  For Download the Excel of ongoing projects feature [ added by Anuj ] ------------------------------------------------------------->
																<!-- <th><i class="fa fa-download"></i></th> -->
																<th><i class="fa fa-download btn btn-success" onClick="downloadOngoingProjects()"></i></th>
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
																			<c:if test="${item.projectRefrenceNo==projectdetail.projectRefrenceNo}">
																				<c:set var="test_data" value="golden-text"/>
																			</c:if>
																		</c:forEach>
																		<tr id='${projectdetail.encProjectId}'
																			class="${test_data}">
																			<td></td>
																			<td>${projectdetail.projectRefrenceNo}</td>
																			<td class="font_16 " ><a
																				class="bold   ${ test_data}"
																				title='Click to View Complete Information'
																				onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a>
																				<p class="font_14 ">
																					<i><a class="orange  ${ test_data}"
																						title="Click here to view Funding org details"
																						data-toggle='modal' data-target='#clientDetails'
																						data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}'
																						class='text-center'>${projectdetail.clientName}</a></i>
																				</p>
																				<p class="bold   ${ test_data} font_14 text-left">${projectdetail.projectRefrenceNo}</p>
																				<p class=" font_12  ${ test_data}" >${projectdetail.businessType}</p>
																				</td>
																			<td>${projectdetail.businessType}</td>
																			<td style="width: 10%;">${projectdetail.startDate}</td>
																			 <td>red</td><td style="width:10%;">${projectdetail.endDate}</td>
																			<td style="width: 10%;">${projectdetail.endDate}</td>
																			<td class="  ${ test_data}" align="center">
																				${projectdetail.startDate}  to  ${projectdetail.endDate}<br>
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
																			

																			<td style="width: 10%;" style="color:#d59a83">
																				<ul>
																					<c:forEach items="${projectdetail.projectDocument}"
																						var="projectDocument">
																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadDocument("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																								
																								<c:choose>
									<c:when test="${projectDocument.uploadedFor == 'Proposal'}">
									
							 <li><a class="${projectDocument.classColor}  bold" onclick='downloadProposalFile("${projectDocument.encNumId}")'> ${projectDocument.documentTypeName}</a></li>			
									</c:when>
									<c:otherwise>
									
								 <li><a class="${projectDocument.classColor}  bold" onclick='downloadDocument("${projectDocument.encNumId}")'> ${projectDocument.documentTypeName}</a></li>	
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
																			<td>${projectdetail.projectRefrenceNo}</td>
																			<td class="font_16 " ><a
																				class="bold ${test_data1}"
																				title='Click to View Complete Information'
																				onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a>
																				<p class="font_14 ">
																					<i><a class="orange ${test_data1}"
																						title="Click here to view Funding org details"
																						data-toggle='modal' data-target='#clientDetails'
																						data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}'
																						class='text-center'>${projectdetail.clientName}</a></i>
																				</p>
																				<p class="bold blue font_14 text-left ${test_data1}">${projectdetail.projectRefrenceNo}</p>
																				<p class=" font_12 ${test_data1}" >${projectdetail.businessType}</p>
																				</td>
																			<td>${projectdetail.businessType}</td>
																			<td style="width: 10%;">${projectdetail.startDate}</td>
																			 <td>red</td><td style="width:10%;">${projectdetail.endDate}</td>
																			<td style="width: 10%;">${projectdetail.endDate}</td>
																			<td class="${test_data1}" align="center ">
																				${projectdetail.startDate} to  ${projectdetail.endDate}<br>
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
																			

																			<td style="width: 10%;">
																				<ul>
																					<c:forEach items="${projectdetail.projectDocument}"
																						var="projectDocument">
																						<li><a
																							class="${projectDocument.classColor}  bold"
																							onclick='downloadDocument("${projectDocument.encNumId}")'>
																								${projectDocument.documentTypeName}</a></li>
																					</c:forEach>
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


							</c:otherwise>
						</c:choose>



					</div>
				</div>
			</div>

			<!-- End Group Tiles  --> --%>

<!-- /*--------------------------------------Bhavesh List of Ongoing Projects [27-10-2023] -----------------------------------------------------*/ --> 

			
				<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-top pad-bottom  ">

					<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">
							<spring:message code="dashboard.gc.projectlist.label" />
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
									<%-- 						<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
							</c:otherwise>
									</c:choose>	
							<button class="btn btn1 " data-toggle="tooltip"
								data-placement="top" title="Click here to see details"
								style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
								onclick="loadGroupWiseProjects('${grp.encGroupId}')">${grp.groupShortName}</button>
						</div> --%>
									<!-------------- If Group Show projects fields equals to 1 then display group names  -->
									<c:if test="${grp.showProjects eq 1}">
										<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top hidden ">
											<%-- </c:otherwise>
									</c:choose>	 --%>
											<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProjects('${grp.encGroupId}')">${grp.groupShortName}</button>
												<div id="groupId" class="hidden">${grp.encGroupId}</div>
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
												<table id="exampleList"
													class="table table-striped table-bordered"
													style="width: 100%">
													<thead class="datatable_thead bold ">
														<tr>
															<th style="width: 2%;"><spring:message
																	code="serial.no" /></th>
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
																	<spring:message code="centre.outlay" />
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
															<th><i class="fa fa-download btn btn-success"
																onClick="downloadOngoingProjects()"></i><input
																type="hidden" id="groupId" /></th>
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

								<div class="col-md-12 col-lg-12 pad-top"
									id="groupWiseProjectDtl"></div>
								<div class="hidden " id="groupWiseProject">

									<div class="col-md-12 col-lg-12" id="groupWiseProjectDtl"></div>

								</div>
							</div>
						</div>
					</div>
				</div>
			
			<!-- End Group Tiles  -->
<!-- /*--------------------------------------Bhavesh END List of Ongoing Projects [27-10-2023] -----------------------------------------------------*/ --> 

			<!-- Modal for Projects -->
			<%-- <div class="modal dash-grpwise-bargraph-modal"
				id="dash-grpwise-bargraph-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.ongoingprojects.label" />
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="modal-body ">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<h4 class="pad-bottom">
									<span class="orange">${ongoingProjectCount}</span> ongoing
									projects worth <span class="orange">${ongoingProjectCost}
									</span> Lakhs
								</h4>
								<h5><span class="orange ">&#42; </span><spring:message code="dashbaord.amount.label" />.</h5>

							</div>
							<c:if test="${fn:length(countProjects)>1}">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
									<div class="table-responsive">

										<table class="table table-bordered table-striped mb-0" id="">
											<thead class="my-custom-scrollbar-thead datatable_thead">
												<tr>
													<th><spring:message
															code="dashboard.serial.shortcode.label" /></th>

													<th scope="col"><spring:message
															code="dashboard.number.projects.label" /></th>
													<th scope="col"><spring:message
															code="dashboard.project.outlay.label" /> (in INR)</th>
												</tr>
											</thead>
											<tbody class="">

												<c:forEach items="${countProjects}" var="count"
													varStatus="loop">
													<tr>
														<td>${loop.index+1}</td>

														<td class="text-right">${count.projectCount}</td>
														<td class="text-right currency-inr">${count.projectCost}</td>

													</tr>



												</c:forEach>


											</tbody>
										</table>

									</div>


								</div>
							</c:if>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
								<div class="panel panel-info ">
									<div class="panel-heading font_16">
										<spring:message
											code="dashboard.businestype.distribution.label" />
									</div>
									<div class="panel-body">
										<!-- <div
										class="col-md-6 col-lg-6 col-sm-12 col-xs-12 model-border-chart ">
										<div id="chart_container_tot_projects"
											style="width: 100%; height: 100%;"></div>
									</div>
									<div
										class="col-md-6 col-lg-6 col-sm-12 col-xs-12  model-border-chart">
										<div id="chart_container_cost_projects"
											style="width: 100%; height: 100%;"></div>
									</div> -->

										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 model-border-chart ">
											<div id="chart_Business_type_Wise_Projects"
												style="width: 100%; height: 600px;"></div>
										</div>

									</div>
								</div>

							</div>


						</div>
					</div>
				</div>
			</div> --%>



			<!-- Modal for Employees -->
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<!-- <div class="modal dash-employee-modal" id="dash-employee-modal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static"> -->
			<div class="modal dash-employee-modal dash-newProposalList-modal" id="dash-employee-modal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
				<!-- <div class="modal-dialog modal-lg " > -->
				<div class="modal-dialog modal-lg mymodal1" style="width:97%; height:93%;">
				<!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
				<!--<div class="modal-content  " > -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.employee.label" />
							</h3>
							<!-- Bhavesh(23-06-2023) added onclick="restoreprop()"  -->
							<!--<button type="button" class="close"  data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button> -->
							<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(23-06-2023) added resize Button"  -->
							<!-- <div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>  -->
							<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>

						<div class="modal-body ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									<spring:message code="dashboard.total.employee.label" />
									:<span class="orange">${employeeCount}</span>
								</h4>


							</div>
							
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel-group" id="accordionGroupClosed" role="tablist" aria-multiselectable="true">
 
								<div class="panel panel-info  ">
									<div class="panel-heading font_16 " role="tab" id="headingOne">
									<h4 class="panel-title">
									<!-- updated by devesh on 16/6/23 for adding onclick function -->
										<a class="collapsed" id="intro-switch" style="color:black;"  data-toggle="collapse" href="#multiCollapse1"  aria-expanded="true" aria-controls="multiCollapse1"
											onclick="setTimeout(function() {drawEmploymentTypeWiseChart();}, 100)">
									<!-- end -->
										<spring:message code="dashboard.employment.type.label" /></a>
									</h4>
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse1" role="tabpanel" aria-labelledby="headingOne">
										<div class="panel-body">
										<div id="chart-container-emp"></div>
										</div>
									</div> 
								</div>
								
								<!-- added by devesh on 9/6/23 for deputed graph -->
								<div class="panel panel-info  " >
									<div class="panel-heading font_16" role="tab" id="headingTwo">
									<h4 class="panel-title">
										<a class="collapsed" style="color:black;" data-toggle="collapse" href="#multiCollapse3"  aria-expanded="false" aria-controls="multiCollapse3"
											onclick="setTimeout(function() {drawdeputedChart();}, 100)">
										<spring:message code="dashboard.gc.deputed.distribution.label" />  </a>	
									</h4>			
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse3" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
										<div id="chart-container-emp2"></div>
										</div>
									</div>
								</div>
								<!-- End -->
							
								<div class="panel panel-info  " >
									<div class="panel-heading font_16" role="tab" id="headingTwo">
									<h4 class="panel-title">
						<a class="collapsed" style="color:black;" data-toggle="collapse" href="#multiCollapse2"  aria-expanded="false" aria-controls="multiCollapse2">
						<spring:message code="dashboard.gc.gender.distribution.label" />  </a>	
						</h4>			
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse2" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
										<div id="chart-container-emp1"></div>
										</div>
									</div>
								</div>
							</div>
</div>
						
		
						
							<div
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


												<c:forEach var="employeeCountByGroupDesignation"
													items="${employeeCountByGroupandDesignation}"
													varStatus="loop">

													<c:choose>
														<c:when test="${loop.index==0}">

															<tr class="my-custom-scrollbar-thead datatable_thead">
																<th class="bold font_16">
																	${employeeCountByGroupDesignation.key}</th>
																<c:forEach var="counter"
																	items="${employeeCountByGroupDesignation.value}">
																	<th id="${counter}">${counter}</th>
																</c:forEach>

															</tr>

														</c:when>
														<c:otherwise>
															<tr>
																<td class="bold font_16">
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
							</div>
							<!-- employee Details With Involvements -->
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="employeeWithInvolvementsTbl" class="table table-striped">
									<thead>
										<tr class="datatable_thead bold ">
											<th width="5%"><spring:message code="forms.serialNo"/></th>
											<th width="10%"><spring:message code="employee.EmployeeId"/></th>
											<th width="20%"><spring:message code="employee_Role.employee_Name"/></th>
											<th width="13%"><spring:message code="employee.designation"/></th>
											<th width="12%"><spring:message code="employee.Employment.type"/></th>
											<th width="1%"><spring:message code="employee.officeEmail"/></th>
											<th width="12%"><spring:message code="employee.deputedat"/></th> <!--Deputed at Column added by devesh on 09/06/23  -->
											<%-- <th width="40%"><spring:message code="Project_Module_Master.projectName"/></th> --%>
										</tr>
									</thead>
									<thead  class="filters">
										<tr>										
											<th></th>
											<th></th>
											<th></th>
											<th class="comboBox"></th>
											<th class="comboBox"></th>
											<th></th>
											<th class="comboBox"></th><!-- added by devesh on 14/6/23 for deputed filter -->
											<!-- <th></th>	 -->																		
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${employeeWithInvolvement}" var="employee" varStatus="theCount">
											<tr>
												<td>${theCount.count}</td>
												<td>${employee.employeeId}</td>
												<td>${employee.employeeName}</td>
												<td>${employee.strDesignation}</td>
												<td>${employee.employeeTypeName}</td>
												<td>${employee.officeEmail}</td>
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

			<!-- Modal for Income -->
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-income-modal dash-newProposalList-modal " id="dash-income-modal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
				<!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.projectwise.income" />
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

						<div class="modal-body ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									<spring:message code="dashboard.income.label" />
									since <span class="orange asOnDateProposals1" id="asOnDate1"></span>
								</h4>

							</div>
							<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Last:</label> <select id="months" onchange="calculateDates(this.value,'incomefrom','incometo','go-btn2',0)" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
								
								
							</div> -->
							<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">From</label> <input type="text"
									id="incomefrom" name="from2" readonly /> <label
									class="pad-left" for="to">To</label> <input type="text"
									id="incometo" name="to2" readonly />

								<button type="button" class="btn btn-success go-btn2">Go</button>

							</div> -->
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
                                  <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="font_16" for="from">Last:</label> <select class="months1" id="months" onchange="calculateDates(this.value,'incomefrom','incometo','go-btn2',0)" class="inline form-control" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			
<!-- <div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" onclick="openIncomeAnotherFilter()" id="incomCustom"><i>Custom Duration</i></a></div> -->
							</div>
							<div id="incomeAnotherFilter">
							<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
                                
                                <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="font_16" for="from">From</label> <input class="fromProposal1" type="text"
									id="incomefrom" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1" type="text" id="incometo"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn2">Go</button>
							</div>
							</div>
							</div>
							 <!-- Bhavesh(27-06-2023) changed the format of income_dataTable  -->
				 	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<div id="col-md-12">
									<table id="income_dataTable"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>

												<th width="5%"><spring:message code="forms.serialNo"></spring:message> 
												
												<th width="45%" ><spring:message
												
												
														code="dashboard.project.label" /></th>
												<%-- <th width="15%"><spring:message
														code="project.projectRefNo" /></th> --%>
														
												<th width="25%" class="text-right"><spring:message
														code="dashboard.payment.received.date" /></th>
												<th width="25%" class="text-right"><spring:message
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

			<div class="modal dash-newProposalList-modal dash-modal"
				id="dash-newProposalList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
				<!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<%-- <spring:message code="dashboard.newproposals.list" /> --%>
								List of Proposals Submitted & Project Received  <%---- Add Project Received [12-10-2023]-----%>
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

						<%-- 
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<h4 class="pad-bottom ">
									<spring:message code="dashboard.newproposals.label" />
									 -- Add Project Received [12-10-2023]---
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
                                <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="font_16" for="from">Last:</label> 
								<select class="months1" id="months" onchange="calculateDates(this.value,'fromProposal','toProposal','goProposal',1)" class="inline form-control monthsProposal " style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								Bhavesh (17-10-23) extended the filter to 3 years and 5 years
								<option value="36">3 Years</option> 
								<option value="60">5 Years</option>
								</select>
			
							<!-- <div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" onclick="openProposalAnotherFilter()" id="propSubCust"><i>Custom Duration</i></a></div> -->
							</div>
							<div id="proposalsAnotherFilter">
							
								
							<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">
                                  <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input class="fromProposal1" type="text"
									id="fromProposal" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1" type="text" id="toProposal"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button" id="goProposal" class="btn btn-success ">Go</button>
                                
							</div>
							 
								   
							
							
							    <div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;">
							  
                                  <label class="font_16" for="incomecalendarYearDropdown"> <span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year:</label>
                       <select id="incomecalendarYearDropdown" class="form-control" onchange="getYearIncomeRange()" style="width: auto; margin-left: 20px;height: 28px;">
                                <option value="0">Select Year's</option>
                                 </select>
                                      </div>
							</div>
							</div>
                                                 
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<!-- 
								<br> <br> -->
								<table id="newProposals_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="20%"><spring:message code="group.groupName" /></th>
											<th width="21%"><spring:message code="menu.proposal.label" /></th>
											(11-10-23) repalced dashboard.client.name  with client.name  
											<th width="22%"><spring:message code="client.name" /></th>
											<th width="9%"><spring:message code="dashboard.newproposals.submittedOn" /></th>
											<th width="9%"><spring:message code="project.details.duration" /></th>
											<th width="8%"><spring:message code="dashboard.newproposals.received.project" /></th>
											<th width="11%"><spring:message code="dashboard.lable.outlay" /></th>
											<!-- new column added of total outlay by varun -->
											<th width="11%">Value of Proposal(Total Outlay) </th>
											<!-- Added heading for proposal history column in new proposal tiles by devesh on 27/7/23 -->
											<th width="9%">View Proposal History</th>
											<!-- End of heading -->


										</tr>
									</thead>

									<tbody class="">
									</tbody>
									------- Add Footer [12-10-2023] --------
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
							</div>
						</div> --%>
											
						<%-------------- Proposal Submitted and Project Received Table  [ 08-02-2024 ] ----------------%>
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<h4 class="pad-bottom ">
									Proposals Submitted	& Project Received since : <span class="orange asOnDateProposals1" id="asOnDateProposals1"></span>
								</h4>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 pad-bottom text-right "> 
									<label class="font_16">Total Proposals Submitted :</label>  <span id="totalProCost"> </span></div>
								<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom text-right " id="projRec">
									<label class="font_16"> Projects Received : </label> <span id="totalConvCost"> </span></div>
								</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">							
								<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">
									<label class="font_16" for="from">Last:</label> 
									<select class="months1 inline form-control monthsProposal" id="months" onchange="calculateDates(this.value,'fromProposal','toProposal','goProposal',1)" style="width:80%">
									<option value="0">Select Months/Year's</option>
									<option value="3">3 Months</option>
									<option value="6">6 Months</option>
									<option value="12">1 Years</option>
									<option value="24">2 Years</option>
									<option value="36">3 Years</option> 
									<option value="60">5 Years</option>
									</select>
								</div>
								<div id="proposalsAnotherFilter">	
									<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">
										<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> 
										<input class="fromProposal1" type="text" id="fromProposal" name="from" readonly /> 
										<label class="font_16" for="to">To</label> <input class="toProposal1" type="text" id="toProposal" name="to" readonly /> &nbsp; &nbsp;
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
														<th width="21%"><spring:message code="menu.proposal.label" /></th>
														<th width="22%"><spring:message code="client.name" /></th>
														<th width="9%"><spring:message code="dashboard.newproposals.submittedOn" /></th>
														<th width="9%"><spring:message code="project.details.duration" /></th>
														<th width="8%"><spring:message code="dashboard.newproposals.received.project" /></th>
														<th width="11%"><spring:message code="dashboard.lable.outlay" /></th>
														<th width="11%">Value of Proposal(Total Outlay) </th>
														<th width="9%">View Proposal History</th>
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
														<th width="21%"><spring:message code="menu.proposal.label" /></th>
														<th width="21%">Project Name</th>
														<th width="22%"><spring:message code="client.name" /></th>
														<th width="9%">Work-Order Date</th>
														<th width="9%"><spring:message code="project.details.duration" /></th>
														<th width="11%"><spring:message code="dashboard.lable.outlay" /></th>
														<th width="11%">Value of Proposal(Total Outlay) </th>
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
			
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-new-joinee-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
               <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								New Joinees
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
									New Joinees
									since : <span class="orange asOnDateProposals1" id="asOnDateJoinee1"></span>
								</h4>

							</div>
							
							<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Last:</label> <select id="months" onchange="calculateDates(this.value,'fromJoin','toJoin','go-btn-join',0)" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			

							</div> -->
							<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<label class="" for="from">From</label> <input type="text"
									id="fromJoin" name="from" readonly /> <label class="" for="to">To</label>
								<input type="text" id="toJoin" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn-join">Go</button>

							</div> -->
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
                                <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
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
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
                                  <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="font_16" for="from">From</label> <input class="fromProposal1" type="text"
									id="fromJoin" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1" type="text" id="toJoin"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn-join">Go</button>

							</div>
							</div>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="joinee_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="15%">Employee Id</th>
									
											<th width="25%"><spring:message
													code="employee_Role.employee_Name" /></th>
											<th width="25%"><spring:message
													code="employee.designation" /></th>
													<th width="20%"><spring:message
													code="employee.dateofJoining" /></th>
													<th width="15%">Status</th>
											<%-- <th width="18%"><spring:message
													code="dashboard.project.startDate" /></th>
											<th width="20%"><spring:message
													code="dashboard.lable.outlay" /></th> --%>

										</tr>
									</thead>
									<thead class="filters ">
												<tr>
											<th width="5%"></th>
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
<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->			
<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-resigned-emp-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<!-- <h3 class="modal-title center" id="">
								Resigned Employees
							</h3> -->
							<h3 class="modal-title center" id="">
								Relieved Employees
							</h3><!-- Name modified by devesh on 19-10-23 -->
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

								<!-- <h4 class="pad-bottom">
									Resigned
									since : <span class="orange asOnDateProposals1" id="asOnDateResign1"></span>
								</h4> -->
								<h4 class="pad-bottom">
									Relieved
									since : <span class="orange asOnDateProposals1" id="asOnDateResign1"></span>
								</h4><!-- Name modified by devesh on 19-10-23 -->
							</div>
							<!-- <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Last:</label> <select id="months" onchange="calculateDates(this.value,'fromRes','toRes','go-btn-resign',0)" class="inline form-control" style="width:32%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			

							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<label class="" for="from">From</label> <input type="text"
									id="fromRes" name="from" readonly /> <label class="" for="to">To</label>
								<input type="text" id="toRes" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn-resign">Go</button>

							</div> -->

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
                                 <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="font_16" for="from">Last:</label> <select class="months1" id="months" onchange="calculateDates(this.value,'fromRes','toRes','go-btn-resign',0)" class="inline form-control" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			
<!-- <div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" id="resignCustom" onclick="openResignedAnotherFilter()"><i>Custom Duration</i></a></div> -->
							</div>
							<div id="resignedAnotherFilter">
							<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
                                 <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="font_16" for="from">From</label> <input class="fromProposal1" type="text"
									id="fromRes" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1"  type="text" id="toRes"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn-resign">Go</button>

							</div>
							</div>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="resigned_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<%-- <th width="5%"><spring:message code="forms.serialNo" />
											</th> --%>
											<th width="15%">Employee Id</th>
									
											<th width="25%"><spring:message
													code="employee_Role.employee_Name" /></th>
											<th width="25%"><spring:message
													code="employee.designation" /></th>
											<!-- <th width="20%">Date of Resign/Release</th> -->
											<th width="20%">Date of Release</th><!-- Name Modified by devesh on 19-10-23 -->
											<th width="15%">Status</th>

										</tr>
									</thead>
									<thead class="filters ">
												<tr>
											<th width="5%"></th>
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
			
			
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-rejoin-emp-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								Rejoined
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
									Rejoind
									since : <span class="orange asOnDateProposals1" id="asOnDateRejoin1"></span>
								</h4>

							</div>
							

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
                                  <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="font_16" for="from">Last:</label> <select class="months1" id="months" onchange="calculateDates(this.value,'fromRej','toRej','go-btn-rejoin',0)" class="inline form-control" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			
<!-- <div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" id="rejoinCustom" onclick="openRejoinAnotherFilter()"><i>Custom Duration</i></a></div> -->
							</div>
							<div id="rejoinAnotherFilter">
							<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">
                                 <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="font_16" for="from">From</label> <input class="fromProposal1" type="text"
									id="fromRej" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1" type="text" id="toRej"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn-rejoin">Go</button>

							</div>
							</div>
							</div>
							<ul class="nav nav-tabs proj_details center pad-left-double pad-right-double" role="tablist">
         <li role="presentation" style="width: 20%;" class="active "><a class="color2" onclick="newRejoinEmpTable('Working')"  aria-controls="budget" role="tab" data-toggle="tab"><i class="fa fa-street-view fa-2x "></i>  <h6>Working</h6></a></li>
                                    <li role="presentation"  style="width: 20%;"><a class="color1" onclick="newRejoinEmpTable('Relieved')" aria-controls="about" role="tab" data-toggle="tab"><i class="fa fa-street-view fa-2x"></i>  <h6>Relieved</h6></a></li>
                                   
                                  </ul>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="rejoin_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<%-- <th width="5%"><spring:message code="forms.serialNo" />
											</th> --%>
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

			<!-- Modal for New Projects -->
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-newProjectList-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								List of Projects Approval Pending<%-- <spring:message code="dashboard.newprojects.list" /> --%> <%---- Remove Received word [12-10-2023]-----%>
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
									Projects <%-- <spring:message code="dashboard.newprojects.label" /> --%><%---- Remove Received word [12-10-2023]-----%>
									since : <span class="orange asOnDateProposals1" id="asOnDateProjects1"></span>
								</h4>
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
                                  <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="font_16" for="from">Last:</label> <select class="months1 inline form-control months" id="months" onchange="calculateDates(this.value,'from','to','go-btn',0)" style="width:80%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
			
<!-- <div style="text-align:right;olor: #1578c2;"><a href="javascript:void()" id="ProjectCust" onclick="openProjectsAnotherFilter()" ><i>Custom Duration</i></a></div> -->
							</div>
							<div id="projectsAnotherFilter">
							
							<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5  date-bar  pad-bottom ">
                                 <!--Bhavesh(23-06-2023)added classes class="fromProposal1" and class="toProposal1"    -->
								<label class="font_16" for="from"><span style="color: #1578c2;" > <b>OR </b> &nbsp; &nbsp; </span>From</label> <input class="fromProposal1" type="text"
									id="from" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input class="toProposal1"  type="text" id="to"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn ">Go</button>

							</div>
							  <div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-bottom " style="display: -webkit-inline-box;">
							 <label class="font_16" for="projectcalendarYearDropdown"><span  style="color: #1578c2;" > <b>OR </b>&nbsp; &nbsp;  </span>Calendar Year</label>
						    <select id="projectcalendarYearDropdown" class="form-control" onchange="getYearprojectRange()" style="width: auto;margin-left: 20px;height: 28px;">
						        <option value="0">Select Year's</option>
						        
						    </select>
						    </div>
							</div>
							</div>
                            

<%--------------------------  Project Received Tile [14-08-2023]  -----------------------------------------------------------------------------------------%>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="newProjects_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="22%"><spring:message code="master.name" /></th>
											<%--(11-10-23) repalced dashboard.client.name  with client.name   --%>
											<th width="14%"><spring:message code="client.name" /></th>
											<th width="14%"><spring:message
													code="projectMaster.MOUdate" /> <i
												class="fas fa-grip-lines-vertical"></i> <spring:message
													code="projectMaster.WorkorderDate" /></th>
											<th width="10%"><spring:message
													code="dashboard.project.startDate" /></th>
											<th width="10%"><spring:message
													code="projectMaster.endDate" /></th>
											<th width="10%"><spring:message
													code="dashboard.lable.outlay" /></th>
											<th width="18%">Status</th>
										</tr>
									</thead>
									<thead class="filters">
										<tr>
											<th width="22%"></th>
											<th width="14%"></th>
											<th width="14%"></th>
											<th width="10%" scope="col"></th>
											<th width="10%" scope="col"></th>
											<th width="10%" scope="col"></th>
											<th width="18%" class="comboBox"></th>
										</tr>
									</thead>
									<tbody class="">
									</tbody>
									<%--------- Add Footer [12-10-2023] ----------%>
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
							</div>

							<%-- 
<!-------------------------------------------- PENDING AT PL [04-08-2023] --------------------------------------------------------------------->
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
																<th width="25%"><spring:message code="master.name" /></th>
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
																<th width="25%"><spring:message code="master.name" /></th>
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
																<th width="25%"><spring:message code="master.name" /></th>
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
																<th width="25%"><spring:message code="master.name" /></th>
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
<!-----------------------------------  panel-group End  ------------------------------------------------------------>
 --%>							
						</div>
					</div>
				</div>
			</div>
			<!-- End of modal for New Project -->
				<!-- Modal for Pending Closur -->
				<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-closure-pending-modal dash-modal dash-newProposalList-modal"
				id="dash-closure-pending-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								List Of Projects Pending for Technical Closure
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

								

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">
			  <!-- Display the correct filter data on 7-06-23 -->
								<select id="filterClosureSymbol" onchange="calculateClosureDates()" class="inline form-control" style="width:32%">
								<option value=">=">Less than (<) </option>
								<option value="<=">Greater than (>)</option>
								
								</select>
			
			                       <!-- Bhavesh(23-06-2023) added class in select class="months1"  -->
								<label class="" for="from">Last:</label> <select id="monthsClosure" onchange="calculateClosureDates()" class="months1 inline form-control" style="width:32%">
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
											<th width="3%"><spring:message  code="forms.serialNo" /></th>
										<th width="22%"><spring:message code="master.name" /></th>
										<%-- 	<th width="15%"><spring:message
																		code="project.projectRefNo" /></th>
												<th width="15%"><spring:message
													code="dashboard.client.name" /></th> --%>
													<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>
											<th width="15%"><spring:message code="client.name" /></th>
												<th width="7%"><spring:message
																		code="project.details.leader" /></th>
											<th width="7%"><spring:message code="projectMaster.startDate" /></th>
											<th width="8%"><spring:message code="projectMaster.endDate" /></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="5%"><spring:message
													code="dashboard.lable.outlay" /></th>
											<th width="13%">Status</th>
											<!-- End of Columns -->

										</tr>
									</thead>
									<thead class="filters">
										<tr>
											<th width="3%"></th>
											<th width="22%" class=""></th>
											<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>
											<th width="15%" class="comboBox"><spring:message code="client.name" /></th>
											<th width="7%"></th>
											<th width="7%"></th>
											<th width="8%"></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="5%"></th>
											<th width="13%" id="statusComboBox"></th>
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
        
        <!-- Commented by devesh on 16/8/23 because it was not required -->
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
			
			<!-- Modal for Under closure table -->
				<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-closure-pending-modal1 dash-modal dash-newProposalList-modal"
				id="dash-closure-pending-modal1" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
						<%--bhavesh (12-10-23) changed <spring:message code="menu.project.underClosure" /> to Closure Request --%>
							<h3 class="modal-title center" id="">
								Closure Request
							</h3>
							<!-- Bhavesh(23-06-2023) added onclick="restoreprop()"  -->
							<button id="actionButton" type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
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
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">	
						      <fieldset class="fieldset-border">
						      <%---------------   Under Closure Request Tile Modal Table [21-09-2023] ------%>	
								<input type="hidden" value="${encWorkflowId_Closure}" id="encWorkflowId_Closure"/>		
									<table id="exampleUnderClosure" class="table table-striped table-bordered"
										style="width: 100%">
										<thead class="datatable_thead bold ">	
											<tr>										
						 						<th width="2%"><spring:message code="serial.no" /></th>
												<th width="15%"><spring:message code="Project_Module_Master.projectName" /></th>
												<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>
												<th width="15%"><spring:message code="client.name" /></th>
												<th><spring:message code="project.details.leader" /></th>
												<th><spring:message code="projectMaster.startDate"/></th>
												<th><spring:message code="projectMaster.endDate"/></th>
							                    <th width="15%"><spring:message code="forms.status"/></th>
												<th width="8%"><p>Technical</p> <p>Closure</p><p> Submission</p><p> Date</p></th>
												<th width="8%">Remarks</th>
												<th width="3%"><spring:message code="forms.action"/> </th>
											</tr>
										</thead>
										<thead class="filters">
											<tr>
												<th width="2%"></th>
												<th width="15%"></th>
												<th width="15%"></th>
												<th></th>
												<th></th>
												<th></th>
							                    <th width="15%" class="comboBox_Status"></th>
												<th width="8%"></th>
												<th width="8%"></th>
												<th width="3%"></th>
											</tr>
										</thead>
										<tbody id="ClosureRequestTableList">
											<%-- <c:forEach items="${underClosureCount1}" var="projectData" varStatus="loop">						
												<tr class="golden-text">
													<td class="golden-text text-center">${loop.count}</td>	
						 							<td class="font_16"><a title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectData.encProjectId}')"><span  class="golden-text">${projectData.strProjectName}</span></a></td>
													<td class="font_16 brown-text " style="width:15%;" id="td2_${projectData.encProjectId}">${projectData.clientName}
														<a class="orange golden-text" title="Click here to view Funding org details" data-toggle='modal' data-target='#clientDetails' data-whatever='${projectData.encClientId};${projectData.encProjectId};${projectData.endUserId}'>
															${projectData.clientName}
														</a> 
													</td>
													<td style="width: 10%;"><span class="font_16 ">${projectData.strPLName}</span></td>
													<td  class="golden-text" style="width:5%;">${projectData.startDate}</td>
													<td  class="golden-text" style="width:5%;">${projectData.endDate}</td>
													<td  class="golden-text">${projectData.workflowModel.strActionPerformed} by <b>${projectData.workflowModel.employeeName}</b> at ${projectData.workflowModel.transactionAt}</td>							
													<td  class="golden-text text-center">${projectData.closureDate}</td>
													<td  class="golden-text">${projectData.strProjectRemarks}</td>
													<td><div class="dropdown text-center">																						
													 <a class="btn btn-warning dropdown-toggle" data-toggle="dropdown" onclick = "viewAllowedActions('${projectData.encProjectId}')" aria-haspopup="true" aria-expanded="false">
														<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i></a>
													<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${projectData.encProjectId}"></ul></div>	
														<!-- Added by devesh on 01-09-23 to Check Project Team contains HOD or not -->
														<c:forEach items="${HODStatus}" var="CurrentHOD" varStatus="innerLoopStatus">
															<c:if test="${loop.index == innerLoopStatus.index}">
																<c:set value="${CurrentHOD}" var="CurrentHODStatus"/>
															</c:if>
														</c:forEach>
														<input type="text" class="${projectData.encProjectId}_1 hidden" value="${CurrentHODStatus}" readonly>
														<!-- END -->
													</td>											
												</tr>
											</c:forEach> --%>
										</tbody>
										<tfoot>
											<tr>
												<td colspan="10">
													<div class="legend">
														<div class="legend-item">
															<div class="legend-color"
																style="background-color: #cc9900;"></div>
															<span> Closure Initiated</span>
														</div>
														<%----- Add new legend-item [06-12-2023] --------%>
														<div class="legend-item">
															<div class="legend-color"
																style="background-color: #993300;"></div>
															<span> Financial Closure</span>
														</div>
														<%----- End Add new legend-item [06-12-2023] --------%>
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
			<%---------------   END of Under Closure Request Tile Modal [21-09-2023] ------%>	
			
			

	<%---------------   Add Modal For View Project Closure Details Action in Closure Request Tile [21-09-2023] ------%>	
	<div class="modal amount_receive_deatil_modal" id="viewProjectClosure"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document" style="height: 95%;">
			<div class="modal-content" style="height: 95%;">
				<div class="modal-header">
					<h4 class="modal-title center">View Closure Details</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body dash-modal-body ">
					<div class="container pad-bottom">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">				
								<div class="panel-body">						
									<p><span class="bold  font_14 ">Project Reference Number: </span> <span class="bold blue font_14"><i id="projectReferenceNumber"></i></span></p>
									<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i id="projectStrProjectName"></i></span></p>
									<p><span class="bold  font_16 ">Start Date: </span> <span class="bold blue font_16" id="projectStartDate"></span></p>
									<p><span class="bold  font_16 ">End date: </span> <span class="bold blue font_16" id="projectEndDate"></span></p>								
								</div>
							</div>
						</div>
					</div>
					<div class="container">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="font-weight-bold label-class"><spring:message code="project.closure.date"/>:</label> 
								<span id="closureDate"></span>
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="font-weight-bold label-class"><spring:message code="project.closure.remarks"/>:</label>
								<span id="closureRemark"></span> 
							</div>					
						</div> 				
					</div>
					<div class="container">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">				
							<table id="example" class="table table-striped table-bordered" style="width: 100%">
								<thead class="datatable_thead bold">
									<tr>
										<th style="width:5%;"><spring:message code="serial.no"/></th>
 					    				<th style="width:12%;"><spring:message code="document.documentName"/></th>
										<th style="width:10%;"><spring:message code="task.download" /></th>
									</tr>
								</thead>
								<tbody class="" id="documentDetailsTable">
								</tbody>
							</table>
						</div>
					</div>
					<div class="container pad-top mb-4">
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
										</tr>
									</thead>
									<tbody id="teamDetailsTable">
									</tbody>
								</table>
				    	</fieldset>
    				</div>
				</div>
			</div>
		</div>
	</div>
	<%---------------EOL of View Project Closure Details Action in Closure Request Tile ------%>
			<!--Bhavesh(22-09-23) Modal for Financial Closure Projects  -->
			<div class="modal dash-closure-pending-modal3 dash-modal"
				id="dash-closure-pending-modal3" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myPending3" 
					style="width: 99%; height: 95%;">
					<div class="modal-content pendings" id="myContentPending"
						style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">Pending For Financial Closure</h3>
							<button type="button" class="close" onclick="restorepending3()"
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
						
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
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
										<div id="closedAnotherFilter1">
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

							<%--------------------------- Financial Closure Request Table  [ 16-01-2024 ] ----------------%>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
			            		<div class="panel panel-info">
				            		<div class="panel-heading font_16" style="background: #1e2787 !important;color: #fff;border-color: #1e2787;">
				                        <h4>Financial Closure Reconciled </h4>
				                    </div>
			                    	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
										<fieldset class="fieldset-border">
											<table id="FinancialClosureRequestTable" class="table table-striped table-bordered"
												style="width: 100%">
												<thead class="datatable_thead bold ">	
													<tr>										
								 						<th width="2%"><spring:message code="serial.no" /></th>
														<th width="15%"><spring:message code="Project_Module_Master.projectName" /></th>
														<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>
														<th width="15%"><spring:message code="client.name" /></th>
														<th><spring:message code="project.details.leader" /></th>
														<th><spring:message code="projectMaster.startDate"/></th>
														<th><spring:message code="projectMaster.endDate"/></th>
									                    <th width="15%"><spring:message code="forms.status"/></th>
														<th width="8%"><p>Technical</p> <p>Closure</p><p> Submission</p><p> Date</p></th>
														<th width="8%">Remarks</th>
														<th width="3%"><spring:message code="forms.action"/> </th>
													</tr>
												</thead>
												<tbody id="">
		
												</tbody>
												<tfoot>
													<tr>
														<td colspan="10">
															<div class="legend">
																<div class="legend-item">
																	<div class="legend-color"
																		style="background-color: #009999;"></div>
																	<span> Financial Closure Reconciled</span>
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
		                    <%--------------------------- EOL Financial Closure Request Table  [ 16-01-2024 ] ----------------%>
		                    <%--------------------------- Financial Closure Pending  Table  [ 16-01-2024 ] --------------------%>
						    <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
			            		<div class="panel panel-info">
				            		<div class="panel-heading font_16" style="background: #1e2787 !important;color: #fff;border-color: #1e2787;">
				                        <h4>Pending For Reconciliation/Closure </h4>
				                    </div>
									<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
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
										<div id="closedAnotherFilter1">
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
									</div> -->
									<%--------- End of fields for filters [12-10-2023] ----------%>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
										<fieldset class="fieldset-border">
											<table id="exampleUnderClosureFinance"
												class="table table-striped table-bordered" style="width: 100%">
												<thead class="datatable_thead bold ">
													<tr>
														<th style="width: 2%;"><spring:message code="serial.no" /></th>
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
												<tbody id="financialTableBody">
												<!-- Commented by devesh on 13-10-23 to append table body from ajax call -->
													<%-- <c:forEach items="${FinancialPendingProjectDetails}"
														var="projectdetail" varStatus="loop">
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
															<td class="font_16" style="width: 15%;"
																id="td2_${projectdetail.encProjectId}"><p>
																	<a class="bold" style="color:${rowcolor}"
																		title='Click to View Complete Information'
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
												<!--Bhavesh 22-09-23 added legends Pending for Financial Closure in brown color  -->
												<tfoot>
													<tr>
														<td colspan="11">
															<div class="legend">
																<div class="legend-item">
																	<div class="legend-color"
																		style="background-color: #993300;"></div>
																	<span>Pending for Financial Closure </span>
																</div>
																<div class="legend-item">
																	<div class="legend-color"
																		style="background-color:red;"></div>
																	<span>No Payment Received</span>
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
							<%--------------------------- EOL Financial Closure Pending  Table  [ 16-01-2024 ] --------------------%>
							
							
						</div>
					</div>
				</div>
			</div>

			<!-- End of modal for Pending Closure -->
		
		<%---------------   Add Modal For View Proceeding Action in Closure Request Tile [16-01-2024] ------%>	
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
		
			<!-- Modal for Closed Projects -->
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

								<!-- <h4 class="pad-bottom"> -->
								<h4 class="pad-bottom" id="asOnDateClosedProjects1div"> <!-- Modified by devesh on 16-01-24 to hide filter in closed project tiles -->
									<spring:message code="dashboard.closedprojects.label" />
									since : <span class="orange asOnDateProposals1" id="asOnDateClosedProjects1"></span>
								</h4>

							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 date-bar pad-bottom ">
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
											<!-- <th  width="5%" class="comboBox"></th> -->
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
			</div>
			
					<!-- end Modal For validation   -->
					
			<!--Modal For Pending Payments  -->
            <!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
				id="dash-pending-payments-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                   <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                     <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.pendingpayments.label" />
							</h3>
							<!-- Bhavesh(23-06-2023) added onclick="restoreprop()"  -->
							<button type="button" class="close" onclick="restoreprop()"  data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<!-- Bhavesh(23-06-2023) added resize Button"  -->
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

											 <th scope="col" width="20%">Project Name</th>
										<%-- 	<th scope="col" width="20%"><spring:message
													code="project.projectRefNo" /></th> --%>
											<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>	
											<th width="15%"><spring:message code="client.name" /></th>
													 
													
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

											 <th  width="20%" class=""></th>
							           	<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>   
											<th width="15%" class="comboBox"><spring:message code="client.name" /></th>
													 
													
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
            <!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-invoices-modal dash-modal dash-newProposalList-modal"
				id="dash-pending-invoices-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.pendinginvoices.label" />
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
											<th width="40%" class=""></th>
										<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>
											<th width="10%" class="comboBox">><spring:message
													code="client.name" /></th> 
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


			<!-- Modal for Expenditure -->
			<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-expenditure-modal dash-newProposalList-modal" id="dash-expenditure-modal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true" data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="dashboard.projectwise.expenditure" />
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

						<div class="modal-body ">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<div id="col-md-12">
									<table id="expenditure_dataTable"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>


												<th><spring:message code="dashboard.project.label" /></th>
												<th><spring:message code="dashboard.expenditure.label" /></th>
											</tr>
										</thead>
										<thead class="filters ">
											<tr>

												<th class="textBox"><spring:message
														code="dashboard.project.label" /></th>
												<th class="textBox"><spring:message
														code="dashboard.expenditure.label" /></th>

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
			<!-- End of modal for Expenditure -->

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
			
			<!--Modal For Pending Payments  -->
             <!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
			<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
				id="Progress-Reports-modal-sentByPL" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                  <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                   <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="project.progress.report" />
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
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							
							<div id="sendToPMOReport"></div>
							
								<table id="projectProgressTbSentByPL"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="35%"><spring:message code="Project_Payment_Schedule.projectName" /></th>
										<%-- 	<th width="20%"><spring:message	code="project.projectRefNo" /></th> --%>
											<th width="15%"><spring:message code="label.report.of" /></th>
											<th width="20%"><spring:message code="label.sent.on" /></th>
											<th width="15%"> Action</th>								
										</tr>
									</thead>
									<tbody class=""></tbody>
								</table>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<div class="row pad-top form_btn text-center">
										<button type="button" class="btn btn-primary font_14" id="sendToPMO">
											<span class="pad-right"><i class="fa fa-arrow-up"></i></span>Approve and Send to PMO
										</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%------------ Modal For Monthly Progress Report Sent Back by PMO [21-05-2024] ----%>
			<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
				id="Progress-Reports-modal-backByPMO" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                  <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                   <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="project.progress.report" />
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
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							
							<div id="sendToPMOReport"></div>
							
								<table id="projectProgressTbBackByPMO"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="35%"><spring:message code="Project_Payment_Schedule.projectName" /></th>
										<%-- 	<th width="20%"><spring:message	code="project.projectRefNo" /></th> --%>
											<th width="15%"><spring:message code="label.report.of" /></th>
											<th width="20%"><spring:message code="label.sent.on" /></th>
											<th width="15%"> Action</th>								
										</tr>
									</thead>
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%------------ End of Modal For Monthly Progress Report Sent Back by PMO [21-05-2024] ----%>
<!-- Bhavesh(23-06-2023) added new class-dash-newProposalList-modal  -->
<div class="modal dash-pending-payments-modal dash-modal newProposalList-modal"
				id="Progress-Reports-modal-all" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<!-- Bhavesh(23-06-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
                 <div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
                 <!-- Bhavesh(23-06-2023) added new class-modal-lg and style="height:100%;"  -->
					<div class="modal-content modal-lg " style="height:100%;"> 
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								<spring:message code="project.progress.report" />
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
							<div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">

								<label class="" for="from">Reports for</label> <input class="fromProposal1" type="text"
									id="fromDt" name="from2" readonly /> 
											<!-- Bhavesh(23-06-2023) added class="months1"  -->
												<select class="months1"  id="reportType" class="select2Option">
													
													<option value="0">---- Select Report Type---- </option>
													<option value="1">Reports pending at PL</option>
													<option value="4">Reports pending at Project Incharge</option>
													<option value="2">Reports pending at GC</option>
													<option value="3">Approved Reports</option>

												</select>
												
											
								<button type="button" class="btn btn-success go-btn5" onclick="callGo()">Go</button>
								

							</div>
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="projectProgressTblAll"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="45%"><spring:message code="Project_Payment_Schedule.projectName" /></th>
											<%-- <th width="20%"><spring:message	code="project.projectRefNo" /></th> --%>
											<%-- <th width="15%"><spring:message code="label.report.of" /></th>
											<th width="20%"><spring:message code="label.sent.on" /></th> --%>
											<th width="20%">Last Action</th>	
											<th width="10%">View Report</th>									
										</tr>
									</thead>
									
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!--Modal For Progress Reports End  -->
			
			
			<div class="modal dash-pending-payments-modal dash-modal"
				id="Employee-Details-modal" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								List of employees which are not fully mapped to the project
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
						</div>
							
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="employeeDetails"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											<th width="10%"><spring:message code="employee.EmployeeId"/></th>
											<th width="20%"><spring:message code="employee_Role.employee_Name"/></th>
											<th width="13%"><spring:message code="employee.designation"/></th>
											<%-- <th width="15%"><spring:message code="thirdParty.contact.number" /></th> --%>
											<th width="15%">Mapped Involvement (%)</th>	
											<th width="15%">Non-Mapped Involvement (%)</th>									
										</tr>
									</thead>
									
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			
	<div class="modal amount_receive_deatil_modal" id="empProjectDetails"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content" style="margin-left:10%;width:75%">
				<div class="modal-header"  style="background: #15c2b2 !important;" >
					<h4 class="modal-title  center" id="exampleModalLabel">Employee Project Details</h4>
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
             <table id="empInvTable" class="table table-striped table-bordered example_det "
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
				<div class="modal-footer" id="modelFooter">
					
				</div>
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


<!-------------------------Add Modal of MileStone Exceeded Details [ 21-08-2023 ]  -->
			<div
				class="modal dash-closure-pending-modal1 dash-modal dash-newProposalList-modal"
				id="dash-milestone-exceeded-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1"
					style="width: 99%; height: 95%;">
					<div class="modal-content modal-lg " style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">Milestones Exceeded
								Deadline</h3>
							<button type="button" class="close" onclick="restoreprop()"
								data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn"
									onclick="toggleMaximize()">
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
									<select id="filterSymbol_milestoneExceeded"
										onchange="calculateMilestoneExceededDates()"
										class="inline form-control" style="width: 43%">
										<option value=">=">Less than (<)</option>
										<option value="<=">Greater than (>)</option>
									</select> <label class="" for="from">Last:</label> <select
										id="months_milestoneExceeded"
										onchange="calculateMilestoneExceededDates()"
										class="months1 inline form-control" style="width: 43%">
										<option value="0">Select Months/Year's</option>
										<option value="3">3 Months</option>
										<option value="6">6 Months</option>
										<option value="12">1 Years</option>
										<option value="24">2 Years</option>
									</select>
								</div>
								<div id="milesAnothergcFilter">
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
								<div class=" datatable_row pad-bottom"
									style="padding-top: 25px;">
									<table id="MilestoneExceeded_datatable"
										class="table table-striped table-bordered" style="width: 100%">
										<thead class="datatable_thead bold ">
											<tr>
												<th width="5%"><spring:message code="serial.no" /></th>
												<th width="40%"><spring:message
														code="Project_Module_Master.projectName" /></th>
												<th width="30%"><spring:message
														code="Project_Payment_Schedule.milestoneName" /></th>
												<th width="20%"><spring:message
														code="Expected_CompletionDate" /></th>
												<th width="10%"><spring:message code="forms.action" /></th>
											</tr>
										</thead>
										<tbody class="">
											<c:forEach items="${data2}" var="milestoneData" varStatus="theCount">
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
													<td><a class="font_14 ${row_color }"
														title="Click to View Complete Information"
														onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a>
														<p class="bold ${row_color } font_14 text-left">${milestoneData.strProjectReference}</p></td>
													<td class="">${milestoneData.milestoneName}</td>
													<c:choose>
														<c:when test="${not empty milestoneData.completionDate}">
															<td class="text-center">${milestoneData.completionDate}</td>
														</c:when>
														<c:when
															test="${not empty milestoneData.expectedStartDate}">
															<td class="text-center">${milestoneData.expectedStartDate}</td>
														</c:when>
														<c:otherwise>
															<td></td>
														</c:otherwise>
													</c:choose>
													<td><div class="text-center">
															<a class='btn btn-primary '
																onclick="viewProjectDetails('/PMS/mst/MilestoneReviewMaster/${milestoneData.encMilestsoneId}')">Review</a>
														</div></td>
												</tr>
											</c:forEach>
										</tbody>
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
								</div>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
			</div>
<!----------------------------------- End of modal for milestone exceeded -------------------------------------->
<!------------------------- Add Modal of MileStone Due In One Month [ 21-08-2023 ]  ---------------------------->
			<div
				class="modal dash-closure-pending-modal1 dash-modal dash-newProposalList-modal"
				id="dash-milestone-dueInOneMonth-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1"
					style="width: 99%; height: 95%;">
					<div class="modal-content modal-lg " style="height: 100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">Milestones Due In Next
								One Month</h3>
							<button type="button" class="close" onclick="restoreprop()"
								data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn"
									onclick="toggleMaximize()">
									<span aria-hidden="true"><i
										class="far fa-window-restore fa-2xs"></i></span>
								</button>

							</div>
						</div>

						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class=" datatable_row pad-bottom"
									style="padding-top: 25px;">
									<fieldset class="fieldset-border">
									<table id="datatable_milestoneDueInOneMonth"
										class="table table-striped table-bordered" width="100%">
										<thead class="datatable_thead bold ">
											<tr>
												<th width="5%"><spring:message code="serial.no" /></th>
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
											<c:forEach items="${data1}" var="milestoneData"
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

													<td><a class="font_14 ${row_color }"
														title="Click to View Complete Information"
														onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a>
														<p class="font_14 ${row_color }">${milestoneData.clientName}</p>
														<p class="bold ${row_color } font_14 text-left">${milestoneData.strProjectReference}</p></td>
													<td>${milestoneData.milestoneName}</td>
													<c:choose>
														<c:when test="${not empty milestoneData.completionDate}">
															<td class="text-center">${milestoneData.completionDate}</td>
														</c:when>
														<c:when
															test="${not empty milestoneData.expectedStartDate}">
															<td class="text-center">${milestoneData.expectedStartDate}</td>
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
											<tr >
												<td colspan="5">
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
									</fieldset>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!----------------------------------- End of Modal of MileStone Due In One Month  -------------------------------------->

<%-------------- Get data of closure projects and pending for closure [29-08-2023]   ------------------%>
		 <div id="closureListID">
		<c:forEach var="item" items="${closureData}">
			<p class="closureList hidden">${item.projectRefrenceNo}</p>
		</c:forEach>
		</div> 
	<c:forEach var="item" items="${underapprovaldata}">
		<p class="validateList hidden">${item.projectRefrenceNo}</p>
	</c:forEach>
		<c:forEach var="item2" items="${pendingClosureProjectList}">
			<p class="pendingClosureList hidden">${item2.projectRefrenceNo}</p>
		</c:forEach>
<%-------------- EOL Get data of closure projects and pending for closure [29-08-2023]   ------------------%>

		</div>
		
		<!--Bhavesh(10-10-23) Modal for PMO Closure Pending -->
			<div class="modal dash-closure-pending-modal4 dash-modal"
				id="dash-closure-pending-modal4" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg" id="myPending4"
					style="width: 99%; height: 95%;">
					<div class="modal-content pendings" id="myContentPending"
						style="height: 100%;">
						<div class="modal-header">
						<!-- rename changed by varun on 03-11-2023 -->
							<h3 class="modal-title center" id="">Pending For PMO Approval/Closure</h3>
							<button type="button" class="close" onclick="restorepending4()"
								data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>

							</button>

							<div class="button-container custom-margin-left">
								<button type="button" class="close-btn"
									onclick="togglePending4()">
									<span aria-hidden="true"><i
										class="far fa-window-restore fa-2xs"></i></span>
								</button>

							</div>
						</div>

						<div class="modal-body dash-modal-body">
							<!-- 	added below div for table approval pending project by varun on 02-11-2023 -->
								<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top"> -->
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
							<div class="panel panel-info">
							<div class="panel-heading font_16">
										<h4>Project Pending For Approval </h4>
									</div>
										<fieldset class="fieldset-border">
											<table id="newProjects_dataTableln"
												class="table table-striped table-bordered" style="width: 100%">
												<thead class="datatable_thead bold ">
													<tr>
													      <th><spring:message  code="forms.serialNo" /></th>  
														<th><spring:message code="master.name" /></th>
														
														<th><spring:message code="client.name" /></th>
														
														<th><spring:message
																code="dashboard.project.startDate" /></th>
														<th><spring:message
																code="projectMaster.endDate" /></th>
														<th><spring:message
																code="dashboard.lable.outlay" /></th>
														<!-- 	<th></th> -->	
														
													</tr>
												</thead>
											</table>
										</fieldset>
							</div> 
							
						</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"></div>
							<!-- changes in div to get the table in heading format by varun on 02-11-2023 -->
					<!-- 		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top"> -->
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
					<div class="panel panel-info  ">
									<div class="panel-heading font_16">
										<h4> Project Pending For Closure</h4>
									</div>
								<fieldset class="fieldset-border">
									<table id="exampleUnderClosurePMO"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="3%"><spring:message  code="forms.serialNo" /></th>
										<th width="22%"><spring:message code="master.name" /></th>
										<%-- 	<th width="15%"><spring:message
																		code="project.projectRefNo" /></th>
												<th width="15%"><spring:message
													code="dashboard.client.name" /></th> --%>
											<%--(11-10-23) repalced publicationdetail.Organization with client.name   --%>
											<th width="15%"><spring:message
													code="client.name" /></th>
												<th width="7%"><spring:message
																		code="project.details.leader" /></th>
											<th width="7%"><spring:message code="projectMaster.startDate" /></th>
											<th width="8%"><spring:message code="projectMaster.endDate" /></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="5%"><spring:message
													code="dashboard.lable.outlay" /></th>
											
											<!-- End of Columns -->

										</tr>
									</thead>
									
									<tbody id="ClosureRequestPMO">
									
									<c:forEach items="${underClosureCountPMO}"
												var="projectdetail" varStatus="loop">
												<tr class="golden-text">
													<td style="width: 3%;">${loop.count}</td>
													<td class="font_16 golden-text " style="width: 22%;"
														id="td2_${projectdetail.encProjectId}"><p>
															<a class="bold"
																title='Click to View Complete Information'
																onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')"><span
																class="golden-text">${projectdetail.strProjectName} <br> ${projectdetail.projectRefrenceNo} </span></a>
														</p></td>
													<td class="font_16 golden-text " style="width: 15%;"
														id="td2_${projectdetail.encProjectId}">
														<p class="font_14 ">${projectdetail.clientName}</p>
													</td>
													<td style="width: 7%;"><span class="font_16 ">${projectdetail.strPLName}</span></td>
													<td class="golden-text" style="width: 7%;">${projectdetail.startDate}</td>
													<td class="golden-text" style="width: 8%;">${projectdetail.endDate}</td>
													
													<td class="total-text" style="width: 5%;" align="left" class=""><span>${projectdetail.strTotalCost}
													</span></td>
													
												</tr>
											</c:forEach> 
											</tbody> 
									 <!--Bhavesh 10-00-23 added tfoot to count total  -->
										<tfoot>
											<tr >
												
												<td colspan="7">
      <div class="legend">
        
       
        <div class="legend-item">
          <div class="legend-color" style="background-color: #cc9900;"></div>
          <span>Closure Initiated</span>
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
			</div>

			<!-- End of modal for Pending PMO -->
			
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
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4">
								<input type="hidden" id="projectDetailClose"/>
								<label class="font_16" for="projectName">Project Name :</label> 
								<select id="projectNameForDocumentTile"
									class="form-select select2Option" multiple="false" name="projectName" onChange="getProjectDocumentData()">
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
                               	<%-- <div class="input-container" id="dynamicDate">
												<form:select path="documentTypeId" class="select2Option" onchange="ondyncamicchange()"> --%>
												<div class="input-container" id="dynamicDate">
                    				<select id="calenderYearForDocumentTile" class="select2Option" onChange="getProjectDocumentData()">
                    				<option value="0">Select</option>
									<c:forEach var="calenderYear" items="${calenderYearForDocument}">
							            <option value="${calenderYear}">${calenderYear}</option>
							        </c:forEach>
                              	</select>
                              	</div>
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
		<%----- Trigger the Closure Request Tile If it comes from projectClosure [06-12-2023] --------%>
		<c:if test="${openRequestTile eq true}">
		    <script>
		 		// Open Project Pending For Fianancial Closure Tile When back from project Closure [ 16-01-2024 ]
			    var modalTrigger = document.querySelector('[data-target="#dash-closure-pending-modal3"]');  
			    if (modalTrigger) {	
			    	modalTrigger.click();
			    }
			    var hasShortCode = window.location.search.includes('shortCode=true');
			    if (hasShortCode) {
			        var updatedUrl = window.location.href.replace(/[\?&]shortCode=true/, '');
			        window.history.replaceState({}, document.title, updatedUrl);
			    }
	          </script>
		</c:if>
		<%----- End of Trigger the Closure Request Tile If it comes from projectClosure [06-12-2023] --------%>
	
	<%------------------- When GC Login , Trigger and show the Modal  [28-02-2024] ----------%>
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
	<%------------------- End of When GC Login , Trigger and show the Modal  [28-02-2024] ----------%>





</section>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script
	src="/PMS/resources/app_srv/PMS/global/js/progressReportWorkflow.js"></script>
	
<script
	src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/gcDashboard.js"></script>



<!-- Projects received in last 3 years group wise -->
<!-- <script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'bar' ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {

		countbarGraph = ${projcountbarGraphData};
		var noOfItems = countbarGraph.length;
		///console.log(countbarGraph.length);
		var colors = (countbarGraph[1]);
		//console.log("3 colors"+colors);
		countbarGraph.splice(1, 1);
		//console.log(countbarGraph);
		if (noOfItems > 2) {

			var data = google.visualization.arrayToDataTable(countbarGraph);

			var options = {

				bar : {
					groupWidth : '30%'
				},
				colors : colors,

			};

			var chart = new google.charts.Bar(document
					.getElementById('columnchart_yearwise_projects_count'));

			chart.draw(data, google.charts.Bar.convertOptions(options));
		} else {
			$('#columnchart_yearwise_projects_count').text('No Data Found');
		}
	}
</script> -->
<!-- Projects in CDAC Noida business type wise -->
<!-- 
<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		countProject = ${businesstypeprojectcount};
		//console.log('Test');
		//console.log(countbarGraph);

		var data = google.visualization.arrayToDataTable(countProject);
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
			title : 'Number of Projects',
			titleTextStyle : {
				fontName : 'Calibri',
				fontSize : 16,
			},
			is3D : true,
			width : 500,
			height : 300,
			pieSliceText: 'none',
			pieSliceTextStyle : {
				color : 'black',
				fontSize : 12,
			},
			legend : {
				text : 'value',
				position : 'right',
				textStyle : {
					fontSize : 12,
					color : 'black'
				}
			},

			colors: [  '#857dc9', '#48c1d9'],
		};
		var chart = new google.visualization.PieChart(document
				.getElementById('chart_container_tot_projects'));
		chart.draw(data, options);
	}
</script> -->

<!--End of total projects  -->


<!-- Projects Cost in CDAC Noida business type wise -->

<!-- <script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		costProject = ${businesstypeprojectcost};
		//console.log(costProject);
		var data = google.visualization.arrayToDataTable(costProject);
		var count = data.getNumberOfRows();
		var values = Array(count).fill().map(function(v, i) {
			return data.getValue(i, 1);
		});
		var total = google.visualization.data.sum(values);
		// console.log(total);
		values.forEach(function(v, i) {
			var key = data.getValue(i, 0);
			// console.log(key);
			var val = (data.getValue(i, 1)) / 100000;
			//console.log(val);
			data.setFormattedValue(i, 0, key + ' (\u20B9 ' + (val).toFixed(2)
					+ ' Lakhs' + ')');
		});

		var options = {
			title : 'Project Outlay',
			titleTextStyle : {
				fontName : 'Calibri',
				fontSize : 16,
			},

			is3D : true,
			width : 500,
			height : 250,
			pieSliceText: 'none',
			pieSliceTextStyle : {
				color : 'black',
				fontSize : 12,
			},
			tooltip : {
				text : 'value',
			},
			legend : {
				text : 'value',
				position : 'right',
				textStyle : {
					fontSize : 12,
					color : 'black'
				}
			},

			colors: [  '#857dc9', '#48c1d9'],
		};
		var formatter = new google.visualization.NumberFormat({
			prefix : "\u20B9",
			negativeColor : 'red',
			negativeParens : true,
			pattern : '#,##,###.##'
		});
		formatter.format(data, 1);

		var chart = new google.visualization.PieChart(document
				.getElementById('chart_container_cost_projects'));
		chart.draw(data, options);
	}
</script> -->






<!--Number of employees in CDAC-Noida based on employee type  -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	//google.charts.setOnLoadCallback(drawChart);//updated on 16/6/23 to call function with updated name
	google.charts.setOnLoadCallback(drawEmploymentTypeWiseChart);
	

	  
	
	
	//function drawChart() {
	function drawEmploymentTypeWiseChart() {//function name updated to call on collapse button on 16/6/23
		employmentTypeWiseCount = ${employmentTypeWiseCount};

		//console.log(employmentTypeWiseCount);
		
		var data = google.visualization
				.arrayToDataTable(employmentTypeWiseCount);
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

			pieSliceText : 'value',
			pieSliceTextStyle : {
				color : 'white',
				fontSize : 14,
			},
			//pieStartAngle: 50,
			legend : {
				text : 'value',
				position : 'right',
				//position : 'labeled',//updated by devesh on 16/6/23 for adding labels in piechart
				textStyle : {
					fontSize : 14,
					color : 'red'
				}
			},

			colors : [ '#b36686', '#532aa1', '#90a603', '#3fbbc4','green' ],
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


<!--Gender wise Employees  -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);  
	
	
	function drawChart() {
		barGraphEmployees = ${empgenderbarGraphData};
	
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

			colors : [ '#e31b7f', '#0291d9' ],
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-emp1'));
		
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
<!--End of Gender wise Employees  -->


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



<!-- Expenditure -->
<script type="text/javascript">
	function initialzeExpenditureChart() {

		expenditureTable();
		
		
	}
</script>


<!-- Business type Wise Projects -->
<script type="text/javascript">
	$(document).ready(function() {
		/* console.log('Test'); */
		initialzeBusinessTypeWiseProjectsChart();
	});

	function initialzeBusinessTypeWiseProjectsChart() {

		var s = JSON.parse('${projects}');
		//console.log(s);
		var keys = [];
		var finalColors = [ "#69a305", "#eb4034" ];
		// console.log(finalColors);
		$.each(s, function(key, value) {
			$.each(value, function(key, value) {
				keys.push(key);
			});
		});

		var businessTypes = keys;
		//console.log(businessTypes);
		//chart_Business_type_Wise_Projects

		$('#chart_Business_type_Wise_Projects').html('');
		$('#chart_Business_type_Wise_Projects')
				.append(
						'<div id="chart_Business_type_Wise_Projects_c1" class="whole owl-carousel owl-theme "></div>');

		var finalStr = "";

		for (var i = 0; i < businessTypes.length; i++) {
			var divStr = "<div id='chart_Business_type_Wise_Projects_c1_C"+i+"' class='whole item'></div>";
			finalStr += divStr;
		}

		$('#chart_Business_type_Wise_Projects').append(finalStr);

		$
				.each(
						s,
						function(key, value) {

							//console.log('index'+key);					 
							//console.log(value);
							var dataArray = new Array();
							var headerArray = new Array();
							headerArray.push("Project Name");
							headerArray.push("Project Cost");
							dataArray.push(headerArray);
							$.each(value, function(key, value) {
								$.each(value, function(key, value) {

									var innerArray = new Array();
									innerArray.push(value.projectName);
									innerArray.push(value.outlay);
									dataArray.push(innerArray);
								});
							});

							var colors = [ finalColors[key] ];
							//console.log(dataArray);
							google.charts.load('current', {
								packages : [ 'corechart', 'bar' ]
							});
							google.charts.setOnLoadCallback(drawChart);
							function drawChart() {

								var data = google.visualization
										.arrayToDataTable(dataArray);
								var count = data.getNumberOfRows();
								var values = Array(count).fill().map(
										function(v, i) {
											return data.getValue(i, 1);
										});
								var total = google.visualization.data
										.sum(values);
								// console.log(total);
								values.forEach(function(v, i) {
									var key = data.getValue(i, 0);
									// console.log(key);
									var val = (data.getValue(i, 1)) / 100000;
									//console.log(val);
									data.setFormattedValue(i, 0, key
											+ ' (\u20B9 ' + (val).toFixed(2)
											+ ' Lakhs' + ')');
								});
								var options = {
									title : businessTypes[key] + ' Projects',
									titleTextStyle : {
										color : 'blue',
										fontSize : 16,
										bold : true
									},
									width : 900,
									height : 300,

									legend : {
										position : 'none'
									},

									vAxis : {
										title : 'Project Cost',
										viewWindow : {
											min : 0,
											max : value
										}
									},

									hAxis : {
										textPosition : 'none'
									},
									bar : {
										groupWidth : '30%'
									},

									colors : colors
								};
								var formatter = new google.visualization.NumberFormat(
										{
											prefix : "\u20B9",
											negativeColor : 'red',
											negativeParens : true,
											pattern : '#,##,###.##'
										});
								formatter.format(data, 1);

								var chart = new google.visualization.ColumnChart(
										document
												.getElementById('chart_Business_type_Wise_Projects_c1_C'
														+ key));
								chart.draw(data, options);
							}

						});

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

	    if (selectedYear > 0) {
	      var firstDate = new Date(selectedYear, 0, 1); // January 1st
	      var lastDate = new Date(selectedYear, 11, 31); // December 31st

	     
	      var formattedFirstDate = $.datepicker.formatDate('dd/mm/yy', firstDate);
	      var formattedLastDate = $.datepicker.formatDate('dd/mm/yy', lastDate);

	    
	      calculateyearDates(formattedFirstDate,formattedLastDate,'fromvalidated','tovalidated','go-btnvalidate',0);
	  } else {
	      
	      console.log('Please select a valid year.');
	    }
	  }

	  
// for income 
	  var incomecurrentYear = new Date().getFullYear();
    var incomeyearDropdown = $("#incomecalendarYearDropdown");
    for (var i = 0; i < 5; i++) {
	    var year = incomecurrentYear - i;
	    incomeyearDropdown.append('<option value="' + year + '">' + year + '</option>');
	  } 
	  function getYearIncomeRange() {
		  $(".months1").val(0) ;
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
<!-- Bhavesh(23-06-2023) added style for resize button  -->
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


