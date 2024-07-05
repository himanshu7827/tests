
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

						<li class="active">Manpower Expenses</li>
					</ol>

				</div>
			</div>

			<div class="row pad-bottom ">
				<div class="col-md-12">
					<!-- Nav tabs -->
					<div class="card">

						<div id="monthlyProress">
							<input type="hidden" id="startdate"/>
							<input type="hidden" id="enddate"/>
							<input type="hidden" id="monthlyParentId" value=""/>
							<div id="hiddenFields"></div>
							
							<div class="row pad-top">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">									
									<div class="col-md-3 col-lg-3 col-sm-3 col-xs-12">
										<label class="label-class">
										<spring:message code="Project_Module_Master.projectName" />:
										<span style="color: red;">*</span></label>
									</div>

									<div class="col-md-9 col-lg-9 col-sm-9 col-xs-12">
										<div class="input-container">
											<select id="encProjectId" class="select2Option" multiple>
												<!-- <option value="0">----Select Project----<option> -->
												<c:forEach items="${projects}" var="project">
													<option value="${project.encProjectId}">
														${project.strProjectName}
													</option>
												</c:forEach>
											</select>
										</div>
									</div>													
								</div>
								<span style="margin-left: 2%;font-style: oblique;color: cadetblue;font-size: revert;">Select All</span>
								<span style="margin-left:1%;"><input class="group_check" id="AllGroup" type='checkbox' value='Select All' onchange="checkGroups()"></span>
							</div>
							
							
							<div class="row pad-top hidden" id="monthpickerDiv">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-3 col-lg-3 col-sm-3 col-xs-12">										
											<label class="label-class">From:</label>
											<span style="color: red;">*</span><i>
											<span class="magenta " id="displayMonth"></span>
											 <span class="magenta pad-right " id="displayYear"></span></i>
									
									</div>
									<div class="col-md-9 col-lg-9 col-sm-9 col-xs-12">
										<input type="text" id="fromDate" name="fromDate">
									</div>
								</div>
							
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-3 col-lg-3 col-sm-3 col-xs-12">										
											<label class="label-class">To:</label>
											<span style="color: red;">*</span><i>
											<span class="magenta " id="displayMonth"></span>
											 <span class="magenta pad-right " id="displayYear"></span></i>
									
									</div>
									<div class="col-md-9 col-lg-9 col-sm-9 col-xs-12">
										<input type="text" id="toDate" name="toDate">
									</div>

								</div>
							</div>

							<div class="row pad-top hidden" id="hrefDiv">

								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12"></div>									
								</div>
							</div>

							<div class="row pad-top form_btn center hidden" id="downloadbutton">
								<button class='btn btn-primary font_14' onclick="downloadManpowerExpensesExcel()">Download Excel</button>
							</div>
						
						</div>


					</div>
				</div>
			</div>
		</div>
	
	</section>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/manpowerExpenses.js"></script>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>

</body>
</html>