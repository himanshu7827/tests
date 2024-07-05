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
</head>
<style>
.bg_grey{
background: LemonChiffon !important;
}
</style>
<body>
	<section id="main-content" class="main-content merge-left">
	<input type="hidden"  id="roleId" value="${roleId}"><!-- Added by devesh on 08-09-23 to check role of user-->
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<li class="active"><spring:message code="project.closure.head"/></li>
					</ol>
				</div>
			</div>
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

<sec:authorize access="hasAnyAuthority('PROJECT_CLOSURE','UNDER_CLOSURE_PROJECTS')">
<%-- <c:choose> --%>
	<%-- <c:when test="${editMode == 1 }"> --%>
	<input type="hidden" id="editMode" value="${editMode}">
		<div class="container pad-bottom">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">				
					<div class="panel-body">						
	 					<c:if test = "${not empty projectDetails.projectRefNo}">
							<p><span class="bold  font_14 ">Project Reference Number: </span> <span class="bold blue font_14"><i>${projectDetails.projectRefNo}</i></span></p>
						</c:if>
						<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${projectDetails.strProjectName}</i></span></p>
						<p><span class="bold  font_16 ">Start Date: </span> <span class="bold blue font_16" id="projectStartDate">${projectDetails.startDate}</span></p>
						<p><span class="bold  font_16 ">End date: </span> <span class="bold blue font_16" id="projectEndDate">${projectDetails.endDate}</span></p>										
						
						
					</div>
				</div>
			</div>
		</div>
					
	<form:form id="form1" modelAttribute="projectClosureModel" action="/PMS/mst/saveUpdateEmployeeRoleMaster" method="post">
			<form:hidden path="encProjectId" value="${projectDetails.encProjectId}"/>	
				<div class="container">
					
				<%-- 	
					--%>
					<%-- <c:choose>
					<c:when test="${editMode == 1}"> --%>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.date"/>:</label>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="input-container">
								<i class="fa fa-calendar icon"></i> 
								<form:input class="input-field" id="closureDate" onchange="changeEffectiveDate()" value="${projectTempMasterDetail.closureDate}" path="closureDate"/>
							
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.remarks"/>:<span
									style="color: red;">*</span></label>
						</div>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="input-container">
								<i class="fa fa-file icon"></i> 
								<textarea class="input-field validateDescription" id="closureRemark" name="closureRemark" >${projectTempMasterDetail.closureRemark}</textarea> 
							
							</div>
						</div>
					</div> 
					<%-- </c:when><c:otherwise>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.date"/>:</label> ${projectTempMasterDetail.closureDate}
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<!-- <div class="input-container">
								<i class="fa fa-calendar icon"></i>  -->
								${projectTempMasterDetail.closureDate}
							
							<!-- </div> -->
						</div>
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.remarks"/>:</label> ${projectTempMasterDetail.closureRemark}
						</div>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<!-- <div class="input-container">
								<i class="fa fa-file icon"></i>  -->
							${projectTempMasterDetail.closureRemark}
							
							<!-- </div> -->
						</div>
					</div> 
					</c:otherwise></c:choose> --%>
					
			</div>
		<%-- 	<c:choose>
			<c:when test="${editMode == 1 }">  --%>
		<c:if test="${not empty documents}">
			<div class="container pad-top">
				<fieldset>
		    		<legend><spring:message code="project.closure.upload.file"/>:</legend>
		    		
		    			<div class="pad-top pad-bottom"><b class="red">Note : </b> If <spring:message code="document.documentName"/> marked as <b class="red">*</b>
		    				has more than 1 <spring:message code="docupload.document.format"/> then upload at least 1 document in any format
		    			</div>
		    		
		    		<table class="table table-responsive table-bordered table-strip">
		    			<thead>
							<tr>
								<th width="30%"><spring:message code="document.documentName"/></th>
								<th width="25%"><spring:message code="docupload.document.format"/></th>								
								
							</tr>
						</thead>
			    		<c:forEach items="${documents}" var="doc" varStatus="loop">
			    			<tr>
			    				<td rowspan="${fn:length(doc.formatModels)}" class="${loop.index} ${doc.mandatory}" id="docName_${loop.index}" >${doc.docTypeName} <c:if test="${doc.mandatory}"><span class="red">*</span></c:if></td>
			    				<input type="hidden" id="docuId_${loop.index}" value="${doc.encStrId} "/>
			    				<input type="hidden" id="docuName_${loop.index}" value="${doc.docTypeName} "/>
			    					<c:forEach items="${doc.formatModels}" var="format" varStatus="loop1">
			    					<%-- <c:choose>
			    						<c:when test="${loop1.index == 0}">
			    							<td><div class="col-md-5"> <b> ${format.formatName} </b> || ${format.icon}</div> <div class="col-md-5"> <input type="hidden"  id="docTypeFormat_${loop.index}${loop1.index}" class="docTypeFormat" value="${doc.encStrId}_${format.encFormatId}" /> <input type="file" class="file_${loop.index}" id="file_${loop.index}${loop1.index}" onchange="uploadProjectFile(this)"/> </div></td>
			    						</c:when>
			    						<c:otherwise>
			    							<tr><td><div class="col-md-5"> <b> ${format.formatName} </b> || ${format.icon} </div> <div class="col-md-5"> <input type="hidden" id="docTypeFormat_${loop.index}${loop1.index}" class="docTypeFormat" value="${doc.encStrId}_${format.encFormatId}" /> <input type="file" class="file_${loop.index}" id="file_${loop.index}${loop1.index}"  onchange="uploadProjectFile(this)"/></div></td> </tr>
			    						</c:otherwise>
			    					</c:choose> --%>
			  <!-----------------  For Check and Upload the file  ------------------->
			    					<c:choose>
			    						<c:when test="${loop1.index == 0}">
			    							<td><div class="col-md-5"> <b> ${format.formatName} </b> || ${format.icon}</div> <div class="col-md-5"> <input type="hidden"  id="docTypeFormat_${loop.index}${loop1.index}" class="docTypeFormat" value="${doc.encStrId}_${format.encFormatId}" /> <div class="test"><input type="file" class="file_${loop.index}" id="file_${loop.index}${loop1.index}" onchange="checkFileFormat(this)"/></div></div></td>
			    						</c:when>
			    						<c:otherwise>
			    							<tr><td><div class="col-md-5"> <b> ${format.formatName} </b> || ${format.icon} </div> <div class="col-md-5"> <input type="hidden" id="docTypeFormat_${loop.index}${loop1.index}" class="docTypeFormat" value="${doc.encStrId}_${format.encFormatId}" /> <div class="test"><input type="file" class="file_${loop.index}" id="file_${loop.index}${loop1.index}" onchange="checkFileFormat(this)"/></div></div></td> </tr>
			    						</c:otherwise>
			    					</c:choose>
			    					
			 <!-----------------  End Changes  ------------------->
			    					</c:forEach>
			    				
			    			</tr>		
			    		
			    		</c:forEach>
		    		</table>
		    	</fieldset>
	    	</div>
		</c:if>	
		<%-- </c:when>
		<c:if test="${not empty teamDetails}">
		<div class="container pad-top">
			<fieldset>
	    		<legend><spring:message code="project.closure.deallocate.team"/>:</legend>
	    		
	    		<div class="pad-top pad-bottom"><b class="red">Note : </b> if Effective Upto Date is not chosen explicitly, closure date will be considered. </div>
	    		
	    			<table class="table table-responsive table-bordered table-strip">
						<thead>
							<tr>
								<th width="30%"><spring:message code="Designation_Master.designationName"/></th>
								<th width="25%"><spring:message code="project_Team_Details.role_Name"/></th>								
								<th width="15%"><spring:message code="project_Team_Details.effective_From"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/> <spring:message code="form.if.any.label"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/></th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach items="${teamDetails}" var="member" varStatus="loop">
								<tr>
									
									<td> <input id="role_${loop.index}" type="hidden" value="${member.encRoleId}"/>
									<input type="hidden" id="empId_${loop.index}" value="${member.encEmployeeId}"/>
										${member.strEmpName}</td>
									<td>${member.strRoleName}</td>
									<td class="startDate" id="startDate_${loop.index}">${member.strStartDate}</td> 
									<td id="end_${loop.index}">${member.strEndDate}</td>
									<td>
									<input type="hidden" id="empRole_${loop.index}" value="${member.encId}"/>
									<input class="datePicker input-field" id="picker_${loop.index}" readonly="readonly">
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
	    	</fieldset>
    		</div>
    	</c:if>
			<c:otherwise>		
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:5%;"><spring:message code="serial.no"/></th>
 					    <th style="width:12%;"><spring:message code="document.documentName"/></th>
  					    <th style="width:12%;"><spring:message code="docupload.document.version"/></th>
						<th style="width:10%;"><spring:message code="docupload.document.Date"/></th>						
						<th style="width:40%;"><spring:message code="master.description"/></th>
						<th style="width:10%;"><spring:message code="task.download" /></th>
		
					    
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${docDetails}" var="documentdetail" varStatus="theCount">
						<tr >
						    <td>${theCount.count}</td>
						    <td ><p class="bold">${documentdetail.documentTypeName}</p>
						    <c:choose>
						    <c:when test="${documentdetail.periodFrom != null}">
						   
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">Valid to: ${documentdetail.periodTo}</span></p>
						    </c:when>
						    <c:otherwise>
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">${documentdetail.periodTo}</span></p>
						    </c:otherwise>
						    </c:choose>
						    </td>
    						<td>${documentdetail.documentVersion}</td>
							<td>${documentdetail.documentDate}</td>
						
 							<td>${documentdetail.description}</td>
							<td align="center">
								<c:forEach items="${documentdetail.detailsModels}" var="details" varStatus="theCount">
									<span> <a onclick=downloadTempDocument("${details.encNumId}")>${details.icon}</a> </span>
								</c:forEach>			
 							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</c:otherwise>
			</c:choose> --%>
			<c:if test="${not empty teamDetails}">
		<div class="container pad-top">
			<fieldset>
	    		<legend><spring:message code="project.closure.resources.release"/>:</legend>
	    		
	    		<div class="pad-top pad-bottom"><b class="red">Note : </b> if Effective Upto Date is not chosen explicitly, closure date will be considered. </div>
	    		
	    			<table class="table table-responsive table-bordered table-strip">
						<thead>
							<tr>
								<th width="30%"><spring:message code="Designation_Master.designationName"/></th>
								<th width="25%"><spring:message code="project_Team_Details.role_Name"/></th>								
								<th width="15%"><spring:message code="project_Team_Details.effective_From"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/> <spring:message code="form.if.any.label"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/></th>
							</tr>
						</thead>
						
						<tbody>
							<c:set var="isHeadOfDepartment" value="false" /><!-- Added by devesh on 01-09-23 to check if HOD is mapped to the project -->
							<c:forEach items="${teamDetails}" var="member" varStatus="loop">
								<tr>
									
									<td> <input id="role_${loop.index}" type="hidden" value="${member.encRoleId}"/>
									<input type="hidden" id="empId_${loop.index}" value="${member.encEmployeeId}"/>
										${member.strEmpName}</td>
									<td>${member.strRoleName}</td>
									<td class="startDate" id="startDate_${loop.index}">${member.strStartDate}</td> 
									<td id="end_${loop.index}">${member.strEndDate}</td>
									<td>
									<input type="hidden" id="empRole_${loop.index}" value="${member.encId}"/>
									<input class="datePicker input-field effectiveUpto" id="picker_${loop.index}" readonly="readonly">
									</td>
								</tr>
								<%-- Added by devesh on 01-09-23 to Check if strRoleName is "Head of Department" and set the boolean to true --%>
							    <c:if test="${member.strRoleName eq 'Head of Department'}">
							        <c:set var="isHeadOfDepartment" value="true" />
							    </c:if>
							    <!-- End of boolean variable -->
							</c:forEach>
						</tbody>
					</table>
	    	</fieldset>
    		</div>
    	</c:if>
			
			<br/>
		 
					
					<%-- <div class="row pad-top pad-bottom form_btn center">
					<c:choose>
						<c:when test="${editMode == 1}">
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Update and Send to GC
						</button></c:when><c:otherwise><button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Initiate Project Closure
						</button></c:otherwise></c:choose>
						<a href="/PMS/mst/underClosureProjects" class="btn btn-orange font_14">												
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
						</a>
					</div> --%>
					<!-- Commented and added by devesh on 01-09-23 to show button according to HOD mapping -->
					<div class="row pad-top pad-bottom form_btn center">
					<c:choose>
						<c:when test="${roleId != 5}">
							<c:choose>
								<c:when test="${editMode == 1}">
									<c:choose>
										<c:when test="${isHeadOfDepartment}">
							                <button type="button" class="btn btn-primary font_14" id="save">
						                        <span class="pad-right"><i class="fa fa-folder"></i></span>
						                        <c:choose>
						                            <c:when test="${roleId != 15}">
						                                Update and Send to HOD
						                            </c:when>
						                            <c:otherwise>
						                                Update and Send to GC
						                            </c:otherwise>
						                        </c:choose>
						                    </button>
							            </c:when>
							            <c:otherwise>
											<button type="button" class="btn btn-primary font_14" id="save">
												<span class="pad-right"><i class="fa fa-folder"></i></span>Update and Send to GC
											</button>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${isHeadOfDepartment}">
							                <button type="button" class="btn btn-primary font_14" id="save">
							                    <span class="pad-right"><i class="fa fa-folder"></i></span>Initiate Project Closure and Send to HOD
							                </button>
							            </c:when>
							            <c:otherwise>
											<button type="button" class="btn btn-primary font_14" id="save">
												<span class="pad-right"><i class="fa fa-folder"></i></span>Initiate Project Closure and Send to GC
											</button>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						 <c:otherwise>
						 <button type="button" class="btn btn-primary font_14" id="save">
	                        <span class="pad-right"><i class="fa fa-folder"></i></span>
	                                Update and Send to GC Finance
	                     </button>
						 </c:otherwise>
					</c:choose>
						<a href="/PMS/mst/underClosureProjects" class="btn btn-orange font_14" id="backButton">												
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
						</a>
						<%------------------- Set The Back button (redirect to dashboard ) [06-12-2023] ---------%>
						<a class="btn btn-orange font_14 hidden" id="backToDashboard" onclick="backToDashboard()">												
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
						</a>
						<input type="hidden" value="${backButtonStatus }" id="backButtonStatus"/>
						<%------------------- End Set The Back button (redirect to dashboard ) [06-12-2023] ---------%>				
					</div>
					<!-- End of buttons according to HOD mapping -->
	</form:form>
	<%-- </c:when> --%>
	<%-- <c:otherwise>
		<div class="container pad-bottom">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
				<div class="panel panel-info  ">				
					<div class="panel-body">						
	 					<c:if test = "${not empty projectDetails.projectRefNo}">
							<p><span class="bold  font_14 ">Project Reference Number: </span> <span class="bold blue font_14"><i>${projectDetails.projectRefNo}</i></span></p>
						</c:if>
						<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${projectDetails.strProjectName}</i></span></p>
						<p> <span class="bold blue font_16">Already Project status is set to ${projectDetails.status} </span></p>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.date"/>:</label>
						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="input-container">
								{projectDetails.tempProjectClosureDate}
							
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="project.closure.remarks"/>:<span
									style="color: red;">*</span></label>
						</div>

						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="input-container">
								{projectDetails.strProjectRemarks}
							
							</div>
						</div>
					</div>
					
						<div class="col-md-12 col-lg-12 col-xs-12 pad-bottom">
							<div class="pull-right">
								<a href="/PMS/mst/ViewAllProjects" class="btn btn-orange font_14">												
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
								</a>
							</div>
						</div>				
					</div>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose> --%>
		<%------------------ Status Condition if it open from dashboard [06-12-2023] ----------%>
		<c:if test="${backButtonStatus eq true}">
		    <script>
	            var conditionMet = true;
				$('#backButton').addClass('hidden');
				$('#backToDashboard').removeClass('hidden');
				
	            function backToDashboard()
	            {
	            	openWindowWithPost('GET', '/PMS/dashboard', {
	            		"shortCode":"true"
	        		}, '_self'); 
	            }
	          </script>
		</c:if>
		<%------------------ End Status Condition if it open from dashboard [06-12-2023] ----------%>
	
</sec:authorize> 
	</div>
	</section>

	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/projectClosure.js"></script>
</body>
</html>