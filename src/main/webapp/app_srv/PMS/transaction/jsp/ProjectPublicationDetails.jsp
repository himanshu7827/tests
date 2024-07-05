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
						<li class="active">Publication Details</li>
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
<%--          <div class="hidden" id="encCategoryId">${encCategoryId}</div> 
 --%>         
         
        <form:form id="form1" name="form1" modelAttribute="publicationDetailsModel" action="/PMS/SavePublicationDetails"  method="post" autocomplete="off">
			<form:hidden path="numProjectPublicationId"  id="numProjectPublicationId" value="0" />
			 <input type="hidden" name=encCategoryId id="encCategoryId" value="${encCategoryId}"/>
			 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
			          
			             <div class="container">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info panel-info1">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">Publication Details</h4>
									</div>
									<div class="panel-body text-center">
										<div class="row pad-top " id="stage_name_row">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">

											<label class="label-class"><spring:message
														code="publicationdetail.Publication.Type"/>:<span
														style="color: red;">*</span></label>
							</div>
			                 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														 <form:select path="strPublicationType" name="strPublicationType" onchange="show()" id="strPublicationType"
															class="select2Option">
															<form:option value="0">Select Publication Type</form:option>
															
															<form:option value="International/National Journal">International/National Journal</form:option>
															<form:option value="International/National conference"> International/National conference</form:option>
														 	<form:option value="Other"> Other</form:option>
													</form:select>
													</div>
													<form:errors path="strPublicationType" cssClass="red"></form:errors>
						   </div>
						                           </div>
						                           	<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group col-md-push-2 col-sm-push-1" id="description">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.Description"/>: </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->
													<form:textarea class="input-field" rows="3"
														
														path="strPublicationDescription" name="strPublicationDescription"></form:textarea>
													<form:errors path="strPublicationDescription" cssClass="error" />
												</div>
											</div>
										</div>
						                                         
						          </div>
						          						          	<div class="row ">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.date"/>:<span
														style="color: red;">*</span></label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" id="dtpubdt" name="dtpubdt" readonly='true' path="dtpubdt" />							
								<form:errors path="dtpubdt" cssClass="red" />
														
												</div>
											</div>
										</div>
						
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.title"/><span class="font_12"
													style="color: red;">(Proceeding/Book)</span>: </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->
													<form:textarea class="input-field" rows="3"
														
														path="strPublicaionTitle" name="strPublicaionTitle"></form:textarea>
													<form:errors path="strPublicaionTitle" cssClass="error" />
												</div>
											</div>
										</div>
										</div>
										<div class="row">
																<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.author"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->
													<form:textarea class="input-field" rows="3"
														
														path="strAuthorDetails" name="strAuthorDetails"></form:textarea>
													<form:errors path="strAuthorDetails" cssClass="error" />
												</div>
											</div>
										</div>
										
															<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.journal.name"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								
													<form:input class="input-field" rows="2"
														
														path="strJournalName" name="strJournalName"></form:input>
												
												</div>
													<form:errors path="strJournalName" cssClass="red"/>
											</div>
										</div>
										</div>
										
					   	<div class="row ">
															<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.conferenceCity"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								
													<form:input class="input-field" rows="2"
														
														path="strConferenceCity" name="strConferenceCity"></form:input>
												
												</div>
													<form:errors path="strConferenceCity" cssClass="red"/>
											</div>
										</div>
									
															<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.refNumber"/>:</label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								
													<form:input class="input-field" rows="2"
														
														path="strReferenceNumber" name="strReferenceNumber"></form:input>
												
												</div>
													<form:errors path="strReferenceNumber" cssClass="red"/>
											</div>
										</div>
										</div>
											<div class="row ">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.publisher"/>:</label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								
													<form:input class="input-field" rows="2"
														
														path="strPublisher" name="strPublisher"></form:input>
												
												</div>
													<form:errors path="strPublisher" cssClass="red"/>
											</div>
										</div>
										
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="publicationdetail.Organization"/>:</label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								
													<form:input class="input-field" rows="2"
														
														path="strOrganisation" name="strOrganisation"></form:input>
												
												</div>
													<form:errors path="strOrganisation" cssClass="red"/>
											</div>
										</div>
										</div>	
					
						
					
				
 						<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
						<div class="row pad-top">					
							<input type="button" class="btn btn-primary font_14" id="add" value="Save as Draft"/>
							<input type="button" class="btn btn-primary font_14" onclick="resetData()" value="Reset"/>
							<%-- <input type="button" class="btn btn-orange font_14" id="back" onclick="viewProjectDetails('/PMS/projectDetails/${monthlyProgressModel.encProjectId}/${encCategoryId}')" value="Back To Main Page"> --%>
							<button type="button" class="btn btn-success font_14" id="previewDetailsBtn">
								<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
							</button>
							<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
						</div>
						
						<div class="row pad-top" id="mainPrevNext">
						
						</div>
						
						 </sec:authorize> 
							
							  <!-- end of whole div-->
									
									
									</div></div></div></div></form:form></sec:authorize> 
									
			</div>
			<div class="container">
			<fieldset>
				 <legend class="info">List of Publications </legend>
				
				 	<table class="table table-responsive table-bordered compact display hover stripe"  id="inboxTable"  data-page-size="5"  id="tab1">
					<thead class="datatable_thead bold">
				        <tr>
