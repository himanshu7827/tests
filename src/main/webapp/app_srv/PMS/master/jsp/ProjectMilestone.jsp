<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">


<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-------- Add Style for Fields Validation Error [23-10-2023] ----------%>
<style>
.errorMessageClass{
    margin-top: 4px;
    border-radius: 9px;
    }

.input-container-milestone{
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    height: 40;
    font-size: 14px;
	}
	
.icon_milestone{
    /* padding: 15px; */
    background: /* #d2b47e */ #cbdeff;
    color: white;
    min-width: 50px;
    height: 40;
    text-align: center;
    color: #5577c1;
    padding-top: 13px;
    padding-bottom: 13px;
}
.btn_milestone{
    display: inline-block;
    padding: 0px 9px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
}
</style>

</head>
<body>

<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
				<div class="row">
				<c:if test="${projectId>0 && empty  referer}">
					<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
						<jsp:param name="moduleTypeId" value="2"/>
						<jsp:param name="applicationId" value="0"/>
					</jsp:include>
				</c:if>
</div>
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">Project Milestone Master</li>
		</ol>
	</div>
</div>
<div class="row ">
		
		 </div>
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
		
		<sec:authorize access="hasAuthority('WRITE_PROJECTMILESTONE_MST')">
		<form:form id="form1" name="form1" modelAttribute="projectMilestoneForm" action="/PMS/mst/saveProjectMilestone" method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<input type="hidden" id="allowDateEdit" value="${allowDateEdit}"/>
			<input type="hidden" id="encProjectId" value="${encProjectId}"/>
			<input type="hidden" id="startProjDate" value="${projectStartDate}">
			<input type="hidden" id="endProjDate" value="${projectEndDate}">
			
			<input type="hidden" id="actionId" value="${actionId}">
			<%-- <input type="hidden" id="startProjDate" value="	${projectDetail.dtProjectEndDate}"> --%>
		
									<%-- <input type="hidden" id="projectId" value="${projectId}" /> --%>
			
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Project Milestone Master</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-right"><%-------- Add text-right [23-10-2023] ----------%>
								<label class="label-class"><spring:message code="Project_Invoice_Master.projectName"/>:</label>
							</div>
							
							<c:choose>
									<c:when test="${projectId==0}">
					  <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
				<form:select path="projectId"  class="select2Option"  style="width:100%">
				          <form:option value="0">--Select Project----</form:option>
							<c:forEach items="${projectData}" var="projectData" >
								<form:option value="${projectData.numId}">
								${projectData.strProjectName}
								</form:option>
							</c:forEach>
				</form:select>
						
						</div>
						</div>
						</c:when>
									<c:otherwise>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 text-left"><%-------- Add text-left [23-10-2023] ----------%>
										<form:hidden path="projectId" value="${projectId}"/>
									<h5 class="bold">${projectDetail.strProjectName}
									<c:if test="${not empty projectDetail.strProjectRefNo}">
 									<span class="bold blue font_14">&nbsp;(${projectDetail.strProjectRefNo}) </span>
									</c:if>
									</h5>
															
								</div>
									</c:otherwise>
								</c:choose>
						</div>
						</div>
							<%-- <div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="Project_Payment_Schedule.milestoneName"/>:<span
									style="color: red;">*</span></label>
							</div>

							  <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field"  path="milestoneName"/>
									<form:errors path="milestoneName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
					<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Expected_StartDate"/>:<span
									style="color: red;">*</span> </label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="expectedStartDate" />							
								<form:errors path="expectedStartDate" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Designation_Master.designationDescription"/>: </label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
										<i class="fa fa-file-text-o icon "></i> 
									<form:textarea class="input-field" rows="2" path="strDesription"></form:textarea>
									<form:errors path="strDesription" cssClass="error" />
								</div>
							</div>
						</div>
					</div> --%>
		<%------------- Add Verticle Columns for Milestone Fields [23-10-2023] ----------%>
					<div class="row">
							<div class="col-md-10 col-lg-10 col-sm-10 col-xs-10 form-group col-md-push-1">
								<table class="table table-striped table-bordered" id="milestoneMultipleData">
								    <thead>
								        <tr>
								            <th class="text-center col-md-4"><spring:message code="Project_Payment_Schedule.milestoneName"/><span style="color:red">*</span></th>     
								            <th class="text-center col-md-4"><spring:message code="Designation_Master.designationDescription"/></th>
								            <th class="text-center col-md-3"><spring:message code="Expected_StartDate"/><span style="color:red">*</span></th>
								            <th class="text-center col-md-1 ActionColumn">Action</th>
								        </tr>
								    </thead>
								    <tbody>
								        <tr id="0row">
								            <td>
								            	<input class="hidden" id="validField_0"/>
								            	<input class="hidden" id="milestoneId_0"/>
								                <div class="input-container-milestone">
								                    <i class="fa fa-user icon_milestone"></i> 
								                    <input class="input-field" id="milestoneName_0" onblur="saveField('0')"/>
								                </div>
								                <div class="alert-danger text-center milestoneName_0 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-file-text-o icon_milestone"></i> 
								                    <textarea class="input-field form-control" rows="2" id="strDescription_0" onblur="saveField('0')"></textarea>
								                </div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-calendar icon_milestone"></i>
								                    <%------ ReadOnly the Field For No Input [16-04-2024] ------%>
								                    <input class="input-field BaselineStartDate" readonly='true' id="expectedStartDate_0" onclick="" onchange="saveField('0')"/>						
								                </div>
								                <div class="alert-danger text-center expectedStartDate_0 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_milestone btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_milestone btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_milestone btn-danger deleteRowButton deleteRowButton_0' onclick="deleteMilestoneDetails(0)">
								                        <i class='fa fa-minus-circle btn_milestone btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <!-- Three more rows added by devesh on 27-10-23 -->
								        <tr id="1row">
								            <td>
								            	<input class="hidden" id="validField_1"/>
								            	<input class="hidden" id="milestoneId_1"/>
								                <div class="input-container-milestone">
								                    <i class="fa fa-user icon_milestone"></i> 
								                    <input class="input-field" id="milestoneName_1" onblur="saveField('1')"/>
								                </div>
								                <div class="alert-danger text-center milestoneName_1 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-file-text-o icon_milestone"></i> 
								                    <textarea class="input-field form-control" rows="2" id="strDescription_1" onblur="saveField('1')"></textarea>
								                </div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-calendar icon_milestone"></i>
								                    <input class="input-field BaselineStartDate" readonly='true' id="expectedStartDate_1" onchange="saveField('1')"/>						
								                </div>
								                <div class="alert-danger text-center expectedStartDate_1 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_milestone btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_milestone btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_milestone btn-danger deleteRowButton' onclick="deleteMilestoneDetails(1)">
								                        <i class='fa fa-minus-circle btn_milestone btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <tr id="2row">
								            <td>
								            	<input class="hidden" id="validField_2"/>
								            	<input class="hidden" id="milestoneId_2"/>
								                <div class="input-container-milestone">
								                    <i class="fa fa-user icon_milestone"></i> 
								                    <input class="input-field" id="milestoneName_2" onblur="saveField('2')"/>
								                </div>
								                <div class="alert-danger text-center milestoneName_2 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-file-text-o icon_milestone"></i> 
								                    <textarea class="input-field form-control" rows="2" id="strDescription_2" onblur="saveField('2')"></textarea>
								                </div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-calendar icon_milestone"></i>
								                    <input class="input-field BaselineStartDate" readonly='true' id="expectedStartDate_2" onchange="saveField('2')"/>						
								                </div>
								                <div class="alert-danger text-center expectedStartDate_2 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_milestone btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_milestone btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_milestone btn-danger deleteRowButton' onclick="deleteMilestoneDetails(2)">
								                        <i class='fa fa-minus-circle btn_milestone btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <tr id="3row">
								            <td>
								            	<input class="hidden" id="validField_3"/>
								            	<input class="hidden" id="milestoneId_3"/>
								                <div class="input-container-milestone">
								                    <i class="fa fa-user icon_milestone"></i> 
								                    <input class="input-field" id="milestoneName_3" onblur="saveField('3')"/>
								                </div>
								                <div class="alert-danger text-center milestoneName_3 errorMessageClass" role="alert">
								  					
												</div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-file-text-o icon_milestone"></i> 
								                    <textarea class="input-field form-control" rows="2" id="strDescription_3" onblur="saveField('3')"></textarea>
								                </div>
								            </td>
								            <td>
								                <div class="input-container-milestone">
								                    <i class="fa fa-calendar icon_milestone"></i>
								                    <input class="input-field BaselineStartDate" readonly='true' id="expectedStartDate_3" onchange="saveField('3')"/>						
								                </div>
								                <div class="alert-danger text-center expectedStartDate_3 errorMessageClass" role="alert">
									
												</div>	
								            </td>
								            <td class="ActionColumn">
								                <div align='center' class="pad-top pad-bottom" style="display: inline-flex">
								                    <button type='button' class='btn_milestone btn-primary addRowButton'>
								                        <i class='fa fa-plus-circle btn_milestone btn-primary'></i>
								                    </button>&nbsp;&nbsp;
								                    <button type='button' class='btn_milestone btn-danger deleteRowButton' onclick="deleteMilestoneDetails(3)">
								                        <i class='fa fa-minus-circle btn_milestone btn-danger'></i>
								                    </button>
								                </div>
								            </td>
								        </tr>
								        <!-- End of rows -->
								    </tbody>
								</table>				
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="saveMiletsone">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>

						<button type="button" class="btn btn-primary reset font_14 hidden" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						<c:if test="${not empty referer}">
							<a href="${referer}" class="btn btn-orange font_14">												
								<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
							</a>
						</c:if>
						
					</div>
					<c:if test="${projectId>0 && empty  referer}"> 
												<div class="row padded pull-left">
									<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" >
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button>
									</div>
										<div class="row padded pull-right">
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="gonext()" >
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
									</div>
									</c:if>
					
					
					</div>
					</div>
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
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
		
			<table id="example1" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
					<%------------- Add Serial Number Columns and Remove Select Columns [23-10-2023] ----------%>
						<th><spring:message code="forms.serialNo"/></th>
						<th class="width20 hidden "><spring:message code="master.select"/></th>
						<th><spring:message code="Project_Payment_Schedule.milestoneName"/></th>
						<th><spring:message code="Expected_StartDate"/></th>
						<th><spring:message code="Designation_Master.designationDescription"/></th>						
						<th><spring:message code="forms.edit"/></th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="milestoneDetail" varStatus="loop">
						<tr>
							<td class="text-center">${loop.index+1}</td>
							<td class=" hidden"><input type="checkbox"  class="CheckBox" id="CheckBox"  value="${milestoneDetail.numId}" />${milestoneDetail.numId}</td>
							<td>${milestoneDetail.milestoneName}</td>
						    <td>${milestoneDetail.expectedStartDate}</td>
							<td>${milestoneDetail.strDesription}</td>
							
							
							<td>
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="editMiletsone" aria-hidden="true"></span>
							</td>

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
	<!--End of datatable_row-->
</div>
	</div>
</div>
</section>

	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/ProjectMilestone.js"></script>

</body>
</html>