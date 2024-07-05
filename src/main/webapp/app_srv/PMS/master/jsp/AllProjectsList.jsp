<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
#team-modal {
	padding-top: 5%;
}

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



</head>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Details</li>
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



			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<!-- <legend class="bold legend_details">Details :</legend> -->
							<sec:authorize access="hasAuthority('PROJECT_CLOSURE')">
								<div class="row pad-bottom pad-top">
									<div class="pull-right">
										<div class="col-md-4">
											<button type='button' class="btn btn-blue"
												onclick='closeProject()'>
												Close Project <i class="icon-tasks red"></i>
											</button>
										</div>
									</div>
								</div>
							</sec:authorize>

							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">

									<tr>
										<sec:authorize access="hasAuthority('PROJECT_CLOSURE')">
											<th data-toggle="true"></th>
										</sec:authorize>

										<th><spring:message code="project.projectRefNo" /></th>
										<th><spring:message
												code="Project_Payment_Schedule.projectName" /></th>
										<th class="hidden"><spring:message
												code="projectMaster.objective" /></th>
										<th class="hidden"><spring:message
												code="projectMaster.briefDescription" /></th>
										<th class="hidden"><spring:message
												code="projectMaster.aim" /></th>
										<th class="hidden"><spring:message
												code="projectMaster.scope" /></th>
										<!-- <th>Project Status</th> -->
										<th style="width: 8%;"><spring:message
												code="projectMaster.type" /></th>
										<th data-hide="all"><spring:message
												code="projectMaster.startDate" /></th>
										<th data-hide="all"><spring:message
												code="projectMaster.endDate" /></th>

										<th data-hide="all" style="width: 8%;"><spring:message
												code="projectMaster.duration" /> <br>(in months)</th>
										<th data-hide="all" style="width: 8%;"><spring:message
												code="projectMaster.Cost" /></th>
										<!-- <th data-hide="all">Project Scope</th> -->
										<%-- 						<th data-hide="all"><spring:message code="forms.status"/></th>
 --%>
										<c:if test="${role != 6 && role!=7}">
											<th class="width10">Edit</th>
										</c:if>
										<th class="">Team Details</th>
									</tr>
								</thead>

								<tbody>
									<!-- Bhavesh(27-07-23) fetching the underclosure data for comparison -->
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos hidden">${item.projectRefrenceNo}</p>
									</c:forEach>
									<c:forEach var="item" items="${data}">
										<p class="data hidden">${item.projectRefrenceNo}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos1 hidden">${item.strProjectName}</p>
									</c:forEach>
									<c:forEach var="item" items="${data}">
										<p class="data1 hidden">${item.strProjectName}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos2 hidden">${item.businessType}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos3 hidden">${item.startDate}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos4 hidden">${item.endDate}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos5 hidden">${item.projectDuration}</p>
									</c:forEach>
									<c:forEach var="item" items="${underClosure}">
										<p class="underclos6 hidden">${item.strTotalCost}</p>
									</c:forEach>
									<c:forEach items="${data}" var="budgetHead">

										<c:choose>
											<c:when test="${budgetHead.dtEndDate < sysDate }">
											<!--Bhavesh(21-09-23) if item1.projectRefrenceNo==budgetHead.projectRefrenceNo then add golden color otherwise grey  -->
												<c:set var="test_data" value="grey-text"/>
												<c:forEach var="item1" items="${closureData}">
													<c:if test="${item1.projectRefrenceNo==budgetHead.projectRefrenceNo}">
														<c:set var="test_data" value="golden-text"/>
													</c:if>
												</c:forEach>
												<tr class="${ test_data}">

													<sec:authorize access="hasAuthority('PROJECT_CLOSURE')">
														<td><input type="radio" name="projectIdRadio"
															value="${budgetHead.encProjectId}" /></td>
													</sec:authorize>
													<td class="${ test_data}">${budgetHead.projectRefrenceNo}</td>
													<!--Bhavesh(19-07-23)<td class="font_16"><a title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${budgetHead.encProjectId}')"><span style="color:#d59a83">${budgetHead.strProjectName}</span> </a></td>  -->
													<td class="font_16 "><a
														title='Click to View Complete Information'
														onclick="viewProjectDetails('/PMS/projectDetails/${budgetHead.encProjectId}')"><span
															class=" ${ test_data}">${budgetHead.strProjectName}</span>
													</a></td>
													<td class="hidden">${budgetHead.strProjectObjective}</td>
													<td class="hidden">${budgetHead.strBriefDescription}</td>
													<td class="hidden">${budgetHead.strProjectAim}</td>
													<td class="hidden">${budgetHead.strProjectScope}</td>
													<%-- <td>${budgetHead.strProject}</td> --%>
													<td class=" ${ test_data}">${budgetHead.businessType}</td>
													<td class=" ${ test_data}">${budgetHead.startDate}</td>
													<td class="${ test_data}">${budgetHead.endDate}</td>

													<td class=" ${ test_data}">${budgetHead.projectDuration}</td>
													<td class=" ${ test_data}">${budgetHead.strTotalCost}</td>
													<c:if test="${role != 6 && role!=7}">
														<td><a
															onclick='startWorkFlow(${budgetHead.projectId},2,0,0,0)'><span
																class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
																id="edit" aria-hidden="true"></span></a></td>
													</c:if>
													<td><button class="btn btn-primary"
															data-toggle="modal" data-target="#team-modal"
															onclick="viewTeamDetails('${budgetHead.encProjectId}')">
															<i class="fa fa-th-large"></i>Team
														</button></td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:set var="test_data1" value="success-text"/>
												<c:forEach var="item1" items="${closureData}">
													<c:if test="${item1.projectRefrenceNo==budgetHead.projectRefrenceNo}">
														<c:set var="test_data1" value="golden-text"/>
													</c:if>
												</c:forEach>
												<tr class="${test_data1}">
													<sec:authorize access="hasAuthority('PROJECT_CLOSURE')">
														<td><input type="radio" name="projectIdRadio"
															value="${budgetHead.encProjectId}" /></td>
													</sec:authorize>
													<td class="${test_data1}">${budgetHead.projectRefrenceNo}</td>
													<td class="font_16"><a
														title='Click to View Complete Information'
														onclick="viewProjectDetails('/PMS/projectDetails/${budgetHead.encProjectId}')"><span
															class="${test_data1}">${budgetHead.strProjectName}</span>
													</a></td>
													<td class="hidden">${budgetHead.strProjectObjective}</td>
													<td class="hidden">${budgetHead.strBriefDescription}</td>
													<td class="hidden">${budgetHead.strProjectAim}</td>
													<td class="hidden">${budgetHead.strProjectScope}</td>
													<%-- <td>${budgetHead.strProject}</td> --%>
													<td class="${test_data1}">${budgetHead.businessType}</td>
													<td class="${test_data1}">${budgetHead.startDate}</td>
													<td class="${test_data1}">${budgetHead.endDate}</td>

													<td class="${test_data1}">${budgetHead.projectDuration}</td>
													<td class="${test_data1}">${budgetHead.strTotalCost}</td>
													<c:if test="${role != 6 && role!=7}">
														<td><a
															onclick='startWorkFlow(${budgetHead.projectId},2,0,0,0)'><span
																class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
																id="edit" aria-hidden="true"></span></a></td>
													</c:if>
													<td><button class="btn btn-primary"
															data-toggle="modal" data-target="#team-modal"
															onclick="viewTeamDetails('${budgetHead.encProjectId}')">
															<i class="fa fa-th-large"></i>Team
														</button></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</c:forEach>
								</tbody>
								<c:choose>
                <c:when test="${role==7}"> 
                   <!--Bhavesh 28-07-23 added legends  -->
										<tfoot>
											<tr >
												<td colspan="9">
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
          <span>Date Elapsed </span>
        </div>
      </div>
    </td>
											</tr>
										</tfoot>
                </c:when>
                <c:otherwise>
                     <!--Bhavesh 28-07-23 added tfoot to count total  -->
										<tfoot>
											<tr >
												<td colspan="10">
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
                </c:otherwise>
            </c:choose>
							</table>

							<!--End of data table-->
						</fieldset>
					</div>
					<!--End of datatable_row-->
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
						<div class="modal-body model-border center">


							<div id="chart-container"></div>

						</div>

					</div>

				</div>

			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/AllProjectsList.js"></script>


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/OrgChart/jquery.orgchart.js"></script>
</body>
</html>