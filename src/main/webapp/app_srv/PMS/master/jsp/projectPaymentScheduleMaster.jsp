
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-------- Add Style for Fields Validation Error [06-11-2023] ----------%>
<style>
.errorMessageClass{
    margin-top: 4px;
    border-radius: 9px;
    }

.input-container-paymentSchedule{
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    height: 40;
    font-size: 14px;
	}
	
.icon_paymentSchedule{
    /* padding: 15px; */
    background: /* #d2b47e */ #cbdeff;
    color: white;
    min-width: 50px;
    height: 40;
    text-align: center;
    color: #5577c1;
    padding-top: 13px;
    padding-bottom: 13px;
}
.btn_paymentSchedule{
    display: inline-block;
    padding: 0px 9px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
}
</style>

</head>



<body>
	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp">
					<jsp:param name="moduleTypeId" value="2" />
					<jsp:param name="applicationId" value="0" />
				</jsp:include>
			</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Payment Schedule Master</li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
			<div id="userinfo-message" class="hidden">
				<p class="success_msg" id="test"></p>
			</div>
			<c:if test="${message != null && message != ''}">
				<c:choose>
					<c:when test="${status=='success'}">
						<div id="userinfo-message1">
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
			<sec:authorize
				access="hasAuthority('READ_PROJECT_PAYMENT_SCHEDULE_MST')">
				<form:form id="form1"
					modelAttribute="projectPaymentScheduleMasterModel"
					action="/PMS/mst/saveUpdateProjectPaymentScheduleMaster"
					method="post">
					<form:hidden path="numId" />
					<form:hidden path="idCheck" />
					<input type="hidden" id="projectid" value="${projectId}" />
					<input type="hidden" id="encProjectId" value="${encProjectId}"/>
					<input type="hidden" id="projectCost" value="${projectData.projectCost}"/> <!-- Added by devesh on 26-03-24 to get cdac outlay -->
					<input type="hidden" id="totalPaymentScheduleAmount" value="${totalPaymentScheduleAmount}"/> <!--Input added by devesh on 27-03-24 to get total payment schedule ammount -->
					<div class="container col-md-12 col-lg-12 col-sm-12 col-xs-12  ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Project Payment Schedule Master</h4>
								</div>
								<div class="panel-body text-center">

									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-right">
												<label class="label-class"><spring:message
														code="Project_Payment_Schedule.projectName" />:<span
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 text-left">

												<form:hidden path="projectId" value="${projectId}" />
												<h5 class="bold">${projectData.strProjectName}
													<c:if test="${not empty projectData.projectRefrenceNo}">
														<span class="bold blue font_14">&nbsp;(${projectData.projectRefrenceNo})
														</span>
													</c:if>
												</h5>

											</div>
										</div>

									</div>



									<%-- <div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="Project_Payment_Schedule.paymentSequenceNumber" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-indent icon"></i>
													<form:input class="input-field" path="numPaymentSequence" />
													<form:errors path="numPaymentSequence" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Payment_Schedule.paymentDueDate" />:<span
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
													<form:input class="input-field" readonly='true'
														path="strPaymentDueDate" id="strPaymentDueDate" />
													<form:errors path="strPaymentDueDate" cssClass="error" />
												</div>

											</div>
										</div>
									</div>


									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Payment_Schedule.amount" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-inr icon"></i>
													<form:input class="input-field" id="numAmount"
														path="numAmount" />
													<form:errors path="numAmount" cssClass="error" />
												</div>
											</div>
										</div>
									</div> --%>

									<div class="row  ">
										<%-- <div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Payment_Schedule.purpose" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th icon "></i>
													<form:textarea class="input-field" rows="2"
														path="strPurpose" id="strPurpose"></form:textarea>
													<form:errors path="strPurpose" cssClass="error" />
												</div>
											</div>
										</div>
										
										
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Payment_Schedule.remarks" />:</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th icon "></i>
													<form:textarea class="input-field" rows="2"
														path="strRemarks" id="strRemarks"></form:textarea>
													<form:errors path="strRemarks" cssClass="error" />
												</div>
											</div>
										</div>
										<div class="row  ">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
												<div
													class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
													<label class="label-class"><spring:message
															code="Project_Payment_Schedule.milestoneName.is_the_Project_contain_any_Milestone" />:</label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:hidden path="linkedWithMilestone" />
														<input type="checkbox" id="linkedWithMilestone1" class="form-check-input"/>
													</div>
													
												</div>
											</div>
										</div>


										<div class="row hidden" id="numMilestoneDiv">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1"
												>
												<div
													class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
													<label class="label-class"><spring:message
															code="Project_Payment_Schedule.milestoneName" />:<span
														style="color: red;">*</span> </label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select path="numMilestoneId" id="numMilestoneId"
															class="select2Option">
															<form:option value="0">-- Select Milestone --</form:option>

															<c:forEach items="${milestoneData}" var="milestone">
																<form:option value="${milestone.numId}">
																	<c:out value="${milestone.milestoneName}"></c:out>
																</form:option>
															</c:forEach>
														</form:select>
													</div>
												</div>
											</div>


										</div> --%>
										<!-- Table Added by devesh on 06-11-23 to add columns vertically for Payment Schedule -->
								<div class="col-md-11 col-lg-11 col-sm-11 col-xs-11 form-group col-md-push-1">
								<table class="table table-striped table-bordered" id="paymentScheduleMultipleData">
								    <thead>
								         <tr>
								            <th class="hidden text-center col-md-1 "><spring:message code="Project_Payment_Schedule.paymentSequenceNumber" /><span style="color:red">*</span></th>     
								            <th class="text-center col-md-2 "><spring:message code="Project_Payment_Schedule.paymentDueDate"/><span style="color:red">*</span></th>
								            <th class="text-center col-md-2 "><spring:message code="Project_Payment_Schedule.amount"/><span style="color:red">*</span></th>
								            <th class="text-center col-md-2 "><spring:message code="Project_Payment_Schedule.purpose"/><span style="color:red">*</span></th>
								            <th class="text-center col-md-2 "><spring:message code="Project_Payment_Schedule.remarks"/>
								            
								          	<th class="text-center col-md-1 "><spring:message code="Project_Payment_Schedule.milestoneName"/></th>
								            <th class="text-center col-md-1  ActionColumn">Action</th>
								        </tr>
								    </thead>
								    <tbody>
								    <tr id="trow"></tr>
								       <%--  <tr id="0row">
								            <td>
								            	<input class="hidden" id="validField_0"/>
								            	<input class="hidden" id="PaymentScheduleId_0"/>
								                <div class="input-container">
													<!-- <i class="fa fa-indent icon_paymentSchedule"></i> -->
													<input class="input-field" id="numPaymentSequence_0" />
												</div>
								                <div class="alert-danger text-center numPaymentSequence_0 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													 <i class="fa fa-calendar icon_paymentSchedule"></i> 
													<input class="input-field PaymentDueDate" readonly='true' id="strPaymentDueDate_0" />
												</div>
												<div class="alert-danger text-center strPaymentDueDate_0 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-inr icon_paymentSchedule"></i>
													<input class="input-field" id="numAmount_0"/>
												</div>
								                <div class="alert-danger text-center numAmount_0 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strPurpose_0"></textarea>
												</div>
								                <div class="alert-danger text-center strPurpose_0 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strRemarks_0"></textarea>
												</div>
								                <div class="alert-danger text-center strRemarks_0 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								            <div>
								            <form:hidden path="linkedWithMilestone" />
											Is the payment linked to a project milestone?&nbsp;&nbsp;&nbsp;<input type="checkbox" id="linkedWithMilestone0" class="form-check-input" onchange="changeField('0')"/> 
														</div><br>
								                <div class="input-container-paymentSchedule " >
														<select id="numMilestoneId_0" class="select2Option">
															<option value="0">-- Select Milestone --</option>

															<c:forEach items="${milestoneData}" var="milestone">
																<option value="${milestone.numId}">
																	<c:out value="${milestone.milestoneName}"></c:out>
																</option>
															</c:forEach>
														</select>
													</div>
								                <div class="alert-danger text-center numMilestoneId_0 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_paymentSchedule btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_paymentSchedule btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_paymentSchedule btn-danger deleteRowButton' onclick="deletePaymentSchedule(0)">
								                        <i class='fa fa-minus-circle btn_paymentSchedule btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <tr id="1row">
								            <td>
								            	<input class="hidden" id="validField_1"/>
								            	<input class="hidden" id="PaymentScheduleId_1"/>
								                <div class="input-container">
													<!-- <i class="fa fa-indent icon_paymentSchedule"></i> -->
													<input class="input-field" id="numPaymentSequence_1" />
												</div>
								                <div class="alert-danger text-center numPaymentSequence_1 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-calendar icon_paymentSchedule"></i> 
													<input class="input-field PaymentDueDate" readonly='true' id="strPaymentDueDate_1" />
												</div>
												<div class="alert-danger text-center strPaymentDueDate_1 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-inr icon_paymentSchedule"></i>
													<input class="input-field" id="numAmount_1"/>
												</div>
								                <div class="alert-danger text-center numAmount_1 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strPurpose_1"></textarea>
												</div>
								                <div class="alert-danger text-center strPurpose_1 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strRemarks_1"></textarea>
												</div>
								                <div class="alert-danger text-center strRemarks_1 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
														<select id="numMilestoneId_1" class="select2Option">
															<option value="0">-- Select Milestone --</option>

															<c:forEach items="${milestoneData}" var="milestone">
																<option value="${milestone.numId}">
																	<c:out value="${milestone.milestoneName}"></c:out>
																</option>
															</c:forEach>
														</select>
													</div>
								                <div class="alert-danger text-center numMilestoneId_1 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_paymentSchedule btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_paymentSchedule btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_paymentSchedule btn-danger deleteRowButton' onclick="deletePaymentSchedule(1)">
								                        <i class='fa fa-minus-circle btn_paymentSchedule btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <tr id="2row">
								            <td>
								            	<input class="hidden" id="validField_2"/>
								            	<input class="hidden" id="PaymentScheduleId_2"/>
								                <div class="input-container">
													<!-- <i class="fa fa-indent icon_paymentSchedule"></i> -->
													<input class="input-field" id="numPaymentSequence_2" />
												</div>
								                <div class="alert-danger text-center numPaymentSequence_2 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-calendar icon_paymentSchedule"></i>
													<input class="input-field PaymentDueDate" readonly='true' id="strPaymentDueDate_2" />
												</div>
												<div class="alert-danger text-center strPaymentDueDate_2 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-inr icon_paymentSchedule"></i> 
													<input class="input-field" id="numAmount_2"/>
												</div>
								                <div class="alert-danger text-center numAmount_2 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strPurpose_2"></textarea>
												</div>
								                <div class="alert-danger text-center strPurpose_2 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strRemarks_2"></textarea>
												</div>
								                <div class="alert-danger text-center strRemarks_2 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
														<select id="numMilestoneId_2" class="select2Option">
															<option value="0">-- Select Milestone --</option>

															<c:forEach items="${milestoneData}" var="milestone">
																<option value="${milestone.numId}">
																	<c:out value="${milestone.milestoneName}"></c:out>
																</option>
															</c:forEach>
														</select>
													</div>
								                <div class="alert-danger text-center numMilestoneId_2 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_paymentSchedule btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_paymentSchedule btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_paymentSchedule btn-danger deleteRowButton' onclick="deletePaymentSchedule(2)">
								                        <i class='fa fa-minus-circle btn_paymentSchedule btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <tr id="3row">
								            <td>
								            	<input class="hidden" id="validField_3"/>
								            	<input class="hidden" id="PaymentScheduleId_3"/>
								                <div class="input-container">
													<!-- <i class="fa fa-indent icon_paymentSchedule"></i> -->
													<input class="input-field" id="numPaymentSequence_3" />
												</div>
								                <div class="alert-danger text-center numPaymentSequence_3 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-calendar icon_paymentSchedule"></i>
													<input class="input-field PaymentDueDate" readonly='true' id="strPaymentDueDate_3" />
												</div>
												<div class="alert-danger text-center strPaymentDueDate_3 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<i class="fa fa-inr icon_paymentSchedule"></i>
													<input class="input-field" id="numAmount_3"/>
												</div>
								                <div class="alert-danger text-center numAmount_3 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strPurpose_3"></textarea>
												</div>
								                <div class="alert-danger text-center strPurpose_3 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
													<!-- <i class="fa fa-th icon_paymentSchedule "></i> -->
													<textarea class="input-field" rows="2" id="strRemarks_3"></textarea>
												</div>
								                <div class="alert-danger text-center strRemarks_3 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td>
								                <div class="input-container-paymentSchedule">
														<select id="numMilestoneId_3" class="select2Option">
															<option value="0">-- Select Milestone --</option>

															<c:forEach items="${milestoneData}" var="milestone">
																<option value="${milestone.numId}">
																	<c:out value="${milestone.milestoneName}"></c:out>
																</option>
															</c:forEach>
														</select>
													</div>
								                <div class="alert-danger text-center numMilestoneId_3 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_paymentSchedule btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_paymentSchedule btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_paymentSchedule btn-danger deleteRowButton' onclick="deletePaymentSchedule(3)">
								                        <i class='fa fa-minus-circle btn_paymentSchedule btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr> --%>
								    </tbody>
								</table>
								<!-- End of table  -->		
										<div class="row pad-top">
											<a href="#"
												class="new_record blue no-underline font_16  text-shadow hidden"
												id="new_record" title="Click Here to Add New Record"> <span
												class="pull-right padded margin-right bg-blue-text hidden blink"
												id="addnewrecord"> Click Here to Add New Record</span></a>
										</div>

										<sec:authorize
											access="hasAuthority('WRITE_PROJECT_PAYMENT_SCHEDULE_MST')">

											<div class="row pad-top  form_btn">
												<!-- <button type="button" class="btn btn-primary font_14"
													id="save"> -->
												<button type="button" class="btn btn-primary font_14"
													id="savePaymentSchedule"> <!-- Added by devesh on 08-11-23 to change save button function -->
													<span class="pad-right"><i class="fa fa-folder"></i></span>Save
												</button>
												<button type="button" class="btn btn-primary reset font_14"
													id="reset">
													<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
												</button>
											</div>

											<c:if test="${projectId>0 && empty  referer}">
												<div class="row padded pull-left">
													<button type="button" class="btn btn-info reset font_14 "
														id="prev" onclick="goprev()">
														<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
													</button>
												</div>
												<div class="row padded pull-right">
													<button type="button" class="btn btn-info reset font_14 "
														id="next" onclick="gonext()">
														<span class="pad-right"><i
															class="fa fa-arrow-right"></i></span>Next
													</button>
												</div>
											</c:if>
										</sec:authorize>

									</div>
								</div>

							</div>
						</div>


				</form:form>
			</sec:authorize>

			
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>

							<sec:authorize
								access="hasAuthority('WRITE_PROJECT_PAYMENT_SCHEDULE_MST')">
								<div class="row">
									<div class="pull-right">
										<div class="col-md-4">
											<input type="button" value="Delete" id="delete"
												class="btn btn-primary a-btn-slide-text">
										</div>
									</div>
								</div>
							</sec:authorize>


							<script>
								$(document).ready(function() {
									$('#toggle-off').removeAttr('Checked');
									$('#toggle-on').attr('checked',true);
											$('#delete').click(function() {
												ProjectPaymentScheduleDelete();
											});
										});

								function ProjectPaymentScheduleDelete() {
									var chkArray = [];

									$(".CheckBox:checked").each(function() {
										chkArray.push($(this).val());
									});

									var selected;
									selected = chkArray.join(',');
									if (selected.length >= 1) {

										swal(
												{
													title : "Are you sure you want to delete the record?",
													icon : "warning",
													buttons : [ 'No', 'Yes' ],
													dangerMode : true,
												}).then(function(isConfirm) {
											if (isConfirm) {
												$("#idCheck").val(selected);

												submit_form_delete();
											}

											else {

												return false;
											}
										});
									} else {
										swal("Please select at least one checkbox to delete");
									}

								}
								function submit_form_delete() {

									document.getElementById("form1").action = "/PMS/mst/deleteProjectPaymentSchedule";
									document.getElementById("form1").method = "POST";
									document.getElementById("form1").submit();
								}
							</script>

							<table id="datatable"
								class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20 check">Select</th>
										<th class="hidden" width="10%">Payment Sequence Number</th> <!-- Hidden by devesh on 03-04-24 to hide payment sequence number-->
										<th><spring:message code="forms.serialNo"/></th> <!-- Serial number header added by devesh on 03-04-24 -->
										<th width="8%">Payment Due Date</th>
										<th width="8%">Amount</th>
										<th width="25%">Purpose</th>
										<th width="15%">Remarks</th>
										<th width="10%">Is contain any Milestone</th>
										<!-- <th width="10%">Status</th> -->
										<th class="hidden">milestoneId</th>
										<th class="">Milestone Name</th>
										<th width="5%">Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="projectPaymentScheduleModel" varStatus="loop">
										<tr>
											<td><input type="checkbox" class="CheckBox"
												id="Checkbox" value="${projectPaymentScheduleModel.numId}"
												autocomplete="off"><span class="hidden">${projectPaymentScheduleModel.numId}</span></td>

											<td class="hidden">${projectPaymentScheduleModel.numPaymentSequence}</td> <!--numPaymentSequence Hidden by devesh on 03-04-24 -->
											<td class="text-center">${loop.index+1}</td> <!-- Serial number added by devesh on 03-04-24 -->
											<td>${projectPaymentScheduleModel.strPaymentDueDate}</td>
											<td>${projectPaymentScheduleModel.strAmount}</td>
											<td>${projectPaymentScheduleModel.strPurpose}</td>
											<c:choose>
												<c:when
													test="${projectPaymentScheduleModel.strRemarks !=null}">
													<td>${projectPaymentScheduleModel.strRemarks}</td>
												</c:when>
												<c:otherwise>
													<td class="center">-</td>
												</c:otherwise>
											</c:choose>
											
											
											
											
											<c:choose>
												<c:when
													test="${projectPaymentScheduleModel.linkedWithMilestone}">
													<td>Yes</td>
												</c:when>
												<c:otherwise>
													<td>No</td>
												</c:otherwise>
											</c:choose>


											<%-- <c:choose>
												<c:when test="${projectPaymentScheduleModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose> --%>

											
											<td class="hidden">${projectPaymentScheduleModel.numMilestoneId}</td>
											<td class="blue">${projectPaymentScheduleModel.milestoneName}</td>
											<td><sec:authorize
													access="hasAuthority('WRITE_PROJECT_PAYMENT_SCHEDULE_MST')">
													<!-- <span
														class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
														id="edit" aria-hidden="true"></span> -->
													<span
														class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
														id="editPaymentSchedule" aria-hidden="true"></span> <!-- Added by devesh on 08-11-23 to change save button function -->
												</sec:authorize></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>

							<!--End of data table-->
						</fieldset>
					</div>
					<!--End of datatable_row-->
				
			</div>
		</div>
	</section>

<div class='hidden' id="startDate">${projectData.startDate}</div>
<div class='hidden' id="endDate">${projectData.endDate}</div>
<div class='hidden' id="paymentSequence">${paymentSequence}</div>

<div class="input-container-paymentSchedule hidden " >
<select id="numMilestoneId_n" class="select2Option">
<option value="0">-- Select Milestone --</option>
<c:forEach items="${milestoneData}" var="milestone">
<option value="${milestone.numId}">
<c:out value="${milestone.milestoneName}"></c:out>
</option>
</c:forEach>
</select>
</div>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/projectPaymentScheduleMaster.js?v=2"></script>
</body>
</html>