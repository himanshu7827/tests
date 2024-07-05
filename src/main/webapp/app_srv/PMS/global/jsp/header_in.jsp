
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="application/x-javascript">
	
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 

</script>

</head>
<style>
.ui-dialog-titlebar-close {
	background:
		url("http://code.jquery.com/ui/1.10.3/themes/smoothness/images/ui-icons_888888_256x240.png")
		repeat scroll -93px -128px rgba(0, 0, 0, 0) !important;
}

.ui-dialog-titlebar-close:hover {
	background:
		url("http://code.jquery.com/ui/1.10.3/themes/smoothness/images/ui-icons_222222_256x240.png")
		repeat scroll -93px -128px rgba(0, 0, 0, 0);
}
/* bhavesh (10-10-23) added checkmark class */
.checkmark::before {
            content: "\2713"; /* Checkmark Unicode character */
            margin-right: 5px;
            color: green;
        }

</style>
<script>

//Bhavesh (13-10-23) called functionForDefault() to set default value if not set
$(document).ready(function() {
	functionForDefault();
});

//Bhavesh (10-10-12) function to fetch the default role and add checkmark
function functionForDefault() {
	
	 var ajaxResultContent;
	$.ajax({
       type: "POST",
       url: "/PMS/popUpRolesForDefault",
       dataType:"json",
       success: function(data) {
           // Update the div with the data received from the AJAX call
           $("#ajaxResult").text(data.numProjectId);
           
           // Capture the content of the div
            ajaxResultContent = $("#ajaxResult").html();
           
                         
           def(data.numProjectId);
           defaultCompare()
       },
       error: function() {
           /* alert("An error occurred while making the AJAX request."); */
       }
       
   });
	
}
//Bhavesh (10-10-12) END of function to fetch the default role and add checkmark

//Bhavesh (13-10-23) fuunction to set default role if Default role not set
function def(id){
		
		var elements = document.querySelectorAll(".rolId");
		var dataArrayRolId = [];

		elements.forEach(function(element) {
			dataArrayRolId.push(element.textContent); //pushed all the role Ids in  dataArrayRolId
		});
		
		
		var spanElementsProjectId = [];

		// Use JavaScript to find and store each <span> element
		var spans = document.querySelectorAll("span#spanId");

		// Loop through the elements and add them to the array
		spans.forEach(function(span) {
			spanElementsProjectId.push(span.textContent);//pushed all the Project Ids in   spanElementsProjectId
		});
			
		if(spanElementsProjectId.length>0){
			
			
			if (spanElementsProjectId.includes(""+id)) {
			    console.log("");
			} else {
			    /* console.log("Array does not contain idRol "+id); */
			    
			    var elements1 = document.querySelectorAll(".nameRoleId");
				var dataArrayRoleName = [];

				elements1.forEach(function(element) {
					dataArrayRoleName.push(element.textContent); //pushed all the Role Name in  dataArrayRoleName
				});
				
				/* console.log("dataArrayRoleName "+dataArrayRoleName); */
					for (var i = 0; i < dataArrayRoleName.length; i++) {
						
				    	if(dataArrayRoleName.includes("Head of Department")) {
					    if (dataArrayRoleName[i]=="Head of Department") {
					   
					    	
					    	setRolesDefault(dataArrayRolId[i]);
					    	break;
					    }
					    else {
					    	continue;
					    }
				    	}
				    	else if(dataArrayRoleName.includes("Project In-charge")){
				    		if (dataArrayRoleName[i]=="Project In-charge") {
								   
						    	
				    			setRolesDefault(dataArrayRolId[i]);
						    	break;
						    }
						    else {
						    	continue;
						    }
				    		
				    		
				    	}
				    	else if(dataArrayRoleName.includes("Project Manager")){
				    		if (dataArrayRoleName[i]=="Project Manager") {
								   
						    	
				    			setRolesDefault(dataArrayRolId[i]);
					 	        break;
						    }
						    else {
						    	continue;
						    }
				    		
				    		
				    	}
				    	
				    	else if(dataArrayRoleName.includes("Module Leader")){
				    		if (dataArrayRoleName[i]=="Module Leader") {
								   
						    	
				    			setRolesDefault(dataArrayRolId[i]);
						    	break;
						    }
						    else {
						    	continue;
						    }
				    		
				    		
				    	}
				    	else if(dataArrayRoleName.includes("Team Member")){
				    		if (dataArrayRoleName[i]=="Team Member") {
								   
						    	
				    			setRolesDefault(dataArrayRolId[i]);
						    	break;
						    }
						    else {
						    	continue;
						    }
				    		
				    		
				    	}
					    
					   
					}
					
					
			}
		
		}

	}
