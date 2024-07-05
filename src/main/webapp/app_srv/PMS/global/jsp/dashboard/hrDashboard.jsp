
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/dashboards.css">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/owl.carousel.min.css">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/owl.theme.min.css">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/owl.transitions.min.css">
<script src="/PMS/resources/app_srv/PMS/global/js/new/owl.carousel.js"></script>

<style>
    #testTbl th, td { white-space: nowrap; }
    

#testTbl_wrapper  table.dataTable{

margin : 0px !important;}

.panel-group .panel-heading a:after {
  content: '-';
  float: right;
}

.panel-group .panel-heading a.collapsed:after {
  content: '+';
}
    
</style>

<style>
    #testTb2 th, td { white-space: nowrap; }
    

#testTb2_wrapper  table.dataTable{

margin : 0px !important;}

.panel-group .panel-heading a:after {
  content: '-';
  float: right;
}

.panel-group .panel-heading a.collapsed:after {
  content: '+';
}
    
</style>

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




		<div class="hr_dashboard">
<!-- hide the question mark by varun on 04-07-23 -->
			<div class="icon-tbar hide">
				<a href="/PMS/resources/app_srv/PMS/global/images/HR_Workflow.png"
					target="_blank" class="question" title="Help"><i
					class="fa fa-question-circle "></i></a>
			</div>
			<div class="row padded tiles">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
					<a  onclick="openEmployee()">
						<div class="info-box bg-cyan  new-tiles-hr new-tiles-cyan">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-cyan ">
									<div class="icon ">
										<!-- <i class="material-icons">playlist_add_check</i> -->
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/newEmp.png"/>
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<div class="text">
												<spring:message code="dashboard.total.employee.label" />
											</div>

											<div class="number" >${employeeCount}</div>
											<div>at CDAC : ${employeeAtCDACCount}</div>
											<!-- added for showing deputed at others on employee type by devesh on 14/6/23 -->
											<%-- <div>at Client Site : ${employeeAtClientCount}</div> --%>
											<div>at Client Site : ${employeeAtCLIENTCount}</div>
											<div>NULL : ${employeeAtOthersCount}</div>
											<!-- End -->
											<div class="pull-right" >(As on Date)</div>
										</div>

									</div>
								</div>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
					<a data-toggle="modal" data-target="#dash-new-joinee-modal">
						<div class="info-box bg-light-green new-tiles-hr new-tiles-green">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-green ">
									<div class="icon ">
										<!-- <i class="material-icons">playlist_add_check</i> -->
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/newEmp1.png"/>
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<div class="text">
												<spring:message code="dashboard.hr.new.joining" />
											</div>

											<div class="number" id="joinCount">${NewJoineeCount}</div>
											
										</div>
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
											<span class=" white asOnDateProposals1" id="newJoinDate"> since: <span
											class="bold">${startDate}</span></span> <input type="hidden" id="startDate" value="${startDate}">
										<input type="hidden" id="endDate" value="${endDate}">
									</div>

									</div>
								</div>
							</div>
						</div>
					</a> 
				</div>
				
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
					<a data-toggle="modal" data-target="#dash-resigned-emp-modal">
						<div class="info-box bg-pink  new-tiles-hr new-tiles-pink">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
								<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 zero icon-pink ">
									<div class="icon ">
										<!-- <i class="material-icons">playlist_add_check</i> -->
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/ED_dash/empResigned.png"/>
									</div>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-xs-8 zero">

									<div class="content">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
											<%-- <div class="text">
												<spring:message code="dashboard.hr.resignation" />
											</div> --%>
											<div class="text">
												Total Relieved
											</div><!-- Name Modified by devesh on 19-10-23 -->

											<div class="number" id="resignCount">${resignedEmployees}</div>
											
										</div>
										<!-- added the asOnDateProposals1 on 3-07-2023 varun -->
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
											<span class="white asOnDateProposals1" id="newResDate"> since: <span
											class="bold">${startDate}</span></span>
									</div>

									</div>
								</div>
							</div>
						</div>
					</a> 
				</div>	
			</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
				<div class=" col-lg-8 col-md-8 col-xs-12 col-sm-12 ">
					<div class="outer-w3-agile col-lg-12 col-md-12 col-xs-12 col-sm-12  ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							
							<div id="owl-demo" class="owl-carousel owl-theme">
							
							<div class="item">
						<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.employment.type.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp"></div>
								</div>
							</div>
						</div>
					<div class="item">
					<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.group.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="groupWise_Employees_PieChart"></div>
								</div>
							</div>
						</div>
						
						<div class="item">
							<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.gc.gender.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp1"></div>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.hr.category.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp2"></div>
								</div>
							</div>
						</div>
						<div class="item">
						<div class="panel panel-info panel-glance">
							<div class="panel-heading font_eighteen">
							<spring:message code="dashboard.hr.comparison.graph" />
						</div>
						<div class="panel-body">
							<div id="comparison_chart"></div>
						</div>
					</div>
					</div>
						</div>
					</div>
				<!--// Pie-chart -->
				</div>
			</div>
					<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 pad-top ">
					<div class="panel panel-default panel-glance">

						<div class="panel-body " style="background: #ececec !important;">
							<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
								<a href="${pageContext.request.contextPath}/mst/employeeMaster"
									target="_blank" style="width: 100%;">
									<div class="serviceBoxHRdash">
										<div class="service-content">
											<div class="service-icon">
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/HR_dash/add_emp.png"/>
											</div>
											<h3 class="title">Add New Employee</h3>

										</div>
									</div>
								</a>
							</div>
							<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
								<a
									href="${pageContext.request.contextPath}/mst/EmployeeSalaryMaster"
									target="_blank" style="width: 100%;">
									<div class="serviceBoxHRdash yellow">
										<div class="service-content">
											<div class="service-icon">
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/HR_dash/update_salary.png"/>
											</div>
											<h3 class="title">Update Salary</h3>

										</div>
									</div>
								</a>
							</div>
							<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
								<div class="serviceBoxHRdash purple">
									<div class="service-content">
										<div class="service-icon">
										<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/HR_dash/contract.png"/>
										</div>
										<h3 class="title">
											<span >Contract Expiring in
												next <span class="dark-grey-font font_16 bold"><select
													id="selectedMonth" onchange="generateContractExpReport()">
														<c:forEach begin="0" end="12" var="val">
															<option value="${val}">${val}</option>
														</c:forEach>
												</select></span> Month
											</span>
										</h3>

									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				
			
