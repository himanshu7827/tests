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
<style>
.bg_red{
background: #f5b8b8 !important;
}

.bg_green{
background: #b5dca8 !important;
}

.bg_yellow{
background: #f5f7b0 !important;
}
</style>


<body>
	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Invoice Master</li>
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

			<sec:authorize access="hasAuthority('WRITE_PROJECT_INVOICE_MST')">
				<form:form id="form1" modelAttribute="projectInvoiceMasterModel"
					action="/PMS/mst/saveUpdateProjectInvoiceMaster" method="post">
					<form:hidden path="numId" />
					<input type="hidden" value="${projectId}" id="currProj"/>
					<form:hidden path="idCheck" />
					<div class="container ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Project Invoice Master</h4>
								</div>
								<div class="panel-body text-center">
								<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.projectName" />:<span
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<form:select path="projectId" id="projectId"
														class="select2Option">
														<form:option value="0">-- Select Project --</form:option>

														<c:forEach items="${projectList}" var="projectList">
															<form:option value="${projectList.numId}">
																<c:out value="${projectList.strProjectName}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
													<form:errors path="projectId" cssClass="error" />
												</div>
											</div>
										</div>

									</div>
<div class="row pad-top ">
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

									</div>
								
								


									<c:choose>
										<c:when test="${showForm == 1}">
											<div class="row pad-top" id="frm"></div>
										</c:when>
										<c:otherwise>
											<div class="row pad-top hidden" id="frm"></div>
										</c:otherwise>
									</c:choose>



									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify name1">

												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceReferenceNumber" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-files-o icon "></i>
													<form:input class="input-field" path="strInvoiceRefno" />
													<form:errors path="strInvoiceRefno" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceDate" />:<span
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
													<form:input class="input-field" readonly='true'
														path="dtInvoice" id="dtInvoice" />
													<form:errors path="dtInvoice" cssClass="error" />
												</div>

											</div>
										</div>
									</div>


									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceAmount" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-inr icon"></i>
													<form:input class="input-field" id="numInvoiceAmt"
														path="numInvoiceAmt" />
													<form:errors path="numInvoiceAmt" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.taxComponent" />: </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<select 
														class="select2Option" id="numTaxComponent">
														<option value="0">-- Select --</option>

														<option value="5">5</option>
														<option value="12">12</option>
														<option value="18">18</option>
														<option value="28">28</option>
														

													</select>
													
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceTaxAmount" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-money icon"></i>
													<form:input class="input-field" path="numTaxAmount"
														id="numTaxAmount" />
													<form:errors path="numTaxAmount" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceTotalAmount" />: </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-inr icon"></i>
													<form:input class="input-field" path="numInvoiceTotalAmt"
														readonly="true" />
													<form:errors path="numInvoiceTotalAmt"
														id="numInvoiceTotalAmt" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									
										<div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceType" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<form:select path="strInvoiceType"
														class="select2Option">
														<form:option value="0">-- Select Type --</form:option>

														<option value="Performa">Performa</option>
														<option value="Tax">Tax</option>
														<option value="Tax Invoice">Tax Invoice</option>
														<option value="Bill Supply">Bill of Supply</option>
														<option value="Debit Note">Debit note</option>
														<option value="Credit Note">Credit note</option>

													</form:select>
													<form:errors path="strInvoiceType" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									
								
									
									<%-- <div class="row">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify ">
												<label class="label-class"><spring:message
														code="Project_Invoice_Master.invoiceStatus" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<form:select path="strInvoiceStatus" id="strInvoiceStatus"
														class="select2Option">
														<form:option value="0">-- Select Status --</form:option>



														<option value="Generated">Generated</option>
														<option value="Cancelled">Cancelled</option>
														<!-- <option value="Payment Received">Payment Received</option>
														<option value="Partially Payment Received">Partially Payment Received</option> -->
															



													</form:select>
													<form:errors path="strInvoiceStatus"
														id="numInvoiceTotalAmt" cssClass="error" />
												</div>
											</div>
										</div>
									</div> --%>
									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="forms.status" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:radiobutton path="valid" value="true" id="toggle-on" />
													<form:label path="valid" for="toggle-on"
														class="btn inline  zero round no-pad">
														<span>Active</span>
													</form:label>
												</div>
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:radiobutton path="valid" value="false"
														id="toggle-off" />

													<form:label path="valid" for="toggle-off"
														class="btn round inline zero no-pad">
														<span class="">Inactive</span>
													</form:label>
												</div>
											</div>
										</div>
									</div>