//Bhavesh (13-10-23) END of fuunction to set default role if Default role not set

function setRolesDefault(id) {
		console.log("setRoles "+id);
		var idRole=id;
		
		var flag = 2;
		if (id != null || id !== undefined) {

			$.ajax({
				type : "POST",
				url : "/PMS/updateRoles",
				data : {"ids": id , "flag" : flag},
				success : function(response) {
					window.parent.location = "/PMS/dashboard";
				},
				error : function(e) {
					alert('Error in updating Roles: ' + e);

				}
			});

		} else {
			alert("Kindly select a Role");
			return false;
		}
		
	}
	
	function setRoles(id) {
		console.log("setRoles "+id);
		var idRole=id;
		
		var flag = 1;
		if (id != null || id !== undefined) {

			$.ajax({
				type : "POST",
				url : "/PMS/updateRoles",
				data : {"ids": id , "flag" : flag},
				success : function(response) {
					window.parent.location = "/PMS/dashboard";
				},
				error : function(e) {
					alert('Error in updating Roles: ' + e);

				}
			});

		} else {
			alert("Kindly select a Role");
			return false;
		}
		
	}
	
	
	
	
	//BHavesh compare the existing project id with default id if match then added class check mark
	function defaultCompare() {
		
		
		var  ajaxResultContent= document.getElementById("ajaxResult").textContent;
		
		var span=document.getElementById("spanId");
		
		var spanElements = [];

		// Use JavaScript to find and store each <span> element
		var spans = document.querySelectorAll("span#spanId");

		// Loop through the elements and add them to the array
		spans.forEach(function(span) {
		    spanElements.push(span.textContent);
		});
		
	
		if(ajaxResultContent>0){
		for (var i = 0; i < spanElements.length; i++) {
			
	    	
		    if (ajaxResultContent ==spanElements[i]) {
		   
		        $("#"+(i+1)).addClass("checkmark");
		        break;
		        
		    }
		}
		
		}
		
		
		
	}
	//Bhavesh (10-10-12) END of compare the existing project id with default id if match then added class check mark
	
	
	
	
