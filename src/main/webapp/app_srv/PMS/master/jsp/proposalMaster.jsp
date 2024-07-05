
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--CSS for radio buttons  -->
<style>
.radio {
	display: block;
	position: relative;
	padding-left: 35px;
	/*  margin-bottom: 12px; */
	cursor: pointer;
	font-size: 12px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}
/* Hide the browser's default radio button */
.radio input {
	position: absolute;
	opacity: 0;
	cursor: pointer;
}

/* Create a custom radio button */
.checkmark {
	position: absolute;
	top: 0;
	left: 0;
	height: 25px;
	width: 25px;
	background-color: #eee;
	border-radius: 50%;
}

/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark {
	background-color: #ccc;
}

/* When the radio button is checked, add a blue background */
.container input:checked ~ .checkmark {
	background-color: #4f6eb8;
}

/* Create the indicator (the dot/circle - hidden when not checked) */
.checkmark:after {
	content: "";
	position: absolute;
	display: none;
}

/* Show the indicator (dot/circle) when checked */
.container input:checked ~ .checkmark:after {
	display: block;
}

/* Style the indicator (dot/circle) */
.container .checkmark:after {
	top: 9px;
	left: 9px;
	width: 8px;
	height: 8px;
	border-radius: 50%;
	background: white;
}

</style>
</head>




<body>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
		<div class="row">
<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="1"/>
<jsp:param name="applicationId" value="${applicationId}"/>

</jsp:include>
		</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Proposal Details</li>
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
	<div class="container pad-bottom">
			<sec:authorize access="hasAuthority('WRITE_PROPOSAL_MST')">
				<form:form id="form_proposal" name="form_proposal"
					modelAttribute="proposalMasterModel"
					action="/PMS/mst/saveUpdateProposalMaster" method="post">
					<form:hidden id="numId" path="numId" value="${proposalMasterModel.numId}"/>
					<form:hidden id="totalCost" path="" value="${totalCost}"/>
					<%-------------- Get Project Category ID [25-01-2024] -------------------------------%>
					<input value="${proposalCategoryId}" id="proposalCategoryId" name="categoryId" class="hidden"/>
					<form:hidden  id="applicationId"  path="applicationId" value="${applicationId}"/>
					<form:hidden path="numIds" />
					<!-- <div class="container"> -->
					<%-- ${proposalMasterModel.numId} --%>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Proposal Details</h4>
								</div>
								<div class="panel-body text-center">

									 <div class="row pad-top ">
									 <div class="row pad-top ">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="proposal.proposalTitle" /> :</label>
										
										
												
													<!-- <i class="fa fa-bookmark icon "></i> -->
												<c:choose>
												<c:when test="${proposalMasterModal.proposalTitle !=null}">
													<form:label path="proposalTitle"><span class="blue">${proposalMasterModal.proposalTitle}</span></form:label>
													</c:when>
												<c:otherwise>
												<div class="input-container">
												<form:textarea class="form-control" rows="2"
														path="proposalTitle" ></form:textarea></div>
														</c:otherwise>
														</c:choose>
													<form:errors path="proposalTitle" cssClass="error" />
												



											</div>
										</div>
										
										</div>
						 
						 																			
										<div class="row ">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
											<div
												class="col-md-3 col-lg-3 col-sm-3 col-xs-12  text-justify" style="width:20%;">
												<label class="label-class"><spring:message
														code="proposal.proposalRefNo" /> :<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-3 col-lg-3 col-sm-3 col-xs-12">									
												<div class="input-container">
							<!-- ReadOnly Fields of Proposal Code  -->
														<c:choose>
															<c:when
																test="${proposalMasterModal.proposalRefNo != null}">
																<form:input class="input-field" path="proposalRefNo"
																	id="proposalRefNo" onblur="saveField('proposalRefNo')"
																	value="${proposalMasterModal.proposalRefNo}" readonly="true"/>
															</c:when>
															<c:otherwise>
																<form:input class="input-field" path="proposalRefNo" onblur="saveField('proposalRefNo')" id="proposalRefNo" readonly="true" /> 
													
															</c:otherwise>
														</c:choose>
														<form:errors path="proposalRefNo" cssClass="error" />
													</div> 



												</div>
										</div>
										