</div>
			</div>
		<%-- <div class="row">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top outer-w3-agile tiles">
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 tiles emp_links">


							<div class="info-box bg-pink">
								<a href="${pageContext.request.contextPath}/mst/employeeMaster"
									target="_blank" style="width:100%;">
									<div class="col-md-4 zero">
										<div class="icon">
											<i class="material-icons">person_add</i>
										</div>
									</div>
									<div class="col-md-8 pad-top-double ">
										<div class="content ">
											<span class="white font_16 bold ">Add New Employee </span>
										</div>
									</div>
								</a>
							</div>
						</div>

						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 tiles emp_links">
							<div class="info-box bg-purple-dark">
								<a
									href="${pageContext.request.contextPath}/mst/EmployeeSalaryMaster"
									target="_blank" style="width:100%;">
									<div class="col-md-4 zero">
										<div class="icon">
											<i class="material-icons">beenhere</i>
										</div>
									</div>
									<div class="col-md-8 pad-top-double">
										<div class="content ">
											<span class="white font_16 bold ">Update Salary</span>
										</div>
									</div>
								</a>
							</div>
						</div>


					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 tiles emp_links ">
						<div class="info-box bg-yellow-dark">

							<div class="col-md-4 zero">
								<div class="icon">
									<i class="material-icons">announcement</i>
								</div>
							</div>
							<div class="col-md-8 pad-top-double">
								<div class="content ">
									<span class="white font_16 bold ">Contract Expiring in
										next <span class="dark-grey-font font_16 bold"><select
											id="selectedMonth" onchange="generateContractExpReport()">
												<c:forEach begin="0" end="12" var="val">
													<option value="${val}">${val}</option>
												</c:forEach>
										</select></span> Month
									</span>
								</div>
							</div>

						</div>
					</div>
				</div>
				</div> --%>
				<div class="row">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12  ">
				<!--2nd panel of tiles  -->

							<div class="col-md-8 col-lg-8 col-sm-12 col-xs-12  ">
						<div class="panel panel-info panel-glance">
							<!-- <div class="panel-heading font_eighteen bold">
								Employee</div> -->
							<div class="panel-heading bold">
							    <span class="font_eighteen">Employee</span>
							    <span style="color: #e0ede0;font-size: 16px;">&nbsp;<span id="dateDisplay"></span></span>
							</div><!-- Modified by devesh on 13-10-23 to display Last updated At-->
								
							<div class="panel-body " style="background: #ececec;">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 align-items ">
								<div class="col-md-6 col-sm-6 col-lg-6 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#Employee-Details-modal">
									<div class="serviceBoxHREmpBox">
										<div class="service-count" id="empDetailsCount"></div>
										<div class="service-icon">
											<i class="fa fa-users"></i>
										</div>
										<div class="service-content">
											<h3 class="title">Non-Mapped Employees</h3>
											
											
										</div>
									</div>
									</a>
								</div>
								<div class="col-md-6 col-sm-6 col-lg-6 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#Employee-UnderUti-Details-modal">
									<div class="serviceBoxHREmpBox purple">
										<div class="service-count" id="empUnderUtilCount"></div>
										<div class="service-icon">
											<i class="fa fa-cog"></i>
										</div>
										<div class="service-content">
											<h3 class="title">Under Utilization Employees</h3>
											
											
										</div>
									</div>
									</a>
								</div>
							</div>
							</div>
							</div>
							</div>
							
					<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12  ">
				<div class="panel panel-default panel-glance"
					style="background: #ececec !important;">

					<div class="panel-body zero">
						

							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad-bottom-zero">
							<%------------ Add a scrollToTop function [12-10-2023] ------------------------%>
									<a class="" href="#" data-image-id="" data-toggle="modal" onclick="scrollToTop()"
								data-title="" data-target="#dash-closedProjectList-modal">
									<div class="card card-stats card-stats-dash" style="min-height: 125px; border: 4px solid #f59829; border-radius: 10px 40px 10px 40px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #ffe0bc;border: 4px solid #f59829;">
												<!-- <i class="material-icons">backspace</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closed_projects1.png"/>
											</div>
											<p class="card-category bold"
												title="Click Here to View Technically and Financially Closed Projects">Closed
												Projects</p>
											<h3 class="card-title" id="closedProjectsOnDate">
												${closedProjectsCount}
												<!--  <small>GB</small> -->
											</h3>
										</div>
										<div class="card-footer">


											<div class="stats">
												<span> <span class="pad-top ">since:</span> <span
													class="bold asOnDateProposals1" id="asOnDateClosedProjects"
													style="font-size: 14px; color: #4d4b4b;">${startRange}</span>
												</span>

											</div>


										</div>
									</div>
								</a>
								
							</div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pad-top-zero">
								<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-closure-pending-modal">
									<div class="card card-stats card-stats-dash" style="min-height: 125px;border: 4px solid #ad6327; border-radius: 10px 40px 10px 40px;">
										<div class="card-header  card-header-icon">
											<div class="card-icon" style="background: #efd1b9; border: 4px solid #ad6327;">
												<!-- <i class="material-icons">access_time</i> -->
												<img class="img-responsive"	src="/PMS/resources/app_srv/PMS/global/images/dashboard/closure_pending.png"/>
											</div>
											<!-- <p class="card-category bold"
												title="Closing date passed.Closure still pending.">Closure/Extension Pending</p> -->
											<p class="card-category bold"
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
							
							</div>
							</div>
							</div>
						
						</div>
					</div>
			
					<!-- 		<div class="row">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top outer-w3-agile tiles">
						 <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#Employee-Details-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header card-header-yellow card-header-icon">
											<div class="card-icon">
												<i class="material-icons">note_add</i>
											</div>
											<p class="card-category" title="List of not mapped employees"> Non-Mapped Employees
											<h3 class="card-title card-count" id="empDetailsCount">
												
										
											</h3>
										</div>
										
									</div>
								</a>
							</div> 

						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
								<a class="" href="#" data-image-id="" data-toggle="modal"
									data-title="" data-target="#Employee-UnderUti-Details-modal">
									<div class="card card-stats" style="min-height: 125px;">
										<div class="card-header card-header-blue card-header-icon">
											<div class="card-icon">
												<i class="material-icons">note_add</i>
											</div>
											<p class="card-category" title="List of under utilization Employees"> Under Utilization Employees
											<h3 class="card-title card-count" id="empUnderUtilCount">
												
										
											</h3>
										</div>
										
									</div>
								</a>
							</div> 

						
						
					
					</div>
				</div> -->
				
			<%-- 	<div class="row">
				 <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top outer-w3-agile tiles">
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-closedProjectList-modal">
								<div class="card card-stats">
									<div class="card-header card-header-danger card-header-icon">
										<div class="card-icon">
											<i class="material-icons">backspace</i>
										</div>
										<p class="card-category"
											title="Projects closed during the period">Closed Projects</p>
										<h3 class="card-title" id="closedProjectsOnDate">
											${closedProjectsCount}
											<!--  <small>GB</small> -->
										</h3>
									</div>
									<div class="card-footer">


										<div class="stats">
											<span> <span class="pad-top">since:</span> <span
												class="bold" id="asOnDateClosedProjects"
												style="font-size: 14px; color: #4d4b4b;"> </span>
											</span>

										</div>


									</div>
								</div>
							</a>
						</div>
						
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							<a class="" href="#" data-image-id="" data-toggle="modal"
								data-title="" data-target="#dash-closure-pending-modal">
								<div class="card card-stats" style="height: 100px;">
									<div class="card-header card-header-blue card-header-icon">
										<div class="card-icon">
											<i class="material-icons">access_time</i>
										</div>
										<p class="card-category"
											title="Closing date passed.Closure still pending.">Closure
											Pending</p>
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
			
						

						
						
						
						
					</div>
				</div> --%>

            <!-- by khushboo -->
            <!-- for technical group -->
            		<div class="row">
                          <div
							class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
							<div class="panel panel-info  ">
								<div class="panel-heading font_16">
									<spring:message code="dashboard.designation.distribution.tech.label" /><!--Updated on 22/6/23 for modifying label  -->
								</div>
								<div class="panel-body">
