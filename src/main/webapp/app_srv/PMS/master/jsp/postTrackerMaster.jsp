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
						
						<li class="active"><spring:message code="postTrackerMaster.post.title"/></li>
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

			 <sec:authorize access="hasAuthority('WRITE_POST_TRACKER_MST')"> 
				<form:form id="form1" modelAttribute="postTrackerMasterModel"
					action="/PMS/mst/saveUpdatePostTrackerMaster" method="post">
					<form:hidden path="numId" />
					<form:hidden path="numIds" />
					<div class="container ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center "><spring:message code="postTrackerMaster.post.title"/></h4>
								</div>
								<div class="panel-body text-center">

									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


												<label class="label-class "><spring:message
														code="postTrackerMaster.post.name" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-university icon"></i>
													<form:input class="input-field" path="postName" />
													<form:errors path="postName" cssClass="error" />
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
														code="postTrackerMaster.post.description" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th-large icon"></i>
													<form:textarea class="form-control" rows="2"
														path="postDescription"></form:textarea>
													<form:errors path="postDescription" cssClass="error" />
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
														code="postTrackerMaster.base.salary" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-inr icon"></i>
													<form:input class="input-field" path="baseSalary" />
													<form:errors path="baseSalary" cssClass="error" />
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
														code="postTrackerMaster.post.code" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-asterisk icon"></i>
													<form:input class="input-field" path="strCode" />
													<form:errors path="strCode" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									
									
									
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<label class="label-class"><spring:message code="postTrackerMaster.vacancy.type"/>:<span style="color: red;">*</span>
										</label>
									</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="vacancyType"  class="select2Option">
											
											<form:option value="0">-- Select Vacancy Type --</form:option>														
														 <option value="Regular">Regular</option>
														  <option value="Consolidated Contract">Consolidated Contract</option>
														  <option value="Grade Based Contract">Grade Based Contract</option>		
														<%-- <form:errors path="strVacancyType" cssClass="error" /> --%>
														 												 
											</form:select>	
												 
										</div>
										<form:errors path="vacancyType" cssClass="error" />	
								</div>
								</div>
					</div>


									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


												<label class="label-class "><spring:message
														code="postTrackerMaster.min.experience" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
												<i class="fa fa-user icon"></i>
													<form:input class="input-field" path="minExperience" />
													<form:errors path="minExperience" cssClass="error" />
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
														code="postTrackerMaster.approved.post" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th-large icon"></i>
													<form:input class="input-field" path="approvedPost" />
													<form:errors path="approvedPost" cssClass="error" />
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
														code="postTrackerMaster.notice.period" />(in days) :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th-large icon"></i>
													<form:input class="input-field" path="noticePeriod" />
													<form:errors path="noticePeriod" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

<%-- 									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="postTrackerMaster.start.date" /> :<span class="pad-left-half"
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
									</div> --%>
									<!--Short Code  -->
<%-- 									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="postTrackerMaster.end.date" /> :<span class="pad-left-half"
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
									</div> --%>
									<!-- End short code -->
									<%-- <div class="row ">
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
 --%>

		<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="postTrackerMaster.post.validity" /> :<span class="pad-left-half"
													style="color: red;">*</span></label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-university icon"></i>
													<form:input class="input-field" path="numValidity" />
													<form:errors path="numValidity" cssClass="error" />
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

							<sec:authorize access="hasAuthority('WRITE_POST_TRACKER_MST')">
								<div class="row">

									<div class="pull-right">
										<div class="col-md-4">
											<input type="submit" value="Delete" onclick="" id="delete"
												class="btn btn-primary a-btn-slide-text">
										</div>
									</div>
								</div>
							</sec:authorize>

							<table id="postTrackerMasterTable"
								class="table table-striped table-bordered table-responsive" style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20">Select</th>
										<th>Post Name</th>
										<th>Post Code</th>
										<th>Post Description</th>
										<th>Base Salary</th>
										<th>Vacancy Type</th>
										<th>Min Experience</th>
										<th>Approved Post</th>
										<th>Notice Period(in days)</th>
										<!-- <th>Start Date</th>
										<th>End Date</th> -->
										<th>Validity (in months)</th>
<!-- 										<th>Status</th>
 -->										<sec:authorize access="hasAuthority('WRITE_POST_TRACKER_MST')">
										<th>Edit</th>
										</sec:authorize>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="postTrackerMasterModel">
										<tr>
											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${postTrackerMasterModel.numId}"
												autocomplete="off">${postTrackerMasterModel.numId}</td>
												
											<td>${postTrackerMasterModel.postName}</td>
											<td>${postTrackerMasterModel.strCode}</td>											
											<td>${postTrackerMasterModel.postDescription}</td>
											<td>${postTrackerMasterModel.baseSalary}</td>
											<td>${postTrackerMasterModel.vacancyType}</td>
											<td>${postTrackerMasterModel.minExperience}</td>
											<td>${postTrackerMasterModel.approvedPost}</td>
											<td>${postTrackerMasterModel.noticePeriod}</td>
											<%-- <td>${postTrackerMasterModel.startDate}</td>
											<td>${postTrackerMasterModel.endDate}</td> --%>
                                           <td>${postTrackerMasterModel.numValidity}</td>
											<%-- <c:choose>
												<c:when test="${postTrackerMasterModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose> --%>
										<sec:authorize access="hasAuthority('WRITE_POST_TRACKER_MST')">
											<td><span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												id="edit" aria-hidden="true"></span></td>
										</sec:authorize>
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
		src="/PMS/resources/app_srv/PMS/master/js/PostTrackerMaster.js"></script>

</body>
</html>