<!-- 
									<div class="row pad-top">
										<a href="#"
											class="new_record blue no-underline font_16  text-shadow hidden"
											id="new_record" title="Click Here to Add New Record"> <span
											class="pull-right padded margin-right bg-blue-text hidden blink"
											id="addnewrecord"> Click Here to Add New Record</span></a>
									</div> -->


									<div class="row pad-top  form_btn">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>
										<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button>
									</div>
									<!-- 	<span class="pull-right padded margin-right bg-blue-text" id="addnewrecord">
								<a href="#"
								class="new_record blue no-underline font_16 blink text-shadow"
								id="new_record" title="Click Here to Add New Record">Click
									Here to Add New Record</a>
							</span> -->

								</div>
							</div>
						</div>
					</div>


				</form:form>
			</sec:authorize>
		<div class="container hidden" id="stagedetails">
			<!-- <div class="container"> -->
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details"> Details :</legend>
							<sec:authorize access="hasAuthority('WRITE_PROJECT_INVOICE_MST')">
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
								$(document).ready(
										function() {
											$('#toggle-off').removeAttr('Checked');
											$('#toggle-on').attr('checked',true);
											$('#userinfo-message').delay(5000)
													.fadeOut();
											$('#delete').click(function() {
												ProjectInvoiceDelete();
											});
										});

								function ProjectInvoiceDelete() {
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

									document.getElementById("form1").action = "/PMS/mst/deletetProjectInvoice";
									document.getElementById("form1").method = "POST";
									document.getElementById("form1").submit();
								}
							</script>

							<table id="datatable"
								class="table table-striped table-bordered hidden"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20 check">Select</th>
										<th class="hidden"></th>
										<th>Invoice Reference Number</th>
										<th>Invoice Date</th>
										<th>Invoice Amount Without Tax(in INR)</th>
										<th>Tax Amount(in INR)</th>
										<th>Invoice Total Amount(in INR)</th>
										<th>Invoice Type</th>
									<!-- 	<th>Invoice Status</th> -->
										<th>Status</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="projectInvoiceModel">
											
									<tr>					
											<td><input type="checkbox" path="checkbox"
												class="CheckBox" id="Checkbox"
												value="${projectInvoiceModel.numId}" autocomplete="off">${projectInvoiceModel.numId}</td>
											<td class="hidden">${projectInvoiceModel.scheduledPaymentID}</td>
											<td>${projectInvoiceModel.strInvoiceRefno}</td>
											<td>${projectInvoiceModel.strInvoiceDate}</td>
											<td class="text-right">${projectInvoiceModel.numInvoiceAmt}</td>
											<td class="text-right">${projectInvoiceModel.numTaxAmount}</td>
											<td class="text-right">${projectInvoiceModel.numInvoiceTotalAmt}</td>
											<td>${projectInvoiceModel.strInvoiceType}</td>
											<td>${projectInvoiceModel.strInvoiceStatus}</td>
									<c:choose>
									<c:when test="${projectInvoiceModel.strInvoiceStatus =='Generated'}">
									<td class="bg_red">${projectInvoiceModel.strInvoiceStatus}</td>
									</c:when>
									<c:when test="${projectInvoiceModel.strInvoiceStatus =='Payment Fully Paid'}">
									<td class="bg_green">${projectInvoiceModel.strInvoiceStatus}</td>
									</c:when>									
									<c:when test="${projectInvoiceModel.strInvoiceStatus =='Payment Partially Paid'}">
									<td class="bg_yellow">${projectInvoiceModel.strInvoiceStatus}</td>
									</c:when>
									</c:choose>	
											<c:choose>
												<c:when test="${projectInvoiceModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>

											<td>
											<sec:authorize access="hasAuthority('WRITE_PROJECT_INVOICE_MST')">
													
													<span
														class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
														id="edit" aria-hidden="true"></span>
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
		</div>
	</section>




<!-- <script
	src="/PMS/resources/app_srv/PMS/global/js/new/jquery-3.3.1.js"></script>
	<script
	src="/PMS/resources/app_srv/PMS/global/js/new/bootstrap.min-3.4.0.js"></script>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/jquery.easyui.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/jquery-ui.js"></script>	
	<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/jquery.dataTables.min.js"></script>
	 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/bootstrapValidator.js"></script>
		<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/select2.min.js"></script> -->
	 
	 


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/projectInvoiceMaster.js"></script>


</body>
</html>