</script>
<body >


	<!--header start-->
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
		<header class="header fixed-top clearfix"> <!--logo start-->

		<div class="col-md-2 col-lg-2 col-xs-3 col-sm-3 zero">
			<div class="brand">
				<a href="${pageContext.request.contextPath}/Homepage" class="logo">
					<img src="/PMS/resources/app_srv/PMS/global/images/cdac_logo.png" />				
				</a>
				
				<div class="sidebar-toggle-box">				
					<div class="fa fa-bars"></div>
				</div>
			</div>
		</div>
		<!--logo end-->
		

		<div class="col-md-6 col-lg-6 col-xs-5 col-sm-4 zero">
		<!-- 	<div
				class=" notify-row col-md-12 col-lg-12 col-sm-12 col-xs-12 zero "
				id="top_menu">

				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<p class="title_pms">Project Management System</p>
				</div>
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<h5 class="orange pms_sub_heading">Center For Development of
						Advanced Computing</h5>
				</div>

			</div> -->
			<div class="header-nav-content notify-row" id="top_menu">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
				<h1>
					<span class="font-40" style="color: /* #173faa */ #1e2787;">P</span><span
						class="" style="color: /* #3e71ca */ #1578c2;">roject</span><span
						class="font-40" style="color: /* #173faa */ #1e2787;"> M</span><span
						class="" style="color: /* #3e71ca */ #1578c2;">anagement</span> <span
						class="font-40" style="color: /* #173faa */ #1e2787;">S</span><span
						class="" style="color: /* #3e71ca */ #1578c2;">ystem</span>
				</h1>
				</div>
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
				<h5>Centre For Development of Advanced Computing (Noida)</h5>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-lg-4 col-xs-4 col-sm-4 pull-right">
			<div class="top-nav clearfix">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class="pull-right padded">
						<sec:authentication property="principal.lastLogin" var="lastLogin" />
						<c:if test="${not empty lastLogin}">
							<div class="header-right-left ">
								<spring:message code="form.label.lastLogin" />
								:<span class="orange bold"> ${lastLogin}</span>
							</div>
						</c:if>
					</div>
				</div>
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<ul class="nav pull-right top-menu">
						<div class="header-right-left">
							<!--notifications of menu start -->
							<ul class="roles-dropdown">
							<li class="dropdown head-dpdn">
					<a href="${pageContext.request.contextPath}/Homepage" class="">
						<i class="fa fa-home fa-2x" aria-hidden="true"></i>
					</a>	
					</li>
								<li class="dropdown head-dpdn"><a href="#" onclick="functionForDefault()"
									class="dropdown-toggle" style="color: #333;"
									data-toggle="dropdown" aria-expanded="false"><i
										class="fa fa-user-circle fa-2x black"></i></a>
									<ul class="dropdown-menu extended header_roles1">
								       
										<sec:authentication property="principal.employeeRoleList"
											var="authorities" />
										<c:forEach items="${authorities}" var="authority"
											varStatus="vs">
											
											<%-- Bhavesh (10-10-23)added div to get the default projectId --%>
											 <div id="ajaxResult" class="hidden"></div>
											  
											<li><a class="roleList" onclick="setRoles(this.id)"
												id="${authority.numEmpId}##${authority.numRoleId}##${authority.numProjectId}##${authority.numGroupId}##${authority.numOrganisationId}"><span id="${vs.count}"
													style="color: #003ff2; font-size: 14px;" class="bold nameRoleId">${authority.strRoleName}</span>
												<span id="spanID" class="hidden">${authority.numProjectId}</span>
												<span id="rolId${vs.count}" class="rolId hidden">${authority.numEmpId}##${authority.numRoleId}##${authority.numProjectId}##${authority.numGroupId}##${authority.numOrganisationId}</span>
												 <c:choose>
														<c:when test="${authority.numProjectId >0}">
															<br>(${authority.strProjectName})
											</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${authority.numGroupId >0}">
																	<br>(${authority.strGroupName})
											</c:when>
																<c:otherwise>
																	<br>(${authority.strOrganisationName})
											</c:otherwise>
															</c:choose>
														</c:otherwise>

													</c:choose> </a></li>
										</c:forEach>
										<c:if test="${fn:length(authorities)>1}">
											<sec:authentication property="principal.employeeId"
												var="employeeId" />


											<%-- <li><a class="roleList" onclick="setRoles(this.id)"
												id="${employeeId}##0##0##0##0"><span
													style="color: #003ff2; font-size: 14px;" class="bold">All
														Roles </span></a></li> --%>
											<li><a id='chng_role' style="color: #f01308;"
												onclick='checkValue()' href='#'> Change Default Role</a></li>
										</c:if>
										
									</ul></li>
							</ul>

						</div>



						<li class="dropdown"><a data-toggle="dropdown"
							class="dropdown-toggle" href="#"> <!--   <img alt="" src=""> -->
								<span class="username"> <sec:authentication
										property="principal.employeeName" /></span> <!-- <b class="caret"></b>  -->
						</a>
							<ul class="dropdown-menu extended logout">
								<li><a
									href="${pageContext.request.contextPath}/mst/UpdateEmployeeMaster?encEmpId=<sec:authentication
							property="principal.encEmployeeId" />"
									class="profile"><i class=" fa fa-suitcase"></i>Profile</a></li>

								<li><a href="/PMS/changePassword"><i class="fa fa-lock"></i>Change
										Password</a></li>

								<li><a href="/PMS/logout"><i class="fa fa-key"></i> Log
										Out</a></li>
								<li><a href="/PMS/logoutAllSessions"><i class="fa fa-power-off"></i> Log
										Out From All Devices</a></li>

							</ul></li>
						<!-- user login dropdown end -->

					</ul>
				</div>
				<!--search & user info end-->
			</div>
		</div>

		</header>
	</div>
	<!--header end-->
	<!-- modal for profile -->
	<!-- Modal -->
	<div class="modal profile-modal" id="profile-modal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-center text-uppercase">Profile</h4>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="modal-body ">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

						<!-- <p>hello</p> -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- end modal for profile -->
	<!--sidebar start-->
	<aside>
	<div id="sidebar" class="nav-collapse hide-left-bar">
		<!-- sidebar menu start-->
		<div class="leftside-navigation">
			<ul class="sidebar-menu" id="nav-accordion">
				<li class="sub-menu"><a href="javascript:;"> <i
						class="fa fa-book"></i> <span><spring:message
								code="menu.master.label" /></span></a>
					<ul class="sub">

						<!-- Added By Rashmi -->

						<sec:authorize access="hasAnyAuthority('ROLE_ADMIN','ROLE_PREVIOUS_ADMINISTRATOR')">
							<li><a
								href="${pageContext.request.contextPath}/admin/switchUser">
									<spring:message code="menu.master.InternalLogin" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('WRITE_PRIVILEGE')">
							<li><a
								href="${pageContext.request.contextPath}/mst/groupMaster"> <spring:message
										code="menu.master.group.label" />
							</a></li>
						</sec:authorize>
						
						
						
						<!-- Added by Himanshu on 10-05-2024 -->
						<sec:authorize access="hasAnyAuthority('TASK_DESC_PRIVILEGE')">
							<li><a
								href="${pageContext.request.contextPath}/mst/TimeSheetMaster">
									<spring:message code="menu.master.subtaskdescmaster.label" />
							</a></li>
						</sec:authorize>
							
						<sec:authorize
							access="hasAnyAuthority('READ_DESIGNATION_MST,MAKER_DESIGNATION_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/designationMaster">
									<spring:message code="menu.employee.designation.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_SKILL_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/SkillMaster"> <spring:message
										code="master.skill" />
							</a></li>
						</sec:authorize>
						
							<sec:authorize access="hasAuthority('READ_MILESTONE_TYPE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/MilestoneType"> <spring:message
										code="menu.milestoneType" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('WRITE_PRIVILEGE_MST')">
							<li><a href="${pageContext.request.contextPath}/privilege">
									<spring:message code="master.privilege" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_EMP_TYPE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/empTypeMaster">
									<spring:message code="menu.employeeType.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_DOC_STAGE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/docStageMaster">
									<spring:message code="menu.docStage.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_CLIENT_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/clientMaster">
									<spring:message code="menu.client.master.label" />
							</a></li>
							<li><a
								href="${pageContext.request.contextPath}/mst/endUserMaster">
									<spring:message code="menu.endUserMaster.master.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_CLIENT_CONTACT_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/clientContactPersonMaster">
									<spring:message code="menu.contactperson.master.label" />
							</a></li>
						</sec:authorize>


						<sec:authorize access="hasAuthority('READ_BUDGET_HEAD_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/BudgetHeadMaster">
									<spring:message code="menu.budgethead.master.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_DOCUMENT_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/documentMaster">
									<spring:message code="menu.docType.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_THRUSTAREA_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/ThrustAreaMaster">
									<spring:message code="menu.thrust.area.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize
							access="hasAuthority('READ_PARENT_ORGANISATION_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/parentOrganisationMaster">
									<spring:message code="parentOrganisation.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_ORGANISATION_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/organisationMaster">
									<spring:message code="menu.master.organisation.label" />
							</a></li>
						</sec:authorize>

						<!-- Added By Harshita -->
						<%-- 				<sec:authorize access="hasAuthority('READ_PROPOSAL_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/proposalMaster">
									<spring:message code="menu.module.proposal.label" />
							</a></li>
						</sec:authorize> --%>

						<%-- <sec:authorize access="hasAuthority('READ_DOCUMENT_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/projectModuleMaster">
									<spring:message code="menu.module.master.label" />
							</a></li>
						</sec:authorize> --%>

						<sec:authorize access="hasAuthority('READ_THIRD_PARTY_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/thirdPartyMaster">
									<spring:message code="menu.master.thirdparty.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_ANSWER_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/AnswerMaster">
									<spring:message code="master.answer.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_QUESTION_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/QuestionMaster">
									<spring:message code="master.question.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_POST_TRACKER_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/postTrackerMaster">
									<spring:message code="postTrackerMaster.post.title" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_APPROVAL_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/ApprovalMaster">
									<spring:message code="approval.master.menu" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('READ_PROJECT_ROLE')">
							<li><a
								href="${pageContext.request.contextPath}/mst/ProjectRoleMaster">
									<spring:message code="label.project.based.role" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('WRITE_NEWSLETTER')">
							<li><a
								href="${pageContext.request.contextPath}/CreateNewsLetterFilter"> <spring:message
										code="menu.newsletter.filter" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('WRITE_NEWSLETTER')">
							<li><a
								href="${pageContext.request.contextPath}/CreateNewsLetter"> <spring:message
										code="menu.newsletter" />
							</a></li>
						</sec:authorize>
						

					</ul></li>
	<sec:authorize access="hasAnyAuthority('READ_PROPOSAL_MST')">
		<li class="sub-menu">
				<a href="javascript:;">
					<i class="fa fa-paper-plane"></i> 
					<span><spring:message code="menu.proposal.label" /></span>
				</a>
			<ul class="sub">
				<sec:authorize access="hasAuthority('READ_PROPOSAL_MST')">
					<li><a
						href="${pageContext.request.contextPath}/applicationBasicDetails">
							<spring:message code="form.create.proposal" />
					</a></li>
				</sec:authorize>
				<sec:authorize access="hasAuthority('READ_PROPOSAL_MST')">
					<li><a
						href="${pageContext.request.contextPath}/ViewApplicationDetails">
							<spring:message code="menu.proposal.detail" />
					</a></li>
				</sec:authorize>			
				
			</ul>
		</li>
	</sec:authorize>
				
				<li class="sub-menu"><a href="javascript:;"> <i
						class="fa fa-th"></i> <span><spring:message
								code="menu.project.label" /></span>
				</a>
					
					<ul class="sub">
						
						<sec:authorize access="hasAuthority('UNDER_APPROVAL_PROJECTS')">
							<li><a
								href="${pageContext.request.contextPath}/mst/underApprovalProjects">
									<spring:message code="menu.project.underApproval" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('UNDER_CLOSURE_PROJECTS')">
							<li><a
								href="${pageContext.request.contextPath}/mst/underClosureProjects">
									<spring:message code="menu.project.underClosure" />
							</a></li>
						</sec:authorize>
												
						<sec:authorize access="hasAuthority('READ_PROJECT_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/ViewAllProjects">
									<spring:message code="menu.project.detail" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_COMPLETED_PROJECT')">
							<li><a
								href="${pageContext.request.contextPath}/mst/getAllCompletedProject">
									<spring:message code="menu.completed.master.label" />
							</a></li>
						</sec:authorize>						
						
						<sec:authorize
							access="hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/MilestoneReviewDetailMaster">
									<spring:message code="milestone.review.detail" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_DOC_STAGE_MAP')">
							<li><a
								href="${pageContext.request.contextPath}/mst/docStageMapping">
									<spring:message code="menu.docstage.mapping.label" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('VIEW_PROJECT_DOCUMENT')">
							<li><a
								href="${pageContext.request.contextPath}/mst/uploadProjectDocument">
									<spring:message code="menu.master.projectDocument.label" />
							</a></li>
						</sec:authorize>
					
						<sec:authorize access="hasAuthority('READ_PROJECT_INVOICE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/projectInvoiceMaster">
									<spring:message code="menu.project.invoice.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize
							access="hasAuthority('READ_PROJECT_PAYMENT_RECEIVED_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/projectPaymentReceived">
									<spring:message code="menu.project.payment.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize
							access="hasAuthority('READ_PROJECT_EXPENDITURE_DETAILS')">
							<li><a
								href="${pageContext.request.contextPath}/mst/projectExpenditureDetails">
									<spring:message code="menu.project.expenditure.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_MANPOWER_UTIL_MST')">
							<li><a
								href="${pageContext.request.contextPath}/manpowerUtilization">
									<spring:message code="manpower.utilization.form.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize
							access="hasAuthority('CHANGE_CLIENT_CONTACT_PERSON')">
							<li><a
								href="${pageContext.request.contextPath}/mst/changeProjectClientContactPerson">
									<spring:message code="menu.project.change.contactPerson.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('WRITE_PATCH_TRACKER')">
							<li><a
								href="${pageContext.request.contextPath}/mst/patchTracker">
									<spring:message code="menu.patch.tracker.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('WRITE_GROUP_MONTHLY_REPORT')">
							<li><a
								href="${pageContext.request.contextPath}/GroupMonthlyReport">
									<spring:message code="monthly.group.report.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('DESIGNATION_FOR_CLIENT')">
							<li><a
								href="${pageContext.request.contextPath}/designationForClient">
									<spring:message code="designation.for.client" />
							</a></li>
						</sec:authorize>

						<sec:authorize
							access="hasAuthority('DESIGNATION_CATEGORY_MAPPING')">
							<li><a
								href="${pageContext.request.contextPath}/projectCategorydesignationMapping">
									<spring:message code="designation.category.mapping" />
							</a></li>
						</sec:authorize>
						
					</ul></li>
				<li class="sub-menu"><a href="javascript:;"> <i
						class="fa fa-users"></i> <span><spring:message
								code="menu.employee.label" /></span>
				</a>
					<ul class="sub">

						<sec:authorize access="hasAuthority('READ_EMPLOYEE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/employeeMaster">
									<spring:message code="menu.employee.master.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_EMP_SALARY_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/EmployeeSalaryMaster">
									<spring:message code="menu.master.employesalary.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_EMPLOYEE_ROLE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/employeeRoleMaster">
									<spring:message code="employee.project.role.mapping" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('WRITE_ROLEPRIVILEGE_MST')">
							<li><a
								href="${pageContext.request.contextPath}/rolePrivilege"> <spring:message
										code="menu.employee.Role.Privilege.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('WRITE_EMP_ROLE_MAP_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/EmployeeRoleMapping">
									<spring:message code="menu.employee.Role.Map.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('PROJECT_TEAM_MAPPING')">
							<li><a
								href="${pageContext.request.contextPath}/mst/projectTeamMapping">
									<spring:message code="project.team.mapping" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('VIEW_APPROVED_JOB')">
							<li><a href="${pageContext.request.contextPath}/approvedJob">
									<spring:message code="approved.job.details.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('VIEW_APPROVED_JOB')">
							<li><a
								href="${pageContext.request.contextPath}/approvedJobs"> <spring:message
										code="approved.job.detail" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('WRITE_JOB_TITLE')">
							<li><a href="${pageContext.request.contextPath}/jobTitle">
									<spring:message code="employee.job.mapping" />
							</a></li>
						</sec:authorize>

						<sec:authorize
							access="hasAuthority('WRITE_ExitInterviewByEmployee_MST')">

							<li><a
								href="${pageContext.request.contextPath}/ExitInterview"> <spring:message
										code="master.interview.exit.employee" />
							</a></li>
						</sec:authorize>

						<sec:authorize
							access="hasAuthority('WRITE_ExitInterviewByFlaNdSla_MST')">
							<li><a
								href="${pageContext.request.contextPath}/InterviewExit"> <spring:message
										code="master.interview.exit" />
							</a></li>
						</sec:authorize>
						<sec:authorize
							access="hasAuthority('WRITE_ExitInterviewByHr_MST')">
							<li><a
								href="${pageContext.request.contextPath}/InterviewExitByHr">
									<spring:message code="master.interview.exit.hr" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('RELEASE_EMPLOYEE')">
							<li><a
								href="${pageContext.request.contextPath}/mst/releaseEmployee">
									<spring:message code="menu.employee.release.label" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('RELIEVED_EMPLOYEE')">
							<li><a
								href="${pageContext.request.contextPath}/mst/relievedEmployee">
									<spring:message code="menu.employee.relieved.label" />
							</a></li>
						</sec:authorize>


						<sec:authorize access="hasAuthority('')">
							<li><a href="${pageContext.request.contextPath}/mst/"> <spring:message
										code="" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('')">
							<li><a href="${pageContext.request.contextPath}/mst/"> <spring:message
										code="" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('')">
							<li><a href="${pageContext.request.contextPath}/mst/"> <spring:message
										code="" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('')">
							<li><a href="${pageContext.request.contextPath}/mst/"> <spring:message
										code="" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('')">
							<li><a href="${pageContext.request.contextPath}/mst/"> <spring:message
										code="" />
							</a></li>
						</sec:authorize>
					</ul></li>
					


