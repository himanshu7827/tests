<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/jquery-ui.css">	

</head>

<body>
	<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">Project Payment Received</li>
		</ol>
	</div>
</div>
<div class="row ">
		
		 </div>
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

		<sec:authorize access="hasAuthority('WRITE_PROJECT_PAYMENT_RECEIVED_MST')">	
			<form:form id="form1" modelAttribute="projectPaymentReceivedModel"
				action="/PMS/mst/saveUpdatePaymentReceived" method="post">
				<form:hidden path="numId" />
				<form:hidden path="idCheck" />
				<input type="hidden" value="${projectId}" id="currProj"/>
				<form:hidden path="" id="startDate" value=""/>
				<div class="container ">
		<div id ="myDiv" class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Project Payment Received</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

				
										<label class="label-class"><spring:message code="Payment_Received.project_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="projectId" id="projectId" class="select2Option">
												<form:option value="0">-- Select Project --</form:option>					
												 <%---------------------- Add Project Name with Reference Number [05-12-2023] --------------------%>
												 <c:forEach items="${projectList}" var="projectList">
											         <c:choose>
											            <c:when test="${currentProjectID == projectList.numId}">
															<c:choose>
													            <c:when test="${not empty projectList.projectRefrenceNo}">
														             <form:option value="${projectList.numId}" selected="selected">
												                    	<c:out value="${projectList.strProjectName} [${projectList.projectRefrenceNo}]"></c:out>
												                	</form:option>
													            </c:when>
													            <c:otherwise>
														             <form:option value="${projectList.numId}" selected="selected">
												                    	<c:out value="${projectList.strProjectName}"></c:out>
												                	</form:option>
													            </c:otherwise>
													        </c:choose>
											            </c:when>
											            <c:otherwise>
															<form:option value="${projectList.numId}">
																<%-- <c:out value="${projectList.strProjectName} [${projectList.projectRefrenceNo}]"></c:out> --%>
																<c:choose>
														            <c:when test="${not empty projectList.projectRefrenceNo}">
														                <c:out value="${projectList.strProjectName} [ ${projectList.projectRefrenceNo}]"></c:out>
														            </c:when>
														            <c:otherwise>
														                <c:out value="${projectList.strProjectName}"></c:out>
														            </c:otherwise>
														        </c:choose>
														     </form:option>
														 </c:otherwise>
													</c:choose>
                                                   <%---------------------- End of Add Project Name with Reference Number [05-12-2023] --------------------%>
												</c:forEach>
												
												
											</form:select>
											<form:errors path="projectId" cssClass="error" />
										</div>
									</div>
								</div>

							</div>
							
							<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="Payment_Received.is_Payment_Related_with_invoice" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="paymentWithoutInvoice"  value="false" id="withInvoice"	onchange="hideSchedulePayments()"  />
									<form:label path="paymentWithoutInvoice" for="withInvoice"
										class="btn inline  zero round no-pad">
										<span>Yes</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="paymentWithoutInvoice" value="true" id="withoutInvoice" onchange="openSchedulePayments()" />

									<form:label path="paymentWithoutInvoice" for="withoutInvoice"
										 class="btn round inline zero no-pad">
										<span class="">No</span>
									</form:label>
</div>
							</div>
						</div>						
					</div> 
							
							
				<div id="isWithPayment" class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="Payment_Received.is_Payment_Related_with_Prev_payment" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="prevPayment"  value="true" id="withPayment"	 />
									<form:label path="prevPayment" for="withPayment"
										class="btn inline  zero round no-pad">
										<span>Yes</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="prevPayment" value="false" id="withoutPayment" />

									<form:label path="prevPayment" for="withoutPayment"
										 class="btn round inline zero no-pad">
										<span class="">No</span>
									</form:label>
</div>
							</div>
						</div>						
					</div> 
					
