<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
						<li class="active">Patch Tracker</li>
					</ol>
				</div>
			</div>						
				<div class="row "></div>
				
				<!-- for flashing the success message -->
			
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
		
	
 			<sec:authorize access="hasAuthority('WRITE_PATCH_TRACKER')">	
 		<form:form id="form1" modelAttribute="patchTrackerModel" action=" /PMS/mst/addpatchdetails" method="post" autocomplete="off">
			
			             <div class="container">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info panel-info1">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">Patch Tracker - Traces Every Span</h4>
									</div>
									<div class="panel-body text-center">
							
							
							
							
<!-- Drop down for Severity -->										<div class="row pad-top " id="stage_name_row">
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"> <spring:message
														code="Patch_Tracker.severity"/>:<span
														style="color: red;">*</span></label>
							</div>
			                 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														 <form:select path="severity" 
															class="select2Option">
															<form:option value="0">-- Select Severity --</form:option>
															<form:option value="Major">MAJOR </form:option>
															<form:option value="Minor">MINOR </form:option>
														 </form:select>
													</div>
	<!-- errors tag to get server side error --><form:errors path="severity" cssClass="red"></form:errors>
						   							</div>
						                           </div>
						                                        
						          				</div>
	
	<!-- Drop Down For Input Type -->				
									<div class="row pad-top " id="stage_name_row">
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
														code="Patch_Tracker.type"/>: <span
														style="color: red;">*</span></label>
							</div>
			                 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														 <form:select path="type" id="type"
															class="select2Option">
															<form:option value="0">-- Select Type --</form:option>
															<form:option value="Bug"> Bug</form:option>
															<form:option value="Enhancement">Enhancement</form:option>
															<form:option value="NewDevelopment">New Development</form:option>
															<form:option value="ChangeRequest">Change Request</form:option>
														 </form:select>
													</div>
													<form:errors path="type" cssClass="red"></form:errors>
						   </div>
						                           </div>
						                                         
						          </div>
							
						
<!--Input for Description  --> 		
                                           	<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.description"/> :<span
													style="color: red;">*</span> </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now --><i class="fa fa-address-book icon "></i>
													<form:textarea class="input-field" rows="3"
														
														path="strdescription"></form:textarea>
													<form:errors path="strdescription" cssClass="error" />
												</div>
											</div>
										</div>
										</div>

<!-- Input for Requested By : (optional) -->
                                   
                                   
                                   
                                      <div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify name1">

												<label class="label-class"><spring:message
														code="Patch_Tracker.requiredBy" />:</label>
											</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container autocomplete">
													<i class="fa fa-user icon"></i>
													<form:input class="input-field" path="strRequestedBy"/>													
												</div>
												<form:errors path="strRequestedBy" cssClass="red"/>
											</div>
										</div>
										</div>
		
		
	<!-- Input for Filenames -->
                                               	<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.nameOfFiles" />:<span
													style="color: red;">*</span> </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								<i class="fa fa-address-book icon "></i>
													<form:textarea class="input-field" rows="2"
														
														path="strNameOfFiles"></form:textarea>
												
												</div>
													<form:errors path="strNameOfFiles" cssClass="red"/>
											</div>
										</div>
										</div>
										
<!-- Input for Team Members -->
                                               	<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.teamMembers" />:<span
													style="color: red;">*</span> </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->								<i class="fa fa-address-book icon "></i>
													<form:textarea class="input-field" rows="2"
														
														path="strTeamMembers"></form:textarea>
													
												</div>
												<form:errors path="strTeamMembers" cssClass="red" />
											</div>
										</div>
										</div>
																				
 <!-- Input for BugZilla Bug Id (Optional) -->  
                              		<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.bugZillaId" />:</label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
<!--  Keeping same icon for now -->								<i class="fa fa-mobile icon"></i>
													<form:input class="input-field"
														
														path="strBugId" />
													<form:errors path="strBugId" cssClass="red" />
												</div>
											</div>
										</div>
										</div> 
<!--Input for date of Deployment -->     	<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.dateOfDeployment" />:<span
													style="color: red;">*</span> </label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="depDate" />							
								<form:errors path="depDate" cssClass="red" />
														
												</div>
											</div>
										</div>
										</div>                        	
		


<!-- Input For SVN Version No. -->	<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.svnNo" />:</label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
<!--  Keeping same icon for now -->								<i class="fa fa-mobile icon"></i>
													<form:input class="input-field"
														
														path="strSvnNo" />
													
												</div>
												<form:errors path="strSvnNo" cssClass="red"/>
											</div>
										</div>
										</div> 

<!-- Input for Restart Required (optional) -->
				<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
													<label class="label-class"><spring:message
														code="Patch_Tracker.restartRequired" />:</label>
												</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
														<form:radiobutton class="toggle-on" path="valid" value="true"
														 id="toggle-on1" />
														<form:label path="valid" for="toggle-on1"
															class="btn inline  zero round no-pad">
															<span>YES</span>
														</form:label>
													</div>
													<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
														<form:radiobutton class="toggle-off" path="valid" value="false"
															id="toggle-off2" />

														<form:label path="valid" for="toggle-off2"
															class="btn round inline zero no-pad">
															<span class="">No</span>
														</form:label>
													</div>
												</div>
											</div>
										</div>
			
<!-- Input for Stage   -->
						<div class="row pad-top " id="stage_name_row">
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
														code="Patch_Tracker.stage" />:<span
														style="color: red;">*</span></label>
							</div>
			                 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														 <form:select path="stage" 
															class="select2Option">
															<form:option value="0">-- Select Stage --</form:option>
															<form:option value="Development">Development</form:option>
															<form:option value="UAT">UAT</form:option>
															<form:option value="Production">Production</form:option>
														 </form:select>
													</div>
													<form:errors path="stage" cssClass="red">  </form:errors>
						   </div>
						                           </div>
						                                          
						          </div>
							
						 <!-- Input For Module -->
               	<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Patch_Tracker.modules" />: </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								<i class="fa fa-address-book icon "></i>
													<form:textarea class="input-field" rows="2"
														
														path="strModules"></form:textarea>
													
												</div>
												<form:errors path="strModules"  cssClass="red"/>
											</div>
										</div>
										</div>
<!-- Submit Button -->		<sec:authorize access="hasAuthority('WRITE_PATCH_TRACKER')">					
                            						<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					</div>
					</sec:authorize>
					
					</div>
					</div>
					</div>
					</div>
						
		</form:form>
	 	</sec:authorize> 
			</div>
		</section>

<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/patchTracker.js"></script>
 </body>
</html>