<div >
		<table id="testTb2" class="table table-striped table-bordered" style="width: 100%">
			<c:forEach var="employeeCountByGroupDesignationTech" items="${employeeCountByGroupandDesignationTechnical}" varStatus="loop">

				<c:choose>
					<c:when test="${loop.index==0}">
                         <thead>
							<tr class="my-custom-scrollbar-thead datatable_thead">
								<th class="bold font_16">
								${employeeCountByGroupDesignationTech.key}</th>
								<c:forEach var="counter"
								items="${employeeCountByGroupDesignationTech.value}">
								<th id="${counter}">${counter}</th>
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
									<td class="bold font_16">
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
    
    
   <!--  for support group -->
            
			<div class="row">
                <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double pad-bottom-double">
					<div class="panel panel-info  ">
						<div class="panel-heading font_16">
							<spring:message code="dashboard.designation.distribution.support.label" /><!-- updated on 22/6/23 for modifying label value -->
						</div>
						<div class="panel-body">
                           <div >
							<table id="testTbl" class="table table-striped table-bordered" style="width: 100%">
									
								<c:forEach var="employeeCountByGroupDesignation" items="${employeeCountByGroupandDesignationSupport}"
												varStatus="loop">

												<c:choose>
													<c:when test="${loop.index==0}">
                                                      <thead>
														<tr class="my-custom-scrollbar-thead datatable_thead">
															<th class="bold font_16">
																${employeeCountByGroupDesignation.key}</th>
															<c:forEach var="counter"
																items="${employeeCountByGroupDesignation.value}">
																<th id="${counter}">${counter}</th>
															</c:forEach>

														</tr>
                                                      </thead>
													</c:when>
												 <c:otherwise>
                                                 <c:if test="${loop.index==1}">
                                                  <tbody>
                                                 </c:if>
													<tr>
													  <td class="bold font_16">
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
			<div class="row">

				<!-- Employee Count---Distribution based on employement type, gender and category -->
				<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12 pad-top">
					<div id="owl-demo" class="owl-carousel owl-theme">
					<div class="item">
					<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.group.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="groupWise_Employees_PieChart"></div>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.employment.type.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp"></div>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.gc.gender.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp1"></div>
								</div>
							</div>
						</div>
						<div class="item">
						<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen">
									<spring:message code="dashboard.hr.category.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp2"></div>
								</div>
							</div>
						</div>
					</div>
				</div>


			</div>
			
			<div class="row">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
					<div class="panel-heading font_16"> <h4>Group-wise & Employment-Type-wise Employees</h4></div>
					<div class="panel-body">
							<div id="col-md-12">
							<table id="employee_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
									<th>S.No.</th>
										<th><spring:message code="dashboard.hr.employee.label" /></th>
										<th><spring:message code="dashboard.group.label" /></th>
										<th><spring:message code="dashboard.employee.type.label" /></th>
										<th>Designation</th>
										<th><spring:message code="employee.dateofJoining" /></th>
										<th><spring:message code="employee.deputedat" /></th><!-- added by devesh on 15/6/23 for deputed column -->
										
									</tr>
								</thead>
								<thead class="filters ">
								
		<tr>   
		<th></th>
		 <th class="textBox"><spring:message code="dashboard.hr.employee.label" /></th>	                                                            
				<th class="comboBox" id="employeeGroupSelect"><spring:message code="dashboard.group.label" /></th>
					<th class="comboBox" id="employmentTypeSelect"><spring:message code="dashboard.employee.type.label" /></th>
				<th class="comboBox" id="employeeDesignationSelect">Designation</th>
				<th><spring:message code="employee.dateofJoining" /></th>
				<th class="comboBox" id="employeeDeputedSelect"><spring:message code="employee.deputedat" /></th><!-- added for deputed at filter on 15/6/23 -->
			
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
<!-- 	</div> -->
</section>
<!-- Modal Body -->
<!-- Modal for Resignations -->
<div class="modal hrDash-newEmployee-modal"
	id="hrDash-newEmployee-modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false"
	data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title center" id="">Group-wise New Joining</h3>
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"> </span><span class="sr-only">Close</span>
				</button>
			</div>

			<div class="modal-body ">

				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<!-- Tabs with Background on Card -->
					<div class="card ">
						<div class="card-header">
							<ul
								class="nav nav-tabs nav-tabs-neutral justify-content-center hr_emp_tabs"
								role="tablist" data-background-color="blue">
								<li class="nav-item"><a class="nav-link " data-toggle="tab"
									href="#eGov" role="tab">e-Governance</a></li>
								<li class="nav-item"><a class="nav-link " data-toggle="tab"
									href="#Academics" role="tab">Academics</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#BDPM" role="tab">BDPM</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#DC" role="tab">Data Centre</a></li>

								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#embedded" role="tab">Embedded</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#HIS" role="tab">HIS</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#NLP" role="tab">NLP</a></li>

							</ul>
						</div>
						<div class="card-body">
							<!-- Tab panes -->
							<div class="tab-content text-center">
								<div class="tab-pane active" id="eGov" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td>1.</td>
														<td>Subhanshi Yadav</td>
														<td>Consolidated Contract</td>
														<td>20/08/2019</td>
													</tr>
													<tr>
														<td>2.</td>
														<td>Sonali Choudhary</td>
														<td>Consolidated Contract</td>
														<td>20/08/2019</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane " id="Academics" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="BDPM" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="tab-pane" id="DC" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>

								<div class="tab-pane" id="embedded" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="tab-pane" id="HIS" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="tab-pane" id="NLP" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Joining</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
					<!-- End Tabs on plain Card -->


				</div>


			</div>
		</div>
	</div>