<!-- 				         <th class="details-control" width="3%" ><input type="checkbox"></th>          
 -->
 				       <th>S.No</th>
 				       <th width="3%"></th>
  							<th class="details-control" width="10%"><spring:message
														code="publicationdetail.Publication.Type"/></th>
  								<th class="control" width="10%"><spring:message
														code="publicationdetail.date"/></th>
  									<th class="control" width="30%"><spring:message
														code="publicationdetail.title"/></th>
  										<th class="none"><spring:message
														code="publicationdetail.author"/></th>
  											<th class="control" width="30%"><spring:message
														code="publicationdetail.journal.name"/></th>
  												<th class="control"><spring:message
														code="publicationdetail.conferenceCity"/></th>
  													<th class="none"><spring:message
														code="publicationdetail.refNumber"/></th>
  													<th class="none"><spring:message
														code="publicationdetail.publisher"/></th>
  													<th class="none"><spring:message
														code="publicationdetail.Organization"/></th>
							<th class="nobreak" width="3%"></th>
							<th class="hidden"><spring:message
														code="publicationdetail.Description"/></th>
				        </tr>
					</thead>
					
					<tbody id="addRowData">
					 <%int i = 1 ;%>
					<c:forEach  items="${modelList}" var="list">
					<tr id="tr_${list.numProjectPublicationId}">
					<td><%=i%></td>
					<td></td>
					<td>${list.strPublicationType}</td>
					<td>${list.dtpubdt}</td>
					<td>${list.strPublicaionTitle}</td>
					<td>${list.strAuthorDetails}</td>
					<td>${list.strJournalName}</td>
					<td>${list.strConferenceCity}</td>
					<td>${list.strReferenceNumber}</td>
					<td>${list.strPublisher}</td>
					<td>${list.strOrganisation}</td>
					<td>
					<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
			<div id="Edit"><input type="hidden"  value="${list.numProjectPublicationId}" >	<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" aria-hidden="true"></span>
							</div> </sec:authorize> </td>
					<td class="hidden">${list.strPublicationDescription}</td>
					</tr>
					<%i++; %>
					</c:forEach>
					</tbody>
					
				  <!-- <tfoot class="footable-pagination">
				        <tr>
				          <td colspan="6"><ul id="pagination" class="footable-nav"></ul></td>
				        </tr>
				      </tfoot> -->
				</table>				
				</fieldset>
			</div> <!--end of div before table-->
			</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/ProjectPublicationDetails.js?v=1"></script>
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>

</html>
