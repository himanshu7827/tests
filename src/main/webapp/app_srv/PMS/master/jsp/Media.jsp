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
        <%--  <div class="hidden" id="encCategoryId">${categoryId}</div>  --%>
			<form:form id="form1" name="form1" modelAttribute="mediaModel"
				action="/PMS/mst/saveMedia" method="post">
				<form:hidden path="numIds" />
				<form:hidden path="numId" />
				
			
				 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
				 <form:hidden path="encCategoryId"  value="${categoryId}"/>
				<div class="container ">
										
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading"><h3 class="text-center text-shadow"><spring:message code="media.lable"/></h3></div>
					<div class="panel-body">
					
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="media.source"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
												<form:select path="source" class="select2Option" onchange="openAnyOther()">
													<form:option value="0">Select</form:option>	
												<form:option value="Magazines">Magazines</form:option>	
												<form:option value="Radio">Radio</form:option>	
												<form:option value="Newspaper">Newspaper</form:option>
												<form:option value="TV">TV</form:option>
												<form:option value="Social Media">Social Media</form:option>
												<form:option value="Others">Others</form:option>
												</form:select>
												<form:errors path="source" cssClass="error" />								
								</div>
							</div>
						</div>
					
					
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" >
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="media.date"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container" >
									<i class="fa fa-calendar icon"></i>
									<form:input class="input-field" readonly='true' placeholder="Date"
														path="dateOfmedia"  id="dateOfmedia"></form:input>
													<form:errors path="dateOfmedia" cssClass="error" />
									
								</div>
							</div>
						</div>
						
					 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="anyOtherType">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="media.anyOther"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container" >
								
									<form:input class="input-field" 
														path="anyOtherDetails"  id="anyOtherDetails"></form:input>
													<form:errors path="anyOtherDetails" cssClass="error" />
									
								</div>
							</div>
						</div>
							
						
						
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="media.source.details"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-film icon"></i> 
									<form:textarea class="input-field" path="sourceDetails"/>
												<form:errors path="sourceDetails" cssClass="error" />
								</div>
							</div>
						</div>	
						
										
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="media.details"/><span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-film icon"></i> 
									<form:input class="input-field" path="details"/>
												<form:errors path="details" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="media.location"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-address-book icon "></i> 
									<form:textarea class="input-field" path="location" />
												<form:errors path="location" cssClass="error" />
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
										<button type="button" class="btn btn-info reset font_14"
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
						<input type="button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div>
</sec:authorize>
							<table id="example" class="table table-responsive table-bordered compact display hover stripe"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th width="10%">Select</th>
										<th width="3%"></th>
										<th class="details-control" width="10%"><spring:message code="media.source" /></th>
										<th class="control" width="20%"><spring:message code="media.source.details" /></th>
										<th class="none"><spring:message code="media.details" /></th>
										<th class="control" width="10%"><spring:message code="media.date" /></th>
										<th class="control" width="20%"><spring:message code="media.location" /></th>
										<th class="control" width="30%"><spring:message code="media.anyOther" /></th>
										 <th class="hidden" ></th>
										<!--  <th class="hidden" ></th> -->
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="mediaData">
										<tr>

											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${mediaData.numId}"/></td>
												<td></td>
											<td>${mediaData.source}</td>
											<td>${mediaData.sourceDetails}</td>
											<td>${mediaData.details}</td>
											<td>${mediaData.dateOfmedia}</td>	
											<td>${mediaData.location}</td>	
											<td>${mediaData.anyOtherDetails}</td>
											<td class="hidden">${mediaData.numId}</td>
										<%-- 	<td class="hidden">${mediaData.numGroupCategoryId}</td> --%>
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
		src="/PMS/resources/app_srv/PMS/master/js/Media.js"></script>
			<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>

</html>