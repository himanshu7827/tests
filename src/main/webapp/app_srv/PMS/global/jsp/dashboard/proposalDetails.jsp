
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

	<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css"> 
	
	<script type="text/javascript">
	var previousOrg = '${collaborativeArray}';
	</script>
	
	<!-- Added by devesh on 09/08/23 to colour the changed value in proposal log table -->
	<style>
	.different-value {
    color: red; /* Change this to the desired color */
	}
	</style>
	<!-- End of proposal log colour -->
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
								code="proposal.report.proposalDetail" /></li>
					</ol>

				</div>
			</div>
<div class="row pad-bottom">

<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
					
					<div class="panel-body">
					
					<p><span class="bold  font_16 ">Proposal: </span> <span class="bold blue font_16"><i>${proposalMaster.proposalTitle}</i>
					<c:if test="${not empty proposalMaster.proposalRefNo}">
					<span class="bold orange font_14">&nbsp;(${proposalMaster.proposalRefNo}) </span>
					</c:if>
					</span>
										<%-- <a class="pull-right" title="Click here to view History" data-toggle="modal" data-target="#history" onclick="getHistory('${encApplicationId}')"  class="text-center"><i class="fa fa-history fa-3x"></i></a> --%>
										<!-- Added new button to call proposal history modal on 3/8/23 by devesh-->
										<a class="pull-right" title="Click here to view History" onclick="getHistory('${encApplicationId}')"  class="text-center"><i class="fa fa-history fa-3x"></i></a>
					</p>
					<input type="hidden" id="encApplicationId" value="${encApplicationId}"/>
					</div>
</div>
</div>
</div>
			<div class="row pad-bottom ">
    <div class="col-md-12"> 
      <!-- Nav tabs -->
      <div class="card">
         <div id="myCarousel" class="carousel slide">

                  <div class="carousel-inner ">
                  
                <div class="item active">
                
        <ul class="nav nav-tabs proj_details center pad-left-double pad-right-double" role="tablist">
          <li role="presentation" class="active "><a class="color1" href="#about" aria-controls="about" role="tab" data-toggle="tab"><i class="fa fa-tasks fa-2x"></i>  <h6><spring:message code="projectDetails.details" /></h6></a></li>
           <li role="presentation"><a class="color5" href="#documents" aria-controls="documents" onclick="loadProjectWiseDocuments('${encProjectId}')" role="tab" data-toggle="tab"><i class="fa fa-list-alt fa-2x "></i>  <h6><spring:message code="projectDetails.documents" /></h6></a></li>
        </ul>
        </div>
        
        <!-- Commented by devesh on 31-08-23 to fix issue on clicking tabs -->
                         <!-- </div>
         <a class="left carousel-control" href="#myCarousel" data-slide="prev"><i class="fa fa-chevron-left "></i></a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next"><i class="fa fa-chevron-right "></i></a>
                </div> -->
        <!-- End of commented code -->
        <!-- Tab panes -->
        <div class="tab-content ">
          <div role="tabpanel" class="tab-pane active" id="about"> <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
         		<table class="table table-bordered bg-white border-grey">

									<tbody>
										<tr>
											<td class="font_15 bold" style="background:#e6f1ff;width: 20%;" >About Proposal
												</td>
											<td class="font_14">
											<p class="pad-bottom bold blue font_14"><i>${proposalMaster.proposalTitle}</i></p>
											
											<p class=" bold pad-bottom"><u>Summary</u></p>
											<p>${proposalMaster.summary}
											</p>
											</td>
										</tr>
									<%-- 	<tr>
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
											
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Date of Submission</td>
											<td class="font_14">${proposalMaster.dateOfSubmission}</td>
										</tr>
											<tr>
											<td class="font_15 bold "  style="background:#e6f1ff">Duration</td>
											<td class="font_14"><p>${proposalMaster.duration} months</p>
											</td>
										</tr>
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff" >Total Proposal Cost</td>
											<td class="font_14 currency-inr">${proposalMaster.totalProposalCost}</td>						
										</tr>
										
										<tr>
											<td class="font_15 bold "  style="background:#e6f1ff" >CDAC Noida Outlay Share	 </td>
											<td class="font_14 currency-inr">${proposalMaster.proposalCost}</td>
										</tr>
								
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
        		<!-- Modal For history Details -->
	<div class="modal amount_receive_deatil_modal" id="history"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="true" data-backdrop="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Proposal History</h4>
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
						<!-- Commented by devesh on 3/8/23 to add new headers -->
						<%-- <th><spring:message code="forms.serialNo"/></th>
						  	<th>Revision Number</th>
						<th>Modified By</th>
						<th>Date On:</th>
						<th>Remarks</th> --%>
						<!-- New headers added on 3/8/23 by devesh -->
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
						<!-- End of new headers -->		
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
          
               <div role="tabpanel" class="tab-pane" id="documents">
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
	
				
			</div>
		
	
	</section>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/proposalDetails.js"></script>
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script> 
	<!-- <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/OrgChart/jquery.orgchart.js"></script> -->
</body>
</html>