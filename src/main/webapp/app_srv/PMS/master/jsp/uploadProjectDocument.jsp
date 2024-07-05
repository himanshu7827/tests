<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
				<c:if test="${projectId>0 && showWorkflow=='true'}">
					<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
					<jsp:param name="moduleTypeId" value="2"/>
					<jsp:param name="applicationId" value="0"/></jsp:include>
					
					<div class="hidden" id="projectStartDate">${projectMasterModel.startDate}</div>
			<div class="hidden" id="projectEndDate">${projectMasterModel.endDate}</div>
					
				</c:if>
			</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Documents</li>
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


			<form:form id="form1" modelAttribute="projectDocumentMasterModel"
				action="/PMS/mst/saveProjectDocument" method="post"
				enctype="multipart/form-data">
				<form:hidden path="numId" />
				<input type="hidden" id="encProjectId" value="${encProjectId}"/>
				<form:hidden id="applicationId" path ="applicationId" />
				<input type="hidden" id="projectID" value="${projectId}" />
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Project Document </h4>
							</div>
							<div class="panel-body ">
							<!-- added the <form:errors path="projectId" class="error_msg"></form:errors>  -->
							<form:errors path="projectId" class="error_msg"></form:errors>
							<div class="row pad-bottom pad-left pad-right pull-right">
									<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12">
									<a class="font_14 blue bold" onclick="viewAllProjectDocument()"><i class="fa fa-hand-o-right black"></i><i><u>View All Uploaded Documents</u></i></a>
									</div>
									</div>
								<form:errors path="projectId" class="error_msg"></form:errors>
					
										<div class="row pad-top text-center">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="Project_Payment_Schedule.projectName" />:<span
												style="color: red;">*</span></label>
										</div>
								<c:choose>
									<c:when test="${projectId==0}">

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select path="projectId" class="select2Option">
													<form:option value="0">-- Select Project --</form:option>
													<c:forEach items="${projectList}" var="project">
														<form:option value="${project.numId}">${project.strProjectName}</form:option>
													</c:forEach>
												</form:select>

											</div>
										</div>
									
									</c:when>
									<c:otherwise>
									
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<h5 class="bold">${projectMasterModel.strProjectName}
								<c:if test="${not empty projectMasterModel.projectRefNo}">
 								<span class="bold blue font_14">&nbsp;(${projectMasterModel.projectRefNo}) </span>
								</c:if>
								</h5>
								</div>
										<form:hidden path="projectId" value="${projectId}"/>
									</c:otherwise>
								</c:choose>
								</div>
								</div>
								<div class="row pad-top text-center">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<%-- <label class="label-class"> <spring:message
													code="document.documentName" /> :<span style="color: red;">*</span>
											</label> --%>
											<label class="label-class"> Document Type :<span style="color: red;">*</span>
											</label> <!-- Modified by devesh on 29-05-24 to change label name -->
											
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<!-- declare the id dynamicDate and call the function  ondyncamicchange() by varun  -->
											<div class="input-container" id="dynamicDate">
												<form:select path="documentTypeId" class="select2Option" onchange="ondyncamicchange()">
													<form:option   value="0">-- Select Document --</form:option>
													<%-- <c:forEach items="${documentTypeList}" var="document">
														<form:option value="${document.numId}">${document.docTypeName}</form:option>
													</c:forEach> --%>
													<c:forEach items="${documentTypeList}" var="documents">
														<optgroup value ="-1" label="${documents.key}">
															<c:forEach items="${documents.value}" var="document">
																<form:option  value="${document.numId}">${document.docTypeName}</form:option>
															</c:forEach>
														</optgroup>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>

								</div>

								<sec:authorize access="hasAuthority('UPLOAD_PROJECT_DOCUMENT')">
									<div id="docBasicDetails" class="hidden row text-center">
										<div class="row  ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<!-- given the id name nonapprovals by varun on 15-11-2023 -->
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;"  id="nonapprovals">



													<label class="label-class"><spring:message
															code="docupload.document.Date" /> :<span
														style="color: red;">*</span></label>
												</div>
												<!-- added below div to hidden and given the id name administrativeapproval the date by varun on 15-11-2023 -->
													<div
													class=" hidden col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" style="left:7px; top: 12px;"  id="administrativeapproval">

													<label class="label-class"> Document Approval Date :<span
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
										<div class="row  text-center">
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
										<!-- add the hidden class to hide startdate on form by varun  -->
										<div class="row text-center hidden">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify" >
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
										<!-- add the hidden class to hide validupto on form by varun  -->
									 	 <div class="row text-center hidden">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
												 <div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
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

										<div class="row text-center">
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
								</sec:authorize>

								<sec:authorize access="hasAuthority('UPLOAD_PROJECT_DOCUMENT')">
									<table id="docUploadTbl"
										class="table table-striped table-bordered hidden" width="80%">
										<thead>
											<tr>
												<th class="hidden"></th>
												<th class="hidden"></th>
												<th><spring:message code="docupload.document.format" />
												</th>
												<th><spring:message code="docupload.document.file" />  <span style="font-size: smaller; color: red;">(The maximum file size allowed is 15 MB.)</span></th>
											</tr>
										</thead>

										<tbody>
										</tbody>
									</table>


									<div class="row pad-top  form_btn text-center">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>										
										<c:if test="${not empty referrer}">
												<a href="${referrer}" class="btn btn-orange font_14">												
													<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
												</a>
											</c:if>
									</div>

								</sec:authorize>
																	<c:if test="${projectId>0 && empty referrer}"> 
												<div class="row padded pull-left">
									<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" >
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button>
									</div>
										<div class="row padded pull-right">
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="gonext()" >
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
									</div>
									</c:if>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">
							<legend class="bold legend_details">Details :</legend>