</div>

<!-- Modal for expiring employees -->

<div class="modal dash-pending-payments-modal dash-modal"
				id="dash-contract-expiring-modal" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								 Contract expiring 
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>
						
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

							 <h4 class="pad-bottom">
								
								Contract expiring in next <select id="selectedMonth">
								  <c:forEach begin="0" end="11" var="val">
								  <option value="${val}">${val}</option>
								  </c:forEach>
								 </select> Month
								  <button type="button" onclick="getContractExpDetail()" class="btn btn-success go-btn1 pad-left">Go</button>
							</h4> 

						</div> 
							
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="ContractExpEmpDet"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" /></th>
											
											<%-- <th width="20%"><spring:message code="employee_Role.group_Name" /></th> --%>
											<th width="10%"><spring:message code="employee.EmployeeId"/></th>
											<th width="20%"><spring:message code="employee_Role.employee_Name"/></th>
											<%-- <th width="13%"><spring:message code="employee.designation"/></th> --%>
																		
										</tr>
									</thead>
									
									<%-- <thead class="filters ">
										<tr>
											<th width="5%"></th>
											<th width="20%" class="comboBox"><spring:message code="employee_Role.group_Name" /></th>
											<th width="10%"></th>
											<th width="20%"></th> 
											<th width="13%"></th>
											<th width="15%"></th> 
											<th width="15%"></th>									
										</tr>
									</thead> --%>
									
									<tbody class=""></tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>

<!--End of Modal for New Employees  -->

<!-- modal for resigned Emp -->
<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-resigned-emp-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<!-- <h3 class="modal-title center" id="">
								Resigned Employees
							</h3> -->
							<h3 class="modal-title center" id="">
								Relieved Employees
							</h3><!-- Name Modified by devesh on 19-10-23 -->
							<button type="button" class="close" onclick="restoredash()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>
						<input type="hidden" id="asOnDateResign" value="${startRange}">
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<!-- <h4 class="pad-bottom">
									Resigned
									since : <span class="orange asOnDateProposals1" id="asOnDateResign1"></span>
								</h4> -->
								<h4 class="pad-bottom">
									Relieved
									since : <span class="orange asOnDateProposals1" id="asOnDateResign1"></span>
								</h4><!-- Name Modified by devesh on 19-10-23 -->

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<label class="" for="from">From</label> <input class="fromProposal1" type="text"
									id="fromRes" name="from" readonly /> <label class="" for="to">To</label>
								<input class="toProposal1" type="text" id="toRes" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn-resign">Go</button>

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="resigned_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="20%"><spring:message code="employee_Role.group_Name" />
											</th>
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
											<th width="20%" class="comboBox"><spring:message code="employee_Role.group_Name" /></th>
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
				id="dash-resigned-emp-modal_New" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<!-- <h3 class="modal-title center" id="">
								Resigned Employees
							</h3> -->
							<h3 class="modal-title center" id="">
								Relieved Employees
							</h3><!-- Name Modified by devesh on 19-10-23 -->
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>
						<input type="hidden" id="asOnDateResign" >
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<!-- <h4 class="pad-bottom">
									Resigned
									since : <span class="orange" id="asOnDateResignNew"></span>
								</h4> -->
								<h4 class="pad-bottom">
									Relieved
									since : <span class="orange" id="asOnDateResignNew"></span>
								</h4><!-- Name Modified by devesh on 19-10-23 -->

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<label class="" for="from">From</label> <input type="text"
									id="fromResign" name="from" readonly /> <label class="" for="to">To</label>
								<input type="text" id="toResign" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn-resign-new">Go</button>

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="resigned_dataTable_new"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="20%"><spring:message code="employee_Role.group_Name" />
											</th>
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
											<th width="20%" class="comboBox"><spring:message code="employee_Role.group_Name" /></th>
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

<!-- modal for new Joinee employees -->
<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
		id="Employee-UnderUti-Details-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">List of under utilization Employees</h3>
					<button type="button" class="close" onclick="restoredash()" data-dismiss="modal">
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

<!-- added the maximize and minimize button on 3-07-23 varun -->
<div class="modal dash-pending-payments-modal dash-modal dash-newProposalList-modal"
		id="Employee-Details-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					<h3 class="modal-title center" id="">List of not mapped Employees</h3>
					<button type="button" class="close" onclick="restoredash()" data-dismiss="modal">
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

