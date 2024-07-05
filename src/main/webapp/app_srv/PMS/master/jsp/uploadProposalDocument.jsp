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


</head>



<body>
	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
		<div class="row">
	<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="1"/>
<jsp:param name="applicationId" value="${proposalMasterModal.applicationId}"/>
</jsp:include> 
		</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Proposal Document</li>
					</ol>
				</div>
			</div>
			<div class="row "></div>

	<%-- 		<c:if test="${message != null && message != ''}">
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
			</c:if> --%>


			<form:form id="form1" modelAttribute="projectDocumentMasterModel"
				action="/PMS/mst/saveProposalDocument" method="post"
				enctype="multipart/form-data">
				<form:hidden path="numId" />
				<input type="hidden"  id="applicationId"  value="${proposalMasterModal.applicationId}" />
				
				
<!-- 				<div class="container ">
 -->					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Proposal Document </h4>
							</div>
							<div class="panel-body text-center">
								<form:errors path="proposalId" class="error_msg"></form:errors>
								<div class="row pad-bottom pad-left pad-right pull-right">
									<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12">
									<a class="font_14 blue bold" onclick="viewAllProposalDocument()"><i class="fa fa-hand-o-right black"></i><i><u>View All Uploaded Documents</u></i></a>
									</div>
									</div>
								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="proposal.proposalTitle" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												 <c:choose>
													<c:when test="${not empty proposalMasterModal.proposalTitle}">
														${proposalMasterModal.proposalTitle}
														<form:hidden path="proposalId" value="${proposalMasterModal.numId}"/>
													</c:when>
													<c:otherwise>
														<form:select path="proposalId" class="select2Option">
															<form:option value="0">-- Select Proposal --</form:option>
															<c:forEach items="${proposalMasterModelList}" var="proposal">
																<form:option value="${proposal.numId}">${proposal.proposalTitle}</form:option>
															</c:forEach>
														</form:select>													
													</c:otherwise>
												</c:choose> 
												
												

											</div>
										</div>
									</div>
								</div>
								<div class="row  ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<%-- <label class="label-class"> <spring:message
													code="document.documentName" /> :<span style="color: red;">*</span>
											</label> --%>
											<label class="label-class"> Document Type :<span style="color: red;">*</span>
											</label><!-- Modified by devesh on 25-09-23 to change label name -->

										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<!-- declare the id dynamicProposalDate and call the function  ondyncamicproposal() by varun  -->
											<div class="input-container" id="dynamicProposalDate">
												<form:select path="documentTypeId" class="select2Option" onchange="ondyncamicproposal()">
													<form:option value="0">-- Select Document --</form:option>
													<c:forEach items="${documentTypeList}" var="document">
														<form:option value="${document.numId}">${document.docTypeName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>

								</div>

<%-- 								<sec:authorize access="hasAuthority('UPLOAD_PROJECT_DOCUMENT')">
 --%>									<div id="docBasicDetails" class="hidden row">
										<div class="row  ">
										<!-- given the id name nonproposalapproval by varun on 15-11-2023 -->
											<div
											
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;" id="nonproposalapproval">

													<label class="label-class"><spring:message
															code="docupload.document.Date" /> :<span
														style="color: red;">*</span></label>
												</div>
												<!-- added below div to hidden and given the id name approvalProposal the date by varun on 16-11-2023 -->
															<div
													class="hidden col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;" id="approvalProposal">

													<label class="label-class">Document Approval Date :<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<i class="fa fa-calendar icon"></i>
														<form:input type="text" class="input-field"
															path="documentDate" readonly="true" />
														<form:errors path="documentDate" cssClass="error" />
													</div>
												</div>
											</div>
										</div>
										<div class="row  ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;">
													<label class="label-class"><spring:message
															code="docupload.document.version" /> :<span
														style="color: red;">*</span></label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<i class="fa fa-indent icon"></i>
														<form:input class="input-field" path="documentVersion" />
														<form:errors path="documentVersion" cssClass="error" />
													</div>
												</div>
											</div>
										</div>
										<div class="row hidden">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;">
													<label class="label-class"><spring:message
															code="docupload.document.startDate" /> :</label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<i class="fa fa-calendar icon"></i>
														<form:input class="input-field" path="periodFrom"
															readonly="true" />
														<form:errors path="periodFrom" cssClass="error" />
													</div>
												</div>
											</div>
										</div>
										<div class="row hidden ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;">
													<label class="label-class"><spring:message
															code="docupload.document.endDate" /> :</label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<i class="fa fa-calendar icon"></i>
														<form:input class="input-field" path="periodTo"
															readonly="true" />
														<form:errors path="periodTo" cssClass="error" />
													</div>
												</div>
											</div>


										</div>

										<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;">
													<label class="label-class"><spring:message
															code="document.description" /> : <span
														style="color: red;">*</span> </label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<i class="fa fa-file-text-o icon "></i>
														<form:textarea class="form-control" rows="2"
															path="description" />
														<form:errors path="description" cssClass="error" />
													</div>
												</div>
											</div>
										</div>

									</div>

									<div class="row hidden" id="newUploadDiv">
										<div class="pull-right">
											<div class="col-md-4">
												<input type="button" value="Upload New Record"
													id="newUpload" class="btn btn-primary a-btn-slide-text">
											</div>
										</div>
									</div>
<%-- 								</sec:authorize>
 --%>
<%-- 								<sec:authorize access="hasAuthority('UPLOAD_PROJECT_DOCUMENT')">
 --%>									<table id="docUploadTbl"
										class="table table-striped table-bordered hidden" width="80%">
										<thead>
											<tr>
												<th class="hidden"></th>
												<th class="hidden"></th>
												<th><spring:message code="docupload.document.format" />
												</th>
												<th><spring:message code="docupload.document.file" />   <span style="font-size: smaller; color: red;">(The maximum file size allowed is 15 MB.)</span></th>
											</tr>
										</thead>

										<tbody>
										</tbody>
									</table>


									<div class="row pad-top  form_btn">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>

									</div>
									
												<br>
									<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" style="float: left;">
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button>
									
									<br>
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="checkFile()" style="float: right;">
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button> 
<%-- 								</sec:authorize>
 --%>							</div>
						</div>
					</div>
			<!-- 	</div> -->
			</form:form>
			<!-- <div class="container"> -->
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">
							<legend class="bold legend_details">Details :</legend>	
<!-- Changes ( Display all the uploaded proposals ) in 4/5/2023 -->
							<table id="example1" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th>Document Date</th>
										<th class="hidden">Valid From</th>
										<th class="hidden">Valid Upto</th>
										<th>Version</th>
										<th>Download Files</th>
										<th class=>Description</th>
										<th>Document Type</th>
										<th>Edit</th>
										<!-- <th>Delete</th> -->
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${alldocumentlist}" var="getalldata">
										<tr id="${getalldata.numId}">
											<td>${getalldata.documentDate}</td>
 <!--    comment the below line by varun to hide the validupto and validfrom on 01-11-2023 -->
											<%-- <c:choose>
												<c:when test="${not empty getalldata.periodFrom}">
													<td>${getalldata.periodFrom}</td>
												</c:when>
												<c:otherwise>
													<td></td>
												</c:otherwise>
											</c:choose> --%>

											<%-- <c:choose>
												<c:when test="${not empty getalldata.periodTo}">
													<td>${getalldata.periodTo}</td>
												</c:when>
												<c:otherwise>
													<td></td>
												</c:otherwise>
											</c:choose> --%>

											<td>${getalldata.documentVersion}</td>
											<td><c:forEach items="${getalldata.detailsModels}"
													var="upload" varStatus="status">
													<c:choose>
														<c:when
															test="${status.count == getalldata.detailsModels.size()}">
															<a onclick="downloadProposalFile('${upload.encNumId}')">${upload.icon}</a>
														</c:when>
														<c:otherwise>
															<a onclick="downloadProposalFile('${upload.encNumId}')">${upload.icon}</a> || 
       													</c:otherwise>
													</c:choose>
												</c:forEach></td>
											<c:if test="${not empty getalldata.description}">
												<td>${getalldata.description}</td>
											</c:if>
											<c:if test="${not empty getalldata.documentTypeName}">
												<td>${getalldata.documentTypeName}</td>
											</c:if>
											<td><span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												onclick="editDocument(${getalldata.numId})"></span></td>
											<%-- <td><span class="fa fa-times btn btn-danger"
												onclick="deleteDocument('${getalldata.encNumId}', ${getalldata.numId})"></span>
											</td> --%>
										</tr>
									</c:forEach>
								</tbody>
							</table>

<!-- End Changes -->
<!-- ON CLICK DISPLAY  END ONCLICK -->
						<table id="example" class="table table-striped table-bordered hidden"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th>Document Date</th>
										<th class="hidden">Valid From</th>
										<th class="hidden">Valid Upto</th>
										<th>Version</th>
										<th>Download Files</th>
										<th class=>Description</th>
										
											<th>Edit</th>
											<!-- <th>Delete</th> -->
										</tr>
								</thead>
								<tbody>

								</tbody>
							</table>

							<!--End of data table-->
						</fieldset>
					</div>
					<!--End of datatable_row-->
				</div>
			</div>
		<!-- </div> -->
	</section>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/uploadProposalDocument.js"></script>
		
				
<!-- Validation added at least one document is required -->		
<script type="text/javascript">
	  // Checks File Exists or not in the Document Table
	  function checkFile(){
			var documentTypeId = $("#proposalId").val();
			//Added by devesh on 04-10-23 to get flag values
			const ApplicationId = $('#applicationId').val();
			const totalProposalCostFlag = localStorage.getItem(ApplicationId+'totalProposalCostFlag');
		    const ProposalCostFlag = localStorage.getItem(ApplicationId+'ProposalCostFlag');
		    const DurationFlag = localStorage.getItem(ApplicationId+'DurationFlag');
		    const ObjectivesFlag = localStorage.getItem(ApplicationId+'ObjectivesFlag');
		  	//End of check
			$.ajax({
		        type: "POST",
		        url: "/PMS/mst/checkProposalDocumentExists",
		        data: {
		        	"documentTypeId" : documentTypeId
		        	},			
				success : function(data) {		
					console.log(data);	
		
					if(data>0){
						/* gonext(); */
						//Added by devesh on 04-10-23 to check if total proposal cost or proposal cost changed
						if (totalProposalCostFlag === 'true' && ProposalCostFlag !== 'true' && DurationFlag !== 'true' && ObjectivesFlag !== 'true') {
					          	swal("Total Proposal Cost has been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag === 'true' && DurationFlag !== 'true' && ObjectivesFlag !== 'true'){
					          	swal("CDAC Noida Outlay Share has been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag !== 'true' && DurationFlag === 'true' && ObjectivesFlag !== 'true'){
				          		swal("Duration has been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag !== 'true' && DurationFlag !== 'true' && ObjectivesFlag === 'true'){
				          		swal("Objectives has been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag === 'true' && DurationFlag !== 'true' && ObjectivesFlag !== 'true'){
						    	swal("Total Proposal Cost and CDAC Noida Outlay Share have been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag !== 'true' && DurationFlag === 'true' && ObjectivesFlag !== 'true'){
					        	swal("Total Proposal Cost and Duration have been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag !== 'true' && DurationFlag !== 'true' && ObjectivesFlag === 'true'){
					        	swal("Total Proposal Cost and Objectives have been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag === 'true' && DurationFlag === 'true' && ObjectivesFlag !== 'true'){
				        	swal("CDAC Noida Outlay Share and Duration have been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag === 'true' && DurationFlag !== 'true' && ObjectivesFlag === 'true'){
				        	swal("CDAC Noida Outlay Share and Objectives have been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag !== 'true' && DurationFlag === 'true' && ObjectivesFlag === 'true'){
				        	swal("Duration and Objectives have been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag === 'true' && DurationFlag === 'true' && ObjectivesFlag !== 'true'){
				        	swal("Total Proposal Cost, CDAC Noida Outlay Share and Duration have been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag === 'true' && DurationFlag !== 'true' && ObjectivesFlag === 'true'){
				        	swal("Total Proposal Cost, CDAC Noida Outlay Share and Objectives have been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag !== 'true' && DurationFlag === 'true' && ObjectivesFlag === 'true'){
				        	swal("Total Proposal Cost, Duration and Objectives have been changed. At least one document required");
				        } else if (totalProposalCostFlag !== 'true' && ProposalCostFlag === 'true' && DurationFlag === 'true' && ObjectivesFlag === 'true'){
				        	swal("CDAC Noida Outlay Share, Duration and Objectives have been changed. At least one document required");
				        } else if (totalProposalCostFlag === 'true' && ProposalCostFlag === 'true' && DurationFlag === 'true' && ObjectivesFlag === 'true'){
				        	swal("Total Proposal Cost, CDAC Noida Outlay Share, Duration and Objectives have been changed. At least one document required");
					    } else {
					          gonext();
					    }
						//End of check
					}else{
						swal("At least one document required");
					}
				},				
				error : function(e) {					
					alert('Error: ' + e);				
				}
			});
		}
	  // Checks File Format is Correct or not
	  function fileValidation(a){
		  var fileformat = document.getElementById('formats'+a+'').value;		  
		  const fileTypeArr = fileformat.split(",");
		  const formatlist = [];
		  for(var i=0;i<fileTypeArr.length;i++){
			  formatlist.push(fileTypeArr[i]); 
		  }
	
	        var fileUpload = document.getElementById('file'+a+'');
                 
	        if (fileUpload.files.length > 0) {
	            // Check file size
	            var fileSize = fileUpload.files[0].size; // in bytes
	            var maxSize = 15 * 1024 * 1024; // 15 MB in bytes

	            if (fileSize > maxSize) {
	                swal("The file size exceeds the limit.");
	                fileUpload.value = null;
	                return;
	            }
	        }
	    	for(var k=0;k<formatlist.length;k++)
	    	{
	    		if(fileUpload.files[0].type===formatlist[k]){
	    			isValid=1;
	    			return;
	    		}else{
	    			isValid=2;
	    		}
	    	}
	    
	        if (isValid===2) {
	          swal("Please Select Appropriate File Type.");
	          fileUpload.value = null;
	          return;
	        }
	      }
	  // End Changes
		</script>

</body>
</html>