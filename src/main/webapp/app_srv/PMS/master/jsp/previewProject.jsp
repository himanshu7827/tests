<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
		<c:if test="${projectId>0 }">
			<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
				<jsp:param name="moduleTypeId" value="2"/>
				<jsp:param name="applicationId" value="0"/>
			</jsp:include>
			<div class="hidden" id="projectStartDate"><fmt:formatDate pattern = "yyyy,MM,dd" 
         value = "${projectDetail.dtProjectStartDate}" /></div>
			<div class="hidden" id="projectEndDate"><fmt:formatDate pattern = "yyyy,MM,dd" 
         value = "${projectDetail.dtProjectEndDate}" /></div>
		</c:if>
</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active"><spring:message code="menu.project.preview.label"/></li>
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
	<form:form id="form1" modelAttribute="projectMasterForm" method="post">
	
		<input type="hidden" id="projectid" value="${projectId}" />
		<input type="hidden" id="encProjectId" value="${encProjectId}"/>
		<input type="hidden" id="projectDocument" value="${projectDocument.size()}">
		<input type="hidden" id="manpowerRequirement" value="${manpowerRequirement.size()}">
		<input type="hidden" id="paymentScheduleList" value="${paymentScheduleList.size()}">
		<input type="hidden" id="milestoneDetails" value="${milestoneDetails.size()}">
		<div class="col-md-12 col-lg-12 col-sm-12 bold">
		<div class="col-md-5 col-lg-5 col-sm-8"> <h3>Click here to view complete details of Project</h3> </div>
		<div class="col-md-6 col-lg-6 col-sm-4"><a title="Click to View Complete Information" onclick="viewProjectDetails('/PMS/projectDetails/${encProjectId}')">View Project Details</a></div>
		
		</div>
		<div id="errorLog" class="red col-md-12 col-sm-12 col-xs-12 col-lg-12 text-center"></div>
		<%-- <div class="red col-md-12 col-sm-12 col-xs-12 col-lg-12 text-center">${isTechnicallyClosed}</div> --%>

<!------------------------ Check Project Team contains HOD or not [ 27/07/2023 added by_Anuj ] ----------------------------------------------->
		<c:if test="${(button=='true') && (currentRoleId==3 || currentRoleId==4) && isTechnicallyClosed==0}">
    			<div class="row pad-top text-center form_btn">					
        			<button type="button" class="btn btn-primary font_14" id="savehod">
            			<span class="pad-right"><i class="fa fa-folder"></i></span>Submit and Send for Approval To HOD
        			</button>						
    			</div>
		</c:if>
		<c:if test="${(button=='false') && (currentRoleId==3 || currentRoleId==4) && isTechnicallyClosed==0}">
    		<div class="row pad-top text-center form_btn">					
       			<button type="button" class="btn btn-primary font_14" id="save">
           			 <span class="pad-right"><i class="fa fa-folder"></i></span>Submit and Send for Approval To GC
        		</button>	
        		<button type="button" class="btn btn-primary font_14" id="savehod" disabled>
            			<span class="pad-right"><i class="fa fa-folder"></i></span>Submit and Send for Approval To HOD
        		</button>					
    		</div>
		</c:if>
		<c:if test="${(currentRoleId==15) && isTechnicallyClosed==0}">
    			<div class="row pad-top text-center form_btn">					
	       			<button type="button" class="btn btn-primary font_14" id="save">
	           			 <span class="pad-right"><i class="fa fa-folder"></i></span>Submit and Send for Approval To GC
	        		</button>						
    			</div>
		</c:if>

		<c:if test="${currentRoleId==5 && isTechnicallyClosed==0}">
    			<div class="row pad-top text-center form_btn">					
	       			<button type="button" class="btn btn-primary font_14" id="savepmo">
	           			 <span class="pad-right"><i class="fa fa-folder"></i></span>Submit and Send for Approval To PMO
	        		</button>						
    			</div>
		</c:if>
<!-------------------------------------------------------------------------------------------------------------------------------------->			
		
		
		<c:if test="${projectId>0 }"> 
		 <div class="row padded pull-left">
			<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" >
			<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
			</button>
			</div> 
		</c:if>
		</form:form>
		</div>
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/previewProject.js"></script>
</body>
</html>