<!-- added maximize and minimize button by 3-07-2023 varun -->
<div class="modal dash-newProjectList-modal dash-modal dash-newProposalList-modal"
				id="dash-new-joinee-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
					<div class="modal-content modal-lg " style="height:100%;">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								New Joinees
							</h3>
							<button type="button" class="close" onclick="restoredash()" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
							<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						</div>
						<input type="hidden" id="asOnDateJoinee" value="${startRange}">
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									New Joinees
									since : <span class="orange asOnDateProposals1" id="asOnDateJoin">${startDate}</span>
								</h4>

							</div>
							
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
                     	
						<!-- 	Added the filter on 09-06-2023 -->
							
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">

								<label class="font_16" for="from">Last:</label> <select class="months1"  id="months" onchange="calculateDates(this.value,'fromJoin','toJoin','go-btn-join',0)" class="inline form-control" style="width:80%">
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
									
									
								<input type="text" id="toJoin" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn-join">Go</button>

							</div> -->

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="joinee_dataTable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="20%"><spring:message code="employee_Role.group_Name" />
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
									<thead class="filters ">
												<tr>
											<th width="5%"></th>
											<th width="20%" class="comboBox"><spring:message code="employee_Role.group_Name" /></th>
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
				id="dash-new-joinee-modal-New" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								New Joinees
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>
						<input type="hidden" id="asOnDateJoinee" value="${startRange}">
						<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<h4 class="pad-bottom">
									New Joinees
									since : <span class="orange" id="asOnDateJoinee1"></span>
								</h4>

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

								<label class="" for="from">From</label> <input type="text"
									id="fromJoining" name="from" readonly /> <label class="" for="to">To</label>
									
									
								<input type="text" id="toJoining" name="to" readonly /> &nbsp; &nbsp;
								<button type="button" class="btn btn-success go-btn-join-new">Go</button>

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="joinee_dataTable_new"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold ">
										<tr>
											<th width="5%"><spring:message code="forms.serialNo" />
											</th>
											<th width="20%"><spring:message code="employee_Role.group_Name" />
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
									<thead class="filters ">
												<tr>
											<th width="5%"></th>
											<th width="20%" class="comboBox"><spring:message code="employee_Role.group_Name" /></th>
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

	
<!-- modal to display Employee Data By Employment Type -->

<div class="modal dash-newProjectList-modal dash-modal"
				id="employeeDataByEmploymentType" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">								
								<spring:message code="dashboard.employment.type.label" />
							</h3> 
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>
						
						<div class="modal-body dash-modal-body">							
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<table id="employmentType_datatable"
									class="table table-striped table-bordered" style="width: 100%">
									<thead class="datatable_thead bold">
													<tr>
														<th>Employee Id</th>
														<th>Name</th>
														<th>Email</th>
														<th>Designation</th>
														<th>Group</th>
														<th id="agencyId_col">Agency</th>
													</tr>
												</thead>
							<thead class="filters ">
								<tr>
									<th></th>
									<th ></th>
									<th></th>
									<th></th>
									<th class="comboBox"><spring:message
											code="employee_Role.group_Name" /></th>
									<th ></th>
									
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
	
	
	<!-- Modal For Emp Details -->
	<div class="modal amount_receive_deatil_modal " id="empDetailModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Employee Detail</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body emp_detail_modal">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
             <table id="datatable1" class="table table-striped table-bordered example_det"
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
	<div class="modal dash-employee-modal second-level-modal" id="dash-employee-modal"
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

						<div class="modal-body ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<%-- <h4 class="pad-bottom">
									<spring:message code="dashboard.total.employee.label" />
									:<span class="orange">${employeeCount}</span>
								</h4> --%>


							</div>
							
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel-group" id="accordionGroupClosed" role="tablist" aria-multiselectable="true">
 
								<div class="panel panel-info  ">
									<div class="panel-heading font_16 " role="tab" id="headingOne">
									<h4 class="panel-title">
										<a class="collapsed" id="intro-switch" style="color:black;"  data-toggle="collapse" href="#multiCollapse1"  aria-expanded="true" aria-controls="multiCollapse1">
										<spring:message code="dashboard.employment.type.label" /></a>
									</h4>
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse1" role="tabpanel" aria-labelledby="headingOne">
										<div class="panel-body">
										<div id="chart-container-empNew"></div>
										</div>
									</div> 
								</div>
								
							
								<div class="panel panel-info  " >
									<div class="panel-heading font_16" role="tab" id="headingTwo">
									<h4 class="panel-title">
						<a class="collapsed" style="color:black;" data-toggle="collapse" href="#multiCollapse2"  aria-expanded="false" aria-controls="multiCollapse2">
						<spring:message code="dashboard.gc.gender.distribution.label" />  </a>	
						</h4>			
									</div>
									<div  class="panel-collapse collapse" id="multiCollapse2" role="tabpanel" aria-labelledby="headingTwo">
										<div class="panel-body">
										<div id="chart-container-empNew1"></div>
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

											<table id="testTblNew"
												class="table table-bordered table-striped mb-0">


												<%-- <c:forEach var="employeeCountByGroupDesignation"
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
 --%>
									<%-- 						</tr>
														</c:otherwise>
													</c:choose>


												</c:forEach> --%>



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
											<!-- <th></th>	 -->																		
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
			
			
		
		
		<div class="modal dash-newProjectList-modal dash-modal"
				id="dash-employee-category-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-keyboard="false" data-backdrop="static">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-title center" id="">
								 Employees Details
							</h3>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true"> </span><span class="sr-only">Close</span>
							</button>
						</div>
						<%-- <input type="hidden" id="asOnDateResign" value="${startRange}"> --%>
							<div class="modal-body dash-modal-body">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

								<table id="employeeCategoryWiseTbl" class="table table-striped">
									<thead>
										<tr class="datatable_thead bold ">
											<th width="5%"><spring:message code="forms.serialNo"/></th>
											<th width="10%"><spring:message code="employee.EmployeeId"/></th>
											<th width="20%"><spring:message code="employee_Role.employee_Name"/></th>
											<th width="13%"><spring:message code="employee.designation"/></th>
											<th width="12%"><spring:message code="employee.Employment.type"/></th>
											<th width="20%"><spring:message code="group.groupName"/></th>
										</tr>
									</thead>
									<thead  class="filters">
										<tr>										
											<th></th>
											<th></th>
											<th></th>
											<th class="comboBox"></th>
											<th class="comboBox"></th>
											<th class="comboBox"></th>
											<!-- <th></th>	 -->																		
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
<!-- Modal for Resignation -->
<div class="modal hrDash-resignation-modal"
	id="hrDash-resignation-modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false"
	data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title center" id="">Group-wise Resignations</h3>
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true"> </span><span class="sr-only">Close</span>
				</button>
			</div>

			<div class="modal-body ">

				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<!-- Tabs with Background on Card -->
					<div class="card ">
						<div class="card-header">
							<ul
								class="nav nav-tabs nav-tabs-neutral justify-content-center hr_emp_tabs"
								role="tablist" data-background-color="blue">
								<li class="nav-item"><a class="nav-link " data-toggle="tab"
									href="#eGov" role="tab">e-Governance</a></li>
								<li class="nav-item"><a class="nav-link " data-toggle="tab"
									href="#Academics" role="tab">Academics</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#BDPM" role="tab">BDPM</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#DC" role="tab">Data Centre</a></li>

								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#embedded" role="tab">Embedded</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#HIS" role="tab">HIS</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#NLP" role="tab">NLP</a></li>

							</ul>
						</div>
						<div class="card-body">
							<!-- Tab panes -->
							<div class="tab-content text-center">
								<div class="tab-pane active" id="eGov" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td>1.</td>
														<td>Alpha Rashmi Das</td>
														<td>Consolidated Contract</td>
														<td>30/06/2019</td>
													</tr>

												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane " id="Academics" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="BDPM" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="tab-pane" id="DC" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>

								<div class="tab-pane" id="embedded" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="tab-pane" id="HIS" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="tab-pane" id="NLP" role="tabpanel">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class=" table-responsive " id="">

											<table id="example_joining"
												class="table table-bordered table-striped table-hover"
												style="width: 100%">
												<thead class="my-custom-scrollbar-thead datatable_thead_new">
													<tr>
														<th>S.No</th>
														<th>Name</th>
														<th>Employment Type</th>
														<th>Date of Relieving</th>

													</tr>
												</thead>
												<tbody class="my-custom-scrollbar-tbody" id="">
													<tr>
														<td></td>
														<td></td>
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
					<!-- End Tabs on plain Card -->


				</div>


			</div>
		</div>
	</div>
