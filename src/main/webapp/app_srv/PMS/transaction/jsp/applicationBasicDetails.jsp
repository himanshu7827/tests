<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>
.oddRow {
	background-color: #c2c3ea;
}
</style>

</head>

<body>


	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<%-- <jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="1"/>

</jsp:include> --%>
			</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active"><spring:message
								code="form.application.basic.dtl" /></li>
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


			<form:form id="form1" modelAttribute="applicationModel" action=""
				method="post">
				<form:hidden path="numId" value="${applicationModel.numId }" />
				<form:hidden path="encNumId" value="${applicationModel.encNumId}"/>
				<!-- Added by devesh on 03/10/23 for setting proposal cost -->
				<form:hidden path="proposalCost" value="${applicationModel.proposalCost}"/>
				<!-- End of setting proposal cost -->
				<div class="container">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">
									<spring:message code="form.application.basic.dtl" />
								</h4>
							</div>
							<div class="panel-body text-center">
								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
										<h5 class="red">
											*Kindly fill all amounts in <span class="black bold">INR</span>
										</h5>
									</div>
								</div>
								<div class="row pad-top">
									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.organisationName" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<c:choose>
														<c:when test="${fn:length(organisationList) > 1 }">
															<form:select class="select2Option" path="organisationId"
																id="organisationId" onChange="saveField('organisationId')" >
																<option value="0">-- Select Organisation --</option>
																<c:forEach items="${organisationList}"
																	var="organisation">
																	<option value="${organisation.numId}"
																		${organisation.numId == applicationModel.organisationId ? 'selected="selected"' : ''}>
																		${organisation.organisationName}</option>
																</c:forEach>
															</form:select>
															<form:errors path="organisationId" cssClass="error" />
														</c:when>
														<c:otherwise>
															<form:hidden path="organisationId"
																value="${organisationList[0].numId}" />
															<span>${organisationList[0].organisationName}</span>
														</c:otherwise>
													</c:choose>
												</div>


											</div>
										</div>
									</div>


									<div class="row ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="group.groupName" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
											<c:choose>
											<c:when test = "${fn:length(grouplist) == 1 }">
											<c:if test = "${grouplist[0].numId == 8}">		
												<form:select class="select2Option" path="groupId" id="groupId" onChange="saveField('groupId')">												
												<option value="0">-- Select Group --</option>												
												<c:forEach items="${grouplist}" var="group">
												<option value="${group.numId}"
												${group.numId == applicationModel.groupId ? 'selected="selected"' : ''}>
												${group.groupName}</option>
													</c:forEach>
												</form:select>
												<form:errors path="groupId" cssClass="error" />
											</c:if>
											<c:if test = "${grouplist[0].numId != 8}">	
												<form:hidden path="groupId" value="${grouplist[0].numId}"/>
												 <span>${grouplist[0].groupName}</span>
											</c:if>
											</c:when>
								
												<c:otherwise>
												<form:select class="select2Option" path="groupId" id="groupId" onChange="saveField('groupId')">												
												<option value="0">-- Select Group --</option>												
												<c:forEach items="${grouplist}" var="group">
												<option value="${group.numId}"
												${group.numId == applicationModel.groupId ? 'selected="selected"' : ''}>
												${group.groupName}</option>
													</c:forEach>
												</form:select>
												<form:errors path="groupId" cssClass="error" /> 												 
												 </c:otherwise>
												</c:choose>															
											</div>

										</div>
									</div>
								</div>
								
									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="proposal.proposalTitle" /> :<span
													style="color: red;">*</span></label>

											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">

													<form:textarea class="form-control" rows="2"
														path="proposalTitle" onblur="saveField('proposalTitle')"  id="proposalTitle"></form:textarea>
													<form:errors path="proposalTitle" cssClass="error" />
												</div>



											</div>
										</div>
									</div>

									<%-- <div class="row pad-top">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="application.businesstype.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select class="select2Option" path="businessTypeId" id="businessTypeId">
													<option value="0">-- Select Business Type --</option>
													<c:forEach items="${businessTypeModelList}"
														var="businessTypeModel">
														<option value="${businessTypeModel.numId}" 
														${businessTypeModel.numId == applicationModel.businessTypeId ? 'selected="selected"' : ''}>
															${businessTypeModel.businessTypeName}</option>
													</c:forEach>
												</form:select>
												<form:errors path="businessTypeId" cssClass="error" />
											</div>
										</div>
									</div>
								</div> --%>

									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="application.projecttype.label" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<form:select class="select2Option" path="projectTypeId"
														id="projectTypeId" onChange="saveField('projectTypeId')">
														<option value="0">-- Select Project Type --</option>
														<c:forEach items="${projectTypeModelList}"
															var="projectTypeModel">
															<option value="${projectTypeModel.numId}"
																${projectTypeModel.numId == applicationModel.projectTypeId ? 'selected="selected"' : ''}>
																${projectTypeModel.projectTypeName}</option>
														</c:forEach>
													</form:select>
													<form:errors path="projectTypeId" cssClass="error" />
												</div>

											</div>
										</div>
									</div>

									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="application.project.category.label" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<form:select class="select2Option" path="categoryId"
														id="categoryId"  onChange="saveField('categoryId')">
														<option value="0">-- Select Project Category --</option>
														<c:forEach items="${projectCategoryModelList}"
															var="projectCategoryModel">
															<option value="${projectCategoryModel.numId}"
																${projectCategoryModel.numId == applicationModel.categoryId ? 'selected="selected"' : ''}>
																${projectCategoryModel.categoryName}</option>
														</c:forEach>
													</form:select>
													<form:errors path="categoryId" cssClass="error" />
												</div>

											</div>
										</div>
									</div>



								




									<%-- 							<div class="row ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class" for="pwd"><spring:message
														code="proposal.proposalType" /> :<span
													style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<form:select path="proposalType" class="select2Option" id="proposalType">
														<form:option value="0"> [Select Proposal Type]</form:option>
														<form:option value="R&D Project">R&D Project</form:option>
														<form:option value="Commercial Project">Commercial Project</form:option>
													</form:select>
												</div>
											</div> 
										</div>
									</div> --%>

									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="proposal.thrustArea" /> :<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<form:select path="thrustAreaId" class="select2Option"
														multiple="true" id="thrustAreaId" onChange="saveField('thrustAreaId')">
														<%-- 									<form:option value="0">--Select Thrust--</form:option>
 --%>
														<c:forEach items="${thrustlist}" var="listthrust">
															<form:option value="${listthrust.numId}">
																<c:out value="${listthrust.strThrustAreaName}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
										</div>
										</div>

										<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">
													<label class="label-class"><spring:message
															code="proposal.submittedTo" /> :<span
														style="color: red;">*</span></label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select path="clientId" onChange="saveField('clientId');abc();"
															id="clientId" class="select2Option">
															<form:option value="0"> -- Select Organization Name -- </form:option>
															<c:forEach items="${clientlist}" var="listclient">
																<form:option value="${listclient.numId}">${listclient.clientName}</form:option>
															</c:forEach>
														</form:select>
													</div>
												</div>

												<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12">
													<button type="button" class="btn-orange font_14 text-left" style="width: 105%;"
														onclick="openChildWindow('/PMS/mst/clientMaster')">
														<span class="pad-right"><i class="fa fa-plus"></i></span>Add Organization
													</button>
												</div>
											</div>
										</div>
										<div class="row ">

											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">
													<label class="label-class"> <spring:message
															code="proposal.contactPerson" /> :<span
														style="color: red;">*</span></label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:errors path="contactPerson" cssClass="error" />
														<div class="input-container">
															<form:select path="contactPersonId" id="contactPerson"
																multiple="true" class="select2Option"  onChange="saveField('contactPerson')"> 
																<%--  														<form:option value="0">[Select Contact Person:]</form:option>
 --%>
																<c:forEach items="${clientContactlist}"
																	var="clientContact">
																	<form:option value="${clientContact.numId}">
																		<c:out value="${clientContact.strContactPersonName}"></c:out>
																	</form:option>
																</c:forEach>
															</form:select>
														</div>
													</div>
												</div>
												<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12">
													<button type="button" class="btn-orange font_14"
														style="width: 105% !important;text-align: left;"
														onclick="openChildWindow('/PMS/mst/clientContactPersonMaster')">
														<span class="pad-right"><i class="fa fa-plus"></i></span>Add
														Contact Person
													</button>
												</div>
											</div>
										</div>
										<div class="row ">

											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">
													<label class="label-class"><spring:message
															code="endUser.Name" /> :</label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select path="endUserId" id="endUserId"
															class="select2Option" onChange="saveField('endUserId')">
															<form:option value="0"> -- Select Client -- </form:option>
															<c:forEach items="${endUserlist}" var="endUser">
																<form:option value="${endUser.numId}">${endUser.clientName}</form:option>
															</c:forEach>
														</form:select>
													</div>
												</div>

												<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12">
													<!-- style="padding:2px 255px;"  -->
													<button type="button" class="btn-orange font_14"
														onclick="openChildWindow('/PMS/mst/endUserMaster')" style="width: 105% !important;text-align: left;">
														<span class="pad-right"><i class="fa fa-plus"></i></span>Add
														Client
													</button>
												</div>
											</div>
										</div>

										<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

													<label class="label-class"><spring:message
															code="application.totalProposal.cost" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<fmt:formatNumber type="number" var="fmtCost"
															pattern="###########.##" maxFractionDigits="3"
															value="${applicationModel.totalProposalCost}" />
														<form:input path="totalProposalCost" class="input-field validateAmt " id="totalProposalCost" onblur="saveField('totalProposalCost')" 
															value="${fmtCost}" />
														<form:errors path="totalProposalCost" cssClass="error" />
													</div>

												</div>
											</div>
										</div>


										<div class="row ">
											<div
												class="col-md-2 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

													<label class="label-class"><spring:message
															code="form.collaborative" />:</label>
												</div>

												<div class="col-md-1 col-lg-1 col-sm-2 col-xs-6">
													<div class="input-container">
														<form:checkbox id="collaborativeCheck" class="input-field"
															path="collaborative" value='true' onchange="saveField('collaborativeCheck')"/>
														<form:errors path="collaborative" cssClass="error" />
													</div>

												</div>
											</div>
										</div>


										<div class="row ">
											<div
												class="col-md-2 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

													<label class="label-class"><spring:message
															code="application.corporateApproval" />:</label>
												</div>

												<div class="col-md-1 col-lg-1 col-sm-2 col-xs-6">
													<div class="input-container">
														<form:checkbox id="corporateCheck" class="input-field"
															path="corporateApproval" value='true'  onchange="saveField('corporateCheck')"/>
														<form:errors path="corporateApproval" cssClass="error" />
													</div>

												</div>
											</div>
										</div>


										<div id="corporateApprovalDiv1">
											<div class="row ">
												<div
													class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
													<div
														class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

														<label class="label-class"><spring:message
																code="application.dateofsub" />:<span
															style="color: red;">*</span></label>
													</div>

													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<i class="fa fa-calendar icon"></i>
															<form:input class="input-field" readonly='true'
																path="dateOfSubmission" onblur="saveField('dateOfSubmission')" />
															<form:errors path="dateOfSubmission" cssClass="error" />
														</div>

													</div>
												</div>
											</div>

											<div class="row ">
												<div
													class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
													<div
														class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

														<label class="label-class"><spring:message
																code="application.clearanceReceive" />:<span
															style="color: red;">*</span></label>
													</div>

													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<i class="fa fa-calendar icon"></i>
															<form:input class="input-field" readonly='true'
																path="clearanceReceivedDate"  onblur="saveField('clearanceReceivedDate')"/>
															<form:errors path="clearanceReceivedDate"
																cssClass="error" />
														</div>

													</div>
												</div>
											</div>
										</div>
										<div class="row pad-top  form_btn">

											<button type="button" class="btn btn-primary reset font_14"
												id="reset">
												<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
											</button>

											<button type="button" class="btn btn-primary font_14"
												id="save">
												<span class="pad-right"><i class="fa fa-folder"></i></span>Save
												&amp; Next
											</button>


										</div>
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- </div> -->
			<!--End of panel-->
		</div>
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/applicationBasicDetails.js"></script>
</body>


</html>