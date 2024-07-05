<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css"> -->

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
</head>

<body>



<!-- 	<div class=" container wrapper1"> -->
<c:choose>
		<c:when test="${showWrapper != 0}">
		<section id="main-content" class="main-content merge-left">
			<div class=" container wrapper1">
				<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
		
			<li class="active">View Project Details</li>
		</ol>
	</div>
</div>
		</c:when>
		<c:otherwise>
		<section id="main-content" class="merge-left">
			<div class=" container-fluid wrapper pad-top">
			
		</c:otherwise>
	</c:choose>

<div class="row ">
		
		 </div>
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
		
	<!--Start data table-->
	
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
						<th><spring:message code="application.businesstype.label"/></th>
						<th><spring:message code="employee_Role.start_Date"/></th>
						<th><spring:message code="employee_Role.end_Date"/></th>
						<th style="width:10%;"><p><spring:message code="project.details.duration"/></p><p>(months)</p></th>
						<th><spring:message code="project.details.leader"/></th>
						<th style="width:10%;"><p><spring:message code="project.details.totaloutlay"/></p><p>(in Lakhs)</p></th>
						<th style="width:10%;"><p><spring:message code="project.details.amountreceived"/></p><p>(in Lakhs)</p></th>
						
						<c:if test="${closure==1}">
							<th style="width:10%;"><p><spring:message code="project.details.closureDate"/></p></th>
						</c:if>
						<th><spring:message code="task.download" /></th>
		
					    
					</tr>
				</thead>
												<thead class="filters">
		<tr>     
		<th><spring:message code="serial.no"/></th>
		<th class="textBox"><spring:message code="project.projectRefNo"/></th>
		<th class="textBox"><spring:message code="Project_Module_Master.projectName"/></th>
		                                                           
				<th class="comboBox" id="businessTypeSelect"><spring:message code="application.businesstype.label"/></th>
				<th class="comboBox" style="width:10%;"><spring:message code="employee_Role.start_Date"/></th>
				<th class="comboBox" style="width:10%;"><spring:message code="employee_Role.end_Date"/></th>
				<th style="width:10%;" class="comboBox" ><spring:message code="project.details.duration"/></th>
				<th class="comboBox" ><spring:message code="project.details.leader"/></th>
				<th style="width:10%;" class="textBox"><spring:message code="project.details.totalcost.lakhs"/></th>
				<th style="width:10%;" class="textBox"><spring:message code="project.details.amountreceived.lakhs"/></th>
				<c:if test="${closure==1}">
					<th style="width:10%;"></th>
				</c:if>
				<th><i class="fa fa-download"></i></th>
		</tr>
</thead>
				<tbody class="">
					<c:forEach items="${data}" var="projectdetail" varStatus="theCount">
						<%--------------------------  SET Row color according to Received Amount [12-10-2023] ---------%>
							<c:set var="rowcolor" value="black" />		<%---- remove color code [07-03-2024] --%>				
							<tr id='${projectdetail.encProjectId}' style="color:${rowcolor}">
						    <td></td>
							<td>${projectdetail.projectRefrenceNo}</td>
							<td class="font_16 " style="width:15%;"><a  style="color:${rowcolor}" class="bold" title='Click to View Complete Information' onclick="viewProjectDetails('/PMS/projectDetails/${projectdetail.encProjectId}')">${projectdetail.strProjectName}</a>
							 <p class="font_14 "><i><a style="color:${rowcolor}" title="Click here to view Funding org details" data-toggle='modal' data-target='#clientDetails' data-whatever='${projectdetail.encClientId};${projectdetail.encProjectId};${projectdetail.endUserId}' class='text-center'>${projectdetail.clientName}</a></i></p> 
						<%-- 	<p class="font_12 "><i><a class="orange" title="Click here to view Funding org details" data-toggle='modal' data-target='#clientDetails' data-whatever='${projectdetail.encClientId}' class='text-center'>${projectdetail.clientName}</a></i></p> --%>
							</td>
							<td>${projectdetail.businessType}</td>
							<td style="width:10%;">${projectdetail.startDate}</td>
							<td style="width:10%;">${projectdetail.endDate}</td>
							<td align="center">${projectdetail.projectDuration}</td>
							<td style="width:10%;"><span class="font_16">${projectdetail.strPLName}</span><p><a style="color:${rowcolor}" title="Team details" data-toggle="modal" data-target="#team-modal" onclick="viewTeamDetails('${projectdetail.encProjectId}')"><span class="font_12 pull-right"><i class="fa fa-users"></i></span></a></p></td>
							<td align="right" class=""><input type="hidden" id="hiddenTotalAmt_${projectdetail.encProjectId}" value="${projectdetail.strTotalCost}"/><span  class="convert_lakhs" id="totalAmt_${projectdetail.encProjectId}">${projectdetail.strTotalCost} </span></td>
 							<td align="right"><input type="hidden" id="hiddenAmt_${projectdetail.encProjectId}" value="${projectdetail.strReceivedAmout}"/> <a title="Click here to view received details" data-toggle='modal' data-target='#amtreceive' data-whatever='${projectdetail.encProjectId};${projectdetail.startDate};${projectdetail.strTotalCost}' class='text-center'>
							<span  id="amt_${projectdetail.encProjectId}">${projectdetail.strReceivedAmout} </span></a></td>

							<c:if test="${closure==1}">
								<td>${projectdetail.closureDate}</td>
							</c:if>