<%-- 					<div class="row pad-top" id="schdPay">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class">Scheduled Payment:</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<form:select path="scheduledPaymentIDs" id="scheduledPaymentID"
														class="select2Option" multiple="true">
														<form:option value="0">-- Select Scheduled Payment --</form:option>

													</form:select>
													<form:errors path="scheduledPaymentIDs" cssClass="error" />
												</div>
											</div>
										</div>

									</div> --%>
									
									
									<div class="row pad-top" id="schdPay">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

				
										<label class="label-class">Scheduled Payment:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="scheduledPaymentIDs" id="scheduledPaymentID"
														class="select2Option" multiple="true">
												<form:option value="0">-- Select Project --</form:option>					
												
										
											</form:select>
											<form:errors path="scheduledPaymentIDs" cssClass="error" />
										</div>
									</div>
								</div>

							</div>

					
					<div id="PaymentDetails" class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

										<label class="label-class"><spring:message code="Payment_Received.project_payment_details"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										
										<div class="input-container">
											<form:select path="paymentId" id= "paymentId" class="select2" style="width:100%">
												 <form:option value="0" placeholder="Payment Received Detail">-- Payment Received Detail --</form:option> 
												  
												<c:forEach items="${paymentList}"
													var="row">
													<form:option  class="font_16" value="${row.numId}" placeholder="Select Detail">
														<c:out value="${row.strUtrNumber}[ Dated ${row.strPaymentRcvdDate}(INR)${row.numReceivedAmount }]"></c:out>
													</form:option> 
												</c:forEach> 
											</form:select>
											<form:errors path="paymentId" cssClass="error" />
											
										</div>
									</div>
								</div>

							</div>
							
					
								<div id = "invoice" class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

										<label class="label-class"><spring:message code="Payment_Received.project_Invoice_Number"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="invoiceId" id="invoiceId" class="select2" style="width:100%">
												 <form:option value="0">-- Project Invoice Number--</form:option>	
												 										
										 <c:forEach items="${invList}"
													var="row">
													<form:option value="${row.numId}" class="font_16">
														<c:out value="${row.strInvoiceRefno} [ Dated ${row.dtInvoice} : (INR)${row.numInvoiceAmt}]"></c:out>
													</form:option> 
												</c:forEach>
												
									</form:select>
								<form:errors path="invoiceId" cssClass="error" />
												
										</div>
									</div>
								</div>

							</div>
							<c:choose>
							<c:when test="${showForm == 1}">
								<div class="row pad-top" id="frm">		</div>						
							</c:when>
							<c:otherwise>
								<div class="row pad-top hidden" id="frm"></div>
							</c:otherwise>
						</c:choose>
						

								
								
						<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

								<label class="label-class" ><spring:message code="Payment_Received.payment_Date"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dtPayment" id="dtPayment" placeholder="Payment Date"/>							
								<form:errors path="dtPayment" cssClass="error" />
								</div> 
	
							</div>
						</div> 
								
								</div>
								
								<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

										<label class="label-class"><spring:message code="Payment_Received.received_Amount"/>
										</label>
										<span class="font_12 orange">( <spring:message code="lable.without.tax"/> )</span>
										:<span style="color: red;">*</span>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<i class="fa fa-inr icon"></i>
											<form:input class="input-field" placeholder="Received Amount" id="numReceivedAmount" 
												path="numReceivedAmount" />
											<form:errors path="numReceivedAmount" cssClass="error" />
										</div>
									</div>
								</div>
								</div>
								
							<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class"><spring:message code="Payment_Received.payment_Mode"/>:<span style="color: red;">*</span>
										</label>
									</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="strPaymentMode" id="strPaymentMode" class="select2Option">
											
											<form:option value="0">-- Select Payment Mode --</form:option>														
														 <option value="Cheque">Cheque</option>
														  <option value="Draft">Draft</option>
														  <option value="NEFT">NEFT</option>
														 <option value="RTGS">RTGS</option>		
														<%-- <form:errors path="strPaymentMode" cssClass="error" /> --%>
														 												 
											</form:select>	
											<form:errors path="strPaymentMode" cssClass="error" />		 
										</div>
								</div>
								</div>
								</div>
								
									<div class="row  ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class"><spring:message code="Payment_Received.utr_Number"/>:
										</label>
									</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<i class="fa fa-files-o icon"></i>
											<form:input class="input-field" 
												path="strUtrNumber"/>
											<form:errors path="strUtrNumber" id="strUtrNumber" cssClass="error" />
										</div>
									</div>
								</div>
								</div>
								
						<div id = "remarksDiv" class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

										<label class="label-class"><spring:message code="Payment_Received.project_Remarks"/>:<span
											style="color: red;">*</span></label>
									</div>
		
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<i class="fa fa-files-o icon"></i>
											<form:input class="input-field" 
												path="remarks"/>
											<form:errors path="remarks" id="strRemarks" cssClass="error" />
										</div>
									</div>
								</div>
								</div>
