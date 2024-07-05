
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css"> -->
	<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css"> 
	
	<script type="text/javascript">
	var previousOrg = '${collaborativeArray}';
	</script>
	
</head>


<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
		<div class="row">
	<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="1"/>
<jsp:param name="applicationId" value="${proposalMaster.applicationId}"/>
</jsp:include> 
		</div>
				<form:form id="form1" action="" >
		
			<div class="row">
<div class="row pad-bottom">

<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">

</div>
</div>
			<div class="row pad-bottom ">
    <div class="col-md-12"> 
      <!-- Nav tabs -->
      <div class="card">

        <!-- Tab panes -->
        <input type="hidden" id="encApplicationId" value="${encApplicationId}" />
        <input type="hidden" id="applicationId" value="${proposalMaster.applicationId}" />
        <div >
          <div  id="about"> <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
         		<table class="table table-bordered bg-white border-grey">

									<tbody>
										<tr>
											<td class="font_15 bold" style="background:#e6f1ff;width: 20% !important;" >About Proposal
												</td>
											<td class="font_14">
											<p class="pad-bottom bold blue font_14"><i>${proposalMaster.proposalTitle}</i><span class="bold orange font_14">&nbsp;(${proposalMaster.proposalRefNo}) </span></p>
											
											<p class=" bold pad-bottom"><u>Description</u></p>
											<p>${proposalMaster.summary}
											</p>
											</td>
										</tr>
										<%-- <tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Business Type</td>
											<td class="font_14">${proposalMaster.businessTypeName}</td>
										</tr> --%>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Project Type</td>
											<td class="font_14">${proposalMaster.projectTypeName}</td>
										</tr>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Project Category</td>
											<td class="font_14">${proposalMaster.projectCategory}</td>
										</tr>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Organization Name</td>
											<td class="font_14">${proposalMaster.organisationName}</td>
										</tr>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Group Name</td>
											<td class="font_14">${proposalMaster.groupName}</td>
										</tr>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Thrust Area</td>
											<td class="font_14">${proposalMaster.thrustArea}</td>
										</tr>
											
											<c:if test="${not empty proposalMaster.dateOfSubmission}">
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Date of Submission</td>
											<td class="font_14">${proposalMaster.dateOfSubmission}</td>
										</tr>
											</c:if>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Duration</td>
											<td class="font_14"><p>${proposalMaster.duration} months</p>
											</td>
										</tr>
										
											<%-- <tr>
											<td class="font_15 bold "  style="background:#e6f1ff" >CDAC Noida Outlay Share	 </td>
											<td class="font_14 currency-inr">${proposalMaster.proposalCost}</td>
											<input type="hidden" value="${proposalMaster.proposalCost}" id="proposalCost">
										</tr> --%>
										<!-- Added by devesh on 02-02-24 to show CDAC Outlay only when it is not zero -->
										<c:if test="${proposalMaster.proposalCost ne 0}">
										    <tr>
										        <td class="font_15 bold" style="background:#e6f1ff">CDAC Noida Outlay Share</td>
										        <td class="font_14 currency-inr">${proposalMaster.proposalCost}</td>
										        <input type="hidden" value="${proposalMaster.proposalCost}" id="proposalCost">
										    </tr>
										</c:if>
										<!-- End of condition -->
											<%-- <tr>
											<td class="font_15 bold "  style="background:#e6f1ff" >Total Proposal Cost </td>
											<td class="font_14 currency-inr">${proposalMaster.totalProposalCost}</td>
											<input type="hidden" value="${proposalMaster.totalProposalCost}" id="totalProposalCost">
										</tr> --%>
										<!-- Added by devesh on 02-02-24 to show Total Proposal Cost only when it is not zero -->
										<c:if test="${proposalMaster.totalProposalCost ne 0}">
											<tr>
												<td class="font_15 bold "  style="background:#e6f1ff" >Total Proposal Cost </td>
												<td class="font_14 currency-inr">${proposalMaster.totalProposalCost}</td>
												<input type="hidden" value="${proposalMaster.totalProposalCost}" id="totalProposalCost">
											</tr>
										</c:if>
										<!-- End of condition -->
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Submitted By / Chief Investigator Name (For R&D projects)</td>
											<td class="font_14">${proposalMaster.submittedBy}</td>
										</tr>
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Funding Organization</td>
											<td class="font_14">${proposalMaster.submittedTo}</td>
										</tr>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Contact Person for Funding Organization</td>
											<td class="font_14">${proposalMaster.contactPerson}</td>
										</tr>
										<c:if test="${not empty proposalMaster.endUser}">	
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">End User</td>
											<td class="font_14">${proposalMaster.endUser}</td>
										</tr>
										</c:if>
										
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Objective</td>
											<td class="font_14">${proposalMaster.objectives}</td>
										</tr>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Background</td>
											<td class="font_14">${proposalMaster.background}</td>
										</tr>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">FTS File No</td>
											<td class="font_14">${proposalMaster.proposalStatus}</td>
										</tr>
										<c:if test="${proposalMaster.corporateApproval == true}">
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Corporate Approval</td>
											<td class="font_14">
											<c:choose>
													<c:when test="${proposalMaster.corporateApproval}">Yes</c:when>
													<c:otherwise>No </c:otherwise>
												</c:choose> 
											</td>
										</tr>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Date of Submission to corporate</td>
											<td class="font_14">${proposalMaster.dateOfSub}</td>
										</tr>
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Clearance Received Date</td>
											<td class="font_14">${proposalMaster.clearanceReceiveDate}</td>
										</tr>
										</c:if>

									</tbody>
								</table>
         
        </div>
        <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12" id="CollaborationDetails">
        		<fieldset class="fieldset-border">
         <legend style="font-size:16px"><b> Collaborative Organization Details :</b> </legend>
            
        <table id="example1" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:5%;"><spring:message code="serial.no"/></th>
 					    <th style="width:12%;"><spring:message code="group.organisationName"/></th>
 					    <th style="width:10%;"><spring:message code="master.contact"/></th>	
 					    <th style="width:10%;"><spring:message code="Client_Contact_Person_Master.contactPersonEmailId"/></th>					    			    
  					    <th style="width:12%;"><spring:message code="application.website.label"/></th>  					    
  					    <th style="width:20%;"><spring:message code="group.groupAddress"/></th>
				    
					</tr>
				</thead>
			
			</table>
			</fieldset>
	       </div>
        </div>
          
               <div id="documents"><p><span class="bold pad-left double font_16 ">Documents: </span> 
              <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">		
			
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:5%;"><spring:message code="serial.no"/></th>
 					    <th style="width:12%;"><spring:message code="document.documentName"/></th>
  					    <th style="width:12%;"><spring:message code="docupload.document.version"/></th>
						<th style="width:10%;"><spring:message code="docupload.document.Date"/></th>
						<th style="width:40%;"><spring:message code="master.description"/></th>
						<th style="width:10%;"><spring:message code="task.download" /></th>
		
					    
					</tr>
				</thead>
				 <tbody class="">
					 <c:forEach items="${proposalDocument}" var="documentdetail" varStatus="theCount">
						<tr >
						    <td>${theCount.count}</td>
						    <td ><p class="bold">${documentdetail.documentTypeName}</p>
						    <c:choose>
						    <c:when test="${documentdetail.periodFrom != null}">
						   
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">Valid to: ${documentdetail.periodTo}</span></p>
						    </c:when>
						    <c:otherwise>
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">${documentdetail.periodTo}</span></p>
						    </c:otherwise>
						    </c:choose>
						    </td>
    						<td align="center">${documentdetail.documentVersion}<br>
    						</td>
							<td>${documentdetail.documentDate}</td>
 							<td>${documentdetail.description}</td>
							<td align="center">
								<c:forEach items="${documentdetail.detailsModels}" var="details" varStatus="theCount">
								<span ><a onclick="downloadProposalFile('${details.encNumId}')">${details.icon}</a> </span>
								</c:forEach>
							
 							
							</td>
						</tr>
					</c:forEach> 
				</tbody> 
			</table>
									
        </div></div>

						   </div>
				
			            </div>
               
                </div>

		</div>
		
			<!-- Modal For client Details -->
	
							<div class="row padded" style="text-align: center;">
							<button type="button" class="btn btn-primary reset font_20 " id="prev" onclick="goprev()" >
									<span class="pad-right"></span>Go back and Edit
									</button>
									
									<button type="button" class="btn btn-primary reset font_20 " id="save"  >
									<span class="pad-right"></span>Save Proposal
									</button>
									</div>
			</div>
			</form:form>
		</div>
	
	</section>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/previewAndSubmitProposal.js"></script>
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script> 
	<!-- <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/OrgChart/jquery.orgchart.js"></script> -->
</body>
</html>