</div>
										 <div class="row  ">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<!--Bhavesh (17-07-2023) added the <span style="color: red;">*</span></label>  -->
												<label class="label-class">Date of submission:</label><span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
													<c:choose>
													<c:when test="${proposalMasterModal.dateOfSubmission != null}">
													<form:input class="input-field" readonly='true'
														path="dateOfSubmission" id="dateOfSubmission"  onblur="saveField('dateOfSubmission')" value="${proposalMasterModal.dateOfSubmission}"/>
													</c:when>
													<c:otherwise>
													<form:input class="input-field" readonly='true'
														path="dateOfSubmission"  onblur="saveField('dateOfSubmission')" id="dateOfSubmission" />
													</c:otherwise>
													</c:choose>
													<form:errors path="dateOfSubmission" cssClass="error" />
												</div>

											</div>
										</div>
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"> <spring:message
														code="proposal.duration" /> :<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-power-off icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.duration !=null}">
													<form:input class="input-field validateDuration" onblur="saveField('duration')" path="duration" value="${proposalMasterModal.duration}"></form:input>
													</c:when>
													
													
													<c:otherwise>
													<form:input class="input-field validateDuration" onblur="saveField('duration')" path="duration"></form:input>
													</c:otherwise>
													</c:choose>
													<form:errors path="duration" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

										<div class="row  ">

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<%-- <label class="label-class"><spring:message
														code="master.cdac.share" />:<span style="color: red;">*</span></label> --%>
												<!-- Added by devesh on 06-02-24 to add suggestion after label-->
												<label class="label-class"><spring:message code="master.cdac.share" /> in INR <!-- Label Modified by devesh on 29-05-24 -->
												<span class="badge badge-info" style="font-weight: 500;font-size: 14px;">(Change if different from Total Proposal Cost)</span> :<span style="color: red;">*</span>
												</label>
												<!-- End of label -->
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-inr icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.proposalCost !=null}">
													<form:input class="input-field validateAmt" id="proposalCost" path="proposalCost" onblur="saveField('proposalCost')" value="${proposalMasterModal.proposalCost}"></form:input>
													</c:when>
													<c:otherwise>
													<form:input class="input-field validateAmt" id="proposalCost" onblur="saveField('proposalCost')" path="proposalCost" ></form:input>
													
													</c:otherwise>
													</c:choose>
													<form:errors path="proposalCost" cssClass="error" />
												</div>
											</div>
										</div>
										
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="proposal.objectives" />:<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.objectives != null}">
													<form:textarea class="form-control validateTextArea" rows="2"
														path="objectives" onblur="saveField('objectives')" value="${proposalMasterModal.objectives}"></form:textarea>
													</c:when>
													<c:otherwise>
													<form:textarea class="form-control validateTextArea" onblur="saveField('objectives')" rows="2"
														path="objectives" ></form:textarea>
													</c:otherwise>
													</c:choose>
													<form:errors path="objectives" cssClass="error" />
												</div>
											</div>
										</div>
									