<%-- 							

 --%>							<td style="width:10%;">
 <ul>
 							<c:forEach items="${projectdetail.projectDocument}" var="projectDocument">
 								<li ><a class="${projectDocument.classColor}  bold" onclick='downloadDocument("${projectDocument.encNumId}")'> ${projectDocument.documentTypeName}</a></li>
 							</c:forEach>
							<%-- <c:if test="${ not empty projectdetail.encProposalDocumentId}">
										
								</c:if>	 --%>
								<%-- <c:if test="${not empty projectdetail.encworkOrderDocumentId }">
										<li><a class=" green bold" onclick='downloadDocument("${projectdetail.encworkOrderDocumentId}")'> Work Order</a></li>
								</c:if>
								<c:if test="${not empty projectdetail.encmouDocumentId }">
										<li><a class="purple bold" onclick='downloadDocument("${projectdetail.encmouDocumentId}")'> MOU</a></li>
								</c:if>
								<c:if test="${not empty projectdetail.encSrsDocumentId }">
										<li><a  class="lightblue bold" onclick='downloadDocument("${projectdetail.encSrsDocumentId}")'> SRS</a></li>
								</c:if> --%>
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
			<%------------------------  END of Table [12-10-2023] --------------------------------------------------%>
			</table>
			</div>
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>

</div>
</section>

<!-- Modal For Amount Receive -->
	<div class="modal amount_receive_deatil_modal" id="amtreceive"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Payment Realized</h4>
					
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
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6" id="projectForPayment">
											
										</div>
									</div>
									</div>
				<div class="row pad-top ">			
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
										<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">

											<label class="label-class"><b><spring:message
													code="client.name" /> :</b></label>

										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6" id="clientIdForPayment">
											
										</div>
									</div>
								</div>
				
             <table id="datatable" class="table table-striped table-bordered example_det pad-top"
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
				<div class="modal-footer" id="modelFooter">
					
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- Modal For Invoice Details -->
	<div class="modal amount_receive_deatil_modal" id="invoiceDetail"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Invoice Detail</h4>
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
						   <th>S.No</th>
						   <th>Invoice Number</th>  
							<th>Invoice Date</th>
							<th>Invoice Amount</th>
							<th>Tax Amount</th>
							<th>Total Amount</th>
							
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
	
	<!-- Modal For client Details -->
	<div class="modal amount_receive_deatil_modal" id="clientDetails"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel"><spring:message code="client.detail"/></h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12 table-responsive pad-top">
				<table class="table table-bordered">
    				<tbody>
        			<tr>
        			<th><h5 class=" blue"><spring:message code="client.name"/></h5></th>
        			<td><span class="black bold" id="clientName"></span></td>
        			</tr>
        			<tr>
        			<th><h5 class=" blue"><spring:message code="master.shortCode"/></h5></th>
        			<td><span class="black" id="shortCode"></span></td>
        			</tr>
        			<tr>
        			<th><h5 class=" blue"><spring:message code="group.groupAddress"/></h5></th>
        			<td><span class="black" id="address"></span> </td>
        			</tr>
        			<tr>
        			<th><h5 class=" blue"><spring:message code="master.contact"/></h5></th>
        			<td><span class="black" id="contactNo"></span></td>
        			</tr>
        		<td><span class="black" id="contactNo"></span></td>
        			</tbody>
        			</table>
				</div>
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top ">
					<h5><u> Contact Person Details (For funding Org)</u></h5></div>
					
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 table-responsive pad-top ">
					
						<table class="table table-bordered table-striped table-hover mb-0 " id="glance_data_table">						
							<thead>
						<tr>
						<td><h5><spring:message code="Client_Contact_Person_Master.clientContactPersonName"/></h5></td>
						<td><h5><spring:message code="Client_Contact_Person_Master.contactPersonDesignation"/></h5></td>
						<td><h5><spring:message code="Client_Contact_Person_Master.contactPersonMobileNumber"/></h5></td>
						<td><h5><spring:message code="Client_Contact_Person_Master.contactPersonEmailId"/></h5></td>
						<td><h5><spring:message code="Client_Contact_Person_Master.contactPersonOfficeAddress"/></h5></td>
						
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
				<div class="modal-footer" id="modelFooter">
					
				</div>
			</div>
		</div>
	</div>
	
		<!-- Modal for team member details-->
<div class="modal" id="team-modal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
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
<script>

</script>
	
</body>
<!-- <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/OrgChart/jquery.orgchart.js"></script> -->
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/ViewProjectDetails.js"></script>

</html>