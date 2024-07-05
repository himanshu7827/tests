<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
						<!-- <li class="active">Role Master</li> -->
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

<div class="container pad-top pad-bottom">
        <div class="panel panel-info  ">
<div class="panel-body">
<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strProjectName}</i></span></p>
<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyProgressModel.strMonth}-${monthlyProgressModel.year}</i></span></p>
</div>
</div>
</div> 
			<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
		 <div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
         <%-- <div class="hidden" id="encCategoryId">${categoryId}</div>  --%>
				<form:form id="form1" name="form1" modelAttribute="awardWonModel"
					action="/PMS/mst/saveAwardWon" method="post">
					<form:hidden path="numIds" />
					<form:hidden path="numId" />
					
			
				 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
				 <form:hidden path="encCategoryId"  value="${categoryId}"/>
					<div class="container ">

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading">
									<h3 class="text-center text-shadow">
										<spring:message code="award.won.label" />
									</h3>
								</div>
								<div class="panel-body">

									<div class="row pad-top">
										<%-- <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

												<label class="label-class"><spring:message
														code="award.type" />:<span style="color: red;">*</span></label>
											</div>

											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<form:select path="awardType" class="select2Option">
														<form:option value="0">Select</form:option>
														<form:option value="Award/Letter of Appreciation">Award/Letter of Appreciation</form:option>
														<form:option value="Media/Others">Media/Others</form:option>

													</form:select>
													<form:errors path="awardType" cssClass="error" />
												</div>
											</div>
										</div> --%>
<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

												<label class="label-class"><spring:message
														code="award.name" />:<span style="color: red;">*</span></label>
											</div>

											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-film icon"></i>
													<form:input class="input-field" path="awardName" />
													<form:errors path="awardName" cssClass="error" />
												</div>
											</div>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<label class="label-class" for="pwd"><spring:message
														code="award.date" />:<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
													<form:input class="input-field" readonly='true'
														placeholder="Date" path="dateOfAward" id="dateOfAward"></form:input>
													<form:errors path="dateOfAward" cssClass="error" />

												</div>
											</div>
										</div>
									</div>

									<div class="row pad-top">
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

												<label class="label-class"><spring:message
														code="award.reciepient.name" /><span style="color: red;">*</span></label>
											</div>

											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-film icon"></i>
													<form:input class="input-field" path="recipientName" />
													<form:errors path="recipientName" cssClass="error" />
												</div>
											</div>
										</div>
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<label class="label-class" for="pwd"><spring:message
														code="award.achievement.desc" />:<span style="color: red;">*</span></label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-file-text-o icon "></i>
													<form:textarea class="input-field"
														path="achievementDescription" />
													<form:errors path="achievementDescription" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

									<div class="row pad-top">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

												<label class="label-class"><spring:message
														code="award.location" />:<span style="color: red;">*</span></label>
											</div>

											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-address-book icon"></i>
													<form:input class="input-field" path="location" />
													<form:errors path="location" cssClass="error" />
												</div>
											</div>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<label class="label-class" for="pwd"><spring:message
														code="award.by" />:</label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-user icon"></i>
													<form:input class="input-field" path="awardBy" />
													<form:errors path="awardBy" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row pad-top">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<label class="label-class"><spring:message
														code="product.awarded.for" />:</label>
											</div>
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-user icon"></i>
													<form:input class="input-field" path="projectAwardedFor" />
													<form:errors path="projectAwardedFor" cssClass="error" />
												</div>
											</div>
										</div>

									</div>
									<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
										<div class="row pad-top form_btn center">

											<button type="button" class="btn btn-primary font_14"
												id="save">
												<span class="pad-right"><i class="fa fa-folder"></i></span>Save as Draft
											</button>
											<button type="button" class="btn btn-primary reset font_14"
												id="reset">
												<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
											</button>
											<button type="button" class="btn btn-success font_14" id="previewDetailsBtn">
												<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
											</button>
											<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
										</div>
										<div class="row pad-top" id="mainPrevNext">
						
										</div>
									</sec:authorize>

								</div>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
			<!-- </div> -->
			<!--End of panel-->
			<!--Start data table-->
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>
							<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
								<div class="row">
									<div class="pull-right">
										<div class="col-md-4">
											<input type="button" value="Delete" id="delete" name="Delete"
												class="btn btn-primary a-btn-slide-text">
										</div>
									</div>
								</div>
							</sec:authorize>
							<table id="example" class="table table-responsive table-bordered compact display hover stripe"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20">Select</th>
										<th width="3%"></th>
										<%-- <th class="details-control" width="20%"><spring:message code="award.type" /></th> --%>
										<th class="control" width="20%"><spring:message code="award.name" /></th>
										<th class="control" width="10%"><spring:message code="award.reciepient.name" /></th>
										<th class="none"><spring:message code="award.achievement.desc" /></th>
										<th class="control"><spring:message code="award.date" /></th>
										<th class="none"><spring:message code="award.location" /></th>
										<th class="control"><spring:message code="award.by" /></th>
										<th class="control" width="10%"><spring:message code="product.awarded.for" /></th>
										<th class="hidden"></th>
										<!-- <th class="hidden"></th> -->
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="awardData">
										<tr>

											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${awardData.numId}" /></td>
												<td></td>
											<%-- <td>${awardData.awardType}</td> --%>
											<td>${awardData.awardName}</td>
											<td>${awardData.recipientName}</td>
											<td>${awardData.achievementDescription}</td>
											<td>${awardData.dateOfAward}</td>
											<td>${awardData.location}</td>
											<td>${awardData.awardBy}</td>
											<td>${awardData.projectAwardedFor}</td>
											<td class="hidden">${awardData.numId}</td>
										<%-- 	<td class="hidden">${awardData.numGroupCategoryId}</td> --%>
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
		src="/PMS/resources/app_srv/PMS/master/js/AwardWon.js"></script>

<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>

</html>