</div>

<!-- added the library icon size on 3-07-23 varun -->
<head>
				<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha384-******************" crossorigin="anonymous">

				</head>

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
					<button type="button" class="close" onclick="restoredash()" data-dismiss="modal">
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
					<%-------------  Add Year-wise Filter [12-10-2023] ----------------%>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">	
							<div
								class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
								<label class="font_16" for="from">Last:</label>
								<select class="months1 inline form-control monthsClosedProjects"  id="months" onchange="calculateDates(this.value,'from1','to1','goClosed-btn',0)" style="width:80%">
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
							<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;"><strong>OR</strong></h3>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 date-bar">
								<label class="" for="from">From</label> <input class="fromProposal1"  type="text"
								id="from1" name="from1" readonly /> <label class="pad-left"
								for="to">To</label> <input class="toProposal1" type="text" id="to1" name="to1"
								readonly />
								<button type="button" class="btn btn-success goClosed-btn pad-left">Go</button>
							</div>
					</div>
					<%-------------  End of Filters [12-10-2023] --%>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
						<%---------------- ADD some Columns in the closed project table [12-10-2023]---------------%>
						<table id="closedProjects_dataTable"
							class="table table-striped table-bordered table-responsive">
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
	<!--Added by devesh on 22-09-23 to get underclosure value  -->
		<c:forEach var="item" items="${closureData}">
			<p class="underclos hidden">${item.projectRefrenceNo}</p>
		</c:forEach>
	<!-- End of value -->
	<!-- Modal for Pending Closur -->
	<div class="modal dash-closure-pending-modal dash-modal dash-newProposalList-modal"
		id="dash-closure-pending-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg mymodal1" style="width:99%; height:95%;">
			<div class="modal-content modal-lg " style="height:100%;">
				<div class="modal-header">
					
					<h3 class="modal-title center" id="">List Of Projects Pending
						for Technical Closure</h3>
					<button type="button" class="close" onclick="restoredash()"  data-dismiss="modal">
						<span aria-hidden="true"> </span><span class="sr-only">Close</span>
					</button>
					<div class="button-container custom-margin-left">
                    <button type="button" class="close-btn" onclick="toggleMaximize()">
                        <span aria-hidden="true"><i class="far fa-window-restore fa-2xs"></i></span>
                    </button>
							
						</div>
						
				</div>

				<div class="modal-body dash-modal-body">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12"></div>

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar ">

                        <div
								class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom">
			
								<select id="filterClosureSymbol" onchange="calculateClosureDates()" class="inline form-control" style="width:32%">
								<option value=">=">Less than (<) </option>
								<option value="<=">Greater than (>)</option>
								
								</select>
			
			
								<label class="" for="from">Last:</label> <select class="months1"  id="monthsClosure" onchange="calculateClosureDates()" class="inline form-control" style="width:32%">
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
											<th width="24%" ><spring:message code="master.name" /></th> 
											<%-- <th width="40%"><spring:message code="master.name" /></th> --%>
											<th width="14%"><spring:message code="client.name" /></th>
											<th width="7%"><spring:message code="project.details.leader" /></th>
											<th width="8%"><spring:message code="projectMaster.startDate" /></th>
											<th width="9%"><spring:message code="projectMaster.endDate" /></th>
											<!-- Added by devesh on 21-09-23 to add Cdac outlay and status columns -->
											<th width="6%"><spring:message code="dashboard.lable.outlay" /></th>
											<th width="16%">Status</th>
											<!-- End of Columns -->

										</tr>
									</thead>
									<thead class="filters">
										<tr>
											<th width="2%"></th>
											<th width="14%" class="comboBox"><spring:message code="group.groupName" /></th>
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
        
       
        <!-- <div class="legend-item">
          <div class="legend-color" style="background-color: #808080;"></div>
          <span>Date elapsed </span>
        </div> -->
        <!-- Added by devesh on 22-09-23 to show legend for both colors -->
        <div class="legend-item">
          <div class="legend-color" style="background-color: #cc9900;"></div>
          <span>Date elapsed and Closure Initiated</span>
        </div>
        <div class="legend-item">
          <div class="legend-color" style="background-color: #808080;"></div>
          <span>Date elapsed but Closure Not Initiated</span>
        </div>
        <!-- End of legend -->
        <!-- Add more legend items as needed -->
      </div>
    </td>
											</tr>
										</tfoot>
								</table>

						<%-- <table id="pendingCLosure_dataTable"
							class="table table-striped table-bordered" style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th width="5%"><spring:message code="forms.serialNo" /></th>
									
									 <th width="20%"><spring:message code="group.groupName" /></th> 
									<th width="30%"><spring:message code="master.name" /></th>
											<th class="textBox"><spring:message
																		code="project.projectRefNo" /></th>
												<th width="15%"><spring:message
													code="dashboard.client.name" /></th>
                                         
                                         
									 <th class="textBox"><spring:message
											code="project.details.leader" /></th> 
									<th width="10%"><spring:message
											code="projectMaster.startDate" /></th>
									<th width="10%"><spring:message
											code="projectMaster.endDate" /></th>

								</tr>
							</thead>
							<thead class="filters ">
								<tr>
									<th></th>
									<th class="comboBox" id="expenditureSelect"><spring:message
											code="dashboard.group.label" /></th>
									<th class="">
										<spring:message code="dashboard.project.label" />
									</th>
									<th class="">
										<spring:message code="dashboard.expenditure.label" />
									</th>

								</tr>
							</thead>
							<tbody class="">
							</tbody>
						</table> --%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End of modal for Pending Closure -->