</div>
<div class="row  ">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class" for="pwd"><spring:message
														code="Patch_Tracker.description" />:<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-file icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.summary != null}">
													<form:textarea class="form-control validateTextArea"  rows="2" path="summary" onblur="saveField('summary')" value="${proposalMasterModal.summary}"></form:textarea>
													</c:when>
													<c:otherwise>
													<form:textarea class="form-control validateTextArea"  rows="2" path="summary" onblur="saveField('summary')"></form:textarea>
													
													</c:otherwise>
													</c:choose>
													<form:errors path="summary" cssClass="error" />
												</div>
											</div>
										</div>
										
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="proposal.background" />:</label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-list-alt icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.background !=null}">
													<form:textarea class="form-control" onblur="saveField('background')" rows="2"
														path="background" value="${proposalMasterModal.background}"></form:textarea>
													</c:when>
													<c:otherwise>
													<form:textarea class="form-control validateTextArea" onblur="saveField('background')" rows="2"
														path="background" ></form:textarea>
													</c:otherwise>
													</c:choose>
													<form:errors path="background" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row  ">
									
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="submittedBy" />:<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-users icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.submittedBy !=null}">
													<form:textarea class="form-control validateSubmittedBy" rows="2"
														path="submittedBy" onblur="saveField('submittedBy')" value="${proposalMasterModal.submittedBy}"></form:textarea>
													</c:when>
													<c:otherwise>
													<form:textarea class="form-control validateSubmittedBy"  onblur="saveField('submittedBy')" rows="2"
														path="submittedBy" ></form:textarea>
													</c:otherwise>
													</c:choose>
													<form:errors path="submittedBy" cssClass="error" />
												</div>
											</div>
										</div>
										
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class" for="fts file no"><spring:message
														code="proposal.ftsFile" />:</label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-files-o icon "></i>
													<c:choose>
													<c:when test="${proposalMasterModal.proposalStatus}">
													<form:input class="input-field" path="proposalStatus" onblur="saveField('proposalStatus')"  value="${proposalMasterModal.proposalStatus}" />
													</c:when>
													<c:otherwise>
													<form:input class="input-field" path="proposalStatus"  onblur="saveField('proposalStatus')"  />
													
													</c:otherwise>
													</c:choose>
													<form:errors path="proposalStatus" cssClass="error" />
												</div>
											</div>
										</div>

</div>
									<%-- 	<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="forms.status" /> :</label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<label class="radio">Active <input type="radio"
														checked="checked" name="radio" value="true" id="toggle-on" />
														<span class="checkmark"></span></label>
													
												</div>
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<label  class="radio">Inactive <input
														type="radio" name="radio" name="valid" value="false"
														id="toggle-off" /> <span class="checkmark"></span></label>
													
												</div>
											</div>
										</div> --%>

									

									 <!-- <div class="row pad-top  form_btn">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>
										<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button>
									</div>   -->
								
								<c:if test="${proposalMasterModal.status == 'REV'}">									
									<div class="row  ">
																													
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class" for="remarks"><spring:message
														code="proposal.remarks" />:</label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-files-o icon "></i>
													<form:input class="input-field" path="remarks" onblur="saveField('remarks')"  value="${proposalMasterModal.remarks}" />
												
													<form:errors path="remarks" cssClass="error" />
												</div>
											</div>
										</div>										
										</div>
										</c:if>
										<br>
										<div>
										</div>
								<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" style="float: left;">
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button> 
									
										<br>
										<c:set var="numId" scope="session" value="${proposalMasterModel.numId}"/>  
<%-- 										<c:out value="${numId}"></c:out>
 --%>										<c:if test="${numId>0 }">
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="gonext()" style="float: right;">
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
			</div> 
			<!--End of panel-->
			<!--Start data table-->
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<%-- <fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>
							<sec:authorize access="hasAuthority('WRITE_PROPOSAL_MST')">
								<!-- 	<div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type="button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div> -->
							</sec:authorize>
							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20">Select</th>
										<th><spring:message code="proposal.proposalTitle" /></th>
										<th><spring:message code="proposal.thrustArea" /></th>
										<th>Group</th>
										<th><spring:message code="master.totalCost" /></th>
										<th><spring:message code="proposal.ftsFile" /></th>
										<th><spring:message code="forms.status" /></th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="proposalModel">
										<tr>
											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${proposalModel.numId}"
												autocomplete="off" />${proposalModel.numId}</td>
											<td>${proposalModel.proposalTitle}</td>
											<td>${proposalModel.thrustAreaId}</td>
											<td>${proposalModel.groupId}</td>
											<td>${proposalModel.proposalCost}</td>
											<td>${proposalModel.uploadFile}</td>

											<c:choose>
												<c:when test="${proposalModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>
											<sec:authorize access="hasAuthority('WRITE_PROPOSAL_MST')">
												<td><span
													class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
													id="edit" aria-hidden="true"></span></td>
											</sec:authorize>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<!--End of data table-->
						</fieldset> --%>
					</div>
					<!--End of datatable_row-->
				</div>
			</div>
	

	</section>






	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/proposalMaster.js?v=1.1"></script>

</body>
</html>