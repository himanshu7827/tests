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
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Third Party Contract Master</li>
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

			<sec:authorize access="hasAuthority('WRITE_THIRD_PARTY_MST')">
				<form:form id="form1" modelAttribute="thirdPartyMasterModel"
					action="/PMS/mst/saveUpdateThirdPartyMaster" method="post">
					<form:hidden path="numId" />
					<form:hidden path="numIds" />
					<div class="container ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Third Party Contract Master</h4>
								</div>
								<div class="panel-body text-center">

									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


												<label class="label-class "><spring:message
														code="thirdParty.agency.name" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-university icon"></i>
													<form:input class="input-field" path="agencyName" />
													<form:errors path="agencyName" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="thirdParty.agency.address" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-address-book icon"></i>
													<form:textarea class="form-control" rows="2"
														path="agencyAddress"></form:textarea>
													<form:errors path="agencyAddress" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


												<label class="label-class "><spring:message
														code="thirdParty.contact.person" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-user icon"></i>
													<form:input class="input-field" path="contactPerson" />
													<form:errors path="contactPerson" cssClass="error" />
												</div>
											</div>
										</div>
									</div>


									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


												<label class="label-class "><spring:message
														code="thirdParty.mobile.number" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-mobile icon"></i>
													<form:input class="input-field" path="mobileNumber" />
													<form:errors path="mobileNumber" cssClass="error" />
												</div>
											</div>
										</div>
									</div>




									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


												<label class="label-class "><spring:message
														code="thirdParty.contact.number" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-phone icon"></i>
													<form:input class="input-field" path="contactNumber" />
													<form:errors path="contactNumber" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="thirdParty.contract.startDate" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
													<form:input class="input-field" readonly='true'
														path="startDate" />
													<form:errors path="startDate" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<!--Short Code  -->
									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="thirdParty.contract.endDate" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
													<form:input class="input-field" readonly='true'
														path="endDate" />
													<form:errors path="endDate" cssClass="error" />
												</div>
											</div>


										</div>
									</div>
									<!-- End short code -->
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
								</div>
							</div>
						</div>
					</div>
				</form:form>

			</sec:authorize>

			<!--End of panel-->
			<!--Start data table-->
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>

							<sec:authorize access="hasAuthority('WRITE_THIRD_PARTY_MST')">
								<div class="row">

									<div class="pull-right">
										<div class="col-md-4">
											<input type="submit" value="Delete" onclick="" id="delete"
												class="btn btn-primary a-btn-slide-text">
										</div>
									</div>
								</div>
							</sec:authorize>

							<table id="thirdPartyDataTable"
								class="table table-striped table-bordered" style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20">Select</th>
										<th>Agency Name</th>
										<th>Agency Address</th>
										<th>Contact Person</th>
										<th>Mobile Number</th>
										<th>Contact Number</th>
										<th>Start Date</th>
										<th>End Date</th>
										<th>Status</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="thirdPartyMasterModel">
										<tr>
											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${thirdPartyMasterModel.numId}"
												autocomplete="off">${thirdPartyMasterModel.numId}</td>
											<td>${thirdPartyMasterModel.agencyName}</td>
											<td>${thirdPartyMasterModel.agencyAddress}</td>
											<td>${thirdPartyMasterModel.contactPerson}</td>
											<td>${thirdPartyMasterModel.mobileNumber}</td>
											<td>${thirdPartyMasterModel.contactNumber}</td>
											<td>${thirdPartyMasterModel.startDate}</td>
											<td>${thirdPartyMasterModel.endDate}</td>

											<c:choose>
												<c:when test="${thirdPartyMasterModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>

											<td><span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												id="edit" aria-hidden="true"></span></td>

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


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/thirdPartyMaster.js"></script>

</body>
</html>