<!--End of Modal Body  -->

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


<script
	src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/hrDashboard.js"></script>
	
	<!--Number of employees in CDAC-Noida based on employee type  -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		employmentTypeWiseCount = ${employmentTypeWiseCount};
		// Display the total no of employees count in pie chart by varun on 22-08-2023
		var totalEmployeeCount = employmentTypeWiseCount.reduce(function (sum, row) {
		    var value = parseFloat(row[1]);
		    return sum + (isNaN(value) ? 0 : value);
		}, 0);
		
	
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
			width : 700,
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

			colors : [ '#b36686', '#532aa1', '#90a603', '#3fbbc4',],
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-emp'));
		
		  // The select handler. Call the chart's getSelection() method
		  function selectHandler() {
			 // alert("selectHandler");
		    var selectedItem = chart.getSelection()[0];
		    console.log(selectedItem);
		    if (selectedItem) {
		      var value = data.getValue(selectedItem.row, 0);
		   		 loadEmployeeDataByEmploymentType(value);
		    }
		  }
		  
		// Listen for the 'select' event, and call my function selectHandler() when
		  // the user selects something on the chart.
		  google.visualization.events.addListener(chart, 'select', selectHandler);
		
		chart.draw(data, options);
		// Display the total no of employees count in pie chart by varun on 22-08-2023
		var totalEmployeeMessage = document.createElement('p');
		totalEmployeeMessage.textContent = 'Total Employees (' + totalEmployeeCount + ')';
		totalEmployeeMessage.style.textAlign = 'center'; 
		totalEmployeeMessage.style.color = 'black';
		totalEmployeeMessage.style.fontSize = '16px';
		totalEmployeeMessage.style.fontWeight = 'bold';
		totalEmployeeMessage.style.position = 'absolute'; 
		totalEmployeeMessage.style.top = '57px'; 
		totalEmployeeMessage.style.left = '50%'; 
		totalEmployeeMessage.style.transform = 'translateX(-82%)';
		document.getElementById('chart-container-emp').appendChild(totalEmployeeMessage);
		
	}
</script>

<!-- Employees in CDAC Noida Group wise -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		chartData = ${groupWiseEmployeeCount};
		// Display the total no of employees count in pie chart by varun on 22-08-2023
		 var totalgroupEmployeeCount = chartData.reduce(function (sum, row) {
		    var value = parseFloat(row[1]);
		    return sum + (isNaN(value) ? 0 : value);
		}, 0); 
		//console.log("Total Employee Count:", totalgroupEmployeeCount);
		//groupWiseEmployeeCount.push(['Total Employee', totalgroupEmployeeCount]);
		var colors = (chartData[0]);

		chartData.splice(0, 1);

		var data = google.visualization.arrayToDataTable(chartData);
		console.log(data);
		var count = data.getNumberOfRows();
		console.log(count);
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
			width : 700,
			height : 300,

			pieSliceText : 'value',
			pieSliceTextStyle : {
				color : 'black',
				fontSize : 14,
			},
			legend : {
				text : 'value',
				position : 'right',
				textStyle : {
					fontSize : 12,
					color : 'red'
				}
			},

			colors : colors,
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('groupWise_Employees_PieChart'));
		
		// The select handler. Call the chart's getSelection() method
		  function selectHandler() {
		    var selectedItem = chart.getSelection()[0];
		    console.log(selectedItem);
		    if (selectedItem) {
		      var value = data.getValue(selectedItem.row, 0);
		   		 loadEmployeeDataBy(value);
		    }
		  }
		  
		// Listen for the 'select' event, and call my function selectHandler() when
		  // the user selects something on the chart.
		  google.visualization.events.addListener(chart, 'select', selectHandler);
		  chart.draw(data, options);
		// Display the total no of employees count in pie chart by varun on 22-08-2023
		   var totalgroupMessage = document.createElement('p');
		  totalgroupMessage.textContent = 'Technical Group - Total Employees (' + totalgroupEmployeeCount + ')';
		  totalgroupMessage.style.textAlign = 'center'; 
		  totalgroupMessage.style.color = 'black';
		  totalgroupMessage.style.fontSize = '16px';
		  totalgroupMessage.style.fontWeight = 'bold';
		  totalgroupMessage.style.position = 'absolute'; 
		  totalgroupMessage.style.top = '57px'; 
		  totalgroupMessage.style.left = '50%'; 
		  totalgroupMessage.style.transform = 'translateX(-82%)';
			document.getElementById('groupWise_Employees_PieChart').appendChild(totalgroupMessage); 
	}
</script>


