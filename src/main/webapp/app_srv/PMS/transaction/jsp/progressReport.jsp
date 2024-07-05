<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css">
</head>
<style>
.bg_grey{
background: LemonChiffon !important;
}
#prog-file-upload{
	padding-top: 5%;
}
    .ui-datepicker-calendar {
        display: none;
    }
    
</style>
<body>
<div id="hiddenFields"></div>
<section id="main-content" class="main-content merge-left">
		  <c:forEach items="${categoryList}" var="categoryList">
			<input type="hidden" id="isProjectBasedCat_${categoryList.numCategoryId}" value="${categoryList.strProjectGroupFlag}"/>
			<input type="hidden" id="strCatgController_${categoryList.numCategoryId}" value="${categoryList.strCategoryController}"/>
		</c:forEach> 
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active">Monthly Progress Report</li>
					</ol>
					<div class="row padded"></div>
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
					
					<form:form id="form1" modelAttribute="categoryMasterModel"
							action="/PMS/mst/saveUpdateProgressReportMaster" method="post">
							<div class="container">
								<div class="panel panel-info panel-info1">
										<div class="panel-heading">
											<h3 class="text-center text-shadow">
												<spring:message code="project.progress.report" />
											</h3>
										</div>
										<div class="panel-body">
											
										<input type="hidden" value="${currentRole}" id="currentRole"/>
										<input type="hidden" value="" id="encReportId"/>
										<div class="row pad-top">
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
															<label class="label-class"><spring:message
																code="label.progress.category" />:<span
															style="color: red;">*</span></label>
													</div>
													
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<form:select path="numCategoryId" id="numCategoryId"
																class="select2Option">
																<form:option value="0">----Select Category----</form:option>
																<c:forEach items="${categoryList}" var="categoryList">
																	<form:option value="${categoryList.numCategoryId}">
																		<c:out value="${categoryList.strCategoryName}"></c:out>
																	</form:option>
																</c:forEach>
																
													</form:select>
													</div>
													</div>
													
													
													</div>
													
													
										
										</div>
										
										<div class="row pad-top">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="subCatDiv">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="label.progress.subcategory" />:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">													
														<div class="input-container">
															<form:select path="numSubCategoryId" id="numSubCategoryId"
																class="select2Option">
																
																<form:option value="0">----Select Sub Category----</form:option>
													</form:select>
													</div>
														
													</div>
												</div>
										</div>
										
										<div class="row pad-top">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<p  class="label-class">Month & Year:<span style="color: red;">*</span><i><span class="magenta " id="displayMonth"></span> <span  class="magenta pad-right " id="displayYear"></span></i>
															</p>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">													
														
															<p class="font_14 bold">
														
															<input id="strMonthAndYear" class='Default ' type="text" placeholder=""/> 
														
															    </p>
														
													</div>
												</div>
										</div>
										
										
										<div class="row pad-top hidden" id="projectMainDiv">
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
															<label class="label-class"><spring:message
																code="Project_Payment_Schedule.projectName" />:<span
															style="color: red;">*</span></label>
													</div>
													
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<form:select path="numProjectId" id="numProjectId"
																class="select2Option">
																<form:option value="0">----Select Project----</form:option>
																<c:forEach items="${projectsList}" var="projectList">
																	<form:option value="${projectList.numId}">
																		<c:out value="${projectList.strProjectName}"></c:out>
																	</form:option>
																</c:forEach>
																
													</form:select>
													</div>
													</div>
													
													
													</div>
													
													
										
										</div>
										<!-- 
										New Row
										-->
											
										<div class="row pad-top hidden" id="hrefDiv">
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">							
															<a href="#" onclick="openIframe();">Save/Update Details</a>
											</div>
										</div>
										</div>
										
										
										
										</div>
										
							</div>
							</div>
							
							
							</form:form>
</div>
</div>
</div>
</section>
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>

	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/progressReport.js"></script>
</body>
<div class="container padded hidden" id="iframeDiv">
<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
      <div id="dialog1"  title="Dialog Title"><iframe id="iframeId" frameborder="0" width="1500" height="500" src=""></iframe></div>
</div>
</div>

</html>