<sec:authorize access="hasAnyAuthority('READ_ACTIVITY_MST','READ_SUBACTIVITY_MST','READ_TASKDETAIL_MST','READ_TIMESHEET_MST')">
				<li class="sub-menu">
					<a href="javascript:;"> <i
							class="fa fa-clock-o"></i> <span><spring:message
									code="menu.TimeSheet.label" /></span>
					</a>
					<ul class="sub">
						<!--Start Timesheet headers - JAY -->
						<sec:authorize access="hasAuthority('READ_TIMESHEET_MST')">
							<li>
								<a
									href="${pageContext.request.contextPath}/timesheethome">
										Timesheet
								</a>
							</li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_TIMESHEET_REPORT_MST')">
							<li>
								<a
									href="${pageContext.request.contextPath}/reports">
										Timesheet Reports
								</a>
							</li>
						</sec:authorize>
						<!--End Timesheet headers - JAY -->
						<sec:authorize access="hasAuthority('READ_ACTIVITY_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/activityMaster">
									<spring:message code="menu.TimeSheet.activityMaster.label" />
							</a></li>
						</sec:authorize>

						<sec:authorize access="hasAuthority('READ_SUBACTIVITY_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/subActivityMaster">
									<spring:message code="menu.TimeSheet.subActivityMaster.label" />
							</a></li>
						</sec:authorize>
						<sec:authorize access="hasAuthority('READ_TASKDETAIL_MST')">
							<li><a
								href="${pageContext.request.contextPath}/mst/TaskDetailsMaster">
									<spring:message code="menu.task.master.label" />
							</a></li>
						</sec:authorize>
						
						
						
							
						


					</ul>
				</li>
				</sec:authorize>	
				<sec:authorize access="hasAnyAuthority('READ_GENERATE_REPORT','READ_GENERATE_BIRTHDAY_REPORT','GENERATE_REPORT_INTERFACE','ACCESS_ROLE_REPORT','READ_REPORT','AllprojectReport')">
					<li class="sub-menu"><a href="javascript:;"> <i
						class="fa fa-clock-o"></i> <span><spring:message
								code="menu.report.label" /></span>
				</a>
					<ul class="sub">
					
					<sec:authorize access="hasAuthority('READ_REPORT')">
							<li><a
								href="${pageContext.request.contextPath}/EditReport">
									<spring:message code="menu.add.edit.report" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('ACCESS_ROLE_REPORT')">
							<li><a
								href="${pageContext.request.contextPath}/roleReport">
									<spring:message code="menu.employee.Role.Report.label" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('READ_GENERATE_REPORT')">
							<li><a
								href="${pageContext.request.contextPath}/generateMonthlyProgressReport">
									<spring:message code="generate.report.menu" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('READ_GENERATE_BIRTHDAY_REPORT')">
							<li><a
								href="${pageContext.request.contextPath}/generateMonthlyBirthDayReport">
									<spring:message code="generate.birthday.report.menu" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('GENERATE_REPORT_INTERFACE')">
							<li><a href="${pageContext.request.contextPath}/generateDateWiseReports">
									<spring:message code="menu.report.misc.report" />
							</a></li>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('GENERATE_REPORT_EMP_CONTRACT_DET')">
							<li><a href="${pageContext.request.contextPath}/generateEmployeeDetails">
									<spring:message code="menu.report.misc.emp.cont.report" />
							</a></li>
						</sec:authorize>
						
						
						
						<!-- Bhavesh Generate Weekly Report  -->
						<sec:authorize access="hasAuthority('READ_GENERATE_WEEKLY_REPORT')">
							
							
							<li class="btn "  onClick="downloadWeeklyReport()" style="color: white;font-size: 12px;margin-left: 15px;">
							Download Weekly Report
							</li>
						</sec:authorize>		
						
						<!-- Add new menu button for All Project report By varun -->
						<sec:authorize access="hasAuthority('All_Project_Report')">
							<li><a href="${pageContext.request.contextPath}/ViewAllProjectDetailReport">
									Project Custom Report
							</a></li>
						</sec:authorize>				

					</ul></li>
				</sec:authorize>

			</ul>
		</div>
		<!-- sidebar menu end-->
	</div>
	</aside>
	<!--sidebar end-->