<!-- <script type="text/javascript">
	google.charts.load('current', {
		packages : [ 'corechart', 'bar' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Groups', 'Employees'],
          ['e-Gov',     97],
          ['HIS',      1],
          ['BDPM',  1],
          ['Embedded', 0],
          ['Data Centre',   0],
          ['NLP',    0]
        ]);

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
			title : 'GroupWise Employees',
			titleTextStyle : {
				fontName : 'Calibri',
				fontSize : 16,
			},

			width :450,
			height : 300,
			legend : {
				text : 'value',
				position : 'right',
				textStyle : {
					fontSize : 14,
					color : 'red'
				}
			},
			pieSliceText : 'value',
			pieSliceTextStyle : {
				color : 'black'
			},
			is3D: true,
			colors : [ '#f0c86f', '#f2e879','#eacbe0','#b1d39c','#a8e6dd','#bcdaff' ]
		};
		
		var chart = new google.visualization.PieChart(document
				.getElementById('groupWise_Employees_PieChart'));
		chart.draw(data, options);

	}
</script>
 -->


<!-- <script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	
	function drawChart() {
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
	</script> -->

<!--Gender wise Employees  -->

<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);  
	
	
	function drawChart() {
		barGraphEmployees = ${empgenderbarGraphData};
		// Display the total no of employees count in pie chart by varun on 22-08-2023
		var totalEmployeeCount = barGraphEmployees.reduce(function (sum, row) {
		    var value = parseFloat(row[1]);
		    return sum + (isNaN(value) ? 0 : value);
		}, 0);
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
			width : 700,
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
		// Display the total no of employees count in pie chart by varun on 22-08-2023
		var totalEmployeeMessage = document.createElement('p');
		totalEmployeeMessage.textContent = 'Total Employees (' + totalEmployeeCount + ')';
		totalEmployeeMessage.style.textAlign = 'center'; 
		totalEmployeeMessage.style.color = 'black';
		totalEmployeeMessage.style.fontSize = '16px';
		totalEmployeeMessage.style.fontWeight = 'bold';
		totalEmployeeMessage.style.position = 'absolute'; 
		totalEmployeeMessage.style.top = '57px'; 
		totalEmployeeMessage.style.left = '50%'; 
		totalEmployeeMessage.style.transform = 'translateX(-82%)';
		document.getElementById('chart-container-emp1').appendChild(totalEmployeeMessage);
	}
	
</script>
<!--End of Gender wise Employees  -->

<!--Number of employees in CDAC-Noida based on Category  -->
<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		employeesCategoryWiseCount = ${employeesCategoryWiseCount};	
		var totalEmployeeCount = employeesCategoryWiseCount.reduce(function (sum, row) {
		    var value = parseFloat(row[1]);
		    return sum + (isNaN(value) ? 0 : value);
		}, 0);
		var data = google.visualization
				.arrayToDataTable(employeesCategoryWiseCount);
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
			width : 700,
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

			pieHole : 0.4,
			colors : [ '#7f559e', '#cc9508', '#56941f', '#c24058' ],
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('chart-container-emp2'));
		chart.draw(data, options);
		
		// The select handler. Call the chart's getSelection() method
		  function selectHandler() {
		    var selectedItem = chart.getSelection()[0];
		    console.log(selectedItem);
		    if (selectedItem) {
		      var value = data.getValue(selectedItem.row, 0);
		   		 loadEmployeeDataByCategory(value);
		    }
		  }
		  
		// Listen for the 'select' event, and call my function selectHandler() when
		  // the user selects something on the chart.
		  google.visualization.events.addListener(chart, 'select', selectHandler);
		  var totalEmployeeMessage = document.createElement('p');
			totalEmployeeMessage.textContent = 'Total Employees (' + totalEmployeeCount + ')';
			totalEmployeeMessage.style.textAlign = 'center'; 
			totalEmployeeMessage.style.color = 'black';
			totalEmployeeMessage.style.fontSize = '16px';
			totalEmployeeMessage.style.fontWeight = 'bold';
			totalEmployeeMessage.style.position = 'absolute'; 
			totalEmployeeMessage.style.top = '57px'; 
			totalEmployeeMessage.style.left = '50%'; 
			totalEmployeeMessage.style.transform = 'translateX(-82%)';
			document.getElementById('chart-container-emp2').appendChild(totalEmployeeMessage);
	}
</script>

<!--Comparison graph for joining v/s resignations year-wise  -->

<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'bar' ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {
		var dataKeys='';
	
		employeesYearWiseCount = ${employeesYearWiseCount};
		//console.log("employeesYearWiseCount=  "+employeesYearWiseCount);
	
		var data = google.visualization
				.arrayToDataTable(employeesYearWiseCount);
		var count = data.getNumberOfRows();
		var values1 = Array(count).fill().map(function(v, i) {
			return data.getValue(i, 0);
		});
		
	
	
		var options = {
			width : 700,
			height : 300,

			bar : {
				groupWidth : '25%'
			},

			vAxis : {
				title : 'Number Of Employees'
			},
			hAxis : {
				title : 'Year'
			},
			seriesType : 'bars',
			series : {
				2 : {
					type : 'line'
				}
			},
			colors : [ '#b5c520', '#df7316', '#d90d17' ]

		};

		var chart = new google.visualization.ComboChart(document
				.getElementById('comparison_chart'));
		chart.draw(data, options);
		
		console.log(data);
		  // The select handler. Call the chart's getSelection() method
		  function selectHandler() {
			  console.log( chart.getSelection());
		    var selectedItem = chart.getSelection()[0];
		    console.log(selectedItem);
		    if (selectedItem) {
		    	/* console.log(data.getValue(selectedItem.row, 0));
		    	console.log(selectedItem.column); */
		    	 loadEmployeeJoinResignDetails(data.getValue(selectedItem.row, 0),selectedItem.column);
		    }
		  }
		  
		// Listen for the 'select' event, and call my function selectHandler() when
		  // the user selects something on the chart.
		  google.visualization.events.addListener(chart, 'select', selectHandler);
		
		
	}
</script>

<!-- added the style for maximize and minimize button by varun on 03-07-2023 -->
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


	#pendingClosure_dataTable td {
		white-space: normal;
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

