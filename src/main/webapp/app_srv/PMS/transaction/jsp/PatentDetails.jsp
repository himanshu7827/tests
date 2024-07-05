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
<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">	 
	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Patent Details</li>
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
        
           <div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
<%--          <div class="hidden" id="encCategoryId">${encCategoryId}</div> 
 --%>         
        
        <form:form id="form1" name="form1" modelAttribute="patentDetailsModel" action="/PMS/SavePatentDetails"  method="post" autocomplete="off">
			<form:hidden path="projectPatentId" id="projectPatentId" value="0"/>
 					 
			 <input type="hidden" name=encCategoryId id="encCategoryId" value="${categoryId}"/>
			 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
			             <div class="container">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info panel-info1">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">Patent  Details</h4>
									</div>
									<div class="panel-body text-center">
					  	<div class="row ">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.title"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->
													<form:textarea class="input-field" rows="3"
														
														path="strPatentTitle" id="strPatentTitle" name="strPatentTitle"></form:textarea>
													<form:errors path="strPatentTitle" cssClass="error" />
												</div>
											</div>
										</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.inventorname"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								<i class="fa fa-user-circle-o icon"></i>
													<form:input class="input-field" rows="2"
														
														path="strInventorName" id="strInventorName" name="strInventorName"></form:input>
												
												</div>
													<form:errors path="strInventorName" cssClass="red"/>
											</div>
										</div>
									
										</div>
										<div class="row ">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.inventor.address"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->
													<form:textarea class="input-field" rows="3"
														
														path="strInventorAddr" id="strInventorAddr" name="strInventorAddr"></form:textarea>
													<form:errors path="strInventorAddr" cssClass="error" />
												</div>
											</div>
										</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.refNumber"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								<i class="fa fa-file-text icon "></i>
													<form:input class="input-field" rows="2"
														
														path="strReferenceNum" id="strReferenceNum" name="strReferenceNum"></form:input>
												
												</div>
													<form:errors path="strReferenceNum" cssClass="red"/>
											</div>
										</div></div>
													<div class="row ">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
													<label class="label-class"><spring:message
														code="forms.status"/>:<span
														style="color: red;">*</span></label>
												</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
														<form:radiobutton onclick="filedate()" class="toggle-on" path="strIsFiled" value="Y"
														 id="isfiledy" name="radio"/>
														<form:label path="strIsFiled" for="toggle-on1"
															class="btn inline  zero round no-pad">
															<span>Filed</span>
														</form:label>
													<div>&nbsp; &nbsp; &nbsp; </div>
													<!-- <div class="col-md-3 col-lg-3 col-sm-5 col-xs-13 "> -->
														<form:radiobutton onclick="awarddate()" class="toggle-off" path="strIsFiled" value="N"
															id="strIsAwardedy" name="radio" />

														<form:label path="strIsFiled" for="toggle-off2"
															class="btn round inline zero no-pad">
															<span>Awarded</span>
														</form:label>
												<!-- 	</div> -->
												</div>
												</div>
											</div>
											
										
								<div id="filingdate">
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.filingDate"/>:<span
														style="color: red;">*</span></label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
													<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" id="dtFilingDate" name="dtFilingDate" path="dtFilingDate" autocomplete="off"/>							
								<form:errors path="dtFilingDate" cssClass="red" />
														
												</div>
											</div>
										</div>
										</div>
										<div id="Awarddate">
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class">Award Date:<span
														style="color: red;">*</span></label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
													<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" id="dtAwardDate" name="dtAwardDate" path="dtAwardDate" autocomplete="off"/>							
								<form:errors path="dtAwardDate" cssClass="red" />
														
												</div>
											</div>
										</div>
										</div>
										
										</div>
																				<div class="row ">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.country"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
											<div class="input-container">
					<!-- keeping icon same for now -->
													<form:textarea class="input-field" rows="3"
														
														path="strCountryDetials" name="strCountryDetials"></form:textarea>
													<form:errors path="strCountryDetials" cssClass="error" />
												</div>
											</div>
										</div>
									
								<%-- <div class="col-md-6 col-lg-6 col-sm-6 col-xs-6 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="patentdetails.status"/>:<span
														style="color: red;">*</span></label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
<!-- keeping icon same for now -->								<i class="fa fa-file-text icon "></i>
													<form:input class="input-field" rows="2"
														
														path="strStatus" name="strStatus"></form:input>
												
												</div>
													<form:errors path="strStatus" cssClass="red"/>
											</div>
										</div> --%>
										</div>
									
									
										<div class="row pad-top  form_btn">
					
								<input type="button" class="btn btn-primary font_14" id="add" value="Save as Draft">
								<input type="button" class="btn btn-primary font_14" onclick="resetData()" value="Reset"/>
								<button type="button" class="btn btn-success font_14" id="previewDetailsBtn">
									<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
								</button>
							<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
						</div>
						
							<div class="row pad-top" id="mainPrevNext">
						
						</div>
							  <!-- end of whole div-->
									
									
									</div></div></div></div></form:form>
									<div class="container">
										<fieldset>
				    <legend class="info">
				   List of Patents
				    </legend>
				    <div class="whole">
					   
					<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="button" value="Delete" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div> 
							</div>
						</div>
				
				
				 				  <table class="table table-responsive table-bordered compact display hover stripe" width="40%" id="inboxTable"  data-page-size="5"  id="tab1">
					<thead class="datatable_thead bold">
				        <tr>  <th width="3%" class="check">Select</th>
				        <th class="control">S.No</th>
 <th width="3%"></th>
 <th class="details-control" width="20%"><spring:message code="patentdetails.title"/></th>
 <th class="control" width="20%"><spring:message code="patentdetails.inventorname"/></th>
 <th class="none"><spring:message code="patentdetails.inventor.address"/></th>
 <th class="control" width="10%"><spring:message code="patentdetails.refNumber"/></th>
 <th class="control"><spring:message code="forms.status"/></th>
 <th class="control">Date</th>
<th class="control"><spring:message code="patentdetails.country"/></th>
<%-- <th class="control"><spring:message code="patentdetails.status"/></th> --%>
							<th class="nobreak"></th>	
				        </tr>
					</thead>
					
					<tbody id="addRowData">
					 <%int i = 1 ;%>
					<c:forEach  items="${modelList}" var="list">
					<tr id="tr_${list.projectPatentId}">
					<td><input type="checkbox" path="checkbox"
														class="CheckBox" id="CheckBox"
															value="${list.projectPatentId}" autocomplete="off"></td>
					<td><%=i%></td>
					<td></td>
					<td>${list.strPatentTitle}</td>
					<td>${list.strInventorName}</td>
					<td>${list.strInventorAddr}</td>
					<td>${list.strReferenceNum}</td>
					<td>${list.strIsFiled}</td>
					<c:choose>
				         <c:when test = "${list.strIsFiled=='Filed'}">
				           <td>${list.dtFilingDate}</td>
				         </c:when>
				      <c:otherwise>
				              <td>${list.dtAwardDate}</td>
				         </c:otherwise>
				      </c:choose>
					<td>${list.strCountryDetials}</td>
					<%-- <td>${list.strStatus}</td> --%>
					<td>
							 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
			<div id="Edit"><input type="hidden"  value="${list.projectPatentId}" >	<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" aria-hidden="true"></span>
							</div> </sec:authorize> 
							</td>
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
				</div> <!--end of div before table-->
				</fieldset>
					</div>				
			</div>
			</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/PatentDetails.js"></script>		
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
	
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
</sec:authorize>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>
</html>