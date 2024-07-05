<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/dashboards.css">

<%------------------- Add Style of Legend [12-10-2023] -------------------%>
<style type="text/css">
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
<%------------------- End of Style Legend [12-10-2023] -------------------%>
<section id="main-content" class="main-content merge-left">
	<div class=" container wrapper1">
		<div class="row">
			<div class=" col-md-12 pad-top-double  text-left">
				<ol class="breadcrumb bold">
					<li>Home</li>

					<li class="active">Completed Project</li>
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

<div class="row">
							<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 col-md-push-1 col-sm-push-1 pad-top pad-bottom"> <span class="bold red">Note :</span><span class="blue"> If From Date not selected, Data will display for last 1 year </span></div> -->
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-1 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
									<label class="label-class"><spring:message code="from.date" /> :</label>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-6 col-xs-12">								
									<div class="input-container"> 
									<i class="fa fa-calendar icon"></i>
									 <input class="input-field" readonly='true' value="${strStartDate}" id="startDate" name="startDate"/>								
								</div>
									
								</div>
							</div>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-1 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
									<label class="label-class"><spring:message code="to.date" /> :</label>
								</div>
								<div class="col-md-8 col-lg-8 col-sm-6 col-xs-12">								
									<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <input class="input-field" readonly='true' value="${strEndDate}" id="endDate" name="endDate"/>						
								</div>
									
								</div>
							</div>
							</div>
			</div>
	<c:choose>
		<c:when test="${groupProjectDetails.size() eq 1 && groupProjectDetails[0].groupMasterModelList.size() gt 0 }">
			<!-- create Group Tiles Here -->
			<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 groupdiv pad-bottom"
									id="groupdiv">
									<%-- <c:forEach items="${groupProjectDetails[0].groupMasterModelList}" var="grp">
									<c:choose>
										<c:when test="${fn:length(grp.groupName) >= 41}">
											<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 pad-top">
										</c:when>
										<c:when test="${fn:length(grp.groupName) >= 34}">
											<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 pad-top">
										</c:when>
										<c:when test="${fn:length(grp.groupName) >= 29}">
											<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-top">
										</c:when>
										<c:otherwise>
											<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
										</c:otherwise>
									</c:choose>
										
											<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
										</div>									
									
									</c:forEach> --%>
									<c:forEach items="${groupProjectDetails[0].groupMasterModelList}" var="grp">		
<c:if test="${grp.showProjects eq 1 }" >
	<c:choose>
		<c:when test="${fn:length(grp.groupName) >= 41}">
			<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 pad-top">
				<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
			</div>
		</c:when>
		<c:when test="${fn:length(grp.groupName) >= 34}">
			<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 pad-top">
				<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
			</div>
		</c:when>
		<c:when test="${fn:length(grp.groupName) >= 29}">
			<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-top">
				<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
			</div>
		</c:when>
		<c:otherwise>
			<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
				<button class="btn btn1 " data-toggle="tooltip"
												data-placement="top" title="Click here to see details"
												style="font-size:17px; font-weight:600;background-color:${grp.bgColor}"
												onclick="loadGroupWiseProposals('${grp.encGroupId}')">${grp.groupName}</button>
			</div>
		</c:otherwise>
	</c:choose>
	</c:if>
</c:forEach>

								</div>
								<div class="hidden " id="groupWiseProject">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero" id="groupWiseProjectDtl">
											</div>
								</div>
		</c:when>
		<c:otherwise>
			<!-- populate Table Data -->
			
			<div class="row">
		
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

		<!-- 	<legend class="bold legend_details">Details :</legend> -->
		<div class="table-responsive">
			<table id="example" class="table table-striped table-bordered"
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
						<th><spring:message code="project.details.leader"/></th>
						<th style="width:10%;"><p><spring:message code="project.details.totaloutlay"/></p><p>(in Lakhs)</p></th>
						<th style="width:10%;"><p><spring:message code="project.details.amountreceived"/></p><p>(in Lakhs)</p></th>
						<th style="width:10%;"><p><spring:message code="project.details.closureDate"/></p></th>
						<th><spring:message code="task.download" /></th>
		
					    
					</tr>
				</thead>
		<thead class="filters">
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
				<th><i class="fa fa-download"></i></th>
		</tr>
</thead>
				<tbody class="">
					<c:forEach items="${groupProjectDetails}" var="projectdetail" varStatus="theCount">
							<%--------------------------  SET Row color according to Received Amount [12-10-2023] ---------%>
							<c:set var="rowcolor" value="black" />   <%---- remove color code [07-03-2024] --%>
							<tr id='${projectdetail.encProjectId}' style="color:${rowcolor}">
						    <td></td>
							<td>${projectdetail.projectRefrenceNo}</td>
							<td class="font_16 " style="width:15%;" id="td2_${projectdetail.encProjectId}"><p><a style="color:${rowcolor}" class="bold" title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a></p>
							 <p class="font_14 "><i><a style="color:${rowcolor}" class="" title="Click here to view Funding org details" data-toggle='modal' data-target='#clientDetails' data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}' class='text-center'>${projectdetail.clientName}</a></i></p> 
							</td>
							<td>${projectdetail.businessType}</td>
							<td style="width:10%;">${projectdetail.startDate}</td>
							<td style="width:10%;">${projectdetail.endDate}</td>
							<td align="center">${projectdetail.projectDuration}</td>
							<td style="width:10%;"><span class="font_16">${projectdetail.strPLName}</span></td>
							<td align="right"><input type="hidden" id="hiddenTotalAmt_${projectdetail.encProjectId}" value="${projectdetail.strTotalCost}"/><span  class="convertLakhs" id="totalAmt_${projectdetail.encProjectId}">${projectdetail.strTotalCost} </span></td>
 							<td align="right"><input type="hidden" id="hiddenAmt_${projectdetail.encProjectId}" value="${projectdetail.strReceivedAmout}"/> <a title="Click here to view received details" data-toggle='modal' data-target='#amtreceive' data-whatever='${projectdetail.encProjectId};${projectdetail.startDate};${projectdetail.strTotalCost}' class='text-center'>
							<span  id="amt_${projectdetail.encProjectId}">${projectdetail.strReceivedAmout} </span></a></td>
							<td>${projectdetail.closureDate}</td>
<%-- 							
 --%>							<td style="width:10%;">
 <ul>
 							<c:forEach items="${projectdetail.projectDocument}" var="projectDocument">
 								<li ><a class="${projectDocument.classColor}  bold" onclick='downloadDocument("${projectDocument.encNumId}")'> ${projectDocument.documentTypeName}</a></li>
 							</c:forEach>
							
								</ul>
								<a style="float:right;" onclick="viewProjectDetails('/PMS/mst/documentDetails/${projectdetail.encProjectId}')">View All</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="12">
	      					<div class="legend">
						        <div class="legend-item">
						          <div class="legend-color" style="background-color: black;"></div>
						          <span>Closed Projects</span>
						        </div>
					        <!-- Add more legend items as needed -->
					      </div>
					    </td>
					</tr>
				</tfoot>
				<%-------------------------- END of Table [12-10-2023] --------------------------%>
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

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/completedProject.js"></script>
</section>


