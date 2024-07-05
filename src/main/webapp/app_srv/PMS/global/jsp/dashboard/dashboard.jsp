
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/dashboards.css">

<!-- Bhavesh(08-09-2023) added stylesheet link  --> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" integrity="sha384-******************" crossorigin="anonymous">

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

		<div class="row padded tiles">

			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">

				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-grpwise-bargraph-modal">
					<div class="info-box  bg-c-green ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 zero">
								<div class="icon">
									<i class="material-icons">&#xE065;</i>
								</div>
							</div>
							<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 zero">

								<div class="content">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
										<div class="text bold">
											<spring:message code="dashboard.project.label" />
										</div>
										<div class="text-small bold ">
											<sec:authentication property="principal.assignedGroupName" />
										</div>
										<div class="number">${projectCount}</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</a>

			</div>
			<c:if test="${not empty employeeCount}">
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-employee-modal">
					<!--Bhavesh(03-08-23) changed bg-c-cyan to bg-c-yellow  <div class="info-box bg-c-yellow"> -->
					<div class="info-box bg-c-yellow">
					<!-- Bhavesh(03-08-23) -->
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 zero">
								<div class="icon">
									<i class="material-icons">&#xE63d;</i>
								</div>
							</div>
							<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 zero">

								<div class="content">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
										<div class="text bold">
											<spring:message code="dashboard.employee.label" />
										</div>
										<div class="text-small bold ">
											<sec:authentication property="principal.assignedGroupName" />
										</div>
										<div class="number">${employeeCount}</div>
									</div>
								</div>
							</div>
						</div>						
					</div>
				</a>
			</div>
			</c:if>
			
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">

				<a class="" href="#" data-image-id="" data-toggle="modal"
					data-title="" data-target="#dash-income-modal">
					<!--Bhavesh(03-08-23) changed bg-c-gren to bg-purple-dark  <div class="info-box bg-c-yellow"> -->
					<div class="info-box bg-purple-dark">
					<!-- Bhavesh(03-08-23) -->
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 zero">
								<div class="icon">
									<i class="material-icons">&#xE8e5;</i>
								</div>
							</div>
							<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 zero">

								<div class="content">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
										<div class="text bold">
											<spring:message code="dashboard.income.label" />
										</div>
										<div class="text-small bold ">
											<sec:authentication property="principal.assignedGroupName" />
										</div>

										<div class="number" id="incomeOndate">${income} Lakhs</div>
									</div>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<p class="pull-right">
											<span class="white">since: <span class=" bold black"
												id="asOnDate"> </span>
											</span> <span><input class="input-field" type="hidden"
												id="dtStartDate"></span>
										</p>

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
					<!--Bhavesh(03-08-23) changed bg-c-pink to bg-c-yellow  <div class="info-box bg-c-yellow"> -->
					<div class="info-box bg-c-yellow">
					<!-- Bhavesh(03-08-23) -->
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">

							<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 zero">
								<div class="icon">
									<i class="material-icons">&#xE164;</i>
								</div>
							</div>
							<div class="col-md-9 col-lg-9 col-sm-9 col-xs-9 zero">

								<div class="content">
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class="text bold">
											<spring:message code="dashboard.expenditure.label" />
										</div>
										<div class="text-small bold ">
											<sec:authentication property="principal.assignedGroupName" />
										</div>
										<div class="number convert_lakhs" id="expenditureOndate">${expenditure}</div>
									</div>
								</div>
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
									<span class="pull-right"> <span class="white">since:
									</span><span class="black bold" id="asOnDateExp"></span> <span>
											<input class="input-field " type="hidden" id="dtExpStartDate">
									</span>
									</span>
								</div>
							</div>
						</div>
					</div>
				</a>
			</div>

		</div>
	
		

		<div
			class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-bottom-double pad-top">
			<!--Qick Links  -->
			<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 tiles">

				<div class="panel panel-info panel-glance">
					<div class="panel-heading font_eighteen">
						<spring:message code="dashboard.gc.quicklinks.label" />
					</div>
					<div class="panel-body">
					<sec:authorize access="hasAuthority('READ_PROPOSAL_MST')">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  ">
							<a class=""
								href="${pageContext.request.contextPath}/ViewApplicationDetails"
								target="_blank" title="Add/Edit Proposal">
								<div class="market-update-block clr-block-1">
									<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 market-update-right">
										<i class="fa fa-pencil-square fa-4x" aria-hidden="true"> </i>
									</div>
									<div class="col-md-8 col-lg-8 col-sm-8 col-xs-12 market-update-left">

										<div class="text">
											<spring:message code="dashboard.add.edit.proposal" />
										</div>
									</div>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('READ_PROJECT_MST')">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<a class=""
								href="${pageContext.request.contextPath}/mst/ViewAllProjects"
								target="_blank" title="Edit Project">
								<div class="market-update-block clr-block-2">
									<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 market-update-right">
										<i class="fa fa-files-o"> </i>
									</div>
									<div class="col-md-8 col-lg-8 col-sm-8 col-xs-12 market-update-left">

										<div class="text">
											<spring:message code="dashboard.edit.project" />
										</div>

									</div>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('PROJECT_TEAM_MAPPING')">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<a class=""
								href="${pageContext.request.contextPath}/mst/projectTeamMapping"
								target="_blank" title="Map Employee to a project">
								<div class="market-update-block clr-block-3">
									<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 market-update-right">
										<i class="fa fa-user-plus"> </i>
									</div>
									<div class="col-md-8 col-lg-8 col-sm-8 col-xs-12 market-update-left">

										<div class="text">
											<spring:message code="dashboard.map.employee" />
										</div>
									</div>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('READ_PROJECT_INVOICE_MST')">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<a class=""
								href="${pageContext.request.contextPath}/mst/projectInvoiceMaster"
								target="_blank" title="Invoice Details">
								<div class="market-update-block clr-block-5">
									<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 market-update-right">
										<i class="fa fa-clipboard"> </i>
									</div>
									<div class="col-md-8 col-lg-8 col-sm-8 col-xs-12 market-update-left">

										<div class="text">
											<spring:message code="dashboard.invoice.details" />
										</div>

									</div>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</sec:authorize>
					<sec:authorize
							access="hasAuthority('READ_PROJECT_PAYMENT_RECEIVED_MST')">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<a class=""
								href="${pageContext.request.contextPath}/mst/projectPaymentReceived"
								target="_blank" title="Pyment Received">
								<div class="market-update-block clr-block-4">
									<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 market-update-right">
										<i class="fa fa-inr"> </i>
									</div>
									<div class="col-md-8 col-lg-8 col-sm-8 col-xs-12 market-update-left">

										<div class="text">
											<spring:message code="dashboard.payment.received" />
										</div>

									</div>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</sec:authorize>
					
					</div>
				</div>

			</div>
			<!--End of Quick Links  -->
			
			<!-- Grouped Bar Chart for projects received in last 3 years group-wise -->
			<div class="col-md-8 col-lg-8 col-sm-12 col-xs-12">
				<!-- 
				<div class="row">
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
				</div>
				 -->
			</div>
			<!--End Grouped Bar Chart for projects received in last 3 years group-wise -->
		
		</div>

		<!-- Group Tiles  -->

		<div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 pad-bottom ">

			<div class="panel panel-info panel-glance">
				<div class="panel-heading font_eighteen">
					<spring:message code="dashboard.project.label" />
				</div>
				<div class="panel-body">					
					<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 ">
						<div class="row">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class=" datatable_row pad-bottom">
									<fieldset class="fieldset-border">									
										<div class="table-responsive">
											<table id="example"
												class="table table-striped table-bordered"
												style="width: 100%">
												<thead class="datatable_thead bold ">
													<tr>
														<th style="width: 2%;"><spring:message
																code="serial.no" /></th>
														<th><spring:message code="project.projectRefNo" /></th>
														<th><spring:message
																code="Project_Module_Master.projectName" /></th>
														<th><spring:message
																code="application.businesstype.label" /></th>
														<th><spring:message code="employee_Role.start_Date" /></th>
														<th><spring:message code="employee_Role.end_Date" /></th>
														<th style="width: 10%;"><p>
																<spring:message code="project.details.duration" />
															</p>
															<p>(months)</p></th>
														<th><spring:message code="project.details.leader" /></th>
														<th style="width: 10%;"><p>
																<spring:message code="project.details.totaloutlay" />
															</p>
															<p>(in Lakhs)</p></th>
														<th style="width: 10%;"><p>
																<spring:message code="project.details.amountreceived" />
															</p>
															<p>(in Lakhs)</p></th>
														<th><spring:message code="task.download" /></th>
														<sec:authorize access="hasAuthority('CHANGE_PROJECT_STATUS')">
															<th>
																<spring:message code="forms.action" />
															</th>
														</sec:authorize>

													</tr>
												</thead>
												<thead class="filters">
													<tr>
														<th><i class="fa fa-th-list"></i></th>
														<th class="textBox"><spring:message
																code="project.projectRefNo" /></th>
														<th class="textBox"><spring:message
																code="Project_Module_Master.projectName" /></th>

														<th class="comboBox" id="businessTypeSelect"><spring:message
																code="application.businesstype.label" /></th>
														<th class="comboBox" style="width: 10%;"><spring:message
																code="employee_Role.start_Date" /></th>
														<th class="comboBox" style="width: 10%;"><spring:message
																code="employee_Role.end_Date" /></th>
														<th style="width: 10%;" class="comboBox"><spring:message
																code="project.details.duration" /></th>
														<th class="textBox"><spring:message
																code="project.details.leader" /></th>
														<th style="width: 10%;" class="textBox"><spring:message
																code="project.details.totalcost.lakhs" /></th>
														<th style="width: 10%;" class="textBox"><spring:message
																code="project.details.amountreceived.lakhs" /></th>
														<th><i class="fa fa-download"></i></th>
														<sec:authorize access="hasAuthority('CHANGE_PROJECT_STATUS')">
															<th></th>
														</sec:authorize>
													</tr>
												</thead>
												<tbody class="">
													<c:forEach items="${groupProjectDetails}"
														var="projectdetail" varStatus="theCount">
														<tr id='${projectdetail.encProjectId}'>
															<td></td>
															<td>${projectdetail.projectRefrenceNo}</td>
															<td class="font_16 " style="width: 15%;"><a
																class="bold" title='Click to View Complete Information'
																onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a>
																<p class="font_14 ">
																	<i><a class="orange"
																		title="Click here to view funding org details"
																		data-toggle='modal' data-target='#clientDetails'
																		data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}'
																		class='text-center'>${projectdetail.clientName}</a></i>
																</p> <%-- 	<p class="font_12 "><i><a class="orange" title="Click here to view Funding org details" data-toggle='modal' data-target='#clientDetails' data-whatever='${projectdetail.encClientId}' class='text-center'>${projectdetail.clientName}</a></i></p> --%>
															</td>
															<td>${projectdetail.businessType}</td>
															<td style="width: 10%;">${projectdetail.startDate}</td>
															<td style="width: 10%;">${projectdetail.endDate}</td>
															<td align="center">${projectdetail.projectDuration}</td>
															<td style="width: 10%;"><span class="font_16">${projectdetail.strPLName}</span>
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
																class="convertLakhs"
																id="totalAmt_${projectdetail.encProjectId}">${projectdetail.strTotalCost}
															</span></td>
															<td align="right"><input type="hidden"
																id="hiddenAmt_${projectdetail.encProjectId}"
																value="${projectdetail.strReceivedAmout}" /> <a
																title="Click here to view received details"
																data-toggle='modal' data-target='#amtreceive'
																data-whatever='${projectdetail.encProjectId};${projectdetail.startDate};${projectdetail.strTotalCost}'
																class='text-center'> <span class="convertLakhs"
																	id="amt_${projectdetail.encProjectId}">${projectdetail.strReceivedAmout}
																</span></a></td>
															<%-- 							
 --%>
															<td style="width: 10%;">
																<ul>
																	<c:forEach items="${projectdetail.projectDocument}"
																		var="projectDocument">
																		<li><a
																			class="${projectDocument.classColor}  bold"
																			onclick='downloadDocument("${projectDocument.encNumId}")'>
																				${projectDocument.documentTypeName}</a></li>
																	</c:forEach>

																</ul> <a style="float: right;"
																onclick="viewProjectDetails('/PMS/mst/documentDetails/${projectdetail.encProjectId}')">View
																	All</a>
															</td>
															<sec:authorize
																access="hasAuthority('CHANGE_PROJECT_STATUS')">
																<td>
																	<div class="dropdown show">
																		<a class="btn btn-secondary dropdown-toggle"
																			data-toggle="dropdown"
																			onmouseover="viewProjectStatus(${appdetail.numId})"
																			aria-haspopup="true" aria-expanded="false"> <i
																			class="icon-th-large icon-1halfx blue pad-top"
																			aria-hidden="true"></i>
																		</a>
																		<ul class="dropdown-menu pull-right"
																			aria-labelledby="dropdownMenuLink"
																			id="${appdetail.numId}">
																		</ul>
																	</div>
																</td>
															</sec:authorize>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<!--End of data table-->
									</fieldset>
								</div>
								<!--End of datatable_row-->
							</div>
						</div>
					</div>
					<div class=" hidden" id="groupWiseProject">

						<div class="col-md-12 col-lg-12 pad-top" id="groupWiseProjectDtl">

						</div>
						<div class="hidden " id="groupWiseProject">

							<div class="col-md-12 col-lg-12" id="groupWiseProjectDtl">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Group Tiles  -->
		<!-- Modal for Projects -->
		
		<div class="modal dash-grpwise-bargraph-modal "
			id="dash-grpwise-bargraph-modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true"
			data-keyboard="false" data-backdrop="static">
			<!-- Bhavesh(08-09-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
			<div class="modal-dialog modal-lg myModal1" style="width:99%; height:95%;" >
			 <!-- Bhavesh(08-09-2023) added new class-modal-lg and style="height:100%;"  -->
				<div class="modal-content" style="height:100%;">
					<div class="modal-header">
						<h3 class="modal-title center" id="">
							<spring:message code="dashboard.ongoingprojects.label" />
						</h3>
						<h4 class="center">
							<span class="orange"><sec:authentication
									property="principal.assignedGroupName" /></span>
						</h4>
						<!-- Bhavesh(08-09-2023) added onclick="restoreprop()"  -->
						<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<!-- Bhavesh(08-09-2023) added resize Button"  -->
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
								</span> (in Lakhs)
							</h4>
							<%-- <h5><span class="orange ">&#42; </span><spring:message code="dashbaord.amount.label" />.</h5> --%>

						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="table-responsive">

								<table class="table table-bordered table-striped mb-0" id="">
									<thead class="my-custom-scrollbar-thead datatable_thead">
										<tr>
											<%-- <th><spring:message code="dashboard.serial.shortcode.label" /></th> --%>
											<%-- <th scope="col"><spring:message code="dashboard.group.label" /></th> --%>
											<th scope="col"><spring:message
													code="dashboard.number.projects.label" /></th>
											<th scope="col"><spring:message
													code="dashboard.project.outlay.label" /></th>
										</tr>
									</thead>
									<tbody class="">

										<c:forEach items="${countProjects}" var="count"
											varStatus="loop">
											<tr>
												<%-- <td>${loop.index+1}</td> --%>
												<%-- <td>${count.groupName}</td> --%>
												<td class="center">${count.projectCount}</td>
												<%-- 											<td class="text-right"><span class="red">&#8377;</span> ${count.projectCost}
 --%>
												<td class="text-right currency-inr">${count.projectCost}</td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
							<div class="panel panel-info ">
								<div class="panel-heading font_16">
									<spring:message code="dashboard.businestype.distribution.label" />
								</div>
								<div class="panel-body">								
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
        			<th><h5 class=" blue"><spring:message code="master.endUser"/></h5></th>
        			<td><span class="black" id="endUserName"></span></td>
        			</tr>
								</tbody>
							</table>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
							<h5 class="underline"> Contact Person (For Funding Org)</h5>
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

		<!-- Modal for Employees -->
		<div class="modal dash-employee-modal" id="dash-employee-modal"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title center" id="">
							<spring:message code="dashboard.employee.label" />
						</h3>
						<h4 class="center">
							<span class="orange"><sec:authentication
									property="principal.assignedGroupName" /></span>
						</h4>
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
					</div>

					<div class="modal-body ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

							<h4 class="pad-bottom">
								<spring:message code="dashboard.total.employee.label" />
								:<span class="orange">${employeeCount}</span>
							</h4>


						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info  ">
								<div class="panel-heading font_16">
									<spring:message code="dashboard.employment.type.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp"></div>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
								<div class="panel-heading font_16">
									<spring:message code="dashboard.group.distribution.label" />
								</div>
								<div class="panel-body">
									<div id="chart-container-emp1"></div>
								</div>
							</div>
						</div>

						<div
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
					</div>
				</div>
			</div>
		</div>


		<!-- End of modal for employees -->

		<!-- Modal for Income -->
		<div class="modal dash-income-modal dash-grpwise-bargraph-modal " id="dash-income-modal"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<!-- Bhavesh(08-09-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
			<div class="modal-dialog modal-lg myModal1" style="width:99%; height:95%;" >
				<!-- Bhavesh(08-09-2023) added new class-modal-lg and style="height:100%;"  -->
				<div class="modal-content" style="height:100%;">
					<div class="modal-header">
						<h3 class="modal-title center" id="">
							<spring:message code="dashboard.income.label" />
						</h3>
						<h4 class="center">
							<span class="orange"><sec:authentication
									property="principal.assignedGroupName" /></span>
						</h4>
						<!-- Bhavesh(08-09-2023) added onclick="restoreprop()"  -->
						<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<!-- Bhavesh(08-09-2023) added resize Button"  -->
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
								since <span class="orange" id="asOnDate1"></span>
							</h4> 

						</div> 
						<%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
								<div class="panel-heading font_16">
									<spring:message code="dashboard.income.distribution.label" />
								</div>
								<div class="panel-body ">
									<div id="chart-container-income"
										style="width: 100%; height: 100%;" align='center'></div>
								</div>
							</div>
						</div> --%>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
								<div class="panel-heading font_16"></div>
								<div class="panel-body">
									<div id="col-md-12">
										<table id="income_dataTable"
											class="table table-striped table-bordered"
											style="width: 100%">
											<thead class="datatable_thead bold ">
												<tr>
													<th><spring:message code="forms.serialNo"></spring:message>
													<th><spring:message code="dashboard.group.label" /></th>
													<th><spring:message code="dashboard.project.label" /></th>
													<th><spring:message code="Payment_Received.payment_Date"/></th>
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
		<!-- End of modal for income -->

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
		<div class="modal dash-expenditure-modal dash-grpwise-bargraph-modal" id="dash-expenditure-modal"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<!-- Bhavesh(08-09-2023) added new class-mymodal1 and style="width:99%; height:95%;"  -->
			<div class="modal-dialog modal-lg myModal1" style="width:99%; height:95%;" >
				 <!-- Bhavesh(08-09-2023) added new class-modal-lg and style="height:100%;"  -->
				<div class="modal-content" style="height:100%;">
					<div class="modal-header">
						<h3 class="modal-title center" id="">
							<spring:message code="dashboard.expenditure.label" />
						</h3>
						<h4 class="center">
							<span class="orange"><sec:authentication
									property="principal.assignedGroupName" /></span>
						</h4>
						<!-- Bhavesh(08-09-2023) added onclick="restoreprop()"  -->
						<button type="button" class="close" onclick="restoreprop()" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<!-- Bhavesh(08-09-2023) added resize Button"  -->
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
											class="table table-striped table-bordered"
											style="width: 100%">
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
			</div>
		</div>
		<!-- End of modal for Expenditure -->
	</div>
</section>

<script type="text/javascript" 	src="https://www.gstatic.com/charts/loader.js"></script>

<script src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/dashboard.js"></script>


			
			<!-- Projects received in last 3 years group wise -->
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
<!-- Projects in CDAC Noida business type wise -->

		<!--  <script type="text/javascript">

	      google.charts.load("current", {packages:["corechart"]});
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {
	    	  countProject = ${businesstypeprojectcount}	;
	    	 // console.log('Test');
				//console.log(countbarGraph);
			
			
				 var data = google.visualization
						.arrayToDataTable(countProject); 
				 var count  = data.getNumberOfRows();
	                var values = Array(count).fill().map(function(v, i) {
	                  return data.getValue(i, 1);
	                });
	                var total =  google.visualization.data.sum(values);    
	               // console.log(total);
	                values.forEach(function(v, i) {
	                  var key = data.getValue(i, 0);
	                 // console.log(key);
	                  var val = data.getValue(i, 1);
	                  //console.log(val);
	                  data.setFormattedValue(i, 0, key + ' (' + (val) + ')');
	                });

	        var options = {
	     	      title: 'Number of Projects',
	     	      titleTextStyle: {
	     	         fontName: 'Calibri', 
	     	         fontSize:16, 
	     	       },
	  	          is3D: true,
	  	          width:450,
	  	          height:300,
	  	          pieSliceText: 'value',
	  	   		  pieSliceTextStyle: {
	  	             color: 'black',
	  	             fontSize: 14,
	  	          },
	  	         legend:{
		        	text:'value',
		        	position: 'right',
		        	textStyle: {
		        		fontSize: 14,
		        		color:'red'
		        		       }
		          },
	  	         
	  	          colors: [  '#857dc9', '#48c1d9'],
	  	        };
	        var chart = new google.visualization.PieChart(document.getElementById('chart_container_tot_projects'));
	        chart.draw(data, options);
	      }
	      
	    </script>
	       -->
						<!--End of total projects  -->
						
						
					<!-- Projects Cost in CDAC Noida business type wise -->

		<!--  <script type="text/javascript">

	      google.charts.load("current", {packages:["corechart"]});
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {
	    	  costProject = ${businesstypeprojectcost}	;
				
			
			
				 var data = google.visualization
						.arrayToDataTable(costProject); 
				 var count  = data.getNumberOfRows();
	                var values = Array(count).fill().map(function(v, i) {
	                  return data.getValue(i, 1);
	                });
	                var total =  google.visualization.data.sum(values);    
	               // console.log(total);
	                values.forEach(function(v, i) {
	                  var key = data.getValue(i, 0);
	                 // console.log(key);
	                  var val = data.getValue(i, 1);
	                  //console.log(val);
	                  data.setFormattedValue(i, 0, key + ' (\u20B9 ' + (val).toFixed(2) + ')');
	                });
	                
	        var options = {
	       title: 'Project Outlay',
	       titleTextStyle: {  
   	       fontName: 'Calibri', 
   	       fontSize: 16, 
   	     },
   	 
	        is3D: true,
	        width:470,
	  	    height:300,
	  	    pieSliceText: 'value',
	   		pieSliceTextStyle: {
	             color: 'black',
	             fontSize: 14,
	          },
	          tooltip:{
	        	  text:'value',
	        	  },
	        legend:{
	        	text:'value',
	        	position: 'right',
	        	textStyle: {
	        		fontSize: 14,
	        		color:'red'
	        		       }
	          },
	        
	         colors: [  '#857dc9', '#48c1d9'],
	        };
	        var formatter = new google.visualization.NumberFormat(
	    	         { prefix: "\u20B9",negativeColor: 'red', negativeParens: true, pattern: '#,##,###.##'});
	    	        formatter.format(data, 1);       
	    	      

	        var chart = new google.visualization.PieChart(document.getElementById('chart_container_cost_projects'));
	        chart.draw(data, options);
	      }
	    </script>
	       -->
						<!--End of total project Cost  -->


<!--Number of employees in CDAC-Noida based on employee type  -->
						
 <script type="text/javascript">

	      google.charts.load("current", {packages:["corechart"]});
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {
	    	  employmentTypeWiseCount = ${employmentTypeWiseCount}	;
				
			//console.log(employmentTypeWiseCount);
			
				 var data = google.visualization
						.arrayToDataTable(employmentTypeWiseCount); 
				 var count  = data.getNumberOfRows();
	              var values = Array(count).fill().map(function(v, i) {
	                return data.getValue(i, 1);
	              });
	              var total =  google.visualization.data.sum(values);    
	             // console.log(total);
	              values.forEach(function(v, i) {
	                var key = data.getValue(i, 0);
	               // console.log(key);
	                var val = data.getValue(i, 1);
	                //console.log(val);
	                data.setFormattedValue(i, 0, key + ' (' + (val) + ')');
	              });
	        var options = {
	        		
	          is3D: true,
	          width:800,
	          height:300,
	         
	          pieSliceText: 'value',
	   		  pieSliceTextStyle: {
	              color: 'white',
	              fontSize: 14,
	          },
	          legend:{
		        	text:'value',
		        	position: 'right',
		        	textStyle: {
		        		fontSize: 14,
		        		color:'red'
		        		       }
		          },
	         
	          colors : [ '#b36686', '#532aa1', '#90a603',
							'#3fbbc4' ],
	        };

	        var chart = new google.visualization.PieChart(document.getElementById('chart-container-emp'));
	        chart.draw(data, options);
	      }
	    </script>

<!--End of Number of empoyees in CDAC-Noida based on employee type  -->


						<!-- Employees in CDAC Noida Group wise employee type wise-->
						<script type="text/javascript">
							google.charts.load('current', {
								packages : [ 'corechart', 'bar' ]
							});
							google.charts.setOnLoadCallback(drawChart);
							function drawChart() {
								barGraphEmployees = ${empbarGraphData};
								var data = google.visualization
										.arrayToDataTable(barGraphEmployees);
								
								 var view = new google.visualization.DataView(data);
							     view.setColumns([0, 1,
							                       { calc: "stringify",
							                         sourceColumn: 1,
							                         type: "string",
							                         role: "annotation" },
							                       2,{ calc: "stringify",
								                         sourceColumn: 2,
								                         type: "string",
								                         role: "annotation" },3,{ calc: "stringify",
									                         sourceColumn: 3,
									                         type: "string",
									                         role: "annotation" },4,{ calc: "stringify",
										                         sourceColumn: 4,
										                         type: "string",
										                         role: "annotation" }]); 

								var options = {

									width : 750,
									height : 400,

									legend:{
							        	text:'value',
							        	position: 'right',
							        	textStyle: {
							        		fontSize: 14,
							        		color:'red'
							        		       }
							          },
								
									  vAxis: {
								          title: 'Number of Employees'
								        },
									bar : {
										groupWidth : '25%'
									},
									isStacked : true,
									
									colors : [ '#b36686', '#532aa1', '#90a603',
											'#3fbbc4' ],
								};
							
								
								
								var chart = new google.visualization.ColumnChart(
										document
												.getElementById('chart-container-emp1'));
								chart.draw(view, options); 

								google.visualization.events.addListener(chart,
										'select', selectHandler);

								function selectHandler() {
									var selection = chart.getSelection();
									var message = '';
									for (var i = 0; i < selection.length; i++) {
										var item = selection[i];
										if (item.row != null
												&& item.column != null) {
											var str = data.getFormattedValue(
													item.row, item.column);
											var category = data.getValue(chart
													.getSelection()[0].row, 0)
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
											message += '{row:'
													+ item.row
													+ ',column:'
													+ item.column
													+ '} = '
													+ str
													+ '  The Department is:'
													+ category
													+ ' and EmployeeType is  : '
													+ type + '\n';
										} else if (item.row != null) {
											var str = data.getFormattedValue(
													item.row, 0);
											message += '{row:'
													+ item.row
													+ ', column:none}; value (col 0) = '
													+ str
													+ '  The Category is:'
													+ category + '\n';
										} else if (item.column != null) {
											var str = data.getFormattedValue(0,
													item.column);
											message += '{row:none, column:'
													+ item.column
													+ '}; value (row 0) = '
													+ str
													+ '  The Category is:'
													+ category + '\n';
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
						
						
		<!-- Income -->				
 <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      

      function drawIncomeChart() {
    	 
       var incomePieChart = '';
       var colors ='';
    	
       var startDate = null;
		var endDate = null;
		var selectedDateRange = $('#asOnDate').text().trim();

		if(selectedDateRange){
			if(selectedDateRange.includes("to")){
				var inputArray = selectedDateRange.split('to');
				if(inputArray.length >= 2){
					startDate = inputArray[0].trim();
					endDate = inputArray[1].trim();
				}else if(inputArray.length == 1){
					startDate = inputArray[0].trim();					
				}
			}else{
				startDate = selectedDateRange;				
			}		
		}
    	
    	$.ajax({
    		type : "POST",
    		url : "/PMS/mst/getIncomeByDateGraph",
    		data : {"startDate" : startDate,"endDate":endDate},
    		async: false, 
    		success : function(response) {
    			incomePieChart=response;
    			
    			var temp = response.length-1;    			
    	    	colors = (incomePieChart[temp]);    	    	
    	    	incomePieChart.splice(temp,temp);  
	
    		}
		
			});
    	
    
    	
      var data = google.visualization.arrayToDataTable(incomePieChart); 
      var count  = data.getNumberOfRows();
      var values = Array(count).fill().map(function(v, i) {
        return data.getValue(i, 1);
      });
      var total =  google.visualization.data.sum(values);    
    
      values.forEach(function(v, i) {
        var key = data.getValue(i, 0);
      
        var val = data.getValue(i, 1);
        
        data.setFormattedValue(i, 0, key + ' (\u20B9 ' + (val).toFixed(2) + ')');
      });

        		   var options = {
     	    	        
     	    	          titleTextStyle: {
     	    	     	       fontName: 'Calibri', 
     	    	    	       fontSize: 16, 
     	    	    	        
     	    	    	     },
     	    	    	     
     	    	          width:900,
     	    	    	  height:300,
     	    	    	 
     	    	    	 legend:{
					        	text:'value',
					        	position: 'right',
					        	textStyle: {
					        		fontSize: 14,
					        		color:'red'
					        		       }
					          },
					     pieSliceText: 'value',
     	    	  		 pieSliceTextStyle: {
     	    	             color: 'black'
     	    	         },
     	    	       
     	    	        colors: colors
     	    	       
     	    	
     	    	        };

        		   var formatter = new google.visualization.NumberFormat(
        	    	         {prefix: "\u20B9",negativeColor: 'red', negativeParens: true, pattern: '#,##,###.##'});
        	    	        formatter.format(data, 1);       
        	    	      
        	    	        // Create and draw the visualization.
        	    	        var chart =  new google.visualization.PieChart(document.getElementById('chart-container-income'));
        	    	            
       // var chart = new google.visualization.PieChart(document.getElementById('chart-container-income'));
        // Instantiate and draw our chart, passing in some options.

        function selectHandler() {
          var selectedItem = chart.getSelection()[0];
          if (selectedItem) {
            var group = data.getValue(selectedItem.row, 0);             		 
    		$('#incomeSelect select').val(group).trigger('change');
          
          }
        }

        google.visualization.events.addListener(chart, 'select', selectHandler);   

        chart.draw(data, options);
      }
      
      function initialzeIncomeChart(){
    	 
    	//  drawIncomeChart();
    	  incomeTable();
      }
      
    </script> 
    
    
    
    						
		<!-- Expenditure -->				
 <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      /* google.charts.setOnLoadCallback(initialzeIncomeChart()); */

      function drawExpenditureChart() {
    	 
       var expenditurePieChart = '';
       var colors ='';
    	
    
    	var startDate =  $('#asOnDateExp').text();
    	$('#asOnDateExp1').text(startDate);
    	
    	
    	$.ajax({
    		type : "POST",
    		url : "/PMS/mst/getExpenditureByDateGraph",
    		data : "startDate=" + startDate,
    		async: false, 
    		success : function(response) {
    			//console.log(expenditurePieChart);
    		
    			expenditurePieChart=response;
    			colors = (expenditurePieChart[1]);    	    	
    	    	expenditurePieChart.splice(1,1);  
    			 			
    	    	
	
    		}
		
			});
    	
   // console.log(expenditurePieChart)
    	
      var data = google.visualization.arrayToDataTable(expenditurePieChart); 
      var count  = data.getNumberOfRows();
      var values = Array(count).fill().map(function(v, i) {
        return data.getValue(i, 1);
      });
      var total =  google.visualization.data.sum(values);    
     // console.log(total);
      values.forEach(function(v, i) {
        var key = data.getValue(i, 0);
       // console.log(key);
        var val = data.getValue(i, 1);
        //console.log(val);
        data.setFormattedValue(i, 0, key + ' (\u20B9 ' + (val).toFixed(2) + ')');
      });
        		   var options = {
                        titleTextStyle: {
     	    	     	      fontName: 'Calibri', 
     	    	    	       fontSize: 16, 
     	    	    	       },
     	    	    	     
     	    	         width:900,
     	    	    	 height:300,
     	    	    	 legend:{
					        	text:'value',
					        	position: 'right',
					        	textStyle: {
					        		fontSize: 14,
					        		color:'red'
					        		       }
					          },
                         pieSliceText: 'value',
     	    	  		 pieSliceTextStyle: {
     	    	             color: 'black'
     	    	         },
     	    	        
     	    	        colors: colors
     	    	       
     	    	
     	    	        };


        		   var formatter = new google.visualization.NumberFormat(
        	    	         {prefix: "\u20B9",negativeColor: 'red', negativeParens: true, pattern: '#,##,##,##,###.##'});
        	    	        formatter.format(data, 1);       
        	    	      
        	    	        // Create and draw the visualization.
        	    	        var chart =  new google.visualization.PieChart(document.getElementById('chart-container-expenditure'));
        	    	        
        	    	     // Instantiate and draw our chart, passing in some options.

        	    	        function selectHandler() {
        	    	          var selectedItem = chart.getSelection()[0];
        	    	          if (selectedItem) {
        	    	            var group = data.getValue(selectedItem.row, 0);
        	    	            console.log('The user selected ' + group);
        	    	           
        	    	          /*  var table_row = $('#income_dataTable thead  tr th').eq(0).prevObject;
        	    	    		 var abc = table_row[3];
        	    	    		 console.log(abc); */
        	    	    		 
        	    	    		$('#expenditureSelect select').val(group).trigger('change');
        	    	          
        	    	          }
        	    	        }

        	    	        google.visualization.events.addListener(chart, 'select', selectHandler);   

        	    	            chart.draw(data,options);
      }
      
      function initialzeExpenditureChart(){
    	 
    	  drawExpenditureChart();
    	  expenditureTable();
    	  
      }
      
    </script> 
    <!-- Business type Wise Projects -->
<script type="text/javascript">
$( document ).ready(function() {

	initialzeBusinessTypeWiseProjectsChart();
});

	function initialzeBusinessTypeWiseProjectsChart() {		
		
		
		 var s =  JSON.parse('${projects}');
		 //console.log(s);
		 var keys = [];
		 var finalColors = ["#30c5d9", "#ad3190"];
		// console.log(finalColors);
		 $.each(s, function(key, value){			
			     $.each(value, function(key, value){
			    	 keys.push(key);			      
			    });
			});
		
		
		 var businessTypes = keys;
		  //console.log(businessTypes);
		   	//chart_Business_type_Wise_Projects
		   	
		   	$('#chart_Business_type_Wise_Projects').html('');
				$('#chart_Business_type_Wise_Projects').append('<div id="chart_Business_type_Wise_Projects_c1" class="whole owl-carousel owl-theme "></div>');
				
				  var finalStr = "";
				
				  for(var i = 0; i < businessTypes.length;i++){
				  		var divStr = "<div id='chart_Business_type_Wise_Projects_c1_C"+i+"' class='whole item'></div>";
				  		finalStr+=divStr;
				  }
				  
				  $('#chart_Business_type_Wise_Projects').append(finalStr);
			
				  $.each(s, function(key, value){
					 
					  //console.log('index'+key);					 
					  //console.log(value);
					  var dataArray =new Array();
					  var headerArray = new Array();
					  headerArray.push("Project Name");
					  headerArray.push("Project Cost");
					  dataArray.push(headerArray);
					     $.each(value, function(key, value){					    	
					    	 $.each(value, function(key, value){
					    		 
					    		 var innerArray =new Array();
					    		 innerArray.push(value.projectName); 
					    		 innerArray.push(value.outlay);
					    		 dataArray.push(innerArray)  ;
					    	 });			      
					    });	
					
					  
					 
					  var colors=[finalColors[key]];
					     //console.log(dataArray);
					 	google.charts.load('current', {
							packages : [ 'corechart', 'bar' ]
						});
						google.charts.setOnLoadCallback(drawChart);
						function drawChart() {
						
							var data = google.visualization.arrayToDataTable(dataArray);
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
								title: businessTypes[key] + ' Projects',
								 titleTextStyle: {
								        color: 'blue', 
								        fontSize: 16, 
								        bold: true  
								    },
								width : 900,
								height : 300,

								legend: {position: 'none'},

								vAxis : {
									title : 'Project Cost',
											  viewWindow: {
											    min: 0,
											    max: value
											  }
								},
								
								hAxis: {textPosition: 'none'},
								bar : {
									groupWidth : '30%'
								},

								colors : colors
							};
							var formatter = new google.visualization.NumberFormat({
								prefix : "\u20B9",
								negativeColor : 'red',
								negativeParens : true,
								pattern : '#,##,###.##'
							});
							formatter.format(data, 1);
				
							 var chart = new google.visualization.ColumnChart(document
									.getElementById('chart_Business_type_Wise_Projects_c1_C'+key));
							chart.draw(data, options); 
						}
						
					});

	}
</script>
    
<!-- Bhavesh(08-09-2023) added style for resize button  -->
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
</style>