<%------------------------------------  Add 6 fields radio buttons [ excessPaymentAmount,itTDS,gctTDS,LD,ShortPayment and otherRecovery] in the Form [05-12-2023] --------------------------------------------------%>
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class">Excess Payment :</label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-inr icon"></i>
										<form:input class="input-field" placeholder="Excess Payment Amount" id="excessPaymentAmount" 
												path="excessPaymentAmount" />
										<form:errors path="excessPaymentAmount" cssClass="error" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class">IT TDS :</label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-inr icon"></i>
										<form:input class="input-field" placeholder="IT TDS Amount" id="itTDS" 
												path="itTDS" />
										<form:errors path="itTDS" cssClass="error" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class">GST TDS :</label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-inr icon"></i>
										<form:input class="input-field" placeholder="GST TDS Amount" id="gstTDS" 
												path="gstTDS" />
										<form:errors path="gstTDS" cssClass="error" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class">Short Payment :</label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-inr icon"></i>
										<form:input class="input-field" placeholder="Short Payment Amount" id="shortPayment" 
												path="shortPayment" />
										<form:errors path="shortPayment" cssClass="error" />
									</div>
								</div>
							</div>
						</div>
				        <div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class">LD :</label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-inr icon"></i>
										<form:input class="input-field" placeholder="LD Amount" id="ldPayment" 
												path="ldPayment" />
										<form:errors path="ldPayment" cssClass="error" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
										<label class="label-class">Other Recovery :</label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-inr icon"></i>
										<form:input class="input-field" placeholder="Other Recovery Amount" id="otherRecovery" 
												path="otherRecovery" />
										<form:errors path="otherRecovery" cssClass="error" />
									</div>
								</div>
							</div>
						</div>																	
<%---------------------------------- End of   Add 6 fields radio buttons [ excessPaymentAmount,itTDS,gctTDS,LD,ShortPayment and otherRecovery] in the Form [05-12-2023]----------------------------------------------------%>							
								<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="forms.status" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid"  value="true" id="toggle-on"	 />
									<form:label path="valid" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Active</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid" value="false" id="toggle-off" />

									<form:label path="valid" for="toggle-off"
										 class="btn round inline zero no-pad">
										<span class="">Inactive</span>
									</form:label>
</div>
							</div>
						</div>						
					</div> 
					
					
									<div class="row pad-top">
										<a href="#"
											class="new_record blue no-underline font_16  text-shadow hidden"
											id="new_record" title="Click Here to Add New Record"> <span
											class="pull-right padded margin-right bg-blue-text hidden blink"
											id="addnewrecord"> Click Here to Add New Record</span></a>
									</div>
					
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					</div>
					</div> 
					</div>
					
					</div>
					</div>

						<!-- 	<span class="pull-right padded margin-right bg-blue-text hidden" id="addnewrecord">
								<a href="#"
								class="new_record blue no-underline font_16 blink text-shadow"
								id="new_record" title="Click Here to Add New Record">Click
									Here to Add New Record</a>
							</span> -->

			</form:form>
	</sec:authorize>


<div class="container hidden" id="stagedetails">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>







			<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 hidden"
				id="stagedetails">
				<div class=" datatable_row pad-bottom">
					<fieldset class="fieldset-border">

						<legend class="bold legend_details">Project Payment received Details :</legend> -->
							<sec:authorize access="hasAuthority('WRITE_PROJECT_PAYMENT_RECEIVED_MST')">
					<!-- 	<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="button" value="Delete" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div>
							</div>
						</div> -->
				</sec:authorize>
				
						<script>
							$(document).ready(function() {
								$('#toggle-off').removeAttr('Checked');
								$('#toggle-on').attr('checked',true);
								
								$('#userinfo-message').delay(5000).fadeOut();
								$('#delete').click(function() {
									ProjectPaymentDelete();
								});
							});

							function ProjectPaymentDelete() {
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

								document.getElementById("form1").action = "/PMS/mst/deletePaymentReceived";
								document.getElementById("form1").method = "POST";
								document.getElementById("form1").submit();
							}
						</script>
						<%------------------- Add Some Column in the table [05-12-2023] --------------------------%>
						<table id="datatable"
							class="table table-striped table-bordered hidden"
							style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<!-- <th class="width20 check">Select</th>	 -->	
									<th>S. No.</th>							
									<th>Payment Date</th>
									<th>Received Amount (in INR)</th>
									<th>Other Payments (in INR)</th>
									<th>Excess Payment (in INR)</th>
									<th>Payment Mode</th>
									<th>Utr Number</th>							
									<th>Status</th>
									<th>Final Received (in INR)</th>
									<th><spring:message code="Payment_Received.project_Invoice_Number"/></th>
									<th class="hidden"></th>
									<th class="hidden"></th>
									<th>Edit</th>
								</tr>
							</thead>
							<tbody class="">
							
							</tbody>
						</table>

						<!--End of data table-->
					</fieldset>
				</div>
				<!--End of datatable_row-->
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
             <table id="invoiceDetailsTbl" class="table table-striped table-bordered example_det "
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
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/ProjectPaymentReceived.js"></script>

</body>
</html>