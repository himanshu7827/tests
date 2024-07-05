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
				<div class="hidden" id="encProjectId">${monthlyProgressModel.encProjectId}</div>
				<div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div>
				<%-- <div class="hidden" id="encCategoryId">${categoryId}</div> --%>
				<form:form id="form1" name="form1" modelAttribute="seminarEventModel"
				action="/PMS/mst/saveSeminarEvent" method="post">
				<form:hidden path="numIds" />
				<form:hidden path="numId" />
				
			
				 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
				 <form:hidden path="encCategoryId"  value="${categoryId}"/>
				<div class="container ">
										
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading"><h3 class="text-center text-shadow"><spring:message code="seminar.event"/></h3></div>
					<div class="panel-body">
					
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="seminar.type"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
												<form:select path="seminarType" class="select2Option" onchange="openAnyOther()">
													<form:option value="0">Select</form:option>	
												<form:option value="Workshop">Workshop</form:option>	
												<form:option value="Symposium">Symposium</form:option>	
												<form:option value="Conference">Conference</form:option>	
												<form:option value="Training">Training</form:option>
												<form:option value="AnyOther">Any Other</form:option>

												</form:select>
												<form:errors path="seminarType" cssClass="error" />								
								</div>
							</div>
						</div>
					
					
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" >
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="seminar.date"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container" >
									<i class="fa fa-calendar icon"></i>
									<form:input class="input-field" readonly='true' placeholder="Date"
														path="dateOfSeminar"  id="dateOfSeminar"></form:input>
													<form:errors path="dateOfSeminar" cssClass="error" />
									
								</div>
							</div>
						</div>
						
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="anyOtherType">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="seminar.any.other.type"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container" >
								
									<form:input class="input-field" 
														path="anyOtherTypeDetails"  id="anyOtherTypeDetails"></form:input>
													<form:errors path="anyOtherTypeDetails" cssClass="error" />
									
								</div>
							</div>
						</div>	
							
						
						
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="seminar.coordinating.person"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" path="coordinatingPerson"/>
												<form:errors path="coordinatingPerson" cssClass="error" />
								</div>
							</div>
						</div>	
						
										
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="seminar.collaborators"/><span
									style="font-size:small;"> (If any):</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" path="strCollaborators"/>
												<form:errors path="strCollaborators" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="seminar.roles"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:textarea class="input-field" path="cdacRoles" />
												<form:errors path="cdacRoles" cssClass="error" />
								</div>
							</div>
						</div>
					
															
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="seminar.venue"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-address-book icon"></i> 
									<form:input class="input-field" path="venue" />
												<form:errors path="venue" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="seminar.no.of.participant"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" path="noOfParticipant"/>
												<form:errors path="noOfParticipant" cssClass="error" />
								</div>
							</div>
						</div>
					

					
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="seminar.profile.participant"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i>
									 <form:textarea class="input-field" path="strProfileOfParticipant"/>
												<form:errors path="strProfileOfParticipant" cssClass="error" />
								</div>
							</div>
						</div>
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="thirdPartyDiv">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="seminar.objectives"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
								<form:textarea class="input-field" path="objectives"/>
												<form:errors path="objectives" cssClass="error" />
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
						<input type="button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
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
										<th class="details-control" width="20%"><spring:message code="seminar.type" /></th>
										<th class="control" width="5%"><spring:message code="seminar.date" /></th>
										<th class="none"><spring:message code="seminar.objectives" /></th>
										<th class="none"><spring:message code="seminar.coordinating.person" /></th>
										<th class="none"><spring:message code="seminar.roles" /></th>
										<th class="control" width="20%"><spring:message code="seminar.venue" /></th>
										<th class="control" width="10%"><spring:message code="seminar.no.of.participant" /></th>
										<th class="none"><spring:message code="seminar.profile.participant" /></th>
										 <th class="control" width="20%"><spring:message code="seminar.collaborators" /></th>  
										 <th class="control"><spring:message code="seminar.any.other.type" /></th>
										 <th class="hidden" ></th>
										<!--  <th class="hidden"></th> -->
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="seminarData">
										<tr>

											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${seminarData.numId}"/></td>
												<td></td>
											<td>${seminarData.seminarType}</td>
											<td>${seminarData.dateOfSeminar}</td>
											<td>${seminarData.objectives}</td>
											<td>${seminarData.coordinatingPerson}</td>	
											<td>${seminarData.cdacRoles}</td>
											<td>${seminarData.venue}</td>	
											<td>${seminarData.noOfParticipant}</td>
											<td>${seminarData.strProfileOfParticipant}</td>
											<td>${seminarData.strCollaborators}</td> 
											<td>${seminarData.anyOtherTypeDetails}</td> 
											<td class="hidden">${seminarData.numId}</td>
											<%-- <td class="hidden">${seminarData.numGroupCategoryId}</td> --%>
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
		src="/PMS/resources/app_srv/PMS/master/js/SeminarEvent.js"></script>

<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		

<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>
</html>