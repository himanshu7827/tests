
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css">
</head>


<body>
	<div tabindex='1' id="mainDiv"></div>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active"><spring:message
								code="project.progress.report" /></li>
					</ol>

				</div>
			</div>

			<div class="row pad-bottom ">
				<div class="col-md-12">
					<!-- Nav tabs -->
					<div class="card">

						<div id="monthlyProress">
						<input type="hidden" id="isSubCatPresent" />
							<input type="hidden" id="startdate"/>
							<input type="hidden" id="monthlyParentId" value=""/>
							<input type="hidden" id="categoryIdback" value="${categoryIdback}"/>
							<div id="hiddenFields"></div>
							
							<div class="row pad-top">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">									
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class">
										<spring:message code="Project_Module_Master.projectName" />:
										<span style="color: red;">*</span></label>
									</div>

									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<select id="encProjectId" class="select2Option">
												<option value="0">----Select Project----<option>
												<c:forEach items="${projects}" var="project">
													<option value="${project.encProjectId}">
													<%------- Display Project Reference Number with Project Name [21-05-2024] ---%>
														<c:choose>
									                        <c:when test="${not empty project.projectRefNo}">
									                            <c:out value="<strong>${project.projectRefNo}</strong> - ${project.strProjectName}" />
									                        </c:when>
									                        <c:otherwise>
									                            <c:out value="${project.strProjectName}" />
									                        </c:otherwise>
								                    	</c:choose>
													</option>
												</c:forEach>
											</select>
									</div>
								</div>													
							</div>
						</div>
							
							
							<div class="row pad-top hidden" id="monthpickerDiv">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">										
											<label class="label-class">Progress Report For Month:</label>
											<span style="color: red;">*</span><i>
											<span class="magenta " id="displayMonth"></span>
											 <span class="magenta pad-right " id="displayYear"></span></i>
									
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<input id="strMonthAndYear" class='Default ' type="text" readonly/><%---- Set Fields to Readonly [21-05-2024] --%>
									</div>
								</div>
							
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden" id="divCat">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class">
											<spring:message code="label.progress.category" />:
											<span style="color: red;">*</span></label>
									</div>

									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<select id="numCategoryId" class="select2Option">
												<option value="0">----Select Category----<option>
													<c:forEach items="${categoryList}" var="categoryList">
														<option value="${categoryList.encCategoryId}">
															${categoryList.strCategoryName}</option>
													</c:forEach>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row pad-top">
								<div
															class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group hidden"
															id="subCatDiv">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class"><spring:message
																		code="label.progress.subcategory" />:<span
																	style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<select id="numSubCategoryId" class="select2Option">

												<option value="0">----Select Sub Category----</option>
											</select>
										</div>

									</div>
								</div>
							</div>
							<div class="row pad-top hidden" id="hrefDiv">

								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12"></div>									
								</div>
							</div>

							<div class="row pad-top form_btn center" id="nextAction"></div>
						
												</div>


					</div>
				</div>
			</div>
		</div>
	
	</section>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/monthlyProgressForprojects.js"></script>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/progressReportWorkflow.js"></script>

</body>
</html>