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
	
			</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>						
						<li class="active"><spring:message code="label.approval.master" /></li>
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


			<form:form id="form1" modelAttribute="approvalMasterModel" action="/PMS/mst/saveApprovalMaster" method="post">				
			 <form:hidden path="numId"></form:hidden> 
			 <form:hidden path="numApprovalId"></form:hidden> 
			 <form:hidden path="numRoleActionId"></form:hidden> 
			 
				<div class="container">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">
									<spring:message code="label.approval.master" />
								</h4>
							</div>
							<div class="panel-body text-center">
					
								<div class="row ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">

													<label class="label-class"><spring:message
															code="approval.workflow" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option"  path="workshopId"
															id="workshop" onclick="otherChangeActionRole()">
															<option value="0">-- Select Workshop --</option>
															<c:forEach items="${data}"
																var="data">
																<option value="${data.numId}">
																	${data.strWorkflowType}</option>
															</c:forEach>
														</form:select>
														<form:errors path="workshopId" cssClass="error" />
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
															code="approval.role" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="roleId"
															id="roleId" onclick="otherChangeAction()">
															
															<option value="0">-- Select Role --</option>
															<c:forEach items="${roleList}"
																var="data">
																<option value="${data.numId}">
																	${data.roleName}</option>
															</c:forEach>
														</form:select>
														<form:errors path="roleId" cssClass="error" />
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
															code="approval.action" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="actionId"
															id="actionId" onclick="getApprovalDetails()">
															
															<option value="0">-- Select Action --</option>
															<c:forEach items="${actionList}"
																var="data">
																<option value="${data.numActionId}">
																	${data.strName}</option>
															</c:forEach>
														</form:select>
														<form:errors path="actionId" cssClass="error" />
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
															code="approval.next.role" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="nextRoleId"
															id="nextRoleId">
															
															<option value="0">-- Select Next Role --</option>
															<c:forEach items="${roleList}"
																var="data">
																<option value="${data.numId}">
																	${data.roleName}</option>
															</c:forEach>
														</form:select>
														<form:errors path="nextRoleId" cssClass="error" />
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
															code="approval.action.first.page" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="firstPageAction"
															id="firstPageAction" multiple='multiple'>
															
															<option value="0">-- Select Actions --</option>
															<c:forEach items="${actionList}"
																var="data">
																<option value="${data.numActionId}">
																	${data.strName}</option>
															</c:forEach>
														</form:select>
														<form:errors path="firstPageAction" cssClass="error" />
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
															code="approval.action.second.page" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="secondPageAction"
															id="secondPageAction" multiple='multiple'>
															
															<option value="0">-- Select Action --</option>
															<c:forEach items="${actionList}"
																var="data">
																<option value="${data.numActionId}">
																	${data.strName}</option>
															</c:forEach>
														</form:select>
														<form:errors path="secondPageAction" cssClass="error" />
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
															code="approval.transaction.imapct" />:<span
														style="color: red;">*</span></label>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select class="select2Option" path="transactionImpact"
															id="transactionImpact">
															
															<option value="-1">No Entry in Transaction</option>
															<option value="0"> Entry with is_valid=0 </option>
															<option value="1"> Entry with is_valid=1 </option>
														</form:select>
														<form:errors path="transactionImpact" cssClass="error" />
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
														code="approval.status.to.be.updated" /> :<span
													style="color: red;">*</span></label>

											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											
												<div class="input-container">
												
													<form:textarea class="input-field" value=""
														path="statusTobeUpdated"  id="statusTobeUpdated"></form:textarea>
													<form:errors path="statusTobeUpdated" cssClass="error" />
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
															code="approval.copy.to.be.created" />:<span
														style="color: red;">*</span></label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12">
													<%-- <div class="input-container">
														<form:select class="select2Option" path="copyTobeCreated"
															id="copyTobeCreated">
															
															<option value="1">Yes</option>
															<option value="0"> No </option>
															
														</form:select>
														<form:errors path="copyTobeCreated" cssClass="error" />
													</div> --%>
													
													<form:radiobutton  class="toggle-on" path="copyTobeCreated" value="1"
														 id="isfiledy" name="radio"/>
														<form:label path="copyTobeCreated" for="toggle-on1"
															class="btn inline  zero round no-pad">
															<span>YES</span>
														</form:label>
													</div>
													<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
														<form:radiobutton  class="toggle-off" path="copyTobeCreated" value="0"
															id="isfiledn" name="radio" />

														<form:label path="copyTobeCreated" for="toggle-off2"
															class="btn round inline zero no-pad">
															<span class="">No</span>
														</form:label>

												</div>
											</div>
											</div>
										</div>				
	
									</div>
								</div>
								
							</div>
						</div>
					
				 <div class="row pad-top form_btn center">

										<button type="button" class="btn btn-primary font_14" id="save" onclick="detailsSave()">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>
										<button type="button" class="btn btn-primary reset font_14"
											id="reset" onclick="resetForm()">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button>
									</div>
									
									
				
			</form:form>
	</div>
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/ApprovalMaster.js"></script>
	<!-- <script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/ApprovalMaster.js"></script> -->
</body>
</html>