</body>
<div class="container padded">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
		<div id="dialog1" style="display: none; z-index: 100000;"
			title="Dialog Title">
			<iframe frameborder="0" width="400" height="300"
				src="/PMS/popUpRoles"></iframe>
		</div>
	</div>
</div>
<script type="text/javascript">
	$('#chng_role').click(function($e) {
		$e.preventDefault();
	});
	var dialogOpts = {
		close : function() {
			$('#mytesthide').show();
		}
	};

	function checkValue() {
		$('.ui-button-icon-primary').remove();

		/*  $( "#dialog1" ).dialog(); */
		$("#dialog1")
				.dialog(
						{
							width : 'auto',
							autoOpen : false,
							title : 'Choose your role',
							draggable : true,
							resizable : false,
							modal : true,
							height : 'auto',

							open : function() {
								var closeBtn = $('.ui-dialog-titlebar-close');

								closeBtn
										.append('<span class="ui-button-icon-primary ui-icon-closethick"></span>');
							}

						});

		$('#mytesthide').hide();
		$("#dialog1").dialog("open");
		$("#dialog1").show();

		$("#dialog1").dialog(dialogOpts);

	}
	
	
	/*-----------------  For Download the Excel Weekly Reprt [ added by Bhavesh ] -------------------------------------------------------------*/
	function downloadWeeklyReport() {
		
		$.ajaxRequest
				.ajax({
					type : "POST",
					url : "/PMS/downloadWeeklyReport",
					
					success : function(data) {
											
						if(data){
							/* alert("success"); */
						downloadTemplate('Weekly_Report.xlsx');
						}else{
							swal("File download Failed.!");
						}
					}});
	}

	function downloadTemplate(documentName){
		openWindowWithPost('POST','/PMS/downloadTemplate',{"template" : "EMPLOYEE_REG_TEMPLATE","documentTypeName":documentName},'_blank');
	}
</script>
</html>
