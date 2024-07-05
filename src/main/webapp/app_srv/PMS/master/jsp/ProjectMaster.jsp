<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>


<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

	<style>
	#team-modal{
	padding-top: 5%;	
	}
</style>


</head>
<body>

<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
		<div class="row">
		
<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="2"/>
<jsp:param name="applicationId" value="0"/>
</jsp:include>
</div>
			<div class="row">
				<div class=" col-md-12  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Details</li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
		 <c:if test="${message != null && message != ''}">
		 	<c:choose>
		 		<c:when test="${status=='success'}">
		 			<div id="userinfo-message"><p class="success_msg">${message}</p></div> 
		 		</c:when>
		 		<c:otherwise>
		 			<div id="userinfo-message"><p class="error_msg">${message}</p></div> 
		 		</c:otherwise>		 	
		 	</c:choose>       		
        </c:if>	
		<div class="container pad-bottom">
		
		<form:form id="form1" name="form1" modelAttribute="projectMasterForm" action="/PMS/mst/saveProjectMaster" method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<form:hidden path="applicationId"/>
			<form:hidden id="projectId" path="projectId" />
			<input type="hidden" id="encProjectId" value="${encProjectId}"/>
			<input type="hidden" id="totalPaymentScheduleAmount" value="${totalPaymentScheduleAmount}"/> <!-- Added by devesh on 21-03-24 to get totalPaymentScheduleAmount-->
			<form:hidden id="strProjectStatus" path="strProjectStatus"  value="${projectMasterForm.strProjectStatus}"/>
			
				<form:hidden path="" id="encProjectId"/>
			
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Project Details</h4>
								</div>
								<div class="panel-body ">
									<div class="row  ">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 text-justify ">
									<p><span class="bold  font_16 ">Funding Organization: </span><span class="bold orange font_16"><i>${clientMaster.clientName}</i></span></p>
									</div>
 									<%-- <c:if test = "${not empty projectMasterForm.projectRefrenceNo}">
 									
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
									<p><span class="bold  font_14 ">Project Reference Number: </span><span class="bold blue font_14"><i>${projectMasterForm.projectRefrenceNo}</i></span></p>
									</div>
									
 									</c:if> --%>
 										 
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 text-justify" style="text-align: right  !important;">
									<h5 class="red">*Kindly fill all amounts in <span class="black bold">INR</span></h5>
									</div>
										</div>
										
									<%-- 	<div class="row  pad-top">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
											<div class="col-md-3 col-lg-3 col-sm-3 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="project.projectRefNo"/>:<span
									style="color: red;">*</span></label>
							</div>

								<div class="col-md-3 col-lg-3 col-sm-3 col-xs-12">
												<div class="input-container">
									<form:input class="input-field validateProjectRefNo" onblur="saveField('projectRefrenceNo')" path="projectRefrenceNo"/>
									<form:errors path="projectRefrenceNo" cssClass="error" />
								</div>
							</div>
						</div>
						</div> --%>
										<div class="text-center pad-top">
									<div class="row  ">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Project_Payment_Schedule.projectName"/>:<span
									style="color: red;">*</span></label>
							</div>

								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-bookmark icon "></i>
									<%-- <form:input class="input-field validateName" onblur="saveField('strProjectName')" path="strProjectName"/> --%>
									<form:textarea class="input-field validateTextArea"  onblur="saveField('strProjectName')"  path="strProjectName"/>
									<form:errors path="strProjectName" cssClass="error" />
								</div>
							</div>
						</div>
						
						
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="document.description"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file icon"></i> 
									<form:textarea class="input-field validateDescription"  onblur="saveField('description')" path="description"/>
									<form:errors path="description" cssClass="error" />
								</div>
							</div>
						</div> 
						
					

						
					</div>
	
	
	
	
						<div class="row ">
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<%-- <label class="label-class"><spring:message code="master.cdac.share"/>: <span style="color: red;">*</span></label> --%>
								<!-- Label Modified by devesh on 29-05-24 -->
								<label class="label-class"><spring:message code="master.cdac.share"/> (INR) : <span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
									<i class="fa fa-inr icon"></i> 
								<fmt:formatNumber type="number" var="fmtCost" pattern="###########.##" maxFractionDigits="3" value="${projectMasterForm.projectCost}" />
									<form:input class="input-field validateCost" onblur="saveField('projectCost')" path="projectCost" value="${fmtCost}" />
									<input type="number" class="hidden" id="lastcdacCost" pattern="###########.##" value="${fmtCost}"/>
									<form:errors path="projectCost" cssClass="error" />
								</div>
							</div>
						</div>
					
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="proposal.administrationNo"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file icon"></i> 
									<form:input class="input-field validateAdministrationNo"  onblur="saveField('administrationNo')" path="administrationNo"/>
									<form:errors path="administrationNo" cssClass="error" />
								</div>
							</div>
						</div> 

						</div>
						
					
					<div class="row ">
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="projectMaster.WorkorderDate"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' onblur="saveField('workOrderDate')" path="workOrderDate" />							
								<form:errors path="workOrderDate" cssClass="error" />
								</div>
							</div>
						</div>
					
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class" ><spring:message code="projectMaster.MOUdate"/>: </label>
							</div>
						
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" onblur="saveField('mouDate')" readonly='true' path="mouDate" />							
								<form:errors path="mouDate" cssClass="error" />
								</div>
							</div>
						</div>
						<%-- <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="projectMaster.duration"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-power-off icon"></i> 
									<form:input class="input-field validateDuration" placeholder="Duration in Months" onblur="saveField('projectDuration')" path="projectDuration"/>
									<form:errors path="projectDuration" cssClass="error" />
								</div>
							</div>
						</div> --%>
						</div>
						<%-- <div class="row ">
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class" ><spring:message code="projectMaster.MOUdate"/>: </label>
							</div>
						
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field validateMOUDate" onblur="saveField('mouDate')" readonly='true' path="mouDate" />							
								<form:errors path="mouDate" cssClass="error" />
								</div>
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="projectMaster.status"/>: <span style="color: red;">*</span></label>
							</div>
						
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
										<div class="input-container">
											<form:select path="strProjectStatus" onblur="saveField('strProjectStatus')" class="select2Option validateStatus">
												<form:option value="0" selected='selected'>-- Select Project Status --</form:option>
												<form:option value="Ongoing">Ongoing</form:option>
												<form:option value="Completed">Completed</form:option>
												<form:option value="Terminated">Terminated</form:option>
												<form:option value="Withdrawn">Withdrawn</form:option>
											</form:select>
										</div>
							</div>
						</div>
						
					</div> --%>
					
					<div class="row ">
					
					<!-- added below div class hidden by varun to get date of submission -->
					<div class="hidden col-md-6 col-lg-6 col-sm-6 col-xs-12 text-justify ">
									<p id="dtSubmission">${dateOfSubmission}</p>
									</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="projectMaster.startDate"/>: <span style="color: red;">*</span></label>
							</div>					
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' onblur="saveField('startDate')" path="startDate" />							
								<form:errors path="startDate" cssClass="error" />
								</div>
							</div>
						</div>
					
					
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="projectMaster.endDate"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true'  path="endDate" onblur="saveField('endDate')"/>							
								<form:errors path="endDate" cssClass="error" />
								</div>
							</div>							
						</div>												
					</div>
					
					
					
						
						
					
					
				<div class="row ">
				
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="projectMaster.objective"/>: <span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-th icon"></i> 
									<form:textarea class="input-field validateTextArea"  onblur="saveField('strProjectObjective')"  path="strProjectObjective"/>
									<form:errors path="strProjectObjective" cssClass="error" />
								</div>
							</div>
						</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="projectMaster.aim"/>: <span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-list-alt icon"></i> 
									<form:textarea class="input-field validateTextArea" onblur="saveField('strProjectAim')"  path="strProjectAim"/>
									<form:errors path="strProjectAim" cssClass="error" />
								</div>
							</div>
						</div>
					
					</div>
					
					
				<div class="row ">
				
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="projectMaster.gst"/>: </label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-th icon"></i> 
									<form:input class="input-field validateGST"  onblur="saveField('strGST')"  path="strGST"/>
									<form:errors path="strGST" cssClass="error" />
								</div>
							</div>
						</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="projectMaster.tan"/>: </label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-list-alt icon"></i> 
									<form:input class="input-field validateTAN" onblur="saveField('strTAN')"  path="strTAN"/>
									<form:errors path="strTAN" cssClass="error" />
								</div>
							</div>
						</div>
					
					</div>
					
					

								
			<div class="row ">
				
					
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="projectMaster.remark"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file icon"></i> 
									<form:textarea class="input-field validateTextArea"  onblur="saveField('strProjectRemarks')" path="strProjectRemarks"/>
									<form:errors path="strProjectRemarks" cssClass="error" />
								</div>
							</div>
						</div> 
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="projectMaster.fundedScheme"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon"></i> 
									<form:textarea class="input-field validateTextArea" onblur="saveField('strFundedScheme')"  path="strFundedScheme"/>
									<form:errors path="strFundedScheme" cssClass="error" />
								</div>
							</div>
						</div> 
						</div>
						
						<div class="row ">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div
												class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="projectMaster.Cost" />: <span style="color: red;">*</span></label>
											</div> 
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<i class="fa fa-inr icon "></i>
													
													<fmt:formatNumber type="number" var="fmtCost" pattern="###########.##" maxFractionDigits="3" value="${projectMasterForm.totalOutlay}" />
													<form:input class="input-field validateCost" onblur="saveField('totalOutlay')" path="totalOutlay" value="${fmtCost}" />
													<!-- Below line added for validation of Total Outlay by devesh on 05-02-2024 -->
													<input type="number" class="hidden" id="lastTotalCost" pattern="###########.##" value="${fmtCost}"/>
													
												 <form:errors path="totalOutlay" cssClass="error" /> 
												</div>
											</div>
										</div>
										</div>
						<%-------------- Get Project Category ID [25-01-2024] -------------------------------%>
						<input value="${projectCategoryId}" id="projectCategoryId" name="categoryId" class="hidden"/>
		<%-- 			<div class="row ">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="forms.status"/> : <span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								
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
						</div> --%>	
		
					
			<!-- <div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					</div> -->
					
									<div class="row padded pull-right">	
										
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="gonext()" >
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
					</div>
					</div> 
					</div>
					</div>
					</div>
					
		</form:form>
	</div>
	<!--End of panel-->

</div>
</section>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/projectmaster.js"></script>

</body>
</html>