<!-- ON SELECT THE DOCUMENT TYPE -->
							<table id="example" class="table table-striped table-bordered hidden"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th>Document Date</th>
										<th class="hidden">Valid From</th>
									<!-- 	to hide the header by varun  -->
									<th class="hidden">Valid Upto</th> 
										<th>Version No.</th>
										<th>Download Files</th>
										<th>Description</th>
										<sec:authorize access="hasAuthority('UPLOAD_PROJECT_DOCUMENT')">
											<th>Edit</th>
									   <!-- <th>Delete</th>	 -->
										</sec:authorize>	
										
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>

<!-------------------------------------->
<!-- Changes ( Display all the uploaded projects document ) in 16/5/2023 -->
							<table id="example1" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th>Document Date</th>
										<!-- to hide the below header by varun  -->
										<th class="hidden">Valid From</th>
										<th class="hidden">Valid Upto</th> 
										<th>Version No.</th>
										<th>Download Files</th>
										<th class=>Description</th>
										<th>Document Type</th> <!-- Added by devesh on 29-05-24 to fix headers -->
										<sec:authorize access="hasAuthority('UPLOAD_PROJECT_DOCUMENT')">
											<th>Edit</th>	
									   <!-- <th>Delete</th>	 -->
										</sec:authorize>	
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${alldocuments}" var="getalldata">
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
															<a onclick="downloadDocument('${upload.encNumId}')">${upload.icon}</a>
														</c:when>
														<c:otherwise>
															<a onclick="downloadDocument('${upload.encNumId}')">${upload.icon}</a> || 
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
			<!---------------  Hide the Delete Button    ------------------->								
<%-- 											<td><span class="fa fa-times btn btn-danger"
												onclick="deleteDocument('${getalldata.encNumId}', ${getalldata.numId})"></span>
											</td> 
--%>
										</tr>
									</c:forEach>
								</tbody>
							</table>

<!-- End Changes -->
						</fieldset>
					</div>
					<!--End of datatable_row-->
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/uploadProjectDocument.js"></script>

</body>
</html>