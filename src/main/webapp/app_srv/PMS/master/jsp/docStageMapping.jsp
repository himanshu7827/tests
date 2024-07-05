<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
			<li class="active">Document Stage Mapping</li>
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
		
		<sec:authorize access="hasAuthority('WRITE_DOC_STAGE_MAP')">	
		<form:form id="form_doc_stage_mst" name="form_doc_stage_mst" modelAttribute="docStageMappingModel" action="/PMS/mst/saveUpdateDocStageMapping" method="post">
	<form:hidden path="numId"/> 
	<form:hidden path="numIds"/>
		<div class="container">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Document Type Master</h4></div>
					<div class="panel-body text-center">
				
				
						<div class="row pad-top " id="stage_name_row">
						
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="master.stageName"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
											<form:select path="stageId" id="stageId" class="select2Option">
				<form:option value="0">[Select Stage:]</form:option>
							<c:forEach items="${stagelist}" var="liststage" >
								<form:option value="${liststage.numId}">
								<c:out value="${liststage.strStageName}"></c:out></form:option>
							</c:forEach>
				</form:select> 
								</div>
							</div>
						</div>
						
						</div> 
					
					
				<div class="row  text-center" id="doc_name_row">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="document.documentName"/>:<span
									style="color: red;">*</span></label>
							</div>

							
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
								
							<form:select path="documentId" id="documentId" class="select2Option">
				<form:option value="0">[Select Document:]</form:option>
							<c:forEach items="${documentlist}" var="listdocument" >
								<form:option value="${listdocument.numId}">
								<c:out value="${listdocument.docTypeName}"></c:out></form:option>
							</c:forEach>
				</form:select> 
									
								</div>
							</div> 
				
							
							
						</div> 
						</div>
					
			

			<div class="row  text-center" id="doc_seq_row">
					
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="docstagemap.seqNo"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-sort-numeric-asc icon"></i> 
									 <form:input type ="text" class="input-field" placeholder="Document Sequence Number" path="docSeq"/>
									<form:errors path="docSeq" cssClass="error" />
								</div>
							</div> 
							
							
						</div>
					</div>  
					
					<div class="row text-center" id="is_mand_row">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="master.mandatory"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12  ">
								<form:radiobutton path="mandatory"  value="true" id="toggle-on"	 />
									<form:label path="mandatory"  for="toggle-off"
										class="btn inline  zero round no-pad">
										<span>Yes</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="mandatory" value="false" id="toggle-off" />

									<form:label path="mandatory" for="toggle-off"
										 class="btn round inline zero no-pad">
										<span class="">No</span>
									</form:label>
</div>
							</div>
						</div>						
					</div> 
					<div class="row pad-top">
						<a href="#" class="new_record blue no-underline font_16  text-shadow hidden" id="new_record" title="Click Here to Add New Record">
					<span class="pull-right padded margin-right bg-blue-text hidden blink" id="record_span">
					Click Here to Add New Record</span></a>
					</div>
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					
					</div> 
					<div class="row pad-top">
						<!--Start data table-->
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom  hidden" id="datatable_box">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
			<sec:authorize access="hasAuthority('WRITE_DOC_STAGE_MAP')">	
			<div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type="button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div>
			</sec:authorize>
			<table id="example" class="table table-striped table-bordered hidden"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20">Select</th>
						<th><spring:message code="document.documentName"/></th>
						<th><spring:message code="master.stageName"/></th>
						<th><spring:message code="docstagemap.seqNo"/></th>											
					</tr>
				</thead>
				</table>
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
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

	<!--End of main-container-->
	<!-- </div> -->
	<!-- end of container-->
</div>
</section>


	 
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/docStageMapping.js"></script>
	
	
	
<script>

</script>
	 
	
</body>
</html>